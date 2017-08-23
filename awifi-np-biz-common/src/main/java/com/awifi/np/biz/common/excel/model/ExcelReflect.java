package com.awifi.np.biz.common.excel.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 *用于存储实体的属性信息，
 *以便对插入数据的信息进行第一次校验(初始化便会校验)
 *
 */
public class ExcelReflect{
    /**
     * 字段
     */
    private Field field;
    /**
     *方法
     */
    private Method method;
    /**
     *excel行枚举信息
     */
    private ExcelColumn excelColumn;

    public ExcelReflect(){
    }

    public ExcelReflect(Field field, Method method, ExcelColumn excelColumn){
        this.field = field;
        this.method = method;
        this.excelColumn = excelColumn;
    }

    public Field getField(){
        return field;
    }

    public void setField(Field field){
        this.field = field;
    }

    public Method getMethod(){
        return method;
    }

    public void setMethod(Method method){
        this.method = method;
    }

    public ExcelColumn getExcel(){
        return excelColumn;
    }

    public void setExcel(ExcelColumn excelColumn){
        this.excelColumn = excelColumn;
    }

}
