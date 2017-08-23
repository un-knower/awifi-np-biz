package com.awifi.np.biz.common.base.constants;

import java.util.regex.Pattern;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 下午5:18:59
 * 创建作者：亢燕翔
 * 文件名称：regexConstants.java
 * 版本：  v1.0
 * 功能：  正则常量
 * 修改记录：
 */
public class RegexConstants {

    /** 数字 */
    public static final String NUMBER_PATTERN = "^-?\\d+$";
    /**ip地址*/
    public static final String IP_PATTERN = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
    /**ip地址*/
    public static final Pattern IP_PATTERN_COMPILE = Pattern.compile(IP_PATTERN);
    /** MAC地址 */
    public static final String MAC_PATTERN = "^[0-9A-F]{12}$";
    /** MAC地址正确格式中文说明 */
    public static final String MAC_PATTERN_DSP = "12位字符，包含A-F、0-9";
    /**商户名称正则*/
    public static final String MERCHANT_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5_(),.（），。@]{1,50}$";
    /**账号正则*/
    public static final String USER_NAME_PATTERN = "^[0-9a-zA-Z-_]{1,50}$";
    /**热点名称正则(aWiFi- 区分大小写)*/
    public static final String SSID_NAME_PATTERN = "^(aWiFi-).*";
    /**静态用户名正则*/
    public static final String STATIC_USER_NAME = "^[0-9a-zA-Z-_]{1,50}$";
    /**静态用户密码正则*/
    public static final String PASSWORD = "^[0-9a-zA-Z_@$-]{1,50}$";
    /**真实姓名正则 允许为空，正则校验[1-20位字符，包括字母、汉字]*/
    public static final String REAL_NAME = "^[a-zA-Z\u4e00-\u9fa5]{1,20}$";
    /**部门正则 允许为空，正则校验[1-20位汉字、字母、数字]*/
    public static final String DEPT_NAME = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,20}$";
    /** SC|WIC开头的，不区分大小写(特通账号) */
    public static final String SC_PATTERN = "^(sc|wic|brics).*";
    /**1开头的11位符合手机号码规则的数字*/
    public static final String CELLPHONE = "^(1[0-9]{10})?$";
    /**护照正则*/
    public static final String PASSPORT = "^[0-9a-zA-Z]{1,20}$";
    /**身份证号正则*/
    public static final String IDENTITY_CARD= "^[0-9]{17}([0-9]|X){1}$";
    /** 邮箱 */
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"; 
    /**闲时下线时间,单位小时,小数点后保留一位0或5*/
    public static final String HOUR_PATTERN = "^\\d+(\\.[0|5])?$";
    /**项目名称正则*/
    public static final String PROJECT_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5_]{1,50}$";
    /**组件名称正则*/
    public static final String COMPONENT_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**站点名称正则*/
    public static final String SITE_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5_]{1,50}$";
    /**策略名称正则*/
    public static final String STRATEGY_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5_]{1,50}$";
    /**管理员部门正则 允许为空，正则校验[1-50位汉字、字母、数字]*/
    public static final String DEPT_NAME_PATTERN = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**管理员联系人正则*/
    public static final String CONTACT_PERSON_PATTERN = "^[a-zA-Z\u4e00-\u9fa5]{1,50}$";
    /**管理员联系方式正则*/
    public static final String CONTACT_WAY_PATTERN = "^[0-9]{1,50}$";
    /** SSID(前缀)  */
    public static final String SSID_PRIEFIX = "^[0-9a-zA-Z\u4e00-\u9fa5]{0,4}$";
    /** SSID(后缀)  */
    public static final String SSID_SUFFIX = "^[0-9a-zA-Z\u4e00-\u9fa5]{1,5}$";
    /**
     * 定制终端升级包版本
     */
    public static final String UPGRADETPATCH_VERSION = "([a-z]||[A-Z]){1-17}[0-9].[0-9].[0-9]";
    /**
     * 域名校验
     */
    public static final String URL = "([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /** MAC地址 */
    public static final String MAC_NO_CASE_PATTERN = "^[0-9a-fA-F]{12}$";
    /**
     * 定制终端版本号校验
     */
    public static final String FATAP_VERSION = "(V|v)[0-z]{1,9}\\\\.[0-z]{1,9}\\\\.[0-z]{1,9}";
    /**
     * 设备纬度校验
     */
    public static final String DEVICE_LATITUDE = "(-|\\+)?(90\\.0{0,6}|(\\d|[1-8]\\d)\\.\\d{0,6})";
    /**
     * 设备经度校验
     */
    public static final String DEVICE_LONGITUDE =  "(-|\\+)?(180\\.0{0,6}|(\\d{1,2}|1([0-7]\\d))\\.\\d{0,6})";
    /**
     * 正则字符串长度在start-end之间
     * @param start 正则长度开始
     * @param end 正则长度结束
     * 创建日期:2017年7月19日 上午9:51:59
     * 创建作者：许尚敏
     * @return 正则内容
     */
    public static String getStringPattern(int start, int end){
        String pattern = "^.{" + start + "," + end + "}$";
        return pattern;
    }
}