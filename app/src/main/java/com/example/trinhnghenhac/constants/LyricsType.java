package com.example.trinhnghenhac.constants;

import androidx.annotation.IntDef;

@IntDef({
        LyricsType.LYRIC_TIMESTAMP_WORDS,
        LyricsType.LYRIC_TYPE_SENTENCES,
        LyricsType.LYRIC_TYPE_TEXT
})
public @interface LyricsType {

    /**
     * Lyric with timestamp on every word
     */
    int LYRIC_TIMESTAMP_WORDS = 0;

    /**
     * Lyric with timestamp on every sentence
     */
    int LYRIC_TYPE_SENTENCES = 1;

    /**
     * Lyric without timestamp
     */
    int LYRIC_TYPE_TEXT = 2;
}
