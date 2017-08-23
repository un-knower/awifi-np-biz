package com.awifi.np.biz.api.client.dbcenter.merchant.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月24日 上午8:53:43
 * 创建作者：周颖
 * 文件名称：MerchantApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface MerchantApiService {

    /**
     * 商户列表总数
     * @param paramsMap 参数
     * @return 符合条件的总条数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 上午9:04:25
     */
    int getCountByParam(Map<String,Object> paramsMap) throws Exception;
    
    /**
     * 商户列表
     * @param paramsMap 参数
     * @return 符合条件的记录
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 上午9:04:25
     */
    List<Merchant> getListByParam(Map<String,Object> paramsMap) throws Exception;

    /**
     * 获取商户详情
     * @param merchantId 商户id
     * @return 商户详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月4日 上午9:04:47
     */
    Merchant getById(Long merchantId) throws Exception;

    /**
     * 添加商户
     * @param merchant 商户
     * @param industryCode 行业标签
     * @return 商户id
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年2月4日 下午2:36:50
     */
    Long add(Merchant merchant, String industryCode) throws Exception;

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
     * 批量添加商户
     * @param parentId 父商户id
     * @param merchantList 商户列表
     * @return 结果（账号和商户id的对应关系）
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月7日 上午8:41:17
     */
    Map<String, Long> batchAdd(Long parentId,List<Map<String, Object>> merchantList) throws Exception;
}