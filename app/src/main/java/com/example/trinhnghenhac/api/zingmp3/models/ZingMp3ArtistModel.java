package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3ArtistModel implements ZingMp3DataModel, ZingMp3SectionItemModel, Serializable {
    public String id;
    @Nullable
    public String name;
    @Nullable
    public String thumbnail;
    @Nullable
    public String thumbnailM;
    public boolean isOA;
    public boolean isOABrand;
    public boolean hasOA;
    public String alias;
    @Nullable
    public String biography;
    @Nullable
    public String birthday;
    @Nullable
    public String cover;
    public long follow;
    @Nullable
    public String link;
    @Nullable
    public String national;
    public long oaid;
    @Nullable
    public String oalink;
    @Nullable
    public String playlistId;
    @Nullable
    public String realName;
    @Nullable
    public String sectionId;
    @Nullable
    public List<ZingMp3SectionModel> sections;
    @Nullable
    public String sortBiography;
    public boolean spotlight;
    @Nullable
    List<Integer> tabs;
    public long totalFollow;
}
