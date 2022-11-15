package com.hs.photo.background.changer.editor.backgrounderaser.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.fragment.app.Fragment;

import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.CreationsActivity;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.SaveImageActivity;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.MyCreationAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class CreationsEraser extends Fragment {

    public ArrayList<String> images;

    int index = 0;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_editor, container, false);

        ImageView ivnophoto = (ImageView) view.findViewById(R.id.ivnophoto);

        this.images = getAllShownImagesPath();

        if (this.images.isEmpty()) {
            ivnophoto.setVisibility(0);
            if (CreationsActivity.getInstance() != null) {
                CreationsActivity.getInstance().hideBannerad();
            }
        } else {
            ivnophoto.setVisibility(8);
            GridView gridView = (GridView) view.findViewById(R.id.edior_album);
            gridView.setAdapter((ListAdapter) new MyCreationAdapter(this.images, getActivity()));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (CreationsEraser.this.images != null && !CreationsEraser.this.images.isEmpty()) {
                        CreationsEraser.this.index = i;
                        openNext();
                    }
                }
            });
        }

        return view;
    }

    private ArrayList<String> getAllShownImagesPath() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String SAVE_PATH = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString();
            File[] listFiles = new File(SAVE_PATH+"/"+ getString(R.string.app_name) + "/ErasePhoto").listFiles();
            for (File file : listFiles) {
                try {
                    if (file.isFile()) {
                        arrayList.add(file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Collections.reverse(arrayList);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }
    public void openNext() {
        Intent intent = new Intent().setClass(getActivity(), SaveImageActivity.class);
        intent.setData(Uri.parse(this.images.get(this.index)));
        startActivity(intent);
    }

}
