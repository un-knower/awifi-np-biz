package com.awifi.np.biz.devsrv.hotarea.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.util.HotareaClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.devsrv.hotarea.service.HotareaService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午9:01:20
 * 创建作者：亢燕翔
 * 文件名称：HotareaController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/devsrv")
public class HotareaController extends BaseController{

    /**热点业务层*/
    @Resource(name = "hotareaService")
    private HotareaService hotareaService;
    
    /**
     * 热点批量导入
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月13日 上午9:05:12
     */
    @ResponseBody
    @RequestMapping(value="/hotareas/relation", method=RequestMethod.POST)
    public Map batchAddRelation(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token) throws Exception{
        /*获取sheet*/
        Sheet sheet = ExcelUtil.fileResolver(request);
        /*标题非空校验*/
        Cell hotNameTitle = sheet.getRow(0).getCell(0);
        if(hotNameTitle == null){
            Object[] args = {"1行","A列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        Cell devMacTitle = sheet.getRow(0).getCell(1);
        if(devMacTitle == null){
            Object[] args = {"1行","B列"};
            throw new ValidException("E2000032", MessageUtil.getMessage("E2000032",args));//标题[{0},{1}]不允许为空!
        }
        /*循环数据接受数据*/
        Cell hotNameCell = null;
        String hotName = null;
        Cell devMacCell = null;
        String devMac = null;
        int lastRowNum = sheet.getLastRowNum();//最后一行
        Map<String, String> map = null;
        List<Map<String, String>> hotAreaList = new ArrayList<Map<String, String>>();
        for (int i = 1; i < (lastRowNum + 1); i++) {
            hotNameCell = sheet.getRow(i).getCell(0);// 热点名称
            devMacCell = sheet.getRow(i).getCell(1);// MAC
            if (null == hotNameCell) {//热点名称为空校验
                Object[] args = {i+1,hotNameTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            if (null == devMacCell) {//mac为空校验
                Object[] args = {i+1,devMacTitle.getStringCellValue()};
                throw new ValidException("E2000033", MessageUtil.getMessage("E2000033",args));//第（{0}）行的{1}不允许为空!
            }
            hotNameCell.setCellType(Cell.CELL_TYPE_STRING);
            hotName = hotNameCell.getStringCellValue();
            devMac = devMacCell.getStringCellValue();
            if(!RegexUtil.match(devMac, RegexConstants.MAC_PATTERN)){//mac正则校验
                Object[] args = {i+1,devMacTitle.getStringCellValue(),RegexConstants.MAC_PATTERN_DSP};
                throw new ValidException("E2000038", MessageUtil.getMessage("E2000038",args));//第（{0}）行的{1}格式不符合规范，正确的格式是：{2}
            }
            map = new HashMap<String, String>();
            map.put("hotareaName", hotName);
            map.put("macAddr", devMac);
            hotAreaList.add(map);
        }
        String params = JsonUtil.toJson(hotAreaList);
        HotareaClient.batchAddRelation(params);
        return this.successMsg();
    }
    
    /**
     * 热点删除
     * @param access_token 安全令牌
     * @param devMacs 设备数据集
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月13日 上午10:36:09
     */
    @ResponseBody
    @RequestMapping(value="/hotareas", method=RequestMethod.DELETE)
    public Map deleteByDevMacs(@RequestParam(value="access_token",required=true) String access_token,@RequestParam(value="devMacs",required=true) String devMacs) throws Exception{
        hotareaService.deleteByDevMacs(devMacs);
        return this.successMsg();
    }
    
    
}
