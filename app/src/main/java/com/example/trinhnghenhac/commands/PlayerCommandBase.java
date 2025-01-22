package com.example.trinhnghenhac.commands;

import android.content.Context;

import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.services.MediaPlayerService;

import java.util.ListIterator;
import java.util.concurrent.ExecutorService;

public abstract class PlayerCommandBase {
    protected Player mPlayer;
    protected ListIterator<Playable> mPlayableIter;
    protected ExecutorService mExecutor;
    protected MediaPlayerService mService;
    public PlayerCommandBase(MediaPlayerService service, ExoPlayer player, ExecutorService executor, ListIterator<Playable> playableIter) {
        mPlayer = player;
        mService = service;
        mPlayableIter = playableIter;
        mExecutor = executor;
    }

    public abstract void execute();
}
