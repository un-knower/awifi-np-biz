/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午2:12:25
* 创建作者：范立松
* 文件名称：IotController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.iot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
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
import com.awifi.np.biz.devsrv.iot.service.IotService;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class IotController extends BaseController {

    /** 物联网设备管理业务层 */
    @Resource(name = "iotService")
    private IotService iotService;

    /**
     * 分页查询物联网设备列表
     * @param access_token access_token
     * @param params 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午14:20:37
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/iot", method = RequestMethod.GET, produces = "application/json")
    public Map getIotList(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params) throws Exception {
        Map<String, Object> bodyParam = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(bodyParam.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        Integer pageNo = CastUtil.toInteger(bodyParam.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Integer province = CastUtil.toInteger(bodyParam.get("province"));// 省编号
        Integer city = CastUtil.toInteger(bodyParam.get("city"));// 市编号
        Integer county = CastUtil.toInteger(bodyParam.get("county"));// 区编号
        String corporation = (String) bodyParam.get("corporation");// 厂商id
        String model = (String) bodyParam.get("model");// 型号id
        String ipAddr = (String) bodyParam.get("ipAddr"); // 设备ip地址
        Integer entityType = CastUtil.toInteger(bodyParam.get("entityType"));// 实体类型
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pageSize", pageSize);
        paramsMap.put("pageNo", pageNo);
        paramsMap.put("province", province);
        paramsMap.put("city", city);
        paramsMap.put("county", county);
        paramsMap.put("corporation", corporation);
        paramsMap.put("model", model);
        paramsMap.put("ipAddr", ipAddr);
        paramsMap.put("entityType", entityType);
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        iotService.getIotList(page, paramsMap);
        return this.successMsg(page);
    }

    /**根据物联网设备id删除设备信息
     * @param access_token access_token
     * @param idList 设备id列表
     * @return map
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午4:27:45
     */
    @ResponseBody
    @RequestMapping(value = "/devsrv/iot", method = RequestMethod.DELETE, produces = "application/json")
    public Map removeIotById(@RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) List<String> idList) throws Exception {
        if (idList.size() == 0) {
            throw new ValidException("E2301017", MessageUtil.getMessage("E2301017"));// 设备id不允许为空
        }
        iotService.removeIotByIds(idList);
        return this.successMsg();
    }

}
