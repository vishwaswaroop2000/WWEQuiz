package com.example.wwequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String username;

    public void startGame(View view){
        Intent intent = new Intent(this,ImageQuiz.class);
        startActivity(intent);
    }
    public void setUsernameInActivity(String name) {
            TextView userWelcomeText = (TextView) findViewById(R.id.welcomer);
            userWelcomeText.setText("Welcome " + name+"!");
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToProfileViewer(View view){
        Log.i("G","oing");
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void setImage(int imageCode){
        ImageView wweLogo = (ImageView) findViewById(R.id.imageView);
        wweLogo.setImageResource(imageCode);
    }

    public void changeLogo(View view){
        String resourceCode = view.getTag().toString();
        ImageView wweLogo = (ImageView) findViewById(R.id.imageView);
        switch (resourceCode){
            case "1":
                sharedPreferences.edit().putString(username,"1").apply();
                setImage(R.drawable.wwe_1);
                break;
            case "2":
                sharedPreferences.edit().putString(username,"2").apply();
                setImage(R.drawable.wwe_2);
                break;
            case "3":
                sharedPreferences.edit().putString(username,"3").apply();
                setImage(R.drawable.wwe_3);
                break;
            default:
                throw new Error("Image not found");
        }
    }

    public void changeLogoBasedOnPreference(String preference){
        switch (preference){
            case "1":
                setImage(R.drawable.wwe_1);
                break;
            case "2":
                setImage(R.drawable.wwe_2);
                break;
            case "3":
                setImage(R.drawable.wwe_3);
                break;
            default:
                throw new Error("Image not found");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        username = getIntent().getStringExtra("username");
        sharedPreferences = this.getSharedPreferences("com.example.wwequiz.sharedpreferences",MODE_PRIVATE);
        changeLogoBasedOnPreference(sharedPreferences.getString(username,"1"));
        setUsernameInActivity(username);
    }
}
