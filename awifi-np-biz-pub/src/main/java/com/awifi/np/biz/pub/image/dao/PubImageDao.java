/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月28日 上午9:20:33
* 创建作者：余红伟
* 文件名称：PubImageDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.image.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.pub.image.model.PubImage;
@Service
public interface PubImageDao {
    /**
     * 添加图片 
     * status状态 ：临时：0;可以:1;删除：9
     */
    @Insert("insert into np_biz_pub_image(url,old_url,old_id,upload_dir,upload_url,path,status,create_date) "
            + "values (#{url},#{oldUrl},#{oldId},#{uploadDir},#{uploadUrl},#{path},0,now())")
    public Integer addPubImage(PubImage pubImage);
    
    /**
     * 根据id和status更改图片状态 状态 ：临时：0;可以:1;删除：9
     */
    @Update("update np_biz_pub_image set status=#{status} where id = #{id}")
    public Integer updatePubImageByIdAndStatus(@Param(value = "id")Long id, @Param(value = "status")Integer status);
    /**
     * 根据id查询图片
     */
    @Results(value ={ @Result(property = "id" , column = "id" , javaType = Long.class , jdbcType = JdbcType.BIGINT ),
            @Result(property = "url" , column = "url", javaType = String.class , jdbcType = JdbcType.VARCHAR),
            @Result(property = "oldUrl" , column = "old_url", javaType = String.class , jdbcType = JdbcType.VARCHAR),
            @Result(property = "oldId" , column = "old_id", javaType = Long.class , jdbcType = JdbcType.BIGINT),
            @Result(property = "uploadDir", column = "upload_dir", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "uploadUrl", column = "upload_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "path", column = "path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status" , column = "status", javaType = Integer.class , jdbcType = JdbcType.INTEGER),
            @Result(property = "createDate" , column = "create_date", javaType = Date.class , jdbcType = JdbcType.DATE),
    })
    @Select("select id, url, old_url, old_id,upload_dir,upload_url,path,status,create_date from np_biz_pub_image where id=#{id}")
    public PubImage selectById(Long id);
    
    /**
     * 根据id删除图片
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年6月30日 上午9:23:43
     */
    @Delete("delete from np_biz_pub_image where id=#{id}")
    public Integer deleteById(Long id);
    
    /**
     * 根据url查询图片
     * @param url
     * @return
     * @author 余红伟 
     * @date 2017年6月30日 上午10:30:13
     */
    @Results(value ={ @Result(property = "id" , column = "id" , javaType = Long.class , jdbcType = JdbcType.BIGINT ),
            @Result(property = "url" , column = "url", javaType = String.class , jdbcType = JdbcType.VARCHAR),
            @Result(property = "oldUrl" , column = "old_url", javaType = String.class , jdbcType = JdbcType.VARCHAR),
            @Result(property = "oldId" , column = "old_id", javaType = Long.class , jdbcType = JdbcType.BIGINT),
            @Result(property = "uploadDir", column = "upload_dir", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "uploadUrl", column = "upload_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "path", column = "path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status" , column = "status", javaType = Integer.class , jdbcType = JdbcType.INTEGER),
            @Result(property = "createDate" , column = "create_date", javaType = Date.class , jdbcType = JdbcType.DATE),
    })
    @Select("select id, url, old_url, old_id,upload_dir,upload_url,path,status,create_date from np_biz_pub_image where url=#{url}")
    public PubImage selectByUrl(String url);
    
    /**
     * 查询需要删除的记录上传，且状态为9 或者 (状态为0 且 时间是今天之前,防止删除的时候，用户刚刚上传还没确认,暂时定为5小时前) 的图片
     * @return
     * @author 余红伟 
     * @date 2017年6月30日 下午1:58:28
     */
    @Results(value ={ @Result(property = "id" , column = "id" , javaType = Long.class , jdbcType = JdbcType.BIGINT ),
            @Result(property = "url" , column = "url", javaType = String.class , jdbcType = JdbcType.VARCHAR),
            @Result(property = "oldUrl" , column = "old_url", javaType = String.class , jdbcType = JdbcType.VARCHAR),
            @Result(property = "oldId" , column = "old_id", javaType = Long.class , jdbcType = JdbcType.BIGINT),
            @Result(property = "uploadDir", column = "upload_dir", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "uploadUrl", column = "upload_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "path", column = "path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status" , column = "status", javaType = Integer.class , jdbcType = JdbcType.INTEGER),
            @Result(property = "createDate" , column = "create_date", javaType = Date.class , jdbcType = JdbcType.DATE),
    })
    @Select("select id, url, old_url, old_id,upload_dir,upload_url,path,status,create_date from np_biz_pub_image where "
            + " status=9 or (status=0 and create_date < date_sub(now(), interval 5 hour))")
    public List<PubImage> getDeleteList();
}
