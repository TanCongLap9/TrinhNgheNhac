package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3PlaylistModel implements ZingMp3DataModel, ZingMp3SectionItemModel, Serializable {
    @Nullable
    public String PR;
    @Nullable
    public String aliasTitle;
    @Nullable
    public ZingMp3ArtistModel artist;
    public long contentLastUpdate;
    @Nullable
    public String description;
    @Nullable
    public String distributor;
    @Nullable
    public List<String> genreIds;
    @Nullable
    public List<ZingMp3GenreModel> genres;
    public boolean isAlbum;
    public boolean isIndie;
    public boolean isPrivate;
    public boolean isShuffle;
    public boolean isSingle;
    public boolean isoffical;
    public long like;
    public boolean liked;
    public long listen;
    public int playItemMode;
    @Nullable
    public String releaseDate;
    public long releasedAt;
    @Nullable
    public String sectionId;
    @Nullable
    public SongsModel song;
    @Nullable
    public String sortDescription;
    public int subType;
    @Nullable
    public String textType;
    @Nullable
    public String thumbnail;
    @Nullable
    public String thumbnailM;
    public String encodeId;
    @Nullable
    public String title;
    public long uid;
    @Nullable
    public String userName;
    @Nullable
    public String link;
    @Nullable
    public String artistsNames;
    @Nullable
    public List<ZingMp3ArtistModel> artists;

    public static class SongsModel {
        public List<ZingMp3SongModel> items;
        public int total;
        public long totalDuration;
    }
}
