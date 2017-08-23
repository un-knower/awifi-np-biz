/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年3月24日 下午7:22:39
* 创建作者：许小满
* 文件名称：SysConfigService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.sysconfig.service;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.system.sysconfig.model.SysConfig;

public interface SysConfigService {

    /**
     * 通过key获取value
     * @param paramKey 参数键
     * @return 参数值
     * @author 亢燕翔  
     * @date Jan 10, 2017 8:44:50 AM
     */
    String getParamValue(String paramKey);
    
    /**
     * 分页查询接口
     * @param page 分页对象
     * @param keywords 关键字查询条件
     * @author 许小满  
     * @date 2017年3月24日 下午7:24:22
     */
    void getListByParam(Page<SysConfig> page, String keywords);

    /**
     * 系统参数配置添加
     * @param aliasName 别名
     * @param paramKey 参数键
     * @param paramValue 参数值
     * @param remark 备注
     * @author 许小满  
     * @date 2017年5月18日 上午12:03:59
     */
    void add(String aliasName, String paramKey, String paramValue, String remark);
    
    /**
     * 系统参数配置修改
     * @param id 主键id
     * @param aliasName 别名
     * @param paramKey 参数键
     * @param paramValue 参数值
     * @param remark 备注
     * @author 许小满  
     * @date 2017年5月18日 上午12:03:59
     */
    void update(Long id, String aliasName, String paramKey, String paramValue, String remark);
    
    /**
     * 系统参数配置修改删除
     * @param id 主键id
     * @author 许小满  
     * @date 2017年5月18日 上午12:24:53
     */
    void delete(Long id);
    
    /**
     * 通过id获取配置信息
     * @param id 主键id
     * @return 配置信息
     * @author 许小满  
     * @date 2017年5月18日 上午12:23:45
     */
    SysConfig getById(Long id);
    
}
