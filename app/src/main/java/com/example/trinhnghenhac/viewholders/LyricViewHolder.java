package com.example.trinhnghenhac.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.R;
import com.google.android.material.textview.MaterialTextView;

public class LyricViewHolder extends RecyclerView.ViewHolder {
    public MaterialTextView lyric;

    public LyricViewHolder(@NonNull View itemView) {
        super(itemView);
        lyric = itemView.findViewById(R.id.listitem_lyric_sentence_sentence);
    }
}
