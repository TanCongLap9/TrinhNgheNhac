package com.example.trinhnghenhac.api;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SessionCookieJar implements CookieJar {
    private List<Cookie> cookies;

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        this.cookies = new ArrayList<>(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookies != null ? cookies : new ArrayList<>();
    }

    public boolean anyExpired() {
        if (cookies == null || cookies.size() == 0) return true;
        for (Cookie cookie : cookies)
            if (System.currentTimeMillis() >= cookie.expiresAt())
                return true;
        return false;
    }
}
