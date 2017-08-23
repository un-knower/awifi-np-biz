package com.awifi.np.biz.common.excel.dao.sql;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;

public class ExcelSql {
    
    /**
     * 向excel表中新增的sql生成
     * @param excel 参数
     * @return sql
     * @author 李程程  
     * @date 2017年4月14日
     */
    public String addSelective(EmsSysExcel excel){
        StringBuffer sql=new StringBuffer();
        sql.append("insert into np_biz_ems_excel ");
        
        String uploadname=excel.getUploadname();
        String filename=excel.getFilename();
        String filepath=excel.getFilepath();
        String type=excel.getType();
        Integer recordnum=excel.getRecordnum();
        Integer totalnum=excel.getTotalnum();
        Long uploader=excel.getUploader();
        Date uploadtime=excel.getUploadtime();
        Integer filestatus=excel.getFilestatus();
        Long completecosttime=excel.getCompletecosttime();
        Date completetime=excel.getCompletetime();
        String errorfile=excel.getErrorfile();
        String corporation=excel.getCorporation();
        String batch=excel.getBatch();
        String remark=excel.getRemark();
        String uuid=excel.getUuid();
        String ext=excel.getExt();
        Set<String> names=new LinkedHashSet<String>();
        Set<Object> values=new LinkedHashSet<Object>();
        if(uploadname!=null){
            names.add("UPLOADNAME");
            values.add("#{uploadname,jdbcType=VARCHAR}");
        }
        if(filename!=null){
            names.add("FILENAME");
            values.add("#{filename,jdbcType=VARCHAR}");
        }
        if(filepath!=null){
            names.add("FILEPATH");
            values.add("#{filepath,jdbcType=VARCHAR}");
        }
        if(type!=null){
            names.add("TYPE");
            values.add("#{type,jdbcType=VARCHAR}");
        }
        if(recordnum!=null){
            names.add("RECORDNUM");
            values.add("#{recordnum,jdbcType=INTEGER}");
        }
        if(totalnum!=null){
            names.add("TOTALNUM");
            values.add("#{totalnum,jdbcType=INTEGER}");
        }
        if(uploader!=null){
            names.add("UPLOADER");
            values.add("#{uploader,jdbcType=BIGINT}");
        }
        if(uploadtime!=null){
            names.add("UPLOADTIME");
            values.add("#{uploadtime,jdbcType=TIMESTAMP}");
        }
        if(filestatus!=null){
            names.add("FILESTATUS");
            values.add("#{filestatus,jdbcType=INTEGER}");
        }
        if(completecosttime!=null){
            names.add("COMPLETECOSTTIME");
            values.add("#{completecosttime,jdbcType=BIGINT}");
        }
        if(completetime!=null){
            names.add("COMPLETETIME");
            values.add("#{completetime,jdbcType=TIMESTAMP}");
        }
        if(errorfile!=null){
            names.add("ERRORFILE");
            values.add("#{errorfile,jdbcType=VARCHAR}");
        }
        if(corporation!=null){
            names.add("CORPORATION");
            values.add("#{corporation,jdbcType=VARCHAR}");
        }
        if(batch!=null){
            names.add("BATCH");
            values.add("#{batch,jdbcType=VARCHAR}");
        }
        if(remark!=null){
            names.add("REMARK");
            values.add("#{remark,jdbcType=VARCHAR}");
        }
        if(uuid!=null){
            names.add("UUID");
            values.add("#{uuid,jdbcType=VARCHAR}");
        }
        if(ext!=null){
            names.add("EXT");
            values.add("#{ext,jdbcType=VARCHAR}");
        }
        String paramNames=listToString(names, ",");
        sql.append("("+paramNames+")");
        String paramVlaues=listToString(values, ",");
        sql.append(" values("+paramVlaues+")");
        return sql.toString();
    }
    
