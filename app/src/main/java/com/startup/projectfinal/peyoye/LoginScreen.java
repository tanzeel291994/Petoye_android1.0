package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.FacebookSdk;

public class LoginScreen extends Activity {

    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_screen);

        email=((EditText)findViewById(R.id.etLoginEmail)).getText().toString();
        password=((EditText)findViewById(R.id.etLoginPassword)).getText().toString();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LaunchScreen.class);
        startActivity(i);
    }

    ////----------------- if don't have account---------------////////////
    public  void  gotoCreateAccountScreen(View view)
    {
        Intent i = new Intent(this, CreateAccountScreen.class);
        startActivity(i);
    }

    ////----------------if forgot password -----------------------////////
    public  void  forgotPassword(View view)
    {
        // ----------------------
        View v=findViewById(R.id.loginmain);
        Snackbar snackbar1 = Snackbar.make(v, "You should have written it somewhere. -_- ", Snackbar.LENGTH_SHORT);
        snackbar1.show();
    }

    ////-----------------on login pressed-------------------/////
    public void login(View view )
    {
        //authentication

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
