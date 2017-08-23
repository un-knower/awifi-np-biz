/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月10日 下午3:38:18
* 创建作者：王冬冬
* 文件名称：test.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.usrsrv.template.controller;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.model.NPTemplate;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,SessionUtil.class,SysConfigUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class TemplateControllerTest {
    /**被测试类*/
    @InjectMocks
    private TemplateController templateController;
    
    /**菜单服务*/
    @Mock(name = "templateService")
    private TemplateService templateService;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * 模板详情
     * @author 王冬冬  
     * @date 2017年5月10日 下午3:48:31
     */
    @Test
    public void testGetTemplateToAm(){
        NPTemplate template=new NPTemplate();
        Map<String,Object> resultMap = templateController.getTemplateToAm(template);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    /**
     * 模板更新
     * @author 王冬冬  
     * @date 2017年5月10日 下午4:07:05
     */
    @Test
    public void testUpdateTemplateToAm(){
        NPTemplate template=new NPTemplate();
        Map<String,Object> resultMap = templateController.updateTemplateToAm(template);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    
    /**
     * 模板删除
     * @author 王冬冬  
     * @date 2017年5月10日 下午4:09:11
     */
    @Test
    public void testDeleteTemplateToAm(){
        NPTemplate template=new NPTemplate();
        Map<String,Object> resultMap = templateController.deleteTemplateToAm(template);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    
    /**
     * 新增模板
     * @author 王冬冬  
     * @date 2017年5月10日 下午4:16:50
     */
    @Test
    public void testAddTemplateFromAm(){
        NPTemplate template=new NPTemplate();
        template.setSuitCode("xxxx");
        template.setTemplateCode("xxxx");
        template.setTemplateName("xxxx");
        template.setSrc("xxx");
        template.setContent("xxxx");
        template.setServiceCode("xxxx");
        Map<String,Object> resultMap = templateController.addTemplateFromAm(template);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
}
