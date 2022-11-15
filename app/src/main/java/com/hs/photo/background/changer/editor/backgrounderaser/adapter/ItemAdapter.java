package com.hs.photo.background.changer.editor.backgrounderaser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.BackgroundnewActvity;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Object> mRecyclerViewItems;


    public ItemAdapter(Context mContext, List<Object> mRecyclerViewItems){

        this.mContext=mContext;
        this.mRecyclerViewItems=mRecyclerViewItems;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.sticker_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Animation a = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);
        Glide.with(mContext)
                .load(getImage(mRecyclerViewItems.get(position).toString()))
                .into(holder.stickerImg);
        holder.stickerImg.startAnimation(a);
        holder.stickerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof BackgroundnewActvity){

                    ((BackgroundnewActvity)mContext).stickerAdd(getImage(mRecyclerViewItems.get(position).toString()));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView stickerImg;



        ViewHolder(View v) {
            super(v);
            stickerImg=v.findViewById(R.id.frame_img);


        }
    }
    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }
}
