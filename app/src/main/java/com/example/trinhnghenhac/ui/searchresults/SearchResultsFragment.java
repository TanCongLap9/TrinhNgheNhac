package com.example.trinhnghenhac.ui.searchresults;

import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.adapters.SearchResultsPagerAdapter;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.SearchResultsFragmentTab;
import com.example.trinhnghenhac.databinding.FragmentSearchResultsBinding;
import com.example.trinhnghenhac.functionalinterfaces.Action;
import com.example.trinhnghenhac.functionalinterfaces.Outputter;
import com.example.trinhnghenhac.observables.ObservableInt;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.listeners.DelayedSearch;
import com.example.trinhnghenhac.models.SearchResults;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchResultsFragment extends Fragment implements SearchResultsContract.View {
    private static final int[] PAGES_TITLE = new int[] {
            R.string.type_overview,
            R.string.type_songs,
            R.string.type_artists,
            R.string.type_playlists,
            R.string.type_videos
    };
    private SearchView.OnSuggestionListener onSuggestionListener;
    private DelayedSearch delayedSearch;
    private SimpleCursorAdapter searchAdapter;
    private FragmentSearchResultsBinding b;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private ObservableInt tabObservable;
    private String query;
    private boolean hasError;
    private SearchResultsPresenterImpl mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        query = getArguments().getString(Extras.EXTRA_QUERY);
        assertNotNull(query);

        if (savedInstanceState == null)
            mPresenter = new SearchResultsPresenterImpl(this);
        else
            mPresenter = new SearchResultsPresenterImpl(this, (ObservableParcelable<SearchResults>) savedInstanceState.getParcelable(Extras.EXTRA_SEARCH_OBSERVABLE));
        initTabObservable(savedInstanceState);
        searchAdapter = new SimpleCursorAdapter(getContext(),
                R.layout.item_search_suggestion_row,
                null,
                new String[] { MusicPlatformApi.SEARCH_COLUMN_NAME },
                new int[] { R.id.item_search_suggestion_row_text },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        onSuggestionListener = new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @SuppressLint("Range")
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) searchAdapter.getItem(position);
                b.fragmentSearchResultSearch.setQuery(cursor.getString(cursor.getColumnIndex(MusicPlatformApi.SEARCH_COLUMN_NAME)), true);
                return true;
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentSearchResultsBinding.inflate(getLayoutInflater(), null, false);
        b.fragmentSearchResultPager.setUserInputEnabled(false);
        initTabs();
        delayedSearch = new DelayedSearch(b.fragmentSearchResultSearch, searchAdapter)
                .setQueryResultOutputter(new Outputter<Cursor>() {
                    @Override
                    @Nullable
                    public Cursor run() {
                        return mPresenter.getSearchHints(b.fragmentSearchResultSearch.getQuery());
                    }
                })
                .setOnQueryTextSubmitListener(new Action<String>() {
                    @Override
                    public void run(String s) {
                        query = s;
                        mPresenter.fetch(query);
                    }
                });
        b.fragmentSearchResultSearch.setOnQueryTextListener(delayedSearch);
        b.fragmentSearchResultSearch.setOnSuggestionListener(onSuggestionListener);
        if (savedInstanceState == null)
            b.fragmentSearchResultSearch.setQuery(query, true);
        else mPresenter.loadOrFetchData(query);
        return b.getRoot();
    }

    private void initTabObservable(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            tabObservable = savedInstanceState.getParcelable(Extras.EXTRA_TAB_OBSERVABLE);
        else tabObservable = new ObservableInt();
        tabObservable.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                int index = tabObservable.get();
                TabLayout.Tab tab = b.fragmentSearchResultsTabs.getTabAt(index);
                b.fragmentSearchResultsTabs.selectTab(tab);
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
        args.putParcelable(Extras.EXTRA_SEARCH_OBSERVABLE, mPresenter.getObservable());
        args.putParcelable(Extras.EXTRA_TAB_OBSERVABLE, tabObservable);
        SearchResultsPagerAdapter adapter = new SearchResultsPagerAdapter(getActivity(), args);
        b.fragmentSearchResultPager.setAdapter(adapter);
        new TabLayoutMediator(b.fragmentSearchResultsTabs, b.fragmentSearchResultPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(PAGES_TITLE[position]);
            }
        }).attach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Extras.EXTRA_SEARCH_OBSERVABLE, mPresenter.getObservable());
        // Keeps tabObservable as fragment arguments cannot be passed after attached to activity.
        outState.putParcelable(Extras.EXTRA_TAB_OBSERVABLE, tabObservable);
        Log.d("Save search results", "onSaveInstanceState: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
        executor.shutdown();
        tabObservable.deleteObservers();
    }
}