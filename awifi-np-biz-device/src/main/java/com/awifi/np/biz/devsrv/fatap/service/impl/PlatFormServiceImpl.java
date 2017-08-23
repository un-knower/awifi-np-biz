package com.awifi.np.biz.devsrv.fatap.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubPlatform;
import com.awifi.np.biz.api.client.dbcenter.platform.client.PlatFormClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.fatap.service.PlatFormBaseService;
import com.awifi.np.biz.devsrv.fatap.util.FatapUtil;

@Service(value = "platFormBaseService")
public class PlatFormServiceImpl implements PlatFormBaseService {

    /**
     * 查询省份平台
     * @param pageNo 页码
     * @param pageSize 记录数
     * @param platformName 平台名称
     * @param sessionUser 用户
     * @return Page
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:06:34
     */
    @Override
    public Page<CenterPubPlatform> listPlatForm(Integer pageNo,
            Integer pageSize, String platformName, SessionUser sessionUser)
            throws Exception {
        // TODO Auto-generated method stub
        Page<CenterPubPlatform> page = new Page<>();
        Map<String, Object> params = new HashMap<>();
        Long provinceId = sessionUser.getProvinceId();
        Long cityId = sessionUser.getCityId();
        Long countyId = sessionUser.getAreaId();
        if (provinceId != null) {
            params.put("province", provinceId);
        }
        if (cityId != null) {
            params.put("city", cityId);
        }
        if (countyId != null) {
            params.put("county", countyId);
        }
        if (StringUtils.isNotBlank(platformName)) {
            params.put("platformName", platformName);
        }
        Integer count = PlatFormClient.queryPlatformCountByParam(params);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRecord(count);
        params.put("pageNum", pageNo);
        params.put("pageSize", pageSize);
        Map<String, Object> re = PlatFormClient.queryPlatformListByParam(params);
        List<CenterPubPlatform> list = JSONArray.parseArray(JsonUtil.toJson(re.get("rs")), CenterPubPlatform.class);
        // 省市区名称转换
        if (list != null) {
            for (CenterPubPlatform cpp : list) {
                FatapUtil.getCenterPubNameFromId(cpp);
                //如果 portalPort，authPort，platformPort 为0 视为空
                if(cpp.getPortalPort()!=null && cpp.getPortalPort().equals(0)){
                    cpp.setPortalPort(null);
                }
                if(cpp.getAuthPort() != null && cpp.getAuthPort().equals(0)){
                    cpp.setAuthPort(null);
                }
                if(cpp.getPlatformPort() != null && cpp.getPlatformPort().equals(0)){
                    cpp.setPlatformPort(null);
                }
            }
        }
        page.setRecords(list);
        return page;
    }

