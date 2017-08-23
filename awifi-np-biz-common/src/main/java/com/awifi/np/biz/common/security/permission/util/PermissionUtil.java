/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月21日 上午10:26:48
 * 创建作者：周颖 
 * 文件名称：PermissionUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.common.security.permission.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.MessageUtil;

public class PermissionUtil {

    /**
     * 获取权限信息
     * @param sessionUser sessionUser
     * @param merchantId 商户id
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param projectIds 管理项目ids
     * @param filterProjectIds 过滤项目ids
     * @return map
     * @author 周颖  
     * @date 2017年4月21日 上午10:26:48
     */
    public static Map<String, Object> dataPermission(SessionUser sessionUser, Long merchantId, Long provinceId, Long cityId, 
            Long areaId,String projectIds,String filterProjectIds){
        String type = null;
        String merchantIds = null;
        if(null != merchantId){
            type = "this";
            //merchantIds = merchantId.toString();
        }
        /* 1.当组织为"爱WiFi运营中心"，即sessionUser的id==1时，显示全部 */
        if(isSuperAdmin(sessionUser)){//超级管理员不需要做任何限制
            return formatMap(merchantId,merchantIds, type, provinceId, cityId, areaId, filterProjectIds, projectIds);
        }
        
        /* 2.当用户为商户时，按商户层级[type]做数据权限做数据权限  */
        if(isMerchant(sessionUser)){
            if(merchantId == null){
                merchantId = sessionUser.getMerchantId();//从session中获取商户id
                if(merchantId == null){
                    throw new BizException("E2000022", MessageUtil.getMessage("E2000022"));//当前组织id[{0}]对应的账号必须维护商户id[merchantId]!
                }
                //merchantIds = merchantId.toString();
                type = "nextAllWithThis";
            }
            return formatMap(merchantId,merchantIds, type, provinceId, cityId, areaId, filterProjectIds, projectIds);
        }
        /* 其余 有什么属性 按什么做*/
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
        String sessionFilterProjectIds = sessionUser.getFilterProjectIds();
        if(StringUtils.isBlank(filterProjectIds)){//没有选择过滤项目
            filterProjectIds = sessionFilterProjectIds;
        }else if(StringUtils.isNotBlank(sessionFilterProjectIds)){//两个参数都不为空 拼接
            filterProjectIds = filterProjectIds + "," + sessionFilterProjectIds;
        }
        if(StringUtils.isBlank(projectIds)){//没有选择项目
            projectIds = sessionUser.getProjectIds();
        }
        merchantIds = sessionUser.getMerchantIds();
        return formatMap(merchantId, merchantIds, type, provinceId, cityId, areaId, filterProjectIds, projectIds);
    }
    
    /**
     * 权限信息统一返回
     * @param merchantId 商户id
     * @param merchantIds 商户ids
     * @param type 层级关系
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param filterProjectIds 过滤项目ids
     * @param projectIds 项目ids
     * @return map
     * @author 周颖   
     * @date 2017年4月21日 上午10:26:48
     */
    private static Map<String, Object> formatMap(Long merchantId,String merchantIds, String type, Long provinceId, Long cityId, Long areaId,
            String filterProjectIds,String projectIds) {
        Map<String, Object> permissionMap = new HashMap<String, Object>();
        permissionMap.put("merchantId", merchantId);
        permissionMap.put("merchantIds", merchantIds);
        permissionMap.put("type", type);
        permissionMap.put("provinceId", provinceId);
        permissionMap.put("cityId", cityId);
        permissionMap.put("areaId", areaId);
        permissionMap.put("filterProjectIds", filterProjectIds);
        permissionMap.put("projectIds", projectIds);
        return permissionMap;
    }
    
    /**
     * 是否是超管
     * @param sessionUser 当前登陆用户
     * @return true 是超管
     * @author 周颖  
     * @date 2017年4月21日 上午10:26:48
     */
    public static boolean isSuperAdmin(SessionUser sessionUser) {
        Long id = sessionUser.getId();//获取用户ID
        return id==1L;//用户id等于1
    }
    
    /**
     * 是否商户账号
     * @param sessionUser 当前登陆用户
     * @return true 是商户账号
     * @author 周颖  
     * @date 2017年4月21日 上午10:33:38
     */
    public static boolean isMerchant(SessionUser sessionUser){
        Long merchantId = sessionUser.getMerchantId();
        return merchantId != null;
    }
    
    /**
     * 是否 管理商户属性
     * @param sessionUser 当前登陆用户
     * @return true 商户管理员 
     * @author 周颖  
     * @date 2017年4月21日 上午10:38:28
     */
    public static boolean isMerchants(SessionUser sessionUser){
        return StringUtils.isNotBlank(sessionUser.getMerchantIds());
    }
    
    /**
     * 是否 管理项目属性
     * @param sessionUser 当前登陆用户
     * @return true 项目管理员
     * @author 周颖  
     * @date 2017年4月21日 上午10:53:16
     */
    public static boolean isProject(SessionUser sessionUser){
        boolean filterProjectIds = StringUtils.isNotBlank(sessionUser.getFilterProjectIds());//管理过滤项目不为空
        boolean projectIds = StringUtils.isNotBlank(sessionUser.getProjectIds());//管理项目不为空
        boolean isMerchants = isMerchants(sessionUser);//商户管理员
        return (!isMerchants) && (filterProjectIds || projectIds);
    }
    
    /**
     * 是否 只有管理地区属性
     * @param sessionUser 当前登陆用户
     * @return true 地区管理员
     * @author 周颖  
     * @date 2017年4月21日 上午11:18:15
     */
    public static boolean isLocation(SessionUser sessionUser){
        boolean isProject = isProject(sessionUser);
        boolean isMerchants = isMerchants(sessionUser);//商户管理员
        boolean isMerchant = isMerchant(sessionUser);//商户
        boolean isSuperAdmin = isSuperAdmin(sessionUser);
        boolean provinceId = sessionUser.getProvinceId() != null;
        boolean cityId = sessionUser.getCityId() != null;
        boolean areaId = sessionUser.getAreaId() != null;
        boolean isLocation = provinceId || cityId || areaId;
        return !(isProject || isMerchants || isMerchant || isSuperAdmin) && isLocation;
    }
}
