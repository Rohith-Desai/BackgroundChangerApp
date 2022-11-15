package com.hs.photo.background.changer.editor.backgrounderaser.bitmap;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;

public class MemoryManagement {
    public static float free(Context context) {
        @SuppressLint("WrongConstant") int memoryClass = ((ActivityManager) context.getSystemService("activity")).getMemoryClass() - 24;
        if (memoryClass < 1) {
            memoryClass = 1;
        }
        return (float) memoryClass;
    }
}
