package com.example.wwequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class LevelChooser extends AppCompatActivity {

    String username;

    //making use of permissions here
    public boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

    public void startEasyLevel(View view){
        try {
            if(isConnected()) {
                Intent intent = new Intent(this, EasyQuiz.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMediumLevel(View view){
        try {
            if(isConnected()) {
                Intent intent = new Intent(this, ImageQuiz.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startHardLevel(View view){
        try {
            if(isConnected()) {
                Intent intent = new Intent(this, AudioQuiz.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = getIntent().getStringExtra("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_chooser);
    }
}
