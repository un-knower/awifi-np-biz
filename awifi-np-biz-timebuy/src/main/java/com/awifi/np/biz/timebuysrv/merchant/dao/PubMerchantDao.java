/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午11:10:40
* 创建作者：尤小平
* 文件名称：PubMerchantDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao;

import com.awifi.np.biz.timebuysrv.merchant.model.PubMerchant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

@Service(value = "pubMerchantDao")
public interface PubMerchantDao {
    /**
     * 根据Id查询一条数据.
     * 
     * @param id id
     * @return PubMerchant
     * @author 尤小平  
     * @date 2017年5月9日 下午4:54:03
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userId", column = "userId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantName", column = "merchantName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantType", column = "merchantType", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cascadeLabel", column = "cascade_label", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "cascadeLevel", column = "cascade_level", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "parentId", column = "parentId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "parentName", column = "parentName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "account", column = "account", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "roleIds", column = "roleIds", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "roleNames", column = "roleNames", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contact", column = "contact", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "contactWay", column = "contactWay", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "projectId", column = "projectId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "projectName", column = "projectName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "priIndustryCode", column = "priIndustryCode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "priIndustry", column = "priIndustry", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "secIndustryCode", column = "secIndustryCode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "secIndustry", column = "secIndustry", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "provinceId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "province", column = "province", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "cityId", column = "cityId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "city", column = "city", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "areaId", column = "areaId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "area", column = "area", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "locationFullName", column = "locationFullName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "address", column = "address", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "storeType", column = "storeType", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "storeLevel", column = "storeLevel", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "storeStar", column = "storeStar", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "storeScope", column = "storeScope", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "connectType", column = "connectType", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "createDate", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "updateDate", column = "updateDate", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER) })
    @Select("select id,userId,merchantName,merchantType,cascadeLabel,cascadeLevel,parentId,parentName,account,roleIds,roleNames,contact,contactWay," +
            "projectId,projectName,priIndustryCode,priIndustry,secIndustryCode,secIndustry,provinceId,province,cityId,city,areaId,area,locationFullName," +
            "address,storeType,storeLevel,storeStar,storeScope,connectType,remark,createDate,updateDate,status from merchant where id=#{id} limit 1")
    PubMerchant selectByPrimaryKey(Long id);
    
    /**
     * 插入一条数据.
     * 
     * @param merchant PubMerchant
     * @return 成功条数
     * @author 尤小平  
     * @date 2017年5月9日 下午4:54:08
     */
    @Insert("insert into merchant(id,userId,merchantName,merchantType,cascadeLabel,cascadeLevel,parentId,parentName,account,roleIds,roleNames,contact,contactWay," +
            "projectId,projectName,priIndustryCode,priIndustry,secIndustryCode,secIndustry,provinceId,province,cityId,city,areaId,area,locationFullName," +
            "address,storeType,storeLevel,storeStar,storeScope,connectType,remark,createDate,updateDate,status) values (#{id},#{userId},#{merchantName},#{merchantType}," +
            "#{cascadeLabel},#{cascadeLevel},#{parentId},#{parentName},#{account},#{roleIds},#{roleNames},#{contact},#{contactWay},#{projectId},#{projectName}," +
            "#{priIndustryCode},#{priIndustry},#{secIndustryCode},#{secIndustry},#{provinceId},#{province},#{cityId},#{city},#{areaId},#{area},#{locationFullName}," +
            "#{address},#{storeType},#{storeLevel},#{storeStar},#{storeScope},#{connectType},#{remark},#{createDate},#{updateDate},#{status})")
    int insertSelective(PubMerchant merchant);
}
