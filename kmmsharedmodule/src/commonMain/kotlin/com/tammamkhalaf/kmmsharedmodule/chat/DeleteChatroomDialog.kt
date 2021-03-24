package com.tammamkhalaf.myuaeguide.chat

import androidx.fragment.app.DialogFragment

class DeleteChatroomDialog : DialogFragment() {
    private var mChatroomId: String? = null
    fun onCreate(@Nullable savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d(TAG, "onCreate: started")
        mChatroomId = getArguments().getString(getString(R.string.field_chatroom_id))
        if (mChatroomId != null) {
            android.util.Log.d(TAG, "onCreate: got the chatroom id: $mChatroomId")
        }
    }

    @Nullable
    fun onCreateView(inflater: android.view.LayoutInflater, @Nullable container: android.view.ViewGroup?, @Nullable savedInstanceState: android.os.Bundle?): android.view.View {
        val view: android.view.View = inflater.inflate(R.layout.dialog_delete_chatroom, container, false)
        val delete: android.widget.TextView = view.findViewById<android.widget.TextView>(R.id.confirm_delete)
        delete.setOnClickListener(object : android.view.View.OnClickListener() {
            override fun onClick(v: android.view.View) {
                if (mChatroomId != null) {
                    android.util.Log.d(TAG, "onClick: deleting chatroom: $mChatroomId")
                    val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                    reference.child(getString(R.string.dbnode_chatrooms))
                            .child(mChatroomId)
                            .removeValue()
                    getDialog().dismiss()
                    (getActivity() as ChatActivity).init()
                }
            }
        })
        val cancel: android.widget.TextView = view.findViewById<android.widget.TextView>(R.id.cancel)
        cancel.setOnClickListener(android.view.View.OnClickListener { v: android.view.View? ->
            android.util.Log.d(TAG, "onClick: cenceling deletion of chatroom")
            getDialog().dismiss()
        })
        return view
    }

    companion object {
        private const val TAG = "DeleteChatroomDialog"
    }

    //create a new bundle and set the arguments to avoid a null pointer
    init {
        setArguments(android.os.Bundle())
    }
}