package com.example.trinhnghenhac.ui.playlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.models.Playlist;

import java.io.Closeable;
import java.io.IOException;

public interface PlaylistContract {
    interface View {
        void displaySync(Playlist playlist);
        void alertError(IOException e);
    }

    interface Model {
        @Nullable
        Playlist getData();

        void setData(@NonNull Playlist playlist);

        @Nullable
        Playlist getPlaylist(CharSequence playlistId) throws IOException;
    }

    interface Presenter extends Closeable {
        @Nullable
        Model getModel();

        @NonNull
        Playlist getData();

        void loadOrFetchData(CharSequence playlistId);

        void fetch(CharSequence playlistId);
    }
}