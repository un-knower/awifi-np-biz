package com.awifi.np.biz.pagesrv.base.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * 字符串工具类
 * @author 张智威 2017年4月10日 上午10:07:58
 */
public class StringUtil {
    
    /**
     * DES加密方法
     * @param message 加密数据
     * @param keyString 密钥
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
     * DES解密方法
     * @param dataHexString 解密数据
     * @param keyString 密钥
     * @return 解密结果
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
    
    /**
     * 字符串转16进制字符串
     * @param str 字符串
     * @return str
     */
    private static String stringToHexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
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
     * @param hexString str
     * @return str
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
     * byte数组转换成16进制字符串
     * @param b str
     * @return str
     */
    private static String bytesToHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    /**
     * 16进制字符串转字符串
     * @param str str
     * @return a
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
    
    /**
     * charToByte 
     * @param c c
     * @return byte
     * @author 许小满  
     * @date 2017年5月22日 下午5:58:47
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * main
     * @param args 参数
     * @author 许小满  
     * @date 2017年6月20日 上午12:39:36
     */
    public static void main(String[] args) {
        String key = "xfchgfhf$sdEasdtfsd";
        String merchantOpenId = "e5aff55a077b7ef6";
        String merchantId = decryptByDes(merchantOpenId, key);
        System.out.println("提示：merchantId= " + merchantId);
        String merchantOpenId1 = encryptByDes(merchantId, key);
        System.out.println("提示：merchantOpenId= " + merchantOpenId1);
    }
}
