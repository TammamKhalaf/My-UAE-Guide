package com.tammamkhalaf.kmmsharedmodule.common.loginSignup.forgetPassword

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hbb20.CountryCodePicker
import com.tammamkhalaf.myuaeguide.common.loginSignup.VerifyOTP
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import java.util.*

class ForgetPassword : AppCompatActivity() {
    var animation: Animation? = null
    private lateinit var phoneNumberTextField: TextInputLayout
    private lateinit var countryCodePicker: CountryCodePicker
    var progressBar: RelativeLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_forget_password)
        val screenIcon = findViewById<ImageView>(R.id.forget_password_icon)
        val title = findViewById<TextView>(R.id.forget_password_title)
        val description = findViewById<TextView>(R.id.forget_password_description)
        phoneNumberTextField = findViewById(R.id.forget_password_phone_number)
        countryCodePicker = findViewById(R.id.country_code_picker)
        val nextBtn = findViewById<Button>(R.id.forget_password_next_btn)
        progressBar = findViewById(R.id.progress_bar)


        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation)

        //Set animation to all the elements
        screenIcon.animation = animation
        title.animation = animation
        description.animation = animation
        phoneNumberTextField.animation = animation
        countryCodePicker.animation = animation
        nextBtn.animation = animation
    }

    fun callBackScreenFromForgetPassword(view: View?) {}
    fun verifyPhoneNumber(view: View?) {
        if (isConnected(this) && validateField()) {
            progressBar!!.visibility = View.VISIBLE
            //Get complete phone number
            var _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumberTextField.editText)?.text.toString().trim { it <= ' ' }
            //Remove first zero if entered!
            if (_getUserEnteredPhoneNumber[0] == '0') {
                _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1)
            }
            //Complete phone number
            val _phoneNo = "+" + countryCodePicker.fullNumber + _getUserEnteredPhoneNumber
            val checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_phoneNo)
            checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        phoneNumberTextField.error = null
                        phoneNumberTextField.isErrorEnabled = false
                        val intent = Intent(applicationContext, VerifyOTP::class.java)
                        intent.putExtra("phoneNo", _phoneNo)
                        intent.putExtra("whatToDo", "updateData")
                        startActivity(intent)
                        finish()
                        progressBar!!.visibility = View.GONE
                    } else {
                        progressBar!!.visibility = View.GONE
                        phoneNumberTextField.error = getString(string.NoSuchUser)
                        phoneNumberTextField.requestFocus()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    progressBar!!.visibility = View.GONE
                }
            })
        }
    }

    private fun isConnected(forgetPassword: ForgetPassword): Boolean {
        val connectivityManager = forgetPassword.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return wifiConnection != null && wifiConnection.isConnected || mobileConnection != null && mobileConnection.isConnected
    }

    private fun validateField(): Boolean {
        val _phoneNumber = Objects.requireNonNull(phoneNumberTextField.editText)?.text.toString().trim { it <= ' ' }
        return if (_phoneNumber.isEmpty()) {
            phoneNumberTextField.error = getString(string.Empty_Field)
            phoneNumberTextField.requestFocus()
            false
        } else {
            true //todo check zero for phone number add at the beginnings
        }
    }
}