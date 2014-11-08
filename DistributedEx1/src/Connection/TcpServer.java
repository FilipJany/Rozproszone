/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import GUI.Chat.FrameMain;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author filipjany
 */
public class TcpServer 
{
    private FrameMain mainFrame;
    private final int PORT = 6666;
    private boolean isRunning = false;
    private ClientHandler client;
    
    public TcpServer()
    {
        
    }
    
    public TcpServer(FrameMain frame)
    {
        mainFrame = frame;
    }
    
    public void run()
    {
        isRunning = true;
        try
        {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while(isRunning)
            {
                System.out.println("Server running at port: " + PORT);
                Socket socket = serverSocket.accept();
                if(!isRunning)
                    break;
                client = new ClientHandler(socket);
                client.start();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized void write(String message)
    {
        if(mainFrame != null)
        {
            mainFrame.getChatArea().setText(mainFrame.getChatArea().getText() + message + "\n");
            try
            {
                client.oos.writeObject(new Message(1, message));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public synchronized void writeMessageObj(Message message)
    {
        if(mainFrame != null)
        {
            mainFrame.getChatArea().setText(mainFrame.getChatArea().getText() + message.getMessage() + "\n");
            try
            {
                client.oos.writeObject(message);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void closeConnection()
    {
        String endMsg = "Server has been shuted down!";
        writeMessageObj(new Message(2, endMsg));
        isRunning = false;
    }
    
    //class that is responsible for handling client connection management
    class ClientHandler extends Thread
    {
        public Socket socket;
        public ObjectInputStream ois;
        public ObjectOutputStream oos;
        public Message message;
        
        public ClientHandler(Socket socket)
        {
            this.socket = socket;
            try
            {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        @Override
        public void run()
        {
            while(true)
            {
                try
                {
                    message = (Message)ois.readObject();
                    if(message.getType() == 2)
                    {
                        mainFrame.serverOrClientClosedManageFields();
                    }
                    write(message.getMessage());
                }
                catch(IOException | ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
