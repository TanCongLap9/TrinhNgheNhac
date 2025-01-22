package com.example.trinhnghenhac.commands;

import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.media3.exoplayer.ExoPlayer;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.services.MediaPlayerService;
import com.example.trinhnghenhac.utils.ListIteratorUtils;

import java.io.IOException;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;

public class PlayerEnqueueCommand extends PlayerCommandBase {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Playable mPlayable;
    public PlayerEnqueueCommand(MediaPlayerService service, ExoPlayer player, ExecutorService executor, ListIterator<Playable> playableIter, Playable playable) {
        super(service, player, executor, playableIter);
        mPlayable = playable;
    }

    @Override
    public void execute() {
        if (!mPlayableIter.hasPrevious() && !mPlayableIter.hasNext()) {
            Toast.makeText(mService, R.string.song_enqueue_failed_not_started, Toast.LENGTH_SHORT).show();
            return;
        }
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Playable newPlayable = mPlayable;
                    if (mPlayable instanceof Song)
                        newPlayable = MusicPlatformApi.getApi(mPlayable.getPlatform()).getSongInfo(mPlayable.getId());
                    else if (mPlayable instanceof Video)
                        newPlayable = MusicPlatformApi.getApi(mPlayable.getPlatform()).getVideoInfo(mPlayable.getId());
                    String playableName = mPlayable.getTitle();
                    int currentIndex = mPlayableIter.nextIndex();
                    ListIteratorUtils.goTo(mPlayableIter, ListIteratorUtils.LAST);
                    mPlayableIter.add(newPlayable);
                    ListIteratorUtils.goTo(mPlayableIter, currentIndex);
                    String fPlayableName = playableName;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mService, mService.getString(R.string.song_enqueue_complete, fPlayableName), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mService, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
