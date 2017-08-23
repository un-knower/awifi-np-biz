/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午3:34:41
* 创建作者：伍恰
* 文件名称：FatApUpgradePatchController.java
* 版本：  v1.0
* 功能： 定制终端升级包 相关功能 包括 个性化升级包和默认升级包
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.fatap.service.FatApUpgradePatchService;
import com.awifi.np.biz.devsrv.fatap.util.FatapUtil;

@RequestMapping("/devsrv/devices/fatap")
@Controller
public class FatApUpgradePatchController extends BaseController {
    
    /**
     * 默认升级包路径
     */
    @Value("${defaul.upgradet.patch.dir}")
    private String defaulUpgradetPatchDir;
    
    /**
     * 个性化升级包路径
     */
    @Value("${personalized.upgrade.patch.dir}")
    private String personalizedUpgradePatchDir;
    /**
     * 定制终端升级包服务类
     */
    @Resource(name = "fatApUpgradePatchService")
    private FatApUpgradePatchService fatApUpgradePatchService;
    
    /**
     * 定制终端升级--默认升级包查询
     * @param access_token 令牌
     * @param params 查询参数
     * @return  list 
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午3:51:27
     */
    @RequestMapping(value = "/defaultupgradepatch", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDefaulUpgradetPatchList(@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="params", required = true)Map<String,Object> params)throws Exception{
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        this.setPageInfo(params, page);
        fatApUpgradePatchService.getDefaulUpgradetPatchList(params, page);
        return this.successMsg(page);
    }
    
    /**
     * 定制终端升级--默认升级包根据id查询
     * @param access_token 令牌
     * @param id id 
     * @return 升级包信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午4:59:53
     */
    @RequestMapping(value = "/defaultupgradepatch/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDefaulUpgradetPatchById(@RequestParam(value="access_token",required=true)String access_token,@PathVariable(value = "id") String id)throws Exception{
        ValidUtil.valid("主键id", id, "{'required':true,'numeric':'true'}");
        Long patchId = CastUtil.toLong(id);
        return this.successMsg(fatApUpgradePatchService.getDefaulUpgradetPatchById(patchId));
    }
    
    /**
     * 定制终端升级--默认升级启用
     * @param access_token 令牌
     * @param bodyParam 参数
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午5:11:22
     */
    @RequestMapping(value="/defaultupgradepatchstatus",method=RequestMethod.PUT,produces="application/json")
    @ResponseBody
    public Map<String, Object> updateUpgradetPatchStatus(@RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> bodyParam){
        Object ids = null ;
        if (null != bodyParam){
            ids = bodyParam.get("ids");
        }
        ValidUtil.valid("主键[ids]", ids, "{'required':true,'arrayNotBlank':true}");
        String value = (String) bodyParam.get("ids");
        if (StringUtils.isBlank(value)) {
            throw new ValidException("E2301314",
                    MessageUtil.getMessage("E2301314", new Object[]{"ids"}));
        }
        String[] patchArrStr = value.split(",");
        Long [] patchArr = CastUtil.toLongArray(patchArrStr);
        fatApUpgradePatchService.updateUpgradetPatchStatus(patchArr);
        return this.successMsg();
    }
    
