package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.Views.BubbleTextItemView;
import com.hs.photo.background.changer.editor.backgrounderaser.Views.StickerView;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.ItemAdapter;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.AnimalFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.AnniversaryFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.BirdFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.BirdFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.BirthdayFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.BirthdayFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.CityFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FantasyFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FantasyFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FireFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FlowerFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FlowerFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.LoveFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.NatureFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.NatureFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.NeoneFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.NeoneFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.SunsetFragmentNew;
import com.hs.photo.background.changer.editor.backgrounderaser.mytouch.MultiTouchListener2view;
import com.hs.photo.background.changer.editor.backgrounderaser.util.ColorFilterGenerator;
import com.hs.photo.background.changer.editor.backgrounderaser.util.Datastore;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.widget.Toast.LENGTH_SHORT;
import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;

public class BackgroundnewActvity extends BaseActivity implements NatureFragmentNew.NatAdapterListenernew, FantasyFragmentNew.FantasyAdapterListenernew,
        FlowerFragmentNew.FlowerAdapterListenernew, BirthdayFragmentNew.BirthdayAdapterListenernew, NeoneFragmentNew.NeonAdapterListenernew, AnimalFragmentNew.AnimalAdapterListenernew,
        AnniversaryFragmentNew.AnniversaryAdapterListenernew, BirdFragmentNew.BirdAdapterListenernew, CityFragmentNew.CityAdapterListenernew, FireFragmentNew.FireAdapterListenernew, LoveFragmentNew.LoveAdapterListenernew, SunsetFragmentNew.SunsetAdapterListenernew {

    private String stingBG;
    private String stingCut;
    ImageView imgCut;
    ImageView multiTouchimg;
    MultiTouchListener2view multiTouchListener2view;
    ArrayList<Integer> nback = new ArrayList<>();
    ArrayList<Integer> nfore = new ArrayList<>();
    public int catPos;
    public int itemposBackground;
    // public int itemPos;
    ImageView imagBack;
    ImageView imgFore;
    ViewPager viewPagerCat;
    TabLayout tabLayoutCat;
    LinearLayout saveButton;
    public String savePath;
    RelativeLayout rlMain;
    ConstraintLayout rlBody;
    private TextView tabOne;
    private TextView tabTwo;
    private TextView tabThree;
    private TextView tabFour;
    private TextView tabFive;
    private TextView tabSix;
    private TextView tabSeven;
    private TextView tabEight;
    private TextView tabNine;
    private TextView tabTen;
    private TextView tabEleven;
    private TextView tabTwelve;
    private TextView backButtonbg;
    ProgressDialog pDialog;
    private String backPath;
    private String forePath;
    // ImageView bgCancel;
    RelativeLayout backRelat;
    LinearLayout backLinear;
    LinearLayout addText;
    int shadowValue = 0;
    private int currentColor = Color.parseColor("#00ff00");
    private StickerView mCurrentView;
    private ArrayList<View> mViews;
    private BubbleTextItemView mCurrentEditTextView;
    private String[] types;
    Typeface type_1, type_2, type_3, type_4, type_5, type_6, type_7, type_8, type_9, type_10, type_11, type_12,
            type_13, type_14, type_15, type_16, type_17, type_18, type_19, type_20, type_21, type_22, selectedType;

    RelativeLayout addStrickerview;
    LinearLayout addStickimg;
    ArrayList<Object> stickersShortList = new ArrayList<>();
    //ImageView btnStickerclose;
    RelativeLayout stickerRelative;
    RecyclerView strickerRecycler;
    LinearLayout erase_linear;
    private String originalPath;
    RelativeLayout editFeatures;
    private FrameLayout adContainerView;
    ImageView BgImag;
    ImageView StickerImg;
    ImageView TextImg;
    ImageView EraseImg;
    TextView BgText;
    TextView StickerText;
    TextView TextText;
    TextView EraseText;

    private RewardedAd mRewardedAd;
    private final String TAG = "BackgroundnewActvity";

    androidx.appcompat.app.AlertDialog alertDialog;
    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;

    private static boolean canRewarded = false;
    private static boolean rewardVideoComplete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backgroundnew_actvity);

        this.pDialog = new ProgressDialog(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "BackgroundnewActivity");
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("ACTIVITY_SELECTION", bundle);
        }

        adContainerView = findViewById(R.id.edit_banner_ad);
        if (RemoteConfigValues.getOurRemote().getShowBannerAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")) {

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAdedit();
                    }
                });

            }
        }
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        alertDialog = alertDialogBuilder.create();
       /* adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adContainerView, BackgroundnewActvity.this);
            }
        });*/

        // this.stingBG = getIntent().getStringExtra("SELECTED_IMAGE_PATH");
        if (getIntent().hasExtra("SELECTED_IMAGE")) {

            this.stingCut = getIntent().getStringExtra("SELECTED_IMAGE");

        }
        // this.stingCut = getIntent().getStringExtra("SELECTED_IMAGE");
        this.catPos = Integer.parseInt(String.valueOf(getIntent().getIntExtra("catPos", 0)));
        this.itemposBackground = Integer.parseInt(String.valueOf(getIntent().getIntExtra("currentItem", 0)));
        // this.itemPos=Integer.parseInt(String.valueOf(getIntent().getIntExtra("itemPos",0)));
        this.backPath = getIntent().getStringExtra("backPath");
        this.forePath = getIntent().getStringExtra("forePath");
        // this.originalPath=getIntent().getStringExtra("originalPath");

        this.rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        this.rlBody = (ConstraintLayout) findViewById(R.id.rlBody);
        this.backButtonbg = (TextView) findViewById(R.id.btn_back_bg);
        this.backButtonbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        loadStickerdata();

        //  String stringExtra = getIntent().getStringExtra("ratio");

        mViews = new ArrayList<>();

        types = new String[]{"earwig factory", "action jackson", "barbatrick", "if", "BrushScriptStd",
                "FancyPantsNF", "Fiddums Family", "Fortunaschwein_complete", "FUNDR__", "HoboStd",
                "hotpizza", "NuevaStd-Bold", "NuevaStd-BoldCond", "NuevaStd-BoldCondItalic"
        };
        createFonts();

        // bgCancel=(ImageView)findViewById(R.id.btn_close_1);
        backRelat = (RelativeLayout) findViewById(R.id.rlBottom);
        backLinear = (LinearLayout) findViewById(R.id.ll_background);
        addText = (LinearLayout) findViewById(R.id.ll_text);
        addStrickerview = (RelativeLayout) findViewById(R.id.rl3view);
        addStickimg = (LinearLayout) findViewById(R.id.ll_sticker);
        //  btnStickerclose=(ImageView)findViewById(R.id.btn_sticker_close);
        stickerRelative = (RelativeLayout) findViewById(R.id.sticker_rel);
        strickerRecycler = (RecyclerView) findViewById(R.id.sticker_recycler);
        erase_linear = (LinearLayout) findViewById(R.id.ll_eraser);
        editFeatures = (RelativeLayout) findViewById(R.id.edit_features);
        BgImag = (ImageView) findViewById(R.id.bbg_img);
        StickerImg = (ImageView) findViewById(R.id.sticker_img);
        TextImg = (ImageView) findViewById(R.id.text_img);
        EraseImg = (ImageView) findViewById(R.id.eraser_img);
        BgText = (TextView) findViewById(R.id.bg_text);
        StickerText = (TextView) findViewById(R.id.sticker_text);
        TextText = (TextView) findViewById(R.id.text_text);
        EraseText = (TextView) findViewById(R.id.eraser_text);

        erase_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Click eraser feature");
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FEATURES_SELECTION", bundle);
                }
                backRelat.setVisibility(View.GONE);
                stickerRelative.setVisibility(View.GONE);

                BgImag.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                StickerImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                TextImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                EraseImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.orange));
                BgText.setTextColor(Color.parseColor("#ffffffff"));
                StickerText.setTextColor(Color.parseColor("#ffffffff"));
                TextText.setTextColor(Color.parseColor("#ffffffff"));
                EraseText.setTextColor(Color.parseColor("#ffa500"));

                reoveStickerBorders();

                startCropactivity();


            }
        });
       /* btnStickerclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Stickers feature close");
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FEATURES_SELECTION", bundle);
                }

                reoveStickerBorders();
                stickerRelative.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slid_down));
                stickerRelative.setVisibility(View.GONE);
                editFeatures.setVisibility(View.VISIBLE);
                editFeatures.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up));


            }
        });*/
        addStickimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Click stickers feature");
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FEATURES_SELECTION", bundle);
                }

                BgImag.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                StickerImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.orange));
                TextImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                EraseImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                BgText.setTextColor(Color.parseColor("#ffffffff"));
                StickerText.setTextColor(Color.parseColor("#ffa500"));
                TextText.setTextColor(Color.parseColor("#ffffffff"));
                EraseText.setTextColor(Color.parseColor("#ffffffff"));

                reoveStickerBorders();
                /*backRelat.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slid_down));*/
                backRelat.setVisibility(View.GONE);
                stickerRelative.setVisibility(View.VISIBLE);
              /*  stickerRelative.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up));*/

                openStickers();

            }
        });
        addStrickerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reoveStickerBorders();
            }
        });
        rlBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reoveStickerBorders();
            }
        });
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Click frames feature");
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FEATURES_SELECTION", bundle);
                }
                BgImag.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.orange));
                StickerImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                TextImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                EraseImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                BgText.setTextColor(Color.parseColor("#ffa500"));
                StickerText.setTextColor(Color.parseColor("#ffffffff"));
                TextText.setTextColor(Color.parseColor("#ffffffff"));
                EraseText.setTextColor(Color.parseColor("#ffffffff"));
                reoveStickerBorders();

               /* stickerRelative.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slid_down));*/
                stickerRelative.setVisibility(View.GONE);
                backRelat.setVisibility(View.VISIBLE);
               /* backRelat.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up));*/
            }
        });
       /* bgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Features visible to user");
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FEATURES_SELECTION", bundle);
                }

                backRelat.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slid_down));
                backRelat.setVisibility(View.GONE);
                editFeatures.setVisibility(View.VISIBLE);
                editFeatures.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up));



            }
        });*/
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Click add text feature");
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FEATURES_SELECTION", bundle);
                }
                BgImag.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                StickerImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                TextImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.orange));
                EraseImg.setColorFilter(ContextCompat.getColor(BackgroundnewActvity.this, R.color.white));
                BgText.setTextColor(Color.parseColor("#ffffffff"));
                StickerText.setTextColor(Color.parseColor("#ffffffff"));
                TextText.setTextColor(Color.parseColor("#ffa500"));
                EraseText.setTextColor(Color.parseColor("#ffffffff"));
                backRelat.setVisibility(View.GONE);
                stickerRelative.setVisibility(View.GONE);
                reoveStickerBorders();

                openAlertDialog();

            }
        });


        imagBack = findViewById(R.id.img_back);
        imgFore = findViewById(R.id.img_front);
        //imagBack.setImageURI(Uri.fromFile(new File("" + this.backPath)));
        // imgFore.setImageURI(Uri.fromFile(new File("" + this.forePath)));
        if (itemposBackground == 0 && catPos == 0) {


            imagBack.setImageResource(getImage("" + this.backPath));
            imgFore.setImageResource(getImage("" + this.forePath));

        } else {
            imagBack.setImageURI(Uri.fromFile(new File("" + this.backPath)));
            imgFore.setImageURI(Uri.fromFile(new File("" + this.forePath)));
        }

        imgCut = findViewById(R.id.imgCut);
        ImageView imageView2 = this.imgCut;
        imageView2.setImageURI(Uri.fromFile(new File("" + this.stingCut)));
        multiTouchimg = findViewById(R.id.fore_multitouch);
        multiTouchListener2view = new MultiTouchListener2view(this.imgCut);

        BackgroundnewActvity.this.multiTouchListener2view.moveDefault(BackgroundnewActvity.this.multiTouchimg);
        BackgroundnewActvity.this.multiTouchListener2view.moveDefault(BackgroundnewActvity.this.imgCut);
        BackgroundnewActvity.this.imgCut.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
        BackgroundnewActvity.this.multiTouchimg.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
        BackgroundnewActvity.this.multiTouchimg.setOnTouchListener(BackgroundnewActvity.this.multiTouchListener2view);


        viewPagerCat = findViewById(R.id.view_pager_cat);
        tabLayoutCat = findViewById(R.id.tab_main_cat);
        setupViewPager(viewPagerCat);
        tabLayoutCat.setupWithViewPager(viewPagerCat);

        viewPagerCat.setCurrentItem(catPos);
        setupTabIcons();
        tabLayoutCat.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {

                    case 11:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
                        Bundle bundle12 = new Bundle();
                        bundle12.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Sunset");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle12);
                        }
                        break;

                    case 10:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
                        Bundle bundle11 = new Bundle();
                        bundle11.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Love");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle11);
                        }
                        break;

                    case 9:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);

                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle10 = new Bundle();
                        bundle10.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Fire");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle10);
                        }
                        break;

                    case 8:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle9 = new Bundle();
                        bundle9.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "City");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle9);
                        }
                        break;

                    case 7:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle8 = new Bundle();
                        bundle8.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Bird");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle8);
                        }
                        break;
                    case 6:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle7 = new Bundle();
                        bundle7.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Anniversary");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle7);
                        }
                        break;
                    case 5:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle6 = new Bundle();
                        bundle6.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Animal");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle6);
                        }
                        break;
                    case 4:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle5 = new Bundle();
                        bundle5.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Neon");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle5);
                        }
                        break;
                    case 3:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);

                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle4 = new Bundle();
                        bundle4.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Birthday");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle4);
                        }
                        break;

                    case 2:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);

                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Flower");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle3);
                        }
                        break;


                    case 1:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);


                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Fantasy");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle2);
                        }
                        break;

                    case 0:
                        tabOne.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayoutCat.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(4).setCustomView(tabFive);

                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Nature");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_EDITSCREEN", bundle1);
                        }
                        break;


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        saveButton = findViewById(R.id.btn_save_bg);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Click save button");
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FEATURES_SELECTION", bundle);
                }

                reoveStickerBorders();
                saveImage();
            }
        });

    }

    public void loadBannerAdedit() {
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

    public int getImage(String imageName) {

        int drawableResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        return drawableResourceId;
    }

    private void startCropactivity() {

        Intent intent = new Intent(BackgroundnewActvity.this, EraserActivity.class);
        intent.putExtra("eraseimage", this.stingCut);
        startActivityForResult(intent, 1);

    }

    private void loadStickerdata() {
        stickersShortList.clear();
        for (int i = 1; i <= 17; i++) {
            stickersShortList.add("n_" + i);
        }

    }

    private void openStickers() {
        prepareList(stickersShortList);
    }

    private void prepareList(ArrayList list) {

        ItemAdapter framesAdapter = new ItemAdapter(BackgroundnewActvity.this, list);
        strickerRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        strickerRecycler.setItemAnimator(new DefaultItemAnimator());
        strickerRecycler.setAdapter(framesAdapter);
        // strickerRecycler.setVisibility(View.VISIBLE);

    }

    public void stickerAdd(int sticker) {

        final StickerView stickerView = new StickerView(this);
        stickerView.setImageResource(sticker);

        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                addStrickerview.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mViews.indexOf(stickerView);
                if (position == mViews.size() - 1) {
                    return;
                }
                if (position >= 0 && position < mViews.size()) {
                    StickerView stickerTemp = (StickerView) mViews.remove(position);
                    mViews.add(mViews.size(), stickerTemp);
                }
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addStrickerview.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView);

    }

    private void openAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
        final EditText enteredText = (EditText) dialogView.findViewById(R.id.text_view);
        final TextView textView = (TextView) dialogView.findViewById(R.id.text_new);
        final ImageButton cancelBtn = (ImageButton) dialogView.findViewById(R.id.cancel_text_btn);
        final ImageButton colorPickerBtn = (ImageButton) dialogView.findViewById(R.id.color_picker_btn);
        final SeekBar shadowBg = (SeekBar) dialogView.findViewById(R.id.shadow_bg);
        final Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        textView.setTypeface(type_1);
                        selectedType = type_1;
                        break;
                    case 1:
                        textView.setTypeface(type_2);
                        selectedType = type_2;
                        break;
                    case 2:
                        textView.setTypeface(type_3);
                        selectedType = type_3;
                        break;
                    case 3:
                        textView.setTypeface(type_4);
                        selectedType = type_4;
                        break;
                    case 4:
                        textView.setTypeface(type_5);
                        selectedType = type_5;
                        break;
                    case 5:
                        textView.setTypeface(type_6);
                        selectedType = type_6;
                        break;
                    case 6:
                        textView.setTypeface(type_7);
                        selectedType = type_7;
                        break;
                    case 7:
                        textView.setTypeface(type_8);
                        selectedType = type_8;
                        break;
                    case 8:
                        textView.setTypeface(type_9);
                        selectedType = type_9;
                        break;
                    case 9:
                        textView.setTypeface(type_10);
                        selectedType = type_10;
                        break;
                    case 10:
                        textView.setTypeface(type_11);
                        selectedType = type_11;
                        break;
                    case 11:
                        textView.setTypeface(type_12);
                        selectedType = type_12;
                        break;
                    case 12:
                        textView.setTypeface(type_13);
                        selectedType = type_13;
                        break;
                    case 13:
                        textView.setTypeface(type_14);
                        selectedType = type_14;
                        break;
