package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.AlertDialog.Builder;
import org.tensorflow.demo.R; // Explicit import needed for internal Google builds.

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 取得首頁id
        Button btn_main = (Button)findViewById(R.id.btn_main);
        btn_main.setOnClickListener(btn_mainListner);
        // 取得百科id
        Button btn_pedia = (Button)findViewById(R.id.btn_pedia);
        btn_pedia.setOnClickListener(btn_pediaListner);
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
    private Button.OnClickListener btn_pediaListner =
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
}
