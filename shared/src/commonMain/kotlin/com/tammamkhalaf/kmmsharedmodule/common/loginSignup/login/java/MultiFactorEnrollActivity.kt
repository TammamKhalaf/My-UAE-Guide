package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import com.google.android.gms.tasks.OnCompleteListener

/**
 * Activity that allows the user to enroll second factors.
 */
class MultiFactorEnrollActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    private var mBinding: ActivityPhoneAuthBinding? = null
    private var mCodeVerificationId: String? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPhoneAuthBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        mBinding.titleText.setText("SMS as a Second Factor")
        mBinding.status.setVisibility(android.view.View.GONE)
        mBinding.detail.setVisibility(android.view.View.GONE)
        mBinding.buttonStartVerification.setOnClickListener(this)
        mBinding.buttonVerifyPhone.setOnClickListener(this)
    }

    private fun onClickVerifyPhoneNumber() {
        val phoneNumber: String = mBinding.fieldPhoneNumber.getText().toString()
        val callbacks: OnVerificationStateChangedCallbacks = object : OnVerificationStateChangedCallbacks() {
            fun onVerificationCompleted(credential: PhoneAuthCredential?) {
                // Instant-validation has been disabled (see requireSmsValidation below).
                // Auto-retrieval has also been disabled (timeout is set to 0).
                // This should never be triggered.
                throw java.lang.RuntimeException(
                        "onVerificationCompleted() triggered with instant-validation and auto-retrieval disabled.")
            }

            fun onCodeSent(
                    verificationId: String, token: ForceResendingToken?) {
                android.util.Log.d(TAG, "onCodeSent:$verificationId")
                android.widget.Toast.makeText(
                        this@MultiFactorEnrollActivity, "SMS code has been sent", android.widget.Toast.LENGTH_SHORT)
                        .show()
                mCodeVerificationId = verificationId
            }

            fun onVerificationFailed(e: FirebaseException) {
                android.util.Log.w(TAG, "onVerificationFailed ", e)
                android.widget.Toast.makeText(
                        this@MultiFactorEnrollActivity, "Verification failed: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT)
                        .show()
            }
        }
        FirebaseAuth.getInstance()
                .getCurrentUser()
                .getMultiFactor()
                .getSession()
                .addOnCompleteListener(
                        object : OnCompleteListener<MultiFactorSession?>() {
                            fun onComplete(@NonNull task: Task<MultiFactorSession?>) {
                                if (task.isSuccessful()) {
                                    val phoneAuthOptions: PhoneAuthOptions = PhoneAuthOptions.newBuilder()
                                            .setPhoneNumber(phoneNumber) // A timeout of 0 disables SMS-auto-retrieval.
                                            .setTimeout(0L, java.util.concurrent.TimeUnit.SECONDS)
                                            .setMultiFactorSession(task.getResult())
                                            .setCallbacks(callbacks) // Disable instant-validation.
                                            .requireSmsValidation(true)
                                            .build()
                                    PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
                                } else {
                                    android.widget.Toast.makeText(
                                            this@MultiFactorEnrollActivity,
                                            "Failed to get session: " + task.getException(), android.widget.Toast.LENGTH_SHORT)
                                            .show()
                                }
                            }
                        })
    }

    private fun onClickSignInWithPhoneNumber() {
        val smsCode: String = mBinding.fieldVerificationCode.getText().toString()
        if (android.text.TextUtils.isEmpty(smsCode)) {
            return
        }
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(mCodeVerificationId, smsCode)
        enrollWithPhoneAuthCredential(credential)
    }

    private fun enrollWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance()
                .getCurrentUser()
                .getMultiFactor()
                .enroll(PhoneMultiFactorGenerator.getAssertion(credential),  /* displayName= */null)
                .addOnSuccessListener(object : OnSuccessListener<java.lang.Void?>() {
                    fun onSuccess(aVoid: java.lang.Void?) {
                        android.widget.Toast.makeText(
                                this@MultiFactorEnrollActivity,
                                "MFA enrollment was successful",
                                android.widget.Toast.LENGTH_LONG)
                                .show()
                        finish()
                    }
                })
                .addOnFailureListener(object : OnFailureListener() {
                    fun onFailure(@NonNull e: java.lang.Exception) {
                        android.util.Log.d(TAG, "MFA failure", e)
                        android.widget.Toast.makeText(
                                this@MultiFactorEnrollActivity,
                                "MFA enrollment was unsuccessful. $e",
                                android.widget.Toast.LENGTH_LONG)
                                .show()
                    }
                })
    }

    override fun onClick(v: android.view.View) {
        when (v.getId()) {
            R.id.buttonStartVerification -> onClickVerifyPhoneNumber()
            R.id.buttonVerifyPhone -> onClickSignInWithPhoneNumber()
        }
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}