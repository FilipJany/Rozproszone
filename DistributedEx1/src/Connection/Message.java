package Connection;

import java.io.Serializable;

/**
 *
 * @author filipjany
 */
public class Message implements Serializable
{
    private final int type;
    private final String message;
    private int messageNumber;
    private int totalMessageCount;
    
    public Message(int type, String message)
    {
        this.type = type;
        this.message = message;
    }
    
    public Message(int type, String message, int messageNumber, int totalMessages)
    {
        this.type = type;
        this.message = message;
        this.messageNumber = messageNumber;
        this.totalMessageCount = totalMessages;
    }
    
    public int getTotalMessagesCount()
    {
        return totalMessageCount;
    }
    
    public int getMessageNumber()
    {
        return messageNumber;
    }
    
    public int getType()
    {
        return type;
    }
    
    public String getMessage()
    {
        return message;
    }
}
