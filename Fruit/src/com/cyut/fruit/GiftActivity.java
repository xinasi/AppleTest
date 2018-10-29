package com.cyut.fruit;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.cyut.fruit.R;

/*
果籃有禮優惠券主程式
 */
public class GiftActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference mRef;
    ImageButton imgb_g2m, imgb_g2l, imgb_g2c;
    Button btn_coupon_roulette;
    private float scaleHeight, scaleWidth;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        Drawable drawable = ContextCompat.getDrawable(GiftActivity.this,R.mipmap.coupon_roulette);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5),
                (int)(drawable.getIntrinsicHeight()*0.5));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, scaleWidth, scaleHeight);
        //btn_coupon_roulette = (Button)findViewById(R.id.btn_coupon_roulette);
        //btn_coupon_roulette.setCompoundDrawables(sd.getDrawable(), null, null, null);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("Posts List");
        // 取得首頁id
        //imgb_g2m = (ImageButton)findViewById(R.id.imgb_g2m);
        //imgb_g2m.setOnClickListener(imgb_g2mListner);
        // 取得百科id
        //imgb_g2l = (ImageButton)findViewById(R.id.imgb_g2l);
        //imgb_g2l.setOnClickListener(imgb_g2lListner);
        // 取得辨識id
        //imgb_g2c = (ImageButton)findViewById(R.id.imgb_g2c);
        //final AlertDialog mutiItemDialog = getMutiItemDialog(new String[]{"蘋果產地","水果甜度","水果種類"});
        //imgb_g2c.setOnClickListener(new Button.OnClickListener(){
        //    @Override
        //    public void onClick(View view){
        //        //顯示對話框
        //        mutiItemDialog.show();
        //    }
        //});

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Data/Gift");

    }

    //load data


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,
                        R.layout.row,
                        ViewHolder.class,
                        mRef

                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getDescription(), model.getImage());
                    }
                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }
/*
    // 切換首頁
    private Button.OnClickListener imgb_g2mListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent main_intent = new Intent();
                    main_intent.setClass(GiftActivity.this,MainActivity.class);
                    startActivity(main_intent);
                }
            };
    // 切換百科
    private Button.OnClickListener imgb_g2lListner =
            new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent pedia_intent = new Intent();
                    pedia_intent.setClass(GiftActivity.this,LibraryHome.class);
                    startActivity(pedia_intent);
                }
            };

    public AlertDialog getMutiItemDialog(final String[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //設定對話框內的項目
        builder.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                //當使用者點選對話框時，切換不同頁面
                if (items[which] == "蘋果產地") {
                    Intent intent = new Intent();
                    intent.setClass(GiftActivity.this,PlaceActivity.class);
                    startActivity(intent);
                }
                else if (items[which] == "蘋果甜度") {
                    Intent intent = new Intent();
                    intent.setClass(GiftActivity.this,ClassifierActivity.class);
                    startActivity(intent);
                }
                else if (items[which] == "水果種類"){
                    Intent intent = new Intent();
                    intent.setClass(GiftActivity.this,TypeActivity.class);
                    startActivity(intent);
                }

            }
        });
        return builder.create();
    }
    */
}
