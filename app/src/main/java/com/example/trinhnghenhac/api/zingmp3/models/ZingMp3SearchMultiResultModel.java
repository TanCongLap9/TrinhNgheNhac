package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3SearchMultiResultModel implements ZingMp3DataModel, Serializable {
    @Nullable
    public TopModel top;
    @Nullable
    public List<ZingMp3ArtistModel> artists;
    @Nullable
    public List<ZingMp3SongModel> songs;
    @Nullable
    public List<ZingMp3VideoModel> videos;
    @Nullable
    public List<ZingMp3PlaylistModel> playlists;

    public static class TopModel {
        @Nullable
        public String encodeId;
        @Nullable
        public String title;
        public boolean isOfficial;
        @Nullable
        public List<ZingMp3ArtistModel> artists;
        @Nullable
        public String thumbnail;
        public int duration;
        public long relaseDate;
    }
}
