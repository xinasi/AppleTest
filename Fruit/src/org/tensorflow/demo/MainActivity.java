package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.AlertDialog.Builder;
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
import org.tensorflow.demo.R; // Explicit import needed for internal Google builds.

public class MainActivity extends Activity implements Animation.AnimationListener {

    int intNumber = 5;
    long lngDegrees = 0;
    SharedPreferences sharedPreferences;
    ImageView imgv_pointer,imgv_roulette;
    Button btn_start;
    String abc;
    int result; //存放轉盤結果
    private TextView txv_timeview, txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button)findViewById(R.id.btn_start);             //取得開始紐id
        imgv_pointer = (ImageView)findViewById(R.id.imgv_pointer);    //取得指針id
        imgv_roulette = (ImageView)findViewById(R.id.imgv_roulette);  //取得轉盤id
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.intNumber = this.sharedPreferences.getInt("INT_NUMBER",5);
        txv_timeview = (TextView)findViewById(R.id.txv_timeview); //取得抽獎Title
        txv = (TextView)findViewById(R.id.txv_time); //取得計時器id

        // 取得禮物id
        Button btn_gift = (Button)findViewById(R.id.btn_gift);
        btn_gift.setOnClickListener(btn_giftListner);
        // 取得首頁id
        Button btn_main = (Button)findViewById(R.id.btn_main);
        btn_main.setOnClickListener(btn_mainListner);
        // 取得百科id
        Button btn_library = (Button)findViewById(R.id.btn_library);
        btn_library.setOnClickListener(btn_libraryListner);
        // 取得辨識id
        Button btn_classify = (Button)findViewById(R.id.btn_classify);
        final AlertDialog mutiItemDialog = getMutiItemDialog(new String[]{"蘋果產地","水果甜度","水果種類"});
        btn_classify.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                //顯示對話框
                mutiItemDialog.show();
            }
        });

    }

    public void onAnimationStart(Animation animation)
    {
        btn_start.setVisibility(View.VISIBLE);

    }

    public void onAnimationEnd(Animation animation) {
        txv_timeview.setText("距離下次抽獎還有: ");

        result = (int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDegrees) / (360.0d / ((double)this.intNumber))));

        if (result == 1) {
            Toast.makeText(this, "恭喜獲得蘋果折價券!!", Toast.LENGTH_SHORT).show();

        }
        else if (result == 2){
            Toast.makeText(this, "恭喜獲得奇異果折價券!!", Toast.LENGTH_SHORT).show();
        }
        else if (result == 3){
            Toast.makeText(this, "恭喜獲得水梨折價券!!", Toast.LENGTH_SHORT).show();
        }
        else if (result == 4){
            Toast.makeText(this, "恭喜獲得火龍果折價券!!", Toast.LENGTH_SHORT).show();
        }
        else if (result == 5){
            Toast.makeText(this, "恭喜獲得芭樂折價券!!", Toast.LENGTH_SHORT).show();
        }

        btn_start.setVisibility(View.VISIBLE);
        //設定計時器，計時10秒
        new CountDownTimer(10000,1000){

            //計時結束
            @Override
            public void onFinish() {
                btn_start.setEnabled(true); //啟用按鈕功能
                txv_timeview.setText("來抽獎吧!!");
                txv.setText("");
            }
            //計時中
            @Override
            public void onTick(long millisUntilFinished) {
                txv.setText(millisUntilFinished/1000 + " 秒");
            }

        }.start();
    }
    public void onAnimationRepeat(Animation animation) {

    }

    public void onClickButtonRotation(View v) {
        btn_start.setEnabled(false); //關閉按鈕功能
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
    private Button.OnClickListener btn_giftListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent main_intent = new Intent();
                    main_intent.setClass(MainActivity.this,GiftActivity.class);
                    startActivity(main_intent);
                }
            };


    // 切換首頁
    private Button.OnClickListener btn_mainListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent main_intent = new Intent();
                    main_intent.setClass(MainActivity.this,MainActivity.class);
                    startActivity(main_intent);
                }
            };
    // 切換百科
    private Button.OnClickListener btn_libraryListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent pedia_intent = new Intent();
                    pedia_intent.setClass(MainActivity.this,LibraryHome.class);
                    startActivity(pedia_intent);
                }
            };

    public AlertDialog getMutiItemDialog(final String[] items) {
        Builder builder = new Builder(this);
        //設定對話框內的項目
        builder.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                //當使用者點選對話框時，切換不同頁面
                if (items[which] == "蘋果產地") {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,PlaceActivity.class);
                    startActivity(intent);
                }
                else if (items[which] == "水果甜度") {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,ClassifierActivity.class);
                    startActivity(intent);
                }
                else if (items[which] == "水果種類"){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,TypeActivity.class);
                    startActivity(intent);
                }

            }
        });
        return builder.create();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
