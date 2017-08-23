package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNewsSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.TimePackageDao;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.TimePackageSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dozen.zhang on 2017/4/18.
 */

/** 
 * @author arganzheng
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "classpath:spring/spring-context.xml"
//  
//})
public class TimePackageDaoTest {
   private Logger logger =LoggerFactory.getLogger(TimePackageDaoTest.class);
    /**
     * 被测试类
     */
    @Autowired
    private TimePackageDao timePackageDao;

    /**
     * 初始化
     */
    @Before
    public void before() {
       
    }

    /**
     * 测试查询时间套餐列表.
     *
     * @throws Exception
     * @author 张智威
     * @date 2017年4月6日 上午10:02:42
     */
   // @Test
    public void testGetListByParam() throws Exception {
        List<TimePackage> list = timePackageDao.queryListByParam(new HashMap());
        logger.debug(JSON.toJSONString(list));
    }
    /**
     * 测试添加套餐
     * 
     * @author 张智威  
     * @date 2017年4月19日 下午12:05:14
     */
    //@Test
    public void testAdd(){
        TimePackage timePackage = new TimePackage();
        timePackage.setMerchantId(123123l);
        timePackage.setCreateDate(new Date());
        timePackage.setEffectDatetime(new Date());
        timePackage.setExpiredDatetime(new Date());
        timePackage.setPackageKey(201);
        timePackage.setPackageType(2);
        timePackage.setPackageValue(222f);
        timePackage.setRemarks("123123123");
        timePackage.setStatus(1);
        timePackage.setStatusDate(new Date());
        timePackageDao.add(timePackage);
    }
    /**
     * 测试逻辑删除
     * 
     * @author 张智威  
     * @date 2017年4月19日 下午4:41:44
     */
    //@Test
    public void testLogicDelete(){
       // TimePackage timePackage =  timePackageDao.queryById(305l);
        timePackageDao.logicDelete(305l);
        TimePackage timePackage =  timePackageDao.queryById(305l);
        assert timePackage.getStatus()==9;
       // timePackage.setStatus(9);
    }
    /**
     * 测试更新
     * 
     * @author 张智威  
     * @date 2017年4月19日 下午4:41:52
     */
    //@Test
    public void testUpdate(){
       TimePackage timePackage =  timePackageDao.queryById(305l);
       timePackage.setPackageValue(123f);
        timePackageDao.update(timePackage);
        timePackage= timePackageDao.queryById(305l);
        assert timePackage.getPackageValue()==123f;
       // timePackage.setStatus(9);
    }
    /**
     * 测试查询数量
     * 
     * @author 张智威  
     * @date 2017年4月19日 下午4:42:00
     */
    //@Test
    public void testQueryCountByParam(){
       int count = timePackageDao.queryCountByParam(new HashMap());
    
        assert count>0;
       // timePackage.setStatus(9);
    }
}
