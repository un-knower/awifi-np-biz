package com.awifi.np.biz.api.client.dbcenter.merchant.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.service.MerchantApiService;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心 
 * 创建日期:2017年2月3日 上午8:50:32 
 * 创建作者：周颖 
 * 文件名称：MerchantClient.java
 * 版本： v1.0 
 * 功能：商户工具类 
 * 修改记录：
 */
public class MerchantClient {
    
    /** 日志 */
    private static final Log logger = LogFactory.getLog(MerchantClient.class);

    /**
     * 商户
     */
    private static MerchantApiService merchantApiService;

    /**
     * 获取merchantApiService实例
     * @return merchantApiService
     * @author 周颖
     * @date 2017年2月3日 上午9:04:03
     */
    public static MerchantApiService getMerchantApiService() {
        if (merchantApiService == null) {
            merchantApiService = (MerchantApiService) BeanUtil.getBean("merchantApiService");
        }
        return merchantApiService;
    }

    /**
     * 商户列表总数
     * @param paramsMap 参数
     * @return 符合条件的总条数
     * @author 周颖
     * @throws Exception 异常
     * @date 2017年2月3日 上午9:04:25
     */
    public static int getCountByParam(Map<String,Object> paramsMap) throws Exception {
        return getMerchantApiService().getCountByParam(paramsMap);
    }

    /**
     * 商户列表
     * @param paramsMap 参数
     * @return 符合条件的记录
     * @author 周颖
     * @throws Exception 异常
     * @date 2017年2月3日 上午9:04:25
     */
    public static List<Merchant> getListByParam(Map<String,Object> paramsMap) throws Exception {
        return getMerchantApiService().getListByParam(paramsMap);
    }

    /**
     * 商户详情
     * @param merchantId 商户id
     * @return 商户详情
     * @author 周颖
     * @throws Exception 异常
     * @date 2017年2月4日 上午9:07:19
     */
    public static Merchant getById(Long merchantId) throws Exception {
        if(merchantId == null){
            logger.debug("错误：商户id为空！");
            return null;
        }
        return getMerchantApiService().getById(merchantId);
    }
    
    /**
     * 添加商户
     * @param merchant 商户
     * @param industryCode 行业标签
     * @return 商户id
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年2月4日 下午2:36:50
     */
    public static Long add(Merchant merchant, String industryCode) throws Exception{
        return getMerchantApiService().add(merchant,industryCode); 
    }

    /**
     * 从缓存中获取商户名称
     * @param id 商户id
     * @return 商户名称
     * @throws Exception 异常
     * @author 周颖
     * @date 2017年2月4日 上午9:13:03
     */
    public static String getNameByIdCache(Long id) throws Exception {
        // 1. 从缓存中获取
        String merchantName = getNameFromCache(id);
        // 1.1 如果存在，直接使用缓存中的商户名称
        if (StringUtils.isNotBlank(merchantName)) {
            return merchantName;
        }
        // 1.2 如果不存在，通过接口获取商户信息并缓存
        Merchant merchant = getById(id);// 从接口中获取
        putMerchantToCache(merchant);// 将商户信息存放到缓存中
        return merchant.getMerchantName();
    }
    
    /**
     * 获取商户详情 （先从缓存中取，为空再查数据中心 然后缓存）
     * @param id 商户id
     * @return 商户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月10日 下午7:13:22
     */
    public static Merchant getByIdCache(Long id) throws Exception{
        Merchant merchant = getMerchantFromCache(id);//缓存中获取
        if(merchant != null){//不为空，直接返回
            return merchant;
        }
        merchant = getById(id);//从接口中获取
        putMerchantToCache(merchant);//缓存
        return merchant;
    }
    
