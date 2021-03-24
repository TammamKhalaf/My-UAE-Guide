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

import com.facebook.AccessToken

/**
 * Demonstrate Firebase Authentication using a Facebook access token.
 */
class FacebookLoginActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    private var mBinding: ActivityFacebookBinding? = null

    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    private var mCallbackManager: CallbackManager? = null
    fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFacebookBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        setProgressBar(mBinding.progressBar)

        // Views
        mBinding.buttonFacebookSignout.setOnClickListener(this)

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create()
        val loginButton: LoginButton = mBinding.buttonFacebookLogin
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult?>() {
            fun onSuccess(loginResult: LoginResult) {
                android.util.Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.getAccessToken())
            }

            fun onCancel() {
                android.util.Log.d(TAG, "facebook:onCancel")
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }

            fun onError(error: FacebookException?) {
                android.util.Log.d(TAG, "facebook:onError", error)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        })
        // [END initialize_fblogin]
    }

    // [START on_start_check_user]
    fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser = mAuth.getCurrentUser()
        updateUI(currentUser)
    }

    // [END on_start_check_user]
    // [START on_activity_result]
    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    // [END on_activity_result]
    // [START auth_with_facebook]
    private fun handleFacebookAccessToken(token: AccessToken) {
        android.util.Log.d(TAG, "handleFacebookAccessToken:$token")
        // [START_EXCLUDE silent]
        showProgressBar()
        // [END_EXCLUDE]
        val credential: AuthCredential = FacebookAuthProvider.getCredential(token.getToken())
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(TAG, "signInWithCredential:success")
                            val user: FirebaseUser = mAuth.getCurrentUser()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "signInWithCredential:failure", task.getException())
                            android.widget.Toast.makeText(this@FacebookLoginActivity, "Authentication failed.",
                                    android.widget.Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }

                        // [START_EXCLUDE]
                        hideProgressBar()
                        // [END_EXCLUDE]
                    }
                })
    }

    // [END auth_with_facebook]
    fun signOut() {
        mAuth.signOut()
        LoginManager.getInstance().logOut()
        updateUI(null)
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressBar()
        if (user != null) {
            mBinding.status.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()))
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()))
            mBinding.buttonFacebookLogin.setVisibility(android.view.View.GONE)
            mBinding.buttonFacebookSignout.setVisibility(android.view.View.VISIBLE)
        } else {
            mBinding.status.setText(R.string.signed_out)
            mBinding.detail.setText(null)
            mBinding.buttonFacebookLogin.setVisibility(android.view.View.VISIBLE)
            mBinding.buttonFacebookSignout.setVisibility(android.view.View.GONE)
        }
    }

    override fun onClick(v: android.view.View) {
        val i: Int = v.getId()
        if (i == R.id.buttonFacebookSignout) {
            signOut()
        }
    }

    companion object {
        private const val TAG = "FacebookLogin"
    }
}