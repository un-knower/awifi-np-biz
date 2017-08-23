package com.awifi.np.biz.timebuysrv.web.util;


import org.slf4j.Logger;

import com.awifi.np.biz.timebuysrv.util.StringUtil;

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
        if(StringUtil.isBlank(name)){

            logger.error("ConfigUtil.getConfig 参数不能为空");
            return null;
           // throw new Exception("config param is null");
        }
        String value =(String) properties.get(name);
        return value;
     /*   String value =(String) CacheUtil.getInstance().readCache(name,String.class);
        if(StringUtil.isBlank(value)){
          //  Object object = BeanUtil.getBean("sysUserRoleService");
            SysConfigService sysConfigService = (SysConfigService)BeanUtil.getBean("sysConfigService");
            SysConfig config = sysConfigService.selectByName(name);
            if(config!=null) {
                 value = config.getValue();
                CacheUtil.getInstance().writeCache(name,value);
                return value;
            }else {
                return null;
            }
        }else{

            return value;
        }*/

    }
    /**
     * 设置配置信息
     * @param name
     * @param value
     * @return
     * @author 张智威  
     * @date 2017年6月10日 上午9:40:52
     */
    public static String setConfig(String name,String value) {
        properties.setProperty(name, value);
        return value;
    }

}
