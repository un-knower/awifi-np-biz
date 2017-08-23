package com.awifi.np.biz.common.redis.util;

import com.awifi.np.biz.common.redis.command.RedisAdminService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午2:50:04
 * 创建作者：周颖
 * 文件名称：RedisAdminUtil.java
 * 版本：  v1.0
 * 功能： redis工具类
 * 修改记录：
 */
public class RedisAdminUtil {

    /** redis Bean  */
    private static RedisAdminService redisAdminService;
    
    /**
     * @return bean
     * @author 亢燕翔 
     * @date 2016年3月23日 上午11:28:10
     */
    public static RedisAdminService getRedisAdminService() {
        if (redisAdminService == null) {
            redisAdminService = (RedisAdminService) BeanUtil.getBean("redisAdminService");
        }
        return redisAdminService;
    }
    
    /**
     * 设置值以及生存时间
     * @param key 键
     * @param value 值
     * @param seconds 单位（秒）
     * @return value
     * @author 亢燕翔 
     * @date 2016年3月16日 下午5:27:09
     */
    public static String set(String key, String value, Integer seconds){
        return getRedisAdminService().set(key, value, seconds);
    }
    
    /**
     * get 操作
     * @param key 键
     * @return value
     * @author 亢燕翔 
     * @date 2016年3月16日 下午5:29:53
     */
    public static String get(String key){
        return getRedisAdminService().get(key);
    } 
    
    /**
     * 删除
     * @param key key
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午2:10:44
     */
    public static Long delete(String key){
        return getRedisAdminService().delete(key);
    }
}