package com.awifi.np.biz.toe.admin.project.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.project.dao.ProjectDao;
import com.awifi.np.biz.toe.admin.project.model.Project;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月16日 上午11:01:58
 * 创建作者：亢燕翔
 * 文件名称：ProjectServiceImpl.java
 * 版本：  v1.0
 * 功能：  项目管理业务层
 * 修改记录：
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService{

    /** 项目管理持久层  */
    @Resource(name = "projectDao")
    private ProjectDao projectDao;
    
    /**
     * 项目列表分页查询
     * @param sessionUser session
     * @param projectName 项目名称
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param page 分页实体
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年1月16日 上午11:12:16
     */
    public void getListByParam(SessionUser sessionUser, String projectName,
            Long provinceId, Long cityId, Long areaId, Page<Project> page) throws Exception {
        Map<String, Object> permissionMap = getPermissionInfo(sessionUser,provinceId,cityId,areaId);//获取数据权限信息
        Long provinceIdParam = CastUtil.toLong(permissionMap.get("provinceId"));//省id
        Long cityIdParam = CastUtil.toLong(permissionMap.get("cityId"));//市id
        Long areaIdParam = CastUtil.toLong(permissionMap.get("areaId"));//区县id
        Long projectIdParam = CastUtil.toLong(permissionMap.get("projectId"));//项目id
        Long[] projectIds = (Long[]) permissionMap.get("projectIds");
        Long[] filterProjectIds = (Long[]) permissionMap.get("filterProjectIds");
        
         //获取项目数据总数
        int totalRecord = projectDao.getCountByParam(projectName,provinceIdParam,cityIdParam,areaIdParam,projectIdParam,projectIds,filterProjectIds);
        page.setTotalRecord(totalRecord);
        if(totalRecord > 0){
            List<Project> projectList = projectDao.getListByParam(projectName,provinceIdParam,cityIdParam,areaIdParam,projectIdParam,projectIds,filterProjectIds,page.getBegin(),page.getPageSize());
            String nameParam = "fullName";//地区名称参数
            Map<Long, String> locationMap = new HashMap<Long, String>(page.getPageSize());
            String locationFullName = null;
            for(Project project : projectList){
                //区县获取全路径
                Long areaIdLong = project.getAreaId();
                if(areaIdLong != null){
                    locationFullName = locationMap.get(areaIdLong);//先从map中获取，不存在则从redis中获取
                    project.setLocationFullName(locationFullName == null ? LocationClient.getByIdAndParam(areaIdLong, nameParam) : null);//设置到项目实体中
                    locationMap.put(areaIdLong, locationFullName);//放入map中
                    continue;
                }
                //市获取全路径
                Long cityIdLong = project.getCityId(); 
                if(cityIdLong != null){
                    locationFullName = locationMap.get(cityIdLong);
                    project.setLocationFullName(locationFullName == null ? LocationClient.getByIdAndParam(cityIdLong, nameParam) : null);//设置到项目实体中
                    locationMap.put(cityIdLong, locationFullName);//放入map中
                    continue;
                }
                //省获取全路径
                Long provinceIdLong = project.getProvinceId();
                if(provinceIdLong != null){
                    locationFullName = locationMap.get(provinceIdLong);
                    project.setLocationFullName(locationFullName == null ? LocationClient.getByIdAndParam(provinceIdLong, nameParam) : null);//设置到项目实体中
                    locationMap.put(provinceIdLong, locationFullName);//放入map中
                    continue;
                }
            }
            page.setRecords(projectList);
        }
    }
    
    /**
     * 通过用户信息进行权限控制
     * @param sessionUser sessionUser
     * @param provinceId 项目ID
     * @param cityId 市ID
     * @param areaId 区县ID
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月16日 上午11:24:30
     */
    private Map<String, Object> getPermissionInfo(SessionUser sessionUser,Long provinceId,Long cityId,Long areaId) throws Exception {
        /* 1.超管 userId=1 */
        if(PermissionUtil.isSuperAdmin(sessionUser)){//超级管理员不需要做任何限制
            return formatMap(provinceId,cityId,areaId,null,null,null);
        }
        
        /* 2.商户账号登陆，按商户层级[type]做数据权限做数据权限  */
        if(PermissionUtil.isMerchant(sessionUser)){
            Long projectId = projectDao.getProjectByMerchantId(sessionUser.getMerchantId());
            return formatMap(provinceId,cityId,areaId,projectId,null,null);
        }
        
        String merchantIds = sessionUser.getMerchantIds();
        String projectIds = sessionUser.getProjectIds();
        Long[] projectIdsArray = null;
        if(StringUtils.isNotBlank(projectIds)){//管理项目不为空，直接用
            projectIdsArray = CastUtil.toLongArray(projectIds.split(","));
        }else if(StringUtils.isNotBlank(merchantIds)){//单独管理一个或多个商户,没选管理项目
            projectIdsArray = getProjectIdsByMerchantIds(merchantIds);//通过商户ids获取项目ids
        }
        
        String filterProjectIds = sessionUser.getFilterProjectIds();
        Long[] filterProjectIdsArray = null;
        if(StringUtils.isNotBlank(filterProjectIds)){
            filterProjectIdsArray = CastUtil.toLongArray(filterProjectIds.split(","));
        }
        
        /* 3.其余，按省市区做数据权限  */
        Long provinceIdSession = sessionUser.getProvinceId();//省
        if(provinceIdSession != null){
            provinceId = provinceIdSession;
        }
        Long cityIdSession = sessionUser.getCityId();//市
        if(cityIdSession != null){
            cityId = cityIdSession;
        }
        Long areaIdSession = sessionUser.getAreaId();//区县
        if(areaIdSession != null){
            areaId = areaIdSession;
        }
        return formatMap(provinceId,cityId,areaId,null,projectIdsArray,filterProjectIdsArray);
    }
    
    /**
     * 根据商户id获取
     * @param merchantIds 商户ids
     * @return 项目ids
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年8月2日 上午11:14:11
     */
    private Long[] getProjectIdsByMerchantIds(String merchantIds) throws Exception{
        String[] merchantIdsArray = merchantIds.split(",");
        List<Long> projectIds = new ArrayList<Long>();
        int maxLength = merchantIdsArray.length;
        Long projectId = null;
        for(int i= 0; i<maxLength; i++){
            projectId = MerchantClient.getProjectIdById(Long.parseLong(merchantIdsArray[i]));//获取商户的项目id
            if(projectId != null){//如果商户的项目id不为空 拼接
                projectIds.add(projectId);
            }
        }
        return (Long[]) projectIds.toArray(new Long[maxLength]);
    }

    /**
     * 权限信息统一返回
     * @param provinceId 省ID
     * @param cityId 市ID
     * @param areaId 区县ID
     * @param projectId 项目ID
     * @param projectIds 登陆账号管理的项目id
     * @param filterProjectIds 登陆账号指明不管理的项目id
     * @return map
     * @author 亢燕翔  
     * @date 2017年1月16日 下午2:56:31
     */
    private Map<String, Object> formatMap(Long provinceId, Long cityId, Long areaId, Long projectId,Long[] projectIds,Long[] filterProjectIds) {
        Map<String, Object> permissionMap = new HashMap<String, Object>();
        permissionMap.put("provinceId", provinceId);
        permissionMap.put("cityId", cityId);
        permissionMap.put("areaId", areaId);
        permissionMap.put("projectId", projectId);
        permissionMap.put("projectIds", projectIds);
        permissionMap.put("filterProjectIds", filterProjectIds);
        return permissionMap;
    }

    /**
     * 添加项目
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月16日 下午7:15:18
     */
    public void add(Map<String, Object> bodyParam) throws Exception {
        String projectName = (String) bodyParam.get("projectName");//项目名称
        String contact = (String) bodyParam.get("contact");//联系人
        String contactWay = (String) bodyParam.get("contactWay");//联系方式
        Integer provinceId = CastUtil.toInteger(bodyParam.get("provinceId"));//省id
        Integer cityId = CastUtil.toInteger(bodyParam.get("cityId"));//市id
        Integer areaId = CastUtil.toInteger(bodyParam.get("areaId"));//区县id
        String remark = (String) bodyParam.get("remark");//备注
        /* 数据校验  */
        ValidUtil.valid("项目名称[projectName]", projectName, "required");
        ValidUtil.valid("省ID[provinceId]", provinceId, "{'required':true}");
        isProjectNameExist(null, projectName);//判断项目名称是否重复
        Integer platformId = Integer.parseInt(SysConfigUtil.getParamValue("toe_platform_id"));//ToE平台id
        projectDao.add(projectName,contact,contactWay,provinceId,cityId,areaId,platformId,remark);//保存项目
    }
    
    /**
     * 项目详情
     * @param id 项目id
     * @return Project
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月17日 上午9:52:24
     */
    public Project getById(Long id) throws Exception {
        Project project = projectDao.getById(id);//项目详情
        String nameParam = "name";//地区名称参数
        //设置省名称
        Long provinceId = project.getProvinceId();
        if(provinceId != null){
            project.setProvince(LocationClient.getByIdAndParam(provinceId, nameParam));
        }
        //设置市名称
        Long cityId = project.getCityId(); 
        if(cityId != null){
            project.setCity(LocationClient.getByIdAndParam(cityId, nameParam));
        }
        //设置区县名称
        Long areaId = project.getAreaId();
        if(areaId != null){
            project.setArea(LocationClient.getByIdAndParam(areaId, nameParam));
        }
        return project;
    }

    /**
     * 删除项目
     * @param id 项目id
     * @author 亢燕翔  
     * @date 2017年1月17日 上午9:59:54
     */
    public void delete(Long id) {
        isCustomerExistByProjectId(id);//判断项目下是否还存在商户
        projectDao.delete(id);//删除项目
    }
    
    /**
     * 编辑项目
     * @param id 项目id
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @date 2017年1月17日 上午10:26:45
     */
    public void update(Long id, Map<String, Object> bodyParam) {
        String projectName = (String) bodyParam.get("projectName");//项目名称
        String contact = (String) bodyParam.get("contact");//联系人
        String contactWay = (String) bodyParam.get("contactWay");//联系方式
        Integer provinceId = CastUtil.toInteger(bodyParam.get("provinceId"));//省ID
        Integer cityId = CastUtil.toInteger(bodyParam.get("cityId"));//市ID
        Integer areaId = CastUtil.toInteger(bodyParam.get("areaId"));//区县ID
        String remark = (String) bodyParam.get("remark");//备注
        /* 数据校验  */
        ValidUtil.valid("项目名称[projectName]", projectName, "required");
        ValidUtil.valid("省ID[provinceId]", provinceId, "{'required':true}");
        isProjectNameExist(id, projectName);//判断项目名称是否重复
        projectDao.update(id,projectName,contact,contactWay,provinceId,cityId,areaId,remark);//编辑项目
    }
    
    /**
     * 通过项目ID查询该项目下是否还有商户存在
     * @param id 项目ID
     * @author 亢燕翔  
     * @date 2017年1月17日 上午10:03:17
     */
    private void isCustomerExistByProjectId(Long id) {
        int count = projectDao.isCustomerExistByProjectId(id);//查询项目下是否存在商户
        if(count > 0){
            throw new ValidException("E2100001", MessageUtil.getMessage("E2100001"));//该项目已经关联客户，不允许删除!
        }
    }

    /**
     * 判断项目是否存在
     * @param id 项目ID
     * @param projectName 项目名称
     * @author 亢燕翔
     * @date 2017年1月16日 下午8:03:03
     */
    private void isProjectNameExist(Long id, String projectName) {
        int count = projectDao.getNumByProjectName(id, projectName);
        if(count > 0){//查询数量大于0，说明当前项目名称已经存在，抛出异常
            throw new ValidException("E2100002", MessageUtil.getMessage("E2100002",projectName));//项目名称(projectName[{0}])已存在!
        }
    }

    /**
     * 导出项目列表
     * @param sessionUser session
     * @param response response
     * @param projectName 项目名称
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param path 文件地址
     * @author 亢燕翔
     * @throws Exception 异常
     * @date 2017年1月20日 上午9:23:15
     */
    public void export(SessionUser sessionUser,HttpServletResponse response, String projectName, Long provinceId,Long cityId, Long areaId, String path) throws Exception {
        /*获取数据权限信息*/
        Map<String, Object> permissionMap = getPermissionInfo(sessionUser,provinceId,cityId,areaId);
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));//省id
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));//市id
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));//区县id
        Long projectIdLong = CastUtil.toLong(permissionMap.get("projectId"));//项目id
        Long[] projectIds = (Long[]) permissionMap.get("projectIds");
        Long[] filterProjectIds = (Long[]) permissionMap.get("filterProjectIds");
        
        Integer maxSize = 0;//数据的最大长度
        Integer sheetNum = 1;//sheet编号
        String sheetName = null;//sheet名称
        List<Object[]> listObj = new ArrayList<Object[]>();
        String fileName = "projectExport.xls";//文件名称
        String[] rowName = {"序号","项目名称","联系人","联系方式","创建时间"};//设置列表名称
        Integer pageSize = Integer.valueOf(SysConfigUtil.getParamValue("xls_export_sheet_max_size")).intValue();//每页数量--工作表每页数量
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        do{
            listObj = getListObj(projectName,provinceIdLong,cityIdLong,areaIdLong,projectIdLong,projectIds,filterProjectIds,sheetNum,pageSize,rowName.length);//获取数据
            maxSize = listObj.size();//数据长度
            sheetName = getSheetName(sheetNum,pageSize);
            ExcelUtil.fileWriteData(book,sheetName,sheetNum,rowName,listObj);//写文件内容
            sheetNum ++;
        } while (pageSize == maxSize);
        book.write();
        book.close();
        IOUtil.download(fileName, path, response);
    }
    
    /**
     * 创建sheet名称
     * @param sheetNum sheet编号
     * @param pageSize 每页数量
     * @return sheetName
     * @author 亢燕翔  
     * @date 2017年2月8日 下午8:03:53
     */
    private String getSheetName(Integer sheetNum, Integer pageSize) {
        return (((sheetNum - 1) * pageSize)+1)+"--"+(sheetNum * pageSize);
    }

    /**
     * 获取项目列表
     * @param projectName 项目名称
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param projectId 项目id
     * @param projectIds 管理项目id
     * @param filterProjectIds 排除项目id
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return List<Object[]>
     * @param rowLength 
     * @author 亢燕翔  
     * @date 2017年2月8日 下午6:43:22
     */
    private List<Object[]> getListObj(String projectName, Long provinceId,Long cityId, Long areaId, Long projectId,Long[] projectIds, Long[] filterProjectIds, Integer pageNo,Integer pageSize,Integer rowLength) {
        int begin = ((pageNo - 1) * pageSize);
        List<Project> projectList = projectDao.getListByParam(projectName,provinceId,cityId,areaId,projectId,projectIds,filterProjectIds,begin,pageSize);//获取数据集
        Object[] objs = null;
        List<Object[]> listObj = new ArrayList<Object[]>();
        for(Project project : projectList){//遍历数据，重新封装
            objs = new Object[rowLength];
            objs[0] = ++begin;
            objs[1] = project.getProjectName();
            objs[2] = project.getContact();
            objs[3] = project.getContactWay();
            objs[4] = project.getCreateDate();
            listObj.add(objs);
        }
        return listObj;
    }

    /**
     * 通过项目ids获取id和名称
     * @param projectIdSet 项目ids
     * @return id+名称
     * @author 周颖  
     * @date 2017年2月4日 上午8:53:40
     */
    @SuppressWarnings("rawtypes")
    public Map<Long,String> getIdAndNameByIds(Set<Long> projectIdSet){
        Long[] projectIdArray = CastUtil.toLongArray(projectIdSet);//转成Long数组
        List<Map> dataList = projectDao.getIdAndNameByIds(projectIdArray);//获取项目id和项目名称
        int maxSize = dataList != null ? dataList.size() : 0;
        Map<Long,String> projectMap = new HashMap<Long,String>();
        for(int i=0; i<maxSize; i++){//封装
            Map dataMap = dataList.get(i);
            Long id = CastUtil.toLong(dataMap.get("pk_id"));//项目id
            String projectName = (String)dataMap.get("project_name");//项目名称
            projectMap.put(id, projectName);
        }
        return projectMap;
    }
    
    /**
     * 通过项目id获取项目名称
     * @param id 项目id
     * @return 项目名称
     * @author 周颖  
     * @date 2017年2月6日 上午9:53:24
     */
    public String getNameById(Long id){
        if(id == null){
            return StringUtils.EMPTY;
        }
        return projectDao.getNameById(id);
    }
    
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
    public Long addProject(String projectName,String contact, String contactWay, Long provinceId, Long cityId, Long areaId){
        Long id = projectDao.getIdByName(projectName);
        if(id != null){
            return id;
        }
        Integer platformId = Integer.parseInt(SysConfigUtil.getParamValue("toe_platform_id"));//ToE平台id
        Project project = new Project();
        project.setProjectName(projectName);
        project.setPlatformId(platformId);
        project.setContact(contact);
        project.setContactWay(contactWay);
        project.setProvinceId(provinceId);
        project.setCityId(cityId);
        project.setAreaId(areaId);
        projectDao.insert(project);
        return project.getId();
    }
    
    /**
     * 获取项目名称
     * @param projectIds 项目ids
     * @return 项目名称
     * @author 周颖  
     * @date 2017年4月17日 上午10:50:24
     */
    public String getNamesByIds(String projectIds){
        //参数格式化
        if(StringUtils.isBlank(projectIds)){
            return StringUtils.EMPTY;
        }
        Long[] projectIdArray = CastUtil.toLongArray(projectIds.split(","));
        //获取并处理项目名称
        List<String> projectNameList = projectDao.getNamesByIds(projectIdArray);
        return CastUtil.listToString(projectNameList,',');
    }

    /**
     * 获取参数
     * @param type 类型
     * @param merchantIdLong 商户id
     * @param provinceIdLong 省Id
     * @param cityIdLong 市id
     * @param areaIdLong 区id
     * @param projectIds 项目ids
     * @return map
     * @author 王冬冬  
     * @date 2017年5月26日 下午2:52:54
     */
    private Map<String, Object> getDbParams(String type,Long merchantIdLong, Long provinceIdLong, Long cityIdLong, Long areaIdLong,String projectIds) {
        Map<String, Object> dbParams = new HashMap<String, Object>();
        dbParams.put("province", provinceIdLong);//省id
        dbParams.put("city", cityIdLong);//市id
        dbParams.put("county", areaIdLong);//区县id
        dbParams.put("merchantId", merchantIdLong);//商户id
        dbParams.put("projectIds", projectIds);//商户id
        dbParams.put("merchantQueryType", type);//this只查当前节点（默认）;nextLevel只查当前节点的下一层;nextAll查当前节点所有不包括当前;nextAllWithThis查当前节点所有包含当前
        return dbParams;
    }

    /**
     * 根据项目id获取设备和商户数
     * @param sessionUser 用户
     * @param projectIds 项目id
     * @return list
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月5日 下午4:31:40
     */
    public List<Map<String, Object>> getMerchantCountByProjectIds(SessionUser sessionUser, String projectIds)
            throws Exception {
        Long merchantId=sessionUser.getMerchantId();
        /*获取数据权限信息*/
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser, merchantId, null,null,null,null,null);
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));//省id
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));//市id
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));//区县id
        Long merchantIdLong=CastUtil.toLong(permissionMap.get("merchantId"));
        String type = permissionMap.get("type") != null ? (String) permissionMap.get("type") : null;
        Map<String,Object> dbParams=getDbParams(type, merchantIdLong, provinceIdLong, cityIdLong, areaIdLong, projectIds);
        List<Map<String,Object>> resultList=DeviceClient.getMerchantCountByProjectIds(JsonUtil.toJson(dbParams));
        return resultList;
    }
}