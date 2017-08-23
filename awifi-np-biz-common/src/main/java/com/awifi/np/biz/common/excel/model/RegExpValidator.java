package com.awifi.np.biz.common.excel.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.awifi.np.biz.common.base.constants.RegexConstants;

/**
 * 正则化工具类
 * 
 */
public final class RegExpValidator{

    /**
     * 验证数据长度（1~100）
     * 
     * @param str str
     * @return boolean
     */
    public static boolean isLength(String str){
        String regex = "^([\\S]{1,100}$)";
        return match(regex, str.replaceAll(" ", ""));
    }


    /**
     * 长度验证
     * @param str 输入的字符串
     * @param length 长度
     * @return 布尔值
     */
    public static boolean isLengthAuto(String str, int length){
        String regex = "^([\\S]{1," + length + "}$)";
        return match(regex, str.replaceAll(" ", ""));
    }


    /**
     * 验证长度(长度在0-50之内)
     * @param str 输入字符串
     * @return 布尔值
     */
    public static boolean isLength1(String str){
        String regex = "^([\\S]{0,50}$)";
        return match(regex, str.replaceAll(" ", ""));
    }


    /**
     * 字符串长度验证(1-70)
     * @param str 输入字符串
     * @return 布尔值
     */
    public static boolean isLength70(String str){
        String regex = "^([\\S]{0,70}$)";
        return match(regex, str.replaceAll(" ", ""));
    }

    /**
     * @Title:验证数据长度（1~30）
     * @param str str
     * @return boolean
     */
    public static boolean isLength30(String str){
        String regex = "^([\\S]{0,30}$)";
        return match(regex, str.replaceAll(" ", ""));
    }

    /**
     * @Title:验证数据长度（1~100）
     * @param str str
     * @return boolean
     */
    public static boolean isLength100(String str){
        String regex = "^([\\S]{0,100}$)";
        return match(regex, str.replaceAll(" ", ""));
    }

    /**
     * @Title:验证数据长度（1~200）
     * @param str str
     * @return boolean
     */
    public static boolean isLength200(String str){
        String regex = "^([\\S]{0,200}$)";
        return match(regex, str.replaceAll(" ", ""));
    }

    /**
     * @Title:验证数据长度（1~160）
     * @param str str
     * @return boolean
     */
    public static boolean isLength160(String str){
        String regex = "^([\\S]{0,160}$)";
        return match(regex, str.replaceAll(" ", ""));
    }

    /**
     * @Title:手机号码校验
     * @param str str
     * @return boolean
     */
    public static boolean IsMobile(String str){
        String regex = "^$|(1[3|5|8]\\d{9})|(0\\d{2,3}-\\d{7,8})";
        return match(regex, str);
    }

    /**
     * @Title:英文，数字 验证数据长度（1~50）
     * @param str str
     * @return boolean
     */
    public static boolean IsEngAndNumLength(String str){
        String regex = "[a-zA-Z0-9.-]{1,50}";
        return match(regex, str);
    }

    /**
     * @Title:英文，数字 验证数据长度（1~50）
     * @param str str
     * @param length length
     * @return boolean
     */
    public static boolean IsEngAndNumLengthAuto(String str, int length){
        String regex = "[a-zA-Z0-9.-]{1," + length + "}";
        return match(regex, str);
    }

    /**
     * @Title:数字 验证数据长度（1~50）
     * @param str str
     * @return boolean
     */
    public static boolean isNumLength(String str){
        String regex = "[0-9]{1,50}";
        return match(regex, str);
    }

    /**
     * @Title:验证数据长度（1~12）
     * @param str str
     * @return boolean
     */
    public static boolean isLength2(String str){
        String regex = RegexConstants.MAC_NO_CASE_PATTERN;
        return match(regex, str);
    }

    /**
     * @Title:校验IP地址
     * @param str str
     * @return boolean
     */
    public static boolean isIP(String str){
        String regex = "((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)";
        return match(regex, str);

    }

