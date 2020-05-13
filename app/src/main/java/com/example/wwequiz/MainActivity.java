package com.example.wwequiz;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private ShareActionProvider shareActionProvider;
    HelperForUserDB helperForUserDB;
    Switch serviceSwitch;
    SharedPreferences sharedPreferences;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<User> users;

    @Override
    public void onResume(){
        super.onResume();
        fillUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quiz_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.shareaction);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("Hey there! Think you're the best fan of professional wreslting?" +
                "Try this app to test your wrestler guessing skills! It has 3 levels and is challenging too!");
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String message){
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,message);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helperForUserDB = new HelperForUserDB(this);
        fillUsers();
        serviceSwitch = findViewById(R.id.serviceSwitch);
        sharedPreferences = this.getSharedPreferences("com.example.wwequiz.sharedpreferences", MODE_PRIVATE);
        serviceSwitch.setChecked(sharedPreferences.getBoolean("service", true));
        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startService();
                    sharedPreferences.edit().putBoolean("service",true);
                }
                else{
                    stopService();
                    sharedPreferences.edit().putBoolean("service",false);
                }
            }
        });
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, QuizService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, QuizService.class);
        stopService(serviceIntent);
    }

    public void fillUsers(){
        users = helperForUserDB.getUsers();
        Log.i("Length", String.valueOf(users.size()));
        if(users.size()>0) {
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerViewAdapter = new RecyclerViewAdapter(users,this,this);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Toast.makeText(MainActivity.this, users.get(viewHolder.getAdapterPosition()).getName()+" "+
                    String.valueOf(viewHolder.getAdapterPosition()),
                    Toast.LENGTH_SHORT).show();
            helperForUserDB.deleteUser(users.get(viewHolder.getAdapterPosition()).getName());
            users.remove(viewHolder.getAdapterPosition());
            recyclerViewAdapter.notifyDataSetChanged();
        }
    };

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

    public void upload() {
        Intent intent = new Intent(this, Uploader.class);
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


    @Override
    public void recyclerViewListClicked(View v, String name) {
        try {
            goToWelcomeActivity(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}