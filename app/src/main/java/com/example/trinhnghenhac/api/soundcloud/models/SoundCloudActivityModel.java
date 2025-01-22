package com.example.trinhnghenhac.api.soundcloud.models;

import java.io.Serializable;

public class SoundCloudActivityModel<T extends SoundCloudTrackOrPlaylist> implements Serializable {
    public String type;
    public String created_at;
    public SoundCloudTrackOrPlaylist origin;
}
