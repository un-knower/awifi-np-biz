package com.awifi.np.biz.common.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.exception.ValidException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 上午8:49:53
 * 创建作者：亢燕翔
 * 文件名称：ValidUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, BeanUtil.class})
public class ValidUtilTest {

    /**被测试类*/
    @InjectMocks
    private ValidUtil validUtil;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
    }
    
    /**
     * 必填校验
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:05:13
     */
    @Test(expected=Exception.class)
    public void testValidRequired(){
        ValidUtil.valid("参数A", "", "required");
    }
    
    /**
     * 为false时不做校验
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:05:01
     */
    @Test
    public void testValidRequiredMap(){
        ValidUtil.valid("参数A", "", "{'required': false}");
    }
    
    /**
     * 数字校验
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:04:53
     */
    @Test
    public void testValidNumeric(){
        ValidUtil.valid("参数A", "0", "numeric");
    }
    
    /**
     * 数字校验
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:04:53
     */
    @Test
    public void testValidNumericMap(){
        ValidUtil.valid("参数A", "1", "{'numeric':true}");
    }
    
    /**
     * 数字校验含范围比较
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:04:53
     */
    @Test
    public void testValidScope(){
        ValidUtil.valid("参数A", "2", "{'numeric':{'min':1,'max':3}}");
    }
    
    /**
     * 正则校验
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:04:53
     */
    @Test(expected=ValidException.class)
    public void testValidRegex(){
        ValidUtil.valid("参数A", "989096C11D9", "{'required':true, 'regex':'^[0-9A-F]{12}$'}");
    }
    
    
    /**
     * 数组内部不允许存在空值(null|"")的校验
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:04:53
     */
    @Test(expected=ValidException.class)
    public void testValidListNull(){
        Object[] objsNull = {};
        ValidUtil.valid("参数A", objsNull, "arrayNotBlank");
        ValidUtil.valid("参数A", objsNull, "{'arrayNotBlank':true}");
        ValidUtil.valid("参数A", objsNull, "{'arrayNotBlank':false}");
    }
    
    /**
     * 数组内部不允许存在空值(null|"")的校验
     * @author 亢燕翔  
     * @date 2017年3月23日 上午9:04:53
     */
    @Test(expected=ValidException.class)
    public void testValidList(){
        Object[] objs = {"15","","55"};
        ValidUtil.valid("参数A", objs, "arrayNotBlank");
        ValidUtil.valid("参数A", objs, "{'arrayNotBlank':true}");
        ValidUtil.valid("参数A", objs, "{'arrayNotBlank':false}");
    }
    
}
