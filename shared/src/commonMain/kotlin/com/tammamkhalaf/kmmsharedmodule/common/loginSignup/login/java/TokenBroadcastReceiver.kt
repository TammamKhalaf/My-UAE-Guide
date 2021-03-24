/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

/**
 * Receiver to capture tokens broadcast via ADB and insert them into the
 * running application to facilitate easy testing of custom authentication.
 */
abstract class TokenBroadcastReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: android.content.Context, intent: android.content.Intent) {
        android.util.Log.d(TAG, "onReceive:$intent")
        if (ACTION_TOKEN == intent.getAction()) {
            val token: String = intent.getExtras().getString(EXTRA_KEY_TOKEN)
            onNewToken(token)
        }
    }

    abstract fun onNewToken(token: String?)

    companion object {
        private const val TAG = "TokenBroadcastReceiver"
        const val ACTION_TOKEN = "com.google.example.ACTION_TOKEN"
        const val EXTRA_KEY_TOKEN = "key_token"
        val filter: android.content.IntentFilter
            get() = android.content.IntentFilter(ACTION_TOKEN)
    }
}