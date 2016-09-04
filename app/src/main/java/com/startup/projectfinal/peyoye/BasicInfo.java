/*  Required Changes :

>>  onClickDoneButton() network code commented. (cz application crashes) review required.

 */


package com.startup.projectfinal.peyoye;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;

import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.LocationListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.support.v7.appcompat.R.styleable.AlertDialog;

public class BasicInfo extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, LocationListener {

    Spinner spinner_user_category, spinner_pet_category, spinner_breed;
    String username, user_category, pet_category, breed, alertmsg = "";
    boolean displayerror = false, user_category_set = true, pet_category_set = true, breed_set = true;
    GlobalClass globalVariable;
    float latitude = 1, longitude = 1;
    private String provider;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationSettingsRequest mLocationSettingsRequest;
    LocationRequest mLocationRequest;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        globalVariable = (GlobalClass) getApplicationContext();
        context=getApplicationContext();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);

        spinner_user_category = (Spinner) findViewById(R.id.spinner_user_category);
        spinner_pet_category = (Spinner) findViewById(R.id.spinner_pet_category);
        spinner_breed = (Spinner) findViewById(R.id.spinner_breed);

        spinner_user_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_pet_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pet_category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                breed = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i("TAG", "All location settings are satisfied.");

                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i("TAG", "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(BasicInfo.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i("TAG", "PendingIntent unable to execute request.");
                }

                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i("TAG", "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i("TAG", "User agreed to make required location settings changes.");
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("TAG", "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    public void onClickDoneButton(View view) {


        alertmsg = "";
        username = ((EditText) findViewById(R.id.username)).getText().toString();
        // push the strings username , user_category, pet_category, breed to database.


        if (username.equals("") || pet_category.equals("-- Select --") || breed.equals("-- Select --") || user_category.equals("-- Select --"))
            displayerror = true;
        else displayerror = false;
        if (username.equals("")) alertmsg += "Please enter Username. ";
        if (user_category.equals("-- Select --")) alertmsg += "Please select User category. ";
        if (pet_category.equals("-- Select --")) alertmsg += "Please select Pet category. ";
        if (breed.equals("-- Select --")) alertmsg += "Please select Breed. ";

        if (displayerror)    // if information is filled in properly
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage(alertmsg);
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } else {
            //Intent i = new Intent(this, MainActivity.class);
            //i.putExtra("username",username );i.putExtra("user_category",user_category );
            //i.putExtra("pet_category",pet_category );i.putExtra("breed",breed );
            //startActivity(i);


            String id = globalVariable.getUid();
            //String url = "http://api.petoye.com/users/" + id + "/basicinfo";
            String url = "http://api.petoye.com/users/1/basicinfo";
            //Log.i("TAG", id);
            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("username", username);
            jsonParams.put("otype", user_category);
            jsonParams.put("ptype", pet_category);
            jsonParams.put("breed", breed);
            jsonParams.put("lat", String.valueOf(latitude));
            jsonParams.put("lng", String.valueOf(longitude));
            Log.i("TAG", String.valueOf(latitude));
            Log.i("TAG", String.valueOf(longitude));
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //store the user id in global variable
                            Log.i("TAG", response.toString());
                            Intent i = new Intent(context, MainActivity.class);
                            //i.putExtra("username",username );i.putExtra("user_category",user_category );
                            //i.putExtra("pet_category",pet_category );i.putExtra("breed",breed );
                            startActivity(i);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse networkResponse = error.networkResponse;
                            if (networkResponse != null && networkResponse.statusCode == 422) {
                                Log.i("TAG", networkResponse.headers.toString());

                            }
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    //  headers.put("User-agent", "My useragent");
                    return headers;
                }

            };

            MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("TAG","ini");
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation (mGoogleApiClient);
        Log.i("TAG","in");
       // Log.i("TAG",String.valueOf(mLastLocation.getLatitude()));
        if (mLastLocation != null) {
            latitude= (float)mLastLocation.getLatitude();
            longitude=(float)mLastLocation.getLongitude();
            Toast.makeText(this,"Lat : "+String.valueOf(mLastLocation.getLatitude()),Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Long : "+String.valueOf(mLastLocation.getLongitude()),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location)
    {

        if(location!=null)
        {
            latitude= (float)location.getLatitude();
            longitude=(float)location.getLongitude();
            Toast.makeText(this,"Lat : "+String.valueOf(location.getLatitude()),Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Long : "+String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
        }
    }
}
