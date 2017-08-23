package com.awifi.np.biz.timebuysrv.util.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 校验共通类
 * 
 * @author kjl
 * @date 2015-10-31
 * @Description
 * 
 */
public class CommonCheckUtil {

	public static final String REGEX_PHONE = "^[1][2,3,4,5,6,7,8][0-9]{9}$";	// 手机号码正则
	/**
	 * 正则验证
	 * 
	 * @author lxl
	 * @date 2015-9-30
	 * 
	 * @param regex 正则表达式
	 * @param str 要验证的值
	 * @return boolean
	 */
	public static boolean check(String regex, String str){
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 手机号码校验
	 * 
	 * @author kjl
	 * @date 2015-10-31
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if (StringUtils.isEmpty(mobile))
			return false;
		return check(REGEX_PHONE, mobile);
	}
/*
	*//**
	 * qq校验
	 * 
	 * @author kjl
	 * @date 2015-10-31
	 * 
	 * @param qq
	 * @return
	 *//*
	public static boolean checkQq(String qq) {
		if (StringUtils.isEmpty(qq))
			return false;
		return RegexUtil.check(Regex.REGEX_QQ, qq);
	}

	
	public static boolean checkDateIf_DATE_FORMAT_YMDHMS(String str) {
		return checkIfValidDate(str, DateUtil.DATE_FORMAT_YMDHMS);
	}
	
	*//**
	 * 检查时间格式是否符合
	 * @author kjl
	 * @date 2015-10-31
	 * 
	 * @param str
	 * @param farmat
	 * @return
	 *//*
	public static boolean checkIfValidDate(String str, String farmat) {
		if(StringUtils.isEmpty(str)){
			return false;
		}
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(farmat);
		try {
			format.setLenient(false);
			format.parse(str.trim());
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	
	*//**
	 * 小数或者数字校验
	 * @author kjl
	 * @date 2015-10-31
	 * 
	 * @param str
	 * @return
	 *//*
	public static boolean checkNumber(String str) {
		if(StringUtils.isEmpty(str)){
			return false;
		}
		return RegexUtil.check(Regex.REGEX_NUMBER, str);
	}
	
	
	*//**
	 * 身份证校验
	 * @author kjl
	 * @date 2015-10-31
	 * 
	 * @param str
	 * @return
	 *//*
	public static boolean checkIdCard(String str) {
		if(StringUtils.isEmpty(str)){
			return false;
		}
		IDCardUtil util = new IDCardUtil();
		return util.verify(str);
	}
	
	public static void main(String[] args) {
		System.out.println(checkMobile("13521222322"));
		System.out.println(checkQq("1353453452"));
		System.out.println(checkNumber("0.00"));
	}*/

}
