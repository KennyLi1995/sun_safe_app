package com.example.sun_safe_app;

import android.app.Application;

public class MyApplication extends Application {

    private static com.example.sun_safe_app.MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static com.example.sun_safe_app.MyApplication getInstance() {
        return com.example.sun_safe_app.MyApplication.sInstance;
    }
}