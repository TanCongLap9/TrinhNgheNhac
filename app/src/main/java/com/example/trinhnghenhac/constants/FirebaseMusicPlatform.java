package com.example.trinhnghenhac.constants;

import androidx.annotation.StringDef;

@StringDef({
        FirebaseMusicPlatform.PLATFORM_ZINGMP3,
        FirebaseMusicPlatform.PLATFORM_NHACCUATUI,
        FirebaseMusicPlatform.PLATFORM_SOUNDCLOUD
})
public @interface FirebaseMusicPlatform {
    String PLATFORM_ZINGMP3 = "zingmp3";
    String PLATFORM_NHACCUATUI = "nhaccuatui";
    String PLATFORM_SOUNDCLOUD = "soundcloud";
}
