/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月6日 下午10:50:49
* 创建作者：许小满
* 文件名称：MerchantPVApiService.java
* 版本：  v1.0
* 功能：商户访问相关操作--业务层
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.merchant.service;

public interface MerchantVisitApiService {

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
    void pvPush(String merchantId, String devId, String pageType, String num, String userMac, String visitDate);
    
}
