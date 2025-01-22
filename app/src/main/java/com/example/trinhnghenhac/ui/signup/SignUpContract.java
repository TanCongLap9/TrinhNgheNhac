package com.example.trinhnghenhac.ui.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trinhnghenhac.models.User;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestoreException;

public interface SignUpContract {
    interface View extends OnSuccessListener<FirebaseFirestoreException.Code>, OnFailureListener {
        @Nullable
        User getData();
        void setLoading(boolean isLoading);
        boolean validateInput();
    }

    interface Presenter {
        void validateThenSignUp(User user);
    }
}

