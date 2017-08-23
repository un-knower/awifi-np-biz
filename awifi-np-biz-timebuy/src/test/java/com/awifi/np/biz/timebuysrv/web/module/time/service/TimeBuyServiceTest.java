package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantExtends;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantExtendsService;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;

/**
 * Created by dozen.zhang on 2017/4/18.
 */

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MessageUtil.class, RedisUtil.class, DeviceClient.class })
@PowerMockIgnore({ "javax.management.*" })
public class TimeBuyServiceTest {

    /**
     * 被测试类
     */
    private TimeBuyService timeBuyService;

    UserCutoffService userCutoffService;
    UserConsumeService userConsumeService;
    TimePackageService timePackageService;
    CenterVipUserServiceImpl vipUserService;

    MerchantExtendsService merchantExtendsService;

    /**
     * 初始化
     */
    @Before
    public void before() {
        timeBuyService = new TimeBuyService();
        userCutoffService = Mockito.mock(UserCutoffService.class);
        userConsumeService = Mockito.mock(UserConsumeService.class);
        vipUserService = Mockito.mock(CenterVipUserServiceImpl.class);
        timePackageService = Mockito.mock(TimePackageService.class);
        merchantExtendsService = Mockito.mock(MerchantExtendsService.class);
        timeBuyService.setUserCutoffService(userCutoffService);
        timeBuyService.setUserConsumeService(userConsumeService);
        timeBuyService.setTimePackageService(timePackageService);
        timeBuyService.setVipUserService(vipUserService);
        timeBuyService.setMerchantExtendsService(merchantExtendsService);
    }

