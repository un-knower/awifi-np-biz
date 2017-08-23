package com.awifi.np.biz.common.redis.command;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午2:50:04
 * 创建作者：周颖
 * 文件名称：RedisAdminService.java
 * 版本：  v1.0
 * 功能： redis增删改查业务处理
 * 修改记录：
 */
@Lazy(true)
@Service("redisAdminService")
public class RedisAdminService {

	/** 日志 */
    private static Log logger = LogFactory.getLog(RedisAdminService.class);
	
    /** jedisPool */
    @Resource(name = "jedisAdminPool")
    private JedisPool jedisPool;
    
    /** redisDB  */
    @Value("${redis.admin.node.database.index}")
    private int index;
	
    /**
     * 公共方法
     * @param function 方法
     * @param <T> 泛型
     * @return jedis对象
     * @author 亢燕翔  
     * @date Jan 6, 2017 4:01:14 PM
     */
    private <T> T execute(RedisFunction<T, Jedis> function){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();//获取jedis对象
            jedis.select(index);			//设置index
            return function.callBack(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //检查连接是否有效，有效则放回连接池，无效则关闭。
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }
	
    /**
     * 设置值以及生存时间
     * @param key 键
     * @param value 值
     * @param seconds 单位（秒）
     * @return 是否成功结果
     * @author 亢燕翔  
     * @date Jan 6, 2017 4:03:51 PM
     */
    public String set(final String key, final String value, final Integer seconds){
        return this.execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callBack(Jedis jedis) {
                boolean secondsNotNull = seconds != null;
                String result = jedis.set(key, value);
                if(secondsNotNull){
                    jedis.expire(key, seconds);
                }
                logger.debug("提示：redis-"+ index +"-set操作： key="+key+"&value=" + value + (secondsNotNull ? ("&expire="+seconds + " s.") : StringUtils.EMPTY));
                return result;
            }
        });
    }
    
    /**
     * get操作
     * @param key 键
     * @return 值
     * @author 亢燕翔  
     * @date Jan 6, 2017 4:09:08 PM
     */
    public String get(final String key){
    	return this.execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callBack(Jedis jedis) {
                String value = jedis.get(key);
                logger.debug("提示：redis-"+ index +"-get操作： " + key + "=" + value);
                return value;
            }
        });
    } 
    
    /**
     * 删除key
     * @param key key
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午2:09:52
     */
    public Long delete(final String key){
        return this.execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callBack(Jedis jedis) {
                logger.debug("提示：redis-"+ index +"-del操作：key="+key);
                return jedis.del(key);
            }
        });
    }
}