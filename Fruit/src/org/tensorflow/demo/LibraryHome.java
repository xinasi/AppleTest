package org.tensorflow.demo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class LibraryHome extends AppCompatActivity {
    ArrayList <Integer> images_array = new ArrayList<Integer>();

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

    }

    ImageButton.OnClickListener appleAlertDialog_listneer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryHome.this);
            View v1 = getLayoutInflater().inflate(R.layout.activity_check_view, null);
            final CheckBox ck_sweet = (CheckBox) v1.findViewById(R.id.sweet);
            final CheckBox ck_acid = (CheckBox) v1.findViewById(R.id.acid);
            final CheckBox ck_enough = (CheckBox) v1.findViewById(R.id.enough);
            final CheckBox ck_n_enough = (CheckBox) v1.findViewById(R.id.n_enoght);
            final CheckBox ck_solid = (CheckBox) v1.findViewById(R.id.solid);
            final CheckBox ck_soft = (CheckBox) v1.findViewById(R.id.soft);

            builder.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            senddata();
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
                        images_array.add(R.mipmap.fruiticon);
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

            builder1.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            senddata();
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

            builder.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            senddata();
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

            builder.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            senddata();
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

            builder.setView(v1)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            senddata();
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

    public void senddata(){
        Bundle bag =new Bundle();
        bag.putIntegerArrayList("image_put", images_array);
        Intent intent = new Intent();
        intent.putExtras(bag);
        intent.setClass(LibraryHome.this, ImagesView.class);
        startActivity(intent);
    }

}


