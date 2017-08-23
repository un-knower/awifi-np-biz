package com.awifi.np.biz.merdevsrv.device.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.merdevsrv.device.service.DeviceService;
import com.awifi.np.biz.toe.admin.device.service.SfTerminalConfigService;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午2:28:22
 * 创建作者：亢燕翔
 * 文件名称：DeviceServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService{
    
    /**省分平台业务层*/
    @Resource(name = "sfTerminalConfigService")
    private SfTerminalConfigService sfTerminalConfigService;
    
    
//    /**微站商户设备*/
//    @Resource(name = "msMerchantDeviceService")
//    private MsMerchantDeviceService msMerchantDeviceService;
    
    /**
     * toe用户服务
     */
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**
     * toe角色服务
     */
    @Resource(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    /**
     * 获取虚拟设备列表
     * @param sessionUser session
     * @param params 请求参数
     * @param page 分页实体
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月4日 下午2:20:13
     */
    public void getListByParam(SessionUser sessionUser, String params, Page<Device> page) throws Exception {
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户ID
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省ID
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));//市ID
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));//区县ID
        Integer pageNoInteger = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        Integer pageNo = pageNoInteger != null ? pageNoInteger : 1;
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页数量
        
        String userNames=(String) paramsMap.get("userNames");//商户账号
        
        /*数据校验*/
        int maxSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//获取系统配置最大每页条数限制
        ValidUtil.valid("页码[pageNo]", pageNo, "numeric");
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxSize+"}}");
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser, merchantId, provinceId, cityId, areaId, null, null);//获取数据权限信息
        Long merchantIdLong = permissionMap.get("merchantId") != null ? (Long) permissionMap.get("merchantId") : null;
        String type = permissionMap.get("type") != null ? (String) permissionMap.get("type") : null;
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));
        Long projectId= CastUtil.toLong(permissionMap.get("projectId"));
        String projectIds=(String) permissionMap.get("projectIds");
        String excludeProjectIds=(String) permissionMap.get("filterProjectIds");
        String merchantIds=(String) permissionMap.get("merchantIds");
        
        Map<Long,String> idNameMap = null;
        if(StringUtils.isNotBlank(userNames)){
            idNameMap = toeUserService.getIdAndUserNameByUsernames(userNames);//查询toe库返回商户id和用户名的map
            if(idNameMap==null||idNameMap.isEmpty()){
                return;//多个userName查询不到记录，直接返回空
            }
        }
        
        String tempMerchantIds=getMerchantIds(idNameMap,userNames);
        if(tempMerchantIds!=null){
            merchantIds=tempMerchantIds;//账号查询商户ids不为空，覆盖sessionUser中获取的merchantIds
        }
        Map<String, Object> dbParams = getDbParams(merchantIdLong,type,provinceIdLong,cityIdLong,areaIdLong,projectId,projectIds,excludeProjectIds,merchantIds,paramsMap);//获取请求参数
        int totalRecord = DeviceClient.getCountByParam(JsonUtil.toJson(dbParams));
        
        page.setTotalRecord(totalRecord);
        if(totalRecord <= 0){
            return;
        }
        dbParams.put("pageNum", pageNo);
        dbParams.put("pageSize", pageSize);
        
        List<Device> deviceList = DeviceClient.getListByParam(JsonUtil.toJson(dbParams));
        configMerchantUserName(idNameMap, deviceList);//配置商户账号
        page.setRecords(deviceList);
    }

    /**
     * 配置商户账号
     * @param idNameMap 账号
     * @param deviceList 设备集合
     * @author 许小满  
     * @date 2017年6月8日 下午1:30:32
     */
    private void configMerchantUserName(Map<Long, String> idNameMap, List<Device> deviceList) {
        if(idNameMap == null || idNameMap.isEmpty()){
            return;
        }
        for(Device device : deviceList){
            device.setUserName(idNameMap.get(device.getMerchantId()));
        }
    }
    
    /**
     * 获取商户ids
     * @param userNames 账号
     * @author 王冬冬
     * @param idNameMap 
     * @return merchantIds 
     * @date 2017年6月8日 下午1:30:32
     */
    private String getMerchantIds(Map<Long, String> idNameMap, String userNames) {
        if(idNameMap == null || idNameMap.isEmpty()){
            return null;
        }
        StringBuilder merchantIdsBuilder=new StringBuilder();
        for(Long merchantId:idNameMap.keySet()){
            merchantIdsBuilder.append(merchantId+",");
        }
        String merchantIds=merchantIdsBuilder.toString();
        merchantIds=merchantIds.substring(0,merchantIds.lastIndexOf(','));
        return merchantIds;
    }

    /**
     * 获取数据中心请求参数
     * @param merchantIdLong 商户id
     * @param type 层级关系
     * @param provinceIdLong 省id
     * @param cityIdLong 市id
     * @param areaIdLong 区县id
     * @param paramsMap 前端传入参数
     * @return json
     * @author 亢燕翔  
     * @param merchantIds 
     * @param excludeProjectIds 
     * @param projectIds 
     * @param projectId 
     * @date 2017年2月4日 上午8:40:35
     */
    private Map<String, Object> getDbParams(Long merchantIdLong, String type, Long provinceIdLong, Long cityIdLong, Long areaIdLong, Long projectId, String projectIds, String excludeProjectIds, String merchantIds, Map<String, Object> paramsMap) {
        Map<String, Object> dbParams = new HashMap<String, Object>();
        dbParams.put("beginDate", paramsMap.get("beginDate"));//开始时间
        dbParams.put("endDate", paramsMap.get("endDate"));//结束时间
        dbParams.put("belongTo", paramsMap.get("belongTo"));//设备标签
        dbParams.put("deviceId", paramsMap.get("deviceId"));//设备id
        dbParams.put("entityName", paramsMap.get("deviceName"));//设备名称
        dbParams.put("ssid", paramsMap.get("ssid"));//热点
        String entityType = (String) paramsMap.get("entityType");
        dbParams.put("macAddr", paramsMap.get("devMac"));//设备物理地址
        dbParams.put("ipAddr", paramsMap.get("devIp"));//设备IP地址
        dbParams.put("broadbandAccount", paramsMap.get("broadbandAccount"));//宽带账号
        dbParams.put("entityType", entityType);//设备类型
        dbParams.put("province", provinceIdLong);//省id
        dbParams.put("city", cityIdLong);//市id
        dbParams.put("county", areaIdLong);//区县id
        dbParams.put("bindFlag", "1");//商户绑定标志 0:未绑定，1：绑定过，空：全部(String)
        //dbParams.put("status", 1);//1:正常;2:锁定/冻结;9:作废/注销
        dbParams.put("merchantId", merchantIdLong);//商户id
        dbParams.put("merchantQueryType", type);//this只查当前节点（默认）;nextLevel只查当前节点的下一层;nextAll查当前节点所有不包括当前;nextAllWithThis查当前节点所有包含当前
        if(projectId!=null){
            dbParams.put("projectId",projectId);//项目id//（Long）
        }
        if(StringUtils.isNotBlank(projectIds)){
//            dbParams.put("merchantProjects",projectIds);
            dbParams.put("projectIds",projectIds);
        }
        if(StringUtils.isNotBlank(excludeProjectIds)){
            dbParams.put("excludeProjectIds",excludeProjectIds);
        }
        if(StringUtils.isNotBlank(merchantIds)){
            dbParams.put("merchantIds", merchantIds);
        }
        String devMacs=(String) paramsMap.get("devMacs");
        if(StringUtils.isNotBlank(devMacs)){
            dbParams.put("macs",devMacs);//多个mac//（String）
        }
        //ON OFF
        String chinaNetSwitch= (String) paramsMap.get("chinaNetSwitch");
        if(StringUtils.isNotBlank(chinaNetSwitch)){
            dbParams.put("chinaNetSwitch","ON".equalsIgnoreCase(chinaNetSwitch)?1:0);
        }
        String awifiSwitch=(String) paramsMap.get("awifiSwitch");
        if(StringUtils.isNotBlank(awifiSwitch)){
            dbParams.put("awifiSwitch","ON".equalsIgnoreCase(awifiSwitch)?1:0);
        }
        String onekeySwitch=(String) paramsMap.get("onekeySwitch");
        if(StringUtils.isNotBlank(onekeySwitch)){
            dbParams.put("onekeySwitch","ON".equalsIgnoreCase(onekeySwitch)?1:0);
        }
        String lanSwitch=(String) paramsMap.get("lanSwitch");
        if(StringUtils.isNotBlank(lanSwitch)){
            dbParams.put("lanSwitch", "ON".equalsIgnoreCase(lanSwitch)?1:0);
        }
        Integer offlineTime=CastUtil.toInteger(paramsMap.get("offlineTime"));
        if(offlineTime!=null){
            dbParams.put("offlineTime", offlineTime);
        }
        return dbParams;
    }
    
    /**
     * 热点导入
     * @param sheet 工作表
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param lastRowNum 最大行数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 下午6:50:42
     */
    public void importHotarea(Sheet sheet, String belongTo, String entityType, Long merchantId, Long projectId, int lastRowNum) throws Exception {
        //标题非空校验
        Cell hotareaTitle = sheet.getRow(0).getCell(0);
        if(hotareaTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        
        //临时变量
        Cell hotarea = null;
        String hotareaValue = null;
        DeviceOwner deviceOwner = null;
        List<DeviceOwner> deviceOwnersList = new ArrayList<DeviceOwner>();
        HashSet<String> hashSetHotarea = new HashSet<String>();//临时存储Hotarea集合
        Map<Integer, String> mapHotarea = new TreeMap<Integer, String>();//临时存储Hotarea集合
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            hotarea = row.getCell(0);
            //非空校验
            if(hotarea == null){
                Object[] args = {i+1,hotareaTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            
            //重复性校验
            hotareaValue = hotarea.getStringCellValue();
            hashSetHotarea.add(hotareaValue);
            mapHotarea.put(i, hotareaValue);
            if(hashSetHotarea.size() != i){
                for(Entry<Integer, String> maps : mapHotarea.entrySet()){
                    if(maps.getValue().equals(hotareaValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            
            //数据封装
            deviceOwner = new DeviceOwner();
            deviceOwner.setDeviceType(entityType);
            deviceOwner.setDeviceName(hotareaValue);
            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwnersList.add(deviceOwner);
        }
        paramsMap.put("owner", belongTo);
        paramsMap.put("deviceList", deviceOwnersList);
        DeviceClient.setOwner(JsonUtil.toJson(paramsMap));//请求数据中心
    }

    /**
     * nas导入
     * @param sheet 工作表
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param lastRowNum 最大行数
     * @throws Exception 
     * @author 亢燕翔  
     * @date 2017年2月6日 下午6:46:15
     */
    public void importNas(Sheet sheet, String belongTo, String entityType,Long merchantId, Long projectId, int lastRowNum) throws Exception {
        //标题非空校验
        Cell nasTitle = sheet.getRow(0).getCell(0);
        if(nasTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        
        //临时变量
        Cell nas = null;
        String nasValue = null;
        DeviceOwner deviceOwner = null;
        List<DeviceOwner> deviceOwnersList = new ArrayList<DeviceOwner>();
        HashSet<String> hashSetNas = new HashSet<String>();//临时存储Nas集合
        Map<Integer, String> mapNas = new TreeMap<Integer, String>();//临时存储Nas集合
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            nas = row.getCell(0);
            //非空校验
            if(nas == null){
                Object[] args = {i+1,nasTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            
            //重复性校验
            nasValue = nas.getStringCellValue();
            hashSetNas.add(nasValue);
            mapNas.put(i, nasValue);
            if(hashSetNas.size() != i){
                for(Entry<Integer, String> maps : mapNas.entrySet()){
                    if(maps.getValue().equals(nasValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            
            //数据封装
            deviceOwner = new DeviceOwner();
            deviceOwner.setDeviceType(entityType);
            deviceOwner.setDeviceName(nasValue);
            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwnersList.add(deviceOwner);
        }
        paramsMap.put("owner", belongTo);
        paramsMap.put("deviceList", deviceOwnersList);
        DeviceClient.setOwner(JsonUtil.toJson(paramsMap));//请求数据中心
    }

    /**
     * 瘦ap导入
     * @param sheet 工作页
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param lastRowNum 最大行数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 下午6:19:36
     */
    public void importFit(Sheet sheet, String belongTo, String entityType, Long merchantId, Long projectId, int lastRowNum) throws Exception {
        //标题非空校验
        Cell devMacTitle = sheet.getRow(0).getCell(0);
        if(devMacTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        Cell ssidTitle = sheet.getRow(0).getCell(1);
        if(ssidTitle == null){
            Object[] args = {"1行","B列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }

        //临时变量
        Cell devMac = null;
        String devMacValue = null;
        Cell ssid = null;
        String ssidValue = null;
        DeviceOwner deviceOwner = null;
        List<DeviceOwner> deviceOwnersList = new ArrayList<DeviceOwner>();
        HashSet<String> hashSetMac = new HashSet<String>();//临时存储MAC集合
        Map<Integer, String> mapMac = new TreeMap<Integer, String>();//临时存储MAC集合
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            devMac = row.getCell(0);//设备mac
            ssid = sheet.getRow(i).getCell(1);//热点
            
            //非空校验
            if(devMac == null){
                Object[] args = {i+1,devMacTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            if(ssid == null){
                Object[] args = {i+1,ssidTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            
            //重复性校验
            devMacValue = devMac.getStringCellValue();
            ssidValue = ssid.getStringCellValue();
            hashSetMac.add(devMacValue);
            mapMac.put(i, devMacValue);
            if(hashSetMac.size() != i){
                for(Entry<Integer, String> maps : mapMac.entrySet()){
                    if(maps.getValue().equals(devMacValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            
            //mac正则校验
            if(!RegexUtil.match(devMacValue, RegexConstants.MAC_PATTERN)){
                Object[] args = {i+1,devMacTitle.getStringCellValue(),RegexConstants.MAC_PATTERN_DSP};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }

            //数据封装
            deviceOwner = new DeviceOwner();
            deviceOwner.setDeviceType(entityType);
            deviceOwner.setMac(devMacValue);
            deviceOwner.setSsid(ssidValue);
            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwnersList.add(deviceOwner);
        }
        paramsMap.put("owner", belongTo);
        paramsMap.put("deviceList", deviceOwnersList);
        DeviceClient.setOwner(JsonUtil.toJson(paramsMap));//请求数据中心
        
    
    }

    /**
     * 胖ap导入
     * @param sheet 工作页
     * @param belongTo 设备归属
     * @param entityType 设备类型 
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param isSF 是否为省分平台
     * @param provinceId 省id
     * @param lastRowNum 最大行数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 上午11:17:28
     */
    public void importFat(Sheet sheet, String belongTo, String entityType, Long merchantId, Long projectId, Boolean isSF, Long provinceId, int lastRowNum) throws Exception {

        Cell devMacTitle = sheet.getRow(0).getCell(0);
        if(devMacTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        Cell devMac = null;
        String devMacValue = null;
        DeviceOwner deviceOwner = null;
        List<DeviceOwner> deviceOwnersList = new ArrayList<DeviceOwner>();
        HashSet<String> hashSetMac = new HashSet<String>();//临时存储MAC集合
        Map<Integer, String> mapMac = new TreeMap<Integer, String>();//临时存储MAC集合
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            devMac = row.getCell(0);//设备mac
            
            //非空校验
            if(devMac == null){
                Object[] args = {i+1,devMacTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            
            //重复性校验
            devMacValue = devMac.getStringCellValue();
            hashSetMac.add(devMacValue);
            mapMac.put(i, devMacValue);
            if(hashSetMac.size() != i){
                for(Entry<Integer, String> maps : mapMac.entrySet()){
                    if(maps.getValue().equals(devMacValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            
            //mac正则校验
            if(!RegexUtil.match(devMacValue, RegexConstants.MAC_PATTERN)){
                Object[] args = {i+1,devMacTitle.getStringCellValue(),RegexConstants.MAC_PATTERN_DSP};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            deviceOwner = new DeviceOwner();
            deviceOwner.setDeviceType(entityType);
            deviceOwner.setMac(devMacValue);
            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwnersList.add(deviceOwner);
        }
        paramsMap.put("owner", belongTo);
        paramsMap.put("deviceList", deviceOwnersList);
        DeviceClient.setOwner(JsonUtil.toJson(paramsMap));
        //省份平台设备激活
        if(isSF){
            sfTerminalConfigService.setFatAPRegister(provinceId,deviceOwnersList);
        }
        
    }

    /**
     * 设备过户
     * @param bodyParam 请求体参数
     * @author 亢燕翔
     * @throws Exception 异常
     * @date 2017年2月7日 下午2:18:47
     */
    public void transfer(Map<String, Object> bodyParam) throws Exception {
        //接收参数
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));//商户ID
        Long projectId = CastUtil.toLong(bodyParam.get("projectId"));//项目ID
        List<String> deviceIdList = (List<String>) bodyParam.get("deviceIds");//设备id集合
        if(deviceIdList == null){
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", new Object[]{"设备ids"}));//{0}不允许为空!
        }
        int maxSize = deviceIdList.size();
        
        //数据校验
        ValidUtil.valid("目标商户id[toMerchantId]", merchantId, "{'required':true,'numeric':true}");
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");
        ValidUtil.valid("设备id集合", deviceIdList, "arrayNotBlank");
        
        //数据解绑并绑定
        String belongTo = FormatUtil.formatBelongToByProjectId(projectId.intValue());//通过项目id获取对应的belongTo
        for(int j=0; j<maxSize; j++){
            DeviceClient.transfer(deviceIdList.get(j),merchantId,belongTo);
        }
        
    }

  /**
     * 获取设备放通参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 王冬冬
     * @date 2017年4月17日 下午6:53:01
     */
    private String getBatchEscapeData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("release", devSwitch);//OK是进入逃生模式，FAIL是退出逃生
        if(devSwitch.equals("OK")){
            busParams.put("time", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_fatapescape_time")));//任务过期时间，单位秒
        }
        return JsonUtil.toJson(busParams);
    }
    
    /**
     * @param bodyParam 设备放通参数
     * @author 王冬冬  
     * @date 2017年4月17日 下午7:07:40
     */
    @Override
    public void batchEscape(List<Map<String, Object>> bodyParam) throws Exception {
    	Map<String, Object> deviceParamMap = new HashMap<String, Object>(4);
    	deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
    	deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
        for(Map<String, Object> paramMap : bodyParam){
            deviceParamMap.put("merchantId", CastUtil.toLong(paramMap.get("merchantId")));//商户id
        	//deviceParamMap.put(key, value);//设备开关
            deviceParamMap.put("onekeySwitch",((String)paramMap.get("devSwitch")).equalsIgnoreCase("ON")?0:1);//取相反
            
            Integer maxCount=Integer.parseInt(SysConfigUtil.getParamValue("xls_export_sheet_max_size"));//配置的最大数
            int totalRecord = DeviceClient.getCountByParam(JsonUtil.toJson(deviceParamMap));//获取数据中心该商户设备总记录数
            if(totalRecord>maxCount){
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058", new Object[]{"数据总记录数", totalRecord}));//{0}[{1}]超出了范围!
            }
            deviceParamMap.put("pageNum",1);//页码
            deviceParamMap.put("pageSize",maxCount);//页数
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            if(totalRecord > 0){
                List<Device> devices=DeviceClient.getListByParam(JsonUtil.toJson(deviceParamMap));//根据参数调数据中心获取设备信息
                devSwitch = devSwitch.equalsIgnoreCase("ON") ? "OK" : "FAIL";
                for(Device device:devices){
                    String data = getBatchEscapeData(device.getDeviceId(),device.getDevMac(),devSwitch);//获取设备放通参数
                    DeviceBusClient.setFatAPEscape(data);//请求设备总线接口，放通设备
                }
            }
            
        }

    }
    /**
     * @param bodyParam chinanet开关参数
     * @author 王冬冬  
     * @date 2017年4月17日 下午7:07:40
     */
    @Override
    public void batchChinanetSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception {
    	Map<String,Object> deviceParamMap=new HashMap<String,Object>(4);
    	deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
    	deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
        for(Map<String, Object> paramMap : bodyParam){
            deviceParamMap.put("merchantId",paramMap.get("merchantId"));//设备类型            
            deviceParamMap.put("chinaNetSwitch",((String)paramMap.get("devSwitch")).equalsIgnoreCase("ON")?0:1);//设备开关          

            Integer maxCount=Integer.parseInt(SysConfigUtil.getParamValue("xls_export_sheet_max_size"));//配置的最大数
            int totalRecord = DeviceClient.getCountByParam(JsonUtil.toJson(deviceParamMap));//获取数据中心该商户设备总记录数
            if(totalRecord>maxCount){
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",new Object[]{"数据总记录数", totalRecord}));//{0}[{1}]超出了范围!
            }
            deviceParamMap.put("pageNum",1);//页码
            deviceParamMap.put("pageSize",maxCount);//页数
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            if(totalRecord > 0){
                List<Device> devices=DeviceClient.getListByParam(JsonUtil.toJson(deviceParamMap));//根据参数调数据中心获取设备信息
                for(Device device:devices){
                    String data = getBatchChinanetSsidSwitchData(device.getDeviceId(),device.getDevMac(),devSwitch);//获取设备放通参数
                    DeviceBusClient.setFatAPSSIDSwitch(data);//请求设备总线接口，放通设备
                }
            }
            
        }

    }

/**
     * 获取Chinanet开关接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 王冬冬
     * @date 2017年2月16日 上午9:02:44
     */
    private String getBatchChinanetSsidSwitchData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("wifiswitch", devSwitch);//ON开OFF关
        return JsonUtil.toJson(busParams);
    }

    @Override
    public void batchAwifiSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception {
    	Map<String,Object> deviceParamMap=new HashMap<String,Object>(4);
    	deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
    	deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
    	for(Map<String, Object> paramMap : bodyParam){
    	    deviceParamMap.put("merchantId",paramMap.get("merchantId"));//设备类型            
            deviceParamMap.put("awifiSwitch",((String)paramMap.get("devSwitch")).equalsIgnoreCase("ON")?0:1);//设备开关          
            Integer maxCount=Integer.parseInt(SysConfigUtil.getParamValue("xls_export_sheet_max_size"));//配置的最大数
            
            
            int totalRecord = DeviceClient.getCountByParam(JsonUtil.toJson(deviceParamMap));//获取数据中心该商户设备总记录数
            
            if(totalRecord>maxCount){
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058", new Object[]{"数据总记录数", totalRecord}));//{0}[{1}]超出了范围!
            }
            deviceParamMap.put("pageNum",1);//页码
            deviceParamMap.put("pageSize",maxCount);//页数
            
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            if(totalRecord > 0){
                List<Device> devices=DeviceClient.getListByParam(JsonUtil.toJson(deviceParamMap));//根据参数调数据中心获取设备信息
                for(Device device:devices){
                    String data = getBatchAwifiSsidSwitchData(device.getDeviceId(),device.getDevMac(),devSwitch);//获取设备放通参数
                    DeviceBusClient.setFatAPAWiFiSwitch(data);//请求设备总线接口，放通设备
                }
            }
            
        }
	
    }
    /**
     * 获取aWiFi开关接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 王冬冬
     * @date 2017年2月16日 上午9:12:24
     */
    private String getBatchAwifiSsidSwitchData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("awifiswitch", devSwitch);//ON开OFF关
        return JsonUtil.toJson(busParams);
    }
    @Override
	public void batchLanSwitch(List<Map<String, Object>> bodyParam) throws Exception {
    	Map<String,Object> deviceParamMap=new HashMap<String,Object>(4);
    	deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
    	deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
    	for(Map<String, Object> paramMap : bodyParam){
    	    deviceParamMap.put("merchantId",paramMap.get("merchantId"));//设备类型            
            deviceParamMap.put("lanSwitch",((String)paramMap.get("devSwitch")).equalsIgnoreCase("ON")?0:1);//设备开关          
            Integer maxCount=Integer.parseInt(SysConfigUtil.getParamValue("xls_export_sheet_max_size"));//配置的最大数
            
            int totalRecord = DeviceClient.getCountByParam(JsonUtil.toJson(deviceParamMap));//获取数据中心该商户设备总记录数
            
            if(totalRecord>maxCount){
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058", new Object[]{"数据总记录数", totalRecord}));//{0}[{1}]超出了范围!
            }
            deviceParamMap.put("pageNum",1);//页码
            deviceParamMap.put("pageSize",maxCount);//页数
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            if(totalRecord > 0){
                List<Device> devices=DeviceClient.getListByParam(JsonUtil.toJson(deviceParamMap));//根据参数调数据中心获取设备信息
                for(Device device:devices){
                    String data = getBatchLanSwitchData(device.getDeviceId(),device.getDevMac(),devSwitch);//获取设备放通参数
                    DeviceBusClient.setFatAPLANSwitch(data);//请求设备总线接口，放通设备
                }
            }
            
        }
    }
    
    /**
     * 获取LAN口认证开关接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:19:18
     */
    private String getBatchLanSwitchData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("lanswitch", devSwitch);//ON开OFF关
        return JsonUtil.toJson(busParams);
    }
    
    @Override
	public void batchClientTimeout(List<Map<String, Object>> bodyParam) throws Exception {
    	Map<String,Object> deviceParamMap=new HashMap<String,Object>(4);
    	deviceParamMap.put("merchantQueryType","this");//设置merchantQueryType
    	deviceParamMap.put("entityType","31,32,33,34,35,36,37");//设备类型
    	for(Map<String, Object> paramMap : bodyParam){
    	    deviceParamMap.put("merchantId",paramMap.get("merchantId"));//设备类型            
//            deviceParamMap.put("offlineTime",paramMap.get("offlineTime"));//闲时下线时间  
            Integer maxCount=Integer.parseInt(SysConfigUtil.getParamValue("xls_export_sheet_max_size"));//配置的最大数
            int totalRecord = DeviceClient.getCountByParam(JsonUtil.toJson(deviceParamMap));//获取数据中心该商户设备总记录数
            
            if(totalRecord>maxCount){
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058", new Object[]{"数据总记录数", totalRecord}));//{0}[{1}]超出了范围!
            }
            deviceParamMap.put("pageNum",1);//页码
            deviceParamMap.put("pageSize",maxCount);//页数
           
            Float time = CastUtil.toFloat(paramMap.get("offlineTime"));//闲时下线时间
            if(time==null){
                time = CastUtil.toFloat("0.5");
            }
            if(!RegexUtil.match(time.toString(), RegexConstants.HOUR_PATTERN) || time <= 0.0|| time > 24.0){
                throw new ValidException("E2400005", MessageUtil.getMessage("E2400005"));//不符合正则规范!
            }
            Integer secondsTime = timeHourToSeconds(time);//把时间从小时转为秒
            if(totalRecord > 0){
                List<Device> devices=DeviceClient.getListByParam(JsonUtil.toJson(deviceParamMap));//根据参数调数据中心获取设备信息
                for(Device device:devices){
                    String data = getBatchClientTimeoutData(device.getDeviceId(),device.getDevMac(),secondsTime);//获取设备放通参数
                    DeviceBusClient.setFatAPClientTimeout(data);//请求设备总线接口
                }
            }
        }
    }
 
    /**
     * 把时间从小时转为秒
     * @param time 时间（小时）
     * @return 时间（秒）
     * @author 亢燕翔  
     * @date 2017年2月28日 下午7:10:09
     */
    private Integer timeHourToSeconds(Float time) {
        Float seconds = time * 60 * 60;
        return CastUtil.toInteger(seconds);
    }

	/**
     * 获取闲时下线接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param time 闲时下线时间，单位秒
     * @return data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:26:31
     */
    private String getBatchClientTimeoutData(String deviceId, String devMac, Integer time) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("time", time);//闲时下线时间，单位秒  前端传入单位为小时  请求设备总线为秒
        return JsonUtil.toJson(busParams);
    }

    @Override
    public void importFat(Sheet sheet, String belongTo, String entityType, Long projectId, Long roleId,int lastRowNum,SessionUser user) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        Cell fatTitle = sheet.getRow(0).getCell(0);
        if(fatTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        Cell mac = null;
        Set<String> macSet=new HashSet<String>();
        Map<Integer,String> macMap=new HashMap<Integer,String>();
        DeviceOwner deviceOwner=null;
        List<DeviceOwner> deviceOwnersList=new ArrayList<DeviceOwner>();
        Set<String> userNamesSet=new HashSet<String>();
        Map<String,Long> userNamesMap=null;
        Map<String, List<Map<String, Object>>> industryMap = IndustryClient.getIndustryMap();//一次性获取全部行业信息
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            mac = row.getCell(0);//设备mac
            //非空校验
            if(mac == null){
                Object[] args = {i+1,fatTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            String devMacValue = mac.getStringCellValue();
            //mac正则校验
            if(!RegexUtil.match(mac.getStringCellValue(), RegexConstants.MAC_PATTERN)){
                Object[] args = {i+1,fatTitle.getStringCellValue(),RegexConstants.MAC_PATTERN_DSP};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            //重复性校验
            macSet.add(devMacValue);
            macMap.put(i, devMacValue);
            if(macSet.size() != i){
                for(Entry<Integer, String> maps : macMap.entrySet()){
                    if(maps.getValue().equals(devMacValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            
            Cell broadAccount=sheet.getRow(i).getCell(1);
            Cell broadAccountTitle=sheet.getRow(0).getCell(1);
            if(broadAccount==null){
                Object[] args = {i+1,broadAccountTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            broadAccount.setCellType(Cell.CELL_TYPE_STRING);
            if(!RegexUtil.match(broadAccount.getStringCellValue(), RegexConstants.USER_NAME_PATTERN)){
                Object[] args = {i+1,broadAccount.getStringCellValue(),RegexConstants.USER_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            
            Cell userName=sheet.getRow(i).getCell(2);
            Cell userNameTitle=sheet.getRow(0).getCell(2);
            if(userName==null){
                Object[] args = {i+1,userNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            userName.setCellType(Cell.CELL_TYPE_STRING);
            if(!RegexUtil.match(userName.getStringCellValue(), RegexConstants.USER_NAME_PATTERN)){
                Object[] args = {i+1,userName.getStringCellValue(),RegexConstants.USER_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            userNamesSet.add(userName.getStringCellValue());
            Cell merchantName=sheet.getRow(i).getCell(3);
            Cell merchantNameTitle=sheet.getRow(0).getCell(3);
            if(merchantName==null){
                Object[] args = {i+1,merchantNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            if(!RegexUtil.match(merchantName.getStringCellValue(), RegexConstants.MERCHANT_NAME_PATTERN)){
                Object[] args = {i+1,merchantName.getStringCellValue(),RegexConstants.MERCHANT_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            Cell priIndustry=sheet.getRow(i).getCell(4);
            Cell priIndustryTitle=sheet.getRow(0).getCell(4);
            Map<String,Object> priIndustryMap = this.getIndustry(priIndustry.getStringCellValue(), industryMap, "1");
            if(priIndustryMap == null){
                Object[] message={(i+1),priIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            String priIndustryId = (String)priIndustryMap.get("industryId");
            Cell secIndustry=sheet.getRow(i).getCell(5);
            Cell secIndustryTitle=sheet.getRow(0).getCell(5);
            Map<String,Object> secIndustryMap = this.getIndustry(secIndustry.getStringCellValue(), industryMap, "2");
            if(secIndustryMap == null){
                Object[] message={(i+1),secIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            //判断是否是这个一级行业下的二级行业
            String secIndustryId = (String)secIndustryMap.get("industryId");
            boolean isChild = StringUtils.contains(secIndustryId,priIndustryId);
            if(!isChild){
                Object[] message={(i+1),"二级行业"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            
            Cell merchantAddress=sheet.getRow(i).getCell(8);
            Cell merchantAddressTitle=sheet.getRow(0).getCell(8);
            if(merchantAddress==null){
                Object[] args = {i+1,merchantAddressTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            merchantAddress.setCellType(Cell.CELL_TYPE_STRING);

            Cell province=sheet.getRow(i).getCell(10);
            Cell provinceTitle=sheet.getRow(0).getCell(10);
            if(province==null){
                Object[] args = {i+1,provinceTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell city=sheet.getRow(i).getCell(11);
            Cell cityTitle=sheet.getRow(0).getCell(11);
            if(city==null){
                Object[] args = {i+1,cityTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell country=sheet.getRow(i).getCell(12);
            Cell countryTitle=sheet.getRow(0).getCell(12);
            if(country==null){
                Object[] args = {i+1,countryTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            
            Cell ssid=sheet.getRow(i).getCell(9);
            Cell ssidTitle=sheet.getRow(0).getCell(9);
            if(ssid==null){
                Object[] args = {i+1,ssidTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            String [] ssidParams=ssid.getStringCellValue().split("-");
            if((!RegexUtil.match(ssidParams[0], RegexConstants.SSID_PRIEFIX)||StringUtils.isBlank(ssidParams[0]))&&!RegexUtil.match(ssidParams[1], RegexConstants.SSID_SUFFIX)){
                Object[] args = {i+1,ssid.getStringCellValue(),RegexConstants.SSID_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            
            Map<String, List<Map<String, Object>>> locationMap = LocationClient.getLocationMap();//一次性获取全部地区信息
            Map<String,Object> provinceMap = this.getLocation(province.getStringCellValue(), locationMap, null);//根据省名称获取省id
            if(provinceMap == null){
                Object[] message={(i+1),"省"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long provinceId = (Long)provinceMap.get("id");
            Map<String,Object> cityMap = this.getLocation(city.getStringCellValue(), locationMap, provinceMap);
            if(cityMap == null){
                Object[] message={(i+1),"市"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long cityId = (Long)cityMap.get("id");
            Map<String,Object> areaMap = this.getLocation(country.getStringCellValue(), locationMap, cityMap);
            if(areaMap == null){
                Object[] message={(i+1),"区"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long areaId = (Long)areaMap.get("id");
            
            deviceOwner = new DeviceOwner();
            deviceOwner.setSsid("aWiFi-"+ssid.getStringCellValue());
            deviceOwner.setDeviceType(entityType);
            deviceOwner.setMac(devMacValue);
//            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwner.setBroadAccount(broadAccount.getStringCellValue());
            deviceOwner.setMerchantAccount(userName.getStringCellValue());
            deviceOwner.setMerchantName(merchantName.getStringCellValue());
            deviceOwner.setFirstIndustry(priIndustryId);
            deviceOwner.setSecondIndustry(secIndustryId);
            deviceOwner.setContacter(sheet.getRow(i).getCell(6).getStringCellValue());
            Cell cellphone=sheet.getRow(i).getCell(7);
            cellphone.setCellType(Cell.CELL_TYPE_STRING);
            deviceOwner.setCellPhone(cellphone.getStringCellValue());
            deviceOwner.setMerchantAddress(merchantAddress.getStringCellValue());

            deviceOwner.setProvinceId(provinceId);
            deviceOwner.setCityId(cityId);
            deviceOwner.setCountryId(areaId);
            deviceOwner.setRoleId(roleId.toString());
            
            Map<String,Object> dbParams=new HashMap<String,Object>();
            dbParams.put("macAddr",deviceOwner.getMac());//设备物理地址
            List<Device> deviceList = DeviceClient.getListByParam(JsonUtil.toJson(dbParams));
            
            if(deviceList==null||deviceList.isEmpty()){
                throw new ValidException("E2400003", MessageUtil.getMessage("E2400003"));//根据mac未找到设备信息
            }
            deviceOwner.setDeviceId(deviceList.get(0).getDeviceId());
            deviceOwnersList.add(deviceOwner);
        }
        userNamesMap=toeUserService.getUserNameAndIdByUsernames(userNamesSet);
        
        for(DeviceOwner owner:deviceOwnersList){
            if(owner.getMerchantAccount()!=null){
                Long merchantId=userNamesMap.get(owner.getMerchantAccount());
                if(merchantId==null){//商户不存在
                    //todo create a merchant
                    merchantId =createaMerchant(owner,belongTo,user);
                }
                owner.setMerchantId(merchantId.toString());
            }
        }
        paramsMap.put("belongTo", belongTo);
        paramsMap.put("deviceList", JsonUtil.toJson(deviceOwnersList));
        DeviceClient.bind(JsonUtil.toJson(paramsMap));//数据中心设备绑定
        DeviceBusClient.setFatAPSSID(deviceOwnersList);//设备总线修改ssid
    }
    /**
     * 根据行业名称查找行业id
     * @param industryName 行业名称
     * @param industryMap 行业map
     * @param labelLevel 层级
     * @return 对应的行业
     * @author 王冬冬
     * @date 2016年3月29日 下午2:01:17
     */
    private Map<String,Object> getIndustry(String industryName, Map<String, List<Map<String,Object>>> industryMap,String labelLevel){
        List<Map<String,Object>> industryList = industryMap.get(industryName);
        if(industryList == null){
            return null;
        }
        int maxSize = industryList.size();
        if(maxSize <= 0){//没有数据返回空值
            return null;
        }
        Map<String,Object> industry = new HashMap<String,Object>();
        for(int i=0;i<maxSize;i++){
            industry = industryList.get(i);
            if(industry.get("industryLevel").equals(labelLevel)){//层级相同
                return industry;
            }
        }
        return null;
    }

    /**
     * 创建商户
     * @param owner 商户
     * @param belongTo 行业
     * @return merchantId
     * @author 王冬冬  
     * @param curUser 当前用户
     * @throws Exception 
     * @date 2017年5月16日 下午7:28:26
     */
    private Long createaMerchant(DeviceOwner owner,String belongTo, SessionUser curUser) throws Exception {
        boolean isExist= toeUserService.isUserNameExist(owner.getMerchantAccount());
        if(isExist){
            throw new ValidException("E2000027", MessageUtil.getMessage("E2000027"));//账号已存在
        }
        Map<String,Object> paramsMap=new HashMap<String,Object>();
        paramsMap.put("merchantName", owner.getMerchantName());
        //符合条件的总数
        int count = MerchantClient.getCountByParam(paramsMap);
        if(count>0){//如果大于0 
            throw new ValidException("E2200002", MessageUtil.getMessage("E2200002"));//账号已存在
        }
        
        Long curUserId = curUser.getId();
        Long merchantId = curUser.getMerchantId();
        Merchant merchant = new Merchant();
        merchant.setParentId(0L);
        if(merchantId != null){//如果当前登陆账号是商户，建的商户默认是这个商户的下级商户
            merchant.setParentId(merchantId);
        }
//        Merchant merchant=new Merchant();
        merchant.setAccount(owner.getMerchantAccount());
        merchant.setAddress(owner.getMerchantAddress());
        merchant.setProvince(owner.getProvince());
        merchant.setArea(owner.getCountry());
        merchant.setCity(owner.getCity());
        merchant.setProvinceId(owner.getProvinceId());
        merchant.setAreaId(owner.getCountryId());
        merchant.setCityId(owner.getCityId());
        merchant.setContact(owner.getContacter());
        merchant.setContactWay(owner.getCellPhone());
        merchant.setProjectId(Long.parseLong(owner.getProjectId()));
        merchant.setMerchantName(owner.getMerchantName());
        merchant.setRoleIds(owner.getRoleId());
        merchant.setPriIndustry(owner.getFirstIndustry());
        merchant.setSecIndustry(owner.getSecondIndustry());
        //调数据中心接口添加商户
        String priIndustryid=merchant.getPriIndustry();
        String secondIndustryId=merchant.getSecIndustry();
        
        if(FormatUtil.isMerchantType1(merchant.getProjectId())){//园区 酒店 微站 保存用户信息
            Long userId = UserAuthClient.addUserAuth(merchant.getAccount());
            merchant.setUserId(userId);
        }
        
        merchantId = MerchantClient.add(merchant,secondIndustryId!=null?secondIndustryId:priIndustryid);
        ToeUser user = new ToeUser();
        user.setUserName(merchant.getAccount());
        user.setProvinceId(merchant.getProvinceId());
        user.setCityId(merchant.getCityId());
        user.setAreaId(merchant.getAreaId());
        user.setContactPerson(merchant.getContact());
        user.setContactWay(merchant.getContactWay());
//        user.setRemark(merchant.getRemark());
        user.setProjectId(merchant.getProjectId());
        user.setCreateUserId(curUserId);
        Long userId = toeUserService.add(user);
        toeRoleService.addUserRole(userId,merchant.getRoleIds());
        toeUserService.addUserMerchant(userId,merchantId);
        return merchantId;
    }

    /**
     * @param merchantId 商户id
     * @param switchType 开关类型
     * @return map
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月4日 下午7:30:06
     */
    public Map<String, Object> getSwitchStatus(Long merchantId, String switchType) throws Exception {
        Map<String,Object>  map= DeviceClient.statSwitchByParam(merchantId,switchType);
        return map;
    }

//    /**
//     * 防蹭网开关
//     * @param bodyParam 传入参数
//     * @author 王冬冬  
//     * @throws Exception 
//     * @date 2017年5月8日 下午2:17:59
//     */
//    public void batchAntiRobber(List<Map<String, Object>> bodyParam) throws Exception {
//        for(Map<String,Object> map:bodyParam){
//            Long merchantId=CastUtil.toLong(map.get("merchantId")); 
//            Byte status=CastUtil.toByte(map.get("status"));
//            msMerchantDeviceService.updateAntiRobberSwitch(merchantId,status);
//        }
//    }
    /**
     * 通过商户id获取设备名称、ssid集合
     * @param merchantId 商户id
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月3日 下午2:05:36
     */
    public Map<String, Object> getDevInfoByMerchantId(Long merchantId) throws Exception {
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("merchantId", merchantId);
        paramsMap.put("merchantQueryType", "this");
        paramsMap.put("bindFlag", 1);
        List<Device> devices = DeviceClient.getListByParam(JsonUtil.toJson(paramsMap));
        int count = devices.size();
        Device device = null;
        String deviceId = null;
        String deviceName = null;
        String ssid = null;
        List<Map<String,Object>> deviceList = new ArrayList<Map<String,Object>>();
        Map<String,Object> deviceMap = null;
        Set<String> ssidSet = new HashSet<String>();//ssid 去重
        for(int i=0; i<count; i++){
            device = devices.get(i);
            deviceId = device.getDeviceId();
            deviceName = device.getDeviceName();
            if(StringUtils.isNotBlank(deviceName)){
                deviceMap = new HashMap<String,Object>();
                deviceMap.put("deviceId", deviceId);
                deviceMap.put("deviceName", deviceName);
                deviceList.add(deviceMap);
            }
            ssid = device.getSsid();
            if(StringUtils.isNotBlank(ssid)){
                ssidSet.add(ssid);
            }
        }
        Map<String, Object> data = new HashMap<String,Object>();
        data.put("deviceNames", deviceList);
        data.put("ssids", ssidSet);
        return data;
    }
    /**
     * 根据地区名称获取对应的id 
     * @param locationName 地区名
     * @param locationMap 地区散列表
     * @param parentLocation 父级
     * @return map
     * @author 王冬冬  
     * @date 2017年5月17日 上午10:18:45
     */
    private Map<String,Object> getLocation(String locationName, Map<String,List<Map<String,Object>>> locationMap, Map<String,Object> parentLocation){
        List<Map<String,Object>> locationList = locationMap.get(locationName);
        if(locationList == null){
            return null;
        }
        int maxSize = locationList.size();
        if(maxSize <= 0){//没有数据返回空值
            return null;
        }
        Map<String,Object> location = null;
        for(int i=0; i<maxSize; i++){
            location = locationList.get(i);
            Long parentId=(Long) location.get("parentId");
            if(parentLocation == null && parentId.longValue()==1L){
                return location;
            }
            if(location.get("parentId").equals(parentLocation.get("id"))){
                return location;
            }
        }
        return null;
    }

    @Override
    public void importFitDev(Sheet sheet, String belongTo, String entityType, Long projectId, Long roleId,int lastRowNum,SessionUser user) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        Cell fitTitle = sheet.getRow(0).getCell(0);
        if(fitTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        Cell mac = null;
        Cell ssid=null;
        Set<String> macSet=new HashSet<String>();
        Map<Integer,String> macMap=new HashMap<Integer,String>();
        Set<String> ssidSet=new HashSet<String>();
        Map<Integer,String> ssidMap=new HashMap<Integer,String>();
        DeviceOwner deviceOwner=null;
        List<DeviceOwner> deviceOwnersList=new ArrayList<DeviceOwner>();
        Set<String> userNamesSet=new HashSet<String>();
        Map<String,Long> userNamesMap=null;
        Map<String, List<Map<String, Object>>> industryMap = IndustryClient.getIndustryMap();//一次性获取全部行业信息
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            mac = row.getCell(0);//设备mac
            //非空校验
            if(mac == null){
                Object[] args = {i+1,fitTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            String devMacValue = mac.getStringCellValue();
            //mac正则校验
            if(!RegexUtil.match(mac.getStringCellValue(), RegexConstants.MAC_PATTERN)){
                Object[] args = {i+1,fitTitle.getStringCellValue(),RegexConstants.MAC_PATTERN_DSP};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            //重复性校验
            macSet.add(devMacValue);
            macMap.put(i, devMacValue);
            if(macSet.size() != i){
                for(Entry<Integer, String> maps : macMap.entrySet()){
                    if(maps.getValue().equals(devMacValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            
            ssid=sheet.getRow(i).getCell(1);
            Cell ssidTitle=sheet.getRow(0).getCell(1);
            if(ssid==null){
                Object[] args = {i+1,ssidTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            String ssidValue=ssid.getStringCellValue();
//            String [] ssidParams=ssidValue.split("-");
//            if((!RegexUtil.match(ssidParams[0], RegexConstants.SSID_PRIEFIX)||StringUtils.isBlank(ssidParams[0]))&&!RegexUtil.match(ssidParams[1], RegexConstants.SSID_SUFFIX)){
//                Object[] args = {i+1,ssid.getStringCellValue(),RegexConstants.SSID_NAME_PATTERN};
//                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
//            }
            //重复性校验
            ssidSet.add(ssidValue);
            ssidMap.put(i, ssidValue);
            if(ssidSet.size() != i){
                for(Entry<Integer, String> maps : ssidMap.entrySet()){
                    if(maps.getValue().equals(ssidValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            Cell merchantName=sheet.getRow(i).getCell(2);
            Cell merchantNameTitle=sheet.getRow(0).getCell(2);
            if(merchantName==null){
                Object[] args = {i+1,merchantNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            if(!RegexUtil.match(merchantName.getStringCellValue(), RegexConstants.MERCHANT_NAME_PATTERN)){
                Object[] args = {i+1,merchantName.getStringCellValue(),RegexConstants.MERCHANT_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            
            Cell userName=sheet.getRow(i).getCell(3);
            Cell userNameTitle=sheet.getRow(0).getCell(3);
            if(userName==null){
                Object[] args = {i+1,userNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            userName.setCellType(Cell.CELL_TYPE_STRING);
            if(!RegexUtil.match(userName.getStringCellValue(), RegexConstants.USER_NAME_PATTERN)){
                Object[] args = {i+1,userName.getStringCellValue(),RegexConstants.USER_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            userNamesSet.add(userName.getStringCellValue());
           
//            Cell priIndustry=sheet.getRow(i).getCell(4);
//            Cell priIndustryTitle=sheet.getRow(0).getCell(4);
//            if(priIndustry==null){
//                Object[] args = {i+1,priIndustryTitle.getStringCellValue()};
//                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
//            }
//            Cell secIndustry=sheet.getRow(i).getCell(5);
//            Cell secIndustryTitle=sheet.getRow(0).getCell(5);
//            if(secIndustry==null){
//                Object[] args = {i+1,secIndustryTitle.getStringCellValue()};
//                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
//            }
            Cell priIndustry=sheet.getRow(i).getCell(4);
            Cell priIndustryTitle=sheet.getRow(0).getCell(4);
            Map<String,Object> priIndustryMap = this.getIndustry(priIndustry.getStringCellValue(), industryMap, "1");
            if(priIndustryMap == null){
                Object[] message={(i+1),priIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            String priIndustryId = (String)priIndustryMap.get("industryId");
            Cell secIndustry=sheet.getRow(i).getCell(5);
            Cell secIndustryTitle=sheet.getRow(0).getCell(5);
            Map<String,Object> secIndustryMap = this.getIndustry(secIndustry.getStringCellValue(), industryMap, "2");
            if(secIndustryMap == null){
                Object[] message={(i+1),secIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            //判断是否是这个一级行业下的二级行业
            String secIndustryId = (String)secIndustryMap.get("industryId");
            boolean isChild = StringUtils.contains(secIndustryId,priIndustryId);
            if(!isChild){
                Object[] message={(i+1),"二级行业"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
           
            Cell province=sheet.getRow(i).getCell(8);
            Cell provinceTitle=sheet.getRow(0).getCell(8);
            if(province==null){
                Object[] args = {i+1,provinceTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell city=sheet.getRow(i).getCell(9);
            Cell cityTitle=sheet.getRow(0).getCell(9);
            if(city==null){
                Object[] args = {i+1,cityTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell country=sheet.getRow(i).getCell(10);
            Cell countryTitle=sheet.getRow(0).getCell(10);
            if(country==null){
                Object[] args = {i+1,countryTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell merchantAddress=sheet.getRow(i).getCell(11);
            Cell merchantAddressTitle=sheet.getRow(0).getCell(11);
            if(merchantAddress==null){
                Object[] args = {i+1,merchantAddressTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            merchantAddress.setCellType(Cell.CELL_TYPE_STRING);
            Map<String, List<Map<String, Object>>> locationMap = LocationClient.getLocationMap();//一次性获取全部地区信息
            Map<String,Object> provinceMap = this.getLocation(province.getStringCellValue(), locationMap, null);//根据省名称获取省id
            if(provinceMap == null){
                Object[] message={(i+1),"省"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long provinceId = (Long)provinceMap.get("id");
            Map<String,Object> cityMap = this.getLocation(city.getStringCellValue(), locationMap, provinceMap);
            if(cityMap == null){
                Object[] message={(i+1),"市"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long cityId = (Long)cityMap.get("id");
            Map<String,Object> areaMap = this.getLocation(country.getStringCellValue(), locationMap, cityMap);
            if(areaMap == null){
                Object[] message={(i+1),"区"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long areaId = (Long)areaMap.get("id");
            deviceOwner = new DeviceOwner();
            deviceOwner.setSsid(ssid.getStringCellValue());
            deviceOwner.setDeviceType(entityType);
            deviceOwner.setMac(devMacValue);
//            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwner.setMerchantAccount(userName.getStringCellValue());
            deviceOwner.setMerchantName(merchantName.getStringCellValue());
            deviceOwner.setFirstIndustry(priIndustryId);
            deviceOwner.setSecondIndustry(secIndustryId);
            deviceOwner.setContacter(sheet.getRow(i).getCell(6).getStringCellValue());
            Cell phone =sheet.getRow(i).getCell(7);
            phone.setCellType(Cell.CELL_TYPE_STRING);
            deviceOwner.setCellPhone(phone.getStringCellValue());
            deviceOwner.setMerchantAddress(merchantAddress.getStringCellValue());
            deviceOwner.setProvinceId(provinceId);
            deviceOwner.setCityId(cityId);
            deviceOwner.setCountryId(areaId);
            deviceOwner.setRoleId(roleId.toString());
            
//            Map<String,Object> dbParams=new HashMap<String,Object>();
//            dbParams.put("macAddr",deviceOwner.getMac());//设备物理地址
//            List<Device> deviceList = DeviceClient.getListByParam(JsonUtil.toJson(dbParams));
//            if(deviceList!=null&&!deviceList.isEmpty()){
//                deviceOwner.setDeviceId(deviceList.get(0).getDeviceId());
//            }
            deviceOwnersList.add(deviceOwner);
        }
        userNamesMap=toeUserService.getUserNameAndIdByUsernames(userNamesSet);
        
        for(DeviceOwner owner:deviceOwnersList){
            if(owner.getMerchantAccount()!=null){
                Long merchantId=userNamesMap.get(owner.getMerchantAccount());
                if(merchantId==null){//商户不存在
                    //todo create a merchant
                    merchantId =createaMerchant(owner,belongTo,user);
                }
                owner.setMerchantId(merchantId.toString());
            }
        }
        paramsMap.put("owner", belongTo);
        paramsMap.put("deviceList", deviceOwnersList);
        DeviceClient.setOwner(JsonUtil.toJson(paramsMap));
    }

    /**
     * 批量导入ac,bas设备
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param projectId 项目id
     * @param roleId 商户id
     * @param lastRowNum 最后一行
     * @param user 用户
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月17日 上午10:40:27
     */
    public void importACBasDev(Sheet sheet, String belongTo, String entityType, Long projectId, Long roleId,
            int lastRowNum,SessionUser user) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        Cell acbasTitle = sheet.getRow(0).getCell(0);
        if(acbasTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        Cell acbas = null;
        Set<String> acbasSet=new HashSet<String>();
        Map<Integer,String> acbasMap=new HashMap<Integer,String>();
        DeviceOwner deviceOwner=null;
        List<DeviceOwner> deviceOwnersList=new ArrayList<DeviceOwner>();
        Set<String> userNamesSet=new HashSet<String>();
        Map<String,Long> userNamesMap=null;
        Map<String, List<Map<String, Object>>> industryMap = IndustryClient.getIndustryMap();//一次性获取全部行业信息
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            acbas = row.getCell(0);//设备mac
            //非空校验
            if(acbas == null){
                Object[] args = {i+1,acbasTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            String acbasValue = acbas.getStringCellValue();
           
            //重复性校验
            acbasSet.add(acbasValue);
            acbasMap.put(i, acbasValue);
            if(acbasSet.size() != i){
                for(Entry<Integer, String> maps : acbasMap.entrySet()){
                    if(maps.getValue().equals(acbasValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            Cell merchantName=sheet.getRow(i).getCell(1);
            Cell merchantNameTitle=sheet.getRow(0).getCell(1);
            if(merchantName==null){
                Object[] args = {i+1,merchantNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            if(!RegexUtil.match(merchantName.getStringCellValue(), RegexConstants.MERCHANT_NAME_PATTERN)){
                Object[] args = {i+1,merchantName.getStringCellValue(),RegexConstants.MERCHANT_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            
            Cell userName=sheet.getRow(i).getCell(2);
            Cell userNameTitle=sheet.getRow(0).getCell(2);
            if(userName==null){
                Object[] args = {i+1,userNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            userName.setCellType(Cell.CELL_TYPE_STRING);
            if(!RegexUtil.match(userName.getStringCellValue(), RegexConstants.USER_NAME_PATTERN)){
                Object[] args = {i+1,userName.getStringCellValue(),RegexConstants.USER_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            userNamesSet.add(userName.getStringCellValue());

            Cell priIndustry=sheet.getRow(i).getCell(3);
            Cell priIndustryTitle=sheet.getRow(0).getCell(3);
            Map<String,Object> priIndustryMap = this.getIndustry(priIndustry.getStringCellValue(), industryMap, "1");
            if(priIndustryMap == null){
                Object[] message={(i+1),priIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            String priIndustryId = (String)priIndustryMap.get("industryId");
            Cell secIndustry=sheet.getRow(i).getCell(4);
            Cell secIndustryTitle=sheet.getRow(0).getCell(4);
            Map<String,Object> secIndustryMap = this.getIndustry(secIndustry.getStringCellValue(), industryMap, "2");
            if(secIndustryMap == null){
                Object[] message={(i+1),secIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            //判断是否是这个一级行业下的二级行业
            String secIndustryId = (String)secIndustryMap.get("industryId");
            boolean isChild = StringUtils.contains(secIndustryId,priIndustryId);
            if(!isChild){
                Object[] message={(i+1),"二级行业"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            
            Cell province=sheet.getRow(i).getCell(7);
            Cell provinceTitle=sheet.getRow(0).getCell(7);
            if(province==null){
                Object[] args = {i+1,provinceTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell city=sheet.getRow(i).getCell(8);
            Cell cityTitle=sheet.getRow(0).getCell(8);
            if(city==null){
                Object[] args = {i+1,cityTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell country=sheet.getRow(i).getCell(9);
            Cell countryTitle=sheet.getRow(0).getCell(9);
            if(country==null){
                Object[] args = {i+1,countryTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell merchantAddress=sheet.getRow(i).getCell(10);
            Cell merchantAddressTitle=sheet.getRow(0).getCell(10);
            if(merchantAddress==null){
                Object[] args = {i+1,merchantAddressTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            merchantAddress.setCellType(Cell.CELL_TYPE_STRING);
            Map<String, List<Map<String, Object>>> locationMap = LocationClient.getLocationMap();//一次性获取全部地区信息
            Map<String,Object> provinceMap = this.getLocation(province.getStringCellValue(), locationMap, null);//根据省名称获取省id
            if(provinceMap == null){
                Object[] message={(i+1),"省"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long provinceId = (Long)provinceMap.get("id");
            Map<String,Object> cityMap = this.getLocation(city.getStringCellValue(), locationMap, provinceMap);
            if(cityMap == null){
                Object[] message={(i+1),"市"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long cityId = (Long)cityMap.get("id");
            Map<String,Object> areaMap = this.getLocation(country.getStringCellValue(), locationMap, cityMap);
            if(areaMap == null){
                Object[] message={(i+1),"区"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long areaId = (Long)areaMap.get("id");
            deviceOwner = new DeviceOwner();
            deviceOwner.setDeviceType(entityType);
//            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwner.setMerchantAccount(userName.getStringCellValue());
            deviceOwner.setMerchantName(merchantName.getStringCellValue());
            deviceOwner.setFirstIndustry(priIndustryId);
            deviceOwner.setSecondIndustry(secIndustryId);
            deviceOwner.setContacter(sheet.getRow(i).getCell(5).getStringCellValue());
            Cell phone =sheet.getRow(i).getCell(6);
            phone.setCellType(Cell.CELL_TYPE_STRING);
            deviceOwner.setCellPhone(phone.getStringCellValue());
            deviceOwner.setMerchantAddress(merchantAddress.getStringCellValue());
            deviceOwner.setProvinceId(provinceId);
            deviceOwner.setCityId(cityId);
            deviceOwner.setCountryId(areaId);
            deviceOwner.setRoleId(roleId.toString());
            deviceOwner.setDeviceName(acbas.getStringCellValue());
//            Map<String,Object> dbParams=new HashMap<String,Object>();
//            dbParams.put("macAddr",deviceOwner.getMac());//设备物理地址
//            List<Device> deviceList = DeviceClient.getListByParam(JsonUtil.toJson(dbParams));
//            if(deviceList!=null&&!deviceList.isEmpty()){
//                deviceOwner.setDeviceId(deviceList.get(0).getDeviceId());
//            }
            
            deviceOwnersList.add(deviceOwner);
        }
        userNamesMap=toeUserService.getUserNameAndIdByUsernames(userNamesSet);
        for(DeviceOwner owner:deviceOwnersList){
            if(owner.getMerchantAccount()!=null){
                Long merchantId=userNamesMap.get(owner.getMerchantAccount());
                if(merchantId==null){//商户不存在
                    //todo create a merchant
                    merchantId =createaMerchant(owner,belongTo,user);
                }
                owner.setMerchantId(merchantId.toString());
            }
        }
        paramsMap.put("owner", belongTo);
        paramsMap.put("deviceList", deviceOwnersList);
        DeviceClient.setOwner(JsonUtil.toJson(paramsMap));
    }

    /**
     * 批量导入hotarea设备
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param projectId 项目id
     * @param roleId 商户id
     * @param lastRowNum 最后一行
     * @param user 用户
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月17日 上午10:40:27
     */
    public void importHotareaDev(Sheet sheet, String belongTo, String entityType, Long projectId, Long roleId,int lastRowNum,SessionUser user) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();//入参json map
        Cell hotareaTitle = sheet.getRow(0).getCell(0);
        if(hotareaTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        Cell hotareaName = null;
        Set<String> hotareaNameSet=new HashSet<String>();
        Map<Integer,String> hotareaNameMap=new HashMap<Integer,String>();
        DeviceOwner deviceOwner=null;
        List<DeviceOwner> deviceOwnersList=new ArrayList<DeviceOwner>();
        Set<String> userNamesSet=new HashSet<String>();
        Map<String,Long> userNamesMap=null;
        Map<String, List<Map<String, Object>>> industryMap = IndustryClient.getIndustryMap();//一次性获取全部行业信息
        for (int i = 1; i < (lastRowNum + 1); i++) {
            Row row=sheet.getRow(i);
            if(row==null){
                Object[] args = {i};
                throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",args));//第（{0}）行是空行
            }
            hotareaName = row.getCell(0);//设备mac
            //非空校验
            if(hotareaName == null){
                Object[] args = {i+1,hotareaTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            String acbasValue = hotareaName.getStringCellValue();
            //重复性校验
            hotareaNameSet.add(acbasValue);
            hotareaNameMap.put(i, acbasValue);
            if(hotareaNameSet.size() != i){
                for(Entry<Integer, String> maps : hotareaNameMap.entrySet()){
                    if(maps.getValue().equals(acbasValue)){
                        Object[] args = {i+1,maps.getKey()+1};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034",args));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                }
            }
            Cell merchantName=sheet.getRow(i).getCell(1);
            Cell merchantNameTitle=sheet.getRow(0).getCell(1);
            if(merchantName==null){
                Object[] args = {i+1,merchantNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            if(!RegexUtil.match(merchantName.getStringCellValue(), RegexConstants.MERCHANT_NAME_PATTERN)){
                Object[] args = {i+1,merchantName.getStringCellValue(),RegexConstants.MERCHANT_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            
            Cell userName=sheet.getRow(i).getCell(2);
            Cell userNameTitle=sheet.getRow(0).getCell(2);
            if(userName==null){
                Object[] args = {i+1,userNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            userName.setCellType(Cell.CELL_TYPE_STRING);
            if(!RegexUtil.match(userName.getStringCellValue(), RegexConstants.USER_NAME_PATTERN)){
                Object[] args = {i+1,userName.getStringCellValue(),RegexConstants.USER_NAME_PATTERN};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            userNamesSet.add(userName.getStringCellValue());
           
            Cell priIndustry=sheet.getRow(i).getCell(3);
            Cell priIndustryTitle=sheet.getRow(0).getCell(3);
            Map<String,Object> priIndustryMap = this.getIndustry(priIndustry.getStringCellValue(), industryMap, "1");
            if(priIndustryMap == null){
                Object[] message={(i+1),priIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            String priIndustryId = (String)priIndustryMap.get("industryId");
            Cell secIndustry=sheet.getRow(i).getCell(4);
            Cell secIndustryTitle=sheet.getRow(0).getCell(4);
            Map<String,Object> secIndustryMap = this.getIndustry(secIndustry.getStringCellValue(), industryMap, "2");
            if(secIndustryMap == null){
                Object[] message={(i+1),secIndustryTitle.getStringCellValue()};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            //判断是否是这个一级行业下的二级行业
            String secIndustryId = (String)secIndustryMap.get("industryId");
            boolean isChild = StringUtils.contains(secIndustryId,priIndustryId);
            if(!isChild){
                Object[] message={(i+1),"二级行业"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
            }
            
            Cell province=sheet.getRow(i).getCell(7);
            Cell provinceTitle=sheet.getRow(0).getCell(7);
            if(province==null){
                Object[] args = {i+1,provinceTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell city=sheet.getRow(i).getCell(8);
            Cell cityTitle=sheet.getRow(0).getCell(8);
            if(city==null){
                Object[] args = {i+1,cityTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell country=sheet.getRow(i).getCell(9);
            Cell countryTitle=sheet.getRow(0).getCell(9);
            if(country==null){
                Object[] args = {i+1,countryTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            Cell merchantAddress=sheet.getRow(i).getCell(10);
            Cell merchantAddressTitle=sheet.getRow(0).getCell(10);
            if(merchantAddress==null){
                Object[] args = {i+1,merchantAddressTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            merchantAddress.setCellType(Cell.CELL_TYPE_STRING);
            Map<String, List<Map<String, Object>>> locationMap = LocationClient.getLocationMap();//一次性获取全部地区信息
            Map<String,Object> provinceMap = this.getLocation(province.getStringCellValue(), locationMap, null);//根据省名称获取省id
            if(provinceMap == null){
                Object[] message={(i+1),"省"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long provinceId = (Long)provinceMap.get("id");
            Map<String,Object> cityMap = this.getLocation(city.getStringCellValue(), locationMap, provinceMap);
            if(cityMap == null){
                Object[] message={(i+1),"市"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long cityId = (Long)cityMap.get("id");
            Map<String,Object> areaMap = this.getLocation(country.getStringCellValue(), locationMap, cityMap);
            if(areaMap == null){
                Object[] message={(i+1),"区"};
                throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
            }
            Long areaId = (Long)areaMap.get("id");
            deviceOwner = new DeviceOwner();
            deviceOwner.setDeviceType(entityType);
//            deviceOwner.setMerchantId(merchantId.toString());
            deviceOwner.setProjectId(projectId.toString());
            deviceOwner.setMerchantAccount(userName.getStringCellValue());
            deviceOwner.setMerchantName(merchantName.getStringCellValue());
            deviceOwner.setFirstIndustry(priIndustryId);
            deviceOwner.setSecondIndustry(secIndustryId);
            deviceOwner.setContacter(sheet.getRow(i).getCell(5).getStringCellValue());
            Cell phone=sheet.getRow(i).getCell(6);
            phone.setCellType(Cell.CELL_TYPE_STRING);
            deviceOwner.setCellPhone(phone.getStringCellValue());
            deviceOwner.setMerchantAddress(merchantAddress.getStringCellValue());
            deviceOwner.setProvinceId(provinceId);
            deviceOwner.setCityId(cityId);
            deviceOwner.setCountryId(areaId);
            deviceOwner.setRoleId(roleId.toString());
            deviceOwner.setDeviceName(hotareaName.getStringCellValue());
//            Map<String,Object> dbParams=new HashMap<String,Object>();
//            dbParams.put("macAddr",deviceOwner.getMac());//设备物理地址
//            List<Device> deviceList = DeviceClient.getListByParam(JsonUtil.toJson(dbParams));
//            if(deviceList!=null&&!deviceList.isEmpty()){
//                deviceOwner.setDeviceId(deviceList.get(0).getDeviceId());
//            }
            deviceOwnersList.add(deviceOwner);
        }
        userNamesMap=toeUserService.getUserNameAndIdByUsernames(userNamesSet);
        for(DeviceOwner owner:deviceOwnersList){
            if(owner.getMerchantAccount()!=null){
                Long merchantId=userNamesMap.get(owner.getMerchantAccount());
                if(merchantId==null){//商户不存在
                    //todo create a merchant
                    merchantId =createaMerchant(owner,belongTo,user);
                }
                owner.setMerchantId(merchantId.toString());
            }
        }
        paramsMap.put("owner", belongTo);
        paramsMap.put("deviceList", deviceOwnersList);
        DeviceClient.setOwner(JsonUtil.toJson(paramsMap));
    }
}
