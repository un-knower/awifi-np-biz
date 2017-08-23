package com.awifi.np.biz.common.excel.model;

import java.math.BigDecimal;

public class NumberData{
    /**
     * 
     */
    private String data;

    public NumberData(String data){
        this.data = data;
    }


    /**
     * 转换为long类型((^[a-z][a-zA-Z0-9]{0,29}$)
     * @return long
     */
    public Long Long(){
        return Long.parseLong(data);
    }

    /**
     * 转换为int类型
     * @return
     */
    public Integer Integer(){
        return Integer.parseInt(data);
    }
    /**
     * 转换为bigDecimal类型
     * @return BigDecimal
     */
    public BigDecimal BigDecimal(){
        return new BigDecimal(data);
    }
    /**
     * 返回字符串类型的data
     * @return String
     */
    public String String(){
        return data;
    }

}
