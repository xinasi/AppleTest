package com.cyut.fruit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

import java.util.Random;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import com.cyut.fruit.R; // Explicit import needed for internal Google builds.


public class MainActivity extends Activity implements Animation.AnimationListener {

    int intNumber = 12;
    long lngDegrees = 0;
    SharedPreferences sharedPreferences;
    ImageView imgv_pointer, imgv_roulette, couponResult;
    ImageButton imgb_start, imgb_gift;
    Button couponAccept, btn_classify, btn_main, btn_library;
    TextView couponTitle, couponMessage;
    int result; //存放轉盤結果
    private TextView txv_timeview, txv_UserEmail;
    Dialog couponDialog;
    StorageReference storageRef;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase mdatabase;
    DatabaseReference databaseReference; // 取得資料庫的參照
    private Button btn_Logout, btn_Coupon;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        couponDialog = new Dialog(this); //新建一個彈跳視窗

        imgb_start = (ImageButton) findViewById(R.id.imgb_start);             //取得開始紐id
        imgv_pointer = (ImageView)findViewById(R.id.imgv_pointer);    //取得指針id
        imgv_roulette = (ImageView)findViewById(R.id.imgv_roulette);  //取得轉盤id
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.intNumber = this.sharedPreferences.getInt("INT_NUMBER",12);
        txv_timeview = (TextView)findViewById(R.id.txv_timeview); //取得抽獎Title

        // 取得禮物id
        ImageButton btn_gift = (ImageButton)findViewById(R.id.imgb_gift);
        btn_gift.setOnClickListener(imgb_giftListner);
        // 取得百科id
        Button btn_library = (Button)findViewById(R.id.btn_library);
        btn_library.setOnClickListener(btn_libraryListner);
        // 取得辨識id
        ImageButton btn_classify = (ImageButton)findViewById(R.id.btn_classify);
        btn_classify.setOnClickListener(mutiItemDialog_listneer);

