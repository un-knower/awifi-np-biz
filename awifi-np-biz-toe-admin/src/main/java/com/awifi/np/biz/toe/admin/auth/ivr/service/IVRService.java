/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月14日 下午9:22:35
* 创建作者：许小满
* 文件名称：IVRService.java
* 版本：  v1.0
* 功能：IVR 语音认证
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.auth.ivr.service;

import java.util.Map;

public interface IVRService {

    /**
     * 轮循 查看是否放行成功或者放行失败
     * @param phoneNumber 手机号
     * @param resultMap 
     * @author ZhouYing 
     * @throws Exception 异常
     * @date 2016年7月27日 下午3:08:35
     */
    void poll(String phoneNumber, Map<String, Object> resultMap) throws Exception;
 
    /**
     * IVR语音认证-保存参数及日志
     * @param redisKey 
     * @param redisValue 
     * @param phoneNumber 
     * @param userMac 
     * @param result 
     * @param merchantId 
     * @param cascadeLabel 
     * @author kangyanxiang 
     * @date 2016年7月28日 上午10:13:20
     */
    void save(String redisKey, String redisValue, String phoneNumber, String userMac, Integer result, Long merchantId, String cascadeLabel);
    
    /**
     * 校验短信网关手机号 放行
     * @param phoneNumber 手机号
     * @param publicUserIp 公网ip
     * @param publicUserPort 公网端口
     * @param userAgent 请求头里面的userAgent
     * @author ZhouYing 
     * @throws Exception 异常
     * @date 2016年7月26日 上午10:16:45
     */
    void ivr(String phoneNumber, String publicUserIp, String publicUserPort, String userAgent) throws Exception;
    
}
