package com.awifi.np.biz.common.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午10:36:11
 * 创建作者：周颖
 * 文件名称：SqlUtilTest.java
 * 版本：  v1.0
 * 功能：SqlUtil测试类
 * 修改记录：
 */
@SuppressWarnings("static-access")
public class SqlUtilTest {

    /**被测试类*/
    @InjectMocks
    private SqlUtil sqlUtil;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**测试in方法 columnValueArray为空*/
    @Test
    public void testInReturn() {
        String columnName="test";
        String paramName="test";
        Long[] columnValueArray=null;
        StringBuffer sql = new StringBuffer();
        sql.append("select");
        sqlUtil.in(columnName, paramName, columnValueArray, sql);
    }
    
    /**测试in方法*/
    @Test
    public void testInOK() {
        String columnName="test";
        String paramName="test";
        Long[] columnValueArray={1L,2L,3L};
        StringBuffer sql = new StringBuffer();
        sql.append("select");
        sqlUtil.in(columnName, paramName, columnValueArray, sql);
    }  
}