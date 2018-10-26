package com.cyut.fruit;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.cyut.fruit.R;

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
                    // 10/24
                    @Override
                    public RouletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        final RouletteViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new RouletteViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                cRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot objSnapshot: snapshot.getChildren()) {

                                        }
                                        //Toast.makeText(RouletteCouponActivity.this, "Click!! " + key, Toast.LENGTH_SHORT).show();
                                        /*
                                        for (DataSnapshot objSnapshot: snapshot.getChildren()) {

                                        }
                                        */
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError firebaseError) {
                                        Log.e("Read failed", firebaseError.getMessage());
                                    }
                                });

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(RouletteCouponActivity.this, "Long Click!! ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return viewHolder;
                    }
                };
        //set adapter to recyclerview
        rc_RecyclerView.setAdapter(firebaseRecyclerAdapter);




    }




/*
    public void onDeleteClick(int position) {
        String uid = (String) firebaseAuth.getInstance().getCurrentUser().getUid();
        Coupon selectedItem = couponKeys.get(position);
        String selectedKey = selectedItem.getKey();
        rFirebaseDatabase.getReference().child("Data/User").child(uid).child(selectedKey).removeValue();


    }
*/

}
