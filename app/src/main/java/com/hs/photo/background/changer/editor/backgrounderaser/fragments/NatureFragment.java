package com.hs.photo.background.changer.editor.backgrounderaser.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.HomeActivity;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.SaveImageActivity;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.NatureframesAdapter;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NatureFragment extends Fragment {
    RecyclerView natureCateRec;
    NatureframesAdapter natureframesAdapter;
    /* ArrayList<Integer> nthumb=new ArrayList<>();
     ArrayList<Integer> nback=new ArrayList<>();
     ArrayList<Integer> nfore=new ArrayList<>();*/
    ArrayList<Object> natureThumb = new ArrayList<>();
    ArrayList<Object> natureBack = new ArrayList<>();
    ArrayList<Object> natureFore = new ArrayList<>();

    ArrayList<Object> beforeKeythumb = new ArrayList<>();
    ArrayList<Object> beforeKeyback = new ArrayList<>();
    ArrayList<Object> beforeKeyfore = new ArrayList<>();

    ArrayList<Object> downloadedlistThumb = new ArrayList<>();
    ArrayList<Object> downloadedlistback = new ArrayList<>();
    ArrayList<Object> downloadedlistfore = new ArrayList<>();

    ArrayList<Object> naturedownloadedlistThumb = new ArrayList<>();
    ArrayList<Object> naturedownloadedlistback = new ArrayList<>();
    ArrayList<Object> naturedownloadedlistfore = new ArrayList<>();

    ArrayList<Object> beforeKeydownloadedlistThumb = new ArrayList<>();
    ArrayList<Object> beforeKeydownloadedlistback = new ArrayList<>();
    ArrayList<Object> beforeKeydownloadedlistfore = new ArrayList<>();

    ArrayList<Object> tobedownloadlistThumb = new ArrayList<>();
    ArrayList<Object> tobedownloadlistback = new ArrayList<>();
    ArrayList<Object> tobedownloadlistfore = new ArrayList<>();

    ArrayList<Object> beforeKeytobedownloadlistThumb = new ArrayList<>();
    ArrayList<Object> beforeKeytobedownloadlistback = new ArrayList<>();
    ArrayList<Object> beforeKeytobedownloadlistfore = new ArrayList<>();

    ArrayList<Object> naturetobedownloadlistThumb = new ArrayList<>();
    ArrayList<Object> naturetobedownloadlistback = new ArrayList<>();
    ArrayList<Object> naturetobedownloadlistfore = new ArrayList<>();

    ArrayList<Object> combinedarraylistThumb = new ArrayList<>();
    ArrayList<Object> combinedarraylistback = new ArrayList<>();
    ArrayList<Object> combinedarraylistfore = new ArrayList<>();


    private FragmentListener mListener;
    private List<NativeAd> mNativeAds = new ArrayList<>();
    private AdLoader adLoader;
    boolean adLoaded;
    public static final int NUMBER_OF_AD_MOB_ADS = 1;
    static final int STRING = 404;
    static final int NATIVE_UnifiedNativeAd = 10519;
    String backFrame;
    String backFrame2;
    String foreFrame;
    String foreFrame2;
    String img1;
    String img2;
    public static int keycountnature = 0;

    public interface FragmentListener {
        void onRecyclerViewItemClicked(String back, String forei, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_nature, viewGroup, false);
        natureCateRec = (RecyclerView) inflate.findViewById(R.id.nature_rec);
        natureThumb.clear();
        natureFore.clear();
        natureBack.clear();
        beforeKeythumb.clear();
        beforeKeyback.clear();
        beforeKeyfore.clear();

        downloadedlistThumb.clear();
        downloadedlistback.clear();
        downloadedlistfore.clear();

        tobedownloadlistThumb.clear();
        tobedownloadlistback.clear();
        tobedownloadlistfore.clear();

        beforeKeydownloadedlistThumb.clear();
        beforeKeydownloadedlistback.clear();
        beforeKeydownloadedlistfore.clear();

        beforeKeytobedownloadlistThumb.clear();
        beforeKeytobedownloadlistback.clear();
        beforeKeytobedownloadlistfore.clear();

        naturetobedownloadlistThumb.clear();
        naturetobedownloadlistback.clear();
        naturetobedownloadlistfore.clear();

        naturedownloadedlistThumb.clear();
        naturedownloadedlistback.clear();
        naturedownloadedlistfore.clear();

        combinedarraylistThumb.clear();
        combinedarraylistback.clear();
        combinedarraylistfore.clear();
        /*for (int i=1; i<=2;i++){
            natureThumb.add("nature_thumb_"+i);
            natureBack.add("nature_back_"+i);
            natureFore.add("nature_fore_"+i);
        }*/
       /* natureThumb.add("nature_thumb_1");
        natureBack.add("nature_back_1");
        natureFore.add("nature_fore_1");*/

        if (RemoteConfigValues.getOurRemote().getNatureUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getNatureUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

                        for (int i = 2; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()); i++) {
                            beforeKeythumb.add(RemoteConfigValues.getOurRemote().getNatureUrl() + "nature_thumb_" + i + ".jpg");
                            beforeKeyback.add(RemoteConfigValues.getOurRemote().getNatureUrl() + "nature_back_" + i + ".png");
                            beforeKeyfore.add(RemoteConfigValues.getOurRemote().getNatureUrl() + "nature_fore_" + i + ".png");
                        }

                    }
                }
            }
        }

        if (RemoteConfigValues.getOurRemote().getNatureUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getNatureUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getNatureCount() != null) {
                    if (!RemoteConfigValues.getOurRemote().getNatureCount().equals("")) {
                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

                                for (int i = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()) + 1; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getNatureCount()); i++) {
                                    natureThumb.add(RemoteConfigValues.getOurRemote().getNatureUrl() + "nature_thumb_" + i + ".jpg");
                                    natureBack.add(RemoteConfigValues.getOurRemote().getNatureUrl() + "nature_back_" + i + ".png");
                                    natureFore.add(RemoteConfigValues.getOurRemote().getNatureUrl() + "nature_fore_" + i + ".png");
                                }

                            }
                        }
                    }
                }
            }
        }

        /*for (int i = 0; i < natureThumb.size(); i++) {
            img1 = natureThumb.get(i).toString();
            backFrame = natureBack.get(i).toString();
            foreFrame = natureFore.get(i).toString();

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
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    downloadedlistThumb.add(natureThumb.get(i));
                    downloadedlistback.add(natureBack.get(i));
                    downloadedlistfore.add(natureFore.get(i));
//                    }
                } else {
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    tobedownloadlistThumb.add(natureThumb.get(i));
                    tobedownloadlistback.add(natureThumb.get(i));
                    tobedownloadlistfore.add(natureThumb.get(i));
//                    }
                }

            }
        }*/
        long seed = System.nanoTime();

        Collections.shuffle(beforeKeythumb, new Random(seed));
        Collections.shuffle(beforeKeyback, new Random(seed));
        Collections.shuffle(beforeKeyfore, new Random(seed));

        Collections.shuffle(natureThumb, new Random(seed));
        Collections.shuffle(natureBack, new Random(seed));
        Collections.shuffle(natureFore, new Random(seed));

        //to check and seperate the frames that are downloaded in beforekeylist of frames
        for (int i = 0; i < beforeKeythumb.size(); i++) {
            img2 = beforeKeythumb.get(i).toString();
            backFrame2 = beforeKeyback.get(i).toString();
            foreFrame2 = beforeKeyfore.get(i).toString();


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
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    //adding the downloaded frames
                    beforeKeydownloadedlistThumb.add(beforeKeythumb.get(i));
                    beforeKeydownloadedlistback.add(beforeKeyback.get(i));
                    beforeKeydownloadedlistfore.add(beforeKeyfore.get(i));
//                    }
                } else {
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    //adding the not downloaded frames
                    beforeKeytobedownloadlistThumb.add(beforeKeythumb.get(i));
                    beforeKeytobedownloadlistback.add(beforeKeyback.get(i));
                    beforeKeytobedownloadlistfore.add(beforeKeyfore.get(i));

                }

            }
        }

        //to check and seperate the frames that are downloaded in natureframes list after key list
        for (int i = 0; i < natureThumb.size(); i++) {
            img1 = natureThumb.get(i).toString();
            backFrame = natureBack.get(i).toString();
            foreFrame = natureFore.get(i).toString();


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

                    naturedownloadedlistThumb.add(natureThumb.get(i));
                    naturedownloadedlistback.add(natureBack.get(i));
                    naturedownloadedlistfore.add(natureFore.get(i));
                    keycountnature = naturedownloadedlistThumb.size();
                    System.out.println(keycountnature);
                } else {
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    //adding the not downloaded frames
                    naturetobedownloadlistThumb.add(natureThumb.get(i));
                    naturetobedownloadlistback.add(natureBack.get(i));
                    naturetobedownloadlistfore.add(natureFore.get(i));

                }

            }
        }
        if ((beforeKeydownloadedlistThumb != null && beforeKeydownloadedlistThumb.size() > 0)
                || (naturedownloadedlistThumb != null && naturedownloadedlistThumb.size() > 0)) {
            if ((beforeKeydownloadedlistback != null && beforeKeydownloadedlistback.size() > 0)
                    || (naturedownloadedlistback != null && naturedownloadedlistback.size() > 0)) {
                if ((beforeKeydownloadedlistfore != null && beforeKeydownloadedlistfore.size() > 0)
                        || (naturedownloadedlistfore != null && naturedownloadedlistfore.size() > 0)) {
                    beforeKeydownloadedlistThumb.addAll(naturedownloadedlistThumb);
                    beforeKeydownloadedlistback.addAll(naturedownloadedlistback);
                    beforeKeydownloadedlistfore.addAll(naturedownloadedlistfore);
                }
            }
        }
        if ((beforeKeytobedownloadlistThumb != null && beforeKeytobedownloadlistThumb.size() > 0)
                || (naturetobedownloadlistThumb != null && naturetobedownloadlistThumb.size() > 0)) {
            if ((beforeKeytobedownloadlistback != null && beforeKeytobedownloadlistback.size() > 0)
                    || (naturetobedownloadlistback != null && naturetobedownloadlistback.size() > 0)) {
                if ((beforeKeytobedownloadlistfore != null && beforeKeytobedownloadlistfore.size() > 0)
                        || (naturetobedownloadlistfore != null && naturetobedownloadlistfore.size() > 0)) {

                    beforeKeytobedownloadlistThumb.addAll(naturetobedownloadlistThumb);
                    beforeKeytobedownloadlistback.addAll(naturetobedownloadlistback);
                    beforeKeytobedownloadlistfore.addAll(naturetobedownloadlistfore);
                }
            }
        }
        if ((beforeKeydownloadedlistThumb != null && beforeKeydownloadedlistThumb.size() > 0)
                || (beforeKeytobedownloadlistThumb != null && beforeKeytobedownloadlistThumb.size() > 0)) {
            if ((beforeKeydownloadedlistback != null && beforeKeydownloadedlistback.size() > 0)
                    || (beforeKeytobedownloadlistback != null && beforeKeytobedownloadlistback.size() > 0)) {
                if ((beforeKeydownloadedlistfore != null && beforeKeydownloadedlistfore.size() > 0)
                        || (beforeKeytobedownloadlistfore != null && beforeKeytobedownloadlistfore.size() > 0)) {

                    beforeKeydownloadedlistThumb.addAll(beforeKeytobedownloadlistThumb);
                    beforeKeydownloadedlistback.addAll(beforeKeytobedownloadlistback);
                    beforeKeydownloadedlistfore.addAll(beforeKeytobedownloadlistfore);


                    combinedarraylistThumb.addAll(beforeKeydownloadedlistThumb);
                    combinedarraylistback.addAll(beforeKeydownloadedlistback);
                    combinedarraylistfore.addAll(beforeKeydownloadedlistfore);
                }
            }
        }
        /*if (beforeKeythumb != null && beforeKeythumb.size() > 0) {

            for (int i = 0; i < beforeKeythumb.size(); i++) {

                natureThumb.add(i, beforeKeythumb.get(i));
                natureBack.add(i, beforeKeyback.get(i));
                natureFore.add(i, beforeKeyfore.get(i));

            }


        }*/
        HomeActivity activity = (HomeActivity) getActivity();
        if (activity.getIsadloaded()) {

            mNativeAds = activity.getRecyclerViewItems();
            natureDataWithAds();

        } else {
            natureDataonly();
        }

       /* if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                admobAd();
            } else {
                adLoaded = true;
                prepareNatureFrameList();
                System.out.println("XXX - a");
            }
        } else {
            adLoaded = true;
            prepareNatureFrameList();
            System.out.println("XXX - b");
        }*/
        // admobAd();

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


        return inflate;
    }

    private void natureDataWithAds() {

        //add ad to cautionList

       /*  long seed = System.nanoTime();
         Collections.shuffle(natureThumb, new Random(seed));
         Collections.shuffle(natureBack, new Random(seed));
         Collections.shuffle(natureFore, new Random(seed));*/

        /*natureThumb.add(0, "nature_thumb_1");
        natureBack.add(0, "nature_back_1");
        natureFore.add(0, "nature_fore_1");*/
        combinedarraylistThumb.add(0, "nature_thumb_1");
        combinedarraylistback.add(0, "nature_back_1");
        combinedarraylistfore.add(0, "nature_fore_1");
        if (combinedarraylistfore.size() > 0 && mNativeAds.size() > 0 && combinedarraylistThumb.size() > 6) {
            combinedarraylistThumb.add(6, mNativeAds.get(0));
            combinedarraylistfore.add(6, null);
            combinedarraylistback.add(6, null);
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

//        if (natureFore.size() > 0) {
        if (combinedarraylistfore.size() > 0) {

            natureframesAdapter = new NatureframesAdapter(getActivity(), combinedarraylistThumb, combinedarraylistback, combinedarraylistfore);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (natureframesAdapter.getItemViewType(position)) {
                        case STRING:
                            return 1;
                        case NATIVE_UnifiedNativeAd:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
            natureCateRec.setLayoutManager(gridLayoutManager);
            natureCateRec.setHasFixedSize(true);
            natureCateRec.setAdapter(natureframesAdapter);
            natureframesAdapter.setListener(new NatureframesAdapter.NatAdapterListener() {
                @Override
                public void onNatItemClicked(String back, String forei, int position) {
                    mListener.onRecyclerViewItemClicked(back, forei, position);

                }
            });


        }
    }

    private void natureDataonly() {

        /* long seed = System.nanoTime();
         Collections.shuffle(natureThumb, new Random(seed));
         Collections.shuffle(natureBack, new Random(seed));
         Collections.shuffle(natureFore, new Random(seed));*/

//        natureThumb.add(0, "nature_thumb_1");
//        natureBack.add(0, "nature_back_1");
//        natureFore.add(0, "nature_fore_1");

        /*for (int i = 0; i < natureThumb.size(); i++) {
            img1 = natureThumb.get(i).toString();
            backFrame = natureBack.get(i).toString();
            foreFrame = natureFore.get(i).toString();

            //to check and seperate the frames that are downloaded
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
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    //adding the downloaded frames
                    downloadedlistThumb.add(natureThumb.get(i));
                    downloadedlistback.add(natureBack.get(i));
                    downloadedlistfore.add(natureFore.get(i));
//                    }
                } else {
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    //adding the not downloaded frames
                    tobedownloadlistThumb.add(natureThumb.get(i));
                    tobedownloadlistback.add(natureBack.get(i));
                    tobedownloadlistfore.add(natureFore.get(i));

                }

            }
        }*/
        // Combining the both downloaded and tobedownloaded arraylist

        /*if ((downloadedlistThumb != null && downloadedlistThumb.size() > 0) || (tobedownloadlistThumb != null && tobedownloadlistThumb.size() > 0)) {

            downloadedlistThumb.addAll(tobedownloadlistThumb);
            downloadedlistback.addAll(tobedownloadlistback);
            downloadedlistfore.addAll(tobedownloadlistfore);

            System.out.println(downloadedlistThumb);
            System.out.println(downloadedlistback);
            System.out.println(downloadedlistfore);
            // adding the value at positoon 0 of arraylist and merging the above resulted list to it .
            combinedarraylistThumb.add(0, "nature_thumb_1");
            combinedarraylistback.add(0, "nature_back_1");
            combinedarraylistfore.add(0, "nature_fore_1");

            if (combinedarraylistThumb != null && combinedarraylistback != null && combinedarraylistfore != null) {
                combinedarraylistThumb.addAll(downloadedlistThumb);
                combinedarraylistback.addAll(downloadedlistback);
                combinedarraylistfore.addAll(downloadedlistfore);
            }

        }*/
        combinedarraylistThumb.add(0, "nature_thumb_1");
        combinedarraylistback.add(0, "nature_back_1");
        combinedarraylistfore.add(0, "nature_fore_1");
//        if ((beforeKeydownloadedlistThumb != null && beforeKeydownloadedlistThumb.size() > 0)){
//            combinedarraylistThumb.addAll(beforeKeydownloadedlistThumb);
//            combinedarraylistback.addAll(beforeKeydownloadedlistback);
//            combinedarraylistfore.addAll(beforeKeydownloadedlistfore);
//        }

        natureframesAdapter = new NatureframesAdapter(getActivity(), combinedarraylistThumb, combinedarraylistback, combinedarraylistfore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        natureCateRec.setLayoutManager(gridLayoutManager);
        natureCateRec.setHasFixedSize(true);
        natureCateRec.setAdapter(natureframesAdapter);
        natureframesAdapter.setListener(new NatureframesAdapter.NatAdapterListener() {
            @Override
            public void onNatItemClicked(String back, String forei, int position) {
                mListener.onRecyclerViewItemClicked(back, forei, position);

            }
        });

    }

    private void prepareNatureFrameList() {


        if (adLoaded) {


            //add ad to cautionList
            if (natureFore.size() > 0 && mNativeAds.size() > 0 && natureThumb.size() > 2) {
                natureThumb.add(2, mNativeAds.get(0));
                natureFore.add(2, null);
                natureBack.add(2, null);
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

            if (natureFore.size() > 0) {
                //long seed = System.nanoTime();
                // Collections.shuffle(natureThumb, new Random(seed));
                // Collections.shuffle(natureBack, new Random(seed));
                // Collections.shuffle(natureFore, new Random(seed));

                natureframesAdapter = new NatureframesAdapter(getActivity(), natureThumb, natureBack, natureFore);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        switch (natureframesAdapter.getItemViewType(position)) {
                            case STRING:
                                return 1;
                            case NATIVE_UnifiedNativeAd:
                                return 2;
                            default:
                                return -1;
                        }
                    }
                });
                natureCateRec.setLayoutManager(gridLayoutManager);
                natureCateRec.setHasFixedSize(true);
                natureCateRec.setAdapter(natureframesAdapter);
                natureframesAdapter.setListener(new NatureframesAdapter.NatAdapterListener() {
                    @Override
                    public void onNatItemClicked(String back, String forei, int position) {
                        mListener.onRecyclerViewItemClicked(back, forei, position);

                    }
                });

            }
        } else {
            long seed = System.nanoTime();
            Collections.shuffle(natureThumb, new Random(seed));
            Collections.shuffle(natureBack, new Random(seed));
            Collections.shuffle(natureFore, new Random(seed));

            natureframesAdapter = new NatureframesAdapter(getActivity(), natureThumb, natureBack, natureFore);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
            natureCateRec.setLayoutManager(gridLayoutManager);
            natureCateRec.setHasFixedSize(true);
            natureCateRec.setAdapter(natureframesAdapter);
            natureframesAdapter.setListener(new NatureframesAdapter.NatAdapterListener() {
                @Override
                public void onNatItemClicked(String back, String forei, int position) {
                    mListener.onRecyclerViewItemClicked(back, forei, position);

                }
            });
        }

    }

  /*  private void admobAd() {

        mNativeAds.clear();
        AdLoader.Builder builder = new AdLoader.Builder(getActivity(), getString(R.string.admob_native_id));
        adLoader = builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {

                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                        mNativeAds.add(nativeAd);
                        if (!adLoader.isLoading()) {
                            adLoaded = true;
                            System.out.println("XXX - c");
                            prepareNatureFrameList();
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
                    adLoaded = true;
                    System.out.println("XXX - d");
                    prepareNatureFrameList();

                }
                System.out.println("XXX" + "loaded");

            }
            }).build();


        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_AD_MOB_ADS);

    }*/


}
