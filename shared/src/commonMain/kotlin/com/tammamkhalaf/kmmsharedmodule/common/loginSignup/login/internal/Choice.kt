package com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal

class Choice(val title: String, val description: String, launchIntent: android.content.Intent) {
    val launchIntent: android.content.Intent

    init {
        this.launchIntent = launchIntent
    }
}