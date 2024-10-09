package com.example.pratapsandroidlab4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the data from the saved share preferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        //

        Log.w("Main Activity","On create");
        Button loginButton=(Button)findViewById(R.id.loginButton);
        EditText emailEditText=(EditText) findViewById(R.id.editTextTextEmailAddress);

        emailEditText.setText(emailAddress );


        //1- create SharedPreferences.Editor object
        SharedPreferences.Editor editor = prefs.edit();

        loginButton.setOnClickListener(view -> {
            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);

            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );

            //2. save the data into my device memory
            editor.putString("LoginName", emailEditText.getText().toString());
            editor.apply();


            startActivity( nextPage);
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.w("Main Activity","on Start");

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.w("Main Activity","on Resume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.w("Main Activity","on Pause");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.w("Main Activity","on Stop");

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.w("Main Activity","on Destroy");
    }





}