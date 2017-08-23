/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月23日 上午10:46:38
* 创建作者：尤小平
* 文件名称：MsMerchantControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchant.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mersrv.ms.merchant.service.MsMerchantService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JsonUtil.class, SysConfigUtil.class, ValidUtil.class, MerchantClient.class })
public class MsMerchantControllerTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private MsMerchantController controller;

    /**
     * MsMerchantService
     */
    @Mock(name = "msMerchantService")
    private MsMerchantService msMerchantService;

    /**
     * request
     */
    @Mock(name = "request")
    private HttpServletRequest request;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月23日 下午2:42:58
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("imgdomain");
    }

    /**
     * after.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月23日 下午2:43:23
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试微站商户信息展示.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月23日 下午2:42:50
     */
    @Test
    public void testGetMerchant() throws Exception {
        Merchant merchant = getMerchant();
        merchant.setMerchantName("testname");
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyString(), anyObject(), anyString());
        PowerMockito.when(msMerchantService.findMerchantInfoExclude9(anyLong())).thenReturn(merchant);
        PowerMockito.doReturn("imgdomain").when(SysConfigUtil.class, "getParamValue", anyString());

        Long merchantId = 1L;
        Map<String, Object> actual = controller.getMerchant(merchantId, request);

        System.out.println("actual=" + actual.get("data"));
        Assert.assertEquals("testname",
                ((Map<String, Object>) ((Map<String, Object>) actual.get("data")).get("merchant")).get("merchantName"));
        PowerMockito.verifyStatic();
        msMerchantService.findMerchantInfoExclude9(anyLong());
        SysConfigUtil.getParamValue(anyString());
    }

    /**
     * 测试微站商户信息展示.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月23日 下午2:42:41
     */
    @Test
    public void testGetMerchantForMerIsNull() throws Exception {
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyString(), anyObject(), anyString());
        PowerMockito.when(msMerchantService.findMerchantInfoExclude9(anyLong())).thenReturn(null);
        PowerMockito.doReturn("imgdomain").when(SysConfigUtil.class, "getParamValue", anyString());

        Long merchantId = 1L;
        Map<String, Object> actual = controller.getMerchant(merchantId, request);

        System.out.println("actual=" + actual.get("data"));
        Assert.assertNull(((Map<String, Object>) actual.get("data")).get("merchant"));
        PowerMockito.verifyStatic();
        msMerchantService.findMerchantInfoExclude9(anyLong());
        SysConfigUtil.getParamValue(anyString());
    }

    /**
     * 测试编辑微站商户信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月23日 下午2:42:24
     */
    @Test
    public void testEditMerchantForSecCode() throws Exception {
        Long merchantId = 1L;
        Map<String, Object> bodyParam = getParam();
        bodyParam.put("merchantName", "updatename");
        Merchant merchant = getMerchant();
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyString(), anyObject(), anyString());
        PowerMockito.when(MerchantClient.getById(anyLong())).thenReturn(merchant);
        PowerMockito.doNothing().when(MerchantClient.class, "update", anyObject(), anyString());
        PowerMockito.doReturn("imgdomain").when(SysConfigUtil.class, "getParamValue", anyString());

        merchant.setSecIndustryCode("secCode");
        Map<String, Object> actual = controller.editMerchant(merchantId, bodyParam);
        Assert.assertEquals("updatename",
                ((Merchant) ((Map<String, Object>) actual.get("data")).get("merchant")).getMerchantName());

        PowerMockito.verifyStatic();
        MerchantClient.getById(anyLong());
        MerchantClient.update(anyObject(), anyString());
        SysConfigUtil.getParamValue(anyString());

    }

    /**
     * 测试编辑微站商户信息.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月23日 下午2:41:50
     */
    @Test
    public void testEditMerchantForPriCode() throws Exception {
        Long merchantId = 1L;
        Map<String, Object> bodyParam = getParam();
        bodyParam.put("merchantName", "updatename");
        Merchant merchant = getMerchant();
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyString(), anyObject(), anyString());
        PowerMockito.when(MerchantClient.getById(anyLong())).thenReturn(merchant);
        PowerMockito.doNothing().when(MerchantClient.class, "update", anyObject(), anyString());
        PowerMockito.doReturn("imgdomain").when(SysConfigUtil.class, "getParamValue", anyString());

        merchant.setPriIndustryCode("priCode");
        Map<String, Object> actual = controller.editMerchant(merchantId, bodyParam);
        Assert.assertEquals("updatename",
                ((Merchant) ((Map<String, Object>) actual.get("data")).get("merchant")).getMerchantName());

        PowerMockito.verifyStatic();
        MerchantClient.getById(anyLong());
        MerchantClient.update(anyObject(), anyString());
        SysConfigUtil.getParamValue(anyString());

    }

    /**
     * 获取请求参数.
     * 
     * @return Map<String, Object>
     * @author 尤小平
     * @date 2017年6月23日 下午2:40:51
     */
    private Map<String, Object> getParam() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantName", "merchantName");
        param.put("remark", "remark");
        param.put("address", "address");
        param.put("telephone", "18912345678");
        param.put("openTime", "09:00");
        param.put("closeTime", "21:00");

        return param;
    }

    /**
     * 返回商户信息.
     * 
     * @return 商户信息
     * @author 尤小平
     * @date 2017年6月23日 下午2:41:20
     */
    private Merchant getMerchant() {
        Merchant merchant = new Merchant();
        merchant.setId(2L);
        merchant.setMerchantName("name");
        merchant.setRemark("remark");
        merchant.setAddress("address");
        merchant.setContactWay("13612345678");
        merchant.setOpenTime("09:00");
        merchant.setCloseTime("21:00");

        return merchant;
    }
}
