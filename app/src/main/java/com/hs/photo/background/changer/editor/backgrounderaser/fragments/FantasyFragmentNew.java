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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FantasyFragmentNew extends Fragment {

    /* ArrayList<Integer> nthumb=new ArrayList<>();
     ArrayList<Integer> nback=new ArrayList<>();
     ArrayList<Integer> nfore=new ArrayList<>();*/
    ArrayList<String> fantasyThumb = new ArrayList<>();
    ArrayList<String> fantasyBack = new ArrayList<>();
    ArrayList<String> fantasyFore = new ArrayList<>();

    ArrayList<String> beforeKeythumb = new ArrayList<>();
    ArrayList<String> beforeKeyback = new ArrayList<>();
    ArrayList<String> beforeKeyfore = new ArrayList<>();


    ArrayList<String> beforekeydownloadedlistFantasyThumb = new ArrayList<>();
    ArrayList<String> beforekeydownloadedlistFantasyback = new ArrayList<>();
    ArrayList<String> beforekeydownloadedlistFantasyfore = new ArrayList<>();

    ArrayList<String> afterkeydownloadedlistFantasyThumb = new ArrayList<>();
    ArrayList<String> afterkeydownloadedlistFantasyback = new ArrayList<>();
    ArrayList<String> afterkeydownloadedlistFantasyfore = new ArrayList<>();


    ArrayList<String> beforekeytobedownloadlistFantasyThumb = new ArrayList<>();
    ArrayList<String> beforekeytobedownloadlistFantasyback = new ArrayList<>();
    ArrayList<String> beforekeytobedownloadlistFantasyfore = new ArrayList<>();

    ArrayList<String> afterkeytobedownloadlistFantasyThumb = new ArrayList<>();
    ArrayList<String> afterkeytobedownloadlistFantasyback = new ArrayList<>();
    ArrayList<String> afterkeytobedownloadlistFantasyfore = new ArrayList<>();

    ArrayList<String> tobedownloadlistThumb = new ArrayList<>();
    ArrayList<String> tobedownloadlistback = new ArrayList<>();
    ArrayList<String> tobedownloadlistfore = new ArrayList<>();

    ArrayList<String> combinedarraylistThumb = new ArrayList<>();
    ArrayList<String> combinedarraylistback = new ArrayList<>();
    ArrayList<String> combinedarraylistfore = new ArrayList<>();
    String backFrame;
    String backFrame2;
    String foreFrame;
    String foreFrame2;
    String img1;
    String img2;
 public  static   int keycountnewfant = 0;

    RecyclerView fantasyCateRecNew;

    FantasyAdapterListenernew fantasyAdapterListenernew;

    public interface FantasyAdapterListenernew {
        void onFantasyItemClickednew(String back, String forei, int position);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_fantasy_new, viewGroup, false);
        fantasyCateRecNew = (RecyclerView) inflate.findViewById(R.id.fantasy_rec_new);
        fantasyThumb.clear();
        fantasyBack.clear();
        fantasyFore.clear();

        beforeKeythumb.clear();
        beforeKeyback.clear();
        beforeKeyfore.clear();


        tobedownloadlistThumb.clear();
        tobedownloadlistback.clear();
        tobedownloadlistfore.clear();
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


        // addding the downloaded frames in a single list
        /*for (int i=1; i<=2;i++){
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



        /*if (beforeKeythumb!=null && beforeKeythumb.size()>0){

            for (int i=0;i<beforeKeythumb.size();i++){

                fantasyThumb.add(i,beforeKeythumb.get(i));
                fantasyBack.add(i,beforeKeyback.get(i));
                fantasyFore.add(i,beforeKeyfore.get(i));

            }

        }*/
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
                    keycountnewfant = afterkeydownloadedlistFantasyThumb.size();
                    System.out.println(keycountnewfant);
                } else {
//                    for( i =0 ;  i<natureThumb.size();i++) {
                    //adding the not downloaded frames
                    afterkeytobedownloadlistFantasyThumb.add(fantasyThumb.get(i));
                    afterkeytobedownloadlistFantasyback.add(fantasyBack.get(i));
                    afterkeytobedownloadlistFantasyfore.add(fantasyFore.get(i));
                }
            }
        }
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

        classAndWidgetIntialize();


        return inflate;
    }

    private void classAndWidgetIntialize() {

        FantasyframesAdapterNew fantasyframesAdapterNew = new FantasyframesAdapterNew(getActivity(), combinedarraylistThumb, combinedarraylistback, combinedarraylistfore);
        //fantasyCateRecNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        fantasyCateRecNew.setLayoutManager(layoutManager);
        fantasyCateRecNew.setHasFixedSize(true);
        fantasyCateRecNew.setAdapter(fantasyframesAdapterNew);


    }

    public class FantasyframesAdapterNew extends RecyclerView.Adapter<FantasyframesAdapterNew.ViewHolder> {

        Context mContext;
        //  ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;

        ArrayList<String> fantasyThumb;
        ArrayList<String> fantasyBack;
        ArrayList<String> fantasyFore;


        public FantasyframesAdapterNew(Activity activity, ArrayList<String> fantasyThumb, ArrayList<String> fantasyBack, ArrayList<String> fantasyFore) {
            this.mContext = activity;
            // this.nthumb=nthumb;
            this.mInflater = LayoutInflater.from(activity);
            this.fantasyThumb = fantasyThumb;
            this.fantasyBack = fantasyBack;
            this.fantasyFore = fantasyFore;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.fantasynew_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            int oldfantacykeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newfantacykeycount;
            if (keycountnewfant > 0) {
                newfantacykeycount = oldfantacykeycount + keycountnewfant;

            } else {
                newfantacykeycount = oldfantacykeycount;
            }
            //holder.fantasynewImg.setImageResource(nthumb.get(position));

            final String img1 = fantasyThumb.get(position).toString();
            final String backFrame = fantasyBack.get(position).toString();
            final String foreFrame = fantasyFore.get(position).toString();
            // Animation a = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);

           /* if (position<2){
                holder.fantasydownNew.setVisibility(View.GONE);
                Glide.with(mContext).load(getImage(fantasyThumb.get(position).toString()))
                        .placeholder(R.drawable.loading_1)
                        .into(holder.fantasynewImg);
            }
            else {

                if (img1.contains("https")) {
                    String[] backpaths = backFrame.split("/");
                    String[] forepaths = foreFrame.split("/");
                    String getpath = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)?
                            mContext.getExternalFilesDir(mContext.getResources().getString(R.string.app_name)).getAbsolutePath()
                            : Environment.getExternalStorageDirectory().toString()+ "/" + mContext.getResources().getString(R.string.app_name) + "/Images";

                    File backmediaStorageDir = null;
                    File foremediaStorageDir= null;
                    backmediaStorageDir = new File(getpath + "/"+backpaths[backpaths.length - 1]);
                    foremediaStorageDir = new File(getpath + "/"+forepaths[forepaths.length - 1]);

                    if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                        holder.fantasydownNew.setVisibility(View.VISIBLE);
                    } else {
                        holder.fantasydownNew.setVisibility(View.GONE);
                    }
                    Glide.with(mContext).load(img1)
                            .placeholder(R.drawable.loading_1)
                            .into(holder.fantasynewImg);
                }



            }*/

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
                    holder.fantasydownNew.setVisibility(View.VISIBLE);
                } else {
                    holder.fantasydownNew.setVisibility(View.GONE);
                }*/

                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                        if (position>=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())){
                        if (position >= newfantacykeycount) {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder.fantasydownNew.setVisibility(View.VISIBLE);
                                holder.fantasydownNew.setImageResource(R.drawable.video);
                            } else {
                                holder.fantasydownNew.setVisibility(View.GONE);
                            }

                        } else {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder.fantasydownNew.setVisibility(View.VISIBLE);
                                holder.fantasydownNew.setImageResource(R.drawable.download);
                            } else {
                                holder.fantasydownNew.setVisibility(View.GONE);
                            }

                        }

                    } else {

                        if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                            holder.fantasydownNew.setVisibility(View.VISIBLE);
                            holder.fantasydownNew.setImageResource(R.drawable.download);
                        } else {
                            holder.fantasydownNew.setVisibility(View.GONE);
                        }

                    }

                } else {

                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                        holder.fantasydownNew.setVisibility(View.VISIBLE);
                        holder.fantasydownNew.setImageResource(R.drawable.download);
                    } else {
                        holder.fantasydownNew.setVisibility(View.GONE);
                    }

                }


                Glide.with(mContext).load(img1)
                        .placeholder(R.drawable.loading_1)
                        .into(holder.fantasynewImg);
            }


            // holder.fantasynewImg.setAnimation(a);

            holder.fantasynewImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.fantasydownNew.getVisibility() == View.VISIBLE) {
                        holder.fantasydownNew.setVisibility(View.GONE);
                    }

                    if (fantasyAdapterListenernew != null) {
                        fantasyAdapterListenernew.onFantasyItemClickednew(fantasyBack.get(position), fantasyFore.get(position), position);
                    }

                }
            });
            holder.fantasydownNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.fantasydownNew.getVisibility() == View.VISIBLE) {
                        holder.fantasydownNew.setVisibility(View.GONE);
                    }

                    if (fantasyAdapterListenernew != null) {
                        fantasyAdapterListenernew.onFantasyItemClickednew(fantasyBack.get(position), fantasyFore.get(position), position);
                    }

                }
            });


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
            ImageView fantasynewImg;
            ImageView fantasydownNew;


            ViewHolder(View v) {
                super(v);
                fantasynewImg = v.findViewById(R.id.adapter_fantasy);
                fantasydownNew = v.findViewById(R.id.fantasy_down_new);

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fantasyAdapterListenernew = (FantasyFragmentNew.FantasyAdapterListenernew) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }
}
