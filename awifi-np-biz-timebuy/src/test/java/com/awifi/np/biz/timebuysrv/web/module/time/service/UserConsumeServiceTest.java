/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午1:43:01
* 创建作者：张智威
* 文件名称：UserConsumeServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 消费记录测试类
 * @author 张智威
 * 2017年6月7日 上午11:06:27
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisUtil.class,DeviceClient.class})
@PowerMockIgnore({"javax.management.*"})
public class UserConsumeServiceTest {
    @Test
    public void addTest(){
        
    }
}
