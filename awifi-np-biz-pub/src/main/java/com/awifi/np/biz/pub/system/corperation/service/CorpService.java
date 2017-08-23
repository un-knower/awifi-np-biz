/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午8:14:46
* 创建作者：范涌涛
* 文件名称：CorpService.java
* 版本：  v1.0
* 功能： 厂商型号管理
* 修改记录：
*/
package com.awifi.np.biz.pub.system.corperation.service;




import com.awifi.np.biz.common.base.model.Page;

@SuppressWarnings({"rawtypes"})
public interface CorpService {
    
    /**
     * 根据条件分页查询厂商
     * @param param 查询条件 page 查询结果
     * @param page 查询结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:27:19
     */
    void queryListByParam(String param,Page page) throws Exception;
}
