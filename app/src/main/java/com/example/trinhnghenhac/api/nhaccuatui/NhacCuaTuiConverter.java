package com.example.trinhnghenhac.api.nhaccuatui;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiArtistModel;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiLyricsModel;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiPlaylistModel;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiSearchAllModel;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiSongModel;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiVideoModel;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Lyrics;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities to convert NhacCuaTui models into standard models
 */
public class NhacCuaTuiConverter {
    public static Song convert(NhacCuaTuiSongModel model) {
        List<Artist> artists = new ArrayList<>();
        if (model.artists != null)
            for (NhacCuaTuiArtistModel artist : model.artists)
                artists.add(convert(artist));

        Pattern pat = Pattern.compile("(\\d+):(\\d+)");
        long duration;
        Matcher matcher = pat.matcher(model.duration);
        try {
            if (matcher.matches()) {
                int minute = Integer.parseInt(matcher.group(1));
                int second = Integer.parseInt(matcher.group(2));
                duration = minute * 60L + second;
            }
            else duration = 0;
        }
        catch (NumberFormatException e) {
            duration = 0;
        }

        return new Song.Builder()
                .setId(model.key)
                .setName(model.title)
                .setDuration(duration)
                .setImage(model.thumbnail)
                .setArtists(artists)
                .setPlatform(MusicPlatform.PLATFORM_NHACCUATUI)
                .setPremium(model.statusViewValue == 2)
                .build();
    }

    public static Lyrics convert(NhacCuaTuiLyricsModel model) {
        return model.lyric != null ? new Lyrics(String.join("\n", model.lyric.split("<br\\s*/>"))) : null;
    }

    public static Artist convert(com.example.trinhnghenhac.api.nhaccuatui.querymodels.NhacCuaTuiArtistModel model) { // Artist detail
        List<Video> videos = new ArrayList<>();
        List<Playlist> playlists = new ArrayList<>();
        if (model.playlist != null && model.playlist.playlist != null)
            for (NhacCuaTuiPlaylistModel playlist : model.playlist.playlist)
                playlists.add(convert(playlist));
        if (model.video != null && model.video.video != null)
            for (NhacCuaTuiVideoModel video : model.video.video)
                videos.add(convert(video));
        return new Artist.Builder()
                .setId(Objects.toString(model.artist.shortLink, ""))
                .setName(model.artist.name)
                .setImage(model.artist.coverImageURL)
                .setPlaylists(playlists)
                .setVideos(videos)
                .setPlatform(MusicPlatform.PLATFORM_NHACCUATUI)
                .build();
    }

    public static Artist convert(NhacCuaTuiArtistModel model) {
        return new Artist.Builder()
                .setId(Objects.toString(model.shortLink, ""))
                .setName(model.name)
                .setImage(model.coverImageURL)
                .setVideos(new ArrayList<>())
                .setPlaylists(new ArrayList<>())
                .setPlatform(MusicPlatform.PLATFORM_NHACCUATUI)
                .build();
    }

    public static Video convert(NhacCuaTuiVideoModel model) {
        List<Artist> artists = new ArrayList<>();
        if (model.artists != null)
            for (NhacCuaTuiArtistModel artist : model.artists)
                artists.add(convert(artist));

        return new Video.Builder()
                .setId(model.key)
                .setName(model.title)
                .setImage(model.thumbnail)
                .setArtists(artists)
                .setPlatform(MusicPlatform.PLATFORM_NHACCUATUI)
                .setPremium(model.statusViewValue == 2)
                .build();
    }

    public static SearchResults convert(NhacCuaTuiSearchAllModel model) {
        List<Artist> artists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        List<Video> videos = new ArrayList<>();
        List<Playlist> playlists = new ArrayList<>();
        if (model.artist != null && model.artist.artist != null)
            for (NhacCuaTuiArtistModel artist : model.artist.artist)
                artists.add(convert(artist));
        if (model.playlist != null && model.playlist.playlist != null)
            for (NhacCuaTuiPlaylistModel playlist : model.playlist.playlist)
                playlists.add(convert(playlist));
        if (model.song != null && model.song.song != null)
            for (NhacCuaTuiSongModel song : model.song.song)
                songs.add(convert(song));
        if (model.video != null && model.video.video != null)
            for (NhacCuaTuiVideoModel video : model.video.video)
                videos.add(convert(video));
        return new SearchResults(artists, songs, playlists, videos, new int[]{0, 1, 2, 3});
    }

    public static Playlist convert(NhacCuaTuiPlaylistModel model) {
        List<Artist> artists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        if (model.artists != null)
            for (NhacCuaTuiArtistModel artist : model.artists)
                artists.add(convert(artist));
        if (model.songs != null)
            for (NhacCuaTuiSongModel song : model.songs)
                songs.add(convert(song));
        return new Playlist.Builder()
                .setId(Objects.toString(model.key, ""))
                .setName(model.title)
                .setArtists(artists)
                .setSongs(songs)
                .setVideos(new ArrayList<>())
                .setImage(model.thumbnail)
                .setFlags((byte)0)
                .setPlatform(MusicPlatform.PLATFORM_NHACCUATUI)
                .build();
    }
}
