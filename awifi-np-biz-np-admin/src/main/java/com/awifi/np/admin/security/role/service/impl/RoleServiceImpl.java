package com.awifi.np.admin.security.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.security.role.dao.RoleDao;
import com.awifi.np.admin.security.role.service.RoleService;
import com.awifi.np.biz.api.client.npadmin.util.NPAdminClient;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:58:58
 * 创建作者：周颖
 * 文件名称：RoleServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    /**角色dao*/
    @Resource(name = "roleDao")
    private RoleDao roleDao;
    
    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 角色
     * @author 周颖  
     * @date 2017年1月11日 下午7:26:38
     */
    public List<Long> getIdsById(Long userId){
        return roleDao.getIdsById(userId);
    }
    
    /**
     * 获取角色名称
     * @param roleIds 角色ids
     * @return 角色名称
     * @author 周颖  
     * @date 2017年2月23日 下午2:48:09
     */
    public String getNamesByIds(String roleIds){
        if(StringUtils.isBlank(roleIds)){//如果为空 直接返回
            return StringUtils.EMPTY;
        }
        String[] roleIdsString = roleIds.split(",");//转成String数组
        Long[] roleIdsLong = CastUtil.toLongArray(roleIdsString);//转成Long数组
        List<String> roleNames = roleDao.getNamesByIds(roleIdsLong);//获取角色名称列表
        int maxSize = roleNames.size();
        if(maxSize <= 0){//如果为空 直接返回
            return StringUtils.EMPTY;
        }
        StringBuffer roleNamesString = new StringBuffer();
        for(int i=0 ; i<maxSize ; i++){
            roleNamesString.append(roleNames.get(i));
            if(i < (maxSize-1)){
                roleNamesString.append(",");
            }
        }
        return roleNamesString.toString();
    }
    
    
    /**
     * 根据roleId获取权限
     * @param roleId 角色id
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月10日 上午9:17:54
     */
    @SuppressWarnings("rawtypes")
    public List getPermisionByRoleId(Long roleId) throws Exception {
        String serviceCode = SysConfigUtil.getParamValue("servicecode_user");//从配置表读取服务代码
        String serviceKey = SysConfigUtil.getParamValue("servicekey_user");//从配置表读取服务密钥    
            //如果请求参数为空直接返回
        if(roleId==null){
            return null;
        }
        //请求admin，参数封装
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("serviceCode", serviceCode);//服务编号
        Long timestamp = System.currentTimeMillis();
        paramMap.put("timestamp", timestamp.toString());//时间戳
        paramMap.put("token", EncryUtil.generateToken(serviceCode,serviceKey,Long.toString(timestamp)));//token
        paramMap.put("roleId", roleId.toString());//角色id
        String params = HttpRequest.getParams(paramMap);
            //请求admin
        Map<String,Object> map=NPAdminClient.getInterfacesByParam(params);
        List list=(List)map.get("data");//获取data
        return list;
    }

    
    /**
     * 维护账号角色关系
     * @param userId 账号id
     * @param roleIds 角色ids
     * @author 周颖  
     * @date 2017年5月8日 下午2:17:21
     */
    public void addUserRole(Long userId, String roleIds){
        String[] roleIdsArray = roleIds.split(",");
        int maxLength = roleIdsArray.length;
        for(int i =0 ; i<maxLength ; i++){
            roleDao.addUserRole(userId, Long.parseLong(roleIdsArray[i]));
        }
    }
    
    /**
     * 删除账号角色
     * @param userId 账号id
     * @author 周颖  
     * @date 2017年5月8日 下午2:43:31
     */
    public void deleteUserRole(Long userId){
        roleDao.deleteUserRole(userId);
    }
    
    /**
     * 全部角色
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午6:19:47
     */
    public List<Map<String, Object>> getAllRole(){
        return roleDao.getAllRole();
    }
    
    /**
     * 获取角色列表
     * @param roleIds 角色ids
     * @return 结果
     * @author 周颖  
     * @date 2017年5月8日 下午6:21:52
     */
    public List<Map<String, Object>> getIdsAndNamesByRoleIds(String roleIds){
        String[] roleIdsArray = roleIds.split(",");
        Long[] roleIdsLong = CastUtil.toLongArray(roleIdsArray);//转成Long数组
        return roleDao.getIdsAndNamesByRoleIds(roleIdsLong);
    }

    /**
     * 获取用户角色名称
     * @param userId 用户id
     * @return 角色名称
     * @author 周颖  
     * @date 2017年5月5日 下午4:03:56
     */
    public Map<String,String> getIdsAndNamesByUserId(Long userId){
        List<Map<String,Object>> roleList = roleDao.getNamesByUserId(userId);
        int maxSize = roleList.size();
        if(maxSize <= 0){//如果为空 直接返回
            return null;
        }
        StringBuffer roleNamesString = new StringBuffer();
        StringBuffer roleIdsString = new StringBuffer();
        Map<String,Object> roleMap = null;
        for(int i=0 ; i<maxSize ; i++){
            roleMap = roleList.get(i);
            roleIdsString.append(roleMap.get("id"));
            roleNamesString.append(roleMap.get("roleName"));
            if(i < (maxSize-1)){
                roleIdsString.append(",");
                roleNamesString.append(",");
            }
        }
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("roleIds", roleIdsString.toString());
        resultMap.put("roleNames", roleNamesString.toString());
        return resultMap;
    }
}