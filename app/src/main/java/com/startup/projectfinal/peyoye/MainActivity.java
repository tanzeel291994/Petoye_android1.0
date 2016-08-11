/*  Required Changes :

>>   Use Comments button to go to Comments activity

 */

package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public  void onClickHomeButton(View view) {}

    public void onClickAddFriendsButton(View view) {}

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
}
