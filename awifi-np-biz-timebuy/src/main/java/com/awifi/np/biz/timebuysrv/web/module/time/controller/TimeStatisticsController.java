/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 上午9:42:45
* 创建作者：余红伟
* 文件名称：TimeStateController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.merchant.service.PubMerchantService;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserConsumeService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
@APIs(description = "统计清单报表接口")
@Controller
@RequestMapping("/timebuysrv/statistics")
public class TimeStatisticsController extends BaseController{
    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(TimeStatisticsController.class);
    
    @Resource
    private UserConsumeService userConsumeService;
    @Resource()
    private PubMerchantService pubmerchantService;
    
    /**
     * 根据参数分页查询付费清单报表
     * @param request
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年5月2日 上午9:08:44
     */
    @SuppressWarnings("unchecked")
//    @API(summary = "付费清单报表接口", description = "根据手机号和商户Id查询", parameters = {
//            @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "userId", description = "用户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "startTime", description = "开始时间(充值)", dataType = DataType.STRING, required = false),
//            @Param(name = "ednTime", description = "结束时间 2017-06-05", dataType = DataType.STRING, required = false),
//            @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "provinceId", description = "省", dataType = DataType.INTEGER, required = false),
//            @Param(name = "pageNo", description = "页码", dataType = DataType.INTEGER, required = true),
//            @Param(name = "pageSize", description = "每页条数", dataType = DataType.INTEGER, required = true )
//    })
    @RequestMapping(value = "/consume", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String,Object> statisticsConsume(@RequestParam(value = "params", required = true) String params,HttpServletRequest request ) throws Exception{
        logger.debug("付费清单查询 params: " + params);
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
        Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
        Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(paramsMap, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(paramsMap, "cityId"));
        Integer countyId = CastUtil.toInteger(MapUtils.getString(paramsMap, "countyId"));
        String startTime = MapUtils.getString(paramsMap, "startTime");
        String endTime = MapUtils.getString(paramsMap, "endTime");
        Integer pageNum = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageNo"));
        Integer pageSize = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageSize"));
        
//        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        SessionUser sessionUser = new SessionUser();
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = CastUtil.toInteger(sessionProvinceId);
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = CastUtil.toInteger(sessionCityId);
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = CastUtil.toInteger(sessionAreaId);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("telephone", telephone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", countyId);
        map.put("createDateStart", startTime);
        map.put("createDateEnd", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        //付费清单条件
        map.put("consumeType", 3);//套餐类型为3
        if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        List<UserConsume> list = userConsumeService.queryListByParam(map);
        //数据还是暂时调用数据中心接口获取，会有数据匹配不上情况。
//        for(UserConsume consume : list){
//            PubMerchant pubMerchant = pubmerchantService.getMerchantById(consume.getMerchantId());
//            if(pubMerchant != null){
//                consume.setTelephone(pubMerchant.getContactWay());
//                consume.setMerchantName(pubMerchant.getMerchantName());
//            }
//        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        int count = userConsumeService.queryCountByParam(map);
        resultMap.put("totalRecord", count);
        resultMap.put("totalPage", count%pageSize==0?count/pageSize:count/pageSize+1);
        resultMap.put("records",list );
        
        return this.successMsg(resultMap);
        
    }
    /**
     * 分页查询赔付清单报表
     * @param request
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年5月2日 上午9:12:08
     */
    @SuppressWarnings("unchecked")
//    @API(summary = "赔付清单报表接口", description = "根据手机号和商户Id查询", parameters = {
//            @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "userId", description = "用户ID/手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "startTime", description = "开始时间 2017-06-05", dataType = DataType.STRING, required = false),
//            @Param(name = "endTime", description = "结束时间", dataType = DataType.STRING, required = false),
//            @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "pageNum", description = "页码", dataType = DataType.INTEGER, required = true),
//            @Param(name = "pageSize", description = "每页条数", dataType = DataType.INTEGER, required = true )
//    })
    @RequestMapping(value = "/compenstate", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public Map<String,Object> statisticsCompensate(@RequestParam(value = "params", required = true) String params,HttpServletRequest request) throws Exception{
        logger.debug("赔付清单查询 params: " + params);
        
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
        Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
        Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(paramsMap, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(paramsMap, "cityId"));
        Integer countyId = CastUtil.toInteger(MapUtils.getString(paramsMap, "countyId"));
        String startTime = MapUtils.getString(paramsMap, "startTime");
        String endTime = MapUtils.getString(paramsMap, "endTime");
        Integer pageNum = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageNo"));
        Integer pageSize = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageSize"));

        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = CastUtil.toInteger(sessionProvinceId);
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = CastUtil.toInteger(sessionCityId);
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = CastUtil.toInteger(sessionAreaId);
        }
        
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("telephone", telephone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", countyId);
        //对应service获取的map字段名。
        map.put("createDateStart", startTime);
        map.put("createDateEnd", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        //关键参数
        map.put("consumeType", 2);//套餐类型为2,为赔付
        map.put("packageKey", 202);//packageKey为202
        if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        logger.debug("params: "+ JSON.toJSONString(map));
        List<UserConsume> list = userConsumeService.queryListByParam(map);

        logger.debug("赔付清单 ："+JSON.toJSONString(list));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        int count = userConsumeService.queryCountByParam(map);
        resultMap.put("totalRecord", count);
        resultMap.put("totalPage", count%pageSize==0?count/pageSize:count/pageSize+1);
        resultMap.put("records",list );
        return this.successMsg(resultMap);
    }
    
    /**
     * 体验包领取记录
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月10日 下午5:03:50
     */
    @SuppressWarnings("unchecked")
//    @API(summary = "体验包领取记录", description = "根据手机号和商户Id查询", parameters = {
//            @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "userId", description = "用户ID/手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "startTime", description = "开始时间 2017-06-05", dataType = DataType.STRING, required = false),
//            @Param(name = "endTime", description = "结束时间", dataType = DataType.STRING, required = false),
//            @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "pageNum", description = "页码", dataType = DataType.INTEGER, required = true),
//            @Param(name = "pageSize", description = "每页条数", dataType = DataType.INTEGER, required = true )
//    })
    @RequestMapping(value = "/free", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public Map<String,Object> statisticsFree(@RequestParam(value = "params", required = true) String params,HttpServletRequest request) throws Exception{
        logger.debug("免费体验包 params: " + params);
        
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
        Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
        Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(paramsMap, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(paramsMap, "cityId"));
        Integer countyId = CastUtil.toInteger(MapUtils.getString(paramsMap, "countyId"));
        String startTime = MapUtils.getString(paramsMap, "startTime");
        String endTime = MapUtils.getString(paramsMap, "endTime");
        Integer pageNum = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageNo"));
        Integer pageSize = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageSize"));

        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = CastUtil.toInteger(sessionProvinceId);
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = CastUtil.toInteger(sessionCityId);
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = CastUtil.toInteger(sessionAreaId);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("telephone", telephone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", countyId);
        //对应service获取的map字段名。
        map.put("createDateStart", startTime);
        map.put("createDateEnd", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        
        map.put("consumeType", 2);//套餐类型为2
        map.put("packageKey", 201);//packageKey为201
        if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        logger.debug("params: "+ JSON.toJSONString(map));
        List<UserConsume> list = userConsumeService.queryListByParam(map);

        logger.debug("免费体验包记录 ："+JSON.toJSONString(list));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        int count = userConsumeService.queryCountByParam(map);
        resultMap.put("totalRecord", count);
        resultMap.put("totalPage", count%pageSize==0?count/pageSize:count/pageSize+1);
        resultMap.put("records",list );
        return this.successMsg(resultMap);
    }
    /**
     * 导出付费记录，最大1000条，所以页码和条数定了1000
     * @param params
     * @param request
     * @param response
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月11日 下午4:47:32
     */
    @SuppressWarnings({ "unchecked" })
//    @API(summary = "导出付费记录", description = "根据手机号和商户Id等查询", parameters = {
//            @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "userId", description = "用户ID/手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "startTime", description = "开始时间 2017-06-05", dataType = DataType.STRING, required = false),
//            @Param(name = "endTime", description = "结束时间", dataType = DataType.STRING, required = false),
//            @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//           
//    })
    /**
     * 导出付费记录
     * @param params
     * @param request
     * @param response
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月11日 下午4:49:35
     */
    @RequestMapping(value = "/consume/xls", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public void consumeExport(@RequestParam(value = "params",required = true)String params, 
            HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.debug("付费清单查询 params: " + params);
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
        Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
        Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(paramsMap, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(paramsMap, "cityId"));
        Integer countyId = CastUtil.toInteger(MapUtils.getString(paramsMap, "countyId"));
        String startTime = MapUtils.getString(paramsMap, "startTime");
        String endTime = MapUtils.getString(paramsMap, "endTime");
//        Integer pageNum = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageNo"));
//        Integer pageSize = CastUtil.toInteger(MapUtils.getString(paramsMap, "pageSize"));
        Integer pageNum = 1;//固定xls文件最大为1000条
        Integer pageSize = 1000;
        
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = CastUtil.toInteger(sessionProvinceId);
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = CastUtil.toInteger(sessionCityId);
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = CastUtil.toInteger(sessionAreaId);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("telephone", telephone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", countyId);
        map.put("createDateStart", startTime);
        map.put("createDateEnd", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        //付费清单条件
        map.put("consumeType", 3);//套餐类型为3
        if(pageNum==null || pageNum <0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        List<UserConsume> list = userConsumeService.queryListByParam(map);
        String fileName = "consume.xls";
//        String fileName = "付费记录.xls";
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");
        createConsumeXls(path, list, fileName);//保存xls文件
        IOUtil.download(fileName, path, response);//下载文件
    }
    /**
     * 导出赔付记录
     * @param params
     * @param request
     * @param response
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月11日 下午4:49:24
     */
    @SuppressWarnings("unchecked")
//    @API(summary = "导出赔付记录", description = "根据手机号和商户Id查询", parameters = {
//            @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "userId", description = "用户ID/手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "startTime", description = "开始时间 2017-06-05", dataType = DataType.STRING, required = false),
//            @Param(name = "endTime", description = "结束时间", dataType = DataType.STRING, required = false),
//            @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//    })
    @RequestMapping(value = "/compenstate/xls", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public void compensateExport(@RequestParam(value = "params", required = true) String params,
                HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.debug("赔付记录 params: " + params);
        
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
        Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
        Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(paramsMap, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(paramsMap, "cityId"));
        Integer countyId = CastUtil.toInteger(MapUtils.getString(paramsMap, "countyId"));
        String startTime = MapUtils.getString(paramsMap, "startTime");
        String endTime = MapUtils.getString(paramsMap, "endTime");
        Integer pageNum = 1;
        Integer pageSize = 1000;
        
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = CastUtil.toInteger(sessionProvinceId);
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = CastUtil.toInteger(sessionCityId);
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = CastUtil.toInteger(sessionAreaId);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("telephone", telephone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", countyId);
        //对应service获取的map字段名。
        map.put("createDateStart", startTime);
        map.put("createDateEnd", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        //关键参数
        map.put("consumeType", 2);//套餐类型为2
        map.put("packageKey", 202);//packageKey为202
        if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        logger.debug("params: "+ JSON.toJSONString(map));
        List<UserConsume> list = userConsumeService.queryListByParam(map);
        String fileName = "compensate.xls";
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");
        createcompensateXls(path, list, fileName);
        IOUtil.download(fileName, path, response);
      
    }
    /**
     * 体验包领取记录
     * @param params
     * @param request
     * @param response
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月11日 下午5:02:02
     */
    @SuppressWarnings("unchecked")
//    @API(summary = "体验包领取记录", description = "根据手机号和商户Id查询", parameters = {
//            @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "userId", description = "用户ID/手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "startTime", description = "开始时间 2017-06-05", dataType = DataType.STRING, required = false),
//            @Param(name = "endTime", description = "结束时间", dataType = DataType.STRING, required = false),
//            @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//    })
    @RequestMapping(value = "/free/xls", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public void freeExport(@RequestParam(value = "params", required = true) String params,
            HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.debug("免费体验包 params: " + params);
        
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
        Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
        Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(paramsMap, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(paramsMap, "cityId"));
        Integer countyId = CastUtil.toInteger(MapUtils.getString(paramsMap, "countyId"));
        String startTime = MapUtils.getString(paramsMap, "startTime");
        String endTime = MapUtils.getString(paramsMap, "endTime");
        Integer pageNum = 1;
        Integer pageSize = 1000;

        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = CastUtil.toInteger(sessionProvinceId);
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = CastUtil.toInteger(sessionCityId);
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = CastUtil.toInteger(sessionAreaId);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("telephone", telephone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", countyId);
       
        map.put("createDateStart", startTime);
        map.put("createDateEnd", endTime);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        
        map.put("consumeType", 2);//套餐类型为2
        map.put("packageKey", 201);//packageKey为201
        if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        logger.debug("params: "+ JSON.toJSONString(map));
        List<UserConsume> list = userConsumeService.queryListByParam(map);
        String fileName = "free.xls";
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");
        createFreeXls(path, list, fileName);
        IOUtil.download(fileName, path, response);
    }
    /**
     * 创建付费xls
     * @param path
     * @param data
     * @param fileName
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月11日 下午5:02:18
     */
    private static void createConsumeXls(String path, List<UserConsume> data, String fileName ) throws Exception{
        if(!new File(path).exists()){
            new File(path).mkdirs();
        }
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("付费记录", 1);//创建工作簿
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);//标题字体
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(Alignment.CENTRE);//水平居中
        //数据内容部分
        sheet.addCell(new jxl.write.Label(0, 0, "付费账号", titleFormat));
        sheet.addCell(new jxl.write.Label(1, 0, "园区名称", titleFormat));
        sheet.addCell(new jxl.write.Label(2, 0, "园区ID", titleFormat));
        sheet.addCell(new jxl.write.Label(3, 0, "地址", titleFormat));
        sheet.addCell(new jxl.write.Label(4, 0, "充值金额", titleFormat));
        sheet.addCell(new jxl.write.Label(5, 0, "充值套餐类型", titleFormat));
        sheet.addCell(new jxl.write.Label(6, 0, "充值时间", titleFormat));
        sheet.addCell(new jxl.write.Label(7, 0, "充值订单号", titleFormat));
        for(int i=0;i<data.size();i++){
            UserConsume consume = data.get(i);
            sheet.addCell(new jxl.write.Label(0, i+1, consume.getTelephone(), textFormat));
            sheet.addCell(new jxl.write.Label(1, i+1, consume.getMerchantName(), textFormat));
            sheet.addCell(new jxl.write.Label(2, i+1, CastUtil.toString(consume.getMerchantId()), textFormat));
            sheet.addCell(new jxl.write.Label(3, i+1, consume.getAddress(), textFormat));
            sheet.addCell(new jxl.write.Label(4, i+1, CastUtil.toString(consume.getPayNum()), textFormat));
            sheet.addCell(new jxl.write.Label(5, i+1, consume.getPkgDetail(), textFormat));
            sheet.addCell(new jxl.write.Label(6, i+1, consume.getCreateDate(), textFormat));
            sheet.addCell(new jxl.write.Label(7, i+1, consume.getOrderId(), textFormat));
        }
        book.write();
        book.close();
    }
    /**
     * 赔付xls
     * @param path
     * @param data
     * @param fileName
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月11日 下午2:31:08
     */
    private static void createcompensateXls(String path, List<UserConsume> data, String fileName ) throws Exception{
        if(!new File(path).exists()){
            new File(path).mkdirs();
        }
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("赔付记录", 1);//创建工作簿
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);//标题字体
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(Alignment.CENTRE);//水平居中
        
        sheet.addCell(new jxl.write.Label(0, 0, "用户账号", titleFormat));
        sheet.addCell(new jxl.write.Label(1, 0, "园区名称", titleFormat));
        sheet.addCell(new jxl.write.Label(2, 0, "园区ID", titleFormat));
        sheet.addCell(new jxl.write.Label(3, 0, "地址", titleFormat));
        sheet.addCell(new jxl.write.Label(4, 0, "赔付时间", titleFormat));
        sheet.addCell(new jxl.write.Label(5, 0, "赔付天数", titleFormat));
        sheet.addCell(new jxl.write.Label(6, 0, "赔付人员", titleFormat));
        
        for(int i=0;i<data.size();i++){
            UserConsume consume = data.get(i);
            sheet.addCell(new jxl.write.Label(0, i+1, consume.getTelephone(), textFormat));
            sheet.addCell(new jxl.write.Label(1, i+1, consume.getMerchantName(), textFormat));
            sheet.addCell(new jxl.write.Label(2, i+1, CastUtil.toString(consume.getMerchantId()), textFormat));
            sheet.addCell(new jxl.write.Label(3, i+1, consume.getAddress(), textFormat));
            sheet.addCell(new jxl.write.Label(4, i+1, consume.getCreateDate(), textFormat));
            sheet.addCell(new jxl.write.Label(5, i+1, CastUtil.toString(consume.getAddDay()), textFormat));
            sheet.addCell(new jxl.write.Label(6, i+1, consume.getRemarks(), textFormat));
        }
        book.write();
        book.close();
    }
    /**
     * 免费体验记录
     * @param path
     * @param data
     * @param fileName
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月11日 下午5:05:19
     */
    private static void createFreeXls(String path, List<UserConsume> data, String fileName ) throws Exception{
        if(!new File(path).exists()){
            new File(path).mkdirs();
        }
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("免费体验记录", 1);//创建工作簿
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);//标题字体
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(Alignment.CENTRE);//水平居中
        
        sheet.addCell(new jxl.write.Label(0, 0, "用户账号", titleFormat));
        sheet.addCell(new jxl.write.Label(1, 0, "园区名称", titleFormat));
        sheet.addCell(new jxl.write.Label(2, 0, "园区ID", titleFormat));
        sheet.addCell(new jxl.write.Label(3, 0, "地址", titleFormat));
        sheet.addCell(new jxl.write.Label(4, 0, "领取时间", titleFormat));
        sheet.addCell(new jxl.write.Label(5, 0, "免费体验包时长", titleFormat));

        for(int i=0;i<data.size();i++){
            UserConsume consume = data.get(i);
            sheet.addCell(new jxl.write.Label(0, i+1, consume.getTelephone(), textFormat));
            sheet.addCell(new jxl.write.Label(1, i+1, consume.getMerchantName(), textFormat));
            sheet.addCell(new jxl.write.Label(2, i+1, CastUtil.toString(consume.getMerchantId()), textFormat));
            sheet.addCell(new jxl.write.Label(3, i+1, consume.getAddress(), textFormat));
            sheet.addCell(new jxl.write.Label(4, i+1, consume.getCreateDate(), textFormat));
            sheet.addCell(new jxl.write.Label(5, i+1, CastUtil.toString(consume.getAddDay()), textFormat));
        }
        book.write();
        book.close();
    }
}
