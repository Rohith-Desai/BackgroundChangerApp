package com.hs.photo.background.changer.editor.backgrounderaser.ads;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatDelegate;



public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            builder.detectFileUriExposure();
            StrictMode.setVmPolicy(builder.build());
        }



    }
}
