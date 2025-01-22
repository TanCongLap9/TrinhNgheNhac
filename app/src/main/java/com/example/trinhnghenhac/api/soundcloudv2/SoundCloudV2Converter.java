package com.example.trinhnghenhac.api.soundcloudv2;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2Collection;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2CollectionItem;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2PlaylistModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2TrackModel;
import com.example.trinhnghenhac.api.soundcloudv2.models.SoundCloudV2UserModel;
import com.example.trinhnghenhac.constants.PlaylistFlag;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SoundCloudV2Converter {
    public static Song convert(SoundCloudV2TrackModel model) {
        List<Artist> users = new ArrayList<>();
        if (model.user != null)
            users.add(convert(model.user));
        return new Song.Builder()
                .setId(Integer.toString(model.id))
                .setName(model.title)
                .setImage(model.artwork_url)
                .setArtists(users)
                .setDuration(model.duration)
                .setHearts(model.likes_count)
                .setPlatform(MusicPlatform.PLATFORM_SOUNDCLOUD)
                .setPremium(/* TODO: Set premium */false)
                .build();
    }

    public static Playlist convert(SoundCloudV2PlaylistModel model) {
        ArrayList<Song> songs = new ArrayList<>();
        if (model.tracks != null)
            for (SoundCloudV2TrackModel track : model.tracks)
                songs.add(convert(track));
        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(convert(model.user));
        return new Playlist.Builder()
                .setId(Integer.toString(model.id))
                .setName(model.title)
                .setArtists(artists)
                .setSongs(songs)
                .setVideos(new ArrayList<>())
                .setImage(model.artwork_url)
                .setFlags(model.is_album ? PlaylistFlag.FLAG_ALBUM : 0)
                .setPlatform(MusicPlatform.PLATFORM_SOUNDCLOUD)
                .build();

    }

    public static Artist convert(SoundCloudV2UserModel model) {
        return new Artist.Builder()
                .setId(Integer.toString(model.id))
                .setName(model.full_name)
                .setImage(model.avatar_url)
                .setPlaylists(new ArrayList<>())
                .setVideos(new ArrayList<>())
                .setPlatform(MusicPlatform.PLATFORM_SOUNDCLOUD)
                .build();
    }

    public static SearchResults convert(SoundCloudV2Collection<SoundCloudV2CollectionItem> model) {
        List<Artist> artists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        List<Playlist> playlists = new ArrayList<>();
        if (model.collection != null)
            for (SoundCloudV2CollectionItem soundCloudV2CollectionItem : model.collection) {
                if (soundCloudV2CollectionItem instanceof SoundCloudV2TrackModel)
                    songs.add(convert((SoundCloudV2TrackModel)soundCloudV2CollectionItem));
                else if (soundCloudV2CollectionItem instanceof SoundCloudV2PlaylistModel)
                    playlists.add(convert((SoundCloudV2PlaylistModel)soundCloudV2CollectionItem));
                else if (soundCloudV2CollectionItem instanceof SoundCloudV2UserModel)
                    artists.add(convert((SoundCloudV2UserModel)soundCloudV2CollectionItem));
            }
        return new SearchResults(artists, songs, playlists, new ArrayList<>(), new int[] {0, 1, 2, 3});
    }
}