    /**
     * 根据uuid更新数据
     * @param excel 参数
     * @return sql
     * @author 李程程  
     * @date 2017年4月14日
     */
    public String updateById(EmsSysExcel excel){
        StringBuffer sql=new StringBuffer();
        String uploadname=excel.getUploadname();
        String filename=excel.getFilename();
        String filepath=excel.getFilepath();
        String type=excel.getType();
        Integer recordnum=excel.getRecordnum();
        Integer totalnum=excel.getTotalnum();
        Long uploader=excel.getUploader();
        Date uploadtime=excel.getUploadtime();
        Integer filestatus=excel.getFilestatus();
        Long completecosttime=excel.getCompletecosttime();
        Date completetime=excel.getCompletetime();
        String errorfile=excel.getErrorfile();
        String remark=excel.getRemark();
        String ext=excel.getExt();
        sql.append("update np_biz_ems_excel set ");
        Set<Object> values=new LinkedHashSet<Object>();
        if(uploadname!=null){
            values.add("uploadname=#{uploadname,jdbcType=VARCHAR}");
        }
        if(filename!=null){
            values.add("filename=#{filename,jdbcType=VARCHAR}");
        }
        if(filepath!=null){
            values.add("filepath=#{filepath,jdbcType=VARCHAR}");
        }
        if(type!=null){
            values.add("type=#{type,jdbcType=VARCHAR}");
        }
        if(recordnum!=null){
            values.add("recordnum=#{recordnum,jdbcType=INTEGER}");
        }
        if(totalnum!=null){
            values.add("totalnum=#{totalnum,jdbcType=INTEGER}");
        }
        if(uploader!=null){
            values.add("uploader=#{uploader,jdbcType=BIGINT}");
        }
        if(uploadtime!=null){
            values.add("uploadtime=#{uploadtime,jdbcType=TIMESTAMP}");
        }
        if(filestatus!=null){
            values.add("filestatus=#{filestatus,jdbcType=INTEGER}");
        }
        if(completecosttime!=null){
            values.add("completecosttime=#{completecosttime,jdbcType=BIGINT}");
        }
        if(completetime!=null){
            values.add("completetime=#{completetime,jdbcType=TIMESTAMP}");
        }
        if(errorfile!=null){
            values.add("errorfile=#{errorfile,jdbcType=VARCHAR}");
        }
        if(remark!=null){
            values.add("remark=#{remark,jdbcType=VARCHAR}");
        }
        if(ext!=null){
            values.add("ext=#{ext,jdbcType=VARCHAR}");
        }
        String paramValues=listToString(values, ",");
        sql.append(paramValues);
        sql.append(" where ID=#{id,jdbcType=BIGINT}");
        return sql.toString();
    }
    
    /**
     * 连接参数的功能
     * @param set 是指参数的集合(不能重复)
     * @param separator 连接的隔离符号
     * @return str
     */
    public String listToString(Set<?> set,String separator){
        
        
        String str=StringUtils.join(set.iterator(), separator);
        
        return str;
    }
    
    
    /**
     * 根据主键查询详细信息
     * @param id 主键
     * @return sql语句
     */
    public String selectByPrimaryId(Long id){
        StringBuffer sql=new StringBuffer();
        sql.append("select * from np_biz_ems_excel where ID=#{id}");
        return sql.toString();
    }
    
    /**
     * 根据参数查询符合的数据行
     * @param map 参数
     * @return sql语句
     */
    public String queryExcels(Map<String,Object> map){
        StringBuffer sql=new StringBuffer();
        sql.append("select * from np_biz_ems_excel where 1=1 ");
        Long uploder=(Long) map.get("uploader");
        String type=(String) map.get("type");
        Date startTime=(Date) map.get("startTime");
        Date endTime=(Date) map.get("endTime");
        if(uploder!=null){
            sql.append("and UPLOADER=#{uploader} ");
        }
        if(type!=null){
            sql.append("and type = #{type} ");
        }
        if(startTime!=null){
            sql.append("and UPLOADTIME >= #{startTime,jdbcType=TIMESTAMP}");
        }
        if(endTime!=null){
            sql.append("and UPLOADTIME <= date_add(#{endTime,jdbcType=TIMESTAMP}, INTERVAL 1 day) ");
        }
        Integer pageStart=(Integer) map.get("pageStart");
        Integer pageSize=(Integer) map.get("pageSize");
        //页码和记录数分别默认为1和10
        if(pageStart==null){
            map.put("pageStart", 0);
        }
        if(pageSize==null){
            map.put("pageSize", 10);
        }
        sql.append("ORDER BY ID DESC LIMIT #{pageStart},#{pageSize}");
        return sql.toString();
    }

 
 
    public String getMaxBatchNum(Map<String,Object> map){
        StringBuffer sql=new StringBuffer();
        sql.append("select batch from np_biz_ems_excel ");
        sql.append("where corporation = #{corportaion,jdbcType=VARCHAR} "); 
        sql.append("AND createDate = #{createDate,jdbcType=DATE} ");
        sql.append("AND batch is not null order by id DESC limit 1");
        return sql.toString();
    }
    
//    public String setMaxBatchNum(String uuid,String batch,String corporation,String fileStatus){
//        StringBuffer sql=new StringBuffer();
//        sql.append("update np_biz_ems_excel ");
//        Set<String> names=new LinkedHashSet<String>();
//        Set<String> values=new LinkedHashSet<String>();
//        if(batch!=null){
//            names.add("batch");
//            values.add("#{batchNum,jdbcType=VARCHAR}");
//        }
//
//        if(corporation!=null){
//            names.add("corporation");
//            values.add("#{corporation,jdbcType=VARCHAR}");
//        }
//        String paramNames="("+listToString(names,",")+")";
//        sql.append(paramNames);
//        String paramValues=" values("+listToString(values,",")+")";
//        sql.append(paramValues);
//        return sql.toString();
//    }
}
