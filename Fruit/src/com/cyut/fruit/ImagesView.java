package com.cyut.fruit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cyut.fruit.R;

import java.util.ArrayList;

public class ImagesView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_view);

        Intent intent = getIntent();
        Bundle bag = intent.getExtras();
        ArrayList<Integer> test_bag = bag.getIntegerArrayList("image_put");
        ViewPager viewpager = (ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(ImagesView.this, test_bag);
        viewpager.setAdapter(viewPagerAdapter);
    }
}
