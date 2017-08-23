/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 下午4:31:05
* 创建作者：尤小平
* 文件名称：TraceController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.trace.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.util.TraceClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.eagleeyesrv.trace.bean.SpanNode;
import com.awifi.np.biz.eagleeyesrv.trace.bean.Trace;
import com.awifi.np.biz.eagleeyesrv.trace.service.TraceService;

@Controller
@RequestMapping("/eagleeyesrv/trace")
public class TraceController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * TraceService
     */
    @Resource
    private TraceService traceService;

    /**
     * 查询所有services.
     * 
     * @return serviceName列表
     * @throws Exception
     * @author 尤小平  
     * @date 2017年8月14日 下午2:12:49
     */
    @RequestMapping(value = "/getAllServiceNames", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllServiceNames() throws Exception {
        List<String> serviceNameList = TraceClient.getServiceNames();
        logger.debug("serviceNameList:" + JsonUtil.toJson(serviceNameList));

        return this.successMsg(serviceNameList);
    }

    /**
     * 根据serviceName查询所有span名称.
     * 
     * @param serviceName serviceName
     * @return 指定service下所有span名称列表
     * @throws Exception
     * @author 尤小平  
     * @date 2017年8月14日 下午2:14:24
     */
    @RequestMapping(value = "/span/{serviceName}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSpanNamesByServiceName(@PathVariable(value = "serviceName", required = true) String serviceName) throws Exception {
        logger.debug("request serviceName=" + serviceName);
        ValidUtil.valid("serviceName", serviceName, "required");

        List<String> spanNameList = TraceClient.getSpanNames(serviceName);
        logger.debug("spanNameList:" + JsonUtil.toJson(spanNameList));

        return this.successMsg(spanNameList);
    }

    /**
     * traceid查询接口, 根据关键字查询trace.
     * 
     * @param params 请求参数
     * @return traces列表
     * @throws Exception
     * @author 尤小平  
     * @date 2017年8月14日 下午2:15:41
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getTraceListByParam(@RequestParam(value="params", required = true)String params) throws Exception {
        logger.debug("请求参数:" + JsonUtil.toJson(params));
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String keyword = MapUtils.getString(paramsMap, "keyword");
        String beginDate = MapUtils.getString(paramsMap, "beginDate");
        String endDate = MapUtils.getString(paramsMap, "endDate");
        Integer pageNum = CastUtil.toInteger(paramsMap.get("pageNo"));
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));

        ValidUtil.valid("beginDate", beginDate, "required");
        ValidUtil.valid("endDate", endDate, "required");

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 15;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageNum", pageNum);
        param.put("pageSize", pageSize);
        if (StringUtils.isNotBlank(keyword)) {
            param.put("keyWordQuery", keyword);
        }
        if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
            long begin = DateUtil.getTimestampMills(beginDate);
            long end = DateUtil.getTimestampMills(endDate);
            param.put("endTs", end * 1000);
            param.put("lookback", (end - begin) * 1000);
        }

        Page<SpanNode> page = new Page(pageNum, pageSize);
        traceService.getTraceListByParam(param, page);
        logger.debug("return page:" + JsonUtil.toJson(page));

        return this.successMsg(page);
    }

    /**
     * 根据traceId查询链路.
     * 
     * @param traceId traceId
     * @return trace链路
     * @throws Exception
     * @author 尤小平  
     * @date 2017年8月14日 下午2:16:52
     */
    @RequestMapping(value = "/{traceId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getTraceById(@PathVariable(value = "traceId", required = true) String traceId) throws Exception {
        logger.debug("request traceId=" + traceId);

        ValidUtil.valid("traceId", traceId, "required");

        Trace trace = traceService.getTrace(traceId);
        logger.debug("return trace:" + JsonUtil.toJson(trace));

        return this.successMsg(trace);
    }
}
