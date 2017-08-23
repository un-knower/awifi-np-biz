/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 上午9:13:56
* 创建作者：范立松
* 文件名称：FreeAuthController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.freeauth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.freeauth.service.FreeAuthService;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FreeAuthController extends BaseController {

    /** MAC免认证业务层 */
    @Resource(name = "freeAuthService")
    private FreeAuthService freeAuthService;

    /**添加设备区域信息
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:13:49
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth", method = RequestMethod.POST, produces = "application/json")
    public Map addDeviceArea(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        String name = CastUtil.toString(bodyParam.get("name"));// 区域名称
        Long macAuthHour = CastUtil.toLong(bodyParam.get("macAuthHour"));// mac认证有效时间
        Integer refreshKey = CastUtil.toInteger(bodyParam.get("refreshKey"));// 刷新开关,0关,1开
        String remark = CastUtil.toString(bodyParam.get("remark"));// 备注
        String startDate = DateUtil.getNow();// 有效开始时间
        String endDate = DateUtil.yearAdd(startDate, 50);// 有效结束时间
        ValidUtil.valid("区域名称[name]", name, "required");// 校验区域名称
        ValidUtil.valid("有效时间[macAuthHour]", macAuthHour, "{'required':true,'numeric':{'min':0}}");// 校验mac认证有效时间
        ValidUtil.valid("刷新开关[refreshKey]", refreshKey, "{'required':true,'numeric':{'min':0,'max':1}}");// 校验刷新开关
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", name);
        paramsMap.put("macAuthHour", macAuthHour);
        paramsMap.put("refreshKey", refreshKey);
        paramsMap.put("startDate", startDate);
        paramsMap.put("endDate", endDate);
        if (StringUtils.isNotBlank(remark)) {
            paramsMap.put("remark", remark);
        }
        freeAuthService.addDeviceArea(paramsMap);
        return this.successMsg();
    }

    /**修改设备区域信息
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @param deviceAreaId 区域id
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:13:49
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth/{deviceAreaId}", method = RequestMethod.PUT, produces = "application/json")
    public Map updateDeviceArea(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam, @PathVariable String deviceAreaId)
            throws Exception {
        String name = CastUtil.toString(bodyParam.get("name"));// 区域名称
        Long macAuthHour = CastUtil.toLong(bodyParam.get("macAuthHour"));// mac认证有效时间
        Integer refreshKey = CastUtil.toInteger(bodyParam.get("refreshKey"));// 刷新开关,0关,1开
        String remark = CastUtil.toString(bodyParam.get("remark"));// 备注
        String startDate = DateUtil.getNow();// 有效开始时间
        String endDate = DateUtil.yearAdd(startDate, 50);// 有效结束时间
        ValidUtil.valid("区域名称[name]", name, "required");// 校验区域名称
        ValidUtil.valid("有效时间[macAuthHour]", macAuthHour, "{'required':true,'numeric':{'min':0}}");// 校验mac认证有效时间
        ValidUtil.valid("刷新开关[refreshKey]", refreshKey, "{'required':true,'numeric':{'min':0,'max':1}}");// 校验刷新开关
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", deviceAreaId);
        paramsMap.put("name", name);
        paramsMap.put("macAuthHour", macAuthHour);
        paramsMap.put("refreshKey", refreshKey);
        paramsMap.put("startDate", startDate);
        paramsMap.put("endDate", endDate);
        paramsMap.put("remark", StringUtils.defaultString(remark));
        freeAuthService.updateDeviceArea(paramsMap);
        return this.successMsg();
    }

    /**分页查询设备区域列表
     * @param access_token access_token
     * @param params 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:13:49
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth", method = RequestMethod.GET, produces = "application/json")
    public Map getDeviceAreaList(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params) throws Exception {
        Map<String, Object> bodyParam = JsonUtil.fromJson(params, Map.class);// 请求参数 json格式
        Integer pageSize = CastUtil.toInteger(bodyParam.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        Integer pageNo = CastUtil.toInteger(bodyParam.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Map<String, Object> paramsMap = new HashMap<>();
        String name = CastUtil.toString(bodyParam.get("name"));// 区域名称
        String deviceName = CastUtil.toString(bodyParam.get("deviceName"));// 设备名称
        if (StringUtils.isNotBlank(name)) {
            paramsMap.put("name", name);
        }
        if (StringUtils.isNotBlank(deviceName)) {
            paramsMap.put("deviceName", deviceName);
        }
        paramsMap.put("pageSize", pageSize);
        paramsMap.put("pageNum", pageNo);
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        freeAuthService.getDeviceAreaList(page, paramsMap);
        return this.successMsg(page);
    }

    /**根据区域id删除设备区域信息以及设备与区域关联关系
     * @param access_token access_token
     * @param ids 区域id列表
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午4:27:45
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth", method = RequestMethod.DELETE, produces = "application/json")
    public Map removeDeviceAreaById(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "ids", required = true) String ids) throws Exception {
        String[] idArray = ids.split(",");
        ValidUtil.valid("区域id列表[ids]", idArray, "arrayNotBlank");//数组内部是否存在null
        freeAuthService.removeDeviceAreaById(idArray);
        return this.successMsg();
    }

    /**批量添加设备与区域关联信息
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:13:49
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth/rel", method = RequestMethod.POST, produces = "application/json")
    public Map addDeviceAreaRel(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) List<Map<String, Object>> bodyParam) throws Exception {
        for (Map<String, Object> infoMap : bodyParam) {
            String deviceAreaId = CastUtil.toString(infoMap.get("deviceAreaId"));// 区域id
            String deviceId = CastUtil.toString(infoMap.get("deviceId"));// 设备id
            ValidUtil.valid("区域id[deviceAreaId]", deviceAreaId, "required");// 校验区域id
            ValidUtil.valid("设备id[deviceId]", deviceId, "required");// 校验设备id
        }
        freeAuthService.addDeviceAreaRel(bodyParam);
        return this.successMsg();
    }

    /**
     * 根据设备id删除区域和设备关系
     * @param access_token access_token
     * @param ids 设备id列表
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月28日 下午4:35:32
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth/rel", method = RequestMethod.DELETE, produces = "application/json")
    public Map removeRelByDevId(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "ids", required = true) String ids) throws Exception {
        String[] idArray = ids.split(",");
        ValidUtil.valid("设备id列表[ids]", idArray, "arrayNotBlank");//数组内部是否存在null
        freeAuthService.removeRelByDevId(idArray);
        return this.successMsg();
    }

    /**
     * 根据区域id查询设备与区域关系
     * @param access_token access_token
     * @param params 请求参数
     * @param deviceAreaId 区域id
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午4:27:45
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth/rel/{deviceAreaId}", method = RequestMethod.GET, produces = "application/json")
    public Map getRelListByAreaId(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params, @PathVariable String deviceAreaId)
            throws Exception {
        Map<String, Object> bodyParam = JsonUtil.fromJson(params, Map.class);// 请求参数 json格式
        Integer pageSize = CastUtil.toInteger(bodyParam.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        Integer pageNo = CastUtil.toInteger(bodyParam.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Map<String, Object> paramsMap = new HashMap<>();
        String mac = CastUtil.toString(bodyParam.get("mac"));// mac信息
        String deviceName = CastUtil.toString(bodyParam.get("deviceName"));// 设备名称
        if (StringUtils.isNotBlank(mac)) {
            paramsMap.put("mac", mac);
        }
        if (StringUtils.isNotBlank(deviceName)) {
            paramsMap.put("deviceName", deviceName);
        }
        paramsMap.put("pageSize", pageSize);
        paramsMap.put("pageNum", pageNo);
        paramsMap.put("deviceAreaId", deviceAreaId);
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        freeAuthService.getRelListByAreaId(page, paramsMap);
        return this.successMsg(page);
    }

    /**
     * 根据区域id查询可选择的设备
     * @param access_token access_token
     * @param params 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午4:27:45
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/freeauth/optionaldevice", method = RequestMethod.GET, produces = "application/json")
    public Map getChoosableDeviceList(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params) throws Exception {
        Map<String, Object> bodyParam = JsonUtil.fromJson(params, Map.class);// 请求参数 json格式
        Integer pageSize = CastUtil.toInteger(bodyParam.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        Integer pageNo = CastUtil.toInteger(bodyParam.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Map<String, Object> paramsMap = new HashMap<>();
        String merchantProjectId = CastUtil.toString(bodyParam.get("merchantProjectId"));// 项目id
        String merchantId = CastUtil.toString(bodyParam.get("merchantId"));// 商户id
        if (StringUtils.isNotBlank(merchantProjectId)) {
            paramsMap.put("merchantProjectId", merchantProjectId);
        }
        if (StringUtils.isNotBlank(merchantId)) {
            paramsMap.put("merchantId", merchantId);
        }
        paramsMap.put("pageSize", pageSize);
        paramsMap.put("pageNum", pageNo);
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        freeAuthService.getChooseableDeviceList(page, paramsMap);
        return this.successMsg(page);
    }

}
