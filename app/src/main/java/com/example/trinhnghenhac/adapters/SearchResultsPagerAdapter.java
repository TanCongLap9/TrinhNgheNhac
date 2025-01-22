package com.example.trinhnghenhac.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.trinhnghenhac.constants.SearchResultsFragmentTab;
import com.example.trinhnghenhac.ui.searchresults.SearchResultsAllFragment;
import com.example.trinhnghenhac.ui.searchresults.SearchResultsFragment;
import com.example.trinhnghenhac.ui.searchresults.SearchResultsOtherFragment;

public class SearchResultsPagerAdapter extends FragmentStateAdapter {
    private final Bundle mBundle;
    public static final int PAGES_COUNT = 5;

    public SearchResultsPagerAdapter(FragmentActivity activity, Bundle bundle) {
        super(activity);
        mBundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case SearchResultsFragmentTab.TAB_OVERVIEW:
                return new SearchResultsAllFragment((Bundle) mBundle.clone());
            case SearchResultsFragmentTab.TAB_SONGS:
                return new SearchResultsOtherFragment(SearchResultsFragmentTab.TAB_SONGS, (Bundle) mBundle.clone());
            case SearchResultsFragmentTab.TAB_ARTISTS:
                return new SearchResultsOtherFragment(SearchResultsFragmentTab.TAB_ARTISTS, (Bundle) mBundle.clone());
            case SearchResultsFragmentTab.TAB_PLAYLISTS:
                return new SearchResultsOtherFragment(SearchResultsFragmentTab.TAB_PLAYLISTS, (Bundle) mBundle.clone());
            case SearchResultsFragmentTab.TAB_VIDEOS:
                return new SearchResultsOtherFragment(SearchResultsFragmentTab.TAB_VIDEOS, (Bundle) mBundle.clone());
            default:
                throw new IndexOutOfBoundsException("Fragment at position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return PAGES_COUNT;
    }
}
