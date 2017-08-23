/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 下午2:09:06
* 创建作者：伍恰
* 文件名称：HotControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.hot.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EmsExcelUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.excel.service.EmsExcelService;
import com.awifi.np.biz.devsrv.hot.service.HotService;
import com.awifi.np.biz.devsrv.model.servcie.ModelService;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RedisUtil.class,BeanUtil.class,DeviceQueryClient.class,DeviceClient.class,HotController.class,
        ExcelType.class,IOUtil.class,EmsExcelUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class,ValidUtil.class})
public class HotControllerTest{
    /**
     * 被测试类
     */
    @InjectMocks
    private HotController hotController;
    /**
     * 请求
     **/
    private MockMultipartHttpServletRequest request;
    /**
     * 响应
     */
    private MockHttpServletResponse response;
    
    /**
     * token
     */
    private String accessToken = "";
    /**
     * excel文件解析导入层
     */
    @Mock(name="emsExcelService")
    private EmsExcelService emsExcelService;
    /**
     * 文件状态持久层
     */
    @Mock(name="emsExcelDao")
    private  EmsExcelDao emsExcelDao;
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
    
    @Mock(name="hotService")
    private HotService hotService;
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月20日 下午2:13:28
     */
    @Before
    public void befor(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(DeviceQueryClient.class);
        PowerMockito.mockStatic(ExcelType.class);
        PowerMockito.mockStatic(DeviceClient.class);
//        PowerMockito.mockStatic(JsonUtil.class);
//        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(IOUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(EmsExcelUtil.class);
        request = new MockMultipartHttpServletRequest();
        response = new MockHttpServletResponse();
//        PowerMockito.when(BeanUtil.getBean("emsExcelService")).thenReturn(emsExcelService);
        
    }
    /**
     * 
     * @throws Exception
     * @author 范涌涛  
     * @date 2017年7月17日 下午6:55:53
     */
    @Test
    public void testImportExcel() throws Exception{
        String fileName = "test.xls";
        byte[] content = "testFYT".getBytes();
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("batchNum", "testBatchNum");
        resultMap.put("msg", "文件解析成功");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("content", fileName, "multipart/form-data", content);
        request.addFile(mockMultipartFile);
        Mockito.when(emsExcelService.addSelective(anyObject())).thenReturn(1);
        Mockito.when(emsExcelService.importExcel(anyObject(), anyObject(), anyObject())).thenReturn(resultMap);
        PowerMockito.when(ExcelType.valueOf(anyString())).thenReturn(ExcelType.Hot);
        PowerMockito.when(EmsExcelUtil.getDeviceFilePath()).thenReturn("/service");
        SessionUser sessionUser=new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        MultipartFile fileMock=Mockito.mock(MultipartFile.class);
        Mockito.doNothing().when(fileMock).transferTo(anyObject());
        hotController.importExcel(request, response, accessToken);

    }

    /**
     * 获取文件解析情况 测试
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月20日 下午2:22:45
     */
    @Test
    public void testShowDetail() throws Exception {
        String params = "";
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("type", "Hot");
        PowerMockito.stub(PowerMockito.method(JsonUtil.class, "fromJson")).toReturn(resultMap);
        PowerMockito.stub(PowerMockito.method(CastUtil.class,"toString")).toReturn("Hot");
        PowerMockito.when(ExcelType.valueOf(anyString())).thenReturn(ExcelType.Hot);
        SessionUser userMock=Mockito.mock(SessionUser.class);
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(userMock);
        Mockito.doNothing().when(emsExcelService).showDetail(anyObject(), anyObject(), anyString());
        hotController.showDetail(accessToken, params , request, response);
    }
    
