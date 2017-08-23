/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:48:21
* 创建作者：周颖
* 文件名称：SiteDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.site.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.portal.site.dao.sql.SiteSql;
import com.awifi.np.biz.toe.portal.site.model.Site;
import com.awifi.np.biz.toe.portal.site.model.SitePage;
import com.awifi.np.biz.toe.portal.site.model.SitePageComponent;

@Service("siteDao")
public interface SiteDao {

    /**
     * 默认站点总数
     * @param keywords 站点名称关键字
     * @return 总数
     * @author 周颖  
     * @date 2017年4月17日 下午3:57:56
     */
    @SelectProvider(type=SiteSql.class,method="getDefaultCountByParam")
    int getDefaultCountByParam(@Param("keywords")String keywords);

    /**
     * 默认站点列表
     * @param keywords 站点名称关键字
     * @param begin 开始条数
     * @param pageSize 一页大小
     * @return 列表
     * @author 周颖  
     * @date 2017年4月18日 上午8:46:54
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=SiteSql.class,method="getDefaultListByParam")
    List<Site> getDefaultListByParam(@Param("keywords")String keywords,@Param("begin")Integer begin,@Param("pageSize") Integer pageSize);

    /**
     * 地区站点总数
     * @param keywords 站点名称关键字
     * @param provinceId 省id
     * @param cityId 市id
     * @return 总数
     * @author 周颖  
     * @date 2017年4月18日 上午9:33:50
     */
    @SelectProvider(type=SiteSql.class,method="getLocationCountByParam")
    int getLocationCountByParam(@Param("keywords")String keywords,@Param("provinceId") Long provinceId,@Param("cityId") Long cityId);

    /**
     * 地区站点列表
     * @param keywords 站点名称关键字
     * @param provinceId 省id
     * @param cityId 市id
     * @param begin 开始条数
     * @param pageSize 页面大小
     * @return 列表
     * @author 周颖  
     * @date 2017年4月18日 上午9:35:18
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=SiteSql.class,method="getLocationListByParam")
    List<Site> getLocationListByParam(@Param("keywords")String keywords,@Param("provinceId") Long provinceId,
            @Param("cityId") Long cityId,@Param("begin")Integer begin,@Param("pageSize") Integer pageSize);

    /**
     * 行业站点总数
     * @param keywords 站点名称关键字
     * @param priIndustryCode 一级行业
     * @param secIndustryCode 二级行业
     * @return 总数
     * @author 周颖  
     * @date 2017年4月18日 上午11:04:44
     */
    @SelectProvider(type=SiteSql.class,method="getIndustryCountByParam")
    int getIndustryCountByParam(@Param("keywords")String keywords,@Param("priIndustryCode") String priIndustryCode,@Param("secIndustryCode")String secIndustryCode);

    /**
     * 行业站点列表
     * @param keywords 站点名称关键字
     * @param priIndustryCode 一级行业
     * @param secIndustryCode 危机行业
     * @param begin 开始条数
     * @param pageSize 页面大小
     * @return 列表
     * @author 周颖  
     * @date 2017年4月18日 下午1:21:50
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "priIndustryCode", column = "pri_industry_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "secIndustryCode", column = "sec_industry_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=SiteSql.class,method="getIndustryListByParam")
    List<Site> getIndustryListByParam(@Param("keywords")String keywords,@Param("priIndustryCode") String priIndustryCode,
            @Param("secIndustryCode")String secIndustryCode, @Param("begin")Integer begin, @Param("pageSize")Integer pageSize);

    /**
     * 商户站点总数
     * @param keywords 站点名称关键字
     * @param siteId 站点id
     * @param merchantId 商户id
     * @param status 状态
     * @param cascadeLabel 层级
     * @param merchantIds 管理的商户ids
     * @return 总数
     * @author 周颖  
     * @date 2017年4月19日 上午10:19:54
     */
    @SelectProvider(type=SiteSql.class,method="getCountByParam")
    int getCountByParam(@Param("keywords")String keywords,@Param("siteId") Long siteId,@Param("merchantId") Long merchantId,
            @Param("status") Integer status,@Param("cascadeLabel")String cascadeLabel,@Param("merchantIds") Long[] merchantIds);
    
