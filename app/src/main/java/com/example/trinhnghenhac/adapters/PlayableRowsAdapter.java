package com.example.trinhnghenhac.adapters;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.PlayableItemType;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.SavedStarredItem;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.example.trinhnghenhac.viewholders.PlayableRowLoadingViewHolder;
import com.example.trinhnghenhac.viewholders.PlayableRowShowAllViewHolder;
import com.example.trinhnghenhac.viewholders.PlayableRowViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class PlayableRowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int LOADING_ITEM_COUNT = 15;
    private final Context mContext;
    @Nullable
    private List<? extends Playable> mPlayables;
    private final boolean mShowPlatform;
    private final boolean mShowType;
    private View.OnClickListener mOnShowAllClickListener;
    private List<SavedStarredItem> mSavedStarredItems;
    private int mLimit;

    public PlayableRowsAdapter(Context context, boolean showType, boolean showPlatform) {
        mContext = context;
        mShowType = showType;
        mShowPlatform = showPlatform;
    }

    public PlayableRowsAdapter(Context context, boolean showType, boolean showPlatform, int limit) {
        mContext = context;
        mShowType = showType;
        mShowPlatform = showPlatform;
        mLimit = limit;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case PlayableItemType.ITEM_TYPE_PLAYABLE:
                View playableView = LayoutInflater.from(mContext).inflate(R.layout.listitem_playable_row, parent, false);
                return new PlayableRowViewHolder(playableView, viewType, true, mShowType, mShowPlatform);
            case PlayableItemType.ITEM_TYPE_SHOW_ALL:
                View showAllView = LayoutInflater.from(mContext).inflate(R.layout.listitem_playable_row_show_all, parent, false);
                return new PlayableRowShowAllViewHolder(showAllView);
            default:
                View loadingView = LayoutInflater.from(mContext).inflate(R.layout.listitem_playable_row_loading, parent, false);
                return new PlayableRowLoadingViewHolder(loadingView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case PlayableItemType.ITEM_TYPE_PLAYABLE:
                if (mPlayables == null) return;
                PlayableRowViewHolder playableViewHolder = (PlayableRowViewHolder) holder;
                playableViewHolder.setPlayable(mPlayables.get(position));

                if (mSavedStarredItems != null) {
                    playableViewHolder.setStarredFrom(mSavedStarredItems);
                    break;
                }
                mSavedStarredItems = new ArrayList<>();
                FirebaseUtils.getSaved(FirebaseUtils.currentUser).addOnSuccessListener(new OnSuccessListener<List<SavedStarredItem>>() {
                    @Override
                    public void onSuccess(List<SavedStarredItem> savedStarredItems) {
                        mSavedStarredItems = savedStarredItems;
                        playableViewHolder.setStarredFrom(mSavedStarredItems);
                    }
                });
                break;
            case PlayableItemType.ITEM_TYPE_SHOW_ALL:
                if (mOnShowAllClickListener != null)
                    holder.itemView.setOnClickListener(mOnShowAllClickListener);
                break;
        }
    }

    public void update(List<? extends Playable> playables) {
        mPlayables = playables;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mPlayables == null ? PlayableItemType.ITEM_TYPE_LOADING
            : mLimit == 0 ||
                mPlayables.size() <= mLimit ||
                position != getItemCount() - 1
                    ? PlayableItemType.ITEM_TYPE_PLAYABLE
                    : PlayableItemType.ITEM_TYPE_SHOW_ALL;
    }

    public void setOnShowAllClickListener(View.OnClickListener onShowAllClickListener) {
        mOnShowAllClickListener = onShowAllClickListener;
    }

    @Override
    public int getItemCount() {
        return mPlayables == null ? LOADING_ITEM_COUNT : mLimit == 0
                ? mPlayables.size()
                : Math.min(mPlayables.size(), mLimit + 1);
    }
}
