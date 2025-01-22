package com.example.trinhnghenhac.api;

import android.database.Cursor;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.api.nhaccuatui.NhacCuaTuiApi;
import com.example.trinhnghenhac.api.soundcloud.SoundCloudApi;
import com.example.trinhnghenhac.api.zingmp3.ZingMp3Api;
import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.models.*;

import java.io.IOException;
import java.util.List;

public interface MusicPlatformApi {
    String SEARCH_COLUMN_NAME = "keyword";

    static MusicPlatformApi getApi(@MusicPlatform int platform) {
        switch (platform) {
            case MusicPlatform.PLATFORM_ZINGMP3:
                return ZingMp3Api.getInstance();
            case MusicPlatform.PLATFORM_SOUNDCLOUD:
                return SoundCloudApi.getInstance();
            case MusicPlatform.PLATFORM_NHACCUATUI:
                return NhacCuaTuiApi.getInstance();
            default:
                throw new IllegalArgumentException("Unknown platform");
        }
    }

    static MusicPlatformApi[] getAll() {
        return new MusicPlatformApi[]{
                ZingMp3Api.getInstance(),
                NhacCuaTuiApi.getInstance(),
                SoundCloudApi.getInstance()};
    }

    @DrawableRes
    static int toResId(@MusicPlatform int platform) {
        switch (platform) {
            case MusicPlatform.PLATFORM_ZINGMP3:
                return R.drawable.logo_zingmp3;
            case MusicPlatform.PLATFORM_NHACCUATUI:
                return R.drawable.logo_nhaccuatui;
            case MusicPlatform.PLATFORM_SOUNDCLOUD:
                return R.drawable.logo_soundcloud;
            case MusicPlatform.PLATFORM_LOCAL:
                return R.drawable.round_folder_shared_24;
            default:
                return R.drawable.round_question_mark_24;
        }
    }

    /**
     * Gets some info of a song
     * @param songId Song ID
     */
    @Nullable
    Song getSongInfo(CharSequence songId) throws IOException;

    /**
     * Gets media URL of a song
     * @param songId Song ID
     */
    @Nullable
    String getMediaUrl(CharSequence songId) throws IOException;

    /**
     * Gets some song ids that matches the search
     * @param query Search query
     */
    @Nullable
    Cursor getSearchHints(CharSequence query) throws IOException;

    /**
     * Gets search results
     * @param query Search query
     */
    @Nullable
    SearchResults getSearchResults(CharSequence query) throws IOException;

    /**
     * Gets songs search results
     * @param query Search query
     */
    @Nullable
    List<Song> getSearchSongs(CharSequence query, int page, int limit) throws IOException;

    /**
     * Gets artists search results
     * @param query Search query
     */
    @Nullable
    List<Artist> getSearchArtists(CharSequence query, int page, int limit) throws IOException;

    /**
     * Gets playlists search results
     * @param query Search query
     */
    @Nullable
    List<Playlist> getSearchPlaylists(CharSequence query, int page, int limit) throws IOException;

    /**
     * Gets videos search results
     * @param query Search query
     */
    @Nullable
    List<Video> getSearchVideos(CharSequence query, int page, int limit) throws IOException;

    /**
     * Gets song lyric
     */
    @Nullable
    Lyrics getLyrics(CharSequence songId) throws IOException;

    /**
     * Gets playlist info
     */
    @Nullable
    Playlist getPlaylistInfo(CharSequence playlistId) throws IOException;

    /**
     * Gets artist info
     */
    @Nullable
    Artist getArtistInfo(CharSequence artistId) throws IOException;

    /**
     * Gets video info
     */
    @Nullable
    Video getVideoInfo(CharSequence videoId) throws IOException;

    /**
     * Gets playables by category
     */
    @Nullable
    List<Playable> getPlayablesByCategory(@MusicCategory int category) throws IOException;
}
