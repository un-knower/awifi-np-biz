/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 上午8:50:36
* 创建作者：周颖
* 文件名称：ComponentService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.component.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.toe.portal.component.model.Component;

public interface ComponentService {

    /**
     * 组件列表
     * @param page 页面
     * @param keywords 组件名称关键字
     * @author 周颖  
     * @date 2017年4月12日 上午10:22:17
     */
    void getListByParam(Page<Component> page, String keywords);

    /**
     * 组件添加
     * @param request 请求
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月14日 上午11:28:55
     */
    void add(HttpServletRequest request) throws Exception;

    /**
     * 组件编辑
     * @param request 请求
     * @param id 组件id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月14日 下午1:12:15
     */
    void edit(HttpServletRequest request,Long id) throws Exception;

    /**
     * 组件详情
     * @param id 组件id
     * @return 组件
     * @author 周颖  
     * @date 2017年4月14日 下午3:05:32
     */
    Component getById(Long id);

    /**
     * 获取组件列表
     * @param merchantId 商户id
     * @param type 组件类型
     * @return 组件列表
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月3日 上午10:12:52
     */
    List<Component> getListByType(Long merchantId, Integer type) throws Exception;

    /**
     * 图片上传
     * @param request 请求
     * @return 图片保存的路径
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月13日 下午7:29:57
     */
    String picUpload(HttpServletRequest request) throws Exception;
}
