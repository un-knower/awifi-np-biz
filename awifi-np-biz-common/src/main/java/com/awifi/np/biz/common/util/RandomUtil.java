/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午3:53:30
* 创建作者：许小满
* 文件名称：RandomUtil.java
* 版本：  v1.0
* 功能：随机数工具类
* 修改记录：
*/
package com.awifi.np.biz.common.util;

import java.util.Random;

public class RandomUtil {

    /**
     * 得到length位长度的随机数
     * @param length 随机数的长度
     * @return 返回 length位的随机整数
     */
    public static String getRandomNumber(int length) {
        int temp = 0;
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length);
        Random rand = new Random();
        while (true) {
            temp = rand.nextInt(max);
            if (temp >= min) {
                break;
            }
        }
        return String.valueOf(temp);
    }
    
}
