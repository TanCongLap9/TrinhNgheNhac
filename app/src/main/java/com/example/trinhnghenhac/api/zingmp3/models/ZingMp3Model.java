package com.example.trinhnghenhac.api.zingmp3.models;

import java.io.Serializable;

public class ZingMp3Model<T extends ZingMp3DataModel> implements Serializable {
    public int err;
    public String msg;
    public T data;
}
