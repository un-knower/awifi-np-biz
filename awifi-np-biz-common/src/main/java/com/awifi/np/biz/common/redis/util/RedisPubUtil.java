package com.awifi.np.biz.common.redis.util;

import com.awifi.np.biz.common.redis.command.RedisPubService;
import com.awifi.np.biz.common.util.BeanUtil;

/**   
 * @Description:  省分平台--专用redis，后期将废弃
 * @Title: RedisUtil.java 
 * @Package com.awifi.toe.system.util 
 * @author 亢燕翔 
 * @date 2016年3月21日 下午2:47:15
 * @version V1.0   
 */
public class RedisPubUtil {

    /** redis Bean  */
    private static RedisPubService redisService;
    
    /**
     * @return bean
     * @author 亢燕翔 
     * @date 2016年3月23日 上午11:28:10
     */
    public static RedisPubService getRedisService() {
        if (redisService == null) {
            redisService = (RedisPubService) BeanUtil.getBean("redisPubService");
        }
        return redisService;
    }
    
    /**
     * get 操作
     * @param key 键
     * @return value
     * @author 亢燕翔 
     * @date 2016年3月16日 下午5:29:53
     */
    public static String get(String key){
        return getRedisService().get(key);
    }
    
}