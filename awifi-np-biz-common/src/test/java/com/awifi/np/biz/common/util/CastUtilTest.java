package com.awifi.np.biz.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 上午10:37:46
 * 创建作者：周颖
 * 文件名称：CastUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
public class CastUtilTest {

    /**
     * 测试类
     */
    @InjectMocks
    private CastUtil castUtil;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * list转string
     * @author 周颖  
     * @date 2017年3月22日 上午10:45:56
     */
    
    @Test
    public void testListToString() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        String value = castUtil.listToString(list, ',');
        Assert.assertEquals("a,b", value);
    }

    /**
     * 将String数组转化为Long数组 为空
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongArrayStringArrayNull() {
        String[] strs = {};
        Long[] value = castUtil.toLongArray(strs);
        Assert.assertNull(value);
    }
    
    /**
     * 将String数组转化为Long数组
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongArrayStringArray() {
        String[] strs = {"1","2","","3"};
        Long[] value = castUtil.toLongArray(strs);
        Assert.assertNotNull(value);
    }

    /**
     * 将String数组转化为Long数组 为空
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongArraySetOfLongNull() {
        Set<Long> strs = new HashSet<Long>();
        Long[] value = castUtil.toLongArray(strs);
        Assert.assertNull(value);
    }
    
    /**
     * 将String数组转化为Long数组 不为空
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongArraySetOfLong() {
        Set<Long> strs = new HashSet<Long>();
        strs.add(1L);
        strs.add(null);
        strs.add(2L);
        Long[] value = castUtil.toLongArray(strs);
        Assert.assertNotNull(value);
    }

    /**
     * 转Integer类型 为空
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerNull() {
        Integer value = castUtil.toInteger(null);
        Assert.assertNull(value);
    }
    
    /**
     * 转Integer类型 Integer
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerInteger() {
        Integer value = castUtil.toInteger(1);
        Assert.assertNotNull(value);
    }

    /**
     * 转Integer类型 double
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerDouble() {
        Integer value = castUtil.toInteger(1.1);
        Assert.assertNotNull(value);
    }
    
    /**
     * 转Integer类型 Long
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerLong() {
        Integer value = castUtil.toInteger(1L);
        Assert.assertNotNull(value);
    }
    
    /**
     * 转Integer类型 Float
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerFloat() {
        Integer value = castUtil.toInteger((float) 1.1);
        Assert.assertNotNull(value);
    }
    
    /** 转Integer类型 String 为空
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerStringNull() {
        Integer value = castUtil.toInteger("");
        Assert.assertNull(value);
    }
    
    /** 转Integer类型 String
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerString() {
        Integer value = castUtil.toInteger("2");
        Assert.assertNotNull(value);
    }
   
    /** 转Integer类型  boolean
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToIntegerOther() {
        Integer value = castUtil.toInteger(true);
        Assert.assertNull(value);
    }
    
    /** 转Long类型  null
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLong() {
        Long value = castUtil.toLong(null);
        Assert.assertNull(value);
    }
    
    /** 转Long类型  Long
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongLong() {
        Long value = castUtil.toLong(1L);
        Assert.assertNotNull(value);
    }
    
    /** 转Long类型  Integer
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongInteger() {
        Long value = castUtil.toLong(1);
        Assert.assertNotNull(value);
    }
    
    /** 转Long类型  Double
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongDouble() {
        Long value = castUtil.toLong(1.1);
        Assert.assertNotNull(value);
    }
    
    /** 转Long类型  String
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongString() {
        Long value = castUtil.toLong("1");
        Assert.assertNotNull(value);
    }
    
    /** 转Long类型  String
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongStringNull() {
        Long value = castUtil.toLong("");
        Assert.assertNull(value);
    }
    
    /** 转Long类型  boolean
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToLongOther() {
        Long value = castUtil.toLong(true);
        Assert.assertNull(value);
    }

    /** 转Double类型  null
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToDouble() {
        Double value = castUtil.toDouble(null);
        Assert.assertNull(value);
    }
    
    /** 转Double类型  string
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToDoubleString() {
        Double value = castUtil.toDouble("1.2");
        Assert.assertNotNull(value);
    }
    
    /** 转Double类型  string null
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToDoubleStringNull() {
        Double value = castUtil.toDouble("");
        Assert.assertNull(value);
    }
    
    /** 转Double类型  boolean
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToDoubleOther() {
        Double value = castUtil.toDouble(true);
        Assert.assertNull(value);
    }

    /** 转Float类型  null
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToFloat() {
        Float value = castUtil.toFloat(null);
        Assert.assertNull(value);
    }
    
    /** 转Float类型  string
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToFloatString() {
        Float value = castUtil.toFloat("1.2");
        Assert.assertNotNull(value);
    }
    
    /** 转Float类型  string null
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToFloatStringNull() {
        Float value = castUtil.toFloat("");
        Assert.assertNull(value);
    }
    
    /** 转Float类型  boolean
     * @author 周颖  
     * @date 2017年3月22日 上午11:10:41
     */
    @Test
    public void testToFloatOther() {
        Float value = castUtil.toFloat(true);
        Assert.assertNull(value);
    }
    
    /**
     * 将String数组转化为Long数组
     * @author 亢燕翔  
     * @date 2017年3月23日 上午11:20:10
     */
    @Test
    public void testToLongArray(){
        Set<Long> set = new HashSet<Long>();
        set.add(10L);
        castUtil.toLongArray(set);
    }
    
    /**
     * 数据转为Double
     * @author 亢燕翔  
     * @date 2017年3月23日 上午11:20:26
     */
    @Test
    public void testtoDouble(){
        castUtil.toDouble("10");
    }
    
    /**
     * 数据转为Float
     * @author 亢燕翔  
     * @date 2017年3月23日 上午11:20:36
     */
    @Test
    public void testtoFloat(){
        castUtil.toFloat("10");
    }
}
