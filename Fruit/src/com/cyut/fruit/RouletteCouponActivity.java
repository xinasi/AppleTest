package com.cyut.fruit;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.cyut.fruit.R;

import java.util.ArrayList;
import java.util.List;

/*
轉盤優惠券主程式:
    轉盤優惠券程式
*/
public class RouletteCouponActivity extends AppCompatActivity {
    RecyclerView rc_RecyclerView;
    FirebaseDatabase rFirebaseDatabase;
    DatabaseReference cRef;
    FirebaseAuth firebaseAuth;
    private float scaleHeight, scaleWidth;
    Object obj;
    private List<String> list = new ArrayList<>();
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roulette_coupon);

        Drawable drawable = ContextCompat.getDrawable(RouletteCouponActivity.this,R.mipmap.coupon_roulette);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5),
                (int)(drawable.getIntrinsicHeight()*0.5));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, scaleWidth, scaleHeight);

        rc_RecyclerView = findViewById(R.id.rc_recyclerView);
        rc_RecyclerView.setHasFixedSize(true);
        rc_RecyclerView.setLayoutManager(new LinearLayoutManager(this));


        rFirebaseDatabase = FirebaseDatabase.getInstance();
        String uid = (String) firebaseAuth.getInstance().getCurrentUser().getUid();
        cRef = rFirebaseDatabase.getReference().child("Data/User").child(uid).child("url");

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RouletteCoupon, RouletteViewHolder>(
                RouletteCoupon.class,
                R.layout.roulette_row,
                RouletteViewHolder.class,
                cRef
        ) {
            @Override
            protected void populateViewHolder(RouletteViewHolder rouletteviewHolder, RouletteCoupon rouletteCoupon, final int position) {
                rouletteviewHolder.setDetails(getApplicationContext(), rouletteCoupon.getImage());

                rouletteviewHolder.rView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RouletteCouponActivity.this);
                        builder.setMessage("是否兌換此張優惠券？").setCancelable(false)
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        firebaseRecyclerAdapter.getRef(position).removeValue();
                                        firebaseRecyclerAdapter.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("兌換優惠");
                        dialog.show();
                    }
                });
            }
        };
        //set adapter to recyclerview
        rc_RecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}