package com.example.trinhnghenhac.services;

import com.example.trinhnghenhac.R;

import static com.example.trinhnghenhac.utils.AssertUtils.NE;
import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;
import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.trinhnghenhac.commands.PlayerEnqueueCommand;
import com.example.trinhnghenhac.commands.PlayerGotoCommand;
import com.example.trinhnghenhac.commands.PlayerNextCommand;
import com.example.trinhnghenhac.commands.PlayerPlayCommand;
import com.example.trinhnghenhac.commands.PlayerPlayPauseCommand;
import com.example.trinhnghenhac.commands.PlayerPreviousCommand;
import com.example.trinhnghenhac.commands.PlayerRepeatCommand;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.PlayerAction;
import com.example.trinhnghenhac.constants.PlayerRepeatMode;
import com.example.trinhnghenhac.functionalinterfaces.Action;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.ui.player.PlayerActivity;
import com.example.trinhnghenhac.utils.ListIteratorUtils;
import com.example.trinhnghenhac.utils.RandomUtils;
import com.example.trinhnghenhac.utils.ServiceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaPlayerService extends Service {
    public static final String CHANNEL_ID = "MediaPlayer";
    public static final int NOTIFICATION_ID = 1;
    public static final int REQUEST_CODE_PLAY_PAUSE = 1;
    public static final int REQUEST_CODE_PREVIOUS = 2;
    public static final int REQUEST_CODE_NEXT = 3;
    public static final int REQUEST_CODE_ACTIVITY = 4;
    private final IBinder binder = new LocalBinder();
    @Nullable
    private ExoPlayer player;
    private ExecutorService executor;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final List<Playable> mPlayables = new ArrayList<>();
    private ListIterator<Playable> mPlayableIter = mPlayables.listIterator();
    @PlayerRepeatMode
    private int mRepeatMode = 0;
    private final List<Action<Integer>> mOnRepeatModeChangedListeners = new ArrayList<>();

    private void notifyRepeatModeChanged() {
        for (Action<Integer> onRepeatModeChangedListener : mOnRepeatModeChangedListeners) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onRepeatModeChangedListener.run(mRepeatMode);
                }
            });
        }
    }

    public void setRepeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
        notifyRepeatModeChanged();
    }

    public void cycleRepeatMode() {
        setRepeatMode((mRepeatMode + 1) % 4);
        notifyRepeatModeChanged();
    }

    public ListIterator<Playable> newPlayableIter() {
        mPlayableIter = mPlayables.listIterator();
        return mPlayableIter;
    }

    private Playable getPlayable() {
        return ListIteratorUtils.getCurrent(mPlayableIter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceUtils.running = true;
        executor = Executors.newSingleThreadExecutor();
        player = new ExoPlayer.Builder(this).build();
        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                Player.Listener.super.onIsPlayingChanged(isPlaying);
                Log.d("Playable", "" + mPlayableIter.nextIndex());
                NotificationManagerCompat.from(MediaPlayerService.this)
                        .notify(NOTIFICATION_ID, getNotification(getPlayable()));
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);
                if (playbackState == Player.STATE_ENDED) {
                    switch (mRepeatMode) {
                        case PlayerRepeatMode.REPEAT_MODE_OFF:
                            if (!mPlayableIter.hasNext()) return;
                            startService(new Intent(MediaPlayerService.this, MediaPlayerService.class)
                                    .setAction(PlayerAction.ACTION_NEXT));
                            break;
                        case PlayerRepeatMode.REPEAT_MODE_ALL:
                            if (!mPlayableIter.hasNext()) {
                                startService(new Intent(MediaPlayerService.this, MediaPlayerService.class)
                                        .setAction(PlayerAction.ACTION_GOTO)
                                        .putExtra(Extras.EXTRA_SONG_INDEX, 0));
                                return;
                            }
                            startService(new Intent(MediaPlayerService.this, MediaPlayerService.class)
                                    .setAction(PlayerAction.ACTION_NEXT));
                            break;
                        case PlayerRepeatMode.REPEAT_MODE_ONE:
                            startService(new Intent(MediaPlayerService.this, MediaPlayerService.class)
                                    .setAction(PlayerAction.ACTION_GOTO)
                                    .putExtra(Extras.EXTRA_SONG_INDEX, mPlayableIter.previousIndex()));
                            break;
                        case PlayerRepeatMode.REPEAT_MODE_SHUFFLE:
                            startService(new Intent(MediaPlayerService.this, MediaPlayerService.class)
                                    .setAction(PlayerAction.ACTION_GOTO)
                                    .putExtra(Extras.EXTRA_SONG_INDEX, RandomUtils.getRandomIndex(mPlayables)));
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Playable playable = intent.getParcelableExtra(Extras.EXTRA_PLAYABLE);
        int index = intent.getIntExtra(Extras.EXTRA_SONG_INDEX, -1);
        switch (intent.getAction()) {
            case PlayerAction.ACTION_PLAY:
                assertNotNull(playable);
                mPlayables.clear();
                new PlayerPlayCommand(this, player, executor, mPlayableIter, playable).execute();
                break;
            case PlayerAction.ACTION_PLAY_ENQUEUE:
                assertNotNull(playable);
                new PlayerEnqueueCommand(this, player, executor, mPlayableIter, playable).execute();
                break;
            case PlayerAction.ACTION_PLAY_PAUSE:
                new PlayerPlayPauseCommand(this, player, executor, mPlayableIter).execute();
                break;
            case PlayerAction.ACTION_PREVIOUS:
                new PlayerPreviousCommand(this, player, executor, mPlayableIter).execute();
                break;
            case PlayerAction.ACTION_NEXT:
                new PlayerNextCommand(this, player, executor, mPlayableIter).execute();
                break;
            case PlayerAction.ACTION_REPEAT:
                new PlayerRepeatCommand(this, player, executor, mPlayableIter).execute();
                break;
            case PlayerAction.ACTION_GOTO:
                assertThat(index, NE, -1);
                new PlayerGotoCommand(this, player, executor, mPlayableIter, index).execute();
                break;
        }

        // try {
            /*exoPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA).build()
            );
            if (exoPlayer.isPlaying()) exoPlayer.reset();
            exoPlayer.setDataSource(getApplicationContext(), Uri.parse(zingMp3Api.getMediaUrl(songModel.getId())));
            exoPlayer.prepare();
            exoPlayer.start();*/
        /*} catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        /*NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel();
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID).setContentTitle(getString(R.string.media_player_now_playing)).setContentText().build();
        startForeground(startId, );*/

        return START_STICKY;
    }

    public Notification getNotification(Playable playable) {
        Intent iPlayPause = new Intent().setAction(PlayerAction.ACTION_PLAY_PAUSE);
        Intent iNext = new Intent().setAction(PlayerAction.ACTION_NEXT);
        Intent iPrevious = new Intent().setAction(PlayerAction.ACTION_PREVIOUS);
        Intent iActivity = new Intent(this, PlayerActivity.class);
        PendingIntent pPlayPause = PendingIntent.getService(this, REQUEST_CODE_PLAY_PAUSE, iPlayPause, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pPrevious = PendingIntent.getService(this, REQUEST_CODE_PREVIOUS, iPrevious, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pNext = PendingIntent.getService(this, REQUEST_CODE_NEXT, iNext, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pActivity = PendingIntent.getActivity(this, REQUEST_CODE_ACTIVITY, iActivity, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action aPrevious = new NotificationCompat.Action.Builder(R.drawable.ic_round_skip_previous_24, getString(R.string.media_player_previous), pPrevious).build();
        NotificationCompat.Action aPlayPause = new NotificationCompat.Action.Builder(player.isPlaying() ? R.drawable.round_pause_circle_outline_24 : R.drawable.round_play_circle_outline_24, getString(player.isPlaying() ? R.string.media_player_pause : R.string.media_player_play), pPlayPause).build();
        NotificationCompat.Action aNext = new NotificationCompat.Action.Builder(R.drawable.ic_round_skip_next_24, getString(R.string.media_player_next), pNext).build();
        NotificationCompat.Action aStop = new NotificationCompat.Action.Builder(R.drawable.ic_round_stop_24, getString(R.string.media_player_stop), pNext).build();

        NotificationChannelCompat channel = new NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
                .setName("Player").build();

        NotificationManagerCompat.from(MediaPlayerService.this).createNotificationChannel(channel);

        return new NotificationCompat.Builder(MediaPlayerService.this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.media_player_now_playing))
                .setContentText(playable.getTitle() + " - " + playable.getText())
                .setContentIntent(pActivity)
                .addAction(aPrevious)
                .addAction(aPlayPause)
                .addAction(aNext)
                .addAction(aStop)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
        player = null;
        executor.shutdown();
        executor = null;
        mPlayables.clear();
        ServiceUtils.running = false;
    }

    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }

        @Nullable
        public ExoPlayer getMediaPlayer() {
            return player;
        }

        @Nullable
        public Playable getPlayable() {
            return MediaPlayerService.this.getPlayable();
        }

        public int getRepeatMode() {
            return mRepeatMode;
        }

        public void addOnRepeatModeChangedListener(Action<Integer> onRepeatModeChangedListener) {
            mOnRepeatModeChangedListeners.add(onRepeatModeChangedListener);
        }

        public void removeOnRepeatModeChangedListener(Action<Integer> onRepeatModeChangedListener) {
            mOnRepeatModeChangedListeners.remove(onRepeatModeChangedListener);
        }
    }
}
