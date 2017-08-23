/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:20:09
* 创建作者：许小满
* 文件名称：WeChatApiServiceImpl.java
* 版本：  v1.0
* 功能：微信相关操作--业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.service.impl;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.service.WeChatApiService;

@Service("weChatApiService")
public class WeChatApiServiceImpl extends BaseService implements WeChatApiService {

    /** 日志 */
    private static final Log logger = LogFactory.getLog(WeChatApiServiceImpl.class);
    
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
    public String callWechat(String appId,String redisKey,String token,String shopId,String userMac,String devMac,String devId,String ssid,
            String secretkey, String userPhone, String authType, String forceAttention, String wechatAuthUrl) throws Exception {
        String interfaceUrl = "https://wifi.weixin.qq.com/operator/callWechat.xhtml";//接口url
        String authUrl = wechatAuthUrl+"?appId="+appId;
        String timestamp = Calendar.getInstance().getTime().getTime()+"";
        logger.debug("提示：redisKey= " + redisKey);
        logger.debug("提示：token= " + token);
        logger.debug("提示：userMac= " + userMac);
        logger.debug("提示：devMac= " + devMac);
        logger.debug("提示：devId= " + devId);
        logger.debug("提示：userPhone= " + userPhone);
        logger.debug("提示：authType= " + authType);
        logger.debug("提示：forceAttention= " + forceAttention);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("token", token);
        map.put("userMac", userMac);
        map.put("devMac", devMac);
        map.put("devId", devId);
        map.put("redisKey", redisKey);
        map.put("userPhone", userPhone);
        map.put("loginType", authType);//unauth　未认证 authed 已认证
        map.put("forceAttention", forceAttention);//是否强制关注（-1 非强制关注、1 强制关注）
        String extend = JsonUtil.toJson(map);
        String userMacFormat = FormatUtil.formatApMac(userMac);
        logger.debug("提示：生成sign参数-appId= " + appId);
        logger.debug("提示：生成sign参数-extend= " + extend);
        logger.debug("提示：生成sign参数-timestamp= " + timestamp);
        logger.debug("提示：生成sign参数-shopId= " + shopId);
        logger.debug("提示：生成sign参数-authUrl= " + authUrl);
        logger.debug("提示：生成sign参数-userMac= " + userMacFormat);
        logger.debug("提示：生成sign参数-ssid= " + ssid);
        logger.debug("提示：生成sign参数-secretkey= " + secretkey);
        String sign = EncryUtil.getMd5Str(appId + extend + timestamp + shopId + authUrl + userMacFormat + ssid + secretkey);
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("appId", appId);
        parameterMap.put("timestamp", timestamp);//时间戳
        parameterMap.put("sign", sign);
        parameterMap.put("extend", extend);
        parameterMap.put("authUrl", authUrl);
        parameterMap.put("shopId", shopId);
        parameterMap.put("mac", userMacFormat);
        parameterMap.put("ssid", ssid);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(interfaceUrl, interfaceParam);
        if(byteBuffer == null){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"),interfaceUrl, interfaceParam);//接口无返回值！
        }
        String returnMessage = new String(byteBuffer.array(), "utf-8");
        logger.debug("提示：微信返回信息："+returnMessage);
        return returnMessage;
    }
    
}
