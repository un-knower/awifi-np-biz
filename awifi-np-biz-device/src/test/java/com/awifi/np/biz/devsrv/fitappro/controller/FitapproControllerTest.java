/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月13日 下午4:50:46
* 创建作者：伍恰
* 文件名称：FitapproControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fitappro.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.mock.web.MockMultipartFile;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.excel.service.EmsExcelService;
import com.awifi.np.biz.devsrv.fitappro.service.FitapproService;

@PrepareForTest({DeviceQueryClient.class})
public class FitapproControllerTest extends MockBase{
    /**
     * 被测试类
     */
    @InjectMocks
    private FitapproController fitapproController;
    /**
     * 项目型瘦ap的service层
     */
    @Mock(name="fitapproService")
    private FitapproService fitapproService;
    
    /**
     * excel导入service层
     */
    @Mock(name="emsExcelService")
    private EmsExcelService emsExcelService;
    
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月13日 下午4:05:16
     */
    @Before
    public void befor() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(DeviceQueryClient.class);
    }
    
    /**
     * 项目型瘦ap查询功能
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:55:19
     */
    @Test
    public void testGetFitapproList() throws Exception {
        String params = "{pageNo:1,pageSize:10}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("macAddr", "A23123B24149");
        paramsMap.put("provinceId", "1");
        paramsMap.put("cityId", "1");
        paramsMap.put("areaId", "1");
        //paramsMap.put("pageNo", 1);
        //paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        fitapproController.getFitapproList(access_token, params , request, response);
    }
    /**
     * 项目型瘦ap删除功能
     *  异常/参数
     * @author 伍恰  
     * @throws Exception 
     * @date 2017年6月13日 下午4:55:33
     */
    @Test
    public void testDeleteFitapproList() throws Exception {
        String  deviceId = "PRO_FitAP_31_201706108450ef00-f3e3-4c3c-9bc8-d4a1b6ba0162";
        String id ="123";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("idArr", "123,1");
        CenterPubEntity entity = new CenterPubEntity();
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        entity.setMerchantId(0L);
        PowerMockito.when(fitapproService.getFitapproById(anyObject())).thenReturn(entity);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        fitapproController.deleteFitapproList(access_token, deviceId, id);
    }
    /**
     * 项目型瘦ap的 Excel 导入
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:56:42
     */
    @SuppressWarnings("unused")
    @Test
    public void testImportFitappro() throws Exception {
        String fileName = "test.xls";
        //创建excel文件对象  
        @SuppressWarnings("resource")
        HSSFWorkbook wb = new HSSFWorkbook();  
        //创建一个张表  
        Sheet sheet = wb.createSheet();  
        //创建第一行  
        Row row = sheet.createRow(0);  
        //创建第二行  
        Row row1 = sheet.createRow(1);  
        byte[] content = wb.getBytes();
        File testPath = new File("/service/tmp");
        if (!testPath.exists()) {
            testPath.mkdirs();
        }
        File test = new File("/service/tmp/test.xls");
        if(!test.exists()){
            test.createNewFile();
        }
        FileOutputStream fOut  =   new  FileOutputStream("/service/tmp/test.xls");
        wb.write(fOut);
        fOut.flush();
        fOut.close();
        FileInputStream fIn = new FileInputStream(test);
        MockMultipartFile file = new MockMultipartFile(fileName, ".xls", "application/x-excel", fIn);
        fIn.close();
        mockMultipartHttpServletRequest.addFile(file);
        PowerMockito.when(SysConfigUtil.getParamValue("resources_folder_path")).thenReturn("/service");
        fitapproController.importFitappro(mockMultipartHttpServletRequest, access_token);
    }

}
