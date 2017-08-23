/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 22, 2017 5:11:31 PM
* 创建作者：季振宇
* 文件名称：MerchantControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.merchant.controller;

import static org.mockito.Matchers.anyString;

import java.util.Map;

import static org.mockito.Matchers.anyLong;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devicebindsrv.merchant.service.MerchantService;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ValidUtil.class})
public class MerchantControllerTest {
    
    /**被测试类*/
    @InjectMocks
    private MerchantController merchantController;
    
    /**商户服务*/
    @Mock(name = "merchantService")
    private MerchantService merchantService;
    
    /**toe用户服务*/
    @Mock(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(ValidUtil.class);
    }

    /**
     * 测试通过账号获取商户详情接口
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 5:14:23 PM
     */
    @Test
    public void testGetByUserName() throws Exception {
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyString(),anyString(),anyString());
        PowerMockito.when(toeUserService.getMerIdByUserName(anyString())).thenReturn(62L);
        
        Merchant merchant = new Merchant();
        merchant.setAccount("account");
        PowerMockito.when(merchantService.getById(anyLong())).thenReturn(merchant);
        
        Map<String,Object> result = merchantController.getByUserName("userName");
        
        Assert.assertNotNull(result);
    }

    /**
     * 测试通过账号获取商户详情接口
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 5:14:23 PM
     */
    @Test
    public void testGetByUserNameFalse() throws Exception {
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyString(),anyString(),anyString());
        PowerMockito.when(toeUserService.getMerIdByUserName(anyString())).thenReturn(null);
        
        Merchant merchant = new Merchant();
        merchant.setAccount("account");
        PowerMockito.when(merchantService.getById(anyLong())).thenReturn(merchant);
        
        Map<String,Object> result = merchantController.getByUserName("userName");
        
        Assert.assertNotNull(result);
    }
}
