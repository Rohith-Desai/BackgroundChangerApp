package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.AnimalFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.AnniversaryFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.BirdFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.BirthdayFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.CityFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FantasyFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FireFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.FlowerFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.LoveFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.NatureFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.NeoneFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.SunsetFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.receivers.AlarmEvngReceiver;
import com.hs.photo.background.changer.editor.backgrounderaser.receivers.AlarmReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.commonMethods;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;

public class HomeActivity extends AppCompatActivity implements NatureFragment.FragmentListener, FantasyFragment.FantasyFragmentListener,
        FlowerFragment.FlowerFragmentListener, BirthdayFragment.BirthdayFragmentListener, NeoneFragment.NeonFragmentListener, AnimalFragment.AnimalFragmentListener,
        AnniversaryFragment.AnniversaryFragmentListener, BirdFragment.BirdFragmentListener, CityFragment.CityFragmentListener, FireFragment.FireFragmentListener, LoveFragment.LoveFragmentListener, SunsetFragment.SunsetFragmentListener {

    private String[] appPermissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};

    ViewPager viewPager;
    TabLayout tabLayout;
    private static final int PICK_IMAGE_REQUEST = 189;
    // public int itemPosition;
    public int categoryPosition;
    LinearLayout myCreation;
    boolean showAd = true;

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
    ProgressDialog pDialog;
    String backPath;
    String forePath;
    int currentItempos;
    // TextView exitNo;
    // TextView exitYes;

    // private NativeAd nativeAd;


    //public static BottomSheetBehavior sheetBehavior;
    //private ConstraintLayout bottomsheetview;
    private FrameLayout adContainerView;

   /* private AppUpdateManager appUpdateManager;
    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124;*/
   /*private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;*/

    private AdLoader adLoader;
    boolean adLoaded;
    boolean natureLoad;
    boolean fantasyLoad;
    boolean flowerLoad;
    boolean birthdayLoad;
    boolean neonLoad;
    boolean animalLoad;
    boolean anniversaryLoad;
    boolean birdLoad;
    boolean cityLoad;
    boolean fireLoad;
    boolean loveLoad;
    boolean sunsetLoad;

    public static final int NUMBER_OF_AD_MOB_ADS = 4;
    private List<NativeAd> mNativeAds = new ArrayList<>();

    private RewardedAd mRewardedAd;
    private final String TAG = "HomeActivity";

    androidx.appcompat.app.AlertDialog alertDialog;
    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;

    private static boolean canRewarded = false;
    private static boolean rewardVideoComplete = false;

    String fromActvity;
    private String stingCut;

    Dialog dailogtoast;
    Dialog toastdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       /* appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
       // checkUpdate();
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                removeInstallStateUpdateListener();
            } else {
                Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
            }
        };
        appUpdateManager.registerListener(installStateUpdatedListener);
        checkUpdate();*/

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "HomeActivity");
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("ACTIVITY_SELECTION", bundle);
        }

        if (getIntent().hasExtra("fromActivity")) {
            fromActvity = getIntent().getStringExtra("fromActivity");

        }
        if (getIntent().hasExtra("SELECTED_IMAGE")) {

            this.stingCut = getIntent().getStringExtra("SELECTED_IMAGE");

        }
