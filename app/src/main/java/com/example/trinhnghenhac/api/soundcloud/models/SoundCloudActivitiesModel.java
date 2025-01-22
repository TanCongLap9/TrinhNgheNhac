package com.example.trinhnghenhac.api.soundcloud.models;

import java.io.Serializable;
import java.util.List;

public class SoundCloudActivitiesModel implements Serializable {
    public List<SoundCloudActivityModel> collection;
    public String next_href;
    public String future_href;
}
