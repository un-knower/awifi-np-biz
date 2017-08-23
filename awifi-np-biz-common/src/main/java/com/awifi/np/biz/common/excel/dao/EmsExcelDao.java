package com.awifi.np.biz.common.excel.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
//import com.awifi.np.biz.common.excel.dao.sql.DeviceImportSql;
import com.awifi.np.biz.common.excel.dao.sql.ExcelSql;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;

@Service("emsExcelDao")
public interface EmsExcelDao {

    /**
     * 加入新的选项
     * @param excel 更新的信息
     * @return int
     */
    @InsertProvider(type=ExcelSql.class,method="addSelective")
    @SelectKey(before=false,keyProperty="id",resultType=Long.class,statement="SELECT LAST_INSERT_ID()")
    public int addSelective(EmsSysExcel excel);
    
    @UpdateProvider(type=ExcelSql.class,method="updateById")
    public int updateById(EmsSysExcel excel);
    
    
    /**
     * 根据id进行查询
     * @param id 主键
     * @return excel信息
     */
    @SelectProvider(type=ExcelSql.class,method="selectByPrimaryId")
    @Results(value={
            @Result(id=true,property="id",column="ID",jdbcType=JdbcType.BIGINT),
            @Result(property="filename",column="FILENAME",jdbcType=JdbcType.VARCHAR),
            @Result(property="type",column="TYPE",jdbcType=JdbcType.VARCHAR),
            @Result(property="filepath",column="FILEPATH",jdbcType=JdbcType.VARCHAR),
            @Result(property="recordnum",column="RECORDNUM",jdbcType=JdbcType.INTEGER),
            @Result(property="totalnum",column="TOTALNUM",jdbcType=JdbcType.INTEGER),
            @Result(property="uploader",column="UPLOADER",jdbcType=JdbcType.BIGINT),
            @Result(property="uploadname",column="UPLOADNAME",jdbcType=JdbcType.VARCHAR),
            @Result(property="uploadtime",column="UPLOADTIME",jdbcType=JdbcType.TIMESTAMP),
            @Result(property="filestatus",column="FILESTATUS",jdbcType=JdbcType.INTEGER),
            @Result(property="completecosttime",column="COMPLETECOSTTIME",jdbcType=JdbcType.BIGINT),
            @Result(property="completetime",column="COMPLETETIME",jdbcType=JdbcType.TIMESTAMP),
            @Result(property="errorfile",column="ERRORFILE",jdbcType=JdbcType.VARCHAR),
            @Result(property="corporation",column="CORPORATION",jdbcType=JdbcType.VARCHAR),
            @Result(property="batch",column="BATCH",jdbcType=JdbcType.VARCHAR),
            @Result(property="remark",column="REMARK",jdbcType=JdbcType.VARCHAR),
            @Result(property="uuid",column="UUID",jdbcType=JdbcType.VARCHAR),
            @Result(property="ext",column="EXT",jdbcType=JdbcType.VARCHAR),
    })
    public EmsSysExcel selectByPrimaryId(Long id);
    
    
    /**
     * 查询多个选项
     * @param map 参数项
     * @return list
     */
    @SelectProvider(type=ExcelSql.class,method="queryExcels")
    @Results(value={
            @Result(id=true,property="id",column="ID",jdbcType=JdbcType.BIGINT),
            @Result(property="filename",column="FILENAME",jdbcType=JdbcType.VARCHAR),
            @Result(property="type",column="TYPE",jdbcType=JdbcType.VARCHAR),
            @Result(property="filepath",column="FILEPATH",jdbcType=JdbcType.VARCHAR),
            @Result(property="recordnum",column="RECORDNUM",jdbcType=JdbcType.INTEGER),
            @Result(property="totalnum",column="TOTALNUM",jdbcType=JdbcType.INTEGER),
            @Result(property="uploader",column="UPLOADER",jdbcType=JdbcType.BIGINT),
            @Result(property="uploadname",column="UPLOADNAME",jdbcType=JdbcType.VARCHAR),
            @Result(property="uploadtime",column="UPLOADTIME",jdbcType=JdbcType.TIMESTAMP),
            @Result(property="filestatus",column="FILESTATUS",jdbcType=JdbcType.INTEGER),
            @Result(property="completecosttime",column="COMPLETECOSTTIME",jdbcType=JdbcType.BIGINT),
            @Result(property="completetime",column="COMPLETETIME",jdbcType=JdbcType.TIMESTAMP),
            @Result(property="errorfile",column="ERRORFILE",jdbcType=JdbcType.VARCHAR),
            @Result(property="remark",column="REMARK",jdbcType=JdbcType.VARCHAR),
            @Result(property="uuid",column="UUID",jdbcType=JdbcType.VARCHAR),
            @Result(property="ext",column="EXT",jdbcType=JdbcType.VARCHAR),
    })
    public List<EmsSysExcel> queryExcels(Map<String,Object> map);
    
      
    @Select("select count(id) from np_biz_ems_excel where TYPE=#{type}")
    public int queryCount(@Param("type")String type);

    @SelectProvider(type=ExcelSql.class,method="getMaxBatchNum")
    public String getMaxBatchNum(Map<String,Object> map);
    
    @Update("update np_biz_ems_excel set FILESTATUS=#{fileStatus},CREATEDATE=#{date},CORPORATION=#{corporation},BATCH=#{batch},ERRORFILE=#{errorfile} where UUID=#{uuid}")
    public int setMaxBatchNum(@Param("fileStatus")Integer fileStatus,@Param("date")Date date,@Param("corporation")String corporation,
            @Param("batch")String batch,@Param("errorfile")String errorfile,@Param("uuid")String uuid);

}

