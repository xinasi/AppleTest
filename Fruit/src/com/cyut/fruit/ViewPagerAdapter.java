package com.cyut.fruit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyut.fruit.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    AppCompatActivity activity;
    ArrayList<Integer> images = new ArrayList<Integer>();


    public ViewPagerAdapter(AppCompatActivity activity, ArrayList<Integer> images) {
        this.activity = activity;
        this.images = images;
    }


    public ViewPagerAdapter (Context context){
        this.context = context;
    }




    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view,  Object object) {
        return  view ==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.slideimg);
        imageView.setImageResource(images.get(position));


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}

