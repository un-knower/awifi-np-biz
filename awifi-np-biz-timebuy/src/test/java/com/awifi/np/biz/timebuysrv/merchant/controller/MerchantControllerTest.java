/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月9日 下午5:12:00
* 创建作者：尤小平
* 文件名称：MerchantControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.controller;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.timebuysrv.merchant.service.PubMerchantService;
import org.junit.After;
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

import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MerchantClient.class})
public class MerchantControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private MerchantController controller;

    /**
     * PubMerchantService
     */
    @Mock(name = "pubMerchantService")
    private PubMerchantService pubMerchantService;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月9日 下午5:30:18
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MerchantClient.class);
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月9日 下午5:30:23
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试根据id获取商户信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月9日 下午5:30:27
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testGetMerchantById() throws Exception{
        String id = "10";

        Merchant merchant = new Merchant();
        merchant.setId(Long.valueOf(id));
        PowerMockito.when(MerchantClient.getByIdCache(any(Long.class))).thenReturn(merchant);

        Map actual = controller.getMerchantById(id);

        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
        MerchantClient.getByIdCache(any(Long.class));
    }
}
