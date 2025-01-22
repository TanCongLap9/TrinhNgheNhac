package com.example.trinhnghenhac.ui.searchresults;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.models.Artist;
import com.example.trinhnghenhac.models.Playlist;
import com.example.trinhnghenhac.models.SearchResults;
import com.example.trinhnghenhac.models.Song;
import com.example.trinhnghenhac.models.Video;
import com.example.trinhnghenhac.utils.RandomUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsModelImpl implements SearchResultsContract.Model {
    @NonNull
    private final ObservableParcelable<SearchResults> mSearchResultsObservable;

    @NonNull
    private final MusicPlatformApi[] apis;

    public SearchResultsModelImpl() {
        apis = MusicPlatformApi.getAll();
        mSearchResultsObservable = new ObservableParcelable<>();
    }

    public SearchResultsModelImpl(@NonNull ObservableParcelable<SearchResults> observable) {
        apis = MusicPlatformApi.getAll();
        mSearchResultsObservable = observable;
    }

    public SearchResultsModelImpl(@NonNull SearchResults data) {
        apis = MusicPlatformApi.getAll();
        mSearchResultsObservable = new ObservableParcelable<>(data);
    }

    @Nullable
    public Cursor getSearchHints(CharSequence query) throws IOException {
        return apis[MusicPlatform.PLATFORM_ZINGMP3].getSearchHints(query);
    }

    @NonNull
    @Override
    public ObservableParcelable<SearchResults> getObservable() {
        return mSearchResultsObservable;
    }

    @Nullable
    @Override
    public SearchResults getData() {
        return mSearchResultsObservable.get();
    }

    @NonNull
    @Override
    public SearchResults getSearchResults(CharSequence query) throws IOException {
        SearchResults zingMp3SearchResults = apis[MusicPlatform.PLATFORM_ZINGMP3].getSearchResults(query);
        SearchResults nhacCuaTuiSearchResults = apis[MusicPlatform.PLATFORM_NHACCUATUI].getSearchResults(query);
        SearchResults soundCloudSearchResults = apis[MusicPlatform.PLATFORM_SOUNDCLOUD].getSearchResults(query);

        List<Artist> artists = RandomUtils.mergeRandom(
                zingMp3SearchResults == null ? new ArrayList<>() : zingMp3SearchResults.getArtists(),
                nhacCuaTuiSearchResults == null ? new ArrayList<>() : nhacCuaTuiSearchResults.getArtists(),
                soundCloudSearchResults == null ? new ArrayList<>() : soundCloudSearchResults.getArtists());
        List<Playlist> playlists = RandomUtils.mergeRandom(
                zingMp3SearchResults == null ? new ArrayList<>() : zingMp3SearchResults.getPlaylists(),
                nhacCuaTuiSearchResults == null ? new ArrayList<>() : nhacCuaTuiSearchResults.getPlaylists(),
                soundCloudSearchResults == null ? new ArrayList<>() : soundCloudSearchResults.getPlaylists());
        List<Song> songs = RandomUtils.mergeRandom(
                zingMp3SearchResults == null ? new ArrayList<>() : zingMp3SearchResults.getSongs(),
                nhacCuaTuiSearchResults == null ? new ArrayList<>() : nhacCuaTuiSearchResults.getSongs(),
                soundCloudSearchResults == null ? new ArrayList<>() : soundCloudSearchResults.getSongs());
        List<Video> videos = RandomUtils.mergeRandom(
                zingMp3SearchResults == null ? new ArrayList<>() : zingMp3SearchResults.getVideos(),
                nhacCuaTuiSearchResults == null ? new ArrayList<>() : nhacCuaTuiSearchResults.getVideos(),
                soundCloudSearchResults == null ? new ArrayList<>() : soundCloudSearchResults.getVideos());
        return new SearchResults(artists, songs, playlists, videos, new int[]{0, 1, 2, 3});
    }
}
