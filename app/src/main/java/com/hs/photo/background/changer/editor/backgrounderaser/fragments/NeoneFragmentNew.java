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

public class NeoneFragmentNew extends Fragment {

   /* ArrayList<Integer> nthumb=new ArrayList<>();
    ArrayList<Integer> nback=new ArrayList<>();
    ArrayList<Integer> nfore=new ArrayList<>();*/

    ArrayList<String> neonThumb = new ArrayList<>();
    ArrayList<String> neonBack = new ArrayList<>();
    ArrayList<String> neonFore = new ArrayList<>();

    ArrayList<String> beforeKeythumb=new ArrayList<>();
    ArrayList<String> beforeKeyback=new ArrayList<>();
    ArrayList<String> beforeKeyfore=new ArrayList<>();


    ArrayList<String> downloadedThumb = new ArrayList<>();
    ArrayList<String> downloadedBack = new ArrayList<>();
    ArrayList<String> downloadedFore = new ArrayList<>();

    ArrayList<String> tobedownloadedThumb = new ArrayList<>();
    ArrayList<String> tobedownloadedBack = new ArrayList<>();
    ArrayList<String> tobedownloadedFore = new ArrayList<>();

    ArrayList<String> beforeKeydownloadthumb = new ArrayList<>();
    ArrayList<String> beforeKeydownloadback = new ArrayList<>();
    ArrayList<String> beforeKeydownloadfore = new ArrayList<>();

    ArrayList<String> beforeKeytobedownloadthumb = new ArrayList<>();
    ArrayList<String> beforeKeytobedownloadback = new ArrayList<>();
    ArrayList<String> beforeKeytobedownoladfore = new ArrayList<>();


    String backFrame, backFrame2;
    String foreFrame, foreFrame2;
    String img1, img2;
  public static   int keycountnewneon= 0;
    RecyclerView neonCateRecNew;

   NeonAdapterListenernew neonAdapterListenernew;
    public interface NeonAdapterListenernew {
        void onNeonItemClickednew(String back, String forei,int position);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_neon_new, viewGroup, false);
        neonCateRecNew=(RecyclerView)inflate.findViewById(R.id.neon_rec_new);
        neonThumb.clear();
        neonBack.clear();
        neonFore.clear();

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
        /*for (int i=1; i<=2;i++){
            neonThumb.add("neon_thumb_"+i);
            neonBack.add("neon_back_"+i);
            neonFore.add("neon_fore_"+i);
        }*/

