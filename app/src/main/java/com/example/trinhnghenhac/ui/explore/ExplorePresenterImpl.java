package com.example.trinhnghenhac.ui.explore;

import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicCategory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExplorePresenterImpl implements ExploreContract.Presenter {
    @NonNull
    private final ExploreContract.View mView;
    @NonNull
    private final ExploreContract.Model mModel;
    @NonNull
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public ExplorePresenterImpl(@NonNull ExploreContract.View view) {
        mView = view;
        mModel = new ExploreModelImpl();
    }

    public ExplorePresenterImpl(@NonNull ExploreContract.View view, @NonNull ExploreData data) {
        mView = view;
        mModel = new ExploreModelImpl(data);
    }

    @Nullable
    @Override
    public Cursor getSearchHints(CharSequence query) {
        try {
            return mModel.getSearchHints(query);
        } catch (IOException e) {
            mView.alertError(e);
            return null;
        }
    }

    @NonNull
    @Override
    public ExploreContract.Model getModel() {
        return mModel;
    }

    @NonNull
    @Override
    public ExploreData getData() {
        return mModel.getData();
    }

    @Override
    public void loadOrFetchData() {
        if (mModel.getData().getChill() == null) fetchChillCategory();
        else mView.displayChillCategorySync(mModel.getData().getChill());
        if (mModel.getData().getNewest() == null) fetchNewestCategory();
        else mView.displayNewestCategorySync(mModel.getData().getNewest());
        if (mModel.getData().getVietnamese() == null) fetchVietnameseCategory();
        else mView.displayVietnameseCategorySync(mModel.getData().getVietnamese());
        if (mModel.getData().getChinese() == null) fetchChineseCategory();
        else mView.displayChineseCategorySync(mModel.getData().getChinese());
    }

    @Override
    public void fetchChineseCategory() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.getData().setChinese(mModel.getRandomlyMergedPlayablesByCategory(MusicCategory.CATEGORY_CHINESE));
                    Log.d("Loaded", "run: ");
                    mView.displayChineseCategorySync(mModel.getData().getChinese());
                } catch (IOException e) {
                    mView.alertError(e);
                }
            }
        });
    }

    @Override
    public void fetchVietnameseCategory() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.getData().setVietnamese(mModel.getRandomlyMergedPlayablesByCategory(MusicCategory.CATEGORY_VIETNAMESE));
                    Log.d("Loaded", "run: ");
                    mView.displayVietnameseCategorySync(mModel.getData().getVietnamese());
                } catch (IOException e) {
                    mView.alertError(e);
                }
            }
        });
    }

    @Override
    public void fetchChillCategory() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.getData().setChill(mModel.getRandomlyMergedPlayablesByCategory(MusicCategory.CATEGORY_CHILL));
                    Log.d("Loaded", "run: ");
                    mView.displayChillCategorySync(mModel.getData().getChill());
                } catch (IOException e) {
                    mView.alertError(e);
                }
            }
        });
    }

    @Override
    public void fetchNewestCategory() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.getData().setNewest(mModel.getRandomlyMergedPlayablesByCategory(MusicCategory.CATEGORY_NEW_RELEASES));
                    Log.d("Loaded", "run: ");
                    mView.displayNewestCategorySync(mModel.getData().getNewest());
                } catch (IOException e) {
                    mView.alertError(e);
                }
            }
        });
    }

    @Override
    public void close() {
        mExecutor.shutdown();
    }
}
