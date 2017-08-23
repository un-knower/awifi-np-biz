package com.awifi.np.biz.devsrv.fatap.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubPlatform;
import com.awifi.np.biz.api.client.dbcenter.location.service.LocationApiService;
import com.awifi.np.biz.common.redis.command.RedisService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;
/**
 * 
 * @ClassName: FatapUtilTest
 * @Description: 定制终端公共方法类 单元测试
 * @author wuqia
 * @date 2017年6月12日 下午4:53:35
 *
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class})
public class FatapUtilTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private FatapUtil fatapUtil;
    /** locationApiService 业务层 */
    @Mock(name = "locationApiService")
    private LocationApiService locationApiService;
    /** redis业务层 */
    @Mock(name = "redisService")
    private RedisService redisService;
    /** 初始化 */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean("locationApiService"))
                .thenReturn(locationApiService);
        PowerMockito.when(BeanUtil.getBean("redisService"))
                .thenReturn(redisService);
    }
    /**
     * 省分平台返回数据  省市区编码转名称
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:15:02
     */
    @Test
    public void testGetCenterPubNameFromId() throws Exception {
        CenterPubPlatform cpp = new CenterPubPlatform();
        cpp.setCity(32);
        cpp.setProvince(22);
        cpp.setCounty(123);
        fatapUtil.getCenterPubNameFromId(cpp);
    }
    
    /**
     * 根据层级获取目录格式
     *  异常/参数
     * @author 伍恰  
     * @date 2017年6月29日 上午10:09:16
     */
    @Test
    public void testGetPathFomat(){
        System.out.println(fatapUtil.getPathFomat(3));
    }
    
    /**
     * 是否是Linux系统
     *  异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午8:13:54
     */
    @Test
    public void testIsLinux(){
        fatapUtil.isLinux();
    }
    
    /**
     * 是否是Windows系统
     *  异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午8:13:54
     */
    @Test
    public void testIsWindows(){
        fatapUtil.isWindows();
    }

}
