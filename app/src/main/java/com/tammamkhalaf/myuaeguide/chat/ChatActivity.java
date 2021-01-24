package com.tammamkhalaf.myuaeguide.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.tammamkhalaf.myuaeguide.chat.utility.ChatroomListAdapter;
import com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.PhoneAuthActivity;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.Chatroom;
import com.tammamkhalaf.myuaeguide.databases.firebase.models.User;
import com.tammamkhalaf.myuaeguide.databases.firebase.storage.Utility.UniversalImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    //widgets
    private ListView mListView;
    private FloatingActionButton mFob;


    //vars
    private ArrayList<Chatroom> mChatrooms;
    private ChatroomListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);
        mListView = findViewById(R.id.listView);
        mFob = findViewById(R.id.fob);

        init();

        //initImageLoader();
    }

    /**
     * init universal image loader
     */
//    private void initImageLoader(){
//        UniversalImageLoader imageLoader = new UniversalImageLoader(ChatActivity.this);
//        ImageLoader.getInstance().init(imageLoader.getConfig());
//    }

    public void init(){

        getChatrooms();

        mFob.setOnClickListener(view -> {
            NewChatroomDialog dialog = new NewChatroomDialog();
            dialog.show(getSupportFragmentManager(), getString(R.string.dialog_new_chatroom));
        });
    }

    private void setupChatroomList(){
        Log.d(TAG, "setupChatroomList: setting up chatroom listview");
        mAdapter = new ChatroomListAdapter(ChatActivity.this, R.layout.layout_chatroom_listitem, mChatrooms);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d(TAG, "onItemClick: selected chatroom: " + mChatrooms.get(i).toString());
            Intent intent = new Intent(ChatActivity.this, ChatroomActivity.class);
            intent.putExtra(getString(R.string.intent_chatroom), mChatrooms.get(i));
            startActivity(intent);
        });

    }

    private void getChatrooms(){
        Log.d(TAG, "getChatrooms: retrieving chatrooms from firebase database.");
        mChatrooms = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child(getString(R.string.dbnode_chatrooms));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot:  dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found chatroom: "
                            + singleSnapshot.getValue());

                    Chatroom chatroom = new Chatroom();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                    chatroom.setChatroom_id(objectMap.get(getString(R.string.field_chatroom_id)).toString());
                    chatroom.setChatroom_name(objectMap.get(getString(R.string.field_chatroom_name)).toString());
                    chatroom.setCreator_id(objectMap.get(getString(R.string.field_creator_id)).toString());
                    chatroom.setSecurity_level(objectMap.get(getString(R.string.field_security_level)).toString());


//                    chatroom.setChatroom_id(singleSnapshot.getValue(Chatroom.class).getChatroom_id());
//                    chatroom.setSecurity_level(singleSnapshot.getValue(Chatroom.class).getSecurity_level());
//                    chatroom.setCreator_id(singleSnapshot.getValue(Chatroom.class).getCreator_id());
//                    chatroom.setChatroom_name(singleSnapshot.getValue(Chatroom.class).getChatroom_name());

                    //get the chatrooms messages
                    ArrayList<ChatMessage> messagesList = new ArrayList<>();
                    for(DataSnapshot snapshot: singleSnapshot
                            .child(getString(R.string.field_chatroom_messages)).getChildren()){
                        ChatMessage message = new ChatMessage();
                        message.setTimestamp(snapshot.getValue(ChatMessage.class).getTimestamp());
                        message.setUser_id(snapshot.getValue(ChatMessage.class).getUser_id());
                        message.setMessage(snapshot.getValue(ChatMessage.class).getMessage());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                .child("username").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                String username = snapshot.getValue(String.class);
                                message.setName(username);
                                Log.d(TAG, "onDataChange: username "+snapshot.getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                        FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                .child("profile_image").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                String imageUrl = snapshot.getValue(String.class);
                                message.setProfile_image(imageUrl);
                                Log.d(TAG, "onDataChange: image "+ snapshot.getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        messagesList.add(message);
                    }
                    chatroom.setChatroom_messages(messagesList);
                    mChatrooms.add(chatroom);
                }
                setupChatroomList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showDeleteChatroomDialog(String chatroomId){
        DeleteChatroomDialog dialog = new DeleteChatroomDialog();
        Bundle args = new Bundle();
        args.putString(getString(R.string.field_chatroom_id), chatroomId);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_delete_chatroom));
    }

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

            Intent intent = new Intent(ChatActivity.this, PhoneAuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }


}












