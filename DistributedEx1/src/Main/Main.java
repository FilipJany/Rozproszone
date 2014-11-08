/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import GUI.Chat.FrameMain;

/**
 *
 * @author filipjany
 */
public class Main 
{
    private static final boolean isUdp = true;
    public static void main(String[] args)
    {
        FrameMain fm = new FrameMain(600, 400, isUdp);
    }
}
