package com.example.trinhnghenhac.api.nhaccuatui;

import com.example.trinhnghenhac.api.nhaccuatui.querymodels.*;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface NhacCuaTuiService {
    String ROUTE_ARTIST_DETAIL = "/api/artist/detail";
    String ROUTE_LYRIC = "/api/lyric";
    String ROUTE_QUICK_SEARCH = "/api/search/quick";
    String ROUTE_GET_SEARCH_ALL = "/api/search/all";
    String ROUTE_GET_SEARCH_SONG = "/api/search/song";
    String ROUTE_GET_SEARCH_VIDEO = "/api/search/video";
    String ROUTE_GET_SEARCH_PLAYLIST = "/api/search/playlist";
    String ROUTE_PLAYING_SONG = "/api/playing/song";
    String ROUTE_PLAYING_PLAYLIST = "/api/playing/playlist";
    String ROUTE_MEDIA_INFO = "/api/media/info";
    String ROUTE_TOPIC_DETAIL = "/api/topic/detail";
    String ROUTE_GENRE = "/api/genre";
    String TYPE_SONG = "song";
    String TYPE_ALL = "all";
    String TYPE_VIDEO = "video";
    String TYPE_PLAYLIST = "playlist";
    String TYPE_DESCRIPTION = "description";
    String STATUS_SUCCESS = "success";
    String STATUS_ERROR = "error";
/*
    https://beta.nhaccuatui.com/api/search/all?a=e3afd4b6c89147258a56a641af16cc79&s=c7fbd1d6520bba9ca297bcc0cbfe047bb79a89429ff494d6fc3ced6a10286e5e273dc86cd3251543c9fece485d6abbdfb6a059fb418b743189b6ada4cf4fb1a9&t=1733028436122
    key: sơn tùng
    pageSize: 12

    https://beta.nhaccuatui.com/api/playing/song?a=e3afd4b6c89147258a56a641af16cc79&s=30fa9214fe892a8f909983a40df40c9831b5f9de00b54b3957620862c3f8ad0de9e134667a76fcdfa9e1acf81aca2a413f17eae93c7cc061832528d2db5e04d8&t=1733029197871
    key: vtEybe9NxLw7

    https://beta.nhaccuatui.com/api/search/song?a=e3afd4b6c89147258a56a641af16cc79&s=ac7deba644021a3d7e8eee626af9ecac019c7aa2b75eface12a5dd96dd48963d2df1f51d237082d9e34317dca878a4394b2c1d498515e982db5d1140385772e3&t=1733029405358
    key: sơn tùng
    pageIndex: 1
    pageSize: 36

    https://beta.nhaccuatui.com/api/search/playlist?a=e3afd4b6c89147258a56a641af16cc79&s=470e3fd10b7ce79325d09797fcdb52ec3a322d4a882a665b92f7438f314b8e284f66608a3fa1917c7b3bd3e7e7bb6f9490c56232650117e4882d5950c752b210&t=1733029410132
    key: sơn tùng
    pageIndex: 1
    pageSize: 36

    https://beta.nhaccuatui.com/api/search/video?a=e3afd4b6c89147258a56a641af16cc79&s=24d269d1997fc31df17350a480086a2f40430ffd121d6727148bac11280a8617efdcfb523218bc2f0a8ee9980583378b38d7802c315b3263073868515a616e58&t=1733029440827
    key: sơn tùng
    pageIndex: 1
    pageSize: 36
    No results

    https://beta.nhaccuatui.com/api/search/video?a=e3afd4b6c89147258a56a641af16cc79&s=d79d7ac31fc5c10c68e20caae2528b64f7fe0afe16b5b83ed94b04d8753daad65713a2357cc6b7cf58a8c0344b9ba4d2bba886be215c5f963a893e468fa38067&t=1733029535855
    key: e
    pageIndex: 1
    pageSize: 36

    https://beta.nhaccuatui.com/api/media/info?a=e3afd4b6c89147258a56a641af16cc79&s=bd5d52cf40c9685048a11eb3b1e8ed6e914d5455340f9ee24d56e0016ae9750ff2456985b67b6aad1687e35fd63ba8de7d2dc4e1bc31004932313c6433507fe3&t=1733030734922
    key: drtVtzOFHpZ5
    type: song

    https://beta.nhaccuatui.com/api/lyric?a=e3afd4b6c89147258a56a641af16cc79&s=f6cc80e74f07e2a556a5cf47f8f908270dce70e567e9f6f1b8f30888dd2cb22e735a102a33e288a3b61c743d3fc7ce8efb7d11f1e43c96d338d81d52eed7472d&t=1733030737263
    key: drtVtzOFHpZ5
    type: song

    https://beta.nhaccuatui.com/api/lyric?a=e3afd4b6c89147258a56a641af16cc79&s=6758e74359c6e75a3befa9a30fd183ee0e12b47b47b81b7f08145f4d8121a3768b7c06f51b894705de3c49f30005000a0b5cef6397ffd4e70992f9d1c74c36fd&t=1733031043685
    key: bwlP9Ip3Uzc8
    type: song

    https://beta.nhaccuatui.com/api/search/quick?a=e3afd4b6c89147258a56a641af16cc79&s=603eae1336d3e10ca54c8bfd6928e3d4ef3113861dca1b77d293eeae1bc4620bd8fba4d624be8b33ae5244b506e12977a75b4a193d4744db5eddbe8d09828df4&t=1733031404286
    key: sơn tùng
    size: 5

    https://beta.nhaccuatui.com/api/artist/detail?a=e3afd4b6c89147258a56a641af16cc79&s=e7bc461b837d109e66c2b7a1e212869e72138cbc025e75991692a425c91f421cbc4c014e4b75b07e8d88face496c23bf5635faf5fbf1eb8bebc7d16adfae20b4&t=1733031486255
    shortLink: vu
    type: all
    size: 20
    index: 1
    sort: 0

    https://beta.nhaccuatui.com/api/artist/detail?a=e3afd4b6c89147258a56a641af16cc79&s=d944a5da27865ea6a2a9089226817afd832feff9af4daf755a01bcdf80d44ad389bf94bc0653cbbbc0c5f9a4ccdd6ad070aa9aba14b536ed00bdf81c781874f9&t=1733033495808
    shortLink: son-tung-mtp
    type: all
    size: 20
    index: 1
    sort: 0


    https://beta.nhaccuatui.com/api/playing/playlist?a=e3afd4b6c89147258a56a641af16cc79&s=27f60ced1a517412dc65a0368afa83ae910ffed7f936fca6cc059b228d0322684cb00f980188ce15a328fa28f01552e03f6da1bcb3cf60d919afa23988f81abe&t=1733033141880
    key: uBwEwbqakP9e
    */

    @FormUrlEncoded
    @POST(ROUTE_GET_SEARCH_ALL)
    Call<NhacCuaTuiSearchAllModel> getSearchAll(
            @Field("key") CharSequence key,
            @Field("pageSize") int pageSize,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_PLAYING_SONG)
    Call<NhacCuaTuiSongModel> getSongInfo(
            @Field("key") CharSequence key,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_GET_SEARCH_VIDEO)
    Call<NhacCuaTuiSearchVideosModel> getSearchVideos(
            @Field("key") CharSequence key,
            @Field("pageIndex") int pageIndex,
            @Field("pageSize") int pageSize,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_GET_SEARCH_SONG)
    Call<NhacCuaTuiSearchSongsModel> getSearchSongs(
            @Field("key") CharSequence key,
            @Field("pageIndex") int pageIndex,
            @Field("pageSize") int pageSize,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_GET_SEARCH_PLAYLIST)
    Call<NhacCuaTuiSearchPlaylistsModel> getSearchPlaylists(
            @Field("key") CharSequence key,
            @Field("pageIndex") int pageIndex,
            @Field("pageSize") int pageSize,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_MEDIA_INFO)
    Call<NhacCuaTuiSongModel> getMediaInfo(
            @Field("key") CharSequence key,
            @Field("type") CharSequence type,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_LYRIC)
    Call<NhacCuaTuiLyricsModel> getLyrics(
            @Field("key") CharSequence key,
            @Field("type") CharSequence type,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_QUICK_SEARCH)
    Call<NhacCuaTuiSearchHintsModel> getSearchHints(
            @Field("key") CharSequence key,
            @Field("size") int size,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_ARTIST_DETAIL)
    Call<NhacCuaTuiArtistModel> getArtistInfo(
            @Field("shortLink") CharSequence shortLink,
            @Field("type") CharSequence type,
            @Field("size") int size,
            @Field("index") int index,
            @Field("sort") int sort,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_PLAYING_PLAYLIST)
    Call<NhacCuaTuiPlaylistModel> getPlaylistInfo(
            @Field("key") CharSequence key,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_TOPIC_DETAIL)
    Call<NhacCuaTuiTopicModel> getTopicDetail(
            @Field("key") CharSequence key,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ROUTE_GENRE)
    Call<NhacCuaTuiSongsModel> getGenre(
            @Field("type") CharSequence type,
            @Field("key") CharSequence key,
            @Field("order") int order,
            @Field("pageIndex") int pageIndex,
            @Field("pageSize") int pageSize,
            @QueryMap Map<String, String> options);
}