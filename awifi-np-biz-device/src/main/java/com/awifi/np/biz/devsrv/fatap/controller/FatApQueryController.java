package com.awifi.np.biz.devsrv.fatap.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.fatap.service.FatApService;

@SuppressWarnings({"rawtypes", "unchecked"})
@RequestMapping("/devsrv")
@Controller
public class FatApQueryController extends BaseController {
    /**
     * 版权所有： 爱WiFi无线运营中心 创建作者：李程程 文件名称：FatApQueryController.java 版本： v1.0 功能：
     * 修改记录：
     */

    @Resource(name = "fatApService")
    private FatApService fatApService;

    /**
     * 定制终端入库信息查询
     * @param access_token token
     * @param params 参数
     * @param request 请求
     * @return map
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午3:43:38
     */
    @ResponseBody
    @RequestMapping(value = "/devices/fatap", method = RequestMethod.GET)
    public Map getEmsDevBaseImpList(@RequestParam(value = "access_token", required = true) String access_token,@RequestParam(value = "params", required = true) String params,HttpServletRequest request) throws Exception {
        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        CenterPubEntity entity = new CenterPubEntity();
        getEntity(entity, map);
        Page page = new Page();
        Integer pageNo = CastUtil.toInteger(map.get("pageNo"));
        if (pageNo == null) {
            pageNo = 1;// 页码默认为1
        }
        Integer pageSize = CastUtil.toInteger(map.get("pageSize"));
        if (pageSize == null) {
            pageSize = 10;// 记录数默认为10
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        SessionUser user = SessionUtil.getCurSessionUser(request);
        String createDateB = (String) map.get("createDateB");
        String createDateE = (String) map.get("createDateE");
        fatApService.getEmsDevBaseFatShowList(entity, createDateB,
                createDateE, page, user);
        return this.successMsg(page);
    }

    /**
     * 根据id获得具体设备信息
     * 
     * @param access_token
     *            token
     * @param id
     *            主键
     * @return map
     * @throws Exception
     *             异常
     */
    @RequestMapping(value = "/devices/fatap/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map getDevBaseById(
            @RequestParam(value = "access_token", required = true) String access_token,
            @PathVariable(value = "id") String id) throws Exception {
        ValidUtil.valid("主键id", id, "numeric");
        CenterPubEntity entity = fatApService.queryEntityInfoById(id);
        return this.successMsg(entity);
    }
    /**
     * 定制终端查询功能(查询页面/入库页面详情)
     * 
     * @param access_token 安全令牌
     *            access_token
     * @param params
     *            参数
     * @param request
     *            请求
     * @param response
     *            相应
     * @return map
     * @throws Exception
     *             异常
     */
    @RequestMapping(value = "/devices/fatap/getlist", method = RequestMethod.GET)
    @ResponseBody
    public Map getFatapList(
            @RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params", required = true) String params,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isBlank(params)) {
            map = JsonUtil.fromJson(params, Map.class);
        }
        ValidUtil.valid("页面判断pass", map.get("pass"), "required");
        if (!(map.get("pass") instanceof Boolean)) {
            throw new ValidException("E2000015",
                    MessageUtil.getMessage("E2000015", "页面判断pass"));// 进行校验
        }
        Boolean pass = (Boolean) map.get("pass");
        CenterPubEntity entity = new CenterPubEntity();
        getEntity(entity, map);
        if(pass){
            entity.setFlowSts(FlowSts.reviewGo.getValue());// 仅查询审核通过的
        }
        Page page = new Page();
        Integer pageNo = CastUtil.toInteger(map.get("pageNo"));
        if (pageNo == null) {
            pageNo = 1;// 页码默认为1
        }
        Integer pageSize = CastUtil.toInteger(map.get("pageSize"));
        if (pageSize == null) {
            pageSize = 10;// 记录数默认为10
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        SessionUser user = SessionUtil.getCurSessionUser(request);
        String createDateB = (String) map.get("createDateB");
        String createDateE = (String) map.get("createDateE");
        fatApService.getEmsDevBasePassedFatShowList(entity, createDateB,
                createDateE, page, user);
        return this.successMsg(page);
    }

    /**
     * 定制终端入库页面 审核通过功能(根据批次号审核)
     * @param access_token 安全令牌
     * @param bodyParam 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午3:45:02
     */
    @ResponseBody
    @RequestMapping(value = "/devices/fatap/reviewGoFatAP", method = RequestMethod.PUT, produces = "application/json")
    public Map reviewGoFatAP(
            @RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam,
            HttpServletRequest request) throws Exception {
        String batchNumStr = (String) bodyParam.get("batchNums");
        ValidUtil.valid("批次号batchNums", batchNumStr, "required");
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String flowStsBy = sessionUser.getUserName();
        String remarks = (String) bodyParam.get("remarks");
        String [] batchNumArr = batchNumStr.split(",");
        StringBuffer erro = new StringBuffer();
        for(String batchNum : batchNumArr){
            Map<String, Object> params = new HashMap<>();
            if(StringUtils.isEmpty(remarks)){
                params.put("remarks", "");
            }
            params.put("batchNum", batchNum);
            params.put("flowStsBy", flowStsBy);
            params.put("flowSts", FlowSts.reviewGo.getValue());
            try {
                fatApService.updateFlowStsByBatch(params);
            } catch (Exception e) {
                String msg = "批次号【"+batchNum+"】审核/驳回失败："+e.getMessage()+" ";
                erro.append(msg);
                logger.error(msg);
            }
            
        }
        if(erro.length()>0){
            return this.failMsg("E2301312", erro);
        }
        return this.successMsg();
    }
    
    /**
     * 定制终端入库页面 审核驳回功能
     * @param access_token 安全令牌
     * @param bodyParam 参数
     * @param request 请求
     * @return Map 请求结果
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午3:46:01
     */
    @RequestMapping(value = "/devices/fatap/reviewBackFatap", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map reviewBackFatap(
            @RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParam,
            HttpServletRequest request) throws Exception {
        String batchNumStr = (String) bodyParam.get("batchNums");
        ValidUtil.valid("批次号batchNums", batchNumStr, "required");
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String flowStsBy = sessionUser.getUserName();
        String remarks = (String) bodyParam.get("remarks");
        String [] batchNumArr = batchNumStr.split(",");
        StringBuffer erro = new StringBuffer();
        for(String batchNum : batchNumArr){
            Map<String, Object> params = new HashMap<>();
            if(StringUtils.isEmpty(remarks)){
                params.put("remarks", "");
            }
            params.put("batchNum", batchNum);
            params.put("flowStsBy", flowStsBy);
            params.put("flowSts", FlowSts.reviewBack.getValue());
            try {
                fatApService.updateFlowStsByBatch(params);
            } catch (Exception e) {
                String msg = "批次号【"+batchNum+"】审核/驳回失败："+e.getMessage()+" ";
                erro.append(msg);
                logger.error(msg);
            }
            
        }
        if(erro.length()>0){
            return this.failMsg("E2301312", erro);
        }
        return this.successMsg();
    }

    /**
     * 定制终端信息删除(pass 为true 按ids 删除 ，为false 按 批次号删除) 
     * @param access_token 安全令牌
     * @param params 参数
     * @return Map 请求情况
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午3:47:43
     */
    @ResponseBody
    @RequestMapping(value = "/devices/fatap/deleteAwifiFatAPByIds", method = RequestMethod.DELETE)
    public Map deleteAwifiFatAPByIds(
            @RequestParam(value = "access_token", required = true) String access_token,
            @RequestParam(value = "params") String params) throws Exception {
        Map<String, Object> map = JsonUtil.fromJson(params, Map.class);
        StringBuffer erro = new StringBuffer();
        ValidUtil.valid("删除判断pass", map.get("pass"), "required");
        if (!(map.get("pass") instanceof Boolean)) {
            throw new ValidException("E2000015",
                    MessageUtil.getMessage("E2000015", "删除判断pass"));// 进行校验
        }
        Boolean pass = (Boolean) map.get("pass");
        if (pass) {
            ValidUtil.valid("主键id", map.get("idArr"), "required");// 主键id不能为空
            String idArr = (String) map.get("idArr");
            String[] ids = idArr.split(",");
            if (ids.length > 1) {
                throw new ValidException("E2301304",
                        MessageUtil.getMessage("E2301304"));
            }
            String id = ids[0];
            CenterPubEntity entity = fatApService.queryEntityInfoById(id);
            if (entity == null) {
                return this.successMsg();
            }
            Long merchantId = entity.getMerchantId();
            if (merchantId != null && merchantId != 0L) {
                throw new ValidException("E2301305",
                        MessageUtil.getMessage("E2301305"));// 绑定商户设备不能删除
            }
            fatApService.deleteAwifiFatAPByIds(ids);
        } else {
            String batchNumStr = (String) map.get("batchNums");
            ValidUtil.valid("批次号batchNums", batchNumStr, "required");
            String [] batchNumArr = batchNumStr.split(",");
            for(String batchNum : batchNumArr){
                Map<String, Object> paramsTmp = new HashMap<>();
                paramsTmp.put("batchNum", batchNum);
                try {
                    fatApService.deleteEntityByBatch(paramsTmp);
                } catch (Exception e) {
                    String msg = "批次号【"+batchNum+"】删除失败："+e.getMessage()+" ";
                    erro.append(msg);
                    logger.error(msg);
                }
                
            }
        }
        if(erro.length()>0){
            return this.failMsg("E2301311", erro);
        }
        return this.successMsg();
    }

    /**
     * 定制终端update功能
     * @param access_token 安全令牌
     * @param bodyParams 请求参数
     * @param request 请求
     * @return Map 请求结果
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午3:48:31
     */
    @ResponseBody
    @RequestMapping(value = "/devices/fatap", method = RequestMethod.PUT)
    public Map updateFatapById(
            @RequestParam(value = "access_token", required = true) String access_token,
            @RequestBody(required = true) Map<String, Object> bodyParams,
            HttpServletRequest request) throws Exception {
        ValidUtil.valid("主键id", bodyParams.get("id"),
                "{'required':true,'numeric':true}");
        ValidUtil.valid("厂家", bodyParams.get("corporation"),
                "{'required':true,'numeric':true}");
        ValidUtil.valid("型号", bodyParams.get("model"),
                "{'required':true,'numeric':true}");
        ValidUtil.valid("固件版本号", bodyParams.get("fwVersion"), "{'required':true,'regex':'"+RegexConstants.FATAP_VERSION+"'}");
        ValidUtil.valid("组件版本号", bodyParams.get("cpVersion"), "{'required':true,'regex':'"+RegexConstants.FATAP_VERSION+"'}");
        if (bodyParams.get("provinceId") != null) {
            ValidUtil.valid("省份编码", bodyParams.get("provinceId"), "numeric");
        }
        if (bodyParams.get("cityId") != null) {
            ValidUtil.valid("省份编码", bodyParams.get("provinceId"), "required");
            ValidUtil.valid("市编码", bodyParams.get("cityId"), "numeric");
        }
        if (bodyParams.get("areaId") != null) {
            ValidUtil.valid("省份编码", bodyParams.get("provinceId"), "required");
            ValidUtil.valid("市编码", bodyParams.get("cityId"), "required");
            ValidUtil.valid("区县编码", bodyParams.get("areaId"), "numeric");
        }
        String id = (String) bodyParams.get("id");
        String corporation = (String) bodyParams.get("corporation");// 厂家
        String model = (String) bodyParams.get("model");// 型号
        String fwVersion = (String) bodyParams.get("fwVersion");// 固件版本号
        String cpVersion = (String) bodyParams.get("cpVersion");// 组件版本号
        Integer provinceId = CastUtil.toInteger(bodyParams.get("provinceId"));
        Integer cityId = CastUtil.toInteger(bodyParams.get("cityId"));
        Integer areaId = CastUtil.toInteger(bodyParams.get("areaId"));
        Long modelId = Long.valueOf(model);
        Long corporationId = Long.valueOf(corporation);
        String entityType = CorporationClient.getModelType(modelId,
                corporationId);
        if (StringUtils.isBlank(entityType)) {
            throw new ValidException("E2301301",
                    MessageUtil.getMessage("E2301301"));
        }
        CenterPubEntity entity = new CenterPubEntity();
        entity.setProvince(provinceId);
        entity.setCity(cityId);
        entity.setCounty(areaId);
        entity.setModel(model);
        int type = 0;
        try {
            type = DevType.valueOf(entityType.toLowerCase()).getValue();
        } catch (Exception e) {
            throw new ValidException("E2301307",
                    MessageUtil.getMessage("E2301307", "型号"));
        }
        entity.setEntityType(type);
        entity.setId(id);
        entity.setCorporation(corporation);
        entity.setFwVersion(fwVersion);
        entity.setCpVersion(cpVersion);
        entity.setImporter("EMS");// 发送的平台
        fatApService.updateFatApById(entity);
        return this.successMsg();
    }

    /**
     * 校验前端查询参数
     * 
     * @param entity
     *            实体类
     * @param map
     *            map
     */
    public void getEntity(CenterPubEntity entity, Map<String, Object> map) {
        Object provinceId = map.get("provinceId");
        if (provinceId != null) {
            ValidUtil.valid("省份", provinceId, "numeric");// 数字校验;
            entity.setProvince(CastUtil.toInteger(provinceId));
        }
        Object cityId = map.get("cityId");
        if (cityId != null) {
            ValidUtil.valid("市", cityId, "numeric");// 数字校验;
            entity.setCity(CastUtil.toInteger(cityId));
        }
        Object areaId = map.get("areaId");
        if (areaId != null) {
            ValidUtil.valid("区县", areaId, "numeric");// 数字校验;
            entity.setCounty(CastUtil.toInteger(areaId));
        }
        Object macAddr = map.get("macAddr");
        if (macAddr != null) {
            ValidUtil.valid("mac地址", macAddr, "{'regex':'"+RegexConstants.MAC_NO_CASE_PATTERN+"'}");// mac地址校验
            entity.setMacAddr((String) macAddr);
        }
        Object batchNum = map.get("batchNum");
        if (batchNum != null) {
            entity.setBatchNum((String) batchNum);
        }
        entity.setCorporation((String) map.get("corporation"));

        entity.setModel((String) map.get("model"));
        Object entityType = map.get("entityType");
        if (entityType != null) {
            ValidUtil.valid("型号类型", entityType, "numeric");// 型号类型校验
            entity.setEntityType(CastUtil.toInteger(entityType));
        }
        entity.setFwVersion((String) map.get("fwVersion"));
        entity.setCpVersion((String) map.get("cpVersion"));
        Object flowSts = map.get("flowSts");
        if (flowSts != null) {
            ValidUtil.valid("状态", flowSts, "numeric");
            entity.setFlowSts(CastUtil.toInteger(flowSts));
        }
    }
}
