/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Chat;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author filipjany
 */
public class AddressFrame extends JFrame
{
    private final int width = 300, height = 160;
    private JLabel descr;
    private JTextField addressArea;
    private JButton okButton, cancelButton;
    private SpringLayout layout;
    private Container contentPane;
    private JLabel hiddenField;
    
    public AddressFrame(JLabel hF)
    {
        hiddenField = hF;
        drawFrame();
    }
    
    private void drawFrame()
    {
        setBounds(100, 100, width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initAndDrawFields();
        setResizable(false);
        setVisible(true);
    }
    
    private void initAndDrawFields()
    {
        layout = new SpringLayout();
        setLayout(layout);
        contentPane = getContentPane();
        
        descr = new JLabel("Enter server address: ");
        descr.setPreferredSize(new Dimension(100, 20));
        addressArea = new JTextField();
        addressArea.setPreferredSize(new Dimension(280, 40));
        addressArea.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                okButton.doClick();
            }
        });
        okButton = new JButton("Connect");
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(!addressArea.getText().equalsIgnoreCase(""))
                {
                    hiddenField.setText(addressArea.getText());
                    dispose();
                }
                else {
                    addressArea.setText("Please provide address or click 'Cancel'!");
                    addressArea.requestFocusInWindow();
                    addressArea.selectAll();
                }
            }
        }); 
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dispose();
            }
        });
        
        contentPane.add(descr);
        contentPane.add(addressArea);
        contentPane.add(cancelButton);
        contentPane.add(okButton);
        
        layout.putConstraint(SpringLayout.NORTH, descr, 10, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, descr, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, descr, -10, SpringLayout.EAST, contentPane);
        
        layout.putConstraint(SpringLayout.NORTH, addressArea, 10, SpringLayout.SOUTH, descr);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addressArea, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.EAST, addressArea, -10, SpringLayout.EAST, contentPane);
        
        layout.putConstraint(SpringLayout.WEST, cancelButton, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, cancelButton, -10, SpringLayout.SOUTH, contentPane);
        
        layout.putConstraint(SpringLayout.EAST, okButton, -10, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, okButton, -10, SpringLayout.SOUTH, contentPane);
    }
}
