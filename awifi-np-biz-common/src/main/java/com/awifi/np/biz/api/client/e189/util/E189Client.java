/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月7日 下午2:54:11
* 创建作者：周颖
* 文件名称：E189Client.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.e189.util;

import java.nio.ByteBuffer;

import com.awifi.np.biz.api.client.e189.service.E189ApiService;
import com.awifi.np.biz.common.util.BeanUtil;

public class E189Client {

    /***/
    private static E189ApiService e189ApiService;
    
    /**
     * 获取e189ApiService实例
     * @return e189ApiService
     * @author 周颖  
     * @date 2017年8月3日 下午5:25:29
     */
    private static E189ApiService getE189ApiService(){
        if(e189ApiService == null){
            e189ApiService = (E189ApiService) BeanUtil.getBean("e189ApiService");
        }
        return e189ApiService;
    }
    
    /**
     * 获取生成二维码的uuid
     * @param params 请求参数
     * @return uuid
     * @author 周颖  
     * @date 2017年8月3日 下午5:24:19
     */
    public static String getQRUUID(String params){
        return getE189ApiService().getQRUUID(params);
    }
    
    /**
     * 获取二维码
     * @param params 入参
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年8月4日 上午9:20:38
     */
    public static ByteBuffer getQRCode(String params) throws Exception{
        return getE189ApiService().getQRCode(params);
    }

    /**
     * 获取accessToken
     * @param params 入参
     * @return 结果
     * @author 周颖  
     * @date 2017年8月4日 下午3:44:39
     */
    public static String getAccessToken(String params){
        return getE189ApiService().getAccessToken(params);
    }
    
    /**
     * 通过accessToken返回手机号
     * @param params 参数
     * @return 手机号
     * @author 周颖  
     * @date 2017年8月4日 上午9:34:51
     */
    public static String getCellphone(String params){
        return getE189ApiService().getCellphone(params);
    }
}
