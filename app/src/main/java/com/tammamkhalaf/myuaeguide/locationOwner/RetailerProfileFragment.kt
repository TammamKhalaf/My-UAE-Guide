package com.tammamkhalaf.myuaeguide.locationOwner

import android.content.Intent
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.FirebaseDatabase
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.databases.SessionManager

/**
 * A simple [Fragment] subclass.
 * Use the [RetailerProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RetailerProfileFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var view:View = inflater.inflate(R.layout.fragment_retailer_profile, container, false)


        var text:EditText =  view.findViewById(R.id.profile_name)

        var btn: Button = view.findViewById(R.id.profile_button_update)

        btn.setOnClickListener {
            if (text.text.isNotEmpty()) {
                Log.d(TAG, "text.text : ${text.text}")

                val reference = FirebaseDatabase.getInstance().getReference("Users")

                reference.child(SessionManager.usersDetailFromSession["phoneNo"]!!).child("username").setValue(text.text.toString())
                        .addOnSuccessListener {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                            Log.d(TAG, ": onFailureListener $it")
                }

            }
        }

        return view
    }

    companion object {
        private const val TAG = "RetailerProfileFragment"
    }
}