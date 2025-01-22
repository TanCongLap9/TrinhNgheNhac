package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ZingMp3VideoModel implements ZingMp3DataModel, ZingMp3SectionItemModel, Serializable {
    public String encodeId;
    @Nullable
    public String title;
    public boolean isOfficial;
    @Nullable
    public List<ZingMp3ArtistModel> artists;
    @Nullable
    public String thumbnail;
    @Nullable
    public String alias;
    @Nullable
    public ZingMp3ArtistModel artist;
    public boolean isWorldWide;
    @Nullable
    public String link;
    public int streamingStatus;
    @Nullable
    public String username;
    @Nullable
    public String thumbnailM;
    public boolean isOffical; // sic
    public int duration; // s
    @Nullable
    public String artistsNames;
    public long relaseDate;
    public long comment;
    @Nullable
    public List<ZingMp3ArtistModel> composers;
    public long createdAt;
    public boolean disableAds;
    public long endTime; // s
    @Nullable
    public List<ZingMp3GenreModel> genres;
    public long like;
    public boolean liked;
    public long listen;
    @Nullable
    public String lyric;
    @Nullable
    public String privacy;
    @Nullable
    public List<ZingMp3VideoModel> recommends; // public
    @Nullable
    public ZingMp3SongModel song;
    public long startTime; // s
    public int statusCode;
    @Nullable
    public String statusName;
    @Nullable
    public VideoStreamingModel streaming;
    @Nullable
    public ZingMp3AlbumModel album;

    public static class VideoStreamingModel {
        @Nullable
        public HlsModel hls;
    }

    public static class HlsModel {
        @Nullable
        @SerializedName("1080p")
        public String $1080p;
        @Nullable
        @SerializedName("720p")
        public String $720p;
        @Nullable
        @SerializedName("360p")
        public String $360p;
        @Nullable
        @SerializedName("0p")
        public String $0p;
    }
}
