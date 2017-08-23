package com.awifi.np.biz.api.client.dbcenter.device.hotarea.util;

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

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.service.HotareaApiService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午1:05:59
 * 创建作者：亢燕翔
 * 文件名称：HotareaClientTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class})
public class HotareaClientTest {

    /**被测试类*/
    @InjectMocks
    private HotareaClient hotareaClient;
    
    /**热点业务层*/
    @Mock(name = "hotareaApiService")
    private HotareaApiService hotareaApiService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(hotareaApiService);
    }
    
    /**
     * 获取数据总数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 下午1:13:47
     */
    @Test
    public void testGetCountByParam() throws Exception{
        hotareaClient.getCountByParam("xxx");
    }
    
    /**
     * 获取列表
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 下午1:13:39
     */
    @Test
    public void testGetListByParam() throws Exception{
        hotareaClient.getListByParam("xxx");
    }
    
    /**
     * 批量添加热点
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 下午1:13:25
     */
    @Test
    public void testBatchAddRelation() throws Exception{
        hotareaClient.batchAddRelation("xxx");
    }
    
    /**
     * 删除热点
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 下午1:13:09
     */
    @Test
    public void testDeleteByDevMacs() throws Exception{
        hotareaClient.deleteByDevMacs("xxx");
    }
}
