/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午4:59:33
* 创建作者：范立松
* 文件名称：ComboController.java
* 版本：  v1.0
* 功能：套餐配置管理
* 修改记录：
*/
package com.awifi.np.biz.devsrv.combo.controller;

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
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.combo.service.ComboService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class ComboController extends BaseController {

    /** 套餐配置业务层 */
    @Resource(name = "comboService")
    private ComboService comboService;

    /**
     * 查询所有套餐
     * @param access_token access_token
     * @return 套餐页面
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 上午11:07:12
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/combo", method = RequestMethod.GET, produces = "application/json")
    public Map getComboList(@RequestParam(value = "access_token", required = true) String access_token)
            throws Exception {
        Page<Map<String, Object>> page = new Page<>();
        comboService.getComboList(page);
        return this.successMsg(page);
    }

    /**
     * 添加套餐信息
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:16:26
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/combo", method = RequestMethod.POST, produces = "application/json")
    public Map addCombo(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        Long downBand = CastUtil.toLong(bodyParam.get("downBand"));
        Integer onlineTimeout = CastUtil.toInteger(bodyParam.get("onlineTimeout"));
        ValidUtil.valid("带宽[downBand]", downBand, "required");//校验带宽
        ValidUtil.valid("在线时长[onlineTimeout]", onlineTimeout, "required");//校验在线时长
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("downBand", downBand);
        paramsMap.put("onlineTimeout", onlineTimeout);
        comboService.addCombo(paramsMap);
        return this.successMsg();
    }

    /**
     * 删除套餐信息
     * @param access_token access_token
     * @param comboId 套餐id
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:16:26
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/combo/{comboId}", method = RequestMethod.DELETE, produces = "application/json")
    public Map removeCombo(@RequestParam(value = "access_token", required = true) String access_token,
            @PathVariable String comboId) throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", comboId);
        comboService.removeCombo(paramsMap);
        return this.successMsg();
    }

    /**
     * 分页查询套餐与商户关系列表
     * @param access_token access_token
     * @param params 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:16:26
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/combo/manage", method = RequestMethod.GET, produces = "application/json")
    public Map getComboManageList(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params) throws Exception {
        Map<String, Object> bodyParam = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Long accountId = CastUtil.toLong(bodyParam.get("accountId"));
        Integer pageSize = CastUtil.toInteger(bodyParam.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        Integer pageNo = CastUtil.toInteger(bodyParam.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Map<String, Object> paramsMap = new HashMap<>();
        if (accountId != null) {
            paramsMap.put("accountId", accountId);
        }
        paramsMap.put("pageSize", pageSize);
        paramsMap.put("pageNum", pageNo);
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        comboService.getComboManageList(page, paramsMap);
        return this.successMsg(page);
    }

    /**
     * 添加套餐配置信息
     * @param access_token access_token
     * @param bodyParam 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:16:26
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/combo/manage", method = RequestMethod.POST, produces = "application/json")
    public Map addComboManage(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        Long accountId = CastUtil.toLong(bodyParam.get("accountId"));
        Long packageId = CastUtil.toLong(bodyParam.get("packageId"));
        String expiredDate = CastUtil.toString(bodyParam.get("expiredDate"));
        String remarks = CastUtil.toString(bodyParam.get("remarks"));
        ValidUtil.valid("商户id[accountId]", accountId, "required");// 校验商户id
        ValidUtil.valid("套餐id[packageId]", packageId, "required");// 校验套餐id
        ValidUtil.valid("截止时间[expiredDate]", expiredDate, "required");// 校验截止时间
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("accountId", accountId);
        paramsMap.put("packageId", packageId);
        paramsMap.put("expiredDate", expiredDate);
        if (StringUtils.isNotBlank(remarks)) {
            paramsMap.put("remarks", remarks);
        }
        comboService.addComboManage(paramsMap);
        return this.successMsg();
    }

    /**
     * 删除套餐配置信息
     * @param access_token access_token
     * @param ids 套餐配置商户id列表
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:16:26
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/combo/manage", method = RequestMethod.DELETE, produces = "application/json")
    public Map removeComboManage(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "ids", required = true) String ids) throws Exception {
        String[] idArray = ids.split(",");
        ValidUtil.valid("商户id列表[ids]", idArray, "arrayNotBlank");//数组内部是否存在null
        comboService.removeComboManage(idArray);
        return this.successMsg();
    }

    /**
     * 修改套餐配置信息
     * @param access_token access_token
     * @param id 套餐配置主键id
     * @param bodyParam 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年5月5日 下午3:19:56
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/combo/manage/{id}", method = RequestMethod.PUT, produces = "application/json")
    public Map updateComboManage(@RequestParam(value = "access_token", required = true) String access_token,
            @PathVariable String id, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        String expiredDate = CastUtil.toString(bodyParam.get("expiredDate"));
        String remarks = CastUtil.toString(bodyParam.get("remarks"));
        ValidUtil.valid("截止时间[expiredDate]", expiredDate, "required");// 校验截止时间
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", id);
        paramsMap.put("expiredDate", expiredDate);
        paramsMap.put("remarks", StringUtils.defaultString(remarks));
        comboService.continueComboManage(paramsMap);
        return this.successMsg();
    }

}
