package com.awifi.np.biz.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA1Util {

    /** */
    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * 生成签名数据
     * @param data 待加密的数据
     * @param key 加密使用的key
     * @throws Exception 
     * @return String
     */
    public static String getSignature( String data, String key ) throws Exception {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec( keyBytes, HMAC_SHA1 );
        Mac mac = Mac.getInstance( HMAC_SHA1 );
        mac.init( signingKey );
        byte[] rawHmac = mac.doFinal( data.getBytes() );
        StringBuilder sb = new StringBuilder();
        for( byte b : rawHmac ) {
            sb.append( byteToHexString( b ) );
        }
        return sb.toString();
    }

    /**
     * 
     * @param ib 
     * @return String
     */
    private static String byteToHexString( byte ib ) {
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] ob = new char[2];
        ob[0] = digit[(ib >>> 4) & 0X0f];
        ob[1] = digit[ib & 0X0F];
        String s = new String( ob );
        return s;
    }
    
    /**
     * 测试
     * @param args 参数
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年8月2日 下午4:04:48
     */
    public static void main(String[] args) throws Exception{
        String key = "uoquewryuqwyuery"; 
        String plainSig = "abcdse";
        System.out.println("sign= "+ getSignature(plainSig,key));
    }
}
