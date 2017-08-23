/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月3日 下午1:56:51
* 创建作者：王冬冬
* 文件名称：WhiteUserServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceApiService;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.mws.whiteuser.dao.WhiteUserDao;
import com.awifi.np.biz.mws.whiteuser.model.StationMerchantNamelistSendlog;
import com.awifi.np.biz.mws.whiteuser.model.WhiteUser;
import com.awifi.np.biz.mws.whiteuser.service.impl.WhiteUserServiceImpl;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao.StaticUserDao;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;


@RunWith(PowerMockRunner.class)
@PrepareForTest({MerchantClient.class})
public class WhiteUserServiceTest {
    
    
    /**
     * whiteuser服务层
     */
    @InjectMocks
    private WhiteUserServiceImpl whiteUserService;
    /**
     * 白名单dao
     */
    @Mock
    private WhiteUserDao whiteUserDao;
    
    /**
     * 静态用户dao
     */
    @Mock
    private StaticUserDao staticUserDao;
    
    /**白名单下发服务*/
    @Mock
    private WhiteUserServiceSendlogService whiteUserServiceSendlogService;
    
    /**设备总线服务*/
    @Mock
    private DeviceBusService deviceBusService;
    
    /**数据中心服务*/
    @Mock
    private DeviceApiService deviceApiService;
    
    /**
     * 
     * @author 王冬冬  
     * @date 2017年7月3日 下午2:03:29
     */
    @Before
    public void setup(){
        PowerMockito.mockStatic(MerchantClient.class);
    }

