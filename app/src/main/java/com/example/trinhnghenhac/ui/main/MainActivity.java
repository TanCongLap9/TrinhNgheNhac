package com.example.trinhnghenhac.ui.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.PlayerAction;
import com.example.trinhnghenhac.databinding.ActivityMainBinding;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.User;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.services.MediaPlayerService;
import com.example.trinhnghenhac.ui.player.PlayerActivity;
import com.example.trinhnghenhac.utils.DateUtils;
import com.example.trinhnghenhac.utils.ServiceUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.media3.common.Player;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding b;
    private MediaPlayerService.LocalBinder binder;
    private User user;
    private final Player.Listener listener = new Player.Listener() {
        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Player.Listener.super.onIsPlayingChanged(isPlaying);
            updatePlayPause(isPlaying);
        }
    };

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (MediaPlayerService.LocalBinder) iBinder;
            Playable playable = binder.getPlayable();
            if (playable instanceof Song) {
                Song song = (Song) playable;
                b.activityMainNowPlaying.nowPlayingSong.setText(
                        song.getName() + " - " + song.getArtists());
            } else if (playable instanceof Video) {
                Video video = (Video) playable;
                b.activityMainNowPlaying.nowPlayingSong.setText(
                        video.getName() + " - " + video.getArtists());
            }
            binder.getMediaPlayer().addListener(listener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra(Extras.EXTRA_USER);
        b = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        initActionBar();
        initTooltips();
        initUserPanel();
        initEvents();
        initNav();
        setContentView(b.getRoot());
    }

    private void initEvents() {
        b.activityMainNowPlaying.getRoot().setOnClickListener(this);
        b.activityMainNowPlaying.nowPlayingPlayPause.setOnClickListener(this);
        b.activityMainNowPlaying.nowPlayingNext.setOnClickListener(this);
        b.activityMainNowPlaying.nowPlayingPrevious.setOnClickListener(this);
    }

    private void initTooltips() {
        TooltipCompat.setTooltipText(b.activityMainNowPlaying.nowPlayingPlayPause, getString(R.string.media_player_pause));
        TooltipCompat.setTooltipText(b.activityMainNowPlaying.nowPlayingNext, getString(R.string.media_player_next));
        TooltipCompat.setTooltipText(b.activityMainNowPlaying.nowPlayingPrevious, getString(R.string.media_player_previous));
    }

    private void updatePlayPause(boolean isPlaying) {
        if (isPlaying) {
            b.activityMainNowPlaying.nowPlayingPlayPause.setIconResource(R.drawable.round_pause_circle_outline_24);
            TooltipCompat.setTooltipText(b.activityMainNowPlaying.nowPlayingPlayPause, getString(R.string.media_player_pause));
        } else {
            b.activityMainNowPlaying.nowPlayingPlayPause.setIconResource(R.drawable.round_play_circle_outline_24);
            TooltipCompat.setTooltipText(b.activityMainNowPlaying.nowPlayingPlayPause, getString(R.string.media_player_play));
        }
    }

    @Override
    public void onClick(View view) {
        if (view == b.activityMainNowPlaying.getRoot()) {
            Intent playerIntent = new Intent(this, PlayerActivity.class);
            startActivity(playerIntent);
        }
        else if (view == b.activityMainNowPlaying.nowPlayingPlayPause) {
            Intent intent = new Intent(this, MediaPlayerService.class);
            intent.setAction(PlayerAction.ACTION_PLAY_PAUSE);
            startService(intent);
        } else if (view == b.activityMainNowPlaying.nowPlayingPrevious) {
            Intent intent = new Intent(this, MediaPlayerService.class);
            intent.setAction(PlayerAction.ACTION_PREVIOUS);
            startService(intent);
        } else if (view == b.activityMainNowPlaying.nowPlayingNext) {
            Intent intent = new Intent(this, MediaPlayerService.class);
            intent.setAction(PlayerAction.ACTION_NEXT);
            startService(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (binder != null) {
            binder.getMediaPlayer().removeListener(listener);
            unbindService(conn);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        b.getRoot().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ServiceUtils.running) {
                    b.activityMainNowPlaying.getRoot().setVisibility(View.VISIBLE);
                    Intent intent = new Intent(MainActivity.this, MediaPlayerService.class);
                    bindService(intent, conn, BIND_AUTO_CREATE);
                } else {
                    b.activityMainNowPlaying.getRoot().setVisibility(View.GONE);
                }
            }
        }, 500);
    }

    private void initNav() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_explore, R.id.navigation_library, R.id.navigation_categories)
                .build();
        NavController navController = Navigation.findNavController(b.getRoot().findViewById(R.id.nav_host_fragment));
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(b.navView, navController);
    }

    private void initActionBar() {
        b.activityMainActionbar.actionbarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.activityMainUser.getRoot().setVisibility(
                        b.activityMainUser.getRoot().getVisibility() == View.VISIBLE
                                ? View.GONE
                                : View.VISIBLE
                );
            }
        });
        setSupportActionBar(b.activityMainActionbar.actionbarActionbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setLogo(R.drawable.logo48);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    private void initUserPanel() {
        b.activityMainUser.getRoot().setVisibility(View.GONE);
        b.activityMainUser.contentUserName.setText(user.getName());
        b.activityMainUser.contentUserEmail.setText(user.getEmail());
        b.activityMainUser.contentUserBirth.setText(DateUtils.toString(user.getBirth()));
        b.activityMainUser.contentUserPhone.setText(user.getPhone());
        b.activityMainUser.contentUserSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // FirebaseUtils.signOut();
    }
}