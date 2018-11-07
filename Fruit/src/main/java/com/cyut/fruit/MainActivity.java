package com.cyut.fruit;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import com.cyut.fruit.R; // Explicit import needed for internal Google builds.

import static android.support.constraint.Constraints.TAG;
public class MainActivity extends Activity implements Animation.AnimationListener {

    int intNumber = 12;
    long lngDegrees = 0;
    SharedPreferences sharedPreferences;
    private ImageView imgv_pointer, imgv_roulette, couponResult;
    private ImageButton imgb_Classify, imgb_Start, imgb_Gift;
    TextView couponTitle, couponMessage;
    int result; //存放轉盤結果
    private TextView txv_timeview, txv_UserEmail;
    Dialog couponDialog;
    StorageReference storageRef;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase mdatabase;
    DatabaseReference databaseReference; // 取得資料庫的參照
    private Button btn_Logout, btn_Coupon, btn_Library, couponAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        couponDialog = new Dialog(this); //新建一個彈跳視窗

        /* 計時器背景程式 */
        startService(new Intent(this, BroadcastService.class));
        Log.i(TAG, "Started service");

        imgb_Start = (ImageButton) findViewById(R.id.imgb_Start);             //取得開始紐id
        imgv_pointer = (ImageView)findViewById(R.id.imgv_pointer);    //取得指針id
        imgv_roulette = (ImageView)findViewById(R.id.imgv_roulette);  //取得轉盤id
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.intNumber = this.sharedPreferences.getInt("INT_NUMBER",12);
        txv_timeview = (TextView)findViewById(R.id.txv_timeview); //取得抽獎Title

