/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 下午4:20:46
* 创建作者：伍恰
* 文件名称：ImportHotServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.hot.service;


import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.excel.ImportExcelThread;
import com.awifi.np.biz.common.excel.ImportMonitorThread;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.excel.model.ExcelHot;
import com.awifi.np.biz.devsrv.model.servcie.ModelService;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;
@PrepareForTest({DateUtil.class,RedisUtil.class})
public class ImportHotServiceTest extends MockBase{
    /**
     * 被测试类
     */
    @InjectMocks
    private ImportHotService importHotService;
    
    /**
     * 设备查询持久层
     */
    @Mock(name = "modelService")
    private ModelService modelService;

    /**
     * 批量数据存储业务层
     */
    @Mock(name = "importExcelService")
    private ImportExcelService importExcelService;

    /**
     * 地区查询业务层
     */
    @Mock(name = "pubAreaService")
    private PubAreaService pubAreaService;
    
    /**
     * 
     */
    @Mock(name = "emsExcelDao")
    private EmsExcelDao emsExcelDao;
    
    /**
     * 类型的定义
     */
    private static final String TYPE = "Hot";
    
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月20日 下午4:33:53
     */
    @Before
    public void befor(){
        PowerMockito.mockStatic(DateUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        
    }
    /*@Test
    public void testReadExcelInExcelIteratorOfExcelHotSessionUser() {
        importHotService.readExcel(excel, sessionUser);
    }
    */
    
    /**
     * 批量信息导入数据库和本地数据库的操作
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月18日 下午2:03:59
     */
    /*@Test
    public void testInsertDatasList() throws Exception {
        List<ExcelHot> excellist = new ArrayList<>();
        ExcelHot excelHot = new ExcelHot();
        excelHot.setProvince("1");
        excelHot.setCity("1");
        excelHot.setCounty("1");
        excelHot.setApnum(1);
        excelHot.setWlanganfangnum(1);
        excellist.add(excelHot);
        excelHot.setHotareaname("");
        SessionUser sessionUser = new SessionUser();
        String batchDeviceId = "123";
        String uuid = UUID.randomUUID().toString();
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("0-0", "{\"line\": \"0-0\",\"num\": \"1\",\"message\": \"设备类型不对(DBC-301001001004)!\",\"status\": \"3\"}");
        maps.add(map);
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(maps);
        String redisKey = TYPE+"-"+batchDeviceId;
        PowerMockito.whenNew(ImportMonitorThread.class).withArguments(redisKey, null ,batchDeviceId,uuid,emsExcelDao).thenReturn(null);
        Integer maxThread=5;
        Long maxThreadSleep=30L;
        List<CenterPubEntity> centerPubEntityList = new ArrayList<CenterPubEntity>();
        int maxSize=centerPubEntityList.size();
        int redisSize=importExcelService.initRedisSet(redisKey, maxSize);
        PowerMockito.whenNew(ImportExcelThread.class).withArguments(maxThread , maxThreadSleep, redisSize-1, centerPubEntityList, maxSize, batchDeviceId, TYPE).thenReturn(null);
        importHotService.insertDatas(excellist, sessionUser, batchDeviceId, uuid);
    }*/
    
    /**
     * 获取批次号
     * @author 伍恰  
     * @date 2017年6月20日 下午4:34:12
     */
    @Test
    public void testGetBatchNumExcelHot() {
        ExcelHot excelHot = new ExcelHot();
        importHotService.getBatchNum(excelHot);
    }
    
    /**
     * 获取类型
     * @author 伍恰  
     * @date 2017年7月18日 下午2:12:48
     */
    @Test
    public void testGetExcelClass(){
        importHotService.getExcelClass();
    }
}
