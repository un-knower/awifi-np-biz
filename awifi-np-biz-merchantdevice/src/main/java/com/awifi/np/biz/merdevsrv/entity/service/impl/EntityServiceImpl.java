package com.awifi.np.biz.merdevsrv.entity.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.api.client.dbcenter.device.entity.util.EntityClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RequestParamUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.merdevsrv.entity.service.EntityService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:08:15
 * 创建作者：亢燕翔
 * 文件名称：EntityServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@Service(value = "entityService")
public class EntityServiceImpl implements EntityService{

    /**
     * 设备监控列表
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午9:16:54
     */
    @Override
    public void getEntityInfoListByMerId(SessionUser sessionUser, String params, Page<EntityInfo> page) throws Exception {
        
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));
        String ssid= (String) paramsMap.get("ssid");
        String devMac = (String) paramsMap.get("devMac");
        
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省ID
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));//市ID
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));//区县ID
        
        /*数据校验*/
        ValidUtil.valid("商户id[merchantId]", merchantId, "numeric");//数字
        Integer pageNo = RequestParamUtil.getPageNo(paramsMap);//页码
        Integer pageSize = RequestParamUtil.getPageSize(paramsMap);//每页记录数
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser, merchantId, provinceId, cityId, areaId,null,null);//获取数据权限信息
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));
        Long projectId= CastUtil.toLong(permissionMap.get("projectId"));
        String projectIds=(String) permissionMap.get("projectIds");
        String excludeProjectIds=(String) permissionMap.get("filterProjectIds");
        String merchantIds=(String) permissionMap.get("merchantIds");
        String merchantQueryType= (String) permissionMap.get("type");
        Map<String, Object> dbParams = getDbParams(merchantId,devMac,ssid,merchantQueryType,provinceIdLong,cityIdLong,areaIdLong,projectId,projectIds,excludeProjectIds,merchantIds);//获取请求参数
        int totalRecord = EntityClient.getEntityInfoCountByMerId(JsonUtil.toJson(dbParams));
        page.setTotalRecord(totalRecord);
        if(totalRecord > 0){
            dbParams.put("pageNum", pageNo);
            dbParams.put("pageSize", pageSize);
            List<EntityInfo> entityInfoList = EntityClient.getEntityInfoListByMerId(JsonUtil.toJson(dbParams));
            Map<Long,String> merchantNameCacheMap = new HashMap<Long,String>();//商户名称缓存map，缓存上次查询的内容，优化性能
            configMerchatName(entityInfoList, merchantNameCacheMap);//配置商户名称
            page.setRecords(entityInfoList);
        }
    }

    /**
     * 配置设备对应的商户名称
     * @param entityInfoList 设备监控列表
     * @param merchantNameCacheMap 商户名称缓存map，缓存上次查询的内容，优化性能
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年6月16日 下午2:53:55
     */
    private void configMerchatName(List<EntityInfo> entityInfoList, Map<Long,String> merchantNameCacheMap) throws Exception {
        for(EntityInfo entityInfo : entityInfoList){
            Long merchantId = entityInfo.getMerchantId();
            if(merchantId != null && merchantId != 0L){
                String merchantName= merchantNameCacheMap.get(merchantId);//从缓存map中获取
                if(StringUtils.isBlank(merchantName)){
                    merchantName = MerchantClient.getNameByIdCache(merchantId);//调用数据中心接口获取商户名称
                    merchantNameCacheMap.put(merchantId, merchantName);//存入缓存中
                    
                }
                entityInfo.setMerchantName(merchantName);
            }
        }
    }

    /**
     * 封装请求参数（列表）
     * @param merchantId 商户id
     * @param devMac 设备mac
     * @param ssid 热点
     * @param merchantQueryType 层级关系
     * @return map
     * @author 亢燕翔  
     * @param merchantIds 
     * @param excludeProjectIds 
     * @param projectIds 
     * @param projectId 
     * @param areaIdLong 
     * @param cityIdLong 
     * @param provinceIdLong 
     * @date 2017年2月8日 上午9:51:21
     */
    private Map<String, Object> getDbParams(Long merchantId, String devMac, String ssid, String merchantQueryType, Long provinceIdLong, Long cityIdLong, Long areaIdLong, Long projectId, String projectIds, String excludeProjectIds, String merchantIds) {
        Map<String, Object> dbParams = new HashMap<String, Object>();
        if(merchantId!=null){
            dbParams.put("merchantId", merchantId);
        }
        if(!StringUtils.isBlank(devMac)){
            dbParams.put("macAddr", devMac);
        }
        if(!StringUtils.isBlank(ssid)){
            dbParams.put("ssid", ssid);
        }
        if(!StringUtils.isBlank(merchantQueryType)){
            dbParams.put("merchantQueryType", merchantQueryType);
        }
        if(provinceIdLong!=null){
            dbParams.put("province", provinceIdLong);//省id
        }
        if(cityIdLong!=null){
            dbParams.put("city", cityIdLong);//市id
        }
        if(areaIdLong!=null){
            dbParams.put("county", areaIdLong);//区县id
//        dbParams.put("projectId",12321);//项目id//（Long）
        }
        if(projectId!=null){
            dbParams.put("projectId",projectId);//项目id//（Long）
        }
//        dbParams.put("projectIds", "1,2,3");
        if(projectIds!=null){
            dbParams.put("projectIds",projectIds);
        }
//        dbParams.put("excludeProjectIds", "3,4,5");
        if(excludeProjectIds!=null){
            dbParams.put("excludeProjectIds",excludeProjectIds);
        }
//        dbParams.put("merchantIds", "1,2,3");
        if(merchantIds!=null){
            dbParams.put("merchantIds",merchantIds);
        }
        return dbParams;
    }

    /**
     * @param sheetNum sheet数目
     * @param pageSize 分页大小
     * @return string
     * @author 王冬冬  
     * @date 2017年5月3日 上午10:13:24
     */
    private String getSheetName(Integer sheetNum, Integer pageSize) {
        return (((sheetNum - 1) * pageSize)+1)+"--"+(sheetNum * pageSize);
    }

    
    /**
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @param sessionUser 用户
     * @param response 响应
     * @param merchantId 商户id
     * @param ssid ssid
     * @param mac mac地址
     * @param path 导出路径
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月3日 上午10:18:12
     */
    public void export(Long provinceId, Long cityId, Long areaId, SessionUser sessionUser, HttpServletResponse response,
            Long merchantId, String ssid, String mac, String path) throws Exception {
        /*获取数据权限信息*/
        Map<String, Object> permissionMap =PermissionUtil.dataPermission(sessionUser, merchantId, provinceId, cityId, areaId,null,null);//获取数据权限信息
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));//省id
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));//市id
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));//区县id
        Long projectId= CastUtil.toLong(permissionMap.get("projectId"));
        String projectIds=(String) permissionMap.get("projectIds");
        String excludeProjectIds=(String) permissionMap.get("excludeProjectIds");
        String merchantIds=(String) permissionMap.get("merchantIds");
      //请求数据中心Type设置
        String merchantQueryType = null;
        if(merchantId == null){//商户id为空
            merchantId = sessionUser.getMerchantId();//从session中获取商户id
//            if(merchantId == null){//如果仍然为空，则抛出异常
//                throw new BizException("E2000037", MessageUtil.getMessage("E2000037"));//用户信息中的商户id[merchantId]不允许为空!
//            }
            if(merchantId!=null){
                merchantQueryType = "nextAllWithThis";
            }
        } else {//商户id不为空
            merchantQueryType = "this";
        }
        int pageSize = Integer.valueOf(SysConfigUtil.getParamValue("xls_export_sheet_max_size")).intValue();//每页数量--工作表每页数量
        int maxSize = 0;//数据的最大长度
        Integer sheetNum = 1;//sheet编号
        String sheetName = null;//sheet名称
        List<Object[]> listObj = new ArrayList<Object[]>();
        String fileName = "devicemonitorexport.xls";//文件名称
        String[] rowName = {"序号","mac地址","SSID","商户名称","所属AC","设备类型","状态","在线时间","在线用户数"};//设置列表名称
        Map<Long,String> merchantNameCacheMap = new HashMap<Long,String>();//商户名称缓存map，缓存上次查询的内容，优化性能
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        do{
            if(merchantNameCacheMap.size() > 10000){
                merchantNameCacheMap.clear();//当大于10000时，清空缓存，防止内存溢出
            }
            listObj = getListObj(merchantId,mac,ssid,merchantQueryType,provinceIdLong,cityIdLong,areaIdLong,projectId,projectIds,excludeProjectIds,
                            merchantIds,sheetNum,pageSize,rowName.length,merchantNameCacheMap);//获取数据
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
     * @param merchantId 商户id
     * @param mac mac地址
     * @param ssid ssid
     * @param merchantQueryType 商户查询类型
     * @param provinceIdLong 省id
     * @param cityIdLong 市id
     * @param areaIdLong 区id
     * @param projectId 项目id
     * @param projectIds 项目ids
     * @param excludeProjectIds 排除项目的ids
     * @param merchantIds 商户ids
     * @param pageNo sheet页码
     * @param pageSize 分页大小
     * @param length 列数
     * @param merchantNameCacheMap 商户名称缓存map，缓存上次查询的内容，优化性能
     * @return list
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月3日 上午10:39:00
     */
    private List<Object[]> getListObj(Long merchantId, String mac, String ssid, String merchantQueryType,
            Long provinceIdLong, Long cityIdLong, Long areaIdLong, Long projectId, String projectIds,
            String excludeProjectIds, String merchantIds,Integer pageNo,Integer pageSize, int length, Map<Long,String> merchantNameCacheMap) throws Exception {
        List<Object[]> list=new ArrayList<Object[]>();
        Object[] objs=null;
        int start=(pageNo-1)*pageSize;
        Map<String, Object> dbParams = getDbParams(merchantId,mac,ssid,merchantQueryType,provinceIdLong,cityIdLong,areaIdLong,projectId,projectIds,excludeProjectIds,merchantIds);//获取请求参数
        int totalRecord = EntityClient.getEntityInfoCountByMerId(JsonUtil.toJson(dbParams));
        if(totalRecord <= 0){
            return list;
        }
        dbParams.put("pageNum", pageNo);
        dbParams.put("pageSize", pageSize);
        List<EntityInfo> entityInfoList = EntityClient.getEntityInfoListByMerId(JsonUtil.toJson(dbParams));
        for(EntityInfo info : entityInfoList){
            objs = new Object[length];
            objs[0] = ++start;
            objs[1] = info.getDevMac();
            objs[2] = info.getSsid();
            objs[3] = getMerNameCacheByMerId(merchantNameCacheMap, info);//获取商户名称
            objs[4] = info.getAcName();
            objs[5] = info.getEntityTypeDsp();
            objs[6] = info.getStatusDsp();
            objs[7] = info.getOnlineLastTime();
            objs[8] = info.getOnlineNum();
            list.add(objs);
        }
        return list;
    }
    
    /**
     * 通过商户id从缓存map获取商户名称
     * @param merchantNameCacheMap 商户名称缓存map
     * @param info 设备信息
     * @return 商户名称
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年7月31日 上午11:55:03
     */
    private String getMerNameCacheByMerId(Map<Long, String> merchantNameCacheMap, EntityInfo info) throws Exception {
        Long merId = null;//商户id
        String merName = null;//商户名称
        merId = info.getMerchantId();//商户id
        if(merId == null || merId == 0L){
            return StringUtils.EMPTY;//直接返回空字符串
        }
        merName = merchantNameCacheMap.get(merId);
        if(StringUtils.isBlank(merName)){
            merName = MerchantClient.getNameByIdCache(merId);//调用数据中心接口获取商户名称
            merchantNameCacheMap.put(merId, merName);//将商户名称缓存至map中
        }
        return merName;
    }

    /**
     * @param entityinfo 监控实体
     * @param devMac 设备mac
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年6月5日 下午2:36:18
     */
    @SuppressWarnings("rawtypes")
    public void deviceStatusRefresh(EntityInfo entityinfo,String devMac) throws Exception {
        Map deviceData=DeviceBusClient.getDeviceData(devMac);//获取设备信息
        Map data=(Map) deviceData.get(devMac);
        if(data==null||data.isEmpty()){
            throw new BizException("E2400003", MessageUtil.getMessage("E2400003",devMac));//通过设备MAC[{0}]未找到设备信息!
        }
        String status=(String) data.get("status");
        Map merchantDeviceData=DeviceBusClient.getMerchantDeviceData(devMac);//获取设备商户信息
        Integer onlineNum=merchantDeviceData==null?0:merchantDeviceData.size();
        if(status.equalsIgnoreCase("offline")){
            entityinfo.setStatus(0);//设置设备状态离线
            entityinfo.setOnlineNum(0);//离线的话在线数量置为0
        }else{
            entityinfo.setStatus(1);//设置设备状态在线
            entityinfo.setOnlineNum(onlineNum);//设置在线数量
        }
       
    }
}
