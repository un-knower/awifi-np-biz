/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年6月13日 下午8:50:39
 * 创建作者：尤小平
 * 文件名称：StationAppMerchantRelationDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.mws.merchant.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.mws.merchant.app.dao.sql.StationAppMerchantRelationSql;
import com.awifi.np.biz.mws.merchant.app.model.StationAppMerchantRelation;

@Service(value = "stationAppMerchantRelationDao")
public interface StationAppMerchantRelationDao {
    /**
     * 删除商户应用.
     * 
     * @param relation 商户应用
     * @return 成功条数
     * @author 尤小平  
     * @date 2017年6月15日 下午4:39:44
     */
    @DeleteProvider(type = StationAppMerchantRelationSql.class, method = "delete")
    int delete(StationAppMerchantRelation relation);

    /**
     * 查询商户应用.
     * 
     * @param relation 商户应用
     * @return 商户应用列表
     * @author 尤小平  
     * @date 2017年6月15日 下午4:40:24
     */
    @SelectProvider(type = StationAppMerchantRelationSql.class, method = "select")
    List<StationAppMerchantRelation> select(StationAppMerchantRelation relation);

    /**
     * 新增商户应用.
     * 
     * @param relation 商户应用
     * @return 成功条数
     * @author 尤小平  
     * @date 2017年6月15日 下午4:40:28
     */
    @InsertProvider(type = StationAppMerchantRelationSql.class, method = "insert")
    int insertSelective(StationAppMerchantRelation relation);

    /**
     * 更新商户应用.
     * 
     * @param relation 商户应用
     * @return 成功条数
     * @author 尤小平  
     * @date 2017年6月15日 下午4:40:33
     */
    @UpdateProvider(type = StationAppMerchantRelationSql.class, method = "update")
    int updateByPrimaryKeySelective(StationAppMerchantRelation relation);
    
    /**
     * 获取应用数量
     * @param merchantId 商户id
     * @param appId 应用id
     * @param status 状态
     * @return 记录数
     * @author 许小满  
     * @date 2017年6月19日 下午7:54:07
     */
    @Select("select count(id) from station_app_merchant_relation where merchantid=#{merchantId} and appid=#{appId} and status=#{status}")
    int getNumByMerIdAndAppId(@Param("merchantId")Long merchantId, @Param("appId")Long appId, @Param("status")int status);
}
