package com.cyut.fruit;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ImagesView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_view);

        Intent intent = getIntent();
        Bundle bag = intent.getExtras();
        Bundle bag2 = intent.getExtras();
        ArrayList<Integer> test_bag = bag.getIntegerArrayList("image_put");
        ArrayList<ArrayList<String>> check_result = (ArrayList<ArrayList<String>>) bag2.getSerializable("text_put");

        ViewPager viewpager = (ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(ImagesView.this, test_bag, check_result);
        viewpager.setAdapter(viewPagerAdapter);

        ImageButton backpage = (ImageButton) findViewById(R.id.backpage);
        backpage.setOnClickListener(backpage_listneer);
}

    ImageButton.OnClickListener backpage_listneer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ImagesView.this,LibraryHome.class);
            startActivity(intent);
        }
    };
}
