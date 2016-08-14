package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.FacebookSdk;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends Activity {

    private String email,password;
    GlobalClass globalVariable;
    boolean isAuthorized;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalVariable=(GlobalClass) getApplicationContext();
        isAuthorized=false;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_screen);

       // email=((EditText)findViewById(R.id.etLoginEmail)).getText().toString();
       // password=((EditText)findViewById(R.id.etLoginPassword)).getText().toString();
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
        String url = "http://api.petoye.com/users/login";
       email=((EditText)findViewById(R.id.etLoginEmail)).getText().toString();
        password=((EditText)findViewById(R.id.etLoginPassword)).getText().toString();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("email",email);
        jsonParams.put("password",password);
        Log.i("TAG",password);
        Log.i("TAG",email);
        Log.i("TAG",new JSONObject(jsonParams).toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                   // Log.i("TAG","in response listener");
                    @Override
                    public void onResponse(JSONObject response) {
                        //store the user id in global variable

                        //Log.i("TAG", response.toString());
                        try{
                            isAuthorized=true;
                        globalVariable.setUid(response.getString("id"));}
                        catch(Exception e){
                        Log.i("TAG",e.toString());}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == 422) {
                            Log.i("TAG",networkResponse.headers.toString());

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

        if(isAuthorized) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login");
            alertDialog.setMessage("wrong credentials");
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

}
