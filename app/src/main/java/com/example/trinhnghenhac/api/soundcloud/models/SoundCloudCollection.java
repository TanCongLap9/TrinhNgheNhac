package com.example.trinhnghenhac.api.soundcloud.models;

import java.io.Serializable;
import java.util.List;

public class SoundCloudCollection<T extends SoundCloudCollectionItem> implements Serializable {
    public List<T> collection;
    public String next_href;
}
