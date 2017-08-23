/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月12日 下午3:40:41
* 创建作者：尤小平
* 文件名称：MsCommonUtil.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.ms.util;


public class MsCommonUtil {
    private static final String SECRET_FOR_GET_OPENID = "xfchgfhf$sdEasdtfsd";
    public static String TRANSFER_SAFETY_PASSWORD = "qwerty123456"; // 双方约定密码
    public static String TRANSFER_SAFETY_SECRETKEY = "a1b2c3d4"; // 双方约定密钥
    public static long TRANSFER_SAFETY_CHECKTIME = 600000l; // 约定超时时间

    /**
     * 获得数据中心des秘钥 token(String) 加密后（DES加密）的字符串（由：13位时间戳+5为随机数+双方约定密码），不能为空
     * 
     * @throws Exception
     *             异常
     * @param randNum
     *            随机数
     * @author kjl
     * @date 2015-11-7
     * @return 加密后字段
     */
    public static String getCenterToken(String randNum) throws Exception {
        StringBuilder data = new StringBuilder().append(System.currentTimeMillis()).append(randNum)
                .append(TRANSFER_SAFETY_PASSWORD);
        return StringUtil.encryptByDes(data.toString(), TRANSFER_SAFETY_SECRETKEY);
    }

    /**
     * 得到openId
     * 
     * @param data
     * @return
     * @author 张智威
     * @date 2017年8月10日 下午6:38:47
     */
    public static String getOpenId(Object data) {
        return StringUtil.encryptByDes(data.toString(), SECRET_FOR_GET_OPENID);
    }

    /**
     * 生成对外服务的token
     * @param timeStamp
     * @param appId
     * @param secretKey
     * @return
     * @author 张智威  
     * @date 2017年8月10日 下午6:39:21
     */
    public static String createToken(Long timeStamp, String appId, String secretKey) {
        StringBuffer data = new StringBuffer(String.valueOf(timeStamp)).append(",").append(appId).append(",")
                .append(secretKey);
        return MD5.md5(data.toString());
    }
}
