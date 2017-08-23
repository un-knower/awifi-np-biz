package com.awifi.np.biz.common.util.xxtea;

/**
 * 创 建 者：王家宾
 * 创建日期：    2008-04-14
 * 重大修改记录：
 *             2008-04-14 by 王家宾 ： 创建
 * 
 * Copyright (C) 2008 - 版权所有   2008 21CN Corp. Ltd
 */

/**
 * XXTea加解密实现
 */
public class XXTeaUtil {
    
    /** 字符集 */
    private static final String CHARSET = "UTF-8";

    /**
     * 加密
     * @param plain 明文
     * @param hexKey 十六进制字符串形式密钥
     * @return 十六进制字符串形式密文
     * @throws Exception 
     */
    public static String encrypt(String plain, String hexKey) throws Exception {
        if (plain == null || hexKey == null) {
            return null;
        }
        
        return ByteFormat.toHex(encrypt(plain.getBytes(CHARSET), ByteFormat.hexToBytes(hexKey)));
    }
    
    /**
     * 解密
     * @param cipherHex 十六进制字符串形式密文
     * @param hexKey 十六进制字符串形式密钥
     * @return 明文
     * @throws Exception 
     */
    public static String decrypt(String cipherHex, String hexKey) throws Exception{
        if (cipherHex == null || hexKey == null) {
            return null;
        }
        return new String(decrypt(ByteFormat.hexToBytes(cipherHex), ByteFormat.hexToBytes(hexKey)), CHARSET);
    }
    
    /**
     * 加密
     * @param plainData 明文
     * @param key 密钥
     * @return byte[]
     */
    public static byte[] encrypt(byte[] plainData, byte[] key) {
        if (plainData == null || plainData.length == 0 || key == null) {
            return null;
        }
        return toByteArray(encrypt(toIntArray(plainData, true), toIntArray(key, false)), false);
    }

    /**
     * 解密
     * @param cipherData 密文
     * @param key 密钥
     * @return 明文
     */
    public static byte[] decrypt(byte[] cipherData, byte[] key) {
        if (cipherData == null || cipherData.length == 0 || key == null) {
            return null;
        }
        return toByteArray(decrypt(toIntArray(cipherData, false), toIntArray(key, false)), true);
    }

    /**
     * Encrypt data with key.
     * @param v 
     * @param k 
     * @return int[]
     */
    private static int[] encrypt(int[] v, int[] k) {
        int n = v.length - 1;

        if (n < 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];

            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n];
        int y = v[0];
        int delta = 0x9E3779B9;
        int sum = 0;
        int e;
        int p = 6 + 52 / (n + 1);
        int q = p;

        while (q-- > 0) {
            sum = sum + delta;
            e = sum >>> 2 & 3;
            for (p = 0; p < n; p++) {
                y = v[p + 1];
                z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                        + (k[p & 3 ^ e] ^ z);
            }
            y = v[0];
            z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                    + (k[p & 3 ^ e] ^ z);
        }
        return v;
    }

    /**
     * Decrypt data with key.
     * @param v 
     * @param k 
     * @return int[]
     */
    private static int[] decrypt(int[] v, int[] k) {
        int n = v.length - 1;
        if (n < 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];

            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n];
        int y = v[0];
        int delta = 0x9E3779B9;
        int sum;
        int e;
        int p = 6 + 52 / (n + 1);
        int q = p;

        sum = q * delta;
        while (sum != 0) {
            e = sum >>> 2 & 3;
            for (p = n; p > 0; p--) {
                z = v[p - 1];
                y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                        + (k[p & 3 ^ e] ^ z);
            }
            z = v[n];
            y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                    + (k[p & 3 ^ e] ^ z);
            sum = sum - delta;
        }
        return v;
    }

    /**
     * Convert byte array to int array.
     * @param data 
     * @param includeLength 
     * @return int[]
     */
    private static int[] toIntArray(byte[] data, boolean includeLength) {
        int n = (((data.length & 3) == 0) ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;

        if (includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }

    /**
     * Convert int array to byte array.
     * @param data 
     * @param includeLength 
     * @return byte[]
     */
    private static byte[] toByteArray(int[] data, boolean includeLength) {
        int n = data.length << 2;
        if (includeLength) {
            int m = data[data.length - 1];

            if (m > n || m <= 0) {
                return null;
            } else {
                n = m;
            }
        }
        byte[] result = new byte[n];

        for (int i = 0; i < n; i++) {
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
        }
        return result;
    }

    /** 
     * 测试
     * @param args 参数
     * @throws Exception 
     * @author 许小满  
     * @date 2017年8月2日 下午3:48:05
     */
    public static void main(String[] args) throws Exception{
        long t1 = System.currentTimeMillis();
        String str = "709179";
        String key = "aWiFi@123";
        String str1 = XXTeaUtil.encrypt(str, ByteFormat.toHex(key.getBytes()));//加密
        System.out.println("加密前的字符串：" + str);
        System.out.println("加密密钥：" + key);
        System.out.println("加密后的字符串：" + str1);
        System.out.println("------------------------------------");
        String str2 = XXTeaUtil.decrypt(str1, ByteFormat.toHex(key.getBytes()));//解密
        System.out.println("解密前的字符串：" + str1);
        System.out.println("解密密钥：" + key);
        System.out.println("解密后的字符串：" + str2);
        System.out.println("XXTea加解密共花费了" + (System.currentTimeMillis()-t1) + "ms.");
    }

}
