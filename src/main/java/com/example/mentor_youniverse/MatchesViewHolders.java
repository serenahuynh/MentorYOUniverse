package com.example.mentor_youniverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentor_youniverse.Chat.ChatActivity;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView myMatchID, myMatchName;
    public ImageView myMatchImage;

    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        myMatchID = (TextView) itemView.findViewById(R.id.Matchid);
        myMatchName = (TextView) itemView.findViewById(R.id.MatchName);

        myMatchImage = (ImageView) itemView.findViewById(R.id.MatchImage);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId", myMatchID.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);

    }
}
