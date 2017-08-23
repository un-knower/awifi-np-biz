/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午8:18:54
* 创建作者：范涌涛
* 文件名称：DeviceQueryClient.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.fatap.util;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.servcie.DeviceQueryApiService;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings({"rawtypes"})
public class DeviceQueryClient {
    
    /** 设备查询服务service*/
    private static DeviceQueryApiService deviceQueryApiService;
    
    /**
     * 获取bean
     * @return deviceQueryApiService
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:18:26
     */
    public static DeviceQueryApiService getDeviceQueryApiService(){
        if(deviceQueryApiService==null){
            deviceQueryApiService=(DeviceQueryApiService) BeanUtil.getBean("deviceQueryApiService");
        }
        return deviceQueryApiService;
    }
    
    /**
     * 得到数据库中符合条件的设备记录数
     * @param params 查询条件
     * @return 记录数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:19:22
     */
    public static int queryEntityCountByParam(Map<String,Object> params)throws Exception{
        return getDeviceQueryApiService().queryEntityCountByParam(params);
    }
    
    /**
     * 条件查询设备列表
     * @param params 查询条件
     * @return 列表信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:23:28
     */
    public static List<CenterPubEntity> queryEntityInfoListByParam(Map<String, Object> params)throws Exception{
        return getDeviceQueryApiService().queryEntityInfoListByParam(params);
    }
    
    /**
     * 条件查不同型号设备数量
     * @param map 查询条件
     * @return 数量
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:24:37
     */
    public static Long queryEntityCountByParamGroupByModel(Map<String,Object> map)throws Exception{
        return getDeviceQueryApiService().queryEntityCountByParamGroupByModel(map);
    }
    
    /**
     * 根据主键查询设备和虚拟信息
     * @param id 主键
     * @return entity
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:25:47
     */
    public static CenterPubEntity queryEntityInfoById(String id) throws Exception {
        return getDeviceQueryApiService().queryEntityInfoById(id);
    }

    /**
     * 根据ID查询awifi热点信息
     * @param id awifi热点ID
     * @return awifi热点信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:39:09
     */
    public static Map<String,Object> queryHotareaInfoById(Long id) throws Exception {
        return getDeviceQueryApiService().queryHotareaInfoById(id);
    }

    /**
     * 根据条件查询awifi热点信息
     * @param reqParam 查询条件
     * @return 热点信息列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:27:13
     */
    public static Page queryHotareaInfoListByParam(Map<String,Object> reqParam) throws Exception {
        return getDeviceQueryApiService().queryHotareaInfoListByParam(reqParam);
    }
    
    /**
     * 根据条件查询chinanet热点信息
     * @param reqParam 查询条件
     * @return 热点信息列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:28:12
     */
    public static Page queryChinaNetHotareaInfoListByParam(Map<String,Object> reqParam) throws Exception {
        return getDeviceQueryApiService().queryChinaNetHotareaInfoListByParam(reqParam);
    }
    
    /**
     * 根据ID查询chinanet热点信息
     * @param id chinanet热点ID
     * @return chinanet热点信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:39:09
     */
    public static Map queryChinaHotareaInfoById(Long id) throws Exception {
        return getDeviceQueryApiService().queryChinaHotareaInfoById(id);
    }
    
    /**
     * 根据条件查询chinanet热点信息
     * @param reqParam 查询条件
     * @return 热点信息列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月13日 下午3:27:13
     */
    public static Page queryHotFitapInfoListByParam(Map<String,Object> reqParam) throws Exception {
        return getDeviceQueryApiService().queryHotFitapInfoListByParam(reqParam);
    }
    
    /**
     * 条件查询设备按批次分组 分页总条数
     * @param params 条件
     * @return 总数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:29:25
     */
    public static Integer queryEntityCountByParamGroup(Map<String,Object> params)throws Exception{
        return getDeviceQueryApiService().queryEntityCountByParamGroup(params);
    }
    
    /**
     * 条件查询设备按批次分组 信息--分页
     * @param params 条件
     * @return 批次信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:29:41
     */
    public static List<CenterPubEntity> queryEntityListByParamGroup(Map<String, Object> params)throws Exception{
        return getDeviceQueryApiService().queryEntityListByParamGroup(params);
    }
   
}
