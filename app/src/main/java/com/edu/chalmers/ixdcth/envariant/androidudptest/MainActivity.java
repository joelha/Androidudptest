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
import android.widget.NumberPicker;
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

    private NumberPicker delayPicker;
    //Needed since setMaxValue appears to be buggy..
    private int delay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAddresses();
        initDelayPicker();
    }

    /**
     * initializes the numberpicker for selecting the delay of the messages. Sets the available
     * values, max/min value and default value
     */
    private void initDelayPicker() {
        delayPicker = (NumberPicker) findViewById(R.id.delayPicker);

        String[] values = {"0", "100", "200", "300", "400", "500", "600", "700", "800", "900", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000"};
        delayPicker.setDisplayedValues(values);
        delayPicker.setMaxValue(20);
        delayPicker.setMinValue(0);
        delayPicker.setValue(0);
        delayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //wierd fix needed since getValue and setMaxValue of the numberpicker won't work,
                // setMaxValue throws index out of bounds exception if larger than the length of the
                // string array (even though this shouldn't have anyting to do with the index..)
                delay = newVal * 100;
            }
        });
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

    /**
     * called whenever one of the start buttons are pressed
     *
     * @param view
     */
    public void onClickStart(View view) {
        String msg = "";
        if (view.equals(findViewById(R.id.startVideo1Button))) {
            msg = "start video1" + END;
        } else if (view.equals(findViewById(R.id.startVideo2Button))) {
            msg = "start video2" + END;
        } else if (view.equals(findViewById(R.id.startVideo3Button))) {
            msg = "start video3" + END;
        } else if (view.equals(findViewById(R.id.startFadeButton))) {
            msg = "start fade" + END;
        }
        Log.d(DEBUG_TAG, msg);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        sendMessage(msg);
    }

    /**
     * called whenever one of the stop buttons are pressed
     *
     * @param view
     */
    public void onClickStop(View view) {
        String msg = "";
        if (view.equals(findViewById(R.id.stopVideo1Button))) {
            msg = "stop video1" + END;
        } else if (view.equals(findViewById(R.id.stopVideo2Button))) {
            msg = "stop video2" + END;
        } else if (view.equals(findViewById(R.id.stopVideo3Button))) {
            msg = "stop video3" + END;
        } else if (view.equals(findViewById(R.id.stopFadeButton))) {
            msg = "stop fade" + END;
        }
        Log.d(DEBUG_TAG, msg);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        sendMessage(msg);
    }

    /**
     * helper for sending messages, sends given string with currently picked delay.
     *
     * @param msg
     */
    private void sendMessage(String msg) {
        final String message = msg;
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(delay);
                    MessageHandler.getInstance().sendMessage(message);
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
