/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月3日 下午3:42:19
* 创建作者：张智威
* 文件名称：SessionDTOTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthResult;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisUtil.class,DeviceClient.class})
@PowerMockIgnore({"javax.management.*"})
public class SessionDTOTest {
    @Test
    public void testPutInSession() throws Exception {
        SessionDTO sessionDTO =new SessionDTO();
        sessionDTO.setTimeInfo(new UserTimeInfo());
        sessionDTO.setPortalParam(new PortalParam());
        sessionDTO.setMerchant(new Device());
        sessionDTO.setAuthResult(new AuthResult());
        try{  
            FileOutputStream fs = new FileOutputStream("foo.ser");  
            ObjectOutputStream os =  new ObjectOutputStream(fs);  
            os.writeObject(sessionDTO);  
            os.close();  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
    }
}
