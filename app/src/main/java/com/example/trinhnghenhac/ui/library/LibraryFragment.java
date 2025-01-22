package com.example.trinhnghenhac.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.adapters.CategoriesAdapter;
import com.example.trinhnghenhac.adapters.PlayableRowsAdapter;
import com.example.trinhnghenhac.databinding.FragmentLibraryBinding;
import com.example.trinhnghenhac.models.SavedStarredItem;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class LibraryFragment extends Fragment {
    private FragmentLibraryBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentLibraryBinding.inflate(inflater, container, false);
        /*PlayableRowsAdapter adapter = new PlayableRowsAdapter(getContext(), true, true);
        b.fragmentLibraryStarredList.setAdapter(adapter);
        FirebaseUtils.getSaved(FirebaseUtils.currentUser).addOnSuccessListener(new OnSuccessListener<List<SavedStarredItem>>() {
            @Override
            public void onSuccess(List<SavedStarredItem> savedStarredItems) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        return b.getRoot();
    }
}