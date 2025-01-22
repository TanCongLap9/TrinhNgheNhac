package com.example.trinhnghenhac.models;

import android.os.Parcel;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.constants.PlayableType;
import com.example.trinhnghenhac.constants.PlaylistFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Playlist implements Playable {
    private String mId;
    @Nullable
    private String mName;
    @Nullable
    private String mImage;
    private int mFlags;
    @NonNull
    private List<Artist> mArtists;
    private String mArtistsStr;
    @NonNull
    private List<Song> mSongs;
    @NonNull
    private List<Video> mVideos;
    @MusicPlatform
    private int mPlatform;

    private Playlist() {
        mPlatform = MusicPlatform.PLATFORM_UNKNOWN;
        mSongs = new ArrayList<>();
        mVideos = new ArrayList<>();
        mArtists = new ArrayList<>();
    }

    private Playlist(Parcel in) {
        in.readInt(); // Playable type
        mId = in.readString();
        mName = in.readString();
        mImage = in.readString();
        mFlags = in.readInt();
        // mArtists = Objects.requireNonNull(in.createTypedArrayList(Artist.CREATOR));
        mArtistsStr = in.readString();
        mSongs = Objects.requireNonNull(in.createTypedArrayList(Song.CREATOR));
        mVideos = Objects.requireNonNull(in.createTypedArrayList(Video.CREATOR));
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
        return mArtistsStr;
    }
    @Override
    public String getImage() {
        return mImage;
    }
    @PlaylistFlag
    public int getFlags() {
        return mFlags;
    }
    public boolean isAlbum() {
        return (mFlags & PlaylistFlag.FLAG_ALBUM) != 0;
    }
    public boolean isSingle() {
        return (mFlags & PlaylistFlag.FLAG_SINGLE) != 0;
    }
    public boolean isOfficial() {
        return (mFlags & PlaylistFlag.FLAG_OFFICIAL) != 0;
    }
    public boolean isIndie() {
        return (mFlags & PlaylistFlag.FLAG_INDIE) != 0;
    }
    public boolean isPrivate() {
        return (mFlags & PlaylistFlag.FLAG_PRIVATE) != 0;
    }
    public String getArtists() {
        return mArtistsStr;
    }
    public List<Song> getSongs() {
        return mSongs;
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
        parcel.writeInt(PlayableType.TYPE_PLAYLIST);
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mImage);
        parcel.writeInt(mFlags);
        // parcel.writeTypedList(mArtists);
        parcel.writeString(mArtistsStr);
        parcel.writeTypedList(mSongs);
        parcel.writeTypedList(mVideos);
        parcel.writeInt(mPlatform);
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public static class Builder {
        private final Playlist mPlaylist;

        public Builder() {
            mPlaylist = new Playlist();
        }

        public Builder setId(@NonNull String id) {
            mPlaylist.mId = id;
            return this;
        }

        public Builder setName(@Nullable String name) {
            mPlaylist.mName = name;
            return this;
        }

        public Builder setImage(@Nullable String image) {
            mPlaylist.mImage = image;
            return this;
        }

        public Builder setFlags(@PlaylistFlag int flags) {
            mPlaylist.mFlags = flags;
            return this;
        }

        public Builder setArtists(@NonNull List<Artist> artists) {
            mPlaylist.mArtists = artists;
            mPlaylist.mArtistsStr = Artist.concat(artists);
            return this;
        }

        public Builder setSongs(@NonNull List<Song> songs) {
            mPlaylist.mSongs = songs;
            return this;
        }

        public Builder setVideos(@NonNull List<Video> videos) {
            mPlaylist.mVideos = videos;
            return this;
        }

        public Builder setPlatform(@MusicPlatform int platform) {
            mPlaylist.mPlatform = platform;
            return this;
        }

        public Playlist build() {
            if (mPlaylist.mId == null)
                throw new IllegalArgumentException("Id is required.");
            if (mPlaylist.mPlatform == MusicPlatform.PLATFORM_UNKNOWN)
                throw new IllegalArgumentException("Platform is required.");
            return mPlaylist;
        }
    }
}
