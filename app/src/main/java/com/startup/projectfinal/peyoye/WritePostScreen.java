package com.startup.projectfinal.peyoye;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WritePostScreen extends AppCompatActivity {

    FrameLayout container;
    EditText postcontent;
    ImageView img_user,feed_img;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post_screen);

        container=(FrameLayout)findViewById(R.id.container);
        container.setVisibility(View.GONE);

        postcontent=(EditText)findViewById(R.id.post_content);

        img_user=(ImageView)findViewById(R.id.img_user);

        feed_img=(ImageView)findViewById(R.id.feed_img);

        final ImageButton btngoogleplus=(ImageButton)findViewById(R.id.btngoogleplus);
        btngoogleplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored google plus button
                btngoogleplus.setImageResource(R.drawable.google_plus_color);
                //for grey google plus button
                btngoogleplus.setImageResource(R.drawable.google_plus_gray);
            }
        });

        final ImageButton btnfb=(ImageButton)findViewById(R.id.btnfb);
        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored fb button
                btnfb.setImageResource(R.drawable.fb_color);
                //for grey fb button
                btnfb.setImageResource(R.drawable.fb_gray);
            }
        });

        final ImageButton btntwitter=(ImageButton)findViewById(R.id.btntwitter);
        btntwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored twitter button
                btntwitter.setImageResource(R.drawable.twitter_color);
                //for gray twitter button
                btntwitter.setImageResource(R.drawable.twitter_gray);
            }
        });
        final ImageButton btninsta=(ImageButton)findViewById(R.id.btninsta);
        btninsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored insta button
                btninsta.setImageResource(R.drawable.insta_color);
                //for gray insta button
                btninsta.setImageResource(R.drawable.insta_gray);
            }
        });

        final ImageButton btn_Share=(ImageButton)findViewById(R.id.btn_Share);
        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for colored Share button
                btn_Share.setImageResource(R.drawable.share_color_button);
                //for grey Share button
                btn_Share.setImageResource(R.drawable.share_gray);
            }
        });
    }

    public void goBack(View view){
        finish();
    }
    public void share(View view)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        String url="http://api.petoye.com/feeds/1/create";
        Map<String, String> jsonParams = new HashMap<String, String>();
       //jsonParams.put("uid","1");
        jsonParams.put("message", postcontent.getText().toString());
        if(bitmap!=null)
        jsonParams.put("image",getStringImage(bitmap));
        Log.i("TAG", jsonParams.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //store the user id in global variable
                        Log.i("TAG", response.toString());


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



    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.i("TAG", encodedImage);
        return encodedImage;
    }

    //select media for upload
    public void selectMedia(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        container.setVisibility(View.VISIBLE);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 10, 20, 10); //optional you can also set more margins and do lot of stuff here
                ImageView imageView = new ImageView(this); //context is the activity context say, this
                imageView.setLayoutParams(lp);
                //imgl.DisplayImage(image.getUrl(), imageView);
                container.addView(imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void  removeMedia(View view)
    {
        feed_img.setImageDrawable(null);
        feed_img.setImageResource(0);
        container.setVisibility(View.GONE);
    }
}
