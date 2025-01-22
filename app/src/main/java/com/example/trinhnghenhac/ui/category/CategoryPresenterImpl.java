package com.example.trinhnghenhac.ui.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.models.Playable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryPresenterImpl implements CategoryContract.Presenter {
    @NonNull
    public final CategoryContract.View mView;
    @NonNull
    public final CategoryModelImpl mModel;
    @NonNull
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public CategoryPresenterImpl(@NonNull CategoryContract.View view) {
        mView = view;
        mModel = new CategoryModelImpl();
    }

    public CategoryPresenterImpl(@NonNull CategoryContract.View view, List<Playable> data) {
        mView = view;
        mModel = new CategoryModelImpl(data);
    }

    @NonNull
    @Override
    public CategoryModelImpl getModel() {
        return mModel;
    }

    @Nullable
    @Override
    public List<Playable> getData() {
        return mModel.getData();
    }

    @Override
    public void setData(List<Playable> data) {
        mModel.setData(data);
    }

    @Override
    public void loadOrFetchData(@MusicCategory int category) {
        if (mModel.getData() == null) fetch(category);
        else mView.displaySync(mModel.getData());
    }

    @Override
    public void fetch(@MusicCategory int category) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.setData(mModel.getPlayablesByCategory(category));
                    mView.displaySync(mModel.getData());
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
