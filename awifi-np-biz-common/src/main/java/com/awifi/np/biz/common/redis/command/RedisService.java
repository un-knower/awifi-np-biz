package com.awifi.np.biz.common.redis.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.util.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 6, 2017 3:51:55 PM
 * 创建作者：亢燕翔
 * 文件名称：RedisService.java
 * 版本：  v1.0
 * 功能： redis增删改查业务处理
 * 修改记录：
 */
@Service("redisService")
public class RedisService {

	/** 日志 */
    private static Log logger = LogFactory.getLog(RedisService.class);
	
    /** jedisPool */
    @Resource(name="jedisPool")
    private JedisPool jedisPool;
    
    /** redisDB  */
    @Value("${redis.node.database.index}")
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
                long beginTime = System.currentTimeMillis();
                String result = jedis.set(key, value);
                if(seconds != null){
                    jedis.expire(key, seconds);
                }
                log(index, "set", key, value, seconds, beginTime);//输出日志
                return result;
            }
        });
    }
    
    /**
     * set操作 - 批量
     * @param map key-存储MAP
     * @param seconds 单位（秒）
     * @author kangyanxiang 
     * @date Nov 16, 2016 2:32:31 PM
     */
    public void setBatch(final Map<String, String> map, final Integer seconds) {
        this.execute(new RedisFunction<String,Jedis>(){
            @Override
            public String callBack(Jedis jedis) {
                Pipeline pipeline = jedis.pipelined();
                boolean secondsNotNull = seconds != null;
                for(Map.Entry<String, String> entry : map.entrySet()){
                    long beginTime = System.currentTimeMillis();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    pipeline.set(key, value);
                    if(secondsNotNull){
                        pipeline.expire(key, seconds);
                    }
                    log(index, "set", key, value, seconds, beginTime);//输出日志
                }
                pipeline.sync();
                closePipeline(pipeline);//关闭管道
                return null;
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
                long beginTime = System.currentTimeMillis();
                String value = jedis.get(key);
                log(index, "get", key, value, null, beginTime);//输出日志
                return value;
            }
        });
    }
    
    /**
     * 返回内容包含对应的key
     * @param redisKey redis键值
     * @return map
     * @author kangyanxiang 
     * @date Nov 18, 2016 9:26:12 AM
     */
    public Map<String, String> getBatch(final String redisKey) {
        return this.execute(new RedisFunction<Map<String, String>, Jedis>() {
            @Override
            public Map<String, String> callBack(Jedis jedis) {
                Pipeline pipeline = jedis.pipelined();
                Response<Set<String>> keyResponse = pipeline.keys(redisKey);//获取所有key
                pipeline.sync();
                Set<String> keySet = keyResponse.get();
                //循环所有key，并获取对应的值
                int maxSize = keySet.size();
                Map<String, Response<String>> dataResponseMap = new HashMap<String, Response<String>>(maxSize);
                for(String redisKey : keySet){
                    Response<String> valueResponse = pipeline.get(redisKey);
                    dataResponseMap.put(redisKey, valueResponse);
                }
                pipeline.sync();
                Map<String, String> newMap = new HashMap<String, String>(maxSize);
                for(Map.Entry<String, Response<String>> entry : dataResponseMap.entrySet()){
                    String value = entry.getValue().get();
                    newMap.put(entry.getKey(), value);
                    log(index, "get", redisKey, value, null, null);
                }
                closePipeline(pipeline);//关闭管道
                return newMap;
            }
        });
    }
    
    /**
     * 获取所有keys，仅允许在数据量少时使用
     * @param key key
     * @return 所有keys
     * @author 许小满  
     * @date 2016年11月10日 下午4:02:10
     */
    public Set<String> keys(final String key){
        return this.execute(new RedisFunction<Set<String>, Jedis>() {
            @Override
            public Set<String> callBack(Jedis jedis) {
                //long beginTime = System.currentTimeMillis();
                Set<String> keySet = jedis.keys(key);
                //log(index, "keys", key, keySet.toString(), null, beginTime);//输出日志
                return keySet;
            }
        });
    }
    
    /**
     * 批量Hash键值设置操作
     * @param dataMap map
     * @param seconds 有效时间
     * @return 结果
     * @author 周颖  
     * @date 2017年1月22日 上午10:00:32
     */
    public String hmsetBatch(Map<String, Map<String, String>> dataMap, Integer seconds) {
        return this.execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callBack(Jedis jedis){
                Pipeline pipeline = jedis.pipelined();
                boolean secondsNotNull = seconds != null;
                for(Map.Entry<String, Map<String, String>> entry : dataMap.entrySet()){
                    String key = entry.getKey();
                    Map<String, String> value = entry.getValue();
                    pipeline.hmset(key, value);
                    if(secondsNotNull){
                        pipeline.expire(key, seconds);
                    } 
                    log(index, "hmset", key, JsonUtil.toJson(value), seconds, null);
                }
                pipeline.sync();
                closePipeline(pipeline);
                return null;
            }
        });
    }
    
    /**
     * Hash键值get操作 -- 批量
     * @param key 键
     * @return map
     * @author 许小满  
     * @date 2016年11月11日 上午10:23:13
     */
    public List<Map<String, String>> hgetAllBatch(final String key){
        return this.execute(new RedisFunction<List<Map<String, String>>, Jedis>() {
            @Override
            public List<Map<String, String>> callBack(Jedis jedis) {
                Pipeline pipeline = jedis.pipelined();
                //获取所有key
                Response<Set<String>> keyResponse = pipeline.keys(key);
                pipeline.sync();
                Set<String> keySet = keyResponse.get();
                //循环所有key，并获取对应的值
                int maxSize = keySet.size();
                List<Response<Map<String,String>>> dataResponseList = new ArrayList<Response<Map<String,String>>>(maxSize);
                for(String redisKey : keySet){
                    Response<Map<String,String>> dataResponse = pipeline.hgetAll(redisKey);
                    dataResponseList.add(dataResponse);
                }
                pipeline.sync();
                //循环并封装数据
                List<Map<String,String>> dataList = new ArrayList<Map<String,String>>(maxSize);
                for(Response<Map<String,String>> dataResponse : dataResponseList){
                    dataList.add(dataResponse.get());
                }
                closePipeline(pipeline);//关闭管道
                log(index, "hgetAll", key, JsonUtil.toJson(dataList), null, null);
                return dataList;
            }
        });
    }
    
    /**
     * Hash键值 字段 get操作
     * @param key 键
     * @param fields 字段(可多个)
     * @return 结果
     * @author 许小满  
     * @date 2016年6月16日 下午3:45:28
     */
    public List<String> hmget(final String key,final String... fields){
        return this.execute(new RedisFunction<List<String>, Jedis>() {
            @Override
            public List<String> callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                List<String> filedValueList = jedis.hmget(key, fields);
                log(index, "hmget", key, JsonUtil.toJson(filedValueList), null, beginTime);
                return filedValueList;
            }
        });
    }
    
    /**
     * Hash键值get操作
     * @param key 键
     * @return map
     * @author 亢燕翔
     */
    public Map<String, String> hgetAll(final String key){
        return this.execute(new RedisFunction<Map<String, String>, Jedis>() {
            @Override
            public Map<String, String> callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                Map<String, String> resultMap = jedis.hgetAll(key);
                log(index, "hgetAll", key, JsonUtil.toJson(resultMap), null, beginTime);
                return resultMap;
            }
        });
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
    public String hmset(final String key,final Map<String, String> map, final Integer seconds){
        return this.execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                boolean secondsNotNull = seconds != null;
                String result = jedis.hmset(key, map);
                if(secondsNotNull){
                    jedis.expire(key, seconds);
                }
                log(index, "hmset", key, result, seconds, beginTime);
                return result;
            }
        });
    }
    
    /**
     * Hash键值 字段 get操作 -- 单条数据模糊匹配
     * @param key 键
     * @param fields 字段(可多个)
     * @return 结果
     * @author 许小满  
     * @date 2016年11月11日 上午10:50:53
     */
    public List<String> hmgetLike(final String key,final String... fields){
        return this.execute(new RedisFunction<List<String>, Jedis>() {
            @Override
            public List<String> callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                Pipeline pipeline = jedis.pipelined();
                //获取所有key
                Response<Set<String>> keyResponse = pipeline.keys(key);
                pipeline.sync();
                Set<String> keySet = keyResponse.get();
                //循环所有key，并获取对应的值
                Response<List<String>> dataResponse = null;
                for(String redisKey : keySet){
                    dataResponse = pipeline.hmget(redisKey, fields);
                }
                pipeline.sync();
                List<String> dataList = dataResponse != null ? dataResponse.get() : new ArrayList<String>();
                closePipeline(pipeline);//关闭管道
                log(index, "hmget", key, fields, JsonUtil.toJson(dataList), null, beginTime);
                return dataList;
            }
        });
    }
    
    /**
     * 判断keys是否存在
     * @param key key
     * @return true 存在、false 不存在
     * @author 许小满  
     * @date 2016年11月3日 下午1:54:10
     */
    public Boolean exists(final String key){
        return this.execute(new RedisFunction<Boolean, Jedis>() {
            private long beginTime = System.currentTimeMillis();
            @Override
            public Boolean callBack(Jedis jedis) {
                Boolean isExists = jedis.exists(key);
                log(index, "exists", key, JsonUtil.toJson(isExists), null, beginTime);
                return isExists;
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
    public Long del(final String key){
        return this.execute(new RedisFunction<Long, Jedis>() {
            private long beginTime = System.currentTimeMillis();
            @Override
            public Long callBack(Jedis jedis) {
                Long count = jedis.del(key);
                log(index, "del", key, JsonUtil.toJson(count), null, beginTime);
                return count;
            }
        });
    }
    
    /**
     * 删除 操作 - 批量
     * @param keySet 键集合
     * @return 结果
     * @author 许小满 
     * @date 2016年11月10日 下午5:58:22
     */
    public Long delBatch(final Set<String> keySet){
        return this.execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callBack(Jedis jedis) {
                Pipeline pipeline = jedis.pipelined();
                long count = 0;
                List<Response<Long>> responseList = new ArrayList<Response<Long>>();
                for(String key : keySet){
                    log(index, "del", key, JsonUtil.toJson(count), null, null);
                    Response<Long> response = pipeline.del(key);
                    responseList.add(response);
                }
                pipeline.sync();
                for(Response<Long> response : responseList){
                    count = count + response.get();
                }
                closePipeline(pipeline);//关闭管道
                return count;
            }
        });
    }
    
    /**
     * Hash的键值操作(单个 filed)
     * @param key key
     * @param field field
     * @param value value
     * @return long
     * @author 李程程
     * @date 2017年4月21日 下午3:55:12
     */
    public Long hset(String key,String field,String value){
        return this.execute(new RedisFunction<Long, Jedis>() {
            private long beginTime = System.currentTimeMillis();
            @Override
            public Long callBack(Jedis jedis) {
                Long result=jedis.hset(key, field, value);
                log(index, "hset", key, new String[]{field}, value, null, beginTime);
                return result;
            }
        });
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
    public Long zrem(String key, String members) {
        return this.execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callBack(Jedis jedis) {
                Long result = jedis.zrem(key, members);
                logger.debug("提示：redis-" + index + "-zrem： key=" + key + "&members=" + members);
                return result;
            }
        });
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
    public Long incr(String key, Integer seconds) {
        return this.execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                boolean secondsNotNull = seconds != null;
                Long result = jedis.incr(key);
                if (secondsNotNull) {
                    jedis.expire(key, seconds);
                }
                log(index, "incr", key, JsonUtil.toJson(result), seconds, beginTime);
                return result;
            }
        });
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
    public Long zadd(String key, double score, String member) {
        return this.execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callBack(Jedis jedis) {
                Long result = jedis.zadd(key, score, member);
                logger.debug("提示：redis-" + index + "-zadd： key=" + key + "&score=" + score + "&member=" + member);
                return result;
            }
        });
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
    public Set<String> zrangeByScore(String key, double min, double max) {
        return this.execute(new RedisFunction<Set<String>, Jedis>() {
            @Override
            public Set<String> callBack(Jedis jedis) {
                Set<String> result = jedis.zrangeByScore(key, min, max);
                logger.debug("提示：redis-" + index + "-zrangeByScore： key=" + key + "&min=" + min + "&max=" + max);
                return result;
            }
        });
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
    public String hget(String key, String field) {
        return this.execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                String result = jedis.hget(key, field);
                logger.debug("提示：redis-" + index + "-hget操作： key=" + key + "&filed=" + field);
                log(index, "hget", key, new String[]{field},result, null, beginTime);
                return result;
            }
        });
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
    public Long hdel(String key, String field) {
        return this.execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                Long result = jedis.hdel(key, field);
                log(index, "hdel", key, new String[]{field}, JsonUtil.toJson(result), null, beginTime);
                return result;
            }
        });
    }
    
    /**
     * 获取jedis
     * @return jedis
     * @throws Exception
     * @author 张智威  
     * @date 2017年4月12日 上午9:28:32
     */
    public   Jedis getResource() {
        //logger.info("jedis pool:" + (pool == null));
        Jedis jedis =  null;
        try {
            jedis = jedisPool.getResource();//获取jedis对象
            jedis.select(index);			//设置index
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //检查连接是否有效，有效则放回连接池，无效则关闭。
            if(jedis != null){
                jedis.close();
            }
        }
        return jedis;
    }
    
    /**
     * 对一个列表进行修剪(trim) 保留指定区间内的元素 其余被删除
     * @param key key 
     * @param start 开始
     * @param end 结束
     * @return 结果
     * @author 周颖  
     * @date 2017年7月6日 下午4:02:16
     */
    public String ltrim(final String key,final int start,final int end) {
        return this.execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                String result = jedis.ltrim(key, start, end);
                logger.debug("提示：redis-" + index + "-ltrim： key=" + key + "&scope=[" + start + "," + "" + end + "]"
                        + ",cost=" + (System.currentTimeMillis()-beginTime) + "ms.");
                return result;
            }
        });
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
    public Long lpush(final String key,final Integer seconds,final String... fields) {
        return this.execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                boolean secondsNotNull = seconds != null;
                Long result = jedis.lpush(key, fields);
                if(secondsNotNull){
                    jedis.expire(key, seconds);
                }
                log(index, "lpush", key, fields, JsonUtil.toJson(result), seconds, beginTime);
                return result;
            }
        });
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
    public List<String> lrange(final String key,final int start,final int end){
        return this.execute(new RedisFunction<List<String>,Jedis>() {
            @Override
            public List<String> callBack(Jedis jedis) {
                long beginTime = System.currentTimeMillis();
                List<String> result = jedis.lrange(key, start, end);
                logger.debug("提示：redis-" + index + "-lrange： key=" + key + "&scope=[" + start + "," + "" + end + "]" + "&value=" + JsonUtil.toJson(result)
                        + ",cost=" + (System.currentTimeMillis()-beginTime) + "ms.");
                return result;
            }
        });
    }
    
    /**
     * 关闭 管道
     * @param pipeline 管道
     * @author 许小满  
     * @date 2016年11月11日 上午9:33:06
     */
    private void closePipeline(Pipeline pipeline){
        try{
            if(pipeline == null){
                return;
            }
            pipeline.close();
        }catch(Exception e){
        }
    }
    
    /**
     * 日志输出格式化
     * @param index redis db index
     * @param cmd 命令
     * @param key 键
     * @param value 值
     * @param seconds 有效时间，可以为空
     * @param beginTime 开始时间，用于计算花费时间
     * @author 许小满  
     * @date 2017年7月14日 上午9:00:23
     */
    private void log(int index, String cmd, String key, String value, Integer seconds, Long beginTime){
        log(index, cmd, key, null, value, seconds, beginTime);
    }
    
    /**
     * 日志输出格式化
     * @param index redis db index
     * @param cmd 命令
     * @param key 键
     * @param fields 字段数组，可以为空
     * @param value 值
     * @param seconds 有效时间，可以为空
     * @param beginTime 开始时间，用于计算花费时间
     * @author 许小满  
     * @date 2017年7月14日 上午9:00:23
     */
    private void log(int index, String cmd, String key, String[] fields, String value, Integer seconds, Long beginTime){
        StringBuilder log = new StringBuilder();
        log.append("提示：redis-").append(index).append("-").append(cmd).append("：key=").append(key);
        if(fields != null){
            log.append("&fields=").append(StringUtils.join(fields, ","));
        }
        if(value != null){
            int maxLength = value.length();
            if(maxLength > 4000){
                value = value.substring(0, 4000) + "...";
            }
            log.append("&value=").append(value);
        }
        if(seconds != null){
            log.append("&expire=").append(seconds).append("s");
        }
        if(beginTime != null){
            log.append("&cost=").append(System.currentTimeMillis() - beginTime).append("ms.");
        }
        logger.debug(log.toString());//输出日志
    }
}
