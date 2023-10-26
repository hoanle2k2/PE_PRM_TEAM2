package com.example.pe_prm_team2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserDetailActivity extends AppCompatActivity {

    ImageView imgavatar;
    TextView tvuserid;
    TextView tvuserfullname;
    TextView tvemail;
    Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //gọi intent từ mainactivity
        Intent myIntent= getIntent();

        imgavatar= findViewById(R.id.img_detailavatar);
        tvuserid=findViewById(R.id.tv_detailid);
        tvuserfullname=findViewById(R.id.tv_detailfullname);
        tvemail=findViewById(R.id.tv_detailemail);
        btnback=findViewById(R.id.btn_backhome);


        String avatar=myIntent.getStringExtra("avatar");
        String id=myIntent.getStringExtra("id");
        String fullname=myIntent.getStringExtra("fullname");
        String email=myIntent.getStringExtra("email");


        Picasso.get().load(avatar).into(imgavatar);
        tvuserid.setText(id);
        tvuserfullname.setText(fullname);
        tvemail.setText(email);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}