    /**
     * 商户站点列表
     * @param keywords 站点名关键字
     * @param siteId 站点id
     * @param merchantId 商户名称
     * @param status 状态
     * @param cascadeLabel 层级
     * @param merchantIds 管理的商户ids
     * @param begin 开始条数
     * @param pageSize 页面大小
     * @return 列表
     * @author 周颖  
     * @date 2017年4月19日 上午10:33:31
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=SiteSql.class,method="getListByParam")
    List<Site> getListByParam(@Param("keywords")String keywords,@Param("siteId") Long siteId,@Param("merchantId") Long merchantId, @Param("status") Integer status,
            @Param("cascadeLabel")String cascadeLabel,@Param("merchantIds") Long[] merchantIds, @Param("begin")Integer begin, @Param("pageSize")Integer pageSize);

    /**
     * 获取地区站点
     * @param provinceId 省id
     * @param cityId 市id
     * @return 站点
     * @author 周颖  
     * @date 2017年4月20日 上午10:39:34
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,site_name,thumb,status,province_id,city_id,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date from toe_portal_site "
            + "where delete_flag=1 and default_site=3 and province_id=#{provinceId} and city_id=#{cityId} limit 1")
    Site getLocationSite(@Param("provinceId")Long provinceId,@Param("cityId") Long cityId);

    /**
     * 获取行业站点
     * @param priIndustryCode 一级行业编号
     * @param secIndustryCode 二级行业编号
     * @return 站点
     * @author 周颖  
     * @date 2017年4月20日 上午11:14:07
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "priIndustryCode", column = "pri_industry_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "secIndustryCode", column = "sec_industry_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,site_name,thumb,status,pri_industry_code,sec_industry_code,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date from toe_portal_site "
            + "where delete_flag=1 and default_site=2 and pri_industry_code=#{priIndustryCode} and sec_industry_code=#{secIndustryCode} limit 1")
    Site getIndustrySite(@Param("priIndustryCode")String priIndustryCode,@Param("secIndustryCode") String secIndustryCode);

    /***
     * 获取默认站点
     * @return 默认站点
     * @author 周颖  
     * @date 2017年4月20日 上午11:17:58
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "thumb", column = "thumb", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,site_name,thumb,status,from_unixtime(create_date,'%Y-%m-%d %H:%i:%S') as create_date from toe_portal_site "
            + "where delete_flag=1 and default_site=1 limit 1")
    Site getDefaultSite();

    /**
     * 默认站点条数
     * @return 总数
     * @author 周颖  
     * @date 2017年4月21日 下午2:10:22
     */
    @Select("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=1")
    int getDefaultSiteNum();
    
    /**
     * 一级行业站点条数
     * @param priIndustryCode 一级行业编号
     * @return 总数
     * @author 周颖  
     * @date 2017年4月24日 上午9:37:28
     */
    @Select("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=2 and pri_industry_code=#{priIndustryCode} and sec_industry_code is null")
    int getPriIndustrySiteNum(@Param("priIndustryCode")String priIndustryCode);
    
    /**
     * 一级行业站点条数
     * @param id 主键id
     * @param priIndustryCode 一级行业编号
     * @return 总数
     * @author 周颖  
     * @date 2017年4月25日 下午2:51:16
     */
    @Select("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=2 and pri_industry_code=#{priIndustryCode} and sec_industry_code is null and pk_id!=#{id}")
    int getPriIndustrySiteNumById(@Param("id")Long id,@Param("priIndustryCode")String priIndustryCode);
   
    /**
     * 二级行业站点条数
     * @param secIndustryCode 二级行业编号
     * @return 总数
     * @author 周颖  
     * @date 2017年4月25日 上午9:47:26
     */
    @Select("select count(pk_id) from toe_portal_site where default_site=2 and sec_industry_code=#{secIndustryCode} and delete_flag=1")
    int getSecIndustrySiteNum(@Param("secIndustryCode")String secIndustryCode);
    
