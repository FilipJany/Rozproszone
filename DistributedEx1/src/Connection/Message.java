package Connection;

import java.io.Serializable;

/**
 *
 * @author filipjany
 */
public class Message implements Serializable
{
    static final int USERIN = 0, MESSAGE = 1, USEROUT = 2;
    private final int type;
    private final String message;
    
    public Message(int type, String message)
    {
        this.type = type;
        this.message = message;
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
