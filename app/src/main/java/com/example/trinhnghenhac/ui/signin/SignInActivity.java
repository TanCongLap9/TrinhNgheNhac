package com.example.trinhnghenhac.ui.signin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trinhnghenhac.BuildConfig;
import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.utils.DialogUtils;
import com.example.trinhnghenhac.listeners.ClearErrorOnTextChange;
import com.example.trinhnghenhac.ui.main.MainActivity;
import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.databinding.ActivitySignInBinding;
import com.example.trinhnghenhac.models.User;
import com.example.trinhnghenhac.ui.signup.SignUpActivity;
import com.example.trinhnghenhac.utils.FirebaseUtils;
import com.example.trinhnghenhac.utils.TextValidatorUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, SignInContract.View {
    public static final int REQUEST_CODE_SIGN_UP = 0;
    private ActivitySignInBinding b;
    private ProgressDialog progressDialog;
    private EditText email;
    private EditText password;
    private SignInPresenterImpl mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUtils.initialize(this);
        b = ActivitySignInBinding.inflate(getLayoutInflater());
        mPresenter = new SignInPresenterImpl(this);
        email = b.activitySignInContent.activitySignInEmailText;
        password = b.activitySignInContent.activitySignInPasswordText;
        b.activitySignInContent.activitySignInVersion.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
        bindEvents();
        BottomSheetBehavior.from(b.activitySignInContent.activitySignInPanel).setState(BottomSheetBehavior.STATE_EXPANDED);
        setContentView(b.getRoot());
        // autoLogin();
    }

    private void bindEvents() {
        email.addTextChangedListener(new ClearErrorOnTextChange(b.activitySignInContent.activitySignInEmail));
        password.addTextChangedListener(new ClearErrorOnTextChange(b.activitySignInContent.activitySignInPassword));
        b.activitySignInContent.activitySignInSignIn.setOnClickListener(this);
        b.activitySignInContent.activitySignInSignUp.setOnClickListener(this);
    }

    public boolean validateInput() {
        if (new TextValidatorUtils()
                .notEmpty(b.activitySignInContent.activitySignInEmail)
                .notEmpty(b.activitySignInContent.activitySignInPassword)
                .isInvalid()) return false;
        if (new TextValidatorUtils()
                .matchesEmail(b.activitySignInContent.activitySignInEmail)
                .isInvalid()) return false;
        return true;
    }

    private void autoLogin() {
        email.setText("toi@email.com");
        password.setText("password");
        b.activitySignInContent.activitySignInSignIn.performClick();
    }

    @Override
    public void setLoading(boolean isLoading) {
        if (isLoading) progressDialog = DialogUtils.showProgressDialog(this);
        else if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == b.activitySignInContent.activitySignInSignIn) {
            User user = new User.Builder()
                    .setEmail(email.getText().toString())
                    .setPassword(password.getText().toString())
                    .build();
            mPresenter.validateAndSignIn(user);
        } else if (view == b.activitySignInContent.activitySignInSignUp) {
            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivityForResult(signUpIntent, REQUEST_CODE_SIGN_UP);
        }
    }

    @Override
    public void onSuccess(User userModel) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivityIntent.putExtra(Extras.EXTRA_USER, (Parcelable) userModel);
        startActivity(mainActivityIntent);
        password.getText().clear();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e.getMessage().contains("INVALID_LOGIN_CREDENTIALS"))
            Snackbar.make(b.getRoot(), getString(R.string.sign_in_not_found), Snackbar.LENGTH_SHORT).show();
        else Snackbar.make(b.getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_UP && resultCode == RESULT_OK) {
            email.setText(data.getStringExtra(Extras.DATA_EMAIL));
            password.setText(data.getStringExtra(Extras.DATA_PASSWORD));
            b.activitySignInContent.activitySignInSignIn.performClick();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Extras.DATA_EMAIL, email.getText().toString());
        outState.putString(Extras.DATA_PASSWORD, password.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        email.setText(savedInstanceState.getString(Extras.DATA_EMAIL));
        password.setText(savedInstanceState.getString(Extras.DATA_PASSWORD));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        b = null;
    }
}