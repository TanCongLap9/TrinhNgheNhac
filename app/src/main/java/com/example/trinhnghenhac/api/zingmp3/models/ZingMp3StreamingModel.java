package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZingMp3StreamingModel implements ZingMp3DataModel, Serializable {
    @SerializedName("128")
    @Nullable
    public String stream128;
    @SerializedName("320")
    @Nullable
    public String stream320;
}
