package com.awifi.np.biz.timebuysrv.util.define;

//import com.awifi.np.biz.timebuysrv.util.file.PropertiesOperateUtil;

import java.util.HashMap;
import java.util.Map;


public class MsCommonDefine {
	public static final String SYSTEM_DEFAULT_ENCODING = "UTF-8";
	public static final Integer authPlatform = 10; // 数据中心中的auth类型
	public static final String STYLE_BLUE = "back-blue"; // 风格-蓝色
	public static final String SMS_TYPE_REG = "ms_reg"; /* 注册短信发送 */
	public static final String SMS_TYPE_CHANGEPWD = "ms_changepwd";/* 修改密码短信发送 */
	public static final String SMS_TYPE_WARN = "ms_warn";
	public static final String PARAM_DTO = "PARAM_DTO";
	public static final String SMS_TYPE_LOGIN = "login";
	public static final String SESSION_MS_STYLE = "style"; // 用户选择的页面风格
	public static final String SESSION_SMS_CODE = "SESSION_SMS_CODE_"; // 短信验证码（后面自己加type区分短信类型）
	public static final int SESSION_SMS_CODE_FAIL_TIME = 60*10; // 短信验证码失效时间
	public static final int ORDER_CODE_FAIL_TIME = 60*60; // 订单失效时间
	public static final String SESSION_SMS_CODE_FAIL = "SESSION_SMS_CODE_TIME"; // 短信验证码失效
	public static final String SESSION_MERCHANT = "AWIFI_MERCHANT"; // session中保存商户信息
	public static final String SESSION_USER = "AWIFI_MS_USER"; // 用户信息
	public static final String SESSION_USER_AUTH = "AWIFI_MS_USER_AUTH"; // 用户认证数据
	public static final String SESSION_SHOP_MERCHANT_ID = "SESSION_SHOP_MERCHANT_ID"; /* 用户所在商户信息 */
	public static final Integer PIC_PAGE_NUM = 3; //评论每页显示个数

	public static final String USER_TYPE_NORMAL = "1";// 普通用户
	public static final String USER_TYPE_MERCHANT = "2";// 商家用户

	public static final String PIC_TOP_FOCUS = "1"; // 商户顶部焦点大图
	public static final String PIC_PHOTO_WALL = "2"; // 照片墙图片
	public static final String PIC_MERCHANT_LOGO = "3"; // 照片墙图片
	public static final Integer COMMENT_PAGE_NUM = 3; // 评论每页显示个数
	public static final Integer REPLY_SIZE = 20; //评论回复数


	public static final String FILEPATH_USERICON = "media/weizhan/usericon/";
	public static final String FILEPATH_SHOP = "media/weizhan/shop/";
	public static final String VISTOR_ICON = "/static/weizhan/blue/img/messageboard/avatar.png";

	public static final String COOKIE_USER_LOGIN_DETAIL = "asnrsos";	// cookie中设置用户数据免认证用
	public static final String COOKIE_SECRT = "H12f#sgh$$%sdf12Fsds";
	
//	public static final String imgdomain = PropertiesOperateUtil.GetConfig("config", "imgdomain");
//	public static final String maindomain = PropertiesOperateUtil.GetConfig("config", "maindomain");
//	public static final String staticdomain = PropertiesOperateUtil.GetConfig("config", "staticdomain");
	
	public static final Map<Integer,String> PACKAGEINTRO = new HashMap<Integer, String>();

	
	static{
	    PACKAGEINTRO.put(101, "元/天");
	    PACKAGEINTRO.put(102, "元/周");
	    PACKAGEINTRO.put(103, "元/月");
	    PACKAGEINTRO.put(104, "元/季度");
	    PACKAGEINTRO.put(105, "元/半年");
	    PACKAGEINTRO.put(106, "元/年");
	    PACKAGEINTRO.put(201, "免费体验天数");
	    PACKAGEINTRO.put(202, "VIP体验天数");
	}

}
