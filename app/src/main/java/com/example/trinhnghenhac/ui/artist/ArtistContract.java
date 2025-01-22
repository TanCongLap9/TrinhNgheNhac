package com.example.trinhnghenhac.ui.artist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.observables.ObservableParcelable;

import java.io.Closeable;
import java.io.IOException;

public interface ArtistContract {
    interface View {
        void displaySync(Artist artist);
        void alertError(IOException e);
    }

    interface Model {
        @NonNull
        ObservableParcelable<Artist> getObservable();

        @Nullable
        Artist getData();

        @Nullable
        Artist getArtistInfo(String artistId) throws IOException;
    }

    interface Presenter extends Closeable {
        @NonNull
        Model getModel();

        @NonNull
        ObservableParcelable<Artist> getObservable();

        @Nullable
        Artist getData();

        void loadOrFetchData(String playlistId);

        void fetch(String artistId);
    }
}
