package com.example.trinhnghenhac.ui.searchresults;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.observables.ObservableParcelable;
import com.example.trinhnghenhac.models.SearchResults;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchResultsPresenterImpl implements SearchResultsContract.Presenter {
    @NonNull
    private final SearchResultsContract.Model mModel;

    @NonNull
    private final SearchResultsContract.View mView;

    @NonNull
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public SearchResultsPresenterImpl(@NonNull SearchResultsContract.View mView) {
        this.mView = mView;
        mModel = new SearchResultsModelImpl();
    }

    public SearchResultsPresenterImpl(@NonNull SearchResultsContract.View mView, ObservableParcelable<SearchResults> observable) {
        this.mView = mView;
        this.mModel = new SearchResultsModelImpl(observable);
    }

    public SearchResultsPresenterImpl(@NonNull SearchResultsContract.View mView, SearchResults data) {
        this.mView = mView;
        this.mModel = new SearchResultsModelImpl(data);
    }

    @NonNull
    @Override
    public SearchResultsContract.Model getModel() {
        return mModel;
    }

    @NonNull
    @Override
    public ObservableParcelable<SearchResults> getObservable() {
        return mModel.getObservable();
    }

    @Nullable
    @Override
    public SearchResults getData() {
        return mModel.getData();
    }

    @Override
    public void loadOrFetchData(CharSequence query) {
        if (mModel.getData() == null) fetch(query);
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

    @Override
    public void fetch(CharSequence query) {
        mModel.getObservable().set(null);
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mModel.getObservable().set(mModel.getSearchResults(query));
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
