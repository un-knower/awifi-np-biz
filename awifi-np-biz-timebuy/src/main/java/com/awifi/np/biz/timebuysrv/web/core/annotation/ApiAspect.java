package com.awifi.np.biz.timebuysrv.web.core.annotation;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.util.ValidateUtil;
import com.awifi.np.biz.timebuysrv.web.core.rules.CheckBox;
import com.awifi.np.biz.timebuysrv.web.core.rules.IpRule;
import com.awifi.np.biz.timebuysrv.web.core.rules.MacShortRule;
import com.awifi.np.biz.timebuysrv.web.core.rules.Numeric;
import com.awifi.np.biz.timebuysrv.web.core.rules.PhoneRule;
import com.awifi.np.biz.timebuysrv.web.core.rules.Required;
import com.awifi.np.biz.timebuysrv.web.core.rules.Rule;
import com.awifi.np.biz.timebuysrv.web.util.ResultUtil;

/**
 * Created by dozen.zhang on 2017/3/20.
 */
 @Component
 @Aspect
public class ApiAspect {
     Logger logger = LoggerFactory.getLogger(ApiAspect.class);
    /*
     * @Pointcut("execution(public * com.itsoft.action..*.*(..))") public void
     * recordLog(){}
     */
    /**
     * 定义缓存逻辑
     */
    @Around("execution(* *.*(..)) && @annotation(com.cpj.swagger.annotation.API)")
    public Object cache(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        Method method = getMethod(pjp);
        API api = method.getAnnotation(com.cpj.swagger.annotation.API.class);
        RequestMapping methodUrl = method.getAnnotation(RequestMapping.class);
        Param[] params = api.parameters();
       /* String[] consume = api.consumes();
        
        logger.info(consume.toString());*/
        Object[] objectAry = pjp.getArgs();
        HttpServletRequest request = null;
        for (int i = 0; i < objectAry.length; i++) {
            if (objectAry[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) objectAry[i];
            }
        }
        if (request == null) {
            return pjp.proceed();

// throw new Exception("request 不能为空");
        }

        ValidateUtil vu = new ValidateUtil();
        String validStr = "";

        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }

        int startIndex = methodUrl.value()[0].indexOf("{");
        int endIndex = methodUrl.value()[0].indexOf("}");
        String restfulParam = null;
        if (startIndex >= 0) {
            restfulParam = methodUrl.value()[0].substring(startIndex + 1, endIndex - 1);
        }

        for (int i = 0; i < params.length; i++) {
            String name = params[i].name();
            if (restfulParam != null && restfulParam.equals(name)) {
                continue;
            }

            String value = request.getParameter(name);
            DataType type = params[i].dataType();
            List<Rule> rules = new ArrayList<Rule>();
            if (type == DataType.FILE) {
                continue;
            }else
            if (type == DataType.INTEGER) {
                rules.add(new Numeric());
            } else if (type == DataType.LONG) {
                rules.add(new Numeric());
            } else if (type == DataType.FLOAT) {
                rules.add(new Numeric());
            } else if (type == DataType.DOUBLE) {
                rules.add(new Numeric());
            } else if (type == DataType.STRING) {
                // rules.add(new Numeric());
            } else if (type == DataType.ARRAY) {
                String items = params[i].items();
                rules.add(new CheckBox(items.split(",")));
            } else if (type == DataType.IP) {
                String items = params[i].items();
                rules.add(new IpRule());
            } else if (type == DataType.PORT) {
                rules.add(new Numeric());
            } else if (type == DataType.MAC_SHORT) {
                rules.add(new MacShortRule());
            } else if (type == DataType.PHONE) {
                rules.add(new PhoneRule());
            }
            if (params[i].required()) {
                rules.add(new Required());
            }
            Rule[] ruleAry = new Rule[rules.size()];
            vu.add(name, value, params[i].description(), rules.toArray(ruleAry));
        }
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            throw new ValidException("E04158812321",validStr);
            //return ResultUtil.getResult(302, validStr);
        }
        return pjp.proceed();

    }

    /**
     * 获取被拦截方法对象
     *
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp) {
        // 获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            String name = args[i].getClass().getName();
            // System.out.println(args[i].getClass().getName());
            // System.out.println(args[i].getClass().toString());

            argTypes[i] = args[i].getClass();
            if (args[i].getClass().getName().equals("org.apache.catalina.connector.RequestFacade") || "org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest".equals(args[i].getClass().getName())) {
                argTypes[i] = HttpServletRequest.class;//javax.servlet.http.HttpServletRequest
            }
        }
        Method method = null;
        try {
            Class[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
            Class clz  = pjp.getTarget().getClass();
            String methodName = pjp.getSignature().getName();
            //[class org.springframework.web.multipart.commons.CommonsMultipartFile, class java.lang.Long, class java.lang.Integer, interface javax.servlet.http.HttpServletRequest]
           //[class org.springframework.web.multipart.commons.CommonsMultipartFile, class java.lang.Long, class java.lang.Integer, interface javax.servlet.http.HttpServletRequest]
           
            //org.springframework.web.multipart.MultipartFile,java.lang.Long,int,javax.servlet.http.HttpServletRequest
           
            method = clz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }

}
