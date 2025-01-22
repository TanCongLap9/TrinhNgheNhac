package com.example.trinhnghenhac.ui.explore;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.models.Playlist;

import java.io.Serializable;
import java.util.List;

public class ExploreData implements Serializable, Parcelable {
    private List<Playable> mChill;
    private List<Playable> mVietnamese;
    private List<Playable> mChinese;
    private List<Playable> mNewest;

    public ExploreData() {

    }

    private ExploreData(Parcel in) {
        mChill = in.createTypedArrayList(Playable.CREATOR);
        mVietnamese = in.createTypedArrayList(Playable.CREATOR);
        mChinese = in.createTypedArrayList(Playable.CREATOR);
        mNewest = in.createTypedArrayList(Playable.CREATOR);
    }

    public List<Playable> getChill() {
        return mChill;
    }
    public void setChill(List<Playable> chill) {
        this.mChill = chill;
    }
    public List<Playable> getVietnamese() {
        return mVietnamese;
    }
    public void setVietnamese(List<Playable> vietnamese) {
        this.mVietnamese = vietnamese;
    }
    public List<Playable> getChinese() {
        return mChinese;
    }
    public void setChinese(List<Playable> chinese) {
        this.mChinese = chinese;
    }
    public List<Playable> getNewest() {
        return mNewest;
    }
    public void setNewest(List<Playable> newest) {
        this.mNewest = newest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedList(mChill);
        parcel.writeTypedList(mVietnamese);
        parcel.writeTypedList(mChinese);
        parcel.writeTypedList(mNewest);
    }

    public static final Creator<ExploreData> CREATOR = new Creator<ExploreData>() {
        @Override
        public ExploreData createFromParcel(Parcel in) {
            return new ExploreData(in);
        }

        @Override
        public ExploreData[] newArray(int size) {
            return new ExploreData[size];
        }
    };
}
