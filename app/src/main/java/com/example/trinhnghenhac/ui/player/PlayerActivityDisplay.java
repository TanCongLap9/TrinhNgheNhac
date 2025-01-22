package com.example.trinhnghenhac.ui.player;

import android.content.Context;
import android.os.CountDownTimer;

import androidx.appcompat.widget.TooltipCompat;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.PlayerRepeatMode;
import com.example.trinhnghenhac.databinding.ActivityPlayerBinding;

import java.util.Locale;

public class PlayerActivityDisplay {
    private final Context mContext;
    private final ActivityPlayerBinding b;
    private final CountDownTimer mCountDownTimer;
    public PlayerActivityDisplay(Context context, ActivityPlayerBinding binding, CountDownTimer countDownTimer) {
        mContext = context;
        b = binding;
        mCountDownTimer = countDownTimer;
    }

    public void initTooltips() {
        TooltipCompat.setTooltipText(b.activityPlayerStop, mContext.getString(R.string.media_player_stop));
        TooltipCompat.setTooltipText(b.activityPlayerPlayPause, mContext.getString(R.string.media_player_pause));
        TooltipCompat.setTooltipText(b.activityPlayerRepeat, mContext.getString(R.string.media_player_repeat));
        TooltipCompat.setTooltipText(b.activityPlayerNext, mContext.getString(R.string.media_player_next));
        TooltipCompat.setTooltipText(b.activityPlayerPrevious, mContext.getString(R.string.media_player_previous));
    }

    public void updateRepeat(@PlayerRepeatMode int repeatMode) {
        switch (repeatMode) {
            case PlayerRepeatMode.REPEAT_MODE_OFF:
                b.activityPlayerRepeat.setIconResource(R.drawable.ic_round_repeat_24);
                b.activityPlayerRepeat.setIconTintResource(R.color.grey_500);
                TooltipCompat.setTooltipText(b.activityPlayerRepeat, mContext.getString(R.string.media_player_repeat));
                break;
            case PlayerRepeatMode.REPEAT_MODE_ALL:
                b.activityPlayerRepeat.setIconResource(R.drawable.ic_round_repeat_24);
                b.activityPlayerRepeat.setIconTintResource(R.color.white);
                TooltipCompat.setTooltipText(b.activityPlayerRepeat, mContext.getString(R.string.media_player_repeat_all));
                break;
            case PlayerRepeatMode.REPEAT_MODE_ONE:
                b.activityPlayerRepeat.setIconResource(R.drawable.ic_round_repeat_one_24);
                TooltipCompat.setTooltipText(b.activityPlayerRepeat, mContext.getString(R.string.media_player_repeat_one));
                break;
            case PlayerRepeatMode.REPEAT_MODE_SHUFFLE:
                b.activityPlayerRepeat.setIconResource(R.drawable.round_shuffle_24);
                b.activityPlayerRepeat.setIconTintResource(R.color.white);
                TooltipCompat.setTooltipText(b.activityPlayerRepeat, mContext.getString(R.string.media_player_shuffle));
                break;
        }
    }

    public String formatTime(long seconds) {
        return String.format(Locale.ENGLISH, "%02d:%02d", seconds / 60, seconds % 60);
    }

    public void updateTime(int current, int duration) {
        b.activityPlayerSeeker.setValue(Math.min(current, duration));
        b.activityPlayerSeeker.setValueTo(duration);
        b.activityPlayerTimeNow.setText(formatTime(current));
        b.activityPlayerTimeMax.setText(formatTime(duration));
    }

    public void updateAll(ExoPlayer player, int repeatMode) {
        updatePlayPause(player.isPlaying());
        updateRepeat(repeatMode);
    }

    public void updatePlayPause(boolean isPlaying) {
        if (isPlaying) {
            b.activityPlayerPlayPause.setIconResource(R.drawable.round_pause_circle_outline_24);
            TooltipCompat.setTooltipText(b.activityPlayerPlayPause, mContext.getString(R.string.media_player_pause));
            mCountDownTimer.start();
        } else {
            b.activityPlayerPlayPause.setIconResource(R.drawable.round_play_circle_outline_24);
            TooltipCompat.setTooltipText(b.activityPlayerPlayPause, mContext.getString(R.string.media_player_play));
            mCountDownTimer.cancel();
        }
    }
}
