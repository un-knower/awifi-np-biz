/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月3日 下午5:12:21
* 创建作者：王冬冬
* 文件名称：WhiteUserServiceSendlogServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service;

import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.mws.whiteuser.dao.WhiteUserSendLogDao;
import com.awifi.np.biz.mws.whiteuser.model.StationMerchantNamelistSendlog;
import com.awifi.np.biz.mws.whiteuser.service.impl.WhiteUserSendlogServiceImpl;

@RunWith(PowerMockRunner.class)
public class WhiteUserServiceSendlogTest {

    
    /**
     * 
     */
    @InjectMocks
    private WhiteUserSendlogServiceImpl whiteUserSendlogService;
    
    /**
     * 
     */
    @Mock
    private WhiteUserSendLogDao whiteUserSendLogDao;
    
    /**
     * 
     * @author 王冬冬  
     * @date 2017年7月3日 下午2:03:29
     */
    @Before
    public void setup(){
//        PowerMockito.mockStatic(MerchantClient.class);
    }
    /**
     * 
     * @author 王冬冬  
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testfindByDevId(){
        whiteUserSendlogService.findByDevId("xxx");
    }
    
    
    /**
     * 
     * @author 王冬冬  
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testsaveNameListSendLog(){
        List<StationMerchantNamelistSendlog> logList=new ArrayList<StationMerchantNamelistSendlog>();
        StationMerchantNamelistSendlog log=new StationMerchantNamelistSendlog();
        log.setUserMac("['38BC1ACE434F']");
        logList.add(log);
        PowerMockito.when(whiteUserSendLogDao.findByDevId(anyObject())).thenReturn(logList);
        String rs="{'message':'ok'}";
        PowerMockito.doNothing().when(whiteUserSendLogDao).updateByPrimaryKey(anyObject());
        whiteUserSendlogService.saveNameListSendLog(1L, 1L, "", "", "", rs, 1L, "1");
    }
    
    /**
     * 
     * @author 王冬冬  
     * @date 2017年7月3日 下午2:01:22
     */
    @Test
    public void testsaveNameListSendLog1(){
        List<StationMerchantNamelistSendlog> logList=new ArrayList<StationMerchantNamelistSendlog>();
//        StationMerchantNamelistSendlog log=new StationMerchantNamelistSendlog();
//        log.setUserMac("['38BC1ACE434F']");
//        logList.add(log);
        PowerMockito.when(whiteUserSendLogDao.findByDevId(anyObject())).thenReturn(logList);
        String rs="{'message':'ok'}";
        PowerMockito.doNothing().when(whiteUserSendLogDao).insert(anyObject());
        whiteUserSendlogService.saveNameListSendLog(1L, 1L, "", "", "", rs, 1L, "1");
    }
}
