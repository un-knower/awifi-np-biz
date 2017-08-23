package com.awifi.np.biz.common.exception;

/**
 *@ClassName:ApplicationException
 *@Description:
 *@author root
 *
 */
public class ApplicationException extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = 7177549176140059612L;

    public ApplicationException()
    {
        super();
    }

    public ApplicationException(String message)
    {
        super(message);
    }

    public ApplicationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ApplicationException(Throwable cause)
    {
        super(cause);
    }
}
