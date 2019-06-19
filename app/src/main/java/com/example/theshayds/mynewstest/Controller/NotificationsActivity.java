package com.example.theshayds.mynewstest.Controller;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.Utils.AlarmReceiver;
import com.example.theshayds.mynewstest.Utils.ApiStreams;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationsActivity extends AppCompatActivity {

    private Disposable disposable;
    private EditText mSearchTerm;
    private CheckBox mArts, mEntrepreneurs, mBusiness, mPolitics, mTravel, mSports;
    private Switch mNotificationSwitch;
    private SharedPreferences sharedPreferences;


    private static final String PREF_LAST_QUERY_ID = "lastQueryId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Bind UI items
        mSearchTerm = findViewById(R.id.search_query_text);
        mArts = findViewById(R.id.checkbox_arts);
        mEntrepreneurs = findViewById(R.id.checkbox_entrepreneurs);
        mBusiness = findViewById(R.id.checkbox_business);
        mPolitics = findViewById(R.id.checkbox_politics);
        mTravel = findViewById(R.id.checkbox_travel);
        mSports = findViewById(R.id.checkbox_sports);

        mNotificationSwitch = findViewById(R.id.notification_switch);
        sharedPreferences = getSharedPreferences("toggle_state", MODE_PRIVATE);
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
            if(isChecked){
                // Toggle switch button to "On"
                SharedPreferences.Editor mEditor = getSharedPreferences("toggle_state", MODE_PRIVATE).edit();
                mEditor.putBoolean("State", true);
                mEditor.apply();

                // TODO : Fetch data with API and try them
                mSearchTerm = findViewById(R.id.search_query_text);

                ArrayList<String> mQueries = new ArrayList<>();
                String mSearchQuery = mSearchTerm.getText().toString();

                if(!mSearchQuery.equals("")){
                    mQueries.add(mSearchQuery);
                }
                if(mArts.isChecked()){
                    mQueries.add("arts");
                }
                if(mEntrepreneurs.isChecked()){
                    mQueries.add("entrepreneurs");
                }
                if(mBusiness.isChecked()){
                    mQueries.add("business");
                }
                if(mPolitics.isChecked()){
                    mQueries.add("politics");
                }
                if(mTravel.isChecked()){
                    mQueries.add("travel");
                }
                if(mSports.isChecked()){
                    mQueries.add("sports");
                }

                // Separate queries with "&" symbol
                String result = TextUtils.join("&", mQueries);

                // API query with all parameters
                disposable = ApiStreams.streamArticlesParameters(result).subscribeWith(new DisposableObserver<ArticleSearch>() {
                    @Override
                    public void onNext(ArticleSearch articleSearch) {
                        String lastQueryId = articleSearch.getResponse().getDocs().get(0).getSnippet();
                        String lastQueryURL = articleSearch.getResponse().getDocs().get(0).getWebUrl();

                        setLastQueryResultId(getApplicationContext(), lastQueryId);
                        setLastQueryResultURL(getApplicationContext(), lastQueryURL);

                        int mNotificationID = 1;
                        // Set NotificationID and Text
                        Intent mIntent = new Intent(NotificationsActivity.this, AlarmReceiver.class);
                        mIntent.putExtra("NotificationID", mNotificationID);
                        mIntent.putExtra("Title", "MyNews");
                        mIntent.putExtra("URL", "");
                        mIntent.putExtra("Content", "De nouveaux articles sont disponibles.");

                        Calendar mStartTime = Calendar.getInstance();

                        // Get Broadcast(context, requestCode, intent, flags)
                        PendingIntent mAlarmIntent = PendingIntent.getBroadcast(NotificationsActivity.this, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager mAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                        if (mAlarm != null) {
                            mAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, mStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mAlarmIntent);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
            }
            else {
                // Toggle switch button to "Off"
                SharedPreferences.Editor mEditor = getSharedPreferences("toggle_state", MODE_PRIVATE).edit();
                mEditor.putBoolean("State", false);
                mEditor.apply();

                // TODO : Cancel AlarmReceiver
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotificationManager.cancel(getIntent().getExtras().getInt("NotificationID"));
            }
        });
    }


    // Save LastQueryId = Title
    public static void setLastQueryResultId(Context context, String lastQueryId){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_QUERY_ID, lastQueryId)
                .apply();
    }

    // Save LastQueryURL = URL
    public static void setLastQueryResultURL(Context context, String lastQueryId){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("URL", lastQueryId)
                .apply();
    }

    public String getLastQueryResultId(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(PREF_LAST_QUERY_ID, "");

    }

    public String getLastQueryResultURL(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("URL", "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
