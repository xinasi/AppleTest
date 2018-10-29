package com.cyut.fruit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import com.cyut.fruit.R;

import java.util.ArrayList;

public class LibraryHome extends AppCompatActivity {


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

    ImageButton.OnClickListener appleAlertDialog_listneer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v2 = getLayoutInflater().inflate(R.layout.check_view, null);

            final  ArrayList<Integer> images_array = new ArrayList<Integer>();
            final Switch switch_red = (Switch) v2.findViewById(R.id.switch_red);
            final Switch switch_blue = (Switch) v2.findViewById(R.id.switch_blue);
            final Switch switch_green = (Switch) v2.findViewById(R.id.switch_green);
            final ImageButton checkback = (ImageButton) v2.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v2.findViewById(R.id.checkgo);

            final AlertDialog alert = builder.create();

            alert.setView(v2);
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
                    Bundle bag =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            switch_red.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_red.isChecked()){
                        images_array.add(R.mipmap.testresult);
                    }
                    else{
                        images_array.remove((Integer) R.mipmap.testresult);
                    }
                }
            });

            switch_blue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_blue.isChecked()){
                        images_array.add(R.mipmap.apple);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.apple);
                    }
                }
            });

            switch_green.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_green.isChecked()){
                        images_array.add(R.mipmap.kiwi);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.kiwi);
                    }
                }
            });
        }
    };

    ImageButton.OnClickListener guavaAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v2 = getLayoutInflater().inflate(R.layout.check_view, null);

            final  ArrayList<Integer> images_array = new ArrayList<Integer>();
            final Switch switch_red = (Switch) v2.findViewById(R.id.switch_red);
            final Switch switch_blue = (Switch) v2.findViewById(R.id.switch_blue);
            final Switch switch_green = (Switch) v2.findViewById(R.id.switch_green);
            final ImageButton checkback = (ImageButton) v2.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v2.findViewById(R.id.checkgo);

            final AlertDialog alert = builder.create();

            alert.setView(v2);
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
                    Bundle bag =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            switch_red.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_red.isChecked()){
                        images_array.add(R.mipmap.testresult);
                    }
                    else{
                        images_array.remove((Integer) R.mipmap.testresult);
                    }
                }
            });

            switch_blue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_blue.isChecked()){
                        images_array.add(R.mipmap.apple);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.apple);
                    }
                }
            });

            switch_green.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_green.isChecked()){
                        images_array.add(R.mipmap.kiwi);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.kiwi);
                    }
                }
            });
        }
    };

    ImageButton.OnClickListener kiwiAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v2 = getLayoutInflater().inflate(R.layout.check_view, null);

            final  ArrayList<Integer> images_array = new ArrayList<Integer>();
            final Switch switch_red = (Switch) v2.findViewById(R.id.switch_red);
            final Switch switch_blue = (Switch) v2.findViewById(R.id.switch_blue);
            final Switch switch_green = (Switch) v2.findViewById(R.id.switch_green);
            final ImageButton checkback = (ImageButton) v2.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v2.findViewById(R.id.checkgo);

            final AlertDialog alert = builder.create();

            switch_green.setChecked(true);

            alert.setView(v2);
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
                    Bundle bag =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            switch_red.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_red.isChecked()){
                        images_array.add(R.mipmap.testresult);
                    }
                    else{
                        images_array.remove((Integer) R.mipmap.testresult);
                    }
                }
            });

            switch_blue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_blue.isChecked()){
                        images_array.add(R.mipmap.apple);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.apple);
                    }
                }
            });

            switch_green.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_green.isChecked()){
                        images_array.add(R.mipmap.kiwi);
                    }
                    else{
                        switch_green.setChecked(true);
                    }
                }
            });
        }
    };

    ImageButton.OnClickListener dfAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v2 = getLayoutInflater().inflate(R.layout.check_view, null);

            final  ArrayList<Integer> images_array = new ArrayList<Integer>();
            final Switch switch_red = (Switch) v2.findViewById(R.id.switch_red);
            final Switch switch_blue = (Switch) v2.findViewById(R.id.switch_blue);
            final Switch switch_green = (Switch) v2.findViewById(R.id.switch_green);
            final ImageButton checkback = (ImageButton) v2.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v2.findViewById(R.id.checkgo);

            final AlertDialog alert = builder.create();

            alert.setView(v2);
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
                    Bundle bag =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            switch_red.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_red.isChecked()){
                        images_array.add(R.mipmap.testresult);
                    }
                    else{
                        images_array.remove((Integer) R.mipmap.testresult);
                    }
                }
            });

            switch_blue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_blue.isChecked()){
                        images_array.add(R.mipmap.apple);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.apple);
                    }
                }
            });

            switch_green.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_green.isChecked()){
                        images_array.add(R.mipmap.kiwi);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.kiwi);
                    }
                }
            });
        }
    };

    ImageButton.OnClickListener pearAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            final AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this,R.style.CustomDialog);
            View v2 = getLayoutInflater().inflate(R.layout.check_view, null);

            final  ArrayList<Integer> images_array = new ArrayList<Integer>();
            final Switch switch_red = (Switch) v2.findViewById(R.id.switch_red);
            final Switch switch_blue = (Switch) v2.findViewById(R.id.switch_blue);
            final Switch switch_green = (Switch) v2.findViewById(R.id.switch_green);
            final ImageButton checkback = (ImageButton) v2.findViewById(R.id.checkback);
            final ImageButton checkgo = (ImageButton) v2.findViewById(R.id.checkgo);

            final AlertDialog alert = builder.create();

            alert.setView(v2);
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
                    Bundle bag =new Bundle();
                    bag.putIntegerArrayList("image_put", images_array);
                    Intent intent = new Intent();
                    intent.putExtras(bag);
                    intent.setClass(LibraryHome.this, ImagesView.class);
                    startActivity(intent);
                }
            });

            switch_red.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_red.isChecked()){
                        images_array.add(R.mipmap.testresult);
                    }
                    else{
                        images_array.remove((Integer) R.mipmap.testresult);
                    }
                }
            });

            switch_blue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    if (switch_blue.isChecked()){
                        //
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.apple);
                    }
                }
            });

            switch_green.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_green.isChecked()){
                        images_array.add(R.mipmap.kiwi);
                    }
                    else{
                        images_array.remove((Integer)R.mipmap.kiwi);
                    }
                }
            });
        }

    };

}


