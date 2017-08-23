package com.awifi.np.biz.api.client.dbcenter.merchant.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.service.MerchantApiService;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月24日 上午8:54:11
 * 创建作者：周颖
 * 文件名称：MerchantApiServiceImpl.java
 * 版本：  v1.0
 * 功能：商户实现类
 * 修改记录：
 */
@Service("merchantApiService")
@SuppressWarnings("unchecked")
public class MerchantApiServiceImpl implements MerchantApiService {

    /**
     * 商户列表总数
     * @param paramsMap 参数
     * @return 符合条件的总条数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 上午9:04:25
     */
    public int getCountByParam(Map<String,Object> paramsMap) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getmerchantcount_url");//获取请求地址
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", CastUtil.toLong(paramsMap.get("merchantId")));//商户id
        param.put("ids", (String)paramsMap.get("merchantIds"));//商户ids
        param.put("type", (String)paramsMap.get("type"));//层级关系-nextLevel 只查当前节点的下一层
                                                              // --nextAll查当前节点所有不包括当前
                                                              // --nextAllWithThis查当前节点所有包含当前
        param.put("merchantName", (String)paramsMap.get("merchantName"));//精确
        param.put("merchantNameLike", (String)paramsMap.get("keywords"));//商户名称全模糊
        String industryId = (String)paramsMap.get("industryId");
        if (null != industryId) {//行业编号
            if (industryId.length() == 8 || industryId.length() == 11) {
                param.put("industry", industryId);
            } else {
                param.put("industryRLike", industryId);
            }
        }
        param.put("merchantType", CastUtil.toInteger(paramsMap.get("merchantType")));//1：商客/2：行客如果该字段为空为，默认为1
        param.put("merchantProjects",(String)paramsMap.get("projectIds"));
        param.put("notEqualProjectIds",(String)paramsMap.get("filterProjectIds"));
        param.put("province", CastUtil.toLong(paramsMap.get("provinceId")));//省id
        param.put("city", CastUtil.toLong(paramsMap.get("cityId")));//市id
        param.put("county", CastUtil.toLong(paramsMap.get("areaId")));//区县id
        param.put("status", 1);//状态 1正常 9删除
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(param), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer)returnMap.get("rs");//返回总条数
    }
    
    /**
     * 商户列表
     * @param paramsMap 参数
     * @return 符合条件的记录
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 上午9:04:25
     */
    public List<Merchant> getListByParam(Map<String,Object> paramsMap) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getmerchantlist_url");//获取请求地址
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", CastUtil.toLong(paramsMap.get("merchantId")));//商户id
        param.put("ids", (String)paramsMap.get("merchantIds"));//商户ids
        param.put("type", (String)paramsMap.get("type"));//层级关系-nextLevel 只查当前节点的下一层
                                                              // --nextAll查当前节点所有不包括当前
                                                              // --nextAllWithThis查当前节点所有包含当前
        param.put("merchantName", (String)paramsMap.get("merchantName"));//精确
        param.put("merchantNameLike", (String)paramsMap.get("keywords"));//商户名称全模糊
        String industryId = (String)paramsMap.get("industryId");
        if (null != industryId) {//行业编号
            if (industryId.length() == 8 || industryId.length() == 11) {
                param.put("industry", industryId);
            } else {
                param.put("industryRLike", industryId);
            }
        }
        param.put("merchantType", CastUtil.toInteger(paramsMap.get("merchantType")));//1：商客/2：行客如果该字段为空为，默认为1
        param.put("merchantProjects",(String)paramsMap.get("projectIds"));
        param.put("notEqualProjectIds",(String)paramsMap.get("filterProjectIds"));
        param.put("province", CastUtil.toLong(paramsMap.get("provinceId")));//省id
        param.put("city", CastUtil.toLong(paramsMap.get("cityId")));//市id
        param.put("county", CastUtil.toLong(paramsMap.get("areaId")));//区县id
        param.put("status", 1);//状态 1正常 9删除
        param.put("pageNum", CastUtil.toInteger(paramsMap.get("pageNo")));//页码
        param.put("pageSize", CastUtil.toInteger(paramsMap.get("pageSize")));//每页记录数
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(param), "UTF-8");//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//返回成功的数据
        List<Map<String,Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");//商户列表
        int maxSize = returnList.size();//list
        List<Merchant> merchantList = new ArrayList<Merchant>(maxSize);//返回的list
        Merchant merchant = null;
        String industryIdString;
        for(int i=0;i<maxSize;i++){
            Map<String,Object> merchantMap = returnList.get(i);
            merchant = new Merchant();
            merchant.setId(CastUtil.toLong(merchantMap.get("id")));//商户id
            merchant.setMerchantName((String) merchantMap.get("merchantName"));//商户名称
            merchant.setMerchantType((Integer) merchantMap.get("merchantType"));//商户类型：1代表中小商户、2代表行客
            merchant.setCascadeLabel((String) merchantMap.get("cascadeLabel"));//级联标签
            merchant.setCascadeLevel((Integer) merchantMap.get("cascadeLevel"));//级联等级
            merchant.setParentId(CastUtil.toLong(merchantMap.get("parentId")));//父id
            merchant.setContact((String) merchantMap.get("fullName"));//联系人
            merchant.setContactWay((String) merchantMap.get("telephone"));//联系方式
            merchant.setProjectId(CastUtil.toLong(merchantMap.get("merchantProject")));
            industryIdString = (String) merchantMap.get("industry");//行业标签
            if(StringUtils.isNotBlank(industryIdString)){
                merchant.setPriIndustryCode(industryIdString.substring(0, 6));//一级行业
                if(industryIdString.length() >7){//二级行业
                    merchant.setSecIndustryCode(industryIdString.substring(0, 8));
                }
            }
            merchant.setProvinceId(CastUtil.toLong(merchantMap.get("province")));//省id
            merchant.setProvince((String)merchantMap.get("provinceText"));//省名称
            merchant.setCityId(CastUtil.toLong(merchantMap.get("city")));//市id
            merchant.setCity((String) merchantMap.get("cityText"));//市名称
            merchant.setAreaId(CastUtil.toLong(merchantMap.get("county")));//区县id
            merchant.setArea((String) merchantMap.get("countyText"));//区县名称
            merchant.setAddress((String) merchantMap.get("address"));//详细地址
            merchant.setRemark((String) merchantMap.get("remarks"));//备注
            merchant.setStoreType((Integer) merchantMap.get("storeType"));//销售点二级分类
            merchant.setStoreLevel((Integer) merchantMap.get("storeLevel"));//自有厅级别
            merchant.setStoreStar((Integer) merchantMap.get("storeStar"));//专营店星级
            merchant.setStoreScope(CastUtil.toInteger(merchantMap.get("storeScope")));//专营店类别
            merchant.setConnectType((String) merchantMap.get("connectType"));//接入方式
            merchantList.add(merchant);
        }
        return merchantList;
    }
    
    /**
     * 获取商户详情
     * @param merchantId 商户id
     * @return 商户详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月4日 上午9:04:47
     */
    public Merchant getById(Long merchantId) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getmerchant_url");//获取请求地址
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", merchantId);//商户id
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(param), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//返回成功的数据
        Map<String,Object> merchantMap = (Map<String, Object>) returnMap.get("rs");
        if(merchantMap == null || merchantMap.isEmpty()){
            throw new InterfaceException(MessageUtil.getMessage("E2000066", new Object[]{"商户", merchantId.toString()}), url, paramString);//{0}（{1}）不存在!
        }
        Merchant merchant = new Merchant();
        merchant.setId(CastUtil.toLong(merchantMap.get("id")));//商户id
        merchant.setUserId(CastUtil.toLong(merchantMap.get("userId")));//关系用户id
        merchant.setMerchantName((String) merchantMap.get("merchantName"));//商户名称
        merchant.setMerchantType((Integer) merchantMap.get("merchantType"));//商户类型：1代表中小商户、2代表行客
        merchant.setCascadeLabel((String) merchantMap.get("cascadeLabel"));//级联标签
        merchant.setCascadeLevel((Integer) merchantMap.get("cascadeLevel"));//级联等级
        merchant.setParentId(CastUtil.toLong(merchantMap.get("parentId")));//父id
        merchant.setContact((String) merchantMap.get("fullName"));//联系人
        merchant.setContactWay((String) merchantMap.get("telephone"));//联系方式
        merchant.setProjectId(CastUtil.toLong(merchantMap.get("merchantProject")));
        String industryIdString = (String) merchantMap.get("industry");//行业标签
        if(StringUtils.isNotBlank(industryIdString)){
            merchant.setPriIndustryCode(industryIdString.substring(0, 6));//一级行业
            if(industryIdString.length() >7){//二级行业
                merchant.setSecIndustryCode(industryIdString.substring(0, 8));
            }
        }
        merchant.setProvinceId(CastUtil.toLong(merchantMap.get("province")));
        merchant.setCityId(CastUtil.toLong(merchantMap.get("city")));
        merchant.setAreaId(CastUtil.toLong(merchantMap.get("county")));
        merchant.setAddress((String) merchantMap.get("address"));//详细地址
        merchant.setRemark((String) merchantMap.get("remarks"));//备注
        merchant.setStoreType((Integer) merchantMap.get("storeType"));//销售点二级分类
        merchant.setStoreLevel((Integer) merchantMap.get("storeLevel"));//自有厅级别
        merchant.setStoreStar((Integer) merchantMap.get("storeStar"));//专营店星级
        merchant.setStoreScope(CastUtil.toInteger(merchantMap.get("storeScope")));//专营店类别
        merchant.setConnectType((String) merchantMap.get("connectType"));//接入方式
        merchant.setStatus(CastUtil.toInteger(merchantMap.get("status")));//状态
        merchant.setOpenTime((String) merchantMap.get("openTime"));//营业开门时间
        merchant.setCloseTime((String) merchantMap.get("closeTime"));//营业关门时间
        merchant.setThumb((String) merchantMap.get("thumb")); //商户图标
        return merchant;
    }
    
    /**
     * 添加商户
     * @param merchant 商户
     * @param industryCode 行业标签
     * @return 商户id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月4日 下午2:36:50
     */
    public Long add(Merchant merchant, String industryCode) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_addmerchant_url");//获取请求地址
        
        Integer merchantType = merchant.getMerchantType();//商户类型：1 商客、2 行客
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parentId", merchant.getParentId());//父编号编号
        param.put("accountId", 0);//账号id
        param.put("merchantName", merchant.getMerchantName());//商户名称, 必填
        param.put("industry", industryCode);//行业标签
        param.put("merchantType", merchantType != null ? merchantType : 2);//1：商客；--2：行客;--如果该参数不传入，默认为1
        param.put("merchantProject", merchant.getProjectId().toString());//商户所属工程
        param.put("fullName", merchant.getContact());//商户全名 联系人
        param.put("telephone", merchant.getContactWay());//手机号码
        param.put("province", merchant.getProvinceId());//省
        param.put("city", merchant.getCityId());//市
        param.put("county", merchant.getAreaId());//县
        param.put("address", merchant.getAddress());//详细地址
        param.put("remarks", merchant.getRemark());//描述
        param.put("storeType", merchant.getStoreType());//销售点二级分类
        param.put("storeLevel", merchant.getStoreLevel());//自有厅级别
        param.put("storeStar", merchant.getStoreStar());//专营店星级
        Integer storeScope = merchant.getStoreScope();
        if(storeScope != null){
            param.put("storeScope", storeScope.toString());//专营店类别
        }
        param.put("connectType", merchant.getConnectType());//接入方式
        param.put("userId", merchant.getUserId());//用户id
        Map<String, Object> returnMap = CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(param));//返回成功的数据
        return CastUtil.toLong(returnMap.get("rs"));
    }
    
    /**
     * 编辑商户
     * @param merchant 商户
     * @param industryCode 行业编号
     * @author 周颖  
     * @throws Exception 
     * @throws Exception  
     * @date 2017年2月6日 下午4:43:33
     */
    public void update(Merchant merchant, String industryCode) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_updatemerchant_url");//获取请求地址
        Map<String, Object> param = new HashMap<String, Object>();
        Long merchantId = merchant.getId();//商户id
        param.put("id", merchantId);//商户id
        param.put("merchantName", merchant.getMerchantName());//商户名称, 必填
        param.put("industry", industryCode);//行业标签
        param.put("merchantProject", merchant.getProjectId().toString());//商户所属工程
        param.put("fullName", merchant.getContact());//商户全名 联系人
        param.put("telephone", merchant.getContactWay());//手机号码
        param.put("province", merchant.getProvinceId());//省
        Long cityId = merchant.getCityId();
        param.put("city", cityId != null ? cityId : -1);//市
        Long areaId = merchant.getAreaId();
        param.put("county", areaId != null ? areaId : -1);//县
        param.put("address", merchant.getAddress());//详细地址
        param.put("remarks", merchant.getRemark());//描述
        param.put("storeType", merchant.getStoreType());//销售点二级分类
        param.put("storeLevel", merchant.getStoreLevel());//自有厅级别
        param.put("storeStar", merchant.getStoreStar());//专营店星级
        Integer storeScope = merchant.getStoreScope();
        if(storeScope != null){
            param.put("storeScope", storeScope.toString());//专营店类别
        }
        param.put("connectType", merchant.getConnectType());//接入方式
        param.put("openTime", merchant.getOpenTime());//营业开门时间
        param.put("closeTime", merchant.getCloseTime());//营业关门时间
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(param));//返回成功的数据
        RedisUtil.del(RedisConstants.MERCHANT + merchantId);//删除商户redis缓存
    }
    
    /**
     * 批量添加商户
     * @param parentId 父商户id
     * @param merchantList 商户列表
     * @return 结果（账号和商户id的对应关系）
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月7日 上午8:41:17
     */
    public Map<String, Long> batchAdd(Long parentId,List<Map<String, Object>> merchantList) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_batchaddmerchant_url");//获取请求地址
        Map<Long,List<Map<String, Object>>> paramMap = new HashMap<Long,List<Map<String, Object>>>();
        paramMap.put(parentId, merchantList);//入参
        Map<String, Object> returnMap = CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramMap));//返回成功的数据
        Map<String,Object> rs = (Map<String, Object>) returnMap.get("rs");
        Map<String,Long> resultMap = new HashMap<String,Long>();
        Long merchantId = null;
        for(String key : rs.keySet()){
            merchantId = CastUtil.toLong(rs.get(key));
            if(merchantId == null){
                continue;
            }
            resultMap.put(key, merchantId);
        }
        return resultMap;
    }
}