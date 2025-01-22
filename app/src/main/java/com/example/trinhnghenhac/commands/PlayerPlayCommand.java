package com.example.trinhnghenhac.commands;

import static com.example.trinhnghenhac.services.MediaPlayerService.NOTIFICATION_ID;
import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.services.MediaPlayerService;

import java.io.IOException;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;

public class PlayerPlayCommand extends PlayerCommandBase {
    private final Playable mPlayable;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    public PlayerPlayCommand(MediaPlayerService service, ExoPlayer player, ExecutorService executor, ListIterator<Playable> playableIter, Playable playable) {
        super(service, player, executor, playableIter);
        mPlayable = playable;
    }

    @Override
    public void execute() {
        // ZingMp3 Chiều Đồng Quê ZUOFC78E
        // ZingMp3 Baam ZW9CB7EF
        // NhacCuaTui Chiều Đồng Quê bwlP9Ip3Uzc8
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ListIterator<Playable> playableIter = mService.newPlayableIter();
                    playableIter.add(mPlayable);
                    Playable newPlayable = mPlayable;
                    // Create MediaItem + Requery playable
                    MusicPlatformApi api = MusicPlatformApi.getApi(mPlayable.getPlatform());
                    if (mPlayable instanceof Song)
                        newPlayable = api.getSongInfo(mPlayable.getId());
                    else if (mPlayable instanceof Video)
                        newPlayable = api.getVideoInfo(mPlayable.getId());
                    String mediaUrl = api.getMediaUrl(newPlayable.getId());
                    final MediaItem fItem = MediaItem.fromUri(mediaUrl);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPlayer.setMediaItem(fItem);
                            mPlayer.prepare();
                            mPlayer.play();
                            mService.startForeground(NOTIFICATION_ID, mService.getNotification(mPlayable));;
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(PlayerPlayCommand.this.mService, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
