package com.edu.chalmers.ixdcth.envariant.androidudptest;

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

    private DatagramSocket datagramSocket;
    private int port = 0000;
    private InetAddress host;

    public MessageHandler(int port, InetAddress hostAddress) {
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        host = hostAddress;
    }

    public void setPort(int portNo) {
        port = portNo;
    }

    public void setHost(InetAddress hostAddress) {
        host = hostAddress;
    }

    public void sendMessage(String message) {
        DatagramPacket packet = null;
        packet = new DatagramPacket(message.getBytes(), message.length(), host, port);
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
