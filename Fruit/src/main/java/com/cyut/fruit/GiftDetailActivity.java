package com.cyut.fruit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GiftDetailActivity extends AppCompatActivity {
    private ImageView imgv_gift;
    private TextView title,date, place, method, notice;
    private ImageButton back_toGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);

        back_toGift = (ImageButton) findViewById(R.id.back_toGift);
        back_toGift.setOnClickListener(back_toGiftListner);

        imgv_gift = (ImageView) findViewById(R.id.imgv_gift);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        place = (TextView) findViewById(R.id.place);
        method = (TextView) findViewById(R.id.method);
        notice = (TextView) findViewById(R.id.notice);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Picasso.get().load(bundle.getString("image")).placeholder(R.drawable.store_default).into(imgv_gift);
        title.setText(bundle.getString("title"));
        date.setText(bundle.getString("date"));
        place.setText(bundle.getString("place"));
        method.setText(bundle.getString("method"));
        notice.setText(bundle.getString("notice"));

    }

    ImageButton.OnClickListener back_toGiftListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), GiftActivity.class);
            startActivity(intent);
        }
    };
}
