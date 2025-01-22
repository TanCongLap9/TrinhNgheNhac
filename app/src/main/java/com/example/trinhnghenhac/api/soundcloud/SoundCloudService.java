package com.example.trinhnghenhac.api.soundcloud;

import com.example.trinhnghenhac.api.soundcloud.models.SoundCloudCollection;
import com.example.trinhnghenhac.api.soundcloud.models.SoundCloudPlaylistModel;
import com.example.trinhnghenhac.api.soundcloud.models.SoundCloudTrackModel;
import com.example.trinhnghenhac.api.soundcloud.models.SoundCloudUserModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SoundCloudService {
    String ROUTE_ME = "/me";
    String ROUTE_ME_ACTIVITIES = "/me/activities";
    String ROUTE_ME_ACTIVITIES_ALL_OWN = "/me/activities/all/own";
    String ROUTE_ME_ACTIVITIES_TRACKS = "/me/activities/tracks";
    String ROUTE_ME_LIKES_TRACKS = "/me/likes/tracks";
    String ROUTE_ME_LIKES_PLAYLISTS = "/me/likes/playlists";
    String ROUTE_ME_FOLLOWINGS = "/me/followings";
    String ROUTE_ME_FOLLOWING_TRACKS = "/me/followings/tracks";
    String ROUTE_ME_FOLLOWING_ID = "/me/followings/{userId}";
    String ROUTE_ME_FOLLOWERS = "/me/followers";
    String ROUTE_ME_PLAYLISTS = "/me/playlists";
    String ROUTE_ME_TRACKS = "/me/tracks";
    String ROUTE_TRACKS = "/tracks";
    String ROUTE_TRACKS_ID = "/tracks/{trackId}";
    String ROUTE_TRACKS_ID_STREAMS = "/tracks/{trackId}/streams";
    String ROUTE_TRACKS_ID_COMMENTS = "/tracks/{trackId}/comments";
    String ROUTE_TRACKS_ID_FAVORITERS = "/tracks/{trackId}/favoriters";
    String ROUTE_TRACKS_ID_REPOSTERS = "/tracks/{trackId}/reposters";
    String ROUTE_TRACKS_ID_RELATED = "/tracks/{trackId}/related";
    String ROUTE_PLAYLISTS = "/playlists";
    String ROUTE_PLAYLISTS_ID = "/playlists/{playlistId}";
    String ROUTE_PLAYLISTS_ID_TRACKS = "/playlists/{playlistId}/tracks";
    String ROUTE_PLAYLISTS_ID_REPOSTERS = "/playlists/{playlistId}/reposters";
    String ROUTE_USERS = "/users";
    String ROUTE_USERS_ID = "/users/{userId}";
    String ROUTE_USERS_ID_FOLLOWERS = "/users/{userId}/followers";
    String ROUTE_USERS_ID_FOLLOWINGS = "/users/{userId}/followings";
    String ROUTE_USERS_ID_PLAYLISTS = "/users/{userId}/playlists";
    String ROUTE_USERS_ID_TRACKS = "/users/{userId}/tracks";
    String ROUTE_USERS_ID_WEB_PROFILES = "/users/{userId}/web-profiles";
    String ROUTE_USERS_ID_LIKES_TRACKS = "/users/{userId}/likes/tracks";
    String ROUTE_USERS_ID_LIKES_PLAYLISTS = "/users/{userId}/likes/playlists";
    String ROUTE_LIKES_TRACKS_ID = "/likes/tracks/{trackId}";
    String ROUTE_LIKES_PLAYLISTS_ID = "/likes/playlists/{playlistId}";
    String ROUTE_REPOSTS_TRACKS_ID = "/reposts/tracks/{trackId}";
    String ROUTE_REPOSTS_PLAYLISTS_ID = "/reposts/playlists/{playlistId}";
    String ROUTE_RESOLVE = "/resolve";

    @GET(ROUTE_TRACKS + "?linked_partitioning=true")
    SoundCloudCollection<SoundCloudTrackModel> searchTracks(@Query("q") String q, @Query("limit") int limit);

    @GET(ROUTE_PLAYLISTS + "?linked_partitioning=true")
    SoundCloudCollection<SoundCloudPlaylistModel> searchPlaylists(@Query("q") String q, @Query("limit") int limit);

    @GET(ROUTE_USERS + "?linked_partitioning=true")
    SoundCloudCollection<SoundCloudUserModel> searchUsers(@Query("q") String q, @Query("limit") int limit);

    @GET(ROUTE_PLAYLISTS_ID)
    SoundCloudPlaylistModel getPlaylist(@Path("playlist_id") int playlist_id);

    @GET(ROUTE_TRACKS_ID)
    SoundCloudTrackModel getTrack(@Path("track_id") int track_id);

    @GET(ROUTE_USERS_ID)
    SoundCloudUserModel getUser(@Path("user_id") int user_id);
}
