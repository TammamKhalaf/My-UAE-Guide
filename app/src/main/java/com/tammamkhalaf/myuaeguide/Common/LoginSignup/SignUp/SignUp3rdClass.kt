package com.tammamkhalaf.myuaeguide.Common.LoginSignup.SignUp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.hbb20.CountryCodePicker
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.SignUp.SignUp3rdClass
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.VerifyOTP
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import java.util.*

class SignUp3rdClass : AppCompatActivity() {
    var scrollView: ScrollView? = null
    var countryCodePicker: CountryCodePicker? = null
    var phoneNumber: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_sign_up3rd_class)
        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view)
        countryCodePicker = findViewById(R.id.country_code_picker)
        phoneNumber = findViewById(R.id.signup_phone_number)
    }

    fun callVerifyOTPScreen(view: View?) {
        if (!validatePhoneNumber()) {
            return
        }
        val intentPrevious = intent
        val fullName = intentPrevious.getStringExtra("fullName")
        val username = intentPrevious.getStringExtra("username")
        val email = intentPrevious.getStringExtra("email")
        val password = intentPrevious.getStringExtra("password")
        val gender = intentPrevious.getStringExtra("gender")
        val date = intentPrevious.getStringExtra("age")
        //String getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        //String phoneNo = "+"+countryCodePicker.getSelectedCountryCode()+getUserEnteredPhoneNumber;//getFullNumber

        //Get complete phone number
        var _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumber!!.editText)?.text.toString().trim { it <= ' ' }
        //Remove first zero if entered!
        if (_getUserEnteredPhoneNumber[0] == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1)
        }
        //Complete phone number
        val _phoneNo = "+" + countryCodePicker!!.fullNumber + _getUserEnteredPhoneNumber
        val intent = Intent(applicationContext, VerifyOTP::class.java)
        intent.putExtra("fullName", fullName)
        intent.putExtra("username", username)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
        intent.putExtra("gender", gender)
        intent.putExtra("age", date)
        intent.putExtra("phoneNo", _phoneNo)

        //todo Add Transition
        val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)
        pairs[0] = Pair<View, String>(scrollView, "transition_OTP_screen")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this@SignUp3rdClass, *pairs)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val `val` = Objects.requireNonNull(phoneNumber!!.editText)?.text.toString().trim { it <= ' ' }
        val checkspaces = "Aw{1,20}z"
        return if (`val`.isEmpty()) {
            phoneNumber!!.error = getString(string.EnterValidPhoneNo)
            false
        } else if (`val`.contains(" ")) {
            phoneNumber!!.error = getString(string.NoWhiteSpaces)
            false
        } else {
            phoneNumber!!.error = null
            phoneNumber!!.isErrorEnabled = false
            true
        }
    }

    companion object {
        private const val TAG = "SignUp3rdClass"
    }
}