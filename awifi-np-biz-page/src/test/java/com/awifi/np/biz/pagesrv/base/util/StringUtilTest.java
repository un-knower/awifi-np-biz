package com.awifi.np.biz.pagesrv.base.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月30日 下午2:35:46
 * 创建作者：许尚敏
 * 文件名称：StringUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonUtil.class,RedisUtil.class,SysConfigUtil.class,ExceptionUtil.class,ValidUtil.class})
public class StringUtilTest {
    /**被测试类*/
    @InjectMocks
    private StringUtil stringUtil;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ExceptionUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }
    
    /**
     * DES加密方法测试
     * @author 许尚敏  
     * @date 2017年6月30日 下午3:00:50
     */
    @Test
    public void testEncryptByDes() {

    }

    /**
     * DES解密方法测试
     * @author 许尚敏  
     * @date 2017年6月30日 下午3:00:50
     */
    @Test
    public void testDecryptByDes() {
  
    }

    /**
     * 字符串转16进制字符串测试
     * @author 许尚敏  
     * @date 2017年6月30日 下午3:00:50
     */
    @SuppressWarnings("static-access")
    @Test
    public void testHexStringToString() {
        stringUtil.hexStringToString("aaaa");
    }

}
