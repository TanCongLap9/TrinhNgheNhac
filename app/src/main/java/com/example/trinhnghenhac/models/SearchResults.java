package com.example.trinhnghenhac.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResults implements Serializable, Parcelable {
    private final List<Artist> mArtists;
    private final List<Song> mSongs;
    private final List<Playlist> mPlaylists;
    private final List<Video> mVideos;
    private int[] mOrder;

    private SearchResults() {
        mArtists = new ArrayList<>();
        mSongs = new ArrayList<>();
        mPlaylists = new ArrayList<>();
        mVideos = new ArrayList<>();
    }

    public SearchResults(List<Artist> artists, List<Song> songs, List<Playlist> playlists, List<Video> videos, int[] order) {
        mArtists = artists;
        mSongs = songs;
        mPlaylists = playlists;
        mVideos = videos;
        mOrder = order;
    }

    private SearchResults(Parcel in) {
        mArtists = in.createTypedArrayList(Artist.CREATOR);
        mSongs = in.createTypedArrayList(Song.CREATOR);
        mPlaylists = in.createTypedArrayList(Playlist.CREATOR);
        mVideos = in.createTypedArrayList(Video.CREATOR);
        mOrder = in.createIntArray();
    }

    public List<Artist> getArtists() {
        return mArtists;
    }
    public List<Song> getSongs() {
        return mSongs;
    }
    public List<Playlist> getPlaylists() {
        return mPlaylists;
    }
    public List<Video> getVideos() {
        return mVideos;
    }
    public int[] getOrder() {
        return mOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedList(mArtists);
        parcel.writeTypedList(mSongs);
        parcel.writeTypedList(mPlaylists);
        parcel.writeTypedList(mVideos);
        parcel.writeIntArray(mOrder);
    }

    public static final Creator<SearchResults> CREATOR = new Creator<SearchResults>() {
        @Override
        public SearchResults createFromParcel(Parcel in) {
            return new SearchResults(in);
        }

        @Override
        public SearchResults[] newArray(int size) {
            return new SearchResults[size];
        }
    };
}
