package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.BuildConfig;
import com.hs.photo.background.changer.editor.backgrounderaser.R;

import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;

public class SaveImageActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    //private InterstitialAd mInterstitialAd;
    String savePath;
    //  TextView txtBack;
    TextView txtHome;
    TextView shareMore;
    TextView setWallpapers;
    //  private ReviewManager reviewManager;
    ImageView starOne, starTwo, starThree, starFour, starFive;
    CardView rating_layout;
    // RatingDialogListener
   /* @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }*/
    // private FrameLayout adContainerView;
    private NativeAd nativeAd;
    public static BottomSheetBehavior sheetBehavior;
    private ConstraintLayout bottomsheetview;
    LinearLayout setWallpaper;
    LinearLayout setLockscreen;
    LinearLayout setBoth;

    public Dialog dialogh;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_save_image_new);
        // reviewManager = ReviewManagerFactory.create(this);

        Bundle bundles = new Bundle();
        bundles.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SaveImageActivity");
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("ACTIVITY_SELECTION", bundles);
        }

        //CommonMethods.getInstance().activitiesAD(SaveImageActivity.this);

        if (RemoteConfigValues.getOurRemote().getShowInterstitialOnSave() != null) {
            if (RemoteConfigValues.getOurRemote().getShowInterstitialOnSave().equals("true")) {

                Dialog dialog = new Dialog(SaveImageActivity.this);
                SaveImageActivity.this.dialogh = dialog;
                dialog.setContentView(R.layout.adloading);
                SaveImageActivity.this.dialogh.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                SaveImageActivity.this.dialogh.show();
                SaveImageActivity.this.dialogh.setCancelable(false);

                InterstitialAd.load(SaveImageActivity.this, getResources().getString(R.string.admob_on_launch_interstitial_id), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        if (SaveImageActivity.this.dialogh != null && SaveImageActivity.this.dialogh.isShowing()) {
                            SaveImageActivity.this.dialogh.dismiss();
                        }
                        interstitialAd.show(SaveImageActivity.this);


                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                        if (SaveImageActivity.this.dialogh != null && SaveImageActivity.this.dialogh.isShowing()) {
                            SaveImageActivity.this.dialogh.dismiss();
                        }


                    }
                });


            }
        }


       /* adContainerView=findViewById(R.id.save_banner_ad);

        if (RemoteConfigValues.getOurRemote().getShowBannerAd()!= null){
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")){

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAdsave();
                    }
                });

            }
        }*/
       /* adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adContainerView, SaveImageActivity.this);
            }
        });*/
        if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                admobAd();
            }

        }

        init();
        //showRateApp();

    }
   /* public void loadBannerAdsave() {
        AdView adViewbanner = new AdView(this);
        adViewbanner.setAdUnitId(getString(R.string.banner_ad_on_savescreen));
        adContainerView.removeAllViews();
        adContainerView.addView(adViewbanner);

        AdSize adSize = getAdSize(this, adContainerView);
        adViewbanner.setAdSize(adSize);

        AdRequest adRequest =
                new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adViewbanner.loadAd(adRequest);
    }*/

   /* public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();

                Task <Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {

                });
            }
        });
    }*/

    public void init() {
        this.savePath = new File(getIntent().getData().getPath()).getAbsolutePath();
        //  this.txtBack = (TextView) findViewById(R.id.btn_back);
        this.shareMore = (TextView) findViewById(R.id.share_image);
        this.setWallpapers = (TextView) findViewById(R.id.set_wallpaper);
        this.txtHome = (TextView) findViewById(R.id.btn_home);
        imageView = (ImageView) findViewById(R.id.image);
        setWallpaper = findViewById(R.id.set_wallpaper_bottom);
        setLockscreen = findViewById(R.id.set_lockscreen);
        setBoth = findViewById(R.id.set_both);
        imageView.setImageURI(Uri.parse(this.savePath));
        // this.txtBack.setOnClickListener(this);
        this.shareMore.setOnClickListener(this);
        this.setWallpapers.setOnClickListener(this);
        //findViewById(R.id.instagramShare).setOnClickListener(this);
        //  findViewById(R.id.whatsup_share).setOnClickListener(this);
        // findViewById(R.id.facebook_share).setOnClickListener(this);
        this.txtHome.setOnClickListener(this);


        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.bottom_sheet);
        this.bottomsheetview = constraintLayout;
        BottomSheetBehavior from = BottomSheetBehavior.from(constraintLayout);
        sheetBehavior = from;
        from.setState(BottomSheetBehavior.STATE_HIDDEN);
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                shareMore.setClickable(true);
                if (savePath != null) {

                    setWallpapersbottom(savePath);

                }
            }
        });
        setLockscreen.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                shareMore.setClickable(true);
                if (savePath != null) {

                    setWallpaperslock(savePath);

                }

            }
        });
        setBoth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (savePath != null) {
                    setWallpapersboth(savePath);
                }


            }
        });


        starOne = findViewById(R.id.starOne);
        starTwo = findViewById(R.id.starTwo);
        starThree = findViewById(R.id.starThree);
        starFour = findViewById(R.id.starFour);
        starFive = findViewById(R.id.starFive);
        this.starOne.setOnClickListener(this);
        this.starTwo.setOnClickListener(this);
        this.starThree.setOnClickListener(this);
        this.starFour.setOnClickListener(this);
        this.starFive.setOnClickListener(this);

        rating_layout = findViewById(R.id.rating_layout);
        if (!CommonMethods.getInstance().ratingIsDone(SaveImageActivity.this)) {
            rating_layout.setVisibility(View.VISIBLE);
        } else {
            rating_layout.setVisibility(View.GONE);
        }
        //newLibRateDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setWallpapersboth(String file) {
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        // String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(file);

        try {
            myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
            myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
            Toast.makeText(SaveImageActivity.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setWallpaperslock(String file) {
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        // String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(file);

        try {
            myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
            Toast.makeText(SaveImageActivity.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void shareImage() {
        this.shareMore.setClickable(false);
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.addFlags(524288);
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.share_text) + " : https://play.google.com/store/apps/details?id=" + getPackageName());
            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(this.savePath)));
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_home) {
            //showAd();
            shareMore.setClickable(true);

            Intent intent = new Intent(SaveImageActivity.this, MainHomeActivity.class);
            intent.putExtra("showAd", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else if (id == R.id.share_image) {
            shareImage();
        }
       /* if (id == R.id.instagramShare) {
            try {
                Uri uriImage = FileProvider.getUriForFile(SaveImageActivity.this,
                        BuildConfig.APPLICATION_ID + ".fileprovider", new File(savePath));
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uriImage);
                intent.setPackage("com.instagram.android");
                startActivity(intent);
            } catch (Exception i) {
                Toast.makeText(this, getString(R.string.no_instagram_app), Toast.LENGTH_SHORT).show();
            }


        }
        if (id == R.id.whatsup_share) {
            try {
                Uri uriImage = FileProvider.getUriForFile(SaveImageActivity.this,
                        BuildConfig.APPLICATION_ID + ".fileprovider", new File(savePath));

                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uriImage);
                intent.setPackage("com.whatsapp");
                startActivity(intent);
            } catch (Exception i) {
                Toast.makeText(this, getString(R.string.no_whatsapp_app), Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.facebook_share) {
           // initShareIntent("face");

            try {
                Uri uriImage = FileProvider.getUriForFile(SaveImageActivity.this,
                        BuildConfig.APPLICATION_ID + ".fileprovider", new File(savePath));

                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uriImage);
                intent.setPackage("com.facebook.katana");
                startActivity(intent);
            } catch (Exception i) {
                Toast.makeText(this, getString(R.string.no_facebook_app), Toast.LENGTH_SHORT).show();
            }


        }*/
        if (id == R.id.set_wallpaper) {
            shareMore.setClickable(true);
            File mediaStorageDir = null;
            mediaStorageDir = new File(savePath);
            // setWalpapersnew(mediaStorageDir);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            } else {
                setWalpapersnew(mediaStorageDir);
            }

        } else if (id == R.id.starOne || id == R.id.starTwo || id == R.id.starThree) {

            // openFeedback();
            messagetomail();
        } else if (id == R.id.starFour || id == R.id.starFive) {

            ratingPlaystore();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setWallpapersbottom(String file) {
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        // String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(file);

        try {
            if (bitmap != null) {
                if (myWallpaperManager != null) {
                    myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    Toast.makeText(SaveImageActivity.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ratingPlaystore() {
        CommonMethods.getInstance().ratingDone(SaveImageActivity.this);
        Intent ratingIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        startActivity(ratingIntent);
    }

    private void openMail() {
        try {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"hangoverstudios.mobile@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " Feedback ");
            email.putExtra(Intent.EXTRA_TEXT, R.string.appfeedback_text);
            email.setType("message/rfc822");
            email.setPackage("com.google.android.gm");
            startActivity(email);
        } catch (Exception i) {
            Toast.makeText(this, "Gmail app not found..!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setWalpapersnew(File file) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SaveImageActivity.this);
        dialog.setTitle("Wallpaper")
                .setIcon(R.drawable.wallpaper)
                .setMessage("Are you sure you want to set wallpaper!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());


                        Intent setAs = new Intent(Intent.ACTION_ATTACH_DATA);
                        setAs.addCategory(Intent.CATEGORY_DEFAULT);
                        Uri sourceUri = FileProvider.getUriForFile(SaveImageActivity.this,
                                getApplicationContext().getPackageName() + ".fileprovider", file);
                        setAs.putExtra("mimeType", "image/*");
                        setAs.setData(sourceUri);
                        setAs.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(setAs, "Set As"));

//
                    }
                });
        if (!isFinishing()) {
            dialog.show();
        }


    }

    public void messagetomail() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.appfeedback);
        alertDialog.setMessage(R.string.appfeedback_text);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                CommonMethods.getInstance().ratingDone(SaveImageActivity.this);

                openMail();
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    private void openFeedback() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("App Feedback");
        alertDialog.setMessage("Please give us your feedback we will improve ");
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                CommonMethods.getInstance().ratingDone(SaveImageActivity.this);

                Intent intent = new Intent(Intent.ACTION_VIEW);

                Uri data = Uri.parse("mailto:polisurendrakumar7@gmail.com?subject= Phone Caller Screen Feedback ");
                intent.setData(data);
                PackageManager packageManager = getPackageManager();
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent);
                }
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void initShareIntent(String type) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(
                share, 0);
        if (!resInfo.isEmpty()) {
            // FilePath = getImagePath();

            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type)
                        || info.activityInfo.name.toLowerCase().contains(type)) {

                    Uri uriImage = FileProvider.getUriForFile(SaveImageActivity.this,
                            getApplicationContext().getPackageName() + ".fileprovider", new File(savePath));

                    share.putExtra(Intent.EXTRA_SUBJECT, "Created With #Spiral Photo Editor");
                    share.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    share.putExtra(Intent.EXTRA_STREAM, uriImage);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Toast.makeText(this, getString(R.string.no_facebook_app), Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(Intent.createChooser(share, "Select"));
        }
    }


    @SuppressLint("WrongConstant")
    public void openNext() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(536870912);
        intent.addFlags(67108864);
        startActivity(intent);
    }

    public void newLibRateDialog() {
        new AppRatingDialog.Builder().setPositiveButtonText("Submit").setNegativeButtonText("Cancel").setNeutralButtonText("Later").setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!")).setDefaultRating(4).setTitle("Rate this application").setDescription("Please select some stars and give your feedback").setCommentInputEnabled(false).setStarColor(R.color.yellow).setNoteDescriptionTextColor(R.color.text_color).setTitleTextColor(R.color.text_color).setDescriptionTextColor(R.color.text_color).setHint("Please write your comment here ...").setHintTextColor(R.color.hintTextColor).setWindowAnimation(R.style.MyDialogFadeAnimation).setCancelable(false).setCanceledOnTouchOutside(false).create(this).show();
    }

   /* @Override
    public void onPositiveButtonClicked(int i, String str) {
        if (i > 3) {
            rateApp();
        }
    }*/

    /* private void rateApp() {
         try {
             startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
         } catch (ActivityNotFoundException unused) {
             startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
         }
     }*/
    private void admobAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.native_ad_on_mainhome));
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
                        if (SaveImageActivity.this.nativeAd != null) {
                            SaveImageActivity.this.nativeAd.destroy();

                        }
                        SaveImageActivity.this.nativeAd = nativeAd;
                        FrameLayout frameLayout = findViewById(R.id.native_ad_save);
                        NativeAdView adView =
                                (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified_main_home, null);
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
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());


        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.GONE);
        } else {
            adView.getBodyView().setVisibility(View.GONE);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        shareMore.setClickable(true);
    }
}
