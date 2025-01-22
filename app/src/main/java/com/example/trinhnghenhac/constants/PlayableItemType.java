package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

@IntDef({
        PlayableItemType.ITEM_TYPE_PLAYABLE,
        PlayableItemType.ITEM_TYPE_SHOW_ALL,
        PlayableItemType.ITEM_TYPE_LOADING
})
public @interface PlayableItemType {
    int ITEM_TYPE_PLAYABLE = 0;
    int ITEM_TYPE_SHOW_ALL = 1;
    int ITEM_TYPE_LOADING = 2;
}
