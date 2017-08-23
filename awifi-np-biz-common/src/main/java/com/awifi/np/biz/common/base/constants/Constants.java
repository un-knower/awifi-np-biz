package com.awifi.np.biz.common.base.constants;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午3:52:33
 * 创建作者：周颖
 * 文件名称：Constants.java
 * 版本：  v1.0
 * 功能：常量
 * 修改记录：
 */
public class Constants {  
    
    /** 新运管简称 */
    public static final String NP = "np";
    
    /** 黑名单 */
    public static final String BLACK = "black";
    /** 刚入库和解绑  */
    public static final String PUB = "PUB";
    /** 微站  */
    public static final String MWS = "MWS";
    /** 园区  */
    public static final String MSP = "MSP";
    /** 酒店  */
    public static final String MWH = "MWH";
    /** 项目型  */
    public static final String TOE = "ToE";
    /**认证上网记录缓存总数*/
    public static final int AUTHTOTAL = 10;
    /**认证方式 手机号验证码认证*/
    public static final String SMS = "sms";
    /**认证方式 免认证*/
    public static final String AUTHED = "authed";
    /**认证方式 用户名+密码认证*/
    public static final String ACCOUNT = "account";
    /**认证方式 微信认证*/
    public static final String WECHAT = "wechat";
    /**认证方式 二维码*/
    public static final String QRCODE = "qrcode";
    /**认证方式 IVR语音认证*/
    public static final String IVR = "ivr";
    /**第三方*/
    public static final String APP = "APP";
    /**胖ap*/
    public static final String FAT = "fat";
    /**光猫*/
    public static final String GOPN = "gopn";
    /**一周天数*/
    public static final int WEEKDAYMILL = 7 ;//7*24*60*60*1000
    /**统计跨度  P表示按省份*/
    public static final String STATTYPEP = "P";
    /**统计跨度  T表示按城市*/
    public static final String STATTYPET = "T";
    /**统计跨度  C表示城镇*/
    public static final String STATTYPEC = "C";
}
