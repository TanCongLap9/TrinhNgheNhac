package com.example.trinhnghenhac.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.constants.PlayableType;

import java.io.Serializable;

public interface Playable extends Serializable, Parcelable {
    String getId();
    String getName();
    String getTitle();
    String getText();
    String getImage();
    @MusicPlatform
    int getPlatform();
    boolean isPremium();

    static int getIcon(Playable playable) {
        return playable instanceof Playlist ? R.drawable.round_queue_music_24
                : playable instanceof Artist ? R.drawable.round_person_24
                : playable instanceof Song ? R.drawable.round_music_note_24
                : playable instanceof Video ? R.drawable.round_music_video_24
                : 0;
    }

    Creator<Playable> CREATOR = new Creator<Playable>() {
        @Override
        public Playable createFromParcel(Parcel in) {
            switch (in.readInt()) {
                case PlayableType.TYPE_SONG:
                    in.setDataPosition(0);
                    return in.readParcelable(Song.class.getClassLoader());
                case PlayableType.TYPE_ARTIST:
                    in.setDataPosition(0);
                    return in.readParcelable(Artist.class.getClassLoader());
                case PlayableType.TYPE_PLAYLIST:
                    in.setDataPosition(0);
                    return in.readParcelable(Playlist.class.getClassLoader());
                case PlayableType.TYPE_VIDEO:
                    in.setDataPosition(0);
                    return in.readParcelable(Video.class.getClassLoader());
            }
            throw new IllegalArgumentException("Parcel is invalid.");
        }

        @Override
        public Playable[] newArray(int size) {
            return new Playable[size];
        }
    };
}
