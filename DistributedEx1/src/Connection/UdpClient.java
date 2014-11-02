/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JTextArea;

/**
 *
 * @author filipjany
 */
public class UdpClient 
{
    private DatagramSocket socket;
    private DatagramPacket packet;
    private DatagramPacket incoming;
    private final int port;
    private InetAddress serverAddress;
    private byte[] packetData;
    private byte[] incomingData;
    private final int PACKET_SIZE = 1024;
    private JTextArea chatArea;

    public UdpClient(int port, InetAddress serverAddress, JTextArea mainArea) 
    {
        this.port = port;
        this.serverAddress = serverAddress;
        chatArea = mainArea;
        System.out.println("Client started and listening on port: " + port + ".");
        initClient();
        startListener();
    }
    
    
    private void startListener()
    {
        Thread t = new Thread(new Runnable() 
        {
            @Override
            public void run() 
            {
                incomingData = new byte[PACKET_SIZE];
                incoming = new DatagramPacket(incomingData, incomingData.length);
                try
                {
                    socket.receive(incoming);
                    System.err.println("Bangla");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    
    private void initClient()
    {
        try
        {
            socket = new DatagramSocket();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message)
    {
        packetData = message.getBytes();
        packet = new DatagramPacket(packetData, packetData.length, serverAddress, port);
        try
        {
            socket.send(packet);
            chatArea.setText(chatArea.getText() + "This: " + message + "\n");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
