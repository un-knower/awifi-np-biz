package com.awifi.np.biz.devicebindsrv.system.industry.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.devicebindsrv.system.industry.service.IndustryService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月20日 上午9:54:47
 * 创建作者：周颖
 * 文件名称：IndustryServiceImpl.java
 * 版本：  v1.0
 * 功能：行业实现类
 * 修改记录：
 */
@Service("industryService")
public class IndustryServiceImpl implements IndustryService {

    /**
     * 获取行业信息
     * @param parentCode 父行业编号
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午9:59:50
     */
    public List<Map<String, String>> getListByParam(String parentCode) throws Exception{
        String key;//redisKey
        Integer endIndex;//
        List<Map<String, String>> industryList = new ArrayList<Map<String,String>>();
        if(StringUtils.isNotBlank(parentCode)){//父编号不为空 查二级行业
            key = RedisConstants.INDUSTRY + parentCode + "*_2";//redisKey
            endIndex = 24;//截取八位二级行业编号
        }else{//查一级行业
            key = RedisConstants.INDUSTRY + "*_1";//redisKey 
            endIndex = 22;//截取六位 一级行业编号
        }
        Map<String,String> redisMap = RedisUtil.getBatch(key);//获取行业信息
        if(redisMap == null || redisMap.isEmpty()){//如果为空 重新缓存再获取
            IndustryClient.cache();//重新缓存
            redisMap = RedisUtil.getBatch(key);//获取行业信息
        }
        industryList = this.formateIndustry(redisMap,endIndex);//格式化
        return industryList;
    }
    
    /**
     * redisMap 格式化
     * @param redisMap redisMap
     * @param endIndex 截取
     * @return 行业数据
     * @author 周颖  
     * @date 2017年1月20日 上午11:06:05
     */
    private List<Map<String,String>> formateIndustry(Map<String,String> redisMap,int endIndex){
        if(redisMap == null || redisMap.isEmpty()){//如果为空 重新缓存再获取
            return null;
        }
        String key;//redisKey
        String industryCode;//行业编号
        String industryName;//行业名称
        Map<String,String> industryMap;
        List<Map<String,String>> industryList = new ArrayList<Map<String,String>>();
        for(Map.Entry<String, String> entry : redisMap.entrySet()){
            industryMap = new LinkedHashMap<String,String>();
            key = entry.getKey();//获取map key
            industryCode = key.substring(16, endIndex);//截取行业编号
            industryName = entry.getValue();//获取map value
            industryMap.put("industryCode", industryCode);//保存行业编号
            industryMap.put("industryName", industryName);//保存行业名称
            industryList.add(industryMap);
        }
        return sort(industryList);//返回数据
    }
    
    /**
     * 按industryCode排序
     * @param industryList 列表
     * @return 排序后的列表
     * @author 周颖  
     * @date 2017年2月20日 下午2:44:00
     */
    private List<Map<String,String>> sort(List<Map<String,String>> industryList){
        int maxSize = industryList.size();
        for(int i=0;i<maxSize;i++){
            for(int j=i;j<maxSize;j++){
                Map<String,String> startIndustry = industryList.get(i);
                Map<String,String> endIndustry = industryList.get(j);
                String startCode = industryList.get(i).get("industryCode");
                String endCode = industryList.get(j).get("industryCode");
                if(startCode.compareTo(endCode) > 0){
                    industryList.set(i, endIndustry);
                    industryList.set(j, startIndustry);
                }
            }
        }
        return industryList;
    }
}