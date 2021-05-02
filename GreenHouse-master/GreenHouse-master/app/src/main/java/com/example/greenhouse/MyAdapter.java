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

class MyAdapter extends ArrayAdapter<String>
{
    Context context;
    String rTitle[];
    String rTemperature[];
    String rHumidity[];
    int rImgs[];

    MyAdapter(Context c,String title[],String temperature[],String humidity[],int imgs[]){
        super(c,R.layout.row,R.id.main_title_txt,title);
        this.context=c;
        this.rTitle=title;
        this.rTemperature=temperature;
        this.rHumidity=humidity;
        this.rImgs=imgs;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row =layoutInflater.inflate(R.layout.row,parent,false);
        ImageView images=row.findViewById(R.id.crop_img);
        TextView myTitle=row.findViewById(R.id.main_title_txt);
        TextView myTemperature=row.findViewById(R.id.subtitle1_txt);
        TextView myHumidity=row.findViewById(R.id.subtitle2_txt);


        images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle[position]);
        myTemperature.setText(rTemperature[position]);
        myHumidity.setText(rHumidity[position]);
        return row;
    }
}