    /**
     * @Title:isMenory
     * @param str str
     * @return boolean
     */
    public static boolean isMenory(String str){
        String regex = "[0-9]{1,15}[G|g|m|M]";
        return match(regex, str);
    }

    /**
     * @Title: 数字 验证数据长度（1~15）
     * @param str str
     * @return boolean
     */
    public static boolean isNumLength1(String str){
        String regex = "[0-9]{1,15}";
        return match(regex, str);
    }

    /**
     * @Title:数字 验证数据长度（1~15）
     * @param str str
     * @return RegExpValidator
     */
    public static boolean isNumLength10(String str){
        String regex = "[0-9]{0,10}";
        return match(regex, str);
    }

    /**
     * @Title:IsNumLengthAuto
     * @param str str
     * @param length length
     * @return boolean
     */
    public static boolean isNumLengthAuto(String str, int length){
        String regex = "[\\d+(\\.\\d+)?]{1," + length + "}";
        return match(regex, str);
    }

    /**
     * @Title:数字最大为65535
     * @param str str
     * @return boolean
     */
    public static boolean isNumMax65535(String str){
        String regex = "^$|(65535|(\\d{0,4}|[0-6][0-5][0-5][0-3][0-5]))";
        return match(regex, str);
    }

    /**
     * @Title:验证0或者1
     * @param str str
     * @return boolean
     */
    public static boolean is0or1(String str){
        String regex = "0|1{0,1}";
        return match(regex, str);

    }

    /**
     * @Title:验证A或者B或者C或者
     * @param str str
     * @return boolean
     */
    public static boolean isABCD(String str){
        String regex = "(A|B|C|D){1}";
        return match(regex, str);

    }

    /**
     * @Title:IsVersion
     * @param str str
     * @return boolean
     */
    public static boolean isVersion(String str){
        String regex = "(V|v)[0-z]{1,9}\\.[0-z]{1,9}\\.[0-z]{1,9}";
        return match(regex, str);
    }

    /**
     * @Title:维度校验
     * @param str str
     * @return boolean
     */
    public static boolean regWD(String str){
        String regex = "^$|(-|\\+)?(90|90\\.0{1,5}|(\\d|[1-8]\\d)\\.\\d{1,5}|(\\d|[1-8]\\d))";
        return match(regex, str);
    }

    /**
     * @Title:经度校验
     * @param str str
     * @return boolean
     */
    public static boolean regJD(String str){
        String regex = "^$|(-|\\+)?(180|180\\.0{1,5}|(\\d{1,2}|1([0-7]\\d))\\.\\d{1,5}|(\\d{1,2}|1([0-7]\\d)))";
        return match(regex, str);
    }

    /**
     * @Title:IsCountry
     * @param str str
     * @return boolean
     */
    public static boolean isCountry(String str){
        String regex = "中国";
        return match1(regex, str);
    }

    /**
     * @Title:IsProvince
     * @param str
     * @return boolean
     */
    public static boolean isProvince(String str){
        String regex = "省";
        return match1(regex, str);
    }

    /**
     * @Title:IsCity
     * @param str
     * @return boolean
     */
    public static boolean isCity(String str){
        String regex = "市";
        return match1(regex, str);
    }

    /**
     * @Title:IsCounty
     * @param str str
     * @return boolean
     */
    public static boolean isCounty(String str){
        String regex = "区";
        String regex1 = "县";
        return match2(regex, regex1, str);
    }

    /**
     * @Title:match
     * @param regex regex
     * @param str str
     * @return boolean
     */
    private static boolean match(String regex, String str){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * @Title:match1
     * @param regex regex
     * @param str str
     * @return boolean
     */
    private static boolean match1(String regex, String str){
        if (str.contains(regex)){
            return true;
        }
        return false;
    }

    /**
     * @Title:match2
     * @param regex regex
     * @param regex1 regex1
     * @param str str
     * @return boolean
     */
    private static boolean match2(String regex, String regex1, String str){
        if (str.contains(regex) || str.contains(regex1)){
            return true;
        }
        return false;
    }
}
