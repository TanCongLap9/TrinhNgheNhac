package com.example.trinhnghenhac.api.zingmp3.models;

import androidx.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZingMp3SectionModel implements ZingMp3DataModel, Serializable {
    @Nullable
    public String link;
    @Nullable
    public List<ZingMp3SectionItemModel> items;
    // public List<String> options;
    @Nullable
    public String sectionId;
    @Nullable
    public String sectionType;
    @Nullable
    public String itemType;
    @Nullable
    public String sectionName;
    @Nullable
    public String title;
    @Nullable
    public String viewName;
}
