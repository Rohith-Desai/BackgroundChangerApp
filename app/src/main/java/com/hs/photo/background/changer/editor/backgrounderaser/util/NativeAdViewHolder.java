package com.hs.photo.background.changer.editor.backgrounderaser.util;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hs.photo.background.changer.editor.backgrounderaser.R;

public class NativeAdViewHolder  extends RecyclerView.ViewHolder {

    private NativeAdView adView;

    public NativeAdView getAdView() {
        return adView;
    }

    public NativeAdViewHolder(View view) {
        super(view);
        adView = (NativeAdView) view.findViewById(R.id.ad_view);

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
    }
}
