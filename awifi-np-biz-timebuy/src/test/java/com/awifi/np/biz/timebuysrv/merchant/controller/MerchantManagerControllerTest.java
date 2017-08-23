/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月28日 下午1:42:55
* 创建作者：尤小平
* 文件名称：MerchantManagerControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.controller;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantManagerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class MerchantManagerControllerTest {
    /**
     * MerchantManagerController
     */
    @InjectMocks
    private MerchantManagerController controller;

    /**
     * MerchantManagerService
     */
    @Mock
    private MerchantManagerService merchantManagerService;

    /**
     * request
     */
    private MockHttpServletRequest request;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月28日 下午2:05:58
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月28日 下午2:06:17
     */
    @After
    public void tearDown() throws Exception {
        controller = null;
    }

    /**
     * 测试新增.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月28日 下午2:06:41
     */
    @Test
    public void testAdd() throws Exception {
        request.setParameter("uid", "uid2");
        request.setParameter("mid", "mid2");
        request.setParameter("uname", "uname2");
        request.setParameter("type", "type");

        Mockito.when(merchantManagerService.insert(any(MerchantManager.class))).thenReturn(true);
        controller.setMerchantManagerService(merchantManagerService);

        Map actual = controller.add(request);
        Assert.assertNotNull(actual);
        Assert.assertEquals(true, actual.get("data"));
        Mockito.verify(merchantManagerService).insert(any(MerchantManager.class));
    }

    /**
     * 测试修改管理员.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 上午10:55:07
     */
    @Test
    public void testUpdate() throws Exception {
        String id = "1";
        request.setParameter("uname", "uname");

        Mockito.when(merchantManagerService.update(any(MerchantManager.class))).thenReturn(true);
        controller.setMerchantManagerService(merchantManagerService);

        Map actual = controller.update(id, request);
        Assert.assertNotNull(actual);
        Assert.assertEquals(true, actual.get("data"));
        Mockito.verify(merchantManagerService).update(any(MerchantManager.class));
    }

    /**
     * 测试删除管理员.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 上午10:55:56
     */
    @Test
    public void testDelete() throws Exception {
        String id = "1";

        Mockito.when(merchantManagerService.deleteById(any(Long.class))).thenReturn(true);
        controller.setMerchantManagerService(merchantManagerService);

        Map actual = controller.delete(id);
        Assert.assertNotNull(actual);
        Assert.assertEquals(true, actual.get("data"));
        Mockito.verify(merchantManagerService).deleteById(any(Long.class));
    }

    /**
     * 测试根据id获取管理员信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 上午10:56:15
     */
    @Test
    public void testGetMerchantManagerById() throws Exception {
        String id = "2";

        MerchantManager value = new MerchantManager();
        Mockito.when(merchantManagerService.queryById(any(Long.class))).thenReturn(value);
        controller.setMerchantManagerService(merchantManagerService);

        Map actual = controller.getMerchantManagerById(id);
        Assert.assertNotNull(actual);
        Assert.assertEquals(value, actual.get("data"));
        Mockito.verify(merchantManagerService).queryById(any(Long.class));
    }

    /**
     * 测试获取管理员列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 上午10:56:36
     */
    @Test
    public void testGetListByMerchantManager() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("uid", "uid");
        request.setParameter("mid", "mid");
        request.setParameter("uname", "uname");
        request.setParameter("type", "type");

        List<MerchantManager> list = null;
        Mockito.when(merchantManagerService.getListByMerchantManager(any(MerchantManager.class))).thenReturn(list);
        controller.setMerchantManagerService(merchantManagerService);

        Map actual = controller.getListByMerchantManager(request);
        Assert.assertNotNull(actual);
        Assert.assertEquals(null, actual.get("data"));
        Mockito.verify(merchantManagerService).getListByMerchantManager(any(MerchantManager.class));
    }

    /**
     * 测试获取管理员分页列表.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月2日 上午10:57:00
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetListByParams() throws Exception {
        request.setParameter("id", "1");
        request.setParameter("uid", "uid");
        request.setParameter("mid", "mid");
        request.setParameter("uname", "uname");
        request.setParameter("type", "type");
        request.setParameter("pageSize", "20");

        Page<MerchantManager> list = null;
        Mockito.when(merchantManagerService.getListByParams(any(MerchantManager.class), any(Page.class)))
                .thenReturn(list);
        controller.setMerchantManagerService(merchantManagerService);

        Map actual = controller.getListByParams(request);
        Assert.assertNotNull(actual);
        Assert.assertEquals(null, actual.get("data"));
        Mockito.verify(merchantManagerService).getListByParams(any(MerchantManager.class), any(Page.class));
    }
}
