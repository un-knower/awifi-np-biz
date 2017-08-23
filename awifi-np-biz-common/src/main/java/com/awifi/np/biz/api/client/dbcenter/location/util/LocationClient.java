package com.awifi.np.biz.api.client.dbcenter.location.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.api.client.dbcenter.location.service.LocationApiService;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月22日 上午8:50:23
 * 创建作者：周颖
 * 文件名称：LocationClient.java
 * 版本：  v1.0
 * 功能：地区
 * 修改记录：
 */
public class LocationClient {

    /** 日志 */
    private static final Log logger = LogFactory.getLog(LocationClient.class);
    
    /**locationApiService 地区服务*/
    private static LocationApiService locationApiService;
    
    /**
     * 获取locationApiService实例
     * @return locationApiService
     * @author 周颖  
     * @date 2017年1月22日 上午9:03:56
     */
    private static LocationApiService getLocationApiService(){
        if(locationApiService == null){
            locationApiService = (LocationApiService) BeanUtil.getBean("locationApiService");
        }
        return locationApiService;
    }
    
    /**
     * 获取全部地区信息
     * @author 周颖 
     * @return map 
     * @throws Exception 
     * @date 2017年1月22日 上午10:05:34
     */
    public static Map<Long,Map<String,Object>> getAllLocation() throws Exception{
        return getLocationApiService().getAllLocation();
    }
    

    /**
     * 缓存地区信息
     * @author 周颖 
     * @throws Exception 
     * @date 2017年1月22日 上午10:05:34
     */
    public static void cache() throws Exception{
        Map<Long,Map<String,Object>> allLocationMap = getAllLocation();//全部地区
        String key;//获取地区key前缀
        Long id;//地区主键
        Long parentId;//父id
        Map<String,Object> locationMap;//地区属性
        Map<String,Map<String,String>> redisLocationMap = new HashMap<String,Map<String,String>>();
        for(Map.Entry<Long, Map<String,Object>> entry : allLocationMap.entrySet()){//循环map
            id = entry.getKey();//获取地区主键id
            locationMap = entry.getValue();//获取地区属性
            parentId = (Long) locationMap.get("parentId");//获取父id
            if(parentId == null){//如果为空 继续
                continue;
            }
            key = RedisConstants.LOCATION + id + "_" + parentId;//rediskey
            redisLocationMap.put(key, toStringMap(locationMap));//保存 key-value
        }
        RedisUtil.hmsetBatch(redisLocationMap, RedisConstants.LOCATION_TIME);//redis缓存
    }
    
    /**
     * 将Map<String,Object> 转为 Map<String,String>
     * @param locationMap 地区map
     * @return map
     * @author 许小满  
     * @date 2016年11月3日 下午12:51:25
     */
    private static Map<String,String> toStringMap(Map<String,Object> locationMap){
        Long id = (Long)locationMap.get("id");//地区id
        String name = (String)locationMap.get("name");//地区名称
        String fullName = (String)locationMap.get("fullName");//地区全路径
        String code = (String)locationMap.get("code");//地区编号 -- 用于排序
        Long parentId = (Long)locationMap.get("parentId");//父id
        //String type = (String)locationMap.get("type");//地区级别：PROVINCE 省、CITY 市、 COUNTY 区县
        
        if(id == null || name == null || code == null || parentId == null){
            logger.debug("错误：存在空的记录  id[" + id + "] : name[" + name + "] : code[" + code + "] : parentId[" + parentId + "]");;
        }
        
        Map<String,String> locationStrMap = new HashMap<String,String>(4);
        locationStrMap.put("id", id.toString());//地区id
        locationStrMap.put("name", StringUtils.defaultString(name));//地区名称
        locationStrMap.put("fullName", StringUtils.defaultString(fullName));//地区全路径
        locationStrMap.put("code", StringUtils.defaultString(code));//地区编号 -- 用于排序
        locationStrMap.put("parentId", parentId.toString());//父id
        //locationStrMap.put("type", type);//地区级别：PROVINCE 省、CITY 市、 COUNTY 区县
        return locationStrMap;
    }
    
    /**
     * 获取地区
     * @param locationId 地区id
     * @param param 参数  fullName||name
     * @return 全路径
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月3日 下午5:24:01
     */
    public static String getByIdAndParam(Long locationId,String param) throws Exception{
        //1. 从redis读取数据
        String redisKey = RedisConstants.LOCATION + locationId + "_*";//redis key
        //1.1 当集合不为空时，返回地区数据集合
        List<String> locationList = RedisUtil.hmgetLike(redisKey, param);
        if(locationList != null && locationList.size() > 0){
            return locationList.get(0);
        }
        //1.2 当集合为空时，判断redis 是否已经存在
        boolean isExist = RedisUtil.exist(RedisConstants.LOCATION + "2_1");//判断地区信息是否缓存 以北京为例
        if(isExist){//有缓存
            return null;
        }
        //1.2.2 如果不存在，将地区存入redis中
        cache();
        //1.2.1 重新从redis读取数据
        locationList = RedisUtil.hmgetLike(redisKey, param);
        if(locationList != null && locationList.size() > 0){
            return locationList.get(0);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 地区 key为地区名称，value为该地区属性
     * @return map
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月10日 上午9:56:08
     */
    public static Map<String, List<Map<String, Object>>> getLocationMap() throws Exception {
        return getLocationApiService().getLocationMap();
    }
}