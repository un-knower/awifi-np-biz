package com.awifi.np.biz.timebuysrv.web.core;

/**
 * Created by dozen.zhang on 2016/9/30.
 */
public class Constants {
    public static final String tempRootpath = System.getProperty("user.dir") + "/temp/";

    public static final int excelPageSize = 1000;

    public static final String suffix = ".html";

    public static final String excelext = ".xls";

    public static final String exportexcel = "exportexcel";// 是否是导出操作的key

    public static final String dataUpdate = "更新";

    public static final String dataSave = "保存";

    public static final String dataDelete = "删除";

    public static final String cacheKey = "springraincache";

    public static final String qxCacheKey = "springrainqxcache";

    public static final String tableExt = "_history_";

    public static final String frameTableAlias = "frameTableAlias";

    public static final String pageurlName = "pageurlName";

    public static final String returnDatas = "returnDatas";

    public static final String SESSION_DTO = "SESSION_DTO";
    public static final String SESSION_MERCHANT = "AWIFI_MERCHANT";
    public static final String ROLE_MERCHANT_MANAGER = "1";
    public static final String ROLE_MERCHANT_AGENT_MANAGER = "2";
    // 用户对应的手机号对应的设备mac 设备deviceId 用户mac
    // 因为踢下线逻辑有修改 所以后期变成手机号码 对应设备mac+手机mac+设备id key是 msp_8+手机号码 value 是DevParms 的json格式
    public static final String REDIS_TEL_TO_APMACTERMACDEVID = "msp_8";
    // 临时放行次数 手机号-放通次数 Constants.REDIS_TEMP_PASS_TIMES + username value是times
    // 放通次数
    public static final String REDIS_TEMP_PASS_TIMES = "msp_9";
    // public static String REDIS_KEY_TEMPORARY_PASS="msp_9";
    // 过期时间 用于定时踢下线
    // 需要踢下线的
    public static final String REDIS_TIME_TO_DEVPARAM = "msp_zdd_8";
    // 用于挤下线
    //public static final String REDIS_TEL_TO_TERMAC_MER = "user_device";
    // 踢下线失败队列
    public static final String REDIS_KICK_FAIL = "msp_10";
    // 免费包领取
    public static final String REDIS_PKG_GET = "MSP-free_pkg_get_auth";
    // 24小时内发送短信key值
    public static String REDIS_MSP_SMS_24 = "MSP_SMS_24_";
    // 5分钟内发送短信key值
    public static String REDIS_MSP_SMS_5 = "MSP_SMS_5_";
    // 宽带账号
    public static String REDIS_MSP_BROAD_ACCOUNT = "msp_mer_broadbandAccount";

    // 认证
    // public static final String reloginsession="shiro-reloginsession";
    // 认证
    public static final String authenticationCacheName = "shiro-authenticationCacheName";
    // 授权
    public static final String authorizationCacheName = "shiro-authorizationCacheName";
    // realm名称
    public static final String authorizingRealmName = "shiroDbAuthorizingRealmName";

    // 缓存用户最后有效的登陆sessionId
    public static final String keeponeCacheName = "shiro-keepone-session";

    /**
     * 默认验证码参数名称
     */
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    /**
     * 登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key默认名称
     */
    public static final String DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE = "showCaptcha";

    /**
     * 默认在session中存储的登录次数名称
     */
    public static final String DEFAULT_LOGIN_NUM_KEY_ATTRIBUTE = "loginNum";
    // 允许登录次数，当登录次数大于该数值时，会在页面中显示验证码
    public static final Integer allowLoginNum = 1;

    public static int ZK_SESSION_TIMEOUT = 55000;

    public static String ZK_REGISTRY_PATH = "/registry";
    public static String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";

    /** 问卷调查中消费价格 大于0小于0.4 计数 */
    public static final String REDIS_QUESTIONNAIRE_LOW = "REDIS_QUESTIONNAIRE_LOW";
    /** 问卷调查中消费价格 大于等于0.4小于等于0.8 计数 */
    public static final String REDIS_QUESTIONNAIRE_MIDDEL = "REDIS_QUESTIONNAIRE_MIDDEL";
    public static Long QUESTIONNAIRE_RED_PACKAGE = 10086L;
    public static String CHARSET = "utf-8";
    /**
     * 默认套餐商户id
     */
    public static int DEFAULT_FREE_PKG_KEY = -31;

    // public static int REDIS_SESCONDS=1800;
    /**半小时秒数**/
    public static int HALF_HOUR_SESCONDS = 30 * 60;
    /**1小时秒数**/
    public static int HOUR_SESCONDS = 60 * 60;
}
