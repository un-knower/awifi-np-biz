package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantExtends;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantExtendsService;
import com.awifi.np.biz.timebuysrv.third.access.bean.DevParms;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.util.ConfigUtil;

import redis.clients.jedis.Jedis;

/**
 * 时间总承服务
 * 
 * @author 张智威 2017年4月10日 下午3:32:39
 */
@Service
public class TimeBuyService {
    /** 日志服务 **/
    private static Logger logger = LoggerFactory.getLogger(TimeBuyService.class);
    /**  vip用户服务 **/
    @Resource
    private CenterVipUserServiceImpl vipUserService;
    /**  用户剩余时长服务 **/
    @Resource
    private UserCutoffService userCutoffService;
    /** * 引入 userConsumeService 用户消费服务 */
    @Resource
    private UserConsumeService userConsumeService;
    /** timePackageService 时长礼包服务 **/
    @Resource
    private TimePackageService timePackageService;
    /** wifi 服务 **/
    @Resource
    private WifiService wifiService;
    
    @Resource
    private MerchantExtendsService merchantExtendsService;
    /**
     * . 用户上网时长跟新 用户消费记录创建 领取免费礼包后更新时长 内部方法
     * @param consume 包含以下有用参数
     * param merchantId 商户id
     *            
     * param userId 用户id
     *            
     * param days 增加天数
     *            
     * param packageId 套餐包id
     *            
     * param packageNum 套餐包数量
     *            
     * param totalNum 套餐价值
     *            
     * param orderId 对应订单编号
     *            
     * param payNum 实付金额
     * @return int 增加的时长
     * @author 张智威
     * @throws Exception 异常
     */
    
    public long addConsumeRecordAndUpdateTime(UserConsume consume) throws Exception {
        logger.info("增加时长信息:"+JsonUtil.toJson(consume));
        //=======校验参数==========
        if (consume.getConsumeType() == 1 || consume.getConsumeType() == 3) {
            if (StringUtil.isBlank(consume.getOrderId())) {
                logger.error("pay type 为1 说明是支付 但是为什么orderid 是空的");
                throw new BizException("E4004004", MessageUtil.getMessage("E5054003"));// E4004004=支付参数错误
            }
        }
        Date nowtime = new Date();
        Date startime = nowtime;
        if (consume.getMerchantId()== null || consume.getUserId() == null) {
            logger.error("merchantId userId 不能为空");
            throw new BizException("E4004004", MessageUtil.getMessage("E5054003"));// E4004004=支付参数错误
        }
        //=========== 获取用户时长 用来填写增加后的时长
        UserCutoff userCutoff = userCutoffService.selectByMerIdAndUserId(consume.getMerchantId(),consume.getUserId());
       
        Calendar calendar = new GregorianCalendar();
        
        consume.setRemarks("");
           
        if (userCutoff != null) {
            // 存在记录
            // LogUtil.track(serviceCode, 123, jsonStr, "has record ", null);
            logger.debug("has record");
            //=========== 如果用户在商户下有时长 累加=============
            if (userCutoff.getCutoffDate() != null && userCutoff.getCutoffDate().getTime() > nowtime.getTime()) {
                // 如果还有上网时长则增加时长
                startime = userCutoff.getCutoffDate();
                calendar.setTime(userCutoff.getCutoffDate());
                calendar.add(Calendar.HOUR, Math.round(consume.getAddDay()*24));
               
            } else {
            //=========== 如果用户在商户下时长过期了就要重新计时=============
                // 如果时长过期则当前时间起算
                calendar.setTime(nowtime);
                calendar.add(Calendar.HOUR, Math.round(consume.getAddDay()*24));
            }
            
            consume.setBeginDate(startime);
            consume.setEndDate(calendar.getTime());
            userCutoff.setCutoffDate(calendar.getTime());
            //===========修改用户在商家下的上网时长================
            userCutoffService.updateEndDate(userCutoff.getId(), userCutoff.getCutoffDate());
        } else {
            logger.debug("has no record begin add one consume data");
            // LogUtil.track(serviceCode, 123, jsonStr, "has no record begin
            // add", null);

            //===========新增用户在商家下的上网时长=============
            calendar.setTime(nowtime);
            calendar.add(Calendar.HOUR,  Math.round(consume.getAddDay()*24));
            userCutoff = new UserCutoff();
            userCutoff.setUserId(consume.getUserId());
            userCutoff.setMerchantId(consume.getMerchantId());
            userCutoff.setCutoffDate(calendar.getTime());//设置截止时间
            
            userCutoffService.insert(userCutoff);
            consume.setBeginDate(startime);
            consume.setEndDate(calendar.getTime());

        }
        //========新增消费记录======
        /*
         * LogUtil.track(serviceCode, 147, JsonUtils.toJsonString(consume),
         * "begin iUserConsumeService.addCompConsume  ", null);
         */
        logger.info("begin iUserConsumeService.addCompConsume");

        userConsumeService.addCompConsume(consume);
        
        
        logger.info("iUserConsumeService.addCompConsume success"+JsonUtil.toJson(consume));
        /*
         * LogUtil.track(serviceCode, 157, "",
         * "iUserConsumeService.addCompConsume success ", null);
         */

        return consume.getEndDate().getTime();
    }

 
  
