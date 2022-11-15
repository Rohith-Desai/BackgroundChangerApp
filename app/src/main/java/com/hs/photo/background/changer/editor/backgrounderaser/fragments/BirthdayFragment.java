package com.hs.photo.background.changer.editor.backgrounderaser.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.google.android.gms.ads.nativead.NativeAd;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.HomeActivity;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.util.NativeAdViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.populateNativeAdViewRecycler;

public class BirthdayFragment extends Fragment {

    RecyclerView birthdayCateRec;

   /* ArrayList<Integer> nthumb=new ArrayList<>();
    ArrayList<Integer> nback=new ArrayList<>();
    ArrayList<Integer> nfore=new ArrayList<>();*/

    ArrayList<Object> birthdayThumb = new ArrayList<>();
    ArrayList<Object> birthdayBack = new ArrayList<>();
    ArrayList<Object> birthdayFore = new ArrayList<>();

    ArrayList<Object> beforeKeythumb = new ArrayList<>();
    ArrayList<Object> beforeKeyback = new ArrayList<>();
    ArrayList<Object> beforeKeyfore = new ArrayList<>();

    ArrayList<Object> downloadedThumb = new ArrayList<>();
    ArrayList<Object> downloadedBack = new ArrayList<>();
    ArrayList<Object> downloadedFore = new ArrayList<>();

    ArrayList<Object> tobedownloadedThumb = new ArrayList<>();
    ArrayList<Object> tobedownloadedBack = new ArrayList<>();
    ArrayList<Object> tobedownloadedFore = new ArrayList<>();

    ArrayList<Object> beforeKeydownloadthumb = new ArrayList<>();
    ArrayList<Object> beforeKeydownloadback = new ArrayList<>();
    ArrayList<Object> beforeKeydownloadfore = new ArrayList<>();

    ArrayList<Object> beforeKeytobedownloadthumb = new ArrayList<>();
    ArrayList<Object> beforeKeytobedownloadback = new ArrayList<>();
    ArrayList<Object> beforeKeytobedownoladfore = new ArrayList<>();

    String backFrame, backFrame2;
    String foreFrame, foreFrame2;
    String img1, img2;
    public static int keycountbithday = 0;
    private BirthdayFragmentListener birthdayFragmentListener;
    private View myView;
    private List<NativeAd> mNativeAds = new ArrayList<>();
    static final int STRING = 404;
    static final int NATIVE_UnifiedNativeAd = 10519;

    public interface BirthdayFragmentListener {
        void onBirthdayViewItemClicked(String back, String forei, int position);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_birthday, viewGroup, false);
        birthdayCateRec = (RecyclerView) inflate.findViewById(R.id.birthday_rec);

        birthdayThumb.clear();
        birthdayBack.clear();
        birthdayFore.clear();

        beforeKeythumb.clear();
        beforeKeyback.clear();
        beforeKeyfore.clear();

        downloadedThumb.clear();
        downloadedBack.clear();
        downloadedFore.clear();

        tobedownloadedThumb.clear();
        tobedownloadedBack.clear();
        tobedownloadedFore.clear();

        beforeKeydownloadthumb.clear();
        beforeKeydownloadback.clear();
        beforeKeydownloadfore.clear();

        beforeKeytobedownloadthumb.clear();
        beforeKeytobedownloadback.clear();
        beforeKeytobedownoladfore.clear();

       /* for (int i=1; i<=2;i++){
            birthdayThumb.add("birthday_thumb_"+i);
            birthdayBack.add("birthday_back_"+i);
            birthdayFore.add("birthday_fore_"+i);
        }*/

