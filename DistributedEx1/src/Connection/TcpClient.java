/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import GUI.Chat.FrameMain;
import com.sun.security.ntlm.Client;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author filipjany
 */
public class TcpClient 
{
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    private final int PORT = 6666;
    private FrameMain mainFrame;
    private InetAddress address;
    
    public TcpClient(InetAddress serverAddress, FrameMain frame)
    {
        address = serverAddress;
        mainFrame = frame;
    }
    
    public void run()
    {
        try
        {
            socket = new Socket(address, PORT);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            new ServerListener().start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(Message message)
    {
        try
        {
            oos.writeObject(message);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    class ServerListener extends Thread
    {
        @Override
        public void run()
        {
            while(true)
            {
                try
                {
                    String msg = ((Message) ois.readObject()).getMessage();
                    mainFrame.getChatArea().setText(mainFrame.getChatArea().getText() + msg + "\n");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
