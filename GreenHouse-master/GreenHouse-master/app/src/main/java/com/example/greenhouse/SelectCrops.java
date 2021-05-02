package com.example.greenhouse;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SelectCrops extends AppCompatActivity {
    private boolean isSpinnerInitialValue = true;
    private static final String TAG = "SelectCrops";
    private TextView optomalduration, eDisplayDate, sDisplayDate, selectedCrop;
    private Button btnsave;
    DatabaseReference myRef;
    private DatePickerDialog.OnDateSetListener mDateSetlistener;
    private DatePickerDialog.OnDateSetListener mmDateSetlistener;
    SimpleDateFormat simpleDateFormat;
    Calendar cal;
    String duration;
    int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crops);

        sDisplayDate = findViewById(R.id.tsDate);
        eDisplayDate = findViewById(R.id.teDate);
        optomalduration = findViewById(R.id.textView13);
        btnsave = (Button) findViewById(R.id.button);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                adddate();

            }
        });
       selectedCrop=findViewById(R.id.selectedCrop_txt_cropslection);


        final ArrayList<CropsItem> mCropsList = new ArrayList<CropsItem>();
        mCropsList.add(new CropsItem("Select...", 0));
        mCropsList.add(new CropsItem("None", R.drawable.ic_no));
        mCropsList.add(new CropsItem("Asparagu", R.drawable.asparagu));
        mCropsList.add(new CropsItem("Aubergine", R.drawable.aubergine));
        mCropsList.add(new CropsItem("Beet", R.drawable.beet));
        mCropsList.add(new CropsItem("Broccoli", R.drawable.broccoli));
        mCropsList.add(new CropsItem("Carrot", R.drawable.carrot));
        mCropsList.add(new CropsItem("Corn", R.drawable.corn));
        mCropsList.add(new CropsItem("Garlic", R.drawable.garlic));
        mCropsList.add(new CropsItem("Lemon", R.drawable.lemon));
        mCropsList.add(new CropsItem("Lettuce", R.drawable.lettuce));
        mCropsList.add(new CropsItem("Potatoes", R.drawable.patatoes));
        mCropsList.add(new CropsItem("Pepper", R.drawable.pepper));
        mCropsList.add(new CropsItem("Radish", R.drawable.radish));
        mCropsList.add(new CropsItem("Zucchini", R.drawable.zucchini));

        Spinner spinnerCrops = findViewById(R.id.spinner_crops);
        CropsAdapter mAdapter = new CropsAdapter(this, R.id.spinner_crops, mCropsList);
        mAdapter.setDropDownViewResource(R.layout.crops_spinner_row);
        spinnerCrops.setAdapter(mAdapter);

        spinnerCrops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (isSpinnerInitialValue) {
                    isSpinnerInitialValue = false;
                } else {
                    CropsItem clickedItem = (CropsItem) parent.getItemAtPosition(position);
                    String clickedCropName = clickedItem.getCropsName();
                    int clickedCropImg = clickedItem.getCropsImage();

                    //Toast.makeText(SelectCrops.this, clickedCropName + " selected", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase CropDb = FirebaseDatabase.getInstance();
                    DatabaseReference myRefCrop = CropDb.getReference("CropSelected");
                    myRefCrop.setValue(clickedCropName);
                    myRefCrop = CropDb.getReference("CropSelectedImg");
                    myRefCrop.setValue(clickedCropImg);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cropSelected = dataSnapshot.child("CropSelected").getValue().toString();
                if (cropSelected == "None") {
                    optomalduration.setText("0");
                    selectedCrop.setText("Selected Crop: None");
                    Toast.makeText(SelectCrops.this, "Please Select Crop", Toast.LENGTH_SHORT).show();
                } else {
                    selectedCrop.setText("Selected Crop: "+cropSelected);
                    duration = dataSnapshot.child("Crops").child(cropSelected).child("Duration").getValue().toString();
                    optomalduration.setText(duration + " Month");

                    cal = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat(" MM/dd/yyyy");
                    String currentdate = simpleDateFormat.format(cal.getTime());
                    sDisplayDate.setText(currentdate);

                    String dateInString = sDisplayDate.getText().toString();  // Start date

                    try {
                        cal.setTime(simpleDateFormat.parse(dateInString));
                    } catch ( ParseException e ) {
                        e.printStackTrace();
                    }

                    month = Integer.parseInt(duration);

                    cal.add(Calendar.MONTH, month);

                    Date resultdate = new Date(cal.getTimeInMillis());   // Get new time
                    dateInString = simpleDateFormat.format(resultdate);
                    eDisplayDate.setText(dateInString);

                    /*String maxHumidity = dataSnapshot.child("MaxHumidity").getValue().toString();
                    String minHumidity = dataSnapshot.child("MinHumidity").getValue().toString();
                    String maxTemperature = dataSnapshot.child("MaxTemperature").getValue().toString();
                    String minTemperature = dataSnapshot.child("MinTemperature").getValue().toString();
                    //Toast.makeText(SelectCrops.this, maxHumidity + minHumidity+maxTemperature+minTemperature, Toast.LENGTH_SHORT).show();*/
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       /* cal.add(Calendar.DATE, 0);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.YEAR, 0);
        simpleDateFormat = new SimpleDateFormat(" dd/MM/yyyy");
        String endDate = simpleDateFormat.format(cal.getTime());
        eDisplayDate.setText(endDate);*/


        sDisplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        SelectCrops.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetlistener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet : M/dd/yyyy" + month + "/" + day + "/" + year);
                String sdate = month + "/" + day + "/" + year;
                sDisplayDate.setText(sdate);
            }

        };
        eDisplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int dd = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(
                        SelectCrops.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mmDateSetlistener, y, m, dd);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
            }
        });
        mmDateSetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker da, int y, int m, int d) {
                m = m + 1;
                Log.d(TAG, "onDateSet : mm/dd/yyyy" + m + "/" + d + "/" + y);
                String edate = m + "/" + d + "/" + y;
                eDisplayDate.setText(edate);


            }

        };

    }


    public void adddate() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("date");
        String fdate = sDisplayDate.getText().toString();
        String ldate = eDisplayDate.getText().toString();
        if (!ldate.isEmpty()) {
            String id = myRef.push().getKey();
            Member date = new Member(id, fdate, ldate);
            myRef.child("date").child(id).setValue(date);
            Toast.makeText(this, "date insert successfully", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "end date empty", Toast.LENGTH_SHORT).show();
        }

    }


  /*  @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selectedCrop",clickedCropName);
        editor.putInt("selectedCropImg",clickedCropImg);
        editor.apply();

    }

    @Override
    protected void onStart() {
        super.onStart();
       SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
       clickedCropName= prefs.getString("selectedCrop", "Select...");
       clickedCropImg=prefs.getInt("selectedCropImg",0);


    }*/

}

