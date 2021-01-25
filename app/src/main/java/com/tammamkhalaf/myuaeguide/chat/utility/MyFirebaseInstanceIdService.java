package com.tammamkhalaf.myuaeguide.chat.utility;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tammamkhalaf.myuaeguide.R;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIdSer";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onTokenRefresh: refreshedToken = "+refreshedToken);
        sendRegistrationTokenToServer(refreshedToken);
    }

    private void sendRegistrationTokenToServer(String refreshedToken) {
        Log.d(TAG, "sendRegistrationTokenToServer: sending token to the server"+refreshedToken);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Users")
                 .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                 .child("messaging_token").setValue(refreshedToken);



    }


}