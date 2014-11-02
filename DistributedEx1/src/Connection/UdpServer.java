package Connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import javax.swing.JTextArea;

/**
 *
 * @author filipjany
 */
public class UdpServer 
{
    private DatagramPacket currentPacket;
    private DatagramPacket incomingPacket;
    private DatagramSocket socket;
    private final int port;
    private String lastMessage;
    private JTextArea chatTextArea;
    
    private byte[] packetData;
    private byte[] incomingData;
    private final int PACKET_SIZE = 1024;
    
    public UdpServer(int port, JTextArea chatArea)
    {
        this.port = port;
        packetData = new byte[PACKET_SIZE];
        chatTextArea = chatArea;
        runServer();
    }
    
    private void runServer()
    {
        try
        {
            socket = new DatagramSocket(port);
            System.out.println("UDP Server is running at port: " + port + ".");
            while(true)
            {
                currentPacket = new DatagramPacket(packetData, PACKET_SIZE);
                socket.receive(currentPacket);
                System.out.println("Got: " + new String(currentPacket.getData()) + " from: " + currentPacket.getAddress());
                System.out.println(currentPacket.toString());
                lastMessage = new String(currentPacket.getData());
                chatTextArea.setText("That: " + chatTextArea.getText() + lastMessage + "\n");
                chatTextArea.repaint();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            socket.close();
        }
    }
    
    public String GetLastMessage()
    {
        return lastMessage;
    }
    
    public void SendMessage(String message)
    {
        incomingData = message.getBytes();
        try
        {
            incomingPacket = new DatagramPacket(packetData, packetData.length, InetAddress.getLocalHost(), port);
            socket.send(incomingPacket);
            System.err.println("sending ");
            //chatArea.setText(chatArea.getText() + "This: " + message + "\n");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
