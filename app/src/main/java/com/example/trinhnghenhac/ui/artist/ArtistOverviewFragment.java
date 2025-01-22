package com.example.trinhnghenhac.ui.artist;

import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.adapters.PlayableColsAdapter;
import com.example.trinhnghenhac.constants.ArtistOtherFragmentTab;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.databinding.FragmentArtistOverviewBinding;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.observables.ObservableInt;
import com.example.trinhnghenhac.observables.ObservableParcelable;

import java.util.Observable;
import java.util.Observer;

public class ArtistOverviewFragment extends Fragment implements ArtistFragmentView {
    private FragmentArtistOverviewBinding b;
    private ObservableParcelable<Artist> mArtistObservable;
    private ObservableInt mTabObservable;
    private PlayableColsAdapter mSongs;
    private PlayableColsAdapter mPlaylists;
    private PlayableColsAdapter mVideos;
    private final Observer mObserver = new Observer() {
        @Override
        public void update(Observable observable, Object o) {
            displaySync((Artist)o);
        }
    };

    public ArtistOverviewFragment() {

    }

    public ArtistOverviewFragment(Bundle bundle) {
        setArguments(bundle);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        mTabObservable = (ObservableInt) getArguments().getSerializable(Extras.EXTRA_TAB_OBSERVABLE);
        assertNotNull(mTabObservable);
        mArtistObservable = (ObservableParcelable<Artist>) getArguments().getSerializable(Extras.EXTRA_ARTIST_OBSERVABLE);
        assertNotNull(mArtistObservable);
        mArtistObservable.addObserver(mObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentArtistOverviewBinding.inflate(inflater, container, false);
        mSongs = new PlayableColsAdapter(getContext(), false, false, 5);
        mSongs.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabObservable.set(ArtistOtherFragmentTab.TAB_SONGS);
            }
        });
        mPlaylists = new PlayableColsAdapter(getContext(), false, false, 5);
        mPlaylists.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabObservable.set(ArtistOtherFragmentTab.TAB_PLAYLISTS);
            }
        });
        mVideos = new PlayableColsAdapter(getContext(), false, false, 5);
        mVideos.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabObservable.set(ArtistOtherFragmentTab.TAB_VIDEOS);
            }
        });
        b.fragmentArtistOverviewSongs.itemSectionTitle.setText(getContext().getString(R.string.type_songs));
        b.fragmentArtistOverviewSongs.itemSectionList.setAdapter(mSongs);
        b.fragmentArtistOverviewPlaylists.itemSectionTitle.setText(getContext().getString(R.string.type_playlists));
        b.fragmentArtistOverviewPlaylists.itemSectionList.setAdapter(mPlaylists);
        b.fragmentArtistOverviewVideos.itemSectionTitle.setText(getContext().getString(R.string.type_videos));
        b.fragmentArtistOverviewVideos.itemSectionList.setAdapter(mVideos);
        if (mArtistObservable.get() != null) displaySync(mArtistObservable.get());
        return b.getRoot();
    }

    @Override
    public void displaySync(Artist artist) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSongs.update(artist.getSongs());
                mPlaylists.update(artist.getPlaylists());
                mVideos.update(artist.getVideos());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
        mArtistObservable.deleteObserver(mObserver);
    }
}