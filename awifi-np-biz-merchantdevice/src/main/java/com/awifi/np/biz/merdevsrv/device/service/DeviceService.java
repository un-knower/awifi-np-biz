package com.awifi.np.biz.merdevsrv.device.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午2:27:51
 * 创建作者：亢燕翔
 * 文件名称：DeviceService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface DeviceService {

    /**
     * 获取虚拟设备列表
     * @param sessionUser sessionUser
     * @param params 参数
     * @param page page
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月3日 下午4:22:34
     */
    void getListByParam(SessionUser sessionUser, String params, Page<Device> page) throws Exception;
    
    /**
     * 设备过户
     * @param bodyParam 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月7日 下午2:17:57
     */
    void transfer(Map<String, Object> bodyParam) throws Exception;

    /**
     * 导入胖ap
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param isSF 是否是省分平台
     * @param provinceId 省id
     * @param lastRowNum 最后一行
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月13日 下午7:08:29
     */
    void importFat(Sheet sheet, String belongTo, String entityType, Long merchantId, Long projectId, Boolean isSF, Long provinceId,int lastRowNum) throws Exception;

    /**
     * 导入瘦ap
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param lastRowNum 最后一行
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月13日 下午4:54:13
     */
    void importFit(Sheet sheet, String belongTo, String entityType, Long merchantId, Long projectId, int lastRowNum) throws Exception;

    /**
     * 导入nas
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param lastRowNum 最后一行
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月13日 下午4:54:47
     */
    void importNas(Sheet sheet, String belongTo, String entityType, Long merchantId, Long projectId, int lastRowNum) throws Exception;

    /**
     * 导入热点
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param merchantId 商户id
     * @param projectId 项目id
     * @param lastRowNum 最后一行
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月13日 下午4:55:14
     */
    void importHotarea(Sheet sheet, String belongTo, String entityType, Long merchantId, Long projectId, int lastRowNum) throws Exception;


	/**
	 * @param bodyParam 参数
	 * @throws Exception 异常
	 * @author 王冬冬  
	 * @date 2017年4月18日 下午1:51:52
	 */
    void batchEscape(List<Map<String, Object>> bodyParam) throws Exception;

    /**
	 * @param bodyParam 参数
	 * @throws Exception 异常
	 * @author 王冬冬  
	 * @date 2017年4月18日 下午1:51:52
	 */
    void batchChinanetSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception;

	/**
	 * @param bodyParam 参数
	 * @throws Exception 异常
	 * @author 王冬冬  
	 * @date 2017年4月18日 下午1:51:52
	 */
    void batchAwifiSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception;

    /**
	 * @param bodyParam 参数
	 * @throws Exception 异常
	 * @author 王冬冬  
	 * @date 2017年4月18日 下午1:51:52
	 */
    void batchLanSwitch(List<Map<String, Object>> bodyParam) throws Exception;

    /**
	 * @param bodyParam 参数
	 * @throws Exception 异常
	 * @author 王冬冬  
	 * @date 2017年4月18日 下午1:51:52
	 */
    void batchClientTimeout(List<Map<String, Object>> bodyParam) throws Exception;

    /**
     * 导入胖ap
     * @param sheet 单元格一条数据
     * @param belongTo 归属
     * @param entityType 设备类型 
     * @param projectId 项目id
     * @param roleId 角色id
     * @param lastRowNum 最后行数
     * @author 王冬冬  
     * @param curUser 用户
     * @throws Exception 
     * @date 2017年5月2日 上午10:48:43
     */
    void importFat(Sheet sheet, String belongTo, String entityType, Long projectId,Long roleId, int lastRowNum, SessionUser curUser) throws Exception;


    /**
     * @param merchantId 商户id
     * @param switchType 开关类型
     * @return map
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月4日 下午7:30:06
     */
    Map<String, Object> getSwitchStatus(Long merchantId, String switchType) throws Exception;

//    /**
//     * 防蹭网开关
//     * @param bodyParam 传入参数
//     * @author 王冬冬  
//     * @throws Exception 
//     * @date 2017年5月8日 下午2:17:59
//     */
//    void batchAntiRobber(List<Map<String, Object>> bodyParam) throws Exception;

    /**
     * 通过商户id获取设备名称、ssid集合
     * @param merchantId 商户id
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月3日 下午2:05:36
     */
    Map<String, Object> getDevInfoByMerchantId(Long merchantId) throws Exception;

    /**
     * 批量导入瘦ap
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param projectId 项目id
     * @param roleId 商户id
     * @param lastRowNum 最后一行
     * @author 王冬冬  
     * @param curUser 用户
     * @throws Exception 异常
     * @date 2017年5月17日 上午10:40:27
     */
    void importFitDev(Sheet sheet, String belongTo, String entityType, Long projectId, Long roleId, int lastRowNum, SessionUser curUser) throws Exception;

    /**
     * 批量导入ac,bas设备
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param projectId 项目id
     * @param roleId 商户id
     * @param lastRowNum 最后一行
     * @author 王冬冬  
     * @param curUser 用户
     * @throws Exception 异常
     * @date 2017年5月17日 上午10:40:27
     */
    void importACBasDev(Sheet sheet, String belongTo, String entityType, Long projectId, Long roleId, int lastRowNum, SessionUser curUser) throws Exception;

    /**
     * 批量导入热点设备
     * @param sheet sheet
     * @param belongTo 设备归属
     * @param entityType 设备类型
     * @param projectId 项目id
     * @param roleId 商户id
     * @param lastRowNum 最后一行
     * @author 王冬冬  
     * @param curUser 用户
     * @throws Exception 异常
     * @date 2017年5月17日 上午10:40:27
     */
    void importHotareaDev(Sheet sheet, String belongTo, String entityType, Long projectId, Long roleId, int lastRowNum, SessionUser curUser) throws Exception;

}
