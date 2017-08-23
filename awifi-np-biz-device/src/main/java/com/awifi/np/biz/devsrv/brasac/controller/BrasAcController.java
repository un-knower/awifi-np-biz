/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午2:36:38
* 创建作者：范立松
* 文件名称：BrasAcController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.brasac.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.enums.Status;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.enums.SourceType;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.brasac.service.BrasAcService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class BrasAcController extends BaseController {

    /** 设备业务层 */
    @Resource(name = "brasAcService")
    private BrasAcService brasAcService;

    /**
     * BRAS/AC 入库提交审核
     * @param access_token access_token
     * @param request request
     * @param idList 设备主键id列表
     * @return map
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月13日 下午1:12:48
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac", method = RequestMethod.PUT, produces = "application/json")
    public Map submitAudit(@RequestParam(value = "access_token", required = true) String access_token,
            HttpServletRequest request, @RequestBody(required = true) List<String> idList) throws Exception {
        if (idList.size() == 0) {
            throw new ValidException("E2301017", MessageUtil.getMessage("E2301017"));// 设备id不允许为空
        }
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String loginName = sessionUser.getUserName();
        List<JSONObject> msgList = new ArrayList<>();
        // 添加白名单
        brasAcService.addWhiteList((idList), msgList);
        if (msgList.size() != 0) {
            return this.failMsg("E2301026", msgList.toString());// 设备总线添加白名单操作失败
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("ids", idList);
        paramsMap.put("flowSts", FlowSts.finished.getValue());
        paramsMap.put("flowStsBy", loginName);
        brasAcService.batchUpdateFlowSts(paramsMap);
        return this.successMsg();
    }

    /**
     * BRAS/AC 入库查询
     * @param access_token access_token
     * @param params 请求参数
     * @param request request
     * @return 设备信息列表
     * @author 范立松
     * @throws Exception 异常
     * @date 2017年4月13日 下午3:31:29
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasacaudit", method = RequestMethod.GET, produces = "application/json")
    public Map queryBrasAcAuditList(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params, HttpServletRequest request)
            throws Exception {
        Map<String, Object> bodyParam = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(bodyParam.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Integer pageNo = CastUtil.toInteger(bodyParam.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        Integer province = CastUtil.toInteger(bodyParam.get("province"));// 省编号
        Integer city = CastUtil.toInteger(bodyParam.get("city"));// 市编号
        Integer county = CastUtil.toInteger(bodyParam.get("county"));// 区编号
        String entityName = CastUtil.toString(bodyParam.get("entityName"));// 实体设备名称
        String ipAddr = CastUtil.toString(bodyParam.get("ipAddr")); //设备ip地址
        Integer entityType = CastUtil.toInteger(bodyParam.get("entityType"));// 实体类型
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pageSize", pageSize);
        paramsMap.put("pageNum", pageNo);
        paramsMap.put("flowSts", FlowSts.waitReview.getValue());
        paramsMap.put("province", province);
        paramsMap.put("city", city);
        paramsMap.put("county", county);
        paramsMap.put("entityName", entityName);
        paramsMap.put("ipAddr", ipAddr);
        paramsMap.put("status", Status.normal.getValue());// 正常
        if (entityType != null) {
            paramsMap.put("entityType", entityType);
        } else {
            paramsMap.put("entityType", DevType.ac.getValue() + "," + DevType.bras.getValue());
        }
        Page<CenterPubEntity> page = new Page<>(pageNo, pageSize);
        brasAcService.queryBrasAcList(page, paramsMap);
        return this.successMsg(page);
    }

    /**
     * BRAS/AC 入库查询(根据id)
     * @param access_token access_token
     * @param brasacId 设备id
     * @return 设备信息
     * @author 范立松
     * @throws Exception 异常
     * @date 2017年4月14日 下午1:12:59
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac/{brasacId}", method = RequestMethod.GET, produces = "application/json")
    public Map queryBrasAcById(@RequestParam(value = "access_token", required = true) String access_token,
            @PathVariable String brasacId) throws Exception {
        return this.successMsg(brasAcService.queryBrasAcById(brasacId));
    }

    /**
     * BRAS/AC 设备查询
     * @param access_token access_token
     * @param params 请求参数
     * @param request request
     * @return 设备信息列表
     * @author 范立松
     * @throws Exception 异常
     * @date 2017年4月13日 下午3:31:29
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac", method = RequestMethod.GET, produces = "application/json")
    public Map queryBrasAcList(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params, HttpServletRequest request)
            throws Exception {
        Map<String, Object> bodyParam = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(bodyParam.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Integer pageNo = CastUtil.toInteger(bodyParam.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        Integer province = CastUtil.toInteger(bodyParam.get("province"));// 省编号
        Integer city = CastUtil.toInteger(bodyParam.get("city"));// 市编号
        Integer county = CastUtil.toInteger(bodyParam.get("county"));// 区编号
        String entityName = CastUtil.toString(bodyParam.get("entityName"));// 实体设备名称
        String ipAddr = CastUtil.toString(bodyParam.get("ipAddr")); //设备ip地址
        Integer entityType = CastUtil.toInteger(bodyParam.get("entityType"));// 实体类型
        String corporation = CastUtil.toString(bodyParam.get("corporation")); //厂商名称
        String model = CastUtil.toString(bodyParam.get("model")); //型号名称
        String outTypeId = CastUtil.toString(bodyParam.get("outTypeId")); //标签(awifi或ChinaNet)
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pageSize", pageSize);
        paramsMap.put("pageNum", pageNo);
        paramsMap.put("province", province);
        paramsMap.put("city", city);
        paramsMap.put("county", county);
        paramsMap.put("entityName", entityName);
        paramsMap.put("ipAddr", ipAddr);
        paramsMap.put("corporation", corporation);
        paramsMap.put("model", model);
        paramsMap.put("outTypeId", outTypeId);
        paramsMap.put("status", Status.normal.getValue());// 正常
        if (StringUtils.isEmpty(outTypeId) || SourceType.awifi.displayName().equals(outTypeId)) {
            paramsMap.put("flowSts", FlowSts.finished.getValue());
        }
        if (entityType != null) {
            paramsMap.put("entityType", entityType);
        } else {
            paramsMap.put("entityType", DevType.ac.getValue() + "," + DevType.bras.getValue());
        }
        Page<CenterPubEntity> page = new Page<>(pageNo, pageSize);
        brasAcService.queryBrasAcList(page, paramsMap);
        return this.successMsg(page);
    }

    /**
     * 添加bras
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @param request request
     * @return map
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月14日 下午2:20:03
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac/bras", method = RequestMethod.POST, produces = "application/json")
    public Map addBras(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam, HttpServletRequest request) throws Exception {
        Map<String, Object> paramsMap = validBrasParams(bodyParam);
        paramsMap.put("macAddr", CastUtil.toString(bodyParam.get("macAddr")));
        paramsMap.put("readCom", CastUtil.toString(bodyParam.get("readCom")));
        paramsMap.put("writeCom", CastUtil.toString(bodyParam.get("writeCom")));
        paramsMap.put("pinCode", CastUtil.toString(bodyParam.get("isping")));
        paramsMap.put("fixAddr", CastUtil.toString(bodyParam.get("fixAddr")));
        paramsMap.put("remarks", CastUtil.toString(bodyParam.get("remarks")));
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String loginName = sessionUser.getUserName();
        paramsMap.put("createBy", loginName);
        brasAcService.addBras(paramsMap);
        return this.successMsg();
    }

    /**
     * 更新bras
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @param brasId bras设备id
     * @param request request
     * @return map
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:20:46
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac/bras/{brasId}", method = RequestMethod.PUT, produces = "application/json")
    public Map updateBras(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam, @PathVariable String brasId,
            HttpServletRequest request) throws Exception {
        Long nasId = CastUtil.toLong(bodyParam.get("nasId"));// nas过滤主键id
        Map<String, Object> paramsMap = validBrasParams(bodyParam);
        paramsMap.put("macAddr", StringUtils.defaultString(CastUtil.toString(bodyParam.get("macAddr"))));
        paramsMap.put("readCom", StringUtils.defaultString(CastUtil.toString(bodyParam.get("readCom"))));
        paramsMap.put("writeCom", StringUtils.defaultString(CastUtil.toString(bodyParam.get("writeCom"))));
        paramsMap.put("pinCode", StringUtils.defaultString(CastUtil.toString(bodyParam.get("isping"))));
        paramsMap.put("fixAddr", StringUtils.defaultString(CastUtil.toString(bodyParam.get("fixAddr"))));
        paramsMap.put("remarks", StringUtils.defaultString(CastUtil.toString(bodyParam.get("remarks"))));
        paramsMap.put("id", brasId);
        paramsMap.put("nasId", nasId);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String loginName = sessionUser.getUserName();
        paramsMap.put("modifyBy", loginName);
        // 更新bras
        brasAcService.updateBras(paramsMap);
        return this.successMsg();
    }

    /**
     * 添加ac
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @param request request
     * @return map
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:21:10
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac/ac", method = RequestMethod.POST, produces = "application/json")
    public Map addAc(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam, HttpServletRequest request) throws Exception {
        Map<String, Object> paramsMap = validAcParams(bodyParam);
        paramsMap.put("macAddr", CastUtil.toString(bodyParam.get("macAddr")));
        paramsMap.put("readCom", CastUtil.toString(bodyParam.get("readCom")));
        paramsMap.put("writeCom", CastUtil.toString(bodyParam.get("writeCom")));
        paramsMap.put("maxBw", CastUtil.toInteger(bodyParam.get("acmaxbw")));
        paramsMap.put("maxCapc", CastUtil.toInteger(bodyParam.get("acmaxcapc")));
        paramsMap.put("maxDevconn", CastUtil.toInteger(bodyParam.get("acmaxdevconn")));
        paramsMap.put("maxStaconn", CastUtil.toInteger(bodyParam.get("acmaxstaconn")));
        paramsMap.put("pinCode", CastUtil.toString(bodyParam.get("isping")));
        paramsMap.put("fixAddr", CastUtil.toString(bodyParam.get("fixAddr")));
        paramsMap.put("remarks", CastUtil.toString(bodyParam.get("remarks")));
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String loginName = sessionUser.getUserName();
        paramsMap.put("createBy", loginName);
        brasAcService.addAc(paramsMap);
        return this.successMsg();
    }

    /**
     * 更新ac
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @param acId ac设备id
     * @param request request
     * @return map
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:21:22
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac/ac/{acId}", method = RequestMethod.PUT, produces = "application/json")
    public Map updateAc(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam, @PathVariable String acId,
            HttpServletRequest request) throws Exception {
        Long nasId = CastUtil.toLong(bodyParam.get("nasId"));// nas过滤主键id
        Map<String, Object> paramsMap = validAcParams(bodyParam);
        paramsMap.put("macAddr", StringUtils.defaultString(CastUtil.toString(bodyParam.get("macAddr"))));
        paramsMap.put("readCom", StringUtils.defaultString(CastUtil.toString(bodyParam.get("readCom"))));
        paramsMap.put("writeCom", StringUtils.defaultString(CastUtil.toString(bodyParam.get("writeCom"))));
        paramsMap.put("maxBw", ObjectUtils.defaultIfNull(CastUtil.toInteger(bodyParam.get("acmaxbw")), 0));// 为空则默认为0
        paramsMap.put("maxCapc", ObjectUtils.defaultIfNull(CastUtil.toInteger(bodyParam.get("acmaxcapc")), 0));// 为空则默认为0
        paramsMap.put("maxDevconn", ObjectUtils.defaultIfNull(CastUtil.toInteger(bodyParam.get("acmaxdevconn")), 0));// 为空则默认为0
        paramsMap.put("maxStaconn", ObjectUtils.defaultIfNull(CastUtil.toInteger(bodyParam.get("acmaxstaconn")), 0));// 为空则默认为0
        paramsMap.put("pinCode", StringUtils.defaultString(CastUtil.toString(bodyParam.get("isping"))));
        paramsMap.put("fixAddr", StringUtils.defaultString(CastUtil.toString(bodyParam.get("fixAddr"))));
        paramsMap.put("remarks", StringUtils.defaultString(CastUtil.toString(bodyParam.get("remarks"))));
        paramsMap.put("id", acId);
        paramsMap.put("nasId", nasId);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String loginName = sessionUser.getUserName();
        paramsMap.put("modifyBy", loginName);
        // 更新ac
        brasAcService.updateAc(paramsMap);
        return this.successMsg();
    }

    /**
     * BRAS/AC 删除设备
     * @param access_token access_token
     * @param ids 设备id列表
     * @return map
     * @author 范立松
     * @throws Exception 异常
     * @date 2017年4月14日 下午1:12:59
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/brasac", method = RequestMethod.DELETE, produces = "application/json")
    public Map removeBrasAc(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "ids", required = true) String ids) throws Exception {
        String[] idArray = ids.split(",");
        ValidUtil.valid("设备id列表[ids]", idArray, "arrayNotBlank");//数组内部是否存在null
        brasAcService.removeBrasAc(idArray);
        return this.successMsg();
    }

    /**
     * 添加/更新bras时参数校验
     * @param bodyParam 请求参数
     * @return 校验后的参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月28日 下午2:02:40
     */
    public Map<String, Object> validBrasParams(Map<String, Object> bodyParam) throws Exception {
        Integer entityType = CastUtil.toInteger(bodyParam.get("entityType"));// 设备类型,不允许为空
        String entityName = CastUtil.toString(bodyParam.get("entityName"));// 设备名称,不允许为空
        Integer province = CastUtil.toInteger(bodyParam.get("province"));// 省份ID,不允许为空
        Integer city = CastUtil.toInteger(bodyParam.get("city"));// 市ID,不允许为空
        Integer county = CastUtil.toInteger(bodyParam.get("county"));// 区县ID,不允许为空
        String ipAddr = CastUtil.toString(bodyParam.get("ipAddr"));// IP地址,不允许为空
        Integer port = CastUtil.toInteger(bodyParam.get("port"));// 端口,不允许为空
        Integer snmpPort = CastUtil.toInteger(bodyParam.get("snmpPort"));// snmp端口,不允许为空
        String corporation = CastUtil.toString(bodyParam.get("corporation"));// 厂商id,不允许为空
        String model = CastUtil.toString(bodyParam.get("model"));// 型号id,不允许为空
        String ipSectionBegin = CastUtil.toString(bodyParam.get("ipSectionBegin"));// ip前段
        String ipSectionEnd = CastUtil.toString(bodyParam.get("ipSectionEnd"));// ip后段

        // 参数校验
        ValidUtil.valid("设备类型[entityType]", entityType, "required");// 校验设备类型
        ValidUtil.valid("设备名称[entityName]", entityName, "required");// 校验设备名称
        ValidUtil.valid("省份ID[province]", province, "required");// 校验省份ID
        ValidUtil.valid("市ID[city]", city, "required");// 校验市ID
        ValidUtil.valid("区县ID[county]", county, "required");// 校验区县ID
        ValidUtil.valid("IP地址[ipAddr]", ipAddr, "required");// 校验IP地址
        ValidUtil.valid("portal端口[port]", port, "required");// 校验端口
        ValidUtil.valid("snmp端口[snmpPort]", snmpPort, "required");// 校验snmp端口
        ValidUtil.valid("厂商id[corporation]", corporation, "required");// 校验厂商id
        ValidUtil.valid("型号id[model]", model, "required");// 校验型号id

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("entityType", DevType.bras.getValue());
        paramsMap.put("entityName", entityName);
        paramsMap.put("province", province);
        paramsMap.put("city", city);
        paramsMap.put("county", county);
        paramsMap.put("ipAddr", ipAddr);
        paramsMap.put("port", port);
        paramsMap.put("snmpPort", snmpPort);
        paramsMap.put("corporation", corporation);
        paramsMap.put("model", model);
        paramsMap.put("ipSectionBegin", ipSectionBegin);
        paramsMap.put("ipSectionEnd", ipSectionEnd);

        return paramsMap;
    }

    /**
     * 添加/更新ac时参数校验
     * @param bodyParam 请求参数
     * @return 校验后的参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月28日 下午2:02:40
     */
    public Map<String, Object> validAcParams(Map<String, Object> bodyParam) throws Exception {
        Integer entityType = CastUtil.toInteger(bodyParam.get("entityType"));// 设备类型,不允许为空
        String entityName = CastUtil.toString(bodyParam.get("entityName"));// 设备名称,不允许为空
        Integer province = CastUtil.toInteger(bodyParam.get("province"));// 省份ID,不允许为空
        Integer city = CastUtil.toInteger(bodyParam.get("city"));// 市ID,不允许为空
        Integer county = CastUtil.toInteger(bodyParam.get("county"));// 区县ID,不允许为空
        String ipAddr = CastUtil.toString(bodyParam.get("ipAddr"));// IP地址,不允许为空
        Integer port = CastUtil.toInteger(bodyParam.get("port"));// 端口,不允许为空
        Integer snmpPort = CastUtil.toInteger(bodyParam.get("snmpPort"));// snmp端口,不允许为空
        String corporation = CastUtil.toString(bodyParam.get("corporation"));// 厂商id,不允许为空
        String model = CastUtil.toString(bodyParam.get("model"));// 型号id,不允许为空
        String ipSectionBegin = CastUtil.toString(bodyParam.get("ipSectionBegin"));// ip前段
        String ipSectionEnd = CastUtil.toString(bodyParam.get("ipSectionEnd"));// ip后段

        // 参数校验
        ValidUtil.valid("设备类型[entityType]", entityType, "required");// 校验设备类型
        ValidUtil.valid("设备名称[entityName]", entityName, "required");// 校验设备名称
        ValidUtil.valid("省份ID[province]", province, "required");// 校验省份ID
        ValidUtil.valid("市ID[city]", city, "required");// 校验市ID
        ValidUtil.valid("区县ID[county]", county, "required");// 校验区县ID
        ValidUtil.valid("IP地址[ipAddr]", ipAddr, "required");// 校验IP地址
        ValidUtil.valid("portal端口[port]", port, "required");// 校验端口
        ValidUtil.valid("snmp端口[snmpPort]", snmpPort, "required");// 校验snmp端口
        ValidUtil.valid("厂商id[corporation]", corporation, "required");// 校验厂商id
        ValidUtil.valid("型号id[model]", model, "required");// 校验型号id

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("entityType", DevType.ac.getValue());
        paramsMap.put("entityName", entityName);
        paramsMap.put("province", province);
        paramsMap.put("city", city);
        paramsMap.put("county", county);
        paramsMap.put("ipAddr", ipAddr);
        paramsMap.put("port", port);
        paramsMap.put("snmpPort", snmpPort);
        paramsMap.put("corporation", corporation);
        paramsMap.put("model", model);
        paramsMap.put("ipSectionBegin", ipSectionBegin);
        paramsMap.put("ipSectionEnd", ipSectionEnd);

        return paramsMap;
    }

}
