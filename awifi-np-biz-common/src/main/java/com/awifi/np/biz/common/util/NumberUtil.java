package com.awifi.np.biz.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


/**
 * Number数字工具类
 * 
 * @author zhu wei rong
 * 
 */
public class NumberUtil{
    
    
    /**
     * float数字格式化，返回不带小数点和带一位小数点的数字
     * @param num 数字
     * @return string
     * @author 王冬冬  
     * @date 2017年6月27日 下午4:52:49
     */
    public static String decimalFormat(Float num){
        if(num==null){
            return null;
        }
        DecimalFormat df = new DecimalFormat("######.#");
        return df.format(num);
    }

    /**
     * 保留二位小数, num1/num2
     * 
     * @param num1
     *            除数
     * @param num2
     *            被除数
     * @return 保留二位小数的Double
     */
    public static Double divideRate(Double num1, Double num2){
        java.math.BigDecimal bigDecimal = new java.math.BigDecimal(num1 / num2);
        return Double.parseDouble(bigDecimal.setScale(2, java.math.BigDecimal.ROUND_HALF_UP).toString());
        // 另一种方法
        /*
         * NumberFormat numberFormat = NumberFormat.getInstance();
         * numberFormat.setMaximumFractionDigits(2); return
         * Double.parseDouble(numberFormat.format(num1 /num2));
         */
    }

    /**
     * 四舍五入得到二位小数
     * 
     * @Title:round2Point
     * @Description:
     * @param num num
     * @return NumberUtil
     */
    public static Double round2Point(Double num){
        return roundPoint(num, 2);
    }

    /**
     * 四舍五入保留pointNum位小数
     * 
     * @param num num
     * @param pointNum pointNum
     * @return Double
     */
    public static Double roundPoint(Double num, int pointNum){
        java.math.BigDecimal bigDecimal = new java.math.BigDecimal(num);
        return Double.parseDouble(bigDecimal.setScale(pointNum, java.math.BigDecimal.ROUND_HALF_UP)
                .toString());
    }

    /**
     * 按format指定格式格式化number,一般用于数字格式化
     * 
     * @param number
     *            要格式的数字
     * @param format
     *            指定的格式
     * @return String
     */
    public static String formatNumber(Object number, String format){
        return new java.text.DecimalFormat(format).format(number);
    }

    /**
     * 判断是否为不为空的Number
     * 
     * @param number number
     * @return boolean
     */
    public static boolean isNotNullNumber(Object number){
        try{
            if (null != number && StringUtils.isNotBlank(number.toString())){
                new BigDecimal(number.toString().trim());
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * 格式化Number,包括科学计算法与小数位数
     * 
     * @param number number
     * @return String
     */
    public static String formatNumber(Object number){
        if (isNotNullNumber(number)){
            java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("##.##");
            decimalFormat.setMaximumFractionDigits(6); // 保留6位小数
            return decimalFormat.format(new BigDecimal(number.toString().trim()));
        }
        return null;
    }

    /**
     * 格式化Number,包括科学计算法与小数位数,保留最大位的小数
     * 
     * @param number number
     * @return Number
     */
    public static Number formatMaxPointNumber(Object number){
        if (isNotNullNumber(number)){
            java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("##.##");
            decimalFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
            return new BigDecimal(number.toString().trim());
        }
        return null;
    }

    /**
     * @Title:formatMaxPointString
     * @param number number
     * @return String
     */
    public static String formatMaxPointString(Object number){
        if (isNotNullNumber(number)){
            java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("##.##");
            decimalFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
            return new BigDecimal(number.toString().trim()).toPlainString();

        }
        return null;
    }

    /**
     * 金币计算策略,默认取整数,不四舍五入
     * 
     * @param d d
     * @return long
     */
    public static long calcGold(Double d){
        return d.longValue();
    }

    /**
     * 积分计算策略,默认取整数,不四舍五入
     * 
     * @param d d
     * @return long
     */
    public static long calcScore(Double d){
        return calcGold(d);
    }

    /**
     * 判断一个字符串是否为数值类型
     * 
     * @param str str
     * @return boolean
     */
    public static boolean isNumeric(String str){
        return NumberUtils.isNumber(str);
    }

    /**
     * 判断一个字符串是否为全部为零
     * 
     * @param str str
     * @return boolean
     */
    public static boolean isZeroNumeric(String str){
        Pattern pattern = Pattern.compile("[0]*");
        Matcher isZeroNum = pattern.matcher(str);
        if (!isZeroNum.matches()){
            return false;
        }
        return true;
    }

    /**
     * 如果小数点后全部为0，返回小数点前面的数字，否则返回本身
     * @Title:getNumber
     * @Description:
     * @param str str
     * @return String
     */
    public static String getNumber(String str){
        if (str == null || !NumberUtils.isNumber(str)){
            return str;
        }
        String[] strs = str.split("\\.");
        if (strs.length != 2){
            return str;
        }
        try{
            if (Long.valueOf(strs[1]) > 0){// 不能转换 则说明小数点后的数据含有其他符号
                return removeEndZero(str);
            }
            else{
                return strs[0];
            }
        }
        catch (NumberFormatException e){
            return str;
        }
    }

    /**
     * @Title:removeEndZero
     * @param str str
     * @return String
     */
    private static String removeEndZero(String str){
        if (str.endsWith("0")){
            str = str.substring(0, str.length() - 1);
        }
        else{
            return str;
        }
        return removeEndZero(str);
    }

    /**
     * @Title:getDecimalNumber
     * @param value value
     * @param decimalNumber decimalNumbe
     * @return String
     */
    public static String getDecimalNumber(double value, int decimalNumber){
        try{
            return String.format("%." + decimalNumber + "f", value);
        }
        catch (Exception e){
            return "";
        }
    }

    /**
     * @Title:isMobileNO
     * @param mobiles mobiles
     * @return boolean
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
