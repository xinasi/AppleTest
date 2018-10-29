package com.cyut.fruit;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.squareup.picasso.Picasso;

import com.cyut.fruit.R;

/*
轉盤優惠券:
    使用Picasso放入圖片
 */
public  class RouletteViewHolder extends RecyclerView.ViewHolder {

    View rView;

    public RouletteViewHolder(View itemView) {
        super(itemView);
        rView = itemView;
    }

    public void setDetails(Context ctx, String image) {
        ImageView rImageView = rView.findViewById(R.id.rc_ImageView);

        Picasso.get().load(image).placeholder(R.drawable.picture).into(rImageView);

    }
}