//        if (isFirstTimeIN()) {
//            dailogtoast = new Dialog(this);
//            dailogtoast.setCancelable(false);
//            dailogtoast.setContentView(R.layout.toast_dialog_frames_edit);
//            Button btn_ok = (Button) dailogtoast.findViewById(R.id.btn_ok);
//            dailogtoast.show();
//            WindowManager.LayoutParams lp = dailogtoast.getWindow().getAttributes();
//            lp.dimAmount = 0.3f;
//            dailogtoast.getWindow().setAttributes(lp);
//            dailogtoast.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//            btn_ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dailogtoast.dismiss();
//                }
//            });
//        }
        /*ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.bottom_sheet_exit);
        this.bottomsheetview = constraintLayout;
        BottomSheetBehavior from = BottomSheetBehavior.from(constraintLayout);
        sheetBehavior = from;
        from.setState(BottomSheetBehavior.STATE_HIDDEN);
        exitNo=findViewById(R.id.exit_no);
        exitYes=findViewById(R.id.exit_yes);
        exitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });
       exitYes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });*/
        adContainerView = findViewById(R.id.home_banner_ad);
        if (RemoteConfigValues.getOurRemote().getShowBannerAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")) {

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAdMain();
                    }
                });

            }
        }
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        alertDialog = alertDialogBuilder.create();
        /*adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adContainerView, HomeActivity.this);
            }
        });*/
       /* if (RemoteConfigValues.getOurRemote().getShowNativeAd()!= null) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                admobAd();
            }

        }*/
        // admobAd();


        this.pDialog = new ProgressDialog(this);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager_items);
        this.tabLayout = (TabLayout) findViewById(R.id.tab_category);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {

                    case 11:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle12 = new Bundle();
                        bundle12.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Sunset");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle12);
                        }

                        break;

                    case 10:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle11 = new Bundle();
                        bundle11.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Love");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle11);
                        }

                        break;


                    case 9:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle10 = new Bundle();
                        bundle10.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Fire");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle10);
                        }

                        break;

                    case 8:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle9 = new Bundle();
                        bundle9.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "City");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle9);
                        }

                        break;

                    case 7:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);

                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle8 = new Bundle();
                        bundle8.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Bird");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle8);
                        }

                        break;

                    case 6:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle7 = new Bundle();
                        bundle7.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Anniversary");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle7);
                        }

                        break;
                    case 5:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle6 = new Bundle();
                        bundle6.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Animal");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle6);
                        }

                        break;
                    case 4:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle5 = new Bundle();
                        bundle5.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Neon");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle5);
                        }
                        break;
                    case 3:
                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle4 = new Bundle();
                        bundle4.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Birthday");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle4);
                        }
                        break;

                    case 2:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Flower");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle3);
                        }
                        break;


                    case 1:

                        tabOne.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Fantasy");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle2);
                        }
                        break;

                    case 0:
                        tabOne.setBackgroundResource(R.drawable.ic_selected_tab_back);
                        tabLayout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(1).setCustomView(tabTwo);
                        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(2).setCustomView(tabThree);
                        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(3).setCustomView(tabFour);
                        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(4).setCustomView(tabFive);
                        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(5).setCustomView(tabSix);
                        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(6).setCustomView(tabSeven);
                        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(7).setCustomView(tabEight);
                        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(8).setCustomView(tabNine);
                        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(9).setCustomView(tabTen);
                        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(10).setCustomView(tabEleven);
                        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
                        tabLayout.getTabAt(11).setCustomView(tabTwelve);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Nature");
                        if (mFirebaseAnalytics != null) {
                            mFirebaseAnalytics.logEvent("CATEGORY_SELECTION_MAINSCREEN", bundle1);
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
        if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                admobAdFragments();
            }

        }
        //  admobAdFragments();

        myCreation = findViewById(R.id.linear_mycreation);
        myCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opneNext();
            }
        });
        if (checkAndRequestPermission()) {

        }


       /* if (getIntent().hasExtra("showAd")) {
            showAd = getIntent().getBooleanExtra("showAd", false);
        }
        if (showAd){
            CommonMethods.getInstance().onLaunchAD(HomeActivity.this);
        }*/

        SharedPreferences prefs = getSharedPreferences("Notifications_Cal_Background", MODE_PRIVATE);
        boolean startServ = prefs.getBoolean("STARTSERVICE", true);
        if (startServ) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("current time:  " + c.getTime());
            long old = c.getTimeInMillis();
            c.add(Calendar.DATE, 1);  // number of days to add
            c.set(Calendar.HOUR_OF_DAY, 10);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            long newT = c.getTimeInMillis();
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent receiverIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, receiverIntent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            if (alarmMgr != null) {
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);
            }

            //Evening Alarm

            AlarmManager alarmMgrEvng = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent receiverIntentEvng = new Intent(getApplicationContext(), AlarmEvngReceiver.class);
            PendingIntent alarmIntentEvng = PendingIntent.getBroadcast(getApplicationContext(), 0, receiverIntentEvng, 0);

            Calendar calendarEvng = Calendar.getInstance();
            calendarEvng.setTimeInMillis(System.currentTimeMillis());
            calendarEvng.set(Calendar.HOUR_OF_DAY, 20);
            if (alarmMgrEvng != null) {
                alarmMgrEvng.setRepeating(AlarmManager.RTC_WAKEUP, calendarEvng.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntentEvng);
            }

            SharedPreferences.Editor editor = getSharedPreferences("Notifications_Cal_Background", MODE_PRIVATE).edit();
            editor.putBoolean("STARTSERVICE", false);
            editor.apply();


        }


        // MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("D386081C603C356C4F2FF4E48ACDA0CA")).build());
    }

