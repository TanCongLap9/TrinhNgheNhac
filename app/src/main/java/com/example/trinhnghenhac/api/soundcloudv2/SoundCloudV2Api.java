package com.example.trinhnghenhac.api.soundcloudv2;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2Collection;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2CollectionItem;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2PlaylistModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2SearchHintItem;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2TrackModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2UserModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A workaround for SoundCloud API
 */
public class SoundCloudV2Api implements MusicPlatformApi {
    private static final String CLIENT_ID = "SVkIw5JG3HkzXAJlIvR3V43PkBxZ0dEL";
    private static final String BASE_URL = "https://api-v2.soundcloud.com";
    private static final String APP_VERSION = "1732887361";
    private static SoundCloudV2Api instance;
    private final SoundCloudV2Service mService;

    public SoundCloudV2Api() {
        Gson gson = new GsonBuilder().registerTypeAdapter(SoundCloudV2CollectionItem.class, new JsonDeserializer<SoundCloudV2CollectionItem>() {
            @Override
            public SoundCloudV2CollectionItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject object = json.getAsJsonObject();
                if (object.has(SoundCloudV2Service.MEMBER_KIND) && object.get(SoundCloudV2Service.MEMBER_KIND).getAsString().equals(SoundCloudV2Service.KIND_USER))
                    return context.deserialize(object, SoundCloudV2UserModel.class);
                else if (object.has(SoundCloudV2Service.MEMBER_KIND) && object.get(SoundCloudV2Service.MEMBER_KIND).getAsString().equals(SoundCloudV2Service.KIND_TRACK))
                    return context.deserialize(object, SoundCloudV2TrackModel.class);
                else if (object.has(SoundCloudV2Service.MEMBER_KIND) && object.get(SoundCloudV2Service.MEMBER_KIND).getAsString().equals(SoundCloudV2Service.KIND_PLAYLIST))
                    return context.deserialize(object, SoundCloudV2PlaylistModel.class);
                else throw new ClassCastException();
            }
        }).create();
        mService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SoundCloudV2Service.class);
    }

    public static SoundCloudV2Api getInstance() {
        if (instance == null) instance = new SoundCloudV2Api();
        return instance;
    }

    public Map<String, String> getAuthorizationParams() {
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("client_id", CLIENT_ID);
        queryParamsMap.put("app_version", APP_VERSION);
        return queryParamsMap;
    }

    @Override
    public String getMediaUrl(CharSequence songId) throws IOException {
        Response<SoundCloudV2TrackModel> response = mService.getTrackInfo(
                Integer.parseInt(songId.toString()),
                getAuthorizationParams()).execute();

        SoundCloudV2TrackModel model = response.body();
        if (model.media == null || model.media.transcodings == null || model.media.transcodings.size() == 0)
            return null;
        else
            return model.media.transcodings.get(0).url;
    }

    @Override
    public Song getSongInfo(CharSequence songId) throws IOException {
        Response<SoundCloudV2TrackModel> response = mService.getTrackInfo(
                Integer.parseInt(songId.toString()),
                getAuthorizationParams()).execute();
        return SoundCloudV2Converter.convert(response.body());
    }

    @Override
    public Cursor getSearchHints(CharSequence query) throws IOException {
        Response<SoundCloudV2Collection<SoundCloudV2SearchHintItem>> response =
                mService.getSearchHints(
                        query, 20, 0, getAuthorizationParams()).execute();

        MatrixCursor matrixCursor = new MatrixCursor(new String[] {BaseColumns._ID, SEARCH_COLUMN_NAME});
        for (int i = 0; i < response.body().collection.size(); i++)
            matrixCursor.addRow(new Object[] {i, response.body().collection.get(i).query});
        return matrixCursor;
    }

    @Override
    public SearchResults getSearchResults(CharSequence query) throws IOException {
        Response<SoundCloudV2Collection<SoundCloudV2CollectionItem>> response =
                mService.getSearchEverything(query, 20, 0, getAuthorizationParams()).execute();
        return SoundCloudV2Converter.convert(response.body());
    }

    @Override
    public Lyrics getLyrics(CharSequence songId) throws IOException {
        return null;
    }

    @Override
    public Playlist getPlaylistInfo(CharSequence playlistId) throws IOException {
        Response<SoundCloudV2PlaylistModel> response = mService.getPlaylistInfo(
                Integer.parseInt(playlistId.toString()),
                getAuthorizationParams()).execute();
        return SoundCloudV2Converter.convert(response.body());
    }

    @Override
    public Artist getArtistInfo(CharSequence artistId) throws IOException {
        Response<SoundCloudV2UserModel> response = mService.getUserInfo(
                Integer.parseInt(artistId.toString()),
                getAuthorizationParams()).execute();
        return SoundCloudV2Converter.convert(response.body());
    }

    @Override
    public Video getVideoInfo(CharSequence videoId) throws IOException {
        return null;
    }

    @Override
    public List<Playable> getPlayablesByCategory(int category) throws IOException {
        return null;
    }

    @Nullable
    @Override
    public List<Song> getSearchSongs(CharSequence query, int page, int limit) throws IOException {
        return null;
    }

    @Nullable
    @Override
    public List<Artist> getSearchArtists(CharSequence query, int page, int limit) throws IOException {
        return null;
    }

    @Nullable
    @Override
    public List<Playlist> getSearchPlaylists(CharSequence query, int page, int limit) throws IOException {
        return null;
    }

    @Nullable
    @Override
    public List<Video> getSearchVideos(CharSequence query, int page, int limit) throws IOException {
        return null;
    }
}
