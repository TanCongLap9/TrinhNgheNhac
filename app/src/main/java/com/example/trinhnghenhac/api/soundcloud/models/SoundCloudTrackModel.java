package com.example.trinhnghenhac.api.soundcloud.models;

import java.io.Serializable;

public class SoundCloudTrackModel implements SoundCloudTrackOrPlaylist, SoundCloudCollectionItem, Serializable {
    public String title;
    public String artwork_url;
    public int bpm;
    public int comment_count;
    public boolean commentable;
    public String created_at;
    public String description;
    public int download_count;
    public boolean downloadable;
    public int duration;
    public int favoritings_count;
    public String genre;
    public int id;
    public String isrc;
    public String key_signature;
    public String kind;
    public String label_name;
    public String license;
    public String permalink_url;
    public int playback_count;
    public String purchase_title;
    public String purchase_url;
    public String release;
    public int release_day;
    public int release_month;
    public int release_year;
    public String sharing;
    public String stream_url;
    public boolean streamable;
    public String tag_list;
    public String uri;
    public SoundCloudUserModel user;
    public boolean user_favorite;
    public String user_playback_count;
    public String waveform_url;
    public String available_country_codes;
    public String access; // playable, preview, blocked
    public String download_url;
    public int reposts_count;
    public String secret_uri;
}
