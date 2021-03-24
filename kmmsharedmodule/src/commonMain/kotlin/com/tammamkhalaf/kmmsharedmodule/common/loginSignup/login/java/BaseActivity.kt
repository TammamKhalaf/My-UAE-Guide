package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import androidx.annotation.VisibleForTesting

class BaseActivity : AppCompatActivity() {
    @VisibleForTesting
    var mProgressBar: android.widget.ProgressBar? = null
    fun setProgressBar(progressBar: android.widget.ProgressBar?) {
        mProgressBar = progressBar
    }

    fun showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(android.view.View.VISIBLE)
        }
    }

    fun hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(android.view.View.INVISIBLE)
        }
    }

    fun hideKeyboard(view: android.view.View) {
        val imm: android.view.inputmethod.InputMethodManager? = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager?
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }

    fun onStop() {
        super.onStop()
        hideProgressBar()
    }
}