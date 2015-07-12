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

    private static final String DEBUG_TAG = "DEBUG";

    //Strings for checking previously stored host and port values.
    private static final String PORT = "host";
    private static final String HOST = "port";
    private static final String ADRESSES = "addresses";

    //termination character for messages
    private static final String END = "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAddresses();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAddresses();
        Log.d(DEBUG_TAG, "resume!");
    }

    /**
     * checks for previously stored host and port numbers in shared preferences,
     * if found the handler gets uses these, otherwise host address is set to 127.0.0.1 and port to 5555.
     */
    private void loadAddresses() {
        String host = getSharedPreferences(ADRESSES, MODE_PRIVATE).getString(HOST, "127.0.0.1");
        int port = Integer.parseInt(getSharedPreferences(ADRESSES, MODE_PRIVATE).getString(PORT, "5555"));
        MessageHandler handler = MessageHandler.getInstance();
        handler.setHost(host);
        handler.setPort(port);
    }

    public void onClickVideo1(View view) {
        Toast.makeText(getApplicationContext(), "Playing video 1", Toast.LENGTH_SHORT).show();
        new Thread() {
            @Override
            public void run() {
                try {
                    //example sequence
                    MessageHandler.getInstance().sendMessage("start video1" + END);
                    sleep(500);
                    MessageHandler.getInstance().sendMessage("stop video1" + END);
                    MessageHandler.getInstance().sendMessage("start video2" + END);
                    sleep(1200);
                    MessageHandler.getInstance().sendMessage("start fade" + END);
                    sleep(200);
                    MessageHandler.getInstance().sendMessage("stop fade" + END);
                    MessageHandler.getInstance().sendMessage("start video2" + END);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void onClickVideo2(View view) {
        Toast.makeText(getApplicationContext(), "Playing video 2", Toast.LENGTH_SHORT).show();
        new Thread() {
            @Override
            public void run() {
                try {
                    //example sequence
                    MessageHandler.getInstance().sendMessage("start video2" + END);
                    sleep(500);
                    MessageHandler.getInstance().sendMessage("start fade" + END);
                    sleep(1200);
                    MessageHandler.getInstance().sendMessage("stop fade" + END);
                    sleep(200);
                    MessageHandler.getInstance().sendMessage("start fade" + END);
                    sleep(1200);
                    MessageHandler.getInstance().sendMessage("stop fade" + END);
                    MessageHandler.getInstance().sendMessage("stop video2" + END);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void onClickVideo3(View view) {
        Toast.makeText(getApplicationContext(), "Playing video 3", Toast.LENGTH_SHORT).show();
        new Thread() {
            @Override
            public void run() {
                try {
                    //example sequence
                    MessageHandler.getInstance().sendMessage("start video3" + END);
                    sleep(500);
                    MessageHandler.getInstance().sendMessage("start fade" + END);
                    sleep(1000);
                    MessageHandler.getInstance().sendMessage("stop fade" + END);
                    MessageHandler.getInstance().sendMessage("stop video3" + END);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_admin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
