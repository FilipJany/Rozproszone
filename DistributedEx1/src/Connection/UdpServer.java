package Connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTextArea;

/**
 *
 * @author filipjany
 */
public class UdpServer implements Runnable
{
    DatagramSocket theSocket = null;
    DatagramPacket firstPacket;
    InetAddress address;
    private final int PACKET_SIZE = 100;
    JTextArea chatArea;

    @SuppressWarnings("CallToPrintStackTrace")
    public UdpServer(InetAddress address, JTextArea chatArea)
    {
        this.address = address;
        this.chatArea = chatArea;
        try
        {
            theSocket = new DatagramSocket(6666, address);
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
            //System.out.println("address: " + firstPacket.getAddress() + " port: " + firstPacket.getPort());
            chatArea.setText(chatArea.getText() + message + "\n");
            theSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, firstPacket.getAddress(), firstPacket.getPort()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public byte[] getObjectBytes(Message object)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput oo = null;
        try
        {
            oo = new ObjectOutputStream(baos);
            oo.writeObject(object);
            byte[] objectBytes = baos.toByteArray();
            return objectBytes;
        }
        catch(Exception e)
        {
            return null;
        }
        finally
        {
            try
            {
                oo.close();
                baos.close();
            }
            catch(Exception e1)
            {
                
            }
        }
    }
    
    public ArrayList<byte[]> divideMessage(String message)
    {
        byte[] wholeMessage = message.getBytes();
        ArrayList<byte[]> messages = new ArrayList<>();
        int originalMessageLength = message.getBytes().length;
        int i = 0;
        Arrays.copyOfRange(wholeMessage, i, i+PACKET_SIZE);
        while(i < originalMessageLength)
        {
            byte[] part = new byte[PACKET_SIZE];
            if(i+PACKET_SIZE < originalMessageLength)
                part = Arrays.copyOfRange(wholeMessage, i, i+PACKET_SIZE);
            else
                part = Arrays.copyOfRange(wholeMessage, i, originalMessageLength);
            messages.add(part);
            i += PACKET_SIZE;
        }
        return messages;
    }
    
    public ArrayList<byte[]> divideMessage(Message object)
    {
        byte[] objectBytes = getObjectBytes(object);
        ArrayList<byte[]> parts = new ArrayList<>();
        int originalMessageLength = objectBytes.length;
        int i = 0;
        while(i < originalMessageLength)
        {
            byte[] part = new byte[PACKET_SIZE];
            if(i+PACKET_SIZE < originalMessageLength)
                part = Arrays.copyOfRange(objectBytes, i, i+PACKET_SIZE);
            else
                part = Arrays.copyOfRange(objectBytes, i, originalMessageLength);
            parts.add(part);
            i += PACKET_SIZE;
        }
        return parts;
    }
    
    
    
    public Message getObjectFromBytes(byte[] bytes)
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInput oi = null;
        try
        {
            oi = new ObjectInputStream(bais);
            Message m = (Message)oi.readObject();
            return m;
        }
        catch(Exception e)
        {
            return null;
        }
        finally
        {
            try
            {
                bais.close();
                oi.close();
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
            }
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
                firstPacket = new DatagramPacket(recivedPacket.getData(), recivedPacket.getData().length, recivedPacket.getAddress(), recivedPacket.getPort());
                chatArea.setText(chatArea.getText() + new String(recivedPacket.getData()) + "\n");
                System.out.println("Got: " + new String(recivedPacket.getData()));
                //theSocket.send(new DatagramPacket("Hejka".getBytes(), "Hejka".getBytes().length, recivedPacket.getAddress(), recivedPacket.getPort()));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
