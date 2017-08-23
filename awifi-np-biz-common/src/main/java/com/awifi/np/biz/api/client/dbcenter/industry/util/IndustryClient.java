package com.awifi.np.biz.api.client.dbcenter.industry.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.dbcenter.industry.service.IndustryApiService;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月19日 下午2:49:40
 * 创建作者：周颖
 * 文件名称：IndustryClient.java
 * 版本：  v1.0
 * 功能： 行业工具类
 * 修改记录：
 */
public class IndustryClient {

    /**行业服务*/
    private static IndustryApiService industryApiService;
    
    /**
     * 获取industryApiService实例
     * @return industryApiService
     * @author 周颖  
     * @date 2017年1月20日 上午9:04:43
     */
    public static IndustryApiService getIndustryApiService() {
        if (industryApiService == null) {
            industryApiService = (IndustryApiService) BeanUtil.getBean("industryApiService");
        }
        return industryApiService;
    }
    
    /**
     * 获取行业所有信息
     * @return 行业list
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午9:05:37
     */
    public static List<Map<String, Object>> getAllIndustry() throws Exception{
        return getIndustryApiService().getAllIndustry();
    }  
    
    /**
     * 缓存行业信息
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午10:27:35
     */
    public static void cache() throws Exception{
        List<Map<String,Object>> industryList = getAllIndustry();//获取所有行业信息
        Map<String,String> redisMap = new HashMap<String,String>();
        String labelCode;//行业编码
        String labelName;//行业名称
        Integer labelLevel;//行业层级 1:一级行业 2:二级行业
        String key;//redisKey
        for(Map<String,Object> indestryMap : industryList){
            labelCode = (String) indestryMap.get("labelCode");//获取行业编号
            labelLevel = (Integer) indestryMap.get("labelLevel");//获取行业层级
            labelName = (String) indestryMap.get("labelName");//获取行业名称
            if(StringUtils.isNotBlank(labelCode) && labelCode.contains("OCAB")){//只保存OCAB开头的行业
                key = RedisConstants.INDUSTRY + labelCode + "_" +labelLevel;//生成key
                redisMap.put(key, labelName);
            }
        }
        RedisUtil.setBatch(redisMap, RedisConstants.INDUSTRY_TIME);//批量添加key
    }
    
    /**
     * 获取行业名称
     * @param industryCode 行业编号
     * @return 行业名称
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年1月20日 下午4:38:55
     */
    public static String getNameByCode(String industryCode) throws Exception{
        if(StringUtils.isBlank(industryCode)){//如果为空 直接返回
            return null;
        }
        Integer labelLevel = 1;//行业层级 一级行业
        if(industryCode.length() >= 8){//二级行业
            labelLevel = 2;
        }
        String redisKey = RedisConstants.INDUSTRY + industryCode + "_" + labelLevel;//redisKey
        String redisValue = RedisUtil.get(redisKey);//获取value
        if(StringUtils.isNotBlank(redisValue)){//不为空
            return redisValue;
        }
        Set<String> keys = RedisUtil.keys(RedisConstants.INDUSTRY + "*");//判断行业信息是否缓存
        if(keys.size() > 0){//有缓存
            return null;
        }
        cache();//缓存
        return RedisUtil.get(redisKey);
    }
    
    /**
     * 行业集合
     * @return 行业名称+行业信息
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月10日 上午9:34:15
     */
    public static Map<String,List<Map<String,Object>>> getIndustryMap() throws Exception{
        List<Map<String, Object>> allIndustryList = getAllIndustry();
        Map<String, List<Map<String, Object>>> allIndustryMap = new HashMap<String, List<Map<String, Object>>>();
        List<Map<String, Object>> industryList = null;
        Map<String, Object> industryMap = null;
        String labelCode;//行业编码
        String labelName;//行业名称
        Integer labelLevel;//行业层级 1:一级行业 2:二级行业
        for(Map<String, Object> industry : allIndustryList){
            labelCode = (String) industry.get("labelCode");//获取行业编号
            if(StringUtils.isNotBlank(labelCode) && labelCode.contains("OCAB")){//只保存OCAB开头的行业
                industryMap = new HashMap<String, Object>();
                industryMap.put("industryId", labelCode);
                labelLevel = (Integer) industry.get("labelLevel");//获取行业层级
                industryMap.put("industryLevel", labelLevel.toString());
                labelName = (String) industry.get("labelName");//获取行业名称
                industryMap.put("industryName", labelName);
                industryList = allIndustryMap.get(labelName);
                if(industryList == null){
                    industryList = new ArrayList<Map<String, Object>>();
                    allIndustryMap.put(labelName, industryList);
                }
                industryList.add(industryMap);
            }
        }
        return allIndustryMap;
    }
}