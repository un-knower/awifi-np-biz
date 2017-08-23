package com.awifi.np.biz.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月9日 下午3:38:12
 * 创建作者：周颖
 * 文件名称：FormatUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
public class FormatUtilTest {

    /**被测试类*/
    @InjectMocks
    private FormatUtil formatUtil; 
    
    /**mock*/
    private MockHttpServletRequest httpRequest;
    
    /***
     * 初始化
     * @author 周颖  
     * @date 2017年1月9日 下午3:51:35
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
    }
    
    /**
     * 测试请求参数格式化方法
     * @author 周颖  
     * @date 2017年1月9日 下午3:51:46
     */
    @Test
    public void test() {
        String result = formatUtil.formatRequestParam(httpRequest);
        Assert.assertNotNull(result);
    }
    
    /**
     * 测试MWS
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:11:36
     */
    @Test
    public void testFormatBelongMWS(){
        formatUtil.formatBelongToByProjectId(501);
    }
    
    /**
     * 测试MSP
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:11:36
     */
    @Test
    public void testFormatBelongMSP(){
        formatUtil.formatBelongToByProjectId(504);
    }
    
    /**
     * 测试MWH
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:11:36
     */
    @Test
    public void testFormatBelongMWH(){
        formatUtil.formatBelongToByProjectId(507);
    }
    
    /**
     * 测试TOE
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:11:36
     */
    @Test
    public void testFormatBelongTOE(){
        formatUtil.formatBelongToByProjectId(500);
    }
    
    /**
     * 测试瘦AP
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplainFitAP(){
        formatUtil.formatEntityType(42);
    }
    
    /**
     * 测试胖AP
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplainFatAP(){
        formatUtil.formatEntityType(31);
    }
    
    /**
     * 测试AC
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplainAC(){
        formatUtil.formatEntityType(21);
    }
    
    /**
     * 测试BAS
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplainBAS(){
        formatUtil.formatEntityType(11);
    }
    
    /**
     * 测试GPON
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplainGPON(){
        formatUtil.formatEntityType(32);
    }
    
    /**
     * 测试EPON
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplainEPON(){
        formatUtil.formatEntityType(34);
    }
    
    /**
     * 测试二合一
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplaintow(){
        formatUtil.formatEntityType(36);
    }
    
    /**
     * 测试三合一
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:14:06
     */
    @Test
    public void testEntityTypeToExplainthree(){
        formatUtil.formatEntityType(37);
    }
    
    /**
     * 测试离线
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:19:22
     */
    @Test
    public void testDeviceStatusoffline(){
        formatUtil.formatDeviceStatus(0);
    }
    
    /**
     * 测试离线
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:19:22
     */
    @Test
    public void testDeviceStatusToExplainonine(){
        formatUtil.formatDeviceStatus(1);
    }
    
    /**
     * 测试锁定/冻结
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:19:22
     */
    @Test
    public void testDeviceStatusToExplainlock(){
        formatUtil.formatDeviceStatus(2);
    }
    
    /**
     * 测试作废/注销
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:19:22
     */
    @Test
    public void testDeviceStatusinvalid(){
        formatUtil.formatDeviceStatus(9);
    }
    
    /**
     * 测试未知状态
     * @author 亢燕翔  
     * @date 2017年3月22日 上午10:19:22
     */
    @Test
    public void testDeviceStatusunknown(){
        formatUtil.formatDeviceStatus(10);
    } 
    
