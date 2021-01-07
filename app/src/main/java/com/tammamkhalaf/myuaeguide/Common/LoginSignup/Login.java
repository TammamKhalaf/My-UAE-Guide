package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.ForgetPassword.ForgetPassword;
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.SignUp.SignUp;
import com.tammamkhalaf.myuaeguide.Databases.SessionManager;
import com.tammamkhalaf.myuaeguide.LocationOwner.RetailerDashboard;
import com.tammamkhalaf.myuaeguide.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity {


    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;
    CheckBox RememberMe;

    TextInputEditText phoneNumberEditText, passwordEditText;//todo create hooks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);

        RememberMe = findViewById(R.id.remember_me);
        phoneNumberEditText = findViewById(R.id.login_phone_number_editText);
        passwordEditText = findViewById(R.id.login_password_editText);

        SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBER_ME);
        if(sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phoneNumberEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PHONE_NUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PASSWORD));
        }
    }

    public void RememberMeButton(View view){

    }

    public void callForgetPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }

    public void letTheUserLoggedIn(View view) {
        if(!isConnected(this)){
            showCustomDialog();
        }

        progressbar.setVisibility(View.VISIBLE);

        if (!validateFields()) {
            return;
        }

        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        //Get complete phone number
        String _getUserEnteredPhoneNumber = _phoneNumber;
        //Remove first zero if entered!
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        //Complete phone number
        final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        if(RememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBER_ME);
            sessionManager.createRememberMeSession(_password,_getUserEnteredPhoneNumber);
        }

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_phoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = snapshot.child(_phoneNo).child("password").getValue(String.class);
                    if (Objects.requireNonNull(systemPassword).equals(_password)) {
                        password.setError(null);
                        phoneNumber.setErrorEnabled(false);

                        String systemFullName = snapshot.child(_phoneNo).child("fullName").getValue(String.class);
                        String systemUsername = snapshot.child(_phoneNo).child("username").getValue(String.class);
                        String systemEmail = snapshot.child(_phoneNo).child("email").getValue(String.class);
                        String systemDOB = snapshot.child(_phoneNo).child("date").getValue(String.class);
                        String systemPhoneNo = snapshot.child(_phoneNo).child("phoneNo").getValue(String.class);
                        String systemGender = snapshot.child(_phoneNo).child("gender").getValue(String.class);

                        SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_USER_SESSION);
                        sessionManager.createLoginSession(systemFullName,systemUsername,systemEmail,systemPassword,systemGender,systemDOB,systemPhoneNo);

                        startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));

                    } else {
                        Toast.makeText(Login.this, getString(R.string.PasswordDoesntMatch), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, getString(R.string.NoSuchUser), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        return wifiConnection != null && wifiConnection.isConnected() || (mobileConnection != null && mobileConnection.isConnected());

    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage(R.string.ConnectInternet)
        .setCancelable(false)
        .setPositiveButton(R.string.connect, (dialogInterface, i) -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }).setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            startActivity(new Intent(getApplicationContext(),RetailerStartUpScreen.class));
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean validateFields() {
        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError(getString(R.string.Empty_Field));
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError(getString(R.string.Empty_Field));
            password.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    public void callSignUpScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUp.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.signup_btn), "transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

}