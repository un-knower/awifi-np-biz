package com.awifi.np.biz.devsrv.excel.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;

import com.awifi.np.biz.common.excel.model.EmsSysExcel;
import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EmsExcelUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.excel.service.EmsExcelService;


@SuppressWarnings({"rawtypes","unchecked"})
@RequestMapping("/devsrv")
@Controller
public class EmsExcelController extends BaseController{
    
	/**
	 * excel文件解析导入层
	 */
    @Resource(name="emsExcelService")
    private EmsExcelService emsExcelService;
    
    /**
     * 批量导入excel文件内容信息
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @return map
     * @throws Exception 异常
     */
//    @RequestMapping(value="/excel/import/{filename:.*}",method=RequestMethod.POST)
    @RequestMapping(value="/excel/import",method=RequestMethod.POST)
    @ResponseBody
    public Map importExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token)throws Exception{
      //获取文件(仅适合单文件上传)
        Date date=new Date();
        String filename="";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String dir = EmsExcelUtil.getDeviceFilePath();//获取文件路径
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartFile file = null;
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;//转换成多个请求
            Iterator<String>  iter = multiRequest.getFileNames();
            while(iter.hasNext()){//遍历文件
                file = multiRequest.getFile((String)iter.next());
                if(file == null){//文件为空跳过
                    continue;
                }
                filename=file.getOriginalFilename();
            }
        }
        String newFilename=sdf.format(date)+filename.substring(filename.lastIndexOf("."));
//        对file进行初始化验证和type的获取(目前不需要，直接给出类型)
//        Sheet sheet=WorkbookFactory.create(file.getInputStream()).getSheetAt(0);
//        int lastRowNum = sheet.getLastRowNum();//最后一行
//        if (lastRowNum < 1) {
//            throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
//        }
//        String typeValue=sheet.getRow(1).getCell(1).getStringCellValue();
//        ValidUtil.valid("型号", typeValue, "required");
//        String corpText=sheet.getRow(1).getCell(0).getStringCellValue();//获取厂商名称
//        ValidUtil.valid("厂商", corpText, "required");
//        Map<String,Object> map=new HashMap<>();
//        map.put("modelName",typeValue );
//        String entityType=CorporationClient.getModelType(map, corpText);//通过型号和厂商名称调用开放平台接口查找相应类型.
        String entityType="FatAP";
        
        if(StringUtils.isBlank(entityType)){
            throw new ValidException("E2301301", MessageUtil.getMessage("E2301301"));
        }
        ExcelType type=ExcelType.valueOf(entityType);
        File localFile=new File(dir,type.name().concat(newFilename));//把文件传输到本地
        file.transferTo(localFile);
        EmsSysExcel excel=new EmsSysExcel();
        excel.setFilename(filename);
        excel.setFilepath(dir+type.name().concat(newFilename));
        excel.setType(type.getValue());
        excel.setFilestatus(0);//已上传
        SessionUser sessionUser=SessionUtil.getCurSessionUser(request);
