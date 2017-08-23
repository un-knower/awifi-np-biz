/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月27日 上午9:53:26
* 创建作者：周颖
* 文件名称：DeviceTrendStatController.java
* 版本：  v1.0
* 功能：设备发展分析
* 修改记录：
*/
package com.awifi.np.biz.statsrv.stat.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.awifi.np.biz.api.client.dbcenter.stat.model.DeviceTrend;
import com.awifi.np.biz.api.client.dbcenter.stat.util.DeviceTrendStatClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Controller
@RequestMapping("/statsrv/devicetrend")
@SuppressWarnings("unchecked")
public class DeviceTrendStatController extends BaseController {
    
    /**
     * 设备发展趋势按周
     * @param access_token access_token
     * @param params 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月27日 下午4:10:13
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/week")
    public Map<String,Object> getByWeek(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(value="params",required=true) String params,
            HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Object hasTotalObj = paramsMap.get("hasTotal");
        ValidUtil.valid("是否需要返回总计[hasTotal]", hasTotalObj, "required");
        Boolean hasTotal = (Boolean) hasTotalObj;
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//获取当前登陆账号
        Map<String,Object> reqMap = dealWeekParam(sessionUser, paramsMap);//整理入参
        
        List<DeviceTrend> data = DeviceTrendStatClient.getByWeek(reqMap,hasTotal);//获取数据
        return this.successMsg(data);//返回
    }
    
    /**
     * 处理参数
     * @param sessionUser 登陆用户
     * @param paramsMap 参数
     * @return 筛选过的参数
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月28日 下午3:44:19
     */
    private Map<String,Object> dealWeekParam(SessionUser sessionUser, Map<String,Object> paramsMap) throws Exception{
        String beginDate = (String) paramsMap.get("beginDate");//开始日期，不允许为空
        String endDate = (String) paramsMap.get("endDate");//截至日期，不允许为空
        ValidUtil.valid("开始日期[beginDate]", beginDate, "required");
        ValidUtil.valid("截至日期[endDate]", endDate, "required");
        Long sub = DateUtil.daySubDay(beginDate,endDate);//日期差 毫秒
        if(sub < 0){//如果开始日期大于截止日期
            Object[] error = {beginDate,endDate};
            throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",error));//开始日期[{0}]不能大于截止日期[{1}]!
        }
        if(sub - Constants.WEEKDAYMILL > 0){
            throw new ValidException("E2000063", MessageUtil.getMessage("E2000063"));//开始日期与截止日期之间的跨度不能超过7天!
        }
        int week = DateUtil.dayForWeek(beginDate);
        if(week != 1){//开始时间不是周一
            throw new ValidException("E2000064", MessageUtil.getMessage("E2000064"));//开始日期必须为周一!
        }
        week = DateUtil.dayForWeek(endDate);
        if(week != 7){//结束时间不是周日
            throw new ValidException("E2000065", MessageUtil.getMessage("E2000065"));//截止日期必须为周日!
        }
        Long provinceId = null;
        Long cityId = null;
        Long countyId = null;
        
        String areaId = (String) paramsMap.get("areaId");
        if(StringUtils.isNotBlank(areaId)){//如果不为空 31-383
            String[] areaIdsArray = areaId.split("-");
            ValidUtil.valid("地区id[areaId]", areaIdsArray, "arrayNotBlank");//数组内部不允许存在空值(null|"")的校验
            int maxLength = areaIdsArray.length;
            provinceId = CastUtil.toLong(areaIdsArray[0]);//第一个为省id
            if(maxLength == 2){//如果长度为2 第二个为市id
                cityId = CastUtil.toLong(areaIdsArray[1]);
            }else if(maxLength == 3){//如果长度为3 第三个为区县id
                cityId = CastUtil.toLong(areaIdsArray[1]);
                countyId = CastUtil.toLong(areaIdsArray[2]);
            }
        }
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = sessionProvinceId;
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = sessionUser.getCityId();
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = sessionAreaId;
        }
        //根据地区id 获取地区纬度
        String statType = Constants.STATTYPEC;//默认给 区县的维度
        if(provinceId == null && cityId == null && countyId== null){//如果都为空 按省维度查
            statType = Constants.STATTYPEP;
        }else if(provinceId != null && cityId == null && countyId== null){//省不为空 其余为空 按市维度查
            statType = Constants.STATTYPET;
        }else if(provinceId != null && cityId != null ){//省市都不为空 按区县维度查
            statType = Constants.STATTYPEC;
        }else{//其余 打印日志
            logger.debug("错误：地区维度无法匹配！");
        }
        String entityType = (String)paramsMap.get("entityType");//设备类型，允许为空， 其中：fat代表胖AP、gopn代表光猫
        if(StringUtils.isBlank(entityType)){
            entityType = null;
        }else if(StringUtils.equals(entityType, Constants.FAT)){//如果设备类型为fat 转为31
            entityType = "31";
        }else if(StringUtils.equals(entityType, Constants.GOPN)){//如果设备类型为gopn 转为32,33,34,35
            entityType = "32,33,34,35";
        }else{//超出范围 抛异常
            Object[] error = {"设备类型",entityType};
            throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",error));//{0}[{1}]超出了范围!
        }
        
