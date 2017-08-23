/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 下午1:31:49
* 创建作者：尤小平
* 文件名称：MerchantManagerService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;

import java.util.List;

public interface MerchantManagerService {

    /**
     * 根据主键获取MerchantManager.
     * 
     * @param id 主键
     * @return MerchantManager
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:12:29
     */
    MerchantManager queryById(Long id) throws Exception;

    /**
     * 新增管理员.
     * 
     * @param merchantManager MerchantManager
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:13:33
     */
    boolean insert(MerchantManager merchantManager) throws Exception;

    /**
     * 根据主键修改管理员信息.
     * 
     * @param merchantManager MerchantManager
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:13:59
     */
    boolean update(MerchantManager merchantManager) throws Exception;

    /**
     * 根据主键删除管理员信息.
     * 
     * @param id 主键
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:14:16
     */
    boolean deleteById(Long id) throws Exception;

    /**
     * 获取管理员信息列表.
     *
     * @param merchantManager MerchantManager
     * @throws Exception 异常
     * @return List<MerchantManager>
     * @author 尤小平
     * @date 2017年4月5日 下午3:27:54
     */
    List<MerchantManager> getListByMerchantManager(MerchantManager merchantManager) throws Exception;

    /**
     * 获取管理员信息列表，分页
     * 
     * @param merchantManager MerchantManager
     * @param page Page
     * @return Page<MerchantManager>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:14:41
     */
    Page<MerchantManager> getListByParams(MerchantManager merchantManager, Page<MerchantManager> page) throws Exception;

    /**
     * 获取管理员信息列表总条数.
     * 
     * @param merchantManager MerchantManager
     * @return 总条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:15:21
     */
    int getCountByParams(MerchantManager merchantManager) throws Exception;
}
