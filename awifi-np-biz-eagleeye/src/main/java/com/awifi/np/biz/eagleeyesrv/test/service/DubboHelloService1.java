/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月3日 下午2:26:42
* 创建作者：尤小平
* 文件名称：DubboHelloService1.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.test.service;

import com.awifi.core.pre.eagleeye.client.config.dubbo.annotation.UnSampler;

public interface DubboHelloService1 {

    @UnSampler
    String hello1(String param, String b) throws Exception;
    
    public String hello2(String param);
}
