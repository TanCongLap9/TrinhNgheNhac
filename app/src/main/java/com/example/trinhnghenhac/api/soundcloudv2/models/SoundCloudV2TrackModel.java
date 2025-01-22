package com.example.trinhnghenhac.api.soundcloudv2.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SoundCloudV2TrackModel implements SoundCloudV2TrackOrPlaylist, SoundCloudV2CollectionItem, Serializable {
    // public ? caption
    @Nullable
    public String display_date; // ISO
    @Nullable
    public String embeddable_by;
    public int full_duration; // ms
    public boolean has_downloads_left;
    @Nullable
    public String last_modified;
    public int likes_count;
    @Nullable
    public MediaModel media;
    @Nullable
    public String monetization_model;
    @Nullable
    public String policy;
    @SerializedName("public")
    public boolean public$;
    @Nullable
    public PublisherMetadataModel publisher_metadata;
    @Nullable
    public String release_date;
    @Nullable
    public String secret_token;
    @Nullable
    public String state;
    @Nullable
    public String station_permalink;
    @Nullable
    public String station_urn;
    @Nullable
    public String title;
    @Nullable
    public String track_authorization;
    @Nullable
    public String urn;
    public int user_id;
    // public ? visuals
    @Nullable
    public String artwork_url;
    // public int bpm;
    public int comment_count;
    public boolean commentable;
    @Nullable
    public String created_at; // ISO
    @Nullable
    public String description;
    public int download_count;
    public boolean downloadable;
    public long duration; // ms
    // public int favoritings_count;
    @Nullable
    public String genre;
    public int id;
    // public String isrc;
    // public String key_signature;
    @Nullable
    public String kind;
    @Nullable
    public String label_name;
    @Nullable
    public String license;
    @Nullable
    public String permalink;
    @Nullable
    public String permalink_url;
    public int playback_count;
    @Nullable
    public String purchase_title;
    @Nullable
    public String purchase_url;
    // public String release;
    // public int release_day;
    // public int release_month;
    // public int release_year;
    @Nullable
    public String sharing;
    // public String stream_url;
    public boolean streamable;
    @Nullable
    public String tag_list;
    @Nullable
    public String uri;
    @Nullable
    public SoundCloudV2UserModel user;
    // public boolean user_favorite;
    // public String user_playback_count;
    @Nullable
    public String waveform_url;
    // public String available_country_codes;
    // public String access; // playable, preview, blocked
    // public String download_url;
    public int reposts_count;
    //public String secret_uri;

    public static class MediaModel {
        @Nullable
        public List<TranscodingModel> transcodings;
    }

    public static class TranscodingModel {
        public int duration;
        @Nullable
        public FormatModel format;
        @Nullable
        public String preset;
        @Nullable
        public String quality;
        public boolean snipped;
        @Nullable
        public String url;
    }

    public static class FormatModel {
        @Nullable
        public String mime_type;
        @Nullable
        public String protocol;
    }

    public static class PublisherMetadataModel {
        public boolean contains_music;
        public int id;
        @Nullable
        public String urn;
    }
}
