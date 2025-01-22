package com.example.trinhnghenhac.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trinhnghenhac.adapters.CategoriesAdapter;
import com.example.trinhnghenhac.constants.MusicCategory;
import com.example.trinhnghenhac.databinding.FragmentCategoriesBinding;

public class CategoriesFragment extends Fragment {
    private FragmentCategoriesBinding b;
    private static final int[] ALL_CATEGORIES = {
            MusicCategory.CATEGORY_NEW_RELEASES,
            MusicCategory.CATEGORY_VIETNAMESE,
            MusicCategory.CATEGORY_CHINESE,
            MusicCategory.CATEGORY_EUROPE,
            MusicCategory.CATEGORY_KOREAN,
            MusicCategory.CATEGORY_CHILL,
            MusicCategory.CATEGORY_EDM,
            MusicCategory.CATEGORY_HIPHOP,
            MusicCategory.CATEGORY_JAZZ,
            MusicCategory.CATEGORY_SAD,
            MusicCategory.CATEGORY_WORKOUT,
            MusicCategory.CATEGORY_FILM,
            MusicCategory.CATEGORY_BOLERO,
            MusicCategory.CATEGORY_ACOUSTIC,
            MusicCategory.CATEGORY_INSTRUMENTAL,
            MusicCategory.CATEGORY_COFFEE,
            MusicCategory.CATEGORY_CHILDREN,
            MusicCategory.CATEGORY_CLASSICAL,
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCategoriesBinding.inflate(inflater, container, false);
        b.getRoot().setAdapter(new CategoriesAdapter(getContext(), ALL_CATEGORIES));
        return b.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        b = null;
    }
}