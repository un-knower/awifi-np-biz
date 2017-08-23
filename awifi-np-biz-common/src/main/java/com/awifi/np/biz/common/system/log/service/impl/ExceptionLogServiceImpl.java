package com.awifi.np.biz.common.system.log.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.system.log.dao.ExceptionLogDao;
import com.awifi.np.biz.common.system.log.model.ExceptionLog;
import com.awifi.np.biz.common.system.log.service.ExceptionLogService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月6日 下午3:51:45
 * 创建作者：周颖
 * 文件名称：ExceptionLogServiceImpl.java
 * 版本：  v1.0
 * 功能：异常日志实现类
 * 修改记录：
 */
@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements ExceptionLogService{

    /**
     * 异常
     */
    @Resource(name = "exceptionLogDao")
    private ExceptionLogDao exceptionLogDao;
    
    /**
     * 保存异常日志
     * @param log 异常日志
     * @author 周颖  
     * @date 2017年1月6日 下午5:46:33
     */
    public void saveExceptionLog(ExceptionLog log){
    	exceptionLogDao.saveExceptionLog(log);
    }
}
