/**
 * Copyright Google Inc. All Rights Reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import androidx.appcompat.app.AppCompatActivity

/**
 * Demonstrate Firebase Authentication using a custom minted token. For more information, see:
 * https://firebase.google.com/docs/auth/android/custom-auth
 */
class CustomAuthActivity : AppCompatActivity(), android.view.View.OnClickListener {
    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    private var mBinding: ActivityCustomBinding? = null
    private var mCustomToken: String? = null
    private var mTokenReceiver: com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.TokenBroadcastReceiver? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCustomBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())

        // Button click listeners
        mBinding.buttonSignIn.setOnClickListener(this)

        // Create token receiver (for demo purposes only)
        mTokenReceiver = object : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.TokenBroadcastReceiver() {
            override fun onNewToken(token: String) {
                android.util.Log.d(TAG, "onNewToken:$token")
                setCustomToken(token)
            }
        }

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser = mAuth.getCurrentUser()
        updateUI(currentUser)
    }

    // [END on_start_check_user]
    protected fun onResume() {
        super.onResume()
        registerReceiver(mTokenReceiver, com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.TokenBroadcastReceiver.Companion.getFilter())
    }

    protected fun onPause() {
        super.onPause()
        unregisterReceiver(mTokenReceiver)
    }

    private fun startSignIn() {
        // Initiate sign in with custom token
        // [START sign_in_custom]
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(TAG, "signInWithCustomToken:success")
                            val user: FirebaseUser = mAuth.getCurrentUser()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "signInWithCustomToken:failure", task.getException())
                            android.widget.Toast.makeText(this@CustomAuthActivity, "Authentication failed.",
                                    android.widget.Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
                })
        // [END sign_in_custom]
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            mBinding.textSignInStatus.setText("User ID: " + user.getUid())
        } else {
            mBinding.textSignInStatus.setText("Error: sign in failed.")
        }
    }

    private fun setCustomToken(token: String) {
        mCustomToken = token
        val status: String
        status = if (mCustomToken != null) {
            "Token:$mCustomToken"
        } else {
            "Token: null"
        }

        // Enable/disable sign-in button and show the token
        mBinding.buttonSignIn.setEnabled(mCustomToken != null)
        mBinding.textTokenStatus.setText(status)
    }

    override fun onClick(v: android.view.View) {
        val i: Int = v.getId()
        if (i == R.id.buttonSignIn) {
            startSignIn()
        }
    }

    companion object {
        private const val TAG = "CustomAuthActivity"
    }
}