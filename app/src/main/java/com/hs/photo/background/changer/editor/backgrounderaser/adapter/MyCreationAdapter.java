package com.hs.photo.background.changer.editor.backgrounderaser.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.hs.photo.background.changer.editor.backgrounderaser.mytouch.SquaredImageView;
import java.util.ArrayList;

public class MyCreationAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listItem;

    public long getItemId(int i) {
        return (long) i;
    }

    public MyCreationAdapter(ArrayList<String> arrayList, Context context2) {
        this.listItem = arrayList;
        this.context = context2;
    }

    public int getCount() {
        return this.listItem.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        SquaredImageView squaredImageView;
        if (view == null) {
            squaredImageView = new SquaredImageView(this.context);
            squaredImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            squaredImageView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            squaredImageView.setPadding(3, 3, 3, 3);
        } else {
            squaredImageView = (SquaredImageView) view;
        }
        Glide.with(this.context).load(this.listItem.get(i)).into(squaredImageView);
        return squaredImageView;
    }
}
