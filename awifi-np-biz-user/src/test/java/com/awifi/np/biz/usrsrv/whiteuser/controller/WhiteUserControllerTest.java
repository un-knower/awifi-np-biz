/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月11日 上午10:41:27
* 创建作者：王冬冬
* 文件名称：WhiteUserControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.usrsrv.whiteuser.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.whiteuser.service.WhiteUserService;
import com.awifi.np.biz.toe.admin.security.org.util.OrgUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,SessionUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class, ValidUtil.class,OrgUtil.class,MessageUtil.class,RegexUtil.class,RegexUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class WhiteUserControllerTest {
    /**白名单服务*/
    @Mock(name = "whiteUserService")
    private WhiteUserService whiteUserService;
    
    /**被测试类*/
    @InjectMocks
    private WhiteUserController whiteUserController;
   
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(OrgUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RegexUtil.class);
    }
    
    
    /**
     * 测试列表
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月11日 上午10:51:26
     */
    @Test
    public void testGetListByParam() throws Exception{
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(paramsMap);
        PowerMockito.doNothing().when(ValidUtil.class, "valid",anyObject(),anyObject(),anyObject());
        Mockito.doNothing().when(whiteUserService).getListByParam(anyObject(),anyObject(),anyObject(),anyObject());
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String,Object> result = whiteUserController.getListByParam("access_token", "{'pageSize':2}", request);
        Assert.assertNotNull(result);
    }
    
    /**
     * 测试新增
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月11日 下午1:27:55
     */
    @Test
    public void testadd() throws Exception {
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("merchantId", 0);
//        paramsMap.put("mac","FFFFFFFF");
        paramsMap.put("cellphone","13200000000");
        try {
            Mockito.doNothing().when(whiteUserService).add(anyObject());
            Map<String,Object> result = whiteUserController.add("access_token", paramsMap);
            Assert.assertNotNull(result);
            verify(whiteUserService).add(anyObject());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        paramsMap.clear();
        
        Map<String, Object> result=null;
        try {
            result = whiteUserController.add("access_token", paramsMap);
        } catch (BizException e) {
            
        } catch (Exception e) {
            throw new Exception();
        }
        try{
            Mockito.when(whiteUserService.isCellphoneExist(0L,"13200000000")).thenReturn(true);
            paramsMap.put("merchantId", 0);
//          paramsMap.put("mac","FFFFFFFF");
            paramsMap.put("cellphone","13200000000");
            result = whiteUserController.add("access_token", paramsMap);
        }catch (BizException e) {
            
        }
        
        try{
            Mockito.when(whiteUserService.isCellphoneExist(0L,"13200000000")).thenReturn(false);
            Mockito.when(whiteUserService.isMacExist(0L,"FFFFFFFF")).thenReturn(true);
            paramsMap.put("merchantId", 0);
            paramsMap.put("mac","FFFFFFFF");
            paramsMap.put("cellphone","13200000000");
            result = whiteUserController.add("access_token", paramsMap);
        }catch (BizException e) {
            
        }
    }
    
    /**
     * 测试单个删除
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月11日 下午1:39:35
     */
    @Test
    public void testDeleteById() throws Exception{
        Mockito.doNothing().when(whiteUserService).delete(1);
        Map<String,Object> result = whiteUserController.delete(request,"access_token", 1);
        Assert.assertNotNull(result);
        verify(whiteUserService).delete(1);
    }
    
    /**
     * 测试批量删除
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月11日 下午1:39:35
     */
    @Test
    public void testDeleteBatch() throws Exception{
        Mockito.doNothing().when(whiteUserService).batchDelete("1,2,3");
        Map<String,Object> result = whiteUserController.batchdelete(request,"access_token","1,2,3");
        Assert.assertNotNull(result);
        verify(whiteUserService).batchDelete("1,2,3");
    }
    
    /**
     * 测试批量添加
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月11日 下午1:39:35
     */
    @Test
    public void testaddBatch() throws Exception{
        Map<String,Object> paramsMap =  new HashMap<String,Object>();
        paramsMap.put("merchantId", 0);
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(paramsMap);
        PowerMockito.when(SysConfigUtil.getParamValue("xls_import_max_size")).thenReturn("1000");
        try{
            Map<String,Object> result = whiteUserController.batchAdd("test_token", request, "");
        }catch(BizException e){
            
        }
    } 
    
}
