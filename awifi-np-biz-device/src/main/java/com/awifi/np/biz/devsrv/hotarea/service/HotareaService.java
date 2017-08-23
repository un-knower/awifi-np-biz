package com.awifi.np.biz.devsrv.hotarea.service;


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
     * 删除热点
     * @param devMacs 设备数据集
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月13日 上午10:39:34
     */
    void deleteByDevMacs(String devMacs) throws Exception;

}
