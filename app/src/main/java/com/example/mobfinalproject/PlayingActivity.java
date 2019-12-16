package com.example.mobfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/* NOTES:
    * flow is as follows:
    * there are 10 questions per category: this is CONSTANT
    * at Start (Activity), questions (acc. to categoryID) are loaded from Common.class, randomized, then loaded
    * RIGHT answer -> next question -> loop until finish -> stop game at finish
    * WRONG answer at ANY point before finish -> stop game
 */

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; //1 second
    final static long TIMEOUT = 10000; // 10 seconds
    final static int TOTALQUESTION = 10; //constant, always 10 questions

    CountDownTimer timer;
    int progressValue = 0;
    int index = 0, //which question you are right now
        score = 0,  //current player score
        thisQuestion = 0, //what question you're in right now
        correctAnswer; //number of correct answers

    ProgressBar progressBar;
    Button btnAnsA, btnAnsB, btnAnsC, btnAnsD;
    TextView txtScore, txtTotalQuestion, txtQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        //TextViews
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtTotalQuestion = (TextView) findViewById(R.id.txtTotalQuestion);
        txtQuestion= (TextView) findViewById(R.id.txtQuestion);

        //ProgressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Buttons
        btnAnsA = (Button) findViewById(R.id.btnAnsA);
        btnAnsB = (Button) findViewById(R.id.btnAnsB);
        btnAnsC = (Button) findViewById(R.id.btnAnsC);
        btnAnsD = (Button) findViewById(R.id.btnAnsD);

        btnAnsA.setOnClickListener(this);
        btnAnsB.setOnClickListener(this);
        btnAnsC.setOnClickListener(this);
        btnAnsD.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        timer.cancel();
        //may mga tanong pa
        if (index < TOTALQUESTION){
            Button clickedButton = (Button)v;

            //tama button(i.e. choice) na pinili
            if (clickedButton.getText().toString().equals(Common.questionList.get(index).getCorrectAnswer())){
                score += 10;
                correctAnswer++;
                showQuestion(++index);

            } else { //mali sagot na pinili
                Intent intent = new Intent(this, EndActivity.class);

                Bundle data = new Bundle();
                data.putInt("SCORE", score);
                data.putInt("TOTAL", TOTALQUESTION);
                data.putInt("CORRECT", correctAnswer);

                intent.putExtras(data);
                startActivity(intent);
                finish();
            }

            txtScore.setText(String.format("%d", score));
        }
    }

    private void showQuestion(int i) {
        if (i < TOTALQUESTION){ //may tanong pa
            thisQuestion++;
            txtTotalQuestion.setText(String.format("%d / %d", thisQuestion, TOTALQUESTION));
            progressBar.setProgress(0);
            progressValue = 0;

            //populate textView (Q) and buttons (A) with appropriate values based on questionList index
            txtQuestion.setText(Common.questionList.get(i).getQuestion());
            btnAnsA.setText(Common.questionList.get(i).getAnswerA());
            btnAnsB.setText(Common.questionList.get(i).getAnswerB());
            btnAnsC.setText(Common.questionList.get(i).getAnswerC());
            btnAnsD.setText(Common.questionList.get(i).getAnswerD());

            //start the timer again
            timer.start();
        } else{ //wala nang natitirang mga tanong
            Intent intent = new Intent(this, EndActivity.class);

            Bundle data = new Bundle();
            data.putInt("SCORE", score);
            data.putInt("TOTAL", TOTALQUESTION);
            data.putInt("CORRECT", correctAnswer);

            intent.putExtras(data);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        timer = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                timer.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}
