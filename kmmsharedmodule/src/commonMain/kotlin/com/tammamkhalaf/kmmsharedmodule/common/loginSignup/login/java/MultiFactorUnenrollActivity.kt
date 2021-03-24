package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import com.google.android.gms.tasks.OnCompleteListener

class MultiFactorUnenrollActivity : com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.BaseActivity() {
    private var mBinding: ActivityMultiFactorSignInBinding? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMultiFactorSignInBinding.inflate(getLayoutInflater())
        setContentView(mBinding.getRoot())
        mBinding.smsCode.setVisibility(android.view.View.GONE)
        mBinding.finishMfaSignIn.setVisibility(android.view.View.GONE)
        val phoneFactorButtonList: MutableList<android.widget.Button> = java.util.ArrayList<android.widget.Button>()
        phoneFactorButtonList.add(mBinding.phoneFactor1)
        phoneFactorButtonList.add(mBinding.phoneFactor2)
        phoneFactorButtonList.add(mBinding.phoneFactor3)
        phoneFactorButtonList.add(mBinding.phoneFactor4)
        phoneFactorButtonList.add(mBinding.phoneFactor5)
        for (button in phoneFactorButtonList) {
            button.setVisibility(android.view.View.GONE)
        }
        val multiFactorInfoList: List<MultiFactorInfo> = FirebaseAuth.getInstance().getCurrentUser().getMultiFactor().getEnrolledFactors()
        for (i in multiFactorInfoList.indices) {
            val phoneMultiFactorInfo: PhoneMultiFactorInfo = multiFactorInfoList[i] as PhoneMultiFactorInfo
            val button: android.widget.Button = phoneFactorButtonList[i]
            button.setVisibility(android.view.View.VISIBLE)
            button.setText(phoneMultiFactorInfo.getPhoneNumber())
            button.setClickable(true)
            button.setOnClickListener(generateFactorOnClickListener(phoneMultiFactorInfo))
        }
    }

    private fun generateFactorOnClickListener(phoneMultiFactorInfo: PhoneMultiFactorInfo): android.view.View.OnClickListener {
        return object : android.view.View.OnClickListener() {
            override fun onClick(v: android.view.View) {
                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getMultiFactor()
                        .unenroll(phoneMultiFactorInfo)
                        .addOnCompleteListener(
                                object : OnCompleteListener<java.lang.Void?>() {
                                    fun onComplete(@NonNull task: Task<java.lang.Void?>) {
                                        if (task.isSuccessful()) {
                                            android.widget.Toast.makeText(this@MultiFactorUnenrollActivity,
                                                    "Successfully unenrolled!", android.widget.Toast.LENGTH_SHORT).show()
                                            finish()
                                        } else {
                                            android.widget.Toast.makeText(this@MultiFactorUnenrollActivity,
                                                    "Unable to unenroll second factor. " + task.getException(), android.widget.Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                })
            }
        }
    }
}