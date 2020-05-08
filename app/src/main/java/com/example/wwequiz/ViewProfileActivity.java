package com.example.wwequiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProfileActivity extends AppCompatActivity {

    HelperForUserDB helper;
    User user;

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
        TextView textView = findViewById(R.id.userDetails);
        textView.setText("Name "+user.getName()+", Level"+ user.getLevel()
                +", EasyHs"+ user.getHighScoreInEasyLevel()+"," +
                " MediumHs"+user.getHighScoreInMediumLevel()+
                ", hardHs"+user.getHighScoreInHardLevel());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        helper = new HelperForUserDB(this);
        fillProfile(getIntent().getStringExtra("username"));
    }
}
