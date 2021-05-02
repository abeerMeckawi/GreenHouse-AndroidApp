package com.example.greenhouse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingActivity extends AppCompatActivity {

    private EditText maxhum, minhum, maxtemp, mintemp;
    private TextView cropName, humidityDegree, temperatureDegree;
    private ImageView cropImg;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Setting s;
    private String selected_crop, img_index;
    NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        maxhum = (EditText) findViewById(R.id.max_humidity_edit);
        minhum = (EditText) findViewById(R.id.min_humidity_edit);
        maxtemp = (EditText) findViewById(R.id.max_Temperature_edit);
        mintemp = (EditText) findViewById(R.id.min_Temperature_edit);
        cropName = findViewById(R.id.crop_setting_txt);
        humidityDegree = findViewById(R.id.humidity_degree);
        temperatureDegree = findViewById(R.id.temp_degree);
        cropImg = findViewById(R.id.image_setting_crop);

        maxhum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxhum.setFocusable(true);
                maxhum.setFocusableInTouchMode(true);
                maxhum.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                maxhum.setTextColor(Color.BLACK);
            }
        });
        minhum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minhum.setFocusable(true);
                minhum.setFocusableInTouchMode(true);
                minhum.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                minhum.setTextColor(Color.BLACK);
            }
        });

        maxtemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxtemp.setFocusable(true);
                maxtemp.setFocusableInTouchMode(true);
                maxtemp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                maxtemp.setTextColor(Color.BLACK);
            }
        });

        mintemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mintemp.setFocusable(true);
                mintemp.setFocusableInTouchMode(true);
                mintemp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                mintemp.setTextColor(Color.BLACK);
            }
        });
        //s = new Setting();

        /*database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Setting");*/
        GetSelectedCrop();


    }

  /*  public void AddSetting() {
        int maxH = Integer.parseInt(maxhum.getText().toString().trim());
        int minH = Integer.parseInt(minhum.getText().toString().trim());
        Float maxT = Float.parseFloat(maxtemp.getText().toString().trim());
        Float minT = Float.parseFloat(mintemp.getText().toString().trim());
        s.setMaxHumidiy(maxH);
        s.setMinHumidiy(minH);
        s.setMaxTemperature(maxT);
        s.setMinTemperature(minT);

        databaseReference.push().setValue(s);
        Toast.makeText(this, "data inserted successfully", Toast.LENGTH_SHORT).show();
    }*/

  public void GetSelectedCrop() {
      database =FirebaseDatabase.getInstance();
      databaseReference = database.getReference();
      databaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              selected_crop = dataSnapshot.child("CropSelected").getValue(String.class);
              img_index = dataSnapshot.child("CropSelectedImg").getValue(Integer.class).toString();
              if (selected_crop.equals("None") || selected_crop.equals(null)) {
                  humidityDegree.setText("Please Select Crop To Plant It");
                  ImageView imageNone = findViewById(R.id.image_setting_crop);
                  imageNone.setImageResource(R.drawable.ic_no);
                           /* Notification notification = new NotificationCompat.Builder(SettingActivity.this, AppNotify.CHANNEL_1_ID)
                                    .setContentTitle("Crops Selection")
                                    .setContentText("Please Select Crop To Plant It")
                                    .setSmallIcon(R.drawable.info)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .build();

                            notificationManagerCompat.notify(1, notification);*/
                  imageNone.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          startActivity(new Intent(SettingActivity.this, SelectCrops.class));
                      }
                  });
              } else {
                  cropName.setText(selected_crop);
                  final int img = Integer.parseInt(img_index);
                   cropImg.setImageResource(img);
                  database = FirebaseDatabase.getInstance();
                  databaseReference = database.getReference().child("Crops").child(selected_crop);
                  databaseReference.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          String maxHumidity = dataSnapshot.child("MaxHumidity").getValue().toString();
                          String minHumidity = dataSnapshot.child("MinHumidity").getValue().toString();
                          String maxTemperature = dataSnapshot.child("MaxTemperature").getValue().toString();
                          String minTemperature = dataSnapshot.child("MinTemperature").getValue().toString();

                          maxhum.setText(maxHumidity);
                          minhum.setText(minHumidity);
                          maxtemp.setText(maxTemperature);
                          mintemp.setText(minTemperature);
                          humidityDegree.setText("Humidity: Min = " + minHumidity + " % && Max = " + maxHumidity + " %");
                          temperatureDegree.setText("Temperature: Min = " + minTemperature + " % && Max = " + maxTemperature + " %");
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {
                          Toast.makeText(SettingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                      }
                  });

              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });


        /*database = FirebaseDatabase.getInstance();
        databaseReference= database.getReference().child("date");

        databaseReference .limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    // access last message
                    DataSnapshot messageSnapShot= dataSnapshot.getChildren().iterator().next().child("ldate");
                    String date= messageSnapShot.getValue().toString();

                    try {
                        if(new SimpleDateFormat("MM/dd/yyyy").parse(date).after(new Date())){
                            selected_crop = "None";
                            cropImg.setImageResource(R.drawable.ic_no);

                            database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference("selectedCrop");
                            databaseReference.setValue(selected_crop);
                            databaseReference = database.getReference("selectedImgIndex");
                            databaseReference.setValue(cropImg);

                        }
                    } catch ( ParseException e ) {
                        e.printStackTrace();
                    }


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
}

/*    @Override
   protected void onStop() {
        super.onStop();
        s = new Setting();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Setting");
        AddSetting();

    }*/

}
