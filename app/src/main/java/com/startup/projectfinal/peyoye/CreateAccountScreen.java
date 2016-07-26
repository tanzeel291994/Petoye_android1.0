package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class CreateAccountScreen extends Activity {

    String email,password;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_create_account_screen);

        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LaunchScreen.class);
        startActivity(i);
    }

    ////------------ if Already have an account --------------//////
    public void gotoLoginScreen(View view)
    {
        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);
    }

    ////------------ sign up with email button --------------//////
    public void signup(View view)
    {
        email=((EditText)findViewById(R.id.etNewEmail)).getText().toString();
        password=((EditText)findViewById(R.id.etNewPassword)).getText().toString();
        Intent i = new Intent(this, BasicInfo.class);
        startActivity(i);
    }


}
