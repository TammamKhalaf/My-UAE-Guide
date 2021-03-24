/**
 * Copyright 2016 Google Inc. All Rights Reserved.
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

import com.google.android.gms.tasks.OnCompleteListener

class EmailPasswordActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    private var mBinding: ActivityEmailpasswordBinding? = null

    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityEmailpasswordBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        setProgressBar(mBinding.progressBar)

        // Buttons
        mBinding.emailSignInButton.setOnClickListener(this)
        mBinding.emailCreateAccountButton.setOnClickListener(this)
        mBinding.signOutButton.setOnClickListener(this)
        mBinding.verifyEmailButton.setOnClickListener(this)
        mBinding.reloadButton.setOnClickListener(this)

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
        if (currentUser != null) {
            reload()
        }
    }

    // [END on_start_check_user]
    private fun createAccount(email: String, password: String) {
        android.util.Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        showProgressBar()

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(TAG, "createUserWithEmail:success")
                            val user: FirebaseUser = mAuth.getCurrentUser()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "createUserWithEmail:failure", task.getException())
                            android.widget.Toast.makeText(this@EmailPasswordActivity, "Authentication failed.",
                                    android.widget.Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }

                        // [START_EXCLUDE]
                        hideProgressBar()
                        // [END_EXCLUDE]
                    }
                })
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        android.util.Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        showProgressBar()

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(TAG, "signInWithEmail:success")
                            val user: FirebaseUser = mAuth.getCurrentUser()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "signInWithEmail:failure", task.getException())
                            android.widget.Toast.makeText(this@EmailPasswordActivity, "Authentication failed.",
                                    android.widget.Toast.LENGTH_SHORT).show()
                            updateUI(null)
                            // [START_EXCLUDE]
                            checkForMultiFactorFailure(task.getException())
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mBinding.status.setText(R.string.auth_failed)
                        }
                        hideProgressBar()
                        // [END_EXCLUDE]
                    }
                })
        // [END sign_in_with_email]
    }

    private fun signOut() {
        mAuth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        // Disable button
        mBinding.verifyEmailButton.setEnabled(false)

        // Send verification email
        // [START send_email_verification]
        val user: FirebaseUser = mAuth.getCurrentUser()
        user.sendEmailVerification()
                .addOnCompleteListener(this, object : OnCompleteListener<java.lang.Void?>() {
                    fun onComplete(@NonNull task: Task<java.lang.Void?>) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        mBinding.verifyEmailButton.setEnabled(true)
                        if (task.isSuccessful()) {
                            android.widget.Toast.makeText(this@EmailPasswordActivity,
                                    "Verification email sent to " + user.getEmail(),
                                    android.widget.Toast.LENGTH_SHORT).show()
                        } else {
                            android.util.Log.e(TAG, "sendEmailVerification", task.getException())
                            android.widget.Toast.makeText(this@EmailPasswordActivity,
                                    "Failed to send verification email.",
                                    android.widget.Toast.LENGTH_SHORT).show()
                        }
                        // [END_EXCLUDE]
                    }
                })
        // [END send_email_verification]
    }

    private fun reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(object : OnCompleteListener<java.lang.Void?>() {
            fun onComplete(@NonNull task: Task<java.lang.Void?>) {
                if (task.isSuccessful()) {
                    updateUI(mAuth.getCurrentUser())
                    android.widget.Toast.makeText(this@EmailPasswordActivity,
                            "Reload successful!",
                            android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    android.util.Log.e(TAG, "reload", task.getException())
                    android.widget.Toast.makeText(this@EmailPasswordActivity,
                            "Failed to reload user.",
                            android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateForm(): Boolean {
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
        if (user != null) {
            mBinding.status.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()))
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()))
            mBinding.emailPasswordButtons.setVisibility(android.view.View.GONE)
            mBinding.emailPasswordFields.setVisibility(android.view.View.GONE)
            mBinding.signedInButtons.setVisibility(android.view.View.VISIBLE)
            if (user.isEmailVerified()) {
                mBinding.verifyEmailButton.setVisibility(android.view.View.GONE)
            } else {
                mBinding.verifyEmailButton.setVisibility(android.view.View.VISIBLE)
            }
        } else {
            mBinding.status.setText(R.string.signed_out)
            mBinding.detail.setText(null)
            mBinding.emailPasswordButtons.setVisibility(android.view.View.VISIBLE)
            mBinding.emailPasswordFields.setVisibility(android.view.View.VISIBLE)
            mBinding.signedInButtons.setVisibility(android.view.View.GONE)
        }
    }

    private fun checkForMultiFactorFailure(e: java.lang.Exception) {
        // Multi-factor authentication with SMS is currently only available for
        // Google Cloud Identity Platform projects. For more information:
        // https://cloud.google.com/identity-platform/docs/android/mfa
        if (e is FirebaseAuthMultiFactorException) {
            android.util.Log.w(TAG, "multiFactorFailure", e)
            val intent: android.content.Intent = android.content.Intent()
            val resolver: MultiFactorResolver = (e as FirebaseAuthMultiFactorException).getResolver()
            intent.putExtra("EXTRA_MFA_RESOLVER", resolver)
            setResult(com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.MultiFactorActivity.Companion.RESULT_NEEDS_MFA_SIGN_IN, intent)
            finish()
        }
    }

    override fun onClick(v: android.view.View) {
        val i: Int = v.getId()
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString())
        } else if (i == R.id.emailSignInButton) {
            signIn(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString())
        } else if (i == R.id.signOutButton) {
            signOut()
        } else if (i == R.id.verifyEmailButton) {
            sendEmailVerification()
        } else if (i == R.id.reloadButton) {
            reload()
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}