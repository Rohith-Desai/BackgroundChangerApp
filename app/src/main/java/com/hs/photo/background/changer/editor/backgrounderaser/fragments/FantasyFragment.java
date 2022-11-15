package com.hs.photo.background.changer.editor.backgrounderaser.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.nativead.NativeAd;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.HomeActivity;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.NatureframesAdapter;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.util.NativeAdViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.populateNativeAdViewRecycler;

public class FantasyFragment extends Fragment {

    RecyclerView fantasyCateRec;

    /* ArrayList<Integer> nthumb=new ArrayList<>();
     ArrayList<Integer> nback=new ArrayList<>();
     ArrayList<Integer> nfore=new ArrayList<>();*/
    ArrayList<Object> fantasyThumb = new ArrayList<>();
    ArrayList<Object> fantasyBack = new ArrayList<>();
    ArrayList<Object> fantasyFore = new ArrayList<>();

    ArrayList<Object> beforeKeythumb = new ArrayList<>();
    ArrayList<Object> beforeKeyback = new ArrayList<>();
    ArrayList<Object> beforeKeyfore = new ArrayList<>();

    ArrayList<Object> downloadedlistFantasyThumb = new ArrayList<>();
    ArrayList<Object> downloadedlistFantasyback = new ArrayList<>();
    ArrayList<Object> downloadedlistFantasyfore = new ArrayList<>();

    ArrayList<Object> beforekeydownloadedlistFantasyThumb = new ArrayList<>();
    ArrayList<Object> beforekeydownloadedlistFantasyback = new ArrayList<>();
    ArrayList<Object> beforekeydownloadedlistFantasyfore = new ArrayList<>();

    ArrayList<Object> afterkeydownloadedlistFantasyThumb = new ArrayList<>();
    ArrayList<Object> afterkeydownloadedlistFantasyback = new ArrayList<>();
    ArrayList<Object> afterkeydownloadedlistFantasyfore = new ArrayList<>();


    ArrayList<Object> beforekeytobedownloadlistFantasyThumb = new ArrayList<>();
    ArrayList<Object> beforekeytobedownloadlistFantasyback = new ArrayList<>();
    ArrayList<Object> beforekeytobedownloadlistFantasyfore = new ArrayList<>();

    ArrayList<Object> afterkeytobedownloadlistFantasyThumb = new ArrayList<>();
    ArrayList<Object> afterkeytobedownloadlistFantasyback = new ArrayList<>();
    ArrayList<Object> afterkeytobedownloadlistFantasyfore = new ArrayList<>();

    ArrayList<Object> tobedownloadlistThumb = new ArrayList<>();
    ArrayList<Object> tobedownloadlistback = new ArrayList<>();
    ArrayList<Object> tobedownloadlistfore = new ArrayList<>();

    ArrayList<Object> combinedarraylistThumb = new ArrayList<>();
    ArrayList<Object> combinedarraylistback = new ArrayList<>();
    ArrayList<Object> combinedarraylistfore = new ArrayList<>();
    String backFrame, backFrame2;
    String foreFrame, foreFrame2;
    String img1, img2;
    private FantasyFragmentListener fantasyFragmentListener;
    private View myView;
    private List<NativeAd> mNativeAds = new ArrayList<>();
    static final int STRING = 404;
    static final int NATIVE_UnifiedNativeAd = 10519;
    public static int fantacykeycount = 0;

    public interface FantasyFragmentListener {
        void onFantasyViewItemClicked(String back, String forei, int position);
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_fantasy, viewGroup, false);
        this.myView = inflate;
        fantasyCateRec = (RecyclerView) inflate.findViewById(R.id.fantasy_rec);
        fantasyThumb.clear();
        fantasyBack.clear();
        fantasyFore.clear();

        beforeKeythumb.clear();
        beforeKeyback.clear();
        beforeKeyfore.clear();

        beforekeydownloadedlistFantasyThumb.clear();
        beforekeydownloadedlistFantasyback.clear();
        beforekeydownloadedlistFantasyfore.clear();

        afterkeydownloadedlistFantasyThumb.clear();
        afterkeydownloadedlistFantasyback.clear();
        afterkeydownloadedlistFantasyfore.clear();