//                    case 14:
//                        textView.setTypeface(type_15);
//                        selectedType = type_15;
//                        break;
//                    case 15:
//                        textView.setTypeface(type_16);
//                        selectedType = type_16;
//                        break;
//                    case 16:
//                        textView.setTypeface(type_17);
//                        selectedType = type_17;
//                        break;
//                    case 17:
//                        textView.setTypeface(type_18);
//                        selectedType = type_18;
//                        break;
//                    case 18:
//                        textView.setTypeface(type_19);
//                        selectedType = type_19;
//                        break;
//                    case 19:
//                        textView.setTypeface(type_20);
//                        selectedType = type_20;
//                        break;
//                    case 20:
//                        textView.setTypeface(type_21);
//                        selectedType = type_21;
//                        break;
//                    case 21:
//                        textView.setTypeface(type_22);
//                        selectedType = type_22;
//                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        shadowBg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                textView.setShadowLayer(1, progresValue / 5, progresValue / 5, Color.BLACK);
                shadowValue = progresValue / 5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
//        spinner.setTe
        enteredText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    cancelBtn.setVisibility(View.VISIBLE);
                } else {
                    cancelBtn.setVisibility(View.INVISIBLE);
                }
                textView.setText(enteredText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//

        currentColor = ContextCompat.getColor(this, R.color.colorAccent);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredText.setText("");
            }
        });
        colorPickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AmbilWarnaDialog dialog = new AmbilWarnaDialog(BackgroundnewActvity.this, currentColor, true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        currentColor = color;
                        textView.setTextColor(currentColor);
//                        colorLayout.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        Toast.makeText(getApplicationContext(), "Action canceled!", LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String text = enteredText.getText().toString();
                if (!text.isEmpty()) {
//                    addBubble(text, currentColor, selectedType);
                    addStickerView(text, currentColor, selectedType);
                } else {
                    enteredText.setError("This should not be empty");
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.create();
        builder.show();
    }

    private void addStickerView(String text, int currentColor, Typeface selectedType) {

        final StickerView stickerView = new StickerView(this);
        StringBuilder sb = new StringBuilder(text);
        int i = 0;
        while ((i = sb.indexOf(" ", i + 20)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        text = sb.toString();
        Bitmap bitmap = textAsBitmap(" " + text + " ", 34, currentColor, selectedType);
//        Bitmap bitmap = drawMultilineTextToBitmap(ImageEditActivity.this, R.mipmap.transparent, text, currentColor, selectedType);
        stickerView.setBitmap(bitmap);
//        stickerView.setImageResource(imageName);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                addStrickerview.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mViews.indexOf(stickerView);
                if (position == mViews.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mViews.remove(position);
                mViews.add(mViews.size(), stickerTemp);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        addStrickerview.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView);
    }

    private void setCurrentEdit(StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }

    public Bitmap textAsBitmap(String text, float textSize, int textColor, Typeface selectedType) {
        Paint paint = new TextPaint();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, dm));
        paint.setTypeface(selectedType);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        paint.setShadowLayer(1, shadowValue, shadowValue, Color.BLACK);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        //int height = (int) (baseline + paint.descent() + 140.0f);
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    public void reoveStickerBorders() {
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        if (mCurrentView != null)
            mCurrentView.setInEdit(false);


    }


    private void createFonts() {

        selectedType = Typeface.createFromAsset(getAssets(), "fonts/new_five.ttf");
        type_1 = Typeface.createFromAsset(getAssets(), "fonts/BrushScriptStd.otf");
        type_2 = Typeface.createFromAsset(getAssets(), "fonts/FancyPantsNF.otf");
        type_3 = Typeface.createFromAsset(getAssets(), "fonts/Fiddums Family.ttf");
        type_4 = Typeface.createFromAsset(getAssets(), "fonts/Fortunaschwein_complete.ttf");
        type_5 = Typeface.createFromAsset(getAssets(), "fonts/FUNDR__.TTF");
        type_6 = Typeface.createFromAsset(getAssets(), "fonts/HoboStd.otf");
        type_7 = Typeface.createFromAsset(getAssets(), "fonts/hotpizza.ttf");
        type_8 = Typeface.createFromAsset(getAssets(), "fonts/NuevaStd-Bold.otf");
        type_9 = Typeface.createFromAsset(getAssets(), "fonts/NuevaStd-BoldCond.otf");
        type_10 = Typeface.createFromAsset(getAssets(), "fonts/NuevaStd-BoldCondItalic.otf");
        type_11 = Typeface.createFromAsset(getAssets(), "fonts/NuevaStd-Cond.otf");
        type_12 = Typeface.createFromAsset(getAssets(), "fonts/NuevaStd-CondItalic.otf");
        type_13 = Typeface.createFromAsset(getAssets(), "fonts/NuevaStd-Italic.otf");

    }

    private void setupTabIcons() {
       /* tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);*/
        if (catPos == 0) {

            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);

        } else if (catPos == 1) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 2) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 3) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 4) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 5) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 6) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 7) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 8) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 9) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 10) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        } else if (catPos == 11) {
            tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabOne.setText("Nature");
            tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(0).setCustomView(tabOne);

            tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwo.setText("Fantasy");
            tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(1).setCustomView(tabTwo);

            tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabThree.setText("Flower");
            tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(2).setCustomView(tabThree);

            tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFour.setText("Birthday");
            tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(3).setCustomView(tabFour);

            tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabFive.setText("Neon");
            tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(4).setCustomView(tabFive);

            tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSix.setText("Animal");
            tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(5).setCustomView(tabSix);

            tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabSeven.setText("Anniversary");
            tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(6).setCustomView(tabSeven);

            tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEight.setText("Bird");
            tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(7).setCustomView(tabEight);

            tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabNine.setText("City");
            tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(8).setCustomView(tabNine);

            tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTen.setText("Fire");
            tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(9).setCustomView(tabTen);

            tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabEleven.setText("Love");
            tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
            tabLayoutCat.getTabAt(10).setCustomView(tabEleven);

            tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_new, null);
            tabTwelve.setText("Sunset");
            tabTwelve.setBackgroundResource(R.drawable.ic_selected_tab_back);
            tabLayoutCat.getTabAt(11).setCustomView(tabTwelve);
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterCat adapter = new ViewPagerAdapterCat(getSupportFragmentManager());
        adapter.addFragment(new NatureFragmentNew(), "Nature");
        adapter.addFragment(new FantasyFragmentNew(), "Fantasy");
        adapter.addFragment(new FlowerFragmentNew(), "Flower");
        adapter.addFragment(new BirthdayFragmentNew(), "Birthday");
        adapter.addFragment(new NeoneFragmentNew(), "Neon");
        adapter.addFragment(new AnimalFragmentNew(), "Animal");
        adapter.addFragment(new AnniversaryFragmentNew(), "Anniversary");
        adapter.addFragment(new BirdFragmentNew(), "Bird");
        adapter.addFragment(new CityFragmentNew(), "City");
        adapter.addFragment(new FireFragmentNew(), "Fire");
        adapter.addFragment(new LoveFragmentNew(), "Love");
        adapter.addFragment(new SunsetFragmentNew(), "Sunset");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onNatItemClickednew(String back, String forei, int position) {

       /* if (position<2){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            imagBack.setImageResource(getImage(back));
            imgFore.setImageResource(getImage(forei));

        }
        else {
             String backImag=getFileName(back);
        String foreImg=getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }
        }*/
        if (position != 0) {
            // to change the posiyon and key value after refresh of activity

            int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int oldrewardadkey = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newrewardkeycount = 0;
            if (NatureFragmentNew.keycountnewnature != 0) {
                newrewardkeycount = NatureFragmentNew.keycountnewnature;
                System.out.println(newrewardkeycount);
            }
            if (newrewardkeycount > 0) {
                newrewardkeycount = oldrewardadkey + newrewardkeycount;
            } else {
                newrewardkeycount = oldrewardadkey;
            }
            if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                    if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                    if (position >= newrewardkeycount) {

                        String backImag = getFileName(back);
                        String foreImg = getFileName(forei);

                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                        }
                        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                                : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                        File mediaStorageDir = new File(paths + "/" + backImag);
                        File mediaStorageDirfore = new File(paths + "/" + foreImg);

                        backPath = mediaStorageDir.getAbsolutePath();
                        forePath = mediaStorageDirfore.getAbsolutePath();

                        String urlsPath[] = {back, forei};


                        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                            alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                            alertDialog.setCancelable(false);
                            alertDialog.setIcon(R.drawable.icon);
                            alertDialog.show();

                            AdRequest adRequest = new AdRequest.Builder().build();

                            RewardedAd.load(this, getString(R.string.admob_reward_id),
                                    adRequest, new RewardedAdLoadCallback() {
                                        @Override
                                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                            // Handle the error.
                                            Log.d(TAG, loadAdError.toString());
                                            mRewardedAd = null;
                                            if (alertDialog != null) {
                                                if (alertDialog.isShowing() && !isFinishing()) {
                                                    alertDialog.dismiss();
                                                }
                                            }
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }

                                        @Override
                                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                            if (alertDialog != null) {
                                                if (alertDialog.isShowing() && !isFinishing()) {
                                                    alertDialog.dismiss();
                                                }
                                            }
                                            mRewardedAd = rewardedAd;
                                            Log.d(TAG, "Ad was loaded.");
                                            if (mRewardedAd != null) {

                                                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                    @Override
                                                    public void onAdClicked() {
                                                        // Called when a click is recorded for an ad.
                                                        Log.d(TAG, "Ad was clicked.");
                                                    }

                                                    @Override
                                                    public void onAdDismissedFullScreenContent() {
                                                        // Called when ad is dismissed.
                                                        // Set the ad reference to null so you don't show the ad a second time.
                                                        Log.d(TAG, "Ad dismissed fullscreen content.");
                                                        if (rewardVideoComplete && canRewarded) {
                                                        } else {
                                                            rewardVideoComplete = false;
                                                            canRewarded = false;
                                                        }
                                                        mRewardedAd = null;
                                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                    }

                                                    @Override
                                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                        // Called when ad fails to show.
                                                        Log.e(TAG, "Ad failed to show fullscreen content.");
                                                        mRewardedAd = null;
                                                    }

                                                    @Override
                                                    public void onAdImpression() {
                                                        // Called when an impression is recorded for an ad.
                                                        Log.d(TAG, "Ad recorded an impression.");
                                                    }

                                                    @Override
                                                    public void onAdShowedFullScreenContent() {
                                                        // Called when ad is shown.
                                                        Log.d(TAG, "Ad showed fullscreen content.");
                                                    }
                                                });

                                                if (mRewardedAd != null) {
                                                    Activity activityContext = BackgroundnewActvity.this;
                                                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                        @Override
                                                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                            // Handle the reward.
                                                            Log.d(TAG, "The user earned the reward.");
                                                            canRewarded = true;
                                                            rewardVideoComplete = true;
                                                        }
                                                    });
                                                } else {
                                                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                            } else {
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }


                                        }
                                    });

                            // new DownloadFileAsync(urlsPath).execute(urlsPath);

                        } else {

                            imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                            imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                        }

                    } else {

                        String backImag = getFileName(back);
                        String foreImg = getFileName(forei);

                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                        }
                        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                                : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                        File mediaStorageDir = new File(paths + "/" + backImag);
                        File mediaStorageDirfore = new File(paths + "/" + foreImg);

                        backPath = mediaStorageDir.getAbsolutePath();
                        forePath = mediaStorageDirfore.getAbsolutePath();

                        String urlsPath[] = {back, forei};


                        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                            new DownloadFileAsync(urlsPath).execute(urlsPath);

                        } else {

                            imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                            imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                        }


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }
            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }


        } else {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back + " and " + forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            imagBack.setImageResource(getImage(back));
            imgFore.setImageResource(getImage(forei));
        }


    }

    @Override
    public void onFantasyItemClickednew(String back, String forei, int position) {

       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }


            imagBack.setImageResource(getImage(back));
            imgFore.setImageResource(getImage(forei));

        }
        else {

            String backImag=getFileName(back);
            String foreImg=getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


            File  mediaStorageDir = new File(paths + "/"+backImag);
            File  mediaStorageDirfore = new File(paths + "/"+foreImg);

            backPath=mediaStorageDir.getAbsolutePath();
            forePath=mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
                imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




            }


        }*/
       /* String backImag=getFileName(back);
        String foreImg=getFileName(forei);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        int oldfantacykeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newfantacykeycount = 0;
        if (FantasyFragmentNew.keycountnewfant != 0) {
            newfantacykeycount = FantasyFragmentNew.keycountnewfant;
            System.out.println(newfantacykeycount);
        }
        if (newfantacykeycount > 0) {
            newfantacykeycount = oldfantacykeycount + newfantacykeycount;
            System.out.println(newfantacykeycount);


        } else {
            newfantacykeycount = oldfantacykeycount;
            System.out.println(newfantacykeycount);

        }
        System.out.println(newfantacykeycount);

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newfantacykeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onFlowerItemClickednew(String back, String forei, int position) {

       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            imagBack.setImageResource(getImage(back));
            imgFore.setImageResource(getImage(forei));


        }
        else {

            String backImag=getFileName(back);
            String foreImg=getFileName(forei);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


            File  mediaStorageDir = new File(paths + "/"+backImag);
            File  mediaStorageDirfore = new File(paths + "/"+foreImg);

            backPath=mediaStorageDir.getAbsolutePath();
            forePath=mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
                imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




            }


        }*/
       /* String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        //to set the count key position after checking the downloaded frames
        int oldflowerkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newflowercount = 0;
        if (FlowerFragmentNew.keycountnewflower != 0) {
            newflowercount = FlowerFragmentNew.keycountnewflower;
            System.out.println(newflowercount);
        }
        if (newflowercount > 0) {
            newflowercount = oldflowerkeycount + newflowercount;
            System.out.println(newflowercount);


        } else {
            newflowercount = oldflowerkeycount;
            System.out.println(newflowercount);

        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newflowercount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onBirthdayItemClickednew(String back, String forei, int position) {

       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            imagBack.setImageResource(getImage(back));
            imgFore.setImageResource(getImage(forei));

        }
        else {

            String backImag=getFileName(back);
            String foreImg=getFileName(forei);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


            File  mediaStorageDir = new File(paths + "/"+backImag);
            File  mediaStorageDirfore = new File(paths + "/"+foreImg);

            backPath=mediaStorageDir.getAbsolutePath();
            forePath=mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
                imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




            }

        }*/
       /* String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        //to set the count key position after checking the downloaded frames
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if (BirthdayFragmentNew.keycountbithnew!=0){
            newkeycount = BirthdayFragmentNew.keycountbithnew;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onNeonItemClickednew(String back, String forei, int position) {

       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            imagBack.setImageResource(getImage(back));
            imgFore.setImageResource(getImage(forei));

        }
        else {

            String backImag=getFileName(back);
            String foreImg=getFileName(forei);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


            File  mediaStorageDir = new File(paths + "/"+backImag);
            File  mediaStorageDirfore = new File(paths + "/"+foreImg);

            backPath=mediaStorageDir.getAbsolutePath();
            forePath=mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
                imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




            }


        }*/
       /* String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        //to set the count key position after checking the downloaded frames
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if (NeoneFragmentNew.keycountnewneon!=0){
            newkeycount = NeoneFragmentNew.keycountnewneon;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onAnimalItemClickednew(String back, String forei, int position) {

       /* String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/

        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount=0;
        if (NeoneFragmentNew.keycountnewneon!=0){
            newkeycount = AnimalFragmentNew.keycountanimalnew;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        // h
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onAnniversaryItemClickednew(String back, String forei, int position) {

       /*  String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if (AnniversaryFragmentNew.keycountnewanniversary!=0){
            newkeycount = AnniversaryFragmentNew.keycountnewanniversary;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onBirdItemClickednew(String back, String forei, int position) {

       /* String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if (BirdFragmentNew.keycountbirdnew!=0){
            newkeycount = BirdFragmentNew.keycountbirdnew;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onCityItemClickednew(String back, String forei, int position) {

      /*  String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if (CityFragmentNew.keycountcitynew!=0){
            newkeycount = CityFragmentNew.keycountcitynew;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onFireItemClickednew(String back, String forei, int position) {

       /*  String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if(FireFragmentNew.keycountfirenew!=0){
            newkeycount = FireFragmentNew.keycountfirenew;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onLoveItemClickednew(String back, String forei, int position) {

        /* String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if(LoveFragmentNew.keycountnewlove!=0){
            newkeycount = LoveFragmentNew.keycountnewlove;
            System.out.println(newkeycount);
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    @Override
    public void onSunsetItemClickednew(String back, String forei, int position) {

       /*  String backImag=getFileName(back);
        String foreImg=getFileName(forei);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag+" and "+foreImg);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
        }
        String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Images";


        File  mediaStorageDir = new File(paths + "/"+backImag);
        File  mediaStorageDirfore = new File(paths + "/"+foreImg);

        backPath=mediaStorageDir.getAbsolutePath();
        forePath=mediaStorageDirfore.getAbsolutePath();

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            imagBack.setImageURI(Uri.fromFile(new File(""+backPath)));
            imgFore.setImageURI(Uri.fromFile(new File(""+forePath)));




        }*/
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount =0;
        if (SunsetFragmentNew.keycountnewsunset!=0){
            newkeycount = SunsetFragmentNew.keycountnewsunset;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (position >= newkeycount) {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        alertDialog.setMessage(getString(R.string.loading_ads_please_wait));
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(this, getString(R.string.admob_reward_id),
                                adRequest, new RewardedAdLoadCallback() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error.
                                        Log.d(TAG, loadAdError.toString());
                                        mRewardedAd = null;
                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        new DownloadFileAsync(urlsPath).execute(urlsPath);
                                    }

                                    @Override
                                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                                        if (alertDialog != null) {
                                            if (alertDialog.isShowing() && !isFinishing()) {
                                                alertDialog.dismiss();
                                            }
                                        }
                                        mRewardedAd = rewardedAd;
                                        Log.d(TAG, "Ad was loaded.");
                                        if (mRewardedAd != null) {

                                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdClicked() {
                                                    // Called when a click is recorded for an ad.
                                                    Log.d(TAG, "Ad was clicked.");
                                                }

                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    // Called when ad is dismissed.
                                                    // Set the ad reference to null so you don't show the ad a second time.
                                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                                    if (rewardVideoComplete && canRewarded) {
                                                    } else {
                                                        rewardVideoComplete = false;
                                                        canRewarded = false;
                                                    }
                                                    mRewardedAd = null;
                                                    new DownloadFileAsync(urlsPath).execute(urlsPath);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    // Called when ad fails to show.
                                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                                    mRewardedAd = null;
                                                }

                                                @Override
                                                public void onAdImpression() {
                                                    // Called when an impression is recorded for an ad.
                                                    Log.d(TAG, "Ad recorded an impression.");
                                                }

                                                @Override
                                                public void onAdShowedFullScreenContent() {
                                                    // Called when ad is shown.
                                                    Log.d(TAG, "Ad showed fullscreen content.");
                                                }
                                            });

                                            if (mRewardedAd != null) {
                                                Activity activityContext = BackgroundnewActvity.this;
                                                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        // Handle the reward.
                                                        Log.d(TAG, "The user earned the reward.");
                                                        canRewarded = true;
                                                        rewardVideoComplete = true;
                                                    }
                                                });
                                            } else {
                                                Log.d(TAG, "The rewarded ad wasn't ready yet.");
                                                new DownloadFileAsync(urlsPath).execute(urlsPath);
                                            }

                                        } else {
                                            new DownloadFileAsync(urlsPath).execute(urlsPath);
                                        }


                                    }
                                });

                        // new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }

                } else {

                    String backImag = getFileName(back);
                    String foreImg = getFileName(forei);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                    if (mFirebaseAnalytics != null) {
                        mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                    }
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                    File mediaStorageDir = new File(paths + "/" + backImag);
                    File mediaStorageDirfore = new File(paths + "/" + foreImg);

                    backPath = mediaStorageDir.getAbsolutePath();
                    forePath = mediaStorageDirfore.getAbsolutePath();

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                        imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                        imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                    }


                }

            } else {

                String backImag = getFileName(back);
                String foreImg = getFileName(forei);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
                if (mFirebaseAnalytics != null) {
                    mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
                }
                String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


                File mediaStorageDir = new File(paths + "/" + backImag);
                File mediaStorageDirfore = new File(paths + "/" + foreImg);

                backPath = mediaStorageDir.getAbsolutePath();
                forePath = mediaStorageDirfore.getAbsolutePath();

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                    imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                    imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


                }


            }
        } else {

            String backImag = getFileName(back);
            String foreImg = getFileName(forei);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, backImag + " and " + foreImg);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }
            String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                    getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";


            File mediaStorageDir = new File(paths + "/" + backImag);
            File mediaStorageDirfore = new File(paths + "/" + foreImg);

            backPath = mediaStorageDir.getAbsolutePath();
            forePath = mediaStorageDirfore.getAbsolutePath();

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
                imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));


            }


        }


    }

    class ViewPagerAdapterCat extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapterCat(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    private void saveImage() {
       /* if (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestPermission("android.permission.WRITE_EXTERNAL_STORAGE", getString(R.string.permission_write_storage_rationale), 102);
        } else {
            new saveAndGoimag().execute(new Void[0]);
        }*/
        new saveAndGoimag().execute(new Void[0]);
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 102) {
            super.onRequestPermissionsResult(i, strArr, iArr);
        } else if (iArr[0] == 0) {
            saveImage();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class saveAndGoimag extends AsyncTask<Void, Void, String> {
        @Override
        public String doInBackground(Void... voidArr) {

            BackgroundnewActvity backgroundnewActvity = BackgroundnewActvity.this;
            backgroundnewActvity.savePath = backgroundnewActvity.savePhoto();
            return "";
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);

        }

        @SuppressLint("WrongConstant")
        @Override
        public void onPostExecute(String str) {

            // BackgroundnewActvity backgroundnewActvity = BackgroundnewActvity.this;
            //  backgroundnewActvity.savePath = backgroundnewActvity.savePhoto();

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (BackgroundnewActvity.this.savePath.equals("")) {
                Toast.makeText(BackgroundnewActvity.this, "Couldn't save photo, error", 0).show();
            } else {
                openNext();
            }
        }
    }

    public void openNext() {
        Intent intent = new Intent().setClass(this, SaveImageActivity.class);
        intent.setData(Uri.parse(this.savePath));
        startActivity(intent);
        //finish();
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

    private String pathtoSave(String str) {
        String SAVE_PATH = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                : Environment.getExternalStorageDirectory().toString();
        return new File(SAVE_PATH + "/" + getString(R.string.app_name) + "/SpiralPhoto").getPath();
    }

    private void addImageGallery(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("mime_type", "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // CommonMethods.getInstance().activitiesAD(BackgroundnewActvity.this);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        String[] paths;
        String fpath;

        public DownloadFileAsync(String[] paths) {
            super();
            this.paths = paths;
            for (int i = 0; i < paths.length; i++)
                System.out.println((i + 1) + ":  " + paths[i]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Downloading...");
            pDialog.show();
            pDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... aurl) {
            try {
                for (int i = 0; i < aurl.length; i++) {


                    String imgPath = aurl[i];
                    fpath = getFileName(imgPath);
                    URL url = new URL(imgPath);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    String paths = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                            getApplicationContext().getExternalFilesDir(getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Images";
                   /* String paths = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + getResources().getString(R.string.app_name)+ "/"
                            :Environment.getExternalStorageDirectory().toString()+ "/" +getResources().getString(R.string.app_name) + "/" ;*/
                    //File path = new File(Environment.getExternalStorageDirectory() + "/" + mContext.getResources().getString(R.string.app_name) + "/" + typeOfD + "/");
                    File path = new File(paths);
                    File file = new File(path, fpath);
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    int contentLength = connection.getContentLength();

                    DataInputStream stream = new DataInputStream(url.openStream());
                    byte[] buffer = new byte[contentLength];
                    stream.readFully(buffer);
                    stream.close();
                    DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
                    fos.write(buffer);
                    fos.flush();
                    fos.close();
                }
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String unused) {
            if (pDialog != null) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }
            imagBack.setImageURI(Uri.fromFile(new File("" + backPath)));
            imgFore.setImageURI(Uri.fromFile(new File("" + forePath)));

        }
    }

    public String getFileName(String wholePath) {
        String name;
        int start, end;
        start = wholePath.lastIndexOf('/');
        end = wholePath.length();
        name = wholePath.substring((start + 1), end);
        System.out.println("Start:" + start + "\t\tEnd:" + end + "\t\tName:" + name);
        return name;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // String result = data.getStringExtra("SELECTED_IMAGE");
                Bitmap selectedImageBitmap = Datastore.getOurRemote().getStickerBimap();
                imgCut.setImageBitmap(selectedImageBitmap);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}