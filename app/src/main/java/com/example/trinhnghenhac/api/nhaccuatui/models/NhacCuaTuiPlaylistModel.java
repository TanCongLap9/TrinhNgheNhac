package com.example.trinhnghenhac.api.nhaccuatui.models;

import androidx.annotation.Nullable;

import java.util.List;

public class NhacCuaTuiPlaylistModel {
    @Nullable
    public List<NhacCuaTuiArtistModel> artists;
    public long dateCreate;
    @Nullable
    public String dateModify; // dd/MM/yyyy
    public long dateRelease;
    @Nullable
    public String description;
    @Nullable
    public String duration; // mm:ss
    @Nullable
    public String genreKey;
    @Nullable
    public String key;
    // listTag;
    public int numOfItems;
    // provider;
    // refMapping;
    @Nullable
    public List<NhacCuaTuiSongModel> songs;
    @Nullable
    public String thumbnail;
    @Nullable
    public String title;
    @Nullable
    public String type; // PLAYLIST
    @Nullable
    public NhacCuaTuiUploadByModel uploadBy;
    // videos;
}
