package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlusButtonMenuScreen extends Activity {

    Context thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_button_menu_screen);

        thisActivity=getApplicationContext();

        Button btnSharePost=(Button)findViewById(R.id.btnSharePost);
        btnSharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thisActivity,WritePostScreen.class);
                startActivity(intent);
            }
        });
    }

    public  void onClickHomeButton(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onClickAddFriendsButton(View view) {
        Intent intent=new Intent(this,DiscoverScreen.class);
        startActivity(intent);
        finish();
    }

    public void onClickPlusButton(View view) { finish(); }

    public void onClickNotificationButton(View view) {
        Intent i=new Intent(this, LetterboxScreen.class);
        startActivity(i);
        finish();
    }

    public void onClickMyProfileButton(View view) {
        Intent i=new Intent(this, ProfileScreen.class);
        startActivity(i);
        finish();
    }

}
