package com.example.mobfinalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mobfinalproject.Interface.ItemClickListener;
import com.example.mobfinalproject.Interface.RankingCallback;
import com.example.mobfinalproject.ViewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RankingFragment extends Fragment {
    View myFragment;

    RecyclerView rankList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking, RankingViewHolder> adapter;

    //firebase component
    FirebaseDatabase scoreDatabase;
    DatabaseReference questionScore, rankingTable;

    int sum = 0;

    public static RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scoreDatabase = FirebaseDatabase.getInstance();
        questionScore = scoreDatabase.getReference("Score");
        rankingTable = scoreDatabase.getReference("Ranking");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        myFragment = inflater.inflate(R.layout.fragment_ranking, container,  false);

        rankList = (RecyclerView) myFragment.findViewById(R.id.rankList);
        layoutManager = new LinearLayoutManager(getActivity());
        rankList.setHasFixedSize(true);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankList.setLayoutManager(layoutManager);

        updateScore(Common.currentUser.getUsername(), new RankingCallback<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                //update the table
                rankingTable.child(ranking.getUsername())
                    .setValue(ranking);

//                showRank();
            }
        });

        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.ranking_layout,
                RankingViewHolder.class,
                rankingTable.orderByChild("score")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder rankingViewHolder, final Ranking ranking, int i) {
                rankingViewHolder.rankName.setText(ranking.getUsername());
                rankingViewHolder.rankScore.setText(String.valueOf(ranking.getScore()));

                rankingViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void oncClick(View view, int position, boolean isLongClick) {
                        Intent scoreIntent = new Intent(getActivity(), ScoreDetail.class);
                        scoreIntent.putExtra("viewUser", ranking.getUsername());
                        startActivity(scoreIntent);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        rankList.setAdapter(adapter);

        return myFragment;
    }

    private void updateScore(final String username, final RankingCallback<Ranking> callback) {
        questionScore.orderByChild("user").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            Score score = data.getValue(Score.class);
                            sum += Integer.parseInt(score.getScore());
                        }
                        Ranking rank = new Ranking(username, sum);
                        callback.callBack(rank);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
