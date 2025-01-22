package com.example.trinhnghenhac.ui.playlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.models.Playlist;

import java.io.IOException;

public class PlaylistModelImpl implements PlaylistContract.Model {
    @Nullable
    private Playlist mData;

    @NonNull
    private final MusicPlatformApi api;

    public PlaylistModelImpl(@MusicPlatform int platform) {
        api = MusicPlatformApi.getApi(platform);
    }

    public PlaylistModelImpl(@NonNull Playlist playlist) {
        mData = playlist;
        api = MusicPlatformApi.getApi(playlist.getPlatform());
    }

    @Nullable
    @Override
    public Playlist getData() {
        return mData;
    }

    @Override
    public void setData(@NonNull Playlist playlist) {
        mData = playlist;
    }

    @Nullable
    @Override
    public Playlist getPlaylist(CharSequence playlistId) throws IOException {
        return api.getPlaylistInfo(playlistId);
    }
}