    /**
     *  判断手机号是否加白名单
     * @author 王冬冬  
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testIsCellphoneExist(){
        PowerMockito.when(whiteUserDao.isMobileExist(anyObject(), anyString())).thenReturn(1);
        boolean result= whiteUserService.isCellphoneExist(1L, "3333333");
        Assert.assertEquals(true, result);
    }
    
    /**
     *  判断mac是否加白名单
     * @author 王冬冬  
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testIsMacExistExist(){
        PowerMockito.when(whiteUserDao.isMacExist(anyObject(), anyString())).thenReturn(1);
        boolean result= whiteUserService.isMacExist(1L, "3333333");
        Assert.assertEquals(true, result);
    }
    /**
     *  新增白名单
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testadd() throws Exception{
        List<Device> devices =new ArrayList<Device>();
        Device device=new Device();
        device.setDeviceId("1");
        device.setDevMac("xxx");
        devices.add(device);
        PowerMockito.when(deviceApiService.getListByParam(anyString())).thenReturn(devices);
        List<StationMerchantNamelistSendlog> logList=new ArrayList<StationMerchantNamelistSendlog>();
        PowerMockito.when(whiteUserServiceSendlogService.findByDevId(anyString())).thenReturn(logList);
        PowerMockito.doNothing().when(deviceBusService).sendWhiteToDevicebus(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());

        WhiteUser user=new WhiteUser();
        user.setMac("xxx");
        PowerMockito.doNothing().when(whiteUserDao).add(user);
        whiteUserService.add(user);
        PowerMockito.verifyStatic();
    }
    
    /**
     *  新增白名单
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testadd1() throws Exception{
        List<Device> devices =new ArrayList<Device>();
        Device device=new Device();
        device.setDeviceId("1");
        device.setDevMac("xxx");
        devices.add(device);
        PowerMockito.when(deviceApiService.getListByParam(anyString())).thenReturn(devices);
        List<StationMerchantNamelistSendlog> logList=new ArrayList<StationMerchantNamelistSendlog>();
        StationMerchantNamelistSendlog log=new StationMerchantNamelistSendlog();
        log.setUserMac("['38BC1ACE434F']");
        logList.add(log);
        PowerMockito.when(whiteUserServiceSendlogService.findByDevId(anyString())).thenReturn(logList);
        PowerMockito.doNothing().when(deviceBusService).sendWhiteToDevicebus(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());

        WhiteUser user=new WhiteUser();
        user.setMac("xxx");
        PowerMockito.doNothing().when(whiteUserDao).add(user);
        whiteUserService.add(user);
        PowerMockito.verifyStatic();
    }
    
    /**
     *  删除白名单
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testdelete() throws Exception{
        List<WhiteUser> whiteUsers=new ArrayList<WhiteUser>();
        PowerMockito.when(whiteUserDao.getListByIds(any())).thenReturn(whiteUsers);
        WhiteUser user=new WhiteUser();
        user.setMac("xxx");
        user.setMerchantId(1L);
        whiteUsers.add(user);
        List<Device> devices =new ArrayList<Device>();
        Device device=new Device();
        device.setDeviceId("1");
        device.setDevMac("xxx");
        devices.add(device);
        PowerMockito.when(deviceApiService.getListByParam(anyString())).thenReturn(null);
        PowerMockito.doNothing().when(whiteUserDao).delete(anyObject());;
        whiteUserService.delete(1);
        Mockito.verify(whiteUserDao).delete(anyObject());
    }
    
    /**
     *  删除白名单1
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testdelete1() throws Exception{
        List<WhiteUser> whiteUsers=new ArrayList<WhiteUser>();
        PowerMockito.when(whiteUserDao.getListByIds(any())).thenReturn(whiteUsers);
        WhiteUser user=new WhiteUser();
        user.setMac("xxx");
        user.setMerchantId(1L);
        whiteUsers.add(user);
        List<Device> devices =new ArrayList<Device>();
        Device device=new Device();
        device.setDeviceId("1");
        device.setDevMac("xxx");
        devices.add(device);
        PowerMockito.when(deviceApiService.getListByParam(anyString())).thenReturn(devices);
        List<StationMerchantNamelistSendlog> logList=new ArrayList<StationMerchantNamelistSendlog>();
        StationMerchantNamelistSendlog log=new StationMerchantNamelistSendlog();
        log.setUserMac("['38BC1ACE434F']");
        logList.add(log);
        PowerMockito.when(whiteUserServiceSendlogService.findByDevId(anyString())).thenReturn(logList);
        PowerMockito.doNothing().when(deviceBusService).sendWhiteToDevicebus(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
        PowerMockito.doNothing().when(whiteUserDao).delete(anyObject());;
        whiteUserService.delete(1);
        Mockito.verify(whiteUserDao).delete(anyObject());
    }
    
    
    /**
     *  批量删除白名单
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testbatchdelete() throws Exception{
        List<WhiteUser> whiteUsers=new ArrayList<WhiteUser>();
        PowerMockito.when(whiteUserDao.getListByIds(any())).thenReturn(whiteUsers);
        WhiteUser user=new WhiteUser();
        user.setMac("xxx");
        user.setMerchantId(1L);
        whiteUsers.add(user);
        List<Device> devices =new ArrayList<Device>();
        Device device=new Device();
        device.setDeviceId("1");
        device.setDevMac("xxx");
        devices.add(device);
        PowerMockito.when(deviceApiService.getListByParam(anyString())).thenReturn(devices);
        List<StationMerchantNamelistSendlog> logList=new ArrayList<StationMerchantNamelistSendlog>();
        StationMerchantNamelistSendlog log=new StationMerchantNamelistSendlog();
        log.setUserMac("['38BC1ACE434F']");
        logList.add(log);
        PowerMockito.when(whiteUserServiceSendlogService.findByDevId(anyString())).thenReturn(logList);
        PowerMockito.doNothing().when(deviceBusService).sendWhiteToDevicebus(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
        PowerMockito.doNothing().when(whiteUserDao).delete(anyObject());;
        whiteUserService.batchDelete("1");
        Mockito.verify(whiteUserDao).batchDelete(anyObject());
    }
    
    
    /**
     * 查询列表
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月3日 下午3:01:37
     */
    @Test
    public void testList() throws Exception{
        PowerMockito.when(whiteUserDao.getCountByParam(anyObject(),anyObject())).thenReturn(100);
        List<WhiteUser> whiteUsers=new ArrayList<WhiteUser>();
        WhiteUser user=new WhiteUser();
        user.setMerchantId(1L);
        user.setCellPhone("11111111111");
        whiteUsers.add(user);
        PowerMockito.when(whiteUserDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(whiteUsers);
        
        List<StaticUser> staticUsers=new ArrayList<StaticUser>();
        StaticUser staticUser=new StaticUser();
        staticUser.setMerchantId(1L);
        staticUser.setRealName("realname");
        staticUser.setUserName("username");
        staticUser.setUserType(1);
        staticUser.setCellphone("11111111111");
        staticUsers.add(staticUser);
        
        PowerMockito.when(staticUserDao.getListByMerchantIdAndCellPhone(anyObject(),anyObject())).thenReturn(staticUsers);
        Merchant merchant=new Merchant();
        merchant.setId(1L);
        merchant.setAccount("xxx");
        merchant.setMerchantName("merchantName");
        PowerMockito.when(MerchantClient.getByIdCache(anyLong())).thenReturn(merchant);

        Page<WhiteUser> page=new Page<WhiteUser>();
        page.setPageNo(1);
        page.setPageSize(10);
        SessionUser sessionUser=new SessionUser();
        sessionUser.setId(1L);
        whiteUserService.getListByParam(sessionUser,page, 1L, null);
        Assert.assertNotNull(page.getRecords());
    }
    
    
    /**
     *  批量新增白名单
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testbatchadd() throws Exception{
        List<Device> devices =new ArrayList<Device>();
        Device device=new Device();
        device.setDeviceId("1");
        device.setDevMac("xxx");
        devices.add(device);
        PowerMockito.when(deviceApiService.getListByParam(anyString())).thenReturn(devices);
        List<StationMerchantNamelistSendlog> logList=new ArrayList<StationMerchantNamelistSendlog>();
        StationMerchantNamelistSendlog log=new StationMerchantNamelistSendlog();
        log.setUserMac("['38BC1ACE434F']");
        logList.add(log);
        PowerMockito.when(whiteUserServiceSendlogService.findByDevId(anyString())).thenReturn(logList);
        PowerMockito.doNothing().when(deviceBusService).sendWhiteToDevicebus(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());

        WhiteUser user=new WhiteUser();
        user.setMac("xxx");
        PowerMockito.doNothing().when(whiteUserDao).add(user);
        List<WhiteUser>  userList=new ArrayList<WhiteUser>();
        userList.add(user);
        whiteUserService.add(userList, 1L);
        PowerMockito.verifyStatic();
    }
}
