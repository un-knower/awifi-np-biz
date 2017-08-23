/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月4日 下午4:45:45
* 创建作者：尤小平
* 文件名称：SysUserSqlTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.user.dao.sql;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.awifi.np.biz.timebuysrv.user.model.SysUser;

public class SysUserSqlTest {
    /**
     * 被测试类
     */
    private SysUserSql sql;
    
    /**
     * SysUser
     */
    private SysUser sysUser;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月4日 下午4:56:01
     */
    @Before
    public void setUp() throws Exception {
        sql = new SysUserSql();
        sysUser = new SysUser();
        sysUser.setId(1L);
        sysUser.setFace("face");
        sysUser.setNkname("nkname");
        sysUser.setSex(2);
        sysUser.setBirth(new Date());
        sysUser.setAddress("address");
        sysUser.setOutid(10L);
        sysUser.setTelno("telno");
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月4日 下午4:56:11
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试插入sql.
     * 
     * @author 尤小平  
     * @date 2017年5月4日 下午4:56:20
     */
    @Test
    public void testInsert() {
        String actual = sql.insert(sysUser);
        String expected = "insert into sys_user(face,nkname,sex,birth,address,outid,telno) values (#{face},#{nkname},#{sex},#{birth},#{address},#{outid},#{telno})";
        Assert.assertEquals(expected, actual);
    }

    /**
     * 测试修改sql.
     * 
     * @author 尤小平  
     * @date 2017年5月4日 下午4:56:25
     */
    @Test
    public void testUpdate() {
        String actual = sql.update(sysUser);
        String expected = "update sys_user set face=#{face}, nkname=#{nkname}, sex=#{sex}, birth=#{birth}, address=#{address}, telno=#{telno} where outid=#{outid}";
        Assert.assertEquals(expected, actual);
    }

    /**
     * 测试修改sql.
     * 
     * @author 尤小平  
     * @date 2017年5月4日 下午4:56:29
     */
    @Test
    public void testUpdateForNull() {
        sysUser = new SysUser();
        String actual = sql.update(sysUser);
        Assert.assertNull(actual);
    }
}
