/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    private final int currentMessageNumber = 0;
    final int PACKAGE_SIZE = 200;
    ArrayList<Message> messagesToSend;

    @SuppressWarnings("CallToPrintStackTrace")
    public UdpClient(InetAddress address, JTextArea chatArea)
    {
        this.chatArea = chatArea;
        this.address = address;
        messagesToSend = new ArrayList<>();
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
            ArrayList<String> strings = CommonMethods.divideMessage(new Message(1, message, 1, 1));
            for(int i = 0; i < strings.size(); i++)
            {
                messagesToSend.add(new Message(1, strings.get(i), i, strings.size()));
            }
            if(messagesToSend.size() > 1)
            {
                for(int i = 0; i < messagesToSend.size(); i++)
                {
                    if(!messagesToSend.get(i).getMessage().contains("##hello##"))
                        chatArea.setText(chatArea.getText() + messagesToSend.get(i).getMessage() + "\n");
                    theSocket.send(new DatagramPacket(CommonMethods.getObjectBytes(messagesToSend.get(i)), CommonMethods.getObjectBytes(messagesToSend.get(i)).length, address, 6666));
                }
            }
            else if(strings.size() == 1)
            {
                if(!messagesToSend.get(0).getMessage().contains("##hello##"))
                        chatArea.setText(chatArea.getText() + messagesToSend.get(0).getMessage() + "\n");
                theSocket.send(new DatagramPacket(CommonMethods.getObjectBytes(messagesToSend.get(0)), CommonMethods.getObjectBytes(messagesToSend.get(0)).length, address, 6666));
            }
            messagesToSend.clear();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void closeConnection()
    {
        try
        {
            theSocket.close();
            if(theSocket.isClosed())
                JOptionPane.showMessageDialog(new JFrame(), "You have closed connection successfully!", "Information", JOptionPane.ERROR_MESSAGE);
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
        DatagramPacket recivedPacket = new DatagramPacket(new byte[PACKAGE_SIZE], PACKAGE_SIZE);
        String wholeMessage = "";
        while(true)
        {
            try
            {
                theSocket.receive(recivedPacket);
                //firstPacket = new DatagramPacket(recivedPacket.getData(), recivedPacket.getData().length, recivedPacket.getAddress(), recivedPacket.getPort());
                Message m = CommonMethods.getObjectFromBytes(recivedPacket.getData());
                if(!m.getMessage().contains("##hello##"))
                {
                    if(m.getMessage().contains("Server"))
                        chatArea.setText(chatArea.getText() + m.getMessage().substring(0, 6) + "(" + m.getMessageNumber() + ")" + m.getMessage().substring(7) + "\n");
                    else
                        chatArea.setText(chatArea.getText() + "Server(" + m.getMessageNumber() + "):" + m.getMessage() + "\n");
                }
                    
                //recivedPacket = new DatagramPacket(new byte[200], 200);
                //theSocket.send(new DatagramPacket("Recived message".getBytes(), "Recived message".getBytes().length, firstPacket.getAddress(), firstPacket.getPort()));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
