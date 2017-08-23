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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.stat.service.PortalPVStatApiService;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月31日 上午10:33:01
 * 创建作者：许尚敏
 * 文件名称：PortalPVStatApiServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("portalPVStatApiService")
public class PortalPVStatApiServiceImpl implements PortalPVStatApiService {

    /**
     * Portal页面-商户维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月28日 上午10:33:10
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getTrendByMerchant(Map<String, Object> params) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_QueryPortalPvStatByMerchant_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");//转json字符串
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求
        List<Map<String, Object>> returnData = (List<Map<String, Object>>) returnMap.get("rs");//获取数据
        return returnData;//返回数据
    }
    
    /**
     * Portal页面-商户维度-总计
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月28日 上午10:33:10
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getTotalCountByMerchant(Map<String, Object> params) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_QueryPortalPvCountByMerchant_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");//转json字符串
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求
        Map<String, Object> returnData = (Map<String, Object>) returnMap.get("rs");//获取数据
        return returnData;//返回数据
    }
    
    /**
     * Portal页面-商户维度-分页数
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月28日 上午10:33:10
     */
    public int getCountByMerchant(Map<String, Object> params) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_QueryPortalPvListCountByMerchant_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");//转json字符串
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求
        int count = CastUtil.toInteger(returnMap.get("rs"));//获取数据
        return count;//返回数据
    }
    
    /**
     * Portal页面-商户维度-列表
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月28日 上午10:33:10
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getListByMerchant(Map<String, Object> params) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_QueryPortalPvListByMerchant_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");//转json字符串
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求
        List<Map<String, Object>> returnData = (List<Map<String, Object>>) returnMap.get("rs");//获取数据
        return returnData;//返回数据
    }
    
//    /**
//     * @param params 参数
//     * @throws Exception 数据中心异常
//     * @return 数据
//     * @author 王冬冬  
//     * @date 2017年7月27日 下午2:16:16
//     */
//    @SuppressWarnings("unchecked")
//    public List<Map<String, Object>> queryPortalPvStatByArea(String params) {
//        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
//        parameterMap.put("params", params);
//        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
//        String url = SysConfigUtil.getParamValue("dbc_queryPortalPvStatByArea_url");//获取数据中心虚拟设备总记录数接口地址
////        String url="http://localhost:8080/mocksrv/dbc/queryPortalPvStatByArea";
//        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
//        List list=(List)returnMap.get("rs");//总条数
//        
//        List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
//        if(list==null||list.isEmpty()){
//            return null;
//        }
//        for(int i=0;i<list.size();i++){
//            Map<String,Object> map=(Map<String, Object>) list.get(i);
//            Map<String,Object> newMap=new HashMap<String,Object>();
//            newMap.put("dayFlag",map.get("dayFlag"));//时间
//            newMap.put("pv1",map.get("pv1"));//引导页浏览量
//            newMap.put("pv2",map.get("pv2"));//认证页浏览量
//            newMap.put("pv3",map.get("pv3"));//过渡页浏览量
//            newMap.put("pv4",map.get("pv4"));//导航页浏览量
//            result.add(newMap);
//        }
//        return result;
//    }

    /**
     * portal地区维度列表
     * @param params 参数
     * @throws Exception 数据中心异常
     * @return 数据
     * @author 王冬冬  
     * @date 2017年7月27日 下午2:16:16
     */
    public List<Map<String, Object>> getByArea(String params) throws Exception {
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", params);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        String url = SysConfigUtil.getParamValue("dbc_queryPortalPvByArea_url");//获取数据中心虚拟设备总记录数接口地址
//        String url="http://localhost:8080/mocksrv/dbc/queryPortalPvStatByArea";
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
        List list=(List)returnMap.get("rs");//总条数
        
        List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
        if(list==null||list.isEmpty()){
            return null;
        }
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=(Map<String, Object>) list.get(i);
            Map<String,Object> newMap=new HashMap<String,Object>();
            newMap.put("areaId",map.get("areaId"));//区域id
            newMap.put("areaName",map.get("areaName"));//区域名称
            newMap.put("pv1",map.get("pv1"));//引导页浏览量
            newMap.put("pv2",map.get("pv2"));//认证页浏览量
            newMap.put("pv3",map.get("pv3"));//过渡页浏览量
            newMap.put("pv4",map.get("pv4"));//导航页浏览量
            result.add(newMap);
        }
        return result;
    }

   
    /**
     * portal地区维度趋势图
     * @param params 参数
     * @throws Exception 异常
     * @return list
     * @author 王冬冬  
     * @date 2017年8月15日 下午4:16:18
     */
    public List<Map<String, Object>> getTrendByArea(String params) throws Exception {
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", params);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        String url = SysConfigUtil.getParamValue("dbc_queryPortalPvStatByArea_url");//获取数据中心虚拟设备总记录数接口地址
//        String url="http://localhost:8080/mocksrv/dbc/queryPortalPvStatByArea";
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
        List list=(List)returnMap.get("rs");//总条数
        
        List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
        if(list==null||list.isEmpty()){
            return null;
        }
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=(Map<String, Object>) list.get(i);
            Map<String,Object> newMap=new HashMap<String,Object>();
            newMap.put("dayFlag",map.get("visitDate"));//时间
            newMap.put("pv1",map.get("pv1"));//引导页浏览量
            newMap.put("pv2",map.get("pv2"));//认证页浏览量
            newMap.put("pv3",map.get("pv3"));//过渡页浏览量
            newMap.put("pv4",map.get("pv4"));//导航页浏览量
            result.add(newMap);
        }
        return result;
    }
}
