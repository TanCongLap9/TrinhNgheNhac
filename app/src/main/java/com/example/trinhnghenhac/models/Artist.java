package com.example.trinhnghenhac.models;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.constants.PlayableType;

import java.util.ArrayList;
import java.util.List;

public class Artist implements Playable {
    private String mId;
    @Nullable
    private String mName;
    @Nullable
    private String mImage;
    @NonNull
    private List<Song> mSongs;
    @NonNull
    private List<Playlist> mPlaylists;
    @NonNull
    private List<Video> mVideos;
    @MusicPlatform
    private int mPlatform;

    public Artist() {
        mSongs = new ArrayList<>();
        mPlaylists = new ArrayList<>();
        mVideos = new ArrayList<>();
        mPlatform = MusicPlatform.PLATFORM_UNKNOWN;
    }

    private Artist(Parcel in) {
        in.readInt(); // Playable type
        mId = in.readString();
        mName = in.readString();
        mImage = in.readString();
        mSongs = new ArrayList<>();
        mPlaylists = new ArrayList<>();
        mVideos = new ArrayList<>();
        in.readTypedList(mSongs, Song.CREATOR);
        in.readTypedList(mPlaylists, Playlist.CREATOR);
        in.readTypedList(mVideos, Video.CREATOR);
        mPlatform = in.readInt();
    }

    @Override
    public String getId() {
        return mId;
    }
    @Override
    public String getName() {
        return mName;
    }
    @Override
    public String getTitle() {
        return mName;
    }
    @Override
    public String getText() {
        return "";
    }
    @Override
    public String getImage() {
        return mImage;
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
    @Override
    public boolean isPremium() {
        return false;
    }
    @Override
    public int getPlatform() {
        return mPlatform;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(PlayableType.TYPE_ARTIST);
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mImage);
        parcel.writeTypedList(mSongs);
        parcel.writeTypedList(mPlaylists);
        parcel.writeTypedList(mVideos);
        parcel.writeInt(mPlatform);
    }

    @NonNull
    @Override
    public String toString() {
        return mId + " " + mName;
    }

    public static String concat(List<Artist> artists) {
        List<String> names = new ArrayList<>();
        for (Artist artist : artists)
            names.add(artist.getName());
        return String.join(", ", names);
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public static class Builder {
        private final Artist mArtist;

        public Builder() {
            mArtist = new Artist();
        }

        public Builder setId(@NonNull String id) {
            mArtist.mId = id;
            return this;
        }

        public Builder setName(@Nullable String name) {
            mArtist.mName = name;
            return this;
        }

        public Builder setImage(@Nullable String avatar) {
            mArtist.mImage = avatar;
            return this;
        }

        public Builder setSongs(@NonNull List<Song> songs) {
            mArtist.mSongs = songs;
            return this;
        }

        public Builder setPlaylists(@NonNull List<Playlist> playlists) {
            mArtist.mPlaylists = playlists;
            return this;
        }

        public Builder setVideos(@NonNull List<Video> videos) {
            mArtist.mVideos = videos;
            return this;
        }

        public Builder setPlatform(@MusicPlatform int platform) {
            mArtist.mPlatform = platform;
            return this;
        }

        public Artist build() {
            if (mArtist.mId == null)
                throw new IllegalArgumentException("Id is required.");
            if (mArtist.mPlatform == MusicPlatform.PLATFORM_UNKNOWN)
                throw new IllegalArgumentException("Platform is required.");
            return mArtist;
        }
    }
}
