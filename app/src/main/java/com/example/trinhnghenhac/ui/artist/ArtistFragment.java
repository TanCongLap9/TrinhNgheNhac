package com.example.trinhnghenhac.ui.artist;

import static com.example.trinhnghenhac.utils.AssertUtils.NE;
import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;
import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.adapters.ArtistPagerAdapter;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.databinding.FragmentArtistBinding;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.observables.ObservableInt;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArtistFragment extends Fragment implements ArtistContract.View {
    private FragmentArtistBinding b;
    private static final int[] pagesTitle = new int[] {
            R.string.type_overview,
            R.string.type_songs,
            R.string.type_playlists,
            R.string.type_videos
    };
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private ObservableInt tabObservable;
    private String artistId;
    private int platform;
    private boolean hasError;
    private ArtistPresenterImpl mPresenter;

    @Override
    public void displaySync(Artist artist) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.fragmentArtistTitle.setText(artist.getName());
                b.fragmentArtistText.setText(R.string.type_artist);
                b.fragmentArtistPlatform.setImageResource(MusicPlatformApi.toResId(platform));
                Glide.with(ArtistFragment.this).load(artist.getImage()).into(b.fragmentArtistImage);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentArtistBinding.inflate(getLayoutInflater(), null, false);
        initTabs();
        mPresenter.loadOrFetchData(artistId);
        b.fragmentArtistPager.setUserInputEnabled(false);
        return b.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        artistId = getArguments().getString(Extras.EXTRA_ARTISTID);
        assertNotNull(artistId);
        platform = getArguments().getInt(Extras.EXTRA_PLATFORM, MusicPlatform.PLATFORM_UNKNOWN);
        assertThat(platform, NE, MusicPlatform.PLATFORM_UNKNOWN);

        if (savedInstanceState == null)
            mPresenter = new ArtistPresenterImpl(this, platform);
        else
            mPresenter = new ArtistPresenterImpl(this, (ObservableParcelable<Artist>) savedInstanceState.getParcelable(Extras.EXTRA_ARTIST_OBSERVABLE));
        initTabObservable(savedInstanceState);
    }

    private void initTabObservable(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            tabObservable = savedInstanceState.getParcelable(Extras.EXTRA_TAB_OBSERVABLE);
        else tabObservable = new ObservableInt();
        tabObservable.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                TabLayout.Tab tab = b.fragmentArtistTabs.getTabAt(((int)o));
                b.fragmentArtistTabs.selectTab(tab);
            }
        });
    }

    @Override
    public void alertError(IOException e) {
        if (hasError) return;
        Snackbar.make(b.getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show();
        hasError = true;
    }

    private void initTabs() {
        Bundle args = new Bundle();
        args.putParcelable(Extras.EXTRA_ARTIST_OBSERVABLE, mPresenter.getObservable());
        args.putParcelable(Extras.EXTRA_TAB_OBSERVABLE, tabObservable);
        ArtistPagerAdapter adapter = new ArtistPagerAdapter(getActivity(), args);
        b.fragmentArtistPager.setAdapter(adapter);
        new TabLayoutMediator(b.fragmentArtistTabs, b.fragmentArtistPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(pagesTitle[position]);
            }
        }).attach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Extras.EXTRA_ARTIST_OBSERVABLE, mPresenter.getObservable());
        // Keeps tabObservable as fragment arguments cannot be passed after attached to activity.
        outState.putParcelable(Extras.EXTRA_TAB_OBSERVABLE, tabObservable);
        Log.d("Save artist", "onSaveInstanceState: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
        executor.shutdown();
        tabObservable.deleteObservers();
    }
}