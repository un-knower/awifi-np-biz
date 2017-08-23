package com.awifi.np.biz.merdevsrv.hotarea.service;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:08:19
 * 创建作者：亢燕翔
 * 文件名称：HotareaService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface HotareaService {
    
    /**
     * 热点管理分页列表
     * @param sessionUser sessionUser
     * @param page 分页实体
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午10:12:10
     */
    void getListByParam(SessionUser sessionUser, Page<Hotarea> page, String params) throws Exception;

}
