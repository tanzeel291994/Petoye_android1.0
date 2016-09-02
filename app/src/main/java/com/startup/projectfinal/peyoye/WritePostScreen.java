package com.startup.projectfinal.peyoye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class WritePostScreen extends AppCompatActivity {

    FrameLayout container;
    EditText postcontent;
    ImageView img_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post_screen);

        container=(FrameLayout)findViewById(R.id.container);
        postcontent=(EditText)findViewById(R.id.post_content);
        img_user=(ImageView)findViewById(R.id.img_user);

    }

    public void goBack(View view){
        finish();
    }

    //select media for upload
    public void selectMedia(View view){

    }
}
