package com.awifi.np.biz.common.menu.model;

import java.io.Serializable;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月7日 上午10:19:15
 * 创建作者：亢燕翔
 * 文件名称：Menu.java
 * 版本：  v1.0
 * 功能：  菜单实体类
 * 修改记录：
 */
public class Menu implements Serializable{

    /**序列化*/
    private static final long serialVersionUID = 7768761878765444508L;
    
    /**菜单id*/
    private Long id;
    
    /**菜单名称*/
    private String menuName;
    
    /**菜单url*/
    private String menuUrl;
    
    /**目标显示区域*/
    private String targetId;
    
    /**下级菜单*/
    private List<Menu> subMenus;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuUrl() {
        return menuUrl;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    public String getTargetId() {
        return targetId;
    }
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    public List<Menu> getSubMenus() {
        return subMenus;
    }
    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

}
