package com.example.mentor_youniverse.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mentor_youniverse.MatchesAdapter;
import com.example.mentor_youniverse.MatchesObject;
import com.example.mentor_youniverse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myChatAdapter;
    private RecyclerView.LayoutManager myChatLayoutManager;

    private EditText mySendEditText;

    private Button mySendButton;

    private String currentUserId, matchId, chatId;

    DatabaseReference myDatabaseUser, myDatabaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        matchId=getIntent().getExtras().getString("matchId");

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("connections").child("matches").child(matchId).child("ChatId");
        myDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        getChatId();;

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(false);
        myChatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        myRecyclerView.setLayoutManager(myChatLayoutManager);
        myChatAdapter = new ChatAdapter(getDataSetChat(), ChatActivity.this);
        myRecyclerView.setAdapter(myChatAdapter);

        mySendEditText = findViewById(R.id.message);
        mySendButton = findViewById(R.id.send);

        mySendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String sendMessageText = mySendEditText.getText().toString();

        if(!sendMessageText.isEmpty()){
            DatabaseReference newMessageDb = myDatabaseChat.push();

            Map newMessage = new HashMap();
            newMessage.put("createdByUser", currentUserId);
            newMessage.put("text", sendMessageText);

            newMessageDb.setValue(newMessage);
        }
        mySendEditText.setText(null);
    }

    private void getChatId(){
        myDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    chatId = snapshot.getValue().toString();
                    myDatabaseChat = myDatabaseChat.child(chatId);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getChatMessages() {
        myDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String message = null;
                    String createdByUser = null;

                    if (snapshot.child("text").getValue() != null){
                        message = snapshot.child("text").getValue().toString();
                    }
                    if (snapshot.child("createdByUser").getValue() != null){
                        createdByUser = snapshot.child("createdByUser").getValue().toString();
                    }

                    if (message!=null && createdByUser!=null){
                        Boolean currentUserBoolean = false;
                        if (createdByUser.equals(currentUserId)){
                            currentUserBoolean = true;
                        }
                        ChatObject newMessage = new ChatObject(message, currentUserBoolean);
                        resultsChat.add(newMessage);
                        myChatAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private ArrayList<ChatObject> resultsChat = new ArrayList<ChatObject>();
    private List<ChatObject> getDataSetChat() {
        return resultsChat;
    }
}