        if (RemoteConfigValues.getOurRemote().getBirthdayUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getBirthdayUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {
                        for (int i = 1; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()); i++) {
                            beforeKeythumb.add(RemoteConfigValues.getOurRemote().getBirthdayUrl() + "birthday_thumb_" + i + ".jpg");
                            beforeKeyback.add(RemoteConfigValues.getOurRemote().getBirthdayUrl() + "birthday_back_" + i + ".png");
                            beforeKeyfore.add(RemoteConfigValues.getOurRemote().getBirthdayUrl() + "birthday_fore_" + i + ".png");
                        }

                    }
                }
            }
        }


        if (RemoteConfigValues.getOurRemote().getBirthdayUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getBirthdayUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getBirthdayCount() != null) {
                    if (!RemoteConfigValues.getOurRemote().getBirthdayCount().equals("")) {
                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {
                                for (int i = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()) + 1; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getBirthdayCount()); i++) {
                                    birthdayThumb.add(RemoteConfigValues.getOurRemote().getBirthdayUrl() + "birthday_thumb_" + i + ".jpg");
                                    birthdayBack.add(RemoteConfigValues.getOurRemote().getBirthdayUrl() + "birthday_back_" + i + ".png");
                                    birthdayFore.add(RemoteConfigValues.getOurRemote().getBirthdayUrl() + "birthday_fore_" + i + ".png");
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

        Collections.shuffle(birthdayThumb, new Random(seed));
        Collections.shuffle(birthdayBack, new Random(seed));
        Collections.shuffle(birthdayFore, new Random(seed));
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
                    beforeKeydownloadthumb.add(beforeKeythumb.get(i));
                    beforeKeydownloadback.add(beforeKeyback.get(i));
                    beforeKeydownloadfore.add(beforeKeyfore.get(i));
                } else {
                    //adding the not downloaded frames
                    beforeKeytobedownloadthumb.add(beforeKeythumb.get(i));
                    beforeKeytobedownloadback.add(beforeKeyback.get(i));
                    beforeKeytobedownoladfore.add(beforeKeyfore.get(i));

                }
            }
        }
        //to check and seperate the frames that are downloaded in natureframes list after key list
        for (int i = 0; i < birthdayThumb.size(); i++) {
            img1 = birthdayThumb.get(i).toString();
            backFrame = birthdayBack.get(i).toString();
            foreFrame = birthdayFore.get(i).toString();


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

                    downloadedThumb.add(birthdayThumb.get(i));
                    downloadedBack.add(birthdayBack.get(i));
                    downloadedFore.add(birthdayFore.get(i));

                    System.out.println(downloadedThumb.size());
                    keycountbithday = downloadedThumb.size();
                    System.out.println(keycountbithday);
                } else {
                    //adding the not downloaded frames
                    tobedownloadedThumb.add(birthdayThumb.get(i));
                    tobedownloadedBack.add(birthdayBack.get(i));
                    tobedownloadedFore.add(birthdayFore.get(i));
                }
            }
        }
        if ((beforeKeydownloadthumb != null && beforeKeydownloadthumb.size() > 0)
                || (downloadedThumb != null && downloadedBack.size() > 0)) {
            if ((beforeKeydownloadback != null && beforeKeydownloadback.size() > 0)
                    || (downloadedBack != null && downloadedBack.size() > 0)) {
                if ((beforeKeydownloadfore != null && beforeKeydownloadfore.size() > 0)
                        || (downloadedFore != null && downloadedFore.size() > 0)) {
                    beforeKeydownloadthumb.addAll(downloadedThumb);
                    beforeKeydownloadback.addAll(downloadedBack);
                    beforeKeydownloadfore.addAll(downloadedFore);

                }
            }
        }


        if ((beforeKeytobedownloadthumb != null && beforeKeytobedownloadthumb.size() > 0) ||
                (tobedownloadedThumb != null && tobedownloadedThumb.size() > 0)) {
            if ((beforeKeytobedownloadback != null && beforeKeytobedownloadback.size() > 0) ||
                    (tobedownloadedBack != null && tobedownloadedBack.size() > 0)) {
                if ((beforeKeytobedownoladfore != null && beforeKeytobedownoladfore.size() > 0) ||
                        (tobedownloadedFore != null && tobedownloadedFore.size() > 0)) {
                    beforeKeytobedownloadthumb.addAll(tobedownloadedThumb);
                    beforeKeytobedownloadback.addAll(tobedownloadedBack);
                    beforeKeytobedownoladfore.addAll(tobedownloadedFore);
                }
            }
        }


        if ((beforeKeydownloadthumb != null && beforeKeydownloadthumb.size() > 0)
                || (beforeKeytobedownloadthumb != null && beforeKeytobedownloadthumb.size() > 0)) {
            if ((beforeKeydownloadback != null && beforeKeydownloadback.size() > 0)
                    || (beforeKeytobedownloadback != null && beforeKeytobedownloadback.size() > 0)) {
                if ((beforeKeydownloadfore != null && beforeKeydownloadfore.size() > 0)
                        || (beforeKeytobedownoladfore != null && beforeKeytobedownoladfore.size() > 0)) {

                    beforeKeydownloadthumb.addAll(beforeKeytobedownloadthumb);
                    beforeKeydownloadback.addAll(beforeKeytobedownloadback);
                    beforeKeydownloadfore.addAll(beforeKeytobedownoladfore);
                }
            }
        }
        /*if (beforeKeythumb!=null && beforeKeythumb.size()>0){

            for (int i=0;i<beforeKeythumb.size();i++){

                birthdayThumb.add(i,beforeKeythumb.get(i));
                birthdayBack.add(i,beforeKeyback.get(i));
                birthdayFore.add(i,beforeKeyfore.get(i));

            }

        }*/


        HomeActivity activity = (HomeActivity) getActivity();
        if (activity.getbdayadloaded()) {

            mNativeAds = activity.getRecyclerViewItems();
            birthdayDataWithAds();

        } else {
            birthdayDataonly();
        }
        /*nthumb.clear();
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
        Collections.shuffle(birthdayThumb, new Random(seed));
        Collections.shuffle(birthdayBack, new Random(seed));
        Collections.shuffle(birthdayFore, new Random(seed));*/

        // classAndWidgetIntialize();
        return inflate;
    }

    private void classAndWidgetIntialize() {

      /* BirthdayframesAdapter birthdayframesAdapter = new BirthdayframesAdapter(getActivity(), birthdayThumb,birthdayBack,birthdayFore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        birthdayCateRec.setLayoutManager(gridLayoutManager);
        birthdayCateRec.setHasFixedSize(true);
        birthdayCateRec.setAdapter(birthdayframesAdapter);*/

    }

    private void birthdayDataonly() {
       /* long seed = System.nanoTime();
        Collections.shuffle(birthdayThumb, new Random(seed));
        Collections.shuffle(birthdayBack, new Random(seed));
        Collections.shuffle(birthdayFore, new Random(seed));*/
        BirthdayframesAdapter birthdayframesAdapter = new BirthdayframesAdapter(getActivity(), beforeKeydownloadthumb, beforeKeydownloadback, beforeKeydownloadfore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        birthdayCateRec.setLayoutManager(gridLayoutManager);
        birthdayCateRec.setHasFixedSize(true);
        birthdayCateRec.setAdapter(birthdayframesAdapter);

    }

    private void birthdayDataWithAds() {

       /* long seed = System.nanoTime();
        Collections.shuffle(birthdayThumb, new Random(seed));
        Collections.shuffle(birthdayBack, new Random(seed));
        Collections.shuffle(birthdayFore, new Random(seed));*/

        /*if (birthdayFore.size() > 0 && mNativeAds.size() > 0 && birthdayThumb.size() > 2) {
            birthdayThumb.add(2, mNativeAds.get(0));
            birthdayFore.add(2, null);
            birthdayBack.add(2, null);
            System.out.println("XXX - 3");
        }*/
        if (beforeKeydownloadthumb.size() > 0 && mNativeAds.size() > 0 && beforeKeydownloadthumb.size() > 2) {
            beforeKeydownloadthumb.add(2, mNativeAds.get(0));
            beforeKeydownloadback.add(2, null);
            beforeKeydownloadfore.add(2, null);
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

        if (birthdayFore.size() > 0) {
            //long seed = System.nanoTime();
            // Collections.shuffle(natureThumb, new Random(seed));
            // Collections.shuffle(natureBack, new Random(seed));
            // Collections.shuffle(natureFore, new Random(seed));

            BirthdayframesAdapter birthdayframesAdapte = new BirthdayframesAdapter(getActivity(), beforeKeydownloadthumb, beforeKeydownloadback, beforeKeydownloadfore);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (birthdayframesAdapte.getItemViewType(position)) {
                        case STRING:
                            return 1;
                        case NATIVE_UnifiedNativeAd:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
            birthdayCateRec.setLayoutManager(gridLayoutManager);
            birthdayCateRec.setHasFixedSize(true);
            birthdayCateRec.setAdapter(birthdayframesAdapte);


        }


    }

    public class BirthdayframesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context mContext;
        //ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;
        ArrayList<Object> birthdayThumb;
        ArrayList<Object> birthdayBack;
        ArrayList<Object> birthdayFore;

        int rewardedIcon;


        public BirthdayframesAdapter(Activity activity, ArrayList<Object> birthdayThumb, ArrayList<Object> birthdayBack, ArrayList<Object> birthdayFore) {
            this.mContext = activity;
            // this.nthumb=nthumb;
            this.mInflater = LayoutInflater.from(activity);
            this.birthdayThumb = birthdayThumb;
            this.birthdayBack = birthdayBack;
            this.birthdayFore = birthdayFore;

        }

        @Override
        public int getItemViewType(int position) {

            Object recyclerViewItem = birthdayThumb.get(position);
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
                    return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.birthday_layout, parent, false));

            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            // holder.birthdayImg.setImageResource(nthumb.get(position));

            int viewType = getItemViewType(position);
            //to set the count key position after checking the downloaded frames
            int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newkeycount;
            if (keycountbithday > 0) {
                newkeycount = oldkeycount + keycountbithday;

            } else {
                newkeycount = oldkeycount;
            }
            switch (viewType) {
                case NATIVE_UnifiedNativeAd:
                    NativeAd nativeAd = (NativeAd) birthdayThumb.get(position);
                    populateNativeAdViewRecycler(nativeAd, ((NativeAdViewHolder) holder).getAdView());
                    break;
                case STRING:
                    final ViewHolder holder1 = (ViewHolder) holder;

                    final String img1 = birthdayThumb.get(position).toString();
                    final String backFrame = birthdayBack.get(position).toString();
                    final String foreFrame = birthdayFore.get(position).toString();

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
                            holder1.birthdayDown.setVisibility(View.VISIBLE);
                        } else {
                            holder1.birthdayDown.setVisibility(View.GONE);
                        }*/

                        rewardedIcon = position;
                        if (position > 2 && birthdayBack.get(2) == null && birthdayFore.get(2) == null) {
                            rewardedIcon = position - 1;
                        } else {
                            rewardedIcon = position;
                        }

                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                                if (rewardedIcon >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                                if (rewardedIcon >= newkeycount) {

                                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                        holder1.birthdayDown.setVisibility(View.VISIBLE);
                                        holder1.birthdayDown.setImageResource(R.drawable.video);
                                    } else {
                                        holder1.birthdayDown.setVisibility(View.GONE);
                                    }


                                } else {

                                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                        holder1.birthdayDown.setVisibility(View.VISIBLE);
                                        holder1.birthdayDown.setImageResource(R.drawable.download);
                                    } else {
                                        holder1.birthdayDown.setVisibility(View.GONE);
                                    }


                                }

                            } else {

                                if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                    holder1.birthdayDown.setVisibility(View.VISIBLE);
                                    holder1.birthdayDown.setImageResource(R.drawable.download);
                                } else {
                                    holder1.birthdayDown.setVisibility(View.GONE);
                                }


                            }
                        } else {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder1.birthdayDown.setVisibility(View.VISIBLE);
                                holder1.birthdayDown.setImageResource(R.drawable.download);
                            } else {
                                holder1.birthdayDown.setVisibility(View.GONE);
                            }


                        }


                        Glide.with(mContext).load(img1)
                                .placeholder(R.drawable.loading_1)
                                .into(holder1.birthdayImg);
                    }


                    // holder.birthdayRelative.setAnimation(a);

                    holder1.birthdayCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (holder1.birthdayDown.getVisibility() == View.VISIBLE) {
                                holder1.birthdayDown.setVisibility(View.GONE);
                            }


                            if (birthdayFragmentListener != null) {
                                birthdayFragmentListener.onBirthdayViewItemClicked(birthdayBack.get(position).toString(), birthdayFore.get(position).toString(), position);
                            }

                        }
                    });


                    break;
            }


        }

        @Override
        public int getItemCount() {
            return birthdayThumb.size();
        }

        public int getImage(String imageName) {

            int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

            return drawableResourceId;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView birthdayImg;
            CardView birthdayCard;
            ImageView birthdayDown;
            RelativeLayout birthdayRelative;


            ViewHolder(View v) {
                super(v);
                birthdayImg = v.findViewById(R.id.birthday_thumb);
                birthdayCard = v.findViewById(R.id.birthday_card);
                birthdayDown = v.findViewById(R.id.birthday_down);
                birthdayRelative = v.findViewById(R.id.birthday_relative);

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            birthdayFragmentListener = (BirthdayFragment.BirthdayFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }
}
