package com.example.trinhnghenhac.ui.player;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.adapters.LyricsAdapter;
import com.example.trinhnghenhac.constants.LyricsType;
import com.example.trinhnghenhac.models.Lyrics;
import com.example.trinhnghenhac.utils.ListIteratorUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

public class LyricsScroller {
    @NonNull
    private final LyricsAdapter mAdapter;
    @NonNull
    private final Lyrics mLyrics;
    private ListIterator<Lyrics.Sentence> mSentenceIter;
    private ListIterator<Lyrics.Word> mWordIter;
    private Lyrics.Sentence mCurrentSentence;
    private Lyrics.Word mCurrentWord;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private long mStartAt;
    private long mStopAt;
    private boolean running;

    private final Runnable mWordStartAction = new Runnable() {
        @Override
        public void run() {
            mAdapter.setCurrentWordIndex(mWordIter.previousIndex());
            nextWord();
        }
    };

    private final Runnable mSentenceStartAction = new Runnable() {
        @Override
        public void run() {
            mAdapter.setCurrentSentenceIndex(mSentenceIter.previousIndex());
            mAdapter.scrollToCenter(mSentenceIter.previousIndex());
            if (mLyrics.getType() == LyricsType.LYRIC_TIMESTAMP_WORDS) {
                mAdapter.setCurrentWordIndex(LyricsAdapter.NO_HIGHLIGHT);
                mWordIter = mCurrentSentence.getWords().listIterator();
                nextWord();
            }
            nextSentence();
        }
    };

    private final Runnable mSentenceEndAction = new Runnable() {
        @Override
        public void run() {
            mAdapter.setCurrentSentenceIndex(LyricsAdapter.NO_HIGHLIGHT);
            mAdapter.setCurrentWordIndex(LyricsAdapter.NO_HIGHLIGHT);
        }
    };

    private final Runnable mSentenceStopAction = new Runnable() {
        @Override
        public void run() {
            mAdapter.setCurrentSentenceIndex(LyricsAdapter.NO_HIGHLIGHT);
            mAdapter.setCurrentWordIndex(LyricsAdapter.NO_HIGHLIGHT);
            stop();
        }
    };

    public LyricsScroller(@NonNull LyricsAdapter adapter, @NonNull Lyrics lyrics) {
        mAdapter = adapter;
        mLyrics = lyrics;
    }

    public void start() {
        start(mStartAt == 0 ? 0 : getTime());
    }

    public void start(int ms) {
        if (running) stop();
        running = true;
        mStartAt = System.currentTimeMillis() - ms;
        if (mSentenceIter == null) {
            switch (mLyrics.getType()) {
                case LyricsType.LYRIC_TIMESTAMP_WORDS:
                case LyricsType.LYRIC_TYPE_SENTENCES:
                    mSentenceIter = mLyrics.getSentences().listIterator();
                    nextSentence();
                    break;
                case LyricsType.LYRIC_TYPE_TEXT:
                    break;
            }
        } else {
            switch (mLyrics.getType()) {
                case LyricsType.LYRIC_TIMESTAMP_WORDS:
                case LyricsType.LYRIC_TYPE_SENTENCES:
                    int index = Collections.binarySearch(mLyrics.getSentences(), new Lyrics.Sentence(null, ms, 0), new Comparator<Lyrics.Sentence>() {
                        @Override
                        public int compare(Lyrics.Sentence sentence, Lyrics.Sentence newSentence) {
                            if (newSentence.getStartTime() < sentence.getStartTime()) return 1;
                            else if (newSentence.getStartTime() > sentence.getEndTime()) return -1;
                            else return 0;
                        }
                    });
                    if (index < 0) {
                        Log.d("Not Found", "at: " + index);
                        mSentenceIter = mLyrics.getSentences().listIterator();
                        nextSentence();
                    } else {
                        Log.d("Found", "at: " + index + ": " + mLyrics.getSentences().get(index));
                        mSentenceIter = mLyrics.getSentences().listIterator(index);
                        nextSentence();
                    }
                    break;
                case LyricsType.LYRIC_TYPE_TEXT:
                    break;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        if (!running) return;
        running = false;
        mStopAt = System.currentTimeMillis();
        mHandler.removeCallbacks(mSentenceEndAction);
        mHandler.removeCallbacks(mSentenceStartAction);
        mHandler.removeCallbacks(mWordStartAction);
    }

    private void nextWord() {
        if (!running || !mWordIter.hasNext()) return;
        mCurrentWord = mWordIter.next();
        long startTimeToPost = mCurrentWord.getStartTime() - getTime();
        Log.d("Sentence.Word", "startTimeToPost: " + startTimeToPost);
        if (startTimeToPost <= 0) mWordStartAction.run();
        else mHandler.postDelayed(mWordStartAction, startTimeToPost);
    }

    private void nextSentence() {
        if (!running || !mSentenceIter.hasNext()) return;
        mCurrentSentence = mSentenceIter.next();
        long startTimeToPost = mCurrentSentence.getStartTime() - getTime();
        Log.d("Sentence", "startTime: " + mCurrentSentence.getStartTime() + ", current: " + getTime());
        if (startTimeToPost <= 0) mSentenceStartAction.run();
        else mHandler.postDelayed(mSentenceStartAction, startTimeToPost);

        Lyrics.Sentence nextSentence = ListIteratorUtils.peekNext(mSentenceIter);
        if (nextSentence == null) {
            long endTimeToPost = mCurrentSentence.getEndTime() - getTime();
            Log.d("Sentence", "stopTime: " + mCurrentSentence.getEndTime() + ", current: " + getTime());
            if (endTimeToPost <= 0) mSentenceStopAction.run();
            else mHandler.postDelayed(mSentenceStopAction, endTimeToPost);
        }
        else if (nextSentence.getStartTime() != mCurrentSentence.getEndTime()) {
            long endTimeToPost = mCurrentSentence.getEndTime() - getTime();
            Log.d("Sentence", "endTime: " + mCurrentSentence.getEndTime() + ", current: " + getTime());
            if (endTimeToPost <= 0) mSentenceEndAction.run();
            else mHandler.postDelayed(mSentenceEndAction, endTimeToPost);
        }
    }

    public int getTime() {
        if (!running) return (int) (System.currentTimeMillis() - mStopAt);
        else return (int) (System.currentTimeMillis() - mStartAt);
    }
}
