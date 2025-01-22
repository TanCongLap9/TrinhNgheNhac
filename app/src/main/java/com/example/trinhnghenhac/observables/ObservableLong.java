package com.example.trinhnghenhac.observables;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Observable;

/**
 * A remake of android.databinding.ObservableLong
 */
public class ObservableLong extends Observable implements Serializable, Parcelable {
    private long mValue;

    public ObservableLong() {

    }

    public ObservableLong(long value) {
        mValue = value;
    }

    private ObservableLong(Parcel in) {
        mValue = in.readLong();
    }

    public long get() {
        return mValue;
    }

    public boolean has() {
        return true;
    }

    public void set(long value) {
        mValue = value;
        setChanged();
        notifyObservers(mValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(mValue);
    }

    public static final Creator<ObservableLong> CREATOR = new Creator<ObservableLong>() {
        @Override
        public ObservableLong createFromParcel(Parcel in) {
            return new ObservableLong(in);
        }

        @Override
        public ObservableLong[] newArray(int size) {
            return new ObservableLong[size];
        }
    };
}
