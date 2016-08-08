/*  Required Changes  :

>>  uncomment json array
>>  make changes to onCommentClicked()

*/

package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class CommentScreen extends Activity {

    ArrayList<Comment> arrayOfComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_screen);

        arrayOfComments = new ArrayList<Comment>();
        CommentAdapter adapter = new CommentAdapter(this, arrayOfComments);

       /* JSONArray jsonArray = new JSONArray();
        ArrayList<Comment> comments = Comment.fromJson(jsonArray);

        adapter.addAll(comments);
        */

        arrayOfComments.add(new Comment("Kirti Karande", "It's 2.01 AM "));
        arrayOfComments.add(new Comment("Kirti Karande", "It's 2.02 AM "));
        arrayOfComments.add(new Comment("Kirti Karande", "It's 2.03 AM "));

        ListView listView = (ListView) findViewById(R.id.list_comments);
        listView.setAdapter(adapter);
    }

    public void onCommentClicked(View view)
    {
        String username="Kirti Karande";
        String user_comment=((EditText)findViewById(R.id.added_comment_msg)).getText().toString();
        if(user_comment.equals(""))
        {
            Toast.makeText(this, "Please enter Comment . . .", Toast.LENGTH_SHORT).show();
        }
        else {
            arrayOfComments.add(new Comment(username, user_comment));

            // add comment to db

            ((EditText) findViewById(R.id.added_comment_msg)).setText("");
        }
    }
    public void goBack(View view)
    {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
