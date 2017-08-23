/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.awifi.np.biz.common.exception;

/**
 * @Title:
 * @Description:
 * @Author:Administrator
 * @Since:2016年3月4日
 * @Version:1.1.0
 */
public class InsertBatchException extends ApplicationException
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8227794493040534015L;

    private int totalInsert;

    /**
     * 
     */
    public InsertBatchException(String message, int totalInsert, Throwable cause)
    {
        super(message, cause);
        this.totalInsert = totalInsert;
    }

    /**
     * @param totalInsert the totalInsert to set
     */
    public void setTotalInsert(int totalInsert)
    {
        this.totalInsert = totalInsert;
    }

    /**
     * @return the totalInsert
     */
    public int getTotalInsert()
    {
        return totalInsert;
    }

}
