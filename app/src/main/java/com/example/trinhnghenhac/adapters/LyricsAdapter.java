package com.example.trinhnghenhac.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.LyricsType;
import com.example.trinhnghenhac.models.Lyrics;
import com.example.trinhnghenhac.viewholders.LyricViewHolder;

import java.util.List;

public class LyricsAdapter extends RecyclerView.Adapter<LyricViewHolder> {
    public static final int NO_HIGHLIGHT = -1;
    public static final int HIGHLIGHT = 1;
    private final Context mContext;
    private final Lyrics mLyrics;
    private final List<String> mSentenceStrs;
    private int mCurrentSentenceIndex = NO_HIGHLIGHT;
    private int mCurrentWordIndex = NO_HIGHLIGHT;
    private final RecyclerView mRecView;

    public LyricsAdapter(RecyclerView recView, Lyrics model) {
        mContext = recView.getContext();
        mRecView = recView;
        mLyrics = model;
        mSentenceStrs = mLyrics.getLyricSentenceStrings();
    }

    @NonNull
    @Override
    public LyricViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(mContext).inflate(R.layout.listitem_lyric_sentence, parent, false);
        return new LyricViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricViewHolder holder, int position) {
        String currentSentenceStr = mSentenceStrs.get(position);
        holder.lyric.setText(currentSentenceStr);
        switch (mLyrics.getType()) {
            case LyricsType.LYRIC_TIMESTAMP_WORDS:
                if (getItemViewType(position) != HIGHLIGHT) break;
                Lyrics.Sentence currentSentence = mLyrics.getSentences().get(position);
                if (mCurrentWordIndex == NO_HIGHLIGHT) {
                    holder.lyric.setTextColor(mContext.getColor(R.color.red_a200));
                } else {
                    Pair<Integer, Integer> indexOfWordInSentence = getIndexOfWordInSentence(currentSentence.getWords(), mCurrentWordIndex, true);
                    SpannableString span = new SpannableString(currentSentenceStr);
                    span.setSpan(new ForegroundColorSpan(mContext.getColor(R.color.red_a200)), indexOfWordInSentence.first, indexOfWordInSentence.second, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holder.lyric.setText(span);
                }
                // else holder.lyric.setText(currentSentenceStr);
                break;
            case LyricsType.LYRIC_TYPE_SENTENCES:
                if (getItemViewType(position) != HIGHLIGHT) break;
                holder.lyric.setTextColor(mContext.getColor(R.color.red_a200));
                // else holder.lyric.setText(currentSentenceStr);
                break;
            case LyricsType.LYRIC_TYPE_TEXT:
                break;
        }
    }

    public void scrollToCenter(View view) {
        if (mRecView == null) return;
        int dy = (int)view.getY() - mRecView.getScrollY() + view.getHeight() / 2 - mRecView.getHeight() / 2;
        Log.d("scroll", "" + dy);
        mRecView.smoothScrollBy(0, dy);
    }

    public void scrollToCenter(int position) {
        RecyclerView.ViewHolder holder = mRecView.findViewHolderForLayoutPosition(position);
        if (holder != null) scrollToCenter(holder.itemView);
        else mRecView.smoothScrollToPosition(position);
    }

    private Pair<Integer, Integer> getIndexOfWordInSentence(List<Lyrics.Word> words, int wordIndex, boolean fromStart) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i <= wordIndex; i++) {
            if (i != 0) {
                startIndex += words.get(i - 1).getText().length() + 1;
                endIndex++;
            }
            endIndex += words.get(i).getText().length();
        }
        return new Pair<>(fromStart ? 0 : startIndex, endIndex);
    }

    public void setCurrentSentenceIndex(int currentSentenceIndex) {
        if (mCurrentSentenceIndex != NO_HIGHLIGHT)
            notifyItemChanged(mCurrentSentenceIndex);
        mCurrentSentenceIndex = currentSentenceIndex;
        if (mCurrentSentenceIndex != NO_HIGHLIGHT)
            notifyItemChanged(mCurrentSentenceIndex);
    }

    public void setCurrentWordIndex(int currentWordIndex) {
        mCurrentWordIndex = currentWordIndex;
        if (mCurrentSentenceIndex != NO_HIGHLIGHT)
            notifyItemChanged(mCurrentSentenceIndex);
    }

    public int getCurrentWordIndex() {
        return mCurrentWordIndex;
    }

    public int getCurrentSentenceIndex() {
        return mCurrentSentenceIndex;
    }

    @Override
    public int getItemCount() {
        return mSentenceStrs.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == mCurrentSentenceIndex ? HIGHLIGHT : NO_HIGHLIGHT;
    }
}
