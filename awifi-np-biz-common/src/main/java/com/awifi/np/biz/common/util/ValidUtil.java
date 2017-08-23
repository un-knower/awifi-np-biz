package com.awifi.np.biz.common.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.exception.ValidException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 下午2:05:15
 * 创建作者：许小满
 * 文件名称：ValidUtil.java
 * 版本：  v1.0
 * 功能：参数校验
 * 修改记录：
 */
@SuppressWarnings("unchecked")
public class ValidUtil {

    /**
     * 校验
     * @param key 键
     * @param value 值
     * @param rules 规则
     * @author 许小满  
     * @date 2017年1月17日 下午2:26:47
     */
    public static void valid(String key, Object value, String rules){
        notBlank("rules", rules);//校验规则不允许为空
        if(rules.indexOf("{") == -1){//判断rules是否属于json格式的
            validSingle(key, value, rules);//校验单参数
        }else {
            validMulti(key, value, rules);//校验正则或多个参数
        }
    }
    
    /**
     * 校验单参数
     * @param key 键
     * @param value 值
     * @param rule 规则
     * @author 许小满  
     * @date 2017年1月17日 下午2:26:47
     */
    private static void validSingle(String key, Object value, String rule){
        if(rule.trim().equals("required")){//必填校验
            notBlank(key, value);//非空字符串校验
        }else if(rule.trim().equals("numeric")){//数字校验
            isNumeric(key, value);//数字校验
        }else if(rule.trim().equals("arrayNotBlank")){//数组内部不允许存在空值(null|"")的校验
            arrayNotBlank(key, value);//数组内部不允许存在空值(null|"")的校验
        }else{
            throw new ValidException("E2000013", MessageUtil.getMessage("E2000013", rule));//rule[{0}]超出了范围!
        }
    }
    
    /**
     * 校验正则或多个参数
     * @param key 键
     * @param value 值
     * @param rules 规则
     * @author 许小满  
     * @date 2017年1月17日 下午2:26:47
     */
    private static void validMulti(String key, Object value, String rules){
        Map<String,Object> ruleMap = JsonUtil.fromJson(rules, LinkedHashMap.class);
        for(Map.Entry<String, Object> entry : ruleMap.entrySet()){
            String ruleKey = entry.getKey();//规则 键
            Object ruleValue = entry.getValue();//规则 值
            notBlank("ruleKey", ruleKey);//规则 键 不允许为空
            notNull("ruleValue", ruleValue);//规则 值 不允许为空
            if(ruleKey.trim().equals("required")){//必填校验
                isBoolean("ruleValue", ruleValue);//规则 值 必须为boolean
                if((Boolean)ruleValue){
                    notBlank(key, value);//非空字符串校验
                }
            }else if(ruleKey.trim().equals("numeric")){//数字校验
                if(ruleValue instanceof Boolean && !((Boolean)ruleValue)){//当为false时，不校验
                    return;
                }
                isNumeric(key, value);//数字校验
                isNumericRange(key,value,ruleValue);//数字最大/最小校验
            }else if(ruleKey.trim().equals("arrayNotBlank")){//数组内部不允许存在空值(null|"")的校验 
                isBoolean("ruleValue", ruleValue);//规则 值 必须为boolean
                if((Boolean)ruleValue){
                    arrayNotBlank(key, value);//数组内部不允许存在空值(null|"")的校验 
                }
            }else if(ruleKey.trim().equals("regex")){//正则匹配
                isString("ruleValue", ruleValue);//规则 值 必须为字符串
                regex(key, value, (String)ruleValue);
            }
        }
    }
    
    /**
     * 字符串判断
     * @param key 键
     * @param value 值
     * @author 许小满  
     * @date 2017年1月17日 下午2:37:26
     */
    private static void isString(String key, Object value){
        if(!(value instanceof String)){
            throw new ValidException("E2000014", MessageUtil.getMessage("E2000014", key));//{0}的数据类型必须为必须为字符串!
        }
    }
    
    /**
     * 字符串判断
     * @param key 键
     * @param value 值
     * @author 许小满  
     * @date 2017年1月17日 下午2:37:26
     */
    private static void isBoolean(String key, Object value){
        if(!(value instanceof Boolean)){
            throw new ValidException("E2000015", MessageUtil.getMessage("E2000015", key));//{0}的数据类型必须为必须为Boolean!
        }
    }
    
    /**
     * 数字判断
     * @param key 键
     * @param value 值
     * @author 许小满  
     * @date 2017年1月22日 上午10:14:54
     */
    private static void isNumeric(String key, Object value){
        if(value == null){
            return;
        }
        if(value instanceof Integer || value instanceof Long){
            return;
        }
        isString(key, value);
        String valueStr = (String)value;
        if(!RegexUtil.match(valueStr, RegexConstants.NUMBER_PATTERN)){
            throw new ValidException("E2000024", MessageUtil.getMessage("E2000024", key));//{0}的数据类型必须为必须为数字!
        }
    }
    
