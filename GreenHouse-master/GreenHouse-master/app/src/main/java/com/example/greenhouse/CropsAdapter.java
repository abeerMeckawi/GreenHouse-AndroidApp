package com.example.greenhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CropsAdapter extends ArrayAdapter {

    public CropsAdapter(Context context, int resource, ArrayList<CropsItem> cropsList) {
        super(context, 0, cropsList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    private View initView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.crops_spinner_row, parent, false
            );
        }
        CropsItem currentItem = (CropsItem) getItem(position);

        ImageView imageViewFlag = convertView.findViewById(R.id.crops_spinner_img);
        TextView textViewName = convertView.findViewById(R.id.crop_txt);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getCropsImage());
            textViewName.setText(currentItem.getCropsName());
        }
        return convertView;
    }
}


