package com.cyut.fruit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import com.cyut.fruit.R;

/*
轉盤優惠券:
    使用Picasso放入圖片
 */
public class RouletteViewHolder extends RecyclerView.ViewHolder {
    View rView;
    private ItemClickListner itemClickListner;

    public RouletteViewHolder(View itemView) {
        super(itemView);
        rView = itemView;

        // 10/24
        rView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        // 10/24
        rView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx, String url) {
        ImageView rImageView = rView.findViewById(R.id.rc_ImageView);

        Picasso.get().load(url).placeholder(R.drawable.picture).into(rImageView);

    }

    // 10/24
    private RouletteViewHolder.ClickListener mClickListener;
    // 10/24
    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }
    // 10/24
    public void setOnClickListener(RouletteViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
