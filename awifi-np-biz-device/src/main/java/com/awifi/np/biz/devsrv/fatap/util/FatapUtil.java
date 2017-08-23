package com.awifi.np.biz.devsrv.fatap.util;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubPlatform;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.util.RegexUtil;

/**
 * 
 * @ClassName: FatapUtil
 * @Description: 省分平台公共方法
 * @author wuqia
 * @date 2017年5月15日 上午11:31:11
 *
 */
public class FatapUtil {
    /**
     * 系统名称
     */
    private static String OS = System.getProperty("os.name").toLowerCase();
    /**
     * 省分平台返回数据 省市区编码转名称
     * @param cpp 平台
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月14日 上午10:21:29
     */
    public static void getCenterPubNameFromId(CenterPubPlatform cpp)
            throws Exception {
        // 省转换
        if (cpp.getProvince() != null) {
            cpp.setProvinceName(LocationClient.getByIdAndParam((long) cpp.getProvince(), "name"));
        }
        if (cpp.getCity() != null) {
            cpp.setCityName(LocationClient.getByIdAndParam((long) cpp.getCity(),"name"));
        }
        if (cpp.getCounty() != null) {
            cpp.setCountyName(LocationClient.getByIdAndParam((long) cpp.getCounty(), "name"));
        }
    }
    /**
     * 根据层级获取目录格式
     * @param n 层级
     * @return 目录
     * @author 伍恰  
     * @date 2017年6月28日 下午1:48:04
     */
    public static String getPathFomat(Integer n){
        StringBuilder path = new StringBuilder();
        if(isLinux()){
            for(int i=0; i<n; i++){
                path.append("%s\\");
            }
        }else if(isWindows()){
            for(int i=0; i<n; i++){
                path.append("%s/");
            }
        }
        return path.toString();
    }
    
    public static boolean isLinux(){  
        return OS.indexOf("linux")>=0;  
    }
    
    public static boolean isWindows(){  
        return OS.indexOf("windows")>=0;  
    }
    
    /**
     * 域名校验
     * @param url 地址
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年7月24日 上午10:18:58
     */
    public static boolean isUrl(String url) {
        return RegexUtil.match(url, RegexConstants.URL);
    }
}