        beforekeytobedownloadlistFantasyThumb.clear();
        beforekeytobedownloadlistFantasyback.clear();
        beforekeytobedownloadlistFantasyfore.clear();

        afterkeytobedownloadlistFantasyThumb.clear();
        afterkeytobedownloadlistFantasyback.clear();
        afterkeytobedownloadlistFantasyfore.clear();

        combinedarraylistThumb.clear();
        combinedarraylistback.clear();
        combinedarraylistfore.clear();

       /* for (int i=1; i<=2;i++){
            fantasyThumb.add("fantasy_thumb_"+i);
            fantasyBack.add("fantasy_back_"+i);
            fantasyFore.add("fantasy_fore_"+i);
        }*/

        if (RemoteConfigValues.getOurRemote().getFantasyUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getFantasyUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {
                        for (int i = 1; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()); i++) {
                            beforeKeythumb.add(RemoteConfigValues.getOurRemote().getFantasyUrl() + "fantasy_thumb_" + i + ".jpg");
                            beforeKeyback.add(RemoteConfigValues.getOurRemote().getFantasyUrl() + "fantasy_back_" + i + ".png");
                            beforeKeyfore.add(RemoteConfigValues.getOurRemote().getFantasyUrl() + "fantasy_fore_" + i + ".png");
                        }
                    }
                }
            }
        }

        if (RemoteConfigValues.getOurRemote().getFantasyUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getFantasyUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getFantasyCount() != null) {
                    if (!RemoteConfigValues.getOurRemote().getFantasyCount().equals("")) {
                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

                                for (int i = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()) + 1; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getFantasyCount()); i++) {
                                    fantasyThumb.add(RemoteConfigValues.getOurRemote().getFantasyUrl() + "fantasy_thumb_" + i + ".jpg");
                                    fantasyBack.add(RemoteConfigValues.getOurRemote().getFantasyUrl() + "fantasy_back_" + i + ".png");
                                    fantasyFore.add(RemoteConfigValues.getOurRemote().getFantasyUrl() + "fantasy_fore_" + i + ".png");
                                }

                            }
                        }
                    }
                }
            }
        }

        long seed = System.nanoTime();

        Collections.shuffle(beforeKeythumb, new Random(seed));
        Collections.shuffle(beforeKeyback, new Random(seed));
        Collections.shuffle(beforeKeyfore, new Random(seed));

        Collections.shuffle(fantasyThumb, new Random(seed));
        Collections.shuffle(fantasyBack, new Random(seed));
        Collections.shuffle(fantasyFore, new Random(seed));

        for (int i = 0; i < beforeKeythumb.size(); i++) {
            img2 = beforeKeythumb.get(i).toString();
            backFrame2 = beforeKeyback.get(i).toString();
            foreFrame2 = beforeKeyfore.get(i).toString();

//to check and seperate the frames that are downloaded in natureframes list before  key list
            if (img2.contains("https")) {
                String[] backpaths = backFrame2.split("/");
                String[] forepaths = foreFrame2.split("/");
                String getpath = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getContext().getExternalFilesDir(getContext().getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getContext().getResources().getString(R.string.app_name) + "/Images";

                File backmediaStorageDir = null;
                File foremediaStorageDir = null;
                backmediaStorageDir = new File(getpath + "/" + backpaths[backpaths.length - 1]);
                foremediaStorageDir = new File(getpath + "/" + forepaths[forepaths.length - 1]);
                if (backmediaStorageDir.exists() && foremediaStorageDir.exists()) {
                    //adding the downloaded frames
                    beforekeydownloadedlistFantasyThumb.add(beforeKeythumb.get(i));
                    beforekeydownloadedlistFantasyback.add(beforeKeyback.get(i));
                    beforekeydownloadedlistFantasyfore.add(beforeKeyfore.get(i));
                } else {
                    //adding the not downloaded frames
                    beforekeytobedownloadlistFantasyThumb.add(beforeKeythumb.get(i));
                    beforekeytobedownloadlistFantasyback.add(beforeKeyback.get(i));
                    beforekeytobedownloadlistFantasyfore.add(beforeKeyfore.get(i));

                }
            }
        }
        //to check and seperate the frames that are downloaded in natureframes list after key list
        for (int i = 0; i < fantasyThumb.size(); i++) {
            img1 = fantasyThumb.get(i).toString();
            backFrame = fantasyBack.get(i).toString();
            foreFrame = fantasyFore.get(i).toString();


            if (img1.contains("https")) {
                String[] backpaths = backFrame.split("/");
                String[] forepaths = foreFrame.split("/");
                String getpath = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getContext().getExternalFilesDir(getContext().getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getContext().getResources().getString(R.string.app_name) + "/Images";

                File backmediaStorageDir = null;
                File foremediaStorageDir = null;
                backmediaStorageDir = new File(getpath + "/" + backpaths[backpaths.length - 1]);
                foremediaStorageDir = new File(getpath + "/" + forepaths[forepaths.length - 1]);
                if (backmediaStorageDir.exists() && foremediaStorageDir.exists()) {
                    //adding the downloaded frames

                    afterkeydownloadedlistFantasyThumb.add(fantasyThumb.get(i));
                    afterkeydownloadedlistFantasyback.add(fantasyBack.get(i));
                    afterkeydownloadedlistFantasyfore.add(fantasyFore.get(i));

                    System.out.println(afterkeydownloadedlistFantasyThumb.size());
                    fantacykeycount = afterkeydownloadedlistFantasyThumb.size();
                    System.out.println(fantacykeycount);
                } else {
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    //adding the not downloaded frames
                    afterkeytobedownloadlistFantasyThumb.add(fantasyThumb.get(i));
                    afterkeytobedownloadlistFantasyback.add(fantasyBack.get(i));
                    afterkeytobedownloadlistFantasyfore.add(fantasyFore.get(i));
                }
            }
        }
        // addding the downloaded frames in a single list
        if ((beforekeydownloadedlistFantasyThumb != null && beforekeydownloadedlistFantasyThumb.size() > 0) ||
                (afterkeydownloadedlistFantasyThumb != null && afterkeydownloadedlistFantasyThumb.size() > 0)) {
            if ((beforekeydownloadedlistFantasyback != null && beforekeydownloadedlistFantasyback.size() > 0) ||
                    (afterkeydownloadedlistFantasyback != null && afterkeydownloadedlistFantasyback.size() > 0)) {
                if ((beforekeydownloadedlistFantasyfore != null && beforekeydownloadedlistFantasyfore.size() > 0) ||
                        (afterkeydownloadedlistFantasyfore != null && afterkeydownloadedlistFantasyfore.size() > 0)) {
                    beforekeydownloadedlistFantasyThumb.addAll(afterkeydownloadedlistFantasyThumb);
                    beforekeydownloadedlistFantasyback.addAll(afterkeydownloadedlistFantasyback);
                    beforekeydownloadedlistFantasyfore.addAll(afterkeydownloadedlistFantasyfore);

                    System.out.println(beforekeydownloadedlistFantasyThumb);
                    System.out.println(beforekeydownloadedlistFantasyback);
                    System.out.println(beforekeydownloadedlistFantasyfore);
                }
            }
        }
        // addding the frames that are not downloaded .
        if ((beforekeytobedownloadlistFantasyThumb != null && beforekeytobedownloadlistFantasyThumb.size() > 0)
                || (afterkeytobedownloadlistFantasyThumb != null && afterkeytobedownloadlistFantasyThumb.size() > 0)) {
            if ((beforekeytobedownloadlistFantasyback != null && beforekeytobedownloadlistFantasyback.size() > 0)
                    || (afterkeytobedownloadlistFantasyback != null && afterkeytobedownloadlistFantasyback.size() > 0)) {
                if ((beforekeytobedownloadlistFantasyfore != null && beforekeytobedownloadlistFantasyfore.size() > 0)
                        || (afterkeytobedownloadlistFantasyfore != null && afterkeytobedownloadlistFantasyfore.size() > 0)) {

                    beforekeytobedownloadlistFantasyThumb.addAll(afterkeytobedownloadlistFantasyThumb);
                    beforekeytobedownloadlistFantasyback.addAll(afterkeytobedownloadlistFantasyback);
                    beforekeytobedownloadlistFantasyfore.addAll(afterkeytobedownloadlistFantasyfore);

                    System.out.println(beforekeytobedownloadlistFantasyThumb);
                    System.out.println(beforekeytobedownloadlistFantasyback);
                    System.out.println(beforekeytobedownloadlistFantasyfore);

                }
            }
        }
        if ((beforekeydownloadedlistFantasyThumb != null && beforekeydownloadedlistFantasyThumb.size() > 0) ||
                (beforekeytobedownloadlistFantasyThumb != null && beforekeytobedownloadlistFantasyThumb.size() > 0)) {
            if ((beforekeydownloadedlistFantasyback != null && beforekeydownloadedlistFantasyback.size() > 0) ||
                    (beforekeytobedownloadlistFantasyback != null && beforekeytobedownloadlistFantasyback.size() > 0)) {
                if ((beforekeydownloadedlistFantasyfore != null && beforekeydownloadedlistFantasyfore.size() > 0) ||
                        (beforekeytobedownloadlistFantasyfore != null && beforekeytobedownloadlistFantasyfore.size() > 0)) {
                    beforekeydownloadedlistFantasyThumb.addAll(beforekeytobedownloadlistFantasyThumb);
                    beforekeydownloadedlistFantasyback.addAll(beforekeytobedownloadlistFantasyback);
                    beforekeydownloadedlistFantasyfore.addAll(beforekeytobedownloadlistFantasyfore);

                    System.out.println(beforekeydownloadedlistFantasyThumb);
                    System.out.println(beforekeydownloadedlistFantasyback);
                    System.out.println(beforekeydownloadedlistFantasyfore);
                }
            }
        }
        if ((beforekeydownloadedlistFantasyThumb != null && beforekeydownloadedlistFantasyThumb.size() > 0)) {

            combinedarraylistThumb.addAll(beforekeydownloadedlistFantasyThumb);
            combinedarraylistback.addAll(beforekeydownloadedlistFantasyback);
            combinedarraylistfore.addAll(beforekeydownloadedlistFantasyfore);
        }

        /*if (beforeKeythumb != null && beforeKeythumb.size() > 0) {

            for (int i = 0; i < beforeKeythumb.size(); i++) {

                fantasyThumb.add(i, beforeKeythumb.get(i));
                fantasyBack.add(i, beforeKeyback.get(i));
                fantasyFore.add(i, beforeKeyfore.get(i));

            }

        }*/

        HomeActivity activity = (HomeActivity) getActivity();
        if (activity.getfantasyadloaded()) {

            Log.d("checkkkkkkkk", "okkkkk");

            mNativeAds = activity.getRecyclerViewItems();
            fantasyDataWithAds();

        } else {
            fantasyDataonly();
        }
       /* nthumb.clear();
        nthumb.add(R.drawable.bird_t1);
        nthumb.add(R.drawable.bird_t2);
        nthumb.add(R.drawable.birthday_t1);
        nthumb.add(R.drawable.birthday_t2);
        nthumb.add(R.drawable.car_t1);
        nthumb.add(R.drawable.car_t2);
        nthumb.add(R.drawable.city_t1);
        nthumb.add(R.drawable.city_t2);
        nthumb.add(R.drawable.drip_t1);
        nthumb.add(R.drawable.drip_t2);
        nthumb.add(R.drawable.fantasy_t1);
        nthumb.add(R.drawable.fantasy_t2);
        nthumb.add(R.drawable.fire_t1);
        nthumb.add(R.drawable.fire_t2);
        nthumb.add(R.drawable.flower_t1);
        nthumb.add(R.drawable.flower_t2);
        nthumb.add(R.drawable.frames_t1);
        nthumb.add(R.drawable.frames_t2);
        nback.clear();
        nback.add(R.drawable.bird_b1);
        nback.add(R.drawable.bird_b2);
        nback.add(R.drawable.birthday_b1);
        nback.add(R.drawable.birthday_b2);
        nback.add(R.drawable.car_b1);
        nback.add(R.drawable.car_b2);
        nback.add(R.drawable.city_b1);
        nback.add(R.drawable.city_b2);
        nback.add(R.drawable.drip_b1);
        nback.add(R.drawable.drip_b2);
        nback.add(R.drawable.fantasy_b1);
        nback.add(R.drawable.fantasy_b2);
        nback.add(R.drawable.fire_b1);
        nback.add(R.drawable.fire_b2);
        nback.add(R.drawable.flower_b1);
        nback.add(R.drawable.flower_b2);
        nback.add(R.drawable.frames_b1);
        nback.add(R.drawable.frames_b2);
        nfore.clear();
        nfore.add(R.drawable.bird_f1);
        nfore.add(R.drawable.bird_f2);
        nfore.add(R.drawable.birthday_f1);
        nfore.add(R.drawable.birthday_f2);
        nfore.add(R.drawable.car_f1);
        nfore.add(R.drawable.car_f2);
        nfore.add(R.drawable.city_f1);
        nfore.add(R.drawable.city_f2);
        nfore.add(R.drawable.drip_f1);
        nfore.add(R.drawable.drip_f2);
        nfore.add(R.drawable.fantasy_f1);
        nfore.add(R.drawable.fantasy_f2);
        nfore.add(R.drawable.fire_f1);
        nfore.add(R.drawable.fire_f2);
        nfore.add(R.drawable.flower_f1);
        nfore.add(R.drawable.flower_f2);
        nfore.add(R.drawable.frames_f1);
        nfore.add(R.drawable.frames_f2);*/

       /* long seed = System.nanoTime();
        Collections.shuffle(fantasyThumb, new Random(seed));
        Collections.shuffle(fantasyBack, new Random(seed));
        Collections.shuffle(fantasyFore, new Random(seed));*/


        //  classAndWidgetIntialize(inflate);


        return this.myView;
    }

    private void classAndWidgetIntialize(View view) {

       /*FantasyframesAdapter fantasyframesAdapter = new FantasyframesAdapter(getActivity(), fantasyThumb,fantasyBack,fantasyFore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        fantasyCateRec.setLayoutManager(gridLayoutManager);
        fantasyCateRec.setHasFixedSize(true);
        fantasyCateRec.setAdapter(fantasyframesAdapter);*/

    }

    private void fantasyDataonly() {

       /* long seed = System.nanoTime();
        Collections.shuffle(fantasyThumb, new Random(seed));
        Collections.shuffle(fantasyBack, new Random(seed));
        Collections.shuffle(fantasyFore, new Random(seed));*/
       /* for (int i = 0; i < fantasyThumb.size(); i++) {

            img1 = fantasyThumb.get(i).toString();
            String backFrame = fantasyBack.get(i).toString();
            String foreFrame = fantasyFore.get(i).toString();

            if (img1.contains("https")) {
                String[] backpaths = backFrame.split("/");
                String[] forepaths = foreFrame.split("/");
                String getpath = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                        getContext().getExternalFilesDir(getContext().getResources().getString(R.string.app_name)).getAbsolutePath()
                        : Environment.getExternalStorageDirectory().toString() + "/" + getContext().getResources().getString(R.string.app_name) + "/Images";

                File backmediaStorageDir = null;
                File foremediaStorageDir = null;
                backmediaStorageDir = new File(getpath + "/" + backpaths[backpaths.length - 1]);
                foremediaStorageDir = new File(getpath + "/" + forepaths[forepaths.length - 1]);

                if (backmediaStorageDir.exists() && foremediaStorageDir.exists()) {
                    //adding the downloaded frames
                    downloadedlistFantasyThumb.add(fantasyThumb.get(i));
                    downloadedlistFantasyback.add(fantasyBack.get(i));
                    downloadedlistFantasyfore.add(fantasyFore.get(i));
                } else {
                    //adding the not downloaded frames
                    tobedownloadlistThumb.add(fantasyThumb.get(i));
                    tobedownloadlistback.add(fantasyBack.get(i));
                    tobedownloadlistfore.add(fantasyFore.get(i));

                }
            }
        }*/
        /*if ((downloadedlistFantasyThumb != null && downloadedlistFantasyThumb.size() > 0) ||
                (downloadedlistFantasyback != null && downloadedlistFantasyback.size() > 0) ||
                (downloadedlistFantasyfore != null && downloadedlistFantasyfore.size() > 0)) {
            downloadedlistFantasyThumb.addAll(tobedownloadlistThumb);
            downloadedlistFantasyback.addAll(tobedownloadlistback);
            downloadedlistFantasyfore.addAll(tobedownloadlistfore);

        }*/

        /*combinedarraylistThumb.addAll(beforekeydownloadedlistFantasyThumb);
        combinedarraylistback.addAll(beforekeydownloadedlistFantasyback);
        combinedarraylistfore.addAll(beforekeydownloadedlistFantasyfore);*/
        FantasyframesAdapter fantasyframesAdapter = new FantasyframesAdapter(getActivity(), combinedarraylistThumb, combinedarraylistback, combinedarraylistfore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        fantasyCateRec.setLayoutManager(gridLayoutManager);
        fantasyCateRec.setHasFixedSize(true);
        fantasyCateRec.setAdapter(fantasyframesAdapter);

    }

    private void fantasyDataWithAds() {
       /* long seed = System.nanoTime();
        Collections.shuffle(fantasyThumb, new Random(seed));
        Collections.shuffle(fantasyBack, new Random(seed));
        Collections.shuffle(fantasyFore, new Random(seed));*/


        /*combinedarraylistThumb.addAll(beforekeydownloadedlistFantasyThumb);
        combinedarraylistback.addAll(beforekeydownloadedlistFantasyback);
        combinedarraylistfore.addAll(beforekeydownloadedlistFantasyfore);*/

        if (combinedarraylistfore.size() > 0 && mNativeAds.size() > 0 && combinedarraylistThumb.size() > 2) {
            combinedarraylistThumb.add(2, mNativeAds.get(0));
            combinedarraylistfore.add(2, null);
            combinedarraylistback.add(2, null);
            System.out.println("XXX - 3");
        }
            /* if (natureFore.size() > 7 && mNativeAds.size() > 1) {
                 natureThumb.add(7, mNativeAds.get(1));
                 natureFore.add(7, null);
                 natureBack.add(7, null);
                 System.out.println("XXX - 4");
             }
             if (natureFore.size() > 12 && mNativeAds.size() > 2) {
                 natureThumb.add(12, mNativeAds.get(2));
                 natureFore.add(12, null);
                 natureBack.add(12, null);
                 System.out.println("XXX - 5");
             }*/

        if (fantasyFore.size() > 0) {
            //long seed = System.nanoTime();
            // Collections.shuffle(natureThumb, new Random(seed));
            // Collections.shuffle(natureBack, new Random(seed));
            // Collections.shuffle(natureFore, new Random(seed));

            FantasyframesAdapter fantasyframesAdapter = new FantasyframesAdapter(getActivity(), combinedarraylistThumb, combinedarraylistback, combinedarraylistfore);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (fantasyframesAdapter.getItemViewType(position)) {
                        case STRING:
                            return 1;
                        case NATIVE_UnifiedNativeAd:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
            fantasyCateRec.setLayoutManager(gridLayoutManager);
            fantasyCateRec.setHasFixedSize(true);
            fantasyCateRec.setAdapter(fantasyframesAdapter);


        }
    }

    public class FantasyframesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context mContext;
        //ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;

        ArrayList<Object> fantasyThumb;
        ArrayList<Object> fantasyBack;
        ArrayList<Object> fantasyFore;

        int rewardedIcon;

        public FantasyframesAdapter(Activity activity, ArrayList<Object> fantasyThumb, ArrayList<Object> fantasyBack, ArrayList<Object> fantasyFore) {
            this.mContext = activity;
            //   this.nthumb=nthumb;
            this.mInflater = LayoutInflater.from(activity);
            this.fantasyThumb = fantasyThumb;
            this.fantasyBack = fantasyBack;
            this.fantasyFore = fantasyFore;

        }

        @Override
        public int getItemViewType(int position) {

            Object recyclerViewItem = fantasyThumb.get(position);
            if (recyclerViewItem instanceof String) {
                return STRING;
            } else if (recyclerViewItem instanceof NativeAd) {
                return NATIVE_UnifiedNativeAd;
            }

            return STRING;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            switch (viewType) {

                case NATIVE_UnifiedNativeAd:
                    View unifiedNativeLayoutView = LayoutInflater.from(
                            parent.getContext()).inflate(R.layout.ad_unified_recycle,
                            parent, false);
                    return new NativeAdViewHolder(unifiedNativeLayoutView);
                case STRING:
                default:
                    return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.fantasy_layout, parent, false));

            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


            int viewType = getItemViewType(position);
            int oldfantacykeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newfantacykeycount;
            if (fantacykeycount > 0) {
                newfantacykeycount = oldfantacykeycount + fantacykeycount;

            } else {
                newfantacykeycount = oldfantacykeycount;
            }

            switch (viewType) {
                case NATIVE_UnifiedNativeAd:
                    NativeAd nativeAd = (NativeAd) fantasyThumb.get(position);
                    populateNativeAdViewRecycler(nativeAd, ((NativeAdViewHolder) holder).getAdView());
                    break;
                case STRING:
                    final ViewHolder holder1 = (ViewHolder) holder;
                    final String img1 = fantasyThumb.get(position).toString();
                    final String backFrame = fantasyBack.get(position).toString();
                    final String foreFrame = fantasyFore.get(position).toString();

                    if (img1.contains("https")) {
                        String[] backpaths = backFrame.split("/");
                        String[] forepaths = foreFrame.split("/");
                        String getpath = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                                mContext.getExternalFilesDir(mContext.getResources().getString(R.string.app_name)).getAbsolutePath()
                                : Environment.getExternalStorageDirectory().toString() + "/" + mContext.getResources().getString(R.string.app_name) + "/Images";

                        File backmediaStorageDir = null;
                        File foremediaStorageDir = null;
                        backmediaStorageDir = new File(getpath + "/" + backpaths[backpaths.length - 1]);
                        foremediaStorageDir = new File(getpath + "/" + forepaths[forepaths.length - 1]);

                       /* if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                            holder1.fantasyDown.setVisibility(View.VISIBLE);
                        } else {
                            holder1.fantasyDown.setVisibility(View.GONE);
                        }*/


                        rewardedIcon = position;
                        if (position > 2 && fantasyBack.get(2) == null && fantasyFore.get(2) == null) {
                            rewardedIcon = position - 1;
                        } else {
                            rewardedIcon = position;
                        }

                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                                if (rewardedIcon >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                                if (rewardedIcon >= newfantacykeycount) {

                                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                        holder1.fantasyDown.setVisibility(View.VISIBLE);
                                        holder1.fantasyDown.setImageResource(R.drawable.video);
                                    } else {
                                        holder1.fantasyDown.setVisibility(View.GONE);
                                    }


                                } else {

                                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                        holder1.fantasyDown.setVisibility(View.VISIBLE);
                                        holder1.fantasyDown.setImageResource(R.drawable.download);
                                    } else {
                                        holder1.fantasyDown.setVisibility(View.GONE);
                                    }


                                }

                            } else {

                                if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                    holder1.fantasyDown.setVisibility(View.VISIBLE);
                                    holder1.fantasyDown.setImageResource(R.drawable.download);
                                } else {
                                    holder1.fantasyDown.setVisibility(View.GONE);
                                }


                            }
                        } else {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder1.fantasyDown.setVisibility(View.VISIBLE);
                                holder1.fantasyDown.setImageResource(R.drawable.download);
                            } else {
                                holder1.fantasyDown.setVisibility(View.GONE);
                            }


                        }

                        Glide.with(mContext).load(img1)
                                .placeholder(R.drawable.loading_1)
                                .into(holder1.fantasyImg);
                    }
                    //  holder.fantasyRelative.setAnimation(a);


                    holder1.fantasyCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (holder1.fantasyDown.getVisibility() == View.VISIBLE) {
                                holder1.fantasyDown.setVisibility(View.GONE);
                            }


                            if (fantasyFragmentListener != null) {
                                fantasyFragmentListener.onFantasyViewItemClicked(fantasyBack.get(position).toString(), fantasyFore.get(position).toString(), position);
                            }

                        }
                    });


                    break;
            }


        }

        @Override
        public int getItemCount() {
            return fantasyThumb.size();
        }

        public int getImage(String imageName) {

            int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

            return drawableResourceId;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView fantasyImg;
            CardView fantasyCard;
            ImageView fantasyDown;
            RelativeLayout fantasyRelative;


            ViewHolder(View v) {
                super(v);
                fantasyImg = v.findViewById(R.id.fantasy_thumb);
                fantasyCard = v.findViewById(R.id.fantasy_card);
                fantasyDown = v.findViewById(R.id.fantasy_down);
                fantasyRelative = v.findViewById(R.id.fantasy_relative);

            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fantasyFragmentListener = (FantasyFragment.FantasyFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }


}
