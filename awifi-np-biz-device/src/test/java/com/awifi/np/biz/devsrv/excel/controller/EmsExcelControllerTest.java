/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月18日 下午7:09:42
* 创建作者：伍恰
* 文件名称：EmsExcelControllerTest.java
* 版本：  v1.0
* 功能： 定制终端 导入 测试类
* 修改记录：
*/
package com.awifi.np.biz.devsrv.excel.controller;


import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.multipart.MultipartFile;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EmsExcelUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.excel.service.EmsExcelService;

@PrepareForTest({RedisUtil.class})
public class EmsExcelControllerTest extends MockBase{
    
    /**
     * 被测试类
     */
    @InjectMocks
    private EmsExcelController emsExcelController;
    
    /**
     * 
     */
    @Mock
    private MockServletContext mockServletContext;
    
    /**
     * 模拟会话
     */
    @Mock
    private MockHttpSession mockHttpSession;
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
     * excel文件解析导入层
     */
    @Mock(name="emsExcelService")
    private EmsExcelService emsExcelService;
    
    /**
     * 批量导入excel文件内容信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午7:15:06
     */
    @Test
    public void testImportExcel() throws Exception {
        String fileName = "test.xls";
        byte[] content = "testFYT".getBytes();
        MockMultipartFile file = new MockMultipartFile("content", fileName, "multipart/form-data", content);
      //  LinkedHashMap titleMap =new LinkedHashMap();
        mockMultipartHttpServletRequest.addFile(file);
        PowerMockito.when(SysConfigUtil.getParamValue("resources_folder_path")).thenReturn("/service");
        //mockMultipartHttpServletRequest.setSession(mockHttpSession);
        //PowerMockito.when(mockMultipartHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        //PowerMockito.when(mockHttpSession.getServletContext()).thenReturn(mockServletContext);
        emsExcelController.importExcel(mockMultipartHttpServletRequest, response, access_token);
    }

    /**
     * 展示excel文件解析情况
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午7:16:35
     */
    @Test
    public void testShowDetail() throws Exception {
        String params = "{pageNo:1,pageSize:10}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("type", "FatAP");
        paramsMap.put("provinceId", "1");
        paramsMap.put("cityId", "1");
        paramsMap.put("areaId", "1");
        //paramsMap.put("pageNo", 1);
        //paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        emsExcelController.showDetail(access_token, params , request, response);
    }

    /**
     * 下载错误文件
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午7:16:49
     */
    @Test
    public void testDownloadErrors() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue("resources_folder_path")).thenReturn("/service");
        String path=EmsExcelUtil.getDeviceFilePath();//获取文件路径
        String filename = "99998179048ImportError.xls";
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        File file1=new File(path+filename);
        if(!file1.exists()){
            file1.createNewFile();
        }
        emsExcelController.downloadErrors(request, response, access_token, filename);
    }

    /**
     * 查询导入数据中心的状态
     *  异常/参数
     * @author 伍恰  
     * @date 2017年7月18日 下午7:17:05
     */
    @Test
    public void testGetStatus() {
        String params = "{}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("redisKey", "FatAP-20170718-H3C-1");
        paramsMap.put("provinceId", "1");
        paramsMap.put("cityId", "1");
        paramsMap.put("areaId", "1");
        List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
        Map<String,String> map = new HashMap<>();
        map.put("0-0", "{\"line\": \"0-0\",\"num\": \"1\",\"message\": \"设备类型不对(DBC-301001001004)!\",\"status\": \"3\"}");
        maps.add(map);
        PowerMockito.when(CastUtil.toString("0-0")).thenReturn("0-0");
        PowerMockito.when(RedisUtil.hgetAllBatch(anyObject())).thenReturn(maps);
        PowerMockito.when(JsonUtil.fromJson("{}", Map.class)).thenReturn(paramsMap);
        Map<String,String> redisMap = new HashMap<>();
        redisMap.put("num", "1"); 
        redisMap.put("line", "0-0"); 
        redisMap.put("status", "3"); 
        redisMap.put("message", "设备类型不对");
        PowerMockito.when(JsonUtil.fromJson(map.get("0-0").toString(), Map.class)).thenReturn(redisMap);
        emsExcelController.getStatus(request, access_token, params );
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(1);
        emsExcelController.getStatus(request, access_token, params );
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(2);
        emsExcelController.getStatus(request, access_token, params );
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(3);
        emsExcelController.getStatus(request, access_token, params );
    }

}
