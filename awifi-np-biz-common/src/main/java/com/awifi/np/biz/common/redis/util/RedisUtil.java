package com.awifi.np.biz.common.redis.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.awifi.np.biz.common.redis.command.RedisService;
import com.awifi.np.biz.common.util.BeanUtil;

import redis.clients.jedis.Jedis;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 9, 2017 2:10:20 PM
 * 创建作者：亢燕翔
 * 文件名称：RedisUtil.java
 * 版本：  v1.0
 * 功能： redis工具类
 * 修改记录：
 */
public class RedisUtil {

    public RedisUtil() {}
    
    /** redis Bean  */
    private static volatile RedisService redisService;
    
    /**
     * 获取Redis实例
     * @return bean
     * @author 亢燕翔 
     * @date 2016年3月23日 上午11:28:10
     */
    public static RedisService getRedisService() {
        if (null == redisService) {
            synchronized (RedisUtil.class) {
                if(null == redisService){
                    redisService = (RedisService) BeanUtil.getBean("redisService");
                }
            }
        }
        return redisService;
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
        return getRedisService().set(key, value, seconds);
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
    
    /**
     * 批量获取value
     * @param key key
     * @return map
     * @author 周颖  
     * @date 2017年1月20日 上午10:14:55
     */
    public static Map<String,String> getBatch(String key){
        return getRedisService().getBatch(key);
    }
    
    /**
     * 获取所有keys
     * @param key key
     * @return 所有keys
     * @author 许小满  
     * @date 2016年11月10日 下午4:02:40
     */
    public static Set<String> keys(String key){
        return getRedisService().keys(key);
    }
    
    /**
     * 批量添加key
     * @param map <key,value>
     * @param seconds 有效时间
     * @author 周颖  
     * @date 2017年1月20日 上午9:16:48
     */
    public static void setBatch(Map<String, String> map,Integer seconds){
        getRedisService().setBatch(map, seconds);
    }
    
    /**
     * 批量Hash键值设置操作
     * @param redisMap map
     * @param seconds 有效时间
     * @return 结果
     * @author 周颖  
     * @date 2017年1月22日 上午10:00:32
     */
    public static String hmsetBatch(Map<String,Map<String,String>> redisMap,Integer seconds){
        return getRedisService().hmsetBatch(redisMap,seconds);
    }
    
    /**
     * Hash键值 字段 get操作
     * @param key 键
     * @param fields 字段(可多个)
     * @return 结果
     * @author 许小满  
     * @date 2016年6月16日 下午3:45:28
     */
    public static List<String> hmget(String key,String... fields){
        return getRedisService().hmget(key, fields);
    }
    
    /**
     * Hash键值get操作
     * @param key 
     * @return map
     * @author kangyanxiang 
     * @date Oct 18, 2016 10:24:00 AM
     */
    public static Map<String, String> hgetAll(String key) {
        return getRedisService().hgetAll(key);
    }
    
    /**
     * 批量Hash键值get操作
     * @param key rediskey
     * @return 结果
     * @author 周颖  
     * @date 2017年1月22日 下午1:57:13
     */
    public static List<Map<String, String>> hgetAllBatch(String key){
        return getRedisService().hgetAllBatch(key);
    }
    
    /**
     * Hash键值设置操作
     * @param key 键
     * @param map 存储MAP
     * @param seconds 单位（秒）
     * @return 结果
     * @author 许小满  
     * @date 2016年6月16日 下午3:45:28
     */
    public static String hmset(String key,Map<String, String> map, Integer seconds){
        return getRedisService().hmset(key, map, seconds);
    }
    
    /**
     * Hash键值 字段 get操作 -- 单条数据模糊匹配
     * @param key 键
     * @param fields 字段(可多个)
     * @return 结果
     * @author 许小满  
     * @date 2016年11月11日 上午10:50:53
     */
    public static List<String> hmgetLike(final String key,final String... fields){
        return getRedisService().hmgetLike(key, fields);
    }

    /**
     * key是否存在
     * @param key key
     * @return 结果
     * @author 周颖  
     * @date 2017年2月24日 上午11:20:29
     */
    public static boolean exist(String key) {
        return getRedisService().exists(key);
    }
    
    /**
     * 删除
     * @param key key
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午2:10:44
     */
    public static Long del(String key){
        return getRedisService().del(key);
    }
    
    /**
     * 批量删除key
     * @param keySet keys
     * @return 执行总条数
     * @author 周颖  
     * @date 2017年1月19日 下午4:13:02
     */
    public static Long delBatch(Set<String> keySet){
        return getRedisService().delBatch(keySet);
    }
    
    /**
     *hash的field操作 
     * @param key key
     * @param field field
     * @param value value
     * @return Long(结果)
     */
    public static Long hset(String key,String field,String value){
        return getRedisService().hset(key, field, value);
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略.
     * 
     * @param key 键
     * @param members 成员
     * @return Long
     * @author 尤小平
     * @date 2017年6月6日 下午5:29:14
     */
    public static Long zrem(String key,String members){
        return getRedisService().zrem(key, members);
    }

    /**
     * 将key的整数值递增1.
     * 
     * @param key 键
     * @param seconds 单位（秒）
     * @return Long
     * @author 尤小平
     * @date 2017年6月6日 下午5:30:09
     */
    public static Long incr(String key, Integer seconds){
        return getRedisService().incr(key, seconds);
    }

    /**
     * 将一个或多个成员元素及其分数值加入到有序集当中.
     * 
     * @param key key
     * @param score score
     * @param member member
     * @return Long
     * @author 尤小平
     * @date 2017年6月6日 下午5:30:32
     */
    public static Long zadd(String key, double score, String member) {
        return getRedisService().zadd(key, score, member);
    }

    /**
     * 返回有序集合在最小值和最大值(包括得分等于最小或最大元素)之间的分数键中的所有元素.
     * 
     * @param key key
     * @param min min
     * @param max max
     * @return Set<String>
     * @author 尤小平
     * @date 2017年6月6日 下午5:30:47
     */
    public static Set<String> zrangeByScore(String key, double min, double max) {
        return getRedisService().zrangeByScore(key, min, max);
    }

    /**
     * 获取与字段中存储的键哈希相关联的值.
     * 
     * @param key key
     * @param field field
     * @return String
     * @author 尤小平
     * @date 2017年6月6日 下午5:31:01
     */
    public static String hget(String key,String field){
        return getRedisService().hget(key, field);
    }

    /**
     * 从存储在键散列删除指定的字段.
     * 
     * @param key key
     * @param field field
     * @return Long
     * @author 尤小平
     * @date 2017年6月6日 下午5:31:16
     */
    public static Long hdel(String key,String field){
        return getRedisService().hdel(key, field);
    }

    /**
     * 获取jedis资源
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午2:10:44
     */
    public static Jedis getResource(){
        return getRedisService().getResource();
    }
    
    /**
     * 将值插入到列表头部
     * @param key key
     * @param seconds 生存时间 
     * @param fields value
     * @return 列表长度
     * @author 周颖  
     * @date 2017年7月6日 下午4:14:53
     */
    public static Long lpush(String key, Integer seconds, String... fields){
        return getRedisService().lpush(key, seconds, fields);
    }
    
    /**
     * 对一个列表进行修剪(trim) 保留指定区间内的元素 其余被删除
     * @param key key 
     * @param start 开始
     * @param end 结束
     * @return 结果 ok
     * @author 周颖  
     * @date 2017年7月6日 下午4:02:16
     */
    public static String ltrim(String key, int start, int end) {
        return getRedisService().ltrim(key, start, end);
    }
    
    /**
     * 获取list
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     * @return list
     * @author 周颖  
     * @date 2017年7月10日 下午3:38:43
     */
    public static List<String> lrange(String key, int start, int end){
        return getRedisService().lrange(key, start, end);
    }
}