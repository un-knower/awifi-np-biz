package com.awifi.np.biz.toe.admin.security.role.model;

import java.io.Serializable;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午2:11:42
 * 创建作者：周颖
 * 文件名称：ToeRole.java
 * 版本：  v1.0
 * 功能：菜单实体类
 * 修改记录：
 */
public class ToeRole implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 8164925186329559657L;

    /**角色id*/
    private Long id;
    /**角色名称*/
    private String roleName;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}