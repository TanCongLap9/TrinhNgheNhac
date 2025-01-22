package com.example.trinhnghenhac.api.soundcloudv2;

import com.example.trinhnghenhac.api.soundcloud.models.SoundCloudCollection;
import com.example.trinhnghenhac.api.soundcloud.models.SoundCloudPlaylistModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2Collection;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2CollectionItem;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2PlaylistModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2RepostModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2SearchHintItem;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2TrackModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2UserModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * A workaround for SoundCloud API
 */
public interface SoundCloudV2Service {
    /*
    https://api-v2.soundcloud.com/search/queries?q=v%C3%B3%20ng%E1%BB%B1a&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=10&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    q: vó ngựa
    client_id: SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL
    limit: 10
    offset: 0
    linked_partitioning: 1
    app_version: 1732887361
    app_locale: en
    */

    /*
    https://api-v2.soundcloud.com/search?q=v%C3%B3%20ng%E1%BB%B1a%20tr%C3%AAn%20%C4%91%E1%BB%93i%20c%E1%BB%8F%20non&sc_a_id=ba0dbd5c27239f49f8b09aef1d35757fdd47037f&variant_ids=&query_urn=soundcloud%3Asearch-autocomplete%3A7d7acf4ef89e441c8519bbbf449d02eb&facet=model&user_id=839253-402567-679556-992431&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    q: vó ngựa trên đồi cỏ non
    sc_a_id: ba0dbd5c27239f49f8b09aef1d35757fdd47037f
    variant_ids:
    query_urn: soundcloud:search-autocomplete:7d7acf4ef89e441c8519bbbf449d02eb
    facet: model
    user_id: 839253-402567-679556-992431
    client_id: SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL
    limit: 20
    offset: 0
    linked_partitioning: 1
    app_version: 1732887361
    app_locale: en
    */

    /*
    https://api-v2.soundcloud.com/tracks?ids=1149999559&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL
    https://api-v2.soundcloud.com/search/tracks?q=v%C3%B3%20ng%E1%BB%B1a%20tr%C3%AAn%20%C4%91%E1%BB%93i%20c%E1%BB%8F%20non&variant_ids=&facet=genre&user_id=131402-952913-241598-389844&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/search/queries?q=v%C3%B3%20ng%E1%BB%B1a%20tr%C3%AAn%20%C4%91%E1%BB%93i%20c%E1%BB%8F%20non&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=10&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/tracks?ids=1860829422&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&%5Bobject%20Object%5D=&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/search/tracks?q=s%C6%A1n%20t%C3%B9ng&variant_ids=&facet=genre&user_id=131402-952913-241598-389844&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/search/users?q=s%C6%A1n%20t%C3%B9ng&variant_ids=&facet=place&user_id=131402-952913-241598-389844&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/search/albums?q=s%C6%A1n%20t%C3%B9ng&variant_ids=&facet=genre&user_id=131402-952913-241598-389844&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/tracks?ids=1081596580%2C1089363859%2C119912868%2C145141042%2C150054315%2C175418373%2C193827922%2C195327295%2C195758079%2C195921631%2C196001089%2C196990924%2C198904735%2C220069050%2C228487877%2C231422896%2C255193053%2C278955819%2C297485909%2C318823995%2C338991370%2C450229686%2C450229707%2C522078324%2C522078450%2C63960382%2C669650009%2C669650120%2C669650144%2C669650282%2C669650297%2C956444059&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&%5Bobject%20Object%5D=&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/search/playlists_without_albums?q=s%C6%A1n%20t%C3%B9ng&variant_ids=&facet=genre&user_id=131402-952913-241598-389844&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/search/playlists_without_albums?query_urn=soundcloud%3Asearch%3A694f73fe1027487eab54c53e1d317b6e&facet=genre&limit=20&variant_ids=&user_id=131402-952913-241598-389844&offset=20&q=s%C6%A1n%20t%C3%B9ng&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/users/1090976224/albums?client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=10&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/users/1090976224/playlists_without_albums?client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=10&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/stream/users/1090976224/reposts?client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=10&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/stream/users/1090976224/reposts?client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=10&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/stream/users/1090976224/reposts?offset=2022-04-01T15%3A53%3A45.000Z%2Csource%3Atrack%3Arepost%2C00000000004272026414&limit=10&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&app_version=1732887361&app_locale=en

    https://api-v2.soundcloud.com/users/1090976224/tracks?representation=&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/search/albums?q=ep&variant_ids=&facet=genre&user_id=131402-952913-241598-389844&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&limit=20&offset=0&linked_partitioning=1&app_version=1732887361&app_locale=en

    https://api-v2.soundcloud.com/playlists/1887870752?representation=full&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&app_version=1732887361&app_locale=en
    https://api-v2.soundcloud.com/tracks/1574728444?representation=full&client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL&app_version=1732887361&app_locale=en

    https://api-v2.soundcloud.com/tracks/1545969292?client_id=SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL

    */
    String ROUTE_SEARCH = "/search";
    String ROUTE_SEARCH_TRACKS = "/search/tracks";
    String ROUTE_SEARCH_USERS = "/search/users";
    String ROUTE_SEARCH_ALBUMS = "/search/albums";
    String ROUTE_SEARCH_PLAYLISTS_WITHOUT_ALBUMS = "/search/playlists_without_albums";
    String ROUTE_SEARCH_QUERIES = "/search/queries";
    String ROUTE_USERS_ID = "/users/{userId}";
    String ROUTE_USERS_ID_TRACKS = "/users/{userId}/tracks";
    String ROUTE_USERS_ID_ALBUMS = "/users/{userId}/albums";
    String ROUTE_USERS_ID_REPOSTS = "/stream/users/{userId}/reposts";
    String ROUTE_USERS_ID_PLAYLISTS_WITHOUT_ALBUMS = "/users/{userId}/playlists_without_albums";
    String ROUTE_TRACKS = "/tracks";
    String ROUTE_TRACKS_ID = "/tracks/{trackId}";
    String ROUTE_PLAYLISTS_ID = "/playlists/{playlistId}";
    String KIND_TRACK = "track";
    String KIND_PLAYLIST = "playlist";
    String KIND_USER = "user";
    String MEMBER_KIND = "kind";


