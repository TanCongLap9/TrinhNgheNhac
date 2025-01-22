package com.example.trinhnghenhac.listeners;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class ClearErrorOnTextChange implements TextWatcher {
    private final TextInputLayout mTextInputLayout;
    public ClearErrorOnTextChange(TextInputLayout textInputLayout) {
        mTextInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mTextInputLayout.setError(null);
    }
}
