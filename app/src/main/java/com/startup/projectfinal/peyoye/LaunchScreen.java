package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchScreen extends Activity {

    Button btnGotoLogin,btnGotoCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    ////------------ if login button pressed --------------//////
    public void gotoLoginScreen(View view)
    {
        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);
    }

    ///------------if create account pressed -------------///////
    public  void  gotoCreateAccountScreen(View view)
    {
        Intent i = new Intent(this, CreateAccountScreen.class);
        startActivity(i);
    }
}
