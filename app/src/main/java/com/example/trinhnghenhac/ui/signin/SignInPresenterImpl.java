package com.example.trinhnghenhac.ui.signin;

import androidx.annotation.NonNull;

import com.example.trinhnghenhac.models.User;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignInPresenterImpl implements SignInContract.Presenter {
    @NonNull
    private final SignInContract.View mView;

    public SignInPresenterImpl(@NonNull SignInContract.View view) {
        mView = view;
    }

    @Override
    public void validateAndSignIn(User user) {
        if (!mView.validateInput()) return;
        mView.setLoading(true);
        FirebaseUtils.getUser(user.getEmail(), user.getPassword())
                .addOnSuccessListener(mView)
                .addOnFailureListener(mView)
                .addOnCompleteListener(new OnCompleteListener<User>() {
                    @Override
                    public void onComplete(@NonNull Task<User> task) {
                        mView.setLoading(false);
                    }
                });
    }
}
