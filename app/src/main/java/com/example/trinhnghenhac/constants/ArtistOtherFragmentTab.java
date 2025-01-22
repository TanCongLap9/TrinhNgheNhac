package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

@IntDef({
        ArtistOtherFragmentTab.TAB_SONGS,
        ArtistOtherFragmentTab.TAB_PLAYLISTS,
        ArtistOtherFragmentTab.TAB_VIDEOS
})
public @interface ArtistOtherFragmentTab {
    int TAB_SONGS = 1;
    int TAB_PLAYLISTS = 2;
    int TAB_VIDEOS = 3;
}
