package com.example.trinhnghenhac.ui.playlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.models.Playlist;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlaylistPresenterImpl implements PlaylistContract.Presenter {
    @NonNull
    public PlaylistContract.Model mModel;

    @NonNull
    public PlaylistContract.View mView;

    @NonNull
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public PlaylistPresenterImpl(@NonNull PlaylistContract.View view, @MusicPlatform int platform) {
        this.mView = view;
        this.mModel = new PlaylistModelImpl(platform);
    }

    public PlaylistPresenterImpl(@NonNull PlaylistContract.View view, @NonNull Playlist data) {
        this.mView = view;
        this.mModel = new PlaylistModelImpl(data);
    }

    @Nullable
    @Override
    public PlaylistContract.Model getModel() {
        return mModel;
    }

    @NonNull
    @Override
    public Playlist getData() {
        return mModel.getData();
    }

    @Override
    public void loadOrFetchData(CharSequence playlistId) {
        if (mModel.getData() == null) fetch(playlistId);
        else mView.displaySync(mModel.getData());
    }

    @Override
    public void fetch(CharSequence playlistId) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.setData(mModel.getPlaylist(playlistId));
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
