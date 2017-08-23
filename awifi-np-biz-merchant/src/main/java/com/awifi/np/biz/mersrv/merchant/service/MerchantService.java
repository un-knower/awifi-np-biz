package com.awifi.np.biz.mersrv.merchant.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月24日 上午8:51:39
 * 创建作者：周颖
 * 文件名称：MerchantService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
public interface MerchantService {

    /**
     * 商户列表
     * @param sessionUser sessionUser
     * @param page page
     * @param paramsMap 搜索参数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 上午9:30:36
     */
    void getListByParam(SessionUser sessionUser,Page page,Map<String,Object> paramsMap) throws Exception;

    /**
     * 添加商户
     * @param curUserId 当前登陆账号id
     * @param merchant 商户
     * @param industryCode 行业编号
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月4日 上午10:58:18
     */
    void add(Long curUserId, Merchant merchant, String industryCode) throws Exception;

    /**
     * 判断商户名称是否存在
     * @param merchantName 商户名称
     * @return true 存在 false 不存在
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月16日 上午11:11:22
     */
    boolean isMerchantNameExist(String merchantName) throws Exception;
    
    /**
     * 获取商户详情
     * @param id 商户id
     * @return 商户详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月6日 上午8:50:18
     */
    Merchant getById(Long id) throws Exception;

    /**
     * 编辑商户
     * @param merchant 商户
     * @param industryCode 行业编号
     * @author 周颖  
     * @throws Exception  
     * @date 2017年2月6日 下午4:43:33
     */
    void update(Merchant merchant, String industryCode) throws Exception;

    /**
     * 更新下级商户项目归属
     * @param paramsMap paramsMap
     * @param projectId 项目id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月6日 上午10:36:58
     */
    void updateSub( Map<String,Object> paramsMap,Long projectId) throws Exception;

    /**
     * 商户导出列表 一个sheet
     * @param paramsMap 参数
     * @param rowLength 列数
     * @return 列表
     * @throws Exception 
     * @author 周颖  
     * @date 2017年4月10日 下午2:23:41
     */
    List<Object[]> getExportList(Map<String, Object> paramsMap, Integer rowLength) throws Exception;

    /**
     * 商户重置密码
     * @param id 商户主键id
     * @author 周颖  
     * @date 2017年4月6日 下午4:31:50
     */
    void resetPassword(Long id);
}