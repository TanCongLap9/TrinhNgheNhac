package com.example.trinhnghenhac.api.nhaccuatui.models;

import androidx.annotation.Nullable;

import com.example.trinhnghenhac.api.nhaccuatui.querymodels.NhacCuaTuiSearchPlaylistsModel;

public class NhacCuaTuiArtistModel {
    @Nullable
    public String imageUrl;
    @Nullable
    public String name;
    @Nullable
    public String shortLink;
    @Nullable
    public int artistId;
    // The following properties are available when you run getArtist() with TYPE_DESCRIPTION
    @Nullable
    public String coverImageURL;
    @Nullable
    public String description;
}
