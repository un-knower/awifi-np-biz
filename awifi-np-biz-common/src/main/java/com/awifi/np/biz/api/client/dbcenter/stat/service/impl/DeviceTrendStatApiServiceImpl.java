/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月28日 上午9:02:48
* 创建作者：周颖
* 文件名称：DeviceTrendStatApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.stat.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.stat.model.DeviceTrend;
import com.awifi.np.biz.api.client.dbcenter.stat.service.DeviceTrendStatApiService;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@Service("deviceTrendStatApiService")
@SuppressWarnings("unchecked")
public class DeviceTrendStatApiServiceImpl implements DeviceTrendStatApiService {

    /**
     * 按周统计
     * @param params 参数
     * @param hasTotal 返回值是否有总计
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年7月28日 上午9:20:58
     */
    public List<DeviceTrend> getByWeek(Map<String, Object> params,boolean hasTotal) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_devicedevelopbyweek_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");//转json字符串
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求
        List<Map<String,Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");//获取数据
        return format(returnList,params,hasTotal);
    }
    
    /**
     * 处理返回数据 
     * @param rs 返回值
     * @param params 参数
     * @param hasTotal 返回值是否有总计
     * @return 结果
     * @author 周颖  
     * @date 2017年7月31日 下午2:42:40
     */
    private List<DeviceTrend> format(List<Map<String,Object>> rs,Map<String, Object> params,boolean hasTotal){
        int maxSize = rs.size();//list 大小
        if(maxSize <= 0){//如果为空 直接返回
            return new ArrayList<DeviceTrend>();
        }
        String statType = (String) params.get("statType");//获取入参的统计跨度
        Boolean hasChild = true;//默认为有下级
        if(StringUtils.equals(statType, Constants.STATTYPEC)){//如果是市的 没有下级 
            hasChild = false;
        }
        List<DeviceTrend> deviceTrendList = new ArrayList<DeviceTrend>(maxSize);//初始化列表
        Map<String,Object> deviceTrendMap = null;
        String areaIdString = getAreaId(params);//根据入参 生成上级地区id 返回地区id格式 31-383
        DeviceTrend deviceTrend = null;
        Integer num = null;
        Integer acceptArriveTotal = 0;//获取受理数-到达数总计
        Integer acceptNewNumTotal = 0;//获取受理数-新增数总计
        Integer acceptUnbindeTotal = 0;//获取受理数-拆机数总计
        Integer apArriveTotal = 0;//获取AP类激活绑定数-到达数总计
        Integer apNewTotal = 0;//获取AP类激活绑定数-新增数总计
        Integer apUnbindTotal = 0;//获取AP类激活绑定数-解绑数总计
        Integer apActiveTotal = 0;//获取AP类激活绑定数-活跃数总计
        Integer arriveMerchantTotal = 0;//获取到达商户数总计
        Integer activeMerchantTotal = 0;//获取活跃商户数总计
        for(int i=0;i<maxSize;i++){//循环列表
            deviceTrendMap = rs.get(i);
            deviceTrend = new DeviceTrend();
            Integer areaId = CastUtil.toInteger(deviceTrendMap.get("areaId"));//获取返回的地区id
            if(StringUtils.isBlank(areaIdString)){//如果上级地区id为空 查省级
                deviceTrend.setAreaId(areaId.toString());
            }else{
                deviceTrend.setAreaId(areaIdString + "-" + areaId); //拼上自身地区id
            }
            deviceTrend.setAreaName((String) deviceTrendMap.get("areaName"));//地区名称
            deviceTrend.setHasChild(hasChild);//是否有下级
            num = getNum("acceptArriveNum",deviceTrendMap);//获取受理数-到达数
            deviceTrend.setAcceptArriveNum(num);
            acceptArriveTotal += num;
            num = getNum("acceptNewNum",deviceTrendMap);//获取受理数-新增数
            deviceTrend.setAcceptNewNum(num);
            acceptNewNumTotal += num;
            num = getNum("acceptUnbindeNum",deviceTrendMap);//获取受理数-拆机数
            deviceTrend.setAcceptUnbindeNum(num);
            acceptUnbindeTotal += num;
            num = getNum("apArriveNum",deviceTrendMap);//获取AP类激活绑定数-到达数
            deviceTrend.setApArriveNum(num);
            apArriveTotal += num;
            num = getNum("apNewNum",deviceTrendMap);//获取AP类激活绑定数-新增数
            deviceTrend.setApNewNum(num);
            apNewTotal += num;
            num = getNum("apUnbindNum",deviceTrendMap);//获取AP类激活绑定数-解绑数
            deviceTrend.setApUnbindNum(num);
            apUnbindTotal += num;
            num = getNum("apActiveNum", deviceTrendMap);//获取AP类激活绑定数-活跃数
            deviceTrend.setApActiveNum(num);
            apActiveTotal += num;
            num = getNum("arriveMerchantNum", deviceTrendMap);//获取到达商户数
            deviceTrend.setArriveMerchantNum(num);
            arriveMerchantTotal += num;
            num = getNum("activeMerchantNum", deviceTrendMap);//获取活跃商户数
            deviceTrend.setActiveMerchantNum(num);
            activeMerchantTotal += num;
            deviceTrendList.add(deviceTrend);
        }
        if(hasTotal){//如果要求返回值有总计
            deviceTrend = new DeviceTrend();
            deviceTrend.setAreaName("合计");//地区置为合计
            deviceTrend.setAcceptArriveNum(acceptArriveTotal);//获取受理数-到达数
            deviceTrend.setAcceptNewNum(acceptNewNumTotal);//获取受理数-新增数
            deviceTrend.setAcceptUnbindeNum(acceptUnbindeTotal);//获取受理数-拆机数
            deviceTrend.setApArriveNum(apArriveTotal);//获取AP类激活绑定数-到达数
            deviceTrend.setApNewNum(apNewTotal);//获取AP类激活绑定数-新增数
            deviceTrend.setApUnbindNum(apUnbindTotal);//获取AP类激活绑定数-解绑数
            deviceTrend.setApActiveNum(apActiveTotal);//获取AP类激活绑定数-活跃数
            deviceTrend.setArriveMerchantNum(arriveMerchantTotal);//获取到达商户数
            deviceTrend.setActiveMerchantNum(activeMerchantTotal);//获取活跃商户数
            deviceTrendList.add(0, deviceTrend);//总计添加到列表头
        }
        return deviceTrendList;//返回列表
    }
    
    /**
     * 拼接areaId
     * @param params 入参
     * @return 结果  31-383
     * @author 周颖  
     * @date 2017年7月28日 下午3:07:30
     */
    private String getAreaId(Map<String, Object> params){
        String statType = (String) params.get("statType");//获取统计跨度
        if(StringUtils.equals(statType, Constants.STATTYPEP)){//如果是按省
            return StringUtils.EMPTY;
        }else if(StringUtils.equals(statType, Constants.STATTYPET)){//如果按市
            Long provinceId = CastUtil.toLong(params.get("province"));//拼上省id
            return provinceId.toString();
        }else if(StringUtils.equals(statType, Constants.STATTYPEC)){//如果按取
            Long provinceId = CastUtil.toLong(params.get("province"));//拼上省
            Long cityId = CastUtil.toLong(params.get("city"));//拼上市
            return provinceId + "-" + cityId;
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * 获取数量
     * @param paramName 参数名
     * @param deviceTrendMap map
     * @return 数量 为空返回0
     * @author 周颖  
     * @date 2017年7月28日 下午2:26:03
     */
    private Integer getNum(String paramName,Map<String,Object> deviceTrendMap){
        Integer num = CastUtil.toInteger(deviceTrendMap.get(paramName));//获取对应的参数值
        if(num == null){//如果为空 返回0
            return 0;
        }else{
            return num;
        }
    }
    
    /**
     * 按月统计
     * @param params 参数
     * @param hasTotal 返回值是否有总计
     * @return 结果
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年7月31日 下午2:34:48
     */
    public List<DeviceTrend> getByMonth(Map<String, Object> params,boolean hasTotal) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_devicedevelopbymonth_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");//转json字符串
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求
        List<Map<String,Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");//获取数据
        return format(returnList,params,hasTotal);
    }
}
