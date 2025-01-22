package com.example.trinhnghenhac.api.zingmp3;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3ArtistModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3LyricsModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3PlaylistModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3SearchMultiResultModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3SectionItemModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3SectionModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3SongModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3StreamingModel;
import com.example.trinhnghenhac.api.zingmp3.models.ZingMp3VideoModel;
import com.example.trinhnghenhac.constants.PlaylistFlag;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Lyrics;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.readers.LrcReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities to convert ZingMp3 models into standard models
 */
public class ZingMp3Converter {
    public static Lyrics convert(ZingMp3LyricsModel model) {
        if (model.sentences != null) {
            List<Lyrics.Sentence> convertedSentences = new ArrayList<>();
            for (ZingMp3LyricsModel.SentenceModel sentence : model.sentences) {
                List<Lyrics.Word> convertedWords = new ArrayList<>();
                for (ZingMp3LyricsModel.WordModel word : sentence.words) {
                    Lyrics.Word convertedWord = new Lyrics.Word(word.data, word.startTime, word.endTime);
                    convertedWords.add(convertedWord);
                }
                Lyrics.Sentence convertedSentence = new Lyrics.Sentence(convertedWords);
                convertedSentences.add(convertedSentence);
            }
            return new Lyrics(convertedSentences);
        } else if (model.file != null) {
            return new LrcReader(model.file).read();
        } else return null;
    }

    public static SearchResults convert(ZingMp3SearchMultiResultModel model) {
        List<Artist> artists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        List<Video> videos = new ArrayList<>();
        List<Playlist> playlists = new ArrayList<>();
        if (model.artists != null)
            for (ZingMp3ArtistModel artist : model.artists)
                artists.add(convert(artist));
        if (model.playlists != null)
            for (ZingMp3PlaylistModel playlist : model.playlists)
                playlists.add(convert(playlist));
        if (model.songs != null)
            for (ZingMp3SongModel song : model.songs)
                songs.add(convert(song));
        if (model.videos != null)
            for (ZingMp3VideoModel video : model.videos)
                videos.add(convert(video));
        return new SearchResults(artists, songs, playlists, videos, new int[]{0, 1, 2, 3});
    }

    public static Video convert(ZingMp3VideoModel model) {
        List<Artist> artists = new ArrayList<>();
        if (model.artists != null)
            for (ZingMp3ArtistModel artist : model.artists)
                artists.add(convert(artist));
        Video.Builder builder = new Video.Builder()
                .setId(model.encodeId)
                .setName(model.title)
                .setImage(model.thumbnail)
                .setArtists(artists)
                .setHearts(model.like)
                .setPlatform(MusicPlatform.PLATFORM_ZINGMP3);
        if (model.streaming != null && model.streaming.hls != null) {
            if (model.streaming.hls.$1080p != null)
                builder.setStreamUrl(model.streaming.hls.$1080p);
            else if (model.streaming.hls.$720p != null)
                builder.setStreamUrl(model.streaming.hls.$720p);
            else if (model.streaming.hls.$360p != null)
                builder.setStreamUrl(model.streaming.hls.$360p);
            else if (model.streaming.hls.$0p != null)
                builder.setStreamUrl(model.streaming.hls.$0p);
        }
        return builder.build();
    }

    public static Artist convert(ZingMp3ArtistModel model) {
        List<Playlist> playlists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        List<Video> videos = new ArrayList<>();
        if (model.sections != null)
            for (ZingMp3SectionModel section : model.sections) {
                if (section.sectionType != null)
                    if (section.sectionType.equals("video") && section.items != null)
                        for (ZingMp3SectionItemModel video : section.items)
                            videos.add(convert((ZingMp3VideoModel) video));
                    else if (section.sectionType.equals("song") && section.items != null)
                        for (ZingMp3SectionItemModel song : section.items)
                            songs.add(convert((ZingMp3SongModel) song));
                    else if (section.sectionType.equals("playlist") && section.items != null)
                        for (ZingMp3SectionItemModel playlist : section.items)
                            playlists.add(convert((ZingMp3PlaylistModel) playlist));
            }
        return new Artist.Builder()
                .setId(model.alias)
                .setName(model.name)
                .setImage(model.thumbnail)
                .setPlaylists(playlists)
                .setSongs(songs)
                .setVideos(videos)
                .setPlatform(MusicPlatform.PLATFORM_ZINGMP3)
                .build();
    }

    public static Song convert(ZingMp3SongModel model, ZingMp3StreamingModel model2) {
        List<Artist> artists = new ArrayList<>();
        if (model.artists != null)
            for (ZingMp3ArtistModel artist : model.artists)
                artists.add(convert(artist));
        return new Song.Builder()
                .setId(model.encodeId)
                .setName(model.title)
                .setImage(model.thumbnail)
                .setArtists(artists)
                .setHearts(model.like)
                .setPlatform(MusicPlatform.PLATFORM_ZINGMP3)
                .setPremium(model.previewInfo != null)
                .build();
    }

    public static Song convert(ZingMp3SongModel model) {
        List<Artist> artists = new ArrayList<>();
        if (model.artists != null)
            for (ZingMp3ArtistModel artist : model.artists)
                artists.add(convert(artist));
        return new Song.Builder()
                .setId(model.encodeId)
                .setName(model.title)
                .setImage(model.thumbnail)
                .setDuration(model.duration * 1000L)
                .setArtists(artists)
                .setHearts(model.like)
                .setPremium(model.previewInfo != null)
                .setPlatform(MusicPlatform.PLATFORM_ZINGMP3)
                .build();
    }

    public static Playlist convert(ZingMp3PlaylistModel model) {
        List<Artist> artists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        List<Video> videos = new ArrayList<>();
        if (model.artists != null)
            for (ZingMp3ArtistModel artist : model.artists)
                artists.add(convert(artist));
        if (model.song != null && model.song.items != null)
            for (ZingMp3SongModel song : model.song.items)
                songs.add(convert(song));
        // TODO: Do playlists contain videos?
        return new Playlist.Builder()
                .setId(model.encodeId)
                .setName(model.title)
                .setArtists(artists)
                .setSongs(songs)
                .setVideos(videos)
                .setImage(model.thumbnail)
                .setFlags((byte)((model.isAlbum ? PlaylistFlag.FLAG_ALBUM : 0) +
                        (model.isSingle ? PlaylistFlag.FLAG_SINGLE : 0) +
                        (model.isoffical ? PlaylistFlag.FLAG_OFFICIAL : 0) +
                        (model.isIndie ? PlaylistFlag.FLAG_INDIE : 0) +
                        (model.isPrivate ? PlaylistFlag.FLAG_PRIVATE : 0)))
                .setPlatform(MusicPlatform.PLATFORM_ZINGMP3)
                .build();
    }
}