//        SessionUser sessionUser=new SessionUser();//测试使用
//        sessionUser.setId(12L);
//        sessionUser.setUserName("machealll");
        excel.setUploader(sessionUser.getId());
        excel.setUploadname(sessionUser.getUserName());
        excel.setUploadtime(new Date());
        //生成唯一编码
        String uuid = UUID.randomUUID().toString();
        excel.setUuid(uuid);
      //将excel信息插入数据库
        emsExcelService.addSelective(excel);
        //开始导入
        Map<String,String> result=emsExcelService.importExcel(excel, type, sessionUser);
        if(result==null){
            throw new ValidException("E2301302", MessageUtil.getMessage("E2301302"));
        }
        return result;
    }
    
    /**
     * 展示excel文件解析情况
     * @param access_token 安全令牌
     * @param params 参数
     * @param request 请求
     * @param response 响应
     * @return page
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping(value="/excel/showDetail",method=RequestMethod.GET)
    public Map showDetail(@RequestParam(name="access_token",required=true) String access_token,@RequestParam(name="params") String params,HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String,Object> map=JsonUtil.fromJson(params, Map.class);
        Integer pageNo=(Integer) map.get("pageNo");
        if(pageNo==null){
            pageNo=1;
        }
        Integer pageSize=(Integer) map.get("pageSize");
        if(pageSize==null){
            pageSize=10;
        }
        String type=(String) map.get("type");
        ValidUtil.valid("设备类型", type, "required");
        String typeValue;
        try{
            typeValue=ExcelType.valueOf(type).getValue();
        }catch(Exception e){
            throw new ValidException("E2301301", MessageUtil.getMessage("E2301301"));
        }
        
        Page<EmsSysExcel> page=new Page<EmsSysExcel>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        SessionUser sessionUser=SessionUtil.getCurSessionUser(request);
        emsExcelService.showDetail(page,sessionUser,typeValue);
        return this.successMsg(page);
    }
    
    /**
     * 下载错误文件
     * @param request 请求
     * @param response 相应
     * @param access_token 安全令牌
     * @param filename 文件名
     * @throws Exception 异常
     */
    @RequestMapping(value="/excel/downloadErrors/{filename:.*}",method=RequestMethod.GET)
    public void downloadErrors(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,@PathVariable(value="filename",required=true)String filename)throws Exception{
        String path=EmsExcelUtil.getDeviceFilePath();//获取文件路径
        File file=new File(path+filename);
        if(!file.exists()){
            throw new BizException("E2000029", MessageUtil.getMessage("E2000029", filename));//文件不存在抛出异常
        }
        IOUtil.download(filename, path, response);
    }
    
    /**
     * 查询导入数据中心的状态
     * @param request 请求
     * @param access_token 安全令牌
     * @param params 参数
     * @return map 返回 
     * @author 伍恰  
     * @date 2017年6月14日 下午1:42:43
     */
    @ResponseBody
    @RequestMapping(value="/excel/getStatus",method=RequestMethod.GET)
    public Map getStatus(HttpServletRequest request,@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="params",required=true) String params){
        Map<String,Object> pmap=JsonUtil.fromJson(params, Map.class);
        ValidUtil.valid("查询键值",pmap.get("redisKey"), "required");
        String redisKey=(String) pmap.get("redisKey");
        ValidUtil.valid("查询键值", redisKey, "required");
        List<Map<String,String>> res=RedisUtil.hgetAllBatch(redisKey);
        if (res.size() == 0) {
            throw new BizException("E2301307",
                    MessageUtil.getMessage("E2301307", "【" + redisKey + "】"));// redisKey不存在抛出异常
        }
        Map<String,String> m=res.get(0);
        TreeMap<String,String> result = new TreeMap(m);
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> newMap=new HashMap<String,Object>();
        result.remove("step");
        result.remove("data");
        Integer successLines=0;
        Integer failLines=0;
        Integer waitingLines=0;
        Integer importingLines=0;
        for(Map.Entry<String, String> entry:result.entrySet()){
            Map<String,Object> map=new HashMap<>();
            String value=entry.getValue();
            Map<String,Object> mapValue=JsonUtil.fromJson(value, Map.class);
            map.put("num", mapValue.get("num")); 
            map.put("line", mapValue.get("line")); 
            map.put("status", mapValue.get("status")); 
            map.put("message", mapValue.get("message")); 
            list.add(map);
            String[] lines = CastUtil.toString(mapValue.get("line")).split("-");
            Integer startIdx=CastUtil.toInteger(lines[0]);
            Integer endIdx=CastUtil.toInteger(lines[1]);
            Integer status = CastUtil.toInteger(mapValue.get("status"));
            if(status==0) {
                waitingLines = endIdx - startIdx + 1 + waitingLines;//等待导入行数
            }
            else if(status==1) {
                importingLines = endIdx - startIdx + 1 + importingLines;//正在导入行数
            }
            else if(status==2) {
                successLines = endIdx - startIdx + 1 + successLines; //导入成功行数
            }
            else if(status==3) {
                failLines = endIdx - startIdx + 1 +failLines;//导入失败行数
            }
        }
        Integer totalLines = waitingLines + importingLines + successLines + failLines;
        newMap.put("list", list);
        newMap.put("totalLines",totalLines);
        newMap.put("waitingLines",waitingLines);
        newMap.put("importingLines", importingLines);
        newMap.put("successLines", successLines);
        newMap.put("failLines", failLines);
        
        return this.successMsg(newMap);
    }
    

}
