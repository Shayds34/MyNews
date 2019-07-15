package com.example.theshayds.mynewstest.Utils;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;

public class NetworkStatusTest {

    private NetworkStatus networkStatus;

    @Mock
    private Context context;

    @Before
    public void setUpTest(){
        MockitoAnnotations.initMocks(this);
        networkStatus = NetworkStatus.getInstance(context);
    }

    @Test
    public void isConnectedTest(){

        boolean isConnected = networkStatus.isOnline();

        assertFalse(isConnected);
    }
}
