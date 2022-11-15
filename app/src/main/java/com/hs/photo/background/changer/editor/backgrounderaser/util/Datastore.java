package com.hs.photo.background.changer.editor.backgrounderaser.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.hs.photo.background.changer.editor.backgrounderaser.R;

public class Datastore {
    private static final Datastore ourRemote = new Datastore();

    private Bitmap bitmap1;



    public static Datastore getOurRemote(){
        return ourRemote;
    }


    public void setStickerBitmap(Bitmap bitmap1){
        this.bitmap1=bitmap1;

    }
    public Bitmap getStickerBimap(){
        return bitmap1;
    }

    public void setColorFilter(Context context, ImageView imageView, TextView textView) {
        imageView.setColorFilter(context.getResources().getColor(R.color.blue_2));
        textView.setTextColor(context.getResources().getColor(R.color.blue_2));
    }

    public void removeColorFilter(Context context, ImageView imageView, TextView textView) {
        imageView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        imageView.setColorFilter(context.getResources().getColor(R.color.transparent));
        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
    }






}
