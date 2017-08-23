/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月6日 下午2:01:23
* 创建作者：王冬冬
* 文件名称：CenterPubPlatformTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.fatap.model;

import java.util.Date;

import org.junit.Test;

public class CenterPubPlatformTest {

    @Test
    public void test(){
        CenterPubPlatform platform=new CenterPubPlatform();
        platform.setAdministrant("");
        platform.setAdministrantPhone("");
        platform.setAuthDomain("");
        platform.setAuthIp("");
        platform.setAuthPort(11);
        platform.setCity(11);
        platform.setCityName("");
        platform.setCounty(11);
        platform.setCountyName("");
        platform.setCreateDate(new Date());
        platform.setDevBusIp("");
        platform.setDevBusPort("");
        platform.setDeviceId("");
        platform.setId(11);
        platform.setPlatformDomain("");
        platform.setPlatformIp("");
        platform.setPlatformName("");
        platform.setPlatformPort(11);
        platform.setPlatformType("");
        platform.setPortalDomain("");
        platform.setPortalIp("");
        platform.setPortalPort(11);
        platform.setProvince(11);
        platform.setProvinceName("");
        platform.setRemark("");
        platform.setStatus(1);
        platform.setUrl("");
        
        platform.getAdministrant();
        platform.getAdministrantPhone();
        platform.getAuthDomain();
        platform.getAuthIp();
        platform.getAuthPort();
        platform.getCity();
        platform.getCityName();
        platform.getCounty();
        platform.getCountyName();
        platform.getCreateDate();
        platform.getDevBusIp();
        platform.getDevBusPort();
        platform.getDeviceId();
        platform.getId();
        platform.getPlatformDomain();
        platform.getPlatformIp();
        platform.getPlatformName();
        platform.getPlatformPort();
        platform.getPlatformType();
        platform.getPortalDomain();
        platform.getPortalIp();
        platform.getPortalPort();
        platform.getProvince();
        platform.getProvinceName();
        platform.getRemark();
        platform.getStatus();
        platform.getUrl();
    }
}
