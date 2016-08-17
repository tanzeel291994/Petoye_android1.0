package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DiscoverScreen extends Activity {

    ArrayList<DiscoverListItem> arrayOfDiscoveredUsers;
    DiscoverListAdapter adapter;
    ListView list_discovered_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_screen);

        arrayOfDiscoveredUsers=new ArrayList<DiscoverListItem>();
        arrayOfDiscoveredUsers.add(new DiscoverListItem("Ameya Vichare","Pet Owner","Pug"));
        arrayOfDiscoveredUsers.add(new DiscoverListItem("Cristiano Ronaldo","Pet Lover","Dog"));
        arrayOfDiscoveredUsers.add(new DiscoverListItem("Lionel Messi","Pet owner","Dalmation"));

        //create custom adapter
        adapter=new DiscoverListAdapter(this.getApplicationContext(),arrayOfDiscoveredUsers);

        list_discovered_users=(ListView)findViewById(R.id.list_discovered_users);
        list_discovered_users.setAdapter(adapter);

    }
    public void goBack(View view) {}
    public void onClickHomeButton(View view){}
    public void onClickAddFriendsButton(View view){}
    public void onClickPlusButton(View view){}
    public void onClickNotificationButton(View view){}
    public void onClickMyProfileButton(View view){}

    public static class DiscoverListItem {
        Image img;
        String username,user_category,breed;

        public DiscoverListItem(Image img, String username,String user_category,String breed)
        {
            this.img=img;
            this.username=username;
            this.user_category=user_category;
            this.breed=breed;
        }
        public DiscoverListItem(String username,String user_category,String breed)
        {
            this.username=username;
            this.user_category=user_category;
            this.breed=breed;
        }

        // Constructor to convert JSON object into a Java class instance
        public DiscoverListItem(JSONObject object){ }

        // Factory method to convert an array of JSON objects into a list of objects
        // Comment.fromJson(jsonArray);
        public ArrayList<DiscoverListItem> fromJson(JSONArray jsonObjects) {
            ArrayList<DiscoverListItem> discoverListItems = new ArrayList<DiscoverListItem>();
            for (int i = 0; i < jsonObjects.length(); i++) {
                try {
                    discoverListItems.add(new DiscoverListItem(jsonObjects.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return discoverListItems;
        }
    }

    public class DiscoverListAdapter extends ArrayAdapter<DiscoverListItem> {

        // View lookup cache
        private class ViewHolder {
            ImageView img_user;
            TextView username,user_category,breed;
            Button follow_button;
        }

        public DiscoverListAdapter(Context context, ArrayList<DiscoverListItem> discoverListItems) {
            super(context, R.layout.item_discover_list, discoverListItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            DiscoverListItem discoverListItem = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_discover_list, parent, false);
                viewHolder.img_user = (ImageView) convertView.findViewById(R.id.img_user);
                viewHolder.username = (TextView) convertView.findViewById(R.id.username);
                viewHolder.user_category = (TextView) convertView.findViewById(R.id.user_category);
                viewHolder.breed = (TextView) convertView.findViewById(R.id.breed);
                viewHolder.follow_button=(Button)convertView.findViewById(R.id.follow_button);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // Populate the data into the template view using the data object
            viewHolder.username.setText(discoverListItem.username);
            viewHolder.user_category.setText(discoverListItem.user_category);
            viewHolder.breed.setText(discoverListItem.breed);
            viewHolder.follow_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button follow_button=(Button)v;
                    follow_button.setText("Following");
                    follow_button.setBackgroundResource(R.drawable.following_button);
                    follow_button.setTextColor(Color.parseColor("#FFFFFF"));
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
