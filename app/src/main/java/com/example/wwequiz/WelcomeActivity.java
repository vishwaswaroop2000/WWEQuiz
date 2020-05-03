package com.example.wwequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

        public void setUsernameInActivity(String name){
        TextView userWelcomeText = (TextView) findViewById(R.id.welcomer);
        userWelcomeText.setText("Welcome, "+name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String username = getIntent().getStringExtra("username");
        setUsernameInActivity(username);
    }
}