    /**
     * 根据id查询平台详细信息
     * @param id id
     * @return CenterPubPlatform
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月24日 下午9:06:34
     */
    @Override
    public Map<String, Object> queryPlatformById(String id) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        Map<String, Object> get = PlatFormClient.queryPlatformById(param);
        // 判断返回数据是否为空
        String result = "";
        if (get.get("rs") != null) {
            result = get.get("rs").toString();
        } else {
            return this.successMsg(null);
        }
        CenterPubPlatform cpp = JsonUtil.fromJson(result,CenterPubPlatform.class);
        // 省市区转换
        FatapUtil.getCenterPubNameFromId(cpp);
        //如果 portalPort，authPort，platformPort 为0 视为空
        if(cpp.getPortalPort()!=null && cpp.getPortalPort().equals(0)){
            cpp.setPortalPort(null);
        }
        if(cpp.getAuthPort() != null && cpp.getAuthPort().equals(0)){
            cpp.setAuthPort(null);
        }
        if(cpp.getPlatformPort() != null && cpp.getPlatformPort().equals(0)){
            cpp.setPlatformPort(null);
        }
        return this.successMsg(cpp);
    }

    /**
     * 根据id更新平台
     * @param params 参数
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:08:38
     */
    @Override
    public void editPlatForm(Map<String, Object> params) throws Exception {
        check(params);
        //非必填字段为空处理
        params.put("portalIp", ObjectUtils.defaultIfNull(params.get("portalIp"), StringUtils.EMPTY));
        params.put("authIp", ObjectUtils.defaultIfNull(params.get("authIp"), StringUtils.EMPTY));
        params.put("platformIp", ObjectUtils.defaultIfNull(params.get("platformIp"), StringUtils.EMPTY));
        params.put("devBusIp", ObjectUtils.defaultIfNull(params.get("devBusIp"), StringUtils.EMPTY));
        params.put("devBusPort", ObjectUtils.defaultIfNull(params.get("devBusPort"), StringUtils.EMPTY));
        params.put("administrant", ObjectUtils.defaultIfNull(params.get("administrant"), StringUtils.EMPTY));
        params.put("administrantPhone", ObjectUtils.defaultIfNull(params.get("administrantPhone"), StringUtils.EMPTY));
        params.put("portalDomain", ObjectUtils.defaultIfNull(params.get("portalDomain"), StringUtils.EMPTY));
        params.put("authDomain", ObjectUtils.defaultIfNull(params.get("authDomain"), StringUtils.EMPTY));
        params.put("platformDomain", ObjectUtils.defaultIfNull(params.get("platformDomain"), StringUtils.EMPTY));
        params.put("portalPort", ObjectUtils.defaultIfNull(params.get("portalPort"), "0"));
        params.put("authPort", ObjectUtils.defaultIfNull(params.get("authPort"), "0"));
        params.put("platformPort", ObjectUtils.defaultIfNull(params.get("platformPort"), "0"));
        params.put("url", ObjectUtils.defaultIfNull(params.get("url"), StringUtils.EMPTY));
        params.put("remark", ObjectUtils.defaultIfNull(params.get("remark"), StringUtils.EMPTY));
        PlatFormClient.editPlatForm(params);
    }

    /**
     * 添加省分平台
     * @param params 参数
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:09:34
     */
    @Override
    public void addPlatform(Map<String, Object> params) throws Exception {
        // TODO Auto-generated method stub
        check(params);
        PlatFormClient.addPlatForm(params);
    }

    /**
     * 根据id删除省分平台信息
     * @param id id 
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:10:05
     */
    @Override
    public void deletePlatform(Long id) throws Exception {
        // TODO Auto-generated method stub
        PlatFormClient.deletePaltform(id);
    }
    
    /**
     * 返回数据模板
     * @param data 回传数据
     * @return Map
     * @author 伍恰  
     * @date 2017年6月14日 上午10:19:10
     */
    protected Map<String, Object> successMsg(Object data) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("code", "0");
        resultMap.put("data", data);
        return resultMap;
    }
    
    /**
     * 省分平台新增 或 编辑 参数校验
     * @param bodyParam 参数
     * @author 伍恰  
     * @throws Exception 
     * @date 2017年6月16日 上午9:53:26
     */
    private void check(Map<String,Object> bodyParam) throws Exception{
        String portalDomain = CastUtil.toString(bodyParam.get("portalDomain"));//portal服务器域名必填
        String portalPort = CastUtil.toString(bodyParam.get("portalPort"));
        String authDomain = CastUtil.toString(bodyParam.get("authDomain"));//认证服务器必填
        String authPort = CastUtil.toString(bodyParam.get("authPort"));
        String platformDomain = CastUtil.toString(bodyParam.get("platformDomain"));//平台服务器域名必填
        String platformPort = CastUtil.toString(bodyParam.get("platformPort"));
        Integer platformType = CastUtil.toInteger(bodyParam.get("platformType"));
        Integer id = CastUtil.toInteger(bodyParam.get("id"));
        String province = CastUtil.toString(bodyParam.get("province"));
        String devBusIp = CastUtil.toString(bodyParam.get("devBusIp"));
        //如果平台类型为省分需要校验必填
        if(platformType.equals(0)){
            ValidUtil.valid("portal服务器域名[portalDomain]", portalDomain, "{'required':true}");
            ValidUtil.valid("portal服务器端口[portalPort]", portalPort, "{'required':true,'numeric':true}");
            ValidUtil.valid("认证服务器域名[authDomain]", authDomain, "{'required':true}");
            ValidUtil.valid("认证服务器器端口[authPort]", authPort, "{'required':true,'numeric':true}");
            ValidUtil.valid("平台服务器域名[platformDomain]", platformDomain, "{'required':true}");
            ValidUtil.valid("平台服务器端口[platformPort]", platformPort, "{'required':true,'numeric':true}");
            //判断该省是否已存在平台
            Map<String,Object> params = new HashMap<>();
            params.put("province", province);
            Map<String, Object> re = PlatFormClient.queryPlatformListByParam(params);
            List<CenterPubPlatform> list = JSONArray.parseArray(JsonUtil.toJson(re.get("rs")), CenterPubPlatform.class);
            if(list != null && list.size() > 0){
                for(CenterPubPlatform centerPubPlatform : list){
                    if(!centerPubPlatform.getId().equals(id) && Integer.valueOf(centerPubPlatform.getPlatformType()) == 0){
                        throw new ValidException("E2301310", MessageUtil.getMessage("E2301310"));
                    }
                }
            }
        } 
        if (StringUtils.isNotEmpty(devBusIp)){
            if (!FatapUtil.isUrl(devBusIp)) {
                throw new ValidException("E2000016", MessageUtil
                        .getMessage("E2000016", "设备总线服务器地址[devBusIp]"));
            }
        }
        if (StringUtils.isNotBlank(portalDomain)) {
            if (!FatapUtil.isUrl(portalDomain)) {
                throw new ValidException("E2000016", MessageUtil
                        .getMessage("E2000016", "portal服务器域名[portalDomain]"));
            }
        }
        if (StringUtils.isNotBlank(portalPort)) {
            ValidUtil.valid("portal服务器端口[portalPort]", portalPort,
                    "{'numeric':true}");
        }
        if (StringUtils.isNotBlank(authDomain)) {
            if (!FatapUtil.isUrl(authDomain)) {
                throw new ValidException("E2000016", MessageUtil
                        .getMessage("E2000016", "认证服务器域名[authDomain]"));
            }
        }
        if (StringUtils.isNotBlank(authPort)) {
            ValidUtil.valid("认证服务器器端口[authPort]", authPort, "{'numeric':true}");
        }
        if (StringUtils.isNotBlank(platformDomain)) {
            if (!FatapUtil.isUrl(platformDomain)) {
                throw new ValidException("E2000016", MessageUtil
                        .getMessage("E2000016", "平台服务器域名[platformDomain]"));
            }
        }
        if (StringUtils.isNotBlank(platformPort)) {
            ValidUtil.valid("平台服务器端口[platformPort]", platformPort,
                    "{'numeric':true}");
        }
        String administrantPhone = CastUtil.toString(bodyParam.get("administrantPhone"));//管理人联系方式
        if(StringUtils.isNotBlank(administrantPhone)){//不为空正则校验
            ValidUtil.valid("管理人联系方式[administrantPhone]", administrantPhone, "{'regex':'"+RegexConstants.CELLPHONE+"'}");
        }
    }
}
