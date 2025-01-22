package com.example.trinhnghenhac.ui.searchresults;

import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.adapters.PlayableColsAdapter;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.SearchResultsFragmentTab;
import com.example.trinhnghenhac.databinding.FragmentSearchResultsAllBinding;
import com.example.trinhnghenhac.observables.ObservableInt;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.models.SearchResults;

import java.util.Observable;
import java.util.Observer;

public class SearchResultsAllFragment extends Fragment implements SearchResultsFragmentView {
    private FragmentSearchResultsAllBinding b;
    private ObservableParcelable<SearchResults> mSearchResultsObservable;
    private ObservableInt mTabObservable;
    private PlayableColsAdapter mSongs;
    private PlayableColsAdapter mPlaylists;
    private PlayableColsAdapter mArtists;
    private PlayableColsAdapter mVideos;
    private final Observer mObserver = new Observer() {
        @Override
        public void update(Observable observable, Object o) {
            displaySync((SearchResults) o);
        }
    };

    public SearchResultsAllFragment() {

    }

    public SearchResultsAllFragment(Bundle bundle) {
        setArguments(bundle);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        mTabObservable = (ObservableInt) getArguments().getSerializable(Extras.EXTRA_TAB_OBSERVABLE);
        assertNotNull(mTabObservable);
        mSearchResultsObservable = (ObservableParcelable<SearchResults>) getArguments().getSerializable(Extras.EXTRA_SEARCH_OBSERVABLE);
        assertNotNull(mSearchResultsObservable);
        mSearchResultsObservable.addObserver(mObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentSearchResultsAllBinding.inflate(inflater, container, false);
        mSongs = new PlayableColsAdapter(getContext(), false, true, 10);
        mSongs.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabObservable.set(SearchResultsFragmentTab.TAB_SONGS);
            }
        });
        mArtists = new PlayableColsAdapter(getContext(), false, true, 10);
        mArtists.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabObservable.set(SearchResultsFragmentTab.TAB_ARTISTS);
            }
        });
        mPlaylists = new PlayableColsAdapter(getContext(), false, true, 10);
        mPlaylists.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabObservable.set(SearchResultsFragmentTab.TAB_PLAYLISTS);
            }
        });
        mVideos = new PlayableColsAdapter(getContext(), false, true, 10);
        mVideos.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabObservable.set(SearchResultsFragmentTab.TAB_VIDEOS);
            }
        });
        b.fragmentSearchResultsOverviewSongs.itemSectionTitle.setText(R.string.type_songs);
        b.fragmentSearchResultsOverviewSongs.itemSectionList.setAdapter(mSongs);
        b.fragmentSearchResultsOverviewArtists.itemSectionTitle.setText(R.string.type_artists);
        b.fragmentSearchResultsOverviewArtists.itemSectionList.setAdapter(mArtists);
        b.fragmentSearchResultsOverviewPlaylists.itemSectionTitle.setText(R.string.type_playlists);
        b.fragmentSearchResultsOverviewPlaylists.itemSectionList.setAdapter(mPlaylists);
        b.fragmentSearchResultsOverviewVideos.itemSectionTitle.setText(R.string.type_videos);
        b.fragmentSearchResultsOverviewVideos.itemSectionList.setAdapter(mVideos);
        if (mSearchResultsObservable.get() != null) displaySync(mSearchResultsObservable.get());
        return b.getRoot();
    }

    @Override
    public void displaySync(@Nullable SearchResults searchResults) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (searchResults == null) {
                    mArtists.update(null);
                    mSongs.update(null);
                    mPlaylists.update(null);
                    mVideos.update(null);
                    return;
                }
                mArtists.update(searchResults.getArtists());
                mSongs.update(searchResults.getSongs());
                mPlaylists.update(searchResults.getPlaylists());
                mVideos.update(searchResults.getVideos());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
        mSearchResultsObservable.deleteObserver(mObserver);
    }
}