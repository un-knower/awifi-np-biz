package com.awifi.np.biz.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:IllegalDataException
 * @Description:
 * @author root
 *
 */
public class IllegalDataException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -4985437481300043902L;

    private List<String> errors = new ArrayList<String>();

    public IllegalDataException(List<String> errors)
    {
        this.errors = errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<String> errors)
    {
        this.errors = errors;
    }

    /**
     * @return the errors
     */
    public List<String> getErrors()
    {
        return errors;
    }

}
