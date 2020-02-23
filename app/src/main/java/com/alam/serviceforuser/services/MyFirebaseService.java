package com.alam.serviceforuser.services;

import android.util.Log;

import com.alam.serviceforuser.data.DataPreference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseService extends FirebaseMessagingService {

   private static final String TAG="MyFireBaseService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());



        }


    @Override
    public void onNewToken(String token) {
        new DataPreference(getApplicationContext()).setToken(token);
        Log.d(TAG, "Refreshed token: " + token);

    }
}
