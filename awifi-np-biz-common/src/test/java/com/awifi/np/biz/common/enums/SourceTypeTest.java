/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月4日 下午3:31:28
* 创建作者：王冬冬
* 文件名称：SourceTypeTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.enums;

import org.junit.Test;

public class SourceTypeTest {
    @Test
    public void test(){
        SourceType flowsts=SourceType.awifi;
        flowsts=SourceType.chinaNet;
        flowsts=SourceType.third;
        System.out.println(flowsts.getValue());
        System.out.println(flowsts.displayName());
        System.out.println(flowsts.name());
    }
}
