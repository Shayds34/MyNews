package com.example.theshayds.mynewstest.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.R;

import java.util.Arrays;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "AlarmReceiver";

    public static int NOTIFICATION_ID = 1;
    Disposable disposable;

    int numberOfNewArticles;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive.");

        String mQuery = intent.getExtras().getString("query");
        String mFilterQuery = intent.getExtras().getString("filterQuery");

        Log.d(TAG, "Query is " + mQuery + " FilterQuery is " + mFilterQuery);

        disposable = ApiStreams.streamArticlesParameters(mQuery, mFilterQuery).subscribeWith(new DisposableObserver<ArticleSearch>() {
            @Override
            public void onNext(ArticleSearch articleSearch) {
                Log.d(TAG, "onNext: number of articles " + articleSearch.getResponse().getDocs().size());
                numberOfNewArticles = articleSearch.getResponse().getDocs().size();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + Arrays.toString(e.getStackTrace()));
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete " + numberOfNewArticles);

                Notification.Builder builder = new Notification.Builder(context);
                Notification notification = builder.setSmallIcon(R.mipmap.ic_ny_time)
                        .setContentTitle("MyNews")
                        .setContentText(numberOfNewArticles + " new articles found.")
                        .setAutoCancel(true)
                        .build();

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_ID, notification);

            }
        });







    }

}
