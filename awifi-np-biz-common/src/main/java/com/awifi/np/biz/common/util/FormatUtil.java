package com.awifi.np.biz.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月9日 上午9:57:44
 * 创建作者：周颖
 * 文件名称：FormatUtil.java 
 * 版本：  v1.0
 * 功能：格式化工具类
 * 修改记录：
 */
@SuppressWarnings("unchecked")
public class FormatUtil {
    
    /** 日志 */
    private static Log logger = LogFactory.getLog(FormatUtil.class);

    /**
     * 格式化 请求参数
     * @param request 请求
     * @return json
     * @author 许小满
     * @date 2015年7月21日 下午6:53:52
     */
    public static String formatRequestParam(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parameter", request.getParameterMap());// 参数
        param.put("method", request.getMethod());// 请求的方法
        param.put("protocol", request.getProtocol());// 协议
        //param.put("cookies", request.getCookies());// 跟踪
        param.put("url", request.getHeader("referer"));// 请求的地址
        return JsonUtil.toJson(param);
    }

    /**
     * 管理系统--byteBuffer转为map
     * @param interfaceUrl 
     * @param interfaceParam 
     * @param byteBuffer 
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年1月12日 下午2:17:55
     */
    public static Map<String, Object> formatNPAdminByteBuffer(String interfaceUrl, String interfaceParam, ByteBuffer byteBuffer){
        return formatByteBuffer(interfaceUrl, interfaceParam, byteBuffer, "code", "msg", "0");
    }
    /**
     * 短信网关--byteBuffer转为map
     * @param interfaceUrl 接口地址
     * @param interfaceParam 接口参数
     * @param byteBuffer 接口返回值
     * @return map
     * @author 许小满  
     * @date 2017年5月27日 下午6:59:53
     */
    public static Map<String, Object> formatSmsByteBuffer(String interfaceUrl, String interfaceParam, ByteBuffer byteBuffer){
//        return formatByteBuffer(interfaceUrl, interfaceParam, byteBuffer, "r", "msg", "0");
        return formatByteBuffer(interfaceUrl, interfaceParam, byteBuffer, "resultCode", "message", "0");
    }
    
    /**
     * 接入认证--byteBuffer转为map
     * @param interfaceUrl 
     * @param interfaceParam 
     * @param byteBuffer 
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年1月12日 下午2:17:55
     */
    public static Map<String, Object> formatAuthByteBuffer(String interfaceUrl, String interfaceParam, ByteBuffer byteBuffer){
        return formatByteBuffer(interfaceUrl, interfaceParam, byteBuffer, "resultCode", "message", "0");
    }
    
