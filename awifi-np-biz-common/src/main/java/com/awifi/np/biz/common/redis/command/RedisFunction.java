package com.awifi.np.biz.common.redis.command;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 6, 2017 3:56:12 PM
 * 创建作者：亢燕翔
 * 文件名称：RedisFunction.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface RedisFunction<T, E> {
	
	/**
	 * @param e 泛型
	 * @return T
	 * @author 亢燕翔  
	 * @date Jan 6, 2017 3:56:47 PM
	 */
    T callBack(E e);
	
}
