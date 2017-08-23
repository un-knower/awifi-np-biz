/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年7月27日 下午2:16:33
 * 创建作者：梁聪
 * 文件名称：UserAuthStatController.java
 * 版本：  v1.0
 * 功能：统计--控制层相关代码
 * 修改记录：
 */
package com.awifi.np.biz.statsrv.stat.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.statsrv.stat.service.UserAuthStatService;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;


@Controller
@RequestMapping("/statsrv")
@SuppressWarnings({"rawtypes","unchecked"})
public class UserAuthStatController extends BaseController {

    /** 用户认证-地区维度-折线趋势图接口服务 */
    @Resource(name = "userAuthStatService")
    private UserAuthStatService userAuthStatService;

    /**项目服务*/
    @Resource(name = "projectService")
    private ProjectService projectService;

    /**
     * 用户认证-地区维度-折线趋势图接口
     * @param request 请求
     * @param access_token 令牌
     * @param params 参数
     * @return json数据
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月27日 上午9:00:15
     */
    @ResponseBody
    @RequestMapping(value="/userauth/trend/area", method= RequestMethod.GET)
    public Map getTrendByArea(HttpServletRequest request, @RequestParam(value="access_token",required=true) String access_token,
                              @RequestParam(value="params",required=true)String params) throws Exception{
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json

        Integer provinceId = CastUtil.toInteger(paramMap.get("provinceId"));//省id
        Integer cityId = CastUtil.toInteger(paramMap.get("cityId"));//市id
        Integer areaId = CastUtil.toInteger(paramMap.get("areaId"));//区id
        String bizType = (String) paramMap.get("bizType");//业务类型

        String dateScope = (String) paramMap.get("dateScope");//统计日期范围
        ValidUtil.valid("dateScope",dateScope, "required");//dateScope必填
        String beginDate = null;//开始日期
        String endDate = null;//结束日期
        if ( "day".equals(dateScope) ) {
            beginDate=(String) paramMap.get("beginDate");//开始日期
            endDate=(String) paramMap.get("endDate");//结束日期
            ValidUtil.valid("beginDate",beginDate, "required");//beginDate必填
            ValidUtil.valid("endDate",endDate, "required");//endDate必填
            Long sub = DateUtil.daySubDay(beginDate,endDate);//日期差 毫秒
            if ( sub < 0 ) {//如果开始日期大于截止日期
                Object[] error = {beginDate,endDate};
                //开始日期[{0}]不能大于截止日期[{1}]!
                throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",error));
            }
        }
        String yearMonth = null;
        if("month".equals(dateScope)) {
            yearMonth = (String) paramMap.get("yearMonth");//年月
            ValidUtil.valid("yearMonth",yearMonth, "required");//yearMonth必填
        }

        Map<String,Object> list = userAuthStatService.getTrendByArea(dateScope,beginDate,endDate,yearMonth,provinceId,cityId,areaId,bizType);//查询数据

        return this.successMsg(list);
    }

