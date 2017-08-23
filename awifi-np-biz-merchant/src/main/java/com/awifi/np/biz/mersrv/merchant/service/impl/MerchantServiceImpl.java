package com.awifi.np.biz.mersrv.merchant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.mersrv.merchant.service.MerchantService;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;
import com.awifi.np.biz.toe.admin.security.role.model.ToeRole;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月24日 上午8:52:14
 * 创建作者：周颖
 * 文件名称：MerchantServiceImpl.java
 * 版本：  v1.0
 * 功能：商户实现类
 * 修改记录：
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {
    
    /**项目服务*/
    @Resource(name = "projectService")
    private ProjectService projectService;
    
    /**toe用户服务*/
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**toe角色服务*/
    @Resource(name = "toeRoleService")
    private ToeRoleService toeRoleService;

    /**
     * 商户列表
     * @param sessionUser sessionUser
     * @param page page
     * @param paramsMap 参数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 上午9:30:36
     */
    public void getListByParam(SessionUser sessionUser,Page page,Map<String,Object> paramsMap) throws Exception{
        Long merchantId = (Long) paramsMap.get("merchantId");
        Long provinceId = (Long) paramsMap.get("provinceId");
        Long cityId = (Long) paramsMap.get("cityId");
        Long areaId = (Long) paramsMap.get("areaId");
        String projectIds = (String) paramsMap.get("projectIds");
        String filterProjectIds = (String)paramsMap.get("filterProjectIds");
        Map<String, Object> param = PermissionUtil.dataPermission(sessionUser, merchantId, provinceId, cityId, areaId, projectIds, filterProjectIds);//数据权限
        paramsMap.put("merchantId", (Long) param.get("merchantId"));
        paramsMap.put("merchantIds", (String) param.get("merchantIds"));
        paramsMap.put("type", (String) param.get("type"));
        paramsMap.put("provinceId", (Long) param.get("provinceId"));
        paramsMap.put("cityId", (Long) param.get("cityId"));
        paramsMap.put("areaId", (Long) param.get("areaId"));
        paramsMap.put("filterProjectIds", (String)param.get("filterProjectIds"));
        paramsMap.put("projectIds", (String)param.get("projectIds"));
        //符合条件的总数
        int count = MerchantClient.getCountByParam(paramsMap);
        page.setTotalRecord(count);//page置总条数
        if(count <= 0){//如果小于0 直接返回
            return;
        }
        paramsMap.put("pageNo", page.getPageNo());
        paramsMap.put("pageSize", page.getPageSize());
        //符合条件的记录
        List<Merchant> merchantList = MerchantClient.getListByParam(paramsMap);
        formateMerchantList(merchantList);
        page.setRecords(merchantList);
    }
    
    /**
     * 商户列表格式化
     * @param merchantList 商户列表
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 下午4:51:53
     */
    private void formateMerchantList(List<Merchant> merchantList) throws Exception{
        Map<Long,String> merchantParentNameCacheMap = new HashMap<Long,String>();//用于减少查询次数
        Map<String,String> industryCacheMap = new HashMap<String,String>();//用于减少重复行业查询的次数
        //Map<Long,String> locationCacheMap = new HashMap<Long,String>();//用于减少重复地区查询的次数
        Set<Long> projectIdSet = new HashSet<Long>();//项目id 批量获取项目名称
        List<Long> merchantIdList = new ArrayList<Long>();
        String locationFullName= null;//地区全称
        for(Merchant merchant : merchantList){
            merchantIdList.add(merchant.getId());
            //1格式化行业信息
            this.formatIndustry(merchant, industryCacheMap);//格式化 行业
            //2格式化地区
            //this.formatLocation(merchant, locationCacheMap);//格式化地区
            locationFullName = FormatUtil.locationFullName(merchant.getProvince(), merchant.getCity(), merchant.getArea());
            merchant.setLocationFullName(locationFullName);//地区全路径
            //3格式化项目
            Long projectId = merchant.getProjectId();
            if(projectId != null){
                projectIdSet.add(projectId);
            }
            //4格式化父商户
            Long parentId = merchant.getParentId();
            //此处continue是为了防止空ID调用接口
            if(parentId == 0){
                continue;
            }
            /* 父客户名称特殊处理，临时缓存该信息，防止重复查询 */
            String parentName = merchantParentNameCacheMap.get(parentId);
            if(StringUtils.isBlank(parentName)){
                parentName = MerchantClient.getNameByIdCache(parentId);
                merchantParentNameCacheMap.put(parentId, parentName);        
            }
            merchant.setParentName(parentName);
        }
        if(merchantIdList!= null && merchantIdList.size() > 0){
            Map<Long, String> accountMap = toeUserService.getNameByMerchantIds(merchantIdList);
            for (Merchant merchant : merchantList) {
                String account = accountMap.get(merchant.getId());
                if (StringUtils.isNotBlank(account)) {
                    merchant.setAccount(account);
                }
            }
        }
        if (!projectIdSet.isEmpty()) {
            // 配置项目名称
            Map<Long, String> projectMap = projectService.getIdAndNameByIds(projectIdSet);
            for (Merchant merchant : merchantList) {
                Long projectId = merchant.getProjectId();
                if (projectId == null) {
                    continue;
                }
                String projectName = projectMap.get(projectId);
                if (StringUtils.isNotBlank(projectName)) {
                    merchant.setProjectName(projectName);
                }
            }
        }
    }
    
    /**
     * 格式化 行业
     * @param merchant 商户
     * @param industryCacheMap 缓存map
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年2月22日 下午7:29:23
     */
    private void formatIndustry(Merchant merchant, Map<String,String> industryCacheMap) throws Exception{
        String priIndustryCode = merchant.getPriIndustryCode();//一级行业标签
        String priIndustryName = null;//一级行业名称
        if(StringUtils.isNotBlank(priIndustryCode)){
            priIndustryName = industryCacheMap.get(priIndustryCode);//行业名称
            if(StringUtils.isBlank(priIndustryName)){
                priIndustryName = IndustryClient.getNameByCode(priIndustryCode);//从缓存中拿
                industryCacheMap.put(priIndustryCode, priIndustryName);//存到map 减少查询次数
            }
            merchant.setPriIndustry(priIndustryName);
        }
        String secIndustryCode = merchant.getSecIndustryCode();//二级行业标签
        String secIndustryName = null;//二级行业名称
        if(StringUtils.isNotBlank(secIndustryCode)){
            secIndustryName = industryCacheMap.get(secIndustryCode);//行业名称
            if(StringUtils.isBlank(secIndustryName)){
                secIndustryName = IndustryClient.getNameByCode(secIndustryCode);//从缓存中拿
                industryCacheMap.put(secIndustryCode, secIndustryName);//存到map 减少查询次数
            }
            merchant.setSecIndustry(secIndustryName);
        }
    }
    
    /**
     * 添加商户
     * @param curUserId 当前登陆账号id
     * @param merchant 商户
     * @param industryCode 行业编号
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年2月4日 上午10:58:18
     */
    public void add(Long curUserId, Merchant merchant, String industryCode) throws Exception{
        if(FormatUtil.isMerchantType1(merchant.getProjectId())){//园区 酒店 微站 保存用户信息
            Long userId = UserAuthClient.addUserAuth(merchant.getAccount());
            merchant.setUserId(userId);
            merchant.setMerchantType(1);//商户类型设为1 商客
        }
        //调数据中心接口添加商户
        Long merchantId = MerchantClient.add(merchant,industryCode);
        ToeUser user = new ToeUser();
        user.setUserName(merchant.getAccount());
        user.setProvinceId(merchant.getProvinceId());
        user.setCityId(merchant.getCityId());
        user.setAreaId(merchant.getAreaId());
        user.setContactPerson(merchant.getContact());
        user.setContactWay(merchant.getContactWay());
        user.setRemark(merchant.getRemark());
        user.setProjectId(merchant.getProjectId());
        user.setCreateUserId(curUserId);
        Long userId = toeUserService.add(user);
        toeRoleService.addUserRole(userId,merchant.getRoleIds());
        toeUserService.addUserMerchant(userId,merchantId);
    }
    
    /**
     * 判断商户名称是否存在
     * @param merchantName 商户名称
     * @throws Exception 异常
     * @return true/false
     * @author 周颖  
     * @date 2017年2月4日 上午11:13:35
     */
    public boolean isMerchantNameExist(String merchantName) throws Exception{
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("merchantName", merchantName);
        //paramsMap.put("merchantType", 2);
        int count = MerchantClient.getCountByParam(paramsMap);//查看商户名称是否存在
        return count > 0 ? true : false;
    }
    
    /**
     * 获取商户详情
     * @param id 商户id
     * @return 商户详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月6日 上午8:50:18
     */
    public Merchant getById(Long id) throws Exception{
        Merchant merchant = MerchantClient.getById(id);//调数据中心商户详情接口
        ToeUser user = toeUserService.getByMerchantId(id);//通过商户id获取账号详情
        if(user != null){//如果账号不为空  补全账号信息
            merchant.setAccount(user.getUserName());//用户名
            List<ToeRole> roleList = toeRoleService.getNamesByUserId(user.getId());
            int maxSize = roleList.size();
            StringBuffer roleNames = new StringBuffer();
            StringBuffer roleIds = new StringBuffer();
            ToeRole role = new ToeRole();
            for(int i=0;i<maxSize;i++){
                role = roleList.get(i);
                roleIds.append(role.getId());
                roleNames.append(role.getRoleName());
                if(i < (maxSize-1)){
                    roleIds.append(",");
                    roleNames.append(",");
                }
            }
            merchant.setRoleIds(roleIds.toString());
            merchant.setRoleNames(roleNames.toString());
        }
        //补全项目
        String projectName = projectService.getNameById(merchant.getProjectId());//获取项目名称
        merchant.setProjectName(projectName);
        //补全行业信息
        String industryName = StringUtils.EMPTY;
        String industryId = merchant.getPriIndustryCode();//一级行业标签
        if (StringUtils.isNotBlank(industryId)) {
            industryName = IndustryClient.getNameByCode(industryId);// 从缓存中拿
            merchant.setPriIndustry(industryName);
        }
        industryId = merchant.getSecIndustryCode();// 二级行业标签
        if (StringUtils.isNotBlank(industryId)) {
            industryName = IndustryClient.getNameByCode(industryId);// 从缓存中拿
            merchant.setSecIndustry(industryName);
        }
        //补全地区
        String nameParam = "name";//地区名称
        Long provinceId = merchant.getProvinceId();//省id
        String province = provinceId != null ? LocationClient.getByIdAndParam(provinceId, nameParam) : null;//省
        Long cityId = merchant.getCityId();//市id
        String city = cityId != null ? LocationClient.getByIdAndParam(cityId, nameParam) : null;//市
        Long areaId = merchant.getAreaId();//区id
        String area = areaId != null ? LocationClient.getByIdAndParam(areaId, nameParam) : null;//区
        merchant.setProvince(province);//省
        merchant.setCity(city);//市
        merchant.setArea(area);//区
        merchant.setLocationFullName(FormatUtil.locationFullName(province, city, area));//地区全路径
        return merchant;
    }
    
    /**
     * 编辑商户
     * @param merchant 商户
     * @param industryCode 行业编号
     * @author 周颖  
     * @throws Exception  
     * @date 2017年2月6日 下午4:43:33
     */
    public void update(Merchant merchant, String industryCode) throws Exception{
        //调数据中心更新商户接口
        MerchantClient.update(merchant,industryCode);
        ToeUser user = new ToeUser();
        user.setContactPerson(merchant.getContact());
        user.setContactWay(merchant.getContactWay());
        user.setProjectId(merchant.getProjectId());
        user.setProvinceId(merchant.getProvinceId());
        user.setCityId(merchant.getCityId());
        user.setAreaId(merchant.getAreaId());
        //更新账号
        Long userId = toeUserService.update(merchant.getId(),user);
        if(userId != null){
            //更新角色关系
            toeRoleService.updateUserRole(userId,merchant.getRoleIds());
        }
    }
    
    /**
     * 更新下级商户项目归属
     * @param paramsMap paramsMap
     * @param projectId 项目id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月6日 上午10:36:58
     */
    public void updateSub( Map<String,Object> paramsMap, Long projectId) throws Exception{
        List<Merchant> merchantList = MerchantClient.getListByParam(paramsMap);
        for(Merchant merchant :merchantList ){
            merchant.setProjectId(projectId);
            //调数据中心更新商户接口
            MerchantClient.update(merchant,null);
            toeUserService.updateProject(merchant.getId(), projectId);
        }
    } 
    
    /**
     * 商户导出列表 一个sheet
     * @param paramsMap 参数
     * @param rowLength 列数
     * @return 列表
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月10日 下午2:23:41
     */
    public List<Object[]> getExportList(Map<String, Object> paramsMap, Integer rowLength) throws Exception{
        Object[] objs = null;
        List<Object[]> listObj = new ArrayList<Object[]>();
        //符合条件的记录
        List<Merchant> merchantList = MerchantClient.getListByParam(paramsMap);
        formateMerchantList(merchantList);
        String secIndustry = null;
        for(Merchant merchant : merchantList){
            objs = new Object[rowLength];
            objs[0] = merchant.getId();
            objs[1] = merchant.getMerchantName();
            objs[2] = merchant.getAccount();
            objs[3] = merchant.getProjectName();
            secIndustry = merchant.getSecIndustry();
            objs[4] = StringUtils.isNotBlank(secIndustry) ? secIndustry : merchant.getPriIndustry();
            objs[5] = merchant.getLocationFullName();
            objs[6] = merchant.getParentName();
            listObj.add(objs);
        }
        return listObj;
    }
    
    /**
     * 商户重置密码
     * @param id 商户主键id
     * @author 周颖  
     * @date 2017年4月6日 下午4:31:50
     */
    public void resetPassword(Long id){
        toeUserService.resetPassword(id);//重置密码
    }
}