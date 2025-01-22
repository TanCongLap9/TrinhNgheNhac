package com.example.trinhnghenhac.ui.explore;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.adapters.PlayableColsAdapter;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.functionalinterfaces.Action;
import com.example.trinhnghenhac.functionalinterfaces.Outputter;
import com.example.trinhnghenhac.listeners.DelayedSearch;
import com.example.trinhnghenhac.databinding.FragmentExploreBinding;
import com.example.trinhnghenhac.ui.searchresults.SearchResultsFragment;
import com.example.trinhnghenhac.utils.NavigationUtils;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

public class ExploreFragment extends Fragment implements ExploreContract.View {
    public static final int SECTION_LIST_ITEMS_LIMIT = 15;
    private FragmentExploreBinding b;
    private CursorAdapter searchAdapter;
    private ExplorePresenterImpl mPresenter;
    private boolean hasError;
    private DelayedSearch delayedSearch;
    private final SearchView.OnSuggestionListener onSuggestionListener = new SearchView.OnSuggestionListener() {
        @Override
        public boolean onSuggestionSelect(int position) {
            return false;
        }

        @SuppressLint("Range")
        @Override
        public boolean onSuggestionClick(int position) {
            Cursor cursor = (Cursor) searchAdapter.getItem(position);
            b.fragmentExploreSearch.setQuery(cursor.getString(cursor.getColumnIndex(MusicPlatformApi.SEARCH_COLUMN_NAME)), true);
            return true;
        }
    };

    private PlayableColsAdapter newestAdapter;
    private PlayableColsAdapter chillAdapter;
    private PlayableColsAdapter chineseAdapter;
    private PlayableColsAdapter vietnameseAdapter;

    @Override
    public void displayChineseCategorySync(@NonNull List<Playable> playables) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chineseAdapter.update(playables);
            }
        });
    }

    @Override
    public void displayVietnameseCategorySync(@NonNull List<Playable> playables) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vietnameseAdapter.update(playables);
            }
        });
    }

    @Override
    public void displayChillCategorySync(@NonNull List<Playable> playables) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chillAdapter.update(playables);
            }
        });
    }

    @Override
    public void displayNewestCategorySync(@NonNull List<Playable> playables) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newestAdapter.update(playables);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            mPresenter = new ExplorePresenterImpl(this);
        else
            mPresenter = new ExplorePresenterImpl(this, savedInstanceState.getParcelable(Extras.DATA_EXPLORE));
        searchAdapter = new SimpleCursorAdapter(getContext(),
                R.layout.item_search_suggestion_row,
                null,
                new String[] { MusicPlatformApi.SEARCH_COLUMN_NAME },
                new int[] { R.id.item_search_suggestion_row_text },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentExploreBinding.inflate(inflater, container, false);
        delayedSearch = new DelayedSearch(b.fragmentExploreSearch, searchAdapter)
                .setQueryResultOutputter(new Outputter<Cursor>() {
                    @Override
                    @Nullable
                    public Cursor run() {
                        return mPresenter.getSearchHints(b.fragmentExploreSearch.getQuery());
                    }
                })
                .setOnQueryTextSubmitListener(new Action<String>() {
                    @Override
                    public void run(String s) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Extras.EXTRA_QUERY, s);
                        Navigation.findNavController(b.getRoot()).navigate(R.id.action_search, bundle);
                    }
                });
        initSections();
        mPresenter.loadOrFetchData();
        return b.getRoot();
    }

    public void alertError(IOException e) {
        if (hasError) return;
        Snackbar.make(b.getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show();
        hasError = true;
    }

    private void initSections() {
        newestAdapter = new PlayableColsAdapter(getContext(), true, true, SECTION_LIST_ITEMS_LIMIT);
        newestAdapter.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtils.viewCategoryFromExplore(view, MusicCategory.CATEGORY_NEW_RELEASES);
            }
        });
        chillAdapter = new PlayableColsAdapter(getContext(), true, true, SECTION_LIST_ITEMS_LIMIT);
        chillAdapter.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtils.viewCategoryFromExplore(view, MusicCategory.CATEGORY_CHILL);
            }
        });
        chineseAdapter = new PlayableColsAdapter(getContext(), true, true, SECTION_LIST_ITEMS_LIMIT);
        chineseAdapter.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtils.viewCategoryFromExplore(view, MusicCategory.CATEGORY_CHINESE);
            }
        });
        vietnameseAdapter = new PlayableColsAdapter(getContext(), true, true, SECTION_LIST_ITEMS_LIMIT);
        vietnameseAdapter.setOnShowAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtils.viewCategoryFromExplore(view, MusicCategory.CATEGORY_VIETNAMESE);
            }
        });
        b.fragmentExploreSearch.setOnQueryTextListener(delayedSearch);
        b.fragmentExploreSearch.setOnSuggestionListener(onSuggestionListener);
        b.fragmentExploreSearch.setSuggestionsAdapter(searchAdapter);
        b.fragmentExploreNewest.itemSectionTitle.setText(getString(R.string.home_newest));
        b.fragmentExploreNewest.itemSectionList.setAdapter(newestAdapter);
        b.fragmentExploreCategoryChill.itemSectionTitle.setText(getString(R.string.music_category_chill));
        b.fragmentExploreCategoryChill.itemSectionList.setAdapter(chillAdapter);
        b.fragmentExploreCategoryChinese.itemSectionTitle.setText(getString(R.string.music_category_chinese));
        b.fragmentExploreCategoryChinese.itemSectionList.setAdapter(chineseAdapter);
        b.fragmentExploreCategoryVietnamese.itemSectionTitle.setText(getString(R.string.music_category_vietnamese));
        b.fragmentExploreCategoryVietnamese.itemSectionList.setAdapter(vietnameseAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenter != null) outState.putParcelable(Extras.DATA_EXPLORE, mPresenter.getData());
        Log.d("Save explore", "onSaveInstanceState: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
        if (mPresenter != null) mPresenter.close();
        if (delayedSearch != null) delayedSearch.close();
    }
}