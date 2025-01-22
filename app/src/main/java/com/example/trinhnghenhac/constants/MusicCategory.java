package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;
@IntDef({
        MusicCategory.CATEGORY_UNKNOWN,
        MusicCategory.CATEGORY_CHILL,
        MusicCategory.CATEGORY_JAZZ,
        MusicCategory.CATEGORY_VIETNAMESE,
        MusicCategory.CATEGORY_EUROPE,
        MusicCategory.CATEGORY_CHINESE,
        MusicCategory.CATEGORY_KOREAN,
        MusicCategory.CATEGORY_ACOUSTIC,
        MusicCategory.CATEGORY_WORKOUT,
        MusicCategory.CATEGORY_COFFEE,
        MusicCategory.CATEGORY_SAD,
        MusicCategory.CATEGORY_INSTRUMENTAL,
        MusicCategory.CATEGORY_CLASSICAL,
        MusicCategory.CATEGORY_CHILDREN,
        MusicCategory.CATEGORY_FILM,
        MusicCategory.CATEGORY_HIPHOP,
        MusicCategory.CATEGORY_EDM,
        MusicCategory.CATEGORY_BOLERO,
        MusicCategory.CATEGORY_NEW_RELEASES
})
public @interface MusicCategory {
    int CATEGORY_UNKNOWN = -1;
    int CATEGORY_CHILL = 0;
    int CATEGORY_JAZZ = 1;
    int CATEGORY_VIETNAMESE = 2;
    int CATEGORY_EUROPE = 3;
    int CATEGORY_CHINESE = 4;
    int CATEGORY_KOREAN = 5;
    int CATEGORY_ACOUSTIC = 6;
    int CATEGORY_WORKOUT = 7;
    int CATEGORY_COFFEE = 8;
    int CATEGORY_SAD = 9;
    int CATEGORY_INSTRUMENTAL = 10;
    int CATEGORY_CLASSICAL = 11;
    int CATEGORY_CHILDREN = 12;
    int CATEGORY_FILM = 13;
    int CATEGORY_HIPHOP = 14;
    int CATEGORY_EDM = 15;
    int CATEGORY_BOLERO = 16;
    int CATEGORY_NEW_RELEASES = 17;
}