/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Chat;

import java.awt.Container;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author filipjany
 */
public class FrameMain 
{
    private final int width, height;
    private SpringLayout layout;
    private JFrame mainFrame;
    private Container contentPane;
    private JTextArea mainArea;
    private JTextField messageField;
    
    
    private JButton beginChatButton, endChatButton, joinChatButton, sendButton;
    
    public FrameMain(int width, int height)
    {
        this.width = width;
        this.height = height;
        
        InitAndShowFrame();
    }
    
    private void InitAndShowFrame()
    {
        mainFrame = new JFrame("Chat");
        mainFrame.setBounds(100, 100, 600, 450);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = mainFrame.getContentPane();
        InitAndShowFrameFields();
        
        mainFrame.setVisible(true);
    }
    
    private void InitAndShowFrameFields()
    {
        layout = new SpringLayout();
        contentPane.setLayout(layout);
        
        mainArea = new JTextArea();
        mainArea.setPreferredSize(new Dimension(580, 320));
        mainArea.setEditable(false);
        messageField = new JTextField();
        messageField.setPreferredSize(new Dimension(380, 40));
        messageField.setEditable(false);
        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(100, 40));
        sendButton.setEnabled(false);
        beginChatButton = new JButton("Start Chat");
        beginChatButton.setPreferredSize(new Dimension(150, 40));
        joinChatButton = new JButton("Join Chat");
        joinChatButton.setPreferredSize(new Dimension(150, 40));
        endChatButton = new JButton("Disconnect");
        endChatButton.setPreferredSize(new Dimension(150, 40));
        endChatButton.setEnabled(false);
        
        contentPane.add(mainArea);
        contentPane.add(messageField);
        contentPane.add(sendButton);
        contentPane.add(beginChatButton);
        contentPane.add(joinChatButton);
        contentPane.add(endChatButton);
        
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
}
