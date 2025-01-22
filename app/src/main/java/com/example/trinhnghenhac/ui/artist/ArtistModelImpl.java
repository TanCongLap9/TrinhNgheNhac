package com.example.trinhnghenhac.ui.artist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.models.Artist;

import java.io.IOException;

public class ArtistModelImpl implements ArtistContract.Model {
    @NonNull
    private final ObservableParcelable<Artist> mArtistObservable;

    @NonNull
    private final MusicPlatformApi api;

    public ArtistModelImpl(@MusicPlatform int platform) {
        api = MusicPlatformApi.getApi(platform);
        mArtistObservable = new ObservableParcelable<>();
    }

    public ArtistModelImpl(ObservableParcelable<Artist> observable) {
        api = MusicPlatformApi.getApi(observable.get().getPlatform());
        mArtistObservable = observable;
    }

    public ArtistModelImpl(Artist data) {
        api = MusicPlatformApi.getApi(data.getPlatform());
        mArtistObservable = new ObservableParcelable<>(data);
    }

    @NonNull
    @Override
    public ObservableParcelable<Artist> getObservable() {
        return mArtistObservable;
    }

    @Nullable
    @Override
    public Artist getData() {
        return mArtistObservable.get();
    }

    @Nullable
    @Override
    public Artist getArtistInfo(String artistId) throws IOException {
        return api.getArtistInfo(artistId);
    }
}
