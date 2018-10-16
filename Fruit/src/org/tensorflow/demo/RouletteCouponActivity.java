package org.tensorflow.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
/*
轉盤優惠券主程式:
    轉盤優惠券程式
    遇到問題:無法順利取得圖片
*/
public class RouletteCouponActivity extends AppCompatActivity {
    RecyclerView rc_RrecyclerView;
    FirebaseDatabase rFirebaseDatabase;
    DatabaseReference cRef;
    FirebaseAuth firebaseAuth;
    private float scaleHeight, scaleWidth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roulette_coupon);


        Drawable drawable = ContextCompat.getDrawable(RouletteCouponActivity.this,R.mipmap.coupon_roulette);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5),
                (int)(drawable.getIntrinsicHeight()*0.5));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, scaleWidth, scaleHeight);

        rc_RrecyclerView = findViewById(R.id.rc_recyclerView);
        rc_RrecyclerView.setHasFixedSize(true);
        rc_RrecyclerView.setLayoutManager(new LinearLayoutManager(this));


        rFirebaseDatabase = FirebaseDatabase.getInstance();
        String uid = (String) firebaseAuth.getInstance().getCurrentUser().getUid();
        cRef = rFirebaseDatabase.getReference().child("Data/User").child(uid).child("url");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RouletteCoupon, RouletteViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<RouletteCoupon, RouletteViewHolder>(
                        RouletteCoupon.class,
                        R.layout.roulette_row,
                        RouletteViewHolder.class,
                        cRef
                ){
                    @Override
                    protected void populateViewHolder(RouletteViewHolder rouletteviewHolder, RouletteCoupon rouletteCoupon, int position) {
                        rouletteviewHolder.setDetails(getApplicationContext(), rouletteCoupon.getImage());
                    }
                };
        //set adapter to recyclerview
        rc_RrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
