package com.example.trinhnghenhac.ui.player;

import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import com.example.trinhnghenhac.R;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BlurMaskFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.Player;

import com.bumptech.glide.Glide;
import com.example.trinhnghenhac.databinding.FragmentPlayerBinding;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.services.MediaPlayerService;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayerFragment extends Fragment implements ServiceConnection {
    private FragmentPlayerBinding b;
    private MediaPlayerService.LocalBinder binder;
    private final Player.Listener listener = new Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int playbackState) {
            Player.Listener.super.onPlaybackStateChanged(playbackState);
            if (playbackState == Player.STATE_READY) {
                updateSongInfo();
            }
        }
    };

    public PlayerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentPlayerBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        binder = (MediaPlayerService.LocalBinder) iBinder;
        updateSongInfo();
        binder.getMediaPlayer().addListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent(getContext(), MediaPlayerService.class);
        getContext().bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unbindService(this);
    }

    private void updateSongInfo() {
        if (getActivity() == null) return;
        Playable playable = binder.getPlayable();
        b.fragmentPlayerTitle.setText(playable.getTitle());
        b.fragmentPlayerText.setText(playable.getText());
        Glide.with(this).load(playable.getImage()).transform(new BlurTransformation(48)).into(b.fragmentPlayerBackground);
        Glide.with(this).load(playable.getImage()).into(b.fragmentPlayerImage);
    }

    @Override
    public void onBindingDied(ComponentName name) {
        ServiceConnection.super.onBindingDied(name);
        Log.d("a", "onBindingDied: ");
    }

    @Override
    public void onNullBinding(ComponentName name) {
        ServiceConnection.super.onNullBinding(name);
        Log.d("a", "onNullBinding: ");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        binder = null;
    }
}
