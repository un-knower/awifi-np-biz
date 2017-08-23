package com.awifi.np.biz.common.enums;

/**
 * 设备业务流程状态 flowSts
 */
public enum FlowSts {
    /**
     * 
     */
    newDev(1, "新建设备"),
    /**
     * 
     */
    waitReview(2, "等待审核"),
    /**
     * 
     */
    reviewBack(3, "审核驳回"),
    /**
     * 
     */
    reviewGo(4, "审核通过"),
    /**
     * 
     */
    waitTest(5, "等待测试"),
    /**
     * 
     */
    tested(6, "测试通过"),
    /**
     * 
     */
    finished(9, "完成"),

    /**
     * 兼容3.0;4.0正式上线就会删掉
     */
    waitTestFail(15, "待测试失败"),
    /**
     * 
     */
    testedFail(16, "测试通过失败"),
    /**
     * 
     */
    synchFail(17, "同步接入失败"),
    /**
     * 
     */
    synSucc(18, "同步接入成功"),
    /**
     * 
     */
    registerFail(21, "注册失败");
    /**
     * 
     */
    private int value;
    /**
     * 
     */
    private String displayName;

    FlowSts(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * @Title:displayName @Description: @return @return FlowSts @throws
     */
    public String displayName() {
        return displayName;
    }

    public int getValue() {
        return value;
    }
}
