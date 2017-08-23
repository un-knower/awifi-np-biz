package com.awifi.np.biz.timebuysrv.util;


import com.awifi.np.biz.timebuysrv.web.core.*;
import com.awifi.np.biz.timebuysrv.web.core.rules.*;
import com.awifi.np.biz.timebuysrv.web.log.ResultDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 版权所有：bean验证工具类
 * 项目名称:kaqm
 * 创建者: 宋展辉
 * 创建日期: 2015年7月10日
 * 文件说明: 
 */
public class ValidateUtil<T> {


    public Map<String, Rule[]> ruleMap = null;
    public Map<String, String> nameMap = null;
    public Map<String, String> valueMap = null;

    public ValidateUtil() {
        ruleMap = new HashMap<String,Rule[]>();
        nameMap = new HashMap<String, String>();
        valueMap = new HashMap<String, String>();
    }

    public Map<String, Object> validate(String key, String value, String name) throws Exception{
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Rule[] rules = ruleMap.get(key);
        if(rules!=null && rules.length>0){
            for(Rule rule : rules){
                rule.setValue(value);
                if(!rule.valid()){
                    resultMap.put("result", false);
                    resultMap.put("message", name+rule.getMessage());
                    break;
                }
            }
        }
        return resultMap;
    }
    
    public void init(){
        ruleMap = new HashMap<String,Rule[]>();
    }
    
    public void add(String key, String value, String name, Rule[] ruleArr){
        nameMap.put(key, name);
        valueMap.put(key, value);
        ruleMap.put(key, ruleArr);
    }

    
    public Map<String, Object> validateAll() throws Exception{
        Map<String, Object> resultMap = null;
        for (Entry<String, Rule[]> entry : ruleMap.entrySet()) {
            String key = entry.getKey();
            resultMap = validate(key);
            boolean result = (Boolean)resultMap.get("result");
            if(!result){
                resultMap.put("result", result);
                break;
            }
        }
        return resultMap;
    }
    
    
    public String validateString() throws Exception{
        String message = null;
        Map<String, Object> resultMap = null;
        for (Entry<String, Rule[]> entry : ruleMap.entrySet()) {
            String key = entry.getKey();
            resultMap = validate(key);
            boolean result = (Boolean)resultMap.get("result");
            if(!result){
                message = (String)resultMap.get("message");
                break;
            }
        }
        this.clear();
        return message;
    }
    
    public Map<String, Object> validate(String key) throws Exception{
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", true);
        Rule[] rules = ruleMap.get(key);
        if(rules!=null && rules.length>0){
            for(Rule rule : rules){
                rule.setValue(valueMap.get(key));
                if(!rule.valid()){
                    resultMap.put("result", false);
                    resultMap.put("message", nameMap.get(key)+rule.getMessage());
                    break;
                }
            }
        }
        return resultMap;
    }
    
    public void clear(){
        //ruleMap.clear();
        //nameMap.clear();
        //valueMap.clear();
        ruleMap = null;
        nameMap = null;
        valueMap = null;
    }

    /**
     * 
     */
    private static final Logger log = LoggerFactory.getLogger(ValidateUtil.class);
    /**
     * @param object 对象
     * @return  ValidateResult
     */
 
}
