package com.edu.chalmers.ixdcth.envariant.androidudptest;

import android.os.Debug;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by EnVariant on 15-07-08.
 */
public class MessageHandler {

    private final String DEBUG_TAG = "DEBUG";

    private DatagramSocket datagramSocket;
    private int port;
    private InetAddress host;

    public MessageHandler(int portNo, InetAddress hostAddress) {
        try {
            datagramSocket = new DatagramSocket(portNo);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        host = hostAddress;
        port = portNo;
    }

    public void setPort(int portNo) {
        port = portNo;
    }

    public void setHost(InetAddress hostAddress) {
        host = hostAddress;
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
