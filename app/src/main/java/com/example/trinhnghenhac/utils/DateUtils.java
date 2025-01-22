package com.example.trinhnghenhac.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    static {
        sdf.setLenient(false);
    }

    public static String toString(Date d) {
        return sdf.format(d);
    }

    public static Date toDate(CharSequence charSequence) throws ParseException {
        return sdf.parse(charSequence.toString());
    }

    public static boolean isParsable(CharSequence charSequence) {
        try {
            DateUtils.toDate(charSequence);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
