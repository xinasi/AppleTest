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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    AppCompatActivity activity;
    ArrayList<Integer> images = new ArrayList<Integer>();
    ArrayList<ArrayList<String>> check_result = new ArrayList<ArrayList<String>>();
    ArrayList<String> result = new ArrayList<>();


    public ViewPagerAdapter(AppCompatActivity activity, ArrayList<Integer> images,ArrayList<ArrayList<String>> check_result) {
        this.activity = activity;
        this.images = images;
        this.check_result = check_result;
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

        TextView textView = (TextView) view.findViewById(R.id.check_result);

        //textView.setText((CharSequence) check_result.get(position));
        //textView.setText(toString(check_result.get(position)));

        StringBuilder builder = new StringBuilder();
        for (int i = 0 ; i < position+1 ;i++){
            for (String value : check_result.get(position)){
                builder.append(value);
                builder.append("  ");
                textView.setText(builder);
            }
            builder.delete(0 , builder.length());
        }




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