    /**
     * 下载错误文件单元测试
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月17日 下午6:55:20
     */
    @Test
    public void testDownloadErrors() throws Exception{
        accessToken="token";
        String filename="testDownloadErrors.xls";
        File fileMock=PowerMockito.mock(File.class);
        when(fileMock.exists()).thenReturn(true);
        PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(fileMock);
        PowerMockito.when(EmsExcelUtil.getDeviceFilePath()).thenReturn("/service/");
        PowerMockito.doNothing().when(IOUtil.class,"download",anyString(), anyString(), anyObject());
        hotController.downloadErrors(request, response, accessToken, filename);
    }
    
    /**
     * 文件导入线程状态信息查询
     *  异常/参数
     * @author 伍恰  
     * @date 2017年6月20日 下午2:27:12
     */
    @Test
    public void testGetStatus(){
        String redisKeyStr = "{\"redisKey\":\"test\"}";
        Map<String,String> map = new HashMap<>();
        map.put("0-1", "{\"line\": \"0-1\",\"num\": \"1\",\"message\": \"等待导入\",\"status\": \"0\"}");
        map.put("2-3", "{\"line\": \"2-3\",\"num\": \"2\",\"message\": \"正在导入\",\"status\": \"1\"}");
        map.put("4-5", "{\"line\": \"4-5\",\"num\": \"3\",\"message\": \"导入成功\",\"status\": \"2\"}");
        map.put("6-7", "{\"line\": \"6-7\",\"num\": \"4\",\"message\": \"导入失败...\",\"status\": \"3\"}");
        List<Map<String,String>> maps = new ArrayList<>();
        maps.add(map);
        PowerMockito.when(RedisUtil.hgetAllBatch(anyString())).thenReturn(maps);
        hotController.getStatus(request, accessToken, redisKeyStr );

    }

    /**
     * 批量删除awifi热点
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月20日 下午2:31:54
     */
    @Test
    public void testDeleteHot() throws Exception {
        String params = "";
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("ids", "1,2,3");
//        PowerMockito.when(JsonUtil.fromJson(params,Map.class)).thenReturn(resultMap);
        PowerMockito.stub(PowerMockito.method(JsonUtil.class, "fromJson")).toReturn(resultMap);
        PowerMockito.stub(PowerMockito.method(CastUtil.class,"toString")).toReturn("1,2,3");
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyString(),anyString(),anyString());
        PowerMockito.doNothing().when(DeviceClient.class,"deleteHotareaByIds",anyObject());
        hotController.deleteHot(accessToken, params);
    }
    /**
     * 修改awifi热点
     * @throws Exception 异常/参数-------
     * @author 伍恰  
     * @date 2017年6月20日 下午2:34:12
     */
    @Test
    public void testUpdateHotarea() throws Exception {
        Map<String, Object> bodyParam = new HashMap<>();
        hotController.updateHotarea(accessToken, bodyParam);
    }
    /**
     * 根据ID查awifi/chinanet热点
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月20日 下午2:36:35
     */
    @Test
    public void testQueryChianetHotById() throws Exception {
        String id = "1";
        System.out.println(hotController.queryChianetHotById(accessToken, id ));
    }
    /**
     * 根据ID查awifi/Hot热点
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月20日 下午2:37:27
     */
    @Test
    public void testQueryAwifiHotById() throws Exception {
        String id = "1";
        hotController.queryAwifiHotById(accessToken, id );
    }
    /**
     * 
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月20日 下午2:41:04
     */
    @Test
    public void testQueryHotList() throws Exception {
        String params = "";
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("outTypeId", "CHINANET");
        PowerMockito.stub(PowerMockito.method(JsonUtil.class, "fromJson")).toReturn(resultMap);
        PowerMockito.stub(PowerMockito.method(CastUtil.class,"toString")).toReturn("CHINANET");
        hotController.queryHotList(accessToken, params);
        resultMap.put("outTypeId", "AWIFI");
        PowerMockito.stub(PowerMockito.method(JsonUtil.class, "fromJson")).toReturn(resultMap);
        PowerMockito.stub(PowerMockito.method(CastUtil.class,"toString")).toReturn("AWIFI");
        hotController.queryHotList(accessToken, params);
    }


}
