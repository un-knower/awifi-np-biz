package com.awifi.np.biz.common.enums;

/**
 * 设备状态 status
 */
public enum Status
{
    /**
     * 
     */
    normal(1, "正常"), freeze(2, "锁定/冻结"), deleted(9, "作废/删除");
    /**
     * 
     */
    private int value;
    /**
     * 
     */
    private String displayName;

    Status(int value, String displayName)
    {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * @Title:displayName
     * @Description:
     * @return
     * @return Status
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
