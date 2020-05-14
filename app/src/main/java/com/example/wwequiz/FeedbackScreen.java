package com.example.wwequiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackScreen extends AppCompatActivity {

    private HelperForUserDB helperForUserDB;
    private User user;
    private int correctanswers;
    private int wronganswers;
    private int level;
    private String username;
    private TextView congradulator;
    private TextView message;
    private TextView rightNumber;
    private TextView wrongNumber;
    private RatingBar ratingBar;

    public void goToMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateHighScores(){
        switch (level){
            case 0:
                if(user.getHighScoreInEasyLevel()<correctanswers){
                    user.setHighScoreInEasyLevel(correctanswers);
                }
                break;
            case 1:
                if(user.getHighScoreInMediumLevel()<correctanswers){
                    user.setHighScoreInMediumLevel(correctanswers);
                }
                break;
            case 2:
                if(user.getHighScoreInHardLevel()<correctanswers){
                    user.setHighScoreInHardLevel(correctanswers);
                }
                break;
        }
    }

    private void updateLevels(){
        if(user.getLevel()==0 && level==0 && correctanswers > Math.ceil(0.33*(correctanswers+wronganswers))){
            user.setLevel(level+1);
            congradulator.setText("Congradulations!!!");
            message.setText("You have unlocked - Medium Level!");

        }
        if(user.getLevel()==1 && level==1 && correctanswers > Math.ceil(0.33*(correctanswers+wronganswers))){
            user.setLevel(level+1);
            congradulator.setText("Congradulations!!!");
            message.setText("You have unlocked - HardLevel!");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_screen);
        username=getIntent().getStringExtra("username");
        Log.i("asd",username);
        level = getIntent().getIntExtra("level",123);
        correctanswers = getIntent().getIntExtra("score",123);
        wronganswers = 15-correctanswers;
        helperForUserDB = new HelperForUserDB(this);
        user = helperForUserDB.getUser(getIntent().getStringExtra("username"));
        ratingBar = findViewById(R.id.rating);
        rightNumber = findViewById(R.id.textView5);
        wrongNumber = findViewById(R.id.textView6);
        congradulator = findViewById(R.id.Congradulator);
        message = findViewById(R.id.message);
        ratingBar.setRating(3);
        rightNumber.setText("Number of correct answers: "+correctanswers);
        wrongNumber.setText("Number of incorrect answers: "+wronganswers);
        updateLevels();
        updateHighScores();
        helperForUserDB.updateUserDetails(user);
        //print unlocked message
    }
}
