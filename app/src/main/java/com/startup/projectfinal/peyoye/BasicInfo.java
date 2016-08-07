package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.support.v7.appcompat.R.styleable.AlertDialog;

public class BasicInfo extends Activity  {

    Spinner spinner_user_category,spinner_pet_category,spinner_breed;
    String username, user_category, pet_category, breed, alertmsg="";
    boolean displayerror=false,user_category_set=true,pet_category_set=true,breed_set=true;
    GlobalClass globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        globalVariable=(GlobalClass) getApplicationContext();

        spinner_user_category = (Spinner) findViewById(R.id.spinner_user_category);
        spinner_pet_category = (Spinner) findViewById(R.id.spinner_pet_category);
        spinner_breed = (Spinner) findViewById(R.id.spinner_breed);

        spinner_user_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_category=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner_pet_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pet_category=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner_breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                breed=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void onClickDoneButton(View view)
    {
        alertmsg="";
        username=((EditText)findViewById(R.id.username)).getText().toString();
        // push the strings username , user_category, pet_category, breed to database.

        Toast.makeText(this,"Username : "+username,Toast.LENGTH_LONG).show();
        Toast.makeText(this,"User Category : "+user_category,Toast.LENGTH_LONG).show();
        Toast.makeText(this," Pet Category : "+pet_category,Toast.LENGTH_LONG).show();
        Toast.makeText(this," Breed : "+breed,Toast.LENGTH_LONG).show();

        if(username.equals("")|| pet_category.equals("-- Select --") || breed.equals("-- Select --") || user_category.equals("-- Select --"))
            displayerror=true;
        else displayerror=false;
        if(username.equals("")) alertmsg+="Please enter Username. ";
        if(user_category.equals("-- Select --")) alertmsg+="Please select User category. ";
        if(pet_category.equals("-- Select --")) alertmsg+="Please select Pet category. ";
        if(breed.equals("-- Select --")) alertmsg+="Please select Breed. ";

        if(displayerror)    // if information is filled in properly
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage(alertmsg);
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
        else
        {
            //Intent i = new Intent(this, MainActivity.class);
            //i.putExtra("username",username );i.putExtra("user_category",user_category );
            //i.putExtra("pet_category",pet_category );i.putExtra("breed",breed );
            //startActivity(i);
        }
        String id=globalVariable.getUid();
        String url = "http://api.petoye.com/users/"+id+"/basicinfo";
        Log.i("TAG",id);
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("username",username);
        jsonParams.put("otype",user_category);
        jsonParams.put("ptype",pet_category);
        jsonParams.put("breed",breed);
       // Log.i("ptype",pet_category);
       // Log.i("TAG",email);
       // Log.i("TAG",new JSONObject(jsonParams).toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    // Log.i("TAG","in response listener");
                    @Override
                    public void onResponse(JSONObject response) {
                        //store the user id in global variable
                        Log.i("TAG", response.toString());
                        //globalVariable.setUid(response.toString());
                        //Log.i("TAG",globalVariable.getUid());
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
    }

}
