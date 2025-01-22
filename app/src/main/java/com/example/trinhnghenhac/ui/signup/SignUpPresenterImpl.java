package com.example.trinhnghenhac.ui.signup;

import androidx.annotation.NonNull;

import com.example.trinhnghenhac.models.User;
import com.example.trinhnghenhac.ui.signin.SignInContract;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SignUpPresenterImpl implements SignUpContract.Presenter {
    @NonNull
    private final SignUpContract.View mView;

    public SignUpPresenterImpl(@NonNull SignUpContract.View view) {
        mView = view;
    }

    @Override
    public void validateThenSignUp(User user) {
        if (!mView.validateInput()) return;
        mView.setLoading(true);
        FirebaseUtils.addUser(user)
                .addOnSuccessListener(mView)
                .addOnFailureListener(mView)
                .addOnCompleteListener(new OnCompleteListener<FirebaseFirestoreException.Code>() {
                    @Override
                    public void onComplete(@NonNull Task<FirebaseFirestoreException.Code> task) {
                        mView.setLoading(false);
                    }
                });
    }
}
