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

    private static final String DEBUG_TAG = "DEBUG";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
