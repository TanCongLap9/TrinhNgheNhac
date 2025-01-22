package com.example.trinhnghenhac.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.trinhnghenhac.ui.player.LyricsFragment;
import com.example.trinhnghenhac.ui.player.PlayerFragment;

public class PlayerPagerAdapter extends FragmentStateAdapter {
    public static final int PAGES_COUNT = 2;

    public PlayerPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new PlayerFragment() : new LyricsFragment();
    }

    @Override
    public int getItemCount() {
        return PAGES_COUNT;
    }
}
