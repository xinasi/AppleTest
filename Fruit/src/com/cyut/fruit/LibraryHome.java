package com.cyut.fruit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

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

        // 取得首頁id
        Button btn_l2m = (Button)findViewById(R.id.btn_library2main);
        btn_l2m.setOnClickListener(btn_l2mListner);
        // 取得百科id
        Button btn_l2l = (Button)findViewById(R.id.btn_library2library);
        btn_l2l.setOnClickListener(btn_l2lListner);
        // 取得辨識id
        Button btn_l2c = (Button)findViewById(R.id.btn_library2classify);
        final android.app.AlertDialog mutiItemDialog = getMutiItemDialog(new String[]{"蘋果產地","蘋果甜度","水果種類"});
        btn_l2c.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                //顯示對話框
                mutiItemDialog.show();
            }
        });

    }

    ImageButton.OnClickListener appleAlertDialog_listneer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this);
            View v2 = getLayoutInflater().inflate(R.layout.check_view, null);
            final  ArrayList<Integer> images_array = new ArrayList<Integer>();
            final Switch switch_red = (Switch) v2.findViewById(R.id.switch_red);
            final Switch switch_blue = (Switch) v2.findViewById(R.id.switch_blue);
            final Switch switch_green = (Switch) v2.findViewById(R.id.switch_green);


            builder.setView(v2);
            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bag =new Bundle();
                            bag.putIntegerArrayList("image_put", images_array);
                            Intent intent = new Intent();
                            intent.putExtras(bag);
                            intent.setClass(LibraryHome.this, ImagesView.class);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            switch_red.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (switch_red.isChecked()){
                        images_array.add(R.mipmap.pitaya);
                        System.out.println(images_array);
                    }
                    else{
                        images_array.remove((Integer) R.mipmap.pitaya);
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
        public void onClick(View v){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(LibraryHome.this);
            View v1 = getLayoutInflater().inflate(R.layout.activity_check_view, null);
            final CheckBox ck_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox ck_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox ck_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox ck_n_enough = (CheckBox) v1.findViewById(R.id.n_enoght);
            final CheckBox ck_solid = (CheckBox) v1.findViewById(R.id.solid);
            final CheckBox ck_soft = (CheckBox) v1.findViewById(R.id.soft);
            final ArrayList <Integer> images_array = new ArrayList<Integer>();

            builder1.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bag =new Bundle();
                            bag.putIntegerArrayList("image_put", images_array);
                            Intent intent = new Intent();
                            intent.putExtras(bag);
                            intent.setClass(LibraryHome.this, ImagesView.class);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
            ck_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_sweet.isChecked()) {
                        images_array.add(R.mipmap.pitaya);
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_acid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_n_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_n_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_solid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_soft.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };

    ImageButton.OnClickListener kiwiAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this);
            View v1 = getLayoutInflater().inflate(R.layout.activity_check_view, null);            final CheckBox ck_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox ck_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox ck_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox ck_n_enough = (CheckBox) v1.findViewById(R.id.n_enoght);
            final CheckBox ck_solid = (CheckBox) v1.findViewById(R.id.solid);
            final CheckBox ck_soft = (CheckBox) v1.findViewById(R.id.soft);
            final ArrayList <Integer> images_array = new ArrayList<Integer>();

            builder.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bag =new Bundle();
                            bag.putIntegerArrayList("image_put", images_array);
                            Intent intent = new Intent();
                            intent.putExtras(bag);
                            intent.setClass(LibraryHome.this, ImagesView.class);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
            ck_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_sweet.isChecked()) {
                        images_array.add(R.mipmap.apple);
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_acid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_n_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_n_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_solid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_soft.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    ImageButton.OnClickListener dfAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this);
            View v1 = getLayoutInflater().inflate(R.layout.activity_check_view, null);
            final CheckBox ck_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox ck_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox ck_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox ck_n_enough = (CheckBox) v1.findViewById(R.id.n_enoght);
            final CheckBox ck_solid = (CheckBox) v1.findViewById(R.id.solid);
            final CheckBox ck_soft = (CheckBox) v1.findViewById(R.id.soft);
            final ArrayList <Integer> images_array = new ArrayList<Integer>();

            builder.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bag =new Bundle();
                            bag.putIntegerArrayList("image_put", images_array);
                            Intent intent = new Intent();
                            intent.putExtras(bag);
                            intent.setClass(LibraryHome.this, ImagesView.class);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
            ck_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_sweet.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_acid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_n_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_n_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_solid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_soft.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };

    ImageButton.OnClickListener pearAlertDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this);
            View v1 = getLayoutInflater().inflate(R.layout.activity_check_view, null);
            final CheckBox ck_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox ck_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox ck_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox ck_n_enough = (CheckBox) v1.findViewById(R.id.n_enoght);
            final CheckBox ck_solid = (CheckBox) v1.findViewById(R.id.solid);
            final CheckBox ck_soft = (CheckBox) v1.findViewById(R.id.soft);
            final ArrayList <Integer> images_array = new ArrayList<Integer>();

            builder.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bag =new Bundle();
                            bag.putIntegerArrayList("image_put", images_array);
                            Intent intent = new Intent();
                            intent.putExtras(bag);
                            intent.setClass(LibraryHome.this, ImagesView.class);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
            ck_sweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_sweet.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_acid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_acid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_n_enough.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_n_enough.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_solid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_solid.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ck_soft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ck_soft.isChecked()) {
                        Toast.makeText(LibraryHome.this, "images add to array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LibraryHome.this, "images remove to array", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };

    // 切換首頁
    private Button.OnClickListener btn_l2mListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent main_intent = new Intent();
                    main_intent.setClass(LibraryHome.this,MainActivity.class);
                    startActivity(main_intent);
                }
            };
    // 切換百科
    private Button.OnClickListener btn_l2lListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent pedia_intent = new Intent();
                    pedia_intent.setClass(LibraryHome.this, LibraryHome.class);
                    startActivity(pedia_intent);
                }
            };

    public android.app.AlertDialog getMutiItemDialog(final String[] items) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        //設定對話框內的項目
        builder.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                //當使用者點選對話框時，切換不同頁面
                if (items[which] == "蘋果產地") {
                    Intent intent = new Intent();
                    intent.setClass(LibraryHome.this,PlaceActivity.class);
                    startActivity(intent);
                }
                else if (items[which] == "蘋果甜度") {
                    Intent intent = new Intent();
                    intent.setClass(LibraryHome.this,ClassifierActivity.class);
                    startActivity(intent);
                }
                else if (items[which] == "水果種類"){
                    Intent intent = new Intent();
                    intent.setClass(LibraryHome.this,TypeActivity.class);
                    startActivity(intent);
                }

            }
        });
        return builder.create();
    }

}


