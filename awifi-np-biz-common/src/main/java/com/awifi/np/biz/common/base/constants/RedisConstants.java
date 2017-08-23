package com.awifi.np.biz.common.base.constants;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 上午9:51:29
 * 创建作者：亢燕翔
 * 文件名称：RedisConstants.java
 * 版本：  v1.0
 * 功能：  redis常量
 * 修改记录：
 */
public class RedisConstants {

    /** 平台_系统_模块_ */
    public static final String SYSTEM_CONFIG = "np_biz_sysconfig_";
    /** 参数配置有效时间 */
    public static final int SYSTEM_CONFIG_TIME = 2678400;   //31*24*60*60(31天)
    
    /**access_token有效时间*/
    //public static final int ACCESS_TOKEN_TIME = ;      //24*60*60(30分钟)
    /**数据中心access_token key*/
    public static final String DBC_ACCESS_TOKEN = "np_biz_dbc_access_token";
    
    /**行业key前缀*/
    public static final String INDUSTRY = "np_biz_industry_";
    /**行业key有效时间*/
    public static final int INDUSTRY_TIME = 2678400;//31*24*60*60(31天)
    
    /**地区key前缀*/
    public static final String LOCATION = "np_biz_location_";
    /**地区key有效时间*/
    public static final int LOCATION_TIME = 2678400;//31*24*60*60(31天)
    
    /**商户key前缀*/
    public static final String MERCHANT = "np_biz_merchant_";
    /**商户key有效时间*/
    public static final int MERCHANT_TIME = 300;//5*60(5分钟)
    
    /**设备key前缀*/
    public static final String DEVICE = "np_biz_device_";
    /**设备key有效时间*/
    public static final int DEVICE_TIME = 90;//90秒
    
    /** 验证码 */
    public static final String AUTH_CODE = "np_biz_authcode_";
    /** 验证码有效时间 */
    public static final int AUTH_CODE_TIME = 300;//5*60(5分钟)
    
    /** 通过策略获取站点id Strategy */
    public static final String SITE_STRATEGY_ID = "np_biz_site_strategy_id_";
    /** 通过策略获取站点id  有效时间 */
    public static final int SITE_STRATEGY_ID_TIME = 90;//90秒
    
    /** 区域站点id  */
    public static final String SITE_LOCATION_ID = "np_biz_site_location_id_";
    /** 区域站点id  有效时间 */
    public static final int SITE_LOCATION_ID_TIME = 2678400;//31*24*60*60(31天)
    
    /** 行业站点id  */
    public static final String SITE_INDUSTRY_ID = "np_biz_site_industry_id_";
    /** 行业站点id  有效时间 */
    public static final int SITE_INDUSTRY_ID_TIME = 2678400;//31*24*60*60(31天)
    
    /** 全国默认站点id  */
    public static final String SITE_DEFAULT_ID = "np_biz_site_default_id";
    /** 全国默认站点id  有效时间 */
    public static final int SITE_DEFAULT_ID_TIME = 2678400;//31*24*60*60(31天)
    
    /** 站点名称  */
    public static final String SITE_NAME = "np_biz_site_name_";
    /** 站点名称  有效时间 */
    public static final int SITE_NAME_TIME = 90;//90秒
    
    /** 站点第一页  */
    public static final String SITE_FIRST_PAGE = "np_biz_site_first_page_";
    /** 站点第一页  有效时间 */
    public static final int SITE_FIRST_PAGE_TIME = 90;//90秒
    
    /** 站点下一页  */
    public static final String SITE_NEXT_PAGE = "np_biz_site_page_";
    /** 站点下一页  有效时间 */
    public static final int SITE_NEXT_PAGE_TIME = 90;//90秒
    
    /** Portal页面跟第三方参数对接  */
    public static final String PORTAL_PARAM = "np_biz_param";
    /** Portal页面跟第三方参数对接  有效时间 */
    public static final int PORTAL_PARAM_TIME = 600;//10分钟有效
    
    /**短信*/
    public static final String SMS = "np_biz_sms_";
    
    /** IVR语音认证存储页面参数 */
    public static final String IVR = "np_biz_ivr_";//暂时使用toe旧key，目的兼容4.x  -- TOE_IVR_
    /** IVR语音认证存储页面参数 有效时间  */
    public static final int IVR_TIME = 600;//10分钟有效
    
    /** 客户配置  */
    public static final String CUSTOMER_CONFIG = "TOE_CUSTOMER_CONFIG_";
    /** 客户配置 有效时间 */
    public static final int CUSTOMER_CONFIG_TIME = 2678400;//31*24*60*60(31天)
    
    /** 防蹭网码  */
    public static final String NET_DEF = "np_biz_netdef_";
    
    /**权限*/
    public static final String PERMISSION = "np_biz_permission_";
    /**权限缓存时间*/
    public static final int PERMISSION_TIME = 86400;//24*60*60 一天
    
    /**用户认证*/
    public static final String USER_AUTH = "np_biz_userauth_";
    /**用户认证有效时间*/
    public static final int USER_AUTH_TIME = 604800;//7*24*60*60 604800 七天
    
    /**二维码key*/
    public static final String QRCODE = "np_biz_qrcode_";
    /**二维码key有效时间*/
    public static final int QRCODE_TIME = 300;//5*60
}
