package com.example.trinhnghenhac.api.nhaccuatui.models;

import androidx.annotation.Nullable;

import java.util.List;

public class NhacCuaTuiSongModel {
    @Nullable
    public List<NhacCuaTuiArtistModel> artists;
    public long dateCreate;
    public long dateRelease;
    @Nullable
    public String description;
    @Nullable
    public String duration; // mm:ss
    @Nullable
    public String genreKey;
    public boolean isMyPlaylist;
    @Nullable
    public String key;
    /*public provider;
    public refMapping;*/
    public int statusAddPlaylistValue;
    public int statusDownloadValue;
    public int statusPlayValue;
    public int statusViewValue;
    @Nullable
    public List<NhacCuaTuiStreamingModel> streamUrls;
    @Nullable
    public String thumbnail;
    @Nullable
    public String title;
    @Nullable
    public String type; // SONG
    @Nullable
    public NhacCuaTuiUploadByModel uploadBy;
}
