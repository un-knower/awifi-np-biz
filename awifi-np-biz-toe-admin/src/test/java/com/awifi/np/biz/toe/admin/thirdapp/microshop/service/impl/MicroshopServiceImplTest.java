/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 上午9:33:46
* 创建作者：方志伟
* 文件名称：MicroshopServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.toe.admin.thirdapp.microshop.dao.MicroshopDao;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.model.Microshop;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*"})
public class MicroshopServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private MicroshopServiceImpl microshopServiceImpl;
    /**持久层*/
    @Mock(name = "microshopDao")
    private MicroshopDao microshopDao;
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试通过商户id获取微旺铺表信息
     * @author 方志伟  
     * @date 2017年6月20日 上午9:47:10
     */
    @Test
    public void testGetByMerchantId(){
//        Microshop microshop = new Microshop();
//        microshop.setSpreadModel(2);
//        when(microshopDao.getByMerchantId(anyObject())).thenReturn(microshop);
//        microshopServiceImpl.getByMerchantId(1L);
    }
    
    /**
     * 通过商户id获取微旺铺表信息
     * @author 方志伟  
     * @date 2017年6月21日 下午6:27:25
     */
    @Test
    public void testGetByMerchantIdNull(){
//        microshopServiceImpl.getByMerchantId(1L);
    }
}
