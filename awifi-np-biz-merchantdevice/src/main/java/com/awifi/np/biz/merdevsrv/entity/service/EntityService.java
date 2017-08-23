package com.awifi.np.biz.merdevsrv.entity.service;

import javax.servlet.http.HttpServletResponse;

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:08:01
 * 创建作者：亢燕翔
 * 文件名称：EntityService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface EntityService {

    /**
     * 设备监控列表
     * @param sessionUser sessionUser
     * @param params 请求参数
     * @param page page
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午9:15:19
     */
    void getEntityInfoListByMerId(SessionUser sessionUser, String params, Page<EntityInfo> page) throws Exception;

    /**
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @param sessionUser 用户
     * @param response 响应
     * @param merchantId 商户id
     * @param ssid ssid
     * @param mac mac地址
     * @param path 导出路径
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月3日 上午10:18:12
     */
    void export(Long provinceId, Long cityId, Long areaId, SessionUser sessionUser, HttpServletResponse response,
            Long merchantId, String ssid, String mac, String path) throws Exception;
    /**
     * @param devMac 设备mac
     * @throws Exception 异常
     * @author 王冬冬  
     * @param info 
     * @date 2017年6月5日 下午2:36:18
     */
    void deviceStatusRefresh(EntityInfo info, String devMac) throws Exception;
}
