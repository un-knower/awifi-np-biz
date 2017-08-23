/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午4:26:23
* 创建作者：范立松
* 文件名称：BrasacServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.brasac.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.enums.SourceType;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.devsrv.brasac.service.BrasAcService;

@Service("brasAcService")
public class BrasAcServiceImpl implements BrasAcService {

    /**
     * 更新设备状态
     * 
     * @author 范立松
     * @date 2017年4月12日 下午5:30:01
     */
    @Override
    public void batchUpdateFlowSts(Map<String, Object> paramsMap) throws Exception {
        DeviceClient.updateFlowStsByIds(paramsMap);
    }

    /**
     * 分页查询设备列表
     * @author 范立松
     * @date 2017年4月14日 下午1:02:44
     */
    @Override
    public void queryBrasAcList(Page<CenterPubEntity> page, Map<String, Object> paramsMap) throws Exception {
        // 符合条件的总数
        int count = DeviceQueryClient.queryEntityCountByParam(paramsMap);
        page.setTotalRecord(count);// page置总条数
        if (count > 0) {
            //得到具体数据
            List<CenterPubEntity> records = DeviceQueryClient.queryEntityInfoListByParam(paramsMap);
            page.setRecords(records);
        }
    }

    /**
     * 根据设备id查询
     * @author 范立松
     * @date 2017年4月14日 下午1:02:48
     */
    @Override
    public CenterPubEntity queryBrasAcById(String id) throws Exception {
        CenterPubEntity entity = DeviceQueryClient.queryEntityInfoById(id);
        if (entity != null) {
            if (entity.getMaxBw() != null && entity.getMaxBw() == 0L) {
                entity.setMaxBw(null);// 为0则默认为空
            }
            if (entity.getMaxCapc() != null && entity.getMaxCapc() == 0L) {
                entity.setMaxCapc(null);// 为0则默认为空
            }
            if (entity.getMaxDevconn() != null && entity.getMaxDevconn() == 0L) {
                entity.setMaxDevconn(null);// 为0则默认为空
            }
            if (entity.getMaxStaconn() != null && entity.getMaxStaconn() == 0L) {
                entity.setMaxStaconn(null);// 为0则默认为空
            }
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("nasIp", entity.getIpAddr());
            paramsMap.put("nasName", entity.getEntityName());
            paramsMap.put("pageNum", 1);
            paramsMap.put("pageSize", 1);
            Map<String, Object> resultMap = getNasFilter(paramsMap);// 查询nasip过滤信息
            if (resultMap != null) {
                entity.setNasId(CastUtil.toLong(resultMap.get("id")));// nas过滤主键id
                entity.setIpSectionBegin(CastUtil.toString(resultMap.get("ipSectionBegin")));// ip前段
                entity.setIpSectionEnd(CastUtil.toString(resultMap.get("ipSectionEnd")));// ip后段
            }
        }
        return entity;
    }

    /**
     * 添加bras设备
     * @author 范立松  
     * @date 2017年4月17日 下午3:23:24
     */
    @Override
    public void addBras(Map<String, Object> paramsMap) throws Exception {
        checkMacExist(CastUtil.toString(paramsMap.get("macAddr")));
        paramsMap.put("flowSts", FlowSts.waitReview.getValue());// 流程状态为待审核
        paramsMap.put("status", Status.normal.getValue());// 状态正常
        paramsMap.put("importer", "EMS");
        CenterPubEntity entity = JsonUtil.fromJson(JsonUtil.toJson(paramsMap), CenterPubEntity.class);
        List<CenterPubEntity> sublist = new ArrayList<>();
        sublist.add(entity);
        DeviceClient.addEntityBatch(sublist);
        Map<String, Object> nasParms = packNasFilterParams(paramsMap);
        if (nasParms != null) {
            addNasFilter(nasParms);// 添加nasIp过滤
        }
    }

    /**
     * 添加ac设备
     * @author 范立松  
     * @date 2017年4月17日 下午3:23:27
     */
    @Override
    public void addAc(Map<String, Object> paramsMap) throws Exception {
        checkMacExist(CastUtil.toString(paramsMap.get("macAddr")));
        paramsMap.put("flowSts", FlowSts.waitReview.getValue());// 流程状态为待审核
        paramsMap.put("status", Status.normal.getValue());// 状态正常
        paramsMap.put("importer", "EMS");
        CenterPubEntity entity = JsonUtil.fromJson(JsonUtil.toJson(paramsMap), CenterPubEntity.class);
        List<CenterPubEntity> sublist = new ArrayList<>();
        sublist.add(entity);
        DeviceClient.addEntityBatch(sublist);
        Map<String, Object> nasParms = packNasFilterParams(paramsMap);
        if (nasParms != null) {
            addNasFilter(nasParms);// 添加nasIp过滤
        }
    }

