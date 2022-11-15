package com.hs.photo.background.changer.editor.backgrounderaser;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;



import java.util.Arrays;

public class SpiralApp extends MultiDexApplication {

    public static synchronized void getInstance() {
        synchronized (SpiralApp.class) {
            synchronized (SpiralApp.class) {

            }
        }
    }

    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);


    }
}