    /**
     * 二级行业站点条数
     * @param id 站点id
     * @param secIndustryCode 二级行业编号
     * @return 总数
     * @author 周颖  
     * @date 2017年4月25日 下午2:52:24
     */
    @Select("select count(pk_id) from toe_portal_site where default_site=2 and sec_industry_code=#{secIndustryCode} and delete_flag=1 and pk_id!=#{id}")
    int getSecIndustrySiteNumById(@Param("id")Long id,@Param("secIndustryCode")String secIndustryCode);
    
    /**
     * 省站点总数
     * @param provinceId 省id
     * @return 总数
     * @author 周颖  
     * @date 2017年4月24日 上午9:45:05
     */
    @Select("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=3 and province_id=#{provinceId} and city_id is null")
    int getProvinceSiteNum(@Param("provinceId")Long provinceId);
    
    /**
     * 省站点总数 排除自己
     * @param id 站点id
     * @param provinceId 省id
     * @return 总数
     * @author 周颖  
     * @date 2017年4月25日 下午2:35:01
     */
    @Select("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=3 and province_id=#{provinceId} and city_id is null and pk_id!=#{id}")
    int getProvinceSiteNumById(@Param("id")Long id,@Param("provinceId")Long provinceId);
    
    /**
     * 市站点总数
     * @param cityId 市id
     * @return 总数
     * @author 周颖  
     * @date 2017年4月24日 上午9:45:05
     */
    @Select("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=3 and city_id=#{cityId}")
    int getCitySiteNum(@Param("cityId") Long cityId);
    
    /**
     * 市站点总数 排除自己
     * @param id 站点id
     * @param cityId 市id
     * @return 总数
     * @author 周颖  
     * @date 2017年4月25日 下午2:33:30
     */
    @Select("select count(pk_id) from toe_portal_site where delete_flag=1 and default_site=3 and city_id=#{cityId} and pk_id!=#{id}")
    int getCitySiteNumById(@Param("id")Long id,@Param("cityId") Long cityId);
    
    /**
     * 商户下站点名称数量
     * @param siteName 站点名称
     * @param merchantId 商户id
     * @return 总数
     * @author 周颖  
     * @date 2017年4月25日 下午2:10:37
     */
    @Select("select count(pk_id) from toe_portal_site where fk_customer_id=#{merchantId} and site_name=#{siteName} and delete_flag=1")
    int getSiteNameNum(@Param("siteName")String siteName,@Param("merchantId") Long merchantId);
    
