package com.example.theshayds.mynewstest.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.Utils.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.GONE;

public class NotificationsActivity extends AppCompatActivity {
    public static final String TAG = "NotificationsActivity";

    private EditText mSearchTerm;
    private CheckBox mArts, mEntrepreneurs, mBusiness, mPolitics, mTravel, mSports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        findViewById(R.id.fields_begin_date).setVisibility(GONE);
        findViewById(R.id.fields_end_date).setVisibility(GONE);

        // Bind UI items
        mSearchTerm = findViewById(R.id.search_query_text);
        mArts = findViewById(R.id.checkbox_arts);
        mEntrepreneurs = findViewById(R.id.checkbox_entrepreneurs);
        mBusiness = findViewById(R.id.checkbox_business);
        mPolitics = findViewById(R.id.checkbox_politics);
        mTravel = findViewById(R.id.checkbox_travel);
        mSports = findViewById(R.id.checkbox_sports);

        Switch mNotificationSwitch = findViewById(R.id.notification_switch);
        SharedPreferences sharedPreferences = getSharedPreferences("toggle_state", MODE_PRIVATE);
        mNotificationSwitch.setChecked(sharedPreferences.getBoolean("State", false));

        // Create Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Notifications");
        setSupportActionBar(mToolbar);

        // Add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        // Create switch events
        mNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                // Toggle switch button to "On"
                SharedPreferences.Editor mEditor = getSharedPreferences("toggle_state", MODE_PRIVATE).edit();
                mEditor.putBoolean("State", true);
                mEditor.apply();

                mSearchTerm = findViewById(R.id.search_query_text);
                String mSearchQuery = mSearchTerm.getText().toString();

                ArrayList<String> mQueries = new ArrayList<>();

                if (mSearchQuery.isEmpty()){

                    Toast.makeText(this, "You have to enter a search query term and at least one checkbox.", Toast.LENGTH_SHORT).show();
                    mNotificationSwitch.setChecked(false);

                } else if ((mArts.isChecked() || mEntrepreneurs.isChecked() || mBusiness.isChecked() || mPolitics.isChecked() || mTravel.isChecked() || mSports.isChecked())) {

                    if (mArts.isChecked()) {
                        mQueries.add("arts");
                    }
                    if (mEntrepreneurs.isChecked()) {
                        mQueries.add("entrepreneurs");
                    }
                    if (mBusiness.isChecked()) {
                        mQueries.add("business");
                    }
                    if (mPolitics.isChecked()) {
                        mQueries.add("politics");
                    }
                    if (mTravel.isChecked()) {
                        mQueries.add("travel");
                    }
                    if (mSports.isChecked()) {
                        mQueries.add("sports");
                    }

                    // Separate queries with empty space " ".
                    String result = TextUtils.join(" ", mQueries);

                    mEditor = getSharedPreferences("notification_queries", MODE_PRIVATE).edit();
                    mEditor.putString("notificationQuery", mSearchQuery);
                    mEditor.putString("notificationFilterQuery", result);

                    // Start Notification
                    startAlarmReceiver(mSearchQuery, result);
                } else {
                    Toast.makeText(this, "You have to enter a search query term and at least one checkbox.", Toast.LENGTH_SHORT).show();
                    mNotificationSwitch.setChecked(false);
                }
            } else {

                // Toggle switch button to "Off"
                SharedPreferences.Editor mEditor = getSharedPreferences("toggle_state", MODE_PRIVATE).edit();
                mEditor.putBoolean("State", false);
                mEditor.apply();

                // Cancel Notification
                cancelAlarmReceiver();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    // Start alarmReceiver
    private void startAlarmReceiver(String query, String filterQuery){

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("query", query);
        notificationIntent.putExtra("filterQuery", filterQuery);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar mStartTime = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d(TAG, "startAlarmReceiver: started");
    }

    
    // Cancel notification
    private void cancelAlarmReceiver() {

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "The notifications system has been cancelled.", Toast.LENGTH_LONG).show();
        
        Log.d(TAG, "cancelAlarmReceiver: cancelled");
    }
}
