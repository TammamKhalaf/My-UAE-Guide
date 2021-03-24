package com.tammamkhalaf.myuaeguide.chat.utility

import androidx.annotation.LayoutRes

/**
 * Created by User on 9/18/2017.
 */
class ChatroomListAdapter(@NonNull context: android.content.Context, @LayoutRes resource: Int, @NonNull objects: List<Chatroom?>?) : android.widget.ArrayAdapter<Chatroom?>(context, resource, objects) {
    private val mLayoutResource: Int
    private val mContext: android.content.Context
    private val mInflater: android.view.LayoutInflater

    class ViewHolder {
        var name: android.widget.TextView? = null
        var creatorName: android.widget.TextView? = null
        var numberMessages: android.widget.TextView? = null
        var mProfileImage: android.widget.ImageView? = null
        var mTrash: android.widget.ImageView? = null
    }

    @NonNull
    override fun getView(position: Int, @Nullable convertView: android.view.View?, @NonNull parent: android.view.ViewGroup): android.view.View {
        var convertView: android.view.View? = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutResource, parent, false)
            holder = ViewHolder()
            holder.name = convertView.findViewById<android.widget.TextView>(R.id.name)
            holder.creatorName = convertView.findViewById<android.widget.TextView>(R.id.creator_name)
            holder.numberMessages = convertView.findViewById<android.widget.TextView>(R.id.number_chatmessages)
            holder.mProfileImage = convertView.findViewById<android.widget.ImageView>(R.id.profile_image)
            holder.mTrash = convertView.findViewById<android.widget.ImageView>(R.id.icon_trash)
        } else {
            holder = convertView.getTag()
        }
        try {
            //set the chatroom name
            holder.name.setText(getItem(position).getChatroom_name())

            //set the number of chat messages
            val chatMessagesString: String = getItem(position).getChatroom_messages().size()
                    .toString() + " messages"
            holder.numberMessages.setText(chatMessagesString)

            //get the users details who created the chatroom
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
            val query: Query = reference.child(mContext.getString(R.string.dbnode_users))
                    .orderByKey()
                    .equalTo(getItem(position).getCreator_id())
            query.addListenerForSingleValueEvent(object : ValueEventListener() {
                fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (singleSnapshot in dataSnapshot.getChildren()) {
                        android.util.Log.d(TAG, "onDataChange: Found chat room creator: "
                                + singleSnapshot.getValue(User::class.java).toString())
                        val createdBy = "created by " + singleSnapshot.getValue(User::class.java).getUsername()
                        holder.creatorName.setText(createdBy)
                        android.util.Log.d(TAG, "onDataChange: " + "created by " + singleSnapshot.getValue(User::class.java).getUsername())

                        //ImageLoader.getInstance().displayImage(singleSnapshot.getValue(User.class).getProfile_image() , holder.mProfileImage);
                        Glide.with(holder.mProfileImage.getContext())
                                .load(singleSnapshot.getValue(User::class.java).getProfile_image())
                                .circleCrop()
                                .placeholder(R.drawable.person)
                                .into(holder.mProfileImage)
                        android.util.Log.d(TAG, "onDataChange: profile_image = " + singleSnapshot.getValue(User::class.java).getProfile_image())
                    }
                }

                fun onCancelled(databaseError: DatabaseError?) {}
            })
            holder.mTrash.setOnClickListener(android.view.View.OnClickListener { v: android.view.View? ->
                if (getItem(position).getCreator_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    android.util.Log.d(TAG, "onClick: asking for permission to delete icon.")
                    (mContext as ChatActivity).showDeleteChatroomDialog(getItem(position).getChatroom_id())
                } else {
                    android.widget.Toast.makeText(mContext, "You didn't create this chatroom", android.widget.Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: java.lang.NullPointerException) {
            android.util.Log.e(TAG, "getView: NullPointerException: ", e.cause)
        }
        return convertView
    }

    companion object {
        private const val TAG = "ChatroomListAdapter"
    }

    init {
        mContext = context
        mLayoutResource = resource
        mInflater = context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater
    }
}