    /**
     * 新建站点 
     * @param site 站点
     * @return 站点主键id
     * @author 周颖  
     * @date 2017年4月24日 上午9:01:31
     */
    @SelectKey(before=false,keyProperty="id",resultType=Long.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    @Insert("insert into toe_portal_site (site_name,pri_industry_code,sec_industry_code,province_id,city_id,thumb,status,remark,create_date,update_date,fk_customer_id,cascade_label,default_site)"
            + " values(#{siteName},#{priIndustryCode},#{secIndustryCode},#{provinceId},#{cityId},'',#{status},#{remark},unix_timestamp(now()),unix_timestamp(now()),#{merchantId},#{cascadeLabel},#{defaultSite})")
    Long add(Site site);

    /**
     * 更新站点状态
     * @param siteId 站点id
     * @param status 状态o
     * @author 周颖  
     * @date 2017年4月24日 上午9:23:40
     */
    @Update("update toe_portal_site set status=#{status} where pk_id=#{siteId}")
    void updateStatusById(@Param("siteId")Long siteId,@Param("status")Integer status);

    /**
     * 通过pageId获取站点页面路径
     * @param pageId pageId
     * @return 站点页面路径
     * @author 周颖  
     * @date 2017年4月24日 上午11:02:23
     */
    @Select("select page_path from toe_portal_site_page where pk_id=#{pageId}")
    String getPagePath(@Param("pageId")Long pageId);

    /**
     * 删除站点页的组件
     * @param pageId 站点页主键id
     * @author 周颖  
     * @date 2017年4月24日 上午11:13:20
     */
    @Delete("delete from toe_portal_site_page_component where fk_site_page_id=#{pageId}")
    void deletePageComponent(@Param("pageId")Long pageId);

    /**
     * 删除站点页
     * @param pageId 站点页主键id
     * @author 周颖  
     * @date 2017年4月24日 上午11:14:46
     */
    @Delete("delete from toe_portal_site_page where pk_id=#{pageId}")
    void deletePage(@Param("pageId")Long pageId);

    /**
     * 更新站点页的序号
     * @param pageId 站点页主键id
     * @param num 序号
     * @author 周颖  
     * @date 2017年4月24日 上午11:24:02
     */
    @Update("update toe_portal_site_page set num=#{num} where pk_id=#{pageId}")
    void updatePageNum(@Param("pageId")Long pageId,@Param("num") Integer num);

    /**
     * 新建站点页面
     * @param sitePage 站点页面
     * @author 周颖  
     * @date 2017年4月24日 下午2:26:26
     */
    @SelectKey(before=false,keyProperty="pageId",resultType=Long.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS pageId")
    @Insert("insert into toe_portal_site_page (page_type,num,page_path,create_date,update_date,fk_site_id) values (#{pageType},#{num},#{pagePath},unix_timestamp(now()),unix_timestamp(now()),#{siteId})")
    void insertSitePage(SitePage sitePage);
    
    /**
     * 删除站点页组件
     * @param id 站点页组件id
     * @author 周颖  
     * @date 2017年4月24日 下午3:37:50
     */
    @Delete("delete from toe_portal_site_page_component where id=#{id} ")
    void deletePageComponentById(@Param("id")Long id);
    
    /**
     * 新建站点页面组件
     * @param spc 页面组件
     * @author 周颖  
     * @date 2017年4月24日 下午3:50:01
     */
    @SelectKey(before=false,keyProperty="sitePageComponentId",resultType=Long.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS sitePageComponentId")
    @Insert("insert into toe_portal_site_page_component (data_json,order_no,create_date,update_date,fk_site_page_id,fk_component_id,cascade_label) "
            + "values (#{json},#{orderNo},unix_timestamp(now()),unix_timestamp(now()),#{sitePageId},#{componentId},'')")
    void insertSitePageComp(SitePageComponent spc);
    
    /**
     * 修改站点页面组件
     * @param json 组件json
     * @param orderNo 排序号
     * @param id 站点页面组件id
     * @author 周颖  
     * @date 2017年4月24日 下午4:20:29
     */
    @Update("update toe_portal_site_page_component set order_no=#{orderNo},data_json=#{json} where id=#{id}")
    void updateSitePageComp(@Param("json")String json,@Param("orderNo")Integer orderNo,@Param("id") Long id);

    /**
     * 修改默认站点名称
     * @param id 主键id
     * @param siteName 站点名称
     * @author 周颖  
     * @date 2017年4月25日 下午1:32:12
     */
    @Update("update toe_portal_site set site_name=#{siteName},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void updateSiteName(@Param("id")Long id,@Param("siteName") String siteName);

    /**
     * 编辑地区站点
     * @param id 主键id
     * @param siteName 站点名称
     * @param provinceId 省id
     * @param cityId 区id
     * @author 周颖  
     * @date 2017年4月25日 下午2:40:57
     */
    @Update("update toe_portal_site set site_name=#{siteName},province_id=#{provinceId},city_id=#{cityId},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void updateLocation(@Param("id")Long id,@Param("siteName") String siteName,@Param("provinceId") Long provinceId,@Param("cityId") Long cityId);
    
    /**
     * 编辑行业站点
     * @param id 站点id
     * @param siteName 站点名
     * @param priIndustryCode 一级行业编号
     * @param secIndustryCode 二级行业编号
     * @author 周颖  
     * @date 2017年4月25日 下午2:59:29
     */
    @Update("update toe_portal_site set site_name=#{siteName},pri_industry_code=#{priIndustryCode},"
            + "sec_industry_code=#{secIndustryCode},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void updateIndustry(@Param("id")Long id,@Param("siteName") String siteName,
             @Param("priIndustryCode") String priIndustryCode,@Param("secIndustryCode") String secIndustryCode);
   
    /**
     * 获取站点类型
     * @param id 站点id
     * @return 类型
     * @author 周颖  
     * @date 2017年4月25日 下午1:49:15
     */
    @Select("select default_site from toe_portal_site where pk_id=#{id}")
    Integer getType(@Param("id")Long id);

    /**
     * 更新站点页面组件cascade_label字段
     * @param spcId 站点页面组件id
     * @param cascadeLabel 层级
     * @author 周颖  
     * @date 2017年4月25日 下午5:23:43
     */
    @Update("update toe_portal_site_page_component set cascade_label=#{cascadeLabel} where id=#{spcId}")
    void updateCascadeLabel(@Param("spcId")Long spcId, @Param("cascadeLabel")String cascadeLabel);

    /**
     * 逻辑删除站点
     * @param id 站点id
     * @author 周颖  
     * @date 2017年4月25日 下午7:50:01
     */
    @Update("update toe_portal_site set delete_flag=-1 where pk_id=#{id}")
    void delete(@Param("id")Long id);

    /**
     * 站点详情
     * @param id 站点id
     * @return 详情
     * @author 周颖  
     * @date 2017年4月26日 下午1:43:01
     */
    @Results(value = {
            @Result(property = "siteName", column = "site_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "priIndustryCode", column = "pri_industry_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "secIndustryCode", column = "sec_industry_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceId", column = "province_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cityId", column = "city_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    @Select("select site_name,pri_industry_code,sec_industry_code,province_id,city_id,fk_customer_id from toe_portal_site where pk_id=#{id}")
    Site getById(@Param("id")Long id);

    /**
     * 站点页面列表
     * @param id 站点页面
     * @return 站点页面
     * @author 周颖  
     * @date 2017年4月26日 下午2:08:03
     */
    @Results(value = {
            @Result(property = "pageId", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pageType", column = "page_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "num", column = "num", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
            })
    @Select("select pk_id,page_type,num from toe_portal_site_page where fk_site_id=#{id} order by num")
    List<SitePage> getSitePageList(@Param("id")Long id);

    /**
     * 站点页面组件列表
     * @param sitePageId 站点页主键
     * @return 组件列表
     * @author 周颖  
     * @date 2017年4月26日 下午2:18:13
     */
    @Results(value = {
            @Result(property = "sitePageComponentId", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "json", column = "data_json", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "orderNo", column = "order_no", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "componentId", column = "fk_component_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    @Select("select id,data_json,order_no,fk_component_id from toe_portal_site_page_component where fk_site_page_id=#{sitePageId} order by order_no")
    List<SitePageComponent> getSitePageComponentList(@Param("sitePageId")Long sitePageId);

    /**
     * 通过站点id获取商户信息
     * @param siteId 站点id
     * @return 商户信息
     * @author 周颖  
     * @date 2017年4月28日 下午2:09:12
     */
    @Results(value = {
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cascadeLabel", column = "cascade_label", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select fk_customer_id,cascade_label from toe_portal_site where pk_id=#{siteId}")
    Map<String, Object> getMerchantById(@Param("siteId")Long siteId);
    
    /**
     * 获取区域站点id（省）
     * @param provinceId 省id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:23:51
     */
    @Select("select pk_id from toe_portal_site where province_id=#{provinceId} and city_id is null and default_site=3 and delete_flag=1 order by pk_id desc limit 1")
    Long getProvinceSitId(Long provinceId);
    
    /**
     * 获取区域站点id（市）
     * @param cityId 市id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:23:51
     */
    @Select("select pk_id from toe_portal_site where city_id=#{cityId} and default_site=3 and delete_flag=1 order by pk_id desc limit 1")
    Long getCitySitId(Long cityId);
    
    /**
     * 获取行业站点id（一级行业）
     * @param priIndustryCode 一级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:23:51
     */
    @Select("select pk_id from toe_portal_site where pri_industry_code=#{priIndustryCode} and sec_industry_code is null and default_site=2 and delete_flag=1 order by pk_id desc limit 1")
    Long getPriIndustrySitId(String priIndustryCode);
    
    /**
     * 获取行业站点id（二级行业）
     * @param secIndustryCode 二级行业编号
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月15日 上午12:23:51
     */
    @Select("select pk_id from toe_portal_site where sec_industry_code=#{secIndustryCode} and default_site=2 and delete_flag=1 order by pk_id desc limit 1")
    Long getSecIndustrySitId(String secIndustryCode);
    
    /**
     * 获取默认站点id
     * @return 默认站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:36:46
     */
    @Select("select pk_id from toe_portal_site where default_site=1 and delete_flag=1 order by pk_id desc limit 1")
    Long getDefaultSitId();
    
    /**
     * 获取站点名称
     * @param siteId  站点id
     * @return 站点名称
     * @author 许小满  
     * @date 2017年5月11日 下午8:16:50
     */
    @Select("select site_name from toe_portal_site where pk_id=#{siteId}")
    String getSiteName(Long siteId);
    
    /**
     * 获取站点首页
     * @param siteId 站点id
     * @return 站点页面
     * @author 许小满  
     * @date 2017年5月11日 下午8:44:18
     */
    @Results(value = {
            @Result(property = "pageId", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pagePath", column = "page_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "pageType", column = "page_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "num", column = "num", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
            })
    @Select("select pk_id,page_path,page_type,num from toe_portal_site_page where fk_site_id=#{siteId} order by page_type asc,num asc limit 1")
    SitePage getFirstPage(Long siteId);
    
    /**
     * 获取站点下一页
     * @param siteId 站点id
     * @param pageType 页面类型
     * @param num 序号
     * @return 站点页面
     * @author 许小满  
     * @date 2017年5月11日 下午8:20:18
     */
    @Results(value = {
            @Result(property = "pageId", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pagePath", column = "page_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "pageType", column = "page_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "num", column = "num", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
            })
    @Select("select pk_id,page_path,page_type,num from toe_portal_site_page where fk_site_id=#{siteId} and ((page_type=#{pageType} and num>#{num}) or page_type>#{pageType}) order by page_type asc,num asc limit 1")
    SitePage getNextPage(@Param("siteId")Long siteId, @Param("pageType")Integer pageType, @Param("num")Integer num);

    /**
     * 获取站点上一页
     * @param id 站点id
     * @param pageType 页面类型
     * @param num 序号
     * @return 站点页面
     * @author 周颖  
     * @date 2017年5月12日 上午10:14:04
     */
    @Results(value = {
            @Result(property = "pageId", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pageType", column = "page_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "num", column = "num", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
            })
    @Select("select pk_id,page_type,num from toe_portal_site_page where fk_site_id=#{id} and ((page_type=#{pageType} and num<#{num}) or page_type<#{pageType}) order by page_type desc,num desc limit 1")
    SitePage getPrevSitePage(@Param("id")Long id,@Param("pageType")Integer pageType, @Param("num")Integer num);

    /**
     * 站点末页id
     * @param siteId 站点id
     * @return 末页id
     * @author 周颖  
     * @date 2017年5月2日 下午5:07:29
     */
    @Select("select pk_id from toe_portal_site_page where fk_site_id=#{siteId} order by page_type desc,num desc limit 1")
    Long getLastPageId(@Param("siteId")Long siteId);

    /**
     * 获取站点缩略图路径
     * @param id 站点id
     * @return 结果
     * @author 周颖  
     * @date 2017年5月24日 下午2:40:00
     */
    @Select("select thumb from toe_portal_site where pk_id=#{id}")
    String getThumbPath(@Param("id")Long id);

    /**
     * 保存站点缩略图路径
     * @param id 站点id
     * @param thumbPath 缩略图路径
     * @author 周颖  
     * @date 2017年6月5日 上午11:08:51
     */
    @Update("update toe_portal_site set thumb=#{thumbPath} where pk_id=#{id}")
    void saveThumbPath(@Param("id")Long id,@Param("thumbPath") String thumbPath);
}
