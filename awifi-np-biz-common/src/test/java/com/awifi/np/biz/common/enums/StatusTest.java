/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月4日 下午3:35:23
* 创建作者：王冬冬
* 文件名称：StatusTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.enums;

import org.junit.Test;

public class StatusTest {
    @Test
    public void test(){
        Status status=Status.deleted;
        status=Status.freeze;
        status=Status.normal;
        System.out.println(status.displayName());
        
    }
}
