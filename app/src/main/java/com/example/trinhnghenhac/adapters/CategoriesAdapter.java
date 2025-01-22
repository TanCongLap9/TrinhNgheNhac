package com.example.trinhnghenhac.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.api.MusicCategories;
import com.example.trinhnghenhac.ui.category.CategoryFragment;
import com.example.trinhnghenhac.utils.NavigationUtils;
import com.example.trinhnghenhac.viewholders.CategoryViewHolder;

public class
CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private final Context mContext;
    private final int[] mCategories;

    public CategoriesAdapter(Context context, int[] categories) {
        mContext = context;
        mCategories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(mContext).inflate(R.layout.listitem_category, parent, false);
        return new CategoryViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        int category = mCategories[position];
        holder.mTitle.setText(MusicCategories.getStringRes(category));
        holder.mImage.setImageResource(MusicCategories.getDrawableRes(category));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtils.viewCategoryFromCategories(view, category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.length;
    }
}
