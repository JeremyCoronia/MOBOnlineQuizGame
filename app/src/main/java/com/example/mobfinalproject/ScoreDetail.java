package com.example.mobfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobfinalproject.ViewHolder.ScoreDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreDetail extends AppCompatActivity {

    //firebase component
    FirebaseDatabase detailsDatabase;
    DatabaseReference details;

    RecyclerView scoreList;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Score, ScoreDetailViewHolder> adapter;

    Button btnBack;
    String viewUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);

        btnBack = (Button) findViewById(R.id.btnBack);

        //firebase
        detailsDatabase = FirebaseDatabase.getInstance();
        details = detailsDatabase.getReference("Score");

        //recycler view
        scoreList = (RecyclerView) findViewById(R.id.scoreList);
        scoreList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        scoreList.setLayoutManager(layoutManager);

        if (getIntent() != null){
            viewUser = getIntent().getStringExtra("viewUser");
        }

        if (!viewUser.isEmpty()){
            loadScoreDetail(viewUser);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadScoreDetail(String viewUser) {
        adapter = new FirebaseRecyclerAdapter<Score, ScoreDetailViewHolder>(
                Score.class,
                R.layout.score_detail_layout,
                ScoreDetailViewHolder.class,
                details.orderByChild("user").equalTo(viewUser)
        ) {
            @Override
            protected void populateViewHolder(ScoreDetailViewHolder scoreDetailViewHolder, Score score, int i) {
                scoreDetailViewHolder.scoreName.setText(score.getCategoryName());
                scoreDetailViewHolder.score.setText(score.getScore());
            }
        };

        adapter.notifyDataSetChanged();
        scoreList.setAdapter(adapter);

    }
}
