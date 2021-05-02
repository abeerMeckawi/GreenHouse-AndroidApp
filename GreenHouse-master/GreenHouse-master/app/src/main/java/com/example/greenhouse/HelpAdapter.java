package com.example.greenhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HelpAdapter extends ArrayAdapter {

    public HelpAdapter(Context context, ArrayList<HelpData> help) {
        super(context, 0, help);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.help_row, parent, false);
        }

        HelpData currentItem= (HelpData) getItem(position);

        TextView subTextView =listItemView.findViewById(R.id.subTitle_text);
        subTextView.setText(currentItem.getSubTitle());

        TextView mainTextView = listItemView.findViewById(R.id.mainTitle_text);
        mainTextView.setText(currentItem.getMainTitle());


        ImageView imageHelp =listItemView.findViewById(R.id.image_help);
        imageHelp.setImageResource(currentItem.getImageResourceHelpId());

        return listItemView;
    }
}
