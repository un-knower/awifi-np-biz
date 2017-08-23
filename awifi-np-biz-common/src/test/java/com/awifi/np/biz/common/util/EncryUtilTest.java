package com.awifi.np.biz.common.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 下午1:54:18
 * 创建作者：周颖
 * 文件名称：EncryUtilTest.java
 * 版本：  v1.0
 * 功能：加密测试类
 * 修改记录：
 */
@SuppressWarnings("static-access")
public class EncryUtilTest {

    /**
     * 测试类
     */
    @InjectMocks
    private EncryUtil encryUtil;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 下午2:10:54
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * md5加密 为空
     * @author 周颖  
     * @date 2017年3月23日 下午2:11:02
     */
    @Test
    public void testGetMd5StrNull() {
        encryUtil.getMd5Str(null);
    }
    
    /**
     * md5加密 不为空
     * @author 周颖  
     * @date 2017年3月23日 下午2:11:02
     */
    @Test
    public void testGetMd5Str() {
        encryUtil.getMd5Str("abc");
    }

    /**
     * 是否超时
     * @author 周颖  
     * @date 2017年3月23日 下午2:11:30
     */
    @Test
    public void testIsTimeoutTrue() {
        encryUtil.isTimeout("1490248995", 10L);
    }
    
    /**
     * 生成token
     * @author 周颖  
     * @date 2017年3月23日 下午2:11:45
     */
    @Test
    public void testGenerateToken() {
        encryUtil.generateToken("code", "key", "1490248995");
    }
}
