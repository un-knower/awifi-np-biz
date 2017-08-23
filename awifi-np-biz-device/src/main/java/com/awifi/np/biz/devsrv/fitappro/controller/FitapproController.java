package com.awifi.np.biz.devsrv.fitappro.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;
import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EmsExcelUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.excel.service.EmsExcelService;
import com.awifi.np.biz.devsrv.fitappro.service.FitapproService;

@SuppressWarnings({"rawtypes","unchecked"})
@RequestMapping("/devsrv")
@Controller
public class FitapproController extends BaseController{
    
    /**
     * 项目型瘦ap的service层
     */
    @Resource(name="fitapproService")
    private FitapproService fitapproService;
    
    /**
     * excel导入service层
     */
    @Resource(name="emsExcelService")
    private EmsExcelService emsExcelService;
    /**
     * 项目型瘦ap查询功能
     * @param access_token token
     * @param params 参数字符串(json)
     * @param request request
     * @param response response
     * @return map
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月13日 下午4:20:49
     */
    @RequestMapping(value="/devices/fitappro",method=RequestMethod.GET)
    @ResponseBody
    public Map getFitapproList(@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="params")String params,HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,Object> map=JsonUtil.fromJson(params, Map.class);
        Integer pageNo=(Integer) map.get("pageNo");
        Integer pageSize=(Integer) map.get("pageSize");
        SessionUser sessionUser=SessionUtil.getCurSessionUser(request);
//        SessionUser sessionUser=new SessionUser();//测试使用
        CenterPubEntity entity=new CenterPubEntity();
        if(pageNo==null){
            pageNo=1;//每页页码默认为1；
        }
        if(pageSize==null){
            pageSize=10;//每页记录数默认为10
        }
        Object provinceId=map.get("provinceId");
        if(provinceId!=null){
            ValidUtil.valid("省份", provinceId, "numeric");//数字校验;
            entity.setProvince(CastUtil.toInteger(provinceId));
        }
        Object cityId=map.get("cityId");
        if(cityId!=null){
            ValidUtil.valid("市", cityId, "numeric");//数字校验;
            entity.setCity(CastUtil.toInteger(cityId));
        }
        Object areaId=map.get("areaId");
        if(areaId!=null){
            ValidUtil.valid("区县", areaId, "numeric");//数字校验;
            entity.setCounty(CastUtil.toInteger(areaId));
        }
        Object macAddr=map.get("macAddr");
        if(macAddr!=null){
            ValidUtil.valid("mac地址", macAddr, "{'regex':'"+RegexConstants.MAC_NO_CASE_PATTERN+"'}");//mac地址校验
            entity.setMacAddr((String)macAddr);
        }
        entity.setParEntityName((String)map.get("parEntityName"));
        Page<CenterPubEntity> page=new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        fitapproService.getFitapproList(page, entity, sessionUser);
        return this.successMsg(page);
    }
    
    /**
     * 项目型瘦ap删除功能
     * @param access_token token
     * @param  id id
     * @param deviceId 设备id
     * @return map
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月13日 下午4:20:49
     */
    @RequestMapping(value="/devices/fitappro",method=RequestMethod.DELETE)
    @ResponseBody
    public Map deleteFitapproList(@RequestParam(name="access_token",required=true) String access_token,@RequestParam(value="id",required=true) String id, @RequestParam(value="deviceId",required=true) String deviceId)throws Exception{
        CenterPubEntity entity=fitapproService.getFitapproById(id);
        if(entity!=null){
            Long merchantId=entity.getMerchantId();
            if(merchantId!=null && merchantId!=0L){
                throw new ValidException("E2301305",MessageUtil.getMessage("E2301305"));//绑定商户设备不能删除
            }
        }
        String [] arr = {deviceId};
        fitapproService.deleteFitapproList(arr);
        return this.successMsg();
    }
    /**
     * 项目型瘦ap的 Excel 导入
     * @param request 请求
     * @param access_token 安全令牌
     * @return Map 
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:20:49
     */
    @RequestMapping(value="/devices/fitappro/excel",method=RequestMethod.POST)
    @ResponseBody
    public Map importFitappro(HttpServletRequest request,@RequestParam(value="access_token",required=true)String access_token)throws Exception{
        String filename="";
        Date date=new Date();
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
//        //对file进行初始化验证和type的获取
//        new HSSFWorkbook(file.getInputStream()).getSheetAt(0);
        Sheet sheet=WorkbookFactory.create(file.getInputStream()).getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();//最后一行
        if (lastRowNum < 1) {
            throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
        }
        String entityType="FitAPPro";//直接获取类型
        ExcelType type=ExcelType.valueOf(entityType);
        File localFile=new File(dir,type.name().concat(newFilename));//把文件传输到本地
        file.transferTo(localFile);
        EmsSysExcel excel=new EmsSysExcel();
        excel.setFilename(filename);
        excel.setFilepath(dir+type.name().concat(newFilename));
        excel.setType(type.getValue());
        excel.setFilestatus(0);//已上传
        SessionUser sessionUser=SessionUtil.getCurSessionUser(request);
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
//        Thread.sleep(50000000000L);//为了测试执行线程
        return result;
    }

}
