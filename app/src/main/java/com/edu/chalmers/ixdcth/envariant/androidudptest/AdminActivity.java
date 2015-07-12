package com.edu.chalmers.ixdcth.envariant.androidudptest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AdminActivity extends ActionBarActivity {

    //Strings for checking previously stored host and port values.
    private static final String PORT = "host";
    private static final String HOST = "port";
    private static final String ADRESSES = "addresses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final EditText hostEditText = (EditText) findViewById(R.id.editTextHost);
        final EditText portEditText = (EditText) findViewById(R.id.editTextPort);

        SharedPreferences sharedPreferences = getSharedPreferences(ADRESSES, MODE_PRIVATE);
        //show current host and port
        hostEditText.setText(sharedPreferences.getString(HOST, ""));
        portEditText.setText(sharedPreferences.getString(PORT, ""));

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Host and port saved", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getSharedPreferences(ADRESSES, MODE_PRIVATE).edit();

                //TODO check if changed, otherwise, and if not, don't save
                editor.putString(HOST, hostEditText.getText().toString());
                editor.putString(PORT, portEditText.getText().toString());
                editor.apply();
            }
        });
    }
}
