package com.example.trinhnghenhac.ui.category;

import static com.example.trinhnghenhac.utils.AssertUtils.NE;
import static com.example.trinhnghenhac.utils.AssertUtils.assertNotNull;
import static com.example.trinhnghenhac.utils.AssertUtils.assertThat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trinhnghenhac.adapters.PlayableRowsAdapter;
import com.example.trinhnghenhac.api.MusicCategories;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.databinding.FragmentCategoryBinding;
import com.example.trinhnghenhac.models.Playable;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryContract.View {
    private PlayableRowsAdapter adapter;
    private boolean hasError;
    private FragmentCategoryBinding b;
    private @MusicCategory int category;
    private CategoryPresenterImpl mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assertNotNull(getArguments());
        category = getArguments().getInt(Extras.EXTRA_CATEGORY, MusicCategory.CATEGORY_UNKNOWN);
        assertThat(category, NE, MusicCategory.CATEGORY_UNKNOWN);

        if (savedInstanceState == null)
            mPresenter = new CategoryPresenterImpl(this);
        else
            mPresenter = new CategoryPresenterImpl(this, savedInstanceState.getParcelableArrayList(Extras.DATA_CATEGORY));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentCategoryBinding.inflate(getLayoutInflater(), null, false);
        initViews();
        mPresenter.loadOrFetchData(category);
        return b.getRoot();
    }

    @Override
    public void displaySync(List<Playable> playables) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.update(playables);
            }
        });
    }

    private void initViews() {
        adapter = new PlayableRowsAdapter(getContext(), true, true);
        b.fragmentCategoryTitle.setText(MusicCategories.getStringRes(category));
        b.fragmentCategoryList.setAdapter(adapter);
    }

    public void alertError(IOException e) {
        if (hasError) return;
        Snackbar.make(b.getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show();
        hasError = true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Extras.DATA_CATEGORY, (ArrayList<Playable>) mPresenter.getData());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
        mPresenter.close();
    }
}