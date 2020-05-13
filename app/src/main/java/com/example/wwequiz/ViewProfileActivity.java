package com.example.wwequiz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ViewProfileActivity extends AppCompatActivity {

    HelperForUserDB helper;
    User user;
    TextView uname;
    TextView easy;
    TextView medium;
    TextView hard;


    public void resetScores(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Doing this would reset your high scores and level.")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        user.setHighScoreInEasyLevel(0);
                        user.setHighScoreInMediumLevel(0);
                        user.setHighScoreInHardLevel(0);
                        user.setLevel(2);
                        helper.updateUserDetails(user);
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViewProfileActivity.this, "Reset Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fillProfile(String name){
        user = helper.getUser(name);
        uname.setText("Name -"+user.getName());
        easy.setText("High Score in Easy Level -"+user.getHighScoreInEasyLevel());
        medium.setText("High Score in Medium Level -"+user.getHighScoreInMediumLevel());
        hard.setText("High Score in Hard Level -"+user.getHighScoreInHardLevel());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        helper = new HelperForUserDB(this);
        uname = findViewById(R.id.name_user);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);
        fillProfile(getIntent().getStringExtra("username"));

    }
}
