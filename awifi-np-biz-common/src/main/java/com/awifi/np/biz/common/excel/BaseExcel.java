package com.awifi.np.biz.common.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.awifi.np.biz.common.excel.model.ExcelColumn;
import com.awifi.np.biz.common.excel.model.ExcelReflect;
import com.awifi.np.biz.common.excel.model.Sheet;


public abstract class BaseExcel<T>{
    /**
     * 获取导入数据的实体类
     */
    protected Class<T> clz;
    /**
     * 用于存储待导入数据的集合
     */
    protected List<T> datas;
    /**
     * 实体类的反射信息
     */
    protected ExcelReflect[] reflects;
    /**
     * 
     */
    protected ExcelReflect sheet;

    /**
     * 构造方法
     * @param clz class
     */
    public BaseExcel(Class<T> clz){
        this.clz = clz;
        //初始化便会获取实体的反射信息
        reflect();
    }
    
    /**
     * 得到实体类反射方法
     */
    private void reflect(){
        try{
            Field[] fields = clz.getDeclaredFields();
            List<ExcelReflect> fs = new ArrayList<ExcelReflect>();
            for (Field field : fields){
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                if (excelColumn != null){
                    Method method = getMethod(field);
                    fs.add(new ExcelReflect(field, method, excelColumn));
                    Sheet s = field.getAnnotation(Sheet.class);
                    if (sheet == null && s != null){
                        sheet = new ExcelReflect(field, method, excelColumn);
                    }
                }
            }
            reflects = fs.toArray(new ExcelReflect[0]);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        catch (NoSuchMethodException e){
            e.printStackTrace();
        }
    }
    /**
     * 得到实体字段的set方法
     * @param field 属性名
     * @return method
     * @throws NoSuchMethodException 异常
     * @throws SecurityException 异常
     */
    protected abstract Method getMethod(Field field) throws NoSuchMethodException, SecurityException;
    /**
     * 获得属性名(首字母大写)
     * @param methodName 属性名
     * @return string
     */
    protected final String getMethodName(String methodName){
        return methodName.substring(0, 1).toUpperCase().concat(methodName.substring(1));
    }
}
