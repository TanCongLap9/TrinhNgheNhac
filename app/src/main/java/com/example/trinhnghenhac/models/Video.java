package com.example.trinhnghenhac.models;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.constants.PlayableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Video implements Playable {
    private String mId;
    @Nullable
    private String mName;
    @Nullable
    private String mImage;
    private long mDuration;
    @Nullable
    private String mStreamUrl;
    private long mHearts;
    private boolean mPremium;
    @NonNull
    private List<Artist> mArtists;
    private String mArtistsStr;
    @MusicPlatform
    private int mPlatform;

    private Video() {
        mPlatform = MusicPlatform.PLATFORM_UNKNOWN;
        mArtists = new ArrayList<>();
    }

    private Video(Parcel in) {
        in.readInt(); // Playable type
        mId = in.readString();
        mName = in.readString();
        mImage = in.readString();
        mDuration = in.readLong();
        mStreamUrl = in.readString();
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
    public String getStreamUrl() {
        return mStreamUrl;
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
        parcel.writeInt(PlayableType.TYPE_VIDEO);
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mImage);
        parcel.writeLong(mDuration);
        parcel.writeString(mStreamUrl);
        parcel.writeLong(mHearts);
        parcel.writeInt(mPremium ? 1 : 0); // writeBoolean() requires API 29
        // parcel.writeTypedList(mArtists);
        parcel.writeString(mArtistsStr);
        parcel.writeInt(mPlatform);
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public static class Builder {
        private final Video mVideo;

        public Builder() {
            mVideo = new Video();
        }

        public Builder setId(@NonNull String id) {
            mVideo.mId = id;
            return this;
        }

        public Builder setName(@Nullable String name) {
            mVideo.mName = name;
            return this;
        }

        public Builder setImage(@Nullable String image) {
            mVideo.mImage = image;
            return this;
        }

        public Builder setDuration(long duration) {
            mVideo.mDuration = duration;
            return this;
        }

        public Builder setStreamUrl(@Nullable String streamUrl) {
            mVideo.mStreamUrl = streamUrl;
            return this;
        }

        public Builder setHearts(long hearts) {
            mVideo.mHearts = hearts;
            return this;
        }

        public Builder setPremium(boolean premium) {
            mVideo.mPremium = premium;
            return this;
        }

        public Builder setArtists(@NonNull List<Artist> artists) {
            mVideo.mArtists = artists;
            mVideo.mArtistsStr = Artist.concat(artists);
            return this;
        }

        public Builder setPlatform(@MusicPlatform int platform) {
            mVideo.mPlatform = platform;
            return this;
        }

        public Video build() {
            if (mVideo.mId == null)
                throw new IllegalArgumentException("Id is required.");
            if (mVideo.mPlatform == MusicPlatform.PLATFORM_UNKNOWN)
                throw new IllegalArgumentException("Platform is required.");
            return mVideo;
        }
    }
}
