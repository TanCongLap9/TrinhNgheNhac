package com.example.trinhnghenhac.ui.searchresults;

import androidx.annotation.Nullable;

import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.SearchResults;

public interface SearchResultsFragmentView {
    void displaySync(@Nullable SearchResults searchResults);
}
