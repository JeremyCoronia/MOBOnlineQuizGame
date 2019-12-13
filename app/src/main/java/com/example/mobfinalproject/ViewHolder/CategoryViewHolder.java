package com.example.mobfinalproject.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobfinalproject.Interface.ItemClickListener;
import com.example.mobfinalproject.R;

//process each item at Recycler Adapter
public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView categImage;
    public TextView categName;

    private ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView){
        super(itemView);

        categImage = (ImageView) itemView.findViewById(R.id.categImage);
        categName = (TextView) itemView.findViewById(R.id.categName);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.oncClick(v, getAdapterPosition(), false);
    }
}