        /* 身分驗證 */
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        txv_UserEmail = (TextView) findViewById(R.id.txv_UserEmail); //取得歡迎使用者id
        String [] EmailSplit = user.getEmail().split("@");
        txv_UserEmail.setText(EmailSplit[0]); //顯示歡迎詞
        btn_Logout = (Button) findViewById(R.id.btn_Logout); //取得登出id
        btn_Logout.setOnClickListener(btn_LogouListner);
        btn_Coupon = (Button) findViewById(R.id.btn_Coupon); //取得優惠券按鈕id
        btn_Coupon.setOnClickListener(btn_CouponListner);

    }

    public void onAnimationStart(Animation animation)
    {
        imgb_start.setVisibility(View.VISIBLE);

    }


    public void onAnimationEnd(Animation animation) {
        result = (int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDegrees) / (360.0d / ((double)this.intNumber))));

        if (result == 1 || result == 12) {
            /* 總價減五元 */
            couponDialog.setContentView(R.layout.coupon_dialog);
            couponResult = (ImageView) couponDialog.findViewById(R.id.imgv_CouponResult);
            final String couponurl1 = "https://firebasestorage.googleapis.com/v0/b/fruit-t2938.appspot.com/o/Roulette%2Fcoupon1.png?alt=media&token=24b64623-c456-47d6-aaf1-fcb36aa154c8";
            Glide.with(this).load(couponurl1).into(couponResult);

            couponAccept = (Button) couponDialog.findViewById(R.id.btn_CouponAccept);
            couponTitle = (TextView) couponDialog.findViewById(R.id.txv_CouponTitle);
            couponMessage = (TextView) couponDialog.findViewById(R.id.txv_CouponMessage);


            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uid = (String) firebaseAuth.getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Data/User").child(uid).child("url");


                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String image = couponurl1;
                            Coupon coupon = new Coupon(image);
                            databaseReference.push().setValue(coupon);
                            Toast.makeText(MainActivity.this, "寫入成功", Toast.LENGTH_LONG).show();
                            //couponDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "寫入失敗", Toast.LENGTH_LONG).show();
                        }
                    });

                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }
        else if (result == 2 || result == 3){
            /* 總價個位數歸零 */
            couponDialog.setContentView(R.layout.coupon_dialog);
            couponResult = (ImageView) couponDialog.findViewById(R.id.imgv_CouponResult);
            final String couponurl2 = "https://firebasestorage.googleapis.com/v0/b/fruit-t2938.appspot.com/o/Roulette%2Fcoupon2.png?alt=media&token=761cec7a-e6ee-4030-8f34-70f639ac1ad3";
            Glide.with(this).load(couponurl2).into(couponResult);

            couponAccept = (Button) couponDialog.findViewById(R.id.btn_CouponAccept);
            couponTitle = (TextView) couponDialog.findViewById(R.id.txv_CouponTitle);
            couponMessage = (TextView) couponDialog.findViewById(R.id.txv_CouponMessage);


            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uid = (String) firebaseAuth.getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Data/User").child(uid).child("url");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String image = couponurl2;
                            Coupon coupon = new Coupon(image);
                            databaseReference.push().setValue(coupon);
                            Toast.makeText(MainActivity.this, "寫入成功", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "寫入失敗", Toast.LENGTH_LONG).show();
                        }
                    });

                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }
        else if (result == 4 || result == 5){
            /* 總價八五折 */
            couponDialog.setContentView(R.layout.coupon_dialog);
            couponResult = (ImageView) couponDialog.findViewById(R.id.imgv_CouponResult);
            final String couponurl3 = "https://firebasestorage.googleapis.com/v0/b/fruit-t2938.appspot.com/o/Roulette%2Fcoupon3.png?alt=media&token=db456b02-dcbc-4382-b1b9-8ca4ecc682d2";
            Glide.with(this).load(couponurl3).into(couponResult);

            couponAccept = (Button) couponDialog.findViewById(R.id.btn_CouponAccept);
            couponTitle = (TextView) couponDialog.findViewById(R.id.txv_CouponTitle);
            couponMessage = (TextView) couponDialog.findViewById(R.id.txv_CouponMessage);


            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uid = (String) firebaseAuth.getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Data/User").child(uid).child("url");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String image = couponurl3;
                            Coupon coupon = new Coupon(image);
                            databaseReference.push().setValue(coupon);
                            Toast.makeText(MainActivity.this, "寫入成功", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "寫入失敗", Toast.LENGTH_LONG).show();
                        }
                    });

                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }
        else if (result == 6 || result == 7){
            /* 下次加油 */
            couponDialog.setContentView(R.layout.coupon_dialog_lose);
            couponResult = (ImageView) couponDialog.findViewById(R.id.imgv_CouponResultLose);
            String couponurl4 = "https://firebasestorage.googleapis.com/v0/b/fruit-t2938.appspot.com/o/Roulette%2Fcoupon4.png?alt=media&token=f0ef6b4f-1c49-4004-b97a-0a755e39917f";
            Glide.with(this).load(couponurl4).into(couponResult);

            couponAccept = (Button) couponDialog.findViewById(R.id.btn_CouponAcceptLose);
            couponTitle = (TextView) couponDialog.findViewById(R.id.txv_CouponTitleLose);
            couponMessage = (TextView) couponDialog.findViewById(R.id.txv_CouponMessageLose);

            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }
        else if (result == 8 || result == 9){
            /* 銘謝惠顧 */
            couponDialog.setContentView(R.layout.coupon_dialog_lose);
            couponResult = (ImageView) couponDialog.findViewById(R.id.imgv_CouponResultLose);
            String couponurl5 = "https://firebasestorage.googleapis.com/v0/b/fruit-t2938.appspot.com/o/Roulette%2Fcoupon5.png?alt=media&token=172dffb5-d9c8-4ac0-9ad7-6acb3c5c32b9";
            Glide.with(this).load(couponurl5).into(couponResult);

            couponAccept = (Button) couponDialog.findViewById(R.id.btn_CouponAcceptLose);
            couponTitle = (TextView) couponDialog.findViewById(R.id.txv_CouponTitleLose);
            couponMessage = (TextView) couponDialog.findViewById(R.id.txv_CouponMessageLose);


            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }
        else if (result == 10 || result == 11){
            /* 總價九折 */
            couponDialog.setContentView(R.layout.coupon_dialog);
            couponResult = (ImageView) couponDialog.findViewById(R.id.imgv_CouponResult);
            final String couponurl6 = "https://firebasestorage.googleapis.com/v0/b/fruit-t2938.appspot.com/o/Roulette%2Fcoupon6.png?alt=media&token=81e03e89-8093-4444-b1ef-1bf34ce0acf5";
            Glide.with(this).load(couponurl6).into(couponResult);

            couponAccept = (Button) couponDialog.findViewById(R.id.btn_CouponAccept);
            couponTitle = (TextView) couponDialog.findViewById(R.id.txv_CouponTitle);
            couponMessage = (TextView) couponDialog.findViewById(R.id.txv_CouponMessage);


            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uid = (String) firebaseAuth.getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Data/User").child(uid).child("url");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String image = couponurl6;
                            Coupon coupon = new Coupon(image);
                            databaseReference.push().setValue(coupon);
                            Toast.makeText(MainActivity.this, "寫入成功", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "寫入失敗", Toast.LENGTH_LONG).show();
                        }
                    });

                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }

        imgb_start.setVisibility(View.VISIBLE);
        //設定計時器，計時10秒
        new CountDownTimer(10000,1000){

            //計時結束
            @Override
            public void onFinish() {
                imgb_start.setEnabled(true); //啟用按鈕功能
                txv_timeview.setText("來抽獎吧!!");
            }
            //計時中
            @Override
            public void onTick(long millisUntilFinished) {
                txv_timeview.setText("距離下次抽獎還剩: " + millisUntilFinished/1000 + " 秒");
            }

        }.start();
    }
    public void onAnimationRepeat(Animation animation) {

    }

    public void onClickButtonRotation(View v) {
        imgb_start.setEnabled(false); //關閉按鈕功能
        int ran = new Random().nextInt(360) + 3600;
        RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDegrees, (float)
                (this.lngDegrees + ((long)ran)),1,0.5f,1,0.5f);

        this.lngDegrees = (this.lngDegrees + ((long)ran)) % 360;
        rotateAnimation.setDuration((long)ran);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(this);
        imgv_roulette.setAnimation(rotateAnimation);
        imgv_roulette.startAnimation(rotateAnimation);

    }
    //切換果藍有禮
    private Button.OnClickListener imgb_giftListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,GiftActivity.class);
                    startActivity(intent);
                }
            };

    //切換優惠券
    private Button.OnClickListener btn_CouponListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,RouletteCouponActivity.class);
                    startActivity(intent);
                }
            };

    /*
    private Runnable btn_main_delay = new Runnable() {
        public void run() {
            // 0.45秒後跳至遊戲說明
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,LibraryHome.class);
            startActivity(intent);
        }
    };
    */
    // 切換百科
    private Button.OnClickListener btn_libraryListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent pedia_intent = new Intent();
                    pedia_intent.setClass(MainActivity.this,LibraryHome.class);
                    startActivity(pedia_intent);
                }
            };
    //切換辨識
    ImageButton.OnClickListener mutiItemDialog_listneer = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this,R.style.CustomDialog);
            View v2 = getLayoutInflater().inflate(R.layout.classifier_dialog, null);

            final Button button1 = (Button)  v2.findViewById(R.id.button);
            final Button button2 = (Button)  v2.findViewById(R.id.button2);
            final Button button3 = (Button)  v2.findViewById(R.id.button3);

            final android.support.v7.app.AlertDialog alert = builder.create();

            alert.setView(v2);
            alert.show();


            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, PlaceActivity.class);
                    startActivity(intent);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ClassifierActivity.class);
                    startActivity(intent);
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, TypeActivity.class);
                    startActivity(intent);
                }
            });

        }

    };

    // 登出事件
    private Button.OnClickListener btn_LogouListner =
            new Button.OnClickListener() {
                public void onClick(View view) {
                    final AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                    final AlertDialog alert = builder.create();
                    View v =getLayoutInflater().inflate(R.layout.logout,null);

                    alert.setView(v);
                    alert.show();

                    Button logout = (Button) v.findViewById(R.id.logout);
                    Button back = (Button) v.findViewById(R.id.back);

                    logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.cancel();
                        }
                    });


                }
            };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /* 10/30 按鈕動畫 */
    public void btnAnimation(Button btnSet) {
        /* 10/10 載入按鈕動畫 */
        Animation btnAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_bounce_anim);
        /* 10/10 設定按鈕的動畫 */
        btnSet.setAnimation(btnAnim);
        /* 10/10 設定反彈幅度0.05, 頻率8 */
        BounceInterpolator interpolator = new BounceInterpolator(0.05, 8);
        /* 10/10 套用反彈效果 */
        btnAnim.setInterpolator(interpolator);
        /* 10/10 運行按鈕動畫 */
        btnSet.startAnimation(btnAnim);
    }
}
