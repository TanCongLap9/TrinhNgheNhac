package com.example.trinhnghenhac.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.example.trinhnghenhac.services.MediaPlayerService;

public class ServiceUtils {
    public static boolean running = false;
    public static boolean isMediaPlayerServiceRunning(Context context) {
        ActivityManager activityManager = context.getSystemService(ActivityManager.class);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.service.getClassName().equals(MediaPlayerService.class.getName()))
                return true;
        }
        return false;
    }
}
