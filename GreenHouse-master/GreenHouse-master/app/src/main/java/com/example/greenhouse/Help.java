package com.example.greenhouse;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Help extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        final ArrayList<HelpData> help=new ArrayList<HelpData>();
        help.add(new HelpData("Log In",R.string.login,R.drawable.login));
        help.add(new HelpData("Sign Up",R.string.signUp,R.drawable.signup));
        help.add(new HelpData("Home",R.string.home,R.drawable.home));
        help.add(new HelpData("Live Monitor",R.string.live_monitor,R.drawable.li));
        help.add(new HelpData("Crops Selection",R.string.crop_selection,R.drawable.select));
        help.add(new HelpData("Setting",R.string.setting,R.drawable.setting_page));
        help.add(new HelpData("Manual Action",R.string.manual_action,R.drawable.manual));
        help.add(new HelpData("About",R.string.about,R.drawable.about_page));

        HelpAdapter adapter = new HelpAdapter(this, help);
        ListView listView = (ListView) findViewById(R.id.help_list);
        listView.setAdapter(adapter);


    }
}
