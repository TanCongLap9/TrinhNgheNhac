package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

@IntDef({
        MusicPlatform.PLATFORM_UNKNOWN,
        MusicPlatform.PLATFORM_ZINGMP3,
        MusicPlatform.PLATFORM_NHACCUATUI,
        MusicPlatform.PLATFORM_SOUNDCLOUD,
        MusicPlatform.PLATFORM_LOCAL
})
public @interface MusicPlatform {
    int PLATFORM_LOCAL = -2;
    int PLATFORM_UNKNOWN = -1;
    int PLATFORM_ZINGMP3 = 0;
    int PLATFORM_NHACCUATUI = 1;
    int PLATFORM_SOUNDCLOUD = 2;
}
