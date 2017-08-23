/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午11:56:28
* 创建作者：许小满
* 文件名称：CssApiService.java
* 版本：  v1.0
* 功能：获取css api 接口
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.css.service;

public interface CssApiService {

    /**
     * 获取css api接口
     * @param redisKey 缓存key
     * @param interfaceUrl 接口路径
     * @return css内容
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月13日 下午12:08:00
     */
    String getCss(String redisKey, String interfaceUrl) throws Exception;
    
}
