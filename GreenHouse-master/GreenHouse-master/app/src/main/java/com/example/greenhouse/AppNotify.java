package com.example.greenhouse;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AppNotify extends Application {

    public static final String CHANNEL_1_ID="channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        //check android Version

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {

            //identify channel
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "channel1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is Channel 1");

            //Create channel
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }
}