    /**
     * 用户认证-地区维度-统计接口
     * @param request 请求
     * @param access_token 令牌
     * @param params 参数
     * @return json数据
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月31日 下午2:30:25
     */
    @ResponseBody
    @RequestMapping(value="/userauth/area", method= RequestMethod.GET)
    public Map getByArea(HttpServletRequest request, @RequestParam(value="access_token",required=true) String access_token,
                              @RequestParam(value="params",required=true)String params) throws Exception{
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json

        Integer provinceId = null;
        Integer cityId = null;
        Integer countyId = null;

        String areaId = (String) paramMap.get("areaId");//areaId 为省市区的拼接组合
        if(StringUtils.isNotBlank(areaId)){//如果不为空 31-383
            String[] areaIdsArray = areaId.split("-");
            ValidUtil.valid("地区id[areaId]", areaIdsArray, "arrayNotBlank");//数组内部不允许存在空值(null|"")的校验
            int maxLength = areaIdsArray.length;
            provinceId = CastUtil.toInteger(areaIdsArray[0]);//第一个为省id
            if(maxLength == 2){//如果长度为2 第二个为市id
                cityId = CastUtil.toInteger(areaIdsArray[1]);
            }else if(maxLength == 3){//如果长度为3 第三个为区县id
                cityId = CastUtil.toInteger(areaIdsArray[1]);
                countyId = CastUtil.toInteger(areaIdsArray[2]);
            }
        }

        String bizType = (String) paramMap.get("bizType");//业务类型
        boolean hasTotal = (Boolean) paramMap.get("hasTotal");//是否需要返回总计，不允许为空，true代表返回值包含总计信息，false代表不包含
        ValidUtil.valid("hasTotal",hasTotal, "required");//hasTotal必填

        String dateScope = (String) paramMap.get("dateScope");//统计日期范围
        ValidUtil.valid("dateScope",dateScope, "required");//dateScope必填
        String beginDate = null;//开始日期
        String endDate = null;//结束日期
        if("day".equals(dateScope)){
            beginDate=(String) paramMap.get("beginDate");//开始日期
            endDate=(String) paramMap.get("endDate");//结束日期
            ValidUtil.valid("beginDate",beginDate, "required");//beginDate必填
            ValidUtil.valid("endDate",endDate, "required");//endDate必填
            Long sub = DateUtil.daySubDay(beginDate,endDate);//日期差 毫秒
            if(sub < 0){//如果开始日期大于截止日期
                Object[] error = {beginDate,endDate};
                throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",error));//开始日期[{0}]不能大于截止日期[{1}]!
            }
        }
        String yearMonth = null;
        if("month".equals(dateScope)) {
            yearMonth = (String) paramMap.get("yearMonth");//年月
            ValidUtil.valid("yearMonth",yearMonth, "required");//yearMonth必填
        }

        List<Map<String,Object>> list = userAuthStatService.getByArea(dateScope,beginDate,endDate,yearMonth,provinceId,cityId,countyId,bizType,hasTotal,areaId);//查询数据

        return this.successMsg(list);
    }

