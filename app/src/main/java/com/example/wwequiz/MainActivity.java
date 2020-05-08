package com.example.wwequiz;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import com.example.wwequiz.HelperForUserDB;

public class MainActivity extends AppCompatActivity {

    HelperForUserDB helperForUserDB;
    ListView listView;

    @Override
    public void onResume(){
        super.onResume();
        fillUsers();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helperForUserDB = new HelperForUserDB(this);
        fillUsers();

    }

    public void fillUsers(){
        ArrayList<User> users = helperForUserDB.getUsers();
        Log.i("Length", String.valueOf(users.size()));
        if(users.size()>0) {
            listView = (ListView) findViewById(R.id.userListView);
            final ArrayList<String> userNames = new ArrayList<>();
            for (User user:users) {
                userNames.add(user.getName());
            }
            ArrayAdapter<String> userArrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,userNames);
            listView.setAdapter(userArrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    try {
                        goToWelcomeActivity(userNames.get(position));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public boolean isUserNameValid(String username) throws IOException {
        if(helperForUserDB.getUser(username)!=null){
            return false;
        };
        return true;
    }


    public void goToWelcomeActivity(String name) throws IOException {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }

    //maybe change user name
    public void setUserName(View view){

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View viewForDialog = getLayoutInflater().inflate(R.layout.username_dialog,null);
        final EditText username = (EditText) viewForDialog.findViewById(R.id.editText);
        Button goToUserProfileButton = (Button) viewForDialog.findViewById(R.id.button);
        builder.setView(viewForDialog);
        final AlertDialog dialog = builder.create();
        dialog.show();

        goToUserProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                try {
                    if(!name.isEmpty() && isUserNameValid(name)){
                        helperForUserDB.addUser(new User(name));
                        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick();
                        goToWelcomeActivity(name);
                    }
                    else if(name.isEmpty()){
                        Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Name already chosen", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
}