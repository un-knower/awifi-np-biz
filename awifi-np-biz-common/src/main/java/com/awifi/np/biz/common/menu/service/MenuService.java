package com.awifi.np.biz.common.menu.service;

import java.util.List;

import com.awifi.np.biz.common.menu.model.Menu;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月7日 上午10:11:43
 * 创建作者：亢燕翔
 * 文件名称：MenuService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface MenuService {
    
    /**
     * 获取对应的菜单
     * @param sessionUser sessionUser
     * @param serviceCode 服务编号 
     * @return list
     * @author 亢燕翔  
     * @date 2017年2月7日 上午10:22:11
     */
    List<Menu> getListByParam(SessionUser sessionUser, String serviceCode);

    /**
     * 通过参数获取菜单
     * @param serviceCode 服务编号
     * @return 菜单集合
     * @author 许小满  
     * @date 2017年2月16日 下午2:40:04
     */
    List<Menu> getListByParam(String serviceCode);
    
    /**
     * 通过参数获取菜单
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @return 菜单集合
     * @author 许小满  
     * @date 2017年2月16日 下午2:40:04
     */
    List<Menu> getListByParam(String serviceCode, Long roleId);
    
    /**
     * 角色-菜单关系表  批量更新
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @param menuIds 菜单表主键id数组
     * @author 许小满  
     * @date 2017年2月16日 下午3:14:46
     */
    void batchAddRoleMenu(String serviceCode, Long roleId, Long[] menuIds);
}
