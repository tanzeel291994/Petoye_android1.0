package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountScreen extends Activity {

    private String email,password;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    GlobalClass globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("TAG","IN MAIN");
         globalVariable=(GlobalClass) getApplicationContext();
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

        String url = "http://api.petoye.com/users/signup";
        email=((EditText)findViewById(R.id.etNewEmail)).getText().toString();
        password=((EditText)findViewById(R.id.etNewPassword)).getText().toString();
        //do some validations
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("email",email);
        jsonParams.put("password",password);
        Log.i("TAG",password);
        Log.i("TAG",email);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //store the user id in global variable
                        //Log.i("TAG", response.toString());
                        globalVariable.setUid(response.toString());
                        Log.i("TAG",globalVariable.getUid());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == 422) {
                            Log.i("TAG","could not be created");
                        }
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                //  headers.put("User-agent", "My useragent");
                return headers;
            }

        };
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        Intent i = new Intent(this, BasicInfo.class);
       // Log.i("TAG","IN SIGNUP");
        startActivity(i);
        //Log.i("TAG","after");
    }


}
