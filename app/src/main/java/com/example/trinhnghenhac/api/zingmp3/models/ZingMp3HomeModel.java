package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import com.example.trinhnghenhac.models.Playlist;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

public class ZingMp3HomeModel implements ZingMp3DataModel, Serializable {
    @Nullable
    public List<ItemModel> items;
    public static class ItemModel {
        @Nullable
        public JsonObject sectionObject;
    }

    public static class PopularSongsModel implements Serializable {
        @Nullable
        public String title; // Nhạc hot thịnh hành
        @Nullable
        public String sectionType; // playlist
        @Nullable
        public String sectionId; // hEditorTheme1
        @Nullable
        public String itemType; // description
        @Nullable
        public List<PopularSongsPlaylistModel> items;
    }

    public static class PopularSongsPlaylistModel implements Serializable {
        @Nullable
        public String encodeId;
        @Nullable
        public String title;
        @Nullable
        public String artistsNames;
        @Nullable
        public List<ZingMp3ArtistModel> artists;
        @Nullable
        public String thumbnail;
    }

    public static class HotAlbumsModel implements Serializable {
        @Nullable
        public String title; // Album Hot
        @Nullable
        public String sectionType; // playlist
        @Nullable
        public String sectionId; // hAlbum
        @Nullable
        public List<Playlist> items;
    }

    public static class NewReleasesModel implements Serializable {
        @Nullable
        public String title; // Mới phát hành
        @Nullable
        public String sectionType; // new-release
        @Nullable
        public NewReleasesTabsModel items;
    }

    public static class NewReleasesTabsModel implements Serializable {
        @Nullable
        public List<ZingMp3SongModel> all;
        @Nullable
        public List<ZingMp3SongModel> others;
        @Nullable
        public List<ZingMp3SongModel> vPop;
    }

    public static class TinhCaNgayDongModel implements Serializable {
        @Nullable
        public String title; // Tình ca ngày đông
        @Nullable
        public String sectionId; // hSeasonTheme
        @Nullable
        public String sectionType; // playlist
        @Nullable
        public String itemType; // description
        @Nullable
        public List<ZingMp3PlaylistModel> items;
    }
}
