package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ZingMp3FeaturedModel implements ZingMp3DataModel, Serializable {
    @Nullable
    public List<ItemModel> items;
    public static class ItemModel {
        @Nullable
        public List<ZingMp3SectionItemModel> items;
    }
}
