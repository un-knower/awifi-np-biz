/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月13日 下午4:41:59
* 创建作者：伍恰
* 文件名称：ImportFitApProServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fitappro.service.impl;

import static org.mockito.Matchers.anyObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.api.client.dbcenter.fitap.model.ExcelFitApPro;
import com.awifi.np.biz.api.client.dbcenter.location.model.CenterPubArea;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.InExcelIterator;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;

@PrepareForTest({RedisUtil.class})
public class ImportFitApProServiceTest extends MockBase{
    /**
     * 被测试类
     */
    @InjectMocks
    private ImportFitApProService importFitApProService;
    
    /**
     * 批量数据存储业务层
     */
    @Mock(name="importExcelService")
    private ImportExcelService importExcelService;
    
    /**
     * 地区查询业务层
     */
    @Mock(name="pubAreaService")
    private PubAreaService pubAreaService;
    /**
     * Excel状态信息
     */
    @Mock(name="emsExcelDao")
    private  EmsExcelDao emsExcelDao;
    
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月20日 下午4:33:53
     */
    @Before
    public void befor(){
        PowerMockito.mockStatic(RedisUtil.class);
        
    }
    
    /**
     * 获取批次号
     *  异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:49:42
     */
    @Test
    public void testGetBatchNumExcelFitApPro() {
        ExcelFitApPro pro = new ExcelFitApPro();
        pro.setAcName("aa");
        importFitApProService.getBatchNum(pro);
    }
    
    /**
     * 批量信息导入数据库和本地数据库的操作
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午4:20:18
     */
    /*@Test
    public void testInsertDatas() throws Exception {
        List<ExcelFitApPro> datas = new ArrayList<>();
        ExcelFitApPro excelFitApPro = new ExcelFitApPro();
        excelFitApPro.setProvince("1");
        excelFitApPro.setCity("1");
        excelFitApPro.setCounty("1");
        datas.add(excelFitApPro);
        SessionUser sessionUser = new SessionUser();
        String batchDeviceId = "1233";
        String uuid = UUID.randomUUID().toString();
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("0-0", "{\"line\": \"0-0\",\"num\": \"1\",\"message\": \"设备类型不对(DBC-301001001004)!\",\"status\": \"3\"}");
        maps.add(map);
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(maps);
        importFitApProService.insertDatas(datas, sessionUser, batchDeviceId, uuid);
    }*/
    
    /**
     * 读取excel文件中的内容，以获取原始批量信息
     *  异常/参数
     * @author 伍恰  
     * @throws Exception 
     * @date 2017年7月19日 上午11:18:56
     */
    @SuppressWarnings("unused")
    @Test
    public void testReadExcel() throws Exception {
        String filePath = "/service/tmp/test.xls";
      //创建excel文件对象  
        @SuppressWarnings("resource")
        HSSFWorkbook wb = new HSSFWorkbook();  
        //创建一个张表  
        Sheet sheet = wb.createSheet();  
        //创建第一行  
        Row row = sheet.createRow(0);  
        //创建第二行  
        Row row1 = sheet.createRow(1);
        //创建行数据
        Cell cell0 = row1.createCell(0);
        cell0.setCellValue("浙江");
        Cell cell1 = row1.createCell(1);
        cell1.setCellValue("杭州");
        Cell cell2 = row1.createCell(2);
        cell2.setCellValue("拱墅区");
        Cell cell3 = row1.createCell(3);
        cell3.setCellValue("A23123F23137");
        Cell cell4 = row1.createCell(4);
        cell4.setCellValue("1234213");
        Cell cell5 = row1.createCell(5);
        cell5.setCellValue("h3c_caiji");
        File testPath = new File("/service/tmp");
        if (!testPath.exists()) {
            testPath.mkdirs();
        }
        File testFile = new File(filePath);
        if (!testFile.exists()) {
            testFile.createNewFile();
        }
        FileOutputStream fOut  =   new  FileOutputStream(filePath);
        wb.write(fOut);
        fOut.flush();
        fOut.close();
        Class<ExcelFitApPro> clz = importFitApProService.getExcelClass();
        InExcelIterator<ExcelFitApPro> excel = new InExcelIterator<ExcelFitApPro>(filePath, clz);
        SessionUser sessionUser = new SessionUser();
        CenterPubArea centerPubArea = new CenterPubArea();
        centerPubArea.setId(12L);
        PowerMockito.when(pubAreaService.queryByParam(anyObject(),anyObject(),anyObject())).thenReturn(centerPubArea);
        importFitApProService.readExcel(excel, sessionUser);
    }
    
    /**
     * 获取类型
     * @author 伍恰  
     * @date 2017年7月19日 上午11:20:49
     */
    @Test
    public void testGetExcelClass() {
        importFitApProService.getExcelClass();
    }
}
