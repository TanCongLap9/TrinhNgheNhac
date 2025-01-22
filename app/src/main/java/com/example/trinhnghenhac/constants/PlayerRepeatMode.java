package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

@IntDef({
        PlayerRepeatMode.REPEAT_MODE_OFF,
        PlayerRepeatMode.REPEAT_MODE_ALL,
        PlayerRepeatMode.REPEAT_MODE_ONE,
        PlayerRepeatMode.REPEAT_MODE_SHUFFLE
})
public @interface PlayerRepeatMode {
    int REPEAT_MODE_OFF = 0;
    int REPEAT_MODE_ALL = 1;
    int REPEAT_MODE_ONE = 2;
    int REPEAT_MODE_SHUFFLE = 3;
}
