package com.example.theshayds.mynewstest.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.R;

import java.util.Arrays;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "AlarmReceiver";

    public static int NOTIFICATION_ID = 1;
    Disposable disposable;

    int numberOfNewArticles;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive.");

        // Fetch data from user's SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("notification_queries", MODE_PRIVATE);

        // Get latest queries entered by the user when setting up notifications
        String mQuery = sharedPreferences.getString("notificationQuery", intent.getExtras().getString("query"));
        String mFilterQuery = sharedPreferences.getString("notificationFilterQuery", intent.getExtras().getString("filterQuery"));

        // Retrofit request using notification queries parameters
        disposable = ApiStreams.streamArticlesParameters(mQuery, mFilterQuery).subscribeWith(new DisposableObserver<ArticleSearch>() {
            @Override
            public void onNext(ArticleSearch articleSearch) {

                // Number of new articles found
                numberOfNewArticles = articleSearch.getResponse().getDocs().size();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + Arrays.toString(e.getStackTrace()));
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete " + numberOfNewArticles);

                // Build notification with Title and Content
                Notification.Builder builder = new Notification.Builder(context);
                Notification notification = builder.setSmallIcon(R.mipmap.ic_ny_time)
                        .setContentTitle("MyNews")
                        .setContentText(numberOfNewArticles + " new articles found.")
                        .setAutoCancel(true)
                        .build();

                // Show notification
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_ID, notification);

            }
        });







    }

}