    /**
     * 定制终端升级--默认升级包上传
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param bodyParam 参数
     * @return 结果
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午6:09:03
     */
    @RequestMapping(value="/defaultupgradepatch",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addDefaulUpgradetPatch (HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> bodyParam)throws Exception{
        MultipartFile file = IOUtil.getFileFromRequest(request);
        if (null == file || file.isEmpty()){
            throw new ValidException("E2000030",MessageUtil.getMessage("E2000030"));
        }
        /*校验字段信息*/
        String provinceId = (String)bodyParam.get("provinceId");
        ValidUtil.valid("省份编码[provinceId]", provinceId, "{'required':true,'numeric':true}");
        
        String cityId = (String)bodyParam.get("cityId");
        ValidUtil.valid("地区市编码[cityId]", cityId, "{'required':true,'numeric':true}");
        
        String districtId = (String)bodyParam.get("districtId");
        ValidUtil.valid("区、县编码[districtId]", districtId, "{'required':true,'numeric':true}");
        
        String provinceText = (String)bodyParam.get("provinceText");
        ValidUtil.valid("省份名称[provinceText]", provinceText, "{'required':true}");
        
        String cityText = (String)bodyParam.get("cityText");
        ValidUtil.valid("市名称[cityText]", cityText, "{'required':true}");
        
        String districtText = (String)bodyParam.get("districtText");
        ValidUtil.valid("区县名称[districtText]", districtText, "{'required':true}");
        
        String corporationId = (String)bodyParam.get("corporationId");
        ValidUtil.valid("厂家id[corporationId]", corporationId, "{'required':true,'numeric':true}");
        
        String corporationText = (String)bodyParam.get("corporationText");
        ValidUtil.valid("厂家名称[corporationText]", corporationText, "{'required':true}");
        
        String modelId = (String)bodyParam.get("modelId");
        ValidUtil.valid("型号id[modelId]", modelId, "{'required':true,'numeric':true}");
       
        String modelText = (String)bodyParam.get("modelText");
        ValidUtil.valid("型号名称[modelText]", modelText, "{'required':true}");
        
        String version = (String)bodyParam.get("version");
        ValidUtil.valid("版本号[version]", version, "{'required':true , 'regex':'"+RegexConstants.UPGRADETPATCH_VERSION+"'}");
        
        String versionHd = (String)bodyParam.get("versionHd");
        ValidUtil.valid("hd版本号[versionHd]", versionHd, "{'required':true, 'regex':'^[a-zA-Z0-9]{1,20}$'}");
        
        String typeUpgrade = (String)bodyParam.get("typeUpgrade");
        ValidUtil.valid("升级类型[typeUpgrade]", typeUpgrade, "{'required':true}");
        //按照省、市、区、厂家、型号 分目录
        String fileName = version+".bin";
        String upgradetPatchUrl = String.format(FatapUtil.getPathFomat(6),provinceText,cityText,districtText,corporationText,modelText,fileName);
        file.transferTo(new File(defaulUpgradetPatchDir + upgradetPatchUrl));
        bodyParam.put("url", upgradetPatchUrl);
        fatApUpgradePatchService.addDefaulUpgradetPatch(bodyParam);
        return this.successMsg();
    }
    
    /**
     * 定制终端升级--默认升级包删除
     * @param access_token 令牌
     * @param idArr id 数组 
     * @return 信息
     * @author 伍恰  
     * @date 2017年6月27日 下午8:03:51
     */
    @RequestMapping(value="/defaultupgradepatch",method=RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteDefaulUpgradetPatch (@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="idArr",required=true) Long[] idArr){
        fatApUpgradePatchService.deleteDefaulUpgradetPatch(idArr);
        return this.successMsg();
    }
    
    /**
     * 定制终端升级--个性化升级设备查询
     * @param access_token 令牌
     * @param params 参数
     * @return 分页信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:49:57
     */
    @RequestMapping(value = "/personalizedupgradedevice", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUpgradeDeviceList (@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="params", required = true)Map<String,Object> params)throws Exception{
        String factoryId = (String)params.get("factoryId");
        String modelId = (String)params.get("factoryId");
        if(null != factoryId || null != modelId){
            ValidUtil.valid("使用厂商和型号时商户名称必填[merchantName]", params.get("merchantName"), "{'required':true}");
        }
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        this.setPageInfo(params, page);
        fatApUpgradePatchService.getUpgradeDeviceList(params, page);
        return this.successMsg(page);
    }
    
    /**
     * 定制终端升级--个性化升级包查询
     * @param access_token 令牌
     * @param params 参数
     * @return 分页信息
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月27日 下午8:56:37
     */
    @RequestMapping(value = "/personalizedupgradepatch", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPersonalizUpgradePatchList(@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="params", required = true)Map<String,Object> params)throws Exception{
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        this.setPageInfo(params, page);
        fatApUpgradePatchService.getPersonalizUpgradePatchList(params, page);
        return this.successMsg(page);
    }
    
    /**
     * 定制终端升级--个性化升级包根据id查询
     * @param access_token 令牌
     * @param id id 
     * @return 包信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:58:32
     */
    @RequestMapping(value = "/personalizedupgradepatch/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPersonalizUpgradePatchById(@RequestParam(value="access_token",required=true)String access_token,@PathVariable(value = "id") String id)throws Exception{
        ValidUtil.valid("主键id", id, "{'required':true,'numeric':'true'}");
        Long patchId = CastUtil.toLong(id);
        return this.successMsg(fatApUpgradePatchService.getPersonalizUpgradePatchById(patchId));
    }
    
    /**
     * 定制终端升级--个性化升级包上传
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param bodyParam 参数
     * @return 结果
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月27日 下午8:59:51
     */
    @RequestMapping(value="/personalizedupgradepatch",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPersonalizedUpgradePatch(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> bodyParam)throws Exception{
        MultipartFile file = IOUtil.getFileFromRequest(request);
        if (null == file || file.isEmpty()){
            throw new ValidException("E2000030",MessageUtil.getMessage("E2000030"));
        }
        /*校验字段信息*/
        String upgradePatchName = (String)bodyParam.get("upgradePatchName");
        ValidUtil.valid("升级包名称[upgradePatchName]", upgradePatchName, "{'required':true}");
        String corporationText = (String)bodyParam.get("corporationText");
        ValidUtil.valid("厂家名称[corporationText]", corporationText, "{'required':true}");
        
        String modelId = (String)bodyParam.get("modelId");
        ValidUtil.valid("型号id[modelId]", modelId, "{'required':true,'numeric':true}");
       
        String modelText = (String)bodyParam.get("modelText");
        ValidUtil.valid("型号名称[modelText]", modelText, "{'required':true}");
        
        String version = (String)bodyParam.get("version");
        ValidUtil.valid("版本号[version]", version, "{'required':true , 'regex':'"+RegexConstants.UPGRADETPATCH_VERSION+"'}");
        
        String versionHd = (String)bodyParam.get("versionHd");
        ValidUtil.valid("hd版本号[versionHd]", versionHd, "{'required':true, 'regex':'^[a-zA-Z0-9]{1,20}$'}}");
        
        String typeUpgrade = (String)bodyParam.get("typeUpgrade");
        ValidUtil.valid("升级类型[typeUpgrade]", typeUpgrade, "{'required':true}");
        //按照省、市、区、厂家、型号 分目录
        String fileName = version+".bin";
        String upgradetPatchUrl = String.format(FatapUtil.getPathFomat(3),corporationText,modelText,fileName);
        file.transferTo(new File(personalizedUpgradePatchDir + upgradetPatchUrl));
        bodyParam.put("url", upgradetPatchUrl);
        fatApUpgradePatchService.addPersonalizedUpgradePatch(bodyParam);
        return this.successMsg();
    }
    
    /**
     * 定制终端升级--个性化升级包删除
     * @param access_token 令牌
     * @param idArr id 数组
     * @return 参数
     * @author 伍恰  
     * @date 2017年6月27日 下午9:03:02
     */
    @RequestMapping(value="/personalizedupgradepatch",method=RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deletePersonalizedUpgradePatch (@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="idArr",required=true) Long[] idArr){
        fatApUpgradePatchService.deletePersonalizedUpgradePatch(idArr);
        return this.successMsg();
    }
}
