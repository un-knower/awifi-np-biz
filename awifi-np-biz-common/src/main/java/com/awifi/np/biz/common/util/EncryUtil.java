package com.awifi.np.biz.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.DigestUtils;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 上午8:40:21
 * 创建作者：周颖
 * 文件名称：EncryUtil.java
 * 版本：  v1.0
 * 功能：加密
 * 修改记录：
 */
public class EncryUtil {

    /** 日志 */
    private static final Log logger = LogFactory.getLog(EncryUtil.class);
    
    /**
     * MD5加密
     * @param str 字符串
     * @return 加密后的字符串
     * @author 许小满
     * @date 2015年7月8日 下午4:50:39
     */
    public static String getMd5Str(String str) {
        if (str == null){
            return StringUtils.EMPTY;
        }
        byte[] pb = null;
        try {
            pb = str.getBytes("utf-8");
        } catch (Exception e) {
            ErrorUtil.printException(e, logger);
        }
        return DigestUtils.md5DigestAsHex(pb);
    } 
    
   /**
    * 判断是否超时 毫秒
    * @param timestamp 时间戳
    * @param time 有效时间
    * @return true 超时 false 未超时
    * @author 周颖  
    * @date 2017年1月12日 下午3:10:04
    */
    public static boolean isTimeout(String timestamp,Long time){
        long currentTime = System.currentTimeMillis();//当前时间(毫秒)
        if(Math.abs(Long.parseLong(timestamp) - currentTime) > time) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否超时 秒
     * @param timestamp 时间戳
     * @param time 有效时间
     * @return true 超时 false 未超时
     * @author 周颖  
     * @date 2017年1月12日 下午3:10:04
     */
    public static boolean isAppTimeout(String timestamp,Long time){
        long currentTime = System.currentTimeMillis() / 1000;//当前时间(毫秒)
        if(Math.abs(Long.parseLong(timestamp) - currentTime) > time) {
            return true;
        }
        return false; 
    }
    
    /**
     * 根据传入的参数计算token
     * @param code 编号
     * @param key 密钥
     * @param timestamp 时间戳
     * @return  token
     * @author 许小满  
     * @date 2017年2月15日 下午3:49:29
     */
    public static String generateToken(String code, String key, String timestamp) {
        String result = code + key + timestamp;
        return getMd5Str(result);
    }
    
    /**
     * @param appId app编号
     * @param appKey app密钥
     * @param timestamp 时间戳
     * @return token
     * @author 王冬冬  
     * @date 2017年7月10日 下午3:06:21
     */
    public static String generateAppToken(String appId, String appKey, String timestamp) {
        String result = appId +"_"+ appKey + "_"+timestamp;
        return getMd5Str(result);
    }

    /**
     * 根据传入的参数计算token
     * @param secretKey 秘钥
     * @param timestamp 时间戳
     * @return  token
     * @author 许小满  
     * @date 2015年11月30日 下午3:06:59
     */
    public static String generateToken(String secretKey, String timestamp) {
        String result = secretKey + "_" + timestamp;
        return getMd5Str(result);
    }
    
    /**
     * DES加密方法
     * @param message 加密数据
     * @param keyString 密钥
     * @return 加密结果
     */
    public static String encryptByDes(String message, String keyString) {
        try {
            if(StringUtils.isEmpty(message) || StringUtils.isEmpty(keyString)){//当 加密数据或密钥 为空时，不做加密操作
                return StringUtils.EMPTY;
            }
            String keyHexString = stringToHexString(keyString);
            byte[] key = hexStringToBytes(keyHexString);
            String dataHexString = stringToHexString(message);
            byte[] data = hexStringToBytes(dataHexString);
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
            byte[] result = cipher.doFinal(data);
            return new String(Base64.encodeBase64(result));
        } catch (Exception e) {
            ErrorUtil.printException(e, logger);
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * DES解密方法
     * @param dataHexString 解密数据
     * @param keyString 密钥
     * @return 解密后的值 
     */
    public static String decryptByDes(String dataHexString, String keyString) {
        if(StringUtils.isEmpty(dataHexString) || StringUtils.isEmpty(keyString)){//当 解密数据或密钥 为空时，不做加密操作
            return StringUtils.EMPTY;
        }
        String keyHexString = stringToHexString(keyString);
        byte[] key = hexStringToBytes(keyHexString);
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(Base64.decodeBase64(dataHexString));
            return new String(result).trim();
        } catch (Exception e) {
            //ErrorUtil.printException(e, logger);
            logger.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 字符串转16进制字符串
     * @param str 字符串
     * @return 转后的字符串
     */
    public static String stringToHexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }
    
    /**
     * 16进制字符串转成byte数组
     * @param hexString 字符串
     * @return 转后的字符串
     */
    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        int hexLength = length;
        while (hexLength % 8 != 0) {
            hexLength++;
        }
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[hexLength];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * 
     * @param c 字符
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * 测试函数
     * @param args 参数
     * @author 许小满  
     * @date 2017年2月15日 下午3:47:16
     */
    public static void main(String[] args) {
        String code = "S_APP";
        String key = "ErB9Q2QPa5D8BWFv6j9hEpxguZ09fcJn";
        String timestamp = "1500947490740";
        //String timestamp = Long.toString(System.currentTimeMillis());
        String token = EncryUtil.generateToken(code, key, timestamp);
        //System.out.println("timestamp=" + timestamp);
        System.out.println("token=" + token);
        
        /*String appId = "ecd6b7ed";
        String appKey = "28e57302167d";
        String timestamp = "1500290442";
        String token = generateAppToken(appId, appKey, timestamp);
        System.out.println("token="+token+"&appid="+appId+"&timestamp="+timestamp);*/
    }
}