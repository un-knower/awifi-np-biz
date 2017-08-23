package com.awifi.np.biz.usrsrv.whiteuser.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.whiteuser.model.WhiteUser;
import com.awifi.np.biz.mws.whiteuser.service.WhiteUserService;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午8:50:00
 * 创建作者：王冬冬
 * 文件名称：BlackUserController.java
 * 版本：  v1.0
 * 功能：白名单控制层
 * 修改记录：
 */
@Controller
public class WhiteUserController extends BaseController {

	/**
	 * 微站 accountid 默认为空
	 */
    private static final Integer ACCOUT_ID=null;
	
    /**
     * 微站userid默认为空
     */
    private static final Integer USER_ID=null;
	
    /**白名单服务*/
    @Resource(name = "whiteUserService")
    private WhiteUserService whiteUserService;
    
    /**
     * 白名单列表
     * @param accessToken access_token
     * @param params 参数
     * @param request 请求
     * @return 列表
     * @author 王冬冬
     * @throws Exception 
     * @date 2017年2月13日 上午9:17:29
     */
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(method=RequestMethod.GET, value="/usrsrv/whiteusers")
    public Map<String, Object> getListByParam(@RequestParam(value="access_token",required=true)String accessToken,@RequestParam(value="params",required=true)String params,HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录数 不能大于最大数
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));
        if(pageNo == null){//如果为空 默认第一页
            pageNo = 1;
        }
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));
        String keywords = (String) paramsMap.get("keywords");//支持[mac地址|手机号]
        Page<WhiteUser> page = new Page<WhiteUser>(pageNo,pageSize);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        whiteUserService.getListByParam(sessionUser,page,merchantId,keywords);
        return this.successMsg(page);
    }
    
    /**
     * 新增白名单
     * @param accessToken access_token
     * @param bodyParam 参数
     * @return 结果
     * @author 王冬冬 
     * @throws Exception 
     * @date 2017年2月13日 下午1:53:21
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, value="/usrsrv/whiteuser")
    public Map<String, Object> add(@RequestParam(value="access_token",required=true)String accessToken,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));//商户id，数字，不允许为空
        String mac = (String) bodyParam.get("mac");//mac地址，不允许为空
        String cellphone = (String) bodyParam.get("cellphone");//手机号，不允许为空,正则校验[1开头的11位符合手机号码规则的数字] 
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        if(StringUtils.isBlank(mac)&&StringUtils.isBlank(cellphone)){
            throw new BizException("E2500009", MessageUtil.getMessage("E2500009"));
        }
        if(StringUtils.isNotBlank(cellphone)){
            ValidUtil.valid("手机号[cellphone]",cellphone,"{'required':true,'regex':'"+RegexConstants.CELLPHONE+"'}");
            if(whiteUserService.isCellphoneExist(merchantId,cellphone)){
                throw new BizException("E2500005", MessageUtil.getMessage("E2500005", cellphone));
            }
        }
        if(StringUtils.isNotBlank(mac)){
            ValidUtil.valid("mac地址[mac]", mac, "{'required':true,'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            if(whiteUserService.isMacExist(merchantId,mac)){
                throw new BizException("E2500006", MessageUtil.getMessage("E2500006", mac));
            }
        }
        WhiteUser whiteUser = new WhiteUser();
        whiteUser.setMerchantId(merchantId);
        whiteUser.setCellPhone(cellphone);
        whiteUser.setMac(mac);
        whiteUser.setAccountId(ACCOUT_ID);
        whiteUser.setUserId(USER_ID);
        whiteUserService.add(whiteUser);
        return this.successMsg();
    }

    /**
     * 逻辑删除白名单
     * @param request 请求
     * @param accessToken access_token
     * @param id 黑名单主键id
     * @return 结果
     * @author 王冬冬
     * @throws Exception 异常
     * @date 2017年2月13日 下午2:01:34
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.DELETE, value="/usrsrv/whiteuser/{id}")
    public Map<String, Object> delete(HttpServletRequest request,@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Integer id) throws Exception{
        whiteUserService.delete(id);
        return this.successMsg();
    }
    
    /**
     * 逻辑批量删除白名单
     * @param request 请求
     * @param accessToken access_token
     * @param ids 白名单主键id
     * @return 结果
     * @author 王冬冬 
     * @throws Exception 异常
     * @date 2017年2月13日 下午2:01:34
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.DELETE, value="/usrsrv/whiteusers")
    public Map<String, Object> batchdelete(HttpServletRequest request,@RequestParam(value="access_token",required=true)String accessToken, String ids) throws Exception{
    	whiteUserService.batchDelete(ids);
        return this.successMsg();
    }
    
    /**
     * 批量导入白名单
     * @param accessToken access_token
     * @param request 请求
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 王冬冬 
     * @date 2017年2月10日 下午4:56:33
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.POST, value="/usrsrv/whiteusers")
    @ResponseBody
    public Map<String, Object> batchAdd(@RequestParam(value="access_token",required=true)String accessToken,HttpServletRequest request,@RequestParam(value="params",required=true)String params) throws Exception{
    	logger.debug("批量导入白名单入参params:"+params);
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        
        Integer importMaxNum = Integer.parseInt(SysConfigUtil.getParamValue("xls_import_max_size"));
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());//创建一个通用的多部分解析器.
        if(!multipartResolver.isMultipart(request)){//判断 request 是否有文件上传,即多部分请求
            throw new BizException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
        }
        MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;//转换成多部分request
        Iterator<String>  iter = multiRequest.getFileNames();
        Workbook book = null;  
        while(iter.hasNext()){
            MultipartFile file = multiRequest.getFile(iter.next());//取得上传文件 
            if(file == null){
                continue;
            }
            book = Workbook.getWorkbook(file.getInputStream());
            Sheet sheet = book.getSheet(0);
            ExcelUtil.cmpTemplateExcel(sheet,ExcelUtil.WHITEUSERS_EXCELCOLUMNS);
            int row = sheet.getRows();//行数
            logger.debug("row:"+row);
            if(row-1 <= 0){//小于等于0 抛异常
                throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
            }else if(row-1 > importMaxNum){//大于数据导入最大数量
                Object[] message = {row-1,importMaxNum};
                throw new ValidException("E2000031", MessageUtil.getMessage("E2000031", message));//导入的记录总数[{0}]不允许大于[{1}]!
            }
            WhiteUser whiteUser = null;//白名单
            String cellphone = null; //手机号
            String mac = null;//mac地址
            
//            HashSet<String> macSet = new HashSet<String>();//临时存储用户名集合
//            HashSet<String> cellphoneSet = new HashSet<String>();//临时存储手机号集合
            Map<String,Integer> macMap = new TreeMap<String,Integer>();
            Map<String,Integer> cellphoneMap = new TreeMap<String,Integer>();
            List<WhiteUser> successList = new ArrayList<WhiteUser>(row-1);
            for(int i=1 ; i< row ; i++){
                cellphone = sheet.getCell(0,i).getContents();//手机号
                mac = sheet.getCell(1,i).getContents();
                if(StringUtils.isBlank(cellphone)&&StringUtils.isBlank(mac)){
                    throw new BizException("E2500010", MessageUtil.getMessage("E2500010","第"+ (i+1)+"行"));//mac和手机号都为空，抛出异常
                }
                
                if(StringUtils.isNoneBlank(cellphone)){
                    ValidUtil.valid("第"+ (i+1) + "行手机号",cellphone,"{'required':true,'regex':'"+RegexConstants.CELLPHONE+"'}");
                    if(cellphoneMap.containsKey(cellphone)){
                        Object[] message = {(i+1),"手机号",(cellphoneMap.get(cellphone)+1)};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                    cellphoneMap.put(cellphone,i);
                    if(whiteUserService.isCellphoneExist(merchantId,cellphone)){
                        throw new BizException("E2500005", MessageUtil.getMessage("E2500005", cellphone));
                    }
                }
                
                if(StringUtils.isNoneBlank(mac)){
                    ValidUtil.valid("第"+ (i+1) + "行mac地址", mac, "{'required':true,'regex':'"+RegexConstants.MAC_PATTERN+"'}");
                    if(macMap.containsKey(mac)){
                        Object[] message = {(i+1),"mac地址",(macMap.get(mac)+1)};
                        throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                    }
                    macMap.put(mac, i);
                    if(whiteUserService.isMacExist(merchantId,mac)){
                        throw new BizException("E2500006", MessageUtil.getMessage("E2500006", mac));
                    }
                }
                whiteUser= new WhiteUser();
                whiteUser.setMerchantId(merchantId);
                whiteUser.setCellPhone(cellphone);
                whiteUser.setMac(mac);
                whiteUser.setAccountId(ACCOUT_ID);
                whiteUser.setUserId(USER_ID);
                successList.add(whiteUser);//添加到成功列表
            }
            whiteUserService.add(successList,merchantId);//添加用户
        }
       
    	return this.successMsg();
    }
}