package com.tammamkhalaf.myuaeguide.chat

import androidx.fragment.app.DialogFragment

class NewChatroomDialog : DialogFragment() {
    private var mSeekBar: android.widget.SeekBar? = null
    private var mChatroomName: android.widget.EditText? = null
    private var mCreateChatroom: android.widget.TextView? = null
    private var mSecurityLevel: android.widget.TextView? = null
    private var mUserSecurityLevel = 0
    private var mSeekProgress = 0
    @Nullable
    fun onCreateView(inflater: android.view.LayoutInflater?, @Nullable container: android.view.ViewGroup?, @Nullable savedInstanceState: android.os.Bundle?): android.view.View {
        val view: android.view.View = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_chatroom, container, false)
        mChatroomName = view.findViewById<android.widget.EditText>(R.id.input_chatroom_name)
        mSeekBar = view.findViewById<android.widget.SeekBar>(R.id.input_security_level)
        mCreateChatroom = view.findViewById<android.widget.TextView>(R.id.create_chatroom)
        mSecurityLevel = view.findViewById<android.widget.TextView>(R.id.security_level)
        mSeekProgress = 0
        mSecurityLevel.setText("0") //String.valueOf(mSeekProgress)
        userSecurityLevel
        mCreateChatroom.setOnClickListener(object : android.view.View.OnClickListener() {
            override fun onClick(view: android.view.View) {
                if (mChatroomName.getText().toString() != "") {
                    android.util.Log.d(TAG, "onClick: creating new chat room")
                    if (mUserSecurityLevel >= mSeekBar.getProgress()) {
                        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                        //get the new chatroom unique id
                        val chatroomId: String = reference
                                .child(getString(R.string.dbnode_chatrooms))
                                .push().getKey()

                        //create the chatroom
                        val chatroom = Chatroom()
                        chatroom.setSecurity_level("0") //no need for the time -->String.valueOf(mSeekBar.getProgress())
                        chatroom.setChatroom_name(mChatroomName.getText().toString())
                        chatroom.setCreator_id(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        chatroom.setChatroom_id(chatroomId)


                        //insert the new chatroom into the database
                        reference
                                .child(getString(R.string.dbnode_chatrooms))
                                .child(chatroomId)
                                .setValue(chatroom)

                        //create a unique id for the message
                        val messageId: String = reference
                                .child(getString(R.string.dbnode_chatrooms))
                                .push().getKey()

                        //insert the first message into the chatroom
                        val message = ChatMessage()
                        message.setMessage("Welcome to the new chatroom!")
                        message.setTimestamp(timestamp)
                        reference
                                .child(getString(R.string.dbnode_chatrooms))
                                .child(chatroomId)
                                .child(getString(R.string.field_chatroom_messages))
                                .child(messageId)
                                .setValue(message)
                        (getActivity() as ChatActivity).init()
                        getDialog().dismiss()
                    } else {
                        android.widget.Toast.makeText(getActivity(), "insuffient security level", android.widget.Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        mSeekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: android.widget.SeekBar, i: Int, b: Boolean) {
                mSeekProgress = i
                mSecurityLevel.setText(mSeekProgress.toString())
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar) {}
        })
        return view
    }

    //OR could use ->.orderByChild(getString(R.string.field_user_id))
    private val userSecurityLevel: Unit
        private get() {
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
            val query: Query = reference.child(getString(R.string.dbnode_users))
                    .orderByKey() //OR could use ->.orderByChild(getString(R.string.field_user_id))
                    .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
            query.addListenerForSingleValueEvent(object : ValueEventListener() {
                fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (singleSnapshot in dataSnapshot.getChildren()) {
                        android.util.Log.d(TAG, "onDataChange: users security level: "
                                + singleSnapshot.getValue(User::class.java).getSecurity_level())
                        mUserSecurityLevel = java.lang.String.valueOf(singleSnapshot.getValue(User::class.java).getSecurity_level()).toInt()
                    }
                }

                fun onCancelled(databaseError: DatabaseError?) {}
            })
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

    companion object {
        private const val TAG = "NewChatroomDialog"
    }
}