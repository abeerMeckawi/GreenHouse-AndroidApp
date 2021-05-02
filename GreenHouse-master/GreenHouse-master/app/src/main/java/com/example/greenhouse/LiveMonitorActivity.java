package com.example.greenhouse;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import me.itangqi.waveloadingview.WaveLoadingView;

public class LiveMonitorActivity extends AppCompatActivity {

    TextView txtHumidity, txtTemperature;
    CircularProgressBar humidityProgressBar, temperatureProgressBar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private Thermometer thermometer;
    WaveLoadingView waveLoadingView;
    private int humidity;
    float temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_monitor);
        // thermometer = (Thermometer) findViewById(R.id.thermometer);
        txtHumidity = findViewById(R.id.humidity_degree_txt);
        txtTemperature = findViewById(R.id.temperature_degree_txt);
        humidityProgressBar = findViewById(R.id.humidity_ProgressBar);
        temperatureProgressBar = findViewById(R.id.temp_ProgressBar);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String valueHumidity = dataSnapshot.child("humidity").getValue().toString();
                String valueTemperature = dataSnapshot.child("Temperature").getValue().toString();
                txtHumidity.setText(valueHumidity + "%");
                txtTemperature.setText(valueTemperature + "°C ");
                humidityProgressBar.setProgress(Float.parseFloat(valueHumidity));
                temperatureProgressBar.setProgress(Float.parseFloat(valueTemperature));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

      /*  waveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
         database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String valueHumidity = dataSnapshot.child("humidity").getValue().toString();
                String valueTemperature = dataSnapshot.child("Temperature").getValue().toString();
                // txtHumidity.setText(valueHumidity);
                //txtTemperature.(valueTemperature);

             /*  temp = Float.parseFloat(valueTemperature);
                thermometer.setCurrentTemp(temp);*/

              /*  humidity = Integer.parseInt(valueHumidity);
                waveLoadingView.setProgressValue(humidity);
                if (humidity < 30) {
                    waveLoadingView.setBottomTitle("Low Humidity");
                    waveLoadingView.setCenterTitle(String.format("%d%%", humidity));
                    waveLoadingView.setTopTitle("");

                } else if (humidity > 70) {
                    waveLoadingView.setBottomTitle("Heigh Humidity");
                    waveLoadingView.setCenterTitle(String.format("%d%%", humidity));
                    waveLoadingView.setTopTitle("");
                } else {
                    waveLoadingView.setBottomTitle("Good Humidity");
                    waveLoadingView.setCenterTitle(String.format("%d%%", humidity));
                    waveLoadingView.setTopTitle("");
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });*/


    }
}


