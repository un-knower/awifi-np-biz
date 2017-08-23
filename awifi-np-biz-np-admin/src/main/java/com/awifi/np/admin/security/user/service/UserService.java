package com.awifi.np.admin.security.user.service;

import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:51:30
 * 创建作者：周颖
 * 文件名称：UserService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface UserService {

    /**
     * 根据用户名名密码获取用户信息
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     * @author 周颖  
     * @date 2017年1月11日 下午8:03:24
     */
    User getByNameAndPwd(String userName,String password);
    
    /**
     * 补全用户信息
     * @param id 用户主键
     * @return 用户信息
     * @author 周颖  
     * @date 2017年2月23日 下午3:06:26
     */
    User getById(Long id);

    /**
     * 更新用户信息
     * @param user 用户
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午6:58:19
     */
    int updateById(User user);
    
    /**
     * 更新密码
     * @param id 用户id
     * @param password 密码
     * @author 许小满  
     * @date 2017年2月23日 下午3:04:42
     */
    void updatePwdById(Long id, String password);

    /**
     * 管理员账号列表
     * @param user 登陆账号
     * @param page 页面
     * @param roleId 角色id
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param userName 账号关键字
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月5日 上午10:09:12
     */
    void getListByParams(SessionUser user, Page<User> page, Long roleId, Long provinceId, Long cityId, Long areaId, String userName) throws Exception;

    /**
     * 判断管理员账号是否存在
     * @param userName 账号
     * @return true 存在
     * @author 周颖  
     * @date 2017年5月8日 下午1:54:23
     */
    boolean isUserNameExist(String userName);

    /**
     * 添加管理员
     * @param user 管理员
     * @author 周颖  
     * @date 2017年5月8日 下午2:06:24
     */
    void add(User user);

    /**
     * 修改管理员
     * @param user 管理员
     * @author 周颖  
     * @date 2017年5月8日 下午2:37:03
     */
    void update(User user);

    /**
     *管理员详情
     * @param id 主键id
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月8日 下午3:29:10
     */
    User getUserById(Long id) throws Exception;

    /**
     * 删除管理员
     * @param id 主键id
     * @author 周颖  
     * @date 2017年5月8日 下午7:15:50
     */
    void delete(Long id);

    /**
     * 重置密码
     * @param id 账号id
     * @author 周颖  
     * @date 2017年5月8日 下午7:20:10
     */
    void resetPassword(Long id);

    /**
     * 更新用户默认套码
     * @param userId 用户id
     * @param suitCode 套码
     * @author 周颖  
     * @date 2017年5月9日 下午3:04:32
     */
    void updateSuitById(Long userId, String suitCode);
}