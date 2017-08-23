package com.awifi.np.biz.api.client.dbcenter.device.util;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月15日 下午5:12:55
 * 创建作者：亢燕翔
 * 文件名称：DevUtil.java
 * 版本：  v1.0
 * 功能：  设备工具类
 * 修改记录：
 */
public class DevUtil {
    
    /**
     * 校验是否是胖ap
     * @param entityType 设备类型
     * @author 亢燕翔  
     * @date 2017年2月15日 下午5:18:09
     */
    public static void isFatAP(String entityType){
        if(StringUtils.isBlank(entityType)){
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", "设备类型"));//{0}不允许为空!
        }
        String entityTypes = "31,32,33,34,35,36,37";//属于胖AP、光猫
        if(!entityTypes.contains(entityType.toString())){//是否包含该设备
            throw new ValidException("E2300002",MessageUtil.getMessage("E2300002"));//设备SSID修改目前仅支持胖AP、光猫设备!
        }
    }
    
}
