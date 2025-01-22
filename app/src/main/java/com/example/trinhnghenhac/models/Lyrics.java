package com.example.trinhnghenhac.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.LyricsType;
import com.example.trinhnghenhac.utils.ListIteratorUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class Lyrics implements Serializable, Parcelable {
    public static final int END_TIME_ENDLESS = -1;
    @Nullable
    private List<Sentence> mSentences;
    @Nullable
    private String mText;

    public Lyrics(@Nullable List<Sentence> sentences) {
        this.mSentences = sentences;
    }

    public Lyrics(@Nullable String text) {
        this.mText = text;
    }

    private Lyrics(Parcel in) {
        switch (in.readInt()) {
            case LyricsType.LYRIC_TIMESTAMP_WORDS:
            case LyricsType.LYRIC_TYPE_SENTENCES:
                mSentences = in.createTypedArrayList(Sentence.CREATOR);
                break;
            default:
                mText = in.readString();
                break;
        }
    }

    public List<Sentence> getSentences() {
        return mSentences;
    }

    public String getLyricText() {
        switch (getType()) {
            case LyricsType.LYRIC_TIMESTAMP_WORDS:
                StringBuilder sb2 = new StringBuilder();
                if (getSentences() != null)
                    for (Sentence sentence : getSentences()) {
                        for (Word word : sentence.getWords()) {
                            sb2.append(word.getText());
                            sb2.append(" ");
                        }
                        sb2.append("\n");
                    }
                return sb2.toString();
            case LyricsType.LYRIC_TYPE_SENTENCES:
                StringBuilder sb = new StringBuilder();
                if (getSentences() != null)
                    for (Sentence sentence : getSentences()) {
                        sb.append(sentence.getText());
                        sb.append("\n");
                    }
                return sb.toString();
            case LyricsType.LYRIC_TYPE_TEXT:
                return mText;
        }
        return null;
    }

    public List<String> getLyricSentenceStrings() {
        List<String> sentences = new ArrayList<>();
        switch (getType()) {
            case LyricsType.LYRIC_TYPE_TEXT:
                if (mText != null)
                    for (String sentence : mText.split("\n"))
                        sentences.add(sentence);
                break;
            case LyricsType.LYRIC_TYPE_SENTENCES:
                if (getSentences() != null)
                    for (Sentence sentence : getSentences())
                        sentences.add(sentence.getText());
                break;
            case LyricsType.LYRIC_TIMESTAMP_WORDS:
                StringBuilder sb2 = new StringBuilder();
                if (getSentences() != null)
                    for (Sentence sentence : getSentences()) {
                        sb2.delete(0, sb2.length());
                        for (Word word : sentence.getWords()) {
                            sb2.append(word.getText());
                            sb2.append(" ");
                        }
                        sentences.add(sb2.toString());
                    }
                break;
        }
        return sentences;
    }

    @LyricsType
    public int getType() {
        return mSentences != null
                ? mSentences.size() != 0 && mSentences.get(0).getWords() != null
                ? LyricsType.LYRIC_TIMESTAMP_WORDS
                : LyricsType.LYRIC_TYPE_SENTENCES
                : LyricsType.LYRIC_TYPE_TEXT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        int lyricType = getType();
        parcel.writeInt(lyricType);
        switch (lyricType) {
            case LyricsType.LYRIC_TIMESTAMP_WORDS:
            case LyricsType.LYRIC_TYPE_SENTENCES:
                parcel.writeTypedList(mSentences);
                break;
            case LyricsType.LYRIC_TYPE_TEXT:
                parcel.writeString(mText);
                break;
        }
    }

    public static final Creator<Lyrics> CREATOR = new Creator<Lyrics>() {
        @Override
        public Lyrics createFromParcel(Parcel in) {
            return new Lyrics(in);
        }

        @Override
        public Lyrics[] newArray(int size) {
            return new Lyrics[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "Lyrics{" +
                "mSentences=" + mSentences +
                ", mText='" + mText + '\'' +
                '}';
    }

    public static class Sentence implements Serializable, Parcelable {
        @Nullable
        private List<Word> mWords;
        private String mText;
        private int mStartTime;
        private int mEndTime;

        public Sentence(@NonNull List<Word> words) {
            mWords = words;
        }

        /**
         * @param text
         * @param startTime in ms
         * @param endTime   in ms
         */
        public Sentence(String text, int startTime, int endTime) {
            mText = text;
            mStartTime = startTime;
            mEndTime = endTime;
        }

        private Sentence(Parcel in) {
            if (in.readInt() == LyricsType.LYRIC_TIMESTAMP_WORDS)
                mWords = in.createTypedArrayList(Word.CREATOR);
            else {
                mText = in.readString();
                mStartTime = in.readInt();
                mEndTime = in.readInt();
            }
        }

        @Nullable
        public List<Word> getWords() {
            return mWords;
        }

        public int getStartTime() {
            if (mWords != null && !mWords.isEmpty())
                return mWords.get(0).getStartTime();
            return mStartTime;
        }

        public int getEndTime() {
            if (mWords != null && !mWords.isEmpty())
                return mWords.get(mWords.size() - 1).getEndTime();
            return mEndTime;
        }

        @Nullable
        public String getText() {
            return mText;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (mWords != null) {
                parcel.writeInt(LyricsType.LYRIC_TIMESTAMP_WORDS);
                parcel.writeTypedList(mWords);
            } else {
                parcel.writeInt(LyricsType.LYRIC_TYPE_SENTENCES);
                parcel.writeString(mText);
                parcel.writeInt(mStartTime);
                parcel.writeInt(mEndTime);
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "Sentence{" +
                    "mWords=" + mWords +
                    ", mText='" + mText + '\'' +
                    ", mStartTime=" + mStartTime +
                    ", mEndTime=" + mEndTime +
                    '}';
        }

        public static final Creator<Sentence> CREATOR = new Creator<Sentence>() {
            @Override
            public Sentence createFromParcel(Parcel in) {
                return new Sentence(in);
            }

            @Override
            public Sentence[] newArray(int size) {
                return new Sentence[size];
            }
        };
    }

    public static class Word implements Serializable, Parcelable {
        @NonNull
        private final String mText;
        private final int mStartTime;
        private final int mEndTime;

        public Word(@NonNull String text, int startTime, int endTime) {
            mText = text;
            mStartTime = startTime;
            mEndTime = endTime;
        }

        private Word(Parcel in) {
            mText = Objects.requireNonNull(in.readString());
            mStartTime = in.readInt();
            mEndTime = in.readInt();
        }

        @NonNull
        public String getText() {
            return mText;
        }

        public int getStartTime() {
            return mStartTime;
        }

        public int getEndTime() {
            return mEndTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeString(mText);
            parcel.writeInt(mStartTime);
            parcel.writeInt(mEndTime);
        }

        @NonNull
        @Override
        public String toString() {
            return "Word{" +
                    "mText='" + mText + '\'' +
                    ", mStartTime=" + mStartTime +
                    ", mEndTime=" + mEndTime +
                    '}';
        }

        public static final Creator<Word> CREATOR = new Creator<Word>() {
            @Override
            public Word createFromParcel(Parcel in) {
                return new Word(in);
            }

            @Override
            public Word[] newArray(int size) {
                return new Word[size];
            }
        };
    }
}
