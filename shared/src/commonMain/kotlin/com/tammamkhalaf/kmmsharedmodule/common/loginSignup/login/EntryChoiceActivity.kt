package com.tammamkhalaf.kmmsharedmodule.common.loginSignup.login

import android.content.Intent
import com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.BaseEntryChoiceActivity
import com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.Choice

class EntryChoiceActivity : BaseEntryChoiceActivity() {

    override fun getChoices(): List<Choice> {
        return listOf(Choice("Welcome", "Run the Firebase Auth quickstart written in Java.", Intent(this,
                        com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.ChooserActivity::class.java)))
    }
}