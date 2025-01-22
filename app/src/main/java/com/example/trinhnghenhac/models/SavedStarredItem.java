package com.example.trinhnghenhac.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.trinhnghenhac.constants.FirebaseMusicPlatform;
import com.example.trinhnghenhac.constants.FirebasePlayableType;

import java.io.Serializable;
import java.util.Objects;

public class SavedStarredItem implements Serializable, Parcelable {
    private String accountId;
    private String itemId;
    @FirebaseMusicPlatform
    private String platform;
    @FirebasePlayableType
    private String type;

    private SavedStarredItem() {

    }

    public SavedStarredItem(@NonNull String accountId, @NonNull String itemId, @NonNull @FirebaseMusicPlatform String platform, @NonNull @FirebasePlayableType String type) {
        this.accountId = accountId;
        this.itemId = itemId;
        this.platform = platform;
        this.type = type;
    }

    public SavedStarredItem(Parcel in) {
        accountId = Objects.requireNonNull(in.readString());
        itemId = Objects.requireNonNull(in.readString());
        platform = Objects.requireNonNull(in.readString());
        type = Objects.requireNonNull(in.readString());
    }

    @NonNull
    public String getAccountId() {
        return accountId;
    }

    @NonNull
    public String getItemId() {
        return itemId;
    }

    @NonNull
    @FirebaseMusicPlatform
    public String getPlatform() {
        return platform;
    }

    @NonNull
    @FirebasePlayableType
    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(accountId);
        parcel.writeString(itemId);
        parcel.writeString(platform);
        parcel.writeString(type);
    }

    public static final Creator<SavedStarredItem> CREATOR = new Creator<SavedStarredItem>() {
        @Override
        public SavedStarredItem createFromParcel(Parcel in) {
            return new SavedStarredItem(in);
        }

        @Override
        public SavedStarredItem[] newArray(int size) {
            return new SavedStarredItem[size];
        }
    };
}
