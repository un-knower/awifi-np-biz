/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:12:00
* 创建作者：许小满
* 文件名称：WeChatClient.java
* 版本：  v1.0
* 功能：微信相关操作--工具类
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.util;

import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.service.WeChatApiService;

public class WeChatClient {

    /** 微信 api接口业务层 */
    private static WeChatApiService weChatApiService;
    
    /**唤起微信
     * @param appId 商户微信公众平台识别id wwp_appId
     * @param redisKey 缓存key
     * @param token 令牌
     * @param shopId 微旺铺id
     * @param userMac 用户终端MAC
     * @param devMac 设备MAC
     * @param devId 设备ID
     * @param ssid 热点
     * @param secretkey 生成sign参数  wifikey
     * @param userPhone 手机号
     * @param authType authType
     * @param forceAttention 强制关注
     * @param wechatAuthUrl 微信认证url
     * @throws Exception 异常
     * @return 唤起成功
     * @author 郭海勇  
     * @date 2016年3月21日 上午10:01:34
     */
    public static String callWechat(String appId,String redisKey,String token,String shopId,String userMac,String devMac,String devId,String ssid,
            String secretkey, String userPhone, String authType, String forceAttention, String wechatAuthUrl) throws Exception{
        return getWeChatApiService().callWechat(appId, redisKey, token, shopId, userMac, devMac, devId, ssid, secretkey, userPhone, authType, forceAttention, wechatAuthUrl);
    }
    
    /**
     * 微信 api接口实例
     * @return 背景图api接口实例
     * @author 许小满  
     * @date 2017年5月16日 下午2:22:30
     */
    private static WeChatApiService getWeChatApiService(){
        if(weChatApiService == null){
            weChatApiService = (WeChatApiService)BeanUtil.getBean("weChatApiService");
        }
        return weChatApiService;
    }
    
}
