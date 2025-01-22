package com.example.trinhnghenhac.commands;

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
import com.example.trinhnghenhac.utils.ListIteratorUtils;

import java.io.IOException;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;

public class PlayerNextCommand extends PlayerCommandBase {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    public PlayerNextCommand(MediaPlayerService service, ExoPlayer player, ExecutorService executor, ListIterator<Playable> playableIter) {
        super(service, player, executor, playableIter);
    }

    @Override
    public void execute() {
        Playable playable = ListIteratorUtils.getCurrent(mPlayableIter);
        if (mPlayableIter.hasNext()) playable = mPlayableIter.next();
        final Playable fPlayable = playable;
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String mediaUrl = MusicPlatformApi.getApi(fPlayable.getPlatform()).getMediaUrl(fPlayable.getId());
                    MediaItem item = MediaItem.fromUri(mediaUrl);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPlayer.setMediaItem(item);
                            mPlayer.prepare();
                            mPlayer.play();
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(mService, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
