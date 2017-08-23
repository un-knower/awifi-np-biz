/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午9:44:08
* 创建作者：余红伟
* 文件名称：MerchantStatisticsController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantStatistics;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantStatisticsService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
@APIs(description = "园区统计")
@Controller
@RequestMapping(value = "/timebuysrv/merchant/statistics")
public class MerchantStatisticsController extends BaseController{
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private MerchantStatisticsService merchantStatisticsService;
   
    /**
     * 根据省市区，查询园区报表，逐级点击查询。
     * @param request
     * @return
     * @author 余红伟 
     * @date 2017年5月22日 上午9:29:48
     */
    @API(summary = "根据省市区，查询园区报表", parameters = {
            @Param(name = "province", description = "province", dataType = DataType.INTEGER,required = false),
            @Param(name = "city", description = "city", dataType = DataType.INTEGER, required = false),
            @Param(name = "county", description = "county", dataType = DataType.INTEGER, required = false),
            @Param(name = "startTime", description = "startTime", dataType = DataType.STRING, required = false),
            @Param(name = "endTime", description = "endTime", dataType = DataType.STRING, required = false),
            @Param(name = "pageNum", description = "pageNum", dataType = DataType.INTEGER, required = true),
            @Param(name = "pageSize", description = "pageSize",dataType = DataType.INTEGER, required = true)
    })
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map queryList(HttpServletRequest request){
       
        Integer province = CastUtil.toInteger(request.getParameter("province"));
        Integer city = CastUtil.toInteger(request.getParameter("city"));
        Integer county = CastUtil.toInteger(request.getParameter("county"));
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Integer pageNum = CastUtil.toInteger(request.getParameter("pageNum"));
        Integer pageSize = CastUtil.toInteger(request.getParameter("pageSize"));
        
        Map map = new HashMap<>();
        map.put("province", province);
        map.put("city", city);
        map.put("county", county);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("pageNum",pageNum);
        map.put("pageSize", pageSize);
        if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        //分页开始记录
        Integer start = (pageNum - 1)*pageSize;
        map.put("start", start);
        logger.debug("params: " + JSON.toJSONString(map));
        List<MerchantStatistics> list = merchantStatisticsService.queryStatistics(map);
        
        //返回结果集
        Map resultMap = new HashMap<>();
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        Integer count = merchantStatisticsService.countByParams(map);
        resultMap.put("totalRecord", count);
        resultMap.put("totalPage", count%pageSize==0?count/pageSize:count/pageSize+1);
        resultMap.put("records",list );
        return this.successMsg(resultMap);
    }
    
    /**
     * 定时任务,统计园区数据
     * @param request
     * @return
     * @author 余红伟 
     * @date 2017年5月11日 下午4:22:43
     */
    @RequestMapping(value = "task", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map timeTask(HttpServletRequest request){
        merchantStatisticsService.statistics();
        return this.successMsg();
    }
}
