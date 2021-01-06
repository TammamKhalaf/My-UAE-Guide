package com.tammamkhalaf.myuaeguide.LocationOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.tammamkhalaf.myuaeguide.Databases.SessionManager;
import com.tammamkhalaf.myuaeguide.R;

import java.util.HashMap;

public class RetailerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboard);


        TextView textView = findViewById(R.id.textView);

        SessionManager sessionManager = new SessionManager(this);

        HashMap<String,String> userDetails = sessionManager.getUsersDetailFromSession();

        String fullName = userDetails.get(sessionManager.KEY_FULLNAME);
        String phoneNo = userDetails.get(sessionManager.KEY_PHONE_NUMBER);

        textView.setText(fullName+"\n"+phoneNo);

    }
}