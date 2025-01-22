package com.example.trinhnghenhac.api.zingmp3;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.api.DigestUtils;
import com.example.trinhnghenhac.api.SessionCookieJar;
import com.example.trinhnghenhac.api.nhaccuatui.NhacCuaTuiApi;
import com.example.trinhnghenhac.api.zingmp3.models.*;
import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Lyrics;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZingMp3Api implements MusicPlatformApi {
    private static final String BASE_URL = "https://zingmp3.vn";
    private static final String API_VERSION = "1.11.11";
    private static final String API_KEY = "X5BM3w8N7MKozC0B85o4KMlzLZKhV00y";
    private static final String SECRET_KEY = "acOrvUS15XRW2o9JksiK1KgQ6Vbds8ZW";
    private static ZingMp3Api sInstance;
    private final ZingMp3Service mService;
    private final SessionCookieJar mCookieJar;

    private ZingMp3Api() {
        mCookieJar = new SessionCookieJar();
        Gson gson = new GsonBuilder().registerTypeAdapter(ZingMp3SectionItemModel.class, new JsonDeserializer<ZingMp3SectionItemModel>() {
            @Override
            public ZingMp3SectionItemModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject object = json.getAsJsonObject();
                if (object.has("playlistId"))
                    return context.deserialize(object, ZingMp3ArtistModel.class);
                else if (object.has("isAlbum"))
                    return context.deserialize(object, ZingMp3PlaylistModel.class);
                else if (object.has("genreIds"))
                    return context.deserialize(object, ZingMp3SongModel.class);
                else
                    return context.deserialize(object, ZingMp3VideoModel.class);
            }
        }).create();
        mService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder()
                        .cookieJar(mCookieJar)
                        .build())
                .build()
                .create(ZingMp3Service.class);
    }

    private static long getTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public static ZingMp3Api getInstance() {
        if (sInstance == null) sInstance = new ZingMp3Api();
        return sInstance;
    }

    public static Map<String, String> getAuthorizationParams(CharSequence apiRoute) {
        long ctime = getTime();
        String n = "ctime=" + ctime + "version=" + API_VERSION;
        String sig = DigestUtils.getHmacSha512(apiRoute + DigestUtils.getSha256(n), SECRET_KEY);
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("ctime", Long.toString(ctime));
        queryParamsMap.put("sig", sig);
        queryParamsMap.put("apiKey", API_KEY);
        queryParamsMap.put("version", API_VERSION);
        return queryParamsMap;
    }

    public static Map<String, String> getAuthorizationParams(CharSequence apiRoute, CharSequence id) {
        long ctime = getTime();
        String n = "ctime=" + ctime + "id=" + id + "version=" + API_VERSION;
        String sig = DigestUtils.getHmacSha512(apiRoute + DigestUtils.getSha256(n), SECRET_KEY);
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("ctime", Long.toString(ctime));
        queryParamsMap.put("sig", sig);
        queryParamsMap.put("apiKey", API_KEY);
        queryParamsMap.put("version", API_VERSION);
        return queryParamsMap;
    }

    public static Map<String, String> getAuthorizationParams(CharSequence apiRoute, int count, CharSequence id) {
        long ctime = getTime();
        String n = "count=" + count + "ctime=" + ctime + "id=" + id + "version=" + API_VERSION;
        String sig = DigestUtils.getHmacSha512(apiRoute + DigestUtils.getSha256(n), SECRET_KEY);
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("ctime", Long.toString(ctime));
        queryParamsMap.put("sig", sig);
        queryParamsMap.put("apiKey", API_KEY);
        queryParamsMap.put("version", API_VERSION);
        return queryParamsMap;
    }

    public static Map<String, String> getAuthorizationParams(CharSequence apiRoute, int count, int page) {
        long ctime = getTime();
        String n = "count=" + count + "ctime=" + ctime + "page=" + page + "version=" + API_VERSION;
        String sig = DigestUtils.getHmacSha512(apiRoute + DigestUtils.getSha256(n), SECRET_KEY);
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("ctime", Long.toString(ctime));
        queryParamsMap.put("sig", sig);
        queryParamsMap.put("apiKey", API_KEY);
        queryParamsMap.put("version", API_VERSION);
        return queryParamsMap;
    }

    public static Map<String, String> getAuthorizationParams(CharSequence apiRoute, int count, int page, CharSequence type) {
        long ctime = getTime();
        String n = "count=" + count + "ctime=" + ctime + "page=" + page + "type=" + type + "version=" + API_VERSION;
        String sig = DigestUtils.getHmacSha512(apiRoute + DigestUtils.getSha256(n), SECRET_KEY);
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("ctime", Long.toString(ctime));
        queryParamsMap.put("sig", sig);
        queryParamsMap.put("apiKey", API_KEY);
        queryParamsMap.put("version", API_VERSION);
        return queryParamsMap;
    }

    public static Map<String, String> getAuthorizationParams(CharSequence apiRoute, int count) {
        long ctime = getTime();
        String n = "count=" + count + "ctime=" + ctime + "version=" + API_VERSION;
        String sig = DigestUtils.getHmacSha512(apiRoute + DigestUtils.getSha256(n), SECRET_KEY);
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("ctime", Long.toString(ctime));
        queryParamsMap.put("sig", sig);
        queryParamsMap.put("apiKey", API_KEY);
        queryParamsMap.put("version", API_VERSION);
        return queryParamsMap;
    }

    @Override
    @Nullable
    public String getMediaUrl(CharSequence songId) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3StreamingModel>> response =
                mService.getMediaUrl(
                        songId,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_MEDIA_URL, songId)
                ).execute();
        if (!checkSuccess(response)) return null;
        return response.body().data.stream128;
    }

    @Override
    @Nullable
    public Song getSongInfo(CharSequence songId) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3SongModel>> response =
                mService.getSongInfo(
                        songId,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_SONG_INFO, songId)
                ).execute();
        if (!checkSuccess(response)) return null;
        return ZingMp3Converter.convert(response.body().data);
    }

    @Override
    @Nullable
    public Cursor getSearchHints(CharSequence query) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3SearchHintModel>> response = mService.getSearchHints(
                query, 10, "vi",
                ZingMp3Api.getAuthorizationParams(ZingMp3Service.ROUTE_GET_SEARCH_HINT)
        ).execute();
        if (!checkSuccess(response)) return null;
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{BaseColumns._ID, SEARCH_COLUMN_NAME});
        if (response.body().data.items != null &&
                !response.body().data.items.isEmpty() &&
                response.body().data.items.get(0).keywords != null &&
                !response.body().data.items.get(0).keywords.isEmpty())
            for (int i = 0; i < response.body().data.items.get(0).keywords.size(); i++)
                matrixCursor.addRow(new Object[]{i, response.body().data.items.get(0).keywords.get(i).keyword});
        return matrixCursor;
    }

    @Override
    @Nullable
    public Lyrics getLyrics(CharSequence songId) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3LyricsModel>> response =
                mService.getLyrics(
                        songId,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_LYRIC, songId)
                ).execute();
        if (!checkSuccess(response)) return null;
        return ZingMp3Converter.convert(response.body().data);
    }

    @Override
    @Nullable
    public SearchResults getSearchResults(CharSequence query) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3SearchMultiResultModel>> response =
                mService.getSearchMulti(
                        query,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_SEARCH_MULTI)
                ).execute();
        if (!checkSuccess(response)) return null;
        return ZingMp3Converter.convert(response.body().data);
    }

    @Override
    @Nullable
    public Playlist getPlaylistInfo(CharSequence playlistId) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3PlaylistModel>> response =
                mService.getPlaylistInfo(
                        playlistId,
                        null,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_PLAYLIST_INFO, playlistId)
                ).execute();
        if (!checkSuccess(response)) return null;
        return ZingMp3Converter.convert(response.body().data);
    }

    @Override
    @Nullable
    public Artist getArtistInfo(CharSequence artistId) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3ArtistModel>> response =
                mService.getArtist(
                        artistId,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_ARTIST_INFO)
                ).execute();
        if (!checkSuccess(response)) return null;
        return ZingMp3Converter.convert(response.body().data);
    }

    @Override
    @Nullable
    public Video getVideoInfo(CharSequence videoId) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3VideoModel>> response =
                mService.getVideoInfo(
                        videoId,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_VIDEO_INFO, videoId)
                ).execute();
        if (!checkSuccess(response)) return null;
        return ZingMp3Converter.convert(response.body().data);
    }

    @Override
    @Nullable
    public List<Playable> getPlayablesByCategory(int category) throws IOException {
        HashMap<Integer, String> categoryIds = new HashMap<>();
        categoryIds.put(MusicCategory.CATEGORY_CHILL, "IWZ9Z0CI");
        categoryIds.put(MusicCategory.CATEGORY_JAZZ, "IWZ9Z0AB");
        categoryIds.put(MusicCategory.CATEGORY_VIETNAMESE, "IWZ9Z087");
        categoryIds.put(MusicCategory.CATEGORY_EUROPE, "IWZ9Z086");
        categoryIds.put(MusicCategory.CATEGORY_CHINESE, "IWZ9Z08Z");
        categoryIds.put(MusicCategory.CATEGORY_KOREAN, "IWZ9Z08U");
        categoryIds.put(MusicCategory.CATEGORY_WORKOUT, "IWZ9Z09F");
        categoryIds.put(MusicCategory.CATEGORY_COFFEE, "IWZ9Z09Z");
        categoryIds.put(MusicCategory.CATEGORY_BOLERO, "IWZ9Z09U");
        categoryIds.put(MusicCategory.CATEGORY_SAD, "IWZ9Z099");
        categoryIds.put(MusicCategory.CATEGORY_ACOUSTIC, "IWZ9Z089");
        categoryIds.put(MusicCategory.CATEGORY_INSTRUMENTAL, "IWZ9Z08D");
        categoryIds.put(MusicCategory.CATEGORY_CLASSICAL, "IWZ9Z0C9");
        categoryIds.put(MusicCategory.CATEGORY_CHILDREN, "IWZ9Z090");
        categoryIds.put(MusicCategory.CATEGORY_FILM, "IWZ9Z0B7");
        categoryIds.put(MusicCategory.CATEGORY_HIPHOP, "IWZ9Z08C");
        categoryIds.put(MusicCategory.CATEGORY_EDM, "IWZ9Z08B");
        categoryIds.put(MusicCategory.CATEGORY_NEW_RELEASES, "IWZ9Z0CA");
        if (!categoryIds.containsKey(category)) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Category for " + category + " is not implemented.");
            return null;
        }
        String categoryId = categoryIds.get(category);
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3HubDetailModel>> response = mService.getHubDetail(categoryId, getAuthorizationParams(ZingMp3Service.ROUTE_GET_HUB_DETAIL, categoryId)).execute();

        if (!checkSuccess(response)) return null;

        List<Playable> playables = new ArrayList<>();
        if (response.body().data.sections != null &&
                response.body().data.sections.get(1) != null &&
                response.body().data.sections.get(1).items != null)
            for (ZingMp3SectionItemModel item : response.body().data.sections.get(1).items) {
                if (item instanceof ZingMp3SongModel)
                    playables.add(ZingMp3Converter.convert((ZingMp3SongModel) item));
                if (item instanceof ZingMp3VideoModel)
                    playables.add(ZingMp3Converter.convert((ZingMp3VideoModel) item));
                if (item instanceof ZingMp3PlaylistModel)
                    playables.add(ZingMp3Converter.convert((ZingMp3PlaylistModel) item));
            }
        return playables;
    }

    @Nullable
    @Override
    public List<Song> getSearchSongs(CharSequence query, int page, int limit) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3SearchResultModel<ZingMp3SectionItemModel>>> response =
                mService.getSearch(
                        query,
                        ZingMp3Service.SEARCH_TYPE_SONG, page + 1, limit,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_SEARCH)
                ).execute();
        if (!checkSuccess(response)) return null;

        List<Song> songs = new ArrayList<>();
        for (int i = 0; i < response.body().data.items.size(); i++) {
            ZingMp3SectionItemModel item = response.body().data.items.get(i);
            if (item instanceof ZingMp3SongModel)
                songs.add(ZingMp3Converter.convert((ZingMp3SongModel) item));
        }
        return songs;
    }

    @Nullable
    @Override
    public List<Artist> getSearchArtists(CharSequence query, int page, int limit) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3SearchResultModel<ZingMp3SectionItemModel>>> response =
                mService.getSearch(
                        query,
                        ZingMp3Service.SEARCH_TYPE_ARTIST, page + 1, limit,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_SEARCH)
                ).execute();
        if (!checkSuccess(response)) return null;

        List<Artist> artists = new ArrayList<>();
        for (int i = 0; i < response.body().data.items.size(); i++) {
            ZingMp3SectionItemModel item = response.body().data.items.get(i);
            if (item instanceof ZingMp3ArtistModel)
                artists.add(ZingMp3Converter.convert((ZingMp3ArtistModel) item));
        }
        return artists;
    }

    @Nullable
    @Override
    public List<Playlist> getSearchPlaylists(CharSequence query, int page, int limit) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3SearchResultModel<ZingMp3SectionItemModel>>> response =
                mService.getSearch(
                        query,
                        ZingMp3Service.SEARCH_TYPE_PLAYLIST, page + 1, limit,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_SEARCH)
                ).execute();
        if (!checkSuccess(response)) return null;

        List<Playlist> playlists = new ArrayList<>();
        for (int i = 0; i < response.body().data.items.size(); i++) {
            ZingMp3SectionItemModel item = response.body().data.items.get(i);
            if (item instanceof ZingMp3PlaylistModel)
                playlists.add(ZingMp3Converter.convert((ZingMp3PlaylistModel) item));
        }
        return playlists;
    }

    @Nullable
    @Override
    public List<Video> getSearchVideos(CharSequence query, int page, int limit) throws IOException {
        if (mCookieJar.anyExpired()) mService.getCookie().execute();
        Response<ZingMp3Model<ZingMp3SearchResultModel<ZingMp3SectionItemModel>>> response =
                mService.getSearch(
                        query,
                        ZingMp3Service.SEARCH_TYPE_VIDEO, page + 1, limit,
                        getAuthorizationParams(ZingMp3Service.ROUTE_GET_SEARCH)
                ).execute();
        if (!checkSuccess(response)) return null;

        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < response.body().data.items.size(); i++) {
            ZingMp3SectionItemModel item = response.body().data.items.get(i);
            if (item instanceof ZingMp3VideoModel)
                videos.add(ZingMp3Converter.convert((ZingMp3VideoModel) item));
        }
        return videos;
    }

    public boolean checkSuccess(Response<? extends ZingMp3Model<? extends ZingMp3DataModel>> response) {
        if (response == null) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Response returned null");
            return false;
        }
        if (!response.isSuccessful()) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Response is not successful");
            return false;
        }
        if (response.body() == null) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Response body returned null");
            return false;
        }
        if (response.body().err != 0) {
            Log.w(ZingMp3Api.class.getSimpleName(), "Response body returned with error code " + response.body().err + ", message: " + response.body().msg);
            return false;
        }
        if (response.body().data == null) {
            Log.w(ZingMp3Api.class.getSimpleName(), "Data returned null");
            return false;
        }
        return true;
    }
}
