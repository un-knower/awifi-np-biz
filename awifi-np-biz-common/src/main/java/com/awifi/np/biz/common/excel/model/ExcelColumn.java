package com.awifi.np.biz.common.excel.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *excel行的枚举
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn{

    /**
     * 列号,0开始
     */
    int columnNum();

    /**
     * 列对应的字段名称
     */
    String columnName();

    /**
     * 列数据类型
     */
    Type columnType() default Type.String;

    /**
     * 是否可以空
     */
    boolean none() default true;

    /**
     * 字符长度
     */
    int length() default 100;

}
