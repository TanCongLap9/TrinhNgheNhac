package com.example.trinhnghenhac.ui.artist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.models.Artist;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArtistPresenterImpl implements ArtistContract.Presenter {
    @NonNull
    private final ArtistContract.Model mModel;
    @NonNull
    private final ArtistContract.View mView;
    @NonNull
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public ArtistPresenterImpl(@NonNull ArtistContract.View mView, @MusicPlatform int platform) {
        this.mView = mView;
        mModel = new ArtistModelImpl(platform);
    }

    public ArtistPresenterImpl(@NonNull ArtistContract.View mView, ObservableParcelable<Artist> observable) {
        this.mView = mView;
        this.mModel = new ArtistModelImpl(observable);
    }

    public ArtistPresenterImpl(@NonNull ArtistContract.View mView, Artist data) {
        this.mView = mView;
        this.mModel = new ArtistModelImpl(data);
    }

    @NonNull
    @Override
    public ArtistContract.Model getModel() {
        return mModel;
    }

    @NonNull
    @Override
    public ObservableParcelable<Artist> getObservable() {
        return mModel.getObservable();
    }

    @Nullable
    @Override
    public Artist getData() {
        return mModel.getData();
    }

    @Override
    public void loadOrFetchData(String playlistId) {
        if (mModel.getData() == null) fetch(playlistId);
        else mView.displaySync(mModel.getData());
    }

    @Override
    public void fetch(String artistId) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.getObservable().set(mModel.getArtistInfo(artistId));
                    mView.displaySync(mModel.getData());
                } catch (IOException e) {
                    mView.alertError(e);
                }
            }
        });
    }

    @Override
    public void close() {
        mExecutor.shutdown();
    }
}
