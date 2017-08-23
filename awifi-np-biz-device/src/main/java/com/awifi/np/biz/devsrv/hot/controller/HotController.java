/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年5月11日 下午4:36:08
 * 创建作者：范涌涛
 * 文件名称：HotpointController.java
 * 版本：  v1.0
 * 功能：热点管理:完成Chinanet和awifi热点的查询;awifi热点可以导入、修改、删除，Chinanet的热点只能够进行查询操作，不允许修改或删除
 * 修改记录：
 */
package com.awifi.np.biz.devsrv.hot.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
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
import com.awifi.np.biz.devsrv.excel.controller.ExcelBase;
import com.awifi.np.biz.devsrv.excel.service.EmsExcelService;
import com.awifi.np.biz.devsrv.hot.service.HotService;


@SuppressWarnings({"rawtypes","unchecked"})
@RequestMapping("/devsrv")
@Controller
public class HotController extends ExcelBase{

    /**
     * excel文件解析导入层
     */
    @Resource(name="emsExcelService")
    private EmsExcelService emsExcelService;
    
    @Resource(name="hotService")
    private HotService hotService;
    /**
     * 批量导入excel文件内容信息
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @return Map
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月4日 下午2:33:02
     */
    @RequestMapping(value="/hots/excel",method=RequestMethod.POST)
    @ResponseBody
    public Map importExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token)throws Exception{
        
        String dir = EmsExcelUtil.getDeviceFilePath();//获取文件路径
        MultipartFile file=getFile(request, response,dir);
        String filename=file.getOriginalFilename();
        String newFilename=getNewFileName(filename);
        ExcelType type=ExcelType.valueOf("Hot");
        File localFile=new File(dir,type.name().concat(newFilename));//把文件传输到本地
        file.transferTo(localFile);
        SessionUser sessionUser=SessionUtil.getCurSessionUser(request);
        EmsSysExcel excel = getExcelFileStatus(filename, dir+type.name().concat(newFilename), type, sessionUser);
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
     * 获取解析情况
     * @param access_token 必填
     * @param params 请求参数
     * @param request 请求
     * @param response 响应
     * @return Page
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月4日 下午2:29:41
     */
    @ResponseBody
    @RequestMapping(value="/hots/excel/parseDetail",method=RequestMethod.GET)
    public Map showDetail(@RequestParam(name="access_token",required=true) String access_token,@RequestParam(name="params") String params,HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String,Object> map=JsonUtil.fromJson(params, Map.class);
        Integer pageNo=CastUtil.toInteger( map.get("pageNo"));
        if(pageNo==null){
            pageNo=1;
        }
        Integer pageSize=CastUtil.toInteger( map.get("pageSize"));
        if(pageSize==null){
            pageSize=10;
        }
        String type=CastUtil.toString( map.get("type"));
        String typeValue=ExcelType.valueOf(type).getValue();
        Page<EmsSysExcel> page=new Page<EmsSysExcel>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        SessionUser sessionUser=SessionUtil.getCurSessionUser(request);
        emsExcelService.showDetail(page,sessionUser,typeValue);
        return this.successMsg(page);
    }

    /**
     * 下载解析错误文件
     * @param request request
     * @param response response
     * @param access_token token
     * @param filename 错误文件名
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月4日 下午2:32:09
     */
    @RequestMapping(value="/hots/excel/errorFiles/{filename:.*}",method=RequestMethod.GET)
    public void downloadErrors(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,@PathVariable(name="filename",required=true)String filename)throws Exception{
        String path=EmsExcelUtil.getDeviceFilePath();//获取文件路径
        File file=new File(path+filename);
        if(!file.exists()){
            throw new BizException("E2000029", MessageUtil.getMessage("E2000029", filename));//文件不存在抛出异常
        }
        IOUtil.download(filename, path, response);
    }

    /**
     * 
     * @param request 请求
     * @param access_token 安全令牌
     * @param redisKey redis键值
     * @return
     * @author 范涌涛  
     * @date 2017年6月4日 下午2:33:52
     */
    @ResponseBody
    @RequestMapping(value="/hots/excel/getStatus",method=RequestMethod.GET)
    public Map getStatus(HttpServletRequest request,@RequestParam(value="access_token",required=true)String access_token,@RequestParam(name="params",required=true) String params){
        Map reqParam=JsonUtil.fromJson(params, Map.class);
        String redisKey = CastUtil.toString(reqParam.get("redisKey"));
        ValidUtil.valid("热点redisKey", redisKey,"{'required':true}");
        List<Map<String, String>> result=RedisUtil.hgetAllBatch(redisKey);
        if(result.size()==0){
            throw new BizException("E2301307", MessageUtil.getMessage("E2301307","【"+redisKey+"】"));//redisKey不存在抛出异常
        }
        Map<String,String> res=result.get(0);
        TreeMap<String,String> dataMap = new TreeMap(res);
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> newMap=new HashMap<String,Object>();
        dataMap.remove("step");
        dataMap.remove("data");
        Integer successLines=0;
        Integer failLines=0;
        Integer waitingLines=0;
        Integer importingLines=0;
        for(Map.Entry<String, String> entry:dataMap.entrySet()){
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
    /**
     * 批量删除awifi热点
     * @param accessToken 安全令牌
     * @param params 请求参数
     * @return map
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月19日 上午11:05:37
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/hots", produces = "application/json")
    @ResponseBody
    public Map deleteHot(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestParam(name="params") String params) throws Exception {
        Map<String,Object> map=JsonUtil.fromJson(params, Map.class);
        String idStr = CastUtil.toString(map.get("ids"));
        ValidUtil.valid("热点ids", idStr,"{'required':true}");
        String[] ids = idStr.split(",");
        DeviceClient.deleteHotareaByIds(ids);
        return this.successMsg();
    }
    
    /**
     * 修改awifi热点
     * @param accessToken 安全令牌
     * @param bodyParam 请求体
     * @return map
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月19日 上午11:04:05
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/hots", produces = "application/json")
    @ResponseBody
    public Map updateHotarea(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
    	hotService.updateHotarea(bodyParam);
        return this.successMsg();
    }
    /**
     * 根据ID查awifi/chinanet热点
     * @param accessToken 安全令牌
     * @param id 热点ID
     * @return map
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月19日 上午11:04:01
     */
    @RequestMapping(method = RequestMethod.GET, value = "/hots/chinanet/{id}") 
    @ResponseBody
    public Map queryChianetHotById(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="id",required=true) String id) throws Exception {
        ValidUtil.valid("热点ID", id,"{'required':true,'numeric':true}");
        Map<String,Object> res;
        res = DeviceQueryClient.queryChinaHotareaInfoById(Long.parseLong(id));
        //数据中心接口，数字类型非必填项，修改时默认填0，所以按ID查询接口，将这些字段的0不显示
        Long apNum = CastUtil.toLong(res.get("apNum"));
        Long xpos = CastUtil.toLong(res.get("xpos"));
        Long ypos = CastUtil.toLong(res.get("ypos"));
        if(apNum !=null && apNum == 0L) {
            res.remove("apNum");
        }
        if(xpos !=null && xpos == 0L) {
            res.remove("xpos");
        }
        if(ypos !=null && ypos == 0L) {
            res.remove("ypos");
        }
        return this.successMsg(res);
    }
    
    /**
     * 按照ID查awifi热点信息
     * @param accessToken 安全令牌
     * @param id 主键ID
     * @return 热点信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月12日 下午2:51:44
     */
    @RequestMapping(method = RequestMethod.GET, value = "/hots/awifi/{id}") 
    @ResponseBody
    public Map queryAwifiHotById(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="id",required=true) String id) throws Exception {
        ValidUtil.valid("热点ID", id,"{'required':true,'numeric':true}");
        Map<String,Object> res;
        res = DeviceQueryClient.queryHotareaInfoById(Long.parseLong(id));
      //数据中心接口，数字类型非必填项，修改时默认填0，所以按ID查询接口，将这些字段的0不显示
        Long apNum = CastUtil.toLong(res.get("apNum"));
        Long xpos = CastUtil.toLong(res.get("xpos"));
        Long ypos = CastUtil.toLong(res.get("ypos"));
        if(apNum !=null && apNum == 0L) {
            res.remove("apNum");
        }
        if(xpos !=null && xpos == 0L) {
            res.remove("xpos");
        }
        if(ypos !=null && ypos == 0L) {
            res.remove("ypos");
        }
        return this.successMsg(res);
    }
    
    
    /**
     * 分页查询awifi热点和Chinanet热点信息
     * @param accessToken 安全令牌
     * @param params 请求参数
     * @return Map
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月19日 上午11:06:18
     */
    @RequestMapping(method = RequestMethod.GET, value = "/hots", produces = "application/json") 
    @ResponseBody
    public Map queryHotList(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestParam(name = "params", required = false) String params) throws Exception {
        Map reqParam=JsonUtil.fromJson(params, Map.class);
        String outTypeId = CastUtil.toString(reqParam.get("outTypeId"));
        ValidUtil.valid("热点查询outTypeId", outTypeId,"{'required':true}");
        Page page;
        if(outTypeId.equals("CHINANET")) {
            page = DeviceQueryClient.queryChinaNetHotareaInfoListByParam(reqParam);
        } else if(outTypeId.equals("AWIFI")) {
            page = DeviceQueryClient.queryHotareaInfoListByParam(reqParam);
        } else {
            throw  new ValidException("E2301104", MessageUtil.getMessage("E2301104",outTypeId));
        }
        return this.successMsg(page);
    }
    
}
