/*  Required Changes  :

>>  uncomment json array
>>  make changes to onCommentClicked()

*/

package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentScreen extends Activity {

    ArrayList<Comment> arrayOfComments;
    Context thisActivityContext;
    ListView listView;
    CommentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_screen);

        arrayOfComments = new ArrayList<Comment>();
        //adapter = new CommentAdapter(this, arrayOfComments);
        thisActivityContext=this.getApplicationContext();
       /* JSONArray jsonArray = new JSONArray();
        ArrayList<Comment> comments = Comment.fromJson(jsonArray);

        adapter.addAll(comments);
        */
//start an async task.......
        new DownloadComments().execute();
       //arrayOfComments.add(new Comment("Kirti Karande", "It's 2.01 AM "));
        //arrayOfComments.add(new Comment("Kirti Karande", "It's 2.02 AM "));
        //arrayOfComments.add(new Comment("Kirti Karande", "It's 2.03 AM "));

        listView = (ListView) findViewById(R.id.list_comments);
        //listView.setAdapter(adapter);
    }

    public void onCommentClicked(View view)
    {
        String username="Kirti Karande";
        String user_comment=((EditText)findViewById(R.id.added_comment_msg)).getText().toString();
        if(user_comment.equals(""))
        {
            Toast.makeText(this, "Please enter Comment . . .", Toast.LENGTH_SHORT).show();
        }
        else {
            arrayOfComments.add(new Comment(username, user_comment));

            // add comment to db

            ((EditText) findViewById(R.id.added_comment_msg)).setText("");
        }
    }
    public void goBack(View view)
    {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public class DownloadComments extends AsyncTask<Void, Void,Void>
    {


        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                //String id=globalVariable.getUid();
                String url = "http://api.petoye.com/feeds/1/showcomment";
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                Log.i("TAG", response.toString());
                               try {
                                   arrayOfComments=Comment.fromJson(response.getJSONArray("comments"));
                                    Log.i("TAG",String.valueOf(arrayOfComments.size()));
                                   adapter = new CommentAdapter(thisActivityContext, arrayOfComments);
                                   listView.setAdapter(adapter);
                               }
                                catch (Exception e){}

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

                MySingleton.getInstance(thisActivityContext).addToRequestQueue(jsObjRequest);


            }
            catch (Exception e){    }

            return  null;
        }



        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i("TAG","in....");


        }
    }
}
