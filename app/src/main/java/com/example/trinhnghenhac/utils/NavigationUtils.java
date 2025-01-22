package com.example.trinhnghenhac.utils;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.ui.artist.ArtistFragment;
import com.example.trinhnghenhac.ui.category.CategoryFragment;
import com.example.trinhnghenhac.ui.playlist.PlaylistFragment;

public class NavigationUtils {
    public static void viewPlaylist(View view, Playlist playlist) {
        Bundle bundle = new Bundle();
        bundle.putString(Extras.EXTRA_PLAYLISTID, playlist.getId());
        bundle.putInt(Extras.EXTRA_PLATFORM, playlist.getPlatform());
        Navigation.findNavController(view).navigate(R.id.action_view_playlist, bundle);
    }

    public static void viewArtist(View view, Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putString(Extras.EXTRA_ARTISTID, artist.getId());
        bundle.putInt(Extras.EXTRA_PLATFORM, artist.getPlatform());
        Navigation.findNavController(view).navigate(R.id.action_view_artist, bundle);
    }

    public static void viewCategoryFromCategories(View view, @MusicCategory int category) {
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.EXTRA_CATEGORY, category);
        Navigation.findNavController(view).navigate(R.id.action_view_category_from_categories, bundle);
    }

    public static void viewCategoryFromExplore(View view, @MusicCategory int category) {
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.EXTRA_CATEGORY, category);
        Navigation.findNavController(view).navigate(R.id.action_view_category_from_explore, bundle);
    }
}
