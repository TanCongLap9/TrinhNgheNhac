package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

@IntDef({
        PlayableType.TYPE_SONG,
        PlayableType.TYPE_ARTIST,
        PlayableType.TYPE_PLAYLIST,
        PlayableType.TYPE_VIDEO
})
public @interface PlayableType {
    int TYPE_SONG = 0;
    int TYPE_ARTIST = 1;
    int TYPE_PLAYLIST = 2;
    int TYPE_VIDEO = 3;
}
