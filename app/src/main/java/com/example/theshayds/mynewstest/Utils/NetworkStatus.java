package com.example.theshayds.mynewstest.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetworkStatus {

    private static NetworkStatus instance = new NetworkStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    boolean connected = false;

    public static NetworkStatus getInstance(Context mContext){
        context = mContext.getApplicationContext();
        return instance;
    }

    public boolean isOnline(){
        try{
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
            connected = mNetworkInfo != null && mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();

            return connected;

        } catch (Exception e){
            System.out.println("CheckConnectivity Exception :" + e.getMessage());
            Log.v("Connectivity", e.toString());
        }
        return connected;
    }
}