    /**
     * 用户认证-地区维度-导出接口
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param params 参数
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年8月2日 上午10:24:10
     */
    @ResponseBody
    @RequestMapping(value="/userauth/area/xls", method=RequestMethod.GET)
    public void exportByArea(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="access_token",required=true) String access_token,
                             @RequestParam(name="params",required=true) String params) throws Exception{
        Map map=JsonUtil.fromJson(params, Map.class);
        String beginDate=(String) map.get("beginDate");//开始日期
        String endDate=(String) map.get("endDate");//结束日期
        String bizType=(String) map.get("bizType");//业务类型
        ValidUtil.valid("beginDate",beginDate, "required");//beginDate必填
        ValidUtil.valid("endDate",endDate, "required");//endDate必填

        Integer provinceId = null;
        Integer cityId = null;
        Integer countyId = null;

        String areaId = (String) map.get("areaId");//areaId 为省市区的拼接组合
        if(StringUtils.isNotBlank(areaId)){//如果不为空 31-383
            String[] areaIdsArray = areaId.split("-");
            ValidUtil.valid("地区id[areaId]", areaIdsArray, "arrayNotBlank");//数组内部不允许存在空值(null|"")的校验
            int maxLength = areaIdsArray.length;
            provinceId = CastUtil.toInteger(areaIdsArray[0]);//第一个为省id
            if(maxLength == 2){//如果长度为2 第二个为市id
                cityId = CastUtil.toInteger(areaIdsArray[1]);
            }else if(maxLength == 3){//如果长度为3 第三个为区县id
                cityId = CastUtil.toInteger(areaIdsArray[1]);
                countyId = CastUtil.toInteger(areaIdsArray[2]);
            }
        }

        if(DateUtil.daySubDay(beginDate, endDate)<0){
            throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",new Object[]{beginDate,endDate}));//开始日期[{0}]不能大于截止日期[{1}]!
        }

        String dateScope = (String) map.get("dateScope");//统计日期范围
        ValidUtil.valid("dateScope",dateScope, "required");//dateScope必填

        String statType=getStatType(provinceId,cityId,countyId);
        String yearMonth = null;
        if("month".equals(dateScope)) {
            yearMonth = (String) map.get("yearMonth");//年月
            ValidUtil.valid("yearMonth",yearMonth, "required");//yearMonth必填
        }
        List<Map<String,Object>> resultList = userAuthStatService.getByArea(dateScope,beginDate,endDate,yearMonth,provinceId,cityId,countyId,bizType,true,areaId);//查询数据

        String fileName = "userAuthAreaExport.xls";//文件名称
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("用户认证-地区维度",1);// 创建工作表
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);//设置标题样式
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中

        sheet.addCell(new Label(0, 0, "时间: "+beginDate.replace("-", "/") + "-" + endDate.replace("-", "/")+"    终端类型：全部    运营商：全部 "));//设置title
        sheet.mergeCells(0, 0, 5,0);

        for(int i=0;i<=3;i++){
            sheet.setColumnView(i, 15);//设置每列表格宽度（20）
        }
        switch(statType){
            case Constants.STATTYPEP:
                sheet.addCell(new Label(0, 1, "省", titleFormat));//设置title
                break;
            case Constants.STATTYPET:
                sheet.addCell(new Label(0, 1, "市", titleFormat));//设置title
                break;
            case Constants.STATTYPEC:
                sheet.addCell(new Label(0, 1, "区", titleFormat));//设置title
                break;
            default:
                break;
        }
        sheet.addCell(new Label(1, 1, "用户数(UV)", titleFormat));//设置title
        sheet.addCell(new Label(2, 1, "新用户数", titleFormat));//设置title
        sheet.addCell(new Label(3, 1, "认证数", titleFormat));//设置title

        int size=resultList.size();
        for(int i=0;i<size;i++){
            Map<String,Object> resultpv=resultList.get(i);
            if(i==0){
                sheet.addCell(new Label(0, i+2,"合计", textFormat));//合计
            }else{
                sheet.addCell(new Label(0, i+2, resultpv.get("areaName").toString(), textFormat));//地区
            }
            sheet.addCell(new Label(1, i+2,resultpv.get("userNum").toString(), textFormat));//用户数(UV)
            sheet.addCell(new Label(2, i+2,resultpv.get("newUserNum").toString(), textFormat));//新用户数
            sheet.addCell(new Label(3, i+2, resultpv.get("authNum").toString(), textFormat));//认证数

        }
        book.write();
        book.close();
        IOUtil.download(fileName, path, response);
    }

    /**
     * 根据省市区获取statType地区纬度
     * @param provinceId 省Id
     * @param cityId 市id
     * @param country 区id
     * @return statType
     * @author 梁聪
     * @date 2017年8月2日 下午3:21:19
     */
    private String getStatType(Integer provinceId, Integer cityId, Integer country) {
        String statType=null;
        if(provinceId==null&&cityId==null&&country==null){
            statType= Constants.STATTYPEP;//跨度为省
        }
        if(provinceId!=null&&cityId==null&&country==null){
            statType=Constants.STATTYPET;//跨度为市
        }
        if(provinceId!=null&&cityId!=null){
            statType=Constants.STATTYPEC;//跨度为区
        }
        return statType;
    }

    /**
     * 用户认证-商户维度-折线趋势图接口
     * @param access_token access_token
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 11:16:45 AM
     */
    @ResponseBody
    @RequestMapping(value="/userauth/trend/merchant", method=RequestMethod.GET)
    public Map<String, Object> getTrendByMerchant(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception {
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数json格式
        String dateScope = (String)paramsMap.get("dateScope");//统计日期范围，不允许为空，其中：day代表按日统计、month代表按月统计
        String beginDate = (String)paramsMap.get("beginDate");//开始时间 当为按日统计，即dateScope=="day"时，不允许为空
        String endDate = (String)paramsMap.get("endDate");//结束时间 当为按日统计，即dateScope=="day"时，不允许为空
        String yearMonth = (String)paramsMap.get("yearMonth");//年月 当为按月统计，即dateScope=="month"时，不允许为空
        Long projectId = CastUtil.toLong(paramsMap.get("projectId"));//项目id 数字，不允许为空
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id 数字，允许为空
        
        ValidUtil.valid("统计日期范围[dateScope]", dateScope, "required");//校验时间范围
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//项目id校验
        
        if ("day".equals(dateScope)) {//判断时间范围为天
            ValidUtil.valid("开始日期[beginDate]", beginDate, "required");//检验开始时间
            ValidUtil.valid("截至日期[endDate]", endDate, "required");//校验结束时间
            Long sub = DateUtil.daySubDay(beginDate,endDate);//日期差 毫秒
            if(sub < 0){//如果开始日期大于截止日期
                Object[] error = {beginDate,endDate};
                throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",error));//开始日期[{0}]不能大于截止日期[{1}]!
            }
        }else if ("month".equals(dateScope)) {//判断时间范围为月
            ValidUtil.valid("年月[yearMonth]", yearMonth, "required");//校验年月
            endDate = DateUtil.getMonthLastDay(yearMonth);//获取一个月最后一天
            beginDate = yearMonth + "-01";
        } 
        
        String today = DateUtil.getTodayDate();//获取当天的时间
        String timeUnit= "D";//申明时间单位和时间格式
        if (beginDate.equals(today) && endDate.equals(today)) {//当开始时间和结束时间为同一天
            timeUnit = "H";//设置时间单位为小时
        }
        Map<String, Object> reqParams = new HashMap<>();//创建参数map
        if (merchantId != null) {
            reqParams.put("merchantId", merchantId);//添加商户id
        }
        reqParams.put("projectId", projectId);//添加项目id
        reqParams.put("timeUnit", timeUnit);//添加时间单位
        reqParams.put("dayFlagB", beginDate.replaceAll("-", ""));//添加开始时间
        reqParams.put("dayFlagE", endDate.replaceAll("-", ""));//添加结束时间
        
        Map<String, Object> data = userAuthStatService.getTrendByMerchant(JsonUtil.toJson(reqParams));//查询折线图数据
        
        return this.successMsg(data);//返回结果
    }

    /**
     * 用户认证-商户维度-统计接口
     * @param access_token access_token
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 11:17:51 AM
     */
    @ResponseBody
    @RequestMapping(value="/userauth/merchant", method=RequestMethod.GET)
    public Map<String, Object> getByMerchant(@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception {
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数json格式
        String dateScope = (String)paramsMap.get("dateScope");//统计日期范围，不允许为空，其中：day代表按日统计、month代表按月统计
        String beginDate = (String)paramsMap.get("beginDate");//开始时间 当为按日统计，即dateScope=="day"时，不允许为空
        String endDate = (String)paramsMap.get("endDate");//结束时间 当为按日统计，即dateScope=="day"时，不允许为空
        String yearMonth = (String)paramsMap.get("yearMonth");//年月 当为按月统计，即dateScope=="month"时，不允许为空
        Long projectId = CastUtil.toLong(paramsMap.get("projectId"));//项目id 数字，不允许为空
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id 数字，允许为空
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//页条数
        
        ValidUtil.valid("统计日期范围[dateScope]", dateScope, "required");//校验时间范围
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//项目id校验
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录最大数
        if ("day".equals(dateScope)) {//判断时间范围为天
            ValidUtil.valid("开始日期[beginDate]", beginDate, "required");//检验开始时间
            ValidUtil.valid("截至日期[endDate]", endDate, "required");//校验结束时间
            Long sub = DateUtil.daySubDay(beginDate,endDate);//日期差 毫秒
            if(sub < 0){//如果开始日期大于截止日期
                Object[] error = {beginDate,endDate};
                throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",error));//开始日期[{0}]不能大于截止日期[{1}]!
            }
        }else if ("month".equals(dateScope)) {//判断时间范围为月
            ValidUtil.valid("年月[yearMonth]", yearMonth, "required");//校验年月
            endDate = DateUtil.getMonthLastDay(yearMonth);//获取一个月最后一天
            beginDate = yearMonth + "-01";
        }
        
        if (pageNo == null) {//页码为空则默认为第一页
            pageNo = 1;
        }
        
        Map<String, Object> reqParams = new HashMap<>();//创建参数map
        if (merchantId != null) {
            reqParams.put("merchantId", merchantId);//添加商户id
        }
        reqParams.put("projectId", projectId);//添加项目id
        reqParams.put("dayFlagB", beginDate.replaceAll("-", ""));//添加开始时间
        reqParams.put("dayFlagE", endDate.replaceAll("-", ""));//添加结束时间
        
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageNo,pageSize);  //新建page
        
        userAuthStatService.getByMerchant(page,reqParams);//查询分页数据
        
        return this.successMsg(page);//返回结果
    }

    /**
     * 用户认证-商户维度-统计接口
     * @param access_token access_token
     * @param params 参数
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 11:17:51 AM
     */
    @ResponseBody
    @RequestMapping(value="/userauth/merchant/xls", method=RequestMethod.GET)
    public void exportByMerchant(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception {
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数json格式
        String dateScope = (String)paramsMap.get("dateScope");//统计日期范围，不允许为空，其中：day代表按日统计、month代表按月统计
        String beginDate = (String)paramsMap.get("beginDate");//开始时间 当为按日统计，即dateScope=="day"时，不允许为空
        String endDate = (String)paramsMap.get("endDate");//结束时间 当为按日统计，即dateScope=="day"时，不允许为空
        String yearMonth = (String)paramsMap.get("yearMonth");//年月 当为按月统计，即dateScope=="month"时，不允许为空
        Long projectId = CastUtil.toLong(paramsMap.get("projectId"));//项目id 数字，不允许为空
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id 数字，允许为空

        ValidUtil.valid("统计日期范围[dateScope]", dateScope, "required");//校验时间范围
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//项目id校验
        if ("day".equals(dateScope)) {//判断时间范围为天
            ValidUtil.valid("开始日期[beginDate]", beginDate, "required");//检验开始时间
            ValidUtil.valid("截至日期[endDate]", endDate, "required");//校验结束时间
            Long sub = DateUtil.daySubDay(beginDate,endDate);//日期差 毫秒
            if(sub < 0){//如果开始日期大于截止日期
                Object[] error = {beginDate,endDate};
                throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",error));//开始日期[{0}]不能大于截止日期[{1}]!
            }
        }else if ("month".equals(dateScope)) {//判断时间范围为月
            ValidUtil.valid("年月[yearMonth]", yearMonth, "required");//校验年月
            endDate = DateUtil.getMonthLastDay(yearMonth);//获取一个月最后一天
            beginDate = yearMonth + "-01";
        }
        
        Map<String, Object> reqParams = new HashMap<>();//创建参数map
        if (merchantId != null) {
            reqParams.put("merchantId", merchantId);//添加商户id
        }
        reqParams.put("projectId", projectId);//添加项目id
        reqParams.put("dayFlagB", beginDate.replaceAll("-", ""));//添加开始时间
        reqParams.put("dayFlagE", endDate.replaceAll("-", ""));//添加结束时间

        int maxSize = 0;//数据的最大长度
        Integer pageSize = Integer.valueOf(SysConfigUtil.getParamValue("xls_export_sheet_max_size")).intValue();//每页数量--工作表每页数量
        Integer sheetNum = 1;
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(sheetNum,pageSize);  //新建page
        
        String fileName = "userAuthMerchantExport.xls";//文件名称
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);//设置标题样式
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        
        List<Map<String, Object>> listMap = new ArrayList<>();
        do{
            WritableSheet sheet = book.createSheet("sheet"+sheetNum,sheetNum-1);// 创建工作表
            
            userAuthStatService.getByMerchant(page,reqParams);//查询分页数据
            listMap = page.getRecords();
            
            Date bDate = DateUtil.parseToDate(beginDate, DateUtil.YYYY_MM_DD);//调整时间格式
            Date eDate = DateUtil.parseToDate(endDate, DateUtil.YYYY_MM_DD);//调整时间格式
            String projectName = projectService.getNameById(projectId);//通过项目id获取项目名称
            sheet.addCell(new Label(0, 0, "时间:" + DateUtil.formatToString(bDate, "yyyy/MM/dd") + "-" + DateUtil.formatToString(eDate, "yyyy/MM/dd") + " 项目名称:" + projectName, titleFormat));//设置title
            sheet.mergeCells(0, 0, 3, 0);//合并单元格
            sheet.addCell(new Label(0, 1, "商户名称", titleFormat));//设置title
            sheet.addCell(new Label(1, 1, "用户数(UV)", titleFormat));//设置title
            sheet.addCell(new Label(2, 1, "新用户数", titleFormat));//设置title
            sheet.addCell(new Label(3, 1, "认证数", titleFormat));//设置title
            
            maxSize = listMap.size();//数据长度
            Map<String, Object> mapData = null;
            for(int i=0;i<maxSize;i++){
                mapData = listMap.get(i);
                sheet.addCell(new Label(0, i+2, mapData.get("merchantName").toString(), textFormat));//商户名称
                sheet.addCell(new Label(1, i+2, mapData.get("userNum").toString(), textFormat));//用户数(UV)
                sheet.addCell(new Label(2, i+2, mapData.get("newUserNum").toString(), textFormat));//新用户数
                sheet.addCell(new Label(3, i+2, mapData.get("authNum").toString(), textFormat));//认证数
            }
            
            sheetNum ++;
            page.setPageNo(sheetNum);
        } while ((pageSize+1) == maxSize);

        book.write();
        book.close();
        IOUtil.download(fileName, path, response);//下载

        logger.debug("----------提示：用户认证-商户维度-导出接口执行成 功  UserAuthStatController.exportByMerchant(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
    }
}
