/*  Required Changes :

>>   Disabled hamburger and comment on action bar.
 */

package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void openHamburgerMenu(View view){
        openOptionsMenu();
    }

    public void onClickSearchButton(View view){}
//========================= footer ==========================================
    public  void onClickHomeButton(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void onClickAddFriendsButton(View view) {
        Intent intent=new Intent(this,DiscoverScreen.class);
        startActivity(intent);
    }

    public void onClickPlusButton(View view) {
        Intent i=new Intent(this,PlusButtonMenuScreen.class);
        startActivity(i);
    }

    public void onClickNotificationButton(View view) {
        Intent i=new Intent(this, LetterboxScreen.class);
        startActivity(i);
    }

    public void onClickMyProfileButton(View view) {
        Intent i=new Intent(this, ProfileScreen.class);
        i.putExtra("username","Kirti Karande");
        startActivity(i);
    }

//============================================================================================================
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[] { "Trending", "Followed","Nearby Pets" };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0)  return TrendingFragment.newInstance();
            else if(position==1)    return FollowedFragment.newInstance();
            else return NearbyPetsFragment.newInstance();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
//=========================================================================================

    public static class Feed{
        Image user_img;
        String user_name,timestamp,img_description,like_count,author_id,comment_count,feed_id;

        public Feed(){}
        public Feed(String user_name,String timestamp,String img_description,
                    String like_count,String comment_count,String author_id,String feed_id)
        {
            this.user_name=user_name;
            this.timestamp=timestamp;
            this.img_description=img_description;
            this.like_count=like_count;
            this.author_id=author_id;
            this.comment_count=comment_count;
            this.feed_id=feed_id;

        }
        public Feed(JSONObject object)
        {
            try
            {
                // this.sender_name = object.getJSONObject("user").getString("username");
                this.img_description = object.getString("message");
                this.user_name=object.getJSONObject("user").getString("username");
                this.author_id=object.getJSONObject("user").getString("id");
                this.like_count = object.getString("like_count");
                this.comment_count = object.getString("comment_count");
                this.timestamp = object.getString("created_at");
                this.feed_id=object.getString("id");
                //Log.i("TAG",feed_id);
                //Log.i("TAG", comment_msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Factory method to convert an array of JSON objects into a list of objects
        // Comment.fromJson(jsonArray);
        public static ArrayList<Feed> fromJson(JSONArray jsonObjects) {
            ArrayList<Feed> feeds = new ArrayList<Feed>();
            for (int i = 0; i < jsonObjects.length(); i++) {
                try {
                    feeds.add(new Feed(jsonObjects.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return feeds;
        }

    }

    public static class FeedAdapter extends ArrayAdapter<Feed> {

        Context thisActivityContext;
        // View lookup cache
        private class ViewHolder {
            ImageView feed_img;
            TextView username,timestamp,img_description,like_comment_count;
        }

        public FeedAdapter(Context context, ArrayList<Feed> feeds) {
            super(context, R.layout.item_feed, feeds);
            thisActivityContext=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Feed feed = (Feed) getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            final ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_feed, parent, false);

                viewHolder.feed_img=(ImageView)convertView.findViewById(R.id.feed_img);
                viewHolder.username=(TextView)convertView.findViewById(R.id.username);
                viewHolder.timestamp=(TextView)convertView.findViewById(R.id.timestamp);
                viewHolder.img_description=(TextView)convertView.findViewById(R.id.img_description);
                viewHolder.like_comment_count=(TextView)convertView.findViewById(R.id.likes_comments_count);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // Populate the data into the template view using the data object
            viewHolder.username.setText(feed.user_name);
            viewHolder.timestamp.setText(feed.timestamp);
            viewHolder.img_description.setText(feed.img_description);
            viewHolder.like_comment_count.setText(feed.like_count+" Likes "+ feed.comment_count+" Comments");
            //viewHolder.feed_img.setImageBitmap();
            Button btn_like_feed = (Button) convertView.findViewById(R.id.btnLike_feed);
            btn_like_feed.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //feed.like_count=feed.like_count+1;
                    viewHolder.like_comment_count.setText(String.valueOf(Integer.parseInt(feed.like_count)+1)+" Likes "+ feed.comment_count+" Comments");
                    new FollowedFragment.LikeFeed().execute(feed.feed_id);
                }
            });
            Button btn_comment_feed = (Button) convertView.findViewById(R.id.btn_comments_feed);
            btn_comment_feed.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(FollowedFragment.thisActivityContext, CommentScreen.class);
                    i.putExtra("feed_id",feed.feed_id);
                    FollowedFragment.thisActivityContext.startActivity(i);
                }
            });
            ImageButton report_button=(ImageButton)convertView.findViewById(R.id.report_button);
            report_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(thisActivityContext,PostOptions.class);
                    thisActivityContext.startActivity(i);
                }
            });
            // Return the completed view to render on screen
            return convertView;
        }
    }

