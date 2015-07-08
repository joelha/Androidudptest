package com.edu.chalmers.ixdcth.envariant.androidudptest;

import android.os.Debug;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by EnVariant on 15-07-08.
 */
public class MessageHandler {

    private final String DEBUG_TAG = "DEBUG";

    private static MessageHandler messageHandler;

    private DatagramSocket datagramSocket;
    private int port = 5555;
    private InetAddress host;

    private MessageHandler() {
        init();
    }

    public static MessageHandler getInstance() {
        if(messageHandler != null) {
            return messageHandler;
        } else {
            return new MessageHandler();
        }
    }

    private void init() {
        if(host == null) {
            try {
                host = InetAddress.getByName("127.0.0.1");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        try {
            datagramSocket = new DatagramSocket(port, host);
            datagramSocket.connect(host, port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setPort(int portNo) {
        if(port != portNo) {
            port = portNo;
            datagramSocket.close();
            init();
        }
    }

    public void setHost(String adr) {
        try {
            if (!host.equals(InetAddress.getByName(adr))) {
                host = InetAddress.getByName(adr);
                datagramSocket.close();
                init();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        final String msg = message;
        new Thread() {
            @Override
            public void run() {
                DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), host, port);
                try {
                    datagramSocket.send(packet);
                    Log.d(DEBUG_TAG, "sent packet");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
