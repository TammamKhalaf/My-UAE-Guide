package com.tammamkhalaf.myuaeguide.chat

class ChatroomActivity : AppCompatActivity() {
    //firebase
    private var mAuthListener: AuthStateListener? = null
    private var mMessagesReference: DatabaseReference? = null

    //widgets
    private var mChatroomName: android.widget.TextView? = null
    private var mListView: android.widget.ListView? = null
    private var mMessage: android.widget.EditText? = null
    private var mCheckmark: android.widget.ImageView? = null

    //vars
    private var mChatroom: Chatroom? = null
    private var mMessagesList: MutableList<ChatMessage>? = null
    private var mAdapter: ChatMessageListAdapter? = null
    var username: String? = null
    var profile_image: String? = null
    protected fun onCreate(@Nullable savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_chatroom)
        mChatroomName = findViewById(R.id.text_chatroom_name)
        mListView = findViewById(R.id.listView)
        mMessage = findViewById(R.id.input_message)
        mCheckmark = findViewById(R.id.checkmark)
        android.util.Log.d(TAG, "onCreate: started.")
        mMessagesList = java.util.ArrayList<ChatMessage>()
        username = ANONYMOUS
        setupFirebaseAuth()
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        /*
            ---------- QUERY Method 1 ----------
         */
        val query1: Query = reference.child("Users").orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
        query1.addListenerForSingleValueEvent(object : ValueEventListener() {
            fun onDataChange(dataSnapshot: DataSnapshot) {

                //this loop will return a single result
                for (singleSnapshot in dataSnapshot.getChildren()) {
                    android.util.Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: " + singleSnapshot.getValue(User::class.java).toString())
                    val user: User = singleSnapshot.getValue(User::class.java)
                    username = user.getUsername()
                    profile_image = user.getProfile_image()
                }
            }

            fun onCancelled(databaseError: DatabaseError?) {}
        })
        chatroom
        init()
        hideSoftKeyboard()
    }

    private fun init() {
        mMessage.setOnClickListener(android.view.View.OnClickListener { view: android.view.View? ->
            mListView.setSelection(mAdapter.getCount() - 1) //scroll to the bottom of the list
        })
        mCheckmark.setOnClickListener(android.view.View.OnClickListener { view: android.view.View? ->
            if (mMessage.getText().toString() != "") {
                val message: String = mMessage.getText().toString()
                android.util.Log.d(TAG, "onClick: sending new message: $message")

                //create the new message object for inserting
                val newMessage = ChatMessage()
                newMessage.setMessage(message)
                newMessage.setTimestamp(timestamp)
                newMessage.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid())
                newMessage.setName(username)
                newMessage.setProfile_image(profile_image)
                //get a database reference
                val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                        .child(getString(R.string.dbnode_chatrooms))
                        .child(mChatroom.getChatroom_id())
                        .child(getString(R.string.field_chatroom_messages))

                //create the new messages id
                val newMessageId: String = reference.push().getKey()

                //insert the new message into the chatroom
                reference.child(newMessageId).setValue(newMessage)

                //clear the EditText
                mMessage.setText("")

                //refresh the messages list? Or is it done by the listener??
            }
        })
    }

    /**
     * Retrieve the chatroom name using a query
     */
    private val chatroom: Unit
        private get() {
            android.util.Log.d(TAG, "getChatroom: getting selected chatroom details")
            val intent: android.content.Intent = getIntent()
            if (intent.hasExtra(getString(R.string.intent_chatroom))) {
                val chatroom: Chatroom = intent.getParcelableExtra(getString(R.string.intent_chatroom))
                android.util.Log.d(TAG, "getChatroom: chatroom: " + chatroom.toString())
                mChatroom = chatroom
                mChatroomName.setText(mChatroom.getChatroom_name())
                enableChatroomListener()
            }
        }//check and make sure it's not the first message (has no user id)

    // i have add this
    //query the users node to get the profile images and names
    //need to catch null pointer here because the initial welcome message to the
    //chatroom has no user id
    private val chatroomMessages: Unit
        private get() {
            mMessagesList = java.util.ArrayList<ChatMessage>()
            if (mMessagesList!!.size > 0) {
                mMessagesList!!.clear()
                mAdapter.clear()
            }
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
            val query: Query = reference.child(getString(R.string.dbnode_chatrooms))
                    .child(mChatroom.getChatroom_id())
                    .child(getString(R.string.field_chatroom_messages))
            query.addListenerForSingleValueEvent(object : ValueEventListener() {
                fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (singleSnapshot in dataSnapshot.getChildren()) {
                        val snapshot: DataSnapshot = singleSnapshot
                        android.util.Log.d(TAG, "onDataChange: found chatroom message: "
                                + singleSnapshot.getValue())
                        try { //need to catch null pointer here because the initial welcome message to the
                            //chatroom has no user id
                            val message = ChatMessage()
                            val userId: String = snapshot.getValue(ChatMessage::class.java).getUser_id()
                            if (userId != null) { //check and make sure it's not the first message (has no user id)
                                message.setMessage(snapshot.getValue(ChatMessage::class.java).getMessage())
                                message.setUser_id(snapshot.getValue(ChatMessage::class.java).getUser_id())
                                message.setTimestamp(snapshot.getValue(ChatMessage::class.java).getTimestamp())
                                message.setProfile_image(snapshot.getValue(ChatMessage::class.java).getProfile_image()) // i have add this
                                message.setName(snapshot.getValue(ChatMessage::class.java).getName())
                                mMessagesList!!.add(message)
                            } else {
                                message.setMessage(snapshot.getValue(ChatMessage::class.java).getMessage())
                                message.setTimestamp(snapshot.getValue(ChatMessage::class.java).getTimestamp())
                                mMessagesList!!.add(message)
                            }
                        } catch (e: java.lang.NullPointerException) {
                            android.util.Log.e(TAG, "onDataChange: NullPointerException: " + e.message)
                        }
                    }
                    //query the users node to get the profile images and names
                    userDetails
                    initMessagesList()
                }

                fun onCancelled(databaseError: DatabaseError?) {}
            })
        }

    //DataSnapshot singleSnapshot = dataSnapshot.getChildren().iterator().next();
    private val userDetails: Unit
        private get() {
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
            for (i in mMessagesList!!.indices) {
                android.util.Log.d(TAG, "onDataChange: searching for userId: " + mMessagesList!![i].getUser_id())
                if (mMessagesList!![i].getUser_id() != null) {
                    val query: Query = reference.child(getString(R.string.dbnode_users)).orderByKey().equalTo(mMessagesList!![i].getUser_id())
                    query.addListenerForSingleValueEvent(object : ValueEventListener() {
                        fun onDataChange(dataSnapshot: DataSnapshot) {
                            //DataSnapshot singleSnapshot = dataSnapshot.getChildren().iterator().next();
                            for (singleSnapshot in dataSnapshot.getChildren()) {
                                android.util.Log.d(TAG, "onDataChange: found user id: " + singleSnapshot.getValue(User::class.java).getUser_id())
                                mMessagesList!![i].setProfile_image(singleSnapshot.getValue(User::class.java).getProfile_image())
                                mMessagesList!![i].setName(singleSnapshot.getValue(User::class.java).getUsername())
                                mAdapter.notifyDataSetChanged()
                            }
                        }

                        fun onCancelled(databaseError: DatabaseError?) {}
                    })
                }
            }
        }

    private fun initMessagesList() {
        mAdapter = ChatMessageListAdapter(this@ChatroomActivity, R.layout.layout_chatmessage_listitem, mMessagesList)
        mListView.setAdapter(mAdapter)
        mListView.setSelection(mAdapter.getCount() - 1) //scroll to the bottom of the list
    }

    /**
     * Return the current timestamp in the form of a string
     * @return
     */
    private val timestamp: String
        private get() {
            val sdf: java.text.SimpleDateFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US)
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("Canada/Pacific"))
            return sdf.format(java.util.Date())
        }

    private fun hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    /*
            ----------------------------- Firebase setup ---------------------------------
    */
    protected fun onResume() {
        super.onResume()
        checkAuthenticationState()
    }

    private fun checkAuthenticationState() {
        android.util.Log.d(TAG, "checkAuthenticationState: checking authentication state.")
        val user: FirebaseUser = FirebaseAuth.getInstance().getCurrentUser()
        if (user == null) {
            android.util.Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.")
            val intent: android.content.Intent = android.content.Intent(this@ChatroomActivity, PhoneAuthActivity::class.java)
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent)
        } else {
            android.util.Log.d(TAG, "checkAuthenticationState: user is authenticated.")
        }
    }

    private fun setupFirebaseAuth() {
        android.util.Log.d(TAG, "setupFirebaseAuth: started.")
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user: FirebaseUser = firebaseAuth.getCurrentUser()
            if (user != null) {
                // User is signed in
                android.util.Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid())
            } else {
                // User is signed out
                android.util.Log.d(TAG, "onAuthStateChanged:signed_out")
                val intent: android.content.Intent = android.content.Intent(this@ChatroomActivity, PhoneAuthActivity::class.java)
                startActivity(intent)
            }
        }
    }

    protected fun onDestroy() {
        super.onDestroy()
        mMessagesReference.removeEventListener(mValueEventListener)
    }

    var mValueEventListener: ValueEventListener = object : ValueEventListener() {
        fun onDataChange(dataSnapshot: DataSnapshot?) {
            chatroomMessages
        }

        fun onCancelled(databaseError: DatabaseError?) {}
    }

    private fun enableChatroomListener() {
        /*
            ---------- Listener that will watch the 'chatroom_messages' node ----------
         */
        mMessagesReference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.dbnode_chatrooms))
                .child(mChatroom.getChatroom_id())
                .child(getString(R.string.field_chatroom_messages))
        mMessagesReference.addValueEventListener(mValueEventListener)
    }

    fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener)
    }

    fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener)
        }
    }

    companion object {
        private const val TAG = "ChatroomActivity"
        const val ANONYMOUS = "anonymous"
    }
}