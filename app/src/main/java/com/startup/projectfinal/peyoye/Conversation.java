package com.startup.projectfinal.peyoye;

import android.media.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Conversation
{
    Image sender_img;
    String sender_name, sender_id;

    public Conversation(Image sender_img, String sender_name, String sender_id) {
        this.sender_img = sender_img;
        this.sender_name = sender_name;
        this.sender_id = sender_id;
    }

    public Conversation(String sender_name, String last_msg) {
        this.sender_name = sender_name;
        this.sender_id = last_msg;
    }
    public Conversation(JSONObject object){
        try {
            this.sender_name = object.getString("username");
            this.sender_id = object.getString("id");
            //Log.i("TAG", comment_username);
            //Log.i("TAG", comment_msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // Constructor to convert JSON object into a Java class instance


    // Factory method to convert an array of JSON objects into a list of objects
    // Comment.fromJson(jsonArray);
    public static ArrayList<Conversation> fromJson(JSONArray jsonObjects) {
        ArrayList<Conversation> conversations = new ArrayList<Conversation>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                conversations.add(new Conversation(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return conversations;
    }
}
