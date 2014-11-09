/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Filip
 */
public class CommonMethods 
{
    private static final int PACKAGE_SIZE = 150;
    private static final int OBJECT_SIZE = 128;
    
    public static byte[] getObjectBytes(Message object)
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
        Arrays.copyOfRange(wholeMessage, i, i+PACKAGE_SIZE);
        while(i < originalMessageLength)
        {
            byte[] part = new byte[PACKAGE_SIZE];
            if(i+PACKAGE_SIZE < originalMessageLength)
                part = Arrays.copyOfRange(wholeMessage, i, i+PACKAGE_SIZE);
            else
                part = Arrays.copyOfRange(wholeMessage, i, originalMessageLength);
            messages.add(part);
            i += PACKAGE_SIZE;
        }
        return messages;
    }
    
    public static ArrayList<String> divideMessage(Message object)
    {
        ArrayList<String> parts = new ArrayList<>();
        int originalMessageLength = object.getMessage().length();
        int i = 0;
        String part = "";
        while(i < originalMessageLength)
        {
            if(i + (PACKAGE_SIZE - OBJECT_SIZE) < originalMessageLength)
                part = object.getMessage().substring(i, i + (PACKAGE_SIZE - OBJECT_SIZE));
            else
                part = object.getMessage().substring(i);
            parts.add(part);
            i += (PACKAGE_SIZE - OBJECT_SIZE);
        }
        return parts;
    }
    
    public static ArrayList<Message> convertToMessages(ArrayList<byte[]> bytes)
    {
        ArrayList<Message> msgs = new ArrayList<>();
        for(int i = 0; i < bytes.size(); i++)
        {
            Message m = new Message(2, null, i, i);
        }
        return null;
    }
    
    public static Message getObjectFromBytes(byte[] bytes)
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
}
