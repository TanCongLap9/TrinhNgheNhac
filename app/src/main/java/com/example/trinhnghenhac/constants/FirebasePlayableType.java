package com.example.trinhnghenhac.constants;

import androidx.annotation.StringDef;

@StringDef({
        FirebasePlayableType.ITEM_TYPE_ARTIST,
        FirebasePlayableType.ITEM_TYPE_SONG,
        FirebasePlayableType.ITEM_TYPE_VIDEO,
        FirebasePlayableType.ITEM_TYPE_PLAYLIST
})
public @interface FirebasePlayableType {
    String ITEM_TYPE_ARTIST = "artist";
    String ITEM_TYPE_SONG = "song";
    String ITEM_TYPE_VIDEO = "video";
    String ITEM_TYPE_PLAYLIST = "playlist";
}
