package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3SongModel implements ZingMp3DataModel, ZingMp3SectionItemModel, Serializable {
    public String encodeId;
    @Nullable
    public String title;
    @Nullable
    public String thumbnail;
    @Nullable
    public String thumbnailM;
    public boolean isOffical; // sic
    public long releaseDate;
    public int duration; // s
    @Nullable
    public String artistsNames;
    @Nullable
    public List<ZingMp3ArtistModel> artists;
    @Nullable
    public ZingMp3AlbumModel album;
    @Nullable
    public List<ZingMp3GenreModel> genres;
    @Nullable
    public String alias;
    public boolean allowAudioAds;
    public long comment;
    @Nullable
    public List<ZingMp3ArtistModel> composers;
    @Nullable
    public String distributor;
    @Nullable
    public List<String> genreIds;
    public boolean hasLyric;
    /*public List<?> indicators;*/
    public boolean isIndie;
    public boolean isPrivate;
    public boolean isRBT;
    public boolean isWorldWide;
    public long like;
    public boolean liked;
    @Nullable
    public String link;
    public long listen;
    public boolean preRelease;
    public int streamingStatus;
    public long userid;
    @Nullable
    public String username;
    public boolean zingChoice;
    @Nullable
    public List<Integer> streamPrivileges;
    @Nullable
    public String mvlink;
    @Nullable
    public List<Integer> downloadPrivileges;
    @Nullable
    public PreviewInfo previewInfo;

    public static class PreviewInfo {
        public long startTime;
        public long endTime;
    }
}