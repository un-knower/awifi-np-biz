/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 上午11:18:04
* 创建作者：尤小平
* 文件名称：MerchantManagerDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantExtends;

@Service(value = "merchantExtendsDao")
public interface MerchantExtendsDao {

    /**
     * 根据主键获取MerchantManager.
     * 
     * @param id
     *            主键
     * @return MerchantManager
     * @author 尤小平
     * @date 2017年4月27日 下午8:12:29
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),

            @Result(property = "buyout", column = "buyout", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
    @Select("select id,buyout from merchant_extends where id=#{id}")
    MerchantExtends selectByPrimaryKey(Long id);
}
