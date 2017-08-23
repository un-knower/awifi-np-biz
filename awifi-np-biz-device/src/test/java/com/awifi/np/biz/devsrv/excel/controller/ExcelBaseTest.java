/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月17日 下午7:54:19
* 创建作者：范涌涛
* 文件名称：ExcelBaseTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.excel.controller;

import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.excel.controller.ExcelBase;

@RunWith(PowerMockRunner.class)
public class ExcelBaseTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private ExcelBase excelBase;
    
    /**
     * 请求
     **/
    public MockMultipartHttpServletRequest request;
    
    /**
     * 响应
     */
    public MockHttpServletResponse response;
    
    /** 初始化 */
    @Before
    public void before() {
        request = new MockMultipartHttpServletRequest();
        response = new MockHttpServletResponse();
    }
    /**
     * 从request中获取文件内容
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月17日 下午8:51:45
     */
    @Test
    public void testGetFile() throws Exception{
        String dir="/service/media/np/device/";
        String fileName = "test.xls";
        byte[] content = "testFYT".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("content", fileName, "text/plain", content);
        request.addFile(mockMultipartFile);
        excelBase.getFile(request, response, dir);
    }
    /**
     * 获取新的文件名
     * 
     * @author 范涌涛  
     * @date 2017年7月17日 下午8:21:50
     */
    @Test
    public void testGetNewFileName(){
        String filename="test.xls";
        excelBase.getNewFileName(filename);
    }
    /**
     * 获取文件状态
     * 
     * @author 范涌涛  
     * @date 2017年7月17日 下午8:21:25
     */
    @Test
    public void testGetExcelFileStatus(){
        String filename="test.xls";
        String path="/service/np/device/";
        ExcelType type=ExcelType.valueOf("Hot");
        SessionUser session = new SessionUser();
        excelBase.getExcelFileStatus(filename,path,type,session);
    }
    
}