    /**
     * 销售点二级分类转换 null
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreTypeDsp(){
        String value = formatUtil.storeTypeDsp(null);
        Assert.assertNotNull(value);
    }
    
    /**
     * 销售点二级分类转换  专营店
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreTypeDspOne(){
        String value = formatUtil.storeTypeDsp(1);
        Assert.assertNotNull(value);
    }
    
    /**
     * 销售点二级分类转换  自有厅
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreTypeDspTwo(){
        String value = formatUtil.storeTypeDsp(2);
        Assert.assertNotNull(value);
    }
    
    /**
     * 销售点二级分类转换  其他
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreTypeDspThree(){
        String value = formatUtil.storeTypeDsp(3);
        Assert.assertNotNull(value);
    }
    
    /**
     * 销售点二级分类转换  默认
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreTypeDspOther(){
        String value = formatUtil.storeTypeDsp(4);
        Assert.assertNotNull(value);
    }
    
    /**
     * 自有厅级别  null
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreLevelDspNull(){
        String value = formatUtil.storeLevelDsp(null);
        Assert.assertNotNull(value);
    }
    
    /**
     * 自有厅级别  1级
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreLevelDspOne(){
        String value = formatUtil.storeLevelDsp(1);
        Assert.assertNotNull(value);
    }
    /**
     * 自有厅级别  2级
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreLevelDspTwo(){
        String value = formatUtil.storeLevelDsp(2);
        Assert.assertNotNull(value);
    }
    /**
     * 自有厅级别  3级
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreLevelDspThree(){
        String value = formatUtil.storeLevelDsp(3);
        Assert.assertNotNull(value);
    }
    /**
     * 自有厅级别 4级
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreLevelDspFour(){
        String value = formatUtil.storeLevelDsp(4);
        Assert.assertNotNull(value);
    }
    /**
     * 自有厅级别  5级
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreLevelDspFive(){
        String value = formatUtil.storeLevelDsp(5);
        Assert.assertNotNull(value);
    }
    /**
     * 自有厅级别  默认
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreLevelDspOther(){
        String value = formatUtil.storeLevelDsp(6);
        Assert.assertNotNull(value);
    }
    
    /**
     * 专营店星级  null
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreStarDspNull(){
        String value = formatUtil.storeStarDsp(null);
        Assert.assertNotNull(value);
    }
    
    /**
     * 专营店星级  1星
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreStarDspOne(){
        String value = formatUtil.storeStarDsp(1);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店星级  2星
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreStarDspTwo(){
        String value = formatUtil.storeStarDsp(2);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店星级  3星
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreStarDspThree(){
        String value = formatUtil.storeStarDsp(3);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店星级 4星
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreStarDspFour(){
        String value = formatUtil.storeStarDsp(4);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店星级  5星
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreStarDspFive(){
        String value = formatUtil.storeStarDsp(5);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店星级  默认
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreStarDspOther(){
        String value = formatUtil.storeStarDsp(6);
        Assert.assertNotNull(value);
    }
    
    /**
     * 专营店类别  null
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreScopeDspNull(){
        String value = formatUtil.storeScopeDsp(null);
        Assert.assertNotNull(value);
    }
    
    /**
     * 专营店类别  社区店
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreScopeDspOne(){
        String value = formatUtil.storeScopeDsp(1);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店类别  商圈店
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreScopeDspTwo(){
        String value = formatUtil.storeScopeDsp(2);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店类别  旗舰店
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreScopeDspThree(){
        String value = formatUtil.storeScopeDsp(3);
        Assert.assertNotNull(value);
    }
    /**
     * 专营店类别  其他
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreScopeDspFour(){
        String value = formatUtil.storeScopeDsp(4);
        Assert.assertNotNull(value);
    }
    
    /**
     * 专营店类别  默认
     * @author 周颖  
     * @date 2017年3月22日 下午1:50:03
     */
    @Test
    public void testStoreScopeDspOther(){
        String value = formatUtil.storeScopeDsp(6);
        Assert.assertNotNull(value);
    }
    
    /** 
     * 接入方式 空
     * @author 周颖  
     * @date 2017年3月22日 下午2:19:13
     */
    @Test
    public void testConnectTypeDsp(){
        String value = formatUtil.connectTypeDsp(null);
        Assert.assertNotNull(value);
    }
    
    /** 
     * 接入方式 Chinanet接入
     * @author 周颖  
     * @date 2017年3月22日 下午2:19:13
     */
    @Test
    public void testConnectTypeDspChinanet(){
        String value = formatUtil.connectTypeDsp("chinanet");
        Assert.assertNotNull(value);
    }
    