    /**
     * 更新bras设备
     * @author 范立松  
     * @date 2017年4月17日 下午3:23:29
     */
    @Override
    public void updateBras(Map<String, Object> paramsMap) throws Exception {
        String id = CastUtil.toString(paramsMap.get("id"));
        boolean flag = false;// 更新时添加nasIp过滤的标识位
        List<String> nasIpList = new ArrayList<>();
        nasIpList.add(CastUtil.toString(paramsMap.get("ipAddr")));
        Map<String, Object> nasParms = packNasFilterParams(paramsMap);// nasIp过滤参数信息
        CenterPubEntity entity = DeviceQueryClient.queryEntityInfoById(id);
        if (entity != null) {
            if (FlowSts.finished.getValue() == entity.getFlowSts()) {
                List<JSONObject> msgList = new ArrayList<>();
                // 添加白名单
                addWhiteList(paramsMap, msgList);
                if (msgList.size() != 0) {
                    throw new BizException("E2301026", msgList.toString());// 设备总线添加白名单操作失败
                }
            }
            Map<String, Object> nasMap = new HashMap<>();
            nasMap.put("nasIp", entity.getIpAddr());
            nasMap.put("nasName", entity.getEntityName());
            nasMap.put("pageNum", 1);
            nasMap.put("pageSize", 1);
            Map<String, Object> resultMap = getNasFilter(nasMap);// 查询nasip过滤信息
            if (resultMap == null) {
                flag = true;
            }
        }
        checkEntityInfo(id, paramsMap);
        checkMacExist((String) paramsMap.get("macAddr"));
        paramsMap.put("importer", "EMS");
        DeviceClient.updateEntity(paramsMap);
        if (paramsMap.get("nasId") != null) {
            if (nasParms != null) {
                updateNasFilter(nasParms);// 更新nasIp过滤
            } else {
                removeNasFilter(nasIpList);// 删除nasIp过滤
            }
        } else if (nasParms != null && flag) {
            addNasFilter(nasParms);// 添加nasIp过滤
        }
    }

    /**
     * 更新ac设备
     * @author 范立松  
     * @date 2017年4月17日 下午3:23:33
     */
    @Override
    public void updateAc(Map<String, Object> paramsMap) throws Exception {
        String id = CastUtil.toString(paramsMap.get("id"));
        boolean flag = false;// 更新时添加nasIp过滤的标识位
        List<String> nasIpList = new ArrayList<>();
        nasIpList.add(CastUtil.toString(paramsMap.get("ipAddr")));
        Map<String, Object> nasParms = packNasFilterParams(paramsMap);// nasIp过滤参数信息
        CenterPubEntity entity = DeviceQueryClient.queryEntityInfoById(id);
        if (entity != null) {
            if (FlowSts.finished.getValue() == entity.getFlowSts()) {
                List<JSONObject> msgList = new ArrayList<>();
                // 添加白名单
                addWhiteList(paramsMap, msgList);
                if (msgList.size() != 0) {
                    throw new BizException("E2301026", msgList.toString());// 设备总线添加白名单操作失败
                }
            }
            Map<String, Object> nasMap = new HashMap<>();
            nasMap.put("nasIp", entity.getIpAddr());
            nasMap.put("nasName", entity.getEntityName());
            nasMap.put("pageNum", 1);
            nasMap.put("pageSize", 1);
            Map<String, Object> resultMap = getNasFilter(nasMap);// 查询nasip过滤信息
            if (resultMap == null) {
                flag = true;
            }
        }
        checkEntityInfo(id, paramsMap);
        checkMacExist((String) paramsMap.get("macAddr"));
        paramsMap.put("importer", "EMS");
        DeviceClient.updateEntity(paramsMap);
        if (paramsMap.get("nasId") != null) {
            if (nasParms != null) {
                updateNasFilter(nasParms);// 更新nasIp过滤
            } else {
                removeNasFilter(nasIpList);// 删除nasIp过滤
            }
        } else if (nasParms != null && flag) {
            addNasFilter(nasParms);// 添加nasIp过滤
        }
    }

    /**
     * 添加白名单(提交设备时)
     * @author 范立松  
     * @date 2017年4月17日 下午3:23:35
     */
    @Override
    public void addWhiteList(List<String> idList, List<JSONObject> msgList) throws Exception {
        for (String id : idList) {
            CenterPubEntity entity = DeviceQueryClient.queryEntityInfoById(id);
            createNas(id, entity, msgList);
        }
    }

