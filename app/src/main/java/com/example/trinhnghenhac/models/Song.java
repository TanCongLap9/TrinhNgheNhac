package com.example.trinhnghenhac.models;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.constants.PlayableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Song implements Playable {
    private String mId;
    @Nullable
    private String mName;
    @Nullable
    private String mImage;
    private long mDuration; // in ms
    private long mHearts;
    private boolean mPremium;
    @NonNull
    private List<Artist> mArtists;
    private String mArtistsStr;
    @MusicPlatform
    private int mPlatform;

    private Song() {
        mPlatform = MusicPlatform.PLATFORM_UNKNOWN;
        mArtists = new ArrayList<>();
    }

    private Song(Parcel in) {
        in.readInt(); // Playable type
        mId = in.readString();
        mName = in.readString();
        mImage = in.readString();
        mDuration = in.readLong();
        mHearts = in.readLong();
        mPremium = in.readInt() != 0;
        // mArtists = Objects.requireNonNull(in.createTypedArrayList(Artist.CREATOR));
        mArtistsStr = in.readString();
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
    public long getDuration() {
        return mDuration;
    }
    public long getHearts() {
        return mHearts;
    }
    @Override
    public boolean isPremium() {
        return mPremium;
    }
    public String getArtists() {
        return mArtistsStr;
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
        parcel.writeInt(PlayableType.TYPE_SONG);
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mImage);
        parcel.writeLong(mDuration);
        parcel.writeLong(mHearts);
        parcel.writeInt(mPremium ? 1 : 0); // writeBoolean() requires API 29
        // parcel.writeTypedList(mArtists);
        parcel.writeString(mArtistsStr);
        parcel.writeInt(mPlatform);
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public static class Builder {
        private final Song mSong;

        public Builder() {
            mSong = new Song();
        }

        public Builder setId(@NonNull String id) {
             mSong.mId = id;
             return this;
        }

        public Builder setName(@Nullable String name) {
            mSong.mName = name;
            return this;
        }

        public Builder setImage(@Nullable String image) {
            mSong.mImage = image;
            return this;
        }

        public Builder setDuration(long duration) {
            mSong.mDuration = duration;
            return this;
        }

        public Builder setHearts(long hearts) {
            mSong.mHearts = hearts;
            return this;
        }

        public Builder setPremium(boolean premium) {
            mSong.mPremium = premium;
            return this;
        }

        public Builder setArtists(@NonNull List<Artist> artists) {
            mSong.mArtists = artists;
            mSong.mArtistsStr = Artist.concat(artists);
            return this;
        }

        public Builder setPlatform(@MusicPlatform int platform) {
            mSong.mPlatform = platform;
            return this;
        }

        public Song build() {
            if (mSong.mId == null)
                throw new IllegalArgumentException("Id is required.");
            if (mSong.mPlatform == MusicPlatform.PLATFORM_UNKNOWN)
                throw new IllegalArgumentException("Platform is required.");
            return mSong;
        }
    }
}