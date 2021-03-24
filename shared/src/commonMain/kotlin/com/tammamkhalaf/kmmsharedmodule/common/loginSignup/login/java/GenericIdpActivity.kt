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

import com.google.android.gms.tasks.OnFailureListener

/**
 * Demonstrate Firebase Authentication using a Generic Identity Provider (IDP).
 */
class GenericIdpActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    private var mBinding: ActivityGenericIdpBinding? = null
    private var mSpinnerAdapter: android.widget.ArrayAdapter<String>? = null

    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGenericIdpBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())

        // Views

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Set up button click listeners
        mBinding.genericSignInButton.setOnClickListener(this)
        mBinding.signOutButton.setOnClickListener(this)

        // Spinner
        val providers: List<String> = java.util.ArrayList<String>(PROVIDER_MAP.keys)
        mSpinnerAdapter = android.widget.ArrayAdapter(this, R.layout.item_spinner_list, providers)
        mBinding.providerSpinner.setAdapter(mSpinnerAdapter)
        mBinding.providerSpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener() {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View, position: Int, id: Long) {
                mBinding.genericSignInButton.setText(getString(R.string.generic_signin_fmt, mSpinnerAdapter.getItem(position)))
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        })
        mBinding.providerSpinner.setSelection(0)
    }

    fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser = mAuth.getCurrentUser()
        updateUI(currentUser)

        // Look for a pending auth result
        val pending: Task<AuthResult> = mAuth.getPendingAuthResult()
        if (pending != null) {
            pending.addOnSuccessListener(object : OnSuccessListener<AuthResult?>() {
                fun onSuccess(authResult: AuthResult) {
                    android.util.Log.d(TAG, "checkPending:onSuccess:$authResult")
                    updateUI(authResult.getUser())
                }
            }).addOnFailureListener(object : OnFailureListener() {
                fun onFailure(@NonNull e: java.lang.Exception?) {
                    android.util.Log.w(TAG, "checkPending:onFailure", e)
                }
            })
        } else {
            android.util.Log.d(TAG, "checkPending: null")
        }
    }

    private fun signIn() {
        // Could add custom scopes here
        val scopes: java.util.ArrayList<String> = java.util.ArrayList<String>()

        // Examples of provider ID: apple.com (Apple), microsoft.com (Microsoft), yahoo.com (Yahoo)
        val providerId = providerId
        mAuth.startActivityForSignInWithProvider(this,
                OAuthProvider.newBuilder(providerId, mAuth)
                        .setScopes(scopes)
                        .build())
                .addOnSuccessListener(
                        object : OnSuccessListener<AuthResult?>() {
                            fun onSuccess(authResult: AuthResult) {
                                android.util.Log.d(TAG, "activitySignIn:onSuccess:" + authResult.getUser())
                                updateUI(authResult.getUser())
                            }
                        })
                .addOnFailureListener(
                        object : OnFailureListener() {
                            fun onFailure(@NonNull e: java.lang.Exception?) {
                                android.util.Log.w(TAG, "activitySignIn:onFailure", e)
                                showToast(getString(R.string.error_sign_in_failed))
                            }
                        })
    }

    private val providerId: String?
        private get() {
            val providerName: String = mSpinnerAdapter.getItem(mBinding.providerSpinner.getSelectedItemPosition())
            return PROVIDER_MAP[providerName]
        }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressBar()
        if (user != null) {
            mBinding.status.setText(getString(R.string.generic_status_fmt, user.getDisplayName(), user.getEmail()))
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()))
            mBinding.spinnerLayout.setVisibility(android.view.View.GONE)
            mBinding.genericSignInButton.setVisibility(android.view.View.GONE)
            mBinding.signOutButton.setVisibility(android.view.View.VISIBLE)
        } else {
            mBinding.status.setText(R.string.signed_out)
            mBinding.detail.setText(null)
            mBinding.spinnerLayout.setVisibility(android.view.View.VISIBLE)
            mBinding.genericSignInButton.setVisibility(android.view.View.VISIBLE)
            mBinding.signOutButton.setVisibility(android.view.View.GONE)
        }
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: android.view.View) {
        when (v.getId()) {
            R.id.genericSignInButton -> signIn()
            R.id.signOutButton -> {
                mAuth.signOut()
                updateUI(null)
            }
        }
    }

    companion object {
        private const val TAG = "GenericIdp"
        private val PROVIDER_MAP: Map<String, String> = object : java.util.HashMap<String?, String?>() {
            init {
                put("Apple", "apple.com")
                put("Microsoft", "microsoft.com")
                put("Yahoo", "yahoo.com")
                put("Twitter", "twitter.com")
            }
        }
    }
}