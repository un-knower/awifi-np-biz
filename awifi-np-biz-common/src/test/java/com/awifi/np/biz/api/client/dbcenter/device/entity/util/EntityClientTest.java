package com.awifi.np.biz.api.client.dbcenter.device.entity.util;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.entity.service.EntityApiService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 上午11:21:33
 * 创建作者：亢燕翔
 * 文件名称：EntityClientTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class})
public class EntityClientTest {

    /**被测试类*/
    @InjectMocks
    private EntityClient entityClient;

    /**实体业务层*/
    @Mock(name = "entityApiService")
    private EntityApiService entityApiService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(entityApiService);
    }
 
    /**
     * 设备监控查询总数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:26:58
     */
    @Test
    public void testGetEntityInfoCountByMerId() throws Exception{
        entityClient.getEntityInfoCountByMerId("xxx");
    }
    
    /**
     * 设备监控列表
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:27:39
     */
    @Test
    public void testGetEntityInfoListByMerId() throws Exception{
        entityClient.getEntityInfoListByMerId("xxx");
    }
    
    /**
     * 编辑设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:28:23
     */
    @Test
    public void testUpdate() throws Exception{
        entityClient.update("xxx");
    }
    
}
