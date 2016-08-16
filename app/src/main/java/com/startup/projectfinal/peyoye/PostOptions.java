package com.startup.projectfinal.peyoye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PostOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_options);
    }

    public void onClickFollow(View view){
        Toast.makeText(this,"Follow pressed",Toast.LENGTH_SHORT).show();
    }

    public void onClickReport(View view){
        Intent intent=new Intent(this,ReportScreen.class);
        startActivity(intent);
    }

    public void onClickCancel(View view){ finish();}
}
