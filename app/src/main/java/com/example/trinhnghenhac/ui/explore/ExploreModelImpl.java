package com.example.trinhnghenhac.ui.explore;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicPlatform;
import com.example.trinhnghenhac.api.MusicPlatformApi;
import com.example.trinhnghenhac.models.Playable;
import com.example.trinhnghenhac.utils.RandomUtils;

import java.io.IOException;
import java.util.List;

public class ExploreModelImpl implements ExploreContract.Model {
    @NonNull
    private final ExploreData mData;
    @NonNull
    private final MusicPlatformApi[] mApis;

    public ExploreModelImpl() {
        mApis = MusicPlatformApi.getAll();
        mData = new ExploreData();
    }

    public ExploreModelImpl(@NonNull ExploreData data) {
        mApis = MusicPlatformApi.getAll();
        mData = data;
    }

    @Nullable
    @Override
    public Cursor getSearchHints(CharSequence query) throws IOException {
        return mApis[MusicPlatform.PLATFORM_ZINGMP3].getSearchHints(query);
    }

    @Override
    public List<Playable> getRandomlyMergedPlayablesByCategory(int category) throws IOException {
        List<Playable> zingMp3Playables = mApis[MusicPlatform.PLATFORM_ZINGMP3].getPlayablesByCategory(category);
        List<Playable> nhacCuaTuiPlayables = mApis[MusicPlatform.PLATFORM_NHACCUATUI].getPlayablesByCategory(category);
        return RandomUtils.mergeRandom(zingMp3Playables, nhacCuaTuiPlayables);
    }

    @NonNull
    @Override
    public ExploreData getData() {
        return mData;
    }
}
