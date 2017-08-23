/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月6日 下午2:24:22
* 创建作者：王冬冬
* 文件名称：ExcelFAtApTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.fatap.model;

import org.junit.Test;

public class ExcelFAtApTest {

    @Test
    public void test(){
        ExcelFatAp fatap=new ExcelFatAp();
        fatap.setAcname("");
        fatap.setAntennum(1L);
        fatap.setBand("");
        fatap.setCity("");
        fatap.setCorporation("");
        fatap.setCorporationText("");
        fatap.setCounty("");
        fatap.setCpu("");
        fatap.setCpversion("");
        fatap.setFwversion("");
        fatap.setMem("");
        fatap.setModel("");
        fatap.setOnsiteservice(1);
        fatap.setOs("");
        fatap.setParentid(1L);
        fatap.setPincode("");
        fatap.setProvince("");
        fatap.setRj45num(1L);
        fatap.setSsid("");
        fatap.setStorage("");
        
        fatap.getAcname();
        fatap.getAntennum();
        fatap.getBand();
        fatap.getCity();
        fatap.getCorporation();
        fatap.getCorporationText();
        fatap.getCounty();
        fatap.getCpu();
        fatap.getCpversion();
        fatap.getFwversion();
        fatap.getMem();
        fatap.getModel();
        fatap.getOnsiteservice();
        fatap.getOs();
        fatap.getParentid();
        fatap.getPincode();
        fatap.getProvince();
        fatap.getRj45num();
        fatap.getSsid();
        fatap.getStorage();
    }
    
    
}
