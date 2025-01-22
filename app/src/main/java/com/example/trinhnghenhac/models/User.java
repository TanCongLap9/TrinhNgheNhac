package com.example.trinhnghenhac.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * User that logins into the app
 */
public class User implements Serializable, Parcelable {
    @Nullable
    private String id;
    @Nullable
    private String name;
    @Nullable
    private String email;
    @Nullable
    private Date birth;
    @Nullable
    private String phone;
    @Nullable
    private String password; // Encoded

    private User() {

    }

    public User(@Nullable String id) {
        this.id = id;
    }

    public User(@Nullable String id, @Nullable String name, @Nullable String email, @Nullable Date birth, @Nullable String phone, @Nullable String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.password = password;
    }

    public User(@Nullable String name, @Nullable String email, @Nullable Date birth, @Nullable String phone, @Nullable String password) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.password = password;
    }

    private User(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        birth = (Date) in.readSerializable();
        phone = in.readString();
        password = in.readString();
    }

    @Nullable
    public String getId() {
        return id;
    }
    @Nullable
    public String getName() {
        return name;
    }
    @Nullable
    public String getEmail() {
        return email;
    }
    @Nullable
    public Date getBirth() {
        return birth;
    }
    @Nullable
    public String getPhone() {
        return phone;
    }
    @Nullable
    public String getPassword() {
        return password;
    }

    public User withId(String id) {
        return new User(id, name, email, birth, phone, password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeSerializable(birth);
        parcel.writeString(phone);
        parcel.writeString(password);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static class Builder {
        private final User mUser;

        public Builder() {
            mUser = new User();
        }

        public Builder setId(@Nullable String id) {
            mUser.id = id;
            return this;
        }

        public Builder setName(@Nullable String name) {
            mUser.name = name;
            return this;
        }

        public Builder setEmail(@Nullable String email) {
            mUser.email = email;
            return this;
        }

        public Builder setBirth(@Nullable Date birth) {
            mUser.birth = birth;
            return this;
        }

        public Builder setPhone(@Nullable String phone) {
            mUser.phone = phone;
            return this;
        }

        public Builder setPassword(@Nullable String password) {
            mUser.password = password;
            return this;
        }

        public User build() {
            return mUser;
        }
    }
}
