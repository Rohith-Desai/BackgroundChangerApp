package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.CreationsEditor;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.CreationsEraser;

import java.util.ArrayList;
import java.util.List;

import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;

public class CreationsActivity extends AppCompatActivity {

    TabLayout galleryTab;
    ViewPager galleryViewpager;

    // private FrameLayout adContainerView;
    public FrameLayout adContainerView;
    public static CreationsActivity creationsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creations);
        creationsActivity = this;
        //adContainerView=findViewById(R.id.creation_banner_ad);

        Bundle bundles = new Bundle();
        bundles.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "CreationsActivity");
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("ACTIVITY_SELECTION", bundles);
        }

       /* if (RemoteConfigValues.getOurRemote().getShowBannerAd()!= null){
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")){

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAdcreation();
                    }
                });

            }
        }*/
        adContainerView = findViewById(R.id.banner_ad_creations);

        if (RemoteConfigValues.getOurRemote().getShowBannerAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")) {

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {

                        loadBannerAdcreationss();
                    }
                });

            }
        }

        galleryTab = (TabLayout) findViewById(R.id.gallery_tabLayout);
        galleryViewpager = (ViewPager) findViewById(R.id.gallery_designs_viewpager);
        galleryTab.setupWithViewPager(galleryViewpager);

        setupViewPager(galleryViewpager);
    }

    public void loadBannerAdcreationss() {
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreationsEditor(), "Editor");
        adapter.addFragment(new CreationsEraser(), "Eraser");
        viewPager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
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

        public Fragment getFragment(int position) {
            return mFragmentList.get(position);

        }
    }

    public static CreationsActivity getInstance() {
        return creationsActivity;
    }
    public void hideBannerad(){
        adContainerView.setVisibility(View.INVISIBLE);
    }
}