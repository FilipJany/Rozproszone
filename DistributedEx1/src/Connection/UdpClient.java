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
public class UdpClient implements Runnable
{
    DatagramSocket theSocket = null;
    InetAddress address;
    JTextArea chatArea;

    @SuppressWarnings("CallToPrintStackTrace")
    public UdpClient(InetAddress address, JTextArea chatArea)
    {
        this.chatArea = chatArea;
        this.address = address;
        try
        {
            theSocket = new DatagramSocket();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public void send(String message)
    {
        try
        {
            chatArea.setText(chatArea.getText() + message + "\n");
            theSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, address, 6666));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings({"ImplicitArrayToString", "CallToPrintStackTrace"})
    public void run()
    {
        DatagramPacket recivedPacket = new DatagramPacket(new byte[100], 100);
        while(true)
        {
            try
            {
                theSocket.receive(recivedPacket);
                chatArea.setText(chatArea.getText() + new String(recivedPacket.getData()) + "\n");
                System.out.println("Got: " + new String(recivedPacket.getData()));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
