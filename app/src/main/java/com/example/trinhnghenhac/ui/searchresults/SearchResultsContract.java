package com.example.trinhnghenhac.ui.searchresults;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.utils.RandomUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface SearchResultsContract {
    interface View {
        void alertError(IOException e);
    }

    interface Model {
        @Nullable
        Cursor getSearchHints(CharSequence query) throws IOException;

        @NonNull
        ObservableParcelable<SearchResults> getObservable();

        @Nullable
        SearchResults getData();

        @NonNull
        SearchResults getSearchResults(CharSequence query) throws IOException;
    }

    interface Presenter extends Closeable {
        @NonNull
        Model getModel();

        @NonNull
        ObservableParcelable<SearchResults> getObservable();

        @Nullable
        SearchResults getData();

        void loadOrFetchData(CharSequence query);

        @Nullable
        Cursor getSearchHints(CharSequence query);

        void fetch(CharSequence query);
    }
}