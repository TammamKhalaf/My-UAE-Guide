package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import androidx.appcompat.app.AppCompatActivity

/**
 * Demonstrate authentication using the FirebaseUI-Android library. This activity demonstrates
 * using FirebaseUI for basic email/password sign in.
 *
 * For more information, visit https://github.com/firebase/firebaseui-android
 */
class FirebaseUIActivity : AppCompatActivity(), android.view.View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private var mBinding: ActivityFirebaseUiBinding? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFirebaseUiBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        mBinding.signInButton.setOnClickListener(this)
        mBinding.signOutButton.setOnClickListener(this)
    }

    protected fun onStart() {
        super.onStart()
        updateUI(mAuth.getCurrentUser())
    }

    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign in succeeded
                updateUI(mAuth.getCurrentUser())
            } else {
                // Sign in failed
                android.widget.Toast.makeText(this, "Sign In Failed", android.widget.Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun startSignIn() {
        // Build FirebaseUI sign in intent. For documentation on this operation and all
        // possible customization see: https://github.com/firebase/firebaseui-android
        val intent: android.content.Intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(listOf(
                        EmailBuilder().build()))
                .setLogo(R.mipmap.ic_launcher)
                .build()
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Signed in
            mBinding.status.setText(getString(R.string.firebaseui_status_fmt, user.getEmail()))
            mBinding.detail.setText(getString(R.string.id_fmt, user.getUid()))
            mBinding.signInButton.setVisibility(android.view.View.GONE)
            mBinding.signOutButton.setVisibility(android.view.View.VISIBLE)
        } else {
            // Signed out
            mBinding.status.setText(R.string.signed_out)
            mBinding.detail.setText(null)
            mBinding.signInButton.setVisibility(android.view.View.VISIBLE)
            mBinding.signOutButton.setVisibility(android.view.View.GONE)
        }
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this)
        updateUI(null)
    }

    override fun onClick(view: android.view.View) {
        when (view.getId()) {
            R.id.signInButton -> startSignIn()
            R.id.signOutButton -> signOut()
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}