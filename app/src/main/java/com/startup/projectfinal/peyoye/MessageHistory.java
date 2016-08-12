package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageHistory extends Activity {
//set user as current user.
    String current_user="Kirti Karande",other_user;
    ListView list_msghistory;
    ArrayList<Message> arrayOfMessages;
    ArrayAdapter adapter;
    String new_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_history);

        other_user=getIntent().getStringExtra("user");
        ((TextView)findViewById(R.id.other_user)).setText(other_user);


        list_msghistory=(ListView)findViewById(R.id.list_msghistory);

        //populate array
        arrayOfMessages=new ArrayList<Message>();
        arrayOfMessages.add(new Message("Kirti Karande","Comments Done."));
        arrayOfMessages.add(new Message("Tanzeel Shaikh","Notifications Done."));
        arrayOfMessages.add(new Message("Ameya Vichare","home screen Done."));

        //create custom adapter
        adapter=new MessageAdapter(this,arrayOfMessages);

        list_msghistory=(ListView)findViewById(R.id.list_msghistory);
        list_msghistory.setAdapter(adapter);

    }

    public void goBack(View view)
    {
        Intent intent=new Intent(this,LetterboxScreen.class);
        startActivity(intent);
    }

    public void onMessageClicked(View view) {
        new_msg=((EditText)findViewById(R.id.added_msg)).getText().toString();
        arrayOfMessages.add(new Message(current_user,new_msg));
        ((EditText) findViewById(R.id.added_msg)).setText("");
    }

//======================================================================================================

    /**
     * Message and MessageAdapter
     */
    public class Message {
        String sender_name;
        String msg;

        public Message(String sender_name,String msg)
        {
            this.sender_name=sender_name;
            this.msg=msg;
        }

        // Constructor to convert JSON object into a Java class instance
        public Message(JSONObject object){ }

        // Factory method to convert an array of JSON objects into a list of objects
        // Message.fromJson(jsonArray);

        public ArrayList<Message> fromJson(JSONArray jsonObjects) {
            ArrayList<Message> messages = new ArrayList<Message>();
            for (int i = 0; i < jsonObjects.length(); i++) {
                try {
                    messages.add(new Message(jsonObjects.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return messages;
        }
    }

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

                if (message.sender_name.equals(current_user)) {
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
}
