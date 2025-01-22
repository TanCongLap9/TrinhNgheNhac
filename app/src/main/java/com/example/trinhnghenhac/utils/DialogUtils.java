package com.example.trinhnghenhac.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.trinhnghenhac.R;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

public class DialogUtils {
    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    @SuppressLint("RestrictedApi")
    public static MaterialStyledDatePickerDialog showDatePickerDialog(Context context, TextView tv) {
        Calendar cal = Calendar.getInstance();
        MaterialStyledDatePickerDialog dialog = new MaterialStyledDatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                tv.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        return dialog;
    }
}
