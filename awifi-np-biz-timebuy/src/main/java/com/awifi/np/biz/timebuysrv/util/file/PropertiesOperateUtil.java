/*
 * @(#)PropertiesOperateUtil.java 2010-11-24下午05:41:30
 * Copyright 2010 Palm Control, Inc. All rights reserved.
 */
package com.awifi.np.biz.timebuysrv.util.file;

import java.util.ResourceBundle;

/**
 * 资源文件操作工具类
 * @modificationHistory.  
 * <ul>
 * <li>kjl 2010-11-24下午05:41:30 TODO</li>
 * </ul> 
 */

public class PropertiesOperateUtil {

	private static final String configPath =  ResourceBundle.getBundle("main").getString("basePropertiesPath");

	/**
	 * 取资源文件配置项
	 * @author kjl
	 * @creationDate. 2010-11-24 下午05:43:18 
	 *  @param fileName 资源文件名称
	 *  @param key 键
	 *  @return 配置项的值
	 */
	public static String GetConfig(String fileName,String key) {
		if("config".equals(fileName)){
			fileName = configPath + "/" + fileName;
		}
		return ResourceBundle.getBundle(fileName).getString(key);
	}
	/**
	 * 测试
	 * @author kjl
	 * @creationDate. 2010-11-24 下午05:47:32 
	 *  @param args
	 */
	public static void main(String[] args) {
		System.out.println(GetConfig("config", "access.account.auth.url"));
	}
}
