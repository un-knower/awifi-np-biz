package com.awifi.np.biz.common.security.permission.thread;

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

import com.awifi.np.biz.common.security.permission.service.PermissionService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午2:33:24
 * 创建作者：亢燕翔
 * 文件名称：PermissionThreadTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class})
public class PermissionThreadTest {

    /**被测试类*/
    @InjectMocks
    private PermissionThread permissionThread;
    
    /**权限业务层*/
    @Mock(name = "permissionService")
    private PermissionService permissionService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(permissionService);
    }
    
    /**
     * 测试创建线程
     * @author 亢燕翔  
     * @date 2017年3月23日 上午11:04:27
     */
    @Test
    public void testPermissionThread(){
        new PermissionThread();
        new PermissionThread("xxx", "xxx");
    }
    
    /**
     * 测试启动线程
     * @author 亢燕翔  
     * @date 2017年3月23日 上午11:04:38
     */
    @Test
    public void testRun(){
        permissionThread.run();
    }
    
}
