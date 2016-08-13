package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageHistory extends Activity {
//set user as current user.
    String other_user,other_user_id;
    ListView list_msghistory;
    ArrayList<Message> arrayOfMessages;
    ArrayAdapter adapter;
    String new_msg;
    GlobalClass globalClass;
    String my_user_id;
    Context thisActivityContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_history);

        thisActivityContext=this.getApplicationContext();

        globalClass=(GlobalClass)getApplicationContext();
        //my_user_id=globalClass.getUid();
        my_user_id="1";
        other_user=getIntent().getStringExtra("sender_name");
        other_user_id=getIntent().getStringExtra("sender_id");
        ((TextView)findViewById(R.id.other_user)).setText(other_user);


        list_msghistory=(ListView)findViewById(R.id.list_msghistory);

        //populate array
        arrayOfMessages=new ArrayList<Message>();
       // arrayOfMessages.add(new Message("Kirti Karande","Comments Done."));
        //arrayOfMessages.add(new Message("Tanzeel Shaikh","Notifications Done."));
        //arrayOfMessages.add(new Message("Ameya Vichare","home screen Done."));

        //create custom adapter
        adapter=new MessageAdapter(this,arrayOfMessages);

        list_msghistory=(ListView)findViewById(R.id.list_msghistory);
        list_msghistory.setAdapter(adapter);
        new DownloadOneConversation().execute();
    }

    public void goBack(View view)
    {
        Intent intent=new Intent(this,LetterboxScreen.class);
        startActivity(intent);
    }

    public void onMessageClicked(View view) {
        new_msg=((EditText)findViewById(R.id.added_msg)).getText().toString();
        arrayOfMessages.add(new Message(new_msg,my_user_id));
        adapter.notifyDataSetChanged();
        String url = "http://api.petoye.com/conversations";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("body",new_msg);
        jsonParams.put("sender_id","1");
        jsonParams.put("recipient_id",other_user_id);
        //Log.i("TAG",password);
        //Log.i("TAG",email);
        //Log.i("TAG",new JSONObject(jsonParams).toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    // Log.i("TAG","in response listener");
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("TAG", response.toString());

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

        ((EditText) findViewById(R.id.added_msg)).setText("");
    }

//======================================================================================================



    public class MessageAdapter extends ArrayAdapter<Message> {

        // View lookup cache
        private class ViewHolder {
            TextView msg;
        }

        public MessageAdapter(Context context, ArrayList<Message> messages) {
            super(context, R.layout.item_msg, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Message message = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());

                if (message.sender_id.equals(my_user_id)) {
                    //if msg was sent by current user
                    convertView = inflater.inflate(R.layout.list_item_message_right,parent,false);
                } else {
                    convertView = inflater.inflate(R.layout.list_item_message_left,parent,false);
                }

                viewHolder.msg = (TextView) convertView.findViewById(R.id.msg);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // Populate the data into the template view using the data object
            viewHolder.msg.setText(message.msg);
            // Return the completed view to render on screen
            return convertView;
        }
    }
    public class DownloadOneConversation extends AsyncTask<Void, Void,Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {

                //String url = "http://api.petoye.com/conversations/"+globalVariable.getUid()+"/all";
                String url = "http://api.petoye.com/conversations/1/"+ other_user_id+"/open";
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                Log.i("TAG", response.toString());
                                try {
                                    arrayOfMessages = Message.fromJson(response.getJSONArray("conversations"));
                                    //Log.i("TAG", String.valueOf(arrayOfMessages.size()));
                                    adapter = new MessageAdapter(thisActivityContext,arrayOfMessages);
                                    list_msghistory.setAdapter(adapter);
                                } catch (Exception e)
                                {
                                }

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
                // Access the RequestQueue through your singleton class.

                MySingleton.getInstance(thisActivityContext).addToRequestQueue(jsObjRequest);


            } catch (Exception e) {
            }

            return null;
        }


        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i("TAG", "in....");


        }
    }



}
