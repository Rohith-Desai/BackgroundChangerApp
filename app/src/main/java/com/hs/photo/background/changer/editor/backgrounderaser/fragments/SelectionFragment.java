package com.hs.photo.background.changer.editor.backgrounderaser.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.MainActivity;


public class SelectionFragment extends Fragment {

    View view;
    LinearLayout gallery, camera;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.select_img_popup, container, false);
// get the reference of Button
        gallery =  view.findViewById(R.id.layoutSelectPhoto);
// perform setOnClickListener on second Button
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// display a message by using a Toast
                //Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_LONG).show();
                ((MainActivity) getActivity()).selectPhoto();
            }
        });

        camera =  view.findViewById(R.id.layoutTakePhoto);
// perform setOnClickListener on second Button
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// display a message by using a Toast
                //Toast.makeText(getActivity(), "Camera", Toast.LENGTH_LONG).show();
                ((MainActivity) getActivity()).captureImage();
            }
        });
        return view;
    }
}
