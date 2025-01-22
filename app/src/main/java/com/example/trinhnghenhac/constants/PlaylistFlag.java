package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

@IntDef({
        PlaylistFlag.FLAG_ALBUM,
        PlaylistFlag.FLAG_SINGLE,
        PlaylistFlag.FLAG_OFFICIAL,
        PlaylistFlag.FLAG_INDIE,
        PlaylistFlag.FLAG_PRIVATE
})
public @interface PlaylistFlag {
    int FLAG_ALBUM = 1;
    int FLAG_SINGLE = 2;
    int FLAG_OFFICIAL = 4;
    int FLAG_INDIE = 8;
    int FLAG_PRIVATE = 16;
}
