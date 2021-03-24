package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import com.google.android.gms.tasks.OnFailureListener

/**
 * Activity that handles MFA sign-in
 */
class MultiFactorSignInActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity(), android.view.View.OnClickListener {
    private var mBinding: ActivityMultiFactorSignInBinding? = null
    private var mMultiFactorResolver: MultiFactorResolver? = null
    private var mPhoneAuthCredential: PhoneAuthCredential? = null
    private var mVerificationId: String? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMultiFactorSignInBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
        val phoneFactorButtonList: MutableList<android.widget.Button> = java.util.ArrayList<android.widget.Button>()
        phoneFactorButtonList.add(mBinding.phoneFactor1)
        phoneFactorButtonList.add(mBinding.phoneFactor2)
        phoneFactorButtonList.add(mBinding.phoneFactor3)
        phoneFactorButtonList.add(mBinding.phoneFactor4)
        phoneFactorButtonList.add(mBinding.phoneFactor5)
        for (button in phoneFactorButtonList) {
            button.setVisibility(android.view.View.GONE)
        }
        mBinding.finishMfaSignIn.setOnClickListener(this)
        mMultiFactorResolver = retrieveResolverFromIntent(getIntent())
        val multiFactorInfoList: List<MultiFactorInfo> = mMultiFactorResolver.getHints()
        for (i in multiFactorInfoList.indices) {
            val phoneMultiFactorInfo: PhoneMultiFactorInfo = multiFactorInfoList[i] as PhoneMultiFactorInfo
            val button: android.widget.Button = phoneFactorButtonList[i]
            button.setVisibility(android.view.View.VISIBLE)
            button.setText(phoneMultiFactorInfo.getPhoneNumber())
            button.setClickable(true)
            button.setOnClickListener(generateFactorOnClickListener(phoneMultiFactorInfo))
        }
    }

    fun onSaveInstanceState(bundle: android.os.Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putString(KEY_VERIFICATION_ID, mVerificationId)
    }

    protected fun onRestoreInstanceState(savedInstanceState: android.os.Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mVerificationId = savedInstanceState.getString(KEY_VERIFICATION_ID)
    }

    private fun generateFactorOnClickListener(phoneMultiFactorInfo: PhoneMultiFactorInfo): android.view.View.OnClickListener {
        return object : android.view.View.OnClickListener() {
            override fun onClick(v: android.view.View) {
                PhoneAuthProvider.verifyPhoneNumber(
                        PhoneAuthOptions.newBuilder()
                                .setActivity(this@MultiFactorSignInActivity)
                                .setMultiFactorSession(mMultiFactorResolver.getSession())
                                .setMultiFactorHint(phoneMultiFactorInfo)
                                .setCallbacks(generateCallbacks()) // A timeout of 0 disables SMS-auto-retrieval.
                                .setTimeout(0L, java.util.concurrent.TimeUnit.SECONDS)
                                .build())
            }
        }
    }

    private fun generateCallbacks(): OnVerificationStateChangedCallbacks {
        return object : OnVerificationStateChangedCallbacks() {
            fun onVerificationCompleted(@NonNull phoneAuthCredential: PhoneAuthCredential?) {
                mPhoneAuthCredential = phoneAuthCredential
                mBinding.finishMfaSignIn.performClick()
                android.widget.Toast.makeText(
                        this@MultiFactorSignInActivity, "Verification complete!", android.widget.Toast.LENGTH_SHORT)
                        .show()
            }

            fun onCodeSent(@NonNull verificationId: String?, @NonNull token: ForceResendingToken?) {
                mVerificationId = verificationId
                mBinding.finishMfaSignIn.setClickable(true)
            }

            fun onVerificationFailed(@NonNull e: FirebaseException) {
                android.widget.Toast.makeText(
                        this@MultiFactorSignInActivity, "Error: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    private fun retrieveResolverFromIntent(intent: android.content.Intent): MultiFactorResolver {
        return intent.getParcelableExtra(EXTRA_MFA_RESOLVER)
    }

    private fun onClickFinishSignIn() {
        if (mPhoneAuthCredential == null) {
            if (android.text.TextUtils.isEmpty(mBinding.smsCode.getText().toString())) {
                android.widget.Toast.makeText(
                        this@MultiFactorSignInActivity, "You need to enter an SMS code.", android.widget.Toast.LENGTH_SHORT)
                        .show()
                return
            }
            mPhoneAuthCredential = PhoneAuthProvider.getCredential(
                    mVerificationId, mBinding.smsCode.getText().toString())
        }
        mMultiFactorResolver
                .resolveSignIn(PhoneMultiFactorGenerator.getAssertion(mPhoneAuthCredential))
                .addOnSuccessListener(
                        object : OnSuccessListener<AuthResult?>() {
                            fun onSuccess(authResult: AuthResult?) {
                                setResult(android.app.Activity.RESULT_OK)
                                finish()
                            }
                        })
                .addOnFailureListener(
                        object : OnFailureListener() {
                            fun onFailure(@NonNull e: java.lang.Exception) {
                                android.widget.Toast.makeText(
                                        this@MultiFactorSignInActivity, "Error: " + e.message, android.widget.Toast.LENGTH_SHORT)
                                        .show()
                            }
                        })
    }

    override fun onClick(v: android.view.View) {
        if (v.getId() == R.id.finishMfaSignIn) {
            onClickFinishSignIn()
        }
    }

    companion object {
        private const val KEY_VERIFICATION_ID = "key_verification_id"
        const val EXTRA_MFA_RESOLVER = "EXTRA_MFA_RESOLVER"
    }
}