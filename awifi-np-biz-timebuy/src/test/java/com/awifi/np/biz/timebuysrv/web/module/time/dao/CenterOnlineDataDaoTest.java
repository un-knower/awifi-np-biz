/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月21日 下午3:18:58
* 创建作者：余红伟
* 文件名称：CenterOnlineDataDaoTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.awifi.np.biz.timebuysrv.web.module.time.model.CenterOnlineDataObject;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "classpath:spring/spring-context.xml"
//})
public class CenterOnlineDataDaoTest {

    private Logger logger = LoggerFactory.getLogger(CenterOnlineDataDaoTest.class);
    
    /**
     * 被测试类
     */
    @Autowired
    private CenterOnlineDataDao centerOnlineDataDao;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * 新增推送数据记录
     * 
     * @author 余红伟 
     * @date 2017年4月21日 下午4:12:02
     */
//    @Test
    public void testAdd() {
        CenterOnlineDataObject object = new CenterOnlineDataObject();
        object.setBroadbandAccount("dssss");
        object.setPkgNum("10");
        centerOnlineDataDao.add(object);
    }

}
