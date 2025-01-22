package com.example.trinhnghenhac.commands;

import android.content.Context;

import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.services.MediaPlayerService;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;

public class PlayerRepeatCommand extends PlayerCommandBase {
    public PlayerRepeatCommand(MediaPlayerService service, ExoPlayer player, ExecutorService executor, ListIterator<Playable> playableIter) {
        super(service, player, executor, playableIter);
    }

    @Override
    public void execute() {
        mService.cycleRepeatMode();
    }
}
