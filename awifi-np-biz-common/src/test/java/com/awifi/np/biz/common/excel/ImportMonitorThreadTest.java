/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 上午11:07:23
* 创建作者：伍恰
* 文件名称：ImportMonitorThreadTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.excel;


import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.test.MockBase;
import com.awifi.np.biz.common.util.JsonUtil;

@PrepareForTest(RedisUtil.class)
public class ImportMonitorThreadTest extends MockBase{
    /**
     * 被测试类
     */
    @InjectMocks
    private ImportMonitorThread importMonitorThread;
    /**监控的redis ke名*/
    private String redisKey;
    
    /**uuid用于索引导入文件对应的记录*/
    private String uuid;
    
    /**定制终端导入中的厂家*/
    private String corporation;
    
    /**批次号*/
    private String batch;
    
    /**dao层,根据导入情况更新filestatus,batch*/
    @Mock
    private EmsExcelDao emsExcelDao;
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月20日 上午11:33:46
     */
    @Before
    public void before() {
        PowerMockito.mockStatic(RedisUtil.class);
    }
    /**
     *  测试线程方法
     * @author 伍恰  
     * @date 2017年6月20日 上午11:33:30
     */
    @Test
    public void testRun() {
        Map<String,String> map = new HashMap<>();
        map.put("0-0", "{\"line\": \"0-0\",\"num\": \"1\",\"message\": \"数据保存成功\",\"status\": \"2\"}");
        List<Map<String,String>> maps = new ArrayList<>();
        maps.add(map);
        PowerMockito.when(RedisUtil.hgetAllBatch(anyString())).thenReturn(maps);
        importMonitorThread = new ImportMonitorThread(redisKey, corporation, batch, uuid, emsExcelDao);
        importMonitorThread.run();
    }

}