    /**
     * 数字最大/最小校验
     * @param key 
     * @param value 
     * @param ruleValue 
     * @author 亢燕翔  
     * @date 2017年1月22日 下午2:23:16
     */
    private static void isNumericRange(String key, Object value, Object ruleValue) {
        if(value == null){//当值为空时，流程结束
            return;
        }
        if(ruleValue instanceof Boolean){//ruleValue 为boolean时，流程结束
            return;
        }
        /* 规则  不允许为为空，防止出现  rlueValue=="" 的情况 */
        if(ruleValue instanceof String){
            String ruleValueStr = (String)ruleValue;//校验规则 转化为字符串
            if(ruleValueStr == null || ruleValueStr.trim().length() <= 0){//非空校验
                throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", "规则"));//{0}不允许为空!
            }
        }
        /* value 处理，转换为int */
        isNumeric("numeric.value", value);//判断value是否为数字
        Integer valueInt = CastUtil.toInteger(value);
        
        Map<String, Integer> numMap = JsonUtil.fromJson(ruleValue.toString(), HashMap.class);
        /* numeric.min 处理，转换为int */
        Object minObj = numMap.get("min");
        isNumeric("numeric.min", minObj);//判断规则中的最小值[min]是否为数字
        Integer min =CastUtil.toInteger(minObj);
        if(min != null && min > valueInt){
            throw new ValidException("E2000025", MessageUtil.getMessage("E2000025", new Object[]{key,min}));
        }
        /* numeric.max 处理，转换为int */
        Object maxObj = numMap.get("max");
        isNumeric("numeric.max", maxObj);//判断规则中的最小值[min]是否为数字
        Integer max =CastUtil.toInteger(maxObj);
        if(max == null){
            return;
        }
        if(max < valueInt){
            throw new ValidException("E2000026", MessageUtil.getMessage("E2000026", new Object[]{key, max}));
        }
    }
    
    /**
     * 非空判断
     * @param key 键
     * @param value 值
     * @author 许小满  
     * @date 2017年1月17日 下午2:08:08
     */
    private static void notNull(String key, Object value){
        if(value == null){
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", key));//{0}不允许为空!
        }
    }
    
    /**
     * 非空字符串判断
     * @param key 键
     * @param value 值
     * @author 许小满  
     * @date 2017年1月17日 下午2:08:08
     */
    private static void notBlank(String key, Object value){
        notNull(key, value);//非空判断
        //isString(key, value);//字符串判断
        if(!(value instanceof String)){
            return;
        }
        String valueStr = (String)value;
        if(valueStr.trim().length() <= 0){
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", key));//{0}不允许为空!
        }
    }
    
    /**
     * 数组内部不允许存在空值(null|"")的校验
     * @param key 键
     * @param value 值
     * @author 许小满  
     * @date 2017年2月9日 上午9:29:17
     */
    private static void arrayNotBlank(String key, Object value){
        notNull(key, value);//非空判断
        if(!(value instanceof Object[])){
            return;
        }
        Object[] objArray= (Object[])value;
        int maxLength = objArray.length;
        if(maxLength <= 0){
            throw new ValidException("E2000042", MessageUtil.getMessage("E2000042", "数组内容"));//{0}不允许为空!
        }
        Object obj = null;
        for(int i=0; i<maxLength; i++){
            obj = objArray[i];
            if(obj == null){//对象不允许为空
                throw new ValidException("E2000042", MessageUtil.getMessage("E2000042", key));//{0}不允许为空!
            }
            if(obj instanceof String){//字符串不允许为""
                String str = (String)obj;
                if(str.trim().length() <= 0){
                    throw new ValidException("E2000042", MessageUtil.getMessage("E2000042", key));//{0｝不允许包含空记录!
                }
            }
        }
        
    }
    
    /**
     * 正则匹配
     * @param key 键
     * @param value 值
     * @param pattern 正则公式
     * @author 许小满  
     * @date 2017年1月17日 下午2:19:39
     */
    private static void regex(String key, Object value, String pattern){
        //notNull(key, value);//非空判断
        if(value == null){
            return;
        }
        isString(key, value);//字符串判断
        String valueStr = (String)value;
        if(!RegexUtil.match(valueStr, pattern)){
            throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", key));//{0}不符合正则规范!
        }
    }
    
    /**
     * 测试
     * @param args 参数
     * @author 许小满  
     * @date 2017年1月17日 下午2:07:17
     */
    public static void main(String[] args) {
        //ValidUtil.valid("参数A", "", "required");
        //ValidUtil.valid("参数A", "", "{'required': false}");//为false时不做校验
        //ValidUtil.valid("参数A", "", "{'required': true}");
        
        //ValidUtil.valid("参数A", "0", "numeric");//数字校验
        //ValidUtil.valid("参数A", "1", "{'numeric':true}");//数字校验
        //ValidUtil.valid("参数A", "1", "{'required':true,'numeric':true}");//数字校验
        //ValidUtil.valid("参数A", "2", "{'numeric':{'min':1,'max':3}}");//数字校验含范围比较
        
        //ValidUtil.valid("参数A", "989096C11D9", "{'required':true, 'regex':'^[0-9A-F]{12}$'}");//正则校验
        
        /* 数组内部不允许存在空值(null|"")的校验 */
        //Object[] objs = {};
        //ValidUtil.valid("参数A", objs, "arrayNotBlank");
        //ValidUtil.valid("参数A", objs, "{'arrayNotBlank':true}");
    }
}
