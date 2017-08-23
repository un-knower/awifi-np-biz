/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 上午10:15:28
* 创建作者：余红伟
* 文件名称：VipUserController.java
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
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;
import com.awifi.np.biz.timebuysrv.web.module.time.service.CenterVipUserServiceImpl;
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

@APIs(description = "VIP用户接口")
@Controller
@RequestMapping("/timebuysrv/vip")
public class VipUserController extends BaseController{
    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource
    private CenterVipUserServiceImpl centerVipUserService;
    /**
     * VIP用户列表
     * @param request
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年4月24日 上午10:53:16
     */
    
    @SuppressWarnings("unchecked")
//    @API(summary = "分页查询VIP用户", description = "根据手机号查询VIP用户列表", parameters = {
//        @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//        @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//        @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//        @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//        @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//        @Param(name = "startTime", description = "开始时间", dataType = DataType.STRING, required = false),
//        @Param(name = "endTime", description = "结束时间", dataType = DataType.STRING, required = false),
//        @Param(name = "pageNum", description = "页码", dataType = DataType.INTEGER, required = true),
//        @Param(name = "pageSize", description = "每页条数", dataType = DataType.INTEGER, required = true)
//    })
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String,Object> queryListByParam(@RequestParam(value = "params", required = true) String params,HttpServletRequest request) throws Exception{
        logger.debug("vip查询 params: " + params);
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
        Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
//        Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
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
        map.put("telephone", telephone);
        map.put("merchantId", merchantId);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", countyId);
        if(StringUtils.isNotBlank(startTime)){
            map.put("startTime",DateUtil.getDateMills(startTime) );//除以了1000
        }
        if(StringUtils.isNotBlank(endTime)){
            map.put("endTime", DateUtil.getDateMills(endTime));
        }
        if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
            throw new ValidException("E5056002","分页参数错误");
        }
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        //开始记录数
        Integer start = (pageNum-1)*pageSize;
        map.put("start", start);
        
        logger.debug(JSON.toJSONString(map));
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
            logger.error("开始时间不能大于结束时间");
            throw new ValidException("E5056001","开始时间不能小于结束时间");
        }
        
        List<VipUserObject> list = centerVipUserService.queryListByParam(map);
        logger.debug("获取VIP用户条数: -- " + list.size());
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        int count = centerVipUserService.queryVipUserCount(map);
        resultMap.put("totalRecord", count);
        resultMap.put("totalPage", count%pageSize==0?count/pageSize:count/pageSize+1);
        resultMap.put("records",list );
        return this.successMsg(resultMap);
    }
    @SuppressWarnings("unchecked")
//    @API(summary = "导出白名单xls", description = "根据手机号查询VIP用户列表", parameters = {
//            @Param(name = "telephone", description = "手机号码", dataType = DataType.LONG, required = false),
//            @Param(name = "merchantId", description = "商户ID", dataType = DataType.LONG, required = false),
//            @Param(name = "provinceId", description = "省id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "cityId", description = "市id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "countyId", description = "区id", dataType = DataType.INTEGER, required = false),
//            @Param(name = "startTime", description = "开始时间", dataType = DataType.STRING, required = false),
//            @Param(name = "endTime", description = "结束时间", dataType = DataType.STRING, required = false),
//        })
        @RequestMapping(value = "/xls", method = RequestMethod.GET, produces = "application/json")
        @ResponseBody
        public void vipExport(@RequestParam(value = "params", required = true) String params,
                HttpServletRequest request,HttpServletResponse response) throws Exception{
            logger.debug("vip导出 params: " + params);
            Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
            Long telephone = CastUtil.toLong(MapUtils.getString(paramsMap, "telephone"));
            Long merchantId = CastUtil.toLong(MapUtils.getString(paramsMap, "merchantId"));
//            Long userId = CastUtil.toLong(MapUtils.getString(paramsMap, "userId"));
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
            map.put("telephone", telephone);
            map.put("merchantId", merchantId);
            map.put("provinceId", provinceId);
            map.put("cityId", cityId);
            map.put("areaId", countyId);
            if(StringUtils.isNotBlank(startTime)){
                map.put("startTime",DateUtil.getDateMills(startTime) );//除以了1000
            }
            if(StringUtils.isNotBlank(endTime)){
                map.put("endTime", DateUtil.getDateMills(endTime));
            }
            if(pageNum==null || pageNum <=0||pageSize == null || pageSize<=0){
                throw new ValidException("E5056002","分页参数错误");
            }
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            //开始记录数
            Integer start = (pageNum-1)*pageSize;
            map.put("start", start);
            
            logger.debug(JSON.toJSONString(map));
            if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && DateUtil.getDateMills(startTime)>DateUtil.getDateMills(endTime)){
                logger.error("开始时间不能大于结束时间");
                throw new ValidException("E5056001","开始时间不能小于结束时间");
            }
            
            List<VipUserObject> list = centerVipUserService.queryListByParam(map);
            String fileName = "vip.xls";
            String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");
            createVipXls(path, list, fileName);
            IOUtil.download(fileName, path, response);
        }
    private static void createVipXls(String path, List<VipUserObject> data, String fileName ) throws Exception{
        if(!new File(path).exists()){
            new File(path).mkdirs();
        }
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        WritableSheet sheet = book.createSheet("Vip记录", 1);//创建工作簿
        
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);//标题字体
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);//设置标题样式
        titleFormat.setAlignment(Alignment.CENTRE);//水平居中
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直居中
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(Alignment.CENTRE);//水平居中
        
        sheet.addCell(new jxl.write.Label(0, 0, "用户帐户", titleFormat));
        sheet.addCell(new jxl.write.Label(1, 0, "园区名称", titleFormat));
        sheet.addCell(new jxl.write.Label(2, 0, "园区ID", titleFormat));
        sheet.addCell(new jxl.write.Label(3, 0, "地区", titleFormat));
        sheet.addCell(new jxl.write.Label(4, 0, "免费市场起始时间", titleFormat));
        sheet.addCell(new jxl.write.Label(5, 0, "免费时长结束时间", titleFormat));
        sheet.addCell(new jxl.write.Label(6, 0, "受理时间", titleFormat));
        for(int i=0;i<data.size();i++){
            VipUserObject vipUser = data.get(i);
            sheet.addCell(new jxl.write.Label(0, i+1, vipUser.getTelephone(), textFormat));
            sheet.addCell(new jxl.write.Label(1, i+1, vipUser.getMerchantName(), textFormat));
            sheet.addCell(new jxl.write.Label(2, i+1, CastUtil.toString(vipUser.getMerchantId()), textFormat));
            sheet.addCell(new jxl.write.Label(3, i+1, vipUser.getProvinceName() +","+ vipUser.getCityName()+"-"+vipUser.getCountyName(), textFormat));
            sheet.addCell(new jxl.write.Label(4, i+1, vipUser.getStartTimeStr(), textFormat));
            sheet.addCell(new jxl.write.Label(5, i+1, vipUser.getEndTimeStr(), textFormat));
            sheet.addCell(new jxl.write.Label(6, i+1, vipUser.getCreateDateStr(), textFormat));
        }
        book.write();
        book.close();
    }
    /**
     * 测试所需
     * 
     * @param controller
     * @author 余红伟 
     * @date 2017年4月25日 上午8:56:00
     */
    public void setVipUserController(CenterVipUserServiceImpl controller){
        this.centerVipUserService = controller;
    }
}
