package com.example.trinhnghenhac.api.soundcloud.models;

public class SoundCloudCommentModel implements SoundCloudCollectionItem {
    public String body;
    public String created_at;
    public int id;
    public String kind;
    public int user_id;
    public String timestamp;
    public int track_id;
    public String uri;
    public SoundCloudUserModel user;
}