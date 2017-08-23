package com.awifi.np.biz.common.system.log.service.impl;

import static org.mockito.Matchers.anyObject;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.awifi.np.biz.common.system.log.dao.ExceptionLogDao;
import com.awifi.np.biz.common.system.log.model.ExceptionLog;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月9日 下午2:09:49
 * 创建作者：周颖
 * 文件名称：ExceptionLogServiceImplTest.java
 * 版本：  v1.0
 * 功能：异常实现类测试类
 * 修改记录：
 */
public class ExceptionLogServiceImplTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private ExceptionLogServiceImpl exceptionLogServiceImpl;
    
    /**异常日志dao层*/
    @Mock(name="exceptionLogDao")
    private ExceptionLogDao exceptionLogDao;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年1月9日 下午2:17:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 保存异常日志接口测试方法
     * @author 周颖  
     * @date 2017年1月9日 下午2:18:04
     */
    @Test
    public void test() {
        Mockito.doNothing().when(exceptionLogDao).saveExceptionLog(anyObject());
        ExceptionLog ex = new ExceptionLog();
        ex.setId(1L);
        ex.getId();
        ex.getErrorCode();
        ex.getModuleName();
        ex.getParameter();
        ex.getErrorMessage();
        ex.getInterfaceUrl();
        ex.getInterfaceParam();
        ex.getInterfaceReturnValue();
        ex.setCreateDate(new Date());
        ex.getCreateDate();
        exceptionLogServiceImpl.saveExceptionLog(ex);        
    }
}