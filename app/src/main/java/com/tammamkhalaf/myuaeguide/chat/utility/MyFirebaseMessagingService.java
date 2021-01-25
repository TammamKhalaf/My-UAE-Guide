package com.tammamkhalaf.myuaeguide.chat.utility;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";


    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        String notificationBody ="";
        String notificationTitle ="";
        String notificationData ="";

        try {

            notificationBody = remoteMessage.getNotification().getBody();
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationData = remoteMessage.getData().toString();

            Log.d(TAG, "onMessageReceived: notificationBody "+notificationBody);
            Log.d(TAG, "onMessageReceived: notificationTitle "+notificationTitle);
            Log.d(TAG, "onMessageReceived: notificationData "+notificationData);

        }catch (NullPointerException e){
            Log.d(TAG, "onMessageReceived: "+e.getMessage());
        }

    }


}
