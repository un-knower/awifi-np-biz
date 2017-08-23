package com.awifi.np.biz.mersrv.merchant.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mersrv.merchant.service.MerchantService;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月15日 下午4:33:05
 * 创建作者：周颖
 * 文件名称：MerchantControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class, 
        ValidUtil.class,PermissionUtil.class,MessageUtil.class,RegexUtil.class,MerchantClient.class,
        MerchantController.class,Workbook.class,IndustryClient.class,LocationClient.class})
public class MerchantControllerTest {

    /**被测试类*/
    @InjectMocks
    private MerchantController merchantController;
    
    /**模板服务*/
    @Mock(name = "templateService")
    private TemplateService templateService;
    
    /**toe用户服务*/
    @Mock(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**商户服务*/
    @Mock(name = "merchantService")
    private MerchantService merchantService;
    
    /**项目*/
    @Mock(name="projectService")
    private ProjectService projectService;
    
    /**角色*/
    @Mock(name="toeRoleService")
    private ToeRoleService toeRoleService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**mock*/
    @Mock
    protected Log logger;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RegexUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(IndustryClient.class);
        PowerMockito.mockStatic(LocationClient.class);
    }
    
    /**
     * 显示接口抛异常
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午9:21:31
     */
    @Test(expected=BizException.class)
    public void testViewBizException() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue("servicecode_merchant")).thenReturn("merchant");
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        
        Map<String,Object> result = merchantController.view("accessToken", "templateCode", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue("servicecode_merchant");
        SessionUtil.getCurSessionUser(anyObject());
        MessageUtil.getMessage(anyString());
    }
    
    /**
     * 显示接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 上午9:27:27
     */
    @Test
    public void testViewBizOk() throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue("servicecode_merchant")).thenReturn("merchant");
        SessionUser sessionUser = new SessionUser();
        sessionUser.setSuitCode("suitCode");
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        when(templateService.getByCode(anyObject(), anyObject(), anyObject())).thenReturn("template");
        
        Map<String,Object> result = merchantController.view("accessToken", "templateCode", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue("servicecode_merchant");
        SessionUtil.getCurSessionUser(anyObject());
        MessageUtil.getMessage(anyString());
        verify(templateService).getByCode(anyObject(), anyObject(), anyObject());
    }

    /**
     * 商户列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 上午8:44:14
     */
    @Test
    public void testGetListByParam() throws Exception {
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(2L);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        Mockito.doNothing().when(merchantService).getListByParam(anyObject(), anyObject(), anyObject());
        
        Map<String,Object> result = merchantController.getListByParam("accessToken", "{'pageSize':2}", httpRequest);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        JsonUtil.fromJson(anyString(), anyObject());
        CastUtil.toInteger(anyObject());
        CastUtil.toLong(anyObject());
        SysConfigUtil.getParamValue("page_maxsize");
        SessionUtil.getCurSessionUser(anyObject());
    }
    
    /**
     * 商户列表 异常
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 上午8:55:39
     */
    @Test(expected=BizException.class)
    public void testGetListByParamBizException() throws Exception {
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        paramsMap.put("account", "account");
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        when(toeUserService.getMerIdByUserName(anyObject())).thenReturn(null);
        
        Map<String,Object> result = merchantController.getListByParam("accessToken", "{'pageSize':2}", httpRequest);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        JsonUtil.fromJson(anyString(), anyObject());
        CastUtil.toInteger(anyObject());
        SysConfigUtil.getParamValue("page_maxsize");
        MessageUtil.getMessage(anyString());
        verify(toeUserService).getMerIdByUserName(anyObject());
    }

    /**
     * 添加商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 上午9:20:29
     */
    @Test
    public void testAdd() throws Exception {
        PowerMockito.when(CastUtil.toLong(1)).thenReturn(1L);
        PowerMockito.when(CastUtil.toLong(31)).thenReturn(31L);
        PowerMockito.when(CastUtil.toLong(383)).thenReturn(383L);
        PowerMockito.when(CastUtil.toLong(3883)).thenReturn(3883L);
        PowerMockito.when(CastUtil.toInteger("storeType")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeLevel")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeStar")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeScope")).thenReturn(1);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        Mockito.doNothing().when(merchantService).add(anyObject(), anyObject(), anyObject());
        
        Map<String,Object> bodyParam =  new HashMap<String,Object>();
        bodyParam.put("projectId", 1);
        bodyParam.put("provinceId", 31);
        bodyParam.put("cityId", 383);
        bodyParam.put("areaId", 3883);
        bodyParam.put("storeType", "storeType");
        bodyParam.put("storeLevel", "storeLevel");
        bodyParam.put("storeStar", "storeStar");
        bodyParam.put("storeScope", "storeScope");
        Map<String,Object> result = merchantController.add("access_token", bodyParam, httpRequest);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toLong(1);
        CastUtil.toLong(31);
        CastUtil.toLong(383);
        CastUtil.toLong(3883);
        CastUtil.toInteger("storeType");
        CastUtil.toInteger("storeLevel");
        CastUtil.toInteger("storeStar");
        CastUtil.toInteger("storeScope");
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
        verify(merchantService).add(anyObject(), anyObject(), anyObject());
    }

    /**
     * 添加下级商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 上午9:42:58
     */
    @Test
    public void testAddSub() throws Exception {
        PowerMockito.when(CastUtil.toLong(1)).thenReturn(1L);
        PowerMockito.when(CastUtil.toLong(31)).thenReturn(31L);
        PowerMockito.when(CastUtil.toLong(383)).thenReturn(383L);
        PowerMockito.when(CastUtil.toLong(3883)).thenReturn(3883L);
        PowerMockito.when(CastUtil.toInteger("storeType")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeLevel")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeStar")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeScope")).thenReturn(1);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        Mockito.doNothing().when(merchantService).add(anyObject(), anyObject(), anyObject());
        
        Map<String,Object> bodyParam =  new HashMap<String,Object>();
        bodyParam.put("projectId", 1);
        bodyParam.put("provinceId", 31);
        bodyParam.put("cityId", 383);
        bodyParam.put("areaId", 3883);
        bodyParam.put("storeType", "storeType");
        bodyParam.put("storeLevel", "storeLevel");
        bodyParam.put("storeStar", "storeStar");
        bodyParam.put("storeScope", "storeScope");
        
        Map<String,Object> result = merchantController.addSub("access_token", bodyParam, httpRequest);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toLong(1);
        CastUtil.toLong(31);
        CastUtil.toLong(383);
        CastUtil.toLong(3883);
        CastUtil.toInteger("storeType");
        CastUtil.toInteger("storeLevel");
        CastUtil.toInteger("storeStar");
        CastUtil.toInteger("storeScope");
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
        verify(merchantService).add(anyObject(), anyObject(),anyObject());
    }

    /**
     * 商户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 上午9:26:54
     */
    @Test
    public void testGetById() throws Exception {
        Merchant merchant = new Merchant();
        when(merchantService.getById(anyLong())).thenReturn(merchant);
        
        Map<String,Object> result = merchantController.getById("access_token", 1L);
        Assert.assertNotNull(result);
        verify(merchantService).getById(anyLong());
        
    }

    /**
     * 商户编辑
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 上午9:35:15
     */
    @Test
    public void testUpdate() throws Exception {
        PowerMockito.when(CastUtil.toLong(1)).thenReturn(1L);
        PowerMockito.when(CastUtil.toLong(31)).thenReturn(31L);
        PowerMockito.when(CastUtil.toLong(383)).thenReturn(383L);
        PowerMockito.when(CastUtil.toLong(3883)).thenReturn(3883L);
        PowerMockito.when(CastUtil.toInteger("storeType")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeLevel")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeStar")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeScope")).thenReturn(1);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Mockito.doNothing().when(merchantService).update(anyObject(), anyObject());
        when(merchantService.isMerchantNameExist(anyObject())).thenReturn(false);
        
        Map<String,Object> bodyParam =  new HashMap<String,Object>();
        bodyParam.put("merchantName", "merchantName");
        bodyParam.put("oldMerchantName", "merchantName");
        bodyParam.put("projectId", 1);
        bodyParam.put("provinceId", 31);
        bodyParam.put("cityId", 383);
        bodyParam.put("areaId", 3883);
        bodyParam.put("storeType", "storeType");
        bodyParam.put("storeLevel", "storeLevel");
        bodyParam.put("storeStar", "storeStar");
        bodyParam.put("storeScope", "storeScope");
        
        Map<String,Object> result = merchantController.update("access_token", bodyParam, 1L);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toLong(1);
        CastUtil.toLong(31);
        CastUtil.toLong(383);
        CastUtil.toLong(3883);
        CastUtil.toInteger("storeType");
        CastUtil.toInteger("storeLevel");
        CastUtil.toInteger("storeStar");
        CastUtil.toInteger("storeScope");
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
        verify(merchantService).update(anyObject(), anyObject());
    }
    
    /**
     * 商户编辑 异常
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月16日 上午9:42:29
     */
    @Test(expected=ValidException.class)
    public void testUpdateValidException() throws Exception {
        PowerMockito.when(CastUtil.toLong(1)).thenReturn(1L);
        PowerMockito.when(CastUtil.toLong(31)).thenReturn(31L);
        PowerMockito.when(CastUtil.toLong(383)).thenReturn(383L);
        PowerMockito.when(CastUtil.toLong(3883)).thenReturn(3883L);
        PowerMockito.when(CastUtil.toInteger("storeType")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeLevel")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeStar")).thenReturn(1);
        PowerMockito.when(CastUtil.toInteger("storeScope")).thenReturn(1);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        when(merchantService.isMerchantNameExist(anyObject())).thenReturn(true);
       
        
        Map<String,Object> bodyParam =  new HashMap<String,Object>();
        bodyParam.put("merchantName", "merchantName");
        bodyParam.put("oldMerchantName", "oldMerchantName");
        bodyParam.put("projectId", 1);
        bodyParam.put("provinceId", 31);
        bodyParam.put("cityId", 383);
        bodyParam.put("areaId", 3883);
        bodyParam.put("storeType", "storeType");
        bodyParam.put("storeLevel", "storeLevel");
        bodyParam.put("storeStar", "storeStar");
        bodyParam.put("storeScope", "storeScope");
        
        Map<String,Object> result = merchantController.update("access_token", bodyParam, 1L);
        Assert.assertNotNull(result);
        
        PowerMockito.verifyStatic();
        CastUtil.toLong(1);
        CastUtil.toLong(31);
        CastUtil.toLong(383);
        CastUtil.toLong(3883);
        CastUtil.toInteger("storeType");
        CastUtil.toInteger("storeLevel");
        CastUtil.toInteger("storeStar");
        CastUtil.toInteger("storeScope");
        ValidUtil.valid(anyObject(),anyObject(),anyObject());
    }
    
    /**
     * 批量导入一级商户 文件为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月19日 下午12:30:25
     */
    @Test
    public void testImportMerchantNull() throws Exception{
        MultipartHttpServletRequest request = PowerMockito.mock(MultipartHttpServletRequest.class);
        HttpSession hs = PowerMockito.mock(HttpSession.class);
        PowerMockito.when(request.getSession()).thenReturn(hs);
        PowerMockito.when(SysConfigUtil.getParamValue("xls_import_max_size")).thenReturn("10");
        CommonsMultipartResolver multipartResolver = PowerMockito.mock(CommonsMultipartResolver.class);
        PowerMockito.whenNew(CommonsMultipartResolver.class).withAnyArguments().thenReturn(multipartResolver);
        PowerMockito.when(multipartResolver.isMultipart(anyObject())).thenReturn(true);
        
        List<String> list = new ArrayList<String>();
        list.add("test");
        Iterator<String>  iter = list.iterator();
        PowerMockito.when(request.getFileNames()).thenReturn(iter);
        
        merchantController.importMerchant("access_token", request);
        
    }
    
    /**
     * 批量导入一级商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月19日 下午12:28:56
     */
    @Test(expected=ValidException.class)
    public void testImportMerchantIndustryNull() throws Exception{
        MultipartHttpServletRequest request = PowerMockito.mock(MultipartHttpServletRequest.class);
        HttpSession hs = PowerMockito.mock(HttpSession.class);
        PowerMockito.when(request.getSession()).thenReturn(hs);
        PowerMockito.when(SysConfigUtil.getParamValue("xls_import_max_size")).thenReturn("10");
        CommonsMultipartResolver multipartResolver = PowerMockito.mock(CommonsMultipartResolver.class);
        PowerMockito.whenNew(CommonsMultipartResolver.class).withAnyArguments().thenReturn(multipartResolver);
        PowerMockito.when(multipartResolver.isMultipart(anyObject())).thenReturn(true);
        
        List<String> list = new ArrayList<String>();
        list.add("test");
        Iterator<String>  iter = list.iterator();
        PowerMockito.when(request.getFileNames()).thenReturn(iter);
        
        FileInputStream fis = PowerMockito.mock(FileInputStream.class);
        PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
        
        MultipartFile mf = PowerMockito.mock(MultipartFile.class);
        PowerMockito.when(request.getFile(anyString())).thenReturn(mf);
        
        InputStream is = PowerMockito.mock(InputStream.class);
        PowerMockito.when(mf.getInputStream()).thenReturn(is);

        PowerMockito.mockStatic(Workbook.class);
        Workbook book = PowerMockito.mock(Workbook.class);
        PowerMockito.when(Workbook.getWorkbook(any(InputStream.class))).thenReturn(book);
        
        Sheet sheet = PowerMockito.mock(Sheet.class);
        PowerMockito.when(book.getSheet(anyInt())).thenReturn(sheet);
        
        PowerMockito.when(sheet.getRows()).thenReturn(2);
        
        Cell cell = PowerMockito.mock(Cell.class);
        PowerMockito.when(sheet.getCell(anyInt(),anyInt())).thenReturn(cell);
        PowerMockito.when(cell.getContents()).thenReturn("content");
        
        merchantController.importMerchant("access_token", request);
        
    }
    
    /**
     * 正常数据
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月21日 上午9:30:51
     */
    @Test
    public void testImportMerchantAll() throws Exception{
        MultipartHttpServletRequest request = PowerMockito.mock(MultipartHttpServletRequest.class);
        HttpSession hs = PowerMockito.mock(HttpSession.class);
        PowerMockito.when(request.getSession()).thenReturn(hs);
        PowerMockito.when(SysConfigUtil.getParamValue("xls_import_max_size")).thenReturn("10");
        CommonsMultipartResolver multipartResolver = PowerMockito.mock(CommonsMultipartResolver.class);
        PowerMockito.whenNew(CommonsMultipartResolver.class).withAnyArguments().thenReturn(multipartResolver);
        PowerMockito.when(multipartResolver.isMultipart(anyObject())).thenReturn(true);
        
        List<String> list = new ArrayList<String>();
        list.add("test");
        Iterator<String>  iter = list.iterator();
        PowerMockito.when(request.getFileNames()).thenReturn(iter);
        
        FileInputStream fis = PowerMockito.mock(FileInputStream.class);
        PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
        
        MultipartFile mf = PowerMockito.mock(MultipartFile.class);
        PowerMockito.when(request.getFile(anyString())).thenReturn(mf);
        
        InputStream is = PowerMockito.mock(InputStream.class);
        PowerMockito.when(mf.getInputStream()).thenReturn(is);

        PowerMockito.mockStatic(Workbook.class);
        Workbook book = PowerMockito.mock(Workbook.class);
        PowerMockito.when(Workbook.getWorkbook(any(InputStream.class))).thenReturn(book);
        
        Sheet sheet = PowerMockito.mock(Sheet.class);
        PowerMockito.when(book.getSheet(anyInt())).thenReturn(sheet);
        
        PowerMockito.when(sheet.getRows()).thenReturn(2);
        
        Cell cell = PowerMockito.mock(Cell.class);
        PowerMockito.when(sheet.getCell(anyInt(),anyInt())).thenReturn(cell);
        PowerMockito.when(cell.getContents()).thenReturn("content");
        
        Map<String, List<Map<String, Object>>> industryMap = new HashMap<String, List<Map<String,Object>>>();
        List<Map<String, Object>> industryList = new ArrayList<Map<String,Object>>();
        Map<String, Object> industryParamMap = new HashMap<String, Object>();
        industryParamMap.put("industryId", "OCAB1001");
        industryParamMap.put("industryLevel", "2");
        industryParamMap.put("industryName", "中餐");
        industryList.add(industryParamMap);
        industryParamMap = new HashMap<String, Object>();
        industryParamMap.put("industryId", "OCAB10");
        industryParamMap.put("industryLevel", "1");
        industryParamMap.put("industryName", "餐饮");
        industryList.add(industryParamMap);
        industryMap.put("content", industryList);
        PowerMockito.when(IndustryClient.getIndustryMap()).thenReturn(industryMap);
        
        Map<String, List<Map<String, Object>>> locationMap = new HashMap<String, List<Map<String,Object>>>();
        List<Map<String, Object>> locationList = new ArrayList<Map<String,Object>>();
        Map<String, Object> locationParamMap = new HashMap<String, Object>();
        locationParamMap.put("id", 31L);
        locationParamMap.put("parentId", 1L);
        locationParamMap.put("name", "浙江");
        locationList.add(locationParamMap);
        locationParamMap = new HashMap<String, Object>();
        locationParamMap.put("id", 383L);
        locationParamMap.put("parentId", 31L);
        locationParamMap.put("name", "杭州");
        locationList.add(locationParamMap);
        locationParamMap = new HashMap<String, Object>();
        locationParamMap.put("id", 3230L);
        locationParamMap.put("parentId", 383L);
        locationParamMap.put("name", "上城区");
        locationList.add(locationParamMap);
        locationMap.put("content", locationList);
        PowerMockito.when(LocationClient.getLocationMap()).thenReturn(locationMap);
        
        when(toeRoleService.getIdByName(anyString())).thenReturn(1L);
        
        Map<String, Long> customerMap= new HashMap<String, Long>();
        customerMap.put("content", 1L);
        PowerMockito.when(MerchantClient.batchAdd(anyObject(),anyObject())).thenReturn(customerMap);
        
        Mockito.doNothing().when(toeRoleService).addUserRole(anyLong(), anyString());
        Mockito.doNothing().when(toeUserService).addUserMerchant(anyLong(),anyLong());
        
        merchantController.importMerchant("access_token", request);
    }
}
