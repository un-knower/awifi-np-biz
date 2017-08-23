/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月7日 下午2:54:31
* 创建作者：周颖
* 文件名称：E189ApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.e189.service;

import java.nio.ByteBuffer;

public interface E189ApiService {
    
    /**
     * 获取生成二维码的uuid
     * @param params 请求参数
     * @return uuid
     * @author 周颖  
     * @date 2017年8月3日 下午5:24:19
     */
    String getQRUUID(String params);

    /**
     * 获取二维码
     * @param params 入参
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年8月4日 上午9:20:38
     */
    ByteBuffer getQRCode(String params) throws Exception;
    
    /**
     * 获取accessToken
     * @param params 入参
     * @return 结果
     * @author 周颖  
     * @date 2017年8月4日 下午3:44:39
     */
    String getAccessToken(String params);

    /**
     * 通过accessToken返回手机号
     * @param params 参数
     * @return 手机号
     * @author 周颖  
     * @date 2017年8月4日 上午9:34:51
     */
    String getCellphone(String params);
}
