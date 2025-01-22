package com.example.trinhnghenhac.api.soundcloudv2.models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class SoundCloudV2UserModel implements SoundCloudV2CollectionItem, Serializable {
    @Nullable
    public BadgesModel badges;
    @Nullable
    public String country_code;
    @Nullable
    public String station_permalink;
    @Nullable
    public String station_urn;
    @Nullable
    public String urn;
    public boolean verified;
    @Nullable
    public String avatar_url;
    @Nullable
    public String city;
    // public String country;
    // public String description;
    // public String discogs_name;
    @Nullable
    public String first_name;
    public int followers_count;
    // public int followings_count;
    @Nullable
    public String full_name;
    public int id;
    @Nullable
    public String kind;
    // public String created_at;
    @Nullable
    public String last_modified;
    @Nullable
    public String last_name;
    @Nullable
    public String permalink;
    @Nullable
    public String permalink_url;
    // public String plan;
    // public int playlist_count;
    // public int public_favorites_count;
    // public int reposts_count;
    // public int track_count;
    @Nullable
    public String uri;
    @Nullable
    public String username;
    // public String website;
    // public String website_title;

    public static class BadgesModel {
        public boolean creator_mid_tier;
        public boolean pro;
        public boolean pro_unlimited;
        public boolean verified;
    }
}