    /**
     * 添加白名单(修改设备时)
     * @author 范立松  
     * @date 2017年4月17日 下午3:23:35
     */
    @Override
    public void addWhiteList(Map<String, Object> paramsMap, List<JSONObject> msgList) throws Exception {
        CenterPubEntity entity = JSONObject.parseObject(JsonUtil.toJson(paramsMap), CenterPubEntity.class);
        String id = entity.getId();
        createNas(id, entity, msgList);
    }

    /**
     * 向设备总线发送注册Nas设备请求
     * @param id 设备id
     * @param entity 设备实体
     * @param msgList 错误信息列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月5日 下午6:48:35
     */
    public void createNas(String id, CenterPubEntity entity, List<JSONObject> msgList) throws Exception {
        if (entity == null) {
            JSONObject msg = new JSONObject();
            msg.put(id, MessageUtil.getMessage("E2301011"));// 未查询到该id对应的设备记录!
            msgList.add(msg);
            return;
        } else {
            Map<String, Object> param = new HashMap<String, Object>();
            String nasip = entity.getIpAddr();
            String shortname = entity.getEntityName();
            Integer type = entity.getEntityType();
            if (StringUtils.isEmpty(nasip)) {
                JSONObject msg = new JSONObject();
                msg.put(entity.getId(), MessageUtil.getMessage("E2301023"));// 设备ip不允许为空
                msgList.add(msg);
                return;
            }
            if (StringUtils.isEmpty(shortname)) {
                JSONObject msg = new JSONObject();
                msg.put(entity.getId(), MessageUtil.getMessage("E2301024"));// 设备名称不允许为空
                msgList.add(msg);
                return;
            }
            if (type == null) {
                JSONObject msg = new JSONObject();
                msg.put(entity.getId(), MessageUtil.getMessage("E2301025"));// 设备类型不允许为空!
                msgList.add(msg);
                return;
            }
            if (type == DevType.bras.getValue()) {
                param.put("type", DevType.bras.displayName());
            }
            if (type == DevType.ac.getValue()) {
                param.put("type", DevType.ac.displayName());
            }
            param.put("nasip", entity.getIpAddr());
            param.put("platform", "ems");
            param.put("shortname", shortname);
            param.put("owner", "awifi");
            param.put("secret", "ct10000");
            String url = SysConfigUtil.getParamValue("devicebus_regradiusnas_url");// 获取注册NAS设备到FreeRadius的接口地址
            String params = "data=" + JsonUtil.toJson(param);
            String result = HttpRequest.sendPost(url, params);
            System.out.println(result);
            boolean flag = false;
            if (StringUtils.isNotBlank(result)) {
                JSONObject jObject = JSONObject.parseObject(result);
                if (jObject.getString("resultCode").equals("000")) {
                    flag = true;
                }
            }
            if (!flag) {
                JSONObject msg = new JSONObject();
                msg.put(entity.getEntityName(), MessageUtil.getMessage("E2301026"));// 设备总线添加白名单操作失败
                msgList.add(msg);
            }
        }
    }

    /**
     * 检查mac地址是否已存在
     * @param mac mac地址
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月24日 下午8:01:36
     */
    public void checkMacExist(String mac) throws Exception {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("status", Status.normal.getValue());
        queryMap.put("outTypeId", SourceType.awifi.displayName());
        if (StringUtils.isNotBlank(mac)) {
            queryMap.put("macAddr", mac);
            int total = DeviceQueryClient.queryEntityCountByParam(queryMap);
            if (total > 0) {
                throw new BizException("E2301011", MessageUtil.getMessage("E2301011"));// MAC地址已经存在
            }
        }
    }

