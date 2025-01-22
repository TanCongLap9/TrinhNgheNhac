package com.example.trinhnghenhac.listeners;

import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;

import com.example.trinhnghenhac.functionalinterfaces.Action;
import com.example.trinhnghenhac.functionalinterfaces.Outputter;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayedSearch implements SearchView.OnQueryTextListener, Closeable, Runnable {
    private final CursorAdapter mAdapter;
    private final long mDelay;
    private final SearchView mSearchView;
    private final ExecutorService mExecutor;
    private Action<String> mAction;
    private Outputter<Cursor> mQueryResultOutputter;

    public void run() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                @Nullable
                Cursor c = mQueryResultOutputter.run();
                if (c == null) return;
                if (c.getCount() == 0) {
                    mAdapter.changeCursor(null);
                    c.close();
                    return;
                }
                mSearchView.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.changeCursor(c);
                    }
                });
            }
        });
    }

    public DelayedSearch(SearchView searchView, CursorAdapter adapter) {
        mDelay = 1000L;
        mAdapter = adapter;
        mExecutor = Executors.newSingleThreadExecutor();
        mSearchView = searchView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mAction != null) mAction.run(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchView.removeCallbacks(this);
        if (mSearchView.hasFocus()) mSearchView.postDelayed(this, mDelay);
        return true;
    }

    public DelayedSearch setQueryResultOutputter(Outputter<Cursor> outputter) {
        mQueryResultOutputter = outputter;
        return this;
    }

    public DelayedSearch setOnQueryTextSubmitListener(Action<String> consumer) {
        mAction = consumer;
        return this;
    }

    @Override
    public void close() {
        mExecutor.shutdown();
    }
}
