/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tammamkhalaf.myuaeguide.common.loginSignup.login.java

import androidx.appcompat.app.AppCompatActivity

/**
 * Simple list-based Activity to redirect to one of the other Activities. This Activity does not
 * contain any useful code related to Firebase Authentication. You may want to start with
 * one of the following Files:
 * [GoogleSignInActivity]
 * [FacebookLoginActivity]}
 * [EmailPasswordActivity]
 * [PasswordlessActivity]
 * [PhoneAuthActivity]
 * [AnonymousAuthActivity]
 * [.java.CustomAuthActivity]
 * [GenericIdpActivity]
 * [.java.MultiFactorActivity]
 */
class ChooserActivity : AppCompatActivity(), android.widget.AdapterView.OnItemClickListener {
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooser)

        // Set up ListView and Adapter
        val listView: android.widget.ListView = findViewById(R.id.listView)
        val adapter = MyArrayAdapter(this, android.R.layout.simple_list_item_2, CLASSES)
        adapter.setDescriptionIds(DESCRIPTION_IDS)
        listView.setAdapter(adapter)
        listView.setOnItemClickListener(this)
    }

    override fun onItemClick(parent: android.widget.AdapterView<*>?, view: android.view.View, position: Int, id: Long) {
        val clicked: java.lang.Class = CLASSES[position]
        startActivity(android.content.Intent(this, clicked))
    }

    class MyArrayAdapter(context: android.content.Context, resource: Int, objects: Array<java.lang.Class>) : android.widget.ArrayAdapter<java.lang.Class?>(context, resource, objects) {
        private val mContext: android.content.Context
        private val mClasses: Array<java.lang.Class>
        private var mDescriptionIds: IntArray
        override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
            var view: android.view.View? = convertView
            if (convertView == null) {
                val inflater: android.view.LayoutInflater = mContext.getSystemService<Any>(LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater
                view = inflater.inflate(android.R.layout.simple_list_item_2, null)
            }
            (view.findViewById<android.view.View>(android.R.id.text1) as android.widget.TextView).setText(mClasses[position].getSimpleName())
            (view.findViewById<android.view.View>(android.R.id.text2) as android.widget.TextView).setText(mDescriptionIds[position])
            return view
        }

        fun setDescriptionIds(descriptionIds: IntArray) {
            mDescriptionIds = descriptionIds
        }

        init {
            mContext = context
            mClasses = objects
        }
    }

    companion object {
        private val CLASSES: Array<java.lang.Class> = arrayOf<java.lang.Class>(
                com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.GoogleSignInActivity::class.java,
                com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.FacebookLoginActivity::class.java,
                com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.EmailPasswordActivity::class.java,  //            PasswordlessActivity.class,
                com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.PhoneAuthActivity::class.java //,
                //            AnonymousAuthActivity.class,
                //            FirebaseUIActivity.class,
                //            CustomAuthActivity.class,
                //            GenericIdpActivity.class,
                //            MultiFactorActivity.class,
        )
        private val DESCRIPTION_IDS = intArrayOf(
                R.string.desc_google_sign_in,
                R.string.desc_facebook_login,
                R.string.desc_emailpassword,  // R.string.desc_passwordless,
                R.string.desc_phone_auth)
    }
}