    /**
     * 测试用户上网时长跟新 用户消费记录创建 领取免费礼包后更新时长 内部方法
     * 
     * @throws Exception
     * @author 张智威
     * @date 2017年4月28日 上午10:26:34
     */
    @Test
    public void testaddConsumeRecordAndUpdateTime() throws Exception {
        UserConsume userConsume = new UserConsume();
        userConsume.setMerchantId(15761l);
        userConsume.setUserId(720l);
        Long merchantId = 15761l;
        Long userId = 720l;
        int days = 2;
        Long packageId = 12l;
        int packageNum = 1;
        Float totalNum = 1f;
        String orderId = "123123";
        Float payNum = 1.0f;
        int type = 1;
        int payType = 1;

        userConsume.setMerchantId(15761l);
        userConsume.setUserId(720l);
        userConsume.setAddDay(days * 1f);
        userConsume.setPackageId(packageId);
        userConsume.setPackageNum(packageNum);
        userConsume.setTotalNum(totalNum);
        userConsume.setOrderId(orderId);
        userConsume.setPayNum(payNum);
        userConsume.setConsumeType(payType);

        // 在未来的时间
        Mockito.when(userCutoffService.selectByMerIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(new UserCutoff());
        // 更新时间
        Mockito.when(userCutoffService.updateEndDate(Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(1);
        // 或者插入时间
        Mockito.when(userCutoffService.insert(Mockito.any(UserCutoff.class))).thenReturn(1);

        // Mockito.when(userConsumeService.addCompConsume(Mockito.any(UserConsume.class)));

        // 增加消费记录并更改时长
        timeBuyService.addConsumeRecordAndUpdateTime(userConsume);
        // 测试插入
        Mockito.when(userCutoffService.selectByMerIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(null);
        timeBuyService.addConsumeRecordAndUpdateTime(userConsume);

        // 测试更新

    }

    /**
     * 测试今天剩余秒数
     * 
     * @throws ParseException
     * @author 张智威
     * @date 2017年5月15日 下午2:19:27
     */
    @Test
    public void testtodaySecondsLeft() throws ParseException {
        Date date = new Date();
        date.setHours(24);
        date.setMinutes(0);
        date.setSeconds(0);
        long nowTime = new Date().getTime();
        long left = date.getTime() - nowTime;
        left = left / 1000;
        System.out.println(left - timeBuyService.todaySecondsLeft());
        ;
        // 相差2秒都是可以的
        Assert.assertTrue(left - timeBuyService.todaySecondsLeft() <= 2);

    }

    @Test
    public void testIsVipUser() throws Exception {
        String phone = "13958173965";
        // 正式测试的时候把mock去掉就可以了
        Mockito.when(vipUserService.queryOnlineUserCount(Mockito.anyMap())).thenReturn(1);
        boolean isVipUser = timeBuyService.isVipUser(phone);

    }

    @Test
    public void testgetUserTimeInfo() throws Exception {
        Mockito.when(userConsumeService.canGetFreePkg(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
        MerchantExtends merchantExtends = new MerchantExtends();
        merchantExtends.setBuyout(0);

        Mockito.when(merchantExtendsService.selectByPrimaryKey(Mockito.anyLong())).thenReturn(merchantExtends);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.when(RedisUtil.set(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn("ok");
        UserCutoff userCutoff = new UserCutoff();
        userCutoff.setCutoffDate(new Date());
        Mockito.when(userCutoffService.selectByMerIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(userCutoff);
        timeBuyService.getUserTimeInfo(12l, "13958173965", 12l);
    }

    @Test
    public void testisBuyOoutGetUserTimeInfo() throws Exception {
        Mockito.when(userConsumeService.canGetFreePkg(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
        MerchantExtends merchantExtends = new MerchantExtends();
        merchantExtends.setBuyout(1);
        Mockito.when(merchantExtendsService.selectByPrimaryKey(Mockito.anyLong())).thenReturn(merchantExtends);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.when(RedisUtil.set(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn("ok");
        UserCutoff userCutoff = new UserCutoff();
        userCutoff.setCutoffDate(new Date());
        Mockito.when(userCutoffService.selectByMerIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(userCutoff);
        timeBuyService.getUserTimeInfo(12l, "13958173965", 12l);
    }

    @Test
    public void testGetFreePkgAndUpdateTime() throws Exception {
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.when(RedisUtil.get(Mockito.anyString())).thenReturn("ok");
        UserConsume userConsume = new UserConsume();
        userConsume.setMerchantId(15761l);
        userConsume.setUserId(720l);
        Long merchantId = 15761l;
        Long userId = 720l;
        int days = 2;
        Long packageId = 12l;
        int packageNum = 1;
        Float totalNum = 1f;
        String orderId = "123123";
        Float payNum = 1.0f;
        int type = 1;
        int payType = 1;

        userConsume.setMerchantId(15761l);
        userConsume.setUserId(720l);
        userConsume.setAddDay(days * 1f);
        userConsume.setPackageId(packageId);
        userConsume.setPackageNum(packageNum);
        userConsume.setTotalNum(totalNum);
        userConsume.setOrderId(orderId);
        userConsume.setPayNum(payNum);
        userConsume.setConsumeType(payType);

        // 在未来的时间
        Mockito.when(userCutoffService.selectByMerIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(new UserCutoff());
        // 更新时间
        Mockito.when(userCutoffService.updateEndDate(Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(1);
        // 或者插入时间
        Mockito.when(userCutoffService.insert(Mockito.any(UserCutoff.class))).thenReturn(1);

        Mockito.when(userConsumeService.canGetFreePkg(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

        // TimePackage freePkg =
        // timePackageService.queryFreePkgByMerId(merchantId);

        TimePackage freePkg = new TimePackage();
        freePkg.setPackageKey(102);
        freePkg.setPackageValue(12f);
        Mockito.when(timePackageService.queryFreePkgByMerId(Mockito.anyLong())).thenReturn(freePkg);

        timeBuyService.getFreePkgAndUpdateTime(123l, 123l);

    }

    @Test
    public void testGetBuyUrl() {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setMerchant(new Device());
        sessionDTO.setSessionUser(new SessionUser());
        String url = timeBuyService.getBuyUrl(sessionDTO);
        System.out.println("timeBuyUrl" + url);
    }
}
