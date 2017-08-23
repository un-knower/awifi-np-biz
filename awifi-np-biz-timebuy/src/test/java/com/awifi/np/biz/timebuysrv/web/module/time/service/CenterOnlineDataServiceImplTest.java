/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月21日 下午2:45:10
* 创建作者：余红伟
* 文件名称：CenterOnlineDataServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.any;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.CenterOnlineDataDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.CenterOnlineDataObject;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;

public class CenterOnlineDataServiceImplTest {
    
    /**
     * 被测试类
     */
    private CenterVipUserServiceImpl centerOnlineDateServiceImpl;
    
    private CenterOnlineDataDao mockCenterOnlineDataDao;
    
    @Before
    public void setUp() throws Exception {
        centerOnlineDateServiceImpl = new CenterVipUserServiceImpl();
        mockCenterOnlineDataDao = Mockito.mock(CenterOnlineDataDao.class);
    }

    @After
    public void tearDown() throws Exception {
        centerOnlineDateServiceImpl = null;
        mockCenterOnlineDataDao = null;
    }

    @Test
    public void testAdd() {
        mockCenterOnlineDataDao.add(new CenterOnlineDataObject());
    }

}
