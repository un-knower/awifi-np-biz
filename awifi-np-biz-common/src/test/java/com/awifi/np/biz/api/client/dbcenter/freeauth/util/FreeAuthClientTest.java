/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午5:13:59
* 创建作者：范立松
* 文件名称：FreeAuthClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.freeauth.util;

import static org.mockito.Matchers.anyObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.freeauth.service.FreeAuthApiService;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BeanUtil.class })
public class FreeAuthClientTest {

    /**被测试类*/
    @InjectMocks
    private FreeAuthClient freeAuthClient;

    /**
     * 配置服务
     */
    @Mock(name = "freeAuthApiService")
    private FreeAuthApiService freeAuthApiService;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyObject())).thenReturn(freeAuthApiService);
    }

    /**
     * 测试添加设备区域信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceArea() throws Exception {
        freeAuthClient.addDeviceArea(anyObject());
    }

    /**
     * 测试修改设备区域信息
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testUpdateDeviceArea() throws Exception {
        freeAuthClient.updateDeviceArea(anyObject());
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetDeviceAreaList() throws Exception {
        freeAuthClient.getDeviceAreaList(anyObject());
    }

    /**
     * 测试根据区域id删除设备区域信息以及设备与区域关联关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveDeviceAreaById() throws Exception {
        freeAuthClient.removeDeviceAreaById(anyObject());
    }

    /**
     * 测试批量添加设备与区域关联信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceAreaRel() throws Exception {
        freeAuthClient.addDeviceAreaRel(anyObject());
    }

    /**
     * 测试根据设备id删除设备与区域关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveRelByDevId() throws Exception {
        freeAuthClient.removeRelByDevId(anyObject());
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetRelListByAreaId() throws Exception {
        freeAuthClient.getRelListByAreaId(anyObject());
    }

    /**
     * 测试按条件查询设备区域记录数
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testQueryAreaCountByParam() throws Exception {
        freeAuthClient.queryAreaCountByParam(anyObject());
    }

    /**
     * 测试按条件查询设备与区域关联记录数
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testQueryRelCountByParam() throws Exception {
        freeAuthClient.queryRelCountByParam(anyObject());
    }
    
    /**
     * 测试分页查询设备与区域关联时可选择的设备列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午11:17:05
     */
    @Test
    public void testGetChooseableDeviceList() throws Exception {
        freeAuthClient.getChooseableDeviceList(anyObject());
    }
    
    /**
     * 测试按条件查询设备与区域关联时可选择的设备数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午11:17:05
     */
    @Test
    public void testQueryChooseableDeviceCount() throws Exception {
        freeAuthClient.queryChooseableDeviceCount(anyObject());
    }

    /**
     * 测试获取bean对象
     * @author 范立松  
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetFreeAuthApiService() {
        freeAuthClient.getFreeAuthApiService();
    }

}
