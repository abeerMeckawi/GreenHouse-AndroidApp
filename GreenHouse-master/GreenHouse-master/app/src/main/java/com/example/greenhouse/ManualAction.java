package com.example.greenhouse;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class ManualAction extends AppCompatActivity {
    ImageView motor_img, led_img;
    boolean offMotor,offLed;
    FirebaseDatabase database,db;
    DatabaseReference myRef,myReference;
    TextView txtHumidity, txtTemperature, mTextViewCountDownHum, mTextViewCountDownTemp;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunningHum, mTimerRunningTemp;
    private long mStartTimeInMillisHum, mStartTimeInMillisTemp;
    private long mTimeLeftInMillisHum, mTimeLeftInMillisTemp;
    private long mEndTimeHum, mEndTimeTemp;
    private EditText mEditTextInputHum, mEditTextInputTemp;
    private Button mButtonSetHum, mButtonSetTemp;
    String motorImg, ledImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_action);
        txtHumidity = findViewById(R.id.motor_dgree_txt);
        txtTemperature = findViewById(R.id.led_dgree_txt);
        mTextViewCountDownHum = findViewById(R.id.Text_counter_humidity);
        mTextViewCountDownTemp = findViewById(R.id.Text_counter_Temp);
        mEditTextInputHum = findViewById(R.id.edit_text_input_hum);
        mEditTextInputTemp = findViewById(R.id.edit_text_input_temp);
        mButtonSetHum = findViewById(R.id.button_set_hum);
        mButtonSetTemp = findViewById(R.id.button_set_temp);

        // set button of motor timer action
        mButtonSetHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInputHum.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(ManualAction.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 1000;
                if (millisInput == 0) {
                    Toast.makeText(ManualAction.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = FirebaseDatabase.getInstance();
                myReference = db.getReference("MotorTimerInSecond");
                myReference.setValue(Integer.parseInt(input));

                setTimeHum(millisInput);
                mEditTextInputHum.setText("");
            }
        });

        // set button of led timer action
        mButtonSetTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInputTemp.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(ManualAction.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 1000;
                if (millisInput == 0) {
                    Toast.makeText(ManualAction.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = FirebaseDatabase.getInstance();
                myReference = db.getReference("LedTimerInSecond");
                myReference.setValue(Integer.parseInt(input));
                
                setTimeTemp(millisInput);
                mEditTextInputTemp.setText("");
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
// get the humidity and temperature degree from firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String valueHumidity = dataSnapshot.child("humidity").getValue().toString();
                String valueTemperature = dataSnapshot.child("Temperature").getValue().toString();
                txtHumidity.setText(valueHumidity + "%");
                txtTemperature.setText(valueTemperature + "°C");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        // store timer value


        /*
        if(mEditTextInputTemp !=null) {
            myRef = database.getReference("LedTimeInMinutes");
            myRef.setValue(mEditTextInputTemp.getText().toString());
        }*/


        offMotor = true;
        motor_img = (ImageView) findViewById(R.id.motor_off_img);
        motorButtonControl();

        offLed=true;
        led_img = (ImageView) findViewById(R.id.led_off_img);
        ledButtonControl();
    }

    private void setTimeHum(long milliseconds) {
        mStartTimeInMillisHum = milliseconds;
        resetTimerHum();
        closeKeyboard();
    }

    private void setTimeTemp(long milliseconds) {
        mStartTimeInMillisTemp = milliseconds;
        resetTimerTemp();
        closeKeyboard();
    }

    private void startTimerHum() {
        mEndTimeHum = System.currentTimeMillis() + mTimeLeftInMillisHum;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillisHum, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillisHum = millisUntilFinished;
                updateCountDownTextHum();
            }

            @Override
            public void onFinish() {
                mTimerRunningHum = false;
                motor_img.setImageResource(R.drawable.off);
                motor_img.setTag(70);
                resetTimerHum();
                offMotor = true;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("MOTOR_STATUS");
                myRef.setValue("OFF");
            }
        }.start();

        mTimerRunningHum = true;

    }

    private void startTimerTemp() {
        mEndTimeTemp = System.currentTimeMillis() + mTimeLeftInMillisTemp;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillisTemp, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillisTemp = millisUntilFinished;
                updateCountDownTextTemp();
            }

            @Override
            public void onFinish() {
                mTimerRunningTemp = false;
                led_img.setImageResource(R.drawable.off);
                led_img.setTag(70);
                resetTimerTemp();
                offLed = true;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                myRef = database.getReference("LED_STATUS");
                myRef.setValue("OFF");
            }
        }.start();

        mTimerRunningTemp = true;

    }

    private void pauseTimerHum() {
        mCountDownTimer.cancel();
        mTimerRunningHum = false;
    }

    private void pauseTimerTemp() {
        mCountDownTimer.cancel();
        mTimerRunningTemp = false;
    }

    private void resetTimerHum() {
        mTimeLeftInMillisHum = mStartTimeInMillisHum;
        updateCountDownTextHum();

    }

    private void resetTimerTemp() {
        mTimeLeftInMillisTemp = mStartTimeInMillisTemp;
        updateCountDownTextTemp();

    }

    private void updateCountDownTextHum() {

        int hours = (int) (mTimeLeftInMillisHum / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillisHum / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillisHum / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDownHum.setText(timeLeftFormatted);
    }

    private void updateCountDownTextTemp() {

        int hours = (int) (mTimeLeftInMillisTemp / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillisTemp / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillisTemp / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDownTemp.setText(timeLeftFormatted);
    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillisHum", mStartTimeInMillisHum);
        editor.putLong("millisLeftHum", mTimeLeftInMillisHum);
        editor.putBoolean("timerRunningHum", mTimerRunningHum);
        editor.putLong("endTimeHum", mEndTimeHum);


        editor.putLong("startTimeInMillisTemp", mStartTimeInMillisTemp);
        editor.putLong("millisLeftTemp", mTimeLeftInMillisTemp);
        editor.putBoolean("timerRunningTemp", mTimerRunningTemp);
        editor.putLong("endTimeTemp", mEndTimeTemp);
        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTimeInMillisHum = prefs.getLong("startTimeInMillisHum", 600000);
        mTimeLeftInMillisHum = prefs.getLong("millisLeftHum", mStartTimeInMillisHum);
        mTimerRunningHum = prefs.getBoolean("timerRunningHum", false);


        mStartTimeInMillisTemp = prefs.getLong("startTimeInMillisTemp", 600000);
        mTimeLeftInMillisTemp = prefs.getLong("millisLeftTemp", mStartTimeInMillisHum);
        mTimerRunningTemp = prefs.getBoolean("timerRunningTemp", false);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                motorImg = dataSnapshot.child("MOTOR_STATUS").getValue().toString();
                if (motorImg == "ON") {
                    motor_img.setImageResource(R.drawable.on);
                    offMotor = false;
                    motorButtonControl();


                } else {
                    motor_img.setImageResource(R.drawable.off);
                    offMotor = true;
                    motorButtonControl();
                }

                ledImg = dataSnapshot.child("LED_STATUS").getValue().toString();
                if (ledImg == "ON") {
                    led_img.setImageResource(R.drawable.on);
                    offLed = false;
                    ledButtonControl();

                } else {
                    led_img.setImageResource(R.drawable.off);
                    offLed = true;
                    ledButtonControl();
                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        updateCountDownTextHum();
        updateCountDownTextTemp();

        if (mTimerRunningHum) {
            mEndTimeHum = prefs.getLong("endTimeHum", 0);
            mTimeLeftInMillisHum = mEndTimeHum - System.currentTimeMillis();
            if (mTimeLeftInMillisHum < 0) {
                mTimeLeftInMillisHum = 0;
                mTimerRunningHum = false;
                updateCountDownTextHum();

            } else {
                startTimerHum();
            }
        }

        if (mTimerRunningTemp) {
            mEndTimeTemp = prefs.getLong("endTimeTemp", 0);
            mTimeLeftInMillisTemp = mEndTimeTemp - System.currentTimeMillis();
            if (mTimeLeftInMillisTemp < 0) {
                mTimeLeftInMillisTemp = 0;
                mTimerRunningTemp = false;
                updateCountDownTextTemp();

            } else {
                startTimerTemp();
            }
        }
    }

    public void motorButtonControl() {
        motor_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (offMotor) {
                    startTimerHum();
                    motor_img.setImageResource(R.drawable.on);
                    offMotor = false;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("MOTOR_STATUS");
                    myRef.setValue("ON");
                } else {
                    if (mTimerRunningHum) {
                        pauseTimerHum();
                        motor_img.setImageResource(R.drawable.off);
                        motor_img.setTag(70);
                        offMotor = true;
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("MOTOR_STATUS");
                        myRef.setValue("OFF");
                    }
                }

            }
        });
    }

    public void ledButtonControl() {
        led_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (offLed) {
                    startTimerTemp();
                    led_img.setImageResource(R.drawable.on);
                    offLed = false;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("LED_STATUS");
                    myRef.setValue("ON");
                } else {
                    if (mTimerRunningTemp) {
                        pauseTimerTemp();
                        led_img.setImageResource(R.drawable.off);
                        led_img.setTag(70);
                        offLed = true;
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("LED_STATUS");
                        myRef.setValue("OFF");
                    }
                }

            }
        });
    }
}
