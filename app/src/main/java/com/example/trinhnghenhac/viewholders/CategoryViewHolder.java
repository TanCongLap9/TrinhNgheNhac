package com.example.trinhnghenhac.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImage;
    public TextView mTitle;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.listitem_category_image);
        mTitle = itemView.findViewById(R.id.listitem_category_title);
    }
}