    /**
     * 从缓存获取商户详情
     * @param id 商户id
     * @return 商户详情
     * @author 周颖  
     * @date 2017年4月10日 下午7:12:21
     */
    private static Merchant getMerchantFromCache(Long id){
        String key = RedisConstants.MERCHANT + id;
        List<String> columnList = RedisUtil.hmget(key, 
                "id","merchantName","cascadeLabel","cascadeLevel","parentId","projectId",
                "priIndustryCode","priIndustry","secIndustryCode","secIndustry",
                "provinceId","province","cityId","city","areaId","area","merchantType","userId","status","telephone","openTime","closeTime","thumb");
        if(columnList == null || columnList.size() <= 0){
            return null;
        }
        //从缓存中获取客户信息
        String idString = columnList.get(0);//客户id
        if(StringUtils.isBlank(idString)){
            return null;
        }
        String merchantName = columnList.get(1);
        String cascadeLabel = columnList.get(2);
        String cascadeLevel = columnList.get(3);
        String parentId = columnList.get(4);
        String projectId = columnList.get(5);
        String priIndustryCode = columnList.get(6);
        String priIndustry = columnList.get(7);
        String secIndustryCode = columnList.get(8);
        String secIndustry = columnList.get(9);
        String provinceId = columnList.get(10);
        String province = columnList.get(11);
        String cityId = columnList.get(12);
        String city = columnList.get(13);
        String areaId = columnList.get(14);
        String area = columnList.get(15);
        String merchantType = columnList.get(16);
        String userId = columnList.get(17);
        String status = columnList.get(18);
        String telephone = columnList.get(19);
        String openTime = columnList.get(20);
        String closeTime = columnList.get(21);
        String thumb = columnList.get(22);

        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setMerchantName(StringUtils.defaultString(merchantName));
        merchant.setCascadeLabel(StringUtils.defaultString(cascadeLabel));
        merchant.setCascadeLevel(StringUtils.isNotBlank(cascadeLevel) ? Integer.parseInt(cascadeLevel) : null);
        merchant.setParentId(StringUtils.isNotBlank(parentId) ? Long.parseLong(parentId) : null);
        merchant.setProjectId(StringUtils.isNotBlank(projectId) ? Long.parseLong(projectId) : null);
        merchant.setPriIndustryCode(StringUtils.defaultString(priIndustryCode));
        merchant.setPriIndustry(StringUtils.defaultString(priIndustry));
        merchant.setSecIndustryCode(StringUtils.defaultString(secIndustryCode));
        merchant.setSecIndustry(StringUtils.defaultString(secIndustry));
        merchant.setProvinceId(StringUtils.isNotBlank(provinceId) ? Long.parseLong(provinceId) : null);
        merchant.setProvince(StringUtils.defaultString(province));
        merchant.setCityId(StringUtils.isNotBlank(cityId) ? Long.parseLong(cityId) : null);
        merchant.setCity(StringUtils.defaultString(city));
        merchant.setAreaId(StringUtils.isNotBlank(areaId) ? Long.parseLong(areaId) : null);
        merchant.setArea(StringUtils.defaultString(area));
        merchant.setMerchantType(StringUtils.isNotBlank(merchantType) ? Integer.parseInt(merchantType) : null);
        merchant.setUserId(StringUtils.isNotBlank(userId) ? Long.parseLong(userId) : null);
        merchant.setStatus(StringUtils.isNotBlank(status) ? Integer.parseInt(status) : null);
        merchant.setContactWay(StringUtils.defaultString(telephone));
        merchant.setOpenTime(StringUtils.defaultString(openTime));
        merchant.setCloseTime(StringUtils.defaultString(closeTime));
        merchant.setThumb(StringUtils.defaultString(thumb));
        return merchant;
    }

    /**
     * 从缓存获取商户名称
     * @param id 商户id
     * @return 商户名称
     * @author 周颖
     * @date 2017年2月4日 上午9:12:09
     */
    private static String getNameFromCache(Long id) {
        String key = RedisConstants.MERCHANT + id;
        List<String> columnList = RedisUtil.hmget(key, "merchantName");
        if (columnList == null || columnList.size() <= 0) {
            return null;
        }
        return columnList.get(0);
    }

