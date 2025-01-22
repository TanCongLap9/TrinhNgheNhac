package com.example.trinhnghenhac.api.soundcloud.models;

import java.io.Serializable;

public class SoundCloudQuotaModel implements Serializable {
    public boolean unlimited_upload_quota;
    public int upload_seconds_used;
    public int upload_seconds_left;
}
