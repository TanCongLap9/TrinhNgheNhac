package com.example.trinhnghenhac.api.soundcloudv2.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SoundCloudV2PlaylistModel implements SoundCloudV2TrackOrPlaylist, SoundCloudV2CollectionItem, Serializable {
    public String display_date;
    public boolean is_album;
    public boolean managed_by_feeds;
    @SerializedName("public")
    public String public$;
    public String published_at; // ISO
    public String release_date;
    public int reposts_count;
    public String secret_token;
    public String set_type;
    public String title;
    public int id;
    public String kind;
    public String artwork_url;
    public String created_at;
    public String description;
    // public boolean downloadable;
    public int duration;
    // public String ean;
    public String embeddable_by;
    public String genre;
    // public int label_id;
    public String label_name;
    public String last_modified;
    public String license;
    public String permalink;
    public String permalink_url;
    // public String playlist_type;
    public String purchase_title;
    public String purchase_url;
    // public String release;
    //    public int release_day;
    //    public int release_month;
    //    public int release_year;
    public String sharing;
    // public boolean streamable;
    public String tag_list;
    public int track_count;
    public List<SoundCloudV2TrackModel> tracks;
    // public String type;
    public String uri;
    public SoundCloudV2UserModel user;
    public int user_id;
    public int likes_count;
    public SoundCloudV2UserModel label;
    public String tracks_uri;
    public String tags;
}
