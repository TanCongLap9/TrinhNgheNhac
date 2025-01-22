package com.example.trinhnghenhac.api.zingmp3;

import com.example.trinhnghenhac.api.zingmp3.models.*;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ZingMp3Service {
    String ROUTE_GET_MEDIA_URL = "/api/v2/song/get/streaming";
    String ROUTE_GET_LYRIC = "/api/v2/lyric/get/lyric";
    String ROUTE_GET_SEARCH = "/api/v2/search";
    String ROUTE_GET_SEARCH_MULTI = "/api/v2/search/multi";
    String ROUTE_GET_SONG_INFO = "/api/v2/page/get/song";
    String ROUTE_GET_FEATURED = "https://ac.zingmp3.vn/v1/web/featured";
    String ROUTE_GET_SEARCH_HINT = "https://ac.zingmp3.vn/v1/web/ac-suggestions";
    String ROUTE_GET_HOME = "/api/v2/page/get/home";
    String ROUTE_GET_RECOMMENDED_SONGS = "/api/v2/recommend/get/songs";
    String ROUTE_GET_PLAYLIST_INFO = "/api/v2/page/get/playlist";
    String ROUTE_GET_ARTIST_INFO = "/api/v2/page/get/artist";
    String ROUTE_GET_VIDEO_INFO = "/api/v2/page/get/video";
    String ROUTE_GET_HUB_DETAIL = "/api/v2/page/get/hub-detail";
    String SEARCH_TYPE_ARTIST = "playlist";
    String SEARCH_TYPE_VIDEO = "video";
    String SEARCH_TYPE_SONG = "song";
    String SEARCH_TYPE_PLAYLIST = "artist";

    /*
    https://zingmp3.vn/api/v2/user/mymusic/get/overview?ctime=1732895511&version=1.11.11&sig=240fdb85074276a8ecd8e11f41fc5bb887f6d1a049e77399094c5ae19b8e11ce2ddbfa6a3589c2bc512faf64b0afc1eaba005d8b8902298c84a1708199143bd3&apiKey=X5BM3w8N7MKozC0B85o4KMlzLZKhV00y
    https://zingmp3.vn/api/v2/song/get/section-song-station?count=20&ctime=1732894639&version=1.11.11&sig=fb76e47606339baa04aa37fc7129bd506096355cde86978f873db179619263173aeafba3f268d6d4dd8dba6d90433f61646a46912f47f9b8c07eaead9d3ee344&apiKey=X5BM3w8N7MKozC0B85o4KMlzLZKhV00y
    https://zingmp3.vn/api/v2/user/playlist/get/list?type=created&page=1&count=18&sectionId=mPlaylist&ctime=1732894592&version=1.11.11&sig=b4387fdcac100f02ed60a0aa99ffea0411cee31e22ec5ed370b0151ad1ffe211c958c68446de5d406f3e45e03b236d2750af009b17f2a58a6d73eb7811b995d5&apiKey=X5BM3w8N7MKozC0B85o4KMlzLZKhV00y
    https://zingmp3.vn/api/v2/user/song/get/list?type=library&page=1&count=50&sectionId=mFavSong&ctime=1732894481&version=1.11.11&sig=0e993bfa9df16fd55a8dcc1164a237603506308bc807371100dc20436f8bb0ade4c4ef1ace001e510cd9d6a7661e2dc6a4172e18d940af367273f9823704b461&apiKey=X5BM3w8N7MKozC0B85o4KMlzLZKhV00y
    https://zingmp3.vn/api/v2/app/get/recommend-keyword?ctime=1732894083&version=1.11.11&sig=7aa1aa36e57e228cc50ac23977ece81bb9c5e550fa71d9a2a8836fc0a5c41ae3f1abecc7576c4d51478955822e047b6611a044ce5c405908cc9c2f47900cabe1&apiKey=X5BM3w8N7MKozC0B85o4KMlzLZKhV00y
    https://zingmp3.vn/api/v2/page/get/playlist?id=6BOU8CIE&sig=c68f78d5b929a7979e36ccee483575e5f24346a769df0ea9f77bf647c116caabb36906a5ba9855a0b437232bc1f337807d4531bbc6268de2137f60217d27f032&apiKey=X5BM3w8N7MKozC0B85o4KMlzLZKhV00y&ctime=1733020855&version=1.11.11
    https://zingmp3.vn/api/v2/song/get/vip-preview-info?id=Z7D7ZWZE&ctime=1732990908&version=1.11.11&sig=054b798db53404909a6b8b99ae33e8fbf84ce954406090ef1648a8d1ef7d19f77bbadba15218b2dc434881f448d9e31db07d0bc4df5cafe003460e384ef92a94&apiKey=X5BM3w8N7MKozC0B85o4KMlzLZKhV00y
     */

    /**
     * Gets cookie. Call this method before anything else.
     */
    @GET("/")
    Call<ResponseBody> getCookie();

    @GET(ROUTE_GET_MEDIA_URL)
    Call<ZingMp3Model<ZingMp3StreamingModel>> getMediaUrl(
            @Query("id") CharSequence songId,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_LYRIC)
    Call<ZingMp3Model<ZingMp3LyricsModel>> getLyrics(
            @Query("id") CharSequence songId,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_SEARCH_MULTI)
    Call<ZingMp3Model<ZingMp3SearchMultiResultModel>> getSearchMulti(
            @Query("q") CharSequence query,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_SEARCH)
    <T extends ZingMp3SectionItemModel> Call<ZingMp3Model<ZingMp3SearchResultModel<T>>> getSearch(
            @Query("q") CharSequence query,
            @Query("type") CharSequence type,
            @Query("page") int page,
            @Query("count") int count,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_SONG_INFO)
    Call<ZingMp3Model<ZingMp3SongModel>> getSongInfo(
            @Query("id") CharSequence songId,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_FEATURED)
    Call<ZingMp3Model<ZingMp3FeaturedModel>> getFeatured(
            @Query("query") CharSequence query,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_HOME)
    Call<ZingMp3Model<ZingMp3HomeModel>> getHome(
            @Query("page") int page,
            @Query("count") int count,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_RECOMMENDED_SONGS)
    Call<ZingMp3Model<ZingMp3RecommendedSongModel>> getRecommendedSongs(
            @Query("id") CharSequence songId,
            @Query("start") int start,
            @Query("count") int count,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_SEARCH_HINT)
    Call<ZingMp3Model<ZingMp3SearchHintModel>> getSearchHints(
            @Query("query") CharSequence query,
            @Query("num") int num,
            @Query("language") CharSequence language,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_PLAYLIST_INFO)
    Call<ZingMp3Model<ZingMp3PlaylistModel>> getPlaylistInfo(
            @Query("id") CharSequence playlistId,
            @Query("thumbSize") CharSequence thumbSize,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_ARTIST_INFO)
    Call<ZingMp3Model<ZingMp3ArtistModel>> getArtist(
            @Query("alias") CharSequence alias,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_VIDEO_INFO)
    Call<ZingMp3Model<ZingMp3VideoModel>> getVideoInfo(
            @Query("id") CharSequence id,
            @QueryMap Map<String, String> options);

    @GET(ROUTE_GET_HUB_DETAIL)
    Call<ZingMp3Model<ZingMp3HubDetailModel>> getHubDetail(
            @Query("id") CharSequence id,
            @QueryMap Map<String, String> options);
}
