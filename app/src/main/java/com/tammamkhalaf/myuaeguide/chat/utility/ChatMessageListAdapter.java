package com.tammamkhalaf.myuaeguide.chat.utility;

import android.content.Context;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tammamkhalaf.myuaeguide.R;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by User on 9/18/2017.
 */

public class ChatMessageListAdapter extends ArrayAdapter<ChatMessage> {

    private static final String TAG = "ChatMessageListAdapter";

    private int mLayoutResource;
    private Context mContext;

    public ChatMessageListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ChatMessage> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutResource = resource;
    }

    public static class ViewHolder {
        TextView name, message;
        ImageView mProfileImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.name);
            holder.message = convertView.findViewById(R.id.message);
            holder.mProfileImage = convertView.findViewById(R.id.profile_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.name.setText("");
            holder.message.setText("");
        }

        try {
            holder.message.setText(getItem(position).getMessage());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.person);
            requestOptions.circleCropTransform();
            requestOptions.transforms( new RoundedCorners(300));

            //ImageLoader.getInstance().displayImage(getItem(position).getProfile_image(),holder.mProfileImage);
            Glide.with(holder.mProfileImage.getContext())
                    .load(getItem(position).getProfile_image())
                    .circleCrop()
                    .placeholder(R.drawable.person)
                    .apply(requestOptions)
                    .into(holder.mProfileImage);

            Log.d(TAG, "getView:image  " + getItem(position).getProfile_image());
            Log.d(TAG, "getView:message " + getItem(position).getMessage());
            Log.d(TAG, "getView:timestamp " + getItem(position).getTimestamp());
            Log.d(TAG, "getView:user_id " + getItem(position).getUser_id());
            Log.d(TAG, "getView:name " + getItem(position).getName());
            Log.d(TAG, "getView:class " + getItem(position).getClass());

            holder.name.setText(getItem(position).getName());


        } catch (NullPointerException e) {
            Log.e(TAG, "getView: NullPointerException: ", e.getCause());
        }

        return convertView;
    }

}

















