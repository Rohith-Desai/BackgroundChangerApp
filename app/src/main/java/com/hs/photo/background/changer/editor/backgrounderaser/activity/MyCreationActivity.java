package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.MyCreationAdapter;

import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
//import com.google.android.gms.ads.InterstitialAd;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;

public class MyCreationActivity extends BaseActivity {

    TextView btn_back;
    public ArrayList<String> images;
    int index = 0;
    //private InterstitialAd mInterstitialAd;
    private FrameLayout adContainerView;


    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_creation);
        adContainerView=findViewById(R.id.creation_banner_ad);

        Bundle bundles = new Bundle();
        bundles.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "MyCreationActivity");
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("ACTIVITY_SELECTION", bundles);
        }

        if (RemoteConfigValues.getOurRemote().getShowBannerAd()!= null){
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")){

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAdcreation();
                    }
                });

            }
        }





        this.btn_back = (TextView) findViewById(R.id.btn_back);
        this.images = getAllShownImagesPath();
        ImageView ivnophoto = (ImageView) findViewById(R.id.ivnophoto);
        if (this.images.isEmpty()) {
            ivnophoto.setVisibility(0);
        } else {
            ivnophoto.setVisibility(8);
            GridView gridView = (GridView) findViewById(R.id.gvalbum);
            gridView.setAdapter((ListAdapter) new MyCreationAdapter(this.images, this));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (MyCreationActivity.this.images != null && !MyCreationActivity.this.images.isEmpty()) {
                        MyCreationActivity.this.index = i;
                       openNext();
                    }
                }
            });
        }
       // loadAd();
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyCreationActivity.this.onBackPressed();
            }
        });
    }
    public void loadBannerAdcreation() {
        AdView adViewbanner = new AdView(this);
        adViewbanner.setAdUnitId(getString(R.string.banner_ad_on_mainscreen));
        adContainerView.removeAllViews();
        adContainerView.addView(adViewbanner);

        AdSize adSize = getAdSize(this, adContainerView);
        adViewbanner.setAdSize(adSize);

        AdRequest adRequest =
                new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adViewbanner.loadAd(adRequest);
    }

    private ArrayList<String> getAllShownImagesPath() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String SAVE_PATH = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString();
            File[] listFiles = new File(SAVE_PATH+"/"+ getString(R.string.app_name) + "/SpiralPhoto").listFiles();
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
        Intent intent = new Intent().setClass(this, SaveImageActivity.class);
        intent.setData(Uri.parse(this.images.get(this.index)));
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  CommonMethods.getInstance().activitiesAD(MyCreationActivity.this);
    }
}
