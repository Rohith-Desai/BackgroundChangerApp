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

public class FlowerFragment extends Fragment {

    RecyclerView flowerCateRec;

    /* ArrayList<Integer> nthumb=new ArrayList<>();
     ArrayList<Integer> nback=new ArrayList<>();
     ArrayList<Integer> nfore=new ArrayList<>();*/
    ArrayList<Object> flowerThumb = new ArrayList<>();
    ArrayList<Object> flowerBack = new ArrayList<>();
    ArrayList<Object> flowerFore = new ArrayList<>();

    ArrayList<Object> downloadedflowerThumb = new ArrayList<>();
    ArrayList<Object> downloadedflowerBack = new ArrayList<>();
    ArrayList<Object> downloadedflowerFore = new ArrayList<>();

    ArrayList<Object> tobedownloadedflowerThumb = new ArrayList<>();
    ArrayList<Object> tobedownloadedflowerBack = new ArrayList<>();
    ArrayList<Object> tobedownloadedflowerFore = new ArrayList<>();

    ArrayList<Object> beforeKeythumb = new ArrayList<>();
    ArrayList<Object> beforeKeyback = new ArrayList<>();
    ArrayList<Object> beforeKeyfore = new ArrayList<>();

    ArrayList<Object> beforeKeydownloadthumb = new ArrayList<>();
    ArrayList<Object> beforeKeydownloadback = new ArrayList<>();
    ArrayList<Object> beforeKeydownloadfore = new ArrayList<>();

    ArrayList<Object> beforeKeytobedownloadthumb = new ArrayList<>();
    ArrayList<Object> beforeKeytobedownloadback = new ArrayList<>();
    ArrayList<Object> beforeKeytobedownoladfore = new ArrayList<>();

    String backFrame, backFrame2;
    String foreFrame, foreFrame2;
    String img1, img2;
    public static int flowerkeycount = 0;
    private FlowerFragmentListener flowerFragmentListener;
    private View myView;
    private List<NativeAd> mNativeAds = new ArrayList<>();
    static final int STRING = 404;
    static final int NATIVE_UnifiedNativeAd = 10519;

    public interface FlowerFragmentListener {
        void onFlowerViewItemClicked(String back, String forei, int poaition);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_flower, viewGroup, false);
        this.myView = inflate;
        flowerCateRec = (RecyclerView) inflate.findViewById(R.id.flower_rec);

        flowerThumb.clear();
        flowerBack.clear();
        flowerFore.clear();

        beforeKeythumb.clear();
        beforeKeyback.clear();
        beforeKeyfore.clear();

        beforeKeydownloadthumb.clear();
        beforeKeydownloadback.clear();
        beforeKeydownloadfore.clear();

        beforeKeytobedownloadthumb.clear();
        beforeKeytobedownloadback.clear();
        beforeKeytobedownoladfore.clear();

        downloadedflowerThumb.clear();
        downloadedflowerBack.clear();
        downloadedflowerFore.clear();

        tobedownloadedflowerThumb.clear();
        tobedownloadedflowerBack.clear();
        tobedownloadedflowerFore.clear();

       /* for (int i=1; i<=2;i++){
            flowerThumb.add("flower_thumb_"+i);
            flowerBack.add("flower_back_"+i);
            flowerFore.add("flower_fore_"+i);
        }*/


