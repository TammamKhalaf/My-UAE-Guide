package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import androidx.appcompat.app.AppCompatActivity

class PhoneAuthActivity : AppCompatActivity(), android.view.View.OnClickListener {
    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null

    // [END declare_auth]
    private var mVerificationInProgress = false
    private var mVerificationId: String? = null
    private var mResendToken: ForceResendingToken? = null
    private var mCallbacks: OnVerificationStateChangedCallbacks? = null
    private var mBinding: ActivityPhoneAuthBinding? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mBinding = ActivityPhoneAuthBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }

        // Assign click listeners
        mBinding.buttonStartVerification.setOnClickListener(this)
        mBinding.buttonVerifyPhone.setOnClickListener(this)
        mBinding.buttonResend.setOnClickListener(this)
        mBinding.signOutButton.setOnClickListener(this)

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = object : OnVerificationStateChangedCallbacks() {
            fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                android.util.Log.d(TAG, "onVerificationCompleted:$credential")
                // [START_EXCLUDE silent]
                mVerificationInProgress = false
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential)
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential)
            }

            fun onVerificationFailed(e: FirebaseException?) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                android.util.Log.w(TAG, "onVerificationFailed", e)
                // [START_EXCLUDE silent]
                mVerificationInProgress = false
                // [END_EXCLUDE]
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    mBinding.fieldPhoneNumber.setError("Invalid phone number.")
                    // [END_EXCLUDE]
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show()
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED)
                // [END_EXCLUDE]
            }

            fun onCodeSent(@NonNull verificationId: String,
                           @NonNull token: ForceResendingToken?) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                android.util.Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT)
                // [END_EXCLUDE]
            }
        }
        // [END phone_auth_callbacks]
    }

    private fun initFCM() {
        val token: String = FirebaseInstanceId.getInstance().getToken()
        android.util.Log.d(TAG, "initFCM: token: $token")
        sendRegistrationTokenToServer(token)
    }

    private fun sendRegistrationTokenToServer(refreshedToken: String) {
        android.util.Log.d(TAG, "sendRegistrationTokenToServer: sending token to the server$refreshedToken")
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        reference.child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("messaging_token").setValue(refreshedToken)
    }

    // [START on_start_check_user]
    fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser = mAuth.getCurrentUser()
        updateUI(currentUser)

        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mBinding.fieldPhoneNumber.getText().toString())
        }
        // [END_EXCLUDE]
    }

    // [END on_start_check_user]
    protected fun onSaveInstanceState(outState: android.os.Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress)
    }

    protected fun onRestoreInstanceState(savedInstanceState: android.os.Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS)
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        val options: PhoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]
        mVerificationInProgress = true
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    // [START resend_verification]
    private fun resendVerificationCode(phoneNumber: String,
                                       token: ForceResendingToken?) {
        val options: PhoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .setForceResendingToken(token) // ForceResendingToken from callbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // [END resend_verification]
    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
                    fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(TAG, "signInWithCredential:success")
                            val user: FirebaseUser = task.getResult().getUser()
                            initFCM()
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user)
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            android.util.Log.w(TAG, "signInWithCredential:failure", task.getException())
                            if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                mBinding.fieldVerificationCode.setError("Invalid code.")
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED)
                            // [END_EXCLUDE]
                        }
                    }
                })
    }

    // [END sign_in_with_phone]
    private fun signOut() {
        mAuth.signOut()
        updateUI(STATE_INITIALIZED)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user)
        } else {
            updateUI(STATE_INITIALIZED)
        }
    }

    private fun updateUI(uiState: Int, user: FirebaseUser) {
        updateUI(uiState, user, null)
    }

    private fun updateUI(uiState: Int, cred: PhoneAuthCredential) {
        updateUI(uiState, null, cred)
    }

    private fun updateUI(uiState: Int, user: FirebaseUser? = mAuth.getCurrentUser(), cred: PhoneAuthCredential? = null) {
        when (uiState) {
            STATE_INITIALIZED -> {
                // Initialized state, show only the phone number field and start button
                enableViews(mBinding.buttonStartVerification, mBinding.fieldPhoneNumber)
                disableViews(mBinding.buttonVerifyPhone, mBinding.buttonResend, mBinding.fieldVerificationCode)
                mBinding.detail.setText(null)
            }
            STATE_CODE_SENT -> {
                // Code sent state, show the verification field, the
                enableViews(mBinding.buttonVerifyPhone, mBinding.buttonResend, mBinding.fieldPhoneNumber, mBinding.fieldVerificationCode)
                disableViews(mBinding.buttonStartVerification)
                mBinding.detail.setText(R.string.status_code_sent)
            }
            STATE_VERIFY_FAILED -> {
                // Verification has failed, show all options
                enableViews(mBinding.buttonStartVerification, mBinding.buttonVerifyPhone, mBinding.buttonResend, mBinding.fieldPhoneNumber,
                        mBinding.fieldVerificationCode)
                mBinding.detail.setText(R.string.status_verification_failed)
            }
            STATE_VERIFY_SUCCESS -> {
                // Verification has succeeded, proceed to firebase sign in
                disableViews(mBinding.buttonStartVerification, mBinding.buttonVerifyPhone, mBinding.buttonResend, mBinding.fieldPhoneNumber, mBinding.fieldVerificationCode)
                mBinding.detail.setText(R.string.status_verification_succeeded)

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mBinding.fieldVerificationCode.setText(cred.getSmsCode())
                    } else {
                        mBinding.fieldVerificationCode.setText(R.string.instant_validation)
                    }
                }
            }
            STATE_SIGNIN_FAILED ->                 // No-op, handled by sign-in check
                mBinding.detail.setText(R.string.status_sign_in_failed)
            STATE_SIGNIN_SUCCESS -> {
            }
        }
        if (user == null) {
            // Signed out
            mBinding.phoneAuthFields.setVisibility(android.view.View.VISIBLE)
            mBinding.signOutButton.setVisibility(android.view.View.GONE)
            mBinding.status.setText(R.string.signed_out)
        } else {
            // Signed in
            mBinding.phoneAuthFields.setVisibility(android.view.View.GONE)
            mBinding.signOutButton.setVisibility(android.view.View.VISIBLE)
            enableViews(mBinding.fieldPhoneNumber, mBinding.fieldVerificationCode)
            mBinding.fieldPhoneNumber.setText(null)
            mBinding.fieldVerificationCode.setText(null)
            mBinding.status.setText(R.string.signed_in)
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()))
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber: String = mBinding.fieldPhoneNumber.getText().toString()
        if (android.text.TextUtils.isEmpty(phoneNumber)) {
            mBinding.fieldPhoneNumber.setError("Invalid phone number.")
            return false
        }
        return true
    }

    private fun enableViews(vararg views: android.view.View) {
        for (v in views) {
            v.setEnabled(true)
        }
    }

    private fun disableViews(vararg views: android.view.View) {
        for (v in views) {
            v.setEnabled(false)
        }
    }

    fun onBackPressed() {
        val intent: android.content.Intent = android.content.Intent(this, UserDashboard::class.java)
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onClick(view: android.view.View) {
        when (view.getId()) {
            R.id.buttonStartVerification -> {
                if (!validatePhoneNumber()) {
                    return
                }
                startPhoneNumberVerification(mBinding.fieldPhoneNumber.getText().toString())
            }
            R.id.buttonVerifyPhone -> {
                val code: String = mBinding.fieldVerificationCode.getText().toString()
                if (android.text.TextUtils.isEmpty(code)) {
                    mBinding.fieldVerificationCode.setError("Cannot be empty.")
                    return
                }
                verifyPhoneNumberWithCode(mVerificationId, code)
            }
            R.id.buttonResend -> {
                if (!validatePhoneNumber()) {
                    return
                }
                resendVerificationCode(mBinding.fieldPhoneNumber.getText().toString(), mResendToken)
            }
            R.id.signOutButton -> signOut()
        }
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
        private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
        private const val STATE_INITIALIZED = 1
        private const val STATE_CODE_SENT = 2
        private const val STATE_VERIFY_FAILED = 3
        private const val STATE_VERIFY_SUCCESS = 4
        private const val STATE_SIGNIN_FAILED = 5
        private const val STATE_SIGNIN_SUCCESS = 6
    }
}