/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月23日 上午10:08:56
* 创建作者：余红伟
* 文件名称：ReportFormController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.statistics.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.statistics.service.ReportFormService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@APIs(description = "")
@Controller
@RequestMapping("/timebuysrv/stat")
public class ReportFormController extends BaseController{
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private ReportFormService reportFormService;
    
    /**
     * 统计结果
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月28日 下午6:46:28
     */
    @RequestMapping(value = "/task", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> task() throws Exception{
        reportFormService.statisticsTask();
        return this.successMsg();
    }
    /**
     * 报表查询
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月28日 下午6:46:48
     */
    @SuppressWarnings("unchecked")
//    @API(summary = "查询", description = "区域维度查询",parameters = {
//            @Param(name = "areaId", description = "区域id, 用-连接,31-383-3232，代表浙江杭州拱墅区,可为空",dataType = DataType.STRING,required = false),
//            @Param(name = "beginDate", description = "开始时间",dataType = DataType.STRING,required = false),
//            @Param(name = "endDate", description = "结束时间",dataType = DataType.STRING,required = false),
//            @Param(name = "hasTotal", description = "是否返回合计", dataType = DataType.STRING,required = false)
//    })
    @RequestMapping(value = "/location/multiple", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> query(@RequestParam(value="params", required = true)String params,HttpServletRequest request) throws Exception{
        logger.debug(params);
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);

        String beginDate = MapUtils.getString(paramsMap, "beginDate");
        String endDate = MapUtils.getString(paramsMap, "endDate");
        
        String areaId = MapUtils.getString(paramsMap, "areaId");
        String hasTotal = MapUtils.getString(paramsMap, "hasTotal");
//        ValidUtil.valid("开始时间", beginDate, "{'required':true}");
//        ValidUtil.valid("结束时间", endDate, "{'required':true}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate) && sdf.parse(beginDate).getTime() > sdf.parse(endDate).getTime()){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        String[] locationArr = null;
        int size = 0;
        if(StringUtils.isNotBlank(areaId)){
            locationArr = areaId.split("-");
            size = locationArr.length;
        }
        Long provinceId = null;
        Long cityId = null;
        Long countyId = null;

        if(size > 0){
            provinceId = CastUtil.toLong(locationArr[0]);
            ValidUtil.valid("省id", provinceId, "{'required':true,'numeric':true}");
        }
        if(size > 1){
            cityId = CastUtil.toLong(locationArr[1]);
            ValidUtil.valid("市id", cityId, "{'required':true,'numeric':true}");
        }
        if(size > 2){
            countyId = CastUtil.toLong(locationArr[2]);
            ValidUtil.valid("区id", countyId, "{'required':true,'numeric':true}");
        }
        //数据权限，超级管理员省市区id都为空， 前端传入省市区参数，后端把有参数的全部换成session里的
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
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
        
        paramsMap.put("provinceId", provinceId);
        paramsMap.put("cityId", cityId);
        paramsMap.put("areaId", countyId);
        List<HashMap<String, Object>> result = reportFormService.queryListByParam(paramsMap);
        for(HashMap<String, Object> map : result){
            if(size< 3){
                map.put("hasChild", true);
            }else{
                map.put("hasChild", false);
            }
        }
        //为空数据用0补全,数据库返回回来的数据都是BigDecimal类型
        for(HashMap<String, Object> map : result){
            String totalPaid = MapUtils.getString(map, "totalPaid");
            if(StringUtils.isBlank(totalPaid)){
                totalPaid = "0";
            }
            String totalUsers = MapUtils.getString(map, "totalUsers");
            if(StringUtils.isBlank(totalUsers)){
                totalUsers = "0";
            }
            String payUsers = MapUtils.getString(map, "payUsers");
            if(StringUtils.isBlank(payUsers)){
                payUsers = "0";
            }
            String pkgDays = MapUtils.getString(map, "pkgDays");
            if(StringUtils.isBlank(pkgDays)){
                pkgDays = "0";
            }
            String pkgMonths = MapUtils.getString(map, "pkgMonths");
            if(StringUtils.isBlank(pkgMonths)){
                pkgMonths = "0";
            }
            String pkgYears = MapUtils.getString(map, "pkgYears");
            if(StringUtils.isBlank(pkgYears)){
                pkgYears = "0";
            }
            String merchantNum = MapUtils.getString(map, "merchantNum");
            if(StringUtils.isBlank(merchantNum)){
                merchantNum = "0";
            }
            String deviceNum = MapUtils.getString(map, "deviceNum");
            if(StringUtils.isBlank(deviceNum)){
                deviceNum = "0";
            }
            map.put("totalPaid", totalPaid);
            map.put("totalUsers", totalUsers);
            map.put("payUsers", payUsers);
            map.put("pkgDays", pkgDays);
            map.put("pkgMonths", pkgMonths);
            map.put("pkgYears", pkgYears);
            map.put("merchantNum", merchantNum);
            map.put("deviceNum", deviceNum);
        }
        if(StringUtils.isNotBlank(hasTotal) && "true".equals(hasTotal)){
//            List<HashMap<String, Object>> totalList = new ArrayList<>();
            HashMap<String, Object> totalMap = new HashMap<>();//合计
            totalMap.put("totalPaid", 0d);
            totalMap.put("totalUsers", 0);
            totalMap.put("payUsers", 0);
            totalMap.put("pkgDays", 0);
            totalMap.put("pkgMonths", 0);
            totalMap.put("pkgYears", 0);
            totalMap.put("merchantNum", 0);
            totalMap.put("deviceNum", 0);
            totalMap.put("areaName", "合计");
            totalMap.put("hasChild", false);
            for(HashMap<String, Object> map : result){
                if(StringUtils.isBlank(MapUtils.getString(map, "totalPaid"))){
                    
                }
                BigDecimal totalPaid = new BigDecimal(MapUtils.getString(map, "totalPaid"));
                BigDecimal totalUsers = new BigDecimal(MapUtils.getString(map, "totalUsers"));
                BigDecimal payUsers = new BigDecimal(MapUtils.getString(map, "payUsers"));
                BigDecimal pkgDays = new BigDecimal(MapUtils.getString(map, "pkgDays"));
                BigDecimal pkgMonths = new BigDecimal(MapUtils.getString(map, "pkgMonths"));
                BigDecimal pkgYears = new BigDecimal(MapUtils.getString(map, "pkgYears"));
                BigDecimal merchantNum = new BigDecimal(MapUtils.getString(map, "merchantNum"));
                BigDecimal deviceNum = new BigDecimal(MapUtils.getString(map, "deviceNum"));
            
                totalMap.put("totalPaid",  new BigDecimal(MapUtils.getString(totalMap, "totalPaid")).add(totalPaid));
                totalMap.put("totalUsers",  new BigDecimal(MapUtils.getString(totalMap, "totalUsers")).add(totalUsers));
                totalMap.put("payUsers",  new BigDecimal(MapUtils.getString(totalMap, "payUsers")).add(payUsers));
                totalMap.put("pkgDays",  new BigDecimal(MapUtils.getString(totalMap, "pkgDays")).add(pkgDays));
                totalMap.put("pkgMonths",  new BigDecimal(MapUtils.getString(totalMap, "pkgMonths")).add(pkgMonths));
                totalMap.put("pkgYears",  new BigDecimal(MapUtils.getString(totalMap, "pkgYears")).add(pkgYears));
                totalMap.put("merchantNum",  new BigDecimal(MapUtils.getString(totalMap, "merchantNum")).add(merchantNum));
                totalMap.put("deviceNum",  new BigDecimal(MapUtils.getString(totalMap, "deviceNum")).add(deviceNum));
            }
            result.add(0, totalMap);
        }
        logger.debug(result);
        
        return this.successMsg(result);
    }
    /**
     * 导出xls文件
     * @param params
     * @param request
     * @param response
     * @throws Exception
     * @author 余红伟 
     * @date 2017年8月9日 下午2:51:16
     */
    @SuppressWarnings("unchecked")
//    @API(summary = "查询", description = "区域维度查询",parameters = {
//            @Param(name = "areaId", description = "区域id, 用-连接,31-383-3232，代表浙江杭州拱墅区,可为空",dataType = DataType.STRING,required = false),
//            @Param(name = "beginDate", description = "开始时间",dataType = DataType.STRING,required = false),
//            @Param(name = "endDate", description = "结束时间",dataType = DataType.STRING,required = false),
//            @Param(name = "hasTotal", description = "是否返回合计", dataType = DataType.STRING,required = false)
//    })
    @RequestMapping(value = "/location/multiple/xls", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public void export(@RequestParam(value = "params",required = true) String params,HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.debug(params);
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String beginDate = MapUtils.getString(paramsMap, "beginDate");
        String endDate = MapUtils.getString(paramsMap, "endDate");
        
        String areaId = MapUtils.getString(paramsMap, "areaId");
        String hasTotal = MapUtils.getString(paramsMap, "hasTotal");
        ValidUtil.valid("开始时间", beginDate, "{'required':true}");
        ValidUtil.valid("结束时间", endDate, "{'required':true}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate) && sdf.parse(beginDate).getTime() > sdf.parse(endDate).getTime()){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        String[] locationArr = null;
        int size = 0;
        if(StringUtils.isNotBlank(areaId)){
            locationArr = areaId.split("-");
            size = locationArr.length;
        }
        Long provinceId = null;
        Long cityId = null;
        Long countyId = null;

        if(size > 0){
            provinceId = CastUtil.toLong(locationArr[0]);
            ValidUtil.valid("省id", provinceId, "{'required':true,'numeric':true}");
        }
        if(size > 1){
            cityId = CastUtil.toLong(locationArr[1]);
            ValidUtil.valid("市id", cityId, "{'required':true,'numeric':true}");
        }
        if(size > 2){
            countyId = CastUtil.toLong(locationArr[2]);
            ValidUtil.valid("区id", countyId, "{'required':true,'numeric':true}");
        }
      //数据权限，超级管理员省市区id都为空， 前端传入省市区参数，后端把有参数的全部换成session里的
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
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
        paramsMap.put("provinceId", provinceId);
        paramsMap.put("cityId", cityId);
        paramsMap.put("areaId", countyId);
        List<HashMap<String, Object>> result = reportFormService.queryListByParam(paramsMap);
        for(HashMap<String, Object> map : result){
            if(size< 3){
                map.put("hasChild", true);
            }else{
                map.put("hasChild", false);
            }
        }
        //为空数据用0补全,数据库返回回来的数据都是BigDecimal类型
        for(HashMap<String, Object> map : result){
            String totalPaid = MapUtils.getString(map, "totalPaid");
            if(StringUtils.isBlank(totalPaid)){
                totalPaid = "0";
            }
            String totalUsers = MapUtils.getString(map, "totalUsers");
            if(StringUtils.isBlank(totalUsers)){
                totalUsers = "0";
            }
            String payUsers = MapUtils.getString(map, "payUsers");
            if(StringUtils.isBlank(payUsers)){
                payUsers = "0";
            }
            String pkgDays = MapUtils.getString(map, "pkgDays");
            if(StringUtils.isBlank(pkgDays)){
                pkgDays = "0";
            }
            String pkgMonths = MapUtils.getString(map, "pkgMonths");
            if(StringUtils.isBlank(pkgMonths)){
                pkgMonths = "0";
            }
            String pkgYears = MapUtils.getString(map, "pkgYears");
            if(StringUtils.isBlank(pkgYears)){
                pkgYears = "0";
            }
            String merchantNum = MapUtils.getString(map, "merchantNum");
            if(StringUtils.isBlank(merchantNum)){
                merchantNum = "0";
            }
            String deviceNum = MapUtils.getString(map, "deviceNum");
            if(StringUtils.isBlank(deviceNum)){
                deviceNum = "0";
            }
            map.put("totalPaid", totalPaid);
            map.put("totalUsers", totalUsers);
            map.put("payUsers", payUsers);
            map.put("pkgDays", pkgDays);
            map.put("pkgMonths", pkgMonths);
            map.put("pkgYears", pkgYears);
            map.put("merchantNum", merchantNum);
            map.put("deviceNum", deviceNum);
        }
        if(StringUtils.isNotBlank(hasTotal) && "true".equals(hasTotal)){
//            List<HashMap<String, Object>> totalList = new ArrayList<>();
            HashMap<String, Object> totalMap = new HashMap<>();//合计
            totalMap.put("totalPaid", 0d);
            totalMap.put("totalUsers", 0);
            totalMap.put("payUsers", 0);
            totalMap.put("pkgDays", 0);
            totalMap.put("pkgMonths", 0);
            totalMap.put("pkgYears", 0);
            totalMap.put("merchantNum", 0);
            totalMap.put("deviceNum", 0);
            totalMap.put("areaName", "合计");
            totalMap.put("hasChild", false);
            for(HashMap<String, Object> map : result){
                if(StringUtils.isBlank(MapUtils.getString(map, "totalPaid"))){
                    
                }
                BigDecimal totalPaid = new BigDecimal(MapUtils.getString(map, "totalPaid"));
                BigDecimal totalUsers = new BigDecimal(MapUtils.getString(map, "totalUsers"));
                BigDecimal payUsers = new BigDecimal(MapUtils.getString(map, "payUsers"));
                BigDecimal pkgDays = new BigDecimal(MapUtils.getString(map, "pkgDays"));
                BigDecimal pkgMonths = new BigDecimal(MapUtils.getString(map, "pkgMonths"));
                BigDecimal pkgYears = new BigDecimal(MapUtils.getString(map, "pkgYears"));
                BigDecimal merchantNum = new BigDecimal(MapUtils.getString(map, "merchantNum"));
                BigDecimal deviceNum = new BigDecimal(MapUtils.getString(map, "deviceNum"));
            
                totalMap.put("totalPaid",  new BigDecimal(MapUtils.getString(totalMap, "totalPaid")).add(totalPaid));
                totalMap.put("totalUsers",  new BigDecimal(MapUtils.getString(totalMap, "totalUsers")).add(totalUsers));
                totalMap.put("payUsers",  new BigDecimal(MapUtils.getString(totalMap, "payUsers")).add(payUsers));
                totalMap.put("pkgDays",  new BigDecimal(MapUtils.getString(totalMap, "pkgDays")).add(pkgDays));
                totalMap.put("pkgMonths",  new BigDecimal(MapUtils.getString(totalMap, "pkgMonths")).add(pkgMonths));
                totalMap.put("pkgYears",  new BigDecimal(MapUtils.getString(totalMap, "pkgYears")).add(pkgYears));
                totalMap.put("merchantNum",  new BigDecimal(MapUtils.getString(totalMap, "merchantNum")).add(merchantNum));
                totalMap.put("deviceNum",  new BigDecimal(MapUtils.getString(totalMap, "deviceNum")).add(deviceNum));
            }
            result.add(0, totalMap);
        }
        logger.debug(result);
        String fileName = "userConsumeExport.xls";
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator +"temp");
        createExcel(path, result, fileName);
        IOUtil.download(fileName, path, response);
    }
    public void createExcel(String path,List<HashMap<String, Object>> list,String fileName) throws Exception{
        if(!new File(path).exists()){
            new File(path).mkdirs();
        }
        logger.debug(path);
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("园区统计", 1);
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED);//设置标题样式
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        
        sheet.addCell(new Label(0, 0, "地区", titleFormat));//设置title
        sheet.addCell(new Label(1, 0, "园区数量", titleFormat));
        sheet.addCell(new Label(2, 0, "设备数量", titleFormat));
        sheet.addCell(new Label(3, 0, "付费总金额", titleFormat));
        sheet.addCell(new Label(4, 0, "总人次", titleFormat));
        sheet.addCell(new Label(5, 0, "白名单人数", titleFormat));
        sheet.addCell(new Label(6, 0, "付费次数", titleFormat));
        sheet.addCell(new Label(7, 0, "包天次数", titleFormat));
        sheet.addCell(new Label(8, 0, "包月次数", titleFormat));
        sheet.addCell(new Label(9, 0, "包年次数", titleFormat));
        int maxSize = list.size();
        for(int i=0;i<maxSize;i++){
           HashMap<String, Object> map = list.get(i);
           sheet.addCell(new Label(0,i+1,MapUtils.getString(map, "areaName")));
           sheet.addCell(new Label(1,i+1,MapUtils.getString(map, "merchantNum")));
           sheet.addCell(new Label(2,i+1,MapUtils.getString(map, "deviceNum")));
           sheet.addCell(new Label(3,i+1,MapUtils.getString(map, "totalPaid")));
           sheet.addCell(new Label(4,i+1,MapUtils.getString(map, "totalUsers")));
           sheet.addCell(new Label(5,i+1,MapUtils.getString(map, "vipUsers")));
           sheet.addCell(new Label(6,i+1,MapUtils.getString(map, "payUsers")));
           sheet.addCell(new Label(7,i+1,MapUtils.getString(map, "pkgDays")));
           sheet.addCell(new Label(8,i+1,MapUtils.getString(map, "pkgMonths")));
           sheet.addCell(new Label(9,i+1,MapUtils.getString(map, "pkgYears")));
        }
        book.write();
        book.close();
    }
   public static void main(String[] args) {
    String s = "-1";
//    String s = null;
    String[] sa = s.split("-");
    System.out.println(sa[0]+ " " + sa[1]);
    String regex = "\\d";
    String key = "a.";
//    Pattern p = Pattern.compile(regex +"+");
    System.out.println(regex +"+");
//    Pattern p = Pattern.compile("\\d+");1
    Pattern p = Pattern.compile("^[a-z]\\.$");
    Matcher m = p.matcher(key);
    System.out.println(m.matches());
    String versions = "V.11.11";
    ValidUtil.valid("版本: ", versions, "{'required':true,'regex':'^V[0-9]*\\\\.[0-9]+\\\\.[0-9]+$'}");
}
}
