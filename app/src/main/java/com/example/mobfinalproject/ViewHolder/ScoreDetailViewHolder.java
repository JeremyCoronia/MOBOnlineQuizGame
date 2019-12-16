package com.example.mobfinalproject.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobfinalproject.R;

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder {

    public TextView scoreName, score;

    public ScoreDetailViewHolder(@NonNull View itemView) {
        super(itemView);

        scoreName = (TextView) itemView.findViewById(R.id.scoreName);
        score = (TextView) itemView.findViewById(R.id.score);
    }
}
