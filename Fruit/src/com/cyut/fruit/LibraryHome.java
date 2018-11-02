package com.cyut.fruit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LibraryHome extends AppCompatActivity {

    ArrayList<Integer> images_array;
    ArrayList<Integer> onerow_images;
    ArrayList<Integer> tworow_images;
    ArrayList<Integer> threerow_images;
    ArrayList<ArrayList<String>> all_result;
    ArrayList<String> check_result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_home);
        ImageButton appleAlertDialog = (ImageButton) findViewById(R.id.applebtn);
        appleAlertDialog.setOnClickListener(appleAlertDialog_listneer);

        ImageButton guavaAlertDialog = (ImageButton) findViewById(R.id.guavabtn);
        guavaAlertDialog.setOnClickListener(guavaAlertDialog_listneer);

        ImageButton kiwiAlertDialog = (ImageButton) findViewById(R.id.kiwibtn);
        kiwiAlertDialog.setOnClickListener(kiwiAlertDialog_listneer);

        ImageButton dfAlertDialog = (ImageButton) findViewById(R.id.dfbtn);
        dfAlertDialog.setOnClickListener(dfAlertDialog_listneer);

        ImageButton pearAlertDialog = (ImageButton) findViewById(R.id.pearbtn);
        pearAlertDialog.setOnClickListener(pearAlertDialog_listneer);

        ImageButton backpage = (ImageButton) findViewById(R.id.backpage);
        backpage.setOnClickListener(backpage_listneer);


    }

    ImageButton.OnClickListener backpage_listneer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LibraryHome.this,MainActivity.class);
            startActivity(intent);
        }
    };


    //蘋果
    ImageButton.OnClickListener appleAlertDialog_listneer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v1 = getLayoutInflater().inflate(R.layout.check_view2, null);


            images_array = new ArrayList<Integer>();
            all_result = new ArrayList<ArrayList<String>>();
            check_result = new ArrayList<String>();

            final int[] onerow_equals= {0,0,0,0};
            onerow_images = new ArrayList<Integer>();
            final int[] tworow_equals= {0,0,0,0};
            tworow_images = new ArrayList<Integer>();
            final int[] threerow_equals= {0,0,0};
            threerow_images = new ArrayList<Integer>();

            final String[] check = {"甜","酸","水分充足","水分不足","口感扎實","口感鬆軟"};
            final String[] images_00 = {"甜","水分充足","口感扎實"};
            final String[] images_01 = {"甜","水分充足","口感扎實"};
            final String[] images_02 = {"甜","水分充足","口感扎實"};
            final String[] images_03 = {"酸","水分充足","口感扎實"};
            final String[][] check_all = {images_00, images_01,images_02,images_03};

            final int[] images_all = {R.mipmap.apple_01, R.mipmap.apple_02, R.mipmap.apple_03, R.mipmap.apple_04};

            final int[] images_sweet = {R.mipmap.apple_01, R.mipmap.apple_02, R.mipmap.apple_03, R.mipmap.apple_04};
            final int[] images_acid = {R.mipmap.apple_04};

            final int[] images_enough = {R.mipmap.apple_01, R.mipmap.apple_02, R.mipmap.apple_03, R.mipmap.apple_04};
            final int[] images_notenough = {};

            final int[] images_solid = {R.mipmap.apple_01, R.mipmap.apple_02, R.mipmap.apple_04};
            final int[] images_soft = {R.mipmap.apple_03};


            final CheckBox checkBox_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox checkBox_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox checkBox_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox checkBox_notenough = (CheckBox) v1.findViewById(R.id.notenough);
            final CheckBox checkBox_soft = (CheckBox) v1.findViewById(R.id.soft);
            final CheckBox checkBox_solid = (CheckBox) v1.findViewById(R.id.solid);
            final ImageButton checkback = (ImageButton) v1.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v1.findViewById(R.id.checkgo);





            final AlertDialog alert = builder.create();

            alert.setView(v1);
            alert.show();

            checkback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            checkgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    union(onerow_images, tworow_images, threerow_images);

                    if (checkBox_sweet.isChecked()){
                        check_result.add(check[0]);
                    }
                    if (checkBox_acid.isChecked()){
                        check_result.add(check[1]);
                    }
                    if (checkBox_enough.isChecked()){
                        check_result.add(check[2]);
                    }
                    if (checkBox_notenough.isChecked()){
                        check_result.add(check[3]);
                    }
                    if (checkBox_solid.isChecked()){
                        check_result.add(check[4]);
                    }
                    if (checkBox_soft.isChecked()){
                        check_result.add(check[5]);
                    }

                    //根據圖片新增二維
                    for (int i=0 ; i < images_array.size() ; i++){
                        all_result.add(new ArrayList<String>());
                    }

                    //將篩選結果放入到該圖片的位置
                    for (int i=0 ; i < images_array.size() ; i++) {
                        for (int j=0 ; j < images_all.length ; j++) {

                            //判斷輸出照片在全部照片照片的所在位置
                            if (images_array.get(i) == images_all[j]) {
                                for (int h = 0 ; h < check_result.size() ; h++){
                                    for (int h1 = 0 ; h1 < check_all[j].length ; h1++){
                                        //判斷篩選結果是否符合
                                        if (check_result.get(h) == check_all[j][h1]){
                                            all_result.get(i).add(check_result.get(h));
                                        }
                                    }
                                }
                            }
                        }
                    }



                    Bundle bag =new Bundle();
                    Bundle bag2 =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    bag2.putSerializable("text_put", all_result);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.putExtras(bag2);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            //甜
            checkBox_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_sweet.isChecked()){
                        checkBox_sweet.setTextColor(Color.parseColor("#CD2626"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_acid.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_sweet[i]);
                                onerow_equals[i] = 1;
                            }
                        }

                        //取消acid的設定
                        checkBox_acid.setChecked(false);
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));


                        //刪除images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });


            //酸
            checkBox_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_acid.isChecked()){
                        checkBox_acid.setTextColor(Color.parseColor("#CD2626"));

                        //取消sweet的設定
                        checkBox_sweet.setChecked(false);
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_sweet.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_acid[i]);
                                onerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //水分足夠
            checkBox_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_enough.isChecked()){
                        checkBox_enough.setTextColor(Color.parseColor("#CD2626"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_notenough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_enough[i]);
                                tworow_equals[i] = 1;
                            }
                        }



                        //取消notenough的設定
                        checkBox_notenough.setChecked(false);
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }

                    }

                }
            });


            //水分不足
            checkBox_notenough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_notenough.isChecked()){
                        checkBox_notenough.setTextColor(Color.parseColor("#CD2626"));

                        //取消enough
                        checkBox_enough.setChecked(false);
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_enough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_notenough[i]);
                                tworow_equals[i] = 1;
                            }
                        }

                    }
                    else{
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }
                    }

                }
            });

            //口感扎實
            checkBox_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_solid.isChecked()){
                        checkBox_solid.setTextColor(Color.parseColor("#CD2626"));

                        //取消soft的設定
                        checkBox_soft.setChecked(false);
                        checkBox_soft.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_soft.length ; i++){
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_solid.length ; i++) {
                            if (threerow_equals[i] == 0){
                                threerow_images.add(images_solid[i]);
                                threerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_solid.length ; i++) {
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //口感鬆軟
            checkBox_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_soft.isChecked()){
                        checkBox_soft.setTextColor(Color.parseColor("#CD2626"));

                        //取消solid
                        checkBox_solid.setChecked(false);
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_solid.length ; i++){
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_soft.length ; i++) {
                            if (threerow_equals[i] == 0){
                                threerow_images.add(images_soft[i]);
                                threerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_soft.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_soft.length ; i++) {
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });
        }
    };


    //芭樂
    ImageButton.OnClickListener guavaAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v1 = getLayoutInflater().inflate(R.layout.check_view2, null);


            images_array = new ArrayList<Integer>();
            all_result = new ArrayList<ArrayList<String>>();
            check_result = new ArrayList<String>();

            final int[] onerow_equals= {0,0,0,0};
            onerow_images = new ArrayList<Integer>();
            final int[] tworow_equals= {0,0,0,0};
            tworow_images = new ArrayList<Integer>();
            final int[] threerow_equals= {0,0,0};
            threerow_images = new ArrayList<Integer>();

            final String[] check = {"甜","酸","水分充足","水分不足","口感扎實","口感鬆軟"};
            final String[] images_00 = {"甜","水分充足","口感扎實"};
            final String[] images_01 = {"甜","水分充足","口感扎實"};
            final String[][] check_all = {images_00, images_01};

            final int[] images_all = {R.mipmap.guava_01, R.mipmap.guava_02};

            final int[] images_sweet = {R.mipmap.guava_01, R.mipmap.guava_02};
            final int[] images_acid = {};

            final int[] images_enough = {R.mipmap.guava_01, R.mipmap.guava_02};
            final int[] images_notenough = {};

            final int[] images_solid = {R.mipmap.guava_01, R.mipmap.guava_02};
            final int[] images_soft = {};


            final CheckBox checkBox_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox checkBox_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox checkBox_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox checkBox_notenough = (CheckBox) v1.findViewById(R.id.notenough);
            final CheckBox checkBox_soft = (CheckBox) v1.findViewById(R.id.soft);
            final CheckBox checkBox_solid = (CheckBox) v1.findViewById(R.id.solid);
            final ImageButton checkback = (ImageButton) v1.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v1.findViewById(R.id.checkgo);





            final AlertDialog alert = builder.create();

            alert.setView(v1);
            alert.show();

            checkback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            checkgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    union(onerow_images, tworow_images, threerow_images);

                    if (checkBox_sweet.isChecked()){
                        check_result.add(check[0]);
                    }
                    if (checkBox_acid.isChecked()){
                        check_result.add(check[1]);
                    }
                    if (checkBox_enough.isChecked()){
                        check_result.add(check[2]);
                    }
                    if (checkBox_notenough.isChecked()){
                        check_result.add(check[3]);
                    }
                    if (checkBox_solid.isChecked()){
                        check_result.add(check[4]);
                    }
                    if (checkBox_soft.isChecked()){
                        check_result.add(check[5]);
                    }

                    //根據圖片新增二維
                    for (int i=0 ; i < images_array.size() ; i++){
                        all_result.add(new ArrayList<String>());
                    }

                    //將篩選結果放入到該圖片的位置
                    for (int i=0 ; i < images_array.size() ; i++) {
                        for (int j=0 ; j < images_all.length ; j++) {

                            //判斷輸出照片在全部照片照片的所在位置
                            if (images_array.get(i) == images_all[j]) {
                                for (int h = 0 ; h < check_result.size() ; h++){
                                    for (int h1 = 0 ; h1 < check_all[j].length ; h1++){
                                        //判斷篩選結果是否符合
                                        if (check_result.get(h) == check_all[j][h1]){
                                            all_result.get(i).add(check_result.get(h));
                                        }
                                    }
                                }
                            }
                        }
                    }



                    Bundle bag =new Bundle();
                    Bundle bag2 =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    bag2.putSerializable("text_put", all_result);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.putExtras(bag2);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            //甜
            checkBox_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_sweet.isChecked()){
                        checkBox_sweet.setTextColor(Color.parseColor("#CD2626"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_acid.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_sweet[i]);
                                onerow_equals[i] = 1;
                            }
                        }

                        //取消acid的設定
                        checkBox_acid.setChecked(false);
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));


                        //刪除images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });


            //酸
            checkBox_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_acid.isChecked()){
                        checkBox_acid.setTextColor(Color.parseColor("#CD2626"));

                        //取消sweet的設定
                        checkBox_sweet.setChecked(false);
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_sweet.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_acid[i]);
                                onerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //水分足夠
            checkBox_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_enough.isChecked()){
                        checkBox_enough.setTextColor(Color.parseColor("#CD2626"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_notenough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_enough[i]);
                                tworow_equals[i] = 1;
                            }
                        }



                        //取消notenough的設定
                        checkBox_notenough.setChecked(false);
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }

                    }

                }
            });


            //水分不足
            checkBox_notenough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_notenough.isChecked()){
                        checkBox_notenough.setTextColor(Color.parseColor("#CD2626"));

                        //取消enough
                        checkBox_enough.setChecked(false);
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_enough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_notenough[i]);
                                tworow_equals[i] = 1;
                            }
                        }

                    }
                    else{
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }
                    }

                }
            });

            //口感扎實
            checkBox_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_solid.isChecked()){
                        checkBox_solid.setTextColor(Color.parseColor("#CD2626"));

                        //取消soft的設定
                        checkBox_soft.setChecked(false);
                        checkBox_soft.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_soft.length ; i++){
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_solid.length ; i++) {
                            if (threerow_equals[i] == 0){
                                threerow_images.add(images_solid[i]);
                                threerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_solid.length ; i++) {
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //口感鬆軟
            checkBox_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_soft.isChecked()){
                        checkBox_soft.setTextColor(Color.parseColor("#CD2626"));

                        //取消solid
                        checkBox_solid.setChecked(false);
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_solid.length ; i++){
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_soft.length ; i++) {
                            if (threerow_equals[i] == 0){
                                threerow_images.add(images_soft[i]);
                                threerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_soft.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_soft.length ; i++) {
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });
        }
    };


    //奇異果
    ImageButton.OnClickListener kiwiAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v1 = getLayoutInflater().inflate(R.layout.check_view2, null);


            images_array = new ArrayList<Integer>();
            all_result = new ArrayList<ArrayList<String>>();
            check_result = new ArrayList<String>();

            final int[] onerow_equals= {0,0,0,0};
            onerow_images = new ArrayList<Integer>();
            final int[] tworow_equals= {0,0,0,0};
            tworow_images = new ArrayList<Integer>();
            final int[] threerow_equals= {0,0,0};
            threerow_images = new ArrayList<Integer>();

            final String[] check = {"甜","酸","水分充足","水分不足","口感扎實","口感鬆軟"};
            final String[] images_00 = {"酸","水分充足","口感鬆軟"};
            final String[] images_01 = {"酸","水分充足","口感鬆軟"};
            final String[] images_02 = {"甜","水分充足","口感鬆軟"};
            final String[][] check_all = {images_00, images_01, images_02};

            final int[] images_all = {R.mipmap.kiwi_01, R.mipmap.kiwi_02, R.mipmap.kiwi_03};

            final int[] images_sweet = {R.mipmap.kiwi_03};
            final int[] images_acid = {R.mipmap.kiwi_01, R.mipmap.kiwi_02};

            final int[] images_enough = {R.mipmap.kiwi_01, R.mipmap.kiwi_02, R.mipmap.kiwi_03};
            final int[] images_notenough = {};

            final int[] images_solid = {};
            final int[] images_soft = {R.mipmap.kiwi_01, R.mipmap.kiwi_02, R.mipmap.kiwi_03};


            final CheckBox checkBox_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox checkBox_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox checkBox_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox checkBox_notenough = (CheckBox) v1.findViewById(R.id.notenough);
            final CheckBox checkBox_soft = (CheckBox) v1.findViewById(R.id.soft);
            final CheckBox checkBox_solid = (CheckBox) v1.findViewById(R.id.solid);
            final ImageButton checkback = (ImageButton) v1.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v1.findViewById(R.id.checkgo);





            final AlertDialog alert = builder.create();

            alert.setView(v1);
            alert.show();

            checkback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            checkback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            checkgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    union(onerow_images, tworow_images, threerow_images);

                    if (checkBox_sweet.isChecked()){
                        check_result.add(check[0]);
                    }
                    if (checkBox_acid.isChecked()){
                        check_result.add(check[1]);
                    }
                    if (checkBox_enough.isChecked()){
                        check_result.add(check[2]);
                    }
                    if (checkBox_notenough.isChecked()){
                        check_result.add(check[3]);
                    }
                    if (checkBox_solid.isChecked()){
                        check_result.add(check[4]);
                    }
                    if (checkBox_soft.isChecked()){
                        check_result.add(check[5]);
                    }

                    //根據圖片新增二維
                    for (int i=0 ; i < images_array.size() ; i++){
                        all_result.add(new ArrayList<String>());
                    }

                    //將篩選結果放入到該圖片的位置
                    for (int i=0 ; i < images_array.size() ; i++) {
                        for (int j=0 ; j < images_all.length ; j++) {

                            //判斷輸出照片在全部照片照片的所在位置
                            if (images_array.get(i) == images_all[j]) {
                                for (int h = 0 ; h < check_result.size() ; h++){
                                    for (int h1 = 0 ; h1 < check_all[j].length ; h1++){
                                        //判斷篩選結果是否符合
                                        if (check_result.get(h) == check_all[j][h1]){
                                            all_result.get(i).add(check_result.get(h));
                                        }
                                    }
                                }
                            }
                        }
                    }



                    Bundle bag =new Bundle();
                    Bundle bag2 =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    bag2.putSerializable("text_put", all_result);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.putExtras(bag2);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            //甜
            checkBox_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_sweet.isChecked()){
                        checkBox_sweet.setTextColor(Color.parseColor("#CD2626"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_acid.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_sweet[i]);
                                onerow_equals[i] = 1;
                            }
                        }

                        //取消acid的設定
                        checkBox_acid.setChecked(false);
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));


                        //刪除images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });


            //酸
            checkBox_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_acid.isChecked()){
                        checkBox_acid.setTextColor(Color.parseColor("#CD2626"));

                        //取消sweet的設定
                        checkBox_sweet.setChecked(false);
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_sweet.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_acid[i]);
                                onerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //水分足夠
            checkBox_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_enough.isChecked()){
                        checkBox_enough.setTextColor(Color.parseColor("#CD2626"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_notenough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_enough[i]);
                                tworow_equals[i] = 1;
                            }
                        }



                        //取消notenough的設定
                        checkBox_notenough.setChecked(false);
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }

                    }

                }
            });


            //水分不足
            checkBox_notenough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_notenough.isChecked()){
                        checkBox_notenough.setTextColor(Color.parseColor("#CD2626"));

                        //取消enough
                        checkBox_enough.setChecked(false);
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_enough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_notenough[i]);
                                tworow_equals[i] = 1;
                            }
                        }

                    }
                    else{
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }
                    }

                }
            });
            checkBox_solid.setChecked(false);
            //口感扎實
            checkBox_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_solid.isChecked()){
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));

                        //取消soft的設定
                        checkBox_solid.setChecked(false);
                    }
                    else{
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));
                        checkBox_soft.setChecked(true);
                    }

                }
            });


            //口感鬆軟
            if (checkBox_soft.isChecked()){
                //
            }
            else{
                checkBox_soft.setChecked(true);
                checkBox_soft.setTextColor(Color.parseColor("#CD2626"));
                // add image
            }
                    if (checkBox_soft.isChecked()){
                        checkBox_soft.setTextColor(Color.parseColor("#CD2626"));

                        //取消solid
                        checkBox_solid.setChecked(false);
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_solid.length ; i++){
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_soft.length ; i++) {
                            if (threerow_equals[i] == 0){
                                threerow_images.add(images_soft[i]);
                                threerow_equals[i] = 1;
                            }
                        }
                    }

                    checkBox_soft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkBox_soft.setTextColor(Color.parseColor("#CD2626"));
                            checkBox_soft.setChecked(true);

                            //刪除images
                            for (int i = 0 ; i < images_soft.length ; i++) {
                                if (threerow_equals[i] == 1){
                                    threerow_images.remove(0);
                                    threerow_equals[i] = 0;
                                }
                            }
                        }
                    });


        }
    };


    //火龍果
    ImageButton.OnClickListener dfAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v1 = getLayoutInflater().inflate(R.layout.check_view2, null);


            images_array = new ArrayList<Integer>();
            all_result = new ArrayList<ArrayList<String>>();
            check_result = new ArrayList<String>();

            final int[] onerow_equals= {0,0,0,0};
            onerow_images = new ArrayList<Integer>();
            final int[] tworow_equals= {0,0,0,0};
            tworow_images = new ArrayList<Integer>();
            final int[] threerow_equals= {0,0,0,0};
            threerow_images = new ArrayList<Integer>();

            final String[] check = {"甜","酸","水分充足","水分不足","口感扎實","口感鬆軟"};
            final String[] images_00 = {"甜","水分充足","口感鬆軟"};
            final String[] images_01 = {"甜","水分充足","口感鬆軟"};
            final String[][] check_all = {images_00, images_01};

            final int[] images_all = {R.mipmap.pitaya_01, R.mipmap.pitaya_02};

            final int[] images_sweet = {R.mipmap.pitaya_01, R.mipmap.pitaya_02};
            final int[] images_acid = {};

            final int[] images_enough = {R.mipmap.pitaya_01, R.mipmap.pitaya_02};
            final int[] images_notenough = {};

            final int[] images_solid = {};
            final int[] images_soft = {R.mipmap.pitaya_01, R.mipmap.pitaya_02};


            final CheckBox checkBox_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox checkBox_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox checkBox_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox checkBox_notenough = (CheckBox) v1.findViewById(R.id.notenough);
            final CheckBox checkBox_soft = (CheckBox) v1.findViewById(R.id.soft);
            final CheckBox checkBox_solid = (CheckBox) v1.findViewById(R.id.solid);
            final ImageButton checkback = (ImageButton) v1.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v1.findViewById(R.id.checkgo);





            final AlertDialog alert = builder.create();

            alert.setView(v1);
            alert.show();

            checkback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            checkback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            checkgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    union(onerow_images, tworow_images, threerow_images);

                    if (checkBox_sweet.isChecked()){
                        check_result.add(check[0]);
                    }
                    if (checkBox_acid.isChecked()){
                        check_result.add(check[1]);
                    }
                    if (checkBox_enough.isChecked()){
                        check_result.add(check[2]);
                    }
                    if (checkBox_notenough.isChecked()){
                        check_result.add(check[3]);
                    }
                    if (checkBox_solid.isChecked()){
                        check_result.add(check[4]);
                    }
                    if (checkBox_soft.isChecked()){
                        check_result.add(check[5]);
                    }

                    //根據圖片新增二維
                    for (int i=0 ; i < images_array.size() ; i++){
                        all_result.add(new ArrayList<String>());
                    }

                    //將篩選結果放入到該圖片的位置
                    for (int i=0 ; i < images_array.size() ; i++) {
                        for (int j=0 ; j < images_all.length ; j++) {

                            //判斷輸出照片在全部照片照片的所在位置
                            if (images_array.get(i) == images_all[j]) {
                                for (int h = 0 ; h < check_result.size() ; h++){
                                    for (int h1 = 0 ; h1 < check_all[j].length ; h1++){
                                        //判斷篩選結果是否符合
                                        if (check_result.get(h) == check_all[j][h1]){
                                            all_result.get(i).add(check_result.get(h));
                                        }
                                    }
                                }
                            }
                        }
                    }



                    Bundle bag =new Bundle();
                    Bundle bag2 =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    bag2.putSerializable("text_put", all_result);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.putExtras(bag2);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            //甜
            checkBox_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_sweet.isChecked()){
                        checkBox_sweet.setTextColor(Color.parseColor("#CD2626"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_acid.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_sweet[i]);
                                onerow_equals[i] = 1;
                            }
                        }

                        //取消acid的設定
                        checkBox_acid.setChecked(false);
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));


                        //刪除images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });


            //酸
            checkBox_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_acid.isChecked()){
                        checkBox_acid.setTextColor(Color.parseColor("#CD2626"));

                        //取消sweet的設定
                        checkBox_sweet.setChecked(false);
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_sweet.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_acid[i]);
                                onerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //水分足夠
            checkBox_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_enough.isChecked()){
                        checkBox_enough.setTextColor(Color.parseColor("#CD2626"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_notenough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_enough[i]);
                                tworow_equals[i] = 1;
                            }
                        }



                        //取消notenough的設定
                        checkBox_notenough.setChecked(false);
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }

                    }

                }
            });


            //水分不足
            checkBox_notenough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_notenough.isChecked()){
                        checkBox_notenough.setTextColor(Color.parseColor("#CD2626"));

                        //取消enough
                        checkBox_enough.setChecked(false);
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_enough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_notenough[i]);
                                tworow_equals[i] = 1;
                            }
                        }

                    }
                    else{
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }
                    }

                }
            });
            checkBox_solid.setChecked(false);
            //口感扎實
            checkBox_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_solid.isChecked()){
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));

                        //取消soft的設定
                        checkBox_solid.setChecked(false);
                    }
                    else{
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));
                        checkBox_soft.setChecked(true);
                    }

                }
            });


            //口感鬆軟
            if (checkBox_soft.isChecked()){
                //
            }
            else{
                checkBox_soft.setChecked(true);
                checkBox_soft.setTextColor(Color.parseColor("#CD2626"));
                // add image
            }
            if (checkBox_soft.isChecked()){
                checkBox_soft.setTextColor(Color.parseColor("#CD2626"));

                //取消solid
                checkBox_solid.setChecked(false);
                checkBox_solid.setTextColor(Color.parseColor("#0072e3"));


                //判斷是否存在，不在則新增images
                for (int i = 0 ; i < images_solid.length ; i++){
                    if (threerow_equals[i] == 1){
                        threerow_images.remove(0);
                        threerow_equals[i] = 0;
                    }
                }


                //新增apple_images
                for (int i = 0 ; i < images_soft.length ; i++) {
                    if (threerow_equals[i] == 0){
                        threerow_images.add(images_soft[i]);
                        threerow_equals[i] = 1;
                    }
                }
            }

            checkBox_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox_soft.setTextColor(Color.parseColor("#CD2626"));
                    checkBox_soft.setChecked(true);

                    //刪除images
                    for (int i = 0 ; i < images_soft.length ; i++) {
                        if (threerow_equals[i] == 1){
                            threerow_images.remove(0);
                            threerow_equals[i] = 0;
                        }
                    }
                }
            });


        }
    };


    //水梨
    ImageButton.OnClickListener pearAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v1 = getLayoutInflater().inflate(R.layout.check_view2, null);


            images_array = new ArrayList<Integer>();
            all_result = new ArrayList<ArrayList<String>>();
            check_result = new ArrayList<String>();

            final int[] onerow_equals= {0,0,0,0};
            onerow_images = new ArrayList<Integer>();
            final int[] tworow_equals= {0,0,0,0};
            tworow_images = new ArrayList<Integer>();
            final int[] threerow_equals= {0,0,0,0};
            threerow_images = new ArrayList<Integer>();

            final String[] check = {"甜","酸","水分充足","水分不足","口感扎實","口感鬆軟"};
            final String[] images_00 = {"甜","水分充足","口感扎實"};
            final String[] images_01 = {"甜","水分充足","口感扎實"};
            final String[] images_02 = {"酸","水分充足","口感扎實"};
            final String[] images_03 = {"甜","水分充足","口感扎實"};
            final String[][] check_all = {images_00, images_01, images_02, images_03};

            final int[] images_all = {R.mipmap.pear_01, R.mipmap.pear_02, R.mipmap.pear_03, R.mipmap.pear_04};

            final int[] images_sweet = {R.mipmap.pear_01, R.mipmap.pear_02, R.mipmap.pear_04};
            final int[] images_acid = {R.mipmap.pear_03};

            final int[] images_enough = {R.mipmap.pear_01, R.mipmap.pear_02, R.mipmap.pear_03, R.mipmap.pear_04};
            final int[] images_notenough = {};

            final int[] images_solid = {R.mipmap.pear_01, R.mipmap.pear_02, R.mipmap.pear_03, R.mipmap.pear_04};
            final int[] images_soft = {};


            final CheckBox checkBox_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox checkBox_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox checkBox_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox checkBox_notenough = (CheckBox) v1.findViewById(R.id.notenough);
            final CheckBox checkBox_soft = (CheckBox) v1.findViewById(R.id.soft);
            final CheckBox checkBox_solid = (CheckBox) v1.findViewById(R.id.solid);
            final ImageButton checkback = (ImageButton) v1.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v1.findViewById(R.id.checkgo);





            final AlertDialog alert = builder.create();

            alert.setView(v1);
            alert.show();

            checkback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            checkgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    union(onerow_images, tworow_images, threerow_images);

                    if (checkBox_sweet.isChecked()){
                        check_result.add(check[0]);
                    }
                    if (checkBox_acid.isChecked()){
                        check_result.add(check[1]);
                    }
                    if (checkBox_enough.isChecked()){
                        check_result.add(check[2]);
                    }
                    if (checkBox_notenough.isChecked()){
                        check_result.add(check[3]);
                    }
                    if (checkBox_solid.isChecked()){
                        check_result.add(check[4]);
                    }
                    if (checkBox_soft.isChecked()){
                        check_result.add(check[5]);
                    }

                    //根據圖片新增二維
                    for (int i=0 ; i < images_array.size() ; i++){
                        all_result.add(new ArrayList<String>());
                    }

                    //將篩選結果放入到該圖片的位置
                    for (int i=0 ; i < images_array.size() ; i++) {
                        for (int j=0 ; j < images_all.length ; j++) {

                            //判斷輸出照片在全部照片照片的所在位置
                            if (images_array.get(i) == images_all[j]) {
                                for (int h = 0 ; h < check_result.size() ; h++){
                                    for (int h1 = 0 ; h1 < check_all[j].length ; h1++){
                                        //判斷篩選結果是否符合
                                        if (check_result.get(h) == check_all[j][h1]){
                                            all_result.get(i).add(check_result.get(h));
                                        }
                                    }
                                }
                            }
                        }
                    }



                    Bundle bag =new Bundle();
                    Bundle bag2 =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    bag2.putSerializable("text_put", all_result);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.putExtras(bag2);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            //甜
            checkBox_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_sweet.isChecked()){
                        checkBox_sweet.setTextColor(Color.parseColor("#CD2626"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_acid.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_sweet[i]);
                                onerow_equals[i] = 1;
                            }
                        }

                        //取消acid的設定
                        checkBox_acid.setChecked(false);
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));


                        //刪除images
                        for (int i = 0 ; i < images_sweet.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });


            //酸
            checkBox_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_acid.isChecked()){
                        checkBox_acid.setTextColor(Color.parseColor("#CD2626"));

                        //取消sweet的設定
                        checkBox_sweet.setChecked(false);
                        checkBox_sweet.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_sweet.length ; i++){
                            if (onerow_equals[i] == 1){
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 0){
                                onerow_images.add(images_acid[i]);
                                onerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_acid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_acid.length ; i++) {
                            if (onerow_equals[i] == 1) {
                                onerow_images.remove(0);
                                onerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //水分足夠
            checkBox_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_enough.isChecked()){
                        checkBox_enough.setTextColor(Color.parseColor("#CD2626"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_notenough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_enough[i]);
                                tworow_equals[i] = 1;
                            }
                        }



                        //取消notenough的設定
                        checkBox_notenough.setChecked(false);
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));
                    }
                    else{
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_enough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }

                    }

                }
            });


            //水分不足
            checkBox_notenough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_notenough.isChecked()){
                        checkBox_notenough.setTextColor(Color.parseColor("#CD2626"));

                        //取消enough
                        checkBox_enough.setChecked(false);
                        checkBox_enough.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_enough.length ; i++){
                            if (tworow_equals[i] == 1){
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 0){
                                tworow_images.add(images_notenough[i]);
                                tworow_equals[i] = 1;
                            }
                        }

                    }
                    else{
                        checkBox_notenough.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_notenough.length ; i++) {
                            if (tworow_equals[i] == 1) {
                                tworow_images.remove(0);
                                tworow_equals[i] = 0;
                            }
                        }
                    }

                }
            });

            //口感扎實
            checkBox_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_solid.isChecked()){
                        checkBox_solid.setTextColor(Color.parseColor("#CD2626"));

                        //取消soft的設定
                        checkBox_soft.setChecked(false);
                        checkBox_soft.setTextColor(Color.parseColor("#0072e3"));

                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_soft.length ; i++){
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_solid.length ; i++) {
                            if (threerow_equals[i] == 0){
                                threerow_images.add(images_solid[i]);
                                threerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_solid.length ; i++) {
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }
                    }

                }
            });


            //口感鬆軟
            checkBox_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_soft.isChecked()){
                        checkBox_soft.setTextColor(Color.parseColor("#CD2626"));

                        //取消solid
                        checkBox_solid.setChecked(false);
                        checkBox_solid.setTextColor(Color.parseColor("#0072e3"));


                        //判斷是否存在，不在則新增images
                        for (int i = 0 ; i < images_solid.length ; i++){
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }


                        //新增apple_images
                        for (int i = 0 ; i < images_soft.length ; i++) {
                            if (threerow_equals[i] == 0){
                                threerow_images.add(images_soft[i]);
                                threerow_equals[i] = 1;
                            }
                        }
                    }
                    else{
                        checkBox_soft.setTextColor(Color.parseColor("#0072e3"));

                        //刪除images
                        for (int i = 0 ; i < images_soft.length ; i++) {
                            if (threerow_equals[i] == 1){
                                threerow_images.remove(0);
                                threerow_equals[i] = 0;
                            }
                        }
                    }
                }
            });
        }
    };

    private void union(ArrayList<Integer> onerow_images, ArrayList<Integer> tworow_images, ArrayList<Integer> threerow_images) {
        this.images_array = new ArrayList<Integer>();

        final int[] onerow_equals= {0,0,0,0};
        this.onerow_images = onerow_images;
        final int[] tworow_equals= {0,0,0,0};
        this.tworow_images = tworow_images;
        final int[] threerow_equals= {0,0,0};
        this.threerow_images = threerow_images;

        this.images_array.addAll(onerow_images);
        this.images_array.removeAll(tworow_images);
        this.images_array.addAll(tworow_images);
        this.images_array.removeAll(threerow_images);
        this.images_array.addAll(threerow_images);

        Collections.sort(this.images_array);
    }

}