        if (RemoteConfigValues.getOurRemote().getFlowerUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getFlowerUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {
                        for (int i = 1; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()); i++) {
                            beforeKeythumb.add(RemoteConfigValues.getOurRemote().getFlowerUrl() + "flower_thumb_" + i + ".jpg");
                            beforeKeyback.add(RemoteConfigValues.getOurRemote().getFlowerUrl() + "flower_back_" + i + ".png");
                            beforeKeyfore.add(RemoteConfigValues.getOurRemote().getFlowerUrl() + "flower_fore_" + i + ".png");
                        }

                    }
                }
            }
        }


        if (RemoteConfigValues.getOurRemote().getFlowerUrl() != null) {
            if (!RemoteConfigValues.getOurRemote().getFlowerUrl().equals("")) {
                if (RemoteConfigValues.getOurRemote().getFlowerCount() != null) {
                    if (!RemoteConfigValues.getOurRemote().getFlowerCount().equals("")) {
                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {
                                for (int i = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter()) + 1; i <= Integer.parseInt(RemoteConfigValues.getOurRemote().getFlowerCount()); i++) {
                                    flowerThumb.add(RemoteConfigValues.getOurRemote().getFlowerUrl() + "flower_thumb_" + i + ".jpg");
                                    flowerBack.add(RemoteConfigValues.getOurRemote().getFlowerUrl() + "flower_back_" + i + ".png");
                                    flowerFore.add(RemoteConfigValues.getOurRemote().getFlowerUrl() + "flower_fore_" + i + ".png");
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

        Collections.shuffle(flowerThumb, new Random(seed));
        Collections.shuffle(flowerBack, new Random(seed));
        Collections.shuffle(flowerFore, new Random(seed));


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
        for (int i = 0; i < flowerThumb.size(); i++) {
            img1 = flowerThumb.get(i).toString();
            backFrame = flowerBack.get(i).toString();
            foreFrame = flowerFore.get(i).toString();


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

                    downloadedflowerThumb.add(flowerThumb.get(i));
                    downloadedflowerBack.add(flowerBack.get(i));
                    downloadedflowerFore.add(flowerFore.get(i));

                    System.out.println(downloadedflowerThumb.size());
                    flowerkeycount = downloadedflowerThumb.size();
                    System.out.println(flowerkeycount);
                } else {
                    //adding the not downloaded frames
                    tobedownloadedflowerThumb.add(flowerThumb.get(i));
                    tobedownloadedflowerBack.add(flowerBack.get(i));
                    tobedownloadedflowerFore.add(flowerFore.get(i));

                }
            }
        }
        if ((beforeKeydownloadthumb != null && beforeKeydownloadthumb.size() > 0)
                || (downloadedflowerThumb != null && downloadedflowerThumb.size() > 0)) {
            if ((beforeKeydownloadback != null && beforeKeydownloadback.size() > 0)
                    || (downloadedflowerBack != null && downloadedflowerBack.size() > 0)) {
                if ((beforeKeydownloadfore != null && beforeKeydownloadfore.size() > 0)
                        || (downloadedflowerFore != null && downloadedflowerFore.size() > 0)) {
                    beforeKeydownloadthumb.addAll(downloadedflowerThumb);
                    beforeKeydownloadback.addAll(downloadedflowerBack);
                    beforeKeydownloadfore.addAll(downloadedflowerFore);

                }
            }
        }

        if ((beforeKeytobedownloadthumb != null && beforeKeytobedownloadthumb.size() > 0) ||
                (tobedownloadedflowerThumb != null && tobedownloadedflowerThumb.size() > 0)) {
            if ((beforeKeytobedownloadback != null && beforeKeytobedownloadback.size() > 0) ||
                    (tobedownloadedflowerBack != null && tobedownloadedflowerBack.size() > 0)) {
                if ((beforeKeytobedownoladfore != null && beforeKeytobedownoladfore.size() > 0) ||
                        (tobedownloadedflowerFore != null && tobedownloadedflowerFore.size() > 0)) {
                    beforeKeytobedownloadthumb.addAll(tobedownloadedflowerThumb);
                    beforeKeytobedownloadback.addAll(tobedownloadedflowerBack);
                    beforeKeytobedownoladfore.addAll(tobedownloadedflowerFore);
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
     /*if (beforeKeythumb != null && beforeKeythumb.size() > 0) {

            for (int i = 0; i < beforeKeythumb.size(); i++) {

                flowerThumb.add(i, beforeKeythumb.get(i));
                flowerBack.add(i, beforeKeyback.get(i));
                flowerFore.add(i, beforeKeyfore.get(i));

            }

        }*/

        HomeActivity activity = (HomeActivity) getActivity();
        if (activity.getfloweradloaded()) {

            mNativeAds = activity.getRecyclerViewItems();
            flowerDataWithAds();

        } else {
            flowerDataonly();
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
        Collections.shuffle(flowerThumb, new Random(seed));
        Collections.shuffle(flowerBack, new Random(seed));
        Collections.shuffle(flowerFore, new Random(seed));*/

        //  classAndWidgetIntialize(inflate);

        return this.myView;
    }

    private void classAndWidgetIntialize(View view) {

       /* FlowerframesAdapter flowerframesAdapter = new FlowerframesAdapter(getActivity(), flowerThumb,flowerBack,flowerFore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        flowerCateRec.setLayoutManager(gridLayoutManager);
        flowerCateRec.setHasFixedSize(true);
        flowerCateRec.setAdapter(flowerframesAdapter);*/

    }

    private void flowerDataonly() {
       /* long seed = System.nanoTime();
        Collections.shuffle(flowerThumb, new Random(seed));
        Collections.shuffle(flowerBack, new Random(seed));
        Collections.shuffle(flowerFore, new Random(seed));*/

        FlowerframesAdapter flowerframesAdapter = new FlowerframesAdapter(getActivity(), beforeKeydownloadthumb, beforeKeydownloadback, beforeKeydownloadfore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        flowerCateRec.setLayoutManager(gridLayoutManager);
        flowerCateRec.setHasFixedSize(true);
        flowerCateRec.setAdapter(flowerframesAdapter);

    }

    private void flowerDataWithAds() {

       /* long seed = System.nanoTime();
        Collections.shuffle(flowerThumb, new Random(seed));
        Collections.shuffle(flowerBack, new Random(seed));
        Collections.shuffle(flowerFore, new Random(seed));*/

        /*if (flowerFore.size() > 0 && mNativeAds.size() > 0 && flowerThumb.size() > 2) {
            flowerThumb.add(2, mNativeAds.get(0));
            flowerFore.add(2, null);
            flowerBack.add(2, null);
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

        if (flowerFore.size() > 0) {
            //long seed = System.nanoTime();
            // Collections.shuffle(natureThumb, new Random(seed));
            // Collections.shuffle(natureBack, new Random(seed));
            // Collections.shuffle(natureFore, new Random(seed));

            FlowerframesAdapter flowerframesAdapter = new FlowerframesAdapter(getActivity(), beforeKeydownloadthumb, beforeKeydownloadback, beforeKeydownloadfore);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (flowerframesAdapter.getItemViewType(position)) {
                        case STRING:
                            return 1;
                        case NATIVE_UnifiedNativeAd:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
            flowerCateRec.setLayoutManager(gridLayoutManager);
            flowerCateRec.setHasFixedSize(true);
            flowerCateRec.setAdapter(flowerframesAdapter);


        }

    }


    public class FlowerframesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context mContext;
        // ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;

        ArrayList<Object> flowerThumb;
        ArrayList<Object> flowerBack;
        ArrayList<Object> flowerFore;
        int rewardedIcon;


        public FlowerframesAdapter(Activity activity, ArrayList<Object> flowerThumb, ArrayList<Object> flowerBack, ArrayList<Object> flowerFore) {
            this.mContext = activity;
            // this.nthumb=nthumb;
            this.mInflater = LayoutInflater.from(activity);
            this.flowerThumb = flowerThumb;
            this.flowerBack = flowerBack;
            this.flowerFore = flowerFore;

        }

        @Override
        public int getItemViewType(int position) {

            Object recyclerViewItem = flowerThumb.get(position);
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
                    return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.flower_layout, parent, false));

            }


        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            // holder.flowerImg.setImageResource(nthumb.get(position));

            int viewType = getItemViewType(position);
            //to set the count key position after checking the downloaded frames
            int oldflowerkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newflowercount;
            if (flowerkeycount > 0) {
                newflowercount = oldflowerkeycount + flowerkeycount;

            } else {
                newflowercount = oldflowerkeycount;
            }

            switch (viewType) {
                case NATIVE_UnifiedNativeAd:
                    NativeAd nativeAd = (NativeAd) flowerThumb.get(position);
                    populateNativeAdViewRecycler(nativeAd, ((NativeAdViewHolder) holder).getAdView());
                    break;
                case STRING:
                    final ViewHolder holder1 = (ViewHolder) holder;

                    final String img1 = flowerThumb.get(position).toString();
                    final String backFrame = flowerBack.get(position).toString();
                    final String foreFrame = flowerFore.get(position).toString();

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
                            holder1.flowerDown.setVisibility(View.VISIBLE);
                        } else {
                            holder1.flowerDown.setVisibility(View.GONE);
                        }*/


                        rewardedIcon = position;
                        if (position > 2 && flowerBack.get(2) == null && flowerFore.get(2) == null) {
                            rewardedIcon = position - 1;
                        } else {
                            rewardedIcon = position;
                        }

                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                                if (rewardedIcon >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                                if (rewardedIcon >= newflowercount) {

                                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                        holder1.flowerDown.setVisibility(View.VISIBLE);
                                        holder1.flowerDown.setImageResource(R.drawable.video);
                                    } else {
                                        holder1.flowerDown.setVisibility(View.GONE);
                                    }


                                } else {

                                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                        holder1.flowerDown.setVisibility(View.VISIBLE);
                                        holder1.flowerDown.setImageResource(R.drawable.download);
                                    } else {
                                        holder1.flowerDown.setVisibility(View.GONE);
                                    }


                                }

                            } else {

                                if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                    holder1.flowerDown.setVisibility(View.VISIBLE);
                                    holder1.flowerDown.setImageResource(R.drawable.download);
                                } else {
                                    holder1.flowerDown.setVisibility(View.GONE);
                                }


                            }
                        } else {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder1.flowerDown.setVisibility(View.VISIBLE);
                                holder1.flowerDown.setImageResource(R.drawable.download);
                            } else {
                                holder1.flowerDown.setVisibility(View.GONE);
                            }


                        }


                        Glide.with(mContext).load(img1)
                                .placeholder(R.drawable.loading_1)
                                .into(holder1.flowerImg);
                    }


                    // holder.flowerRelative.setAnimation(a);


                    holder1.flowerCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (holder1.flowerDown.getVisibility() == View.VISIBLE) {
                                holder1.flowerDown.setVisibility(View.GONE);
                            }

                            if (flowerFragmentListener != null) {
                                flowerFragmentListener.onFlowerViewItemClicked(flowerBack.get(position).toString(), flowerFore.get(position).toString(), position);
                            }

                        }
                    });


                    break;
            }


        }

        @Override
        public int getItemCount() {
            return flowerThumb.size();
        }

        public int getImage(String imageName) {

            int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

            return drawableResourceId;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView flowerImg;
            CardView flowerCard;
            ImageView flowerDown;
            RelativeLayout flowerRelative;


            ViewHolder(View v) {
                super(v);
                flowerImg = v.findViewById(R.id.flower_thumb);
                flowerCard = v.findViewById(R.id.flower_card);
                flowerDown = v.findViewById(R.id.flower_down);
                flowerRelative = v.findViewById(R.id.flower_relative);

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            flowerFragmentListener = (FlowerFragment.FlowerFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

}