        // 取得禮物id
        imgb_Gift = (ImageButton)findViewById(R.id.imgb_Gift);
        imgb_Gift.setOnClickListener(imgb_GiftListner);
        // 取得百科id
        btn_Library = (Button)findViewById(R.id.btn_Library);
        btn_Library.setOnClickListener(btn_LibraryListner);
        // 取得辨識id
        imgb_Classify = (ImageButton)findViewById(R.id.imgb_Classify);
        imgb_Classify.setOnClickListener(mutiItemDialog_listneer);

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
        btn_Logout.setOnClickListener(btn_LogoutListner);
        btn_Coupon = (Button) findViewById(R.id.btn_Coupon); //取得優惠券按鈕id
        btn_Coupon.setOnClickListener(btn_CouponListner);

    }

    public void onAnimationStart(Animation animation)
    {
        imgb_Start.setVisibility(View.VISIBLE);

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
            /* 寫入事件 */
            String uid = (String) firebaseAuth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Data/User").child(uid).child("url");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image = couponurl1;
                    Coupon coupon = new Coupon(image);
                    databaseReference.push().setValue(coupon);
                    Toast.makeText(MainActivity.this, "寫入成功", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "寫入失敗", Toast.LENGTH_LONG).show();
                }
            });
            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* 新增彈跳 */
                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }

        else if (result == 2 || result == 3){
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
                    /* 新增彈跳 */
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
            /* 寫入事件 */
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
            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* 新增彈跳 */
                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }

        else if (result == 6 || result == 7){
            /* 總價個位數歸零 */
            couponDialog.setContentView(R.layout.coupon_dialog);
            couponResult = (ImageView) couponDialog.findViewById(R.id.imgv_CouponResult);
            final String couponurl2 = "https://firebasestorage.googleapis.com/v0/b/fruit-t2938.appspot.com/o/Roulette%2Fcoupon2.png?alt=media&token=761cec7a-e6ee-4030-8f34-70f639ac1ad3";
            Glide.with(this).load(couponurl2).into(couponResult);

            couponAccept = (Button) couponDialog.findViewById(R.id.btn_CouponAccept);
            couponTitle = (TextView) couponDialog.findViewById(R.id.txv_CouponTitle);
            couponMessage = (TextView) couponDialog.findViewById(R.id.txv_CouponMessage);
            /* 寫入事件 */
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
            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* 新增彈跳 */
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
                    /* 新增彈跳 */
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
            /* 寫入事件 */
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
            couponAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* 新增彈跳 */
                    couponDialog.dismiss();
                }
            });
            couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            couponDialog.show();
        }

        imgb_Start.setVisibility(View.VISIBLE);


        //設定計時器，計時10秒
        new CountDownTimer(10000 + 500,1000){
            //計時結束
            @Override
            public void onFinish() {
                imgb_Start.setEnabled(true); //啟用按鈕功能
                txv_timeview.setText("來抽獎吧!!");
            }
            //計時中
            @Override
            public void onTick(long millisUntilFinished) {
                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                txv_timeview.setText("抽獎倒數 " + hours + " : " + minutes + " : " + seconds);
            }

        }.start();
    }
    public void onAnimationRepeat(Animation animation) {

    }
    /* 開始抽獎事件 */
    public void onClickButtonRotation(View v) {
        // 彈跳動畫
        ImgbtnAnimation(imgb_Start);
        Handler imgbStartHandler = new Handler();
        imgbStartHandler.postDelayed(imgbStartDelay, 450);
        //轉盤旋轉
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
    /* 運行完動畫後關閉按鈕功能 */
    private Runnable imgbStartDelay = new Runnable() {
        public void run() {
            imgb_Start.setEnabled(false); //關閉按鈕功能
        }
    };

    //切換果藍有禮
    private ImageButton.OnClickListener imgb_GiftListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    // 彈跳動畫
                    ImgbtnAnimation(imgb_Gift);
                    Handler imgbGiftHandler = new Handler();
                    imgbGiftHandler.postDelayed(imgbGiftDelay, 450);

                }
            };
    /* 運行完動畫候切換至公告 */
    private Runnable imgbGiftDelay = new Runnable() {
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,GiftActivity.class);
            startActivity(intent);
        }
    };

    //切換優惠券, 先運行動畫
    private Button.OnClickListener btn_CouponListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    btnAnimation(btn_Coupon);
                    Handler btnCouponHandler = new Handler();
                    // 0.45秒後跳至優惠券畫面
                    btnCouponHandler.postDelayed(btnCouponDelay, 450);
                }
            };
    /* 運行完動畫執行切換 */
    private Runnable btnCouponDelay = new Runnable() {
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,RouletteCouponActivity.class);
            startActivity(intent);
        }
    };

    // 切換百科
    private Button.OnClickListener btn_LibraryListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    btnAnimation(btn_Library);
                    Handler btnLibraryHandler = new Handler();
                    // 0.45秒後跳至百科畫面
                    btnLibraryHandler.postDelayed(btnLibraryDelay, 450);
                }
            };
    /* 運行完動畫執行切換 */
    private Runnable btnLibraryDelay = new Runnable() {
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,LibraryHome.class);
            startActivity(intent);
        }
    };

    //切換辨識
    final ImageButton.OnClickListener mutiItemDialog_listneer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 彈跳動畫
            ImgbtnAnimation(imgb_Classify);
            Handler imgbClassifyHandler = new Handler();
            imgbClassifyHandler.postDelayed(imgbClassifyDelay, 450);
        }
    };
    /* 運行完動畫候切換至辨識Dialog */
    private Runnable imgbClassifyDelay = new Runnable() {
        public void run() {
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this, R.style.CustomDialog);
            View v2 = getLayoutInflater().inflate(R.layout.classifier_dialog, null);

            final ImageButton classifier_dialog_back = (ImageButton) v2.findViewById(R.id.classifier_dialog_back);
            final Button button1 = (Button) v2.findViewById(R.id.button);
            final Button button2 = (Button) v2.findViewById(R.id.button2);
            final Button button3 = (Button) v2.findViewById(R.id.button3);

            final android.support.v7.app.AlertDialog alert = builder.create();

            alert.setView(v2);
            alert.show();
            /* 關閉辨識對話框 */
            classifier_dialog_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAnimation(button1);
                    Handler button1Handler = new Handler();
                    button1Handler.postDelayed(button1Delay, 450);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAnimation(button2);
                    Handler button2Handler = new Handler();
                    button2Handler.postDelayed(button2Delay, 450);
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAnimation(button3);
                    Handler button3Handler = new Handler();
                    button3Handler.postDelayed(button3Delay, 450);
                }
            });
        }
    };
    /* 運行完動畫執行切換至產地 */
    private Runnable button1Delay = new Runnable() {
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, PlaceActivity.class);
            startActivity(intent);
        }
    };
    /* 運行完動畫執行切換至甜度 */
    private Runnable button2Delay = new Runnable() {
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ClassifierActivity.class);
            startActivity(intent);
        }
    };
    /* 運行完動畫執行切換至種類 */
    private Runnable button3Delay = new Runnable() {
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TypeActivity.class);
            startActivity(intent);
        }
    };

    // 登出事件
    private Button.OnClickListener btn_LogoutListner =
            new Button.OnClickListener() {
                public void onClick(View view) {
                    // 11/04新增彈跳動畫
                    btnAnimation(btn_Logout);
                    Handler btnLogoutHandler = new Handler();
                    btnLogoutHandler.postDelayed(btnLogoutDelay, 450);
                }
            };
    /* 運行完動畫執行登出事件 */
    private Runnable btnLogoutDelay = new Runnable() {
        public void run() {
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this,R.style.CustomDialog);
            final android.support.v7.app.AlertDialog alert = builder.create();
            View v = getLayoutInflater().inflate(R.layout.logout,null);

            alert.setView(v);
            alert.show();

            Button logout = (Button) v.findViewById(R.id.logout);
            Button back = (Button) v.findViewById(R.id.back);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();

                    //Google登出
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    GoogleSignIn.getClient(MainActivity.this, gso).signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "已登出",Toast.LENGTH_LONG).show();
                        }
                    });

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


    /* 處理返回鍵離開事件 */
    @Override
    public void onBackPressed() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this,R.style.CustomDialog);
        final android.support.v7.app.AlertDialog alert = builder.create();
        View v =getLayoutInflater().inflate(R.layout.exit_event,null);
        alert.setView(v);
        alert.show();
        Button exit = (Button) v.findViewById(R.id.exit);
        Button cancel = (Button) v.findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                finish();
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /* 10/30 Button動畫 */
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

    /* 11/04  ImageButton動畫*/
    public void ImgbtnAnimation(ImageButton ImgbtnSet) {
        /* 11/04 載入按鈕動畫 */
        Animation ImgbtnAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_bounce_anim);
        /* 11/04 設定按鈕的動畫 */
        ImgbtnSet.setAnimation(ImgbtnAnim);
        /* 11/04 設定反彈幅度0.05, 頻率8 */
        BounceInterpolator interpolator = new BounceInterpolator(0.05, 8);
        /* 11/04 套用反彈效果 */
        ImgbtnAnim.setInterpolator(interpolator);
        /* 11/04 運行按鈕動畫 */
        ImgbtnSet.startAnimation(ImgbtnAnim);
    }
}
