package com.example.trinhnghenhac.ui.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.Player;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.databinding.FragmentLyricBinding;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.services.MediaPlayerService;
import com.example.trinhnghenhac.adapters.LyricsAdapter;
import com.example.trinhnghenhac.models.Lyrics;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LyricsFragment extends Fragment {
    private FragmentLyricBinding b;
    private MediaPlayerService.LocalBinder binder;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private LyricsScroller scroller;
    private Lyrics lyrics;
    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (MediaPlayerService.LocalBinder) iBinder;
            binder.getMediaPlayer().addListener(listener);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // ZZD9DO7A Xin má rước dâu : Lời từ, nhanh
                        // ZUOFC78E Chiều đồng quê : Lời từ
                        // ZUUECEIC Khuê Mộc Lang : Lời từ
                        // ZW9CB7EF Baam : Lời dòng
                        // ZZAZF76F Thương em thiệt hông (Đại mèo remix) : Chưa có lời
                        Playable playable = binder.getPlayable();
                        if (playable instanceof Song) {
                            Song song = (Song) playable;
                            MusicPlatformApi api = MusicPlatformApi.getApi(song.getPlatform());
                            lyrics = api.getLyrics(song.getId());
                        } else if (playable instanceof Video) {
                            Video video = (Video) playable;
                            MusicPlatformApi api = MusicPlatformApi.getApi(video.getPlatform());
                            lyrics = api.getLyrics(video.getId());
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (lyrics != null) {
                                    LyricsAdapter adapter = new LyricsAdapter(b.activityLyricLyric, lyrics);
                                    b.activityLyricLyric.setAdapter(adapter);
                                    if (scroller != null) scroller.stop();
                                    scroller = new LyricsScroller(adapter, lyrics);
                                    scroller.start((int) binder.getMediaPlayer().getCurrentPosition());
                                } else b.activityLyricNotFound.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binder = null;
        }
    };

    private final Player.Listener listener = new Player.Listener() {
        @Override
        public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
            Player.Listener.super.onPositionDiscontinuity(oldPosition, newPosition, reason);
            scroller.start((int) binder.getMediaPlayer().getCurrentPosition());
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Player.Listener.super.onIsPlayingChanged(isPlaying);
            if (isPlaying)
                scroller.start((int) binder.getMediaPlayer().getCurrentPosition());
            else scroller.stop();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentLyricBinding.inflate(getLayoutInflater(), null, false);
        b.activityLyricLyric.post(new Runnable() {
            @Override
            public void run() {
                int halfHeight = b.activityLyricLyric.getHeight() / 2;
                b.activityLyricLyric.setPadding(0, halfHeight, 0, halfHeight);
            }
        });
        return b.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binder != null) {
            binder.getMediaPlayer().removeListener(listener);
            getContext().unbindService(conn);
        }
        if (scroller != null) scroller.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent(getContext(), MediaPlayerService.class);
        getContext().bindService(intent, conn, Context.BIND_AUTO_CREATE);
        if (binder != null && binder.getMediaPlayer() != null && scroller != null && lyrics != null)
            scroller.start((int) binder.getMediaPlayer().getCurrentPosition());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}