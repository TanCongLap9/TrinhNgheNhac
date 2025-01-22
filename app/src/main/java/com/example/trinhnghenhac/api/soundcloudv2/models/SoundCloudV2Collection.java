package com.example.trinhnghenhac.api.soundcloudv2.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class SoundCloudV2Collection<T extends SoundCloudV2CollectionItem> implements Serializable {
    // old
    @Nullable
    public List<T> collection;
    @Nullable
    public String next_href;
}
