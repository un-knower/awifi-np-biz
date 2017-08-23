/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 21, 2017 2:54:52 PM
* 创建作者：季振宇
* 文件名称：DeviceControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.device.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.ms.util.MsCommonUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RandomUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devicebindsrv.device.service.DeviceInstallerService;
import com.awifi.np.biz.devicebindsrv.merchant.service.MerchantService;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ValidUtil.class,MsCommonUtil.class,SysConfigUtil.class,RandomUtil.class,PubUserAuth.class,UserAuthClient.class,StringUtils.class,DeviceClient.class,JsonUtil.class,MessageUtil.class,MerchantClient.class,FormatUtil.class,DeviceBusClient.class,CastUtil.class,EncryUtil.class})
public class DeviceControllerTest {
    
    /**被测试类*/
    @InjectMocks
    private DeviceController deviceController;
    
    /**商户服务*/
    @Mock(name = "merchantService")
    private MerchantService merchantService;
    
    /**toe用户服务*/
    @Mock(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**toe角色服务*/
    @Mock(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    
    /**设备-装维人员关系 服务*/
    @Mock(name = "deviceInstallerService")
    private DeviceInstallerService deviceInstallerService;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(FormatUtil.class);
        PowerMockito.mockStatic(DeviceBusClient.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.mockStatic(UserAuthClient.class);
        PowerMockito.mockStatic(PubUserAuth.class);
        PowerMockito.mockStatic(RandomUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MsCommonUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
    }

    /**
     * 测试校验设备以否已绑定接口
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 21, 2017 4:31:14 PM
     */
    @Test
    public void testCheckByMac() throws Exception {
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyObject(), anyObject(), anyObject());
        
        Map<String,Object> params = new HashMap<String,Object>();//参数
        params.put("pageNum", 1);//页码
        params.put("pageSize", 1);//每页数量
        params.put("macAddr", "devMac");//设备MAC
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn(params.toString());
        
        List<Device> deviceList = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId("123");
        deviceList.add(device);
        PowerMockito.when(DeviceClient.getListByParam(anyObject())).thenReturn(deviceList);
        
        Map<String,Object> result = deviceController.checkByMac("1C184A0F8DDD");
        Assert.assertNotNull(result);
    }
    
    /**
     * 测试设施是否绑定
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 21, 2017 3:42:29 PM
     */
    @Test
    public void testIsBind () throws Exception {
        Map<String,Object> params = new HashMap<String,Object>();//参数
        params.put("pageNum", 1);//页码
        params.put("pageSize", 1);//每页数量
        params.put("macAddr", "devMac");//设备MAC
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn(params.toString());
        
        List<Device> deviceList = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId("123");
        deviceList.add(device);
        PowerMockito.when(DeviceClient.getListByParam(anyObject())).thenReturn(deviceList);
        
        //获取目标类的class对象  
        Class<DeviceController> class1 = DeviceController.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("isBind", new Class[]{String.class});
        method.setAccessible(true);
        String result = (String)method.invoke(instance, new Object[]{"devMac"});
        Assert.assertEquals(result, "123");
    }

    /**
     * 测试绑定
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 9:27:24 AM
     */
    @Test
    public void testBindForMerIdNotNull() throws Exception {
        Map<String, Object> map = getParam();
        
        List<Device> deviceList = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId("123");
        deviceList.add(device);
        PowerMockito.when(DeviceClient.getListByParam(anyObject())).thenReturn(deviceList);
        PowerMockito.doNothing().when(DeviceClient.class,"bind",anyMap());

        Merchant merchant = new Merchant();
        merchant.setProjectId(1L);
        PowerMockito.when(MerchantClient.getById(anyLong())).thenReturn(merchant);
        
        PowerMockito.doNothing().when(DeviceBusClient.class,"setFatAPSSID",anyString(),anyString());
        PowerMockito.doNothing().when(deviceInstallerService).add(anyString(), anyString());
        
        Map<String, Object> ret = deviceController.bind(map);
        Assert.assertNotNull(ret);
        
        PowerMockito.verifyStatic();
        DeviceClient.getListByParam(anyObject());
        MerchantClient.getById(anyLong());
    }
    
    /**
     * 测试绑定
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 9:27:24 AM
     */
    @Test
    public void testBindForMerIdNull() throws Exception {
        Map<String, Object> map = getParam();
        map.put("merchantId", "");
        
        List<Device> deviceList = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId("123");
        deviceList.add(device);
        PowerMockito.when(DeviceClient.getListByParam(anyObject())).thenReturn(deviceList);
        PowerMockito.doNothing().when(DeviceClient.class,"bind",anyMap());

        PowerMockito.when(merchantService.isMerchantNameExist(anyString())).thenReturn(false);
        PowerMockito.when(toeUserService.isUserNameExist(anyString())).thenReturn(false);
        
        PowerMockito.when(UserAuthClient.queryUserAuthByParam(anyObject())).thenReturn(null);
        
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("defaultPwd");
        PowerMockito.when(EncryUtil.getMd5Str(anyString())).thenReturn("AuthPswd");
        PowerMockito.when(RandomUtil.getRandomNumber(anyInt())).thenReturn("12345");
        PowerMockito.when(MsCommonUtil.getCenterToken(anyString())).thenReturn("token");
        PowerMockito.when(UserAuthClient.addUserAuth(anyString())).thenReturn(10L);
        
        PowerMockito.when(FormatUtil.industryCodeToProjectId(anyString(),anyString())).thenReturn(12L);
        
        PowerMockito.doNothing().when(toeRoleService).addUserRole(anyLong(), anyString());
        PowerMockito.doNothing().when(toeUserService).addUserMerchant(anyLong(), anyLong());
        
        Merchant merchant = new Merchant();
        merchant.setProjectId(1L);
        PowerMockito.when(MerchantClient.getById(anyLong())).thenReturn(merchant);
        
        PowerMockito.doNothing().when(DeviceBusClient.class,"setFatAPSSID",anyString(),anyString());
        PowerMockito.doNothing().when(deviceInstallerService).add(anyString(), anyString());
        
                
        
        Map<String, Object> ret = deviceController.bind(map);
        Assert.assertNotNull(ret);
        
        PowerMockito.verifyStatic();
//        CastUtil.toLong(anyObject());
        DeviceClient.getListByParam(anyObject());
        merchantService.isMerchantNameExist(anyString());
        toeUserService.isUserNameExist(anyString());
        UserAuthClient.queryUserAuthByParam(anyObject());
        SysConfigUtil.getParamValue(anyString());
        EncryUtil.getMd5Str(anyString());
        RandomUtil.getRandomNumber(anyInt());
        MsCommonUtil.getCenterToken(anyString());
        UserAuthClient.addUserAuth(anyString());
        MerchantClient.getById(anyLong());
        FormatUtil.industryCodeToProjectId(anyString(),anyString());
    }    
    /**
     * 创建绑定用参数map
     * @return 绑定用参数map
     * @author 季振宇  
     * @date Jun 22, 2017 9:17:31 AM
     */
    private Map<String, Object> getParam() {
        Map<String, Object> map = new HashMap<>();
        
        map.put("devMac", "devMac");
        map.put("broadbandAccount", "broadbandAccount");
        map.put("merchantId", 62L);
        map.put("account", "account");
        map.put("merchantName", "merchantName");
        map.put("contactWay", "contactWay");
        map.put("priIndustryCode", "priIndustryCode");
        map.put("secIndustryCode", "secIndustryCode");
        map.put("provinceId", 31L);
        map.put("cityId", 383L);
        map.put("areaId", 3883L);
        map.put("address", "address");
        map.put("ssidPrefix", "ssidPrefix");
        map.put("ssidSuffix", "ssidSuffix");
        map.put("jobNumber", "jobNumber");
        
        return map;
    }
    
    /**
     * 测试获取ssid
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 10:01:52 AM
     */
    @Test
    public void testGetSsid () throws Exception {
        PowerMockito.when(StringUtils.isNoneBlank(anyString())).thenReturn(true);
        
        //获取目标类的class对象  
        Class<DeviceController> class1 = DeviceController.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("getSsid", new Class[]{String.class,String.class});
        method.setAccessible(true);
        String result = (String)method.invoke(instance, new Object[]{"123","234"});
        Assert.assertEquals("aWiFi-123-234", result);
    }
    
    /**
     * 测试校验参数
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 10:09:27 AM
     */
    @Test
    public void testValidParam () throws Exception {
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyString(),anyObject(),anyString());
      //获取目标类的class对象  
        Class<DeviceController> class1 = DeviceController.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("validParam", new Class[]{String.class,String.class,String.class,String.class,String.class,String.class,Long.class,Long.class,Long.class,String.class,String.class,String.class,String.class});
        method.setAccessible(true);
        method.invoke(instance, new Object[]{"123","234","123","234","123","234",34L,383L,3883L,"234","123","234","123"});
    }
    
    /**
     * 测试数据中心-账号[account]唯一性校验
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 10:09:27 AM
    @Test
    public void testUserAuthUniqueCheck () throws Exception {
        List<PubUserAuth> pubUserAuthList = new ArrayList<>();
        PowerMockito.when(UserAuthClient.queryAuthUserByParam(anyObject())).thenReturn(pubUserAuthList);
        
      //获取目标类的class对象  
        Class<DeviceController> class1 = DeviceController.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("userAuthUniqueCheck", new Class[]{String.class});
        method.setAccessible(true);
        method.invoke(instance, new Object[]{"123"});
    }
    */
    
    /**
     * 测试新增userAuth记录
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 10:09:27 AM
     */
//    @Test
//    public void testAddUserAuth () throws Exception {
//        PowerMockito.when(SysConfigUtil.getParamValue("default_password")).thenReturn("password");
//        PowerMockito.when(RandomUtil.getRandomNumber(anyInt())).thenReturn("12345");
//        PowerMockito.when(MsCommonUtil.getCenterToken(anyString())).thenReturn("token");
//        PowerMockito.when(UserAuthClient.getUserIdByPhone(anyObject())).thenReturn(null);
//        PowerMockito.when(UserAuthClient.addUserAuth(anyString())).thenReturn(12L);
//        
//        //获取目标类的class对象  
//        Class<DeviceController> class1 = DeviceController.class;  
//        //获取目标类的实例  
//        Object instance = class1.newInstance(); 
//        Method method = class1.getDeclaredMethod("addUserAuth", new Class[]{String.class});
//        method.setAccessible(true);
//        Long userId = (Long)method.invoke(instance, new Object[]{"userName"});
//        Long expected = 12L;
//        Assert.assertEquals(expected, userId);
//        
//        PowerMockito.verifyStatic();
//        SysConfigUtil.getParamValue("default_password");
//        RandomUtil.getRandomNumber(anyInt());
//        MsCommonUtil.getCenterToken(anyString());
//        UserAuthClient.addUserAuth(anyString());
//    }
    
    /**
     * 测试添加商户
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 10:09:27 AM
     */
    @Test
    public void testAddMerchant () throws Exception {
        PowerMockito.when(MerchantClient.add(anyObject(), anyString())).thenReturn(62L);
        
      //获取目标类的class对象  
        Class<DeviceController> class1 = DeviceController.class;  
        //获取目标类的实例  
        Object instance = class1.newInstance(); 
        Method method = class1.getDeclaredMethod("addMerchant", new Class[]{String.class,String.class,String.class,String.class,String.class,Long.class,Long.class,Long.class,String.class,Long.class,Long.class});
        method.setAccessible(true);
        Long result = (Long)method.invoke(instance, new Object[]{"123","123","123","123","123",10L,10L,10L,"123",10L,10L});
        Long expected = 62L;
        Assert.assertEquals(expected, result);
        
        PowerMockito.verifyStatic();
        MerchantClient.add(anyObject(), anyString());
    }
    
//    /**
//     * 测试创建用户表记录
//     * @throws Exception 异常
//     * @author 季振宇  
//     * @date Jun 22, 2017 10:09:27 AM
//     */
//    @Test
//    public void testAddToeUser () throws Exception {
////        PowerMockito.when(toeUserService.add(anyObject())).thenReturn(62L);
//        PowerMockito.doReturn(62L).when(toeUserService).add(anyObject());
////        PowerMockito.doNothing().when(toeRoleService).addUserRole(anyLong(), anyString());
//      //获取目标类的class对象  
//        Class<DeviceController> class1 = DeviceController.class;  
//        //获取目标类的实例  
//        Object instance = class1.newInstance(); 
//        Method method = class1.getDeclaredMethod("addToeUser", new Class[]{String.class,String.class,Long.class,Long.class,Long.class,Long.class});
//        method.setAccessible(true);
//        Long result = (Long)method.invoke(instance, new Object[]{"userName","merchantName",38L,383L,3883L,10L});
//        Long expected = 62L;
//        Assert.assertEquals(expected, result);
//    }
}
