package com.awifi.np.biz.common.base.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 下午2:05:44
 * 创建作者：亢燕翔
 * 文件名称：PageTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class PageTest {

    /**被测试类*/
    @InjectMocks
    private Page<T> page;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试分页
     * @author 亢燕翔  
     * @date 2017年3月23日 下午2:11:30
     */
    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
    @Test
    public void testPage(){
        Page page = new Page(1);
        Page pageSize = new Page(1, 10);
        List<Device> devices = new ArrayList<Device>();
        pageSize.setRecords(devices);
        pageSize.setPageNo(2);
        pageSize.setPageSize(20);
        pageSize.setBegin(1);
        pageSize.getBegin();
        pageSize.setTotalPage(50);
        pageSize.setTotalRecord(100);
        pageSize.toString();
    }
    
}
