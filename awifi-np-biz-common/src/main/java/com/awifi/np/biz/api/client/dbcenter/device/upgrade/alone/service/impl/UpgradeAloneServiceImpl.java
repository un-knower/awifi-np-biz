package com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.service.UpgradeAloneService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * Created by youxp on 2017/7/10.
 */
@SuppressWarnings("unchecked")
@Service(value = "upgradeAloneService")
public class UpgradeAloneServiceImpl implements UpgradeAloneService {
    
    /**
     * 查询主界面 -- 个性化任务列表
     * @author 余红伟 
     * @date 2017年7月10日 下午8:41:09
     */
   
    @Override
    public List<Map<String, Object>> getPersonalizedUpgradeTaskList(Map<String, Object> params)throws Exception {
        String mac = MapUtils.getString(params, "mac");
        String merchantId = MapUtils.getString(params, "merchantId");
        String packageName = MapUtils.getString(params, "packageName");
        if(StringUtils.isNotBlank(mac)){
            ValidUtil.valid("mac地址", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
        }
        if(StringUtils.isNotBlank(merchantId)){
            ValidUtil.valid("商户id ", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(merchantId)){
            params.remove("merchantId");
        }
        if(StringUtils.isBlank(mac)){
            params.remove("mac");
        }
        if(StringUtils.isBlank(packageName)){
            params.remove("packageName");
        }
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryTaskListByParam_url");
//        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryDeviceListByParam_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnValue = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String, Object>> returnList = (List<Map<String, Object>>) returnValue.get("rs");
        
        return returnList;
    }
    /**
     * 统计个性化任务总数
     * @author 余红伟 
     * @date 2017年7月10日 下午8:10:21
     */
    @Override
    public Integer getPersonalizedUpgradeTaskCount(Map<String, Object> params) throws Exception {
        String mac = MapUtils.getString(params, "mac");
        String merchantId = MapUtils.getString(params, "merchantId");
        String packageName = MapUtils.getString(params, "packageName");
        if(StringUtils.isNotBlank(mac)){
            ValidUtil.valid("mac地址", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
        }
        if(StringUtils.isNotBlank(merchantId)){
            ValidUtil.valid("商户id ", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(merchantId)){
            params.remove("merchantId");
        }
        if(StringUtils.isBlank(mac)){
            params.remove("mac");
        }
        if(StringUtils.isBlank(packageName)){
            params.remove("packageName");
        }
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryTaskCountByParam_url");
//        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryDeviceCountByParam_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnValue = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        Integer count = (Integer) returnValue.get("rs");
        return count;
    }
    /**
     * 查询个性化任务  根据id查询
     * @author 余红伟 
     * @date 2017年7月13日 下午1:56:49
     */
    public Map<String, Object> getPersonalizedUpgradeTaskById(Long id)throws Exception{
        ValidUtil.valid("个性化任务id", id, "{'required':true,'numeric':true}");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryTaskById_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        Map<String, Object> task = (Map<String, Object>) returnMap.get("rs");
        return task;
    }
    /**
     * 添加任务
     * @author 余红伟 
     * @date 2017年7月11日 上午8:51:56
     */
    @Override
    public void addPersonalizedUpgradeTask(Map<String, Object> params) throws Exception {
        //mac地址和升级包id必填
        Long upgradeId = CastUtil.toLong(params.get("upgradeId"));
        String mac = MapUtils.getString(params, "mac");
        Long userId = CastUtil.toLong(params.get("userId"));
        String userName = MapUtils.getString(params, "userName");
        ValidUtil.valid("升级包id ", upgradeId, "{'required':true,'numeric':true}");
        ValidUtil.valid("mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
        ValidUtil.valid("操作用户id ", userId, "{'required':true,'numeric':true}");
        ValidUtil.valid("操作用户名称 ", userName, "{'required':true}");
        
        //TODO 12312
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneAddTask_url");
        String interfaceParams = JsonUtil.toJson(params);
        CenterHttpRequest.sendPostRequest(url, interfaceParams);
    }
    /**
     * 定制终端升级--个性化升级任务删除 --主界面 删除任务
     * @author 余红伟 
     * @date 2017年7月11日 上午9:48:20
     */
    @Override
    public void deletePersonalizedUpgradeTask(Long id) throws Exception {
        ValidUtil.valid("任务id ", id, "{'required':true,'numeric':true}");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneDeleteTask_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, interfaceParams);
    }
    /**
     * 定制终端升级--个性化升级包查询 根据 id
     * @author 余红伟 
     * @date 2017年7月11日 上午10:24:27
     */
    @Override
    public Map<String, Object> getPersonalizUpgradePatchById(Long id) throws Exception {
        ValidUtil.valid("升级包id", id, "{'required':true,'numeric':true}");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryById_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        Map<String, Object> patch = (Map<String, Object>) returnMap.get("rs");
        return patch;
    }
    /**
     * 根据mac地址查询设备
     * @author 余红伟 
     * @date 2017年7月11日 下午2:17:02
     */
    @Override
    public Map<String, Object> getDeviceByMac(String mac) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("mac", mac);
        String url = SysConfigUtil.getParamValue("");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        Map<String, Object> device = (Map<String, Object>) returnMap.get("rs");
        return device;
    }
    /**
     * 个性化升级包 列表查询
     * @author 余红伟 
     * @date 2017年7月11日 下午2:38:35
     */
    @Override
    public List<Map<String, Object>> getPersonalizUpgradePatchList(Map<String, Object> params) throws Exception {
        String corporationId = MapUtils.getString(params, "corporationId");
        String modelId = MapUtils.getString(params, "modelId");
        String name = MapUtils.getString(params, "name");
        
        if(StringUtils.isNotBlank(corporationId)){
            ValidUtil.valid("厂商: ", corporationId, "{'required':true,'numeric':true}");
            ValidUtil.valid("型号: ", modelId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isNotBlank(modelId)){
            ValidUtil.valid("厂商: ", corporationId, "{'required':true,'numeric':true}");
            ValidUtil.valid("型号: ", modelId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(modelId)){
            params.remove("modelId");
        }
        if(StringUtils.isBlank(corporationId)){
            params.remove("corporationId");
        }
        if(StringUtils.isBlank(name)){
            params.remove("name");
        }
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryListByParam_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnValue = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String, Object>> returnList = (List<Map<String, Object>>) returnValue.get("rs");
        return returnList;
    }
    /**
     * 个性化升级包 统计
     * @author 余红伟 
     * @date 2017年7月11日 下午2:55:22
     */
    @Override
    public Integer getPersonalizUpgradePatchCount(Map<String, Object> params) throws Exception {
        String corporationId = MapUtils.getString(params, "corporationId");
        String modelId = MapUtils.getString(params, "modelId");
        String name = MapUtils.getString(params, "name");
        if(StringUtils.isNotBlank(corporationId)){
            ValidUtil.valid("厂商: ", corporationId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isNotBlank(modelId)){
            ValidUtil.valid("型号: ", modelId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(modelId)){
            params.remove("modelId");
        }
        if(StringUtils.isBlank(corporationId)){
            params.remove("corporationId");
        }
        if(StringUtils.isBlank(name)){
            params.remove("name");
        }
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryCountByParam_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url , interfaceParams);
        Integer count = (Integer) returnMap.get("rs");
        return count;
    }
    /**
     * 个性化升级包 删除
     * @author 余红伟 
     * @date 2017年7月11日 下午2:55:51
     */
    @Override
    public void deletePersonalizedUpgradePatch(Long id) throws Exception {
        ValidUtil.valid("升级包id: ", id, "{'required':true,'numeric':true}");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneDelete_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, interfaceParams);
    }
    /**
     * 个人性化升级包 添加
     * @author 余红伟 
     * @date 2017年7月11日 下午2:56:01
     */
    @Override
    public void addPersonalizedUpgradePatch(Map<String, Object> params) throws Exception {
        ValidUtil.valid("包名称: ", params.get("name"), "{'required':true}");
        String name = MapUtils.getString(params, "name");
        if( name.length() > 30){
            throw new ValidException("E2401006", MessageUtil.getMessage("E2401006", "升级包名称长度最大为30个字符!"));
        }
        ValidUtil.valid("升级类型: ", params.get("type"), "{'required':true}");
        ValidUtil.valid("厂商: ", params.get("corporationId"), "{'required':true,'numeric':true}");
        ValidUtil.valid("型号: ", params.get("modelId"), "{'required':true,'numeric':true}");
        ValidUtil.valid("版本: ", params.get("versions"), "{'required':true,'regex':'^V[0-9]*\\\\.[0-9]+\\\\.[0-9]+$'}");//V1.1.23
        ValidUtil.valid("hd版本: ", params.get("hdVersions"), "{'required':true,'regex':'^[a-zA-Z0-9]{1,20}$'}"); 
        ValidUtil.valid("路径: ", params.get("path"), "{'required':true}");
        ValidUtil.valid("操作用户id ", params.get("userId"), "{'required':true,'numeric':true}");
        ValidUtil.valid("操作用户名称 ", params.get("userName"), "{'required':true}");
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneAdd_url");
        String interfaceParams = JsonUtil.toJson(params);
        CenterHttpRequest.sendPostRequest(url, interfaceParams);
        
    }
    /**
     * 定制终端升级--个性化升级设备查询 -- 添加任务界面
     * @param params 参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:26:36
     */
    @Override
    public List<Map<String, Object>> getUpgradeDeviceList(Map<String, Object> params)throws Exception {
        //=====校验规则：1,厂商型号只能关联商户名称查询，不能单独查询;2,MAC和商户名称必须有一个不为空
        String corporationId = MapUtils.getString(params, "corporationId");
        String modelId = MapUtils.getString(params, "modelId");
        String mac =  MapUtils.getString(params, "mac");
        String merchantId = MapUtils.getString(params, "merchantId");
        if(StringUtils.isNotBlank(corporationId) && StringUtils.isNotBlank(modelId)){
            ValidUtil.valid("使用厂商和型号时商户名称必填[mechantId]", merchantId, "{'required':true}");
        }
        if(StringUtils.isBlank(mac)){
            ValidUtil.valid("mac为空时,商户id", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(merchantId)){
            ValidUtil.valid("商户id为空时,mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
        }
        //mac和商户id都传递的时候，两者都校验
        if(StringUtils.isNotBlank(mac)&&StringUtils.isNotBlank(merchantId)){
            ValidUtil.valid("mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            ValidUtil.valid("商户id", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(mac)){
            params.remove("mac");
        }
        if(StringUtils.isBlank(corporationId)){
            params.remove("corporationId");
        }
        if(StringUtils.isBlank(modelId)){
            params.remove("modelId");
        }
        if(StringUtils.isBlank(merchantId)){
            params.remove("merchantId");
        }
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryDeviceListByParam_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnValue = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String, Object>> returnList = (List<Map<String, Object>>) returnValue.get("rs");
        return returnList;
    }
    /**
     * 查询设备数 -- 添加任务界面  
     * @param params
     * @return
     * @author 余红伟 
     * @date 2017年7月12日 上午9:21:40
     */
    @Override
    public Integer queryDeviceCountByParam(Map<String, Object> params)throws Exception {
        //=====校验规则：1,厂商型号只能关联商户名称查询，不能单独查询;2,MAC和商户名称必须有一个不为空
        String corporationId = MapUtils.getString(params, "corporationId");
        String modelId = MapUtils.getString(params, "modelId");
        String mac =  MapUtils.getString(params, "mac");
        String merchantId = MapUtils.getString(params, "merchantId");
        if(StringUtils.isNotBlank(corporationId) && StringUtils.isNotBlank(modelId)){
            ValidUtil.valid("使用厂商和型号时商户名称必填[mechantId]", merchantId, "{'required':true}");
        }
        if(StringUtils.isBlank(mac)){
            ValidUtil.valid("商户id", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(merchantId)){
            ValidUtil.valid("mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
        }
        //mac和商户id都传递的时候，两者都校验
        if(StringUtils.isNotBlank(mac)&&StringUtils.isNotBlank(merchantId)){
            ValidUtil.valid("mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            ValidUtil.valid("商户id", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(mac)){
            params.remove("mac");
        }
        if(StringUtils.isBlank(corporationId)){
            params.remove("corporationId");
        }
        if(StringUtils.isBlank(modelId)){
            params.remove("modelId");
        }
        if(StringUtils.isBlank(merchantId)){
            params.remove("merchantId");
        }
        String url = SysConfigUtil.getParamValue("dbc_UpgradeAloneQueryDeviceCountByParam_url");
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        Integer count = (Integer) returnMap.get("rs");
        return count;
    }
}
