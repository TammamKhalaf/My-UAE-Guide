package com.tammamkhalaf.myuaeguide.Common.LoginSignup.SignUp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.Login
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.SignUp.SignUp
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    //Variables
    var backBtn: ImageView? = null
    var next: Button? = null
    var login: Button? = null
    var titleText: TextView? = null
    var slideText: TextView? = null
    var fullName: TextInputLayout? = null
    var username: TextInputLayout? = null
    var email: TextInputLayout? = null
    var password: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_retailer_sign_up)

        //Hooks for animation
        backBtn = findViewById(R.id.signup_back_button)
        next = findViewById(R.id.signup_next_button)
        login = findViewById(R.id.signup_login_button)
        titleText = findViewById(R.id.signup_title_text)
        slideText = findViewById(R.id.signup_slide_text)
        fullName = findViewById(R.id.signup_fullname)
        username = findViewById(R.id.signup_username)
        email = findViewById(R.id.signup_email)
        password = findViewById(R.id.signup_password)
    }

    fun callNextSignupScreen(view: View?) {
        if (!validateFullName() or !validateUsername() or !validateEmail() or !validatePassword()) {
            return
        }
        val intent = Intent(applicationContext, SignUp2ndClass::class.java)
        intent.putExtra("fullName", Objects.requireNonNull(fullName!!.editText)?.text.toString().trim { it <= ' ' })
        intent.putExtra("username", Objects.requireNonNull(username!!.editText)?.text.toString().trim { it <= ' ' })
        intent.putExtra("email", Objects.requireNonNull(email!!.editText)?.text.toString().trim { it <= ' ' })
        intent.putExtra("password", Objects.requireNonNull(password!!.editText)?.text.toString().trim { it <= ' ' })

        //Add Shared Animation
        val pairs: Array<Pair<View, String>?> = arrayOfNulls(5)
        pairs[0] = Pair<View, String>(backBtn, "transition_back_arrow_btn")
        pairs[1] = Pair<View, String>(next, "transition_next_btn")
        pairs[2] = Pair<View, String>(login, "transition_login_btn")
        pairs[3] = Pair<View, String>(titleText, "transition_title_text")
        pairs[4] = Pair<View, String>(slideText, "transition_slide_text")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this@SignUp, *pairs)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    fun callLoginFromSignUp(view: View?) {
        startActivity(Intent(applicationContext, Login::class.java))
        finish()
    }

    private fun validateFullName(): Boolean {
        val `val` = Objects.requireNonNull(fullName!!.editText)?.text.toString().trim { it <= ' ' }
        return if (`val`.isEmpty()) {
            fullName!!.error = getString(string.Empty_Field)
            false
        } else {
            fullName!!.error = null
            fullName!!.isErrorEnabled = false
            true
        }
    }

    private fun validateUsername(): Boolean {
        val `val` = Objects.requireNonNull(username!!.editText)?.text.toString().trim { it <= ' ' }
        val checkspaces = "\\A\\w{1,20}z"
        return if (`val`.isEmpty()) {
            username!!.error = getString(string.Empty_Field)
            false
        } else if (`val`.length > 20) {
            username!!.error = getString(string.UserNameLarge)
            false
        } else if (`val`.contains(" ")) { //!val.matches(checkspaces)
            username!!.error = getString(string.NoWhiteSpaces)
            false
        } else {
            username!!.error = null
            username!!.isErrorEnabled = false
            true
        }
    }

    private fun validateEmail(): Boolean {
        val `val` = email!!.editText?.text.toString().trim { it <= ' ' }
        val checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+".toRegex()
        return if (`val`.isEmpty()) {
            email!!.error = getString(string.Empty_Field)
            false
        } else if (!`val`.matches(checkEmail)) {
            email!!.error = getString(string.InvalidEmail)
            false
        } else {
            email!!.error = null
            email!!.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
//        String val = password.getEditText().getText().toString().trim();
//        String checkPassword = "^" +
//                //"(?=.*[0-9])" +         //at least 1 digit
//                //"(?=.*[a-z])" +         //at least 1 lower case letter
//                //"(?=.*[A-Z])" +         //at least 1 upper case letter
//                "(?=.*[a-zA-Z])" +      //any letter
//                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
//                "(?=S+$)" ;//+           //no white spaces
//                //".{4,}" +               //at least 4 characters
//                //"$";
//
//        if (val.isEmpty()) {
//            password.setError("Field can not be empty");
//            return false;
//        } else if (!(val.matches(checkPassword))) {
//            password.setError("Password should contain 4 characters!");
//            return false;
//        } else {
//            password.setError(null);
//            password.setErrorEnabled(false);
//            return true;
//        }
        return if (Objects.requireNonNull(password!!.editText)?.text.toString().trim { it <= ' ' }.length < 8 && !isValidPassword(password!!.editText!!.text.toString().trim { it <= ' ' })) {
            password!!.error = getString(string.PasswordStrenght)
            false
        } else {
            println("Valid")
            password!!.error = null
            password!!.isErrorEnabled = false
            true
        }
    }

    companion object {
        //*****************************************************************
        fun isValidPassword(password: String?): Boolean {
            val pattern: Pattern
            val matcher: Matcher
            val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
            pattern = Pattern.compile(PASSWORD_PATTERN)
            matcher = pattern.matcher(password)
            return matcher.matches()
        }
    }
}

