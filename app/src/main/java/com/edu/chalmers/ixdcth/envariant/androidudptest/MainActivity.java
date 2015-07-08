package com.edu.chalmers.ixdcth.envariant.androidudptest;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class MainActivity extends ActionBarActivity {

    private Thread thread;
    private final String DEBUG_TAG = "DEBUG";
    private MessageHandler messageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    messageHandler = new MessageHandler(5555, InetAddress.getLocalHost());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();

        initButtons();
    }

    private void initButtons() {
        Button video1Button = (Button) findViewById(R.id.button);
        video1Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO send message
                Toast.makeText(getApplicationContext(), "Playing video 1", Toast.LENGTH_SHORT).show();
                messageHandler.sendMessage("hej");
            }
        });

        Button video2Button = (Button) findViewById(R.id.button2);
        video2Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO send message
                Toast.makeText(getApplicationContext(), "Playing video 2", Toast.LENGTH_SHORT).show();
            }
        });

        Button video3Button = (Button) findViewById(R.id.button3);
        video3Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO send message
                Toast.makeText(getApplicationContext(), "Playing video 3", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_admin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
