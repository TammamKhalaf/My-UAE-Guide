package com.tammamkhalaf.myuaeguide.chat.utility

import androidx.annotation.LayoutRes

/**
 * Created by User on 9/18/2017.
 */
class ChatMessageListAdapter(@NonNull context: android.content.Context, @LayoutRes resource: Int, @NonNull objects: List<ChatMessage?>?) : android.widget.ArrayAdapter<ChatMessage?>(context, resource, objects) {
    private val mLayoutResource: Int
    private val mContext: android.content.Context

    class ViewHolder {
        var name: android.widget.TextView? = null
        var message: android.widget.TextView? = null
        var mProfileImage: android.widget.ImageView? = null
    }

    @NonNull
    override fun getView(position: Int, @Nullable convertView: android.view.View?, @NonNull parent: android.view.ViewGroup): android.view.View {
        var convertView: android.view.View? = convertView
        val holder: ViewHolder
        if (convertView == null) {
            val inflater: android.view.LayoutInflater = android.view.LayoutInflater.from(mContext)
            convertView = inflater.inflate(mLayoutResource, parent, false)
            holder = ViewHolder()
            holder.name = convertView.findViewById<android.widget.TextView>(R.id.name)
            holder.message = convertView.findViewById<android.widget.TextView>(R.id.message)
            holder.mProfileImage = convertView.findViewById<android.widget.ImageView>(R.id.profile_image)
            convertView.setTag(holder)
        } else {
            holder = convertView.getTag()
            holder.name.setText("")
            holder.message.setText("")
        }
        try {
            holder.message.setText(getItem(position).getMessage())
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.person)
            RequestOptions.circleCropTransform()
            requestOptions.transforms(RoundedCorners(500))

            //ImageLoader.getInstance().displayImage(getItem(position).getProfile_image(),holder.mProfileImage);
            Glide.with(holder.mProfileImage.getContext())
                    .load(getItem(position).getProfile_image())
                    .circleCrop()
                    .placeholder(R.drawable.person)
                    .apply(requestOptions) //.override(300, 300)
                    .into(holder.mProfileImage)
            android.util.Log.d(TAG, "getView:image  " + getItem(position).getProfile_image())
            android.util.Log.d(TAG, "getView:message " + getItem(position).getMessage())
            android.util.Log.d(TAG, "getView:timestamp " + getItem(position).getTimestamp())
            android.util.Log.d(TAG, "getView:user_id " + getItem(position).getUser_id())
            android.util.Log.d(TAG, "getView:name " + getItem(position).getName())
            android.util.Log.d(TAG, "getView:class " + getItem(position).getClass())
            holder.name.setText(getItem(position).getName())
        } catch (e: java.lang.NullPointerException) {
            android.util.Log.e(TAG, "getView: NullPointerException: ", e.cause)
        }
        return convertView
    }

    companion object {
        private const val TAG = "ChatMessageListAdapter"
    }

    init {
        mContext = context
        mLayoutResource = resource
    }
}