package com.example.trinhnghenhac.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.trinhnghenhac.constants.ArtistOtherFragmentTab;
import com.example.trinhnghenhac.ui.artist.ArtistOtherFragment;
import com.example.trinhnghenhac.ui.artist.ArtistOverviewFragment;

public class ArtistPagerAdapter extends FragmentStateAdapter {
    private final Bundle mBundle;
    public static final int PAGES_COUNT = 4;

    public ArtistPagerAdapter(FragmentActivity activity, Bundle bundle) {
        super(activity);
        mBundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ArtistOverviewFragment((Bundle) mBundle.clone());
            case 1:
                return new ArtistOtherFragment(ArtistOtherFragmentTab.TAB_SONGS, (Bundle) mBundle.clone());
            case 2:
                return new ArtistOtherFragment(ArtistOtherFragmentTab.TAB_PLAYLISTS, (Bundle) mBundle.clone());
            default:
                return new ArtistOtherFragment(ArtistOtherFragmentTab.TAB_VIDEOS, (Bundle) mBundle.clone());
        }
    }

    @Override
    public int getItemCount() {
        return PAGES_COUNT;
    }
}
