package com.awifi.np.biz.toe.portal.component.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;
import com.awifi.np.biz.toe.portal.component.dao.ComponentDao;
import com.awifi.np.biz.toe.portal.component.model.Component;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月22日 上午10:54:33
 * 创建作者：许尚敏
 * 文件名称：ComponentServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, CommonsMultipartResolver.class,MerchantClient.class,StringUtils.class})
public class ComponentServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private ComponentServiceImpl componentServiceImpl;
    
    /**组件*/
    @Mock(name = "componentDao")
    private ComponentDao componentDao;
    
    /**项目*/
    @Mock(name = "projectService")
    private ProjectService projectService;
    
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(CommonsMultipartResolver.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(StringUtils.class);
    }
    
    /**
     * 组件列表测试
     * @author 许尚敏 
     * @date 2017年6月22日 上午11:00:17
     */
    @Test
    public void testGetListByParam() {
        Page<Component> page = new Page<Component>();
        page.setBegin(1);
        page.setPageSize(10);
        List<Component> componentList = new ArrayList<Component>();
        Component component = new Component();
        component.setThumb("aaa");
        componentList.add(component);
        PowerMockito.when(componentDao.getCountByParam(anyObject())).thenReturn(2);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("resources_domain");
        PowerMockito.when(componentDao.getListByParam(anyString(),anyObject(),anyObject())).thenReturn(componentList);
        componentServiceImpl.getListByParam(page, "");
    }

    /**
     * 组件添加测试
     * @throws Exception 异常
     * @author 许尚敏   
     * @date 2017年6月22日 下午16:28:17
     */
    @Test
    public void testAdd() throws Exception{
//        httpRequest.setParameter("componentname", "uid2");
//        httpRequest.setParameter("componenttype", "{1}");
//        httpRequest.setParameter("classify", "1");
//        httpRequest.setParameter("projectids", "1,2");
//        httpRequest.setParameter("filterprojectids", "1,2");
//        httpRequest.setParameter("canunique", "1");
//        httpRequest.setParameter("version", "1");
//        httpRequest.setParameter("remark", "11");
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
//        PowerMockito.when(multipartResolver.isMultipart(anyObject())).thenReturn(true);
//        PowerMockito.doNothing().when(multipartResolver).isMultipart(httpRequest);
//        componentServiceImpl.add(httpRequest);
    }

    /**
     * 组件编辑测试
     * @throws Exception 异常
     * @author 许尚敏   
     * @date 2017年6月22日 下午19:28:17
     */
    @Test
    public void testEdit() throws Exception{
//        httpRequest.setParameter("componentname", "uid2");
//        httpRequest.setParameter("componenttype", "{1}");
//        httpRequest.setParameter("classify", "1");
//        httpRequest.setParameter("projectids", "1,2");
//        httpRequest.setParameter("filterprojectids", "1,2");
//        httpRequest.setParameter("canunique", "1");
//        httpRequest.setParameter("version", "1");
//        httpRequest.setParameter("remark", "11");
//        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
//        PowerMockito.when(multipartResolver.isMultipart(anyObject())).thenReturn(true);
//        componentServiceImpl.edit(httpRequest, 1L);
    }

    /**
     * 组件详情测试
     * @throws Exception 异常
     * @author 许尚敏   
     * @date 2017年6月22日 下午19:28:17
     */
    @Test
    public void testGetById() {
        Component component = new Component();
        component.setThumb("aaa");
        component.setProjectIds("aaa");
        component.setFilterProjectIds("bbb");
        PowerMockito.when(componentDao.getById(anyObject())).thenReturn(component);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("aaa");
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(false);
        componentServiceImpl.getById(1L);
    }

    /**
     * 获取后缀测试
     * @author 许尚敏   
     * @date 2017年6月21日 上午10:01:17
     */
    @Test
    public void testGetFileSuffix() {
        ComponentServiceImpl.getFileSuffix("aaa.txt");
    }
    
    /**
     * 获取后缀测试
     * @author 许尚敏   
     * @date 2017年6月21日 上午10:01:17
     */
    @Test
    public void testGetFileSuffixNull() {
        ComponentServiceImpl.getFileSuffix("");
    }
    
    /**
     * 获取后缀测试
     * @author 许尚敏   
     * @date 2017年6月21日 上午10:01:17
     */
    @Test
    public void testGetFileSuffixFileName() {
        ComponentServiceImpl.getFileSuffix("111.txt");
    }

    /**
     * 获取后缀测试
     * @author 许尚敏   
     * @throws Exception 异常
     * @date 2017年6月28日 上午10:01:17
     */
    @Test
    public void testGetListByType() throws Exception{
        List<Component> componentList = new ArrayList<Component>();
        //PowerMockito.when(MerchantClient.getByIdCache(anyObject()).getProjectId()).thenReturn(1L);
        Component component = new Component();
        componentList.add(component);
        //PowerMockito.when(componentDao.getListByTypeAndProjectId(anyObject(),"{" + anyObject() + "}")).thenReturn(componentList);
        PowerMockito.when(componentDao.getListByType(anyObject())).thenReturn(componentList);
        componentServiceImpl.getListByType(null, 1);
    }
    
    /**
     * 获取后缀测试
     * @author 许尚敏   
     * @throws Exception 异常
     * @date 2017年6月28日 上午5:01:17
     */
    @Test
    public void testGetListByTypeNull() throws Exception{
        List<Component> componentList = new ArrayList<Component>(); 
        //PowerMockito.when(MerchantClient.getByIdCache(anyObject()).getProjectId()).thenReturn(1L);
        Component component = new Component();
        componentList.add(component);
        //PowerMockito.when(componentDao.getListByTypeAndProjectId(anyObject(),"{" + anyObject() + "}")).thenReturn(componentList);
        PowerMockito.when(componentDao.getListByType(anyObject())).thenReturn(componentList);
        componentServiceImpl.getListByType(null, 1);
    }

    /**
     * 图片组件图片上传测试
     * @author 许尚敏   
     * @throws Exception 异常
     * @date 2017年6月28日 上午10:30:17
     */
    @Test
    public void testPicUpload() throws Exception{
        //CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
        //PowerMockito.when(multipartResolver.isMultipart(httpRequest)).thenReturn(true);
        //componentServiceImpl.picUpload(httpRequest);
//        componentServiceImpl = new ComponentServiceImpl(){
//            public String picUpload(HttpServletRequest request){
//                return "";
//            }
//        };
    }

}
