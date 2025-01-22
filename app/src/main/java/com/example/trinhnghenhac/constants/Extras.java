package com.example.trinhnghenhac.constants;

import androidx.annotation.StringDef;

@StringDef({
        Extras.EXTRA_PLATFORM,
        Extras.EXTRA_SONG_INDEX,
        Extras.EXTRA_SONGID,
        Extras.EXTRA_ARTISTID,
        Extras.EXTRA_PLAYLISTID,
        Extras.EXTRA_ARTIST_OBSERVABLE,
        Extras.EXTRA_SEARCH_OBSERVABLE,
        Extras.EXTRA_TAB_OBSERVABLE,
        Extras.EXTRA_TAB,
        Extras.EXTRA_CATEGORY,
        Extras.DATA_CATEGORY,
        Extras.DATA_EXPLORE,
        Extras.EXTRA_USER,
        Extras.DATA_PLAYLIST,
        Extras.EXTRA_QUERY,
        Extras.DATA_EMAIL,
        Extras.DATA_PASSWORD,
        Extras.DATA_NAME,
        Extras.DATA_BIRTH,
        Extras.DATA_PHONE,
        Extras.DATA_CONFIRM_PASSWORD,
        Extras.DATA_I_AGREE,
        Extras.DATA_NAME_ERROR,
        Extras.DATA_EMAIL_ERROR,
        Extras.DATA_BIRTH_ERROR,
        Extras.DATA_PHONE_ERROR,
        Extras.DATA_PASSWORD_ERROR,
        Extras.DATA_CONFIRM_PASSWORD_ERROR,
        Extras.DATA_I_AGREE_ERROR,
        Extras.EXTRA_PLAYABLE
})
public @interface Extras {
    String EXTRA_PLATFORM = "EXTRA_PLATFORM";
    String EXTRA_SONG_INDEX = "EXTRA_SONG_INDEX";
    String EXTRA_SONGID = "EXTRA_SONGID";
    String EXTRA_ARTISTID = "EXTRA_ARTISTID";
    String EXTRA_PLAYLISTID = "EXTRA_PLAYLISTID";
    String EXTRA_ARTIST_OBSERVABLE = "EXTRA_ARTIST_OBSERVABLE"; // Allows fragments to wait for the artist data to load from the parent fragment
    String EXTRA_SEARCH_OBSERVABLE = "EXTRA_SEARCH_OBSERVABLE"; // Allows fragments to wait for the search results data to load from the parent fragment
    String EXTRA_TAB_OBSERVABLE = "EXTRA_TAB_OBSERVABLE"; // Allows fragments to select tab in activity
    String EXTRA_TAB = "EXTRA_TAB";
    String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    String EXTRA_USER = "EXTRA_USER";
    String EXTRA_QUERY = "EXTRA_QUERY";
    String DATA_CATEGORY = "DATA_CATEGORY";
    String DATA_EXPLORE = "DATA_EXPLORE";
    String DATA_PLAYLIST = "DATA_PLAYLIST";
    String DATA_EMAIL = "DATA_EMAIL";
    String DATA_PASSWORD = "DATA_PASSWORD";
    String DATA_NAME = "DATA_NAME";
    String DATA_BIRTH = "DATA_BIRTH";
    String DATA_PHONE = "DATA_PHONE";
    String DATA_CONFIRM_PASSWORD = "DATA_CONFIRM_PASSWORD";
    String DATA_I_AGREE = "DATA_I_AGREE";
    String DATA_NAME_ERROR = "EXTRA_NAME_ERROR";
    String DATA_EMAIL_ERROR = "EXTRA_EMAIL_ERROR";
    String DATA_BIRTH_ERROR = "EXTRA_BIRTH_ERROR";
    String DATA_PHONE_ERROR = "EXTRA_PHONE_ERROR";
    String DATA_PASSWORD_ERROR = "EXTRA_PASSWORD_ERROR";
    String DATA_CONFIRM_PASSWORD_ERROR = "EXTRA_CONFIRM_PASSWORD_ERROR";
    String DATA_I_AGREE_ERROR = "EXTRA_I_AGREE_ERROR";
    String EXTRA_PLAYABLE = "EXTRA_PLAYABLE";
}
