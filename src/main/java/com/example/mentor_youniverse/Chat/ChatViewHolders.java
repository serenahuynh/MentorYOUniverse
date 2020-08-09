package com.example.mentor_youniverse.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentor_youniverse.R;

public class ChatViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView myMessage;
    public LinearLayout myContainer;
    public ChatViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        myMessage = itemView.findViewById(R.id.message);
        myContainer = itemView.findViewById(R.id.container);
    }

    @Override
    public void onClick(View view) {

    }
}
