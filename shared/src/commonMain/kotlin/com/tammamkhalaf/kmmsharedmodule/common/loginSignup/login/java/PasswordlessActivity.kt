package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import com.google.android.gms.tasks.OnCompleteListener

/**
 * Demonstrate Firebase Authentication without a password, using a link sent to an
 * email address.
 */
class PasswordlessActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private var mBinding: ActivityPasswordlessBinding? = null
    private var mPendingEmail: String? = null
    private var mEmailLink: String? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPasswordlessBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        setProgressBar(mBinding.progressBar)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        mBinding.passwordlessSendEmailButton.setOnClickListener(this)
        mBinding.passwordlessSignInButton.setOnClickListener(this)
        mBinding.signOutButton.setOnClickListener(this)

        // Restore the "pending" email address
        if (savedInstanceState != null) {
            mPendingEmail = savedInstanceState.getString(KEY_PENDING_EMAIL, null)
            mBinding.fieldEmail.setText(mPendingEmail)
        }

        // Check if the Intent that started the Activity contains an email sign-in link.
        checkIntent(getIntent())
    }

    protected fun onStart() {
        super.onStart()
        updateUI(mAuth.getCurrentUser())
    }

    protected fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        checkIntent(intent)
    }

    protected fun onSaveInstanceState(outState: android.os.Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_PENDING_EMAIL, mPendingEmail)
    }

    /**
     * Check to see if the Intent has an email link, and if so set up the UI accordingly.
     * This can be called from either onCreate or onNewIntent, depending on how the Activity
     * was launched.
     */
    private fun checkIntent(@Nullable intent: android.content.Intent) {
        if (intentHasEmailLink(intent)) {
            mEmailLink = intent.getData().toString()
            mBinding.status.setText(R.string.status_link_found)
            mBinding.passwordlessSendEmailButton.setEnabled(false)
            mBinding.passwordlessSignInButton.setEnabled(true)
        } else {
            mBinding.status.setText(R.string.status_email_not_sent)
            mBinding.passwordlessSendEmailButton.setEnabled(true)
            mBinding.passwordlessSignInButton.setEnabled(false)
        }
    }

    /**
     * Determine if the given Intent contains an email sign-in link.
     */
    private fun intentHasEmailLink(@Nullable intent: android.content.Intent?): Boolean {
        if (intent != null && intent.getData() != null) {
            val intentData: String = intent.getData().toString()
            return mAuth.isSignInWithEmailLink(intentData)
        }
        return false
    }

    /**
     * Send an email sign-in link to the specified email.
     */
    private fun sendSignInLink(email: String) {
        val settings: ActionCodeSettings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName(
                        getPackageName(),
                        false,  /* install if not available? */
                        null /* minimum app version */)
                .setHandleCodeInApp(true)
                .setUrl("https://auth.example.com/emailSignInLink")
                .build()
        hideKeyboard(mBinding.fieldEmail)
        showProgressBar()
        mAuth.sendSignInLinkToEmail(email, settings)
                .addOnCompleteListener(object : OnCompleteListener<java.lang.Void?>() {
                    fun onComplete(@NonNull task: Task<java.lang.Void?>) {
                        hideProgressBar()
                        if (task.isSuccessful()) {
                            android.util.Log.d(TAG, "Link sent")
                            showSnackbar("Sign-in link sent!")
                            mPendingEmail = email
                            mBinding.status.setText(R.string.status_email_sent)
                        } else {
                            val e: java.lang.Exception = task.getException()
                            android.util.Log.w(TAG, "Could not send link", e)
                            showSnackbar("Failed to send link.")
                            if (e is FirebaseAuthInvalidCredentialsException) {
                                mBinding.fieldEmail.setError("Invalid email address.")
                            }
                        }
                    }
                })
    }

    /**
     * Sign in using an email address and a link, the link is passed to the Activity
     * from the dynamic link contained in the email.
     */
    private fun signInWithEmailLink(email: String, link: String?) {
        android.util.Log.d(TAG, "signInWithLink:$link")
        hideKeyboard(mBinding.fieldEmail)
        showProgressBar()
        mAuth.signInWithEmailLink(email, link)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        hideProgressBar()
                        mPendingEmail = null
                        if (task.isSuccessful()) {
                            android.util.Log.d(TAG, "signInWithEmailLink:success")
                            mBinding.fieldEmail.setText(null)
                            updateUI(task.getResult().getUser())
                        } else {
                            android.util.Log.w(TAG, "signInWithEmailLink:failure", task.getException())
                            updateUI(null)
                            if (task.getException() is FirebaseAuthActionCodeException) {
                                showSnackbar("Invalid or expired sign-in link.")
                            }
                        }
                    }
                })
    }

    private fun onSendLinkClicked() {
        val email: String = mBinding.fieldEmail.getText().toString()
        if (android.text.TextUtils.isEmpty(email)) {
            mBinding.fieldEmail.setError("Email must not be empty.")
            return
        }
        sendSignInLink(email)
    }

    private fun onSignInClicked() {
        val email: String = mBinding.fieldEmail.getText().toString()
        if (android.text.TextUtils.isEmpty(email)) {
            mBinding.fieldEmail.setError("Email must not be empty.")
            return
        }
        signInWithEmailLink(email, mEmailLink)
    }

    private fun onSignOutClicked() {
        mAuth.signOut()
        updateUI(null)
        mBinding.status.setText(R.string.status_email_not_sent)
    }

    private fun updateUI(@Nullable user: FirebaseUser?) {
        if (user != null) {
            mBinding.status.setText(getString(R.string.passwordless_status_fmt,
                    user.getEmail(), user.isEmailVerified()))
            mBinding.fieldEmail.setVisibility(android.view.View.GONE)
            mBinding.passwordlessButtons.setVisibility(android.view.View.GONE)
            mBinding.signOutButton.setVisibility(android.view.View.VISIBLE)
        } else {
            mBinding.fieldEmail.setVisibility(android.view.View.VISIBLE)
            mBinding.passwordlessButtons.setVisibility(android.view.View.VISIBLE)
            mBinding.signOutButton.setVisibility(android.view.View.GONE)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: android.view.View) {
        when (view.getId()) {
            R.id.passwordlessSendEmailButton -> onSendLinkClicked()
            R.id.passwordlessSignInButton -> onSignInClicked()
            R.id.signOutButton -> onSignOutClicked()
        }
    }

    companion object {
        private const val TAG = "PasswordlessSignIn"
        private const val KEY_PENDING_EMAIL = "key_pending_email"
    }
}