package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.ConnectivityReceiver;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.SelectionFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.util.SwipeDownListener;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private static final int PERMISSION_REQUEST_CODE = 999;
    private static final int PICK_IMAGE_REQUEST = 189;
    private static final int REQUEST_TAKE_PHOTO = 989;
    private String[] appPermissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    CardView cameraContainer;
    String currentPhotoPath;
    CardView galleryContainer;
    ImageView imgRate;
    ImageView imgShare;
    boolean isCameraContainerClicked = false;
    boolean isCutoutContainerClicked = false;
    boolean isGalleryContainerClicked = false;
    boolean isRecentContainerClicked = false;
    //private InterstitialAd mInterstitialAd;
    SharedPreferences prefs;
    CardView recentContainer;
    RelativeLayout rlMoreApp;
    TextView txtPrivacyPolicy;
    ImageView starOne, starTwo, starThree, starFour, starFive;
    CardView rating_layout;
    private FrameLayout selectionFragmentContainer;
    FragmentTransaction fragmentTransaction;
    private SelectionFragment selectionFragment;
    private FrameLayout adContainerView;

    private RelativeLayout alterNativeAd;

    boolean showAd = true;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_main_3);
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
        if (!CommonMethods.getInstance().ratingIsDone(MainActivity.this)) {
            rating_layout.setVisibility(View.VISIBLE);
        } else {
            rating_layout.setVisibility(View.GONE);
        }

        if (getIntent().hasExtra("showAd")) {
            showAd = getIntent().getBooleanExtra("showAd", false);
        }

//        if (getIntent().hasExtra("SaveFinishActivity")) {
//            showAd = getIntent().getBooleanExtra("SaveFinishActivity", false);
//        }


       /* adContainerView = findViewById(R.id.main_banner_ad);
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adContainerView, MainActivity.this);
            }
        });*/

        alterNativeAd = findViewById(R.id.alter_native);

        initViews();
       // MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("D386081C603C356C4F2FF4E48ACDA0CA")).build());
        //loadAd();
    }



    private void loadFragment(Fragment fragment) {

// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(selectionFragmentContainer.getId(), fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private void initViews() {
        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.cameraContainer);
