package com.tammamkhalaf.myuaeguide.common.loginSignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.FirebaseDatabase
import com.tammamkhalaf.myuaeguide.common.loginSignup.forgetPassword.SetNewPassword
import com.tammamkhalaf.myuaeguide.databases.UserHelperClass
import com.tammamkhalaf.myuaeguide.R
import java.util.*
import java.util.concurrent.TimeUnit

class VerifyOTP : AppCompatActivity() {
    var pinFromUser: PinView? = null
    var codeBySystem: String? = null
    private var mAuth: FirebaseAuth? = null
    var fullName: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var gender: String? = null
    var date: String? = null
    var phoneNo: String? = null
    var whatTodo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_verify_otp)
        pinFromUser = findViewById(R.id.pin_view)
        mAuth = FirebaseAuth.getInstance()
        val intentPrevious = intent
        fullName = intentPrevious.getStringExtra("fullName")
        username = intentPrevious.getStringExtra("username")
        email = intentPrevious.getStringExtra("email")
        password = intentPrevious.getStringExtra("password")
        gender = intentPrevious.getStringExtra("gender")
        date = intentPrevious.getStringExtra("age")
        phoneNo = intentPrevious.getStringExtra("phoneNo")
        whatTodo = intentPrevious.getStringExtra("whatToDo")
        Log.i(TAG, "onCreate: phone Number$phoneNo")
        sendVerificationCodeToUser(phoneNo)
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(codeBySystem!!, code)
        Toast.makeText(this, "verifyCode", Toast.LENGTH_SHORT).show()
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        if (whatTodo != null && whatTodo == "updateData") {
                            updateOldUsersData()
                        } else storeNewUsersData()
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(this@VerifyOTP, "The verification code entered was invalid", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
    }

    private fun updateOldUsersData() {
        val intent = Intent(applicationContext, SetNewPassword::class.java)
        intent.putExtra("phoneNo", phoneNo)
        startActivity(intent)
        finish()
    }

    private fun storeNewUsersData() {
        val rootNode = FirebaseDatabase.getInstance()
        val myRef = rootNode.getReference("Users")
        val userHelperClass = UserHelperClass(fullName, username, email, password, gender, date, phoneNo)
        myRef.child(phoneNo!!).setValue(userHelperClass)
    }

    private fun sendVerificationCodeToUser(phoneNo: String?) {
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
                .setPhoneNumber(phoneNo!!) // Phone number to verify
                .setTimeout(10L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks: OnVerificationStateChangedCallbacks = object : OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            val code = credential.smsCode
            if (code != null) {
                pinFromUser!!.setText(code)
                verifyCode(code)
            }
            Log.d(TAG, "onVerificationCompleted: ")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed" + e.localizedMessage)
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } // The SMS quota for the project has been exceeded
            // ...


            // Show a message and update the UI
            // ...
        }

        override fun onCodeSent(verificationId: String,
                                token: ForceResendingToken) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            super.onCodeSent(verificationId, token)
            codeBySystem = verificationId

            // Save verification ID and resending token so we can use them later
            //mVerificationId = verificationId;
            //mResendToken = token;

            // ...
        }
    }

    fun goToHomeFromOTP(view: View?) {}
    fun callNextScreenFromOTP(view: View?) {
        val code = Objects.requireNonNull(pinFromUser!!.text).toString()
        if (!code.isEmpty()) {
            verifyCode(code)
        }
    }

    companion object {
        private const val TAG = "VerifyOTP"
    }
}