package com.example.trinhnghenhac.api.soundcloud.models;

import com.google.gson.JsonObject;

import java.io.Serializable;

public class SoundCloudMeModel extends SoundCloudUserModel implements Serializable {
    int likes_count;
    String locale;
    boolean online;
    boolean primary_email_confirmed;
    int private_playlists_count;
    int private_tracks_count;
    SoundCloudQuotaModel quota;
    JsonObject subscriptions;
    int upload_seconds_left;
}