//        CardView relativeLayout = (CardView) findViewById(R.id.cameraContainer);
//        this.cameraContainer = relativeLayout;
//        relativeLayout.setOnClickListener(this);
        CardView relativeLayout2 = (CardView) findViewById(R.id.galleryContainer);
        // this.galleryContainer = relativeLayout2;
        selectionFragmentContainer = findViewById(R.id.selection_fragment);
        selectionFragmentContainer.setOnTouchListener(new SwipeDownListener(MainActivity.this));

        selectionFragment = new SelectionFragment();
        findViewById(R.id.spiral_edit).setOnClickListener(this);
        // CardView relativeLayout3 = (CardView) findViewById(R.id.recentContainer);
        // this.recentContainer = relativeLayout3;
        findViewById(R.id.recentContainer).setOnClickListener(this);
        //RelativeLayout relativeLayout4 = (RelativeLayout) findViewById(R.id.rl_more_app);
        //this.rlMoreApp = relativeLayout4;
        //relativeLayout4.setOnClickListener(this);
        //ImageView imageView = (ImageView) findViewById(R.id.img_rate);
        //this.imgRate = imageView;
        //imageView.setOnClickListener(this);
        //ImageView imageView2 = (ImageView) findViewById(R.id.img_share);
        //this.imgShare = imageView2;
        //imageView2.setOnClickListener(this);
        //TextView textView = (TextView) findViewById(R.id.txt_privacy_policy);
        //this.txtPrivacyPolicy = textView;
        //textView.setOnClickListener(this);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void setAnimationAndStartSelectionFragment() {
        loadFragment(new SelectionFragment());
        Animation slide_in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);

        selectionFragmentContainer.startAnimation(slide_in);
        // slide_in.setDuration(2000);
        slide_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                selectionFragmentContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.spiral_edit) {
            //Toast.makeText(this, "spiral_clicked..", Toast.LENGTH_SHORT).show();
            //setAnimationAndStartSelectionFragment();
            loadFragment(selectionFragment);

//            this.isCameraContainerClicked = true;
//            this.isGalleryContainerClicked = false;
//            this.isRecentContainerClicked = false;
//            this.isCutoutContainerClicked = false;
//            if (checkAndRequestPermission()) {
//                dispatchTakePictureIntent();
//            }
        } else if (id == R.id.starOne || id == R.id.starTwo || id == R.id.starThree) {

            // openFeedback();
            messagetomail();
            if (selectionFragment.isVisible()) {
                //fragmentTransaction.remove(selectionFragment);
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down).remove((Fragment) selectionFragment).commitAllowingStateLoss();
            }

        } else if (id == R.id.starFour || id == R.id.starFive) {

            ratingPlaystore();
            if (selectionFragment.isVisible()) {
                //fragmentTransaction.remove(selectionFragment);
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down).remove((Fragment) selectionFragment).commitAllowingStateLoss();
            }

        }
        /*else if (id == R.id.galleryContainer) {

            loadFragment(new SelectionFragment());

            *//*this.isCameraContainerClicked = false;
            this.isGalleryContainerClicked = true;
            this.isRecentContainerClicked = false;
            this.isCutoutContainerClicked = false;
            if (checkAndRequestPermission()) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }*//*
        }*/ /*else if (id == R.id.rl_more_app) {
            moreApp("https://play.google.com/store/apps/developer?id=DEMO");
        } else if (id == R.id.img_share) {
            shareApp();
        } else if (id == R.id.img_rate) {
            rateApp();
        }*/
        else if (id == R.id.recentContainer) {
            openNext();
            if (selectionFragment.isVisible()) {
                //fragmentTransaction.remove(selectionFragment);
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down).remove((Fragment) selectionFragment).commitAllowingStateLoss();
            }
        }/* else if (id == R.id.txt_privacy_policy) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setData(Uri.parse("https://sites.google.com/"));
            startActivity(intent2);
        }*/
    }

    public void selectPhoto() {
        this.isCameraContainerClicked = false;
        this.isGalleryContainerClicked = true;
        this.isRecentContainerClicked = false;
        this.isCutoutContainerClicked = false;
        if (checkAndRequestPermission()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    public void captureImage() {
        this.isCameraContainerClicked = true;
        this.isGalleryContainerClicked = false;
        this.isRecentContainerClicked = false;
        this.isCutoutContainerClicked = false;
        if (checkAndRequestPermission()) {
            dispatchTakePictureIntent();
        }
    }

    public void moreApp(String str) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/search?q=pub:" + getResources().getString(R.string.app_name))));
        }
    }

    public void rateApp() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
    }

    public void shareApp() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", "Hey guys, look at this awesome app --> https://play.google.com/store/apps/details?id=" + getPackageName());
            Intent createChooser = Intent.createChooser(intent, "Share");
            createChooser.addFlags(268435456);
            startActivity(createChooser);
        } catch (Exception e) {
            e.printStackTrace();
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

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException unused) {
            }
            if (file != null) {
                intent.putExtra("output", FileProvider.getUriForFile(this, "com.hs.photo.background.changer.editor.backgrounderaser.fileprovider", file));
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File createTempFile = File.createTempFile("JPEG_" + format + "_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        this.currentPhotoPath = createTempFile.getAbsolutePath();
        return createTempFile;
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == REQUEST_TAKE_PHOTO && i2 == -1) {
            galleryAddPic();
            PrintStream printStream = System.out;
            printStream.println("currentPhotoPath : " + this.currentPhotoPath);
            Intent intent2 = new Intent(this, CropActivity.class);
            intent2.putExtra("SELECTED_URI", Uri.fromFile(new File(this.currentPhotoPath)).toString());
            startActivity(intent2);

        } else if (i == PICK_IMAGE_REQUEST && i2 == -1 && intent != null && intent.getData() != null) {
            Uri data = intent.getData();
            PrintStream printStream2 = System.out;
            printStream2.println("picked photo uri : " + data.toString());
            Intent intent3 = new Intent(this, CropActivity.class);
            intent3.putExtra("SELECTED_URI", data.toString());
            startActivity(intent3);

        }
        if (selectionFragment.isVisible()) {
            //fragmentTransaction.remove(selectionFragment);
            getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down).remove((Fragment) selectionFragment).commitAllowingStateLoss();
        }
    }

    private void galleryAddPic() {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(this.currentPhotoPath)));
        sendBroadcast(intent);
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
                if (this.isCameraContainerClicked) {
                    this.isCameraContainerClicked = false;
                    dispatchTakePictureIntent();
                } else if (this.isGalleryContainerClicked) {
                    this.isGalleryContainerClicked = false;
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.PICK");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else if (this.isRecentContainerClicked) {
                    this.isRecentContainerClicked = false;
                    startActivity(new Intent(this, MyCreationActivity.class));
                } else if (this.isCutoutContainerClicked) {
                    this.isCutoutContainerClicked = false;
                }
            }
        }
    }

    /*public void loadAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        this.mInterstitialAd = interstitialAd;
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad));
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        this.mInterstitialAd.setAdListener(new AdListener() {
            *//* class com.hs.photo.background.changer.editor.backgrounderaser.activity.MainActivity.AnonymousClass1 *//*

            @Override // com.google.android.gms.ads.AdListener
            public void onAdClosed() {
                MainActivity.this.openNext();
                MainActivity.this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public void showAd() {
        InterstitialAd interstitialAd = this.mInterstitialAd;
        if (interstitialAd == null || !interstitialAd.isLoaded()) {
            openNext();
        } else {
            this.mInterstitialAd.show();
        }
    }*/

    public void openNext() {
        this.isCameraContainerClicked = false;
        this.isGalleryContainerClicked = false;
        this.isRecentContainerClicked = true;
        this.isCutoutContainerClicked = false;
        if (checkAndRequestPermission()) {
            startActivity(new Intent(this, MyCreationActivity.class));
            if (selectionFragment.isVisible()) {
                //fragmentTransaction.remove(selectionFragment);
                getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down).remove((Fragment) selectionFragment).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (selectionFragment.isVisible()) {
            //fragmentTransaction.remove(selectionFragment);
            getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down).remove((Fragment) selectionFragment).commitAllowingStateLoss();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {


        Intent intent = new Intent(MainActivity.this, ExitAppActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        /*View inflate = LayoutInflater.from(this).inflate(R.layout.exit_dialog, (ViewGroup) findViewById(16908290), false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.show();
        ((Button) inflate.findViewById(R.id.btnCancelDialog)).setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                create.dismiss();
                MainActivity.this.finish();
            }
        });
        ((Button) inflate.findViewById(R.id.btnBackWork)).setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                create.dismiss();
            }
        });*/
    }

    private void ratingPlaystore() {
        CommonMethods.getInstance().ratingDone(MainActivity.this);
        Intent ratingIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        startActivity(ratingIntent);
    }

    public void messagetomail() {

       /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please give us your feedback we will improve");
        alertDialogBuilder.setTitle("App Feedback");

        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(newlinear!=null){
                            ((ViewGroup) newlinear.getParent()).removeView(newlinear);
                        }
                        edito.putString("star", "1");
                        edito.commit();
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hangoverstudios.mobile@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Photo Book Editor Feedback");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();*/


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.appfeedback);
        alertDialog.setMessage(R.string.appfeedback_text);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                CommonMethods.getInstance().ratingDone(MainActivity.this);

                /*Intent intent = new Intent(Intent.ACTION_VIEW);

                Uri data = Uri.parse("mailto:hangoverstudios.mobile@gmail.com?subject= "+getString(R.string.app_name)+" Feedback ");
                intent.setData(data);
                PackageManager packageManager = getPackageManager();
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent);
                }*/
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

    private void openFeedback() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("App Feedback");
        alertDialog.setMessage("Please give us your feedback we will improve ");
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                CommonMethods.getInstance().ratingDone(MainActivity.this);

                Intent intent = new Intent(Intent.ACTION_VIEW);

                Uri data = Uri.parse("mailto:polisurendrakumar7@gmail.com?subject= Neon Photo Editor Feedback ");
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



    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
