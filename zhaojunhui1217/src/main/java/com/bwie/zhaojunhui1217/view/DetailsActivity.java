package com.bwie.zhaojunhui1217.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.zhaojunhui1217.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView textView01, textView02;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textView01 = findViewById(R.id.textView01);
        textView02 = findViewById(R.id.textView02);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String price = intent.getStringExtra("price");
        String images = intent.getStringExtra("images");

        textView01.setText(title);
        textView02.setText(price);
        String str = "";
        int length = images.length();
        for (int j = 0; j < length; j++) {
            if (images.substring(j, j+1).equals("|")){
                str = images.substring(j+1, length).trim();
            }
        }
        Glide.with(DetailsActivity.this).load(str).into(imageView);

    }
}
