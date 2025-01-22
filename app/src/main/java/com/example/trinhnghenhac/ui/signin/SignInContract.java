package com.example.trinhnghenhac.ui.signin;

import androidx.annotation.NonNull;

import com.example.trinhnghenhac.models.User;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.Closeable;

public interface SignInContract {
    interface View extends OnSuccessListener<User>, OnFailureListener {
        void setLoading(boolean isLoading);

        boolean validateInput();
    }

    interface Presenter {
        void validateAndSignIn(User user);
    }
}