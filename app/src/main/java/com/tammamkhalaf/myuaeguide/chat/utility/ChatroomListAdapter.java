package com.tammamkhalaf.myuaeguide.chat.utility;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tammamkhalaf.myuaeguide.R;
import com.tammamkhalaf.myuaeguide.chat.ChatActivity;
import com.tammamkhalaf.myuaeguide.chat.ChatroomActivity;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.Chatroom;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.User;

import org.w3c.dom.Text;

import java.util.List;


/**
 * Created by User on 9/18/2017.
 */

public class ChatroomListAdapter extends ArrayAdapter<Chatroom> {

    private static final String TAG = "ChatroomListAdapter";

    private int mLayoutResource;
    private Context mContext;
    private LayoutInflater mInflater;

    public ChatroomListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Chatroom> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        TextView name, creatorName, numberMessages;
        ImageView mProfileImage, mTrash;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.name);
            holder.creatorName = convertView.findViewById(R.id.creator_name);
            holder.numberMessages = convertView.findViewById(R.id.number_chatmessages);
            holder.mProfileImage = convertView.findViewById(R.id.profile_image);
            holder.mTrash = convertView.findViewById(R.id.icon_trash);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        try{
            //set the chatroom name
            holder.name.setText(getItem(position).getChatroom_name());

            //set the number of chat messages
            String chatMessagesString = String.valueOf(getItem(position).getChatroom_messages().size())
                    + " messages";
            holder.numberMessages.setText(chatMessagesString);

            //get the users details who created the chatroom
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child(mContext.getString(R.string.dbnode_users))
                    .orderByKey()
                    .equalTo(getItem(position).getCreator_id());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot:  dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: Found chat room creator: "
                                + singleSnapshot.getValue(User.class).toString());
                        String createdBy = "created by " + singleSnapshot.getValue(User.class).getUsername();
                        holder.creatorName.setText(createdBy);

                        Log.d(TAG, "onDataChange: "+"created by " + singleSnapshot.getValue(User.class).getUsername());

                        //ImageLoader.getInstance().displayImage(singleSnapshot.getValue(User.class).getProfile_image() , holder.mProfileImage);
                        Glide.with(holder.mProfileImage.getContext())
                                .load(singleSnapshot.getValue(User.class).getProfile_image())
                                .circleCrop()
                                .placeholder(R.drawable.person)
                                .into(holder.mProfileImage);
                        Log.d(TAG, "onDataChange: profile_image = "+singleSnapshot.getValue(User.class).getProfile_image());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            holder.mTrash.setOnClickListener(v -> {
                if(getItem(position).getCreator_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    Log.d(TAG, "onClick: asking for permission to delete icon.");
                    ((ChatActivity)mContext).showDeleteChatroomDialog(getItem(position).getChatroom_id());
                }else{
                    Toast.makeText(mContext, "You didn't create this chatroom", Toast.LENGTH_SHORT).show();
                }

            });

        }catch (NullPointerException e){
            Log.e(TAG, "getView: NullPointerException: ", e.getCause() );
        }

        return convertView;
    }
}