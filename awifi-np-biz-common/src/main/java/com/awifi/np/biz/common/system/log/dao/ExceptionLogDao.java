package com.awifi.np.biz.common.system.log.dao;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.system.log.model.ExceptionLog;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月6日 下午5:17:46
 * 创建作者：周颖
 * 文件名称：ExceptionLogMapper.java
 * 版本：  v1.0
 * 功能：异常日志dao层
 * 修改记录：
 */
@Service("exceptionLogDao")
public interface ExceptionLogDao {

    /**
     * 保存异常日志信息
     * @param log 异常日志
     * @author 周颖  
     * @date 2017年1月6日 下午5:18:14
     */
    @Insert("insert into np_biz_exception_log(error_code,module_name,parameter,error_message,interface_url,interface_param,interface_return_value) "
            + "values(#{errorCode},#{moduleName},#{parameter},#{errorMessage},#{interfaceUrl},#{interfaceParam},#{interfaceReturnValue})")
    void saveExceptionLog(ExceptionLog log);
}