        if (RemoteConfigValues.getOurRemote().getNeonUrl()!=null){
            if (!RemoteConfigValues.getOurRemote().getNeonUrl().equals("")){
                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter()!=null){
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")){
                        for (int i=1;i<=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());i++){
                            beforeKeythumb.add(RemoteConfigValues.getOurRemote().getNeonUrl()+"neon_thumb_"+i+".jpg");
                            beforeKeyback.add(RemoteConfigValues.getOurRemote().getNeonUrl()+"neon_back_"+i+".png");
                            beforeKeyfore.add(RemoteConfigValues.getOurRemote().getNeonUrl()+"neon_fore_"+i+".png");
                        }

                    }
                }
            }
        }

        if (RemoteConfigValues.getOurRemote().getNeonUrl()!=null){
            if (!RemoteConfigValues.getOurRemote().getNeonUrl().equals("")){
                if (RemoteConfigValues.getOurRemote().getNeonCount()!=null){
                    if (!RemoteConfigValues.getOurRemote().getNeonCount().equals("")){
                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter()!=null) {
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")){
                                for (int i=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())+1;i<=Integer.parseInt(RemoteConfigValues.getOurRemote().getNeonCount());i++){
                                    neonThumb.add(RemoteConfigValues.getOurRemote().getNeonUrl()+"neon_thumb_"+i+".jpg");
                                    neonBack.add(RemoteConfigValues.getOurRemote().getNeonUrl()+"neon_back_"+i+".png");
                                    neonFore.add(RemoteConfigValues.getOurRemote().getNeonUrl()+"neon_fore_"+i+".png");
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

        Collections.shuffle(neonThumb, new Random(seed));
        Collections.shuffle(neonBack, new Random(seed));
        Collections.shuffle(neonFore, new Random(seed));
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
        for (int i = 0; i < neonThumb.size(); i++) {
            img1 = neonThumb.get(i).toString();
            backFrame = neonBack.get(i).toString();
            foreFrame = neonFore.get(i).toString();

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

                    downloadedThumb.add(neonThumb.get(i));
                    downloadedBack.add(neonBack.get(i));
                    downloadedFore.add(neonFore.get(i));

                    System.out.println(downloadedThumb.size());
                    keycountnewneon = downloadedThumb.size();
                    System.out.println(keycountnewneon);
                } else {
                    //adding the not downloaded frames
                    tobedownloadedThumb.add(neonThumb.get(i));
                    tobedownloadedBack.add(neonBack.get(i));
                    tobedownloadedFore.add(neonFore.get(i));
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

                neonThumb.add(i,beforeKeythumb.get(i));
                neonBack.add(i,beforeKeyback.get(i));
                neonFore.add(i,beforeKeyfore.get(i));

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

        NeonframesAdapterNew neonframesAdapterNew = new NeonframesAdapterNew(getActivity(), beforeKeydownloadthumb,beforeKeydownloadback,beforeKeydownloadfore);
        neonCateRecNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        neonCateRecNew.setHasFixedSize(true);
        neonCateRecNew.setAdapter(neonframesAdapterNew);


    }
    public class NeonframesAdapterNew  extends RecyclerView.Adapter<NeonframesAdapterNew.ViewHolder> {

        Context mContext;
       // ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;
        ArrayList<String> neonThumb;
        ArrayList<String> neonBack;
        ArrayList<String> neonFore;





        public NeonframesAdapterNew(Activity activity, ArrayList<String> neonThumb, ArrayList<String> neonBack, ArrayList<String> neonFore){
            this.mContext=activity;
           // this.nthumb=nthumb;
            this.mInflater = LayoutInflater.from(activity);
            this.neonThumb=neonThumb;
            this.neonBack=neonBack;
            this.neonFore=neonFore;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.neonnew_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            //to set the count key position after checking the downloaded frames
            int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newkeycount;
            if (keycountnewneon > 0) {
                newkeycount = oldkeycount + keycountnewneon;

            } else {
                newkeycount = oldkeycount;
            }
           // holder.neonnewImg.setImageResource(nthumb.get(position));

            final String img1 = neonThumb.get(position).toString();
            final String backFrame=neonBack.get(position).toString();
            final  String foreFrame=neonFore.get(position).toString();
           // Animation a = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);

           /* if (position<2){

                holder.neondownNew.setVisibility(View.GONE);
                Glide.with(mContext).load(getImage(neonThumb.get(position).toString()))
                        .placeholder(R.drawable.loading_1)
                        .into(holder.neonnewImg);

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
                        holder.neondownNew.setVisibility(View.VISIBLE);
                    } else {
                        holder.neondownNew.setVisibility(View.GONE);
                    }
                    Glide.with(mContext).load(img1)
                            .placeholder(R.drawable.loading_1)
                            .into(holder.neonnewImg);

                }



            }*/
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

              /*  if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                    holder.neondownNew.setVisibility(View.VISIBLE);
                } else {
                    holder.neondownNew.setVisibility(View.GONE);
                }*/

                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter()!=null){
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")){

//                        if (position>=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())){
                        if (position>=newkeycount){

                            if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                                holder.neondownNew.setVisibility(View.VISIBLE);
                                holder.neondownNew.setImageResource(R.drawable.video);
                            } else {
                                holder.neondownNew.setVisibility(View.GONE);
                            }

                        }
                        else {

                            if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                                holder.neondownNew.setVisibility(View.VISIBLE);
                                holder.neondownNew.setImageResource(R.drawable.download);
                            } else {
                                holder.neondownNew.setVisibility(View.GONE);
                            }

                        }

                    }
                    else {

                        if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                            holder.neondownNew.setVisibility(View.VISIBLE);
                            holder.neondownNew.setImageResource(R.drawable.download);
                        } else {
                            holder.neondownNew.setVisibility(View.GONE);
                        }

                    }
                }
                else {

                    if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                        holder.neondownNew.setVisibility(View.VISIBLE);
                        holder.neondownNew.setImageResource(R.drawable.download);
                    } else {
                        holder.neondownNew.setVisibility(View.GONE);
                    }

                }





                Glide.with(mContext).load(img1)
                        .placeholder(R.drawable.loading_1)
                        .into(holder.neonnewImg);

            }


           // holder.neonnewImg.setAnimation(a);


            holder.neonnewImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ( holder.neondownNew.getVisibility() == View.VISIBLE) {
                        holder.neondownNew.setVisibility(View.GONE);
                    }

                    if(neonAdapterListenernew != null) {
                        neonAdapterListenernew.onNeonItemClickednew(neonBack.get(position),neonFore.get(position),position);
                    }

                }
            });
            holder.neondownNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ( holder.neondownNew.getVisibility() == View.VISIBLE) {
                        holder.neondownNew.setVisibility(View.GONE);
                    }

                    if(neonAdapterListenernew != null) {
                        neonAdapterListenernew.onNeonItemClickednew(neonBack.get(position),neonFore.get(position),position);
                    }

                }
            });



        }

        @Override
        public int getItemCount() {
            return neonThumb.size();
        }

        public int getImage(String imageName) {

            int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

            return drawableResourceId;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView neonnewImg;
            ImageView neondownNew;



            ViewHolder(View v) {
                super(v);
                neonnewImg=v.findViewById(R.id.adapter_neon);
                neondownNew=v.findViewById(R.id.neon_down_new);

            }
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            neonAdapterListenernew = (NeoneFragmentNew.NeonAdapterListenernew) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

}
