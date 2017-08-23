package com.awifi.np.biz.common.util;

import java.util.UUID;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 上午10:01:43
 * 创建作者：周颖
 * 文件名称：KeyUtil.java
 * 版本：  v1.0
 * 功能：生成access_token
 * 修改记录：
 */
public class KeyUtil {

    /**accesstoken固定前缀*/
    private static final String PREFIX = "AT_";
    
    /**组件code固定前缀*/
    private static final String COMPONENTPREFIX = "_";
    
    /**
     * 生成access_token
     * @param appId 平台id
     * @return access_token
     * @author 周颖  
     * @date 2017年1月11日 下午6:44:11
     */
    public static String generateAccessToken(String appId){
        String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");//uuid 是去掉中划线然后大写的32位
        String accessToken = PREFIX + appId + "_" + uuid;//生成access_token
        return accessToken;
    }
    
    /**
     * 生成小写的验证码
     * @return uid
     * @author 许小满  
     * @date 2017年3月15日 下午4:45:44
     */
    public static String generateLowerUUID(){
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }
    
    /**
     * 生成大写的验证码
     * @return uid
     * @author 许小满  
     * @date 2017年3月15日 下午4:45:44
     */
    public static String generateUpperUUID(){
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    /**
     * 生成组件code
     * @return 组件code
     * @author 周颖  
     * @date 2017年4月12日 下午4:53:26
     */
    public static String generateKey() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return COMPONENTPREFIX + uuid;
    }
    
    /**
     * 生成第三方应用对应的appId
     * @return 应用id
     * @author 许小满  
     * @date 2017年7月10日 下午7:19:26
     */
    public static String generateAppId(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    }
    
    /**
     * 生成第三方应用对应的appKey
     * @return 应用key
     * @author 许小满  
     * @date 2017年7月10日 下午7:19:26
     */
    public static String generateAppKey(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
    }
    
    /**
     * 测试方法
     * @param args 参数
     * @author 许小满  
     * @date 2017年7月11日 下午8:57:51
     */
    public static void main(String[] args) {
        System.out.println("appId= " + generateAppId());
        System.out.println("appKey= " + generateAppKey());
    }
}
