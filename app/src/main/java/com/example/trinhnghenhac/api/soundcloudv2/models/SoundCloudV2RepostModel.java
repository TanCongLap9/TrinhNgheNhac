package com.example.trinhnghenhac.api.soundcloudv2.models;

import androidx.annotation.Nullable;

public class SoundCloudV2RepostModel implements SoundCloudV2CollectionItem {
    // public ? caption;
    @Nullable
    public String created_at; // ISO
    @Nullable
    public SoundCloudV2TrackModel track;
    @Nullable
    public String type;
    @Nullable
    public SoundCloudV2UserModel user;
    @Nullable
    public String uuid;
}
