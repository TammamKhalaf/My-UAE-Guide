package com.tammamkhalaf.myuaeguide.common.loginSignup

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hbb20.CountryCodePicker
import com.tammamkhalaf.myuaeguide.common.loginSignup.forgetPassword.ForgetPassword
import com.tammamkhalaf.myuaeguide.common.loginSignup.signUp.SignUp
import com.tammamkhalaf.myuaeguide.databases.SessionManager
import com.tammamkhalaf.myuaeguide.locationOwner.RetailerDashboard
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import com.tammamkhalaf.myuaeguide.common.loginSignup.forgetPassword.SetNewPassword
import com.tammamkhalaf.myuaeguide.databases.firebase.models.User
import com.tammamkhalaf.myuaeguide.user.UserDashboard
import java.util.*
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {
    var countryCodePicker: CountryCodePicker? = null
    var phoneNumber: TextInputLayout? = null
    var password: TextInputLayout? = null
    var progressbar: RelativeLayout? = null
    private var RememberMe: CheckBox? = null
    lateinit var phoneNumberEditText: TextInputEditText
    private lateinit var passwordEditText //todo create hooks
     : TextInputEditText
    var whatTodo: String? = null
    var pinFromUser: PinView? = null
    var codeBySystem: String? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        pinFromUser = findViewById(R.id.pin_view)
        mAuth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_retailer_login)
        countryCodePicker = findViewById(R.id.login_country_code_picker)
        phoneNumber = findViewById(R.id.login_phone_number)
        password = findViewById(R.id.login_password)
        progressbar = findViewById(R.id.login_progress_bar)
        RememberMe = findViewById(R.id.remember_me)
        phoneNumberEditText = findViewById(R.id.login_phone_number_editText)
        passwordEditText = findViewById(R.id.login_password_editText)

        //region session
        val sessionManager = SessionManager(this@Login, SessionManager.SESSION_REMEMBER_ME)
        if (sessionManager.checkRememberMe()) {
            val rememberMeDetails = sessionManager.rememberMeDetailFromSession
            phoneNumberEditText.setText(rememberMeDetails[SessionManager.KEY_SESSION_PHONE_NUMBER])
            passwordEditText.setText(rememberMeDetails[SessionManager.KEY_SESSION_PASSWORD])
        }
        //endregion
    }

    fun rememberMeButton(view: View?) {

    }
    fun callForgetPassword(view: View?) {
        startActivity(Intent(applicationContext, ForgetPassword::class.java))
    }

    fun letTheUserLoggedIn(view: View?) {
        if (!isConnected(this)) {
            showCustomDialog()
        }
            progressbar!!.visibility = View.VISIBLE
            if (!validateFields()) {
                return
            }
            val _phoneNumber = Objects.requireNonNull(phoneNumber!!.editText)?.text.toString().trim { it <= ' ' }
            val _password = Objects.requireNonNull(password!!.editText)?.text.toString().trim { it <= ' ' }

            //Get complete phone number
            var _getUserEnteredPhoneNumber = _phoneNumber
            //Remove first zero if entered!
            if (_getUserEnteredPhoneNumber[0] == '0') {
                _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1)
            }
            //Complete phone number
            val _phoneNo = "+" + countryCodePicker!!.fullNumber + _getUserEnteredPhoneNumber
            if (RememberMe!!.isChecked) {
                val sessionManager = SessionManager(this@Login, SessionManager.SESSION_REMEMBER_ME)
                sessionManager.createRememberMeSession(_password, _getUserEnteredPhoneNumber)
            }
            val checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_phoneNo)
            checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        phoneNumber!!.error = null
                        phoneNumber!!.isErrorEnabled = false

                        val systemPassword = snapshot.child(_phoneNo).child("password").getValue(String::class.java)
                        if (Objects.requireNonNull(systemPassword) == _password) {
                            password!!.error = null
                            phoneNumber!!.isErrorEnabled = false
                            val systemFullName = snapshot.child(_phoneNo).child("fullName").getValue(String::class.java)
                            val systemUsername = snapshot.child(_phoneNo).child("username").getValue(String::class.java)
                            val systemEmail = snapshot.child(_phoneNo).child("email").getValue(String::class.java)
                            val systemDOB = snapshot.child(_phoneNo).child("date").getValue(String::class.java)
                            val systemPhoneNo = snapshot.child(_phoneNo).child("phoneNo").getValue(String::class.java)
                            val systemGender = snapshot.child(_phoneNo).child("gender").getValue(String::class.java)
                            val sessionManager = SessionManager(this@Login, SessionManager.SESSION_USER_SESSION)
                            sessionManager.createLoginSession(systemFullName, systemUsername, systemEmail, systemPassword, systemGender, systemDOB, systemPhoneNo)
                            startActivity(Intent(applicationContext, RetailerDashboard::class.java))
                        } else {
                            Toast.makeText(this@Login, getString(string.PasswordDoesntMatch), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        progressbar!!.visibility = View.GONE
                        Toast.makeText(this@Login, getString(string.NoSuchUser), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    progressbar!!.visibility = View.GONE
                    Toast.makeText(this@Login, "" + error.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun isConnected(login: Login): Boolean {
        val connectivityManager = login.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return wifiConnection != null && wifiConnection.isConnected || mobileConnection != null && mobileConnection.isConnected
    }

    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(this@Login)
        builder.setMessage(string.ConnectInternet)
                .setCancelable(false)
                .setPositiveButton(string.connect) { _: DialogInterface?, _: Int -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }
                .setNegativeButton(string.cancel) { _: DialogInterface?, _: Int -> startActivity(Intent(applicationContext, RetailerStartUpScreen::class.java)) }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun validateFields(): Boolean {
        val _phoneNumber = Objects.requireNonNull(phoneNumber!!.editText)?.text.toString().trim { it <= ' ' }
        val _password = Objects.requireNonNull(password!!.editText)?.text.toString().trim { it <= ' ' }
        return when {
            _phoneNumber.isEmpty() -> {
                phoneNumber!!.error = getString(string.Empty_Field)
                phoneNumber!!.requestFocus()
                false
            }
            _password.isEmpty() -> {
                password!!.error = getString(string.Empty_Field)
                password!!.requestFocus()
                false
            }
            else -> {
                true
            }
        }
    }

    fun callSignUpScreen(view: View?) {
        val intent = Intent(applicationContext, SignUp::class.java)
        val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)
        pairs[0] = Pair<View, String>(findViewById(R.id.signup_btn), "transition_signup")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this@Login, *pairs)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ")
    }
    
    companion object{
        private const val TAG = "Login"
    }
}