package com.tammamkhalaf.kmmsharedmodule.common.loginSignup.forgetPassword

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import com.tammamkhalaf.myuaeguide.common.loginSignup.RetailerStartUpScreen
import com.tammamkhalaf.myuaeguide.R
import java.util.*

class SetNewPassword : AppCompatActivity() {
    var animation: Animation? = null
    var confirmPassword: TextInputLayout? = null
    var newPassword: TextInputLayout? = null
    var progress_bar: RelativeLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_set_new_password)
        confirmPassword = findViewById(R.id.confirm_password)
        newPassword = findViewById(R.id.new_password)
        progress_bar = findViewById(R.id.progress_bar)

        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation)

        //Set animation to all the elements
//        screenIcon.setAnimation(animation);
//        title.setAnimation(animation);
//        description.setAnimation(animation);
//        phoneNumberTextField.setAnimation(animation);
//        countryCodePicker.setAnimation(animation);
//        nextBtn.setAnimation(animation);
    }

    fun goToHomeFromSetNewPassword(view: View?) {}
    fun setNewPasswordBtn(view: View?) {
        if (!isConnected(this)) {
            showCustomDialog()
        }
        if (!validateFields()) {
            return
        }
        progress_bar!!.visibility = View.VISIBLE
        val confirmedPassword = Objects.requireNonNull(confirmPassword!!.editText)?.text.toString().trim { it <= ' ' }
        val phoneNo = intent.getStringExtra("phoneNo")
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(phoneNo!!).child("password").setValue(confirmedPassword)
        startActivity(Intent(this@SetNewPassword, ForgetPasswordSuccessMessage::class.java))
        finish()
    }

    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(this@SetNewPassword)
        builder.setMessage("Please Connect To The Internet to connect further!")
                .setCancelable(false)
                .setPositiveButton("Connect") { dialogInterface: DialogInterface?, i: Int -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }.setNegativeButton("Cancel") { dialogInterface: DialogInterface?, i: Int -> startActivity(Intent(applicationContext, RetailerStartUpScreen::class.java)) }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun isConnected(setNewPassword: SetNewPassword): Boolean {
        val connectivityManager = setNewPassword.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return wifiConnection != null && wifiConnection.isConnected || mobileConnection != null && mobileConnection.isConnected
    }

    private fun validateFields(): Boolean {
        return if (Objects.requireNonNull(newPassword!!.editText)?.text.toString().trim { it <= ' ' }.isEmpty()) {
            newPassword!!.error = "Field can not be empty"
            newPassword!!.requestFocus()
            false
        } else if (Objects.requireNonNull(confirmPassword!!.editText)?.text.toString().trim { it <= ' ' }.isEmpty()) {
            confirmPassword!!.error = "Field can not be empty"
            confirmPassword!!.requestFocus()
            false //todo check zero for phone number add at the beginnings
        } else if (newPassword!!.editText!!.text.toString().trim { it <= ' ' } != confirmPassword!!.editText!!.text.toString().trim { it <= ' ' }) {
            newPassword!!.requestFocus()
            newPassword!!.error = "Both should be same"
            confirmPassword!!.requestFocus()
            confirmPassword!!.error = "Both should be same"
            false
        } else {
            true
        }
    }
}