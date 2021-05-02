package com.example.greenhouse;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CropsSelectionActivity extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Beet", "Broccoli", "Carrot", "lettuce", "Asparagus", "Aubergine", "Corn", "Pepper", "Zucchini", "Garlic", "Lemon", "Patatoes", "Radish"};
    String mTemperature[] = {"MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 ", "MaxT = 35 &&  MinT =2 "};
    String mHumidity[] = {"MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60", "MaxH=95% && MinH =60"};

    int images[] = {R.drawable.beet, R.drawable.broccoli, R.drawable.carrot, R.drawable.lettuce, R.drawable.asparagu, R.drawable.aubergine, R.drawable.corn, R.drawable.pepper, R.drawable.zucchini, R.drawable.garlic, R.drawable.lemon, R.drawable.patatoes, R.drawable.radish};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_selection);

        listView=findViewById(R.id.ListView);

        MyAdapter adapter = new MyAdapter(this, mTitle, mTemperature, mHumidity, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(CropsSelectionActivity.this, "beet selected", Toast.LENGTH_SHORT).show();
                }
                if (i == 1) {
                    Toast.makeText(CropsSelectionActivity.this, "beet selected", Toast.LENGTH_SHORT).show();
                }

                if (i == 2) {
                    Toast.makeText(CropsSelectionActivity.this, "beet selected", Toast.LENGTH_SHORT).show();
                }

                if (i == 3) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 4) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 5) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 6) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 7) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 8) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 9) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 10) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 11) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }

                if (i == 12) {
                    Toast.makeText(CropsSelectionActivity.this, "MaxT = 35  MinT =2 && MaxH=95% && MinH =60", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
