package com.awifi.np.biz.devsrv.fatap.service.impl;

import static org.mockito.Matchers.anyObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.api.client.dbcenter.corporation.service.CorporationApiService;
import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.ExcelFatAp;
import com.awifi.np.biz.api.client.dbcenter.fitap.model.ExcelFitApPro;
import com.awifi.np.biz.api.client.dbcenter.location.model.CenterPubArea;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.excel.ImportExcelThread;
import com.awifi.np.biz.common.excel.ImportMonitorThread;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.CenterPubCorporation;
import com.awifi.np.biz.common.excel.model.CenterPubModel;
import com.awifi.np.biz.common.excel.model.InExcelIterator;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.model.servcie.ModelService;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;
/**
 * 
 * @ClassName: ImportFatAPServiceTest
 * @Description: 胖ap导入 服务类单元测试
 * @author wuqia
 * @date 2017年6月12日 下午4:22:20
 *
 */
@PrepareForTest({CorporationClient.class, RedisUtil.class})
public class ImportFatAPServiceTest extends MockBase {
    /**
     * 被测试类
     */
    @InjectMocks
    private ImportFatAPService importFatAPService;
    /**
     * 导入本地数据库持久层
     */
    @Mock(name = "emsExcelDao")
    private EmsExcelDao emsExcelDao;

    /**
     * 批量数据存储业务层
     */
    @Mock(name = "importExcelService")
    private ImportExcelService importExcelService;
    /**
     * 厂商服务层
     */
    @Mock(name = "corporationApiService")
    private CorporationApiService corporationApiService;
    
    /**
     * 一个存储作用的table(线程安全，后期可改)
     */
    protected  Hashtable<String, CenterPubModel> modelMap=new Hashtable<>();
    
    /**
     * 地区查询业务层
     */
    @Mock(name="pubAreaService")
    private PubAreaService pubAreaService;
    
    /**
     * 设备查询持久层
     */
    @Mock(name="modelService")
    private ModelService modelService;
    
    /**
     * 类型的定义
     */
    private static final String TYPE="FatAP";
    
    /** 初始化 */
    @Before
    public void before() {
        PowerMockito.mockStatic(CorporationClient.class);
        PowerMockito.mockStatic(RedisUtil.class);
        // PowerMockito.when(BeanUtil.getBean("corporationApiService")).thenReturn(corporationApiService);
    }
    /**
     * @Title: testGetBatchNumExcelFatAp
     * @Description: 获取批次号
     * @throws 
     * @data  2017年6月12日 下午4:40:46
     * @author wuqia
     */
    @Test
    public void testGetBatchNumExcelFatAp() {
        ExcelFatAp excelFatAp = new ExcelFatAp();
        PowerMockito.when(emsExcelDao.getMaxBatchNum(anyObject())).thenReturn("fat-ap-1");
        importFatAPService.getBatchNum(excelFatAp);
    }
    /**
     * 
     * @Title: testInsertDatasListOfExcelFatApSessionUserStringString
     * @Description: 批量信息导入数据库和本地数据库的操作
     * @throws Exception
     *             参数描述
     *             2017年6月12日 下午4:41:13
     * @author wuqia
     */
   /* @Test
    public void testInsertDatas() throws Exception {
        List<ExcelFatAp> datas = new ArrayList<>();
        ExcelFatAp excelFitApPro = new ExcelFatAp();
        excelFitApPro.setProvince("1");
        excelFitApPro.setCity("1");
        excelFitApPro.setCounty("1");
        excelFitApPro.setModel("m5500");
        datas.add(excelFitApPro);
        SessionUser sessionUser = new SessionUser();
        String batchDeviceId = "1233";
        String uuid = UUID.randomUUID().toString();
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("0-0", "{\"line\": \"0-0\",\"num\": \"1\",\"message\": \"设备类型不对(DBC-301001001004)!\",\"status\": \"3\"}");
        maps.add(map);
        testReadExcelInExcel();
        String redisKey = TYPE+"-"+batchDeviceId;
        PowerMockito.whenNew(ImportMonitorThread.class).withArguments(redisKey, null ,batchDeviceId,uuid,emsExcelDao).thenReturn(null);
        Integer maxThread=5;
        Long maxThreadSleep=30L;
        List<CenterPubEntity> centerPubEntityList = new ArrayList<CenterPubEntity>();
        int maxSize=centerPubEntityList.size();
        int redisSize=importExcelService.initRedisSet(redisKey, maxSize);
        PowerMockito.whenNew(ImportExcelThread.class).withArguments(maxThread , maxThreadSleep, redisSize-1, centerPubEntityList, maxSize, batchDeviceId, TYPE).thenReturn(null);
        PowerMockito.doNothing().when(ImportMonitorThread.class, "start");
        PowerMockito.doNothing().when(ImportExcelThread.class, "start");
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(maps);
        importFatAPService.insertDatas(datas, sessionUser, batchDeviceId, uuid);
    }*/
    
