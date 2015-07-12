package com.edu.chalmers.ixdcth.envariant.androidudptest;

import android.os.Debug;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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
        if(messageHandler == null) {
            messageHandler = new MessageHandler();
        }
        return messageHandler;
    }

    private void init() {
        if(host == null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        //sets a default address
                        host = InetAddress.getByName("127.0.0.1");
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
        try {
            datagramSocket = new DatagramSocket(null);
            datagramSocket.setReuseAddress(true);
            datagramSocket.bind(new InetSocketAddress(host, port));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Used when changing the port number, reinitializes the DatagramSocket if new port number.
     */
    public void setPort(int portNo) {
        if(port != portNo) {
            port = portNo;
            init();
        }
    }

    /**
     * Used when changing the host address, reinitializes the DatagramSocket if new host address.
     * @param address
     */
    public void setHost(String address) {
        final String adr = address;
        new Thread() {
            @Override
            public void run() {
                try {
                    if (!host.equals(InetAddress.getByName(adr))) {
                        host = InetAddress.getByName(adr);
                        init();
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * Used in MainActivity to send simple string messages. Creates a packet holding the message and
     * sends it to the host address and port number.
     * @param message
     */
    public void sendMessage(String message) {
        final String msg = message;
        new Thread() {
            @Override
            public void run() {
                DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), host, port);
                try {
                    datagramSocket.send(packet);
                    Log.d(DEBUG_TAG, "sent packet");
                    Log.d(DEBUG_TAG, "host: " + host);
                    Log.d(DEBUG_TAG, "port: " + port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
