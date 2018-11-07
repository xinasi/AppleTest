package com.cyut.fruit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

        //Drawable drawable = ContextCompat.getDrawable(GiftActivity.this,R.mipmap.coupon_roulette);
        //drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5),
                //(int)(drawable.getIntrinsicHeight()*0.5));
        //ScaleDrawable sd = new ScaleDrawable(drawable, 0, scaleWidth, scaleHeight);



        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Data/Gift");

        ImageButton back_toMain = (ImageButton) findViewById(R.id.back_toMain);
        back_toMain.setOnClickListener(back_toMainListner);

    }

    ImageButton.OnClickListener back_toMainListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GiftActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };

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
                    protected void populateViewHolder(ViewHolder viewHolder, final Model model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getDescription(), model.getImage());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(view.getContext(), GiftDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("image", model.getImage());
                                bundle.putString("title", model.getTitle());
                                bundle.putString("date", model.getDate());
                                bundle.putString("place", model.getPlace());
                                bundle.putString("method", model.getMethod());
                                bundle.putString("notice", model.getNotice());
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }
                        });
                    }
                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
