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
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.NatureframesAdapter;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NatureFragmentNew extends Fragment {

    /*  ArrayList<Integer> nthumb=new ArrayList<>();
      ArrayList<Integer> nback=new ArrayList<>();
      ArrayList<Integer> nfore=new ArrayList<>();*/
    ArrayList<String> natureThumb = new ArrayList<>();
    ArrayList<String> natureBack = new ArrayList<>();
    ArrayList<String> natureFore = new ArrayList<>();

    ArrayList<String> beforeKeythumb = new ArrayList<>();
    ArrayList<String> beforeKeyback = new ArrayList<>();
    ArrayList<String> beforeKeyfore = new ArrayList<>();

    ArrayList<String> downloadedlistThumb = new ArrayList<>();
    ArrayList<String> downloadedlistback = new ArrayList<>();
    ArrayList<String> downloadedlistfore = new ArrayList<>();

    ArrayList<String> naturedownloadedlistThumb = new ArrayList<>();
    ArrayList<String> naturedownloadedlistback = new ArrayList<>();
    ArrayList<String> naturedownloadedlistfore = new ArrayList<>();

    ArrayList<String> beforeKeydownloadedlistThumb = new ArrayList<>();
    ArrayList<String> beforeKeydownloadedlistback = new ArrayList<>();
    ArrayList<String> beforeKeydownloadedlistfore = new ArrayList<>();

    ArrayList<String> tobedownloadlistThumb = new ArrayList<>();
    ArrayList<String> tobedownloadlistback = new ArrayList<>();
    ArrayList<String> tobedownloadlistfore = new ArrayList<>();

    ArrayList<String> beforeKeytobedownloadlistThumb = new ArrayList<>();
    ArrayList<String> beforeKeytobedownloadlistback = new ArrayList<>();
    ArrayList<String> beforeKeytobedownloadlistfore = new ArrayList<>();

    ArrayList<String> naturetobedownloadlistThumb = new ArrayList<>();
    ArrayList<String> naturetobedownloadlistback = new ArrayList<>();
    ArrayList<String> naturetobedownloadlistfore = new ArrayList<>();

    ArrayList<String> combinedarraylistThumb = new ArrayList<>();
    ArrayList<String> combinedarraylistback = new ArrayList<>();
    ArrayList<String> combinedarraylistfore = new ArrayList<>();

    String backFrame;
    String backFrame2;
    String foreFrame;
    String foreFrame2;
    String img1;
    String img2;
   public static  int keycountnewnature = 0;
    RecyclerView natureCateRecNew;
    private View myView;
    NatureframesAdapterNew natureframesAdapternew;
    NatAdapterListenernew mListener;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_nature_new, viewGroup, false);
        this.myView = inflate;
        natureCateRecNew = (RecyclerView) inflate.findViewById(R.id.nature_rec_new);

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
       /* for (int i=1; i<=2;i++){
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
        long seed = System.nanoTime();

        Collections.shuffle(beforeKeythumb, new Random(seed));
        Collections.shuffle(beforeKeyback, new Random(seed));
        Collections.shuffle(beforeKeyfore, new Random(seed));

        Collections.shuffle(natureThumb, new Random(seed));
        Collections.shuffle(natureBack, new Random(seed));
        Collections.shuffle(natureFore, new Random(seed));

        /*if (beforeKeythumb != null && beforeKeythumb.size() > 0) {

            for (int i = 0; i < beforeKeythumb.size(); i++) {

                natureThumb.add(i, beforeKeythumb.get(i));
                natureBack.add(i, beforeKeyback.get(i));
                natureFore.add(i, beforeKeyfore.get(i));

            }

        }*/
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
                    keycountnewnature = naturedownloadedlistThumb.size();
                    System.out.println(keycountnewnature);
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

          /*natureThumb.add(0, "nature_thumb_1");
        natureBack.add(0, "nature_back_1");
        natureFore.add(0, "nature_fore_1");*/


        combinedarraylistThumb.add(0, "nature_thumb_1");
        combinedarraylistback.add(0, "nature_back_1");
        combinedarraylistfore.add(0, "nature_fore_1");


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

        classAndWidgetIntialize(inflate);


        return this.myView;
    }

    private void classAndWidgetIntialize(View view) {

        natureframesAdapternew = new NatureframesAdapterNew(getActivity(), combinedarraylistThumb, combinedarraylistback, combinedarraylistfore);
        // natureCateRecNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        natureCateRecNew.setLayoutManager(layoutManager);
        natureCateRecNew.setHasFixedSize(true);
        natureCateRecNew.setAdapter(natureframesAdapternew);


    }

    public interface NatAdapterListenernew {
        void onNatItemClickednew(String back, String forei, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (NatureFragmentNew.NatAdapterListenernew) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

    public class NatureframesAdapterNew extends RecyclerView.Adapter<NatureframesAdapterNew.ViewHolder> {

        Context mContext;
        //ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;
        ArrayList<String> natureThumb;
        ArrayList<String> natureBack;
        ArrayList<String> natureFore;


        public NatureframesAdapterNew(Activity activity, ArrayList<String> natureThumb, ArrayList<String> natureBack, ArrayList<String> natureFore) {
            this.mContext = activity;
            // this.nthumb=nthumb;
            this.natureThumb = natureThumb;
            this.natureBack = natureBack;
            this.natureFore = natureFore;
            this.mInflater = LayoutInflater.from(activity);

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.naturenew_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            int oldrewardadkey = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newrewardkeycount;

            if (keycountnewnature > 0) {
                newrewardkeycount = oldrewardadkey + keycountnewnature;
            } else {
                newrewardkeycount = oldrewardadkey;
            }
            final String img1 = natureThumb.get(position).toString();
            final String backFrame = natureBack.get(position).toString();
            final String foreFrame = natureFore.get(position).toString();
            // Animation a = AnimationUtils.loadAnimation(mContext, R.anim.item_from_right);
            // holder.natureNewRelative.startAnimation(a);

           /* if (position<2){
                holder.natureDown.setVisibility(View.GONE);
                Glide.with(mContext).load(getImage(natureThumb.get(position).toString()))
                        .placeholder(R.drawable.loading_1)
                        .into(holder.natureImg);

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
                        holder.natureDown.setVisibility(View.VISIBLE);
                    } else {
                        holder.natureDown.setVisibility(View.GONE);
                    }
                    Glide.with(mContext).load(img1)
                            .placeholder(R.drawable.loading_1)
                            .into(holder.natureImg);
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
                    holder.natureDown.setVisibility(View.VISIBLE);
                } else {
                    holder.natureDown.setVisibility(View.GONE);
                }*/

                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

//                        if (position >= Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())) {
                        if (position >= newrewardkeycount) {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder.natureDown.setVisibility(View.VISIBLE);
                                holder.natureDown.setImageResource(R.drawable.video);
                            } else {
                                holder.natureDown.setVisibility(View.GONE);
                            }

                        } else {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder.natureDown.setVisibility(View.VISIBLE);
                                holder.natureDown.setImageResource(R.drawable.download);
                            } else {
                                holder.natureDown.setVisibility(View.GONE);
                            }

                        }

                    } else {

                        if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                            holder.natureDown.setVisibility(View.VISIBLE);
                            holder.natureDown.setImageResource(R.drawable.download);
                        } else {
                            holder.natureDown.setVisibility(View.GONE);
                        }

                    }
                } else {

                    if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                        holder.natureDown.setVisibility(View.VISIBLE);
                        holder.natureDown.setImageResource(R.drawable.download);
                    } else {
                        holder.natureDown.setVisibility(View.GONE);
                    }

                }


                Glide.with(mContext).load(img1)
                        .placeholder(R.drawable.loading_1)
                        .into(holder.natureImg);
            } else {
                holder.natureDown.setVisibility(View.GONE);

                Glide.with(mContext).load(getImage(natureThumb.get(position).toString()))
                        .placeholder(R.drawable.loading_1)
                        .into(holder.natureImg);
            }


            // holder.natureImg.setImageResource(nthumb.get(position));

            holder.natureImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.natureDown.getVisibility() == View.VISIBLE) {
                        holder.natureDown.setVisibility(View.GONE);
                    }


                    if (mListener != null) {
                        mListener.onNatItemClickednew(natureBack.get(position), natureFore.get(position), position);
                    }

                }
            });
            holder.natureDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.natureDown.getVisibility() == View.VISIBLE) {
                        holder.natureDown.setVisibility(View.GONE);
                    }


                    if (mListener != null) {
                        mListener.onNatItemClickednew(natureBack.get(position), natureFore.get(position), position);
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return natureThumb.size();
        }

        public int getImage(String imageName) {

            int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

            return drawableResourceId;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView natureImg;
            ImageView natureDown;
            RelativeLayout natureNewRelative;


            ViewHolder(View v) {
                super(v);
                natureImg = v.findViewById(R.id.adapter_nat);
                natureDown = v.findViewById(R.id.nature_down_new);
                natureNewRelative = v.findViewById(R.id.nature_new_rela);

            }
        }
    }

}
