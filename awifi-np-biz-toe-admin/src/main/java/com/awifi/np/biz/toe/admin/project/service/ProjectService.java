package com.awifi.np.biz.toe.admin.project.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.toe.admin.project.model.Project;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 11, 2017 10:23:09 AM
 * 创建作者：亢燕翔
 * 文件名称：ProjectService.java
 * 版本：  v1.0
 * 功能：  项目管理业务层
 * 修改记录：
 */
public interface ProjectService {

    /**
     * 项目分页列表查询
     * @param sessionUser sessionUser
     * @param projectName 项目名称
     * @param provinceId 省ID
     * @param cityId 市ID
     * @param areaId 区县ID
     * @param page 分页实体表
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月16日 上午11:12:21
     */
    void getListByParam(SessionUser sessionUser, String projectName, Long provinceId, Long cityId, Long areaId, Page<Project> page) throws Exception;

    /**
     * 添加项目
     * @param bodyParam 请求体数据
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月16日 下午7:13:33
     */
    void add(Map<String, Object> bodyParam) throws Exception;
    
    /**
     * 项目详情
     * @param id 项目ID
     * @return 项目实体
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月17日 上午9:51:54
     */
    Project getById(Long id) throws Exception;
    
    /**
     * 删除项目
     * @param id 项目ID
     * @author 亢燕翔  
     * @date 2017年1月17日 上午9:59:33
     */
    void delete(Long id);
    
    /**
     * 编辑项目
     * @param id 项目ID
     * @param bodyParam 请求体数据
     * @author 亢燕翔  
     * @date 2017年1月17日 上午10:26:06
     */
    void update(Long id, Map<String, Object> bodyParam);
    
    /**
     * 导出项目列表
     * @param sessionUser sessionUser
     * @param response 响应
     * @param projectName 项目名称
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param path 文件路径
     * @author 亢燕翔  
     * @throws IOException 
     * @throws Exception 
     * @date 2017年1月20日 上午9:22:34
     */
    void export(SessionUser sessionUser,HttpServletResponse response, String projectName, Long provinceId,Long cityId, Long areaId, String path) throws IOException, Exception;
    
    /**
     * 通过项目ids获取id和名称
     * @param projectIdSet 项目ids
     * @return id+名称
     * @author 周颖  
     * @date 2017年2月4日 上午8:53:40
     */
    Map<Long,String> getIdAndNameByIds(Set<Long> projectIdSet);

    /**
     * 通过项目id获取项目名称
     * @param id 项目id
     * @return 项目名称
     * @author 周颖  
     * @date 2017年2月6日 上午9:53:24
     */
    String getNameById(Long id);

    /**
     * 通过项目名称获取项目id，不存在则新建
     * @param projectName 项目名称
     * @param contact 联系人
     * @param contactWay 联系方式
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @return 项目id
     * @author 周颖  
     * @date 2017年4月10日 上午10:45:01
     */
    Long addProject(String projectName,String contact, String contactWay, Long provinceId, Long cityId, Long areaId);
    
    /**
     * 获取项目名称
     * @param projectIds 项目ids
     * @return 项目名称
     * @author 周颖  
     * @date 2017年4月17日 上午10:50:24
     */
    String getNamesByIds(String projectIds);

    /**
     * @param sessionUser 用户
     * @param projectIds 项目id
     * @return list
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月5日 下午4:31:40
     */
    List<Map<String,Object>> getMerchantCountByProjectIds(SessionUser sessionUser, String projectIds) throws Exception;
}