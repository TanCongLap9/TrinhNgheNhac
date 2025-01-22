package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

import com.example.trinhnghenhac.R;

@IntDef({
        SearchResultsFragmentTab.TAB_OVERVIEW,
        SearchResultsFragmentTab.TAB_SONGS,
        SearchResultsFragmentTab.TAB_ARTISTS,
        SearchResultsFragmentTab.TAB_PLAYLISTS,
        SearchResultsFragmentTab.TAB_VIDEOS
})
public @interface SearchResultsFragmentTab {
    int TAB_OVERVIEW = 0;
    int TAB_SONGS = 1;
    int TAB_ARTISTS = 2;
    int TAB_PLAYLISTS = 3;
    int TAB_VIDEOS = 4;
}
