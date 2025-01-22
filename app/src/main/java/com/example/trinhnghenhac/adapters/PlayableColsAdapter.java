package com.example.trinhnghenhac.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.PlayableItemType;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.SavedStarredItem;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.example.trinhnghenhac.viewholders.PlayableColLoadingViewHolder;
import com.example.trinhnghenhac.viewholders.PlayableColShowAllViewHolder;
import com.example.trinhnghenhac.viewholders.PlayableColViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class PlayableColsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int LOADING_ITEM_COUNT = 10;
    private final Context mContext;
    @Nullable
    private List<? extends Playable> mPlayables;
    private final boolean mShowPlatform;
    private final boolean mShowType;
    private View.OnClickListener mOnShowAllClickListener;
    private List<SavedStarredItem> mSavedStarredItems;
    private int mLimit;

    public PlayableColsAdapter(Context context, boolean showType, boolean showPlatform) {
        mContext = context;
        mShowType = showType;
        mShowPlatform = showPlatform;
    }

    public PlayableColsAdapter(Context context, boolean showType, boolean showPlatform, int limit) {
        mContext = context;
        mShowType = showType;
        mShowPlatform = showPlatform;
        mLimit = limit;
    }

    public void setOnShowAllClickListener(View.OnClickListener onShowAllClickListener) {
        mOnShowAllClickListener = onShowAllClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case PlayableItemType.ITEM_TYPE_PLAYABLE:
                View playableView = LayoutInflater.from(mContext).inflate(R.layout.listitem_playable_col, parent, false);
                return new PlayableColViewHolder(playableView, viewType, true, mShowType, mShowPlatform);
            case PlayableItemType.ITEM_TYPE_SHOW_ALL:
                View showAllView = LayoutInflater.from(mContext).inflate(R.layout.listitem_playable_col_show_all, parent, false);
                return new PlayableColShowAllViewHolder(showAllView);
            default:
                View loadingView = LayoutInflater.from(mContext).inflate(R.layout.listitem_playable_col_loading, parent, false);
                return new PlayableColLoadingViewHolder(loadingView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case PlayableItemType.ITEM_TYPE_PLAYABLE:
                if (mPlayables == null) return;
                PlayableColViewHolder playableViewHolder = (PlayableColViewHolder) holder;
                playableViewHolder.setPlayable(mPlayables.get(position));

                if (mSavedStarredItems != null) {
                    playableViewHolder.setStarredFrom(mSavedStarredItems);
                    break;
                }
                mSavedStarredItems = new ArrayList<>();
                FirebaseUtils.getSaved(FirebaseUtils.currentUser)
                        .addOnSuccessListener(new OnSuccessListener<List<SavedStarredItem>>() {
                        @Override
                        public void onSuccess(List<SavedStarredItem> savedStarredItems) {
                            mSavedStarredItems = savedStarredItems;
                            playableViewHolder.setStarredFrom(mSavedStarredItems);
                        }
                    })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return mPlayables == null ? LOADING_ITEM_COUNT : mLimit == 0
                ? mPlayables.size()
                : Math.min(mPlayables.size(), mLimit + 1);
    }
}
