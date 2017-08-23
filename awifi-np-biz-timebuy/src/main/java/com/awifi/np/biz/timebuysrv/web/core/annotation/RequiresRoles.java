package com.awifi.np.biz.timebuysrv.web.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.cpj.swagger.annotation.APIs;

/**
 * 需要制定角色才能使用的接口
 * @author 张智威
 * 2017年4月17日 上午9:34:55
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRoles {

    /** 角色名称 */
    String value();


}
