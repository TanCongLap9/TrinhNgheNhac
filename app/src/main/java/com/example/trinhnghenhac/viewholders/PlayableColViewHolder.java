package com.example.trinhnghenhac.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.trinhnghenhac.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.FirebaseMusicPlatform;
import com.example.trinhnghenhac.constants.FirebasePlayableType;
import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.constants.PlayableItemType;
import com.example.trinhnghenhac.constants.PlayerAction;
import com.example.trinhnghenhac.databinding.ListitemPlayableColBinding;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SavedStarredItem;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.services.MediaPlayerService;
import com.example.trinhnghenhac.ui.player.PlayerActivity;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.example.trinhnghenhac.utils.NavigationUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class PlayableColViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final boolean mBindEvent;
    private final int mViewType;
    private final boolean mShowPlatform;
    private final boolean mShowType;
    private final Context mContext;
    private Playable mPlayable;
    private SavedStarredItem mItem;
    private ListitemPlayableColBinding b;

    public PlayableColViewHolder(@NonNull View itemView, int viewType, boolean bindEvent, boolean showType, boolean showPlatform) {
        super(itemView);
        mContext = itemView.getContext();
        mViewType = viewType;
        mBindEvent = bindEvent;
        mShowType = showType;
        mShowPlatform = showPlatform;
        if (viewType != PlayableItemType.ITEM_TYPE_PLAYABLE) return;
        b = ListitemPlayableColBinding.bind(itemView);
    }

    public void setPlayable(Playable playable) {
        mPlayable = playable;
        if (playable instanceof Playlist || playable instanceof Artist)
            b.listitemPlayableColAddSaved.setVisibility(View.GONE);
        if (!playable.isPremium()) b.listitemPlayableColPro.setVisibility(View.GONE);
        b.listitemPlayableColTitle.setText(playable.getTitle());
        b.listitemPlayableColText.setText(playable.getText());
        Glide.with(mContext).load(playable.getImage()).into(b.listitemPlayableColImage);
        if (!mShowType)
            b.listitemPlayableColType.setVisibility(View.GONE);
        else b.listitemPlayableColType.setImageResource(Playable.getIcon(playable));
        if (!mShowPlatform)
            b.listitemPlayableColPlatform.setVisibility(View.GONE);
        else b.listitemPlayableColPlatform.setImageResource(MusicPlatformApi.toResId(playable.getPlatform()));

        if (mBindEvent && mViewType == PlayableItemType.ITEM_TYPE_PLAYABLE) {
            itemView.setOnClickListener(this);
            b.listitemPlayableColAddSaved.setOnClickListener(this);
            b.listitemPlayableColStar.setOnClickListener(this);
        }
    }

    public void setStarredFrom(List<SavedStarredItem> items) {
        for (SavedStarredItem item : items) {
            String firebasePlatform = mPlayable.getPlatform() == MusicPlatform.PLATFORM_NHACCUATUI ? FirebaseMusicPlatform.PLATFORM_NHACCUATUI
                    : mPlayable.getPlatform() == MusicPlatform.PLATFORM_ZINGMP3 ? FirebaseMusicPlatform.PLATFORM_ZINGMP3
                    : FirebaseMusicPlatform.PLATFORM_SOUNDCLOUD;

            String firebaseItemType = mPlayable instanceof Artist ? FirebasePlayableType.ITEM_TYPE_ARTIST
                    : mPlayable instanceof Song ? FirebasePlayableType.ITEM_TYPE_SONG
                    : mPlayable instanceof Playlist ? FirebasePlayableType.ITEM_TYPE_PLAYLIST
                    : FirebasePlayableType.ITEM_TYPE_VIDEO;

            if (mPlayable.getId().equals(item.getItemId()) &&
                    item.getType().equals(firebaseItemType) &&
                    item.getPlatform().equals(firebasePlatform)) {
                mItem = item;
                b.listitemPlayableColStar.setIconResource(R.drawable.round_star_24);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == b.listitemPlayableColAddSaved) {
            Intent intent = new Intent(mContext, MediaPlayerService.class);
            intent.setAction(PlayerAction.ACTION_PLAY_ENQUEUE);
            intent.putExtra(Extras.EXTRA_PLAYABLE, (Parcelable) mPlayable);
            mContext.startService(intent);
        } else if (view == b.listitemPlayableColStar) {
            if (mItem != null) {
                FirebaseUtils.deleteSaved(mItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        b.listitemPlayableColStar.setIconResource(R.drawable.round_star_border_24);
                        mItem = null;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                String firebasePlatform = mPlayable.getPlatform() == MusicPlatform.PLATFORM_NHACCUATUI ? FirebaseMusicPlatform.PLATFORM_NHACCUATUI
                        : mPlayable.getPlatform() == MusicPlatform.PLATFORM_ZINGMP3 ? FirebaseMusicPlatform.PLATFORM_ZINGMP3
                        : FirebaseMusicPlatform.PLATFORM_SOUNDCLOUD;

                String firebaseItemType = mPlayable instanceof Artist ? FirebasePlayableType.ITEM_TYPE_ARTIST
                        : mPlayable instanceof Song ? FirebasePlayableType.ITEM_TYPE_SONG
                        : mPlayable instanceof Playlist ? FirebasePlayableType.ITEM_TYPE_PLAYLIST
                        : FirebasePlayableType.ITEM_TYPE_VIDEO;

                FirebaseUtils.addSaved(new SavedStarredItem(
                                FirebaseUtils.currentUser.getId(),
                                mPlayable.getId(),
                                firebasePlatform,
                                firebaseItemType))
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                b.listitemPlayableColStar.setIconResource(R.drawable.round_star_24);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else if (view == itemView) {
            if (mPlayable instanceof Playlist) {
                NavigationUtils.viewPlaylist(view, (Playlist) mPlayable);
            } else if (mPlayable instanceof Artist) {
                NavigationUtils.viewArtist(view, (Artist) mPlayable);
            } else {
                if (mPlayable.isPremium()) {
                    new MaterialAlertDialogBuilder(mContext)
                            .setTitle(R.string.pro_dialog_title)
                            .setMessage(R.string.pro_dialog_text)
                            .setPositiveButton(R.string.dialog_ok, null)
                            .show();
                    return;
                }
                Intent playerIntent = new Intent(mContext, PlayerActivity.class);
                playerIntent.setAction(PlayerAction.ACTION_PLAY);
                playerIntent.putExtra(Extras.EXTRA_PLAYABLE, (Parcelable) mPlayable);
                mContext.startActivity(playerIntent);
            }
        }
    }
}
