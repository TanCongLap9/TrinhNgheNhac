package com.example.trinhnghenhac.observables;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Observable;

/**
 * A remake of android.databinding.ObservableParcelable
 */
public class ObservableParcelable<T extends Parcelable> extends Observable implements Serializable, Parcelable {
    private T mValue;

    public ObservableParcelable() {

    }

    public ObservableParcelable(T value) {
        mValue = value;
    }

    private ObservableParcelable(Parcel in) {
        mValue = in.readParcelable(getClass().getClassLoader());
    }

    public T get() {
        return mValue;
    }

    public boolean has() {
        return mValue != null;
    }

    public void set(T value) {
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
        parcel.writeParcelable(mValue, flags);
    }

    public static final Creator<ObservableParcelable<? extends Parcelable>> CREATOR = new Creator<ObservableParcelable<? extends Parcelable>>() {
        @Override
        public ObservableParcelable<? extends Parcelable> createFromParcel(Parcel in) {
            return new ObservableParcelable<>(in);
        }

        @Override
        public ObservableParcelable<? extends Parcelable>[] newArray(int size) {
            return new ObservableParcelable<?>[size];
        }
    };
}
