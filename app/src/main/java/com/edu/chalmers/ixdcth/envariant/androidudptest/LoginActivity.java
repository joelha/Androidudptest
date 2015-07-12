package com.edu.chalmers.ixdcth.envariant.androidudptest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {

    private static final String DEBUG_TAG = "DEBUG";

    //Strings for checking stored password.
    private static final String ADMIN = "admin";
    private static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String password = getSharedPreferences(ADMIN, MODE_PRIVATE).getString(PASSWORD, "0");
                Log.d(DEBUG_TAG, "pass: " + password);
                Log.d(DEBUG_TAG, "pass edit: " + passwordEditText.getText().toString());

                if(passwordEditText.getText().toString().equals(password)) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