//    private boolean isFirstTimeIN() {
//        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//        boolean ranBeforein = preferences.getBoolean("RanBefore", false);
//        if (!ranBeforein) {
//            // first time
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("RanBefore", true);
//            editor.commit();
//        }
//        return !ranBeforein;
//    }

    public void loadBannerAdMain() {
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

    public List<NativeAd> getRecyclerViewItems() {
        return mNativeAds;
    }

    public boolean getIsadloaded() {
        if (adLoaded) {
            natureLoad = true;
        }
        return adLoaded;
    }

    public boolean getfantasyadloaded() {
        if (adLoaded) {
            fantasyLoad = true;
        }
        return adLoaded;
    }

    public boolean getfloweradloaded() {

        if (adLoaded) {
            flowerLoad = true;
        }

        return adLoaded;
    }

    public boolean getbdayadloaded() {

        if (adLoaded) {
            birthdayLoad = true;
        }
        return adLoaded;
    }

    public boolean getneonadloaded() {

        if (adLoaded) {
            neonLoad = true;
        }
        return adLoaded;
    }

    public boolean getanimaladloaded() {
        if (adLoaded) {
            animalLoad = true;
        }
        return adLoaded;
    }

    public boolean getanniversryadloaded() {

        if (adLoaded) {
            anniversaryLoad = true;
        }
        return adLoaded;
    }

    public boolean getbirdadloaded() {

        if (adLoaded) {
            birdLoad = true;
        }
        return adLoaded;
    }

    public boolean getcityadloaded() {

        if (adLoaded) {
            cityLoad = true;
        }
        return adLoaded;
    }

    public boolean getfireadloaded() {

        if (adLoaded) {
            fireLoad = true;
        }
        return adLoaded;
    }

    public boolean getlovedloaded() {

        if (adLoaded) {
            loveLoad = true;
        }
        return adLoaded;
    }

    public boolean getsunsetdloaded() {
        if (adLoaded) {
            sunsetLoad = true;
        }
        return adLoaded;
    }

    private void admobAdFragments() {

        mNativeAds.clear();
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.native_ad_on_mainhome));
        adLoader = builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {

                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                        mNativeAds.add(nativeAd);
                        if (!adLoader.isLoading()) {
                            adLoaded = true;
                            System.out.println("XXX - c");
                            // prepareNatureFrameList();
                            System.out.println("XXX" + "loaded");
                        }

                    }
                }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                        + " load another.");
                if (!adLoader.isLoading()) {
                    adLoaded = false;
                    System.out.println("XXX - d");
                    // prepareNatureFrameList();

                }
                System.out.println("XXX" + "loaded");

            }
        }).build();


        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_AD_MOB_ADS);

    }
   /* private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdateFlow(appUpdateInfo);
            } else if  (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                startUpdateFlow(appUpdateInfo);
            }
        });
    }*/
   /* private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, HomeActivity.IMMEDIATE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }*/

    /* private void checkUpdate() {

         Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

         appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
             if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                     && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                 startUpdateFlow(appUpdateInfo);
             } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                 popupSnackBarForCompleteUpdate();
             }
         });
     }
     private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
         try {
             appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, HomeActivity.FLEXIBLE_APP_UPDATE_REQ_CODE);
         } catch (IntentSender.SendIntentException e) {
             e.printStackTrace();
         }
     }*/
    private void setupTabIcons() {
       /* tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);*/

        tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Nature");
        tabOne.setBackgroundResource(R.drawable.ic_selected_tab_back);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Fantasy");
        tabTwo.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Flower");
        tabThree.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Birthday");
        tabFour.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText("Neon");
        tabFive.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(4).setCustomView(tabFive);


        tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSix.setText("Animal");
        tabSix.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(5).setCustomView(tabSix);

        tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSeven.setText("Anniversary");
        tabSeven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(6).setCustomView(tabSeven);

        tabEight = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabEight.setText("Bird");
        tabEight.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(7).setCustomView(tabEight);

        tabNine = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabNine.setText("City");
        tabNine.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(8).setCustomView(tabNine);

        tabTen = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTen.setText("Fire");
        tabTen.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(9).setCustomView(tabTen);

        tabEleven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabEleven.setText("Love");
        tabEleven.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(10).setCustomView(tabEleven);

        tabTwelve = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwelve.setText("Sunset");
        tabTwelve.setBackgroundResource(R.drawable.ic_unselected_tab_back);
        tabLayout.getTabAt(11).setCustomView(tabTwelve);

    }

    private void opneNext() {
        if (checkAndRequestPermission()) {
            startActivity(new Intent(this, MyCreationActivity.class));

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NatureFragment(), "Nature");
        adapter.addFragment(new FantasyFragment(), "Fantasy");
        adapter.addFragment(new FlowerFragment(), "Flower");
        adapter.addFragment(new BirthdayFragment(), "Birthday");
        adapter.addFragment(new NeoneFragment(), "Neon");
        adapter.addFragment(new AnimalFragment(), "Animal");
        adapter.addFragment(new AnniversaryFragment(), "Anniversary");
        adapter.addFragment(new BirdFragment(), "Bird");
        adapter.addFragment(new CityFragment(), "City");
        adapter.addFragment(new FireFragment(), "Fire");
        adapter.addFragment(new LoveFragment(), "Love");
        adapter.addFragment(new SunsetFragment(), "Sunset");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onRecyclerViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 0;
        // this.itemPosition=1;
        this.currentItempos = position;
        if (position != 0) {

            int rewardPosition = position;
            if (adLoaded && natureLoad && position > 6) {
                rewardPosition = position - 1;
            } else {
                rewardPosition = position;
            }
// to change by key count based on download frames
            int newkeycount = 0;
            int oldrewardadkey = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newrewardkeycount;
            if (NatureFragment.keycountnature != 0) {
                newkeycount = NatureFragment.keycountnature;
                System.out.println(newkeycount);
            }
            if (newkeycount > 0) {
                newrewardkeycount = oldrewardadkey + newkeycount;
                System.out.println(newrewardkeycount);
            } else {
                newrewardkeycount = oldrewardadkey;
                System.out.println(newrewardkeycount);
            }
            System.out.println(oldrewardadkey);
            if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                    if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                    if (rewardPosition >= newrewardkeycount) {

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

                        Log.d("checkFilepathh", backPath);

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
                                                    Activity activityContext = HomeActivity.this;
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

                            //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                        } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                            if (fromActvity != null) {
                                if (fromActvity.equals("Erasebg")) {

                                    Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                    intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                    intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                    intent.putExtra("backPath", HomeActivity.this.backPath);
                                    intent.putExtra("forePath", HomeActivity.this.forePath);
                                    intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                    startActivity(intent);

                                } else {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction("android.intent.action.PICK");
                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                }
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

                        Log.d("checkFilepathh", backPath);

                        String urlsPath[] = {back, forei};


                        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                            new DownloadFileAsync(urlsPath).execute(urlsPath);

                        } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                            if (fromActvity != null) {
                                if (fromActvity.equals("Erasebg")) {

                                    Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                    intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                    intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                    intent.putExtra("backPath", HomeActivity.this.backPath);
                                    intent.putExtra("forePath", HomeActivity.this.forePath);
                                    intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                    startActivity(intent);

                                } else {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction("android.intent.action.PICK");
                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                }
                            }


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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


                }

            }


        } else {

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back + " and " + forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            backPath = back;
            forePath = forei;

           /* Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

            if (fromActvity != null) {
                if (fromActvity.equals("Erasebg")) {

                    Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                    intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                    intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                    intent.putExtra("backPath", HomeActivity.this.backPath);
                    intent.putExtra("forePath", HomeActivity.this.forePath);
                    intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                    startActivity(intent);

                } else {
                    if (CommonMethods.getInstance().isFirstTime(HomeActivity.this)) {
                        popupToast();
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }
            }

        }




       /* if (!back.contains("https")){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            backPath=back;
            forePath=forei;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);



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

            Log.d("checkFilepathh",backPath);

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        }*/







       /* if (checkAndRequestPermission()){

            this.categoryPosition=0;
            this.itemPosition=1;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/


    }

    @Override
    public void onFantasyViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 1;
        // this.itemPosition=1;

        this.currentItempos = position;

        int rewardPosition = position;
        if (adLoaded && fantasyLoad && position > 2) {
            Log.d("checkkkkkkkk", "surya");
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }

        Log.d("whatisposition", String.valueOf(rewardPosition));

        // to check the position and key value , and change the frmaes after downloaded
        int oldfantacykeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newfantacykeycount = 0;
        if (FantasyFragment.fantacykeycount != 0) {
            newfantacykeycount = FantasyFragment.fantacykeycount;
        }
        if (newfantacykeycount > 0) {
            newfantacykeycount = oldfantacykeycount + newfantacykeycount;
            System.out.println(newfantacykeycount);
        } else {
            newfantacykeycount = oldfantacykeycount;
            System.out.println(newfantacykeycount);
        }


        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition>=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())){
                if (rewardPosition >= newfantacykeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/

       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            backPath=back;
            forePath=forei;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);



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

            Log.d("checkFilepathh",backPath);

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        }*/



       /* if (checkAndRequestPermission()){

            this.categoryPosition=1;
            this.itemPosition=position;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/


    }

    @Override
    public void onFlowerViewItemClicked(String back, String forei, int position) {
        int newflowercount = 0;
        this.categoryPosition = 2;
        //  this.itemPosition=1;

        this.currentItempos = position;

        int rewardPosition = position;
        if (adLoaded && flowerLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }

        // to change the position of reward ad based on key value , so checking the key value after download of frames
        int oldflowerkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        if (FlowerFragment.flowerkeycount != 0) {
            newflowercount = FlowerFragment.flowerkeycount;
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

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newflowercount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }


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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/

       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            backPath=back;
            forePath=forei;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

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

            Log.d("checkFilepathh",backPath);

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        }*/




       /* if (checkAndRequestPermission()){

            this.categoryPosition=2;
            this.itemPosition=position;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        }*/


    }

    @Override
    public void onBirthdayViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 3;
        //  this.itemPosition=1;
        this.currentItempos = position;

        int rewardPosition = position;
        if (adLoaded && birthdayLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }
        // to change the keycount based on downloaded frames
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;

        if (BirthdayFragment.keycountbithday != 0) {
            newkeycount = BirthdayFragment.keycountbithday;
            System.out.println(newkeycount);
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;
            System.out.println(newkeycount);

        } else {
            newkeycount = oldkeycount;
            System.out.println(newkeycount);
        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/


       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            backPath=back;
            forePath=forei;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

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

            Log.d("checkFilepathh",backPath);

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        }*/




       /* if (checkAndRequestPermission()){

            this.categoryPosition=3;
            this.itemPosition=position;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        }*/


    }

    @Override
    public void onNeonViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 4;
        // this.itemPosition=1;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && neonLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }

        //to set the count key position after checking the downloaded frames
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (NeoneFragment.keycountneon != 0) {
            newkeycount = NeoneFragment.keycountneon;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/

       /* if (position<2){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, back+" and "+forei);
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("FRAMES_SELECTION", bundle);
            }

            backPath=back;
            forePath=forei;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

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

            Log.d("checkFilepathh",backPath);

            String urlsPath[] = {back, forei};



            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            }
            else {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        }*/



       /* if (checkAndRequestPermission()){

            this.categoryPosition=4;
            this.itemPosition=position;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        }*/


    }

    @Override
    public void onAnimalViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 5;
        // this.itemPosition=1;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && animalLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }

        // to  check and change positon of key value based on downloaded frames
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (AnimalFragment.keycountanimal != 0) {
            newkeycount = AnimalFragment.keycountanimal;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/


    }

    @Override
    public void onAnniversaryViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 6;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && anniversaryLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }
        //check the key value and positon based on downloaded frames
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (AnniversaryFragment.keycountanniversary != 0) {
            newkeycount = AnniversaryFragment.keycountanniversary;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }
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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/


    }

    @Override
    public void onBirdViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 7;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && birdLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }
        //check the postion and key value based on downloads
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (BirdFragment.keycountbird != 0) {
            newkeycount = BirdFragment.keycountbird;
            System.out.println(newkeycount);
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;
            System.out.println(newkeycount);


        } else {
            newkeycount = oldkeycount;
            System.out.println(newkeycount);

        }
        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/

    }

    @Override
    public void onCityViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 8;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && cityLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }

        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (CityFragment.keycountcity != 0) {
            newkeycount = CityFragment.keycountcity;

        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }
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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/


    }

    @Override
    public void onFireViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 9;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && fireLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }
        // setting mew position for reward ad frames after checking the downloaded frames

        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (FireFragment.keycountfire != 0) {
            newkeycount = FireFragment.keycountfire;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/

    }

    @Override
    public void onLoveViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 10;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && loveLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (LoveFragment.keycountlove != 0) {
            newkeycount = LoveFragment.keycountlove;
            System.out.println(newkeycount);
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;
            System.out.println(newkeycount);

        } else {
            newkeycount = oldkeycount;
            System.out.println(newkeycount);

        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {


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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/

    }

    @Override
    public void onSunsetViewItemClicked(String back, String forei, int position) {

        this.categoryPosition = 11;
        this.currentItempos = position;


        int rewardPosition = position;
        if (adLoaded && sunsetLoad && position > 2) {
            rewardPosition = position - 1;
        } else {
            rewardPosition = position;
        }
        int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newkeycount = 0;
        if (SunsetFragment.keycountsunset != 0) {
            newkeycount = SunsetFragment.keycountsunset;
        }
        if (newkeycount > 0) {
            newkeycount = oldkeycount + newkeycount;

        } else {
            newkeycount = oldkeycount;
        }

        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                if (rewardPosition >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                if (rewardPosition >= newkeycount) {

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

                    Log.d("checkFilepathh", backPath);

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
                                                Activity activityContext = HomeActivity.this;
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

                        //  new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                            /*Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
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

                    Log.d("checkFilepathh", backPath);

                    String urlsPath[] = {back, forei};


                    if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                        new DownloadFileAsync(urlsPath).execute(urlsPath);

                    } else {

                           /* Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

                        if (fromActvity != null) {
                            if (fromActvity.equals("Erasebg")) {

                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        }


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

                Log.d("checkFilepathh", backPath);

                String urlsPath[] = {back, forei};


                if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                    new DownloadFileAsync(urlsPath).execute(urlsPath);

                } else {

                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                    if (fromActvity != null) {
                        if (fromActvity.equals("Erasebg")) {

                            Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                            intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                            intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                            intent.putExtra("backPath", HomeActivity.this.backPath);
                            intent.putExtra("forePath", HomeActivity.this.forePath);
                            intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction("android.intent.action.PICK");
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    }


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

            Log.d("checkFilepathh", backPath);

            String urlsPath[] = {back, forei};


            if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()) {

                new DownloadFileAsync(urlsPath).execute(urlsPath);

            } else {

                   /* Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
                if (fromActvity != null) {
                    if (fromActvity.equals("Erasebg")) {

                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }


            }

        }

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

        Log.d("checkFilepathh",backPath);

        String urlsPath[] = {back, forei};



        if (!mediaStorageDir.exists() && !mediaStorageDirfore.exists()){

            new DownloadFileAsync(urlsPath).execute(urlsPath);

        }
        else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        }*/

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

    public boolean checkAndRequestPermission() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = this.appPermissions;
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 999);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 999) {
            HashMap hashMap = new HashMap();
            int i2 = 0;
            for (int i3 = 0; i3 < iArr.length; i3++) {
                if (iArr[i3] == -1) {
                    hashMap.put(strArr[i3], Integer.valueOf(iArr[i3]));
                    i2++;
                }
            }
            if (i2 == 0) {

            } else {

                showStoragePermisionDialog();

            }
        }
    }

    private void showStoragePermisionDialog() {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.permission_request_msg));
        dialog.setTitle(getString(R.string.permission_request));
        dialog.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        checkAndRequestPermission();
                    }
                });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("cattttpos", String.valueOf(this.categoryPosition));
       /* if (i == IMMEDIATE_APP_UPDATE_REQ_CODE){

            if (i2 == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + i2, Toast.LENGTH_LONG).show();
            } else if (i2 == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + i2, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + i2, Toast.LENGTH_LONG).show();
                checkUpdate();
            }

        }*/
       /* if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }*/
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null) {
            Uri dataa = data.getData();
            PrintStream printStream2 = System.out;
            printStream2.println("picked photo uri : " + dataa.toString());
            Intent intent3 = new Intent(this, CropActivity.class);
            intent3.putExtra("SELECTED_URI", dataa.toString());
            intent3.putExtra("categoryPos", this.categoryPosition);
            intent3.putExtra("backPath", this.backPath);
            intent3.putExtra("forePath", this.forePath);
            intent3.putExtra("fromHome", "fromHome");
            intent3.putExtra("itemPosition", this.currentItempos);
            startActivity(intent3);

        }


    }

   /* private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)

                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.blue_3))
                .show();
    }*/

    /* private void removeInstallStateUpdateListener() {
         if (appUpdateManager != null) {
             appUpdateManager.unregisterListener(installStateUpdatedListener);
         }
     }*/
   /* @Override
    protected void onStop() {
        super.onStop();
      //  Log.d("onStoppppp","stopp");
        removeInstallStateUpdateListener();
    }*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent intent = new Intent(HomeActivity.this, ExitAppActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();*/
        // sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (fromActvity != null) {
                if (fromActvity.equals("Erasebg")) {
                    if (CommonMethods.getInstance().isFirstTime(HomeActivity.this)) {
                        toastdialog = new Dialog(HomeActivity.this);
                        toastdialog.setCancelable(false);
                        toastdialog.setContentView(R.layout.toast_dialog_to_select_pic);
                        Button btn_ok = (Button) toastdialog.findViewById(R.id.btn_ok);
                        toastdialog.show();
                        WindowManager.LayoutParams lp = toastdialog.getWindow().getAttributes();
                        lp.dimAmount = 0.3f;
                        toastdialog.getWindow().setAttributes(lp);
                        toastdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                                intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                                intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                                intent.putExtra("backPath", HomeActivity.this.backPath);
                                intent.putExtra("forePath", HomeActivity.this.forePath);
                                intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                                startActivity(intent);
                                toastdialog.dismiss();
                            }
                        });
                    } else {
                        Intent intent = new Intent(HomeActivity.this, BackgroundnewActvity.class);
                        intent.putExtra("SELECTED_IMAGE", HomeActivity.this.stingCut);
                        intent.putExtra("catPos", HomeActivity.this.categoryPosition);
                        intent.putExtra("backPath", HomeActivity.this.backPath);
                        intent.putExtra("forePath", HomeActivity.this.forePath);
                        intent.putExtra("currentItem", HomeActivity.this.currentItempos);
                        startActivity(intent);
                    }

                } else {
                    if (CommonMethods.getInstance().isFirstTime(HomeActivity.this)) {
                        popupToast();
                        /*toastdialog = new Dialog(HomeActivity.this);
                        toastdialog.setCancelable(false);
                        toastdialog.setContentView(R.layout.toast_dialog_to_select_pic);
                        Button btn_ok = (Button) toastdialog.findViewById(R.id.btn_ok);
                        toastdialog.show();
                        WindowManager.LayoutParams lp = toastdialog.getWindow().getAttributes();
                        lp.dimAmount = 0.3f;
                        toastdialog.getWindow().setAttributes(lp);
                        toastdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction("android.intent.action.PICK");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                toastdialog.dismiss();
                            }
                        });*/

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction("android.intent.action.PICK");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                }
            }


        }
    }

    private void popupToast() {
        toastdialog = new Dialog(HomeActivity.this);
        toastdialog.setCancelable(false);
        toastdialog.setContentView(R.layout.toast_dialog_to_select_pic);
        Button btn_ok = (Button) toastdialog.findViewById(R.id.btn_ok);
        toastdialog.show();
        WindowManager.LayoutParams lp = toastdialog.getWindow().getAttributes();
        lp.dimAmount = 0.3f;
        toastdialog.getWindow().setAttributes(lp);
        toastdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                toastdialog.dismiss();
            }
        });
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

   /* private void admobAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.native_ad_on_bottomsheet));
        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = isDestroyed();
                        }
                        if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (HomeActivity.this.nativeAd != null) {
                            HomeActivity.this.nativeAd.destroy();
                        }
                        HomeActivity.this.nativeAd = nativeAd;
                        FrameLayout frameLayout = findViewById(R.id.native_ad_home_exit);
                        NativeAdView adView =
                                (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified_bottomsheet, null);
                        populateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        com.google.android.gms.ads.nativead.NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }
    public static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.GONE);
        } else {
            adView.getPriceView().setVisibility(View.GONE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.GONE);
        } else {
            adView.getStoreView().setVisibility(View.GONE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }*/

}