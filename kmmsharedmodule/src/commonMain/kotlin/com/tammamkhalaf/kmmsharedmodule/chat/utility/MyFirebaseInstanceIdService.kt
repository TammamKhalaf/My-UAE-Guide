package com.tammamkhalaf.myuaeguide.chat.utility

import com.google.firebase.auth.FirebaseAuth

class MyFirebaseInstanceIdService : FirebaseInstanceIdService() {
    fun onTokenRefresh() {
        val refreshedToken: String = FirebaseInstanceId.getInstance().getToken()
        android.util.Log.d(TAG, "onTokenRefresh: refreshedToken = $refreshedToken")
        sendRegistrationTokenToServer(refreshedToken)
    }

    private fun sendRegistrationTokenToServer(refreshedToken: String) {
        android.util.Log.d(TAG, "sendRegistrationTokenToServer: sending token to the server$refreshedToken")
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        reference.child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("messaging_token").setValue(refreshedToken)
    }

    companion object {
        private const val TAG = "MyFirebaseInstanceIdSer"
    }
}