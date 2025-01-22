package com.example.trinhnghenhac.ui.searchresults;

import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trinhnghenhac.adapters.PlayableRowsAdapter;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.SearchResultsFragmentTab;
import com.example.trinhnghenhac.databinding.FragmentArtistOtherBinding;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.models.SearchResults;

import java.util.Observable;
import java.util.Observer;

public class SearchResultsOtherFragment extends Fragment implements SearchResultsFragmentView {
    private FragmentArtistOtherBinding b;
    private ObservableParcelable<SearchResults> mSearchResultsObservable;
    private PlayableRowsAdapter mAdapter;
    public static final String EXTRA_TAB = "EXTRA_TAB";
    @SearchResultsFragmentTab
    private int mTab;
    private final Observer mObserver = new Observer() {
        @Override
        public void update(Observable observable, Object o) {
            displaySync((SearchResults) o);
        }
    };

    public SearchResultsOtherFragment() {

    }

    public SearchResultsOtherFragment(@SearchResultsFragmentTab int tab, Bundle bundle) {
        bundle.putInt(EXTRA_TAB, tab);
        setArguments(bundle);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        mSearchResultsObservable = (ObservableParcelable<SearchResults>) getArguments().getSerializable(Extras.EXTRA_SEARCH_OBSERVABLE);
        assertNotNull(mSearchResultsObservable);
        mTab = getArguments().getInt(EXTRA_TAB);
        mSearchResultsObservable.addObserver(mObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentArtistOtherBinding.inflate(inflater, container, false);
        mAdapter = new PlayableRowsAdapter(getContext(), false, true);
        b.getRoot().setAdapter(mAdapter);
        if (mSearchResultsObservable.has()) displaySync(mSearchResultsObservable.get());
        return b.getRoot();
    }

    @Override
    public void displaySync(@Nullable SearchResults searchResults) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (searchResults == null) {
                    switch (mTab) {
                        case SearchResultsFragmentTab.TAB_SONGS:
                        case SearchResultsFragmentTab.TAB_ARTISTS:
                        case SearchResultsFragmentTab.TAB_PLAYLISTS:
                        case SearchResultsFragmentTab.TAB_VIDEOS:
                            mAdapter.update(null);
                            break;
                    }
                    return;
                }
                switch (mTab) {
                    case SearchResultsFragmentTab.TAB_SONGS:
                        mAdapter.update(searchResults.getSongs());
                        break;
                    case SearchResultsFragmentTab.TAB_ARTISTS:
                        mAdapter.update(searchResults.getArtists());
                        break;
                    case SearchResultsFragmentTab.TAB_PLAYLISTS:
                        mAdapter.update(searchResults.getPlaylists());
                        break;
                    case SearchResultsFragmentTab.TAB_VIDEOS:
                        mAdapter.update(searchResults.getVideos());
                        break;
                }
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