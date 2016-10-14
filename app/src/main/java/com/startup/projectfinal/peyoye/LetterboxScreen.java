package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

//write all the classes that exist in this class in comments above.....

public class LetterboxScreen extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    GlobalClass globalVariable;
    // static String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letterbox_screen);
        globalVariable=(GlobalClass) getApplicationContext();
      //  uid=globalVariable.getUid();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //new DownloadAllConversation().execute();
    }

    public  void onClickHomeButton(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onClickAddFriendsButton(View view) {
        Intent intent=new Intent(this,DiscoverScreen.class);
        startActivity(intent);
        finish();
    }

    public void onClickPlusButton(View view) {
        Intent i=new Intent(this,PlusButtonMenuScreen.class);
        startActivity(i);
    }

    public void onClickNotificationButton(View view) {
        Intent i=new Intent(this, LetterboxScreen.class);
        startActivity(i);
        finish();
    }

    public void onClickMyProfileButton(View view) {
        Intent i=new Intent(this, ProfileScreen.class);
        i.putExtra("username","Kirti Karande");
        startActivity(i);
        finish();
    }

    public void goBack(View view)
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_letterbox_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//==============================================================================================================
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[] { "Notifications", "Messages" };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0) return Notifications.newInstance();
            else return Messages.newInstance();
        }

        @Override
        public int getCount() {
            // Show  total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
//===============================================================================================================
    /**
     * A Notification fragment (Notification Screen) containing a List of notifications
     */
    public static class Notifications extends Fragment {

        ListView list_notifications;
        ArrayAdapter adapter;
        ArrayList<Notification> arrayOfNotifications;

        public Notifications() {}

        public static Notifications newInstance()
        {
            Notifications n=new Notifications();
            return n;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_notification_screen, container, false);

            arrayOfNotifications=new ArrayList<Notification>();

            arrayOfNotifications.add(new Notification("Comments Done."));
            arrayOfNotifications.add(new Notification("Notifications Done."));
            arrayOfNotifications.add(new Notification("home screen Done."));

            //create custom adapter
            adapter=new NotificationAdapter(this.getActivity(),arrayOfNotifications);

            list_notifications=(ListView)rootView.findViewById(R.id.list_notifications);
            list_notifications.setAdapter(adapter);

            return rootView;
        }

        public class Notification {
            Image img;
            String notification_msg;

            public Notification(Image img, String notification_msg)
            {
                this.img=img;
                this.notification_msg=notification_msg;
            }
            public Notification(String notification_msg)
            {
                this.notification_msg=notification_msg;
            }

            // Constructor to convert JSON object into a Java class instance
            public Notification(JSONObject object){ }

            // Factory method to convert an array of JSON objects into a list of objects
            // Comment.fromJson(jsonArray);
            public ArrayList<Notification> fromJson(JSONArray jsonObjects) {
                ArrayList<Notification> notifications = new ArrayList<Notification>();
                for (int i = 0; i < jsonObjects.length(); i++) {
                    try {
                        notifications.add(new Notification(jsonObjects.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return notifications;
            }
        }

        public class NotificationAdapter extends ArrayAdapter<Notification> {

            // View lookup cache
            private class ViewHolder {
                ImageView img;
                TextView msg;
            }

            public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
                super(context, R.layout.item_notification, notifications);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the data item for this position
                Notification notification = getItem(position);
                // Check if an existing view is being reused, otherwise inflate the view
                ViewHolder viewHolder; // view lookup cache stored in tag
                if (convertView == null) {
                    // If there's no view to re-use, inflate a brand new view for row
                    viewHolder = new ViewHolder();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.item_notification, parent, false);
                    viewHolder.img = (ImageView) convertView.findViewById(R.id.notification_img);
                    viewHolder.msg = (TextView) convertView.findViewById(R.id.notification_msg);
                    // Cache the viewHolder object inside the fresh view
                    convertView.setTag(viewHolder);
                } else {
                    // View is being recycled, retrieve the viewHolder object from tag
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                // Populate the data into the template view using the data object
                viewHolder.msg.setText(notification.notification_msg);
                // Return the completed view to render on screen
                return convertView;
            }
        }
    }

    //==============================================================================================================
    /**
     * A Messages fragment containing a List of conversations
     */
    public  static class Messages extends Fragment {

        Activity thisActivity;
        ListView list_conversations;
        ArrayAdapter adapter;
        ArrayList<Conversation> arrayOfConversations;
        GlobalClass globalVariable;
        Context thisActivityContext;
        public Messages() {}

        public static Messages newInstance()
        {
            Messages m=new Messages();
            return m;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_messages_screen, container, false);
            //globalVariable=(GlobalClass) getApplicationContext();
            thisActivity=this.getActivity();
            thisActivityContext=this.getContext();
            arrayOfConversations=new ArrayList<Conversation>();
            //Log.i("TAG",uid);
            //arrayOfConversations.add(new Conversation("Kirti Karande","Yes. Comments Done."));
            //arrayOfConversations.add(new Conversation("Tanzeel Shaikh","Yes. Notifications Done."));
            //arrayOfConversations.add(new Conversation("Ameya Vichare","Yes. home screen Done."));

            //create custom adapter
            adapter=new ConversationAdapter(this.getActivity(),arrayOfConversations);

            list_conversations=(ListView)rootView.findViewById(R.id.list_conversations);
            list_conversations.setAdapter(adapter);

            list_conversations.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position,long arg3)
                {
                    Conversation c=(Conversation) list_conversations.getItemAtPosition(position);
                    Intent i=new Intent(thisActivity.getApplicationContext(),MessageHistory.class);
                    i.putExtra("sender_name",c.sender_name);
                    i.putExtra("sender_id",c.sender_id);
                    startActivity(i);
                }
            });
            new DownloadAllConversation().execute();
            return rootView;
        }

        public class DownloadAllConversation extends AsyncTask<Void, Void,Void> {


            @Override
            protected Void doInBackground(Void... params) {
                try {

                    //String url = "http://api.petoye.com/conversations/"+globalVariable.getUid()+"/all";
                    String url = "http://api.petoye.com/conversations/1/all";
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.i("TAG", response.toString());
                                    try {
                                        arrayOfConversations = Conversation.fromJson(response.getJSONArray("conversations"));
                                        Log.i("TAG", String.valueOf(arrayOfConversations.size()));
                                        adapter = new ConversationAdapter(thisActivityContext,arrayOfConversations);
                                        list_conversations.setAdapter(adapter);
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
}