        Map<String,Object> reqMap = new HashMap<String, Object>();
        reqMap.put("province", provinceId);
        reqMap.put("city", cityId);
        reqMap.put("county", countyId);
        reqMap.put("entityType", entityType);
        reqMap.put("statType", statType);
        reqMap.put("dayFlagB", beginDate.replaceAll("-", ""));
        reqMap.put("dayFlagE", endDate.replaceAll("-", ""));
        return reqMap;
    }

    /**
     * 设备趋势按周导出
     * @param access_token 令牌
     * @param params 搜索条件
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月31日 上午10:17:11
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/week/xls")
    public void exportByWeek(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(value="params",required=true) String params,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//获取当前登陆账号
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Map<String,Object> reqMap = dealWeekParam(sessionUser, paramsMap);//整理入参
        
        List<DeviceTrend> data = DeviceTrendStatClient.getByWeek(reqMap,true);//获取数据
        String fileName = "deviceTrendWeekExport.xls";//文件名称
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        creatExcel(path,data,fileName);
        IOUtil.download(fileName, path, response);//下载
    }
    
    /**
     * 生成excel文件
     * @param path 路径
     * @param data 数据
     * @param fileName 文件名称
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月31日 下午3:03:58
     */
    private void creatExcel(String path,List<DeviceTrend> data,String fileName) throws Exception{
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("设备发展趋势",1);// 创建工作表
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);//设置标题样式
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        
        sheet.addCell(new Label(0, 0, "地区", titleFormat));//设置title
        sheet.addCell(new Label(1, 0, "受理数", titleFormat));//设置title
        sheet.addCell(new Label(4, 0, "AP类激活绑定数", titleFormat));//设置title
        sheet.addCell(new Label(8, 0, "绑定商户数", titleFormat));//设置title
        
        sheet.mergeCells(0, 0, 0, 1);//合并单元格 地区
        sheet.mergeCells(1, 0, 3, 0);//合并单元格 受理数
        sheet.mergeCells(4, 0, 7, 0);//合并单元格 AP类激活绑定数
        sheet.mergeCells(8, 0, 9, 0);//合并单元格  绑定商户数
        
        sheet.addCell(new Label(1, 1, "到达数", titleFormat));//设置title
        sheet.addCell(new Label(2, 1, "新增数", titleFormat));//设置title
        sheet.addCell(new Label(3, 1, "拆机数", titleFormat));//设置title
        
        sheet.addCell(new Label(4, 1, "到达数", titleFormat));//设置title
        sheet.addCell(new Label(5, 1, "新增数", titleFormat));//设置title
        sheet.addCell(new Label(6, 1, "解绑数", titleFormat));//设置title
        sheet.addCell(new Label(7, 1, "活跃AP数", titleFormat));//设置title
        
        sheet.addCell(new Label(8, 1, "到达数", titleFormat));//设置title
        sheet.addCell(new Label(9, 1, "活跃商户数", titleFormat));//设置title
        
        int maxSize = data.size();
        DeviceTrend deviceTrend = null;
        for(int i=0;i<maxSize;i++){
            deviceTrend = data.get(i);
            sheet.addCell(new Label(0, i+2, deviceTrend.getAreaName(), textFormat));//地区
            sheet.addCell(new Label(1, i+2, Integer.toString(deviceTrend.getAcceptArriveNum()), textFormat));//受理数-到达数
            sheet.addCell(new Label(2, i+2, Integer.toString(deviceTrend.getAcceptNewNum()), textFormat));//受理数-新增数
            sheet.addCell(new Label(3, i+2, Integer.toString(deviceTrend.getAcceptUnbindeNum()), textFormat));//受理数-拆机数
            sheet.addCell(new Label(4, i+2, Integer.toString(deviceTrend.getApArriveNum()), textFormat));//AP类激活绑定数-到达数
            sheet.addCell(new Label(5, i+2, Integer.toString(deviceTrend.getApNewNum()), textFormat));//AP类激活绑定数-新增数
            sheet.addCell(new Label(6, i+2, Integer.toString(deviceTrend.getApUnbindNum()), textFormat));//AP类激活绑定数-解绑数
            sheet.addCell(new Label(7, i+2, Integer.toString(deviceTrend.getApActiveNum()), textFormat));//AP类激活绑定数-活跃数
            sheet.addCell(new Label(8, i+2, Integer.toString(deviceTrend.getArriveMerchantNum()), textFormat));//到达商户数
            sheet.addCell(new Label(9, i+2, Integer.toString(deviceTrend.getActiveMerchantNum()), textFormat));//活跃商户数
        }
        book.write();
        book.close();
    }
    
    /**
     * 设备发展趋势按月统计
     * @param access_token 令牌
     * @param params 入参搜索条件
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月31日 下午2:05:44
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/month")
    public Map<String,Object> getByMonth(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(value="params",required=true) String params,
            HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Object hasTotalObj = paramsMap.get("hasTotal");
        ValidUtil.valid("是否需要返回总计[hasTotal]", hasTotalObj, "required");
        Boolean hasTotal = (Boolean) hasTotalObj;
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//获取当前登陆账号
        Map<String,Object> reqMap = dealMonthParam(sessionUser, params);//整理入参
        
        List<DeviceTrend> data = DeviceTrendStatClient.getByMonth(reqMap,hasTotal);//获取数据
        return this.successMsg(data);//返回
    }
    
    /**
     * 处理参数
     * @param sessionUser 登陆用户
     * @param params 参数
     * @return 筛选过的参数
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月28日 下午3:44:19
     */
    private Map<String,Object> dealMonthParam(SessionUser sessionUser,String params) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        String statDate = (String) paramsMap.get("statDate");//统计日期，不允许为空
        ValidUtil.valid("开始日期[statDate]", statDate, "required");
        String endDate = DateUtil.getMonthLastDay(statDate);//获取一个月最后一天
        statDate = statDate + "-01";
        
        Long provinceId = null;
        Long cityId = null;
        Long countyId = null;
        
        String areaId = (String) paramsMap.get("areaId");
        if(StringUtils.isNotBlank(areaId)){//如果不为空 31-383
            String[] areaIdsArray = areaId.split("-");
            ValidUtil.valid("地区id[areaId]", areaIdsArray, "arrayNotBlank");//数组内部不允许存在空值(null|"")的校验
            int maxLength = areaIdsArray.length;
            provinceId = CastUtil.toLong(areaIdsArray[0]);//第一个为省id
            if(maxLength == 2){//如果长度为2 第二个为市id
                cityId = CastUtil.toLong(areaIdsArray[1]);
            }else if(maxLength == 3){//如果长度为3 第三个为区县id
                cityId = CastUtil.toLong(areaIdsArray[1]);
                countyId = CastUtil.toLong(areaIdsArray[2]);
            }
        }
        //获取登陆账号的地区属性 （初始调用，筛选条件为空）
        Long sessionProvinceId = sessionUser.getProvinceId();//省id
        if(sessionProvinceId != null){//如果不为空 搜索条件的省id置为登陆账号的省id 数据过滤
            provinceId = sessionProvinceId;
        }
        Long sessionCityId = sessionUser.getCityId();
        if(sessionCityId != null){//如果不为空 搜索条件的市id置为登陆账号的市id 数据过滤
            cityId = sessionUser.getCityId();
        }
        Long sessionAreaId = sessionUser.getAreaId();
        if(sessionAreaId != null){//如果不为空 搜索条件的区县id置为登陆账号的区县id 数据过滤
            countyId = sessionAreaId;
        }
        //根据地区id 获取地区纬度
        String statType = Constants.STATTYPEC;//默认给 区县的维度
        if(provinceId == null && cityId == null && countyId== null){//如果都为空 按省维度查
            statType = Constants.STATTYPEP;
        }else if(provinceId != null && cityId == null && countyId== null){//省不为空 其余为空 按市维度查
            statType = Constants.STATTYPET;
        }else if(provinceId != null && cityId != null ){//省市都不为空 按区县维度查
            statType = Constants.STATTYPEC;
        }else{//其余 打印日志
            logger.debug("错误：地区维度无法匹配！");
        }
        String entityType = (String)paramsMap.get("entityType");//设备类型，允许为空， 其中：fat代表胖AP、gopn代表光猫
        if(StringUtils.isBlank(entityType)){
            entityType = null;
        }else if(StringUtils.equals(entityType, Constants.FAT)){//如果设备类型为fat 转为31
            entityType = "31";
        }else if(StringUtils.equals(entityType, Constants.GOPN)){//如果设备类型为gopn 转为32,33,34,35
            entityType = "32,33,34,35";
        }else{//超出范围 抛异常
            Object[] error = {"设备类型",entityType};
            throw new ValidException("E2000058", MessageUtil.getMessage("E2000058",error));//{0}[{1}]超出了范围!
        }
        Map<String,Object> reqMap = new HashMap<String, Object>();
        reqMap.put("province", provinceId);
        reqMap.put("city", cityId);
        reqMap.put("county", countyId);
        reqMap.put("entityType", entityType);
        reqMap.put("statType", statType);
        reqMap.put("dayFlagB", statDate.replaceAll("-", ""));
        reqMap.put("dayFlagE", endDate.replaceAll("-", ""));
        return reqMap;
    }
    
    /**
     * 设备发展趋势按月导出
     * @param access_token 令牌 
     * @param params 入参搜索条件
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月31日 下午3:08:17
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/month/xls")
    public void exportByMonth(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(value="params",required=true) String params,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//获取当前登陆账号
        Map<String,Object> reqMap = dealMonthParam(sessionUser, params);//整理入参
        
        List<DeviceTrend> data = DeviceTrendStatClient.getByMonth(reqMap,true);//获取数据
        String fileName = "deviceTrendMonthExport.xls";//文件名称
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        creatExcel(path,data,fileName);
        IOUtil.download(fileName, path, response);//下载
    }
}
