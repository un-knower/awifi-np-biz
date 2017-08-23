package com.awifi.np.biz.timebuysrv.util;

public class MsCommonUtil {
    public static String TRANSFER_SAFETY_PASSWORD = "qwerty123456";// 双方约定密码
    public static String TRANSFER_SAFETY_SECRETKEY = "a1b2c3d4";// 双方约定密钥
    public static long TRANSFER_SAFETY_CHECKTIME = 600000l;// 约定超时时间


    /**
     * 获得数据中心des秘钥
     * token(String) 加密后（DES加密）的字符串（由：13位时间戳+5为随机数+双方约定密码），不能为空
     *
     * @author kjl
     * @date 2015-11-7
     */
    public static String getCenterToken(String randNum) throws Exception {
        StringBuilder data = new StringBuilder().append(System.currentTimeMillis()).append(randNum).append(TRANSFER_SAFETY_PASSWORD);
        return StringUtil.encryptByDes(data.toString(), TRANSFER_SAFETY_SECRETKEY);
    }

    public static void  main(String[] args) {
        try {
            System.out.println(getCenterToken(Tools.rand(5)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
