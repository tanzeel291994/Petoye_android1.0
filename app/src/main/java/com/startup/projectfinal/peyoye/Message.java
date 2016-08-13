package com.startup.projectfinal.peyoye;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Message {
    String sender_name;
    String msg;
    String sender_id;

    public Message(String msg,String sender_id)
    {
       // this.sender_name=sender_name;
        this.msg=msg;
        this.sender_id=sender_id;
    }

    // Constructor to convert JSON object into a Java class instance
    public Message(JSONObject object)
    {
        try {
           // this.sender_name = object.getJSONObject("user").getString("username");
            this.msg = object.getString("body");
            this.sender_id=object.getString("user_id");
            //Log.i("TAG", comment_username);
            //Log.i("TAG", comment_msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Message.fromJson(jsonArray);

    public  static ArrayList<Message> fromJson(JSONArray jsonObjects) {
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
