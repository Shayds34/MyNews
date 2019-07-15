package com.example.theshayds.mynewstest.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNetworkInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(AndroidJUnit4.class)
public class NetworkStatusTest {
    private ConnectivityManager manager;
    private ShadowNetworkInfo shadowNetworkInfo;
    private NetworkStatus instance;

    @Before
    public void setUp() throws Exception {
        manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        shadowNetworkInfo = shadowOf(manager.getActiveNetworkInfo());
        instance = NetworkStatus.getInstance(getApplicationContext());
    }

    @Test
    @Config(manifest=Config.NONE)
    public void getActiveNetworkInfo_shouldReturnTrueCorrectly() {
        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        assertTrue(instance.isOnline());

        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTING);
        assertTrue(instance.isOnline());

        shadowNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);
        assertFalse(instance.isOnline());
    }
}
