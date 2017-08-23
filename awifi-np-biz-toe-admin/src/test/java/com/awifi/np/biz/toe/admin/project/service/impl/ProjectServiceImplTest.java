package com.awifi.np.biz.toe.admin.project.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.project.dao.ProjectDao;
import com.awifi.np.biz.toe.admin.project.model.Project;

import jxl.Workbook;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月27日 上午10:06:20
 * 创建作者：亢燕翔
 * 文件名称：ProjectServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,LocationClient.class,Workbook.class,ExcelUtil.class,PermissionUtil.class,CastUtil.class,BeanUtil.class,DeviceClient.class})
@PowerMockIgnore({"javax.management.*"})
public class ProjectServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private ProjectServiceImpl projectServiceImpl;
    
    /**项目持久层*/
    @Mock(name = "sfTerminalConfigDao")
    private ProjectDao projectDao;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**响应*/
    private MockHttpServletResponse response;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(Workbook.class);
        PowerMockito.mockStatic(ExcelUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(DeviceClient.class);
    }
    
    /**
     * 测试项目列表区县id存在
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月27日 上午10:30:15
     */
    @Test
    public void testGetListByParamAreaId() throws Exception{
        Page<Project> page = new Page<Project>();
        page.setPageSize(10);
        SessionUser sessionUser = new SessionUser();
        when(projectDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(10);
        List<Project> projectList = new ArrayList<Project>();
        Project project = new Project();
        project.setProvinceId(31L);
        project.setCityId(313L);
        project.setAreaId(3145L);
        project.toString();
        projectList.add(project);
        when(projectDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(projectList);
        projectServiceImpl.getListByParam( sessionUser, "项目名称", 31L, null, null, page);
    }
    
    /**
     * 测试项目列表市id存在
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月27日 上午10:30:39
     */
    @Test
    public void testGetListByParamCityId() throws Exception{
        Page<Project> page = new Page<Project>();
        page.setPageSize(10);
        SessionUser sessionUser = new SessionUser();
        when(projectDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(10);
        List<Project> projectList = new ArrayList<Project>();
        Project project = new Project();
        project.setProvinceId(31L);
        project.setCityId(313L);
        projectList.add(project);
        when(projectDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(projectList);
        projectServiceImpl.getListByParam( sessionUser, "项目名称", 31L, null, null, page);
    }
    
    /**
     * 测试项目列表ok
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月27日 上午10:30:56
     */
    @Test
    public void testGetListByParam() throws Exception{
        Page<Project> page = new Page<Project>();
        page.setPageSize(10);
        SessionUser sessionUser = new SessionUser();
        when(projectDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(10);
        List<Project> projectList = new ArrayList<Project>();
        Project project = new Project();
        project.setProvinceId(31L);
        projectList.add(project);
        when(projectDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(projectList);
        projectServiceImpl.getListByParam( sessionUser, "项目名称", 31L, null, null, page);
    }
    
    /**
     * 测试添加项目
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月27日 上午10:31:13
     */
    @Test
    public void testAdd() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("projectName", "projectName");
        bodyParam.put("provinceId", 15);
        when(SysConfigUtil.getParamValue(anyObject())).thenReturn("15");
        projectServiceImpl.add(bodyParam);
    }
    
    /**
     * 测试项目详情
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月27日 上午10:32:09
     */
    @Test
    public void testGetById() throws Exception{
        Project project = new Project();
        project.setProvinceId(31L);
        project.setCityId(313L);
        project.setAreaId(3145L);
        when(projectDao.getById(anyObject())).thenReturn(project);
        projectServiceImpl.getById(4L);
    }
    
    /**
     * 测试删除项目
     * @author 亢燕翔  
     * @date 2017年2月27日 上午10:33:48
     */
    @Test
    public void testDelete(){
        projectServiceImpl.delete(4L);
    }
    
    /**
     * 测试编辑项目
     * @author 亢燕翔  
     * @date 2017年2月27日 上午10:35:49
     */
    @Test
    public void testUpdate(){
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("projectName", "projectName");
        bodyParam.put("provinceId", 15);
        projectServiceImpl.update(4L, bodyParam);
    }
    
    /**
     * 测试导出
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月27日 上午11:04:54
     */
    @Test(expected=Exception.class)
    public void testExport() throws Exception{
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        SessionUser sessionUser = new SessionUser();
        List<Project> projectList = new ArrayList<Project>();
        Project project = new Project();
        project.setProvinceId(31L);
        project.getId();
        project.setId(1L);
        project.setProjectName("xxx");
        project.setContact("xxx");
        project.setContactWay("xxx");
        project.getProvince();
        project.getCity();
        project.getArea();
        project.getAddress();
        project.setAddress("xxx");
        project.getLocationFullName();
        project.setCreateDate("xxx");
        project.getRemark();
        project.setRemark("xxx");
        projectList.add(project);
        when(projectDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(projectList);
        projectServiceImpl.export(sessionUser, response, "xxx", 31L, null, null, path);
    }
    
    /**
     * 通过项目ids获取id和名称
     * @author 方志伟  
     * @date 2017年6月21日 上午11:29:03
     */
    @Test
    public void testGetIdAndNameByIds(){
        Set<Long> set = new HashSet<Long>();
        set.add(1L);
        set.add(2L);
        set.add(3L);
        projectServiceImpl.getIdAndNameByIds(set);
    }
    
    /**
     * 通过项目id获取项目名称
     * @author 方志伟  
     * @date 2017年6月21日 上午11:29:48
     */
    @Test
    public void testGetNameById(){
        projectServiceImpl.getNameById(1L);
    }
    
    /**
     * 通过项目id获取项目名称 id = null
     * @author 方志伟  
     * @date 2017年6月21日 上午11:30:35
     */
    @Test
    public void testGetNameByIdIsNull(){
        projectServiceImpl.getNameById(null);
    }
    
    /**
     * 通过项目名称获取项目id，不存在则新建
     * @author 方志伟  
     * @date 2017年6月21日 上午11:33:39
     */
    @Test
    public void testAddProject(){
        projectServiceImpl.addProject("营业厅项目", "contact", "contactWay", 1L, 2L, 3L);
    }
    
    /**
     * 获取项目名称 projectIds == null
     * @author 方志伟  
     * @date 2017年6月21日 下午1:47:42
     */
    @Test
    public void testGetNamesByIds(){
        projectServiceImpl.getNamesByIds(null);
    }
    
    /**
     * 获取项目名称 projectIds != null
     * @author 方志伟  
     * @date 2017年6月21日 下午1:48:12
     */
    @Test
    public void testGetNamesByIdsIsNotNull(){
        projectServiceImpl.getNamesByIds("project");
    }
    
    /**
     * 根据项目id获取设备和商户数
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午1:55:47
     */
    @Test
    public void testGetMerchantCountByPIds() throws Exception{
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        projectServiceImpl.getMerchantCountByProjectIds(sessionUser, "projectIds");
    }
}
