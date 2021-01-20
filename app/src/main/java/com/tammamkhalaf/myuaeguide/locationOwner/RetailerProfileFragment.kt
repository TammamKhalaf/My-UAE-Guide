package com.tammamkhalaf.myuaeguide.locationOwner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.databases.SessionManager
import com.tammamkhalaf.myuaeguide.databases.firebase.models.User

/**
 * A simple [Fragment] subclass.
 * Use the [RetailerProfileFragment.new Instance] factory method to
 * create an instance of this fragment.
 */
class RetailerProfileFragment : Fragment() {
    private lateinit var usernameEditText:EditText
    private lateinit var emailEditText:EditText
    lateinit var fullNameEditText:EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        val view:View = inflater.inflate(R.layout.fragment_retailer_profile, container, false)


         usernameEditText =  view.findViewById(R.id.profile_name)
         emailEditText =  view.findViewById(R.id.profile_email)
         fullNameEditText =  view.findViewById(R.id.profile_fullname)

        val btn: Button = view.findViewById(R.id.profile_button_update)

        btn.setOnClickListener {
            if (usernameEditText.text.isNotEmpty()) {
                Log.d(TAG, "text.text : ${usernameEditText.text}")
                val reference = FirebaseDatabase.getInstance().getReference("Users")
                reference.child(SessionManager.usersDetailFromSession["phoneNo"]!!).child("username").setValue(usernameEditText.text.toString())
                        .addOnSuccessListener {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                            Log.d(TAG, ": onFailureListener $it")
                }

            }

            if (emailEditText.text.isNotEmpty()) {
                Log.d(TAG, "text.text : ${usernameEditText.text}")
                val reference = FirebaseDatabase.getInstance().getReference("Users")
                reference.child(SessionManager.usersDetailFromSession["phoneNo"]!!).child("email").setValue(emailEditText.text.toString())
                        .addOnSuccessListener {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                            Log.d(TAG, ": onFailureListener $it")
                }

            }

            if (fullNameEditText.text.isNotEmpty()) {
                Log.d(TAG, "text.text : ${usernameEditText.text}")
                val reference = FirebaseDatabase.getInstance().getReference("Users")
                reference.child(SessionManager.usersDetailFromSession["phoneNo"]!!).child("fullName").setValue(fullNameEditText.text.toString())
                        .addOnSuccessListener {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                            Log.d(TAG, ": onFailureListener $it")
                }

            }

        }

        return view
    }

    init {
        getUserAccountsData()
    }

    private fun getUserAccountsData(){
        val reference:DatabaseReference = FirebaseDatabase.getInstance().reference
        /**
         *
         * ----------------- Query 1-----------
         * */


        val query1:Query = reference.child("Users").orderByKey().equalTo(SessionManager.usersDetailFromSession["phoneNo"])
        
        query1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (singleSnapshot in snapshot.children) {
                    val user: User? = singleSnapshot.getValue(User::class.java)
                    fullNameEditText.setText(user?.fullName)
                    usernameEditText.setText(user?.username)
                    emailEditText.setText(user?.email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: ", error.message as Throwable)
            }

        })

        /**
         *
         * ----------------- Query 2-----------
         * */

        val query2:Query = reference.child("Users")
                .orderByChild("phoneNo")
                .equalTo(SessionManager.usersDetailFromSession["phoneNo"])

        query2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (singleSnapshot in snapshot.children) {
                    val user: User? = singleSnapshot.getValue(User::class.java)
                    fullNameEditText.setText(user?.fullName)
                    usernameEditText.setText(user?.username)
                    emailEditText.setText(user?.email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: ",error.details as Throwable )
            }

        })

    }

    companion object {
        private const val TAG = "RetailerProfileFragment"
    }
}