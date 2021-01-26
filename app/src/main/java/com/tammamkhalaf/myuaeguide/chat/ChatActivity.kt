package com.tammamkhalaf.myuaeguide.chat

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.chat.ChatActivity
import com.tammamkhalaf.myuaeguide.chat.utility.ChatroomListAdapter
import com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.PhoneAuthActivity
import com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage
import com.tammamkhalaf.myuaeguide.databases.firebase.models.Chatroom
import java.util.*

class ChatActivity : AppCompatActivity() {
    //widgets
    private var mListView: ListView? = null
    private var mFob: FloatingActionButton? = null

    //vars
    private var mChatrooms: ArrayList<Chatroom>? = null
    private var mAdapter: ChatroomListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_chat)
        mListView = findViewById(R.id.listView)
        mFob = findViewById(R.id.fob)
    }

    fun init() {
        chatrooms
        mFob!!.setOnClickListener { view: View? ->
            val dialog = NewChatroomDialog()
            dialog.show(supportFragmentManager, getString(R.string.dialog_new_chatroom))
        }
    }

    private fun setupChatroomList() {
        Log.d(TAG, "setupChatroomList: setting up chatroom listview")
        mAdapter = ChatroomListAdapter(this@ChatActivity, R.layout.layout_chatroom_listitem, mChatrooms!!)
        mListView!!.adapter = mAdapter
        mListView!!.onItemClickListener = OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long ->
            Log.d(TAG, "onItemClick: selected chatroom: " + mChatrooms!![i].toString())
            val intent = Intent(this@ChatActivity, ChatroomActivity::class.java)
            intent.putExtra(getString(R.string.intent_chatroom), mChatrooms!![i])
            startActivity(intent)
        }
    }
    //                    chatroom.setChatroom_id(singleSnapshot.getValue(Chatroom.class).getChatroom_id());
//                    chatroom.setSecurity_level(singleSnapshot.getValue(Chatroom.class).getSecurity_level());
//                    chatroom.setCreator_id(singleSnapshot.getValue(Chatroom.class).getCreator_id());
//                    chatroom.setChatroom_name(singleSnapshot.getValue(Chatroom.class).getChatroom_name());

    //get the chatrooms messages
    private val chatrooms: Unit
        private get() {
            Log.d(TAG, "getChatrooms: retrieving chatroom's from firebase database.")
            mChatrooms = ArrayList()
            val reference = FirebaseDatabase.getInstance().reference
            val query: Query = reference.child(getString(R.string.dbnode_chatrooms))
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (singleSnapshot in dataSnapshot.children) {
                        Log.d(TAG, "onDataChange: found chatroom: "
                                + singleSnapshot.value)
                        val chatroom = Chatroom()
                        val objectMap: Map<String, Any>? = singleSnapshot.value as HashMap<String, Any>?
                        chatroom.chatroom_id = objectMap!![getString(R.string.field_chatroom_id)].toString()
                        chatroom.chatroom_name = objectMap[getString(R.string.field_chatroom_name)].toString()
                        chatroom.creator_id = objectMap[getString(R.string.field_creator_id)].toString()
                        chatroom.security_level = objectMap[getString(R.string.field_security_level)].toString()


//                    chatroom.setChatroom_id(singleSnapshot.getValue(Chatroom.class).getChatroom_id());
//                    chatroom.setSecurity_level(singleSnapshot.getValue(Chatroom.class).getSecurity_level());
//                    chatroom.setCreator_id(singleSnapshot.getValue(Chatroom.class).getCreator_id());
//                    chatroom.setChatroom_name(singleSnapshot.getValue(Chatroom.class).getChatroom_name());

                        //get the chatrooms messages
                        val messagesList = ArrayList<ChatMessage>()
                        for (snapshot in singleSnapshot
                                .child(getString(R.string.field_chatroom_messages)).children) {
                            val message = ChatMessage()
                            message.timestamp = snapshot.getValue(ChatMessage::class.java)!!.timestamp
                            message.user_id = snapshot.getValue(ChatMessage::class.java)!!.user_id
                            message.message = snapshot.getValue(ChatMessage::class.java)!!.message
                            FirebaseDatabase.getInstance().reference
                                    .child("Users")
                                    .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)
                                    .child("username").addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val username = snapshot.getValue(String::class.java)
                                            message.name = username
                                            Log.d(TAG, "onDataChange: username " + snapshot.getValue(String::class.java))
                                        }

                                        override fun onCancelled(error: DatabaseError) {}
                                    })
                            FirebaseDatabase.getInstance().reference
                                    .child("Users")
                                    .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)
                                    .child("profile_image").addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val imageUrl = snapshot.getValue(String::class.java)
                                            message.profile_image = imageUrl
                                            Log.d(TAG, "onDataChange: image " + snapshot.getValue(String::class.java))
                                        }

                                        override fun onCancelled(error: DatabaseError) {}
                                    })
                            messagesList.add(message)
                        }
                        chatroom.chatroom_messages = messagesList
                        mChatrooms!!.add(chatroom)
                    }
                    setupChatroomList()
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

    fun showDeleteChatroomDialog(chatroomId: String?) {
        val dialog = DeleteChatroomDialog()
        val args = Bundle()
        args.putString(getString(R.string.field_chatroom_id), chatroomId)
        dialog.arguments = args
        dialog.show(supportFragmentManager, getString(R.string.dialog_delete_chatroom))
    }

    override fun onResume() {
        super.onResume()
        checkAuthenticationState()
    }

    private fun checkAuthenticationState() {
        Log.d(TAG, "checkAuthenticationState: checking authentication state.")
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.")
            MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.title))
                    .setMessage(resources.getString(R.string.supporting_text))
                    .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                        // Respond to negative button press
                        finish()
                    }
                    .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                        // Respond to positive button press
                        val intent = Intent(this@ChatActivity, PhoneAuthActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .show()

        } else {
            Log.d(TAG, "checkAuthenticationState: user is authenticated.")
            init()
        }
    }

    companion object {
        private const val TAG = "ChatActivity"
    }
}