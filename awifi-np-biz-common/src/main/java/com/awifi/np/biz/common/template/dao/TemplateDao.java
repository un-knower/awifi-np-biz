package com.awifi.np.biz.common.template.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.template.model.Template;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午3:11:30
* 创建作者：王冬冬
* 文件名称：TemplateDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@Service("templateDao")
public interface TemplateDao {

	/***
	 * 查询模板内容
	 * 
	 * @param suitCode
	 *            套码
	 * @param serviceCode
	 *            服务代码
	 * @param templateCode
	 *            page(pan)编号
	 * @return 模板内容
	 * @author 周颖
	 * @date 2017年1月11日 上午10:28:10
	 */
    @Select("select t.content from np_biz_template t inner join np_biz_suit_template st on t.id=st.template_id where st.suit_code=#{suitCode} and t.service_code=#{serviceCode} and t.code=#{templateCode} limit 1")
    String getByCode(@Param("suitCode") String suitCode, @Param("serviceCode") String serviceCode,@Param("templateCode") String templateCode);

	/**
	 * @param template 模板
	 * @return int
	 */
    @Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into np_biz_template(code, name,remark,service_code, src, content) values (#{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, "
			+ "#{serviceCode,jdbcType=VARCHAR}, #{src,jdbcType=LONGVARCHAR}, #{content,jdbcType=LONGVARCHAR})")
	int addTemplate(Template template);

	/**
	 * @param suitCode 套码id
	 * @param templateId 模板编号
	 */
    @Insert("insert into np_biz_suit_template(suit_code, template_id) values(#{suitCode,jdbcType=VARCHAR},#{templateId,jdbcType=BIGINT})")
	void addTemplateSuit(@Param("suitCode") String suitCode, @Param("templateId") Long templateId);

	/**
	 * @param template 模板
	 */
    @Delete("delete from np_biz_template where id = #{id,jdbcType=BIGINT}")
	void deleteTemplate(Template template);

	/**
	 * @param template 模板
	 */
    @Update("update np_biz_template set code = #{code,jdbcType=VARCHAR},name = #{name,jdbcType=VARCHAR},remark = #{remark,jdbcType=VARCHAR},service_code = #{serviceCode,jdbcType=VARCHAR},src=#{src,jdbcType=LONGVARCHAR},"
			+ "content=#{content,jdbcType=LONGVARCHAR} where id = #{id,jdbcType=BIGINT}")
	void updateTemplate(Template template);

	/**
	 * @param template 模板
	 * @return Template
	 */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "code", column = "code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "updateDate", column = "update_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
			@Result(property = "serviceCode", column = "service_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "suitCode", column = "suit_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "src", column = "src", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR),
			@Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR) })
	@Select("select t.id,t.`code`,t.`name`,t.remark,t.create_date,t.update_date,t.service_code,st.suit_code,t.src,t.content from np_biz_template t inner join np_biz_suit_template st on t.id=st.template_id where st.suit_code=#{suitCode} "
			+ "and t.service_code=#{serviceCode} and t.code=#{code} limit 1")
	Template getTemplateByParam(Template template);

	/**
	 * @param suitCode 套码id
	 * @param id 模板id
	 */
    @Delete("delete from np_biz_suit_template where suit_code=#{suitCode} and template_id=#{id}")
	void deleteTemplateSuit(@Param("suitCode") String suitCode, @Param("id") Long id);

	/**
	 * @param suitCode 套码编号
	 * @param serviceCode 服务编号
	 * @param templateCode 模板编号
	 * @return int
	 */
    @Select("select count(id) from np_biz_template t inner join np_biz_suit_template st on t.id=st.template_id where st.suit_code=#{suitCode} and t.service_code=#{serviceCode} and t.code=#{templateCode}")
	int isTemplateExist(@Param("suitCode") String suitCode, @Param("serviceCode") String serviceCode,
			@Param("templateCode") String templateCode);

    /**
     * @param suitCode 套码编号
     * @param serviceCode 服务编号
     * @param templateCode 模板编号
     * @return Long
     * @author 王冬冬  
     * @date 2017年4月10日 上午11:08:58
     */
    @Select("select id from np_biz_template t inner join np_biz_suit_template st on t.id=st.template_id where st.suit_code=#{suitCode} and t.service_code=#{serviceCode} and t.code=#{templateCode}")
	Long getExistTemplateId(@Param("suitCode") String suitCode, @Param("serviceCode") String serviceCode,
			@Param("templateCode") String templateCode);
}
