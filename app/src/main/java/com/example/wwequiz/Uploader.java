package com.example.wwequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Uploader extends AppCompatActivity {

    private Button button;
    private EditText question;
    private EditText answer;
    private EditText option1;
    private EditText option2;
    private EditText option3;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);
        databaseReference = FirebaseDatabase.getInstance().getReference("/Level2");
        question = findViewById(R.id.editText2);
        answer = findViewById(R.id.editText3);
        option1 = findViewById(R.id.editText4);
        option2 = findViewById(R.id.editText5);
        option3 = findViewById(R.id.editText6);
        button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    private void setTextToEmpty(){
        question.setText("");
        answer.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
    }

    private void uploadFile(){
        Question question = new Question(this.question.getText().toString(),answer.getText().toString(),
                option1.getText().toString(),option2.getText().toString(),option3.getText().toString());
        databaseReference.push().setValue(question).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Uploader.this, "Uploaded", Toast.LENGTH_SHORT).show();
                setTextToEmpty();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Uploader.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
