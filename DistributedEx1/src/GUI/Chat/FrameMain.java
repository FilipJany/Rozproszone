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
    private JList contactList;
    
    private List<String> contactStringList;
    
    private Object[] items = {"1", "2", "3"};
    
    private JButton beginChatButton, endButton;
    
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
        
        mainArea = new JTextArea(75, 75);
        contactList = new JList(items);
        beginChatButton = new JButton("Chat");
        beginChatButton.setPreferredSize(new Dimension(100, 40));
        endButton = new JButton("Disconnect");
        endButton.setPreferredSize(new Dimension(100, 40));
        
        contentPane.add(mainArea);
        contentPane.add(contactList);
        contentPane.add(beginChatButton);
        contentPane.add(endButton);
        
        layout.putConstraint(SpringLayout.WEST, mainArea, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, mainArea, 10, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, mainArea, -160, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, mainArea, -60, SpringLayout.SOUTH, contentPane);
        
        layout.putConstraint(SpringLayout.WEST, contactList, 10, SpringLayout.EAST, mainArea);
        layout.putConstraint(SpringLayout.NORTH, contactList, 10, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, contactList, -10, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, contactList, -10, SpringLayout.SOUTH, contentPane);
        
        layout.putConstraint(SpringLayout.NORTH, beginChatButton, 10, SpringLayout.SOUTH, mainArea);
        layout.putConstraint(SpringLayout.EAST, beginChatButton, -10, SpringLayout.WEST, contactList);
        layout.putConstraint(SpringLayout.SOUTH, contactList, -10, SpringLayout.SOUTH, contentPane);
        
        layout.putConstraint(SpringLayout.NORTH, endButton, 10, SpringLayout.SOUTH, mainArea);
        layout.putConstraint(SpringLayout.EAST, endButton, -10, SpringLayout.WEST, beginChatButton);
        layout.putConstraint(SpringLayout.SOUTH, endButton, -10, SpringLayout.SOUTH, contentPane);
    }
}
