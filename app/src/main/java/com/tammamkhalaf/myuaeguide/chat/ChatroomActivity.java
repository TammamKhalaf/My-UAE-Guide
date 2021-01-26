package com.tammamkhalaf.myuaeguide.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.PendingResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tammamkhalaf.myuaeguide.R;
import com.tammamkhalaf.myuaeguide.chat.utility.ChatMessageListAdapter;
import com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.PhoneAuthActivity;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.Chatroom;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.User;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ChatroomActivity extends AppCompatActivity {

    private static final String TAG = "ChatroomActivity";

    //firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mMessagesReference;

    //widgets
    private TextView mChatroomName;
    private ListView mListView;
    private EditText mMessage;
    private ImageView mCheckmark;

    //vars
    private Chatroom mChatroom;
    private List<ChatMessage> mMessagesList;
    private ChatMessageListAdapter mAdapter;


    String username,profile_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chatroom);

        mChatroomName = findViewById(R.id.text_chatroom_name);
        mListView = findViewById(R.id.listView);
        mMessage = findViewById(R.id.input_message);
        mCheckmark = findViewById(R.id.checkmark);

        Log.d(TAG, "onCreate: started.");
        mMessagesList = new ArrayList<>();
        username = ANONYMOUS;
        setupFirebaseAuth();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child("Users").orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: " + singleSnapshot.getValue(User.class).toString());
                    User user = singleSnapshot.getValue(User.class);
                    username=user.getUsername();
                    profile_image=user.getProfile_image();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getChatroom();
        init();
        hideSoftKeyboard();
    }

    public static final String ANONYMOUS = "anonymous";

    private void init(){

        mMessage.setOnClickListener(view -> {
            mListView.setSelection(mAdapter.getCount() - 1); //scroll to the bottom of the list
        });

        mCheckmark.setOnClickListener(view -> {

            if(!mMessage.getText().toString().equals("")){
                String message = mMessage.getText().toString();
                Log.d(TAG, "onClick: sending new message: " + message);

                //create the new message object for inserting
                ChatMessage newMessage = new ChatMessage();
                newMessage.setMessage(message);
                newMessage.setTimestamp(getTimestamp());
                newMessage.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

                newMessage.setName(username);
                newMessage.setProfile_image(profile_image);
                //get a database reference
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child(getString(R.string.dbnode_chatrooms))
                        .child(mChatroom.getChatroom_id())
                        .child(getString(R.string.field_chatroom_messages));

                //create the new messages id
                String newMessageId = reference.push().getKey();

                //insert the new message into the chatroom
                reference.child(newMessageId).setValue(newMessage);

                //clear the EditText
                mMessage.setText("");

                //refresh the messages list? Or is it done by the listener??
            }

        });
    }

    /**
     * Retrieve the chatroom name using a query
     */
    private void getChatroom(){
        Log.d(TAG, "getChatroom: getting selected chatroom details");

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.intent_chatroom))){
            Chatroom chatroom = intent.getParcelableExtra(getString(R.string.intent_chatroom));
            Log.d(TAG, "getChatroom: chatroom: " + chatroom.toString());
            mChatroom = chatroom;
            mChatroomName.setText(mChatroom.getChatroom_name());

            enableChatroomListener();
        }
    }


    private void getChatroomMessages(){
        mMessagesList = new ArrayList<>();
        if(mMessagesList.size() > 0){
            mMessagesList.clear();
            mAdapter.clear();
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.dbnode_chatrooms))
                .child(mChatroom.getChatroom_id())
                .child(getString(R.string.field_chatroom_messages));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    
                    DataSnapshot snapshot = singleSnapshot;
                    Log.d(TAG, "onDataChange: found chatroom message: "
                            + singleSnapshot.getValue());
                    try {//need to catch null pointer here because the initial welcome message to the
                        //chatroom has no user id
                        ChatMessage message = new ChatMessage();
                        String userId = snapshot.getValue(ChatMessage.class).getUser_id();
                        if(userId != null){ //check and make sure it's not the first message (has no user id)
                            message.setMessage(snapshot.getValue(ChatMessage.class).getMessage());
                            message.setUser_id(snapshot.getValue(ChatMessage.class).getUser_id());
                            message.setTimestamp(snapshot.getValue(ChatMessage.class).getTimestamp());
                            message.setProfile_image(snapshot.getValue(ChatMessage.class).getProfile_image());// i have add this
                            message.setName(snapshot.getValue(ChatMessage.class).getName());
                            mMessagesList.add(message);
                        }else{
                            message.setMessage(snapshot.getValue(ChatMessage.class).getMessage());
                            message.setTimestamp(snapshot.getValue(ChatMessage.class).getTimestamp());
                            mMessagesList.add(message);
                        }

                    } catch (NullPointerException e) {
                        Log.e(TAG, "onDataChange: NullPointerException: " + e.getMessage());
                    }
                }
                //query the users node to get the profile images and names
                getUserDetails();
                initMessagesList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUserDetails(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        for(int i = 0; i < mMessagesList.size(); i++) {
            Log.d(TAG, "onDataChange: searching for userId: " + mMessagesList.get(i).getUser_id());
            final int j = i;
            if(mMessagesList.get(i).getUser_id() != null){
                Query query = reference.child(getString(R.string.dbnode_users)).orderByKey().equalTo(mMessagesList.get(i).getUser_id());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //DataSnapshot singleSnapshot = dataSnapshot.getChildren().iterator().next();
                        for(DataSnapshot singleSnapshot:dataSnapshot.getChildren()) {

                            Log.d(TAG, "onDataChange: found user id: " + singleSnapshot.getValue(User.class).getUser_id());
                            mMessagesList.get(j).setProfile_image(singleSnapshot.getValue(User.class).getProfile_image());
                            mMessagesList.get(j).setName(singleSnapshot.getValue(User.class).getUsername());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

    }

    private void initMessagesList(){
        mAdapter = new ChatMessageListAdapter(ChatroomActivity.this, R.layout.layout_chatmessage_listitem, mMessagesList);
        mListView.setAdapter(mAdapter);
        mListView.setSelection(mAdapter.getCount() - 1); //scroll to the bottom of the list
    }

    /**
     * Return the current timestamp in the form of a string
     * @return
     */
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
            ----------------------------- Firebase setup ---------------------------------
    */

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    private void checkAuthenticationState(){
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.");

            Intent intent = new Intent(ChatroomActivity.this, PhoneAuthActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());


            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
                Intent intent = new Intent(ChatroomActivity.this, PhoneAuthActivity.class);
                startActivity(intent);
            }
            // ...
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMessagesReference.removeEventListener(mValueEventListener);
    }

    ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            getChatroomMessages();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void enableChatroomListener(){
         /*
            ---------- Listener that will watch the 'chatroom_messages' node ----------
         */
        mMessagesReference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.dbnode_chatrooms))
                .child(mChatroom.getChatroom_id())
                .child(getString(R.string.field_chatroom_messages));

        mMessagesReference.addValueEventListener(mValueEventListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}






