    @GET(ROUTE_SEARCH_TRACKS + "?linked_partitioning=true")
    Call<SoundCloudV2Collection<SoundCloudV2TrackModel>> getSearchTracks(
            @Query("q") CharSequence q,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_SEARCH_PLAYLISTS_WITHOUT_ALBUMS + "?linked_partitioning=true")
    Call<SoundCloudCollection<SoundCloudPlaylistModel>> getSearchPlaylists(
            @Query("q") CharSequence q,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_SEARCH_USERS + "?linked_partitioning=true")
    Call<SoundCloudV2Collection<SoundCloudV2UserModel>> getSearchUsers(
            @Query("q") CharSequence q,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_SEARCH_ALBUMS + "?linked_partitioning=true")
    Call<SoundCloudV2Collection<SoundCloudV2PlaylistModel>> getSearchAlbums(
            @Query("q") CharSequence q,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_SEARCH + "?linked_partitioning=1")
    Call<SoundCloudV2Collection<SoundCloudV2CollectionItem>> getSearchEverything(
            @Query("q") CharSequence q,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_SEARCH_QUERIES + "?linked_partitioning=1")
    Call<SoundCloudV2Collection<SoundCloudV2SearchHintItem>> getSearchHints(
            @Query("q") CharSequence q,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_USERS_ID_TRACKS + "?linked_partitioning=1")
    Call<SoundCloudV2Collection<SoundCloudV2TrackModel>> getTracksFromUser(
            @Path("userId") int userId,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_USERS_ID_ALBUMS + "?linked_partitioning=1")
    Call<SoundCloudV2Collection<SoundCloudV2PlaylistModel>> getAlbumsFromUser(
            @Path("userId") int userId,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_USERS_ID_PLAYLISTS_WITHOUT_ALBUMS + "?linked_partitioning=1")
    Call<SoundCloudV2Collection<SoundCloudV2PlaylistModel>> getPlaylistsFromUser(
            @Path("userId") int userId,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_USERS_ID_REPOSTS + "?linked_partitioning=1")
    Call<SoundCloudV2Collection<SoundCloudV2RepostModel>> getRepostsFromUser(
            @Path("userId") int userId,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_USERS_ID)
    Call<SoundCloudV2UserModel> getUserInfo(
            @Path("userId") int userId,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_TRACKS_ID)
    Call<SoundCloudV2TrackModel> getTrackInfo(
            @Path("trackId") int trackId,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_PLAYLISTS_ID)
    Call<SoundCloudV2PlaylistModel> getPlaylistInfo(
            @Path("playlistId") int playlistId,
            @QueryMap Map<String, String> options);
}
