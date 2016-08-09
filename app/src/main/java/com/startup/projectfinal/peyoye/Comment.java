package com.startup.projectfinal.peyoye;

import android.media.Image;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class Comment {
    Image img_user;
    String comment_username, comment_msg;

    public Comment(Image img_user, String comment_username, String comment_msg)
    {
        this.img_user=img_user;
        this.comment_username=comment_username;
        this.comment_msg=comment_msg;
    }
    public Comment(String comment_username, String comment_msg)
    {
        this.img_user=null;
        this.comment_username=comment_username;
        this.comment_msg=comment_msg;
    }

    // Constructor to convert JSON object into a Java class instance
    public Comment(JSONObject object){
        try {
            this.comment_username = object.getJSONObject("user").getString("username");
            this.comment_msg = object.getString("comment_message");
            Log.i("TAG", comment_username);
            Log.i("TAG", comment_msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Comment.fromJson(jsonArray);
    public static ArrayList<Comment> fromJson(JSONArray jsonObjects) {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                comments.add(new Comment(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return comments;
    }
}
