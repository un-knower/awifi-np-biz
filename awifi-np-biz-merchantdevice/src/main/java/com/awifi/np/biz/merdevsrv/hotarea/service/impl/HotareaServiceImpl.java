package com.awifi.np.biz.merdevsrv.hotarea.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.api.client.dbcenter.device.hotarea.util.HotareaClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.merdevsrv.hotarea.service.HotareaService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:08:29
 * 创建作者：亢燕翔
 * 文件名称：HotareaServiceImpl.java
 * 版本：  v1.0
 * 功能：  
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@Service("hotareaService")
public class HotareaServiceImpl implements HotareaService{

    /**
     * 热点分页列表
     * @param sessionUser session
     * @param params 请求参数
     * @param page 分页实体
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午10:12:30
     */
    public void getListByParam(SessionUser sessionUser, Page<Hotarea> page, String params) throws Exception {
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        int maxSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//获取系统配置最大每页条数限制
        Integer pageNoInteger = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        Integer pageNo = pageNoInteger != null ? pageNoInteger : 1;
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页数量
        ValidUtil.valid("页码[pageNo]", pageNo, "numeric");
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxSize+"}}");
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser,null, null, null, null,null,null);//获取数据权限信息
       
        Long projectId= CastUtil.toLong(permissionMap.get("projectId"));
        String projectIds=(String) permissionMap.get("projectIds");
        String excludeProjectIds=(String) permissionMap.get("excludeProjectIds");
        String merchantIds=(String) permissionMap.get("merchantIds");
        Map<String, Object> dbParams = getDbParams(sessionUser,params,projectId,projectIds,excludeProjectIds,merchantIds);//获取请求参数
        int totalRecord = HotareaClient.getCountByParam(JsonUtil.toJson(dbParams));
        page.setTotalRecord(totalRecord);
        if(totalRecord > 0){
            dbParams.put("pageNum", pageNo);
            dbParams.put("pageSize", pageSize);
            List<Hotarea> hotareaList = HotareaClient.getListByParam(JsonUtil.toJson(dbParams));
            page.setRecords(hotareaList);
        }
    }
    
    /**
     * 获取数据中心请求参数
     * @param sessionUser sessionUser
     * @param params 请求参数
     * @return map
     * @author 亢燕翔  
     * @param merchantIds 
     * @param excludeProjectIds 
     * @param projectIds 
     * @param projectId 
     * @date 2017年2月10日 下午2:34:27
     */
    private Map<String, Object> getDbParams(SessionUser sessionUser, String params, Long projectId, String projectIds, String excludeProjectIds, String merchantIds) {
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户ID
        String hotareaName = (String) paramsMap.get("hotareaName");//热点名称
        String devMac = (String) paramsMap.get("devMac");//设备mac
        Integer status = CastUtil.toInteger(paramsMap.get("status"));//状态
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省ID
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));//市ID
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));//区县ID
        
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser, merchantId, provinceId, cityId, areaId,null,null);//获取数据权限信息
        Long merchantIdLong = CastUtil.toLong(permissionMap.get("merchantId"));//商户id
        String type = (String) permissionMap.get("type");//层级关系
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));//省id
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));//市id
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));//区县id
        
        Map<String, Object> dbParams = new HashMap<String, Object>();
        dbParams.put("merchantId", merchantIdLong);//商户id
        dbParams.put("merchantQueryType", type);//this只查当前节点（默认）;nextLevel只查当前节点的下一层;nextAll查当前节点所有不包括当前;nextAllWithThis查当前节点所有包含当前
        dbParams.put("province", provinceIdLong);//省id
        dbParams.put("city", cityIdLong);//市id
        dbParams.put("county", areaIdLong);//区县id
        dbParams.put("hotareaName", hotareaName);//热点名称
        dbParams.put("macAddr", devMac);//mac地址
        dbParams.put("status", status);//状态
        
//        dbParams.put("projectId",12321);//项目id//（Long）
        if(projectId!=null){
            dbParams.put("projectId",projectId);//项目id//（Long）
        }
//        dbParams.put("projectIds", "1,2,3");
        if(StringUtils.isNotBlank(projectIds)){
            dbParams.put("projectIds",projectIds);
        }
//        dbParams.put("excludeProjectIds", "3,4,5");
        if(StringUtils.isNotBlank(excludeProjectIds)){
            dbParams.put("excludeProjectIds",excludeProjectIds);
        }
//        dbParams.put("merchantIds", "1,2,3");
        if(StringUtils.isNotBlank(merchantIds)){
            dbParams.put("merchantIds",merchantIds);
        }
        return dbParams;
    }
    
}
