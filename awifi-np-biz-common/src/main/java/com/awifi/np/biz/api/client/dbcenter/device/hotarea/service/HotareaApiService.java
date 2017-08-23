package com.awifi.np.biz.api.client.dbcenter.device.hotarea.service;

import java.util.List;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:09:29
 * 创建作者：亢燕翔
 * 文件名称：HotareaApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface HotareaApiService {

    /**
     * 热点管理获取总数
     * @param params 请求参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午11:06:05
     */
    int getCountByParam(String params) throws Exception;
    
    /**
     * 热点管理列表
     * @param params 请求参数
     * @return list
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午11:10:44
     */
    List<Hotarea> getListByParam(String params) throws Exception;

    /**
     * 批量导入热点
     * @param params 请求
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月13日 上午10:02:17
     */
    void batchAddRelation(String params) throws Exception;

    /**
     * 删除热点
     * @param params 请求参数
     * @throws Exception 
     * @author 亢燕翔  
     * @date 2017年2月13日 下午2:16:50
     */
    void deleteByDevMacs(String params) throws Exception;

}
