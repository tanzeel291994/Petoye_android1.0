/*  Required Changes :

>>   Disabled hamburger and comment on action bar.
 */

package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public  void onClickHomeButton(View view) {}

    public void onClickAddFriendsButton(View view) {   }

    public void onClickPlusButton(View view) {}

    public void onClickNotificationButton(View view) {
        Intent i=new Intent(this, LetterboxScreen.class);
        startActivity(i);
    }

    public void onClickMyProfileButton(View view) {}

    public void GotoCommentsPage(View view)
    {
        Intent i=new Intent(this, CommentScreen.class);
        startActivity(i);
    }
    public  void  onLikeClicked(View view){}
    public void onShareviaClicked(View view){}


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
        String user_name,timestamp,img_description,like_comment_count;

        public Feed(){}
        public Feed(String user_name,String timestamp,String img_description,String like_comment_count)
        {
            this.user_name=user_name;
            this.timestamp=timestamp;
            this.img_description=img_description;
            this.like_comment_count=like_comment_count;
        }
        public Feed(JSONObject object){ }

        // Factory method to convert an array of JSON objects into a list of objects
        // Comment.fromJson(jsonArray);
        public ArrayList<Feed> fromJson(JSONArray jsonObjects) {
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

        // View lookup cache
        private class ViewHolder {
            ImageView feed_img;
            TextView username,timestamp,img_description,like_comment_count;
        }

        public FeedAdapter(Context context, ArrayList<Feed> feeds) {
            super(context, R.layout.item_feed, feeds);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Feed feed = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
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
            viewHolder.like_comment_count.setText(feed.like_comment_count);
            //viewHolder.feed_img.setImageBitmap();

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_trending_feeds, container, false);

            arrayOfFeeds=new ArrayList<Feed>();

            adapter=new FeedAdapter(this.getActivity(),arrayOfFeeds);

            arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));
            arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));

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

        public FollowedFragment() { }

        public static FollowedFragment newInstance()
        {
            FollowedFragment fragment = new FollowedFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_followed_feeds, container, false);

            arrayOfFeeds=new ArrayList<Feed>();

            adapter=new FeedAdapter(this.getActivity(),arrayOfFeeds);

            list_followed_feeds=(ListView)rootView.findViewById(R.id.list_followed_feeds);
            list_followed_feeds.setAdapter(adapter);

            arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));
            arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));
            arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));

            return rootView;
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

            arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));
            arrayOfFeeds.add(new Feed("Kirti Karande","2 hours ago","Such a cute ..... I dont know what to call it. ;)","208 Likes 300 Comments"));

            list_nearby_pets_feeds=(ListView)rootView.findViewById(R.id.list_nearby_pets_feeds);
            list_nearby_pets_feeds.setAdapter(adapter);

            return rootView;
        }
    }

}
