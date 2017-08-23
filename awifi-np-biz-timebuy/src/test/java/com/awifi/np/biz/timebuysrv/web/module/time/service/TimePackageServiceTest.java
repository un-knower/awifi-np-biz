package com.awifi.np.biz.timebuysrv.web.module.time.service;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantNoticeDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.merchant.service.impl.MerchantNoticeServiceImpl;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.TimePackageDao;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.TimePackageSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimePackageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

/**
 * Created by dozen.zhang on 2017/4/18.
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisUtil.class,DeviceClient.class})
@PowerMockIgnore({"javax.management.*"})
public class TimePackageServiceTest {

    /**
     * 被测试类
     */
    private TimePackageService timePackageService;
    private TimePackageDao mockTimePackageDao;
    /**
     * 初始化
     */
    @Before
    public void before() {
        timePackageService = new TimePackageService();
        mockTimePackageDao = Mockito.mock(TimePackageDao.class);
        timePackageService.setPackageDao(mockTimePackageDao);
       
    }

  /**
   * 测试查询
   * @throws Exception
   * @author 张智威  
   * @date 2017年4月28日 上午10:26:34
   */
    @Test
    public void queryListByParam() throws Exception {
        
        List<TimePackage> list=new ArrayList<>();
        HashMap param =new HashMap();
        Mockito.when(mockTimePackageDao.queryListByParam(param)).thenReturn(list);
        
        timePackageService. queryListByParam(param);
        try{
        timePackageService. queryListByParam(null);
        }catch(Exception e){
            //e.printStackTrace();
        }
        param.put("pageNum",1);
        param.put("pageSize",10);
        timePackageService. queryListByParam(param);
    }
}
