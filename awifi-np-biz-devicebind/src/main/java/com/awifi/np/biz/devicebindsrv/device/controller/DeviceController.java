/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 上午12:31:07
* 创建作者：许小满
* 文件名称：DeviceController.java
* 版本：  v1.0
* 功能：虚拟设备相关操作控制层
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.device.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devicebindsrv.device.service.DeviceInstallerService;
import com.awifi.np.biz.devicebindsrv.merchant.service.MerchantService;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

@Controller
@RequestMapping(value = "/devbindsrv")
public class DeviceController extends BaseController {

    /**商户服务*/
    @Resource(name = "merchantService")
    private MerchantService merchantService;
    
    /**toe用户服务*/
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**toe角色服务*/
    @Resource(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    
    /**设备-装维人员关系 服务*/
    @Resource(name = "deviceInstallerService")
    private DeviceInstallerService deviceInstallerService;
    
    /**
     * 校验设备以否已绑定接口
     * @param devMac 设备MAC
     * @return json
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 上午12:39:08
     */
    @RequestMapping(method = RequestMethod.GET,value = "/device/check")
    @ResponseBody
    public Map<String,Object> checkByMac(@RequestParam(value="devmac",required=true) String devMac) throws Exception{
        //参数校验
        ValidUtil.valid("设备MAC[devmac]", devMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//设备MAC
        
        //判断设备是否已经绑定
        String deviceId = this.isBind(devMac);
        Map<String,String> resultMap = new HashMap<String,String>(1);
        resultMap.put("deviceId", deviceId);
        return this.successMsg(resultMap);
    }

    /**
     * 设备绑定接口
     * @param bodyParam 请求体参数
     * @return json
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 上午1:16:21
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/device/bind")
    @ResponseBody
    public Map<String,Object> bind(@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        long beginTime = System.currentTimeMillis();
        //获取参数
        String devMac = (String)bodyParam.get("devMac");//设备MAC地址，不允许为空，正则校验：[0-9A-F]{12}
        String broadbandAccount = (String)bodyParam.get("broadbandAccount");//宽带账号，允许为空
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));//商户id，允许为空，数字
        String userName = (String)bodyParam.get("account");//商户账号（商户手机号），不允许为空，1开头的11位符合手机号码规则的数字，正则：^(1[0-9]{10})?$
        String merchantName = (String)bodyParam.get("merchantName");//商户名称（门店名称），不允许为空
        String contactWay = (String)bodyParam.get("contactWay");//联系方式（门店电话），不允许为空
        
        String priIndustryCode = (String)bodyParam.get("priIndustryCode");//一级行业编号,不允许为空
        String secIndustryCode = (String)bodyParam.get("secIndustryCode");//二级行业编号,不允许为空
        
        Long provinceId = CastUtil.toLong(bodyParam.get("provinceId"));//省id，数字，不允许为空
        Long cityId = CastUtil.toLong(bodyParam.get("cityId"));//市id，数字，不允许为空
        Long areaId = CastUtil.toLong(bodyParam.get("areaId"));//区县id，数字，不允许为空
        String address = (String)bodyParam.get("address");//详细地址，不允许为空
        
        String ssidPrefix = (String)bodyParam.get("ssidPrefix");//SSID（前缀），允许为空，0-4位字母或数字
        String ssidSuffix = (String)bodyParam.get("ssidSuffix");//SSID（后缀），不允许为空，1-4位字母或数字
        
        String jobNumber = (String)bodyParam.get("jobNumber");//服务人员工号，不允许为空
        
        
        //参数校验
        validParam(devMac, userName, merchantName, contactWay, priIndustryCode, secIndustryCode, 
                provinceId, cityId, areaId, address, ssidPrefix, ssidSuffix, jobNumber);
        
        //判断设备是否已经绑定
        String deviceId = this.isBind(devMac);
        logger.debug("提示：deviceId= " + deviceId);
        //如果参数商户id[merchantId]为空时，创建商户
        Long projectId = null;
        if(merchantId == null){
            //商户名称[merchantName]唯一性校验
            if(merchantService.isMerchantNameExist(merchantName)){//商户名称存在
                throw new ValidException("E2200002", MessageUtil.getMessage("E2200002", merchantName));
            }
            //账号[account]唯一性校验
            if(toeUserService.isUserNameExist(userName)){//账号存在
                throw new ValidException("E2000027", MessageUtil.getMessage("E2000027", userName));
            }
            //新增userAuth记录，并返回用户id
            Long userId = UserAuthClient.addUserAuth(userName);
            //调数据中心接口添加商户
            projectId = FormatUtil.industryCodeToProjectId(priIndustryCode, secIndustryCode);//行业编号转项目id
            merchantId = addMerchant(userName, merchantName, contactWay, priIndustryCode, secIndustryCode, 
                                        provinceId, cityId, areaId, address, projectId, userId);//添加商户
            //创建用户表(账号)记录
            Long toeUserId = addToeUser(userName, contactWay, provinceId, cityId, areaId, projectId);
            //创建 关联账号--角色关系
            toeRoleService.addUserRole(toeUserId, SysConfigUtil.getParamValue("msw_default_role_id"));
            //创建 关联账号--商户关系
            toeUserService.addUserMerchant(toeUserId, merchantId);
        } else {
            Merchant merchant = MerchantClient.getById(merchantId);
            projectId = merchant.getProjectId();//项目id
        }
        //设备绑定
        String ssid = getSsid(ssidPrefix, ssidSuffix);//SSID
        
        this.bind(broadbandAccount, merchantId, merchantName, provinceId, cityId, areaId, address, deviceId, projectId, ssid);
        //修改SSID
        DeviceBusClient.setFatAPSSID(devMac, ssid);//调用设备总线接口--修改ssid
        //DeviceClient.updateSSID(deviceId, ssid);//调用数据中心接口--修改ssid
        //保存装维人员工号信息
        deviceInstallerService.add(deviceId, jobNumber);
        logger.debug("提示：设备激活绑定共花费了 "+ (System.currentTimeMillis()-beginTime) +" ms.");
        return this.successMsg();
    }

    /**
     * 获取ssid
     * @param ssidPrefix SSID（前缀）
     * @param ssidSuffix SSID（后缀）
     * @return SSID
     * @author 许小满  
     * @date 2017年5月22日 下午7:44:18
     */
    private String getSsid(String ssidPrefix, String ssidSuffix) {
        StringBuilder ssid = new StringBuilder();
        ssid.append("aWiFi");
        if(StringUtils.isNoneBlank(ssidPrefix)){
            ssid.append("-").append(ssidPrefix);
        }
        if(StringUtils.isNoneBlank(ssidSuffix)){
            ssid.append("-").append(ssidSuffix);
        }
        return ssid.toString();
    }

    /**
     * 判断设备是否绑定
     * @param devMac 设备MAC
     * @throws Exception 异常
     * @return 设备id
     * @author 许小满  
     * @date 2017年5月22日 上午1:38:03
     */
    private String isBind(String devMac) throws Exception {
        Map<String,Object> params = new HashMap<String,Object>();//参数
        params.put("pageNum", 1);//页码
        params.put("pageSize", 1);//每页数量
        params.put("macAddr", devMac);//设备MAC
        List<Device> deviceList = DeviceClient.getListByParam(JsonUtil.toJson(params));//调用数据中心接口
        if(deviceList == null || deviceList.size() <= 0){
            throw new BizException("E2410002", MessageUtil.getMessage("E2410002"));//设备不存在！
        }
        Device device = deviceList.get(0);//设备实体
        Long merchantId = device.getMerchantId();//商户id
        logger.debug("提示：商户id[merchantId]= " + merchantId);
        if(merchantId != null && !merchantId.equals(0L)){
            throw new BizException("E2410001", MessageUtil.getMessage("E2410001"));//设备已绑定！
        }
        String deviceId = device.getDeviceId();//设备id
        if(StringUtils.isBlank(deviceId)){
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002","设备id"));//{0}不允许为空!！
        }
        return deviceId;
    }
    
    /**
     * 参数校验
     * @param devMac 设备MAC地址
     * @param userName 商户账号（商户手机号）
     * @param merchantName 商户名称（门店名称）
     * @param contactWay 联系方式（门店电话）
     * @param priIndustryCode 一级行业编号
     * @param secIndustryCode 二级行业编号
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param address 详细地址
     * @param ssidPrefix SSID（前缀）
     * @param ssidSuffix SSID（后缀）
     * @param jobNumber 服务人员工号
     * @author 许小满  
     * @date 2017年5月22日 上午8:38:44
     */
    private void validParam(String devMac, String userName,
            String merchantName, String contactWay, String priIndustryCode,
            String secIndustryCode, Long provinceId, Long cityId, Long areaId,
            String address, String ssidPrefix, String ssidSuffix,
            String jobNumber) {
        ValidUtil.valid("设备MAC[devMac]", devMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//设备MAC地址，不允许为空，正则校验：[0-9A-F]{12}
        //ValidUtil.valid("商户id[merchantId]", merchantId, "required");//商户id，允许为空，数字
        ValidUtil.valid("商户手机号[account]", userName, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//商户账号（商户手机号），不允许为空，1开头的11位符合手机号码规则的数字，正则：^(1[0-9]{10})?$
        ValidUtil.valid("门店名称[merchantName]", merchantName, "required");//商户名称（门店名称），不允许为空
        ValidUtil.valid("门店电话[contactWay]", contactWay, "required");//联系方式（门店电话），不允许为空
        ValidUtil.valid("一级行业编号[priIndustryCode]", priIndustryCode, "required");//一级行业编号,不允许为空
        ValidUtil.valid("二级行业编号[secIndustryCode]", secIndustryCode, "required");//二级行业编号,不允许为空
        
        ValidUtil.valid("省id[provinceId]", provinceId, "required");//省id，数字，不允许为空
        ValidUtil.valid("市id[cityId]", cityId, "required");//市id，数字，不允许为空
        ValidUtil.valid("区县id[areaId]", areaId, "required");//区县id，数字，不允许为空
        ValidUtil.valid("详细地址[address]", address, "required");//详细地址，不允许为空
        
        ValidUtil.valid("SSID（前缀）[ssidPrefix]", ssidPrefix, "{'regex':'"+RegexConstants.SSID_PRIEFIX+"'}");//SSID（前缀），允许为空，0-4位字母或数字
        ValidUtil.valid("SSID（后缀）[ssidSuffix]", ssidSuffix, "{'required':true, 'regex':'"+RegexConstants.SSID_SUFFIX+"'}");//SSID（后缀），不允许为空，1-5位字母或数字
        
        ValidUtil.valid("服务人员工号[jobNumber]", jobNumber, "required");//服务人员工号，不允许为空
    }
    
    /**
     * 添加商户
     * @param userName 用户名
     * @param merchantName 商户名称
     * @param contactWay 联系方式
     * @param priIndustryCode 一级行业
     * @param secIndustryCode 二级行业
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param address 详细地址
     * @param projectId 项目id
     * @param userId 用户id
     * @throws Exception 异常
     * @return 商户id
     * @author 许小满  
     * @date 2017年5月22日 下午6:08:30
     */
    private Long addMerchant(String userName, String merchantName,
            String contactWay, String priIndustryCode, String secIndustryCode, Long provinceId,
            Long cityId, Long areaId, String address, Long projectId, Long userId)
            throws Exception {
        Merchant merchant = new Merchant();
        merchant.setMerchantName(merchantName);//商户名称
        merchant.setParentId(0L);//父商户id
        //merchant.setAccount(userName);//账号
        merchant.setUserId(userId);//用户id
        merchant.setMerchantType(1);//1：商客；--2：行客;--如果该参数不传入，默认为1
        //merchant.setRoleIds(roleIds);//角色id
        //merchant.setContact(contact);//联系人
        merchant.setContactWay(contactWay);//联系方式
        merchant.setProjectId(projectId);//项目id
        merchant.setProvinceId(provinceId);//省id
        merchant.setCityId(cityId);//市id
        merchant.setAreaId(areaId);//区县id
        merchant.setAddress(address);//详细地址
        //merchant.setRemark(remark);//备注
        //merchantService.add(merchant, secIndustryCode);//添加商户
        Long merchantId = MerchantClient.add(merchant, secIndustryCode);//调数据中心接口添加商户
        return merchantId;
    }
    
    /**
     * 创建用户表记录
     * @param userName 用户名
     * @param contactWay 联系方式
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param projectId 项目id
     * @return 用户id
     * @author 许小满  
     * @date 2017年5月22日 下午7:06:38
     */
    private Long addToeUser(String userName, String contactWay,
            Long provinceId, Long cityId, Long areaId, Long projectId) {
        ToeUser user = new ToeUser();
        user.setUserName(userName);//用户名（账号）
        user.setProvinceId(provinceId);//省id
        user.setCityId(cityId);//市id
        user.setAreaId(areaId);//区县id
        //user.setContactPerson(contact);//联系人
        user.setContactWay(contactWay);//联系方式
        //user.setRemark(remark);//备注
        user.setProjectId(projectId);//项目id
        //user.setCreateUserId(curUserId);
        Long toeUserId = toeUserService.add(user);
        return toeUserId;
    }
    
    /**
     * 设备绑定
     * @param broadbandAccount 宽带账号
     * @param merchantId 商户id
     * @param merchantName 商户名称
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param address 详细地址
     * @param deviceId 虚拟设备id
     * @param projectId 项目id
     * @param ssid SSID
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 下午7:48:24
     */
    private void bind(String broadbandAccount, Long merchantId,
            String merchantName, Long provinceId, Long cityId, Long areaId,
            String address, String deviceId, Long projectId, String ssid)
            throws Exception {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("deviceId", deviceId);//虚拟设备id
        paramMap.put("merchantId", merchantId);//商户id
        paramMap.put("broadbandAccount", broadbandAccount);//宽带账号 可空
        paramMap.put("belongTo", projectId != null ? FormatUtil.formatBelongToByProjectId(projectId.intValue()) : StringUtils.EMPTY);//所属平台
        paramMap.put("ssid", ssid);//SSID
        paramMap.put("province", provinceId);//省id
        paramMap.put("city", cityId);//市id
        paramMap.put("county", areaId);//区县id
        paramMap.put("address", address);//详细地址
        paramMap.put("attrInfo1", merchantName);//目前为酒店名可空
        //paramMap.put("longitude", 12.34567);//经度, 可空
        //paramMap.put("latitude", 13.34567);//纬度, 可空
        DeviceClient.bind(paramMap);
    }
    
}
