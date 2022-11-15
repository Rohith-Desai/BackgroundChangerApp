package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.ConnectivityReceiver;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.MyAppOpen;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hs.photo.background.changer.editor.backgrounderaser.util.MyBounceInterpolator;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SplashScreen extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    public static final String PREF_FILE = "PHOTO_COLLAGE";
    public static final String PURCHASE_KEY = "removeads";
    public static SplashScreen splashInstance;
    public static FirebaseAnalytics mFirebaseAnalytics;
    public FirebaseRemoteConfig remoteConfig;

    ArrayList<Object> greetingPotList = new ArrayList<>();
    ArrayList<Object> greetingThumbList = new ArrayList<>();
    ArrayList<Object> greetingTrendList = new ArrayList<>();
    ArrayList<Object> greetingTrendThumbList = new ArrayList<>();
    ArrayList<Object> singleFramesList = new ArrayList<>();
    ArrayList<Object> singleFramesThumbList = new ArrayList<>();
    ArrayList<Object> singleTrendingFramesList = new ArrayList<>();
    ArrayList<Object> singleTrendingThumbList = new ArrayList<>();
    ArrayList<Object> doubleFramesList = new ArrayList<>();
    ArrayList<Object> doubleThumbList = new ArrayList<>();
    ArrayList<Object> doubleTrendingFramesList = new ArrayList<>();
    ArrayList<Object> doubleTrendingThumbList = new ArrayList<>();

    ArrayList<Object> shapeFramesList = new ArrayList<>();
    ArrayList<Object> shapeTrendingFramesList = new ArrayList<>();
    boolean adLoaded, timeOut = false;
    JSONObject remoteConfJson;
    TextView textView1, textView2, textView3;
    private BroadcastReceiver br = new ConnectivityReceiver();
    private boolean receiverRegistered = false;
    //TextView load;


    public static void slideDown(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.f);

        if (view.getHeight() > 0) {
            slideUpNow(view);
        } else {
            // wait till height is measured
            view.post(new Runnable() {
                @Override
                public void run() {
                    slideUpNow(view);
                }
            });
        }
    }

    private static void slideUpNow(final View view) {
        view.setTranslationY(view.getHeight());
        view.animate()
                .translationY(0)
                .setDuration(4000)
                .alpha(1.f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                        view.setAlpha(1.f);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        // load=findViewById(R.id.load_text);
        splashInstance = this;
        FirebaseApp.initializeApp(this);

    }

    private void assignDefaultValues() {
      /*  try {
            JSONObject defaultJson = new JSONObject("{\n" +
                    "            \"upgradeAppVersion\": \"1\",\n" +
                    "            \"showInterstitialOnLaunch\": \"false\",\n" +
                    "            \"showInterstitialOnExit\": \"true\",\n" +
                    "            \"showInterstitialAd\": \"false\",\n" +
                    "            \"InterstitialOnlyGoogle\": \"true\",\n" +
                    "            \"InterstitialOnlyFB\": \"true\",\n" +
                    "            \"AdRotationPolicy\": \"false\",\n" +
                    "            \"AdRotationPolicyNative\": \"false\",\n" +
                    "            \"showNativeAd\": \"true\",\n" +
                    "            \"showNativeAdOnMainScreen\": \"true\",\n" +
                    "            \"NativeOnlyGoogle\": \"true\",\n" +
                    "            \"NativeOnlyFB\": \"false\",\n" +
                    "            \"showOurAppInterstitials\": \"false\",\n" +
                    "            \"OurApps\": \"false\",\n" +
                    "            \"enableIAPflag\": \"false\",\n" +
                    "            \"singleFramesCount\": \"38\",\n" +
                    "            \"greetingCardsCount\": \"28\",\n" +
                    "            \"allFramesCount\": \"20\",\n" +
                    "            \"singleFrames\": \"\",\n" +
                    "            \"doubleFrames\": \"\",\n" +
                    "            \"doubleThumbs\": \"\",\n" +
                    "            \"greetingCards\": \"\",\n" +
                    "            \"shapesFrames\": \"\",\n" +
                    "            \"trendingSingleFrames\": \"\",\n" +
                    "            \"trendingSingleThumbs\": \"\",\n" +
                    "            \"trendingDoubleFrames\": \"\",\n" +
                    "            \"trendingDoubleThumbs\": \"\",\n" +
                    "            \"trendingGreetingCards\": \"\",\n" +
                    "            \"trendingGreetingThumbs\": \"\"\n" +
                    "            }");
            assignValuesRemote(defaultJson, "local");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        RemoteConfigValues.getOurRemote().setUpgradeAppVersion("1");
        RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunch("false");
        RemoteConfigValues.getOurRemote().setShowInterstitialAd("false");
        RemoteConfigValues.getOurRemote().setShowNativeAd("false");
        RemoteConfigValues.getOurRemote().setShowNativeAdOnMainScreen("flase");
        RemoteConfigValues.getOurRemote().setEnableIAPflag("false");
        RemoteConfigValues.getOurRemote().setShowBannerAd("false");
        RemoteConfigValues.getOurRemote().setNatureUrl("");
        RemoteConfigValues.getOurRemote().setFantasyUrl("");
        RemoteConfigValues.getOurRemote().setFlowerUrl("");
        RemoteConfigValues.getOurRemote().setBirthdayUrl("");
        RemoteConfigValues.getOurRemote().setNeonUrl("");
        RemoteConfigValues.getOurRemote().setNatureCount("0");
        RemoteConfigValues.getOurRemote().setFantasyCount("0");
        RemoteConfigValues.getOurRemote().setFlowerCount("0");
        RemoteConfigValues.getOurRemote().setBirthdayCount("0");
        RemoteConfigValues.getOurRemote().setNeonCount("0");

        RemoteConfigValues.getOurRemote().setAnimalUrl("");
        RemoteConfigValues.getOurRemote().setAnniversaryUrl("");
        RemoteConfigValues.getOurRemote().setBirdUrl("");
        RemoteConfigValues.getOurRemote().setCityUrl("");
        RemoteConfigValues.getOurRemote().setFireUrl("");
        RemoteConfigValues.getOurRemote().setLoveUrl("");
        RemoteConfigValues.getOurRemote().setSunsetUrl("");
        RemoteConfigValues.getOurRemote().setAnimalCount("0");
        RemoteConfigValues.getOurRemote().setAnniversaryCount("0");
        RemoteConfigValues.getOurRemote().setBirdCount("0");
        RemoteConfigValues.getOurRemote().setCityCount("0");
        RemoteConfigValues.getOurRemote().setFireCount("0");
        RemoteConfigValues.getOurRemote().setLoveCount("0");
        RemoteConfigValues.getOurRemote().setSunsetCount("0");
        RemoteConfigValues.getOurRemote().setShowInterstitialOnSave("false");

        RemoteConfigValues.getOurRemote().setEnableRewardAfter("0");

        loadRemoteConfig();
    }


    private void loadRemoteConfig() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                timeOut = true;
            }
        }, 10000);

        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .build();
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings);
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        long cacheExpiration = 43200;

        remoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    remoteConfig.activate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {

                            RemoteConfigValues.getOurRemote().setUpgradeAppVersion(remoteConfig.getString("upgradeAppVersion"));
                            RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunch(remoteConfig.getString("showInterstitialOnLaunch"));
                            RemoteConfigValues.getOurRemote().setShowInterstitialAd(remoteConfig.getString("showInterstitialAd"));
                            RemoteConfigValues.getOurRemote().setShowNativeAd(remoteConfig.getString("showNativeAd"));
                            RemoteConfigValues.getOurRemote().setShowNativeAdOnMainScreen(remoteConfig.getString("showNativeAdOnMainScreen"));
                            RemoteConfigValues.getOurRemote().setEnableIAPflag(remoteConfig.getString("enableIAPflag"));
                            RemoteConfigValues.getOurRemote().setShowBannerAd(remoteConfig.getString("showBannerAd"));
                            RemoteConfigValues.getOurRemote().setNatureUrl(remoteConfig.getString("natureUrl"));
                            RemoteConfigValues.getOurRemote().setFantasyUrl(remoteConfig.getString("fantasyUrl"));
                            RemoteConfigValues.getOurRemote().setFlowerUrl(remoteConfig.getString("flowerUrl"));
                            RemoteConfigValues.getOurRemote().setBirthdayUrl(remoteConfig.getString("birthdayUrl"));
                            RemoteConfigValues.getOurRemote().setNeonUrl(remoteConfig.getString("neonUrl"));

                            //To load count from firebse
                            RemoteConfigValues.getOurRemote().setNatureCount(remoteConfig.getString("natureCount"));
                            RemoteConfigValues.getOurRemote().setFantasyCount(remoteConfig.getString("fantasyCount"));
                            RemoteConfigValues.getOurRemote().setFlowerCount(remoteConfig.getString("flowerCount"));
                            RemoteConfigValues.getOurRemote().setBirthdayCount(remoteConfig.getString("birthdayCount"));
                            RemoteConfigValues.getOurRemote().setNeonCount(remoteConfig.getString("neonCount"));

                            //To count frames Manually
                            /*RemoteConfigValues.getOurRemote().setNatureCount("30");
                            RemoteConfigValues.getOurRemote().setFantasyCount("30");
                            RemoteConfigValues.getOurRemote().setFlowerCount("30");
                            RemoteConfigValues.getOurRemote().setBirthdayCount("30");
                            RemoteConfigValues.getOurRemote().setNeonCount("30");*/


                            RemoteConfigValues.getOurRemote().setAnimalUrl(remoteConfig.getString("animalUrl"));
                            RemoteConfigValues.getOurRemote().setAnniversaryUrl(remoteConfig.getString("anniversaryUrl"));
                            RemoteConfigValues.getOurRemote().setBirdUrl(remoteConfig.getString("birdUrl"));
                            RemoteConfigValues.getOurRemote().setCityUrl(remoteConfig.getString("cityUrl"));
                            RemoteConfigValues.getOurRemote().setFireUrl(remoteConfig.getString("fireUrl"));
                            RemoteConfigValues.getOurRemote().setLoveUrl(remoteConfig.getString("loveUrl"));
                            RemoteConfigValues.getOurRemote().setSunsetUrl(remoteConfig.getString("sunsetUrl"));

                            //to load count from firebase
                            RemoteConfigValues.getOurRemote().setAnimalCount(remoteConfig.getString("animalCount"));
                            RemoteConfigValues.getOurRemote().setAnniversaryCount(remoteConfig.getString("anniversaryCount"));
                            RemoteConfigValues.getOurRemote().setBirdCount(remoteConfig.getString("birdCount"));
                            RemoteConfigValues.getOurRemote().setCityCount(remoteConfig.getString("cityCount"));
                            RemoteConfigValues.getOurRemote().setFireCount(remoteConfig.getString("fireCount"));
                            RemoteConfigValues.getOurRemote().setLoveCount(remoteConfig.getString("loveCount"));
                            RemoteConfigValues.getOurRemote().setSunsetCount(remoteConfig.getString("sunsetCount"));

                            // to count frames Manually
                           /* RemoteConfigValues.getOurRemote().setAnimalCount("30");
                            RemoteConfigValues.getOurRemote().setAnniversaryCount("30");
                            RemoteConfigValues.getOurRemote().setBirdCount("30");
                            RemoteConfigValues.getOurRemote().setCityCount("30");
                            RemoteConfigValues.getOurRemote().setFireCount("30");
                            RemoteConfigValues.getOurRemote().setLoveCount("30");
                            RemoteConfigValues.getOurRemote().setSunsetCount("30");*/// end of count

                            RemoteConfigValues.getOurRemote().setShowInterstitialOnSave(remoteConfig.getString("showInterstitialOnSave"));
                            RemoteConfigValues.getOurRemote().setEnableRewardAfter(remoteConfig.getString("enableRewardAfter"));

                            //Log.d("enablerewarded",remoteConfig.getString("enableRewardAfter"));

                           /* RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunch("true");
                            RemoteConfigValues.getOurRemote().setShowInterstitialAd("true");
                            RemoteConfigValues.getOurRemote().setShowNativeAd("true");
                            RemoteConfigValues.getOurRemote().setShowNativeAdOnMainScreen("true");
                            RemoteConfigValues.getOurRemote().setShowBannerAd("true");
                            RemoteConfigValues.getOurRemote().setShowInterstitialOnSave("true");*/


                        }
                    });

                }
            }
        });
        //checkData();
    }

    @Override
    public void onBackPressed() {

    }


    private boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyAppOpen.getInstance() != null) {
            MyAppOpen.getInstance().setConnectivityListener(this);
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(br, filter);
        receiverRegistered = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectivityReceiver.connectivityReceiverListener = null;
        if (receiverRegistered) {
            unregisterReceiver(br);
        }
    }


    private void checkData() {


        int localAppVersion = 11;

        if (timeOut) {
            startActivity(new Intent(SplashScreen.this, MainHomeActivity.class));
            finish();
           /* int a = Integer.parseInt(RemoteConfigValues.getOurRemote().getUpgradeAppVersion());
            if (localAppVersion >= a) {
                startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                finish();

            } else {
                startActivity(new Intent(SplashScreen.this, Update.class));
                finish();
            }*/
        } else {
            holdTime();
        }

    }

    private void holdTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkData();

            }
        }, 3000);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {

            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            assignDefaultValues();
            setProgressBar();

        } else {
            Toast.makeText(SplashScreen.this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setProgressBar() {
        long totalSeconds = 11;
        long intervalSeconds = 1;
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax(100);

        CountDownTimer timer = new CountDownTimer(totalSeconds * 1100, intervalSeconds * 110) {

            public void onTick(long millisUntilFinished) {
                Log.d("seconds elapsed: ", String.valueOf((totalSeconds * 1100 - millisUntilFinished) / 110));
                if (android.os.Build.VERSION.SDK_INT >= 11) {

                    ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", (int) (totalSeconds * 1100 - millisUntilFinished) / 110);
                    animation.setDuration(500); // 0.5 second
                    animation.setInterpolator(new DecelerateInterpolator());
                    animation.start();

                } else {
                    progressBar.setProgress((int) (totalSeconds * 1100 - millisUntilFinished) / 110);
                }
                // progressBar.setProgress((int) (totalSeconds * 1100 - millisUntilFinished) / 110);
            }

            public void onFinish() {
                TextView start_but = findViewById(R.id.start_but);
                //load.setVisibility(View.GONE);
                progressBar.setVisibility(View.INVISIBLE);
                start_but.setVisibility(View.VISIBLE);
                final Animation myAnim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bounce);

                // Use bounce interpolator with amplitude 0.2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                start_but.startAnimation(myAnim);

                start_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkData();
                    }
                });
                Log.d("done!", "Time's up!");
            }

        };
        timer.start();
    }


}
