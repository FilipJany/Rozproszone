/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Chat;

import Connection.Message;
import Connection.TcpClient;
import Connection.TcpServer;
import Connection.UdpClient;
import Connection.UdpServer;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author filipjany
 */
public class FrameMain {

    private final int width, height;
    private SpringLayout layout;
    private JFrame mainFrame;
    private Container contentPane;
    private JTextArea mainArea;
    private JTextField messageField;
    private JButton beginChatButton, endChatButton, joinChatButton, sendButton;
    private final int portNumber = 6666;
    private String message;
    private JLabel hiddenField;
    private FrameMain frame;

    private InetAddress clientAddress;
    private boolean isServer = false;
    private TcpClient tcpClient;
    private TcpServer tcpServer;
    private UdpServer udpServer;
    private UdpClient udpClient;
    private boolean isUdp = false;

    public FrameMain(int width, int height, boolean isUdp) {
        this.isUdp = isUdp;
        this.width = width;
        this.height = height;
        message = "";
        frame = this;
        InitAndShowFrame();
    }

    private void InitAndShowFrame() {
        mainFrame = new JFrame("Chat");
        mainFrame.setBounds(100, 100, 600, 450);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = mainFrame.getContentPane();
        InitAndShowFrameFields();

        mainFrame.setVisible(true);
    }

    private void InitAndShowFrameFields() {
        layout = new SpringLayout();
        contentPane.setLayout(layout);

        mainArea = new JTextArea();
        mainArea.setPreferredSize(new Dimension(580, 320));
        mainArea.setEditable(false);
        messageField = new JTextField();
        messageField.setPreferredSize(new Dimension(380, 40));
        messageField.setEditable(false);
        messageField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });
        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(100, 40));
        sendButton.setEnabled(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isUdp) {
                    if (tcpServer != null) {
                        System.out.println("In server send");
                        tcpServer.write("Server: " + messageField.getText());
                    }
                    if (tcpClient != null) {
                        System.out.println("In client send");
                        tcpClient.sendMessage(new Message(1, "Client: " + messageField.getText()));
                    }
                } else {
                    if (udpServer != null) {
                        System.out.println("in udp server send");
                        udpServer.send("Server: " + messageField.getText());
                    }
                    if (udpClient != null) {
                        System.out.println("in udp client send");
                        udpClient.send("Client: " + messageField.getText());
                    }
                }
                messageField.setText("");
            }
        });
        beginChatButton = new JButton("Start Chat");
        beginChatButton.setPreferredSize(new Dimension(150, 40));
        beginChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(contentPane,  
                            "<html><center>Started chat server at localhost. Computer address in LAN:<br><font size=16>" + InetAddress.getLocalHost().getHostAddress() + "</font></center></html>", 
                            "Local Area Network Address",
                            JOptionPane.PLAIN_MESSAGE);
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
                if (!isUdp) {
                    tcpServer = new TcpServer(frame);
                    new ServerThread().start();
                } else {
                    try {
                        udpServer = new UdpServer(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()), mainArea);
                        new Thread(udpServer).start();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                isServer = true;
                messageField.setEditable(true);
                sendButton.setEnabled(true);
                endChatButton.setEnabled(true);
                joinChatButton.setEnabled(false);
                beginChatButton.setEnabled(false);

            }
        });
        joinChatButton = new JButton("Join Chat");
        joinChatButton.setPreferredSize(new Dimension(150, 40));
        joinChatButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddressFrame sf = new AddressFrame(hiddenField);
                //client creation in hidden field's property change listener
            }
        });
        endChatButton = new JButton("Disconnect");
        endChatButton.setPreferredSize(new Dimension(150, 40));
        endChatButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (!isUdp) 
                {
                    if (tcpServer != null)
                        tcpServer.closeConnection();
                    if (tcpClient != null)
                        tcpClient.closeConnection();
                } else 
                {
                    if (udpServer != null)
                        udpServer.closeConnection();
                    if (udpClient != null)
                        udpClient.closeConnection();
                }
                messageField.setEditable(false);
                sendButton.setEnabled(false);
                beginChatButton.setEnabled(true);
                joinChatButton.setEnabled(true);
                endChatButton.setEnabled(false);
            }
        });
        endChatButton.setEnabled(false);

        hiddenField = new JLabel();
        hiddenField.addPropertyChangeListener("text", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    clientAddress = InetAddress.getByName(hiddenField.getText());
                    if (clientAddress != null) {
                        if (!isUdp) {
                            tcpClient = new TcpClient(clientAddress, frame);
                            tcpClient.run();
                        } else {
                            udpClient = new UdpClient(clientAddress, mainArea);
                            new Thread(udpClient).start();
                            udpClient.send("##hello##");
                        }

                        isServer = false;
                        joinChatButton.setEnabled(false);
                        beginChatButton.setEnabled(false);
                        sendButton.setEnabled(true);
                        messageField.setEditable(true);
                        endChatButton.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        contentPane.add(mainArea);
        contentPane.add(messageField);
        contentPane.add(sendButton);
        contentPane.add(beginChatButton);
        contentPane.add(joinChatButton);
        //contentPane.add(endChatButton);

        layout.putConstraint(SpringLayout.WEST, mainArea, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, mainArea, 10, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, mainArea, -10, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, mainArea, -120, SpringLayout.SOUTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, messageField, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, messageField, 10, SpringLayout.SOUTH, mainArea);
        layout.putConstraint(SpringLayout.EAST, messageField, -120, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, messageField, -60, SpringLayout.SOUTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, sendButton, 10, SpringLayout.EAST, messageField);
        layout.putConstraint(SpringLayout.NORTH, sendButton, 15, SpringLayout.SOUTH, mainArea);
        layout.putConstraint(SpringLayout.EAST, sendButton, -10, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, messageField, -55, SpringLayout.SOUTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, beginChatButton, 30, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, beginChatButton, 10, SpringLayout.SOUTH, messageField);
        layout.putConstraint(SpringLayout.EAST, beginChatButton, 130, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, beginChatButton, -10, SpringLayout.SOUTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, joinChatButton, 10, SpringLayout.EAST, beginChatButton);
        layout.putConstraint(SpringLayout.NORTH, joinChatButton, 10, SpringLayout.SOUTH, messageField);
        layout.putConstraint(SpringLayout.SOUTH, joinChatButton, -10, SpringLayout.SOUTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, endChatButton, 10, SpringLayout.EAST, joinChatButton);
        layout.putConstraint(SpringLayout.NORTH, endChatButton, 10, SpringLayout.SOUTH, messageField);
        layout.putConstraint(SpringLayout.SOUTH, endChatButton, -10, SpringLayout.SOUTH, contentPane);
    }

    public void serverOrClientClosedManageFields() {
        messageField.setEditable(false);
        sendButton.setEnabled(false);
        beginChatButton.setEnabled(true);
        joinChatButton.setEnabled(true);
        endChatButton.setEnabled(false);
    }

    public JTextArea getChatArea() {
        return mainArea;
    }

    class ServerThread extends Thread {

        @Override
        public void run() {
            tcpServer.run();
        }
    }
}
