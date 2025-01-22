package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3ArrayModel<T extends ZingMp3DataModel> implements Serializable {
    public int err;
    @Nullable
    public String msg;
    @Nullable
    public List<T> data;
}
