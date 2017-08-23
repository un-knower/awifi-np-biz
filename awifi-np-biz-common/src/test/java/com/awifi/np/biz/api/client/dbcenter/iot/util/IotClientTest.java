/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月2日 上午10:49:50
* 创建作者：范立松
* 文件名称：IotClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.iot.util;

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

import com.awifi.np.biz.api.client.dbcenter.iot.service.IotApiService;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BeanUtil.class })
public class IotClientTest {

    /**被测试类*/
    @InjectMocks
    private IotClient iotClient;

    /**
     * 配置服务
     */
    @Mock(name = "freeAuthApiService")
    private IotApiService iotApiService;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyObject())).thenReturn(iotApiService);
    }

    /**
     * 测试分页查询物联网设备列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetIotList() throws Exception {
        iotClient.getIotList(anyObject());
    }

    /**
     * 测试删除物联网设备信息
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveIotByIds() throws Exception {
        iotClient.removeIotByIds(anyObject());
    }

    /**
     * 测试获取bean对象
     * @author 范立松  
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetFreeAuthApiService() {
        iotClient.getIotApiService();
    }

}