    /** 
     * 接入方式 胖AP接入
     * @author 周颖  
     * @date 2017年3月22日 下午2:19:13
     */
    @Test
    public void testConnectTypeDspFatAp(){
        String value = formatUtil.connectTypeDsp("fatAp");
        Assert.assertNotNull(value);
    }
    
    /** 
     * 接入方式 定制光猫接入
     * @author 周颖  
     * @date 2017年3月22日 下午2:19:13
     */
    @Test
    public void testConnectTypeDspOptical(){
        String value = formatUtil.connectTypeDsp("optical");
        Assert.assertNotNull(value);
    }
    
    /** 
     * 接入方式 其他
     * @author 周颖  
     * @date 2017年3月22日 下午2:19:13
     */
    @Test
    public void testConnectTypeDspOther(){
        String value = formatUtil.connectTypeDsp("others");
        Assert.assertNotNull(value);
    }
    
    /**
     * 静态用户类型转中文 访客
     * @author 周颖  
     * @date 2017年3月22日 下午2:30:57
     */
    @Test
    public void testUserTypeDspNull(){
        String value = formatUtil.userTypeDsp(null);
        Assert.assertNotNull(value);
    }
    
    /**
     * 静态用户类型转中文 普通员工
     * @author 周颖  
     * @date 2017年3月22日 下午2:30:57
     */
    @Test
    public void testUserTypeDspOne(){
        String value = formatUtil.userTypeDsp(1);
        Assert.assertNotNull(value);
    }
    
    /**
     * 静态用户类型转中文 VIP客户
     * @author 周颖  
     * @date 2017年3月22日 下午2:30:57
     */
    @Test
    public void testUserTypeDspTwo(){
        String value = formatUtil.userTypeDsp(2);
        Assert.assertNotNull(value);
    }
    
    /**
     * 静态用户类型转中文 终端体验区
     * @author 周颖  
     * @date 2017年3月22日 下午2:30:57
     */
    @Test
    public void testUserTypeDspThree(){
        String value = formatUtil.userTypeDsp(3);
        Assert.assertNotNull(value);
    }
    
    /**
     * 静态用户类型转中文 访客
     * @author 周颖  
     * @date 2017年3月22日 下午2:30:57
     */
    @Test
    public void testUserTypeDspOther(){
        String value = formatUtil.userTypeDsp(4);
        Assert.assertNotNull(value);
    }
    
    /**
     * 地区全称
     * @author 周颖  
     * @date 2017年3月22日 下午2:36:01
     */
    @Test
    public void testLocationFullName(){
        String value = formatUtil.locationFullName("province", "city", "area");
        Assert.assertNotNull(value);
    }
    
    /**
     * 地区全称 null
     * @author 周颖  
     * @date 2017年3月22日 下午2:36:01
     */
    @Test
    public void testLocationFullNameNull(){
        String value = formatUtil.locationFullName("", "", "");
        Assert.assertNotNull(value);
    }
    
    /**
     * 黑名单匹配规则转化 null
     * @author 周颖  
     * @date 2017年3月22日 下午2:38:42
     */
    @Test
    public void testMatchRuleDsp(){
        String value = formatUtil.matchRuleDsp(null);
        Assert.assertNull(value);
    }
    
    /**
     * 黑名单匹配规则转化 精确
     * @author 周颖  
     * @date 2017年3月22日 下午2:38:42
     */
    @Test
    public void testMatchRuleDspOne(){
        String value = formatUtil.matchRuleDsp(1);
        Assert.assertNotNull(value);
    }
    
    /**
     * 黑名单匹配规则转化 模糊
     * @author 周颖  
     * @date 2017年3月22日 下午2:38:42
     */
    @Test
    public void testMatchRuleDspTwo(){
        String value = formatUtil.matchRuleDsp(2);
        Assert.assertNotNull(value);
    }
    
    /**
     * 黑名单匹配规则转化 null
     * @author 周颖  
     * @date 2017年3月22日 下午2:38:42
     */
    @Test
    public void testMatchRuleDspOther(){
        String value = formatUtil.matchRuleDsp(3);
        Assert.assertNotNull(value);
    }
}