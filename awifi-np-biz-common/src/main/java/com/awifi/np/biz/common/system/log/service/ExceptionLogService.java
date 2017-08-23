package com.awifi.np.biz.common.system.log.service;

import com.awifi.np.biz.common.system.log.model.ExceptionLog;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月6日 下午3:51:01
 * 创建作者：周颖
 * 文件名称：ExceptionLogService.java
 * 版本：  v1.0
 * 功能：异常日志服务层
 * 修改记录：
 */
public interface ExceptionLogService {

    /**
     * 保存异常日志
     * @param log 异常日志
     * @author 周颖  
     * @date 2017年1月6日 下午5:16:14
     */
    void saveExceptionLog(ExceptionLog log);
}