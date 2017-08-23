/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月6日 下午10:51:45
* 创建作者：许小满
* 文件名称：MerchantPVApiServiceImpl.java
* 版本：  v1.0
* 功能：商户访问相关操作--业务层实现类
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.merchant.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.merchant.service.MerchantVisitApiService;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@Service("merchantVisitApiService")
public class MerchantVisitApiServiceImpl extends BaseService implements MerchantVisitApiService {

    /**
     * 推送pv数据
     * @param merchantId 商户id
     * @param devId 设备id
     * @param pageType 站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
     * @param num 站点页面序号
     * @param userMac 用户终端MAC
     * @param visitDate 访问日期：格式 yyyyMMdd[HH24]
     * @author 许小满  
     * @date 2016年2月22日 下午11:09:08
     */
    public void pvPush(String merchantId, String devId, String pageType, String num, String userMac, String visitDate){
        if("0".equals(merchantId)){
            merchantId = SysConfigUtil.getParamValue("customer.default.id");//客户id为0时，采用默认值
            logger.debug("提示：merchantId==0，采用默认商户id["+ merchantId +"].");
        }
        Map<String,String> paramMap = new HashMap<String,String>(4);//参数集合
        paramMap.put("merchantId", merchantId);//商户编号，必传
        paramMap.put("deviceId", devId);//虚拟设备编号
        paramMap.put("pageFlag", pageType);//页面标签
        paramMap.put("userId", userMac);//用户标识(MAC)
        /** 因从前端浏览器获取的日期不准，换成服务器时间  */
        //paramMap.put("visitDate", visitDate);//访问时间
        paramMap.put("visitDate", DateUtil.getTodayDateHour());//访问时间
        
        String url = SysConfigUtil.getParamValue("dbc_pvpush_url");//获取请求地址
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramMap));
    }
    
}
