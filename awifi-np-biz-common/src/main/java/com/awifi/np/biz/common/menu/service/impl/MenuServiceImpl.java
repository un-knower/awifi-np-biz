package com.awifi.np.biz.common.menu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.menu.dao.MenuDao;
import com.awifi.np.biz.common.menu.model.Menu;
import com.awifi.np.biz.common.menu.service.MenuService;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.CastUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月7日 上午10:11:53
 * 创建作者：亢燕翔
 * 文件名称：MenuServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService{

    /**菜单持久层*/
    @Resource(name = "menuDao")
    private MenuDao menuDao;
    
    /**
     * 获取对应的菜单
     * @author 亢燕翔  
     * @date 2017年2月7日 上午10:22:39
     */
    @Override
    public List<Menu> getListByParam(SessionUser sessionUser, String serviceCode) {
        String roleIds = sessionUser.getRoleIds();
        String[] roleIdStrArray = roleIds.split(",");
        Long[] roleIdLongArray = CastUtil.toLongArray(roleIdStrArray);
        return getListByParam(serviceCode, null , roleIdLongArray);
    }
    
    /**
     * 通过服务编号获取菜单集合
     * @param serviceCode 服务编号
     * @return 菜单集合
     * @author 许小满  
     * @date 2017年2月16日 下午2:40:04
     */
    public List<Menu> getListByParam(String serviceCode){
        return getListByParam(serviceCode, null , null);
    }
    
    /**
     * 通过服务编号、角色id获取菜单集合
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @return 菜单集合
     * @author 许小满  
     * @date 2017年2月16日 下午2:40:04
     */
    public List<Menu> getListByParam(String serviceCode, Long roleId){
        return getListByParam(serviceCode, roleId , null);
    }
    /**
     * 通过参数获取菜单
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @param roleIds 角色ids
     * @return 菜单集合
     * @author 许小满  
     * @date 2017年2月16日 下午2:40:04
     */
    private List<Menu> getListByParam(String serviceCode, Long roleId, Long[] roleIds){
        /*1：获取一级菜单*/
        List<Menu> priMenuList = menuDao.getListByParam(serviceCode, roleId, roleIds, null);//返回的结果list
        if(priMenuList.isEmpty()){//如果该list为空，则直接return
            return priMenuList;
        }
        /*2：获取二级菜单 */
        /* 暂时屏蔽二级菜单，目前前端无法支持二级菜单，提升性能 --许小满 2017-08-16 注释
        Long id = null;
        int maxSize = priMenuList.size();
        Menu priMenu = null;//一级菜单
        for(int i=0; i<maxSize; i++){
            priMenu = priMenuList.get(i);//一级菜单
            id = priMenu.getId();//主键id
            priMenu.setSubMenus(menuDao.getListByParam(serviceCode, roleId, roleIds,id));
        }*/
        return priMenuList;
    }

    /**
     * 角色-菜单关系表  批量更新
     * @param serviceCode 服务编号
     * @param roleId 角色id
     * @param menuIds 菜单表主键id数组
     * @author 许小满  
     * @date 2017年2月16日 下午3:14:46
     */
    public void batchAddRoleMenu(String serviceCode, Long roleId, Long[] menuIds){
        //1.通过角色id删除角色菜单关系表的数据
        menuDao.deleteRoleMenuByRoleId(serviceCode, roleId);
        //2.批量更新角色-菜单关系表
        menuDao.batchAddRoleMenu(roleId, menuIds);
    }
}
