/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月5日 下午4:14:56
* 创建作者：张智威
* 文件名称：PortalUtil.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.util;

import java.util.HashMap;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthResult;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;

public class PortalUtil {
    /**
     * 把sessionDTO中的数据转化成 jso格式传回前台 调用者首页接口
     * 
     * @param sessionDTO
     * @return HashMap 消息格式请见merchant/init.json 返回格式
     * @author 张智威
     * @date 2017年4月21日 下午4:29:12
     */
    public static Map<String, Object> getDetailInfo(SessionDTO sessionDTO) {
        HashMap<String, Object> result = new HashMap<>();
        SessionUser user = sessionDTO.getSessionUser();
        Device merchant = sessionDTO.getMerchant();
        UserTimeInfo timeInfo = sessionDTO.getTimeInfo();
        AuthResult auth = sessionDTO.getAuthResult();
        if (merchant != null) {// 商户信息
            HashMap<String, Object> merchantMap = new HashMap<>();
            merchantMap.put("merchantName", merchant.getMerchantName());
            merchantMap.put("merchantId", merchant.getMerchantId());
            result.put("merchant", merchantMap);

        }
        if (user != null) {// 用户信息
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("phone", user.getPhone());
            userMap.put("role", user.getRole());
            userMap.put("userId", user.getId());
            result.put("user", userMap);

        }
        if (timeInfo != null) {// 时长模块信息
            HashMap<String, Object> timeInfoMap = new HashMap<>();
            timeInfoMap.put("vip", timeInfo.isVip());
            timeInfoMap.put("canGetFreePkg", timeInfo.isCanGetFreePkg());
            timeInfoMap.put("endTime", timeInfo.getEndTime());
            timeInfoMap.put("buyout", timeInfo.isBuyout());// 是否是买断企业
            // timeInfoMap.put("buyUrl", "http://www.baidu.com");
            // timeInfoMap.put("forbiddenBuy", false);

            timeInfoMap.put("netStatus", false);
            result.put("timeInfo", timeInfoMap);
        }
        if (auth != null) {// 认证记录信息
            result.put("auth", auth);
        }
        // result.put("loginFlag", false);// 当前时间 用于显示倒计时
        result.put("canFastLogin", sessionDTO.getPortalParam().getUserType().equals("EXEMPT_AUTH_USER"));// 是否显示一键登录
        result.put("nowDate", new java.util.Date().getTime());// 当前时间 用于显示倒计时
        // result.put("topPicList", sessionDTO.getSlidelist());// 滚动图片
        result.put("buyurl", getBuyUrl(sessionDTO));// 购买url
        // result.put("buyout", sessionDTO.isBuyout());
        return result;

    }

    public static String getBuyUrl(SessionDTO sessionDTO) {
        StringBuffer sb = new StringBuffer();
        if (sessionDTO.getSessionUser() == null) { // 未登录的时候拉取的时候是没有用户信息的
            return "";
        }
        sb.append(ConfigUtil.getConfig("dq.buy.url")).append("&link_phone=")
                .append(sessionDTO.getSessionUser().getPhone()).append("&code_number=")
                .append(sessionDTO.getMerchant().getBroadbandAccount()).append("&yxgh=")
                .append(sessionDTO.getMerchant().getMerchantId());
        return sb.toString();
    }
}
