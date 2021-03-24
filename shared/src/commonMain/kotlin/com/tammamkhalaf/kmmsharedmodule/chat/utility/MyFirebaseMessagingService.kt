package com.tammamkhalaf.myuaeguide.chat.utility

import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    fun onMessageReceived(@NonNull @NotNull remoteMessage: RemoteMessage) {
        var notificationBody = ""
        var notificationTitle = ""
        var notificationData = ""
        try {
            notificationBody = remoteMessage.getNotification().getBody()
            notificationTitle = remoteMessage.getNotification().getTitle()
            notificationData = remoteMessage.getData().toString()
            android.util.Log.d(TAG, "onMessageReceived: notificationBody $notificationBody")
            android.util.Log.d(TAG, "onMessageReceived: notificationTitle $notificationTitle")
            android.util.Log.d(TAG, "onMessageReceived: notificationData $notificationData")
        } catch (e: java.lang.NullPointerException) {
            android.util.Log.d(TAG, "onMessageReceived: " + e.message)
        }
    }

    companion object {
        private const val TAG = "MyFirebaseMessagingServ"
    }
}