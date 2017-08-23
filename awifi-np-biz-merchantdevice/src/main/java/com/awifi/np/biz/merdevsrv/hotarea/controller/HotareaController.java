package com.awifi.np.biz.merdevsrv.hotarea.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.api.client.dbcenter.device.hotarea.util.HotareaClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.merdevsrv.hotarea.service.HotareaService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:02:09
 * 创建作者：亢燕翔
 * 文件名称：HotareaController.java
 * 版本：  v1.0
 * 功能：  热点控制层
 * 修改记录：
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Controller
@RequestMapping("/merdevsrv")
public class HotareaController extends BaseController{

    /**热点业务层*/
    @Resource(name = "hotareaService")
    private HotareaService hotareaService;
    
    /**
     * 热点管理分页列表
     * @param request 请求
     * @param access_token 安全令牌
     * @param params 请求参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午10:04:00
     */
    @ResponseBody
    @RequestMapping(value="/hotareas", method=RequestMethod.GET)
    public Map getListByParam(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Page<Hotarea> page = new Page<Hotarea>();
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        hotareaService.getListByParam(sessionUser,page,params);
        return this.successMsg(page);
    }
    
    /**
     * 导出热点
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午11:19:30
     */
    @RequestMapping(value="/hotareas/xls", method=RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        Map<String, Object> dbParams = getDbParams(sessionUser,params);
        Integer maxSize = 0;//数据的最大长度
        Integer sheetNum = 1;//sheet编号
        String sheetName = null;//sheet名称
        List<Object[]> listObj = new ArrayList<Object[]>();
        String fileName = "hotareaExport.xls";//文件名称
        String[] rowName = {"序号","MAC地址","热点名称","客户名称","设备状态"};//设置列表名称
        Integer pageSize = Integer.valueOf(SysConfigUtil.getParamValue("xls_export_sheet_max_size")).intValue();//每页数量--工作表每页数量
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        do{
            dbParams.put("pageNum", sheetNum);
            dbParams.put("pageSize", pageSize);
            listObj = getListObj(dbParams);//获取数据
            maxSize = listObj.size();//数据长度
            sheetName = (((sheetNum - 1) * pageSize)+1)+"--"+(sheetNum * pageSize);
            ExcelUtil.fileWriteData(book,sheetName,sheetNum,rowName,listObj);//写文件内容
            sheetNum ++;
        } while (pageSize == maxSize);
        book.write();
        book.close();
        IOUtil.download(fileName, path, response);
    }
    
    /**
     * 获取请求参数
     * @param sessionUser 
     * @param params 
     * @return map
     * @author 亢燕翔  
     * @date 2017年2月13日 下午5:02:55
     */
    private Map<String, Object> getDbParams(SessionUser sessionUser, String params) {
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户ID
        String hotareaName = (String) paramsMap.get("hotareaName");//热点名称
        String devMac = (String) paramsMap.get("devMac");//设备mac
        Integer status = CastUtil.toInteger(paramsMap.get("status"));//状态
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省ID
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));//市ID
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));//区县ID
        
        Map<String, Object> permissionMap = PermissionUtil.dataPermission(sessionUser, merchantId, provinceId, cityId, areaId, null, null);//获取数据权限信息
        Long merchantIdLong = CastUtil.toLong(permissionMap.get("merchantId"));//商户id
        String type = (String) permissionMap.get("type");//层级关系
        Long provinceIdLong = CastUtil.toLong(permissionMap.get("provinceId"));//省id
        Long cityIdLong = CastUtil.toLong(permissionMap.get("cityId"));//市id
        Long areaIdLong = CastUtil.toLong(permissionMap.get("areaId"));//区县id
        
        Map<String, Object> dbParams = new HashMap<String, Object>();
        dbParams.put("merchantId", merchantIdLong);//商户id
        dbParams.put("merchantQueryType", type);//this只查当前节点（默认）;nextLevel只查当前节点的下一层;nextAll查当前节点所有不包括当前;nextAllWithThis查当前节点所有包含当前
        dbParams.put("province", provinceIdLong);//省id
        dbParams.put("city", cityIdLong);//市id
        dbParams.put("county", areaIdLong);//区县id
        dbParams.put("hotareaName", hotareaName);//热点名称
        dbParams.put("macAddr", devMac);//mac地址
        dbParams.put("status", status);//状态
        return dbParams;
    }
    
    /**
     * 获取热点列表
     * @param dbParams 
     * @return list
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午2:59:05
     */
    public List<Object[]> getListObj(Map<String, Object> dbParams) throws Exception {
        int begin = 1;
        List<Hotarea> hotareaList = HotareaClient.getListByParam(JsonUtil.toJson(dbParams));
        Object[] objs = null;
        List<Object[]> listObj = new ArrayList<Object[]>();
        for(Hotarea hotarea : hotareaList){//遍历数据，重新封装
            objs = new Object[5];
            objs[0] = begin++;
            objs[1] = hotarea.getDevMac();
            objs[2] = hotarea.getHotareaName();
            objs[3] = hotarea.getMerchantName();
            objs[4] = hotarea.getStatusDsp();
            listObj.add(objs);
        }
        return listObj;
    }
}
