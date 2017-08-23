package com.awifi.np.biz.common.ms.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by dozen.zhang on 2016/12/5.
 */
public class ConfigUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigUtil.class);

    public static Properties properties =null;
    /**
     * 根据name获取配置项值
     * @param name
     * @return
     */
    public static String getConfig(String name) {
        if(properties==null){
            //需要重新加载配置文件
            return ResourceBundle.getBundle("config").getString(name);
        }
        if(StringUtils.isBlank(name)){

            logger.error("ConfigUtil.getConfig 参数不能为空");
            return null;
        }
        String value =(String) properties.get(name);
        return value;
    }
}
