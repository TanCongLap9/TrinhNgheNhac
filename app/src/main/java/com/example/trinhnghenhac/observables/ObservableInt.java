package com.example.trinhnghenhac.observables;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * A remake of android.databinding.ObservableLong
 */
public class ObservableInt extends Observable implements Serializable, Parcelable {
    private int mValue;

    public ObservableInt() {

    }

    public ObservableInt(int value) {
        mValue = value;
    }

    private ObservableInt(Parcel in) {
        mValue = in.readInt();
    }

    public int get() {
        return mValue;
    }

    public boolean has() {
        return true;
    }

    public void set(int value) {
        mValue = value;
        setChanged();
        notifyObservers(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mValue);
    }

    public static final Creator<ObservableInt> CREATOR = new Creator<ObservableInt>() {
        @Override
        public ObservableInt createFromParcel(Parcel in) {
            return new ObservableInt(in);
        }

        @Override
        public ObservableInt[] newArray(int size) {
            return new ObservableInt[size];
        }
    };
}
