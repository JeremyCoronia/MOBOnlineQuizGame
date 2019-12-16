package com.example.mobfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Random;

public class StartActivity extends AppCompatActivity {

    Button btnPlay, btnBack;

    //firebase component
    FirebaseDatabase questionsDatabase;
    DatabaseReference questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnBack = (Button) findViewById(R.id.btnBack);

        questionsDatabase = FirebaseDatabase.getInstance();
        questions = questionsDatabase.getReference("Questions");

        loadQuestions(Common.categoryID);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, PlayingActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadQuestions(String categoryID) {

        //clear before (re)populating
        if (Common.questionList.size() > 0){
            Common.questionList.clear();
        }

        //populate list with questions corresponding to CATEGORY ID (e.g. 01).
        questions.orderByChild("CategoryID").equalTo(categoryID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Question question = postSnapshot.getValue(Question.class);
                    Common.questionList.add(question);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //randomize the list
        Collections.shuffle(Common.questionList);
    }
}
