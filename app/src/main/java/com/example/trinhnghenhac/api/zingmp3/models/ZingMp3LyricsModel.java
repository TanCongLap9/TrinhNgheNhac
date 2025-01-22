package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class ZingMp3LyricsModel implements ZingMp3DataModel, Serializable {
    @Nullable
    public URL file;
    @Nullable
    public List<String> defaultIBGUrls;
    @Nullable
    public List<SentenceModel> sentences;

    public static class SentenceModel {
        public List<WordModel> words;
    }

    public static class WordModel {
        public String data;
        public int startTime;
        public int endTime;
    }
}
