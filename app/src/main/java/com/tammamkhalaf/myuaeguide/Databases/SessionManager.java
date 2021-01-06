package com.tammamkhalaf.myuaeguide.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSessions;
    SharedPreferences.Editor editor;
    Context context;

    public static final String SESSION_USER_SESSION = "userLoginSession";
    public static final String SESSION_REMEMBER_ME = "rememberMe";

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String IS_REMEMBER_ME = "IsRememberMe";
    public static final String KEY_SESSION_PASSWORD = "password";
    public static final String KEY_SESSION_PHONE_NUMBER = "phoneNo";


    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE_NUMBER = "phoneNo";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    public SessionManager(Context _context,String sessionName) {
        this.context = _context;
        userSessions = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSessions.edit();
    }

    public void createLoginSession(String fullName, String username, String email, String password, String gender, String date, String phoneNo) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONE_NUMBER, phoneNo);
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_GENDER, gender);
        editor.commit();
    }

    public void createRememberMeSession(String password,String phoneNo) {
        editor.putBoolean(IS_REMEMBER_ME, true);
        editor.putString(KEY_SESSION_PASSWORD, password);
        editor.putString(KEY_SESSION_PHONE_NUMBER, phoneNo);
        editor.commit();
    }

    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put(KEY_FULLNAME, userSessions.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSessions.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSessions.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSessions.getString(KEY_PASSWORD, null));
        userData.put(KEY_PHONE_NUMBER, userSessions.getString(KEY_PHONE_NUMBER, null));
        userData.put(KEY_DATE, userSessions.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSessions.getString(KEY_GENDER, null));
        return userData;
    }

    public boolean checkLogin() {
        if (userSessions.getBoolean(IS_LOGIN, true)) {
            return true;
        } else return false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.clear();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put(KEY_SESSION_PASSWORD, userSessions.getString(KEY_SESSION_PASSWORD, null));
        userData.put(KEY_SESSION_PHONE_NUMBER, userSessions.getString(KEY_SESSION_PHONE_NUMBER, null));
        return userData;
    }

    public boolean checkRememberMe() {
        if (userSessions.getBoolean(IS_REMEMBER_ME, true)) {
            return true;
        } else return false;
    }

}