//=========================================================================================
    /**
     * Trending Fragment
     */
    public static class TrendingFragment extends Fragment {

        ListView list_trending_feeds;
        ArrayAdapter adapter;
        ArrayList<Feed> arrayOfFeeds;

        public TrendingFragment() { }

        public static TrendingFragment newInstance()
        {
            TrendingFragment fragment = new TrendingFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_trending_feeds, container, false);

            arrayOfFeeds=new ArrayList<Feed>();

            adapter=new FeedAdapter(this.getActivity(),arrayOfFeeds);

            list_trending_feeds=(ListView)rootView.findViewById(R.id.list_trending_feeds);
            list_trending_feeds.setAdapter(adapter);

            return rootView;
        }
    }

//=========================================================================================
    /**
     * Followed Fragment
     */
    public static class FollowedFragment extends Fragment {

        ListView list_followed_feeds;
        ArrayAdapter adapter;
        ArrayList<Feed> arrayOfFeeds;
        static Context thisActivityContext;
        GlobalClass globalClass;
        String uid;

        public FollowedFragment() { }

        public static FollowedFragment newInstance()
        {
            FollowedFragment fragment = new FollowedFragment();
            return fragment;
        }

        public void startact()
        {
            Intent i = new Intent(FollowedFragment.thisActivityContext, CommentScreen.class);
            startActivity(i);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_followed_feeds, container, false);

            arrayOfFeeds=new ArrayList<Feed>();
            //globalClass=(GlobalClass)getContext();
            //uid=globalClass.getUid();
            thisActivityContext=getContext();

            adapter=new FeedAdapter(this.getActivity(),arrayOfFeeds);

            list_followed_feeds=(ListView)rootView.findViewById(R.id.list_followed_feeds);
            list_followed_feeds.setAdapter(adapter);

            new DownloadFollowedFeeds().execute();
            return rootView;
        }

        public class DownloadFollowedFeeds extends AsyncTask<Void, Void,Void> {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    //String url = "http://api.petoye.com/conversations/"+globalVariable.getUid()+"/all";
                    String url = "http://api.petoye.com//feeds/1/followedfeeds";
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.i("TAG", response.toString());
                                    try {
                                        arrayOfFeeds = Feed.fromJson(response.getJSONArray("feeds"));
                                       // Log.i("TAG", String.valueOf(arrayOfFeeds.size()));
                                        adapter = new FeedAdapter(thisActivityContext,arrayOfFeeds);
                                        list_followed_feeds.setAdapter(adapter);
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

        public  static class LikeFeed extends AsyncTask<String, Void,Void> {

            @Override
            protected Void doInBackground(String... fid) {
                try {
                    //String url = "http://api.petoye.com/conversations/"+globalVariable.getUid()+"/all";
                    String url = "http://api.petoye.com/feeds/"+fid[0]+"/like";
                    Map<String, String> jsonParams = new HashMap<String, String>();
                    Log.i("TAG",fid[0]);
                    //jsonParams.put("uid",uid);
                    //for debugging purpose.......................///change
                    jsonParams.put("uid","1");
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(jsonParams),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.i("TAG", response.toString());
                                    try {
                                        Toast.makeText(thisActivityContext,"LIKED",Toast.LENGTH_LONG).show();
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

//=========================================================================================
    /**
     * Nearby Pets Fragment
     */
    public static class NearbyPetsFragment extends Fragment {

        ListView list_nearby_pets_feeds;
        ArrayAdapter adapter;
        ArrayList<Feed> arrayOfFeeds;

        public NearbyPetsFragment() { }

        public static NearbyPetsFragment newInstance()
        {
            NearbyPetsFragment fragment = new NearbyPetsFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_nearby_pets_feeds, container, false);

            arrayOfFeeds=new ArrayList<Feed>();

            adapter=new FeedAdapter(this.getActivity(),arrayOfFeeds);

           // arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));
           // arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));

            list_nearby_pets_feeds=(ListView)rootView.findViewById(R.id.list_nearby_pets_feeds);
            list_nearby_pets_feeds.setAdapter(adapter);

            return rootView;
        }
    }
}
