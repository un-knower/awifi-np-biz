package com.awifi.np.biz.statsrv.stat.controller;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.statsrv.stat.service.PortalPVStatService;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月27日 上午10:54:37
 * 创建作者：许尚敏
 * 文件名称：PortalPVStatController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/statsrv/portal")
public class PortalPVStatController extends BaseController{
    /** Portal页面-商户维度服务 */
    @Resource(name = "portalPVStatService")
    private PortalPVStatService portalPVStatService;
    
    /**项目服务*/
    @Resource(name = "projectService")
    private ProjectService projectService;
    
    /**项目名称集合*/
    private Map<Integer, String> projectNames = new HashMap<Integer, String>();
    
    /**
     * Portal页面-商户维度-统计接口
     * @param access_token 安全令牌
     * @param params 请求参数
     * @throws Exception 异常
     * @return json
     * @author 许尚敏 
     * @date 2017年7月27日 上午10:54:37
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/pv/merchant", method=RequestMethod.GET)
    public Map<String, Object> getByMerchant(
            @RequestParam(value="access_token",required=true)String access_token,//安全令牌，不允许为空
            @RequestParam(value="params",required=true)String params//请求参数
    ) throws Exception{
        logger.debug("----------提示：开始执行Portal页面-商户维度-统计接口  PortalPVStatController.getByMerchant()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json
        Integer pageSize = CastUtil.toInteger(paramMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");
        /* 参数校验 */
        String beginDate = (String)paramMap.get("beginDate");//开始日期，不允许为空
        String endDate = (String)paramMap.get("endDate");//截至日期，不允许为空
        Integer projectId = CastUtil.toInteger(paramMap.get("projectId"));//项目id，数字，不允许为空
        ValidUtil.valid("开始日期[beginDate]", beginDate, "required");//开始日期必填校验
        ValidUtil.valid("截至日期[endDate]", endDate, "required");//开始日期必填校验
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//项目id校验
        if(DateUtil.daySubDay(beginDate, endDate)<0){
            throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",new Object[]{beginDate,endDate}));//开始日期[{0}]不能大于截止日期[{1}]!
        }
        Integer pageNo = CastUtil.toInteger(paramMap.get("pageNo"));//页码
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageNo, pageSize);
        portalPVStatService.getByMerchant(page, paramMap);//返回结果集
        logger.debug("----------提示：Portal页面-商户维度-统计接口执行成 功  PortalPVStatController.getByMerchant(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
        return this.successMsg(page);//返回成功信息
    }
    
    /**
     * portal页面-地区维度趋势图
     * @param request 请求
     * @param access_token 令牌 
     * @param params 参数
     * @throws Exception 异常
     * @return json数据
     * @throws Exception
     * @author 王冬冬  
     * @date 2017年7月27日 上午9:00:15
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value="/pvtrend/area", method=RequestMethod.GET)
    public Map getTrendByArea(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map map=JsonUtil.fromJson(params, Map.class);
        
        String beginDate=(String) map.get("beginDate");//开始日期
        String endDate=(String) map.get("endDate");//结束日期
        Integer provinceId=CastUtil.toInteger(map.get("provinceId"));//省id
        Integer cityId=CastUtil.toInteger(map.get("cityId"));//市id
        Integer areaId=CastUtil.toInteger(map.get("areaId"));//区id
        String bizType = (String) map.get("bizType");//设备类型
        ValidUtil.valid("beginDate",beginDate, "required");//beginDate必填
        ValidUtil.valid("endDate",endDate, "required");//endDate必填
        ValidUtil.valid("bizType",bizType, "required");//bizType必填
        
//        bizType=transform(bizType);//业务类型转换

        if(DateUtil.daySubDay(beginDate, endDate)<0){
            throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",new Object[]{beginDate,endDate}));//开始日期[{0}]不能大于截止日期[{1}]!
        }
        
        String today=DateUtil.getTodayDate();
        char timeUnit='D';//D 代表天
        
        if(today.equals(beginDate)&&today.equals(endDate)){//beginDate==endDate==今天日期
            timeUnit='H'; //H 代表小时
        }
        Map<String, Object> result = portalPVStatService.getTrendByArea(beginDate,endDate,provinceId,cityId,areaId,bizType,timeUnit);//查询数据
        
        return this.successMsg(result);
    }
//    /**
//     * @param bizType 业务类型
//     * @return bizType
//     * @author 王冬冬  
//     * @date 2017年8月21日 上午10:58:36
//     */
//    private String transform(String bizType) {
//        if("TOE".equalsIgnoreCase(bizType)){
//            bizType="PRO";
//            return bizType;
//        }
//        if("MWS".equalsIgnoreCase(bizType)||"MSP".equalsIgnoreCase(bizType)||"MWH".equalsIgnoreCase(bizType)){
//            bizType="STD"; 
//            return bizType;
//        }
//        return null; 
//    }

    /**
     * portal页面-地区维度统计
     * @param request 请求
     * @param access_token 令牌
     * @param params 参数
     * @return json
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月31日 上午9:47:58
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value="/pv/area", method=RequestMethod.GET)
    public Map getByArea(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map map=JsonUtil.fromJson(params, Map.class);
        
        String beginDate=(String) map.get("beginDate");//开始日期
        String endDate=(String) map.get("endDate");//结束日期
        String areaId=CastUtil.toString(map.get("areaId"));//区id
        String bizType=(String) map.get("bizType");//业务类型
        Boolean hasTotal=(Boolean) map.get("hasTotal");//业务类型
        
        Long province=null;
        Long city=null;
        Long country=null;
        ValidUtil.valid("beginDate",beginDate, "required");//beginDate必填
        ValidUtil.valid("endDate",endDate, "required");//endDate必填
        ValidUtil.valid("hasTotal",hasTotal, "required");//hasTotal必填
        ValidUtil.valid("bizType",bizType, "required");//bizType必填
//
//        bizType=transform(bizType);//业务类型转换
        if(DateUtil.daySubDay(beginDate, endDate)<0){
            throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",new Object[]{beginDate,endDate}));//开始日期[{0}]不能大于截止日期[{1}]!
        }
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        Long [] areaParams=new Long[3];
        if(StringUtils.isNoneBlank(areaId)){
            String[] areas=areaId.split("-");
            for(int i=0;i<areas.length;i++){
                areaParams[i]=CastUtil.toLong(areas[i]);
                if(i>=2){
                    break;
                }
            }
            province=areaParams[0];
            city=areaParams[1];
            country=areaParams[2];
        }
        
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser, null,province,city,country, null, null);//获取数据权限信息
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));
        
        List<Map<String, Object>> resultPvList=portalPVStatService.getByArea(beginDate,endDate,provinceIdLong,cityIdLong,areaIdLong,bizType,hasTotal);//查询数据
        return this.successMsg(resultPvList);
    }
    
    /**
     * portal-地区维度导出
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param params 参数
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月1日 上午10:24:10
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value="/pv/area/xls", method=RequestMethod.GET)
    public void exportByArea(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map map=JsonUtil.fromJson(params, Map.class);
        String beginDate=(String) map.get("beginDate");//开始日期
        String endDate=(String) map.get("endDate");//结束日期
        String areaId=CastUtil.toString(map.get("areaId"));//区id
        String bizType=(String) map.get("bizType");//业务类型
        ValidUtil.valid("beginDate",beginDate, "required");//beginDate必填
        ValidUtil.valid("endDate",endDate, "required");//endDate必填
        ValidUtil.valid("bizType",bizType, "required");//bizType必填

        Long province=null;
        Long city=null;
        Long country=null;
//        bizType=transform(bizType);//业务类型转换
        if(DateUtil.daySubDay(beginDate, endDate)<0){
            throw new ValidException("E2000062", MessageUtil.getMessage("E2000062",new Object[]{beginDate,endDate}));//开始日期[{0}]不能大于截止日期[{1}]!
        }
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        Long [] areaParams=new Long[3];//存放省市区三个字段
        if(StringUtils.isNoneBlank(areaId)){
            String[] areas=areaId.split("-");
            for(int i=0;i<areas.length;i++){
                areaParams[i]=CastUtil.toLong(areas[i]);
                if(i>=2){
                    break;
                }
            }
            province=areaParams[0];//省
            city=areaParams[1];//市
            country=areaParams[2];//区
        }
        
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser, null,province,city,country, null, null);//获取数据权限信息
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));
     
        setDataToSheet(request, response, beginDate, endDate, bizType, provinceIdLong, cityIdLong, areaIdLong);//将数据写入excel
    }

    /**
     * 将数据写入excel
     * @param request 请求
     * @param response 响应
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param bizType 业务类型
     * @param provinceIdLong 省Id
     * @param cityIdLong 市id
     * @param areaIdLong 区id
     * @throws Exception 异常
     * @throws IOException io异常
     * @throws WriteException 异常
     * @throws RowsExceededException 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午7:14:01
     */
    private void setDataToSheet(HttpServletRequest request, HttpServletResponse response, String beginDate,
            String endDate, String bizType, Long provinceIdLong, Long cityIdLong, Long areaIdLong)
            throws Exception, IOException, WriteException, RowsExceededException {
        Character statType=getStatType(provinceIdLong,cityIdLong,areaIdLong);//根据省市区获取statType
        List<Map<String, Object>> resultPvList=portalPVStatService.getByArea(beginDate,endDate,provinceIdLong,cityIdLong,areaIdLong,bizType,true);//查询数据
        
        String fileName = "portalPVAreaExport.xls";//文件名称
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("Portal页面-地区维度",1);// 创建工作表
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);//设置标题字体
        WritableCellFormat titleFormat = setTitleStyle(titleFont);//设置标题样式
        WritableCellFormat textFormat = setTextStyle();//设备文本样式
        
        setColumnHeadAndFirstLine(beginDate, endDate, bizType, sheet, titleFormat, textFormat);//设置表格每列标题以及第一行
        setExcelAreaNameByStatType(statType, sheet, titleFormat);//设置第二行第一列，根据不同statType设置不同的地区名称
        putDataintoSheet(provinceIdLong, cityIdLong, statType, resultPvList, sheet, textFormat);//插入数据
        book.write();//写数据
        book.close();//关闭
        IOUtil.download(fileName, path, response);//下载
    }

    /**
     * 设置表格每列标题以及第一行
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param bizType 业务类型
     * @param sheet 表格
     * @param titleFormat 标题格式
     * @param textFormat 文本格式
     * @throws WriteException 异常
     * @throws RowsExceededException 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午8:03:26
     */
    private void setColumnHeadAndFirstLine(String beginDate, String endDate, String bizType, WritableSheet sheet,
            WritableCellFormat titleFormat, WritableCellFormat textFormat)
            throws WriteException, RowsExceededException {
        String cellDateTime=getCellDateTime(beginDate,endDate);//获取显示的时间
        sheet.addCell(new Label(0, 0, "时间: "+cellDateTime+"    业务类型："+getBizTypeName(bizType), textFormat));//设置title
        sheet.mergeCells(0, 0, 5,0);//合并单元格
        
        String [] columnHeads=new String[]{"引导页浏览量","认证页浏览量(PV)","过渡浏览量","导航浏览量","浏览量总计"};//excel标题头
        for(int i=0;i<=5;i++){
            sheet.setColumnView(i, 15);//设置每列表格宽度(15)
            if(i!=0){
                sheet.addCell(new Label(i, 1, columnHeads[i-1], titleFormat));//插入标题(第一列单独处理) 
            }
        }
    }

    /**
     * 获取bizType
     * @param bizType 业务类型
     * @return bizTypeName
     * @author 王冬冬  
     * @date 2017年8月21日 下午7:26:59
     */
    private String getBizTypeName(String bizType) {
        String bizTypeName=null;
        switch(bizType){
            case "PRO":
                bizTypeName="项目型";
                break;
            case "STD":
                bizTypeName="标准型";
                break;
            case "MWS":
                bizTypeName="微站";
                break;
            case "MSP":
                bizTypeName="园区";
                break;  
            case "MWH":
                bizTypeName="酒店";
                break;
            default:
                break;
        }
        return bizTypeName;
    }

    /**
     * 数据插入excel单元格
     * @param provinceIdLong 省id
     * @param cityIdLong 市id
     * @param statType 跨度类型
     * @param resultPvList 记录
     * @param sheet 表格
     * @param textFormat 文本类型
     * @throws WriteException 异常
     * @throws RowsExceededException 异常
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午8:00:58
     */
    private void putDataintoSheet(Long provinceIdLong, Long cityIdLong, Character statType,
            List<Map<String, Object>> resultPvList, WritableSheet sheet, WritableCellFormat textFormat)
            throws WriteException, RowsExceededException, Exception {
        int size=resultPvList.size();
        for(int i=0;i<size;i++){
            Map<String,Object> resultpv=resultPvList.get(i);
            Integer pv1=CastUtil.toInteger(resultpv.get("pv1"));
            Integer pv2=CastUtil.toInteger(resultpv.get("pv2"));
            Integer pv3=CastUtil.toInteger(resultpv.get("pv3"));
            Integer pv4=CastUtil.toInteger(resultpv.get("pv4"));
            if(i==0){
                getAreaNameByStatType(provinceIdLong, cityIdLong, statType, sheet, textFormat);//根据statType获取地区名称
                
            }else{
                sheet.addCell(new Label(0, i+2, resultpv.get("areaName").toString(), textFormat));//地区
            }
            sheet.addCell(new Label(1, i+2, pv1.toString(), textFormat));//引导页浏览量(PV)
            sheet.addCell(new Label(2, i+2, pv2.toString(), textFormat));//认证页浏览量
            sheet.addCell(new Label(3, i+2, pv3.toString(), textFormat));//过渡浏览量
            sheet.addCell(new Label(4, i+2, pv4.toString(), textFormat));//导航浏览量
            sheet.addCell(new Label(5, i+2, CastUtil.toString(pv1+pv2+pv3+pv4), textFormat));//浏览量总计
        }
    }
    /**
     * 根据statType获取地区名称
     * @param provinceId 省id
     * @param cityId 市id
     * @param statType 跨度类型
     * @param sheet 表格
     * @param textFormat 文本格式
     * @throws WriteException 异常
     * @throws RowsExceededException 异常
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午7:08:25
     */
    private void getAreaNameByStatType(Long provinceId, Long cityId, Character statType, WritableSheet sheet,
            WritableCellFormat textFormat) throws WriteException, RowsExceededException, Exception {
        switch(statType){
            case 'P':
                sheet.addCell(new Label(0, 2,"全国", textFormat));//地区
                break;
            case 'T':
                String provinceName=LocationClient.getByIdAndParam(provinceId, "name");
                sheet.addCell(new Label(0, 2,provinceName, textFormat));//地区
                break;
            case 'C':
                String cityName=LocationClient.getByIdAndParam(cityId, "name");
                sheet.addCell(new Label(0, 2,cityName, textFormat));//地区
                break;
            default:
                break;
        }
    }

    /**
     * 根据不同statType设置不同的地区名称
     * @param statType 跨度类型
     * @param sheet 表格
     * @param titleFormat 标题格式
     * @throws WriteException 异常
     * @throws RowsExceededException 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午7:04:08
     */
    private void setExcelAreaNameByStatType(Character statType, WritableSheet sheet, WritableCellFormat titleFormat)
            throws WriteException, RowsExceededException {
        switch(statType){
            case 'P':
                sheet.addCell(new Label(0, 1, "省", titleFormat));//设置title
                break;
            case 'T':
                sheet.addCell(new Label(0, 1, "市", titleFormat));//设置title
                break;
            case 'C':
                sheet.addCell(new Label(0, 1, "区", titleFormat));//设置title
                break;
            default:
                break;
        }
    }

    /**
     * 设置文本风格
     * @return textFormat 文本格式
     * @throws WriteException 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午7:04:50
     */
    private WritableCellFormat setTextStyle() throws WriteException {
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        return textFormat;
    }

    /**设置标题风格
     * @param titleFont 标题字体
     * @return titleFormat
     * @throws WriteException 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午7:04:54
     */
    private WritableCellFormat setTitleStyle(WritableFont titleFont) throws WriteException {
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
        return titleFormat;
    }  
    
    /**
     * 拼接用于Excel显示的时间
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 时间
     * @author 王冬冬  
     * @date 2017年8月1日 下午3:52:58
     */
    public static  String getCellDateTime(String beginDate, String endDate) {
        Date startTime=DateUtil.parseToDate(beginDate, DateUtil.YYYY_MM_DD);
        Date endTime=DateUtil.parseToDate(endDate, DateUtil.YYYY_MM_DD);
        Calendar cal=Calendar.getInstance();
        cal.setTime(startTime);
        
        Integer startYear=cal.get(Calendar.YEAR);
        Integer startMonth=cal.get(Calendar.MONTH)+1;
        Integer startDay=cal.get(Calendar.DAY_OF_MONTH);
        
        cal.setTime(endTime);
        Integer endYear=cal.get(Calendar.YEAR);
        Integer endMonth=cal.get(Calendar.MONTH)+1;
        Integer endDay=cal.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb=new StringBuilder();
        sb.append(startYear).append("年/").append(startMonth).append("月/").append(startDay).append("日~").append(endYear).append("年/").append(endMonth).append("月/").append(endDay).append("日");
        return sb.toString();
    }