    /**
     * 
     * @Title: testGetBaseMap
     * @Description: 批量信息导入数据库和本地数据库的操作
     * @throws 
     * @data  2017年6月12日 下午4:41:31
     * @author wuqia
     */
    @Test
    public void testGetBaseMap() {
        Map<String, Object> map = importFatAPService.getBaseMap();
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testGetCorpMap
     * @Description: 查询得到所有厂家对应的map
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午4:41:46
     * @author wuqia
     */
    @Test
    public void testGetCorpMap() throws Exception {
        String corporation = "ZTE";
        List<Corporation> corporationList = new ArrayList<>();
        Corporation corporationModel = new Corporation();
        corporationModel.setCorpName("ZTE");// 设置厂商名称
        corporationList.add(corporationModel);
        PowerMockito.when(CorporationClient.queryListByParam(anyObject())).thenReturn(corporationList);
        Map<String, CenterPubCorporation> map = importFatAPService.getCorpMap(corporation);
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testRunTask
     * @Description: 解析和导入过程
     * @return void 返回类型描述
     * @throws @data
     *             2017年6月12日 下午4:41:59
     * @author wuqia
     */
    /*
     * 需要读取文件
     * 
     * @Test public void testRunTask() { EmsSysExcel info = new EmsSysExcel();
     * SessionUser sessionUser = new SessionUser(); Map<String, String> map =
     * importFatAPService.runTask(info,sessionUser); Assert.assertNotNull(map);
     * }
     */

     /**
      * 读取excel文件中的内容，以获取原始批量信息
      * @throws Exception 异常/参数
      * @author 伍恰  
      * @date 2017年7月19日 下午2:06:54
      */
    @SuppressWarnings("unused")
    @Test
    public void testReadExcelInExcel() throws Exception {
        String filePath = "/service/tmp/test.xls";
        // 创建excel文件对象
        @SuppressWarnings("resource")
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个张表
        Sheet sheet = wb.createSheet();
        // 创建第一行
        Row row = sheet.createRow(0);
        // 创建第二行
        Row row1 = sheet.createRow(1);
        // 创建行数据
        Cell cell0 = row1.createCell(0);
        cell0.setCellValue("H3C");
        Cell cell1 = row1.createCell(1);
        cell1.setCellValue("m5500");
        Cell cell2 = row1.createCell(2);
        cell2.setCellValue("V4.00.00");
        Cell cell3 = row1.createCell(3);
        cell3.setCellValue("V4.00.00");
        Cell cell4 = row1.createCell(4);
        cell4.setCellValue("2134AB789184");
        Cell cell5 = row1.createCell(5);
        cell5.setCellValue("1");
        Cell cell6 = row1.createCell(6);
        cell6.setCellValue("浙江");
        Cell cell7 = row1.createCell(7);
        cell7.setCellValue("杭州");
        Cell cell8 = row1.createCell(8);
        cell8.setCellValue("拱墅区");
        Cell cell9 = row1.createCell(9);
        cell9.setCellValue("0");
        File testPath = new File("/service/tmp");
        if (!testPath.exists()) {
            testPath.mkdirs();
        }
        File testFile = new File(filePath);
        if (!testFile.exists()) {
            testFile.createNewFile();
        }
        FileOutputStream fOut = new FileOutputStream(filePath);
        wb.write(fOut);
        fOut.flush();
        fOut.close();
        SessionUser sessionUser = new SessionUser();
        CenterPubArea centerPubArea = new CenterPubArea();
        centerPubArea.setId(12L);
        PowerMockito.when(pubAreaService.queryByParam(anyObject(), anyObject(), anyObject())).thenReturn(centerPubArea);
        Map<String,CenterPubModel> mapModel = new HashMap<>();
        CenterPubModel centerPubModel = new CenterPubModel();
        centerPubModel.setCorpId(1L);
        centerPubModel.setId(1L);
        centerPubModel.setModelCode("1");
        centerPubModel.setModelName("m5500");
        centerPubModel.setEntityType(DevType.fatap.getValue());
        mapModel.put("m5500", centerPubModel);
        PowerMockito.when(modelService.getAllModelMap(anyObject())).thenReturn(mapModel);
        List<Corporation> corporationList = new ArrayList<>();
        Corporation corporation = new Corporation();
        corporation.setCorpCode("H3C");
        corporation.setCorpName("H3C");
        corporation.setId(1L);
        corporationList.add(corporation);
        PowerMockito.when(CorporationClient.queryListByParam(anyObject())).thenReturn(corporationList);
        InExcelIterator<ExcelFatAp> excel = new InExcelIterator<>(filePath,importFatAPService.getExcelClass());
        importFatAPService.readExcel(excel, sessionUser);
    }
}
