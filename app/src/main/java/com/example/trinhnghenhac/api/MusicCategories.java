package com.example.trinhnghenhac.api;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.MusicCategory;

public class MusicCategories {
    public static int getStringRes(@MusicCategory int category) {
        switch (category) {
            case MusicCategory.CATEGORY_ACOUSTIC:
                return R.string.music_category_acoustic;
            case MusicCategory.CATEGORY_BOLERO:
                return R.string.music_category_bolero;
            case MusicCategory.CATEGORY_CHILDREN:
                return R.string.music_category_children;
            case MusicCategory.CATEGORY_CHILL:
                return R.string.music_category_chill;
            case MusicCategory.CATEGORY_CHINESE:
                return R.string.music_category_chinese;
            case MusicCategory.CATEGORY_CLASSICAL:
                return R.string.music_category_classical;
            case MusicCategory.CATEGORY_COFFEE:
                return R.string.music_category_coffee;
            case MusicCategory.CATEGORY_EDM:
                return R.string.music_category_edm;
            case MusicCategory.CATEGORY_EUROPE:
                return R.string.music_category_europe;
            case MusicCategory.CATEGORY_FILM:
                return R.string.music_category_film;
            case MusicCategory.CATEGORY_HIPHOP:
                return R.string.music_category_hiphop;
            case MusicCategory.CATEGORY_INSTRUMENTAL:
                return R.string.music_category_instrumental;
            case MusicCategory.CATEGORY_JAZZ:
                return R.string.music_category_jazz;
            case MusicCategory.CATEGORY_KOREAN:
                return R.string.music_category_korean;
            case MusicCategory.CATEGORY_NEW_RELEASES:
                return R.string.home_newest;
            case MusicCategory.CATEGORY_SAD:
                return R.string.music_category_sad;
            case MusicCategory.CATEGORY_VIETNAMESE:
                return R.string.music_category_vietnamese;
            case MusicCategory.CATEGORY_WORKOUT:
                return R.string.music_category_workout;
            default:
                throw new IllegalArgumentException("Unknown category");
        }
    }

    public static int getDrawableRes(@MusicCategory int category) {
        switch (category) {
            case MusicCategory.CATEGORY_ACOUSTIC:
                return R.drawable.music_category_acoustic;
            case MusicCategory.CATEGORY_BOLERO:
                return R.drawable.music_category_bolero;
            case MusicCategory.CATEGORY_CHILDREN:
                return R.drawable.music_category_children;
            case MusicCategory.CATEGORY_CHILL:
                return R.drawable.music_category_chill;
            case MusicCategory.CATEGORY_CHINESE:
                return R.drawable.music_category_chinese;
            case MusicCategory.CATEGORY_CLASSICAL:
                return R.drawable.music_category_classical;
            case MusicCategory.CATEGORY_COFFEE:
                return R.drawable.music_category_coffee;
            case MusicCategory.CATEGORY_EDM:
                return R.drawable.music_category_edm;
            case MusicCategory.CATEGORY_EUROPE:
                return R.drawable.music_category_europe;
            case MusicCategory.CATEGORY_FILM:
                return R.drawable.music_category_film;
            case MusicCategory.CATEGORY_HIPHOP:
                return R.drawable.music_category_hiphop;
            case MusicCategory.CATEGORY_INSTRUMENTAL:
                return R.drawable.music_category_instrumental;
            case MusicCategory.CATEGORY_JAZZ:
                return R.drawable.music_category_jazz;
            case MusicCategory.CATEGORY_KOREAN:
                return R.drawable.music_category_korean;
            case MusicCategory.CATEGORY_NEW_RELEASES:
                return R.drawable.music_category_new_releases;
            case MusicCategory.CATEGORY_SAD:
                return R.drawable.music_category_sad;
            case MusicCategory.CATEGORY_VIETNAMESE:
                return R.drawable.music_category_vietnamese;
            case MusicCategory.CATEGORY_WORKOUT:
                return R.drawable.music_category_workout;
            default:
                throw new IllegalArgumentException("Unknown category");
        }
    }
}
