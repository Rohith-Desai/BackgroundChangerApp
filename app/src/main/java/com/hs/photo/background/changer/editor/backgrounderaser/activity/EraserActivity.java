package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.Views.HoverView;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.util.Datastore;

import static com.hs.photo.background.changer.editor.backgrounderaser.Views.HoverView.savedBitmap;
import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;

import java.io.File;

public class EraserActivity extends AppCompatActivity implements View.OnClickListener {

    private ContentResolver mContentResolver;
    private Bitmap mBitmap;
    public static EraserActivity eraserActivity;
    public HoverView mHoverView;
    double mDensity;
    int viewWidth;
    int viewHeight;
    int bmWidth;
    int bmHeight;
    int actionBarHeight;
    int bottombarHeight;
    double bmRatio;
    double viewRatio;
    public LinearLayout magicWind, eraser, eraserDone, mirror, zoom;
    public TextView magicWindTxt, eraserTxt, eraserDoneTxt, mirrorTxt, zoomTxt;
    public ImageView magicWindImg, eraserImg, eraserDoneImg, mirrorImg, zoomImg;
    ImageView eraserSubButton, unEraserSubButton;
    ImageView magicRemoveButton, magicRestoreButton;
    ImageView undoButton, redoButton;
    ImageView colorButton, magicAImg;
    public SeekBar magicSeekBar, brushSizeSeekBar, brushOffsetSeekBar;
    public RelativeLayout eraserLayout, magicLayout, zoomLayout;
    RelativeLayout mLayout;
    LinearLayout backToImgCrop;
    String stingCut;
    private FrameLayout adContainerView;
    Bitmap erasebitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eraser);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "EraserActivity");
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("ACTIVITY_SELECTION", bundle);
        }

        mContentResolver = getContentResolver();
        eraserActivity = this;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        adContainerView = findViewById(R.id.erase_banner_ad);

        if (RemoteConfigValues.getOurRemote().getShowBannerAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")) {

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAderase();
                    }
                });

            }
        }
       /* adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adContainerView, EraserActivity.this);
            }
        });*/
        if (getIntent().hasExtra("eraseimage")) {

            this.stingCut = getIntent().getStringExtra("eraseimage");
            this.mBitmap = BitmapFactory.decodeFile(this.stingCut);

        } else {

//            byte[] byteArray = getIntent().getByteArrayExtra("SELECTED_IMAGE");
//            this.mBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
//            this.mBitmap = (Bitmap) getIntent().getParcelableExtra("SELECTED_IMAGE");

            this.stingCut = getIntent().getStringExtra("SELECTED_IMAGE");
            this.mBitmap = BitmapFactory.decodeFile(this.stingCut);
        }

       /* if (Datastore.getOurRemote()!=null){
            mBitmap=Datastore.getOurRemote().getStickerBimap();
        }*/
        mLayout = findViewById(R.id.mainLayout);
        mDensity = getResources().getDisplayMetrics().density;
        actionBarHeight = (int) (110 * mDensity);
        bottombarHeight = (int) (60 * mDensity);
        viewWidth = getResources().getDisplayMetrics().widthPixels;
        viewHeight = getResources().getDisplayMetrics().heightPixels - actionBarHeight - bottombarHeight;
        viewRatio = (double) viewHeight / (double) viewWidth;
        if (mBitmap != null) {
            bmRatio = (double) mBitmap.getHeight() / (double) mBitmap.getWidth();
            if (bmRatio < viewRatio) {
                bmWidth = viewWidth;
                bmHeight = (int) (((double) viewWidth) * ((double) (mBitmap.getHeight()) / (double) (mBitmap.getWidth())));
            } else {
                bmHeight = viewHeight;
                bmWidth = (int) (((double) viewHeight) * ((double) (mBitmap.getWidth()) / (double) (mBitmap.getHeight())));
            }
            mBitmap = Bitmap.createScaledBitmap(mBitmap, bmWidth, bmHeight, false);
            mHoverView = new HoverView(this, mBitmap, bmWidth, bmHeight, viewWidth, viewHeight);
            mHoverView.setLayoutParams(new ViewGroup.LayoutParams(viewWidth, viewHeight));
            mLayout.addView(mHoverView);
        }
        backToImgCrop = findViewById(R.id.back_to_img_crop);
        backToImgCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initButton();
    }

    public void loadBannerAderase() {
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

    public void initButton() {


        magicWind = findViewById(R.id.button_magic);
        eraser = findViewById(R.id.button_eraser);
        eraserDone = findViewById(R.id.button_eraser_done);
        mirror = findViewById(R.id.button_mirror);
        zoom = findViewById(R.id.button_zoom);

        magicWind.setOnClickListener(this);
        eraser.setOnClickListener(this);
        eraserDone.setOnClickListener(this);
        mirror.setOnClickListener(this);
        zoom.setOnClickListener(this);

        magicWindTxt = findViewById(R.id.magic_btn_txt);
        eraserTxt = findViewById(R.id.eraser_btn_txt);
        eraserDoneTxt = findViewById(R.id.eraser_done_btn_txt);
        mirrorTxt = findViewById(R.id.mirror_btn_txt);
        zoomTxt = findViewById(R.id.zoom_btn_txt);

        magicWindImg = findViewById(R.id.magic_button_img);
        eraserImg = findViewById(R.id.eraser_button_img);
        eraserDoneImg = findViewById(R.id.eraser_done_button_img);
        mirrorImg = findViewById(R.id.mirror_button_img);
        zoomImg = findViewById(R.id.zoom_button_img);

        magicAImg = findViewById(R.id.magic_button_img);

        eraserSubButton = findViewById(R.id.erase_sub_button);
        eraserSubButton.setOnClickListener(this);
        unEraserSubButton = findViewById(R.id.unerase_sub_button);
        unEraserSubButton.setOnClickListener(this);

        brushSizeSeekBar = findViewById(R.id.brush_siz_seekbar);
        brushSizeSeekBar.incrementProgressBy(20);
        brushSizeSeekBar.setMax(200);
        brushSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar != null) {
                    if (mHoverView != null) {
                        mHoverView.setEraseBrushSize(seekBar.getProgress());
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });

        brushOffsetSeekBar = findViewById(R.id.brush_offset_seekbar);
        brushOffsetSeekBar.incrementProgressBy(20);
        brushOffsetSeekBar.setMax(200);
        brushOffsetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar != null) {
                    if (mHoverView != null) {
                        mHoverView.setPointerOffset(seekBar.getProgress());
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });
        magicSeekBar = findViewById(R.id.magic_seekbar);
        magicSeekBar.setProgress(15);
        magicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mHoverView != null) {
                    mHoverView.setMagicThreshold(seekBar.getProgress());
                    if (mHoverView.getMode() == mHoverView.MAGIC_MODE)
                        mHoverView.magicEraseBitmap();//null
                    else if (mHoverView.getMode() == mHoverView.MAGIC_MODE_RESTORE)
                        mHoverView.magicRestoreBitmap();
                    mHoverView.invalidateView();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });
        magicRemoveButton = findViewById(R.id.magic_remove_button);
        magicRemoveButton.setOnClickListener(this);
        magicRestoreButton = findViewById(R.id.magic_restore_button);
        magicRestoreButton.setOnClickListener(this);
        undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(this);

        redoButton = findViewById(R.id.redoButton);
        redoButton.setOnClickListener(this);
        updateRedoButton();
        eraserLayout = findViewById(R.id.eraser_layout);
        magicLayout = findViewById(R.id.magicWand_layout);
        zoomLayout = findViewById(R.id.zoom_layout);

        colorButton = findViewById(R.id.colorButton);
        colorButton.setOnClickListener(this);

        if (mHoverView != null)
            mHoverView.switchMode(HoverView.ERASE_MODE);
        resetSubEraserButtonState();
        eraserSubButton.setSelected(true);
    }

    public void resetSeekBar() {
        magicSeekBar.setProgress(0);
        if (mHoverView != null) {
            mHoverView.setMagicThreshold(0);
        }
    }

    int currentColor = 0;

    public void setBackGroundColor(int color) {
        switch (color) {
            case 0:
                mLayout.setBackgroundResource(R.drawable.bg);
                colorButton.setBackgroundResource(R.drawable.white_drawable);
                break;
            case 1:
                mLayout.setBackgroundColor(Color.WHITE);
                colorButton.setBackgroundResource(R.drawable.black_drawable);
                break;
            case 2:
                mLayout.setBackgroundColor(Color.BLACK);
                colorButton.setBackgroundResource(R.drawable.transparent_drawable);
                break;

            default:
                break;
        }

        currentColor = color;
    }

    public void resetSubEraserButtonState() {
        eraserSubButton.setSelected(false);
        unEraserSubButton.setSelected(false);
    }

    public void resetSubMagicButtonState() {
        magicRemoveButton.setSelected(false);
        magicRestoreButton.setSelected(false);
    }

    public void updateUndoButton() {
        if (mHoverView != null) {
            if (mHoverView.checkUndoEnable()) {
                undoButton.setEnabled(true);
                undoButton.setAlpha(1.0f);
            } else {
                undoButton.setEnabled(false);
                undoButton.setAlpha(0.3f);
            }
        }
    }

    public void updateRedoButton() {
        if (mHoverView != null) {
            if (mHoverView.checkRedoEnable()) {
                redoButton.setEnabled(true);
                redoButton.setAlpha(1.0f);
            } else {
                redoButton.setEnabled(false);
                redoButton.setAlpha(0.3f);
            }
        }
    }

    @Override
    public void onClick(View v) {
        updateUndoButton();
        updateRedoButton();
        switch (v.getId()) {
            case R.id.button_eraser:
                zoomLayout.setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.switchMode(mHoverView.ERASE_MODE);  //null
                }
                if (eraserLayout.getVisibility() == View.VISIBLE) {
                    eraserLayout.setVisibility(View.GONE);
                } else {
                    eraserLayout.setVisibility(View.VISIBLE);
                }
                magicLayout.setVisibility(View.GONE);
                resetSubEraserButtonState();
                eraserSubButton.setSelected(true);
                if (Datastore.getOurRemote() != null) {
                    Datastore.getOurRemote().setColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;
            case R.id.button_magic:
                zoomLayout.setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MAGIC_MODE);//null
                }
                if (magicLayout.getVisibility() == View.VISIBLE) {
                    magicLayout.setVisibility(View.GONE);
                } else {
                    magicLayout.setVisibility(View.VISIBLE);
                }
                eraserLayout.setVisibility(View.GONE);
                resetSubMagicButtonState();
                resetSeekBar();
                magicRemoveButton.setSelected(true);
                if (Datastore.getOurRemote() != null) {
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    Datastore.getOurRemote().setColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;
            case R.id.button_mirror:
                zoomLayout.setVisibility(View.GONE);
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.mirrorImage();  //OutOfMemoryError
                }
                if (Datastore.getOurRemote() != null) {
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    Datastore.getOurRemote().setColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;
            case R.id.button_zoom:
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MOVING_MODE);
                }
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                findViewById(R.id.zoom_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);

                if (Datastore.getOurRemote() != null) {
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    Datastore.getOurRemote().setColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;

            case R.id.erase_sub_button:
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.ERASE_MODE);//null
                }
                resetSubEraserButtonState();
                eraserSubButton.setSelected(true);
                break;
            case R.id.unerase_sub_button:
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.UNERASE_MODE);//null
                }
                resetSubEraserButtonState();
                unEraserSubButton.setSelected(true);
                break;
            case R.id.magic_remove_button:
                resetSubMagicButtonState();
                magicRemoveButton.setSelected(true);
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MAGIC_MODE);
                }
                resetSeekBar();
                break;

            case R.id.magic_restore_button:
                resetSubMagicButtonState();
                magicRestoreButton.setSelected(true);
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MAGIC_MODE_RESTORE);
                }
                resetSeekBar();
                break;

            case R.id.button_eraser_done:
                if (Datastore.getOurRemote() != null) {
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    Datastore.getOurRemote().setColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    Datastore.getOurRemote().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                if (mHoverView != null) {
                    mHoverView.saveDrawnBitmap();
                }
                Datastore.getOurRemote().setStickerBitmap(savedBitmap);
                System.out.println(savedBitmap);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;

            case R.id.colorButton:
                setBackGroundColor((currentColor + 1) % 3);
                break;

            case R.id.undoButton:
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.undo();
                    if (mHoverView.checkUndoEnable()) {
                        undoButton.setEnabled(true);
                        undoButton.setAlpha(1.0f);
                    } else {
                        undoButton.setEnabled(false);
                        undoButton.setAlpha(0.3f);
                    }
                }
                updateRedoButton();
                break;
            case R.id.redoButton:
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.redo();//null
                }
                updateUndoButton();
                updateRedoButton();
                break;
        }

    }
}