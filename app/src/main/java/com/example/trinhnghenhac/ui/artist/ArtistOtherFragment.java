package com.example.trinhnghenhac.ui.artist;

import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;
import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import com.example.trinhnghenhac.adapters.PlayableRowsAdapter;
import com.example.trinhnghenhac.constants.ArtistOtherFragmentTab;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.observables.ObservableParcelable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trinhnghenhac.databinding.FragmentArtistOtherBinding;

import java.util.Observable;
import java.util.Observer;

public class ArtistOtherFragment extends Fragment implements ArtistFragmentView {
    private FragmentArtistOtherBinding b;
    private ObservableParcelable<Artist> mArtistObservable;
    private PlayableRowsAdapter mAdapter;
    @ArtistOtherFragmentTab
    private int mTab;
    private final Observer mObserver = new Observer() {
        @Override
        public void update(Observable observable, Object o) {
            displaySync((Artist)o);
        }
    };

    public ArtistOtherFragment() {

    }

    public ArtistOtherFragment(@ArtistOtherFragmentTab int tab, Bundle bundle) {
        bundle.putInt(Extras.EXTRA_TAB, tab);
        setArguments(bundle);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        mArtistObservable = (ObservableParcelable<Artist>) getArguments().getSerializable(Extras.EXTRA_ARTIST_OBSERVABLE);
        assertNotNull(mArtistObservable);
        mTab = getArguments().getInt(Extras.EXTRA_TAB);
        mArtistObservable.addObserver(mObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentArtistOtherBinding.inflate(inflater, container, false);
        mAdapter = new PlayableRowsAdapter(getContext(), false, false);
        b.getRoot().setAdapter(mAdapter);
        if (mArtistObservable.has()) displaySync(mArtistObservable.get());
        return b.getRoot();
    }

    @Override
    public void displaySync(Artist artist) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (mTab) {
                    case ArtistOtherFragmentTab.TAB_SONGS:
                        mAdapter.update(artist.getSongs());
                        break;
                    case ArtistOtherFragmentTab.TAB_PLAYLISTS:
                        mAdapter.update(artist.getPlaylists());
                        break;
                    case ArtistOtherFragmentTab.TAB_VIDEOS:
                        mAdapter.update(artist.getVideos());
                        break;
                }
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