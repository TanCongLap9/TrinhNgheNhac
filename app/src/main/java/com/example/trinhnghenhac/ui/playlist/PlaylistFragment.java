package com.example.trinhnghenhac.ui.playlist;

import static com.example.trinhnghenhac.utils.AssertUtils.NE;
import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;
import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.adapters.PlayableRowsAdapter;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.databinding.FragmentPlaylistBinding;
import com.example.trinhnghenhac.models.Playlist;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class PlaylistFragment extends Fragment implements PlaylistContract.View {
    private FragmentPlaylistBinding b;
    private PlayableRowsAdapter adapter;
    private PlaylistPresenterImpl mPresenter;
    private boolean hasError;
    private String playlistId;
    private int platform;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        playlistId = getArguments().getString(Extras.EXTRA_PLAYLISTID);
        assertNotNull(playlistId);
        platform = getArguments().getInt(Extras.EXTRA_PLATFORM, MusicPlatform.PLATFORM_UNKNOWN);
        assertThat(platform, NE, MusicPlatform.PLATFORM_UNKNOWN);

        if (savedInstanceState == null)
            mPresenter = new PlaylistPresenterImpl(this, platform);
        else
            mPresenter = new PlaylistPresenterImpl(this, savedInstanceState.getParcelable(Extras.DATA_PLAYLIST));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentPlaylistBinding.inflate(getLayoutInflater(), null, false);
        initViews();
        mPresenter.loadOrFetchData(playlistId);
        return b.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Extras.DATA_PLAYLIST, mPresenter.getData());
        Log.d("Save playlist", "onSaveInstanceState: ");
    }

    @Override
    public void displaySync(Playlist playlist) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.fragmentPlaylistPlatform.setImageResource(MusicPlatformApi.toResId(platform));
                b.fragmentPlaylistTitle.setText(playlist.getName());
                b.fragmentPlaylistText.setText(R.string.type_playlist);
                Glide.with(PlaylistFragment.this).load(playlist.getImage()).into(b.fragmentPlaylistImage);
                adapter.update(playlist.getSongs());
            }
        });
    }

    private void initViews() {
        adapter = new PlayableRowsAdapter(getActivity(), false, false);
        b.fragmentPlaylistList.setAdapter(adapter);
    }

    @Override
    public void alertError(IOException e) {
        if (hasError) return;
        Snackbar.make(b.getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show();
        hasError = true;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("Restore playlist", "onSaveInstanceState: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
        mPresenter.close();
    }
}