package com.example.mobfinalproject.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobfinalproject.Interface.ItemClickListener;
import com.example.mobfinalproject.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView rankName, rankScore;

    private ItemClickListener itemClickListener;

    public RankingViewHolder(View v){
        super(v);

        rankName = (TextView) v.findViewById(R.id.rankName);
        rankScore = (TextView) v.findViewById(R.id.rankScore);

        v.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.oncClick(v, getAdapterPosition(), false);
    }
}
