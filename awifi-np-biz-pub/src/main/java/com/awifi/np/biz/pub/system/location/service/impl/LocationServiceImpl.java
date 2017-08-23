package com.awifi.np.biz.pub.system.location.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.pub.system.location.service.LocationService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月22日 上午8:48:06
 * 创建作者：周颖
 * 文件名称：LocationServiceImpl.java
 * 版本：  v1.0
 * 功能：地区实现类
 * 修改记录：
 */
@Service("locationService")
public class LocationServiceImpl implements LocationService {

    /** 日志 */
    private static final Log logger = LogFactory.getLog(LocationServiceImpl.class);
    
    /**
     * 获取省地区信息
     * @param sessionUser 当前登陆账号
     * @return list
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 下午2:26:43
     */
    public List<Map<String, Object>> getProvinces(SessionUser sessionUser) throws Exception{
        String redisKey = RedisConstants.LOCATION + "*_1";
        Long provinceId = sessionUser.getProvinceId();//从sessionuser获取省份信息
        if(provinceId != null){
            redisKey = RedisConstants.LOCATION + provinceId + "_*";//生成redisKey
        }
        return this.getLocation(redisKey);//返回list
    }
    
    /**
     * 获取市地区信息
     * @param sessionUser 当前登陆账号
     * @param provinceId 省id
     * @return list
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 下午2:26:43
     */
    public  List<Map<String,Object>> getCities(SessionUser sessionUser,String provinceId) throws Exception{
        String redisKey = RedisConstants.LOCATION + "*_"+ provinceId;//生成redisKey
        Long cityId = sessionUser.getCityId();
        if(cityId != null){
            redisKey = RedisConstants.LOCATION + cityId + "_*";//生成redisKey
        }
        return this.getLocation(redisKey);//只返回id+name的list
    }
    
    /**
     * 获取区县地区信息
     * @param sessionUser 当前登陆账号
     * @param cityId 市id
     * @return list
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 下午2:26:43
     */
    public  List<Map<String,Object>> getAreas(SessionUser sessionUser,String cityId) throws Exception{
        String redisKey = RedisConstants.LOCATION + "*_"+ cityId;//生成redisKey
        Long areaId = sessionUser.getAreaId();
        if(areaId != null){
            redisKey = RedisConstants.LOCATION + areaId + "_*";//生成redisKey
        }
        return this.getLocation(redisKey);//只返回id+name的list
    }
    
    /**
     * 从redis获取数据
     * @param redisKey redisKey
     * @return 结果
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月22日 下午3:34:54
     */
    private List<Map<String, Object>> getLocation(String redisKey) throws Exception{
        logger.debug("redisKey:"+redisKey);
        List<Map<String, String>> redisValue = RedisUtil.hgetAllBatch(redisKey);//获取key对应的值
        if(redisValue != null && redisValue.size() > 0){//不为空
            return formate(redisValue);//只返回id+name的list
        }
        Set<String> keys = RedisUtil.keys(RedisConstants.LOCATION + "*");//判断地区信息是否缓存
        if(keys.size() > 0){//有缓存
            return new ArrayList<Map<String, Object>>();
        }
        LocationClient.cache();//缓存
        redisValue = RedisUtil.hgetAllBatch(redisKey);//获取key对应的值
        if(redisValue != null && redisValue.size() > 0){//不为空
            return formate(redisValue);//只返回id+name的list
        }
        return new ArrayList<Map<String, Object>>();
    }

    /**
     * redis返回的list封装
     * @param redisValue redis list
     * @return list
     * @author 周颖  
     * @date 2017年1月22日 下午2:25:27
     */
    private List<Map<String, Object>> formate(List<Map<String, String>> redisValue) {
        sort(redisValue);//排序
        List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>(redisValue.size());
        Map<String,Object> locationMap = new LinkedHashMap<String,Object>();
        for(Map<String, String> map : redisValue){//循环
            locationMap = formate(map);//封装map
            if(locationMap == null){
                logger.debug("地区map为空！");
                continue;
            }
            returnMap.add(locationMap);
        }
        return returnMap;
    }

    /**
     * 按地区编号从小到大排序(采用冒泡排序)
     * @param locationMapList 地区map
     * @author 许小满  
     * @date 2016年11月11日 下午4:10:11
     */
    private static void sort(List<Map<String, String>> locationMapList){
        int maxSize = locationMapList.size();
        for(int i=1; i<maxSize; i++){
            for(int j=0; j<maxSize-i; j++){
                Map<String,String> curMap = locationMapList.get(j);//地区map
                Map<String,String> nextMap = locationMapList.get(j+1);//地区map
                
                String curCode = StringUtils.defaultString(curMap.get("code"), "zzz");//地区编号，为空时赋值为zzz，为了排序靠后
                String nextCode = StringUtils.defaultString(nextMap.get("code"), "zzz");//地区编号，为空时赋值为zzz，为了排序靠后
                
                if((curCode.compareTo(nextCode)) > 0){//位置互换
                    locationMapList.set(j, nextMap);
                    locationMapList.set(j+1, curMap);
                }
            }
        }
    }

    /**
     * 只返回id+name
     * @param map map
     * @return map
     * @author 周颖  
     * @date 2017年1月22日 下午2:21:38
     */
    private Map<String, Object> formate(Map<String, String> map) {
        Map<String, Object> formateMap = new LinkedHashMap<String,Object>();
        String idString = map.get("id");
        if(StringUtils.isBlank(idString)){
            logger.debug("地区id为空！");
            return null;
        }
        formateMap.put("id", Long.parseLong(idString));
        formateMap.put("areaName", StringUtils.defaultString(map.get("name")));
        return formateMap;
    }  
}