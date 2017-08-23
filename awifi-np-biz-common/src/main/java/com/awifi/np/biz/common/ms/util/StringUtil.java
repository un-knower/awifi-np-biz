/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月12日 下午3:46:10
* 创建作者：尤小平
* 文件名称：StringUtil.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.ms.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class StringUtil {

    /**
     * byte数组转换成16进制字符串
     * 
     * @param b
     * @return
     */
    public static String bytesToHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (b == null || b.length <= 0) {
            return null;
        }
        for (byte aB : b) {
            int v = aB & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制字符串转成byte数组
     * 
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
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
     * 字符串转16进制字符串
     * 
     * @param str
     * @return
     */
    public static String stringToHexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (byte b : bs) {
            bit = (b & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = b & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转字符串
     * 
     * @param str
     * @return
     */
    public static String hexStringToString(String str) {
        byte[] baKeyword = new byte[str.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            str = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return str;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 加密算法
     * 
     * @param data
     *            加密数据
     * @param key
     *            秘钥
     * @return 加密结果
     */
    public static byte[] desEnCryt(byte[] data, byte[] key) {
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            // 创建Cipher对象
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            // 初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
            // 加解密
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密算法
     * 
     * @param data
     *            解密数据
     * @param key
     *            秘钥
     * @return 解密结果
     */
    public static byte[] desDeCryt(byte[] data, byte[] key) {
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * DES加密方法
     * 
     * @param message
     *            加密数据
     * @param keyString
     *            密钥
     * @return 加密结果
     */
    public static String encryptByDes(String message, String keyString) {
        String dataHexString = stringToHexString(message);
        String keyHexString = stringToHexString(keyString);
        byte[] data = hexStringToBytes(dataHexString);
        byte[] key = hexStringToBytes(keyHexString);
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytesToHexString(result);
    }

    /**
     * 判断是否是为空
     * 
     * @param str
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:09:11
     */
    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }
    /**
     * DES解密方法
     * 
     * @param dataHexString
     *            解密数据
     * @param keyString
     *            密钥
     * @return
     */
    public static String decryptByDes(String dataHexString, String keyString) {
        String keyHexString = stringToHexString(keyString);
        byte[] data = hexStringToBytes(dataHexString);
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
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStringToString(bytesToHexString(result)).trim();
    }
}
