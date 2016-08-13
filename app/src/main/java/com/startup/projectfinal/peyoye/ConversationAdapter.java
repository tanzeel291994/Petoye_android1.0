package com.startup.projectfinal.peyoye;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ConversationAdapter extends ArrayAdapter<Conversation> {
    // View lookup cache
    private class ViewHolder {
        ImageView img;
        TextView sender_name,last_msg;
    }

    public ConversationAdapter(Context context, ArrayList<Conversation> conversations) {
        super(context, R.layout.item_conversation, conversations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Conversation conversation = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_conversation, parent, false);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.sender_img);
            viewHolder.sender_name=(TextView)convertView.findViewById(R.id.sender_name);
            viewHolder.last_msg = (TextView) convertView.findViewById(R.id.last_msg);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.sender_name.setText(conversation.sender_name);
        viewHolder.last_msg.setText(conversation.sender_id);
        // Return the completed view to render on screen

        return convertView;
    }
}

