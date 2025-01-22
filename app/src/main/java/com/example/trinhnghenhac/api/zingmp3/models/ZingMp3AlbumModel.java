package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3AlbumModel implements Serializable {
    @Nullable
    public String encodeId;
    @Nullable
    public String title;
    @Nullable
    public List<ZingMp3ArtistModel> artists;
    @Nullable
    public String artistsNames;
    public boolean isofficial;
    public boolean isIndie;
    @Nullable
    public String link;
    @Nullable
    public String PR;
    @Nullable
    public String releaseDate; // dd/MM/yyyy
    @Nullable
    public String thumbnail;
    public long relasedAt;
    @Nullable
    public List<String> genreIds;
}
