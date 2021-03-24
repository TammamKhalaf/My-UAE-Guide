package com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal

import androidx.appcompat.app.AppCompatActivity

abstract class BaseEntryChoiceActivity : AppCompatActivity() {
    private var mRecycler: RecyclerView? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_choice)
        mRecycler = findViewById(R.id.choices_recycler)
        mRecycler.setLayoutManager(LinearLayoutManager(this))
        mRecycler.setAdapter(com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.ChoiceAdapter(this, getChoices()))
    }

    protected abstract fun getChoices(): List<com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.Choice>
}