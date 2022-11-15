package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hs.photo.background.changer.editor.backgrounderaser.R;

import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;

public class ExitAppActivity extends AppCompatActivity {


    TextView exit_ys,exit_n;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_app);


        exit_ys = findViewById(R.id.exit_yes);
        exit_n = findViewById(R.id.exit_no);

        exit_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExitAppActivity.this, HomeActivity.class);
                intent.putExtra("showAd", false);
                startActivity(intent);
                onBackPressed();
            }
        });
        exit_ys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}