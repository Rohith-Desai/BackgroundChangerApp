package com.hs.photo.background.changer.editor.backgrounderaser.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.bitmap.BitmapUtil;
import java.io.IOException;
import java.io.InputStream;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private String[] listItem;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String str);
    }

    public CommonAdapter(String[] strArr, Context context2) {
        this.listItem = strArr;
        this.context = context2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        inflate.setOnClickListener(this);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            String ASSET_IMG=this.listItem[i];
            InputStream open = this.context.getAssets().open(this.listItem[i]);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            Glide.with(context).load(Uri.parse("file:///android_asset/"+ASSET_IMG)).into(viewHolder.imageView);
            //Glide.with(context).load(BitmapUtil.decodeFile(bArr)).into(viewHolder.imageView);
        } catch (IOException e) {
            e.printStackTrace();
            viewHolder.imageView.setImageResource(R.drawable.default_tra);
        } catch (Exception e2) {
            e2.printStackTrace();
            viewHolder.imageView.setImageResource(R.drawable.default_tra);
        }
        View view = viewHolder.itemView;
        view.setTag("" + this.listItem[i]);
    }

    public void onClick(View view) {
        OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = this.mOnItemClickListener;
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onItemClick(view, (String) view.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.mOnItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public int getItemCount() {
        return this.listItem.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item_sticker);
        }
    }
}