    /**
     * 根据设备id删除设备
     * @author 范立松  
     * @date 2017年5月11日 下午4:08:38
     */
    @Override
    public void removeBrasAc(String[] ids) throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        List<String> nasIpList = new ArrayList<>();
        for (String id : ids) {
            String ipAddr = checkEntityInfo(id, msgList);
            nasIpList.add(ipAddr);
        }
        if (msgList.size() > 0) {
            throw new BizException("E2301015", msgList.toString());// 存在不能删除的设备
        }
        DeviceClient.deleteEntityByIds(ids);// 删除设备
        removeNasFilter(nasIpList);// 删除nasIp过滤
    }

    /**
     * 检查设备是否满足操作条件
     * @param id 设备id
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年5月12日 下午5:00:30
     */
    public void checkEntityInfo(String id, Map<String, Object> paramsMap) throws Exception {
        CenterPubEntity entity = DeviceQueryClient.queryEntityInfoById(id);
        if (entity == null) {
            throw new BizException("E2301020", MessageUtil.getMessage("E2301020"));// 未查询到该id对应的设备记录
        }
        if (!SourceType.awifi.displayName().equals(entity.getOutTypeId())) {
            throw new BizException("E2301012", MessageUtil.getMessage("E2301012"));// 设备操作仅支持aWiFi平台设备
        }
        if (paramsMap != null) {
            if (paramsMap.get("entityName").equals(entity.getEntityName())) {
                paramsMap.remove("entityName");
            }
            if (paramsMap.get("ipAddr").equals(entity.getIpAddr())) {
                paramsMap.remove("ipAddr");
            }
            if (paramsMap.get("macAddr") != null && paramsMap.get("macAddr").equals(entity.getMacAddr())) {
                paramsMap.remove("macAddr");
            }
        }
    }

    /**
     * 检查设备是否满足操作条件
     * @param id 设备id
     * @param msgList 错误信息列表
     * @return 设备ip地址
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年5月12日 下午5:00:30
     */
    public String checkEntityInfo(String id, List<JSONObject> msgList) throws Exception {
        CenterPubEntity entity = DeviceQueryClient.queryEntityInfoById(id);
        if (entity == null) {
            JSONObject msg = new JSONObject();
            msg.put(id, MessageUtil.getMessage("E2301020"));// 未查询到该id对应的设备记录
            msgList.add(msg);
            return null;
        }
        if (!SourceType.awifi.displayName().equals(entity.getOutTypeId())) {
            JSONObject msg = new JSONObject();
            msg.put(entity.getEntityName(), MessageUtil.getMessage("E2301012"));// 设备操作仅支持aWiFi平台设备
            msgList.add(msg);
            return null;
        }
        Long merchantId = entity.getMerchantId();
        if (merchantId != null && merchantId != 0L) {
            JSONObject msg = new JSONObject();
            msg.put(entity.getEntityName(), MessageUtil.getMessage("E2301014"));// 该设备下有商户被绑定不允许删除
            msgList.add(msg);
            return null;
        }
        return entity.getIpAddr();
    }

    /**查询nasip过滤信息
     * @author 范立松  
     * @date 2017年6月22日 下午3:05:47
     */
    @Override
    public Map<String, Object> getNasFilter(Map<String, Object> paramsMap) throws Exception {
        int totalRecord = DeviceClient.countNasFilterByParam(paramsMap);
        if (totalRecord > 1) {
            throw new BizException("E2301027", MessageUtil.getMessage("E2301027"));// 查询到多条该nasIp对应的过滤记录!
        }
        List<Map<String, Object>> recordList = DeviceClient.getNasFilterList(paramsMap);
        if (recordList != null && recordList.size() > 0) {
            return recordList.get(0);
        }
        return null;
    }

    /**
     * 添加nasip过滤
     * @author 范立松  
     * @date 2017年6月22日 下午3:06:09
     */
    @Override
    public void addNasFilter(Map<String, Object> paramsMap) throws Exception {
        DeviceClient.addNasFilter(paramsMap);
    }

    /**
     * 更新nasip过滤
     * @author 范立松  
     * @date 2017年6月22日 下午3:06:15
     */
    @Override
    public void updateNasFilter(Map<String, Object> paramsMap) throws Exception {
        DeviceClient.updateNasFilter(paramsMap);
    }

    /**
     * 删除nasip过滤
     * @author 范立松  
     * @date 2017年6月22日 下午3:06:26
     */
    @Override
    public void removeNasFilter(List<String> nasIpList) throws Exception {
        DeviceClient.removeNasFilter(nasIpList);
    }

    /**
     * 检查nasIp过滤请求参数
     * @param paramsMap 请求参数
     * @return nasIp过滤请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午3:51:42
     */
    public Map<String, Object> packNasFilterParams(Map<String, Object> paramsMap) throws Exception {
        String ipSectionBegin = CastUtil.toString(paramsMap.get("ipSectionBegin"));// ip前段
        String ipSectionEnd = CastUtil.toString(paramsMap.get("ipSectionEnd"));// ip前段
        if (StringUtils.isNotBlank(ipSectionBegin) && StringUtils.isNotBlank(ipSectionEnd)) {
            if (!RegexUtil.match(ipSectionBegin, RegexConstants.IP_PATTERN)) {
                throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", "ip前段[ipSectionBegin]"));// 校验ip前段合法性
            }
            if (!RegexUtil.match(ipSectionEnd, RegexConstants.IP_PATTERN)) {
                throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", "ip后段[ipSectionEnd]"));// 校验ip后段合法性
            }
            Map<String, Object> params = new HashMap<>();
            params.put("id", paramsMap.get("nasId"));
            params.put("nasIp", paramsMap.get("ipAddr"));
            params.put("nasName", paramsMap.get("entityName"));
            params.put("ipSection", ipSectionBegin + "~" + ipSectionEnd);// 有效ip段
            return params;
        }
        return null;
    }

}
