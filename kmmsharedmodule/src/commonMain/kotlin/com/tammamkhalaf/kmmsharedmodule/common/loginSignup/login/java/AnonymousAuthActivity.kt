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

import com.google.android.gms.tasks.OnCompleteListener

/**
 * Activity to demonstrate anonymous login and account linking (with an email/password account).
 */
class AnonymousAuthActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    private var mBinding: ActivityAnonymousAuthBinding? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAnonymousAuthBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        // Fields
        setProgressBar(mBinding.progressBar)

        // Click listeners
        mBinding.buttonAnonymousSignIn.setOnClickListener(this)
        mBinding.buttonAnonymousSignOut.setOnClickListener(this)
        mBinding.buttonLinkAccount.setOnClickListener(this)
    }

    // [START on_start_check_user]
    fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser = mAuth.getCurrentUser()
        updateUI(currentUser)
    }

    // [END on_start_check_user]
    private fun signInAnonymously() {
        showProgressBar()
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(TAG, "signInAnonymously:success")
                            val user: FirebaseUser = mAuth.getCurrentUser()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "signInAnonymously:failure", task.getException())
                            android.widget.Toast.makeText(this@AnonymousAuthActivity, "Authentication failed.",
                                    android.widget.Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }

                        // [START_EXCLUDE]
                        hideProgressBar()
                        // [END_EXCLUDE]
                    }
                })
        // [END signin_anonymously]
    }

    private fun signOut() {
        mAuth.signOut()
        updateUI(null)
    }

    private fun linkAccount() {
        // Make sure form is valid
        if (!validateLinkForm()) {
            return
        }

        // Get email and password from form
        val email: String = mBinding.fieldEmail.getText().toString()
        val password: String = mBinding.fieldPassword.getText().toString()

        // Create EmailAuthCredential with email and password
        val credential: AuthCredential = EmailAuthProvider.getCredential(email, password)

        // Link the anonymous user to the email credential
        showProgressBar()

        // [START link_credential]
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            android.util.Log.d(TAG, "linkWithCredential:success")
                            val user: FirebaseUser = task.getResult().getUser()
                            updateUI(user)
                        } else {
                            android.util.Log.w(TAG, "linkWithCredential:failure", task.getException())
                            android.widget.Toast.makeText(this@AnonymousAuthActivity, "Authentication failed.",
                                    android.widget.Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }

                        // [START_EXCLUDE]
                        hideProgressBar()
                        // [END_EXCLUDE]
                    }
                })
        // [END link_credential]
    }

    private fun validateLinkForm(): Boolean {
        var valid = true
        val email: String = mBinding.fieldEmail.getText().toString()
        if (android.text.TextUtils.isEmpty(email)) {
            mBinding.fieldEmail.setError("Required.")
            valid = false
        } else {
            mBinding.fieldEmail.setError(null)
        }
        val password: String = mBinding.fieldPassword.getText().toString()
        if (android.text.TextUtils.isEmpty(password)) {
            mBinding.fieldPassword.setError("Required.")
            valid = false
        } else {
            mBinding.fieldPassword.setError(null)
        }
        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressBar()
        val idView: android.widget.TextView = findViewById(R.id.anonymousStatusId)
        val emailView: android.widget.TextView = findViewById(R.id.anonymousStatusEmail)
        val isSignedIn = user != null

        // Status text
        if (isSignedIn) {
            idView.setText(getString(R.string.id_fmt, user.getUid()))
            emailView.setText(getString(R.string.email_fmt, user.getEmail()))
        } else {
            idView.setText(R.string.signed_out)
            emailView.setText(null)
        }

        // Button visibility
        findViewById(R.id.buttonAnonymousSignIn).setEnabled(!isSignedIn)
        findViewById(R.id.buttonAnonymousSignOut).setEnabled(isSignedIn)
        findViewById(R.id.buttonLinkAccount).setEnabled(isSignedIn)
    }

    override fun onClick(v: android.view.View) {
        val i: Int = v.getId()
        if (i == R.id.buttonAnonymousSignIn) {
            signInAnonymously()
        } else if (i == R.id.buttonAnonymousSignOut) {
            signOut()
        } else if (i == R.id.buttonLinkAccount) {
            linkAccount()
        }
    }

    companion object {
        private const val TAG = "AnonymousAuth"
    }
}