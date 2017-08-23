package com.awifi.np.biz.common.security.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.npadmin.util.NPAdminClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.dao.PermissionDao;
import com.awifi.np.biz.common.security.permission.service.PermissionService;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午8:48:55
 * 创建作者：许小满
 * 文件名称：PermissionServiceImpl.java
 * 版本：  v1.0
 * 功能：权限--业务层接口实现类
 * 修改记录：
 */
@Service("permissionService")
public class PermissionServiceImpl extends BaseService implements PermissionService {

    /** 权限--模型层接口 */
    @Resource(name="permissionDao")
    private PermissionDao permissionDao;
    
    /**
     * 权限校验
     * @param roleIds 角色ids
     * @param code 权限(接口)编号
     * @param serviceCode 服务代码
     * @return true 有效、false 无效
     * @author 许小满  
     * @date 2017年2月9日 上午9:38:41
     */
    public boolean check(Long[] roleIds, String code, String serviceCode){
        int count = permissionDao.getNumByRoleAndCode(roleIds, code, serviceCode);
        return count >0 ? true : false;
    }
    
    /**
     * 通过服务代码[外键]、角色id获取权限编号集合
     * @param serviceCode 服务代码[外键]
     * @param roleId 角色id
     * @return 权限编号集合
     * @author 许小满  
     * @date 2017年2月13日 下午4:54:45
     */
    public List<String> getCodesByRoleId(String serviceCode, Long roleId){
        return permissionDao.getCodesByRoleId(serviceCode, roleId);
    }
    
    /**
     * 角色-权限关系表  批量更新
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @param codes 接口编号数组
     * @author 许小满  
     * @date 2017年2月15日 下午4:20:00
     */
    public void batchAddRolePermission(String serviceCode, Long roleId, String[] codes){
        //1.通过角色id删除角色权限关系表的数据
        permissionDao.deleteRolePermissionByRoleId(serviceCode, roleId);
        //2.删除redis缓存
        String key = RedisConstants.PERMISSION + serviceCode + "_*{" + roleId + "}*_*";
        Set<String> keys = RedisUtil.keys(key);//找到所有key
        RedisUtil.delBatch(keys);//批量删除
        //3.批量更新角色-权限关系表
        int maxLength = codes != null ? codes.length : 0;//数字长度
        Long permissionId = null;//权限表主键id
        String code = null;//编号
        Long[] permissionIdArray = new Long[maxLength];//权限表主键id数组  创建
        for(int i=0; i<maxLength; i++){
            code = codes[i];
            permissionId = permissionDao.getIdByCode(serviceCode, code);//通过编号获取主键id
            if(permissionId == null){
                throw new BizException("E2000044", MessageUtil.getMessage("E2000044", code));//接口[{0}]不存在!
            }
            permissionIdArray[i] = permissionId;
        }
        permissionDao.batchAddRolePermission(roleId, permissionIdArray);
    }

    /**
     * 推送接口注册信息
     * @param serviceCode 服务编号
     * @param serviceKey 服务秘钥
     * @author 亢燕翔                                          
     * @throws Exception 
     * @date 2017年2月20日 上午10:17:47
     */
    public void pushInterfaces(String serviceCode, String serviceKey) throws Exception {
        List<Map<String, Object>> listPermission = getListByServiceCode(serviceCode);
        //如果请求参数中，接口数据集为空，则放弃请求admin接口
        if(listPermission.isEmpty()){
            return;
        }
        //请求admin，参数封装
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("serviceCode", serviceCode);//服务编号
        Long timestamp = System.currentTimeMillis();
        paramMap.put("timestamp", timestamp);//时间戳
        paramMap.put("token", EncryUtil.generateToken(serviceCode,serviceKey,Long.toString(timestamp)));//token
        paramMap.put("interfaceMap", formatPermission(listPermission));
        //请求admin
        NPAdminClient.pushInterfaces(JsonUtil.toJson(paramMap));
    }

    /**
     * 通过服务编号获取接口数据集合
     * @param serviceCode 服务编号
     * @return 接口数据集
     * @author 亢燕翔  
     * @date 2017年3月14日 上午10:00:31
     */
    private List<Map<String, Object>> getListByServiceCode(String serviceCode) {
        return permissionDao.getListByServiceCode(serviceCode);
    }

    /**
     * 格式化Permission数据集
     * @param listPermission 源数据
     * @return list
     * @author 亢燕翔  
     * @date 2017年2月20日 上午10:58:57
     */
    private Map<String, Object> formatPermission(List<Map<String, Object>> listPermission) {
        Map<String, Object> interfaceMap = new HashMap<String, Object>();//接口map
        listPermission.forEach(permissionMap ->{
            Map<String, Object> dataMap = new HashMap<String, Object>();
            String code = permissionMap.get("code").toString();
            int maxLength = code.length();
            int lastIndex = code.lastIndexOf(":");
            dataMap.put("name", permissionMap.get("name"));//接口名称
            dataMap.put("path", code.substring(0, lastIndex));//path
            dataMap.put("type", code.substring((lastIndex+1), maxLength));//请求方式
            interfaceMap.put(permissionMap.get("code").toString(), dataMap);
        });
        return interfaceMap;
    }
    
    /**
     * 获取当前登陆账号所有权限
     * @param roleIds 角色ids
     * @return 权限
     * @author 周颖  
     * @date 2017年5月9日 上午10:03:39
     */
    public List<Map<String,Object>> getPermissionsByRoleIds(Long[] roleIds){
        return permissionDao.getPermissionsByRoleIds(roleIds);
    }
    
    /**
     * 权限校验
     * @param appId 第三方应用表主键id
     * @param code 接口编号
     * @return true 存在
     * @author 周颖  
     * @date 2017年7月19日 下午4:16:24
     */
    public boolean check(Long appId, String code){
        int count = permissionDao.getNumByAppIdAndCode(appId,code);
        return count>0;
    }
}
