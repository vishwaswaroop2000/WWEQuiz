package com.example.wwequiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AudioQuiz extends AppCompatActivity {


    private int questionNumber;
    private String answer;
    private String username;
    private int correct_answers=0;
    private DatabaseReference databaseReference;
    private ArrayList<Question> questions;
    private ImageView imageView;
    private TextView imageQuestion;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private MediaPlayer mediaPlayer;

    public void reflectAnswerAndGoNext(View view) {
        mediaPlayer.stop();
        Button selectedOption = (Button) view;
        if (selectedOption.getText().toString().equals(answer)) {
            correct_answers=correct_answers+1;
            Toast.makeText(this, "correct!", Toast.LENGTH_SHORT).show();
            //calcuate here
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        if(questionNumber<=14) {
            getQuestion(questionNumber);
        }
        else{
            Intent intent = new Intent(this, FeedbackScreen.class);
            intent.putExtra("username", username);
            intent.putExtra("level",2);
            intent.putExtra("score",correct_answers);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_quiz);
        questions = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        imageView = findViewById(R.id.wrestlersPlace);
        imageQuestion = findViewById(R.id.imageQuestion);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        databaseReference = FirebaseDatabase.getInstance().getReference("Level3");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    questions.add(question);
                }
                Log.i("Then",questions.get(0).getAnswer());
                Collections.shuffle(questions);
                getQuestion(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AudioQuiz.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getQuestion(int number) {
        Question question = questions.get(number);
        questionNumber = number + 1;
        answer = question.getAnswer();

        //extract options and shuffle them
        ArrayList<String> options = new ArrayList<>();
        options.add(question.getOption1());
        options.add(question.getOption2());
        options.add(question.getOption3());
        options.add(question.getOption4());
        Collections.shuffle(options);

        imageQuestion.setText(question.getQuestion());
        //this is where image and button values will be changed
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(question.getAudioURI());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        option4.setText(options.get(3));
    }


}
