package com.hs.photo.background.changer.editor.backgrounderaser.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LoveFragment extends Fragment {

    RecyclerView animalCateRec;

    ArrayList<Object> animalThumb=new ArrayList<>();
    ArrayList<Object> animalBack=new ArrayList<>();
    ArrayList<Object> animalFore=new ArrayList<>();

    ArrayList<Object> beforeKeythumb=new ArrayList<>();
    ArrayList<Object> beforeKeyback=new ArrayList<>();
    ArrayList<Object> beforeKeyfore=new ArrayList<>();

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
   public static int keycountlove= 0;

    private List<NativeAd> mNativeAds = new ArrayList<>();
    static final int STRING = 404;
    static final int NATIVE_UnifiedNativeAd = 10519;
    private LoveFragmentListener loveFragmentListener;

    public interface LoveFragmentListener {
        void onLoveViewItemClicked(String back, String forei,int position);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_animal, viewGroup, false);

        animalCateRec=(RecyclerView)inflate.findViewById(R.id.animal_rec);

        animalThumb.clear();
        animalBack.clear();
        animalFore.clear();

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
       /* for (int i=1; i<=2;i++){
            neonThumb.add("neon_thumb_"+i);
            neonBack.add("neon_back_"+i);
            neonFore.add("neon_fore_"+i);
        }*/

        if (RemoteConfigValues.getOurRemote().getLoveUrl()!=null){
            if (!RemoteConfigValues.getOurRemote().getLoveUrl().equals("")){
                if (RemoteConfigValues.getOurRemote().getEnableRewardAfter()!=null){
                    if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")){
                        for (int i=1;i<=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());i++){
                            beforeKeythumb.add(RemoteConfigValues.getOurRemote().getLoveUrl()+"love_thumb_"+i+".jpg");
                            beforeKeyback.add(RemoteConfigValues.getOurRemote().getLoveUrl()+"love_back_"+i+".png");
                            beforeKeyfore.add(RemoteConfigValues.getOurRemote().getLoveUrl()+"love_fore_"+i+".png");
                        }

                    }
                }
            }
        }

        if (RemoteConfigValues.getOurRemote().getLoveUrl()!=null){
            if (!RemoteConfigValues.getOurRemote().getLoveUrl().equals("")){
                if (RemoteConfigValues.getOurRemote().getLoveCount()!=null){
                    if (!RemoteConfigValues.getOurRemote().getLoveCount().equals("")){

                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter()!=null){
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")){
                                for (int i=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())+1;i<=Integer.parseInt(RemoteConfigValues.getOurRemote().getLoveCount());i++){
                                    animalThumb.add(RemoteConfigValues.getOurRemote().getLoveUrl()+"love_thumb_"+i+".jpg");
                                    animalBack.add(RemoteConfigValues.getOurRemote().getLoveUrl()+"love_back_"+i+".png");
                                    animalFore.add(RemoteConfigValues.getOurRemote().getLoveUrl()+"love_fore_"+i+".png");
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

        Collections.shuffle(animalThumb, new Random(seed));
        Collections.shuffle(animalBack, new Random(seed));
        Collections.shuffle(animalFore, new Random(seed));

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
        for (int i = 0; i < animalThumb.size(); i++) {
            img1 = animalThumb.get(i).toString();
            backFrame = animalBack.get(i).toString();
            foreFrame = animalFore.get(i).toString();


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

                    downloadedThumb.add(animalThumb.get(i));
                    downloadedBack.add(animalBack.get(i));
                    downloadedFore.add(animalFore.get(i));

                    System.out.println(downloadedThumb.size());
                    keycountlove = downloadedThumb.size();
                    System.out.println(keycountlove);
                } else {
                    //adding the not downloaded frames
                    tobedownloadedThumb.add(animalThumb.get(i));
                    tobedownloadedBack.add(animalBack.get(i));
                    tobedownloadedFore.add(animalFore.get(i));
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

                animalThumb.add(i,beforeKeythumb.get(i));
                animalBack.add(i,beforeKeyback.get(i));
                animalFore.add(i,beforeKeyfore.get(i));

            }

        }*/

        HomeActivity activity = (HomeActivity) getActivity();
        if (activity.getlovedloaded()){

            mNativeAds = activity.getRecyclerViewItems();
            animalDataWithAds();

        }
        else {
            animalDataonly();
        }

        return inflate;
    }
    private void animalDataonly(){
      /*  long seed = System.nanoTime();
        Collections.shuffle(animalThumb, new Random(seed));
        Collections.shuffle(animalBack, new Random(seed));
        Collections.shuffle(animalFore, new Random(seed));*/
        LoveframesAdapter loveframesAdapter = new LoveframesAdapter(getActivity(), beforeKeydownloadthumb,beforeKeydownloadback,beforeKeydownloadfore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        animalCateRec.setLayoutManager(gridLayoutManager);
        animalCateRec.setHasFixedSize(true);
        animalCateRec.setAdapter(loveframesAdapter);

    }
    private void animalDataWithAds(){

       /* long seed = System.nanoTime();
        Collections.shuffle(animalThumb, new Random(seed));
        Collections.shuffle(animalBack, new Random(seed));
        Collections.shuffle(animalFore, new Random(seed));*/

        if (beforeKeydownloadfore.size() > 0 && mNativeAds.size() > 0 && beforeKeydownloadthumb.size() > 2) {
            beforeKeydownloadthumb.add(2, mNativeAds.get(0));
            beforeKeydownloadfore.add(2, null);
            beforeKeydownloadback.add(2, null);
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

        if (animalFore.size() > 0) {
            //long seed = System.nanoTime();
            // Collections.shuffle(natureThumb, new Random(seed));
            // Collections.shuffle(natureBack, new Random(seed));
            // Collections.shuffle(natureFore, new Random(seed));

            LoveframesAdapter loveframesAdapter  = new LoveframesAdapter(getActivity(), beforeKeydownloadthumb, beforeKeydownloadback, beforeKeydownloadfore);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (loveframesAdapter.getItemViewType(position)) {
                        case STRING:
                            return 1;
                        case NATIVE_UnifiedNativeAd:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
            animalCateRec.setLayoutManager(gridLayoutManager);
            animalCateRec.setHasFixedSize(true);
            animalCateRec.setAdapter(loveframesAdapter);



        }


    }

    public class LoveframesAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context mContext;
        //ArrayList<Integer> nthumb;
        private LayoutInflater mInflater;
        ArrayList<Object> animalThumb;
        ArrayList<Object> animalBack;
        ArrayList<Object> animalFore;
        int rewardedIcon;





        public LoveframesAdapter(Activity activity, ArrayList<Object> animalThumb, ArrayList<Object> animalBack, ArrayList<Object> animalFore){
            this.mContext=activity;
            //  this.nthumb=nthumb;
            this.mInflater = LayoutInflater.from(activity);
            this.animalThumb=animalThumb;
            this.animalBack=animalBack;
            this.animalFore=animalFore;

        }

        @Override
        public  int getItemViewType(int position) {

            Object recyclerViewItem = animalThumb.get(position);
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
                    return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.neon_layout, parent, false));

            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            //  holder.neonImg.setImageResource(nthumb.get(position));

            int viewType = getItemViewType(position);
            int oldkeycount = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
            int newkeycount;
            if (keycountlove > 0) {
                newkeycount = oldkeycount + keycountlove;

            } else {
                newkeycount = oldkeycount;
            }
            switch (viewType) {
                case NATIVE_UnifiedNativeAd:
                    NativeAd nativeAd = (NativeAd) animalThumb.get(position);
                    populateNativeAdViewRecycler(nativeAd, ((NativeAdViewHolder) holder).getAdView());
                    break;
                case STRING:
                    final ViewHolder holder1 = (ViewHolder) holder;

                    final String img1 = animalThumb.get(position).toString();
                    final String backFrame=animalBack.get(position).toString();
                    final  String foreFrame=animalFore.get(position).toString();

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

                       /* if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                            holder1.neonDown.setVisibility(View.VISIBLE);
                        } else {
                            holder1.neonDown.setVisibility(View.GONE);
                        }*/

                        rewardedIcon=position;
                        if (position>2 && animalBack.get(2)==null && animalFore.get(2)==null){
                            rewardedIcon=position-1;
                        }
                        else {
                            rewardedIcon=position;
                        }

                        if (RemoteConfigValues.getOurRemote().getEnableRewardAfter()!=null){
                            if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")){

//                                if (rewardedIcon>=Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter())){
                                if (rewardedIcon>=newkeycount){

                                    if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                                        holder1.neonDown.setVisibility(View.VISIBLE);
                                        holder1.neonDown.setImageResource(R.drawable.video);
                                    } else {
                                        holder1.neonDown.setVisibility(View.GONE);
                                    }


                                }
                                else {

                                    if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                                        holder1.neonDown.setVisibility(View.VISIBLE);
                                        holder1.neonDown.setImageResource(R.drawable.download);
                                    } else {
                                        holder1.neonDown.setVisibility(View.GONE);
                                    }


                                }

                            }
                            else {

                                if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                                    holder1.neonDown.setVisibility(View.VISIBLE);
                                    holder1.neonDown.setImageResource(R.drawable.download);
                                } else {
                                    holder1.neonDown.setVisibility(View.GONE);
                                }


                            }
                        }
                        else {

                            if (!backmediaStorageDir.exists()&& !foremediaStorageDir.exists()) {
                                holder1.neonDown.setVisibility(View.VISIBLE);
                                holder1.neonDown.setImageResource(R.drawable.download);
                            } else {
                                holder1.neonDown.setVisibility(View.GONE);
                            }


                        }



                        Glide.with(mContext).load(img1)
                                .placeholder(R.drawable.loading_1)
                                .into(holder1.neonImg);
                    }



                    //   holder.neonRelative.setAnimation(a);

                    holder1.neonCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if ( holder1.neonDown.getVisibility() == View.VISIBLE) {
                                holder1.neonDown.setVisibility(View.GONE);
                            }

                            if(loveFragmentListener != null) {
                                loveFragmentListener.onLoveViewItemClicked(animalBack.get(position).toString(),animalFore.get(position).toString(),position);
                            }

                        }
                    });




                    break;
            }






        }

        @Override
        public int getItemCount() {
            return animalThumb.size();
        }

        public int getImage(String imageName) {

            int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

            return drawableResourceId;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView neonImg;
            CardView neonCard;
            ImageView neonDown;
            RelativeLayout neonRelative;



            ViewHolder(View v) {
                super(v);
                neonImg=v.findViewById(R.id.neon_thumb);
                neonCard=v.findViewById(R.id.neon_card);
                neonDown=v.findViewById(R.id.neon_down);

                neonRelative=v.findViewById(R.id.neon_relative);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            loveFragmentListener = (LoveFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }


}
