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

class MultiFactorActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    private var mBinding: ActivityMultiFactorBinding? = null

    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMultiFactorBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        setProgressBar(mBinding.progressBar)

        // Buttons
        mBinding.emailSignInButton.setOnClickListener(this)
        mBinding.signOutButton.setOnClickListener(this)
        mBinding.verifyEmailButton.setOnClickListener(this)
        mBinding.enrollMfa.setOnClickListener(this)
        mBinding.unenrollMfa.setOnClickListener(this)
        mBinding.reloadButton.setOnClickListener(this)

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]
        showDisclaimer()
    }

    // [START on_start_check_user]
    fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser = mAuth.getCurrentUser()
        updateUI(currentUser)
    }

    // [END on_start_check_user]
    protected fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: android.content.Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_MULTI_FACTOR) {
            if (resultCode == RESULT_NEEDS_MFA_SIGN_IN) {
                val intent: android.content.Intent = android.content.Intent(this, com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.MultiFactorSignInActivity::class.java)
                intent.putExtras(data.getExtras())
                startActivityForResult(intent, RC_MULTI_FACTOR)
            }
        }
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
                            android.widget.Toast.makeText(this@MultiFactorActivity,
                                    "Verification email sent to " + user.getEmail(),
                                    android.widget.Toast.LENGTH_SHORT).show()
                        } else {
                            android.util.Log.e(TAG, "sendEmailVerification", task.getException())
                            android.widget.Toast.makeText(this@MultiFactorActivity,
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
                    android.widget.Toast.makeText(this@MultiFactorActivity,
                            "Reload successful!",
                            android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    android.util.Log.e(TAG, "reload", task.getException())
                    android.widget.Toast.makeText(this@MultiFactorActivity,
                            "Failed to reload user.",
                            android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressBar()
        if (user != null) {
            mBinding.status.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()))
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()))
            val secondFactors: List<MultiFactorInfo> = user.getMultiFactor().getEnrolledFactors()
            if (secondFactors.isEmpty()) {
                mBinding.unenrollMfa.setVisibility(android.view.View.GONE)
            } else {
                mBinding.unenrollMfa.setVisibility(android.view.View.VISIBLE)
                val sb: java.lang.StringBuilder = java.lang.StringBuilder("Second Factors: ")
                val delimiter = ", "
                for (x in secondFactors) {
                    sb.append((x as PhoneMultiFactorInfo).getPhoneNumber().toString() + delimiter)
                }
                sb.setLength(sb.length - delimiter.length)
                mBinding.mfaInfo.setText(sb.toString())
            }
            mBinding.emailSignInButton.setVisibility(android.view.View.GONE)
            mBinding.signedInButtons.setVisibility(android.view.View.VISIBLE)
            val reloadVisibility: Int = if (secondFactors.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            mBinding.reloadButton.setVisibility(reloadVisibility)
            if (user.isEmailVerified()) {
                mBinding.verifyEmailButton.setVisibility(android.view.View.GONE)
                mBinding.enrollMfa.setVisibility(android.view.View.VISIBLE)
            } else {
                mBinding.verifyEmailButton.setVisibility(android.view.View.VISIBLE)
                mBinding.enrollMfa.setVisibility(android.view.View.GONE)
            }
        } else {
            mBinding.status.setText(R.string.multi_factor_signed_out)
            mBinding.detail.setText(null)
            mBinding.mfaInfo.setText(null)
            mBinding.emailSignInButton.setVisibility(android.view.View.VISIBLE)
            mBinding.signedInButtons.setVisibility(android.view.View.GONE)
        }
    }

    private fun showDisclaimer() {
        android.app.AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Multi-factor authentication with SMS is currently only available for " +
                        "Google Cloud Identity Platform projects. For more information see: " +
                        "https://cloud.google.com/identity-platform/docs/android/mfa")
                .setPositiveButton("OK", null)
                .show()
    }

    override fun onClick(v: android.view.View) {
        val i: Int = v.getId()
        if (i == R.id.emailSignInButton) {
            startActivityForResult(android.content.Intent(this, com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.EmailPasswordActivity::class.java), RC_MULTI_FACTOR)
        } else if (i == R.id.signOutButton) {
            signOut()
        } else if (i == R.id.verifyEmailButton) {
            sendEmailVerification()
        } else if (i == R.id.enrollMfa) {
            startActivity(android.content.Intent(this, com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.MultiFactorEnrollActivity::class.java))
        } else if (i == R.id.unenrollMfa) {
            startActivity(android.content.Intent(this, com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.MultiFactorUnenrollActivity::class.java))
        } else if (i == R.id.reloadButton) {
            reload()
        }
    }

    companion object {
        const val RESULT_NEEDS_MFA_SIGN_IN = 42
        private const val TAG = "MultiFactor"
        private const val RC_MULTI_FACTOR = 9005
    }
}