    /**
     * 商户缓存redis
     * @param merchant 商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月4日 上午10:00:56
     */
    private static void putMerchantToCache(Merchant merchant) throws Exception {
        // 补全行业
        String industryName = StringUtils.EMPTY;
        String industryId = merchant.getPriIndustryCode();// 一级行业标签
        if (StringUtils.isNotBlank(industryId)) {
            industryName = IndustryClient.getNameByCode(industryId);// 从缓存中拿
            merchant.setPriIndustry(industryName);
        }
        industryId = merchant.getSecIndustryCode();// 二级行业标签
        if (StringUtils.isNotBlank(industryId)) {
            industryName = IndustryClient.getNameByCode(industryId);// 从缓存中拿
            merchant.setSecIndustry(industryName);
        }
        //补全地区
        Long provinceId = merchant.getProvinceId();// 省ID
        Long cityId = merchant.getCityId();// 市ID
        Long areaId = merchant.getAreaId();// 区县ID
        String locationName = StringUtils.EMPTY;
        String param = "name";//地区名称
        if (provinceId != null) {
            locationName = LocationClient.getByIdAndParam(provinceId, param);
            merchant.setProvince(locationName);// 设置省
        }
        if (cityId != null) {
            locationName = LocationClient.getByIdAndParam(cityId, param);
            merchant.setCity(locationName);// 设置市
        }
        if (areaId != null) {
            locationName = LocationClient.getByIdAndParam(areaId, param);
            merchant.setArea(locationName);// 设置区县
        }
        Long id = merchant.getId();// 商户id
        Long projectId = merchant.getProjectId();// 项目id
        Integer cascadeLevel = merchant.getCascadeLevel();// 层次等级
        Integer merchantType = merchant.getMerchantType();//商户类型
        Long parentId = merchant.getParentId();// 商户父id
        Long userId = merchant.getUserId();//关系用户Id
        Integer status = merchant.getStatus();//状态
        String key = RedisConstants.MERCHANT + id;
        Map<String, String> map = new HashMap<String, String>(23);
        map.put("id", id.toString());// 商户id
        map.put("merchantName",StringUtils.defaultString(merchant.getMerchantName()));// 商户名称
        map.put("cascadeLabel",StringUtils.defaultString(merchant.getCascadeLabel()));// 商户层级
        map.put("cascadeLevel", cascadeLevel != null ? cascadeLevel.toString() : StringUtils.EMPTY);// 层次等级
        map.put("parentId", parentId != null ? parentId.toString() : StringUtils.EMPTY);// 商户父ID
        map.put("projectId", projectId != null ? projectId.toString() : StringUtils.EMPTY);// 项目id
        map.put("priIndustryCode",StringUtils.defaultString(merchant.getPriIndustryCode()));// 一级行业编号
        map.put("priIndustry",StringUtils.defaultString(merchant.getPriIndustry()));// 一级行业名称
        map.put("secIndustryCode",StringUtils.defaultString(merchant.getSecIndustryCode()));// 二级行业编号
        map.put("secIndustry",StringUtils.defaultString(merchant.getSecIndustry()));// 二级行业名称
        map.put("provinceId", provinceId != null ? provinceId.toString() : StringUtils.EMPTY);// 省id
        map.put("province", StringUtils.defaultString(merchant.getProvince()));// 省
        map.put("cityId", cityId != null ? cityId.toString() : StringUtils.EMPTY);// 市id
        map.put("city", StringUtils.defaultString(merchant.getCity()));// 市
        map.put("areaId", areaId != null ? areaId.toString() : StringUtils.EMPTY);// 区/县id
        map.put("area", StringUtils.defaultString(merchant.getArea()));// 区/县
        map.put("merchantType", merchantType != null ? merchantType.toString() : StringUtils.EMPTY);// 商户类型
        map.put("userId", userId != null ? userId.toString() : StringUtils.EMPTY);
        map.put("status", status != null ? status.toString() : StringUtils.EMPTY);
        map.put("telephone",StringUtils.defaultString(merchant.getContactWay()));//手机号码
        map.put("openTime",StringUtils.defaultString(merchant.getOpenTime()));//营业开门时间
        map.put("closeTime",StringUtils.defaultString(merchant.getCloseTime()));//营业关门时间
        map.put("thumb", StringUtils.defaultString(merchant.getThumb()));//商户图标
        RedisUtil.hmset(key, map, RedisConstants.MERCHANT_TIME);
    }

    /**
     * 编辑商户
     * @param merchant 商户
     * @param industryCode 行业编号
     * @author 周颖  
     * @throws Exception  
     * @date 2017年2月6日 下午4:43:33
     */
    public static void update(Merchant merchant, String industryCode) throws Exception {
        getMerchantApiService().update(merchant,industryCode);
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
    public static  Map<String, Long> batchAdd(Long parentId,List<Map<String,Object>> merchantList) throws Exception{
        return getMerchantApiService().batchAdd(parentId,merchantList);
    }
    
    /**
     * 获取商户项目id 缓存中不存在去数据中心取
     * @param id 商户id
     * @return 项目id
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年8月2日 上午11:08:02
     */
    public static Long getProjectIdById(Long id) throws Exception {
        // 1. 从缓存中获取
        Long projectId = getProjectIdFromCache(id);
        // 1.1 如果存在，直接使用缓存中的商户名称
        if (projectId != null) {
            return projectId;
        }
        // 1.2 如果不存在，通过接口获取商户信息并缓存
        Merchant merchant = getById(id);// 从接口中获取
        putMerchantToCache(merchant);// 将商户信息存放到缓存中
        return merchant.getProjectId();
    }
    
    /**
     * 从缓存中获取项目id
     * @param id 商户id
     * @return 项目id
     * @author 周颖  
     * @date 2017年8月2日 上午11:07:44
     */
    private static Long getProjectIdFromCache(Long id) {
        String key = RedisConstants.MERCHANT + id;
        List<String> columnList = RedisUtil.hmget(key, "projectId");
        if (columnList == null || columnList.size() <= 0) {
            return null;
        }
        String projectId = columnList.get(0);;
        return StringUtils.isNotBlank(projectId) ? Long.parseLong(projectId) : null;
    }
}