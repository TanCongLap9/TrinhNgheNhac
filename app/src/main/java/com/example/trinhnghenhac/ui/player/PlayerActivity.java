package com.example.trinhnghenhac.ui.player;

import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.Tracks;
import androidx.media3.common.VideoSize;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.trinhnghenhac.adapters.PlayerPagerAdapter;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.PlayerAction;
import com.example.trinhnghenhac.functionalinterfaces.Action;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.services.MediaPlayerService;
import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.databinding.ActivityPlayerBinding;
import com.google.android.material.slider.Slider;

public class PlayerActivity extends AppCompatActivity implements ServiceConnection, View.OnClickListener, Slider.OnSliderTouchListener, Slider.OnChangeListener {
    private PlayerActivityDisplay mDisplay;
    @Nullable
    private MediaPlayerService.LocalBinder binder;
    private ActivityPlayerBinding b;
    private final Action<Integer> onRepeatModeChangedListener = new Action<Integer>() {
        @Override
        public void run(Integer integer) {
            mDisplay.updateRepeat(integer);
        }
    };
    private CountDownTimer countDownTimer = new CountDownTimer(3600L * 24L * 1000L, 1000L) {
        @Override
        public void onTick(long l) {
            if (binder.getMediaPlayer().isPlaying())
                mDisplay.updateTime(
                        (int) (binder.getMediaPlayer().getCurrentPosition() / 1000),
                        (int) (binder.getMediaPlayer().getDuration() / 1000)
                );
        }

        @Override
        public void onFinish() {

        }
    };

    private final Player.Listener listener = new Player.Listener() {
        @Override
        public void onTimelineChanged(Timeline timeline, int reason) {
            Player.Listener.super.onTimelineChanged(timeline, reason);
            Log.d("ExoPlayer", "onTimelineChanged: " + timeline);
        }

        @Override
        public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
            Player.Listener.super.onMediaItemTransition(mediaItem, reason);
            Log.d("ExoPlayer", "onMediaItemTransition: " + mediaItem);
        }

        @Override
        public void onTracksChanged(Tracks tracks) {
            Player.Listener.super.onTracksChanged(tracks);
            Log.d("ExoPlayer", "onTracksChanged: " + tracks);
        }

        @Override
        public void onIsLoadingChanged(boolean isLoading) {
            Player.Listener.super.onIsLoadingChanged(isLoading);
            Log.d("ExoPlayer", "onIsLoadingChanged: " + isLoading);
        }

        @Override
        public void onAvailableCommandsChanged(Player.Commands availableCommands) {
            Player.Listener.super.onAvailableCommandsChanged(availableCommands);
            Log.d("ExoPlayer", "onAvailableCommandsChanged: " + availableCommands);
        }

        @Override
        public void onTrackSelectionParametersChanged(TrackSelectionParameters parameters) {
            Player.Listener.super.onTrackSelectionParametersChanged(parameters);
            Log.d("ExoPlayer", "onTrackSelectionParametersChanged: " + parameters);
        }

        @Override
        public void onPlaybackStateChanged(int playbackState) {
            Player.Listener.super.onPlaybackStateChanged(playbackState);
            if (playbackState == Player.STATE_READY) {
                mDisplay.updateTime(
                        (int) (binder.getMediaPlayer().getCurrentPosition() / 1000),
                        (int) (binder.getMediaPlayer().getDuration() / 1000)
                );
            }
            Log.d("ExoPlayer", "onPlaybackStateChanged: " + playbackState);
        }

