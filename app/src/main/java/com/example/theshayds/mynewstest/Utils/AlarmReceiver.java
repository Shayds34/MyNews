package com.example.theshayds.mynewstest.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.theshayds.mynewstest.Controller.NotificationsActivity;
import com.example.theshayds.mynewstest.R;

import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiver extends BroadcastReceiver {

    SharedPreferences mSharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        int mNotificationID = intent.getIntExtra("NotificationID", 0);
        String mTitle = intent.getStringExtra("Title");
        String mContent = intent.getStringExtra("Content");

        NotificationsActivity mGetTitle = new NotificationsActivity();
        String textToCompare = mGetTitle.getLastQueryResultId(context);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_ny_time)
                .setContentTitle(mTitle)
                .setContentText(textToCompare)
                .setAutoCancel(true);

        mNotificationManager.notify(mNotificationID, mBuilder.build());

    }
}
