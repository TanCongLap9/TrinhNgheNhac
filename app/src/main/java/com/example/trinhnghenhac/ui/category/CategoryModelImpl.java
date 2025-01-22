package com.example.trinhnghenhac.ui.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.utils.RandomUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryModelImpl implements CategoryContract.Model {
    @Nullable
    private List<Playable> mData;
    @NonNull
    private final MusicPlatformApi[] apis;

    public CategoryModelImpl() {
        apis = MusicPlatformApi.getAll();
    }

    public CategoryModelImpl(@Nullable List<Playable> data) {
        apis = MusicPlatformApi.getAll();
        mData = data;
    }

    @Nullable
    @Override
    public List<Playable> getData() {
        return mData;
    }

    @Override
    public void setData(List<Playable> data) {
        mData = data;
    }

    @Override
    public List<Playable> getPlayablesByCategory(@MusicCategory int category) throws IOException {
        @Nullable
        List<Playable> zingMp3Playables = apis[MusicPlatform.PLATFORM_ZINGMP3].getPlayablesByCategory(category);

        @Nullable
        List<Playable> nhacCuaTuiPlayables = apis[MusicPlatform.PLATFORM_NHACCUATUI].getPlayablesByCategory(category);

        return RandomUtils.mergeRandom(
                zingMp3Playables == null ? new ArrayList<>() : zingMp3Playables,
                nhacCuaTuiPlayables == null ? new ArrayList<>() : nhacCuaTuiPlayables);
    }
}
