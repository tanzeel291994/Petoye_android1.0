package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileScreen extends AppCompatActivity {

    FrameLayout frame;
    FragmentManager fm;
    FragmentTransaction ft;
    static String thisuser="Kirti Karande";
    static String user;
    Button posts_tab,pictures_tab,petstory_tab;
    static ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        scrollView=(ScrollView)findViewById(R.id.scrollView) ;

        user=getIntent().getStringExtra("username");

        TextView uname=(TextView)findViewById(R.id.username);
        uname.setText(user);

        petstory_tab=(Button)findViewById(R.id.petstory_tab);
        pictures_tab=(Button)findViewById(R.id.pictures_tab);
        posts_tab=(Button)findViewById(R.id.posts_tab);

        frame = (FrameLayout) findViewById(R.id.tabcontent);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        PetStoryFragment petStoryFragment=new PetStoryFragment();
        ft.replace(R.id.tabcontent,petStoryFragment);
        ft.commit();
    }

    public  void  diplayPetsStory(View view){
        petstory_tab.setBackgroundResource(R.drawable.btn_pet_story_selected);
        petstory_tab.setTextColor(Color.parseColor("#FFFFFF"));
        pictures_tab.setBackgroundResource(R.drawable.btn_pictures);
        pictures_tab.setTextColor(Color.parseColor("#1A94CB"));
        posts_tab.setBackgroundResource(R.drawable.btn_posts);
        posts_tab.setTextColor(Color.parseColor("#1A94CB"));

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        PetStoryFragment petStoryFragment=new PetStoryFragment();
        ft.replace(R.id.tabcontent,petStoryFragment);
        ft.commit();
    }

    public void displayPictures(View view){
        petstory_tab.setBackgroundResource(R.drawable.btn_pet_story);
        petstory_tab.setTextColor(Color.parseColor("#1A94CB"));
        pictures_tab.setBackgroundResource(R.drawable.btn_pictures_selected);
        pictures_tab.setTextColor(Color.parseColor("#FFFFFF"));
        posts_tab.setBackgroundResource(R.drawable.btn_posts);
        posts_tab.setTextColor(Color.parseColor("#1A94CB"));

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        PicturesFragment picturesFragment=new PicturesFragment();
        ft.replace(R.id.tabcontent,picturesFragment);
        ft.commit();
    }

    public void displayPosts(View view){
        petstory_tab.setBackgroundResource(R.drawable.btn_pet_story);
        petstory_tab.setTextColor(Color.parseColor("#1A94CB"));
        pictures_tab.setBackgroundResource(R.drawable.btn_pictures);
        pictures_tab.setTextColor(Color.parseColor("#1A94CB"));
        posts_tab.setBackgroundResource(R.drawable.btn_posts_selected);
        posts_tab.setTextColor(Color.parseColor("#FFFFFF"));

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        PostsFragment postsFragment=new PostsFragment();
        ft.replace(R.id.tabcontent,postsFragment);
        ft.commit();
    }
//================ footer ==================================
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
        finish();
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
//===================== tab 1 : pet story ===================================
    public static class PetStoryFragment extends Fragment{

        String testUser="Ameya Vichare";

        public PetStoryFragment() { }

        public static PetStoryFragment newInstance()
        {
            PetStoryFragment fragment = new PetStoryFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_petstory, container, false);

            final Activity thisactivity=this.getActivity();

            if(thisuser.equals(user)){ // view first frame layout
                rootView.findViewById(R.id.ifmyprofile).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.ifnotmyprofile).setVisibility(View.GONE);

                //to view profile as other user
                rootView.findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(thisactivity,ProfileScreen.class);
                        i.putExtra("username",testUser);
                        startActivity(i);
                    }
                });

            }
            else { // view second frame layout
                rootView.findViewById(R.id.ifmyprofile).setVisibility(View.GONE);
                rootView.findViewById(R.id.ifnotmyprofile).setVisibility(View.VISIBLE);

                //to view your own profile
                rootView.findViewById(R.id.btn_like).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(thisactivity,ProfileScreen.class);
                        i.putExtra("username",thisuser);
                        startActivity(i);
                    }
                });
            }

            return rootView;
        }
    }

    public static class PicturesFragment extends Fragment{

        boolean thisuser=true;
        Integer[] imageIDs = {
                R.drawable.testimage1,
                R.drawable.testimage1,
                R.drawable.testimage2,
                R.drawable.testimage2,
                R.drawable.testimage3,
                R.drawable.testimage3,
                R.drawable.testimage4,
                R.drawable.testimage4
        };

        public PicturesFragment() { }

        public static PicturesFragment newInstance()
        {
            PicturesFragment fragment = new PicturesFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);

            GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
            gridView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                    int action = event.getActionMasked();
                    switch (action) {
                        case MotionEvent.ACTION_UP:
                            scrollView.requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                    return false;
                }
            });
            gridView.setAdapter(new ImageAdapter(this.getActivity()));

            return rootView;
        }

        public class ImageAdapter extends BaseAdapter
        {
            private Context context;

            public ImageAdapter(Context c)
            {
                context = c;
            }

            //---returns the number of images---
            public int getCount() {
                return imageIDs.length;
            }

            //---returns the ID of an item---
            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            //---returns an ImageView view---
            public View getView(int position, View convertView, ViewGroup parent)
            {
                ImageView imageView;
                if (convertView == null) {
                    imageView = new ImageView(context);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(1, 1, 1, 1);
                } else {
                    imageView = (ImageView) convertView;
                }
                imageView.setImageResource(imageIDs[position]);
                return imageView;
            }
        }
    }

    public static class PostsFragment extends Fragment{

        ListView list_users_feeds;
        ArrayAdapter adapter;
        ArrayList<MainActivity.Feed> arrayOfFeeds;

        public PostsFragment() { }

        public static PostsFragment newInstance()
        {
            PostsFragment fragment = new PostsFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_feeds, container, false);

            arrayOfFeeds=new ArrayList<MainActivity.Feed>();

            adapter=new MainActivity.FeedAdapter(this.getActivity(),arrayOfFeeds);

            arrayOfFeeds.add(new MainActivity.Feed("Kirti Karande","2 hrs ago","Such a cute pet","480","587","87","92"));
            arrayOfFeeds.add(new MainActivity.Feed("Kirti Karande","3 hrs ago","Such a cute pet","4","5","95","82"));
            arrayOfFeeds.add(new MainActivity.Feed("Kirti Karande","3 hrs ago","Such a cute pet","4","5","95","82"));
            arrayOfFeeds.add(new MainActivity.Feed("Kirti Karande","3 hrs ago","Such a cute pet","4","5","95","82"));

            list_users_feeds=(ListView)rootView.findViewById(R.id.list_user_feeds);

            list_users_feeds.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                    int action = event.getActionMasked();
                    switch (action) {
                        case MotionEvent.ACTION_UP:
                            scrollView.requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                    return false;
                }
            });
            list_users_feeds.setAdapter(adapter);
            return rootView;
        }
    }
}