    /**
     * 计算今天剩余时长 当日过期毫秒剩余 临时放通需要用
     * @return 剩余时长
     * @throws ParseException 异常
     * @author 张智威  
     * @date 2017年5月4日 下午7:42:34
     */
    public int todaySecondsLeft() throws ParseException {
        Date date = new Date();
        date.setDate(date.getDate() + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse(sdf.format(date));
        return (int) ((date.getTime() - System.currentTimeMillis()) / 1000);
    }

    /**
     * 判断是否是vip用户
     * @param phone
     * @throws Exception 异常
     * @author 张智威
     * @date 2017年4月26日 下午4:51:04
     * @return 返回是否vip用户
     */
    public boolean isVipUser(String phone) throws Exception {
        HashMap<String,Object> param = new HashMap<>();
        param.put("telephone", phone);//手机号是当前用户的 并且 vip会员时间截止时间大于当前时间
        return vipUserService.isVipUser(Long.valueOf(phone));
    }
//
    /**
     * 获取可以返回前端的用户时长封装bean dto
     * @param userId 用户id
     * @param phone 手机号码
     * @param merchantId 商户id
     * @return UserTimeInfo 用户时长信息
     * @throws Exception 异常
     * @author 张智威  
     * @date 2017年5月4日 下午7:47:07
     */
    public UserTimeInfo getUserTimeInfo(Long userId, String phone, Long merchantId) throws Exception {
        boolean isVipUser = isVipUser(phone);//判断是否是vip
        
        List<TimePackage> list=timePackageService.packageListSearch(merchantId, 2);
        if(list.size()<0){
            logger.warn("merchant:"+merchantId+"has not set the free package ");
        }
        // 查询用户是否能领取免费礼包
        boolean canGetFreePkg = userConsumeService.canGetFreePkg(userId, merchantId);
        if (canGetFreePkg) {
            TimePackage timePackage = timePackageService.queryFreePkgByMerId(merchantId);
            if (timePackage != null && timePackage.getPackageValue() !=null && Math.round(timePackage.getPackageValue()*24 )== 0) { //如果配置的时间是0 的话
                canGetFreePkg = false;
            }
        }

        if (canGetFreePkg) {
            // 标志位 有这个标志位才能领取免费礼包
            RedisUtil.set(Constants.REDIS_PKG_GET + userId + "" + merchantId, "ok", Constants.HALF_HOUR_SESCONDS);
        }
        UserTimeInfo userTimeInfo = new UserTimeInfo();
        userTimeInfo.setCanGetFreePkg(canGetFreePkg);
        userTimeInfo.setVip(isVipUser);
        
        //查找是否是买断商户
        MerchantExtends merchantExtends = merchantExtendsService.selectByPrimaryKey(merchantId);
        if(merchantExtends!=null &&merchantExtends.getBuyout()!=0){
            userTimeInfo.setBuyout(true);
        }else{
            userTimeInfo.setBuyout(false);
        }
        
        
        UserCutoff userCutoff = userCutoffService.selectByMerIdAndUserId(merchantId, userId);//查找该商户下该用户的剩余时长
        if (userCutoff != null) {//如果为空说明没有时长 并且没有领取过免费礼包
            userTimeInfo.setEndTime(userCutoff.getCutoffDate().getTime());
        }
        logger.info("userId:"+userId+"merchantId:"+merchantId+"套餐情况:"+JsonUtil.toJson(userTimeInfo));
        return userTimeInfo;
    }

   
    /**
     * 
     * @param merchantId 商户id
     * @param userId 用户id
     * @return 返回增加的天数 增加后的时间 token
     * @author 张智威
     * @throws Exception 异常
     * @date 2017年4月10日 下午4:58:36
     */
    public Map<String,Object> getFreePkgAndUpdateTime(Long merchantId, Long userId) throws Exception {

        if (merchantId == null || userId == null) {
            logger.error("商户id 和用户id 丢失 用户未登录");
            throw new ValidException("E4005001", MessageUtil.getMessage("E4005001"));// 用户未登录
        }

        String canGetFreePkgFlag = RedisUtil.get(Constants.REDIS_PKG_GET + userId + "" + merchantId);
        // 查询是否已经领取过免费礼包
        if (!"ok".equals(canGetFreePkgFlag) || !userConsumeService.canGetFreePkg(userId, merchantId)) {
            logger.error("用户不能领取免费礼包");// 正常情况下用户是不能访问到这里的 除非前端逻辑错误
            throw new ValidException("E4005002", MessageUtil.getMessage("E4005002"));// 用户不能领取免费礼包
        }
        // 此处逻辑 如果数据库缺少默认数据补充一条默认的3天免费礼包数据
        TimePackage freePkg = timePackageService.queryFreePkgByMerId(merchantId);
        if (freePkg == null) {
            freePkg = timePackageService.queryFreePkgByMerId(-3l);//获取默认配置
            if (freePkg == null) {
                TimePackage merchantPackage  = new TimePackage();
                merchantPackage.setMerchantId(-3l);
                merchantPackage.setCreateDate(new Date());
                merchantPackage.setEffectDatetime(new Date());
                merchantPackage.setExpiredDatetime(com.awifi.np.biz.timebuysrv.util.DateUtil.parseToDate("2100-07-07","yyyy-MM-dd"));
                merchantPackage.setPackageKey(201);
                merchantPackage.setPackageType(2);
                merchantPackage.setPackageValue(3f);
                merchantPackage.setStatus(1);//可用
                merchantPackage.setStatusDate(new Date());
                timePackageService.add(merchantPackage);
                freePkg = timePackageService.queryFreePkgByMerId(-3l);//获取默认配置
                if(freePkg == null){
                    throw new BizException("E4005004", "该商户未设置免费套餐礼包");
                }
            }
            
        }
        logger.debug("begin get free pkg " + "" + merchantId + userId + ":"
                + (int) (freePkg.getPackageValue().floatValue()) + ":" + freePkg.getId());
        
        UserConsume userConsume =new UserConsume();
        userConsume.setMerchantId(merchantId);
        userConsume.setUserId(userId);
        userConsume.setPackageId(freePkg.getId());
        userConsume.setAddDay(  freePkg.getPackageValue().floatValue());    //按小时数来加
        userConsume.setPackageNum(1);
        userConsume.setTotalNum(0f);
        userConsume.setOrderId("");
        userConsume.setPayNum(0f);
        userConsume.setConsumeType(2);
        userConsume.setRemarks("小时");
        //增加消费记录并更新时长
        long endTime = this.addConsumeRecordAndUpdateTime(userConsume);
               // (int) (freePkg.getPackageValue().floatValue()), freePkg.getId(), 1, (float) 0, "", (float) 0, 1, 2);
        RedisUtil.del(Constants.REDIS_PKG_GET + userId + "" + merchantId);

        // 上网时长
        Map<String,Object> ret = new HashMap<>();
        // 增加的小时数
        ret.put("add_hour", (freePkg.getPackageValue().floatValue()));

        // 认证上网的token
        ret.put("end_time", endTime);
        ret.put("nowTime", new Date().getTime());
        return ret;
    }
    
   
    /**
     * 获取购买链接
     * @param sessionDTO
     * @return
     * @author 张智威  
     * @date 2017年5月9日 下午5:16:49
     */
    public String getBuyUrl(SessionDTO sessionDTO){
        StringBuffer sb =new StringBuffer();
        if(sessionDTO.getSessionUser()== null){ // 未登录的时候拉取的时候是没有用户信息的
            return "";
        }
        sb.append(ConfigUtil.getConfig("dq.buy.url")).append("&link_phone=").append(sessionDTO.getSessionUser().getPhone())
        .append("&code_number=").append(sessionDTO.getMerchant().getBroadbandAccount()).append("&yxgh=").append(sessionDTO.getMerchant().getMerchantId());
        return sb.toString();
    }

    /**
     * 根据商户id和用户id查询园区时长记录.
     * 
     * @param userId 用户id
     * @param merchantId 商户id
     * @return 园区时长记录
     * @author 尤小平  
     * @date 2017年7月31日 下午4:26:05
     */
    public List<UserCutoff> getUserCutoffList(Long userId, Long merchantId) {
        logger.debug("userId=" + userId + ", merchantId=" + merchantId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (userId != null) {
            paramMap.put("userId", userId);
        }
        if (merchantId != null) {
            paramMap.put("merchantId", merchantId);
        }

        List<UserCutoff> list = userCutoffService.selectBymap(paramMap);
        logger.debug("list.size()=" + list.size());
        
        return list;
    }
    
    public CenterVipUserServiceImpl getVipUserService() {
        return vipUserService;
    }

    public void setVipUserService(CenterVipUserServiceImpl vipUserService) {
        this.vipUserService = vipUserService;
    }

    public UserCutoffService getUserCutoffService() {
        return userCutoffService;
    }

    public void setUserCutoffService(UserCutoffService userCutoffService) {
        this.userCutoffService = userCutoffService;
    }

    public UserConsumeService getUserConsumeService() {
        return userConsumeService;
    }

    public void setUserConsumeService(UserConsumeService userConsumeService) {
        this.userConsumeService = userConsumeService;
    }

    public TimePackageService getTimePackageService() {
        return timePackageService;
    }

    public void setTimePackageService(TimePackageService timePackageService) {
        this.timePackageService = timePackageService;
    }
    
    public MerchantExtendsService getMerchantExtendsService() {
        return merchantExtendsService;
    }

    public void setMerchantExtendsService(MerchantExtendsService merchantExtendsService) {
        this.merchantExtendsService = merchantExtendsService;
    }

}
