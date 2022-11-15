package com.hs.photo.background.changer.editor.backgrounderaser.ads;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.LoadindAdlaunch;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.LoadingAdsave;

import java.util.LinkedList;
import java.util.Queue;


public class CommonMethods {

    public static final CommonMethods commonMethods = new CommonMethods();

    public Bitmap getCROPPED_BITMAP() {
        return CROPPED_BITMAP;
    }

    public void setCROPPED_BITMAP(Bitmap CROPPED_BITMAP) {
        this.CROPPED_BITMAP = CROPPED_BITMAP;
    }

    public Bitmap CROPPED_BITMAP = null;
    private final static String RATING_PREFERENCES = "backgroundchanger";

    private Queue<String> bitmapEventQueue = new LinkedList<>();

    public void addToBitmapEventQueue(String record) {
        bitmapEventQueue.add(record);
    }

    public Queue<String> getBitmapEventQueue() {
        return bitmapEventQueue;
    }

    public void clearBitmapEventQueue() {
        if (bitmapEventQueue != null) {
            bitmapEventQueue.clear();
        }
    }

    public static CommonMethods getInstance() {
        return commonMethods;
    }


    public boolean ratingIsDone(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(RATING_PREFERENCES, MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedpreferences.edit();
        return sharedpreferences.getBoolean("rating", false);
    }

    public void ratingDone(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(RATING_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("rating", true);
        editor.apply();
    }

    public void onLaunchAD(Context context) {
        // Log.d("onalunchinterestial", RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch());
        if (RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch() != null
                && RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch().equals("true")) {
            //showGoogleAd(context);
            Intent intent = new Intent(context, LoadindAdlaunch.class);
            context.startActivity(intent);
        }
    }

    public void activitiesAD(Context context) {


        if (RemoteConfigValues.getOurRemote().getShowInterstitialOnSave() != null) {
            if (RemoteConfigValues.getOurRemote().getShowInterstitialOnSave().equals("true")) {

                Intent intent = new Intent(context, LoadingAdsave.class);
                context.startActivity(intent);

            }
        }

    }

    public void loadBannerAdMain(FrameLayout adViewContainer, Context context) {
        if (adViewContainer != null) {

            if (context != null) {
                AdView adViewBanner = new AdView(context);
                adViewBanner.setAdUnitId(context.getString(R.string.banner_ad_on_mainscreen));
                adViewContainer.removeAllViews();
                adViewContainer.addView(adViewBanner);

                AdSize adSize = getAdSize(context, adViewContainer);
                adViewBanner.setAdSize(adSize);

                AdRequest adRequest =
                        new AdRequest.Builder().build();

                adViewBanner.loadAd(adRequest);
            }


        }
    }

    public static AdSize getAdSize(Context context, View adContainerView) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public static void populateNativeAdViewRecycler(NativeAd nativeAd, NativeAdView adView) {


        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
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
    }

    public boolean isFirstTime(Context vcontext) {
        SharedPreferences preferences = vcontext.getSharedPreferences("MYPREF_ACESS",vcontext.MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

}