    /**
     * 设备总线
     * @param interfaceUrl 接口地址
     * @param interfaceParam 参数参数
     * @param byteBuffer byte
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 下午2:55:20
     */
    public static Map<String, Object> formatBusByteBuffer(String interfaceUrl, String interfaceParam, ByteBuffer byteBuffer){
        return formatByteBuffer(interfaceUrl, interfaceParam, byteBuffer, "resultCode", "message", "000000");
    }
    /**
     * 格式化接口返回值
     * @param interfaceUrl 接口地址
     * @param interfaceParam 接口参数
     * @param byteBuffer 接口返回值
     * @param codeKey codeKey
     * @param codeSuccessValue 接口成功的value值
     * @param msgKey msgKey
     * @return map
     * @author 许小满  
     * @date 2017年5月13日 下午7:13:52
     */
    private static Map<String, Object> formatByteBuffer(String interfaceUrl, String interfaceParam, ByteBuffer byteBuffer, 
            String codeKey, String msgKey, String codeSuccessValue){
        if(byteBuffer == null){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), interfaceUrl, interfaceParam);//接口无返回值! 
        }
        String returnMessage = null;
        try {
            returnMessage = new String(byteBuffer.array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            ErrorUtil.printException(e, logger);
            throw new BizException("E2000019", "UnsupportedEncoding");//未知异常
        }
        Map<String, Object> resultMap = JsonUtil.fromJson(returnMessage,Map.class);
        if(resultMap == null || resultMap.isEmpty()){//未得到结果
            throw new InterfaceException(MessageUtil.getMessage("E2000010"), interfaceUrl, interfaceParam);//接口返回值转map后为空！
        }
        String code = CastUtil.toString(resultMap.get(codeKey));
        if(!codeSuccessValue.equals(code)){
            String msg = (String) resultMap.get(msgKey);
            throw new InterfaceException(code, msg, interfaceUrl, interfaceParam, returnMessage);
        }
        return resultMap;
    }
    
    
    /**
     * 销售点二级分类转换
     * @param storeType 销售点二级分类
     * @return dsp
     * @author 周颖  
     * @date 2017年2月3日 下午4:32:58
     */
    public static String storeTypeDsp(Integer storeType) {
        if(storeType == null){
            return StringUtils.EMPTY;
        }
        String storeTypeDsp = null;
        switch (storeType) {
            case 1:
                storeTypeDsp = "专营店";
                break;
            case 2:
                storeTypeDsp = "自有厅";
                break;
            case 3:
                storeTypeDsp = "其他";
                break;
            default:
                storeTypeDsp = StringUtils.EMPTY;
        }
        return storeTypeDsp;
    }
    
    /**
     * 自有厅级别
     * @param storeLevel 自有厅级别
     * @return dsp
     * @author 周颖  
     * @date 2017年2月3日 下午4:35:19
     */
    public static String storeLevelDsp(Integer storeLevel) {
        if(storeLevel == null){
            return StringUtils.EMPTY;
        }
        String storeLevelDsp = null;
        switch (storeLevel) {
            case 1:
                storeLevelDsp = "1级";
                break;
            case 2:
                storeLevelDsp = "2级";
                break;
            case 3:
                storeLevelDsp = "3级";
                break;
            case 4:
                storeLevelDsp = "4级";
                break;
            case 5:
                storeLevelDsp = "5级";
                break;
            default:
                storeLevelDsp = StringUtils.EMPTY;
        }
        return storeLevelDsp;
    }
    
    /**
     * 专营店星级
     * @param storeStar 专营店星级
     * @return dsp
     * @author 周颖  
     * @date 2017年2月3日 下午4:38:18
     */
    public static String storeStarDsp(Integer storeStar) {
        if(storeStar == null){
            return StringUtils.EMPTY;
        }
        String storeStarDsp = null;
        switch (storeStar) {
            case 1:
                storeStarDsp = "1星";
                break;
            case 2:
                storeStarDsp = "2星";
                break;
            case 3:
                storeStarDsp = "3星";
                break;
            case 4:
                storeStarDsp = "4星";
                break;
            case 5:
                storeStarDsp = "5星";
                break;
            default:
                storeStarDsp = StringUtils.EMPTY;
        }
        return storeStarDsp;
    }
    
    /**
     * 专营店类别
     * @param storeScope 专营店类别
     * @return dsp
     * @author 周颖  
     * @date 2017年2月3日 下午4:44:47
     */
    public static String storeScopeDsp(Integer storeScope) {
        if (storeScope == null) {
            return StringUtils.EMPTY;
        }
        String storeScopeDsp = null;
        switch (storeScope) {
            case 1:
                storeScopeDsp = "社区店";
                break;
            case 2:
                storeScopeDsp = "商圈店";
                break;
            case 3:
                storeScopeDsp = "旗舰店";
                break;
            case 4:
                storeScopeDsp = "其他";
                break;
            default:
                storeScopeDsp = StringUtils.EMPTY;
        }
        return storeScopeDsp;
    }
    
    /**
     * 接入方式
     * @param connectType 接入方式
     * @return dsp
     * @author 周颖  
     * @date 2017年2月3日 下午4:46:03
     */
    public static String connectTypeDsp(String connectType) {
        if(StringUtils.isEmpty(connectType)){
            return StringUtils.EMPTY;
        }
        String connectTypeDsp = null;
        if("chinanet".equals(connectType)){
            connectTypeDsp = "Chinanet接入";
        } else if("fatAp".equals(connectType)){
            connectTypeDsp = "胖AP接入";
        } else if(connectType.length()>=7 && "optical".equals(connectType.substring(0, 7))){
            connectTypeDsp = "定制光猫接入";
        } else if("others".equals(connectType)){
            connectTypeDsp = "其他";
        }
        return connectTypeDsp;
    }
    
    /**
     * 判断是否是merchantType==1,即中小商户
     * @param projectId 商户id
     * @return true 是、false 否
     * @author 许小满  
     * @date 2017年8月10日 下午7:27:21
     */
    public static boolean isMerchantType1(Long projectId){
        if(projectId == null){
            logger.debug("错误:projectId为空!");
            return true;
        }
        if(projectId.equals(501L) || projectId.equals(504L) || projectId.equals(507L)){//如果是微站、园区、酒店的即标识为：中小商户
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否是merchantType==2,即项目型商户
     * @param projectId 商户id
     * @return true 是、false 否
     * @author 许小满  
     * @date 2017年8月10日 下午7:27:21
     */
    public static boolean isMerchantType2(Long projectId){
        return !isMerchantType1(projectId);
    }
    
    /**
     * 通过项目id获取对应的BelongTo
     * @param projectId 项目id
     * @return BelongTo
     * @author 亢燕翔  
     * @date 2017年2月6日 上午11:08:23
     */
    public static String formatBelongToByProjectId(Integer projectId) {
        String belongTo = null;
        switch (projectId) {
            case 501://微站
                belongTo = Constants.MWS;
                break;
            case 504://园区
                belongTo = Constants.MSP;
                break;
            case 507://酒店
                belongTo = Constants.MWH;
                break;
            default://项目型
                belongTo = Constants.TOE;
        }
        return belongTo;
    }
    
    /**
     * 行业编号转项目id
     * @param priIndustryCode 一级行业编号
     * @param secIndustryCode 二级行业编号
     * @return 项目id
     * @author 许小满  
     * @date 2017年5月22日 下午6:15:43
     */
    public static Long industryCodeToProjectId(String priIndustryCode, String secIndustryCode){
        boolean priIndustryCodeNull = StringUtils.isBlank(priIndustryCode);
        boolean secIndustryCodeNull = StringUtils.isBlank(secIndustryCode);
        if(priIndustryCodeNull && secIndustryCodeNull){
            return null;
        }
        Long projectId = null;
        if(!priIndustryCodeNull && priIndustryCode.equals("OCAB19")){//产业园区
            projectId = 504L;
        }else if(!priIndustryCodeNull && priIndustryCode.equals("OCAB20")){//完美酒店
            projectId = 507L;
        }else{
            projectId = 501L;
        }
        return projectId;
        
    }

    /**
     * 将设备类型转化成中文
     * FAT_AP:31  GPON:32 GPON_W:33  EPON:34   EPON_W:35  二合一:36  二合一:37  LAN融合：371  GPON融合：372  EPON融合：373
     * @param entityType 设备编号
     * @return 设备名称
     * @author 亢燕翔  
     * @date 2017年2月8日 上午10:29:52
     */
    public static String formatEntityType(Integer entityType) {
        if(entityType == null){//为空时默认为热点
            return "热点";
        }
        String deviceTypeDsp = null;
        if(entityType.equals(41) || entityType.equals(42)|| entityType.equals(43)){
            deviceTypeDsp = "瘦AP";
        }else if(entityType.equals(31)){
            deviceTypeDsp = "胖AP";
        }else if(entityType.equals(21)){
            deviceTypeDsp = "AC";
        }else if(entityType.equals(11)){
            deviceTypeDsp = "BAS";
        }else if(entityType.equals(32) || entityType.equals(33)){
            deviceTypeDsp = "GPON";
        }else if(entityType.equals(34) || entityType.equals(35)){
            deviceTypeDsp = "EPON";
        }else if(entityType.equals(36)){
            deviceTypeDsp = "二合一";
        }else if(entityType.equals(37)){
            deviceTypeDsp = "三合一";
        }else if(entityType.equals(371)){
            deviceTypeDsp = "LAN融合";
        }else if(entityType.equals(372)){
            deviceTypeDsp = "GPON融合";
        }else if(entityType.equals(373)){
            deviceTypeDsp = "EPON融合";
        }else if(entityType.equals(99)){
            deviceTypeDsp = "热点";
        }else{
            logger.debug("错误：entityType["+ entityType +"]超出了范围！");
        }
        return deviceTypeDsp;
    }
    
    /**
     * 获取设备状态
     * @param status 状态编号
     * @return 状态名称
     * @author 亢燕翔  
     * @date 2017年2月8日 上午10:35:16
     */
    public static String formatDeviceStatus(Integer status) {
        String statusName = null;
        switch (status) {
            case 0:
                statusName = "离线";
                break;
            case 1:
                statusName = "在线";
                break;
            case 2:
                statusName = "锁定/冻结";
                break;
            case 9:
                statusName = "作废/注销";
                break;
            default:
                statusName = "未知状态";
                break;
        }
        return statusName;
    }

    /**
     * 静态用户类型转中文
     * @param userType 静态用户类型
     * @return 显示值
     * @author 周颖  
     * @date 2017年2月9日 上午9:08:32
     */
    public static String userTypeDsp(Integer userType){
        if(userType == null){
            return "访客";
        }
        String userTypeDsp = null;
        switch (userType) {
            case 1:
                userTypeDsp = "普通员工";
                break;
            case 2:
                userTypeDsp = "VIP客户";
                break;
            case 3:
                userTypeDsp = "终端体验区";
                break;
            default:
                userTypeDsp = "访客";
        }
        return userTypeDsp;
    }
    
    /**
     * 地区全称
     * @param province 省
     * @param city 市
     * @param area 区
     * @return 地区全称
     * @author 许小满  
     * @date 2017年2月22日 下午7:05:00
     */
    public static String locationFullName(String province, String city, String area){
        return locationFullName(province, city, area, StringUtils.EMPTY);
    }
    
    /**
     * 地区全称
     * @param province 省
     * @param city 市
     * @param area 区
     * @param defaultValue 默认值
     * @return 地区全称
     * @author 许小满  
     * @date 2017年2月22日 下午7:05:00
     */
    public static String locationFullName(String province, String city, String area, String defaultValue){
        boolean provinceNotBlank = StringUtils.isNotBlank(province);
        boolean cityNotBlank = StringUtils.isNotBlank(city);
        boolean areaNotBlank = StringUtils.isNotBlank(area);
        if(!provinceNotBlank && !cityNotBlank && !areaNotBlank){//省市区都为空时，返回默认值
            return defaultValue;
        }
        StringBuffer locationFullName = new StringBuffer();
        if(provinceNotBlank){
            locationFullName.append(province);
        }
        if(cityNotBlank){
            locationFullName.append(city);
        }
        if(areaNotBlank){
            locationFullName.append(area);
        }
        return locationFullName.toString();
    }

    /**
     * 黑名单匹配规则转化
     * @param matchRule 匹配规则
     * @return 显示值
     * @author 周颖  
     * @date 2017年2月28日 下午7:13:15
     */
    public static String matchRuleDsp(Integer matchRule) {
        if(matchRule == null){
            return null;
        }
        String matchRuleDsp = null;
        switch (matchRule) {
            case 1:
                matchRuleDsp = "精确";
                break;
            case 2:
                matchRuleDsp = "模糊";
                break;
            default:
                matchRuleDsp = StringUtils.EMPTY;
        }
        return matchRuleDsp;
    }
    
    /**
     * 组件类型转化
     * @param type 组件类型: 1 引导页、2 认证页、3 过渡页、4 导航页 
     * @return 显示值
     * @author 周颖  
     * @date 2017年4月12日 下午2:36:32
     */
    public static String componentTypeDsp(String type) {
        if(StringUtils.isBlank(type)){
            return StringUtils.EMPTY;
        }
        StringBuffer typeDsp = new StringBuffer();
        if(type.indexOf("{1}") != -1){
            typeDsp.append("引导页、");
        }
        if(type.indexOf("{2}") != -1){
            typeDsp.append("认证页、");
        }
        if(type.indexOf("{3}") != -1){
            typeDsp.append("过渡页、");
        }
        if(type.indexOf("{4}") != -1){
            typeDsp.append("导航页、");
        }
        return typeDsp.substring(0, typeDsp.length()-1);
    }
    
    /**
     * 组件分类转换 
     * @param classify 组件分类 1 通用、 2 认证组件、3 过渡跳转组件
     * @return 显示值
     * @author 周颖  
     * @date 2017年4月12日 下午2:42:02
     */
    public static String componentClassifyDsp(Integer classify){
        if(classify == null){
            return StringUtils.EMPTY;
        }
        String classifyDsp = null;
        switch (classify) {
            case 1: 
                classifyDsp="通用"; 
                break;
            case 2: 
                classifyDsp="认证组件"; 
                break;
            case 3: 
                classifyDsp="过度跳转组件";
                break;
            default:
                classifyDsp = StringUtils.EMPTY;
                break;
        }
        return classifyDsp;
    }

    /**
     * 站点状态
     * @param status 状态值
     * @return 显示值
     * @author 周颖  
     * @date 2017年4月19日 下午3:10:31
     */
    public static String statusDsp(Integer status) {
        String statusDsp = null;
        switch (status) {
            case 1: 
                statusDsp="审核中"; 
                break;
            case 2: 
                statusDsp="已审核"; 
                break;
            case 3: 
                statusDsp="已发布";
                break;   
            case 4: 
                statusDsp="驳回";
                break;
            default:
                statusDsp = StringUtils.EMPTY;
                break;
        }
        return statusDsp;
    }

    /**
     * 设备名称 
     * @param entityType 设备类型
     * @param devMac 设备mac
     * @param ssid ssid
     * @param entityName 实体设备名称
     * @return 设备名称
     * @author 周颖  
     * @date 2017年5月3日 上午10:03:45
     */
    public static String formatDeviceName(Integer entityType, String devMac, String ssid, String entityName) {
        if("42".equals(entityType)||"41".equals(entityType)||"43".equals(entityType)){//瘦AP
            if (StringUtils.isBlank(devMac)){
                return ssid;
            }else if (StringUtils.isBlank(ssid)) {
                return devMac;
            } else {
                return devMac + "_" + ssid;
            }
        }else if("31".equals(entityType) && StringUtils.isNotBlank(devMac)){//胖ap
            return devMac;
        }
        return entityName;
    }
    
    /**
     * APMAC格式化（00:08:D2:ED:D0:F0）
     * @param apMacs apmac
     * @return 格式化后的mac
     * @author 亢燕翔
     * @date 2015年10月26日 下午4:12:47
     */
    public static String formatApMac(String apMacs) {
        String apMac = apMacs.toUpperCase();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < apMac.length(); i += 2) {
            if (i < apMac.length() - 2) {
                sb.append(apMac.substring(i, i + 2)).append(":");
            } else {
                sb.append(apMac.substring(i, i + 2));
            }
        }
        return sb.toString();
    }
    
    /**
     * 设备开关格式化
     * @param devSwitch 设备开关
     * @return ON 代表 开启、OFF 代表 关闭
     * @author 许小满  
     * @date 2017年6月7日 上午2:27:32
     */
    public static String formatDevSwitch(Integer devSwitch){
        if(devSwitch == null){
            return StringUtils.EMPTY;
        }
        if(devSwitch.equals(1)){
            return "ON";
        }else if(devSwitch.equals(0)){
            return "OFF";
        }else {
            logger.error("错误：设备开关["+ devSwitch +"]超出了范围[0|1]！");
            return StringUtils.EMPTY;
        }
    }
    
    /**
     * 设备开关格式化
     * @param devSwitch 设备开关
     * @return ON 代表 开启、OFF 代表 关闭
     * @author 许小满  
     * @date 2017年6月7日 上午2:27:32
     */
    public static String formatDevSwitchDsp(String devSwitch){
        if(StringUtils.isBlank(devSwitch)){
            return StringUtils.EMPTY;
        }
        if(devSwitch.equals("ON")){
            return "开启";
        }else if(devSwitch.equals("OFF")){
            return "关闭";
        }else {
            logger.error("错误：设备开关["+ devSwitch +"]超出了范围[ON|OFF]！");
            return StringUtils.EMPTY;
        }
    }
    
    /**
     * 认证类型转换
     * @param authType 认证类型
     * @return dsp
     * @author 周颖  
     * @date 2017年7月13日 上午10:44:34
     */
    public static String formatAuthType(String authType){
        if(StringUtils.isBlank(authType)){
            return StringUtils.EMPTY;
        }else if(StringUtils.equals(authType, Constants.ACCOUNT)){
            return "用户名认证";
        }else if(StringUtils.equals(authType, Constants.SMS)){
            return "手机号验证码认证";
        }else if(StringUtils.equals(authType, Constants.AUTHED)){
            return "免认证";
        }else if(StringUtils.equals(authType, Constants.WECHAT)){
            return "微信认证";
        }else if(StringUtils.equals(authType, Constants.IVR)){
            return "IVR语音认证";
        }else if(StringUtils.equals(authType, Constants.QRCODE)){
            return "二维码认证";
        }else {
            logger.debug("userauth:"+ authType + "类型错误");
            return StringUtils.EMPTY;
        }
    }

    /**
     * 天翼--接口返回值格式化
     * @param interfaceUrl 请求地址
     * @param interfaceParam 请求参数
     * @param byteBuffer 接口返回值
     * @return 结果
     * @author 周颖  
     * @date 2017年8月3日 下午5:22:31
     */
    public static Map<String,Object> formatEsurfingByteBuffer(String interfaceUrl, String interfaceParam, ByteBuffer byteBuffer) {
        return formatByteBuffer(interfaceUrl, interfaceParam, byteBuffer, "result", "msg", "0");
    }
}