        @Override
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            Player.Listener.super.onPlayWhenReadyChanged(playWhenReady, reason);
            Log.d("ExoPlayer", "onPlayWhenReadyChanged: " + playWhenReady);
        }

        @Override
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
            Player.Listener.super.onPlaybackSuppressionReasonChanged(playbackSuppressionReason);
            Log.d("ExoPlayer", "onPlaybackSuppressionReasonChanged: " + playbackSuppressionReason);
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Player.Listener.super.onIsPlayingChanged(isPlaying);
            mDisplay.updatePlayPause(isPlaying);
        }

        @Override
        public void onPlayerError(PlaybackException error) {
            Player.Listener.super.onPlayerError(error);
            Log.d("ExoPlayer", "onPlayerError: " + error);
        }

        @Override
        public void onPlayerErrorChanged(@Nullable PlaybackException error) {
            Player.Listener.super.onPlayerErrorChanged(error);
            Log.d("ExoPlayer", "onPlayerErrorChanged: " + error);
        }

        @Override
        public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
            Player.Listener.super.onPositionDiscontinuity(oldPosition, newPosition, reason);
            Log.d("ExoPlayer", "onPositionDiscontinuity: oldPosition = " + oldPosition + ", newPosition = " + newPosition);
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Player.Listener.super.onPlaybackParametersChanged(playbackParameters);
            Log.d("ExoPlayer", "onPlaybackParametersChanged: " + playbackParameters);
        }

        @Override
        public void onSeekBackIncrementChanged(long seekBackIncrementMs) {
            Player.Listener.super.onSeekBackIncrementChanged(seekBackIncrementMs);
            Log.d("ExoPlayer", "onSeekBackIncrementChanged: " + seekBackIncrementMs);
        }

        @Override
        public void onSeekForwardIncrementChanged(long seekForwardIncrementMs) {
            Player.Listener.super.onSeekForwardIncrementChanged(seekForwardIncrementMs);
            Log.d("ExoPlayer", "onSeekForwardIncrementChanged: " + seekForwardIncrementMs);
        }

        @Override
        public void onMaxSeekToPreviousPositionChanged(long maxSeekToPreviousPositionMs) {
            Player.Listener.super.onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs);
            Log.d("ExoPlayer", "onMaxSeekToPreviousPositionChanged: " + maxSeekToPreviousPositionMs);
        }

        @Override
        public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            Player.Listener.super.onAudioAttributesChanged(audioAttributes);
            Log.d("ExoPlayer", "onAudioAttributesChanged: " + audioAttributes);
        }

        @Override
        public void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
            Player.Listener.super.onSkipSilenceEnabledChanged(skipSilenceEnabled);
            Log.d("ExoPlayer", "onSkipSilenceEnabledChanged: " + skipSilenceEnabled);
        }

        @Override
        public void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            Player.Listener.super.onDeviceInfoChanged(deviceInfo);
            Log.d("ExoPlayer", "onDeviceInfoChanged: " + deviceInfo);
        }

        @Override
        public void onVideoSizeChanged(VideoSize videoSize) {
            Player.Listener.super.onVideoSizeChanged(videoSize);
            Log.d("ExoPlayer", "onVideoSizeChanged: " + videoSize);
        }

        @Override
        public void onSurfaceSizeChanged(int width, int height) {
            Player.Listener.super.onSurfaceSizeChanged(width, height);
            Log.d("ExoPlayer", "onSurfaceSizeChanged: width = " + width + ", height = " + height);
        }

        @Override
        public void onRenderedFirstFrame() {
            Player.Listener.super.onRenderedFirstFrame();
            Log.d("ExoPlayer", "onRenderedFirstFrame: ");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Playable playable = getIntent().getParcelableExtra(Extras.EXTRA_PLAYABLE);
        b = ActivityPlayerBinding.inflate(getLayoutInflater(), null, false);
        mDisplay = new PlayerActivityDisplay(this, b, countDownTimer);
        mDisplay.initTooltips();
        b.activityPlayerPager.setAdapter(new PlayerPagerAdapter(this));
        bindEvents();

        Intent serviceIntent = new Intent(this, MediaPlayerService.class);
        if (playable != null) {
            serviceIntent.setAction(PlayerAction.ACTION_PLAY);
            serviceIntent.putExtra(Extras.EXTRA_PLAYABLE, (Parcelable) playable);
            startService(serviceIntent);
        }
        getIntent().removeExtra(Extras.EXTRA_PLAYABLE);
        bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
        setContentView(b.getRoot());
    }

    private void bindEvents() {
        b.activityPlayerSeeker.addOnSliderTouchListener(this);
        b.activityPlayerSeeker.addOnChangeListener(this);
        b.activityPlayerNext.setOnClickListener(this);
        b.activityPlayerPrevious.setOnClickListener(this);
        b.activityPlayerPlayPause.setOnClickListener(this);
        b.activityPlayerLyric.setOnClickListener(this);
        b.activityPlayerRepeat.setOnClickListener(this);
        b.activityPlayerStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == b.activityPlayerPrevious) {
            startService(new Intent(this, MediaPlayerService.class)
                    .setAction(PlayerAction.ACTION_PREVIOUS));
        } else if (view == b.activityPlayerNext) {
            startService(new Intent(this, MediaPlayerService.class)
                    .setAction(PlayerAction.ACTION_NEXT));
        } else if (view == b.activityPlayerPlayPause) {
            startService(new Intent(this, MediaPlayerService.class)
                    .setAction(PlayerAction.ACTION_PLAY_PAUSE));
        } else if (view == b.activityPlayerRepeat) {
            startService(new Intent(this, MediaPlayerService.class)
                    .setAction(PlayerAction.ACTION_REPEAT));
        } else if (view == b.activityPlayerStop) {
            stopService(new Intent(this, MediaPlayerService.class));
            finish();
        } else if (view == b.activityPlayerLyric) {
            b.activityPlayerPager.setCurrentItem(1);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStartTrackingTouch(@NonNull Slider slider) {
        countDownTimer.cancel();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStopTrackingTouch(@NonNull Slider slider) {
        if (binder != null && binder.getMediaPlayer() != null && binder.getMediaPlayer().isCommandAvailable(Player.COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM))
            binder.getMediaPlayer().seekTo((long) (slider.getValue() * 1000));
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        binder = (MediaPlayerService.LocalBinder) iBinder;
        if (binder.getMediaPlayer() == null) return;
        mDisplay.updateAll(binder.getMediaPlayer(), binder.getRepeatMode());
        binder.addOnRepeatModeChangedListener(onRepeatModeChangedListener);
        binder.getMediaPlayer().addListener(listener);
                /*.setOnCompletionListener(PlayerActivity.this);
        binder.getMediaPlayer().setOnPreparedListener(PlayerActivity.this);
        binder.getMediaPlayer().setOnSeekCompleteListener(PlayerActivity.this);*/

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        binder = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.removeOnRepeatModeChangedListener(onRepeatModeChangedListener);
        binder.getMediaPlayer().removeListener(listener);
        countDownTimer.cancel();
    }
}