//    public static void main(String[] args) {
//        getCellDateTime("2017-09-09","2017-09-09");
//    }
    /**
     * 根据省市区获取statType
     * @param provinceId 省Id
     * @param cityId 市id
     * @param areaId 区id
     * @return statType
     * @author 王冬冬  
     * @date 2017年8月1日 下午3:21:19
     */
    private Character getStatType(Long provinceId, Long cityId, Long areaId) {
        Character statType=null;
        if(provinceId==null&&cityId==null&&areaId==null){
            statType='P';//跨度为省
        }
        if(provinceId!=null&&cityId==null&&areaId==null){
            statType='T';//跨度为市
        }
        if(provinceId!=null&&cityId!=null){
            statType='C';//跨度为区
        }
        return statType;
    }

    /**
     * Portal页面-商户维度-折线趋势图接口
     * @param access_token 安全令牌
     * @param params 请求参数
     * @throws Exception 异常
     * @return json
     * @author 许尚敏 
     * @date 2017年7月27日 下午4:14:37
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/pvtrend/merchant", method=RequestMethod.GET)
    public Map<String,Object> getTrendByMerchant(
            @RequestParam(value="access_token",required=true)String access_token,//安全令牌，不允许为空
            @RequestParam(value="params",required=true)String params//请求参数
    ) throws Exception{
        logger.debug("----------提示：开始执行Portal页面-商户维度-统计接口  PortalPVStatController.getTrendByMerchant()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json
        /* 参数校验 */
        String beginDate = (String)paramMap.get("beginDate");//开始日期，不允许为空
        String endDate = (String)paramMap.get("endDate");//截至日期，不允许为空
        Integer projectId = CastUtil.toInteger(paramMap.get("projectId"));//项目id，数字，不允许为空
        ValidUtil.valid("开始日期[beginDate]", beginDate, "required");//开始日期必填校验
        ValidUtil.valid("截至日期[endDate]", endDate, "required");//开始日期必填校验
        if(DateUtil.daySubDay(beginDate, endDate)<0){//如果开始日期大于截止日期，返回错误信息[E2000062]
            throw new BizException("E2000062", MessageUtil.getMessage("E2000062", new Object[]{beginDate, endDate}));//开始日期[{0}]不能大于截止日期[{1}]!
        }
        String today = DateUtil.getTodayDate();
        if(today.equals(beginDate)&&today.equals(endDate)){//beginDate==endDate==今天日期
            paramMap.put("timeUnit", "H");
        }else{
            paramMap.put("timeUnit", "D");
        }
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//项目id校验
        Map<String, List<String>> resultMap = portalPVStatService.getTrendByMerchant(paramMap);//返回结果集
        logger.debug("----------提示：Portal页面-商户维度-统计接口执行成 功  PortalPVStatController.getTrendByMerchant(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
        return this.successMsg(resultMap);//返回成功信息
    }
    
    /**
     * Portal页面-商户维度-导出接口
     * @param access_token 安全令牌
     * @param params 请求参数
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     * @author 许尚敏 
     * @date 2017年7月27日 下午4:24:37
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/pv/merchant/xls", method=RequestMethod.GET)
    public void exportByMerchant(
            @RequestParam(value="access_token",required=true)String access_token,//安全令牌，不允许为空
            @RequestParam(value="params",required=true)String params,//请求参数
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception{
        logger.debug("----------提示：开始执行Portal页面-商户维度-统计接口  PortalPVStatController.exportByMerchant()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json
        /* 参数校验 */
        String beginDate = (String)paramMap.get("beginDate");//开始日期，不允许为空
        String endDate = (String)paramMap.get("endDate");//截至日期，不允许为空
        Integer projectId = CastUtil.toInteger(paramMap.get("projectId"));//项目id，数字，不允许为空
        ValidUtil.valid("开始日期[beginDate]", beginDate, "required");//开始日期必填校验
        ValidUtil.valid("截至日期[endDate]", endDate, "required");//开始日期必填校验
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//项目id校验
        if(DateUtil.daySubDay(beginDate, endDate)<0){//如果开始日期大于截止日期，返回错误信息[E2000062]
            throw new BizException("E2000062", MessageUtil.getMessage("E2000062", new Object[]{beginDate, endDate}));//开始日期[{0}]不能大于截止日期[{1}]!
        }
        Integer pageSize = CastUtil.toInteger(paramMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");
        Integer pageNo = CastUtil.toInteger(paramMap.get("pageNo"));//页码
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageNo, pageSize);
        portalPVStatService.getByMerchant(page, paramMap);//返回结果集
        List<Map<String, Object>> listMap = page.getRecords();
        String fileName = "portalPVMerchantExport.xls";//文件名称
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("用户访问分析_Portal页面_商户纬度",1);// 创建工作表
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);//设置标题样式
        WritableCellFormat titleFormat = setTitleStyle(titleFont);
        WritableCellFormat textFormat = setTextStyle();
        String projectName =null;
        if(projectNames.containsKey(projectId)){// 项目名称是否在map中
            projectName = "  项目名称:" + projectNames.get(projectId);
        }else{
            projectName = projectService.getNameById(CastUtil.toLong(projectId));//通过项目id获取项目名称
            if(projectName!=null){
                projectNames.put(projectId, projectName);
                projectName = "  项目名称:" + projectName;
            }else{
                projectName = "";
            }
        }
        sheet.addCell(new Label(0, 0, "时间:" + beginDate.replace("-", "/") + "-" + endDate.replace("-", "/") + projectName, titleFormat));//设置title
        sheet.mergeCells(0, 0, 5,0);//合并单元格 标题
        sheet.addCell(new Label(0, 1, "商户名称", titleFormat));//设置title
        sheet.addCell(new Label(1, 1, "引导页浏览量", titleFormat));//设置title
        sheet.addCell(new Label(2, 1, "认证页浏览量(PV)", titleFormat));//设置title
        sheet.addCell(new Label(3, 1, "过渡浏览量", titleFormat));//设置title
        sheet.addCell(new Label(4, 1, "导航浏览量", titleFormat));//设置title
        sheet.addCell(new Label(5, 1, "浏览量总计", titleFormat));//设置title

        
        int maxSize = listMap.size();
        Map<String, Object> mapData = null;
        for(int i=0;i<maxSize;i++){
            mapData = listMap.get(i);
            sheet.addCell(new Label(0, i+2, mapData.get("merchantName").toString(), textFormat));//商户名称
            sheet.addCell(new Label(1, i+2, mapData.get("pv1").toString(), textFormat));//项目名称
            sheet.addCell(new Label(2, i+2, mapData.get("pv2").toString(), textFormat));//引导页浏览量(PV)
            sheet.addCell(new Label(3, i+2, mapData.get("pv3").toString(), textFormat));//过渡浏览量
            sheet.addCell(new Label(4, i+2, mapData.get("pv4").toString(), textFormat));//导航浏览量
            int totalNum = CastUtil.toInteger(mapData.get("pv1").toString());
            totalNum += CastUtil.toInteger(mapData.get("pv2").toString());
            totalNum += CastUtil.toInteger(mapData.get("pv3").toString());
            totalNum += CastUtil.toInteger(mapData.get("pv4").toString());
            sheet.addCell(new Label(5, i+2, CastUtil.toString(totalNum), textFormat));//浏览量总计
        }
        book.write();
        book.close();
        IOUtil.download(fileName, path, response);//下载
        
        logger.debug("----------提示：Portal页面-商户维度-统计接口执行成 功  PortalPVStatController.exportByMerchant(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
    }
}
