package com.example.trinhnghenhac.ui.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.models.Playable;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface CategoryContract {
    interface View {
        void displaySync(List<Playable> playables);
        void alertError(IOException e);
    }

    interface Model {
        @Nullable
        List<Playable> getData();
        void setData(List<Playable> data);
        List<Playable> getPlayablesByCategory(@MusicCategory int category) throws IOException;
    }

    interface Presenter extends Closeable {
        @NonNull
        Model getModel();
        @Nullable
        List<Playable> getData();
        void setData(List<Playable> data);
        void loadOrFetchData(@MusicCategory int category);
        void fetch(@MusicCategory int category);
    }
}
