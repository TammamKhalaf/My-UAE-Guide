package com.tammamkhalaf.myuaeguide.databases

import android.content.Context
import android.content.SharedPreferences
import com.tammamkhalaf.myuaeguide.common.loginSignup.Login
import com.tammamkhalaf.myuaeguide.locationOwner.RetailerProfileFragment
import java.util.*

class SessionManager(var context: Login, sessionName: String?) {


    var editor: SharedPreferences.Editor
    fun createLoginSession(fullName: String?, username: String?, email: String?, password: String?, gender: String?, date: String?, phoneNo: String?) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_FULLNAME, fullName)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.putString(KEY_PHONE_NUMBER, phoneNo)
        editor.putString(KEY_DATE, date)
        editor.putString(KEY_GENDER, gender)
        editor.commit()
    }

    fun createRememberMeSession(password: String?, phoneNo: String?) {
        editor.putBoolean(IS_REMEMBER_ME, true)
        editor.putString(KEY_SESSION_PASSWORD, password)
        editor.putString(KEY_SESSION_PHONE_NUMBER, phoneNo)
        editor.commit()
    }



    fun checkLogin(): Boolean {
        return userSessions.getBoolean(IS_LOGIN, true)
    }

    fun logoutUserFromSession() {
        editor.clear()
        editor.clear()
    }

    val rememberMeDetailFromSession: HashMap<String, String?>
        get() {
            val userData = HashMap<String, String?>()
            userData[KEY_SESSION_PASSWORD] = userSessions.getString(KEY_SESSION_PASSWORD, null)
            userData[KEY_SESSION_PHONE_NUMBER] = userSessions.getString(KEY_SESSION_PHONE_NUMBER, null)
            return userData
        }

    fun checkRememberMe(): Boolean {
        return userSessions.getBoolean(IS_REMEMBER_ME, true)
    }

    companion object {
        lateinit var userSessions: SharedPreferences

        val usersDetailFromSession: HashMap<String, String?> get() {
            val userData = HashMap<String, String?>()
            userData[KEY_FULLNAME] = userSessions.getString(KEY_FULLNAME, null)
            userData[KEY_USERNAME] = userSessions.getString(KEY_USERNAME, null)
            userData[KEY_EMAIL] = userSessions.getString(KEY_EMAIL, null)
            userData[KEY_PASSWORD] = userSessions.getString(KEY_PASSWORD, null)
            userData[KEY_PHONE_NUMBER] = userSessions.getString(KEY_PHONE_NUMBER, null)
            userData[KEY_DATE] = userSessions.getString(KEY_DATE, null)
            userData[KEY_GENDER] = userSessions.getString(KEY_GENDER, null)
            return userData
        }


        const val SESSION_USER_SESSION = "userLoginSession"
        const val SESSION_REMEMBER_ME = "rememberMe"
        private const val IS_LOGIN = "IsLoggedIn"
        private const val IS_REMEMBER_ME = "IsRememberMe"
        const val KEY_SESSION_PASSWORD = "password"
        const val KEY_SESSION_PHONE_NUMBER = "phoneNo"
        const val KEY_FULLNAME = "fullName"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_PHONE_NUMBER = "phoneNo"
        const val KEY_DATE = "date"
        const val KEY_GENDER = "gender"
    }

    init {
        userSessions = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE)
        editor = userSessions.edit()
    }
}