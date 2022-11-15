package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
//import com.hs.photo.background.changer.editor.backgrounderaser.MyUtils;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.CommonAdapter;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.bitmap.BitmapUtil;
import com.hs.photo.background.changer.editor.backgrounderaser.mytouch.MultiTouchListener;
import com.hs.photo.background.changer.editor.backgrounderaser.mytouch.MultiTouchListener2view;
import com.hs.photo.background.changer.editor.backgrounderaser.util.ColorFilterGenerator;

//import com.google.android.gms.ads.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SpiralEditorActivity extends BaseActivity implements View.OnClickListener {
    byte[] bgByte;
    TextView brnBack;
    LinearLayout btnSave;
    CommonAdapter commonAdapter;
    String flagForBG = " ";
    int flagForLoad = 0;
    SeekBar hueSeekBar;
    ImageView imgBG;
    ImageView imgBG1;
    ImageView imgBack;
    ImageView imgClose1;
    ImageView imgCut;
    ImageView imgDefault;
    ImageView imgFront;
    ImageView imgRefresh;
    public String[] listItem;
    LinearLayout llBG;
    LinearLayout llBgCat;
    LinearLayout llBlur;
    LinearLayout llBlurCat;
    LinearLayout llSpiralCat;
    LinearLayout llSpriral;
    MultiTouchListener multiTouchListener;
    MultiTouchListener2view multiTouchListener2view;
    RecyclerView recycleViewSpiral;
    ImageView remove_hue;
    RelativeLayout rl3view;
    ConstraintLayout rlBody;
    RelativeLayout rlBottom;
    RelativeLayout rlHue;
    RelativeLayout rlMain;
    RelativeLayout rl_blur;
    RelativeLayout rl_recycle;
    public String savePath;
    SeekBar seekBar;
    String stingBG;
    String stingCut;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;
    TextView txt6;
    TextView txt7;
    TextView txt8;
    TextView txt9;
    TextView txt10;
    TextView txt11;
    TextView txt12;
    TextView txt13;
    TextView txt14;
    TextView txt15;

    TextView txtb1;
    TextView txtb2;
    TextView txtb3;
    TextView txtb4;
    TextView txtb5;
    TextView txtb6;
    TextView txtb7;
    TextView txtb8;
    TextView txtb9;
    TextView txtb10;
    TextView txtb11;
    TextView txtb12;
    TextView txtb13;
    TextView txtb14;
    TextView txtb15;
    private FrameLayout adContainerView;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_spiral_editor);



        this.rl_blur = (RelativeLayout) findViewById(R.id.rl_blur);
        this.rl_recycle = (RelativeLayout) findViewById(R.id.rl_recycle);
        this.rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        this.rlBody = (ConstraintLayout) findViewById(R.id.rlBody);
        this.rl3view = (RelativeLayout) findViewById(R.id.rl3view);
        this.imgBG1 = (ImageView) findViewById(R.id.img_bg1);
        this.imgBG = (ImageView) findViewById(R.id.img_bg);
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.imgCut = (ImageView) findViewById(R.id.imgCut);
        this.imgFront = (ImageView) findViewById(R.id.img_front);

        this.stingBG = getIntent().getStringExtra("SELECTED_IMAGE_PATH");
        this.stingCut = getIntent().getStringExtra("SELECTED_IMAGE");
        String stringExtra = getIntent().getStringExtra("ratio");

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.rlBody);
        constraintSet.setDimensionRatio(this.rlMain.getId(), stringExtra);
        constraintSet.applyTo(this.rlBody);
        ImageView imageView = this.imgBG;
        imageView.setImageURI(Uri.fromFile(new File("" + this.stingBG)));
        this.imgBack.setImageResource(R.drawable.default_tra);
        ImageView imageView2 = this.imgCut;
        imageView2.setImageURI(Uri.fromFile(new File("" + this.stingCut)));

        imgFront.setImageResource(R.drawable.default_tra);
        multiTouchListener = new MultiTouchListener();
        multiTouchListener2view = new MultiTouchListener2view(this.imgBack);
        imgFront.setOnTouchListener(null);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleViewSpiral);
        recycleViewSpiral = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        init();
        //loadAd();
    }

    public void init() {
        this.rlHue = (RelativeLayout) findViewById(R.id.rl_hue);
        this.remove_hue = (ImageView) findViewById(R.id.remove_hue);
        this.llSpiralCat = (LinearLayout) findViewById(R.id.ll_spiral_cal);
        this.llBgCat = (LinearLayout) findViewById(R.id.ll_bg_cal);
        this.llBlurCat = (LinearLayout) findViewById(R.id.ll_blur_cal);
        this.btnSave = (LinearLayout) findViewById(R.id.btn_save);
        this.brnBack = (TextView) findViewById(R.id.btn_back);
        this.llBG = (LinearLayout) findViewById(R.id.ll_bg);
        this.llSpriral = (LinearLayout) findViewById(R.id.ll_spiral);
        this.llBlur = (LinearLayout) findViewById(R.id.ll_blur);
        this.rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);
        this.imgClose1 = (ImageView) findViewById(R.id.btn_close_1);
        //this.imgDefault = (ImageView) findViewById(R.id.btn_default);
        //this.imgRefresh = (ImageView) findViewById(R.id.btn_refresh);
        this.txt1 = (TextView) findViewById(R.id.txt_1);
        this.txt2 = (TextView) findViewById(R.id.txt_2);
        this.txt3 = (TextView) findViewById(R.id.txt_3);
        this.txt4 = (TextView) findViewById(R.id.txt_4);
        this.txt5 = (TextView) findViewById(R.id.txt_5);
        this.txt6 = (TextView) findViewById(R.id.txt_6);
        this.txt7 = (TextView) findViewById(R.id.txt_7);
        this.txt8 = (TextView) findViewById(R.id.txt_8);
        this.txt9 = (TextView) findViewById(R.id.txt_9);
        this.txt10 = (TextView) findViewById(R.id.txt_10);
        this.txt11 = (TextView) findViewById(R.id.txt_11);
        this.txt12 = (TextView) findViewById(R.id.txt_12);
        this.txt13 = (TextView) findViewById(R.id.txt_13);
        this.txt14 = (TextView) findViewById(R.id.txt_14);
        this.txt15 = (TextView) findViewById(R.id.txt_15);

        this.txtb1 = (TextView) findViewById(R.id.txtbg_1);
        this.txtb2 = (TextView) findViewById(R.id.txtbg_2);
        this.txtb3 = (TextView) findViewById(R.id.txtbg_3);
        this.txtb4 = (TextView) findViewById(R.id.txtbg_4);
        this.txtb5 = (TextView) findViewById(R.id.txtbg_5);
        this.txtb6 = (TextView) findViewById(R.id.txtbg_6);
        this.txtb7 = (TextView) findViewById(R.id.txtbg_7);
        this.txtb8 = (TextView) findViewById(R.id.txtbg_8);
        this.txtb9 = (TextView) findViewById(R.id.txtbg_9);
        this.txtb10 = (TextView) findViewById(R.id.txtbg_10);
        this.txtb11 = (TextView) findViewById(R.id.txtbg_11);
        this.txtb12 = (TextView) findViewById(R.id.txtbg_12);
        this.txtb13 = (TextView) findViewById(R.id.txtbg_13);
        this.txtb14 = (TextView) findViewById(R.id.txtbg_14);
        this.txtb15 = (TextView) findViewById(R.id.txtbg_15);

        this.remove_hue.setOnClickListener(this);
        this.btnSave.setOnClickListener(this);
        this.brnBack.setOnClickListener(this);
        this.llSpriral.setOnClickListener(this);
        this.llBG.setOnClickListener(this);
        this.llBlur.setOnClickListener(this);
        this.imgClose1.setOnClickListener(this);
        // this.imgDefault.setOnClickListener(this);
        //this.imgRefresh.setOnClickListener(this);
        this.txt1.setOnClickListener(this);
        this.txt2.setOnClickListener(this);
        this.txt3.setOnClickListener(this);
        this.txt4.setOnClickListener(this);
        this.txt5.setOnClickListener(this);
        this.txt6.setOnClickListener(this);
        this.txt7.setOnClickListener(this);
        this.txt8.setOnClickListener(this);
        this.txt9.setOnClickListener(this);
        this.txt10.setOnClickListener(this);
        this.txt11.setOnClickListener(this);
        this.txt12.setOnClickListener(this);
        this.txt13.setOnClickListener(this);
        this.txt14.setOnClickListener(this);
        this.txt15.setOnClickListener(this);

        this.txtb1.setOnClickListener(this);
        this.txtb2.setOnClickListener(this);
        this.txtb3.setOnClickListener(this);
        this.txtb4.setOnClickListener(this);
        this.txtb5.setOnClickListener(this);
        this.txtb6.setOnClickListener(this);
        this.txtb7.setOnClickListener(this);
        this.txtb8.setOnClickListener(this);
        this.txtb9.setOnClickListener(this);
        this.txtb10.setOnClickListener(this);
        this.txtb11.setOnClickListener(this);
        this.txtb12.setOnClickListener(this);
        this.txtb13.setOnClickListener(this);
        this.txtb14.setOnClickListener(this);
        this.txtb15.setOnClickListener(this);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.blur_seek);
        this.seekBar = seekBar2;
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @SuppressLint("WrongConstant")
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    if (SpiralEditorActivity.this.flagForBG.equalsIgnoreCase(" ")) {
                        int progress = seekBar.getProgress();
                        if (progress == 0) {
                            ImageView imageView = SpiralEditorActivity.this.imgBG;
                            imageView.setImageURI(Uri.fromFile(new File("" + SpiralEditorActivity.this.stingBG)));
                            return;
                        }
                        RequestManager with = Glide.with((FragmentActivity) SpiralEditorActivity.this);
                        with.load(Uri.fromFile(new File("" + SpiralEditorActivity.this.stingBG))).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new BlurTransformation(progress))).into(SpiralEditorActivity.this.imgBG);
                        return;
                    }
                    int progress2 = seekBar.getProgress();
                    if (progress2 == 0) {
                        SpiralEditorActivity spiralEditorActivity = SpiralEditorActivity.this;
                        spiralEditorActivity.loadAssetInGlide(spiralEditorActivity.flagForBG, SpiralEditorActivity.this.imgBG1);
                    } else if (SpiralEditorActivity.this.bgByte != null) {
                        Glide.with((FragmentActivity) SpiralEditorActivity.this).load(SpiralEditorActivity.this.bgByte).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new BlurTransformation(progress2))).into(SpiralEditorActivity.this.imgBG1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SpiralEditorActivity.this.visibleMethod();
                    SpiralEditorActivity.this.rl_recycle.setVisibility(0);
                    SpiralEditorActivity.this.rl3view.setOnTouchListener(null);
                    SpiralEditorActivity.this.imgFront.setOnTouchListener(SpiralEditorActivity.this.multiTouchListener2view);
                    SpiralEditorActivity.this.rlBottom.setVisibility(0);
                    SpiralEditorActivity.this.loadSpiral("spiral");
                }
            }
        });
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.hue_seek);
        this.hueSeekBar = seekBar3;
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i == 0) {
                    SpiralEditorActivity.this.imgBack.setColorFilter(-1);
                    SpiralEditorActivity.this.imgFront.setColorFilter(-1);
                } else if (i == 100) {
                    SpiralEditorActivity.this.imgBack.setColorFilter(ViewCompat.MEASURED_STATE_MASK);
                    SpiralEditorActivity.this.imgFront.setColorFilter(ViewCompat.MEASURED_STATE_MASK);
                } else {
                    float f = (float) i;
                    SpiralEditorActivity.this.imgBack.setColorFilter(ColorFilterGenerator.adjustHue(f));
                    SpiralEditorActivity.this.imgFront.setColorFilter(ColorFilterGenerator.adjustHue(f));
                }
            }
        });
    }

    public void loadSpiral(String str) {
        String strCpy = str;
        this.flagForLoad = 0;
        try {
            this.listItem = getAssets().list("sp/" + str + "/thumb");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.listItem != null) {
            ArrayList arrayList = new ArrayList();
            String[] strArr = this.listItem;
            for (int i=0; i<strArr.length; i++) {
                arrayList.add("sp/" + str + "/thumb/" + strArr[i]);
            }
            String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
            this.listItem = strArr2;
            CommonAdapter commonAdapter2 = new CommonAdapter(strArr2, this);
            this.commonAdapter = commonAdapter2;
            this.recycleViewSpiral.setLayoutManager(new GridLayoutManager(this, 5));
            this.recycleViewSpiral.setAdapter(commonAdapter2);
            this.commonAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {

                @SuppressLint("WrongConstant")
                @Override
                public void onItemClick(View view, String str) {
                    if (str.equals("remove_new")) {

                        SpiralEditorActivity.this.imgBack.setImageResource(R.drawable.default_tra);
                        SpiralEditorActivity.this.imgFront.setImageResource(R.drawable.default_tra);
                        SpiralEditorActivity.this.imgFront.setOnTouchListener(null);
                        return;
                    } else {

                        //SpiralEditorActivity. this.multiTouchListener2view.moveDefault( SpiralEditorActivity.this.imgCut);
                        SpiralEditorActivity.this.multiTouchListener2view.moveDefault(SpiralEditorActivity.this.imgFront);
                        SpiralEditorActivity.this.multiTouchListener2view.moveDefault(SpiralEditorActivity.this.imgBack);
                        // SpiralEditorActivity. this.multiTouchListener.moveDefault( SpiralEditorActivity.this.rl3view);
                        SpiralEditorActivity.this.hueSeekBar.setProgress(50);
                        SpiralEditorActivity.this.imgBack.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
                        SpiralEditorActivity.this.imgFront.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
                        SpiralEditorActivity.this.rlHue.setVisibility(0);
                        SpiralEditorActivity.this.imgFront.setOnTouchListener(SpiralEditorActivity.this.multiTouchListener2view);

                       /* if (strCpy.equals("background")) {
                            SpiralEditorActivity.this.loadAssetInGlide(str.replace("thumb", ""), SpiralEditorActivity.this.imgBack);
                            SpiralEditorActivity.this.loadAssetInGlide(str.replace("thumb", ""), SpiralEditorActivity.this.imgFront);
                        } else {*/

                            SpiralEditorActivity.this.loadAssetInGlide(str.replace("thumb", "back"), SpiralEditorActivity.this.imgBack);
                            SpiralEditorActivity.this.loadAssetInGlide(str.replace("thumb", "front"), SpiralEditorActivity.this.imgFront);
                       // }

                    }

                }
            });
        }
    }

    public void loadBG(String strs) {
        this.flagForLoad = 1;

        try {
            this.listItem = getAssets().list("bg/" + strs + "/thumb");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            this.listItem = getAssets().list("bg_thumb");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        if (this.listItem != null) {
            ArrayList arrayList = new ArrayList();
            String[] strArr = this.listItem;
            for (String str2 : strArr) {
                arrayList.add("bg/" + strs + "/thumb/" + str2);
            }
            String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
            this.listItem = strArr2;
            CommonAdapter commonAdapter2 = new CommonAdapter(strArr2, this);
            this.commonAdapter = commonAdapter2;
            this.recycleViewSpiral.setAdapter(commonAdapter2);
            this.commonAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {

                @SuppressLint("WrongConstant")
                @Override
                public void onItemClick(View view, String str) {

                    Log.d("poli", str);

                    if (str.contains("remove_new")) {

                        SpiralEditorActivity.this.flagForBG = " ";
                        SpiralEditorActivity.this.rl3view.setOnTouchListener(null);
                        SpiralEditorActivity.this.imgFront.setOnTouchListener(SpiralEditorActivity.this.multiTouchListener2view);
                        SpiralEditorActivity.this.multiTouchListener2view.moveDefault(SpiralEditorActivity.this.imgCut);
                        SpiralEditorActivity.this.multiTouchListener2view.moveDefault(SpiralEditorActivity.this.imgFront);
                        SpiralEditorActivity.this.multiTouchListener2view.moveDefault(SpiralEditorActivity.this.imgBack);
                        SpiralEditorActivity.this.multiTouchListener.moveDefault(SpiralEditorActivity.this.rl3view);
                        SpiralEditorActivity.this.imgBG.setVisibility(0);
                        SpiralEditorActivity.this.imgBG1.setVisibility(8);
                        SpiralEditorActivity.this.seekBar.setProgress(0);
                        ImageView imageView = SpiralEditorActivity.this.imgBG;
                        imageView.setImageURI(Uri.fromFile(new File("" + SpiralEditorActivity.this.stingBG)));
                        return;

                    } else {



                       /* SpiralEditorActivity.this.flagForBG = str.replace("bg_thumb", "bg").replace("th", "bg");
                        SpiralEditorActivity spiralEditorActivity = SpiralEditorActivity.this;
                        spiralEditorActivity.bgByte = spiralEditorActivity.getByteFromImg(spiralEditorActivity.flagForBG);
                        SpiralEditorActivity.this.seekBar.setProgress(0);
                        SpiralEditorActivity.this.imgFront.setOnTouchListener(null);
                        SpiralEditorActivity.this.rl3view.setOnTouchListener(SpiralEditorActivity.this.multiTouchListener);
                        SpiralEditorActivity.this.imgBG.setVisibility(8);
                        SpiralEditorActivity.this.imgBG1.setVisibility(0);
                        SpiralEditorActivity spiralEditorActivity2 = SpiralEditorActivity.this;
                        spiralEditorActivity2.loadAssetInGlide(spiralEditorActivity2.flagForBG, SpiralEditorActivity.this.imgBG1);*/

                        SpiralEditorActivity.this.flagForBG = str.replace("thumb", "back");
                        SpiralEditorActivity spiralEditorActivity = SpiralEditorActivity.this;
                        spiralEditorActivity.bgByte = spiralEditorActivity.getByteFromImg(spiralEditorActivity.flagForBG);
                        SpiralEditorActivity.this.seekBar.setProgress(0);
                        SpiralEditorActivity.this.imgFront.setOnTouchListener(null);
                        SpiralEditorActivity.this.rl3view.setOnTouchListener(SpiralEditorActivity.this.multiTouchListener);
                        SpiralEditorActivity.this.imgBG.setVisibility(8);
                        SpiralEditorActivity.this.imgBG1.setVisibility(0);
                        SpiralEditorActivity spiralEditorActivity2 = SpiralEditorActivity.this;
                        spiralEditorActivity2.loadAssetInGlide(spiralEditorActivity2.flagForBG, SpiralEditorActivity.this.imgBG1);

                        // spiralEditorActivity2.loadAssetInGlide(str.replace("thumb", "back"), SpiralEditorActivity.this.imgBG1);

                    }

                }
            });
        }
    }

    public void loadAssetInGlide(String str, ImageView imageView) {
        getEncriptedImage(str, imageView);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                return;
            case R.id.btn_close_1:
                this.rlBottom.setVisibility(8);
                this.rlHue.setVisibility(8);
                return;
           /* case R.id.btn_default :
                int i = this.flagForLoad;
                if (i == 0) {
                    this.imgBack.setImageResource(R.drawable.default_tra);
                    this.imgFront.setImageResource(R.drawable.default_tra);
                    this.imgFront.setOnTouchListener(null);
                    return;
                } else if (i == 1) {
                    this.flagForBG = " ";
                    this.rl3view.setOnTouchListener(null);
                    this.imgFront.setOnTouchListener(this.multiTouchListener2view);
                    this.multiTouchListener2view.moveDefault(this.imgCut);
                    this.multiTouchListener2view.moveDefault(this.imgFront);
                    this.multiTouchListener2view.moveDefault(this.imgBack);
                    this.multiTouchListener.moveDefault(this.rl3view);
                    this.imgBG.setVisibility(0);
                    this.imgBG1.setVisibility(8);
                    this.seekBar.setProgress(0);
                    ImageView imageView = this.imgBG;
                    imageView.setImageURI(Uri.fromFile(new File("" + this.stingBG)));
                    return;
                } else {
                    return;
                }
            case R.id.btn_refresh :
                this.multiTouchListener2view.moveDefault(this.imgCut);
                this.multiTouchListener2view.moveDefault(this.imgFront);
                this.multiTouchListener2view.moveDefault(this.imgBack);
                this.multiTouchListener.moveDefault(this.rl3view);
                return;*/
            case R.id.btn_save:
                saveImage();
                return;
            case R.id.ll_bg:
                visibleMethod();
                this.llBgCat.setVisibility(0);
                this.rl_recycle.setVisibility(0);
                this.imgFront.setOnTouchListener(null);
                this.rl3view.setOnTouchListener(this.multiTouchListener);
                this.rlBottom.setVisibility(0);
                loadBG("neon");
                return;
            case R.id.ll_blur:
                this.rlBottom.setVisibility(0);
                visibleMethod();
                this.llBlurCat.setVisibility(0);
                this.rl_blur.setVisibility(0);
                return;
            case R.id.ll_spiral:
                visibleMethod();
                this.rl_recycle.setVisibility(0);
                this.llSpiralCat.setVisibility(0);
                this.rl3view.setOnTouchListener(null);
                this.imgFront.setOnTouchListener(this.multiTouchListener2view);
                this.rlBottom.setVisibility(0);
                loadSpiral("spiral");
                return;
            case R.id.remove_hue:
                this.hueSeekBar.setProgress(50);
                this.imgBack.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
                this.imgFront.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
                return;
            case R.id.txt_1:
                loadSpiral("spiral");
                setActiveColor();
                this.txt1.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_2:
                loadSpiral("wings");
                setActiveColor();
                this.txt2.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_3:
                loadSpiral("shape");
                setActiveColor();
                this.txt3.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_4:
                loadSpiral("rainbow");
                setActiveColor();
                this.txt4.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_5:
                loadSpiral("smoke");
                setActiveColor();
                this.txt5.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_6:
                loadSpiral("emoji");
                setActiveColor();
                this.txt6.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_7:
                loadSpiral("love");
                setActiveColor();
                this.txt7.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_8:
                loadSpiral("celebration");
                setActiveColor();
                this.txt8.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_9:
                loadSpiral("graduation");
                setActiveColor();
                this.txt9.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_10:
                loadSpiral("frame");
                setActiveColor();
                this.txt10.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_11:
                loadSpiral("background");
                setActiveColor();
                this.txt11.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_12:
                loadSpiral("nature");
                setActiveColor();
                this.txt12.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_13:
                loadSpiral("influencer");
                setActiveColor();
                this.txt13.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_14:
                loadSpiral("animals");
                setActiveColor();
                this.txt14.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txt_15:
                loadSpiral("friends");
                setActiveColor();
                this.txt15.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_1:
                loadBG("neon");
                setActiveColor();
                this.txtb1.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_2:
                loadBG("city");
                setActiveColor();
                this.txtb2.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_3:
                loadBG("celebration");
                setActiveColor();
                this.txtb3.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_4:
                loadBG("love");
                setActiveColor();
                this.txtb4.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_5:
                loadBG("nature");
                setActiveColor();
                this.txtb5.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_6:
                loadBG("air");
                setActiveColor();
                this.txtb6.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_7:
                loadBG("doors");
                setActiveColor();
                this.txtb7.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_8:
                loadBG("road");
                setActiveColor();
                this.txtb8.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_9:
                loadBG("summer");
                setActiveColor();
                this.txtb9.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_10:
                loadBG("fantasy");
                setActiveColor();
                this.txtb10.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_11:
                loadBG("wedding");
                setActiveColor();
                this.txtb11.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_12:
                loadBG("rooms");
                setActiveColor();
                this.txtb12.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_13:
                loadBG("kids");
                setActiveColor();
                this.txtb13.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_14:
                loadBG("houses");
                setActiveColor();
                this.txtb14.setTextColor(getResources().getColor(R.color.white));
                return;
            case R.id.txtbg_15:
                loadBG("others");
                setActiveColor();
                this.txtb15.setTextColor(getResources().getColor(R.color.white));
                return;

            default:
                return;
        }
    }

    public void setActiveColor() {
        this.txt1.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt2.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt3.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt4.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt5.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt6.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt7.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt8.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt9.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt10.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt11.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt12.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt13.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt14.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txt15.setTextColor(getResources().getColor(R.color.white_smoke));


        this.txtb1.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb2.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb3.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb4.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb5.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb6.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb7.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb8.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb9.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb10.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb11.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb12.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb13.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb14.setTextColor(getResources().getColor(R.color.white_smoke));
        this.txtb15.setTextColor(getResources().getColor(R.color.white_smoke));


    }

    @SuppressLint("WrongConstant")
    public void visibleMethod() {
        this.rlHue.setVisibility(8);
        this.rl_blur.setVisibility(8);
        this.rl_recycle.setVisibility(8);
        this.llBlurCat.setVisibility(8);
        this.llBgCat.setVisibility(8);
        this.llSpiralCat.setVisibility(8);
    }

    private void saveImage() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestPermission("android.permission.WRITE_EXTERNAL_STORAGE", getString(R.string.permission_write_storage_rationale), 102);
        } else {
            new saveAndGo().execute(new Void[0]);
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class saveAndGo extends AsyncTask<Void, Void, String> {

        public String doInBackground(Void... voidArr) {
            return "";
        }

        public void onPreExecute() {

        }

        @SuppressLint("WrongConstant")
        public void onPostExecute(String str) {
            SpiralEditorActivity spiralEditorActivity = SpiralEditorActivity.this;
            spiralEditorActivity.savePath = spiralEditorActivity.savePhoto();
            if (SpiralEditorActivity.this.savePath.equals("")) {
                Toast.makeText(SpiralEditorActivity.this, "Couldn't save photo, error", 0).show();
            } else {
                openNext();
            }
        }
    }

    @SuppressLint("WrongConstant")
    public String savePhoto() {
        String str = "";
        try {
            this.rlMain.setDrawingCacheEnabled(true);
            this.rlMain.setDrawingCacheQuality(1048576);
            Bitmap drawingCache = this.rlMain.getDrawingCache();
            String str2 = new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.valueOf(System.currentTimeMillis()) + ".jpg";
            File file = new File(pathtoSave("SpiralPhoto"));
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                File file2 = new File(file.getAbsolutePath() + File.separator + str2);
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
                fileOutputStream.flush();
                fileOutputStream.close();
                str = file.getAbsolutePath() + File.separator + str2;
                MediaScannerConnection.scanFile(this, new String[]{file2.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                if (Build.VERSION.SDK_INT < 29) {
                    addImageGallery(str);
                }
            } catch (Exception e) {
                Log.i("Photos to Collage", e.getMessage());
            } catch (Throwable th) {
            }
            this.rlMain.setDrawingCacheEnabled(false);
        } catch (Exception unused) {
        }
        return str;
    }

    private void addImageGallery(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("mime_type", "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private String pathtoSave(String str) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/SpiralPhoto").getPath();
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 102) {
            super.onRequestPermissionsResult(i, strArr, iArr);
        } else if (iArr[0] == 0) {
            saveImage();
        }
    }

    public void openNext() {
        Intent intent = new Intent().setClass(this, SaveImageActivity.class);
        intent.setData(Uri.parse(this.savePath));
        startActivity(intent);
        //finish();
    }

    public void getEncriptedImage(String str, ImageView imageView) {
        try {
            InputStream open = getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            String ASSET_IMG = str;
            Glide.with((FragmentActivity) this).load(Uri.parse("file:///android_asset/" + ASSET_IMG)).into(imageView);
            // Glide.with((FragmentActivity) this).load(BitmapUtil.decodeFile(bArr)).into(imageView);
        } catch (IOException e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.default_tra);
        } catch (Exception e2) {
            e2.printStackTrace();
            imageView.setImageResource(R.drawable.default_tra);
        }
    }

    public byte[] getByteFromImg(String str) {
        try {
            InputStream open = getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return BitmapUtil.decodeFile(bArr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @SuppressLint("WrongConstant")
    public void removeEffects() {
        int i = this.flagForLoad;
        if (i == 0) {
            this.imgBack.setImageResource(R.drawable.default_tra);
            this.imgFront.setImageResource(R.drawable.default_tra);
            this.imgFront.setOnTouchListener(null);
            return;
        } else if (i == 1) {
            this.flagForBG = " ";
            this.rl3view.setOnTouchListener(null);
            this.imgFront.setOnTouchListener(this.multiTouchListener2view);
            this.multiTouchListener2view.moveDefault(this.imgCut);
            this.multiTouchListener2view.moveDefault(this.imgFront);
            this.multiTouchListener2view.moveDefault(this.imgBack);
            this.multiTouchListener.moveDefault(this.rl3view);
            this.imgBG.setVisibility(0);
            this.imgBG1.setVisibility(8);
            this.seekBar.setProgress(0);
            ImageView imageView = this.imgBG;
            imageView.setImageURI(Uri.fromFile(new File("" + this.stingBG)));
            return;
        } else {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  CommonMethods.getInstance().activitiesAD(SpiralEditorActivity.this);
    }
}
