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
public class UdpServer implements Runnable
{
    DatagramSocket theSocket = null;
    DatagramPacket firstPacket;
    InetAddress address;
    private final int PACKAGE_SIZE = 200;//message size - 128
    JTextArea chatArea;
    private final int currentMessageNumber = 0;
    ArrayList<Message> messagesToSend;

    @SuppressWarnings("CallToPrintStackTrace")
    public UdpServer(InetAddress address, JTextArea chatArea)
    {
        this.address = address;
        this.chatArea = chatArea;
        messagesToSend = new ArrayList<>();        
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
                    theSocket.send(new DatagramPacket(CommonMethods.getObjectBytes(messagesToSend.get(i)), CommonMethods.getObjectBytes(messagesToSend.get(i)).length, 
                            firstPacket.getAddress(), firstPacket.getPort()));
                }
            }
            else if(strings.size() == 1)
            {
                if(!messagesToSend.get(0).getMessage().contains("##hello##"))
                        chatArea.setText(chatArea.getText() + messagesToSend.get(0).getMessage() + "\n");
                theSocket.send(new DatagramPacket(CommonMethods.getObjectBytes(messagesToSend.get(0)), CommonMethods.getObjectBytes(messagesToSend.get(0)).length, 
                        firstPacket.getAddress(), firstPacket.getPort()));
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
                firstPacket = new DatagramPacket(recivedPacket.getData(), recivedPacket.getData().length, recivedPacket.getAddress(), recivedPacket.getPort());
                Message m = CommonMethods.getObjectFromBytes(recivedPacket.getData());
                System.out.print(m.getMessage());
                if(!m.getMessage().contains("##hello##"))
                {
                    if(m.getMessage().contains("Client"))
                        chatArea.setText(chatArea.getText() + m.getMessage().substring(0, 6) + "(" + m.getMessageNumber() + ")" + m.getMessage().substring(7) + "\n");
                    else
                        chatArea.setText(chatArea.getText() + "Client(" + m.getMessageNumber() + "):" + m.getMessage() + "\n");
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
