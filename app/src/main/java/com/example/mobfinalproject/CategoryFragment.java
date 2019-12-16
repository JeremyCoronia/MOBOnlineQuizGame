package com.example.mobfinalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobfinalproject.Interface.ItemClickListener;
import com.example.mobfinalproject.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CategoryFragment extends Fragment {

    View myFragment;
    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;

    //firebase component
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FirebaseDatabase categoryDatabase;
    DatabaseReference categories;

    public static CategoryFragment newInstance(){
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Firebase component
        categoryDatabase = FirebaseDatabase.getInstance();
        categories = categoryDatabase.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        myFragment = inflater.inflate(R.layout.fragment_category, container,  false);

        listCategory = (RecyclerView) myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        loadCategories();
        
        return myFragment;
    }

    private void loadCategories() {
        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories
        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder categoryViewHolder, final Category category, int position) {
                //load names and images to recycler views
                categoryViewHolder.categName.setText(category.getName());
                Picasso.with(getActivity())
                        .load(category.getImage())
                        .into(categoryViewHolder.categImage);

                //expected result: click on any category, take you to the Start Activity page to "PLAY"
                categoryViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void oncClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(getActivity(),String.format("%s|%s", adapter.getRef(position).getKey(), category.getName()) ,Toast.LENGTH_SHORT).show();
                        //start intent to take to Start Activity
                        Intent startGame = new Intent(getActivity(), StartActivity.class);
                        Common.categoryID = adapter.getRef(position).getKey();
                        Common.categoryName = category.getName();
                        startActivity(startGame);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}
