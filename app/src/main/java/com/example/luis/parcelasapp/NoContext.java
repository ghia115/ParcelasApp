package com.example.luis.parcelasapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by mario on 17/01/18.
 */

public class NoContext extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        NoContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return NoContext.context;
    }
}
