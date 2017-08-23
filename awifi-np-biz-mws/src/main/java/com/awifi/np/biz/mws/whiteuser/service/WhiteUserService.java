/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午5:03:37
* 创建作者：王冬冬
* 文件名称：WhileUserService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service;

import java.util.List;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.mws.whiteuser.model.WhiteUser;

public interface WhiteUserService {
	 /**
     * 判断手机号是否加白名单
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 王冬冬  
     * @date 2017年2月13日 上午11:20:35
     */
    boolean isCellphoneExist(Long merchantId, String cellphone);

    /**
     * 添加白名单
     * @param whiteUser 白名单
     * @author 王冬冬 
     * @throws Exception 异常
     * @date 2017年2月13日 下午1:45:57
     */
    void add(WhiteUser whiteUser) throws Exception;

    /**
     * 删除白名单
     * @param id 黑名单主键id
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年2月13日 下午1:58:53
     */
    void delete(Integer id) throws Exception;
    
    /**
     * 批量删除用户
     * @param ids 用户ids
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年2月10日 上午9:26:20
     */
    void batchDelete(String ids) throws Exception;

	/**
	 * @param page 页码
	 * @param merchantId 商户id
	 * @param keywords 关键词
	 * @author 王冬冬  
	 * @param sessionUser 用户session
	 * @throws Exception 异常
	 * @date 2017年4月21日 下午3:47:26
	 */
    void getListByParam(SessionUser sessionUser, Page<WhiteUser> page, Long merchantId, String keywords) throws Exception;
	/**
	 * 判断mac地址是否存在
	 * @param merchantId 商户id
	 * @param mac mac地址
	 * @return boolean
	 * @author 王冬冬  
	 * @date 2017年4月25日 上午9:53:48
	 */
    boolean isMacExist(Long merchantId, String mac);

    /**
     * 批量导入白名单
     * @param whiteUserList 白名单列表
     * @throws Exception 异常
     * @author 王冬冬  
     * @param merchantId 商户id
     * @date 2017年6月14日 下午1:38:25
     */
    void add(List<WhiteUser> whiteUserList, Long merchantId) throws Exception;
}
