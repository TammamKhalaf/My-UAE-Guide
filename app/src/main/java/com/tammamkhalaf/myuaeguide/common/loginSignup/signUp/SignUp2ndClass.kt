package com.tammamkhalaf.myuaeguide.common.loginSignup.signUp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import java.util.*

class SignUp2ndClass : AppCompatActivity() {
    //Variables
    var backBtn: ImageView? = null
    var next: Button? = null
    var login: Button? = null
    var titleText: TextView? = null
    var slideText: TextView? = null
    var radioGroup: RadioGroup? = null
    lateinit var selectedGender: RadioButton
    var datePicker: DatePicker? = null
    var gender = 0
    var currentAge = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_sign_up2nd_class)

        //Hooks
        backBtn = findViewById(R.id.signup_back_button)
        next = findViewById(R.id.signup_next_button)
        login = findViewById(R.id.signup_login_button)
        titleText = findViewById(R.id.signup_title_text)
        slideText = findViewById(R.id.signup_slide_text)
        radioGroup = findViewById(R.id.radio_group)
        datePicker = findViewById(R.id.age_picker)
    }

    fun call3rdSigupScreen(view: View?) {
        if (!validateAge() or !validateGender()) {
            finish()
        }
        val intentPrevious = intent
        val fullname = intentPrevious.getStringExtra("fullName")
        val username = intentPrevious.getStringExtra("username")
        val email = intentPrevious.getStringExtra("email")
        val password = intentPrevious.getStringExtra("password")
        selectedGender = findViewById(radioGroup!!.checkedRadioButtonId)
        val selectedGend = selectedGender.text.toString()
        val day = datePicker!!.dayOfMonth
        val month = datePicker!!.month
        val year = datePicker!!.year
        val DOB = "$day/$month/$year"
        val intent = Intent(applicationContext, SignUp3rdClass::class.java)
        intent.putExtra("fullName", fullname)
        intent.putExtra("username", username)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
        intent.putExtra("age", DOB)
        intent.putExtra("gender", selectedGend)

        //Add Transition and call next activity
        val pairs:  Array<Pair<View, String>?> = arrayOfNulls(5)
        pairs[0] = Pair<View, String>(backBtn, "transition_back_arrow_btn")
        pairs[1] = Pair<View, String>(next, "transition_next_btn")
        pairs[2] = Pair<View, String>(login, "transition_login_btn")
        pairs[3] = Pair<View, String>(titleText, "transition_title_text")
        pairs[4] = Pair<View, String>(slideText, "transition_slide_text")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this@SignUp2ndClass, *pairs)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    private fun validateGender(): Boolean {
        return if (radioGroup!!.checkedRadioButtonId == -1) {
            Toast.makeText(this, getString(string.SelectGender), Toast.LENGTH_SHORT).show()
            false
        } else {
            gender = radioGroup!!.checkedRadioButtonId
            true
        }
    }

    private fun validateAge(): Boolean {
        val currentYear = Calendar.getInstance()[Calendar.YEAR]
        val userAge = datePicker!!.year
        val isAgeValid = currentYear - userAge
        currentAge = if (isAgeValid < 14) {
            Toast.makeText(this, getString(string.NotAllowed), Toast.LENGTH_SHORT).show()
            return false
        } else isAgeValid
        return true
    }

    companion object {
        private const val TAG = "SignUp2ndClass"
    }
}