package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3SearchResultModel<T extends ZingMp3SectionItemModel> implements ZingMp3DataModel, Serializable {
    @Nullable
    public List<T> items;
}
