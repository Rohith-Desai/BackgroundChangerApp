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

public class FlowerFragmentNew extends Fragment {

    /* ArrayList<Integer> nthumb=new ArrayList<>();
     ArrayList<Integer> nback=new ArrayList<>();
     ArrayList<Integer> nfore=new ArrayList<>();*/
    ArrayList<String> flowerThumb = new ArrayList<>();
    ArrayList<String> flowerBack = new ArrayList<>();
    ArrayList<String> flowerFore = new ArrayList<>();

    ArrayList<String> beforeKeythumb = new ArrayList<>();
    ArrayList<String> beforeKeyback = new ArrayList<>();
    ArrayList<String> beforeKeyfore = new ArrayList<>();


    ArrayList<String> downloadedflowerThumb = new ArrayList<>();
    ArrayList<String> downloadedflowerBack = new ArrayList<>();
    ArrayList<String> downloadedflowerFore = new ArrayList<>();

    ArrayList<String> tobedownloadedflowerThumb = new ArrayList<>();
    ArrayList<String> tobedownloadedflowerBack = new ArrayList<>();
    ArrayList<String> tobedownloadedflowerFore = new ArrayList<>();


    ArrayList<String> beforeKeydownloadthumb = new ArrayList<>();
    ArrayList<String> beforeKeydownloadback = new ArrayList<>();
    ArrayList<String> beforeKeydownloadfore = new ArrayList<>();

    ArrayList<String> beforeKeytobedownloadthumb = new ArrayList<>();
    ArrayList<String> beforeKeytobedownloadback = new ArrayList<>();
    ArrayList<String> beforeKeytobedownoladfore = new ArrayList<>();

    String backFrame, backFrame2;
    String foreFrame, foreFrame2;
    String img1, img2;
  public static   int keycountnewflower = 0;

    RecyclerView flowerCateRecNew;

    FlowerAdapterListenernew flowerAdapterListenernew;

    public interface FlowerAdapterListenernew {
        void onFlowerItemClickednew(String back, String forei, int position);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_flower_new, viewGroup, false);
        flowerCateRecNew = (RecyclerView) inflate.findViewById(R.id.flower_rec_new);
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
                    keycountnewflower = downloadedflowerThumb.size();
                    System.out.println(keycountnewflower);
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

        classAndWidgetIntialize();


        return inflate;
    }

    private void classAndWidgetIntialize() {

        FlowerframesAdapterNew flowerframesAdapterNew = new FlowerframesAdapterNew(getActivity(), beforeKeydownloadthumb, beforeKeydownloadback, beforeKeydownloadfore);
        flowerCateRecNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        flowerCateRecNew.setHasFixedSize(true);
        flowerCateRecNew.setAdapter(flowerframesAdapterNew);


    }

    public class FlowerframesAdapterNew extends RecyclerView.Adapter<FlowerframesAdapterNew.ViewHolder> {

        Context mContext;
        // ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;
        ArrayList<String> flowerThumb;
        ArrayList<String> flowerBack;
        ArrayList<String> flowerFore;


        public FlowerframesAdapterNew(Activity activity, ArrayList<String> flowerThumb, ArrayList<String> flowerBack, ArrayList<String> flowerFore) {
            this.mContext = activity;
            //  this.nthumb=nthumb;
            this.mInflater = LayoutInflater.from(activity);
            this.flowerThumb = flowerThumb;
            this.flowerBack = flowerBack;
            this.flowerFore = flowerFore;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.flowernew_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            //to set the count key position after checking the downloaded frames
            int oldflowerkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newflowercount;
            if (keycountnewflower > 0) {
                newflowercount = oldflowerkeycount + keycountnewflower;

            } else {
                newflowercount = oldflowerkeycount;
            }
            // holder.flowernewImg.setImageResource(nthumb.get(position));

            final String img1 = flowerThumb.get(position).toString();
            final String backFrame = flowerBack.get(position).toString();
            final String foreFrame = flowerFore.get(position).toString();
            // Animation a = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);

           /* if (position<2){
                holder.flowerdownNew.setVisibility(View.GONE);
                Glide.with(mContext).load(getImage(flowerThumb.get(position).toString()))
                        .placeholder(R.drawable.loading_1)
                        .into(holder.flowernewImg);


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
                        holder.flowerdownNew.setVisibility(View.VISIBLE);
                    } else {
                        holder.flowerdownNew.setVisibility(View.GONE);
                    }
                    Glide.with(mContext).load(img1)
                            .placeholder(R.drawable.loading_1)
                            .into(holder.flowernewImg);
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

                /*if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                    holder.flowerdownNew.setVisibility(View.VISIBLE);
                } else {
                    holder.flowerdownNew.setVisibility(View.GONE);
                }*/

                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                        if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                        if (position >= newflowercount) {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder.flowerdownNew.setVisibility(View.VISIBLE);
                                holder.flowerdownNew.setImageResource(R.drawable.video);
                            } else {
                                holder.flowerdownNew.setVisibility(View.GONE);
                            }

                        } else {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder.flowerdownNew.setVisibility(View.VISIBLE);
                                holder.flowerdownNew.setImageResource(R.drawable.download);
                            } else {
                                holder.flowerdownNew.setVisibility(View.GONE);
                            }

                        }

                    } else {

                        if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                            holder.flowerdownNew.setVisibility(View.VISIBLE);
                            holder.flowerdownNew.setImageResource(R.drawable.download);
                        } else {
                            holder.flowerdownNew.setVisibility(View.GONE);
                        }

                    }
                } else {

                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                        holder.flowerdownNew.setVisibility(View.VISIBLE);
                        holder.flowerdownNew.setImageResource(R.drawable.download);
                    } else {
                        holder.flowerdownNew.setVisibility(View.GONE);
                    }

                }


                Glide.with(mContext).load(img1)
                        .placeholder(R.drawable.loading_1)
                        .into(holder.flowernewImg);
            }


            // holder.flowernewImg.setAnimation(a);

            holder.flowernewImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.flowerdownNew.getVisibility() == View.VISIBLE) {
                        holder.flowerdownNew.setVisibility(View.GONE);
                    }

                    if (flowerAdapterListenernew != null) {
                        flowerAdapterListenernew.onFlowerItemClickednew(flowerBack.get(position), flowerFore.get(position), position);
                    }

                }
            });
            holder.flowerdownNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.flowerdownNew.getVisibility() == View.VISIBLE) {
                        holder.flowerdownNew.setVisibility(View.GONE);
                    }

                    if (flowerAdapterListenernew != null) {
                        flowerAdapterListenernew.onFlowerItemClickednew(flowerBack.get(position), flowerFore.get(position), position);
                    }

                }
            });


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
            ImageView flowernewImg;
            ImageView flowerdownNew;


            ViewHolder(View v) {
                super(v);
                flowernewImg = v.findViewById(R.id.adapter_flower);
                flowerdownNew = v.findViewById(R.id.flower_down_new);

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            flowerAdapterListenernew = (FlowerFragmentNew.FlowerAdapterListenernew) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

}
