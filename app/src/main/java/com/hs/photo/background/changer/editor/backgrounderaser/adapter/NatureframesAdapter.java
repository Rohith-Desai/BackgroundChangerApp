package com.hs.photo.background.changer.editor.backgrounderaser.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.fragments.NatureFragment;
import com.hs.photo.background.changer.editor.backgrounderaser.util.NativeAdViewHolder;

import java.io.File;
import java.util.ArrayList;

import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.populateNativeAdViewRecycler;

public class NatureframesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    // ArrayList<Integer> nthumb;
    private LayoutInflater mInflater;
    private NatAdapterListener listener;

    ArrayList<Object> natureThumb;
    ArrayList<Object> natureBack;
    ArrayList<Object> natureFore;

    int rewardedIcon;
    NatureFragment natureFragment;
    static final int STRING = 404;
    static final int NATIVE_UnifiedNativeAd = 10519;

    public interface NatAdapterListener {
        void onNatItemClicked(String back, String forei, int position);
    }

    public void setListener(NatureframesAdapter.NatAdapterListener listener) {
        this.listener = listener;
    }

    public NatureframesAdapter(Activity activity, ArrayList<Object> natureThumb, ArrayList<Object> natureBack, ArrayList<Object> natureFore) {
        this.mContext = activity;
        // this.nthumb=nthumb;
        this.mInflater = LayoutInflater.from(activity);

        this.natureThumb = natureThumb;
        this.natureBack = natureBack;
        this.natureFore = natureFore;

    }

    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = natureThumb.get(position);
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
                View menuItemLayoutView2 = null;
                menuItemLayoutView2 = LayoutInflater.from(this.mContext).inflate(R.layout.nature_layout, parent, false);
                return new ViewHolders(menuItemLayoutView2);
            // return new ViewHolders(LayoutInflater.from(this.mContext).inflate(R.layout.nature_layout, parent, false));

        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int viewType = getItemViewType(position);

        int newkeycount = NatureFragment.keycountnature;
        int oldrewardadkey = Integer.parseInt(RemoteConfigValues.getOurRemote().getEnableRewardAfter());
        int newrewardkeycount;

        if (newkeycount > 0) {
            newrewardkeycount = oldrewardadkey + newkeycount;
        } else {
            newrewardkeycount = oldrewardadkey;
        }
        System.out.println(oldrewardadkey);
        switch (viewType) {
            case NATIVE_UnifiedNativeAd:
                NativeAd nativeAd = (NativeAd) natureThumb.get(position);
                populateNativeAdViewRecycler(nativeAd, ((NativeAdViewHolder) holder).getAdView());
                break;
            case STRING:
                final ViewHolders holder1 = (ViewHolders) holder;
                final String img1 = natureThumb.get(position).toString();
                final String backFrame = natureBack.get(position).toString();
                final String foreFrame = natureFore.get(position).toString();


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


                    rewardedIcon = position;
                    if (position > 6 && natureBack.get(6) == null && natureFore.get(6) == null) {
                        rewardedIcon = position - 1;
                    } else {
                        rewardedIcon = position;
                    }

                    if (RemoteConfigValues.getOurRemote().getEnableRewardAfter() != null) {
                        if (!RemoteConfigValues.getOurRemote().getEnableRewardAfter().equals("")) {

                            if (rewardedIcon >= newrewardkeycount) {

                                if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                    holder1.download_icon_nature.setVisibility(View.VISIBLE);
                                    holder1.download_icon_nature.setImageResource(R.drawable.video);
                                } else {
                                    holder1.download_icon_nature.setVisibility(View.GONE);
                                }

                            } else {

                                if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                    holder1.download_icon_nature.setVisibility(View.VISIBLE);
                                    holder1.download_icon_nature.setImageResource(R.drawable.download);
                                } else {
                                    holder1.download_icon_nature.setVisibility(View.GONE);
                                }

                            }

                        } else {

                            if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                                holder1.download_icon_nature.setVisibility(View.VISIBLE);
                                holder1.download_icon_nature.setImageResource(R.drawable.download);
                            } else {
                                holder1.download_icon_nature.setVisibility(View.GONE);
                            }

                        }
                    } else {

                        if (!backmediaStorageDir.exists() && !foremediaStorageDir.exists()) {
                            holder1.download_icon_nature.setVisibility(View.VISIBLE);
                            holder1.download_icon_nature.setImageResource(R.drawable.download);
                        } else {
                            holder1.download_icon_nature.setVisibility(View.GONE);
                        }

                    }

                    Glide.with(mContext).load(img1)
                            .placeholder(R.drawable.loading_1)
                            .into(holder1.natureImg);
                } else {
                    holder1.download_icon_nature.setVisibility(View.GONE);
                    Glide.with(mContext).load(getImage(natureThumb.get(position).toString()))
                            .placeholder(R.drawable.loading_1)
                            .into(holder1.natureImg);

                }


                //  holder.natureRelative.setAnimation(a);

                holder1.natureCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder1.download_icon_nature.getVisibility() == View.VISIBLE) {
                            holder1.download_icon_nature.setVisibility(View.GONE);
                        }

                        if (listener != null) {
                            listener.onNatItemClicked(natureBack.get(position).toString(), natureFore.get(position).toString(), position);
                        }

                    }
                });

                break;
        }


    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }

    @Override
    public int getItemCount() {
        return natureThumb.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        CardView natureCard;
        ImageView natureImg;
        ImageView download_icon_nature;
        RelativeLayout natureRelative;


        ViewHolders(View v) {
            super(v);
            natureCard = v.findViewById(R.id.nature_card);
            natureImg = v.findViewById(R.id.nature_thumb);
            download_icon_nature = v.findViewById(R.id.nature_down);
            natureRelative = v.findViewById(R.id.nature_relative);

        }
    }
}
