package com.example.trinhnghenhac.api.nhaccuatui;

import com.example.trinhnghenhac.constants.MusicCategory;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.trinhnghenhac.api.DigestUtils;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiStreamingModel;
import com.example.trinhnghenhac.api.nhaccuatui.querymodels.*;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Lyrics;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NhacCuaTuiApi implements MusicPlatformApi {
    private static final String BASE_URL = "https://beta.nhaccuatui.com";
    private static final String API_KEY = "e3afd4b6c89147258a56a641af16cc79";
    private static final String SECRET_KEY = "6847f1a4fc2f4eb6ab13f9084e082ef4";
    private static NhacCuaTuiApi instance;
    private final NhacCuaTuiService mService;
    private NhacCuaTuiApi() {
        mService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NhacCuaTuiService.class);
    }

    private static long getTime() {
        return System.currentTimeMillis();
    }

    public static NhacCuaTuiApi getInstance() {
        if (instance == null) instance = new NhacCuaTuiApi();
        return instance;
    }

    public static Map<String, String> getAuthorizationParams(String apiRoute) {
        long time = getTime();
        String sig = DigestUtils.getHmacSha512(Long.toString(time), SECRET_KEY);
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("a", API_KEY);
        queryParamsMap.put("s", sig);
        queryParamsMap.put("t", Long.toString(time));
        return queryParamsMap;
    }

    @Override
    public String getMediaUrl(CharSequence songId) throws IOException {
        Response<NhacCuaTuiSongModel> response = mService.getMediaInfo(
                songId, NhacCuaTuiService.TYPE_SONG, getAuthorizationParams(NhacCuaTuiService.ROUTE_MEDIA_INFO)).execute();

        if (!checkSuccess(response)) return null;

        List<NhacCuaTuiStreamingModel> streamUrls = response.body().song.streamUrls;
        if (streamUrls != null)
            for (NhacCuaTuiStreamingModel streamUrl : streamUrls) {
                if (streamUrl.quality.equals("128"))
                    return streamUrl.streamUrl;
            }
        return null;
    }

    @Override
    public Song getSongInfo(CharSequence songId) throws IOException {
        Response<NhacCuaTuiSongModel> response = mService.getSongInfo(
                songId, getAuthorizationParams(NhacCuaTuiService.ROUTE_PLAYING_SONG)).execute();

        if (!checkSuccess(response)) return null;

        return NhacCuaTuiConverter.convert(response.body().song);
    }

    @Override
    public Cursor getSearchHints(CharSequence query) throws IOException {
        Response<NhacCuaTuiSearchHintsModel> response = mService.getSearchHints(
                query, 10,
                getAuthorizationParams(NhacCuaTuiService.ROUTE_QUICK_SEARCH)
        ).execute();

        if (!checkSuccess(response)) return null;

        MatrixCursor matrixCursor = new MatrixCursor(new String[] {BaseColumns._ID, SEARCH_COLUMN_NAME});
        int itemsCount = 0;
        int i;
        for (i = 0; i < response.body().song.size(); i++)
            matrixCursor.addRow(new Object[] {itemsCount + i, response.body().song.get(i).title});
        itemsCount += i;
        for (i = 0; i < response.body().playlist.size(); i++)
            matrixCursor.addRow(new Object[] {itemsCount + i, response.body().playlist.get(i).title});
        itemsCount += i;
        for (i = 0; i < response.body().artist.size(); i++)
            matrixCursor.addRow(new Object[] {itemsCount + i, response.body().artist.get(i).name});
        return matrixCursor;
    }

    @Override
    public SearchResults getSearchResults(CharSequence query) throws IOException {
        Response<NhacCuaTuiSearchAllModel> response = mService.getSearchAll(
                query, 50, getAuthorizationParams(NhacCuaTuiService.ROUTE_GET_SEARCH_ALL)).execute();

        if (!checkSuccess(response)) return null;
        if (response.body().search == null) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Search returned null");
            return null;
        }
        return NhacCuaTuiConverter.convert(response.body().search);
    }

    @Override
    public Lyrics getLyrics(CharSequence songId) throws IOException {
        Response<NhacCuaTuiLyricsModel> response = mService.getLyrics(
                songId, NhacCuaTuiService.TYPE_SONG, getAuthorizationParams(NhacCuaTuiService.ROUTE_LYRIC)).execute();

        if (!checkSuccess(response)) return null;

        return NhacCuaTuiConverter.convert(response.body().lyric);
    }

    @Override
    public Playlist getPlaylistInfo(CharSequence playlistId) throws IOException {
        Response<NhacCuaTuiPlaylistModel> response = mService.getPlaylistInfo(
                playlistId, getAuthorizationParams(NhacCuaTuiService.ROUTE_PLAYING_PLAYLIST)).execute();

        if (!checkSuccess(response)) return null;

        return NhacCuaTuiConverter.convert(response.body().playlist);
    }

    @Override
    public Artist getArtistInfo(CharSequence artistId) throws IOException {
        Response<NhacCuaTuiArtistModel> response = mService.getArtistInfo(
                artistId, NhacCuaTuiService.TYPE_ALL, 20, 0, 0, getAuthorizationParams(NhacCuaTuiService.ROUTE_ARTIST_DETAIL)).execute();

        if (!checkSuccess(response)) return null;

        return NhacCuaTuiConverter.convert(response.body());
    }

    @Override
    public Video getVideoInfo(CharSequence videoId) throws IOException {
        return null;
    }

    @Override
    public List<Playable> getPlayablesByCategory(int category) throws IOException {
        HashMap<Integer, String> categoryIds = new HashMap<>();
        categoryIds.put(MusicCategory.CATEGORY_CHILL, "wepgktzom");
        categoryIds.put(MusicCategory.CATEGORY_JAZZ, "wratctrdp");
        categoryIds.put(MusicCategory.CATEGORY_VIETNAMESE, "wrdastbhw");
        categoryIds.put(MusicCategory.CATEGORY_EUROPE, "weieatejm");
        categoryIds.put(MusicCategory.CATEGORY_CHINESE, "wratsyflt");
        categoryIds.put(MusicCategory.CATEGORY_KOREAN, "wrapqgbmi");
        categoryIds.put(MusicCategory.CATEGORY_WORKOUT, "weoklplbd");
        categoryIds.put(MusicCategory.CATEGORY_COFFEE, "weocfcben");
        categoryIds.put(MusicCategory.CATEGORY_BOLERO, "weouymyiu");
        categoryIds.put(MusicCategory.CATEGORY_SAD, "weoumvsph");
        categoryIds.put(MusicCategory.CATEGORY_ACOUSTIC, "weoofjbgp");
        categoryIds.put(MusicCategory.CATEGORY_INSTRUMENTAL, "wrdsjmsju");
        categoryIds.put(MusicCategory.CATEGORY_CLASSICAL, "weojthusx");
        categoryIds.put(MusicCategory.CATEGORY_CHILDREN, "weihaowig");
        categoryIds.put(MusicCategory.CATEGORY_FILM, "weibcrjia");
        categoryIds.put(MusicCategory.CATEGORY_HIPHOP, "wrdslmtsi");
        categoryIds.put(MusicCategory.CATEGORY_EDM, "weojnddgy");
        if (category == MusicCategory.CATEGORY_NEW_RELEASES) {
            Response<NhacCuaTuiSongsModel> response = mService.getGenre(
                    NhacCuaTuiService.TYPE_SONG,
                    "moi-hot",
                    1,
                    1,
                    36,
                    getAuthorizationParams(NhacCuaTuiService.ROUTE_GENRE)).execute();

            if (!checkSuccess(response)) return new ArrayList<>();

            List<Playable> playables = new ArrayList<>();
            if (response.body().data != null) {
                for (com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiSongModel song : response.body().data)
                    playables.add(NhacCuaTuiConverter.convert(song));
            }
            return playables;
        }
        else if (!categoryIds.containsKey(category)) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Category for " + category + " is not implemented.");
            return new ArrayList<>();
        }
        String categoryId = categoryIds.get(category);
        Response<NhacCuaTuiTopicModel> response = mService.getTopicDetail(categoryId, getAuthorizationParams(NhacCuaTuiService.ROUTE_TOPIC_DETAIL)).execute();

        if (!checkSuccess(response)) return new ArrayList<>();

        List<Playable> playables = new ArrayList<>();
        if (response.body().topic != null &&
                response.body().topic.playlist != null)
            for (com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiPlaylistModel playlist : response.body().topic.playlist) {
                playables.add(NhacCuaTuiConverter.convert(playlist));
            }
        return playables;
    }

    public boolean checkSuccess(Response<? extends NhacCuaTuiQueryModel> response) {
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
        if (!response.body().status.equals(NhacCuaTuiService.STATUS_SUCCESS)) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Response body returned with error message " + response.body().status);
            return false;
        }
        return true;
    }

    @Nullable
    @Override
    public List<Song> getSearchSongs(CharSequence query, int page, int limit) throws IOException {
        Response<NhacCuaTuiSearchSongsModel> response = mService.getSearchSongs(
                query, page + 1, limit, getAuthorizationParams(NhacCuaTuiService.ROUTE_GET_SEARCH_SONG)).execute();

        if (!checkSuccess(response)) return null;

        if (response.body().song == null) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Song returned null");
            return null;
        }
        List<Song> songs = new ArrayList<>();
        for (com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiSongModel song : response.body().song.song) {
            songs.add(NhacCuaTuiConverter.convert(song));
        }
        return songs;
    }

    @Nullable
    @Override
    public List<Artist> getSearchArtists(CharSequence query, int page, int limit) throws IOException {
        return new ArrayList<>(); // The API doesn't have search artists
    }

    @Nullable
    @Override
    public List<Playlist> getSearchPlaylists(CharSequence query, int page, int limit) throws IOException {
        Response<NhacCuaTuiSearchPlaylistsModel> response = mService.getSearchPlaylists(
                query, page + 1, limit, getAuthorizationParams(NhacCuaTuiService.ROUTE_GET_SEARCH_PLAYLIST)).execute();

        if (!checkSuccess(response)) return null;

        if (response.body().playlist == null) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Playlist returned null");
            return null;
        }
        List<Playlist> playlists = new ArrayList<>();
        for (com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiPlaylistModel playlist : response.body().playlist.playlist) {
            playlists.add(NhacCuaTuiConverter.convert(playlist));
        }
        return playlists;
    }

    @Nullable
    @Override
    public List<Video> getSearchVideos(CharSequence query, int page, int limit) throws IOException {
        Response<NhacCuaTuiSearchVideosModel> response = mService.getSearchVideos(
                query, page + 1, limit, getAuthorizationParams(NhacCuaTuiService.ROUTE_GET_SEARCH_VIDEO)).execute();

        if (!checkSuccess(response)) return null;

        if (response.body().video == null) {
            Log.w(NhacCuaTuiApi.class.getSimpleName(), "Video returned null");
            return null;
        }
        List<Video> videos = new ArrayList<>();
        for (com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiVideoModel video : response.body().video.video) {
            videos.add(NhacCuaTuiConverter.convert(video));
        }
        return videos;
    }
}
