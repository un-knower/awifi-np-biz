/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月4日 下午2:41:03
* 创建作者：王冬冬
* 文件名称：DevTypeTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.enums;

import org.junit.Test;

public class DevTypeTest {

    
    @Test
    public void test(){
        
        DevType d= DevType.ac;
        System.out.println(d.displayName());
        System.out.println(d.entityType());
        System.out.println(d.name());
        System.out.println(d.getType());
        System.out.println(d.getValue());
        System.out.println(d.getFatAp());
        DevType d1= DevType.bras;
        System.out.println(d1.displayName());
        System.out.println(d1.compareTo(d));
        DevType d2= DevType.epon;
        System.out.println(d2.displayName());
        DevType d3= DevType.eponw;
        System.out.println(d3.displayName());
        DevType d4= DevType.fatap;
        System.out.println(d1.displayName());
        DevType d5= DevType.fitap;
        System.out.println(d1.displayName());
        DevType d6= DevType.gpon;
        System.out.println(d1.displayName());
        DevType d7= DevType.gponw;
        System.out.println(d1.displayName());
        DevType d8= DevType.hFitap;
        System.out.println(d1.displayName());
        DevType d9= DevType.pFitap;
        System.out.println(d1.displayName());
        DevType d10= DevType.threeForOne;
        System.out.println(d1.displayName());
        DevType d11= DevType.twoForOne;
        System.out.println(d1.displayName());
    }
}
