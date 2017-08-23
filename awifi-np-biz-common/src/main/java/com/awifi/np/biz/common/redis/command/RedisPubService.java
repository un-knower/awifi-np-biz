package com.awifi.np.biz.common.redis.command;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**   
 * @Description:  Redis--增删改查  --专用redis，后期将废弃
 * @Title: RedisService.java 
 * @Package com.awifi.toe.redis.service 
 * @author 亢燕翔 
 * @date 2016年3月16日 下午5:12:02
 * @version V1.0   
 */
@Lazy(true)
@Service("redisPubService")
public class RedisPubService {
    
    /** 日志  */
    private static final Log logger = LogFactory.getLog(RedisPubService.class);
    
    /**
     * redis连接DB
     */
    @Value("${redis.pub.node.database.index}")
    private int index;
    
    /**
     * jedisPool
     * required = false  非强制注入
     */
    @Resource(name = "shardedJedisPubPool")
    private JedisPool jedisPool;
    
    
    /**
     * 公共方法
     * @param function 方法
     * @param <T> 泛型
     * @return jedis对象
     * @author 亢燕翔 
     * @date 2016年3月29日 下午12:14:31
     */
    private <T> T execute(RedisFunction<T, Jedis> function){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();//获取jedis对象
            jedis.select(index);//设置index
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
     * get 操作
     * @param key 键
     * @return 结果
     * @author 亢燕翔 
     * @date 2016年3月16日 下午5:29:53
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
}