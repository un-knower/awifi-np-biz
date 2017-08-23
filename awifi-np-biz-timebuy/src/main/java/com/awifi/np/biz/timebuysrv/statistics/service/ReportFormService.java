/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月23日 下午3:29:33
* 创建作者：余红伟
* 文件名称：ReportFormService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.statistics.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.statistics.dao.ReportFormDao;
import com.awifi.np.biz.timebuysrv.util.FileUtil;
import com.awifi.np.biz.timebuysrv.web.core.path.PathManager;
@Service
public class ReportFormService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReportFormService.class);
    
    @Resource
    private ReportFormDao reportFormDao;
    /**
     * 按条件查询统计结果
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月28日 下午6:42:57
     */
   public List<HashMap<String, Object>> queryListByParam(Map<String, Object> params) throws Exception{
       //获取条件参数,前端传入
       String startTime = MapUtils.getString(params, "beginDate");
       String endTime = MapUtils.getString(params, "endDate");
       Integer provinceId = CastUtil.toInteger(params.get("provinceId"));
       Integer cityId = CastUtil.toInteger(params.get("cityId"));
       Integer areaId = CastUtil.toInteger(params.get("areaId"));
       //获取配置内容
       String query = FileUtil.readFile2Str(PathManager.getInstance().getClassPath().resolve("query.properties").toFile());
       //转成JsonObject
       JSONObject queryJsonObject = JsonUtil.fromJson(query, JSONObject.class);
       //获取JsonObject内的JsonArray
       JSONArray queryJsonArray = queryJsonObject.getJSONArray("fields");//查询字段数组
       String tableName = queryJsonObject.getString("tableName");
       String field = queryJsonObject.getString("field");//表之间关联字段
       String create_date = queryJsonObject.getString("create_date");//统计时间字段
       String connectTable = queryJsonObject.getString("connectTable");//连接表
       String connectField = queryJsonObject.getString("connectField");//连接表关联字段
       String province = queryJsonObject.getString("province");
       String provinceName = queryJsonObject.getString("provinceName");
       String city = queryJsonObject.getString("city");
       String cityName = queryJsonObject.getString("cityName");
       String area = queryJsonObject.getString("area");
       String areaName = queryJsonObject.getString("areaName");
       String merchantName = queryJsonObject.getString("merchantName");
       
       StringBuffer sql = new StringBuffer();
       sql.append("select ");
       
       //查询分组参数
       if(provinceId!=null && cityId!=null&&areaId!=null){
         sql.append("m.").append(merchantName).append(" as areaName,");
         sql.append("ss.").append(field).append(" as areaId, ");
//         sql.append(" concat(").append(provinceId).append(",'-',").append();
       }
       if(provinceId != null && cityId !=null && areaId==null){
           sql.append("m.").append(areaName).append(" as areaName,");
           sql.append(" concat(m.").append(province).append(",'-',m.").append(city)
               .append(",'-',m.").append(area).append(") as areaId, ");
       }
       if(provinceId != null && cityId == null && areaId==null){
           sql.append("m.").append(cityName).append(" as areaName,");
           sql.append(" concat(m.").append(province).append(",'-',m.").append(city)
               .append(") as areaId, ");
       }
       if(provinceId ==null && cityId == null && areaId == null){
           sql.append("m.").append(provinceName).append(" as areaName,");
           sql.append(" concat(m.").append(province).append(") as areaId, ");
       }
       //循环获取查询字段
       for(int i=0;i<queryJsonArray.size();i++){
           String name = queryJsonArray.getJSONObject(i).getString("name");
           String columnName = queryJsonArray.getJSONObject(i).getString("columnName");
           String function = queryJsonArray.getJSONObject(i).getString("function");
           String type = queryJsonArray.getJSONObject(i).getString("type");//限制条件不同，需要单独查询，不能拼接
           if(type!=null && "special".equals(type)){
               continue;
           }
//           sql.append(function).append(" as ").append(name).append(" ,");
           sql.append(function).append(" ,");
       }
       sql.deleteCharAt(sql.length() - 1);
       sql.append(" from ").append(tableName).append(" ss ").append(" left join ").append(connectTable).append(" m ")
           .append(" on ss.").append(field).append("=").append("m.").append(connectField);
       String condition = "";//时间限制条件
       if(StringUtils.isBlank(startTime)){
           if(StringUtils.isNotBlank(endTime)){
               condition = " and ss." + create_date +" < '" +endTime + "'"; 
           }
       }else{
           if(StringUtil.isBlank(endTime)){
               condition = " and ss." + create_date + "> '"+ startTime +"'";
           }else{
               condition = " and ss." + create_date + "> '" + startTime + "' and " +" ss." + create_date +" < '" +endTime + "'"; 
           }
       }
       condition = condition + " and m."+ province +" is not null and m."+city + " is not null and m." + area +" is not null ";
       //分组条件限制
       if(provinceId!=null && cityId!=null&&areaId!=null){
         sql.append(" where 1=1 ").append(condition).append(" group by ").append("m.").append(merchantName);
       }
       if(provinceId != null && cityId !=null && areaId==null){
//           sql.append(" and m.").append(city).append("=").append(cityId).append(" where 1=1 ")
//               .append(condition).append(" group by ").append("m.").append(area);
         sql.append(" where 1=1 ").append(" and m.").append(city).append("=").append(cityId).append(" ")
         .append(condition).append(" group by ").append("m.").append(area);
       }
       if(provinceId != null && cityId == null && areaId==null){
//           sql.append(" and m.").append(province).append("=").append(provinceId).append(" where 1=1 ")
//           .append(condition).append(" group by ").append("m.").append(city);
         sql.append(" where 1=1 ").append(" and m.").append(province).append("=").append(provinceId).append(" ")
         .append(condition).append(" group by ").append("m.").append(city);
       }
       if(provinceId ==null && cityId == null && areaId == null){
           sql.append(" where 1=1 ").append(condition).append(" group by ").append("m.").append(province);
       }
       List<HashMap<String, Object>> resultList = reportFormDao.queryListByParam(sql.toString());
       List<HashMap<String, Object>> newResultList = new ArrayList<>();
       
       
       
       
       for(int i=0;i<queryJsonArray.size();i++){
           String name = queryJsonArray.getJSONObject(i).getString("name");
           String columnName = queryJsonArray.getJSONObject(i).getString("columnName");
           String function = queryJsonArray.getJSONObject(i).getString("function");
           String type = queryJsonArray.getJSONObject(i).getString("type");//限制条件不同，需要单独查询，不能拼接
           StringBuffer newSql = new StringBuffer("select ");
           if(type!=null && "special".equals(type)){
               //查询分组参数
               if(provinceId!=null && cityId!=null&&areaId!=null){
                   newSql.append("m.").append(merchantName).append(" as areaName,");
                   sql.append("ss.").append(field).append(" as areaId, ");
//                 sql.append(" concat(").append(provinceId).append(",'-',").append();
               }
               if(provinceId != null && cityId !=null && areaId==null){
                   newSql.append("m.").append(areaName).append(" as areaName,");
                   newSql.append(" concat(m.").append(province).append(",'-',m.").append(city)
                       .append(",'-',m.").append(area).append(") as areaId, ");
               }
               if(provinceId != null && cityId == null && areaId==null){
                   newSql.append("m.").append(cityName).append(" as areaName,");
                   newSql.append(" concat(m.").append(province).append(",'-',m.").append(city)
                       .append(") as areaId, ");
               }
               if(provinceId ==null && cityId == null && areaId == null){
                   newSql.append("m.").append(provinceName).append(" as areaName,");
                   newSql.append(" concat(m.").append(province).append(") as areaId, ");
               }
               newSql.append(" ").append(function).append(" from ").append(tableName).append(" ss ").append(" left join ").append(connectTable).append(" m ")
               .append(" on ss.").append(field).append("=").append("m.").append(connectField);
               String newCondition = "";//时间限制条件
               if(StringUtils.isBlank(endTime)){
                   newCondition = " and ss." + create_date + " between curdate() - INTERVAL 1 day and curdate() ";
               }else{
//                   Calendar calendar = Calendar.getInstance();
//                   calendar.setTime(DateUtil.parseToDate(endTime, DateUtil.YYYY_MM_DD));
//                   calendar.add(Calendar.DATE, -1);
                   Date endDate = DateUtil.parseToDate(endTime, DateUtil.YYYY_MM_DD);
                   if(endDate.getTime() > new Date().getTime()){//如果传入的结束时间大于今天
                       endTime = DateUtil.formatToString(new Date(), DateUtil.YYYY_MM_DD);
                   }
//                   endTime = DateUtil.formatToString(calendar.getTime(), DateUtil.YYYY_MM_DD);
                   newCondition = " and ss." + create_date + " between  date_sub('"+ endTime + "',interval 1 day) and '" + endTime +"'";
               }
               newCondition = newCondition + " and m."+ province +" is not null and m."+city + " is not null and m." + area +" is not null ";
               //分组条件限制
               if(provinceId!=null && cityId!=null&&areaId!=null){
                   newSql.append(" where 1=1 ").append(newCondition).append(" group by ").append("m.").append(merchantName);
               }
               if(provinceId != null && cityId !=null && areaId==null){
//                   sql.append(" and m.").append(city).append("=").append(cityId).append(" where 1=1 ")
//                       .append(condition).append(" group by ").append("m.").append(area);
                   newSql.append(" where 1=1 ").append(" and m.").append(city).append("=").append(cityId).append(" ")
                 .append(newCondition).append(" group by ").append("m.").append(area);
               }
               if(provinceId != null && cityId == null && areaId==null){
//                   sql.append(" and m.").append(province).append("=").append(provinceId).append(" where 1=1 ")
//                   .append(condition).append(" group by ").append("m.").append(city);
                   newSql.append(" where 1=1 ").append(" and m.").append(province).append("=").append(provinceId).append(" ")
                 .append(newCondition).append(" group by ").append("m.").append(city);
               }
               if(provinceId ==null && cityId == null && areaId == null){
                   newSql.append(" where 1=1 ").append(newCondition).append(" group by ").append("m.").append(province);
               }
               newResultList = reportFormDao.queryListByParam(newSql.toString());
               break;
           }
       }
     //循环原先的list，如果有符合的设备记录，插入
       for( int j=0;j<resultList.size();j++){
           HashMap<String, Object> map = resultList.get(j);
           for(int n=0;n<newResultList.size();n++){
               HashMap<String, Object> newMap = newResultList.get(n);
//               String newAreaName = MapUtils.getString(newMap, "areaName");
               String newAreaId = MapUtils.getString(newMap, "areaId");
               if(newAreaId!=null && newAreaId.equals(MapUtils.getString(map, "areaId"))){
                   map.put("deviceNum", MapUtils.getString(newMap, "deviceNum")); 
               }
           }
       }
//       return reportFormDao.queryListByParam(sql.toString());
       return resultList;
   }
    /**
     * 查询，涉及到省市区查询，必然要关联到其他表，除非统计表自带省市区，不过现在并没有这项数据。
     * merchant表里有省市区，只要统计表左连接merchant表就可
     * 总金额，付费次数... merchant_id, 省 市 区
     * 然后以条件进行group by 如果没有条件，那是全国 group by province,选了省那就 限定某省，group by city
     * 字段规则, 总金额是sum, 感觉都是sum好了。统计次数最好了。。
     */
    /**
     * 每天统计任务
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月27日 下午7:35:04
     */
    public void statisticsTask() throws Exception{
        //获取配置文件内的数据
        String config = FileUtil.readFile2Str(PathManager.getInstance().getClassPath().resolve("statistics.properties").toFile());
        //转成JsonArray
        JSONArray configJsonArray = JsonUtil.fromJson(config, JSONArray.class);
        //循环
      //插入
        String dateStr= DateUtil.formatToString(new Date(), "YYYYMMDD");
        for(int i=0; i<configJsonArray.size(); i++){
            JSONObject object = configJsonArray.getJSONObject(i);
            String tableName = object.getString("tableName");//查询表
            String condition = object.getString("condition");//限制条件
            String name = object.getString("name");
            String columnName = object.getString("columnName");
            String groupBy = object.getString("groupBy");//分组条件
            String function = object.getString("function");//查询函数
            
            String statisticsTableName = object.getString("statisticsTableName");//存放统计结果表名
            String statisticsColumnName = object.getString("statisticsColumnName");//字段名
            String statisticsGroupBy = object.getString("statisticsGroupby");//对应分组字段
            String statisticsCondition = object.getString("statisticsCondition");//限制条件。
            
            String type = object.getString("type");
            
         
            if("javaMethod".equals(type)){
                //循环统计表 获取statisticsGroupBy字段值
                
                List<Long> groupByValueList = reportFormDao.getGroupByValueList(statisticsTableName, statisticsGroupBy);
                String serviceName = object.getString("serviceName");
                String methodName = object.getString("methodName");
                for(Long groupByValue : groupByValueList){
                    Long result =callJavaMthod(serviceName,methodName,groupByValue);
                    
                    StringBuffer updateSql = new StringBuffer();

                    updateSql.append("update ").append(statisticsTableName).append(" ss ").append(" set ss.").append(statisticsColumnName)
                        .append("=").append(result).append(" where create_date> curdate() and ss.").append(statisticsGroupBy).append(" = ").append(groupByValue);
                    logger.info(updateSql.toString());
                    reportFormDao.updateStatistics(updateSql.toString());
                    
                }
                
                continue;
               
            }
            
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","tableName未配置!"));
            }
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","name未配置!"));
            }
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","columnName未配置!"));
            }
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","groupBy未配置!"));
            }
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","function未配置!"));
            }
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","statisticsTableName未配置!"));
            }
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","statisticsColumnName未配置!"));
            }
            if(StringUtils.isBlank(tableName)){
                throw new ValidationException("E0000002",MessageUtil.getMessage("E0000002","statisticsGroupBy未配置!"));
            }
            
            
          
            //第一次循环时,插入新数据
            if(i==0){
                //查询今天是不是第一个统计，如果今天的数据已经有了，就先删掉
                Integer count = reportFormDao.getCountByToday(statisticsTableName, statisticsCondition);
                if(count != null && count > 0){
                    reportFormDao.deleteToday(statisticsTableName, statisticsCondition);
                }
                StringBuffer insertSql = new StringBuffer();
                //插入
                insertSql.append("insert into ").append(statisticsTableName).append("(").append(statisticsColumnName).append(",")
                    .append(statisticsGroupBy).append(",create_date)").append(" select ").append(function).append(",").append(groupBy)
                    .append(",now()").append(" from ")
                    .append(tableName).append(" where ").append(condition).append(" group by ").append(groupBy);
                reportFormDao.insertStatistics(insertSql.toString());
                continue;
            }

            //第二次的时候就是更新数据
            StringBuffer updateSql = new StringBuffer();

            updateSql.append("update ").append(statisticsTableName).append(" ss ").append(" set ss.").append(statisticsColumnName)
                .append("=(").append("select ").append(function).append(" from ").append(tableName).append(" m ").append(" where 1=1  and ")
                .append("m.").append(groupBy).append("=ss.").append(statisticsGroupBy);
                
            if(!StringUtils.isBlank(condition)){
                //把条件加上 ,前面可以加上where 1=1来拼接但我感觉这些条件都必须有，太难了。
                updateSql.append(" and (m.").append(condition).append(")");
            }
            if(StringUtils.isNotBlank(statisticsCondition)){
                updateSql.append(" and (ss.").append(statisticsCondition).append(")");
            }
            updateSql.append(")");
            reportFormDao.updateStatistics(updateSql.toString());
        }
        
    }
    
    /**
     * 通过配置调用java类查询数据
     * @param serviceName 服务名称
     * @param methodName 方法名称
     * @param groupByValue 主键
     * @return 查询结果
     * @author 张智威  
     * @date 2017年8月14日 上午10:44:57
     */
    private Long callJavaMthod(String serviceName, String methodName, Long groupByValue) {
        // TODO Auto-generated method stub
        
        Object serviceBean = BeanUtil.getBean(serviceName);
        
        
        Class<?> serviceClass = serviceBean.getClass();// 然后通过反射调用对应的方法
      
        Class<?>[] parameterTypes = new Class[]{Long.class};
        Object[] parameters = new Long[]{groupByValue}; //request 封装了类名 方法名 和参数

        /*Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAmessible(true);
        return method.invoke(serviceBean, parameters);*/

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        try {
            return (Long)serviceFastMethod.invoke(serviceBean, parameters);
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0l;
        
        
       
    }
}
