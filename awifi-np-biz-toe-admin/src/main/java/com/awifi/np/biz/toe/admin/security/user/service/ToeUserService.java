package com.awifi.np.biz.toe.admin.security.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 上午9:40:36
 * 创建作者：周颖
 * 文件名称：ToeUserService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface ToeUserService {

    /**
     * 根据用户获取对应商户id
     * @param userName 账号
     * @return 商户id
     * @author 周颖  
     * @date 2017年2月3日 上午9:42:00
     */
    Long getMerIdByUserName(String userName);
    
    /**
     * 判断账号是否存在
     * @param userName 账号
     * @return true false
     * @author 周颖  
     * @date 2017年2月4日 上午11:17:42
     */
    boolean isUserNameExist(String userName);

    /**
     * 新建账号
     * @param user 账号
     * @return 账号id
     * @author 周颖  
     * @date 2017年2月4日 下午2:06:22
     */
    Long add(ToeUser user);

    /**
     * 维护账号和商户的关系
     * @param userId 账号id
     * @param merchantId 商户id
     * @author 周颖  
     * @date 2017年2月4日 下午2:25:33
     */
    void addUserMerchant(Long userId, Long merchantId);

    /**
     * 通过商户id查找账号
     * @param merchantId 商户id
     * @return 账号
     * @author 周颖  
     * @date 2017年2月6日 上午9:04:54
     */
    ToeUser getByMerchantId(Long merchantId);

    /**
     * 通过商户id修改账号信息
     * @param merchantId 商户id
     * @param user 账号
     * @return 账号id
     * @author 周颖  
     * @date 2017年2月7日 下午2:04:24
     */
    Long update(Long merchantId, ToeUser user);

    /**
     * 通过用户名密码查询用户
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     * @author 周颖  
     * @date 2017年4月5日 上午9:00:29
     */
    ToeUser getByNameAndPwd(String userName, String password);

    /**
     * 通过用户id获取用户详情
     * @param id 主键id
     * @return 用户详情
     * @author 周颖  
     * @date 2017年4月5日 上午10:30:14
     */
    ToeUser getById(Long id);

    /**
     * 更新用户信息
     * @param toeUser 用户
     * @return 是否保存成功
     * @author 周颖  
     * @date 2017年4月5日 上午11:24:30
     */
    int updateById(ToeUser toeUser);

    /**
     * 更新用户密码
     * @param id 用户主键id
     * @param password 用户密码
     * @author 周颖  
     * @date 2017年4月5日 下午1:54:14
     */
    void updatePwdById(Long id, String password);

    /**
     * 更新用户项目归属
     * @param merchantId 商户id
     * @param projectId 项目id
     * @author 周颖  
     * @date 2017年4月6日 下午1:09:27
     */
    void updateProject(Long merchantId, Long projectId);

    /**
     * 重置商户账号密码
     * @param merchantId 商户id 
     * @author 周颖  
     * @date 2017年4月6日 下午4:37:11
     */
    void resetPassword(Long merchantId);

    /**
     * 批量获取商户的账号
     * @param merchantIdList 商户ids
     * @return key商户id value 账号
     * @author 周颖  
     * @date 2017年4月10日 下午3:00:06
     */
    Map<Long, String> getNameByMerchantIds(List<Long> merchantIdList);

    /**
     * 根据用户名查询id和username
     * @param userNames 用户名
     * @return map
     * @author 王冬冬  
     * @date 2017年5月15日 下午2:15:47
     */
    Map<Long, String> getIdAndUserNameByUsernames(String userNames);

    /**根据商户账号集合查询id和username
     * @param userNamesSet 商户账号集合
     * @return map
     * @author 王冬冬  
     * @date 2017年5月16日 下午6:52:44
     */
    Map<String, Long> getUserNameAndIdByUsernames(Set<String> userNamesSet);

   
}