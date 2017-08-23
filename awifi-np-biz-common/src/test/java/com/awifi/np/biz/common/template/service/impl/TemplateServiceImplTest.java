package com.awifi.np.biz.common.template.service.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.Date;

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

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.template.dao.TemplateDao;
import com.awifi.np.biz.common.template.model.Template;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午9:56:33
 * 创建作者：周颖
 * 文件名称：TemplateServiceImplTest.java
 * 版本：  v1.0
 * 功能：模板业务实现类测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class TemplateServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private TemplateServiceImpl templateServiceImpl;
    
    /**mock*/
    @Mock(name="templateDao")
    private TemplateDao templateDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * 测试成功
     * @author 周颖  
     * @date 2017年1月13日 上午10:27:16
     */
    @Test
    public void testOK(){
        when(templateDao.getByCode(anyString(), anyString(), anyString())).thenReturn("template");
        String suitCode = "suitCode";
        String serviceCode = "serviceCode";
        String templateCode = "templateCode";
        String result = templateServiceImpl.getByCode(suitCode, serviceCode, templateCode);
        Assert.assertNotNull(result);
    }
    
    /***
     * 测试抛业务异常
     * @author 周颖  
     * @date 2017年1月13日 上午10:27:33
     */
    @Test(expected = BizException.class)
    public void testBizException(){
        PowerMockito.when(MessageUtil.getMessage(anyString(), anyObject())).thenReturn("error");
        when(templateDao.getByCode(anyString(), anyString(), anyString())).thenReturn(null);
        String suitCode = "suitCode";
        String serviceCode = "serviceCode";
        String templateCode = "templateCode";
        String result = templateServiceImpl.getByCode(suitCode, serviceCode, templateCode);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        MessageUtil.getMessage(anyString(),anyObject());
    }
    
    
    /**
     * 测试新增模板
     * @author 王冬冬  
     * @date 2017年5月12日 下午1:53:42
     */
    @Test
    public void testaddTemplate(){
        Template template=new Template();
        template.setCode("xxx");
        template.setContent("xxx");
        template.setCreateDate(new Date());
        template.setName("xxx");
        template.setId(1L);
        template.setSuitCode("Xxx");
        when(templateDao.addTemplate(template)).thenReturn(1);
        Mockito.doNothing().when(templateDao).addTemplateSuit(template.getSuitCode(), template.getId());
      
        templateServiceImpl.addTemplate(template);
        Mockito.verify(templateDao).addTemplate(template);
        Mockito.verify(templateDao).addTemplateSuit(template.getSuitCode(), template.getId());
    }
    
    /**
     * 测试删除模板
     * @author 王冬冬  
     * @date 2017年5月12日 下午1:53:42
     */
    @Test
    public void testDeleteTemplate(){
        Template template=new Template();
        template.setCode("xxx");
        template.setContent("xxx");
        template.setCreateDate(new Date());
        template.setName("xxx");
        template.setId(1L);
        template.setSuitCode("Xxx");
        when(templateDao.getTemplateByParam(anyObject())).thenReturn(template);
        Mockito.doNothing().when(templateDao).deleteTemplateSuit(template.getSuitCode(),template.getId());
        Mockito.doNothing().when(templateDao).deleteTemplate(template);
        
        templateServiceImpl.deleteTemplate(template);
        Mockito.verify(templateDao).getTemplateByParam(anyObject());
        Mockito.verify(templateDao).deleteTemplateSuit(template.getSuitCode(),template.getId());
        Mockito.verify(templateDao).deleteTemplate(template);
    }
    
    
    /**
     * 测试新增模板
     * @author 王冬冬  
     * @date 2017年5月12日 下午1:53:42
     */
    @Test
    public void testUpdateTemplate(){
        Template template=new Template();
        template.setCode("xxx");
        template.setContent("xxx");
        template.setCreateDate(new Date());
        template.setName("xxx");
        template.setId(1L);
        template.setSuitCode("Xxx");
        
        Mockito.doNothing().when(templateDao).updateTemplate(template);
      
        templateServiceImpl.updateTemplate(template);
        Mockito.verify(templateDao).updateTemplate(template);
    }
    
    
    /**
     * 测试查询接口
     * @author 王冬冬  
     * @date 2017年5月12日 下午1:53:42
     */
    @Test
    public void testGetTemplateByParam(){
        Template template=new Template();
        template.setCode("xxx");
        template.setContent("xxx");
        template.setCreateDate(new Date());
        template.setName("xxx");
        template.setId(1L);
        template.setSuitCode("Xxx");
        
        when(templateDao.getTemplateByParam(anyObject())).thenReturn(template);
        templateServiceImpl.getTemplateByParam(template);
        Mockito.verify(templateDao).getTemplateByParam(anyObject());
    }
    
    
    /**
     * 测试模板是否存在
     * @author 王冬冬  
     * @date 2017年5月12日 下午1:53:42
     */
    @Test
    public void testIsExist(){
        Template template=new Template();
        template.setCode("xxx");
        template.setContent("xxx");
        template.setCreateDate(new Date());
        template.setName("xxx");
        template.setId(1L);
        template.setSuitCode("Xxx");
        
        when(templateDao.isTemplateExist(template.getSuitCode(), template.getServiceCode(), template.getCode())).thenReturn(1);
        templateServiceImpl.isExist(template);
        Mockito.verify(templateDao).isTemplateExist(template.getSuitCode(), template.getServiceCode(), template.getCode());
    }
    
    /**
     * 测试模板不存在
     * @author 王冬冬  
     * @date 2017年5月12日 下午1:53:42
     */
    @Test
    public void testIsNotExist(){
        Template template=new Template();
        template.setCode("xxx");
        template.setContent("xxx");
        template.setCreateDate(new Date());
        template.setName("xxx");
        template.setId(1L);
        template.setSuitCode("Xxx");
        
        when(templateDao.isTemplateExist(template.getSuitCode(), template.getServiceCode(), template.getCode())).thenReturn(0);
        templateServiceImpl.isExist(template);
        Mockito.verify(templateDao).isTemplateExist(template.getSuitCode(), template.getServiceCode(), template.getCode());
    }
    
    /**
     * 测试根据id获取模板
     * @author 王冬冬  
     * @date 2017年5月12日 下午1:53:42
     */
    @Test
    public void testGetExistTemplateId(){
        Template template=new Template();
        template.setCode("xxx");
        template.setContent("xxx");
        template.setCreateDate(new Date());
        template.setName("xxx");
        template.setId(1L);
        template.setSuitCode("Xxx");
        
        when(templateDao.getExistTemplateId(template.getSuitCode(), template.getServiceCode(), template.getCode())).thenReturn(1L);
        templateServiceImpl.getExistTemplateId(template);
        Mockito.verify(templateDao).getExistTemplateId(template.getSuitCode(), template.getServiceCode(), template.getCode());
    }
}