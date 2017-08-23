/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月4日 下午3:17:09
* 创建作者：王冬冬
* 文件名称：FlowStsTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.enums;

import org.junit.Test;

public class FlowStsTest {

    @Test
    public void test(){
        FlowSts flowsts=FlowSts.finished;
        flowsts=FlowSts.newDev;
        flowsts=FlowSts.registerFail;
        flowsts=FlowSts.reviewBack;
        flowsts=FlowSts.reviewGo;
        flowsts=FlowSts.synchFail;
        flowsts=FlowSts.synSucc;
        flowsts=FlowSts.tested;
        flowsts=FlowSts.testedFail;
        flowsts=FlowSts.waitReview;
        flowsts=FlowSts.waitTest;
        flowsts=FlowSts.waitTestFail;
        System.out.println(flowsts.getValue());
        System.out.println(flowsts.displayName());
        System.out.println(flowsts.name());
        FlowSts flowsts1=FlowSts.newDev;
        System.out.println(flowsts.compareTo(flowsts1));
    }
}
