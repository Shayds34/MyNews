package com.example.theshayds.mynewstest.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkStatus {

    private static NetworkStatus instance = new NetworkStatus();
    private static Context context;
    private boolean isConnected = false;

    public static NetworkStatus getInstance(Context mContext){
        context = mContext.getApplicationContext();
        return instance;
    }

    public boolean isOnline(){
        try{
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            isConnected = mNetworkInfo != null && mNetworkInfo.isAvailable() && mNetworkInfo.isConnectedOrConnecting();

            return isConnected;

        } catch (Exception e){
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }
        return isConnected;
    }
}
