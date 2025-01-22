package com.example.trinhnghenhac.utils;

import androidx.annotation.NonNull;

import com.example.trinhnghenhac.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class TextValidatorUtils {
    private boolean mValid = true;

    public TextValidatorUtils() {

    }

    public boolean isValid() {
        return mValid;
    }

    public boolean isInvalid() {
        return !mValid;
    }

    public TextValidatorUtils notEmpty(@NonNull TextInputLayout textInputLayout) {
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError(textInputLayout.getContext().getString(R.string.field_not_empty));
            mValid = false;
        }
        return this;
    }

    public TextValidatorUtils notEmpty(@NonNull CharSequence charSequence) {
        if (charSequence.length() == 0) mValid = false;
        return this;
    }

    public TextValidatorUtils matchesEmail(@NonNull TextInputLayout textInputLayout) {
        if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", textInputLayout.getEditText().getText().toString())) {
            textInputLayout.setError(textInputLayout.getContext().getString(R.string.field_email_pattern_not_match));
            mValid = false;
        }
        return this;
    }

    public TextValidatorUtils matchesEmail(@NonNull CharSequence charSequence) {
        if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", charSequence)) mValid = false;
        return this;
    }

    public TextValidatorUtils matchesPhone(@NonNull TextInputLayout textInputLayout) {
        if (!Pattern.matches("^0\\d{8,11}$", textInputLayout.getEditText().getText().toString())) {
            textInputLayout.setError(textInputLayout.getContext().getString(R.string.field_phone_pattern_not_match));
            mValid = false;
        }
        return this;
    }

    public TextValidatorUtils matchesPhone(@NonNull CharSequence charSequence) {
        if (!Pattern.matches("^0\\d{8,11}$", charSequence)) mValid = false;
        return this;
    }

    public TextValidatorUtils matchesPassword(@NonNull TextInputLayout textInputLayout) {
        if (!Pattern.matches("(?:[\\p{L}\\d][^\\p{L}\\d]*){8,}", textInputLayout.getEditText().getText().toString())) {
            textInputLayout.setError(textInputLayout.getContext().getString(R.string.field_password_pattern_not_match));
            mValid = false;
        }
        return this;
    }

    public TextValidatorUtils matchesPassword(@NonNull CharSequence charSequence) {
        if (!Pattern.matches("(?:[\\p{L}\\d]+[^\\p{L}\\d]*){8,}", charSequence)) mValid = false;
        return this;
    }

    public TextValidatorUtils matchesConfirmPassword(@NonNull TextInputLayout textInputLayout1, @NonNull TextInputLayout textInputLayout2) {
        if (!textInputLayout1.getEditText().getText().toString().equals(textInputLayout2.getEditText().getText().toString())) {
            textInputLayout2.setError(textInputLayout1.getContext().getString(R.string.field_confirm_confirm_not_match));
            mValid = false;
        }
        return this;
    }

    public TextValidatorUtils matchesConfirmPassword(@NonNull CharSequence charSequence1, @NonNull CharSequence charSequence2) {
        if (!charSequence1.equals(charSequence2)) mValid = false;
        return this;
    }

    public TextValidatorUtils matchesDate(@NonNull TextInputLayout textInputLayout) {
        if (!DateUtils.isParsable(textInputLayout.getEditText().getText())) {
            textInputLayout.setError(textInputLayout.getContext().getString(R.string.field_date_pattern_not_match));
            mValid = false;
        }
        return this;
    }

    public TextValidatorUtils matchesDate(@NonNull CharSequence charSequence) {
        if (!DateUtils.isParsable(charSequence))
            mValid = false;
        return this;
    }
}