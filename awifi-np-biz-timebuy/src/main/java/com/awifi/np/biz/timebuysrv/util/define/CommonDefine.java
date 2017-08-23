package com.awifi.np.biz.timebuysrv.util.define;

import com.awifi.np.biz.timebuysrv.util.file.PropertiesOperateUtil;
import com.awifi.np.biz.timebuysrv.web.util.ConfigUtil;

public class CommonDefine {
	
	public static final String SESSION_USER = "AWIFI_USER";
	public static final String SESSION_MERCHANT = "AWIFI_MERCHANT";
	public static final String WEIXIN_OPEN_ID = "WEIXIN_OPEN_ID";
	
	public static final String DOMAIN =  ConfigUtil.getConfig("maindomain");

	public static final String MENU_MINE = "MENU_MINE";				// 我的
	public static final String MENU_PWD_MODIFY = "MENU_PWD_MODIFY";  // 修改密码
	public static final String MENU_ACCOUNT_BIND = "MENU_ACCOUNT_BIND"; // 微信帐号绑定
	public static final String MENU_BLACKLIST = "MENU_BLACKLIST"; //黑名单
	public static final String MENU_WHITELIST = "MENU_WHITELIST"; // 白名单
	public static final String MENU_LOGOUT = "MENU_LOGOUT";
	public static final String MENU_STATISTIC = "MENU_STATISTIC";
	public static final String MENU_MYINFO = "MENU_MYINFO";
	
	public static final String MENU_URL_MINE = DOMAIN + "member/user/init.shtml";
	public static final String MENU_URL_PWD_MODIFY = DOMAIN + "member/pwd/modify.shtml";
	public static final String MENU_URL_ACCOUNT_BIND = DOMAIN + "login/init.shtml";
	public static final String MENU_URL_BLACKLIST = DOMAIN + "member/blacklist/list.shtml";
	public static final String MENU_URL_WHITELIST = DOMAIN + "member/whiteList/list.shtml";
	public static final String MENU_URL_LOGOUT = DOMAIN + "member/user/logOut.shtml";
	public static final String MENU_URL_STATISTIC = DOMAIN + "member/statistic/pv.shtml";
	public static final String MENU_URL_MYINFO = DOMAIN + "member/my/info.shtml";
	
	public static final String FILEPATHIMG =   "media/img/";
	public static final String FILEPATHFILE =   "media/attach/";



	
	
	public static final Integer WHITE_LIST = 1;
	public static final Integer BLACK_LIST = 2;
	
//	// 白名单发至接入
//	public static final String WHITELIST_SENDTO_JOIN =  PropertiesOperateUtil.GetConfig("config", "whitelist.sendto.join");
//	// 删除白名单发至接入
//	public static final String WHITELIST_SENDTO_DELETE =  PropertiesOperateUtil.GetConfig("config", "whitelist.sendto.delete");
	
	public static short STATUS_SUCCESS = 0;
	public static short STATUS_FAIL = 1;
	
}
