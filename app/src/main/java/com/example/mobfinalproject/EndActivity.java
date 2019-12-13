package com.example.mobfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EndActivity extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtScore, txtQuestions;
    ProgressBar progressFinished;

    //firebase component
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        //firebase component
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Score");

        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtQuestions = (TextView) findViewById(R.id.txtQuestions);
        progressFinished = (ProgressBar) findViewById(R.id.progressFinished);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //get data from firebase
        Bundle data = getIntent().getExtras();
        if (data != null){
            int score = data.getInt("SCORE");
            int totQuestion = data.getInt("TOTAL");
            int correctAnswers = data.getInt("CORRECT");

            txtScore.setText(String.format("SCORE: %d", score));
            txtQuestions.setText(String.format("Correct Answers: %d / %d", correctAnswers, totQuestion));

            progressFinished.setMax(totQuestion);
            progressFinished.setProgress(correctAnswers);

            //upload to FB-DB
            reference.child(String.format("%s_%s", Common.currentUser.getUsername(),
                                                            Common.categoryID))
                    .setValue(new Score(String.format("%s_%s", Common.currentUser.getUsername(), Common.categoryID),
                                        Common.currentUser.getUsername(),
                                        String.valueOf(score)));
        }
    }
}
