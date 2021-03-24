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

import com.google.android.gms.auth.api.signin.GoogleSignIn

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
class GoogleSignInActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mBinding: ActivityGoogleBinding? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGoogleBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        setProgressBar(mBinding.progressBar)

        // Button listeners
        mBinding.signInButton.setOnClickListener(this)
        mBinding.signOutButton.setOnClickListener(this)
        mBinding.disconnectButton.setOnClickListener(this)

        // [START config_signin]
        // Configure Google Sign In
        val gso: GoogleSignInOptions = Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

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
    // [START onactivityresult]
    fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                android.util.Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId())
                firebaseAuthWithGoogle(account.getIdToken())
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                android.util.Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }

    // [END onactivityresult]
    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        // [START_EXCLUDE silent]
        showProgressBar()
        // [END_EXCLUDE]
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
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
                            Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                            updateUI(null)
                        }

                        // [START_EXCLUDE]
                        hideProgressBar()
                        // [END_EXCLUDE]
                    }
                })
    }

    // [END auth_with_google]
    // [START signin]
    private fun signIn() {
        val signInIntent: android.content.Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // [END signin]
    private fun signOut() {
        // Firebase sign out
        mAuth.signOut()

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                object : OnCompleteListener<java.lang.Void?>() {
                    fun onComplete(@NonNull task: Task<java.lang.Void?>?) {
                        updateUI(null)
                    }
                })
    }

    private fun revokeAccess() {
        // Firebase sign out
        mAuth.signOut()

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                object : OnCompleteListener<java.lang.Void?>() {
                    fun onComplete(@NonNull task: Task<java.lang.Void?>?) {
                        updateUI(null)
                    }
                })
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressBar()
        if (user != null) {
            mBinding.status.setText(getString(R.string.google_status_fmt, user.getEmail()))
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()))
            mBinding.signInButton.setVisibility(android.view.View.GONE)
            mBinding.signOutAndDisconnect.setVisibility(android.view.View.VISIBLE)
        } else {
            mBinding.status.setText(R.string.signed_out)
            mBinding.detail.setText(null)
            mBinding.signInButton.setVisibility(android.view.View.VISIBLE)
            mBinding.signOutAndDisconnect.setVisibility(android.view.View.GONE)
        }
    }

    override fun onClick(v: android.view.View) {
        val i: Int = v.getId()
        if (i == R.id.signInButton) {
            signIn()
        } else if (i == R.id.signOutButton) {
            signOut()
        } else if (i == R.id.disconnectButton) {
            revokeAccess()
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}