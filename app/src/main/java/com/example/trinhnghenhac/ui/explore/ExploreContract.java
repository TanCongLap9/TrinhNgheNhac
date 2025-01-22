package com.example.trinhnghenhac.ui.explore;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.models.Playable;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface ExploreContract {
    interface View {
        void displayChineseCategorySync(@NonNull List<Playable> playables);
        void displayVietnameseCategorySync(@NonNull List<Playable> playables);
        void displayChillCategorySync(@NonNull List<Playable> playables);
        void displayNewestCategorySync(@NonNull List<Playable> playables);
        void alertError(IOException e);
    }

    interface Model {
        @Nullable
        Cursor getSearchHints(CharSequence query) throws IOException;
        List<Playable> getRandomlyMergedPlayablesByCategory(int category) throws IOException;
        @NonNull
        ExploreData getData();
    }

    interface Presenter extends Closeable {
        @Nullable
        Cursor getSearchHints(CharSequence query);
        @NonNull
        Model getModel();
        @NonNull
        ExploreData getData();
        void loadOrFetchData();
        void fetchChineseCategory();
        void fetchVietnameseCategory();
        void fetchChillCategory();
        void fetchNewestCategory();
    }
}