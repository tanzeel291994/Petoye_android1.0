package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends Activity {

    boolean IS_LOGGED_IN=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent;
                    if(!IS_LOGGED_IN)
                    {
                        // if user is logging in for first time.
                        intent = new Intent(SplashScreen.this,LaunchScreen.class);

                    }
                    else
                    {
                        // if user has already logged in.
                        intent = new Intent(SplashScreen.this,MainActivity.class);
                    }
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
