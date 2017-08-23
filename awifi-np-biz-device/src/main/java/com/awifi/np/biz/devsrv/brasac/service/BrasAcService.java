/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午2:51:19
* 创建作者：范立松
* 文件名称：BrasAcService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.brasac.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.model.Page;

public interface BrasAcService {

    /**
     * 更新设备状态
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月20日 下午2:04:28
     */
    void batchUpdateFlowSts(Map<String, Object> paramsMap) throws Exception;

    /**
     * 分页查询设备列表 
     * @param page 设备列表
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月14日 上午11:12:59
     */
    void queryBrasAcList(Page<CenterPubEntity> page, Map<String, Object> paramsMap) throws Exception;

    /**
     * 根据设备id查询 
     * @param brasacId 设备id
     * @return 设备信息
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:00
     */
    CenterPubEntity queryBrasAcById(String brasacId) throws Exception;

    /**
     * 添加bras设备 
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:03
     */
    void addBras(Map<String, Object> paramsMap) throws Exception;

    /**
     * 添加ac设备
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:05
     */
    void addAc(Map<String, Object> paramsMap) throws Exception;

    /**
     * 更新bras设备
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:08
     */
    void updateBras(Map<String, Object> paramsMap) throws Exception;

    /**
     * 更新ac设备 
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:11
     */
    void updateAc(Map<String, Object> paramsMap) throws Exception;

    /**
     * 根据设备id删除设备
     * @param ids 设备主键id列表
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:11
     */
    void removeBrasAc(String[] ids) throws Exception;

    /**
     * 添加白名单(提交设备时)
     * @param idList 设备id列表
     * @param msgList 错误信息列表
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:13
     */
    void addWhiteList(List<String> idList, List<JSONObject> msgList) throws Exception;

    /**
     * 添加白名单(修改设备时)
     * @param paramsMap 请求参数
     * @param msgList 错误信息列表
     * @throws Exception 异常
     * @author 范立松
     * @date 2017年4月17日 下午3:23:13
     */
    void addWhiteList(Map<String, Object> paramsMap, List<JSONObject> msgList) throws Exception;

    /**
     * 查询nasip过滤信息
     * @param paramsMap 请求参数
     * @return nasip过滤信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    Map<String, Object> getNasFilter(Map<String, Object> paramsMap) throws Exception;

    /**
     * 添加nasip过滤
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    void addNasFilter(Map<String, Object> paramsMap) throws Exception;

    /**
     * 更新nasip过滤
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    void updateNasFilter(Map<String, Object> paramsMap) throws Exception;

    /**
     * 删除nasip过滤
     * @param nasIpList nasIp列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    void removeNasFilter(List<String> nasIpList) throws Exception;

}
