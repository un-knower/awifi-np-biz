package com.awifi.np.biz.common.system.sysconfig.util;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.system.sysconfig.service.SysConfigService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 10, 2017 8:41:23 AM
 * 创建作者：亢燕翔
 * 文件名称：SysConfigUtil.java
 * 版本：  v1.0
 * 功能：  获取系统参数工具类
 * 修改记录：
 */
public class SysConfigUtil {

	/** 系统参数业务层 */
    private static SysConfigService sysConfigService;
	
    /**
     * 通过key获取value
     * @param paramKey 参数key
     * @return value
     * @author 亢燕翔  
     * @date Jan 10, 2017 8:43:46 AM
     */
    public static String getParamValue(String paramKey){
    	if(StringUtils.isBlank(paramKey)){	//如果参数key为空，则返回参数空(key)。
            return paramKey;
    	}
        return getSysConfigService().getParamValue(paramKey);
    }

    /**
	 * 获取sysConfigService
	 * @return sysConfigService
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 8:43:13 AM
	 */
    private static SysConfigService getSysConfigService() {
    	if (sysConfigService == null) {
            sysConfigService = (SysConfigService) BeanUtil.getBean("sysConfigService");
    	}
    	return sysConfigService;
    }
    
}
