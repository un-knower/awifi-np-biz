/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午2:41:00
* 创建作者：余红伟
* 文件名称：VipUserControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;
import com.awifi.np.biz.timebuysrv.web.module.time.service.CenterVipUserServiceImpl;
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class VipUserControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private VipUserController vipUserController;
    
    @Mock
    private CenterVipUserServiceImpl centerVipUserDao;
    
    private MockHttpServletRequest request;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(MessageUtil.class);
    }

    @After
    public void tearDown() throws Exception {
    }
    /**
     * 测试VIP用户列表
     * @throws Exception
     * @author 余红伟 
     * @date 2017年4月25日 上午10:01:26
     */
    @Test
    public void testQueryListByParams() throws Exception{
        List<VipUserObject> list =new ArrayList<>();
        VipUserObject object = new VipUserObject();
        object.setId(90L);
        list.add(object);
        
        PowerMockito.when(centerVipUserDao.queryListByParam(any(Map.class))).thenReturn(list);
        vipUserController.setVipUserController(centerVipUserDao);
        request.setParameter("telephone","18317278712" );
        request.setParameter("pageNum", "1");
        request.setParameter("pageSize", "2");
       // Map<String, Object> map = vipUserController.queryListByParam("{\"pageNo\":1,\"pageSize\":15}",request);
      //  assertNotNull(map);
    }

}
