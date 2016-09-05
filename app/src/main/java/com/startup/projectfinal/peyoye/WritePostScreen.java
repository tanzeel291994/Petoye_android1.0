package com.startup.projectfinal.peyoye;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class WritePostScreen extends AppCompatActivity {

    FrameLayout container;
    EditText postcontent;
    ImageView img_user,feed_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post_screen);

        container=(FrameLayout)findViewById(R.id.container);
        container.setVisibility(View.GONE);

        postcontent=(EditText)findViewById(R.id.post_content);

        img_user=(ImageView)findViewById(R.id.img_user);

        feed_img=(ImageView)findViewById(R.id.feed_img);

        final ImageButton btngoogleplus=(ImageButton)findViewById(R.id.btngoogleplus);
        btngoogleplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored google plus button
                btngoogleplus.setImageResource(R.drawable.google_plus_color);
                //for grey google plus button
                btngoogleplus.setImageResource(R.drawable.google_plus_gray);
            }
        });

        final ImageButton btnfb=(ImageButton)findViewById(R.id.btnfb);
        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored fb button
                btnfb.setImageResource(R.drawable.fb_color);
                //for grey fb button
                btnfb.setImageResource(R.drawable.fb_gray);
            }
        });

        final ImageButton btntwitter=(ImageButton)findViewById(R.id.btntwitter);
        btntwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored twitter button
                btntwitter.setImageResource(R.drawable.twitter_color);
                //for gray twitter button
                btntwitter.setImageResource(R.drawable.twitter_gray);
            }
        });
        final ImageButton btninsta=(ImageButton)findViewById(R.id.btninsta);
        btninsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored insta button
                btninsta.setImageResource(R.drawable.insta_color);
                //for gray insta button
                btninsta.setImageResource(R.drawable.insta_gray);
            }
        });

        final ImageButton btn_Share=(ImageButton)findViewById(R.id.btn_Share);
        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored Share button
                btn_Share.setImageResource(R.drawable.share_color_button);
                //for grey Share button
                btn_Share.setImageResource(R.drawable.share_gray);
            }
        });
    }

    public void goBack(View view){
        finish();
    }

    //select media for upload
    public void selectMedia(View view){

        //open gallery to select image

        container.setVisibility(View.VISIBLE);
        feed_img.setImageResource(R.drawable.testimage2);
    }

    public  void  removeMedia(View view)
    {
        feed_img.setImageDrawable(null);
        feed_img.setImageResource(0);
        container.setVisibility(View.GONE);
    }
}
