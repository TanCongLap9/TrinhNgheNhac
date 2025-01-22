package com.example.trinhnghenhac.api.nhaccuatui.models;

import java.util.List;

public class NhacCuaTuiSearchAllModel {
    public ArtistsModel artist;
    public PlaylistsModel playlist;
    public SongsModel song;
    public VideosModel video;

    public static class ArtistsModel {
        public List<NhacCuaTuiArtistModel> artist;
        public int total;
    }

    public static class PlaylistsModel {
        public List<NhacCuaTuiPlaylistModel> playlist;
        public int total;
    }

    public static class SongsModel {
        public List<NhacCuaTuiSongModel> song;
        public int total;
    }

    public static class VideosModel {
        public List<NhacCuaTuiVideoModel> video;
        public int total;
    }
}
