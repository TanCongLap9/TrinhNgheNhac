package com.example.trinhnghenhac.api.soundcloud;

import android.database.Cursor;

import androidx.annotation.Nullable;

import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Lyrics;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Can't register app :(
 */
public class SoundCloudApi implements MusicPlatformApi {
    public static final String BASE_URL = "https://api.soundcloud.com";
    public static SoundCloudApi instance;
    private final SoundCloudService mService;

    public SoundCloudApi() {
        mService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoundCloudService.class);
    }

    public static SoundCloudApi getInstance() {
        if (instance == null) instance = new SoundCloudApi();
        return instance;
    }

    @Override
    public String getMediaUrl(CharSequence songId) {
        return null;
    }

    @Override
    public Song getSongInfo(CharSequence songId) {
        return null;
    }

    @Override
    public Cursor getSearchHints(CharSequence query) {
        return null;
    }

    @Override
    public SearchResults getSearchResults(CharSequence query) {
        return null;
    }

    @Override
    public Lyrics getLyrics(CharSequence songId) {
        return null;
    }

    @Override
    public Playlist getPlaylistInfo(CharSequence playlistId) {
        return null;
    }

    @Override
    public Artist getArtistInfo(CharSequence artistId) {
        return null;
    }

    @Override
    public Video getVideoInfo(CharSequence videoId) {
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
