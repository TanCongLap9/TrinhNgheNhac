package com.example.trinhnghenhac.ui.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.trinhnghenhac.constants.Extras;
import com.example.trinhnghenhac.utils.DialogUtils;
import com.example.trinhnghenhac.R;
import com.example.trinhnghenhac.listeners.ClearErrorOnTextChange;
import com.example.trinhnghenhac.models.User;
import com.example.trinhnghenhac.ui.signin.SignInActivity;
import com.example.trinhnghenhac.utils.DateUtils;
import com.example.trinhnghenhac.utils.TextValidatorUtils;
import com.example.trinhnghenhac.databinding.ActivitySignUpBinding;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.ParseException;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity implements
        SignUpContract.View,
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        DialogInterface.OnClickListener {
    private ActivitySignUpBinding b;
    private MaterialStyledDatePickerDialog datePickerDialog;
    private ProgressDialog progressDialog;
    private SignUpPresenterImpl mPresenter;

    @Nullable
    @Override
    public User getData() {
        String name = b.activitySignUpName.getEditText().getText().toString();
        String email = b.activitySignUpEmail.getEditText().getText().toString();
        String dateString = b.activitySignUpBirth.getEditText().getText().toString();
        Date d;
        try {
            d = DateUtils.toDate(dateString);
        } catch (ParseException e) {
            return null;
        }
        String phone = b.activitySignUpPhone.getEditText().getText().toString();
        String password = b.activitySignUpPassword.getEditText().getText().toString();
        return new User.Builder()
                .setName(name)
                .setEmail(email)
                .setBirth(d)
                .setPhone(phone)
                .setPassword(password)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySignUpBinding.inflate(getLayoutInflater(), null, false);
        initEvents();
        initActionBar(b.activitySignUpActionbar.getRoot());
        mPresenter = new SignUpPresenterImpl(this);
        setContentView(b.getRoot());
    }

    private void initEvents() {
        b.activitySignUpBirth.getEditText().addTextChangedListener(new ClearErrorOnTextChange(b.activitySignUpBirth));
        b.activitySignUpBirth.setEndIconOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                if (datePickerDialog == null)
                    datePickerDialog = DialogUtils.showDatePickerDialog(SignUpActivity.this, b.activitySignUpBirth.getEditText());
                else datePickerDialog.show();
            }
        });
        b.activitySignUpName.getEditText().addTextChangedListener(new ClearErrorOnTextChange(b.activitySignUpName));
        b.activitySignUpEmail.getEditText().addTextChangedListener(new ClearErrorOnTextChange(b.activitySignUpEmail));
        b.activitySignUpBirth.getEditText().addTextChangedListener(new ClearErrorOnTextChange(b.activitySignUpBirth));
        b.activitySignUpPhone.getEditText().addTextChangedListener(new ClearErrorOnTextChange(b.activitySignUpPhone));
        b.activitySignUpPassword.getEditText().addTextChangedListener(new ClearErrorOnTextChange(b.activitySignUpPassword));
        b.activitySignUpConfirmPassword.getEditText().addTextChangedListener(new ClearErrorOnTextChange(b.activitySignUpConfirmPassword));
        b.activitySignUpAgree.setOnCheckedChangeListener(this);
        b.activitySignUpSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == b.activitySignUpSignUp)
            mPresenter.validateThenSignUp(getData());
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
    public boolean validateInput() {
        if (new TextValidatorUtils()
                .notEmpty(b.activitySignUpName)
                .notEmpty(b.activitySignUpEmail)
                .notEmpty(b.activitySignUpBirth)
                .notEmpty(b.activitySignUpPhone)
                .notEmpty(b.activitySignUpPassword)
                .notEmpty(b.activitySignUpConfirmPassword)
                .isInvalid()) return false;
        if (new TextValidatorUtils()
                .matchesEmail(b.activitySignUpEmail)
                .matchesDate(b.activitySignUpBirth)
                .matchesPhone(b.activitySignUpPhone)
                .matchesPassword(b.activitySignUpPassword)
                .matchesConfirmPassword(b.activitySignUpPassword, b.activitySignUpConfirmPassword)
                .isInvalid()) return false;
        if (!b.activitySignUpAgree.isChecked()) {
            b.activitySignUpAgreeError.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Extras.DATA_NAME, b.activitySignUpName.getEditText().getText().toString());
        if (b.activitySignUpName.getError() != null)
            outState.putString(Extras.DATA_NAME_ERROR, b.activitySignUpName.getError().toString());

        outState.putString(Extras.DATA_EMAIL, b.activitySignUpEmail.getEditText().getText().toString());
        if (b.activitySignUpEmail.getError() != null)
            outState.putString(Extras.DATA_EMAIL_ERROR, b.activitySignUpEmail.getError().toString());

        outState.putString(Extras.DATA_BIRTH, b.activitySignUpBirth.getEditText().getText().toString());
        if (b.activitySignUpBirth.getError() != null)
            outState.putString(Extras.DATA_BIRTH_ERROR, b.activitySignUpBirth.getError().toString());

        outState.putString(Extras.DATA_PHONE, b.activitySignUpPhone.getEditText().getText().toString());
        if (b.activitySignUpPhone.getError() != null)
            outState.putString(Extras.DATA_PHONE_ERROR, b.activitySignUpPhone.getError().toString());

        outState.putString(Extras.DATA_PASSWORD, b.activitySignUpPassword.getEditText().getText().toString());
        if (b.activitySignUpPassword.getError() != null)
            outState.putString(Extras.DATA_PASSWORD_ERROR, b.activitySignUpPassword.getError().toString());

        outState.putString(Extras.DATA_CONFIRM_PASSWORD, b.activitySignUpConfirmPassword.getEditText().getText().toString());
        if (b.activitySignUpConfirmPassword.getError() != null)
            outState.putString(Extras.DATA_CONFIRM_PASSWORD_ERROR, b.activitySignUpConfirmPassword.getError().toString());

        outState.putBoolean(Extras.DATA_I_AGREE, b.activitySignUpAgree.isChecked());
        outState.putBoolean(Extras.DATA_I_AGREE_ERROR, b.activitySignUpAgreeError.getVisibility() == View.VISIBLE);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        b.activitySignUpName.getEditText().setText(savedInstanceState.getString(Extras.DATA_NAME));
        b.activitySignUpName.setError(savedInstanceState.getString(Extras.DATA_NAME_ERROR));
        b.activitySignUpEmail.getEditText().setText(savedInstanceState.getString(Extras.DATA_EMAIL));
        b.activitySignUpEmail.setError(savedInstanceState.getString(Extras.DATA_EMAIL_ERROR));
        b.activitySignUpBirth.getEditText().setText(savedInstanceState.getString(Extras.DATA_BIRTH));
        b.activitySignUpBirth.setError(savedInstanceState.getString(Extras.DATA_BIRTH_ERROR));
        b.activitySignUpPhone.getEditText().setText(savedInstanceState.getString(Extras.DATA_PHONE));
        b.activitySignUpPhone.setError(savedInstanceState.getString(Extras.DATA_PHONE_ERROR));
        b.activitySignUpPassword.getEditText().setText(savedInstanceState.getString(Extras.DATA_PASSWORD));
        b.activitySignUpPassword.setError(savedInstanceState.getString(Extras.DATA_PASSWORD_ERROR));
        b.activitySignUpConfirmPassword.getEditText().setText(savedInstanceState.getString(Extras.DATA_CONFIRM_PASSWORD));
        b.activitySignUpConfirmPassword.setError(savedInstanceState.getString(Extras.DATA_CONFIRM_PASSWORD_ERROR));
        b.activitySignUpAgree.setChecked(savedInstanceState.getBoolean(Extras.DATA_I_AGREE));
        if (savedInstanceState.getBoolean(Extras.DATA_I_AGREE_ERROR))
            b.activitySignUpAgreeError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        b.activitySignUpAgreeError.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccess(FirebaseFirestoreException.Code code) {
        if (progressDialog != null) progressDialog.cancel();
        new MaterialAlertDialogBuilder(SignUpActivity.this)
                .setTitle(R.string.sign_up_success_title)
                .setMessage(R.string.sign_up_success_text)
                .setPositiveButton(R.string.dialog_ok, this)
                .setCancelable(false)
                .show();
    }

    protected void initActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (progressDialog != null) progressDialog.cancel();
        Snackbar.make(b.getRoot(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Extras.DATA_EMAIL, b.activitySignUpEmail.getEditText().getText().toString());
        resultIntent.putExtra(Extras.DATA_PASSWORD, b.activitySignUpPassword.getEditText().getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        b = null;
    }
}