/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月2日 上午9:43:44
* 创建作者：范立松
* 文件名称：IotServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.iot.service.impl;

import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.freeauth.util.FreeAuthClient;
import com.awifi.np.biz.api.client.dbcenter.iot.util.IotClient;
import com.awifi.np.biz.common.base.model.Page;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ IotClient.class })
@SuppressWarnings({ "rawtypes", "unchecked" })
public class IotServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private IotServiceImpl iotServiceImpl;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(IotClient.class);
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetDeviceAreaList() throws Exception {
        PowerMockito.when(IotClient.countIotByParam(anyObject())).thenReturn(1);
        Page page = new Page<>();
        page.setPageSize(10);
        iotServiceImpl.getIotList(page, anyObject());
    }

    /**
     * 测试根据区域id删除设备区域信息以及设备与区域关联关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveIotByIds() throws Exception {
        List<String> idList = new ArrayList<>();
        idList.add("1");
        iotServiceImpl.removeIotByIds(idList);
    }

}
