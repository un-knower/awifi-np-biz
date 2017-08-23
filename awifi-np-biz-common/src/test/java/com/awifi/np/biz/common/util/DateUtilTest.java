package com.awifi.np.biz.common.util;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午2:53:13
 * 创建作者：亢燕翔
 * 文件名称：DateUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
public class DateUtilTest {

    /**被测试类*/
    @InjectMocks
    private DateUtil dateUtil;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 根据指定格式转换成日期字符串
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:02:32
     */
    @Test
    public void testFormatToString(){
        Date date = new Date();
        dateUtil.formatToString(date, "yyyy-MM-dd");
    }
    
    /**
     * 获取今天日期yyyy-MM-dd
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:02:41
     */
    @Test
    public void testGetTodayDate(){
        dateUtil.getTodayDate();
    }
    
    /**
     * getTodayDateDate
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:02:53
     */
    @Test
    public void testGetTodayDateDate(){
        dateUtil.getTodayDateDate();
    }
    
    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:03:02
     */
    @Test
    public void testGetNow(){
        dateUtil.getNow();
    }
    
    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:03:14
     */
    @Test
    public void testGetNowDate(){
        dateUtil.getNowDate();
    }
    
    /**
     * 解析时间转为＂yyyy-MM-dd＂
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:03:33
     */
    @Test
    public void tesTparseToDate(){
        dateUtil.parseToDate("xxx", "yyyy-MM-dd");
    }
    
    /**
     * 时间格式转换
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:07:24
     */
    @Test
    public void testParseToDateTryYMD(){
        dateUtil.parseToDateTry("2017-03-16");
    }
    
    /**
     * 时间格式转换
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:07:24
     */
    @Test
    public void testParseToDateTryYMDHMS(){
        dateUtil.parseToDateTry("2017-03-16 15:08:52");
    }
    
    /**
     * 时间格式转换
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:07:24
     */
    @Test
    public void testParseToDateTryYMDHMSS(){
        dateUtil.parseToDateTry("20170316150852");
    }
    
    /**
     * 时间格式转换
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:07:24
     */
    @Test
    public void testParseToDateTryYYYYMMDD(){
        dateUtil.parseToDateTry("20170316");
    }
    
    /**
     * 时间格式转换
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:07:24
     */
    @Test
    public void testParseToDateTryyyyy(){
        dateUtil.parseToDateTry("2017/03/16 15:08");
    }
    
    /**
     * 时间格式转换
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:07:24
     */
    @Test
    public void testParseToDateTrnull(){
        dateUtil.parseToDateTry("");
    }
    
    /**
     * 格式化毫秒数
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:12:35
     */
    @Test
    public void testFormatMillisecond(){
        Long time = System.currentTimeMillis();
        dateUtil.formatMillisecond(time);
    }
    
    /**
     * 判断日期格式是否正确
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:13:52
     */
    @Test
    public void testCheckDate(){
        dateUtil.checkDate("2015-15-16", "yyyy-MM-dd");
    }
    
    /**
     * 判断字符串是否是合法日期格式
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:14:41
     */
    @Test
    public void testCheckStrDate(){
        dateUtil.checkStrDate("yyyy-MM-dd");
    }
    
    /**
     * excel日期解析 返回Date
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:15:24
     */
    @Test
    public void testConvertDate4JXL(){
        dateUtil.convertDate4JXL(new Date());
    }
    
    /**
     * excel日期解析 返回Date
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:15:24
     */
    @Test
    public void testStrDate4JXL(){
        dateUtil.strDate4JXL(new Date());
    }
    
    /**
     * 返回一个月份的最后一天
     * @author 亢燕翔  
     * @date 2017年3月22日 下午3:16:48
     */
    @Test
    public void testGetMonthLastDay(){
        Long time = System.currentTimeMillis();
        dateUtil.getMonthLastDay(time.toString());
    }
    
    /**
     * 日期增加
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:25:50
     */
    @Test
    public void testDayAdd(){
        dateUtil.dayAdd("2015-12-15", 2);
    }
    
    /**
     * 月份相加
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:26:47
     */
    @Test
    public void testMonthAdd(){
        dateUtil.monthAdd("2015-12-15", 2);
    }
    
    /**
     * 日期相减
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:27:46
     */
    @Test
    public void testDaySubDay(){
        dateUtil.daySubDay("2015-12-15", "2015-12-12");   
    }
    
    /**
     * 月份相减
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:30:26
     */
    @Test
    public void testMonthSubMonth() throws Exception{
        dateUtil.monthSubMonth("2015-12-15", "2015-12-12");
    }
    
    /**
     * 转换为 yyyy-MM-dd 格式的日期数据
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:30:42
     */
    @Test
    public void testFormatDate(){
        Long time = System.currentTimeMillis();
        dateUtil.formatDate(time);
    }
    
    /**
     * 转换为 yyyy-MM-dd HH:mm:ss 格式的日期数据
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:31:22
     */
    public void testFormatTimestamp(){
        Long time = System.currentTimeMillis();
        dateUtil.formatTimestamp(time);
    }
    
    /**
     * 日期 转 时间戳
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月23日 下午1:31:52
     */
    @Test
    public void testGetDateMills() throws Exception{
        dateUtil.getDateMills("2017-03-23");
    }
    
    /**
     * 日期 转 时间戳
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月23日 下午1:33:03
     */
    @Test
    public void testgetWithMiuMills() throws Exception{
        dateUtil.getWithMiuMills("2017-03-23 13:38");
    }
    
    /**
     * 日期 转 时间戳
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月23日 下午1:39:10
     */
    @Test
    public void testGetTimestampMills() throws Exception{
        dateUtil.getTimestampMills("2017-03-23 13:38:35");
    }
    
    /**
     * 获取时间差
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月23日 下午1:40:30
     */
    @Test
    public void testDiffTime() throws Exception{
        dateUtil.diffTime("2017-03-23 13:38:35", "2017-01-23 13:38:35");
    }
    
    /**
     * 将分钟转换为 天时分
     * @author 亢燕翔  
     * @date 2017年3月23日 下午1:41:24
     */
    @Test
    public void testGetTimeByMimute(){
        dateUtil.getTimeByMimute(15);
    }
    
    @Test
    public void testYearAdd() {
        dateUtil.yearAdd("2017-03-23 13:38:35", 10);
    }
    
}
