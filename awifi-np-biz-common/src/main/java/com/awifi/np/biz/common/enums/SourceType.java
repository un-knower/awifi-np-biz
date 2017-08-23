package com.awifi.np.biz.common.enums;

/**
 * 平台类型
 *
 */
public enum SourceType
{
    /**
     * 
     */
    awifi(11, "AWIFI"),
    /**
     * 
     */
    chinaNet(12, "CHINANET"),
    /**
     * 
     */
    third(13, "THIRD");
    /**
     * 
     */
    private int value;
    /**
     * 
     */
    private String displayName;

    SourceType(int value, String displayName)
    {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * @Title:displayName
     * @Description:
     * @return
     * @return SourceType
     * @throws
     */
    public String displayName()
    {
        return displayName;
    }

    public int getValue()
    {
        return value;
    }
}
