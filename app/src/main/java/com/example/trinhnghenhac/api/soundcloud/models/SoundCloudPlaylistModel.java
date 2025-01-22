package com.example.trinhnghenhac.api.soundcloud.models;

import java.io.Serializable;
import java.util.List;

public class SoundCloudPlaylistModel implements SoundCloudTrackOrPlaylist, SoundCloudCollectionItem, Serializable {
    public String title;
    public int id;
    public String kind;
    public String artwork_url;
    public String created_at;
    public String description;
    public boolean downloadable;
    public int duration;
    public String ean;
    public String embeddable_by;
    public String genre;
    public int label_id;
    public String label_name;
    public String last_modified;
    public String license;
    public String permalink;
    public String permalink_url;
    public String playlist_type;
    public String purchase_title;
    public String purchase_url;
    public String release;
    public int release_day;
    public int release_month;
    public int release_year;
    public String sharing;
    public boolean streamable;
    public String tag_list;
    public int track_count;
    public List<SoundCloudTrackModel> tracks;
    public String type;
    public String uri;
    public SoundCloudUserModel user;
    public int user_id;
    public int likes_count;
    public SoundCloudUserModel label;
    public String tracks_uri;
    public String tags;
}
